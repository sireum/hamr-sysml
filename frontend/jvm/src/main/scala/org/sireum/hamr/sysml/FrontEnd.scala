// #Sireum
package org.sireum.hamr.sysml

import org.sireum._
import org.sireum.hamr.codegen.common.symbols.{AadlPort, AadlThread, GclAnnexClauseInfo}
import org.sireum.hamr.codegen.common.util.{CodeGenConfig, CodeGenIpcMechanism, CodeGenPlatform, ModelUtil}
import org.sireum.hamr.ir
import org.sireum.hamr.ir.{Aadl, ConnectionInstance, GclSpec, GclSubclause, GclGuarantee, GclAssume}
import org.sireum.hamr.ir.SysmlAst.TopUnit
import org.sireum.hamr.ir.instantiation.ConnectionInstantiator
import org.sireum.hamr.sysml.instantiation.Instantiate
import org.sireum.hamr.sysml.library.Sysmlv2Library
import org.sireum.hamr.sysml.stipe.{TypeChecker, TypeHierarchy, TypeOutliner}
import org.sireum.hamr.sysml.symbol.{DelineableTypeInfo, GlobalDeclarationResolver, Info, Resolver}
import org.sireum.hamr.sysml.symbol.Resolver.{NameMap, TypeMap, resolverKind}
import org.sireum.lang.{ast => AST}
import org.sireum.lang.{FrontEnd => SlangFrontEnd}
import org.sireum.lang.tipe.{TypeHierarchy => SlangTypeHierarchy}
import org.sireum.message.{Message, Position, Reporter}

object FrontEnd {

  @datatype class IntegerationConstraints (val systemRootName: ISZ[String],
                                           val systemRootPos: Option[Position],
                                           val typeHierarchy: SlangTypeHierarchy,
                                           val connections: ISZ[IntegerationConnection])

  @datatype class IntegerationConnection(val srcPort: AadlPort,
                                         val dstPort: AadlPort,
                                         val srcConstraint: Option[AST.Exp], // assume this
                                         val dstConstraint: Option[AST.Exp], // assert this

                                         val connectionReferences: HashSMap[ISZ[String], Option[Position]])

  def typeCheck(par: Z, inputs: ISZ[Input], reporter: Reporter): (Option[TypeHierarchy], ISZ[ModelUtil.ModelElements]) = {

    val (thl, rep) = libraryReporter

    if (rep.hasError) {
      rep.printMessages()
      return (None(), ISZ())
    }

    // TODO: pass in reporter
    val (reporter_, topUnits, globalNameMap, globalTypeMap) = parseAndGloballyResolve(par, inputs, thl.typeHierarchy.nameMap, thl.typeHierarchy.typeMap)
    reporter.reports(reporter_.messages)

    if (reporter.hasError) {
      return (None(), ISZ())
    }

    var th = TypeHierarchy.build(F, TypeHierarchy(globalNameMap, globalTypeMap, thl.typeHierarchy.poset, HashSMap.empty), reporter)

    if (reporter.hasError) {
      return (Some(th), ISZ())
    }

    th = TypeOutliner.checkOutline(par, th, reporter)

    if (reporter.hasError) {
      return (Some(th), ISZ())
    }

    th = TypeChecker.checkDefinitions(par, th, reporter)

    if (reporter.hasError) {
      return (Some(th), ISZ())
    }

    val iopts = Instantiate.instantiate(topUnits, th, reporter)

    if (reporter.hasError) {
      return (Some(th), ISZ())
    }

    iopts match {
      case Some((th, models)) =>
        var imodels: ISZ[ModelUtil.ModelElements] = ISZ()
        for (model <- models) {
          val connModel = ConnectionInstantiator.instantiateConnections(model, reporter)

          if (reporter.hasError) {
            return (Some(th), ISZ())
          }

          ModelUtil.resolve(connModel, "model", baseOptions, reporter) match {
            case Some(modelElements) =>
              imodels = imodels :+ modelElements
            case _ =>
          }
        }

        return (Some(th), imodels)
      case _ =>
        return (Some(th), ISZ())
    }
  }

  def getIntegerationConstraints(modelElements: ISZ[ModelUtil.ModelElements], reporter: Reporter): ISZ[IntegerationConstraints] = {

    var integrationConstraints: ISZ[IntegerationConstraints] = ISZ()

    for(me <- modelElements) {

      var portConstraints: Map[ISZ[String], (AadlPort, GclSpec)] = Map.empty
      var th: SlangTypeHierarchy = SlangTypeHierarchy.empty

      for(sc <- me.symbolTable.annexClauseInfos.entries) {
        for (aci <- sc._2) {
          aci match {
            case GclAnnexClauseInfo(annex, gclSymbolTable) if gclSymbolTable.integrationMap.nonEmpty =>
              val c = SlangFrontEnd.combineParseResult(
                r = (ISZ(), ISZ(AST.TopUnit.Program.empty), th.nameMap, th.typeMap),
                u = SlangFrontEnd.ParseResult(
                  program = AST.TopUnit.Program.empty,
                  nameMap = gclSymbolTable.slangTypeHierarchy.nameMap,
                  typeMap = gclSymbolTable.slangTypeHierarchy.typeMap,
                  messages = ISZ())
              )
              reporter.reports(c._1)

              if(reporter.hasError) {
                reporter.printMessages()
                return ISZ()
              }

              th = th(nameMap = c._3, typeMap = c._4)

              portConstraints = portConstraints ++ (for(e <- gclSymbolTable.integrationMap.entries) yield e._1.path ~> (e._1, e._2))
            case _ =>
          }
        }
      }

      var integrationConnections: ISZ[IntegerationConnection] = ISZ()
      for(ci <- me.symbolTable.connections) {
        def resolve(fname: ISZ[String], cname: ISZ[String]): (AadlPort, Option[AST.Exp]) = {
          portConstraints.get(fname) match {
            case Some((aadlPort, GclAssume(_, _, cons))) =>
              assert(aadlPort.direction == ir.Direction.In)
              return (aadlPort, Some(cons))
            case Some((aadlPort, GclGuarantee(_, _, cons))) =>
              assert(aadlPort.direction == ir.Direction.Out)
              return (aadlPort, Some(cons))
            case _ =>
              me.symbolTable.componentMap.get(cname) match {
                case Some(aadlThread: AadlThread) =>
                  return (aadlThread.getPortByPath(fname).get, None())
                case _ =>
                  halt("Infeasible: gumbo integration constraints can only be applied to thread ports")
              }
          }
        }

        val srcCon = resolve(ci.src.feature.get.name, ci.src.component.name)
        val dstCon = resolve(ci.dst.feature.get.name, ci.dst.component.name)

        var connRefs: HashSMap[ISZ[String], Option[Position]] = HashSMap.empty
        for(cr <- ci.connectionRefs) {
          connRefs = connRefs + cr.name.name ~> cr.name.pos
        }

        if (srcCon._2.nonEmpty || dstCon._2.nonEmpty) {
          integrationConnections = integrationConnections :+ IntegerationConnection(
            srcPort = srcCon._1,
            dstPort = dstCon._1,
            srcConstraint = srcCon._2,
            dstConstraint = dstCon._2,
            connectionReferences = connRefs)
        }
      }

      if (integrationConnections.nonEmpty) {
        integrationConstraints = integrationConstraints :+
          IntegerationConstraints(
            systemRootName = me.symbolTable.rootSystem.path,
            systemRootPos = me.symbolTable.rootSystem.component.identifier.pos,
            typeHierarchy = th,
            connections = integrationConnections)
      }
    }

    return integrationConstraints
  }

  def libraryReporter: (TypeChecker, Reporter) = {
    val initNameMap: NameMap = HashSMap.empty
    val initTypeMap: TypeMap = HashSMap.empty

    val (reporter, _, nameMap, typeMap) =
      parseAndGloballyResolve(0, for (f <- Sysmlv2Library.files) yield Input(f._2, f._1), initNameMap, initTypeMap)
    val th =
      TypeHierarchy.build(F, TypeHierarchy(nameMap, typeMap, Poset.empty, HashSMap.empty), reporter)
    val thOutlined = TypeOutliner.checkOutline(0, th, reporter)
    val tc = TypeChecker(thOutlined, ISZ())
    val r = (tc, reporter)
    return r
  }

  // TODO: remove instantiator's dependence on codegen's options
  val baseOptions: CodeGenConfig =
    CodeGenConfig(
      writeOutResources = T,
      ipc = CodeGenIpcMechanism.SharedMemory,

      runtimeMonitoring = F,
      verbose = F,
      platform = CodeGenPlatform.JVM,
      slangOutputDir = None(),
      packageName = None(),
      noProyekIve = T,
      noEmbedArt = F,
      devicesAsThreads = T,
      genSbtMill = T,
      slangAuxCodeDirs = ISZ(),
      slangOutputCDir = None(),
      excludeComponentImpl = F,
      bitWidth = 64,
      maxStringSize = 256,
      maxArraySize = 1,
      runTranspiler = F,
      camkesOutputDir = None(),
      camkesAuxCodeDirs = ISZ(),
      aadlRootDir = None(),
      experimentalOptions = ISZ()
    )


  @datatype class Input(val content: String,
                        val fileUri: Option[String]) {
    @memoize def fingerprint: ISZ[U8] = {
      return ops.StringOps(content).sha3(T, T)
    }

    @memoize def parseGloballyResolve: ParseResult = {
      return FrontEnd.parseGloballyResolve(this)
    }
  }

  @datatype class ParseResult(val topUnit: TopUnit,
                              val nameMap: NameMap,
                              val typeMap: TypeMap,
                              val messages: ISZ[Message])

  object ParseResultMap {
    @strictpure def empty: ParseResultMap = ParseResultMap(HashMap.empty)
  }

  @datatype class ParseResultMap(val map: HashMap[String, ParseResult]) {
    def update(input: Input): ParseResultMap = {
      val pr = parseGloballyResolve(input)
      return ParseResultMap(map + pr.topUnit.fileUri.get ~> pr)
    }

    def updates(par: Z, inputs: ISZ[Input]): ParseResultMap = {
      val prs = ops.ISZOps(inputs).parMapCores(parseGloballyResolve _, par)
      var m = map
      for (pr <- prs if pr.topUnit.fileUri.nonEmpty) {
        m = m + pr.topUnit.fileUri.get ~> pr
      }
      return ParseResultMap(m)
    }

    def merge(nameMap: NameMap, typeMap: TypeMap): (ISZ[Message], ISZ[TopUnit], NameMap, TypeMap) = {
      return ops.ISZOps(map.values).foldLeft(combineParseResult _, (ISZ[Message](), ISZ[TopUnit](), nameMap, typeMap))
    }
  }

  @pure def parseGloballyResolve(input: Input): ParseResult = {
    val reporter = Reporter.create
    val topUnitOpt = SysMLParser.parse(input.fileUri, input.content, reporter)
    val nameMap: NameMap = HashSMap.empty
    val typeMap: TypeMap = HashSMap.empty
    if (reporter.hasError) {
      return ParseResult(TopUnit.empty(fileUri = input.fileUri), nameMap, typeMap,
        reporter.messages)
    }
    topUnitOpt match {
      case Some(topUnit) =>
        val gdr = GlobalDeclarationResolver(nameMap, typeMap, Reporter.create)
        gdr.resolveTopUnit(topUnit)
        reporter.reports(gdr.reporter.messages)
        return ParseResult(topUnit, gdr.globalNameMap, gdr.globalTypeMap, reporter.messages)
      case _ =>
        return ParseResult(TopUnit.empty, nameMap, typeMap, reporter.messages)
    }
  }

  @pure def combineParseResult(r: (ISZ[Message], ISZ[TopUnit], NameMap, TypeMap),
                               u: ParseResult): (ISZ[Message], ISZ[TopUnit], NameMap, TypeMap) = {
    var rNameMap = r._3
    var rTypeMap = r._4
    val uNameMap = u.nameMap
    val uTypeMap = u.typeMap
    val reporter = Reporter.create
    for (p <- uNameMap.entries) {
      val name = p._1
      val uInfo = p._2
      rNameMap.get(name) match {
        case Some(rInfo) if !Resolver.isPosUriSuffixEq(rInfo.posOpt, uInfo.posOpt) =>
          (rInfo, uInfo) match {
            case (_: Info.Package, _: Info.Package) =>
            case _ =>
              rInfo.posOpt match {
                case Some(pos) =>
                  val file: String = pos.uriOpt match {
                    case Some(fileUri) => s" in $fileUri"
                    case _ => ""
                  }
                  reporter.error(uInfo.posOpt, resolverKind, st"Name '${(name, ".")}' has already been declared at [${pos.beginLine}, ${pos.beginColumn}]$file".render)
                case _ =>
              }
          }
        case _ => rNameMap = rNameMap + name ~> uInfo
      }
    }

    for (t <- uTypeMap.entries) {
      val name = t._1
      val uInfo = t._2
      rTypeMap.get(name) match {
        case Some(rInfo) if !Resolver.isPosUriSuffixEq(rInfo.posOpt, uInfo.posOpt) =>
          rInfo.posOpt match {
            case Some(pos) =>
              val file: String = pos.uriOpt match {
                case Some(fileUri) => s" in $fileUri"
                case _ => ""
              }
              reporter.error(uInfo.posOpt, resolverKind, st"Type name '${(name, ".")}' has already been declared at [${pos.beginLine}, ${pos.beginColumn}]$file".render)
            case _ =>
          }
        case _ => rTypeMap = rTypeMap + name ~> uInfo
      }
    }
    val topUnits: ISZ[TopUnit] = if (u.topUnit == TopUnit.empty) r._2 else r._2 :+ u.topUnit
    return (r._1 ++ u.messages ++ reporter.messages, topUnits, rNameMap, rTypeMap)
  }

    @pure def parseAndGloballyResolve(par: Z,
                                    inputs: ISZ[Input],
                                    globalNameMap: NameMap,
                                    globalTypeMap: TypeMap): (Reporter, ISZ[TopUnit], NameMap, TypeMap) = {
      val prm = ParseResultMap.empty.updates(par, inputs)
      val t = prm.merge(globalNameMap, globalTypeMap)
      val p = Resolver.addBuiltIns(t._3, t._4)
      val reporter = Reporter.create
      reporter.reports(t._1)
      val nameMap = p._1
      val typeMap = p._2

      for (topUnit <- t._2) {
        /*
        for (stmt <- program.body.stmts) {
          stmt match {
            case stmt: AST.Stmt.Import =>
              Resolver.checkImport(AST.Util.ids2strings(program.packageName.ids), stmt, nameMap, typeMap, reporter)
            case _ =>
          }
        }
        */
      }

      return (reporter, t._2, nameMap, typeMap)
  }
}
