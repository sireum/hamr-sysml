// #Sireum
package org.sireum.hamr.sysml.stipe

import org.sireum._
import org.sireum.hamr.sysml.{ast => SAST}
import org.sireum.hamr.sysml.symbol.Resolver.QName
import org.sireum.hamr.sysml.symbol.{Info, Scope, TypeInfo, Util}
import org.sireum.message.{Message, Reporter}

object TypeChecker {

  val typeCheckerKind = "Type Checker"

  def checkDefinitions(par: Z, th: TypeHierarchy, reporter: Reporter): TypeHierarchy = {
    var jobs = ISZ[() => (TypeHierarchy => (TypeHierarchy, ISZ[Message]) @pure) @pure]()

    for (info <- th.typeMap.values) {
      info match {
        case info: TypeInfo.AllocationDefinition =>
          jobs = jobs :+ (() => TypeChecker(th, info.name).checkAllocationDefinition(info))
        case info: TypeInfo.AttributeDefinition =>
          jobs = jobs :+ (() => TypeChecker(th, info.name).checkAttributeDefinition(info))
        case info: TypeInfo.ConnectionDefinition =>
          jobs = jobs :+ (() => TypeChecker(th, info.name).checkConnectionDefinition(info))
        case info: TypeInfo.PartDefinition =>
          jobs = jobs :+ (() => TypeChecker(th, info.name).checkPartDefinition(info))
        case info: TypeInfo.PortDefinition =>
          jobs = jobs :+ (() => TypeChecker(th, info.name).checkPortDefinition(info))
        case _ =>
      }
    }

    val init = (th, ISZ[Message]())
    val p = ops.ISZOps(jobs).parMapFoldLeftCores(
      (f: () => (TypeHierarchy => (TypeHierarchy, ISZ[Message]) @pure)@pure) => f(), TypeHierarchy.combine _, init, par)
    var r = p._1

    reporter.reports(p._2)
    return r
  }
}

@datatype class TypeChecker(val typeHierarchy: TypeHierarchy,
                            val context: QName) {
  def checkAllocationDefinition(info: TypeInfo.AllocationDefinition): TypeHierarchy => (TypeHierarchy, ISZ[Message]) = {
    assert (info.outlined, st"${(info.name, "::")} is not outline".render)
    val reporter = Reporter.create

    val messages = reporter.messages

    val newInfo = info

    return (th: TypeHierarchy) => (th(typeMap = th.typeMap + info.name ~> newInfo), messages)
  }

  def checkAttributeDefinition(info: TypeInfo.AttributeDefinition): TypeHierarchy => (TypeHierarchy, ISZ[Message]) = {
    assert (info.outlined, st"${(info.name, "::")} is not outline".render)
    val reporter = Reporter.create

    val scope = Scope.Local.create(info.scope)
    val newBodyItems = checkBodyItems(scopes = ISZ(scope), info.ast.bodyItems, reporter)

    val messages = reporter.messages

    var attributeUsages: HashSMap[String, Info.AttributeUsage] = info.members.attributeUsages
    var connectionUsages: HashSMap[String, Info.ConnectionUsage] = info.members.connectionUsages
    var itemUsages: HashSMap[String, Info.ItemUsage] = info.members.itemUsages
    var partUsages: HashSMap[String, Info.PartUsage] = info.members.partUsages
    var portUsages: HashSMap[String, Info.PortUsage] = info.members.portUsages
    for (bi <- newBodyItems) {
      bi match {
        case nast: SAST.SysmlAst.AttributeUsage if nast.identification.nonEmpty =>
          val (id, posOpt) = Util.getId(nast.identification, nast.posOpt, TypeChecker.typeCheckerKind, reporter)
          val bInfo = info.members.attributeUsages.get(id.get).get
          attributeUsages = attributeUsages + id.get ~> bInfo(ast = nast)

        case nast: SAST.SysmlAst.ConnectionUsage if nast.identification.nonEmpty =>
          val (id, posOpt) = Util.getId(nast.identification, nast.posOpt, TypeChecker.typeCheckerKind, reporter)
          val bInfo = info.members.connectionUsages.get(id.get).get
          connectionUsages = connectionUsages + id.get ~> bInfo(ast = nast)

        case nast: SAST.SysmlAst.ItemUsage if nast.identification.nonEmpty =>
          val (id, posOpt) = Util.getId(nast.identification, nast.posOpt, TypeChecker.typeCheckerKind, reporter)
          val bInfo = info.members.itemUsages.get(id.get).get
          itemUsages = itemUsages + id.get ~> bInfo(ast = nast)

        case nast: SAST.SysmlAst.PartUsage if nast.identification.nonEmpty =>
          val (id, posOpt) = Util.getId(nast.identification, nast.posOpt, TypeChecker.typeCheckerKind, reporter)
          val bInfo = info.members.partUsages.get(id.get).get
          partUsages = partUsages + id.get ~> bInfo(ast = nast)

        case nast: SAST.SysmlAst.PortUsage if nast.identification.nonEmpty =>
          val (id, posOpt) = Util.getId(nast.identification, nast.posOpt, TypeChecker.typeCheckerKind, reporter)
          val bInfo = info.members.portUsages.get(id.get).get
          portUsages = portUsages + id.get ~> bInfo(ast = nast)

        case _ =>
      }
    }

    val newInfo = info(
      typeChecked = T,
      ast = info.ast(bodyItems = newBodyItems),
      members = TypeInfo.Members(
        attributeUsages = attributeUsages,
        connectionUsages = connectionUsages,
        itemUsages = itemUsages,
        partUsages = partUsages,
        portUsages = portUsages)
    )
    return (th: TypeHierarchy) => (th(typeMap = th.typeMap + info.name ~> newInfo), messages)
  }

  def checkConnectionDefinition(info: TypeInfo.ConnectionDefinition): TypeHierarchy => (TypeHierarchy, ISZ[Message]) = {
    assert (info.outlined, st"${(info.name, "::")} is not outline".render)
    val reporter = Reporter.create

    reporter.warn(info.posOpt, TypeChecker.typeCheckerKind, "Need to handle connection defs")

    val messages = reporter.messages
    val newInfo = info

    return (th: TypeHierarchy) => (th(typeMap = th.typeMap + info.name ~> newInfo), messages)
  }

  def checkPartDefinition(info: TypeInfo.PartDefinition): TypeHierarchy => (TypeHierarchy, ISZ[Message]) = {
    assert (info.outlined, st"${(info.name, "::")} is not outline".render)
    val reporter = Reporter.create

    reporter.warn(info.posOpt, TypeChecker.typeCheckerKind, "Need to handle part defs")

    val messages = reporter.messages
    val newInfo = info

    return (th: TypeHierarchy) => (th(typeMap = th.typeMap + info.name ~> newInfo), messages)
  }

  def checkPortDefinition(info: TypeInfo.PortDefinition): TypeHierarchy => (TypeHierarchy, ISZ[Message]) = {
    assert (info.outlined, st"${(info.name, "::")} is not outline".render)
    val reporter = Reporter.create

    reporter.warn(info.posOpt, TypeChecker.typeCheckerKind, "Need to handle port defs")

    val messages = reporter.messages
    val newInfo = info

    return (th: TypeHierarchy) => (th(typeMap = th.typeMap + info.name ~> newInfo), messages)
  }




  def checkBodyItems(scopes: ISZ[Scope.Local], bodyItems: ISZ[SAST.SysmlAst.BodyElement], reporter: Reporter): ISZ[SAST.SysmlAst.BodyElement] = {
    return bodyItems
  }
}
