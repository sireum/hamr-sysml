// #Sireum
package org.sireum.hamr.sysml.stipe

import org.sireum._
import org.sireum.hamr.ir.SysmlAst.{BinaryConnectorPart, GumboAnnotation, RedefinitionsSpecialization, SubsettingsSpecialization, TypingsSpecialization}
import org.sireum.hamr.ir.{GclLib, GclMethod, GclStateVar, GclSubclause, ResolvedInfo, SysmlAst, Typed}
import org.sireum.hamr.{ir => SAST}
import org.sireum.hamr.sysml.symbol.Resolver.NameMap
import org.sireum.hamr.sysml.symbol.Resolver.QName
import org.sireum.hamr.sysml.symbol.{Info, Scope, TypeInfo, Util}
import org.sireum.lang.ast.Exp
import org.sireum.lang.{ast => AST}
import org.sireum.message.{Message, Position, Reporter}
import TypeChecker._
import org.sireum.hamr.sysml.parser.UIF

object TypeChecker {

  val typeCheckerKind: String = "Type Checker"

  def checkDefinitions(par: Z, th: TypeHierarchy, reporter: Reporter): TypeHierarchy = {
    var jobs = ISZ[() => (TypeHierarchy => (TypeHierarchy, ISZ[Message]) @pure) @pure]()

    var initTh = th
    var initMessages = ISZ[Message]()

    // Resolve any usages at the package level
    for (info <- th.typeMap.values) {
      info match {
        case info: TypeInfo.Package if (info.members.nonEmpty) =>
          val x = TypeChecker(initTh, info.name).checkPackage(info)(initTh)
          initMessages = x._2
          initTh = x._1
        case _ =>
      }
    }

    for (info <- th.typeMap.values) {
      info match {
        //case info: TypeInfo.Package if (info.members.nonEmpty) =>
        //  jobs = jobs :+ (() => TypeChecker(th, info.name).checkPackage(info))
        case info: TypeInfo.AllocationDefinition =>
          jobs = jobs :+ (() => TypeChecker(initTh, info.name).checkAllocationDefinition(info))
        case info: TypeInfo.AttributeDefinition =>
          jobs = jobs :+ (() => TypeChecker(initTh, info.name).checkAttributeDefinition(info))
        case info: TypeInfo.ConnectionDefinition =>
          jobs = jobs :+ (() => TypeChecker(initTh, info.name).checkConnectionDefinition(info))
        case info: TypeInfo.PartDefinition =>
          jobs = jobs :+ (() => TypeChecker(initTh, info.name).checkPartDefinition(info))
        case info: TypeInfo.PortDefinition =>
          jobs = jobs :+ (() => TypeChecker(initTh, info.name).checkPortDefinition(info))
        case _ =>
      }
    }

    val p = ops.ISZOps(jobs).parMapFoldLeftCores(
      (f: () => (TypeHierarchy => (TypeHierarchy, ISZ[Message]) @pure) @pure) => f(), TypeHierarchy.combine _, (initTh, initMessages), par)
    var r = p._1

    reporter.reports(p._2)
    return r
  }

  def resolveMethod(m: GclMethod, scope: Scope, typeHierarchy: TypeHierarchy, reporter: Reporter): GclMethod = {
    var params: ISZ[AST.Param] = ISZ()

    def resolveType(t: AST.Type): Option[AST.Type] = {
      t match {
        case t: AST.Type.Named =>
          scope.resolveType(typeHierarchy.typeMap, Util.slangIds2string(t.name.ids)) match {
            case Some(t: TypeInfo.PartDefinition) =>
              val name = AST.Name(
                ids = for (id <- t.name) yield AST.Id(id, AST.Attr(t.posOpt)),
                attr = AST.Attr(t.posOpt)
              )
              val typedName = AST.Typed.Name(t.name, ISZ())
              val typedAttr = AST.TypedAttr(t.posOpt, Some(typedName))
              val named = AST.Type.Named(name, ISZ(), typedAttr)

              return Some(named)

            case Some(e: TypeInfo.EnumDefinition) =>
              val name = AST.Name(
                ids = for (id <- e.name) yield AST.Id(id, AST.Attr(e.posOpt)),
                attr = AST.Attr(e.posOpt)
              )
              val typedName = AST.Typed.Name(e.name, ISZ())
              val typedAttr = AST.TypedAttr(t.posOpt, Some(typedName))
              val named = AST.Type.Named(name, ISZ(), typedAttr)

              return Some(named)
            case _ =>
              reporter.error(t.posOpt, TypeChecker.typeCheckerKind, "Only expecting type to resolve to a part or enumeration definitions")
              return None()
          }
        case _ =>
          return None()
      }
    }

    for(p <- m.method.sig.params) {
      resolveType(p.tipe) match {
        case Some(t) => params = params :+ p(tipe = t)
        case _ =>
          reporter.error(p.id.attr.posOpt, TypeChecker.typeCheckerKind, "Could not resolve the parameter type")
          params = params :+ p
      }
    }

    val returnType: AST.Type = resolveType(m.method.sig.returnType) match {
      case Some(rt) => rt
      case _ =>
        reporter.error(m.method.sig.returnType.posOpt, TypeChecker.typeCheckerKind, "Could not resolve methods return type")
        m.method.sig.returnType
    }

    return m(method = m.method(sig = m.method.sig(params = params, returnType = returnType)))
  }

}

@datatype class TypeChecker(val typeHierarchy: TypeHierarchy,
                            val context: QName) {

  def checkPackage(info: TypeInfo.Package): TypeHierarchy => (TypeHierarchy, ISZ[Message]) @pure = {
    assert(info.outlined, st"${(info.name, "::")} is not outline".render)
    val reporter = Reporter.create

    var scope = Scope.Local.create(info.scope)
    scope = addMembersToScope(info.members, scope)

    val usages: ISZ[SAST.SysmlAst.DefinitionBodyItem] =
      for(u <- info.ast.packageElements.filter(p => p.isInstanceOf[SysmlAst.UsageElement])) yield u.asInstanceOf[SysmlAst.DefinitionBodyItem]
    assert(usages.nonEmpty)

    val newBodyItems = checkBodyItems(ISZ(scope), usages, reporter)

    val newMembers = updateMembers(newBodyItems, info.members, reporter)

    val x: ISZ[SysmlAst.PackageBodyElement] = for(u <- usages) yield u.asInstanceOf[SysmlAst.PackageBodyElement]
    val nonUsages: ISZ[SAST.SysmlAst.PackageBodyElement] =
      info.ast.packageElements.filter(p => !p.isInstanceOf[SysmlAst.UsageElement])

    val newInfo = info(
      typeChecked = T,
      ast = info.ast(packageElements = nonUsages ++ x),
      members = newMembers
    )

    var nameMap = typeHierarchy.nameMap
    for (m <- newMembers.attributeUsages.entries) {
      typeHierarchy.nameMap.get(m._2.name) match {
        case Some(au: Info.AttributeUsage) if au.typedOpt.isEmpty =>
          nameMap = nameMap + m._2.name ~> au(ast = m._2.ast)
        case _ =>
      }
    }

    return (th: TypeHierarchy) => (th(nameMap = nameMap, typeMap = th.typeMap + info.name ~> newInfo), reporter.messages)
  }

  def checkAllocationDefinition(info: TypeInfo.AllocationDefinition): TypeHierarchy => (TypeHierarchy, ISZ[Message]) @pure = {
    assert(info.outlined, st"${(info.name, "::")} is not outline".render)
    val reporter = Reporter.create

    var scope = Scope.Local.create(info.scope)
    scope = addMembersToScope(info.members, scope)

    val newBodyItems = checkBodyItems(ISZ(scope), info.ast.bodyItems, reporter)

    val newMembers = updateMembers(newBodyItems, info.members, reporter)

    val newInfo = info(
      typeChecked = T,
      ast = info.ast(bodyItems = newBodyItems),
      members = newMembers
    )
    return (th: TypeHierarchy) => (th(typeMap = th.typeMap + info.name ~> newInfo), reporter.messages)
  }

  def checkAttributeDefinition(info: TypeInfo.AttributeDefinition): TypeHierarchy => (TypeHierarchy, ISZ[Message]) @pure = {
    assert(info.outlined, st"${(info.name, "::")} is not outline".render)
    val reporter = Reporter.create

    var scope = Scope.Local.create(info.scope)
    scope = addMembersToScope(info.members, scope)

    val newBodyItems = checkBodyItems(ISZ(scope), info.ast.bodyItems, reporter)

    val newMembers = updateMembers(newBodyItems, info.members, reporter)

    val newInfo = info(
      typeChecked = T,
      ast = info.ast(bodyItems = newBodyItems),
      members = newMembers
    )

    return (th: TypeHierarchy) => (th(typeMap = th.typeMap + info.name ~> newInfo), reporter.messages)
  }

  def checkConnectionDefinition(info: TypeInfo.ConnectionDefinition): TypeHierarchy => (TypeHierarchy, ISZ[Message]) @pure = {
    assert(info.outlined, st"${(info.name, "::")} is not outline".render)
    val reporter = Reporter.create

    var scope = Scope.Local.create(info.scope)
    scope = addMembersToScope(info.members, scope)

    val newBodyItems = checkBodyItems(ISZ(scope), info.ast.bodyItems, reporter)

    val newMembers = updateMembers(newBodyItems, info.members, reporter)

    val newInfo = info(
      typeChecked = T,
      ast = info.ast(bodyItems = newBodyItems),
      members = newMembers
    )
    return (th: TypeHierarchy) => (th(typeMap = th.typeMap + info.name ~> newInfo), reporter.messages)
  }

  def checkPartDefinition(info: TypeInfo.PartDefinition): TypeHierarchy => (TypeHierarchy, ISZ[Message]) @pure = {
    assert(info.outlined, st"${(info.name, "::")} is not outline".render)
    val reporter = Reporter.create

    var scope = Scope.Local.create(info.scope)
    scope = addMembersToScope(info.members, scope)

    val newBodyItems = checkBodyItems(ISZ(scope), info.ast.bodyItems, reporter)

    val newMembers: TypeInfo.Members = updateMembers(newBodyItems, info.members, reporter)

    val allocationUsages: HashSMap[String, Info.AllocationUsage] = {
      var cus = newMembers.allocationUsages
      for (e <- newMembers.allocationUsages.entries) {
        val cu: Info.AllocationUsage = e._2
        cu.ast.connectorPart match {
          case Some(b @ SysmlAst.BinaryConnectorPart(src, dst)) =>
            def resolvePart(connEnd: SysmlAst.ConnectorEnd): Option[Info.PartUsage] = {
              assert(connEnd.reference.size == 1)
              val partId = connEnd.reference(connEnd.reference.lastIndex)
              assert (partId.ids.size == 1)
              newMembers.partUsages.get(partId.ids(partId.ids.lastIndex).value) match {
                case Some(p) => return Some(p)
                case _ =>
                  reporter.error(connEnd.resOpt.posOpt, TypeChecker.typeCheckerKind, s"'$partId' is not a valid part usage")
                  return None()
              }
            }
            (resolvePart(src), resolvePart(dst)) match {
              case (Some(srcInfo), Some(dstInfo)) =>
                cus = cus + e._1 ~> cu(
                  srcName = srcInfo.name,
                  srcAst = Some(srcInfo.ast),
                  dstName = dstInfo.name,
                  dstAst = Some(dstInfo.ast)
                )
              case _ =>
            }
          case _ =>
        }
      }
      cus
    }

    // resolve the connection ends
    val connectionsUsages: HashSMap[String, Info.ConnectionUsage] = {
      var cus = newMembers.connectionUsages
      for (e <-newMembers.connectionUsages.entries) {
        val cu: Info.ConnectionUsage = e._2
        cu.ast.connectorPart match {
          case Some(b @ SysmlAst.BinaryConnectorPart(src, dst)) =>

            def resolvePort(connEnd: SysmlAst.ConnectorEnd): Option[Info.PortUsage] = {
              assert(connEnd.reference.size >= 1)
              val srcPortId = connEnd.reference(connEnd.reference.lastIndex)

              val ports: HashSMap[String, Info.PortUsage] =
                if (connEnd.reference.size == 2) {
                  val receiver = connEnd.reference(0)
                  assert(receiver.ids.size == 1)
                  scope.resolveName(typeHierarchy.nameMap, Util.ids2string(receiver.ids)) match {
                    case Some(ipu: Info.PartUsage) =>
                      val typedName = ipu.typedOpt.get.asInstanceOf[Typed.Name]
                      typeHierarchy.typeMap.get(typedName.ids) match {
                        case Some(td: TypeInfo.PartDefinition) =>
                          td.members.portUsages
                        case _ =>
                          halt("Infeasible")
                      }
                    case x =>
                      reporter.error(receiver.posOpt, TypeChecker.typeCheckerKind, "Could not resolve the receiver's type")
                      return None()
                  }
                } else if (connEnd.reference.size == 1) {
                  newMembers.portUsages
                } else {
                  halt("Unexpected")
                }

              val portId = srcPortId.ids(srcPortId.ids.lastIndex).value
              ports.get(portId) match {
                case Some(p) => return Some(p)
                case _ =>
                  reporter.error(connEnd.resOpt.posOpt, TypeChecker.typeCheckerKind, s"'$portId' is not a valid port usage")
                  return None()
              }
            }

            (resolvePort(src), resolvePort(dst)) match {
              case (Some(srcInfo), Some(dstInfo)) =>
                cus = cus + e._1 ~> cu(
                  srcAst = Some(srcInfo.ast),
                  dstAst = Some(dstInfo.ast))
              case _ =>
            }


          case _ =>
        }
      }
      cus
    }

    val xm = newMembers(
      allocationUsages = allocationUsages,
      connectionUsages = connectionsUsages)
    val newInfo = info(
      typeChecked = T,
      ast = info.ast(bodyItems = newBodyItems),
      members = xm
    )
    return (th: TypeHierarchy) => (th(typeMap = th.typeMap + info.name ~> newInfo), reporter.messages)
  }

  def checkPortDefinition(info: TypeInfo.PortDefinition): TypeHierarchy => (TypeHierarchy, ISZ[Message]) @pure = {
    assert(info.outlined, st"${(info.name, "::")} is not outline".render)
    val reporter = Reporter.create

    var scope = Scope.Local.create(info.scope)
    scope = addMembersToScope(info.members, scope)

    val newBodyItems = checkBodyItems(scopes = ISZ(scope), bodyItems = info.ast.bodyItems, reporter = reporter)

    val newMembers = updateMembers(newBodyItems, info.members, reporter)

    val newInfo = info(
      typeChecked = T,
      ast = info.ast(bodyItems = newBodyItems),
      members = newMembers
    )
    return (th: TypeHierarchy) => (th(typeMap = th.typeMap + info.name ~> newInfo), reporter.messages)
  }

  def addMembersToScope(members: TypeInfo.Members, scope: Scope.Local): Scope.Local = {
    var nameMap = HashMap.empty[String, Info]

    def add(map: HashSMap[String, Info]): Unit = {
      for (e <- map.entries) {
        nameMap = nameMap + e
      }
    }

    add(members.allocationUsages.asInstanceOf[HashSMap[String, Info]])
    add(members.attributeUsages.asInstanceOf[HashSMap[String, Info]])
    add(members.connectionUsages.asInstanceOf[HashSMap[String, Info]])
    add(members.itemUsages.asInstanceOf[HashSMap[String, Info]])
    add(members.partUsages.asInstanceOf[HashSMap[String, Info]])
    add(members.portUsages.asInstanceOf[HashSMap[String, Info]])
    add(members.referenceUsages.asInstanceOf[HashSMap[String, Info]])

    return scope(nameMap = scope.nameMap ++ nameMap.entries)
  }

  def updateMembers(newBodyItems: ISZ[SAST.SysmlAst.DefinitionBodyItem], origMembers: TypeInfo.Members, reporter: Reporter): TypeInfo.Members = {

    var allocationUsages: HashSMap[String, Info.AllocationUsage] = origMembers.allocationUsages
    var attributeUsages: HashSMap[String, Info.AttributeUsage] = origMembers.attributeUsages
    var connectionUsages: HashSMap[String, Info.ConnectionUsage] = origMembers.connectionUsages
    var itemUsages: HashSMap[String, Info.ItemUsage] = origMembers.itemUsages
    var partUsages: HashSMap[String, Info.PartUsage] = origMembers.partUsages
    var portUsages: HashSMap[String, Info.PortUsage] = origMembers.portUsages
    var referenceUsages: HashSMap[String, Info.ReferenceUsage] = origMembers.referenceUsages

    for (bi <- newBodyItems) {
      bi match {
        case nast: SAST.SysmlAst.AllocationUsage if nast.commonUsageElements.identification.nonEmpty =>
          val (id, posOpt) = Util.getId(nast.commonUsageElements.identification, nast.commonUsageElements.specializations, nast.posOpt, TypeChecker.typeCheckerKind, reporter)
          val bInfo = origMembers.allocationUsages.get(id.get).get
          allocationUsages = allocationUsages + id.get ~> bInfo(ast = nast)

        case nast: SAST.SysmlAst.AttributeUsage if nast.commonUsageElements.identification.nonEmpty =>
          val (id, posOpt) = Util.getId(nast.commonUsageElements.identification, nast.commonUsageElements.specializations, nast.posOpt, TypeChecker.typeCheckerKind, reporter)
          val bInfo = origMembers.attributeUsages.get(id.get).get
          attributeUsages = attributeUsages + id.get ~> bInfo(ast = nast)

        case nast: SAST.SysmlAst.ConnectionUsage if nast.commonUsageElements.identification.nonEmpty =>
          val (id, posOpt) = Util.getId(nast.commonUsageElements.identification, nast.commonUsageElements.specializations, nast.posOpt, TypeChecker.typeCheckerKind, reporter)
          val bInfo = origMembers.connectionUsages.get(id.get).get
          connectionUsages = connectionUsages + id.get ~> bInfo(ast = nast)

        case nast: SAST.SysmlAst.ItemUsage if nast.commonUsageElements.identification.nonEmpty =>
          val (id, posOpt) = Util.getId(nast.commonUsageElements.identification, nast.commonUsageElements.specializations, nast.posOpt, TypeChecker.typeCheckerKind, reporter)
          val bInfo = origMembers.itemUsages.get(id.get).get
          itemUsages = itemUsages + id.get ~> bInfo(ast = nast)

        case nast: SAST.SysmlAst.PartUsage if nast.commonUsageElements.identification.nonEmpty =>
          val (id, posOpt) = Util.getId(nast.commonUsageElements.identification, nast.commonUsageElements.specializations, nast.posOpt, TypeChecker.typeCheckerKind, reporter)
          val bInfo = origMembers.partUsages.get(id.get).get
          partUsages = partUsages + id.get ~> bInfo(ast = nast)

        case nast: SAST.SysmlAst.PortUsage if nast.commonUsageElements.identification.nonEmpty =>
          val (id, posOpt) = Util.getId(nast.commonUsageElements.identification, nast.commonUsageElements.specializations, nast.posOpt, TypeChecker.typeCheckerKind, reporter)
          val bInfo = origMembers.portUsages.get(id.get).get
          portUsages = portUsages + id.get ~> bInfo(ast = nast)

        case nast: SAST.SysmlAst.ReferenceUsage if nast.commonUsageElements.identification.nonEmpty =>
          val (id, posOpt) = Util.getId(nast.commonUsageElements.identification, nast.commonUsageElements.specializations, nast.posOpt, TypeChecker.typeCheckerKind, reporter)
          val bInfo = origMembers.referenceUsages.get(id.get).get
          referenceUsages = referenceUsages + id.get ~> bInfo(ast = nast)

        case x: SAST.SysmlAst.UsageElement if x.commonUsageElements.identification.nonEmpty =>
          reporter.error(x.posOpt, TypeChecker.typeCheckerKind, s"Need to handle : $x")

        case _ =>

      }
    }
    return TypeInfo.Members(
      allocationUsages = allocationUsages,
      attributeUsages = attributeUsages,
      connectionUsages = connectionUsages,
      itemUsages = itemUsages,
      partUsages = partUsages,
      portUsages = portUsages,
      referenceUsages = referenceUsages)
  }

  // see TypeChecker.checkStmts
  def checkBodyItems(scopes: ISZ[Scope.Local], bodyItems: ISZ[SAST.SysmlAst.DefinitionBodyItem], reporter: Reporter): ISZ[SAST.SysmlAst.DefinitionBodyItem] = {
    var newBodyItems = ISZ[SAST.SysmlAst.DefinitionBodyItem]()

    def checkBodyItemH(i: Z): SAST.SysmlAst.DefinitionBodyItem = {
      val scope: Scope.Local = if (scopes.size == 1) scopes(0) else scopes(i)
      return checkBodyItem(scope, bodyItems(i), reporter)
    }

    val size = bodyItems.size //- 1
    for (i <- 0 until size ) {//if !reporter.hasError) {
      newBodyItems = newBodyItems :+ checkBodyItemH(i)
    }

    if (reporter.hasError) {
      return newBodyItems ++ ops.ISZOps(bodyItems).slice(newBodyItems.size, bodyItems.size)
    }

    if (size < 0) {
      return newBodyItems
    }

    return newBodyItems
  }

  def checkBodyItem(scope: Scope.Local, item: SysmlAst.DefinitionBodyItem, reporter: Reporter): SysmlAst.DefinitionBodyItem = {

    def update(u: SysmlAst.CommonUsageElements): SysmlAst.CommonUsageElements = {

      Util.getId(u.identification, u.specializations, u.attr.posOpt, TypeChecker.typeCheckerKind, reporter) match {
        case (Some(id), posOpt) =>
          var ru = u
          val resOpt: Option[SAST.ResolvedInfo] = scope.resolveName(typeHierarchy.nameMap, ISZ(id)) match {
            case Some(info: Info.UsageInfo) =>
              if (u.identification.isEmpty) {
                assert (u.specializations.nonEmpty, "expecting a redefinition")
                assert (u.attr.resOpt.isEmpty, "how did this get resolved")

                val tipeOpt: Option[SAST.Type] = {
                  u.specializations match {
                    case ISZ(r: RedefinitionsSpecialization) =>
                      // usage has the type of the usage it's redefining
                      info.ast.commonUsageElements.tipeOpt

                    case ISZ(r: SubsettingsSpecialization) =>
                      // usage has the type of the usage it's subsetting
                      info.ast.commonUsageElements.tipeOpt

                    case ISZ(_, ts:TypingsSpecialization) =>
                      ts.names match {
                        case ISZ(redefinedTypeName) =>
                          scope.resolveName(typeHierarchy.nameMap, ISZ(id)) match {
                            case Some(parentUsage : Info.UsageInfo) =>
                              scope.resolveType(typeHierarchy.typeMap, Util.ids2string(redefinedTypeName.ids)) match {
                                case Some(redefiningType) =>
                                  parentUsage.ast.commonUsageElements.attr.typedOpt match {
                                    case Some(t2) =>
                                      if (!typeHierarchy.isSubType(redefiningType.tpe, t2)) {
                                        reporter.error(redefinedTypeName.posOpt, TypeChecker.typeCheckerKind, s"${redefiningType.tpe} is not a subtype of $t2")
                                        None()
                                      } else {
                                        Some(SAST.Type.Named(
                                          name = redefinedTypeName,
                                          attr = SAST.TypedAttr(
                                            posOpt = redefinedTypeName.posOpt,
                                            typedOpt = Some(redefiningType.tpe))))
                                      }
                                    case _ =>
                                      halt("Infeasible: parent usages must have been typed by now")
                                  }
                                case _ =>
                                  reporter.error(redefinedTypeName.posOpt, TypeChecker.typeCheckerKind, st"Could not resolve type name '${(Util.ids2string(redefinedTypeName.ids), "::")}''".render)
                                  None()
                              }
                            case _ =>
                              reporter.error(redefinedTypeName.posOpt, TypeChecker.typeCheckerKind, st"Could not resolve redefined name ${(Util.ids2string(redefinedTypeName.ids), "::")}".render)
                              None()
                          }
                        case _ =>
                          reporter.error(info.posOpt, TypeChecker.typeCheckerKind, s"Currently expecting a redefinition to be followed by a single typing specialization")
                          None()
                      }
                    case _ =>
                      reporter.error(ru.attr.posOpt, TypeChecker.typeCheckerKind, "Expecting at most a redefinition to be followed by a single typing specialization")
                      None()
                  }
                }
                val resOpt: ResolvedInfo = item match {
                  case i:SysmlAst.AllocationUsage => ResolvedInfo.AllocationUsage(owner = context, name = id)
                  case i:SysmlAst.AttributeUsage => ResolvedInfo.AttributeUsage(owner = context, name = id)
                  case i:SysmlAst.ReferenceUsage => ResolvedInfo.ReferenceUsage(owner = context, name = id)
                  case i:SysmlAst.ConnectionUsage => ResolvedInfo.ConnectionUsage(owner = context, name = id)
                  case i:SysmlAst.ItemUsage => ResolvedInfo.ItemUsage(owner = context, name = id)
                  case i:SysmlAst.PartUsage => ResolvedInfo.PartUsage(owner = context, name = id)
                  case i:SysmlAst.PortUsage => ResolvedInfo.PortUsage(owner = context, name = id)
                  case _ =>
                    halt(s"Unexpected usage: $item")
                }

                ru = ru(
                  tipeOpt = tipeOpt,
                  attr = ru.attr(
                    resOpt = Some(resOpt),
                    typedOpt = if (tipeOpt.nonEmpty) tipeOpt.get.typedOpt else info.ast.commonUsageElements.attr.typedOpt))
              } else {
                ru = info.ast.commonUsageElements
              }

              info.resOpt
            case Some(_) =>
              reporter.error(posOpt, TypeChecker.typeCheckerKind,
                "Expecting usage redefinitions to be typed by an existing usage")
              None()
            case x =>
              reporter.error(posOpt, TypeChecker.typeCheckerKind, s"Couldn't resolve $id")
              None()
          }

          val expectedOpt: Option[SAST.Typed] = ru.tipeOpt match {
            case Some(tipe) =>
              tipe.typedOpt match {
                case tOpt@Some(_) => tOpt
                case _ =>
                  val newTipeOpt = typeHierarchy.typed(scope, tipe, reporter)
                  newTipeOpt match {
                    case Some(newTipe) =>
                      ru = ru(tipeOpt = newTipeOpt)
                      newTipe.typedOpt
                    case _ =>
                      None()
                  }
              }
            case _ => None()
          }

          val (fvalue, tOpt): (Option[SAST.SysmlAst.FeatureValue], Option[SAST.Typed]) = ru.featureValue match {
            case Some(fv) =>
              assert (fv.isBound |^ fv.isInitial)
              var tc = this // slang uses this to change the type checkers ModeContext
              if (fv.isBound) {
                // TODO
                //reporter.warn(fv.exp.posOpt, TypeChecker.typeCheckerKind,
                //  "This is a bound/constant feature value so need to ensure any children do not reassign to it")
              }
              val (exp, to) = tc.checkExp(expectedOpt, scope, fv.exp, reporter)
              (Some(fv(exp = exp)), to)
            case _ => (None(), expectedOpt)
          }

          tOpt match {
            case Some(_) =>
              val typedOpt: Option[SAST.Typed] = expectedOpt match {
                case Some(_) => expectedOpt
                case _ => tOpt
              }
              typedOpt match {
                case Some(SAST.Typed.Name(ids)) =>
                  typeHierarchy.typeMap.get(ids) match {
                    case Some(pd: TypeInfo.DefinitionTypeInfo) =>
                      // create a scope that contains the names from the port
                      // def (along with anything that inherits)
                      var attributeUsageScope = Scope.Local.create(scope)
                      attributeUsageScope = addMembersToScope(pd.members, attributeUsageScope)

                      val newBodyItems = checkBodyItems(
                        ISZ(attributeUsageScope), ru.definitionBodyItems, reporter)

                      ru = ru(
                        definitionBodyItems = newBodyItems,
                        attr = ru.attr(typedOpt = typedOpt, resOpt = resOpt))

                      return ru
                    case Some(pd: TypeInfo.EnumDefinition) =>

                      if (ru.definitionBodyItems.nonEmpty) {
                        reporter.error(ru.attr.posOpt, TypeChecker.typeCheckerKind,
                          "Not expecting an attribute typed by an enum definition to have body items")
                      }

                      ru = ru(attr = ru.attr(typedOpt = typedOpt, resOpt = resOpt))
                      return ru
                    case x =>
                      reporter.error(ru.attr.posOpt, TypeChecker.typeCheckerKind,
                        "Usages must be typed by definitions")
                  }
                case _ =>
                  reporter.error(ru.attr.posOpt, TypeChecker.typeCheckerKind,
                    s"Usages must be typed")
              }
            case _ =>
          }
          return u

        case x =>
          reporter.error(item.posOpt, TypeChecker.typeCheckerKind,
            "Usages must currently must have an identification or be a redefinition")
          return u
      }
    }

    item match {
      case item: SysmlAst.AllocationUsage =>
        item.commonUsageElements.attr.resOpt match {
          case Some(_: SAST.ResolvedInfo.AllocationUsage) => return item
          case _ =>
        }
        item.connectorPart match {
          case Some(b @ BinaryConnectorPart(src, dst)) =>
            return item(commonUsageElements = update(item.commonUsageElements))

          case _ =>
            reporter.error(item.posOpt, TypeChecker.typeCheckerKind, "Only binary connections are currently supported")
            return item(commonUsageElements = update(item.commonUsageElements))
        }

      case item: SysmlAst.AttributeUsage =>
        item.commonUsageElements.attr.resOpt match {
          case Some(_: SAST.ResolvedInfo.AttributeUsage) => return item
          case _ =>
        }
        return item(commonUsageElements = update(item.commonUsageElements))

      case item: SysmlAst.ConnectionUsage =>
        item.commonUsageElements.attr.resOpt match {
          case Some(_: SAST.ResolvedInfo.ConnectionUsage) => return item
          case _ =>
        }
        item.connectorPart match {
          case Some(b @ BinaryConnectorPart(src, dst)) =>
            return item(commonUsageElements = update(item.commonUsageElements))

          case _ =>
            reporter.error(item.posOpt, TypeChecker.typeCheckerKind, "Only binary connections are currently supported")
            return item(commonUsageElements = update(item.commonUsageElements))
        }

      case item: SysmlAst.ItemUsage =>
        item.commonUsageElements.attr.resOpt match {
          case Some(_: SAST.ResolvedInfo.ItemUsage) => return item
          case _ =>
        }
        return item(commonUsageElements = update(item.commonUsageElements))

      case item: SysmlAst.ReferenceUsage =>
        item.commonUsageElements.attr.resOpt match {
          case Some(_: SAST.ResolvedInfo.ReferenceUsage) => return item
          case _ =>
        }
        return item(commonUsageElements = update(item.commonUsageElements))

      case item: SysmlAst.PartUsage =>
        item.commonUsageElements.attr.resOpt match {
          case Some(_: SAST.ResolvedInfo.PartUsage) => return item
          case _ =>
        }
        return item(commonUsageElements = update(item.commonUsageElements))

      case item: SysmlAst.PortUsage =>
        item.commonUsageElements.attr.resOpt match {
          case Some(_: SAST.ResolvedInfo.PortUsage) => return item
          case _ =>
        }
        if (item.occurrenceUsagePrefix.refPrefix.direction.isEmpty) {
          reporter.error(item.posOpt, TypeChecker.typeCheckerKind,
            "Port direction must be supplied at the port usage level")
        }
        return item(commonUsageElements = update(item.commonUsageElements))

      case item @ SysmlAst.GumboAnnotation(sc: GclSubclause) =>
        var state = ISZ[GclStateVar]()
        for (s <- sc.state) {
          val classifier = ops.StringOps(ops.StringOps(s.classifier).replaceAllLiterally("::", "%")).split(c => c == '%')
          scope.resolveType(typeHierarchy.typeMap, classifier) match {
            case Some(t: TypeInfo.PartDefinition) => state = state :+ s (classifier = st"${(t.name, "::")}".render)
            case Some(e: TypeInfo.EnumDefinition) => state = state :+ s(classifier = st"${(e.name, "::")}".render)
            case _ =>
              reporter.error(s.posOpt, TypeChecker.typeCheckerKind, "Only expecting part or enumeration definition for state var type")
              state = state :+ s
          }
        }

        val methods: ISZ[GclMethod] = for (m <- sc.methods) yield resolveMethod(m, scope, typeHierarchy, reporter)

        return item (sc(state = state, methods = methods))

      case item @ SysmlAst.GumboAnnotation(l: GclLib) =>

        halt("Infeasible: gumbo libraries can only appear at the package level")

      case x =>
        reporter.error(item.posOpt, TypeChecker.typeCheckerKind,
          s"Need to handle body item $x")
        return x
    }
  }

  def checkInfoOpt(scopeOpt: Option[Scope],
                   infoOpt: Option[Info],
                   posOpt: Option[Position],
                   reporter: Reporter): (Option[SAST.Typed], Option[SAST.ResolvedInfo]) = {
    infoOpt match {
      case Some(info) => return checkInfo(scopeOpt, info, posOpt, reporter)
      case _ => return (None(), None())
    }
  }

  def checkInfo(scope: Option[Scope],
                info: Info,
                posOpt: Option[Position],
                reporter: Reporter): (Option[SAST.Typed], Option[SAST.ResolvedInfo]) = {
    info match {
      case info: Info.EnumDefinition => return (info.typedOpt, info.resOpt)
      case info: Info.AttributeUsage =>
        return (info.ast.commonUsageElements.attr.typedOpt, info.resOpt)
      case info: Info.Package => return (info.typedOpt, info.resOpt)
      case _ =>
        halt(s"TODO: $info")
    }
  }

  def checkId(scope: Scope, id: AST.Id, reporter: Reporter): (Option[SAST.Typed], Option[SAST.ResolvedInfo]) = {
    val nid = ISZ(id.value)
    val infoOpt = scope.resolveName(typeHierarchy.nameMap, nid)
    infoOpt match {
      case Some(info) => return checkInfo(Some(scope), info, id.attr.posOpt, reporter)
      case x =>
        println(st"${(typeHierarchy.nameMap.keys, "\n")}".render)
        halt(s"TODO $id $x")
    }
    reporter.error(id.attr.posOpt, TypeChecker.typeCheckerKind, s"Could not resolve '${id.value}'")
    return (None(), None())
  }


  def checkSelectH(scope: Scope.Local,
                   receiverTyped: SAST.Typed,
                   ident: AST.Id,
                   reporter: Reporter): (Option[SAST.Typed], Option[SAST.ResolvedInfo]) = {
    val id = ident.value

    @pure def noResult: (Option[SAST.Typed], Option[SAST.ResolvedInfo]) = {
      return (None(), None())
    }

    def errAccess(t: SAST.Typed): Unit = {
      reporter.error(ident.attr.posOpt, TypeChecker.typeCheckerKind, s"'$id' is not a member of type '$t'.")
    }

    receiverTyped match {
      case receiverType: SAST.Typed.Name =>
        halt(s"TODO $receiverType $id ${ident.attr.posOpt}")

      case receiverType: SAST.Typed.Enum =>
        typeHierarchy.nameMap.get(receiverType.name) match {
          case Some(info: Info.EnumDefinition) =>
            info.elements.get(id) match {
              case Some(res) =>
                return (info.elementTypedOpt, Some(res))
              case _ =>
                errAccess(receiverType)
                return noResult
            }
          case _ =>
            halt("Unexpected situation while type checking enum access.")
        }
      case receiverType: SAST.Typed.Package =>
        val r = checkInfoOpt(None(), typeHierarchy.nameMap.get(receiverType.name :+ id), ident.attr.posOpt, reporter)
        if (r._1.isEmpty) {
          val tt = typeHierarchy.typeMap.get(receiverType.name :+ id)
          reporter.error(
            ident.attr.posOpt, TypeChecker.typeCheckerKind,
            st"'$id' is not a member of package '${(receiverType.name, ".")}'.".render
          )
        }
        return (r._1, r._2)
      case _ =>
        halt(s"Infeasible: $receiverTyped")
    }
  }


  def checkExp(expectedOpt: Option[Typed], scope: Scope.Local, exp: AST.Exp, reporter: Reporter): (AST.Exp, Option[SAST.Typed]) = {

    def checkInvoke(invokeExp: Exp.Invoke): (AST.Exp, Option[SAST.Typed])  = {

      def checkInvokeH(tOpt: Option[SAST.Typed],
                       rOpt: Option[SAST.ResolvedInfo],
                       receiverOpt: Option[AST.Exp],
                       ident: Exp.Ident): (AST.Exp, Option[SAST.Typed]) = {
        if (tOpt.isEmpty) {
          println(
            st"""tOpt $tOpt
                |rOpt $rOpt
                |receiverOpt $receiverOpt
                |ident $ident""".render)
          halt("Unexpected")
        }

        return (invokeExp, tOpt)

      }

      invokeExp.receiverOpt match {
        case Some(receiver) =>
          val (newReceiver, receiverTypeOpt) = checkExp(expectedOpt = None(), scope = scope, exp = receiver, reporter = reporter)

          val receiverType: SAST.Typed = receiverTypeOpt match {
            case Some(t) => t
            case _ => return (invokeExp(receiverOpt = Some(newReceiver)), None())
          }
          val (typedOpt, resOpt) = checkSelectH(scope, receiverType, invokeExp.ident.id, reporter)

          val iexp = invokeExp.ident(
            attr = invokeExp.ident.attr(
              posOpt = invokeExp.ident.id.attr.posOpt,
              resOpt = Util.toSlangResolvedOpt(resOpt),
              typedOpt = Util.toSlangTypedOpt(typedOpt)))

          val r = checkInvokeH(typedOpt, resOpt, Some(newReceiver), iexp)
          return r

        case _ if UIF.isUif(invokeExp.ident.id.value) =>
          invokeExp.ident.id.value match {
            case UIF.RangeExpression =>
              invokeExp.args match {
                case ISZ(lhs, rhs) =>
                  checkExp(None(), scope, lhs, reporter) match {
                    case (lhsr, Some(lhst)) =>
                      checkExp(None(), scope, rhs, reporter) match {
                        case (rhsr, Some(rhst)) =>
                          if (lhst != rhst) {
                            reporter.error(invokeExp.posOpt, TypeChecker.typeCheckerKind,
                              s"Incompatible types for range construction '$lhst' .. '$rhst'.")
                            return (invokeExp, None())
                          }
                          return (invokeExp(args = ISZ(lhsr, rhsr)), Some(rhst))
                        case _ =>
                          reporter.error(lhs.posOpt, TypeChecker.typeCheckerKind, "Could not resolve type of right expression.")
                          return (invokeExp, None())
                      }
                    case _ =>
                      reporter.error(lhs.posOpt, TypeChecker.typeCheckerKind, "Could not resolve type of left expression.")
                      return (invokeExp, None())
                  }

                case _ => halt("Infeasible")
              }
            case x => halt(s"TODO: need to handle UIF $x")
          }

        case _ =>
          val (typedOpt, resOpt) = checkId(scope, invokeExp.ident.id, reporter)
          val iexp = invokeExp.ident(
            attr = invokeExp.ident.attr(
              posOpt = invokeExp.ident.id.attr.posOpt,
              resOpt = Util.toSlangResolvedOpt(resOpt),
              typedOpt = Util.toSlangTypedOpt(typedOpt)))
          val r = checkInvokeH(typedOpt, resOpt, None(), iexp)
          return r
      }
    }

    def checkIdent(identExp: AST.Exp.Ident): (AST.Exp, Option[SAST.Typed]) = {
      val (typedOpt, resOpt) = checkId(scope, identExp.id, reporter)
      val newIdentExp = identExp // TODO
      val newExp: AST.Exp.Ref = newIdentExp
      if (typedOpt.isEmpty) {
        return (newExp.asExp, typedOpt)
      }
      // TODO: convert to ref??
      return (newExp.asExp, typedOpt)
    }

    def checkSelect(selectExp: AST.Exp.Select): (AST.Exp, Option[SAST.Typed]) = {
      val tr: (AST.Exp.Select, Option[SAST.Typed]) = {
        selectExp.receiverOpt match {
          case Some(receiverType) =>
            val (newReceiver, receiverTypeOpt) = checkExp(None(), scope, receiverType, reporter)
            receiverTypeOpt match {
              case Some(receiverType) =>
                val t = checkSelectH(scope, receiverType, selectExp.id, reporter)
                (selectExp, t._1)
              case _ =>
                (selectExp, None())
            }
          case _ =>
            val (typedOpt, resOpt) = checkId(scope, selectExp.id, reporter)
            // TODO can't do this since we sysml has its own resolutions
            // (selectExp(targs = newTargs, attr = selectExp.attr(typedOpt = typedOpt, resOpt = resOpt)), typedOpt, typeArgs)
            (selectExp, typedOpt)
        }
      }

      val (newExp, typedOpt) = tr

      if (typedOpt.isEmpty) {
        return (newExp, typedOpt)
      }

      return (newExp, typedOpt)
    }

    def checkExpH(): (AST.Exp, Option[SAST.Typed]) = {
      exp match {
        case exp: AST.Exp.Select => return checkSelect(exp)
        case exp: AST.Exp.Ident => return checkIdent(exp)
        case exp: AST.Exp.Invoke => return checkInvoke(exp)
        case exp: AST.Exp.LitB => return (exp, Util.toSysmlTypedOpt(exp.typedOpt))
        case exp: AST.Exp.LitC => return (exp, Util.toSysmlTypedOpt(exp.typedOpt))
        case exp: AST.Exp.LitF32 => return (exp, Util.toSysmlTypedOpt(exp.typedOpt))
        case exp: AST.Exp.LitF64 => return (exp, Util.toSysmlTypedOpt(exp.typedOpt))
        case exp: AST.Exp.LitR => return (exp, Util.toSysmlTypedOpt(exp.typedOpt))
        case exp: AST.Exp.LitString => return (exp, Util.toSysmlTypedOpt(exp.typedOpt))
        case exp: AST.Exp.LitZ => return (exp, Util.toSysmlTypedOpt(exp.typedOpt))
        case _ =>
          //halt(s"TODO $exp")
          reporter.error(exp.posOpt, TypeChecker.typeCheckerKind, s"checkExpH $exp")
          return (exp, None())
      }
    }
    val r = checkExpH()
    r._2 match {
      case Some(t) =>
        expectedOpt match {
          case Some(expected) if expected == t =>
          case Some(expected) =>
            // it doesn't seem correct to do the second isSubtype, but it matches KerML's
            // typeConform
            // https://github.com/Systems-Modeling/SysML-v2-Pilot-Implementation/blob/abd742a79db32d6e4c7c7552e91053522f195db2/org.omg.kerml.xtext/src/org/omg/kerml/xtext/validation/KerMLValidator.xtend#L1123-L1125
            if (!typeHierarchy.isSubType(t, expected) &&
              !typeHierarchy.isSubType(expected, t)) {

              if (!typeHierarchy.mimicSysml(expected, t)) {
                reporter.error(exp.posOpt, TypeChecker.typeCheckerKind, st"Expecting type '${Util.printTyped(expected)}' but '${Util.printTyped(t)}' found.".render)
              }
            }
          case _ =>
        }
      case _ =>
    }
    return r
  }
}
