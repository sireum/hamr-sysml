// #Sireum
package org.sireum.hamr.sysml

import org.sireum._
import org.sireum.hamr.sysml.ast.SysmlAst.TopUnit
import org.sireum.hamr.sysml.symbol.{GlobalDeclarationResolver, Info, Resolver}
import org.sireum.hamr.sysml.symbol.Resolver.{NameMap, TypeMap, resolverKind}
import org.sireum.lang.ast.Transformer
import org.sireum.message.{Message, Reporter}

object FrontEnd {

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
