// #Sireum
package org.sireum.hamr.sysml

import org.sireum._
import org.sireum.hamr.codegen.common.CommonUtil.Store
import org.sireum.hamr.codegen.common.symbols.{AadlPort, AadlThread, GclAnnexClauseInfo}
import org.sireum.hamr.codegen.common.util.HamrCli.{CodegenHamrPlatform, CodegenLaunchCodeLanguage, CodegenNodesCodeLanguage, CodegenOption}
import org.sireum.hamr.codegen.common.util.ModelUtil
import org.sireum.hamr.ir
import org.sireum.hamr.ir.SysmlAst.TopUnit
import org.sireum.hamr.ir.instantiation.ConnectionInstantiator
import org.sireum.hamr.ir.{GclAssume, GclGuarantee, GclSpec}
import org.sireum.hamr.sysml.instantiation.Instantiate
import org.sireum.hamr.sysml.library.Sysmlv2Library
import org.sireum.hamr.sysml.parser.{SlangUtil, SysMLParser}
import org.sireum.hamr.sysml.stipe.{TypeChecker, TypeHierarchy, TypeOutliner}
import org.sireum.hamr.sysml.symbol.Resolver.{NameMap, TypeMap, resolverKind}
import org.sireum.hamr.sysml.symbol.{GlobalDeclarationResolver, Info, Resolver}
import org.sireum.lang.symbol.Scope.{Global => GlobalScope, Local => LocalScope}
import org.sireum.lang.tipe.{TypeChecker => SlangTypeChecker, TypeHierarchy => SlangTypeHierarchy}
import org.sireum.lang.{symbol, FrontEnd => SlangFrontEnd, ast => AST}
import org.sireum.message.{Message, Position, Reporter}

object FrontEnd {

  val integration_constraint_title_prefix: String = "Integration constraint of"

  @datatype class IntegerationConstraints (val systemRootName: ISZ[String],
                                           val systemRootPos: Option[Position],
                                           val typeHierarchy: SlangTypeHierarchy,

                                           // title ~> IntegrationConnection
                                           val connections: HashSMap[String, IntegerationConnection])

  @datatype class IntegerationConnection(val srcPort: AadlPort,
                                         val srcPortExp: AST.Exp,
                                         val srcGclSpec: Option[GclGuarantee],

                                         val dstPort: AadlPort,
                                         val dstPortExp: AST.Exp,
                                         val dstGclSpec: Option[GclAssume],

                                         val srcConstraint: Option[AST.Exp], // assume this
                                         val dstConstraint: Option[AST.Exp], // assert this

                                         // connectionMidPoint is the identifier path and position info of the
                                         // connection references where out connections change to in connections
                                         val connectionMidPoint: (ISZ[String], Option[Position]),

                                         val connectionReferences: HashSMap[ISZ[String], Option[Position]]) {

    val title: String = st"$integration_constraint_title_prefix ${(connectionMidPoint._1, ".")}".render

    @pure def claim: AST.Exp = {
      val andResOpt: AST.ResolvedInfo = AST.ResolvedInfo.BuiltIn(AST.ResolvedInfo.BuiltIn.Kind.BinaryAnd)
      val equivResOpt: AST.ResolvedInfo = AST.ResolvedInfo.BuiltIn(AST.ResolvedInfo.BuiltIn.Kind.BinaryEquiv)
      val implyResOpt: AST.ResolvedInfo = AST.ResolvedInfo.BuiltIn(AST.ResolvedInfo.BuiltIn.Kind.BinaryImply)

      val src: AST.Exp = srcConstraint match {
        case Some(e) => e
        case _ => AST.Exp.LitB(T, AST.Attr(srcPort.feature.identifier.pos))
      }

      val dst = dstConstraint.get

      val (ids, midPointPos) = connectionMidPoint
      val portEquiv = AST.Exp.Binary(srcPortExp, AST.Exp.BinaryOp.Equiv, dstPortExp,
        AST.ResolvedAttr(midPointPos, Some(equivResOpt), AST.Typed.bOpt), midPointPos)
      val lhs = AST.Exp.Binary(portEquiv, AST.Exp.BinaryOp.And, src,
        AST.ResolvedAttr(midPointPos, Some(andResOpt), AST.Typed.bOpt), midPointPos)
      return AST.Exp.Binary(lhs, AST.Exp.BinaryOp.CondImply, dst,
        AST.ResolvedAttr(midPointPos, Some(implyResOpt), AST.Typed.bOpt), midPointPos)
    }
  }

  def typeCheck(par: Z, inputs: ISZ[Input], store: Store, reporter: Reporter): (Option[TypeHierarchy], ISZ[ModelUtil.ModelElements], Store) = {
    var localStore = store

    val (thl, rep) = libraryReporter(par)

    if (rep.hasError) {
      return (None(), ISZ(), localStore)
    }

    // TODO: pass in reporter
    val (reporter_, topUnits, globalNameMap, globalTypeMap) = parseAndGloballyResolve(par, inputs, thl.typeHierarchy.nameMap, thl.typeHierarchy.typeMap)
    reporter.reports(reporter_.messages)

    if (reporter.hasError) {
      return (None(), ISZ(), localStore)
    }

    var th = TypeHierarchy.build(F, TypeHierarchy(globalNameMap, globalTypeMap, thl.typeHierarchy.poset, HashSMap.empty), reporter)

    if (reporter.hasError) {
      return (Some(th), ISZ(), localStore)
    }

    th = TypeOutliner.checkOutline(par, th, reporter)

    if (reporter.hasError) {
      return (Some(th), ISZ(), localStore)
    }

    th = TypeChecker.checkDefinitions(par, th, reporter)

    if (reporter.hasError) {
      return (Some(th), ISZ(), localStore)
    }

    val iopts = Instantiate.instantiate(topUnits, th, reporter)

    if (reporter.hasError) {
      return (Some(th), ISZ(), localStore)
    }

    iopts match {
      case Some((th2, models)) =>
        var imodels: ISZ[ModelUtil.ModelElements] = ISZ()
        for (model <- models) {
          val connModel = ConnectionInstantiator.instantiateConnections(model._1, reporter)

          if (reporter.hasError) {
            return (Some(th2), ISZ(), localStore)
          }

          val r = ModelUtil.resolve(connModel, model._2, "model", baseOptions, localStore, reporter)

          if (reporter.hasError) {
            return (Some(th2), ISZ(), localStore)
          }

          r match {
            case (Some(modelElements), s) =>
              imodels = imodels :+ modelElements
              localStore = s
            case _ =>
          }
        }

        return (Some(th2), imodels, localStore)
      case _ =>
        return (Some(th), ISZ(), localStore)
    }
  }

  def getIntegerationConstraints(modelElements: ISZ[ModelUtil.ModelElements], reporter: Reporter): ISZ[IntegerationConstraints]= {

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
                return ISZ()
              }

              th = th(nameMap = c._3, typeMap = c._4)

              portConstraints = portConstraints ++ (for(e <- gclSymbolTable.integrationMap.entries) yield e._1.path ~> (e._1, e._2))
            case _ =>
          }
        }
      }

      var integrationConnections: HashSMap[String, IntegerationConnection] = HashSMap.empty
      for(ci <- me.symbolTable.connections) {
        def resolve(fname: ISZ[String], cname: ISZ[String]): (AadlPort, Option[AST.Exp], Option[GclSpec]) = {
          portConstraints.get(fname) match {
            case Some((aadlPort, a : GclAssume)) =>
              assert(aadlPort.direction == ir.Direction.In)
              return (aadlPort, Some(a.exp), Some(a))
            case Some((aadlPort, g: GclGuarantee)) =>
              assert(aadlPort.direction == ir.Direction.Out)
              return (aadlPort, Some(g.exp), Some(g))
            case _ =>
              me.symbolTable.componentMap.get(cname) match {
                case Some(aadlThread: AadlThread) =>
                  return (aadlThread.getPortByPath(fname).get, None(), None())
                case _ =>
                  halt("Infeasible: gumbo integration constraints can only be applied to thread ports")
              }
          }
        }

        val midPoint = me.symbolTable.getConnectionInstancePos(ci)

          def slangTypeCheck(context: ISZ[String], exp: AST.Exp, scope: LocalScope): AST.Exp = {
            val typeChecker: SlangTypeChecker = SlangTypeChecker(th, context, F, SlangTypeChecker.ModeContext.Spec, F)
            typeChecker.checkExp(None(), scope, exp, reporter) match {
              case (e, Some(_)) => return e
              case _ => halt(s"Infeasible: was not able to resolve type of $exp")
            }
          }
          def buildExp(port: AadlPort) : AST.Exp = {
            val parentThread = me.symbolTable.componentMap.get(ops.ISZOps(port.path).dropRight(1)).get
            val fqPortPath = parentThread.classifier :+ port.identifier
            th.nameMap.get(fqPortPath) match {
              case Some(symbol.Info.Var(_, _, globalScope: GlobalScope, _)) =>
                val select = SlangUtil.toSelectH(for (p <- fqPortPath) yield AST.Id(p, AST.Attr(midPoint._2)))
                return slangTypeCheck(parentThread.classifier, select, LocalScope.create(HashMap.empty, globalScope))
              case x =>
                halt(s"Infeasible: $fqPortPath did not resolve to a var: $x")
            }
          }

        val srcCon = resolve(ci.src.feature.get.name, ci.src.component.name)
        val dstCon = resolve(ci.dst.feature.get.name, ci.dst.component.name)

        var connRefs: HashSMap[ISZ[String], Option[Position]] = HashSMap.empty
        for(cr <- ci.connectionRefs) {
          connRefs = connRefs + cr.name.name ~> cr.name.pos
        }

        if (srcCon._2.nonEmpty || dstCon._2.nonEmpty) {
          val ic = IntegerationConnection(
            srcPort = srcCon._1,
            srcPortExp = buildExp(srcCon._1),
            srcGclSpec = srcCon._3.asInstanceOf[Option[GclGuarantee]],

            dstPort = dstCon._1,
            dstPortExp = buildExp(dstCon._1),
            dstGclSpec = dstCon._3.asInstanceOf[Option[GclAssume]],

            srcConstraint = srcCon._2,
            dstConstraint = dstCon._2,

            connectionMidPoint = midPoint,
            connectionReferences = connRefs)
          integrationConnections = integrationConnections + ic.title ~> ic
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

  def libraryReporter(par: Z) : (TypeChecker, Reporter) = {
    val initNameMap: NameMap = HashSMap.empty
    val initTypeMap: TypeMap = HashSMap.empty

    val (reporter, _, nameMap, typeMap) =
      parseAndGloballyResolve(par, for (f <- Sysmlv2Library.files) yield Input(f._2, f._1), initNameMap, initTypeMap)
    val th =
      TypeHierarchy.build(F, TypeHierarchy(nameMap, typeMap, Poset.empty, HashSMap.empty), reporter)
    val thOutlined = TypeOutliner.checkOutline(par, th, reporter)
    val tc = TypeChecker(thOutlined, ISZ())
    val r = (tc, reporter)
    return r
  }

  // TODO: remove instantiator's dependence on codegen's options
  val baseOptions: CodegenOption =
    CodegenOption(
      help = "",
      args = ISZ(),
      msgpack = F,
      verbose = F,
      runtimeMonitoring = F,
      platform = CodegenHamrPlatform.JVM,
      outputDir = None(),
      parseableMessages = F,
      //
      slangOutputDir = None(),
      packageName = None(),
      noProyekIve = T,
      noEmbedArt = F,
      devicesAsThreads = T,
      genSbtMill = T,
      //
      slangAuxCodeDirs = ISZ(),
      slangOutputCDir = None(),
      excludeComponentImpl = F,
      bitWidth = 64,
      maxStringSize = 256,
      maxArraySize = 1,
      runTranspiler = F,
      //
      sel4OutputDir = None(),
      sel4AuxCodeDirs = ISZ(),
      workspaceRootDir = None(),
      //
      strictAadlMode = F,
      ros2OutputWorkspaceDir = None(),
      ros2Dir = None(),
      ros2NodesLanguage = CodegenNodesCodeLanguage.Cpp,
      ros2LaunchLanguage = CodegenLaunchCodeLanguage.Xml,
      invertTopicBinding = F,
      //
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
