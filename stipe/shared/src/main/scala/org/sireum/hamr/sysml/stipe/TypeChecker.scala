// #Sireum
package org.sireum.hamr.sysml.stipe

import org.sireum._
import org.sireum.hamr.sysml.ast.{ResolvedInfo, SysmlAst, Typed}
import org.sireum.hamr.sysml.stipe.TypeChecker.typeCheckerKind
import org.sireum.hamr.sysml.{ast => SAST}
import org.sireum.hamr.sysml.symbol.Resolver.QName
import org.sireum.hamr.sysml.symbol.{Info, Scope, TypeInfo, Util}
import org.sireum.lang.{ast => AST}
import org.sireum.message.{Message, Position, Reporter}

object TypeChecker {

  val typeCheckerKind: String = "Type Checker"

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
      (f: () => (TypeHierarchy => (TypeHierarchy, ISZ[Message]) @pure) @pure) => f(), TypeHierarchy.combine _, init, par)
    var r = p._1

    reporter.reports(p._2)
    return r
  }
}

@datatype class TypeChecker(val typeHierarchy: TypeHierarchy,
                            val context: QName) {
  def checkAllocationDefinition(info: TypeInfo.AllocationDefinition): TypeHierarchy => (TypeHierarchy, ISZ[Message]) @pure = {
    assert(info.outlined, st"${(info.name, "::")} is not outline".render)
    val reporter = Reporter.create

    var scope = Scope.Local.create(info.scope)
    scope = addMembersToScope(info.members, scope)

    val newBodyItems = checkBodyItems(ISZ(scope), info.ast.bodyItems, reporter)

    val messages = reporter.messages

    val newMembers = updateMembers(newBodyItems, info.members, reporter)

    val newInfo = info(
      typeChecked = T,
      ast = info.ast(bodyItems = newBodyItems),
      members = newMembers
    )
    return (th: TypeHierarchy) => (th(typeMap = th.typeMap + info.name ~> newInfo), messages)
  }

  def checkAttributeDefinition(info: TypeInfo.AttributeDefinition): TypeHierarchy => (TypeHierarchy, ISZ[Message]) @pure = {
    assert(info.outlined, st"${(info.name, "::")} is not outline".render)
    val reporter = Reporter.create

    var scope = Scope.Local.create(info.scope)
    scope = addMembersToScope(info.members, scope)

    val newBodyItems = checkBodyItems(ISZ(scope), info.ast.bodyItems, reporter)

    val messages = reporter.messages

    val newMembers = updateMembers(newBodyItems, info.members, reporter)

    val newInfo = info(
      typeChecked = T,
      ast = info.ast(bodyItems = newBodyItems),
      members = newMembers
    )
    return (th: TypeHierarchy) => (th(typeMap = th.typeMap + info.name ~> newInfo), messages)
  }

  def checkConnectionDefinition(info: TypeInfo.ConnectionDefinition): TypeHierarchy => (TypeHierarchy, ISZ[Message]) @pure = {
    assert(info.outlined, st"${(info.name, "::")} is not outline".render)
    val reporter = Reporter.create

    var scope = Scope.Local.create(info.scope)
    scope = addMembersToScope(info.members, scope)

    val newBodyItems = checkBodyItems(ISZ(scope), info.ast.bodyItems, reporter)

    val messages = reporter.messages

    val newMembers = updateMembers(newBodyItems, info.members, reporter)

    val newInfo = info(
      typeChecked = T,
      ast = info.ast(bodyItems = newBodyItems),
      members = newMembers
    )
    return (th: TypeHierarchy) => (th(typeMap = th.typeMap + info.name ~> newInfo), messages)
  }

  def checkPartDefinition(info: TypeInfo.PartDefinition): TypeHierarchy => (TypeHierarchy, ISZ[Message]) @pure = {
    assert(info.outlined, st"${(info.name, "::")} is not outline".render)
    val reporter = Reporter.create

    var scope = Scope.Local.create(info.scope)
    scope = addMembersToScope(info.members, scope)

    val newBodyItems = checkBodyItems(ISZ(scope), info.ast.bodyItems, reporter)

    val messages = reporter.messages

    val newMembers = updateMembers(newBodyItems, info.members, reporter)

    val newInfo = info(
      typeChecked = T,
      ast = info.ast(bodyItems = newBodyItems),
      members = newMembers
    )
    return (th: TypeHierarchy) => (th(typeMap = th.typeMap + info.name ~> newInfo), messages)
  }

  def checkPortDefinition(info: TypeInfo.PortDefinition): TypeHierarchy => (TypeHierarchy, ISZ[Message]) @pure = {
    assert(info.outlined, st"${(info.name, "::")} is not outline".render)
    val reporter = Reporter.create

    var scope = Scope.Local.create(info.scope)
    scope = addMembersToScope(info.members, scope)

    val newBodyItems = checkBodyItems(scopes = ISZ(scope), bodyItems = info.ast.bodyItems, reporter = reporter)

    val messages = reporter.messages

    val newMembers = updateMembers(newBodyItems, info.members, reporter)

    val newInfo = info(
      typeChecked = T,
      ast = info.ast(bodyItems = newBodyItems),
      members = newMembers
    )
    return (th: TypeHierarchy) => (th(typeMap = th.typeMap + info.name ~> newInfo), messages)
  }

  def addMembersToScope(members: TypeInfo.Members, scope: Scope.Local): Scope.Local = {
    var nameMap = HashMap.empty[String, Info]

    def add(map: HashSMap[String, Info]): Unit = {
      for (e <- map.entries) {
        nameMap = nameMap + e
      }
    }

    add(members.attributeUsages.asInstanceOf[HashSMap[String, Info]])
    add(members.connectionUsages.asInstanceOf[HashSMap[String, Info]])
    add(members.itemUsages.asInstanceOf[HashSMap[String, Info]])
    add(members.partUsages.asInstanceOf[HashSMap[String, Info]])
    add(members.portUsages.asInstanceOf[HashSMap[String, Info]])
    add(members.referenceUsages.asInstanceOf[HashSMap[String, Info]])

    return scope(nameMap = scope.nameMap ++ nameMap.entries)
  }

  def updateMembers(newBodyItems: ISZ[SAST.SysmlAst.BodyElement], origMembers: TypeInfo.Members, reporter: Reporter): TypeInfo.Members = {

    var attributeUsages: HashSMap[String, Info.AttributeUsage] = origMembers.attributeUsages
    var connectionUsages: HashSMap[String, Info.ConnectionUsage] = origMembers.connectionUsages
    var itemUsages: HashSMap[String, Info.ItemUsage] = origMembers.itemUsages
    var partUsages: HashSMap[String, Info.PartUsage] = origMembers.partUsages
    var portUsages: HashSMap[String, Info.PortUsage] = origMembers.portUsages
    var referenceUsages: HashSMap[String, Info.ReferenceUsage] = origMembers.referenceUsages

    for (bi <- newBodyItems) {
      bi match {
        case nast: SAST.SysmlAst.AttributeUsage if nast.identification.nonEmpty =>
          val (id, posOpt) = Util.getId(nast.identification, nast.specializations, nast.posOpt, TypeChecker.typeCheckerKind, reporter)
          val bInfo = origMembers.attributeUsages.get(id.get).get
          attributeUsages = attributeUsages + id.get ~> bInfo(ast = nast)

        case nast: SAST.SysmlAst.ConnectionUsage if nast.identification.nonEmpty =>
          val (id, posOpt) = Util.getId(nast.identification, nast.specializations, nast.posOpt, TypeChecker.typeCheckerKind, reporter)
          val bInfo = origMembers.connectionUsages.get(id.get).get
          connectionUsages = connectionUsages + id.get ~> bInfo(ast = nast)

        case nast: SAST.SysmlAst.ItemUsage if nast.identification.nonEmpty =>
          val (id, posOpt) = Util.getId(nast.identification, nast.specializations, nast.posOpt, TypeChecker.typeCheckerKind, reporter)
          val bInfo = origMembers.itemUsages.get(id.get).get
          itemUsages = itemUsages + id.get ~> bInfo(ast = nast)

        case nast: SAST.SysmlAst.PartUsage if nast.identification.nonEmpty =>
          val (id, posOpt) = Util.getId(nast.identification, nast.specializations, nast.posOpt, TypeChecker.typeCheckerKind, reporter)
          val bInfo = origMembers.partUsages.get(id.get).get
          partUsages = partUsages + id.get ~> bInfo(ast = nast)

        case nast: SAST.SysmlAst.PortUsage if nast.identification.nonEmpty =>
          val (id, posOpt) = Util.getId(nast.identification, nast.specializations, nast.posOpt, TypeChecker.typeCheckerKind, reporter)
          val bInfo = origMembers.portUsages.get(id.get).get
          portUsages = portUsages + id.get ~> bInfo(ast = nast)

        case nast: SAST.SysmlAst.ReferenceUsage if nast.identification.nonEmpty =>
          val (id, posOpt) = Util.getId(nast.identification, nast.specializations, nast.posOpt, TypeChecker.typeCheckerKind, reporter)
          val bInfo = origMembers.referenceUsages.get(id.get).get
          referenceUsages = referenceUsages + id.get ~> bInfo(ast = nast)

        case _ =>
      }
    }
    return TypeInfo.Members(
      attributeUsages = attributeUsages,
      connectionUsages = connectionUsages,
      itemUsages = itemUsages,
      partUsages = partUsages,
      portUsages = portUsages,
      referenceUsages = referenceUsages)
  }

  def checkBodyItems(scopes: ISZ[Scope.Local], bodyItems: ISZ[SAST.SysmlAst.BodyElement], reporter: Reporter): ISZ[SAST.SysmlAst.BodyElement] = {
    var newBodyItems = ISZ[SAST.SysmlAst.BodyElement]()

    def checkBodyItemH(i: Z): SAST.SysmlAst.BodyElement = {
      val scope: Scope.Local = if (scopes.size == 1) scopes(0) else scopes(i)
      return checkBodyItem(scope, bodyItems(i), reporter)
    }

    val size = bodyItems.size - 1
    for (i <- 0 until size if !reporter.hasError) {
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

  def checkBodyItem(scope: Scope.Local, item: SysmlAst.BodyElement, reporter: Reporter): SysmlAst.BodyElement = {

    item match {
      case item: SysmlAst.AttributeUsage => {
        item.attr.resOpt match {
          case Some(_: SAST.ResolvedInfo.PortUsage) => return item
          case _ =>
        }
        Util.getId(item.identification, item.specializations, item.posOpt, TypeChecker.typeCheckerKind, reporter) match {
          case (Some(id), posOpt) =>
            var r = item
            val resOpt: Option[SAST.ResolvedInfo] = scope.resolveName(typeHierarchy.nameMap, ISZ(id)) match {
              case Some(info: Info.AttributeUsage) =>
                if (r.identification.isEmpty) {
                  assert (r.specializations.nonEmpty, "expecting a redefinition")
                  assert (r.attr.resOpt.isEmpty, "how did this get resolved")
                  // usage has the type of the attribute usage it's redefining
                  r = r(
                    tipeOpt = info.ast.tipeOpt,
                    attr = r.attr(
                      resOpt = Some(ResolvedInfo.AttributeUsage(owner = context, name = id)),
                      typedOpt = info.ast.attr.typedOpt))
                } else {
                  r = info.ast
                }

                info.resOpt
              case Some(_) =>
                reporter.error(posOpt, TypeChecker.typeCheckerKind,
                "Expecting attribute usage redefinitions to be typed by an attribute usage")
                None()
              case x =>
                reporter.error(posOpt, TypeChecker.typeCheckerKind, s"Couldn't resolve $id")
                None()
            }

            val expectedOpt: Option[SAST.Typed] = r.tipeOpt match {
              case Some(tipe) =>
                tipe.typedOpt match {
                  case tOpt@Some(_) => tOpt
                  case _ =>
                    val newTipeOpt = typeHierarchy.typed(scope, tipe, reporter)
                    newTipeOpt match {
                      case Some(newTipe) =>
                        r = r(tipeOpt = newTipeOpt)
                        newTipe.typedOpt
                      case _ =>
                        None()
                    }
                }
              case _ => None()
            }

            val (fvalue, tOpt): (Option[SAST.SysmlAst.FeatureValue], Option[SAST.Typed]) = r.featureValue match {
              case Some(fv) =>
                assert (fv.isBound |^ fv.isInitial)
                var tc = this // slang uses this to change the type checkers ModeContext
                if (fv.isBound) {
                  reporter.warn(fv.exp.posOpt, TypeChecker.typeCheckerKind,
                    "This is a bound/constant feature value so need to ensure any children do not reassign to it")
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
                      case Some(pd: TypeInfo.AttributeDefinition) =>
                        // create a scope that contains the names from the port
                        // def (along with anything that inherits)
                        var attributeUsageScope = Scope.Local.create(scope)
                        attributeUsageScope = addMembersToScope(pd.members, attributeUsageScope)

                        val newAttributeUsageBodyItems = checkBodyItems(
                          ISZ(attributeUsageScope), r.definitionBodyItems, reporter)

                        r = r(
                          definitionBodyItems = newAttributeUsageBodyItems,
                          attr = r.attr(typedOpt = typedOpt, resOpt = resOpt))

                        return r
                      case Some(pd: TypeInfo.EnumDefinition) =>

                        if (r.definitionBodyItems.nonEmpty) {
                          reporter.error(r.posOpt, TypeChecker.typeCheckerKind,
                          "Not expecting an attribute typed by an enum definition to have body items")
                        }

                        r = r(attr = r.attr(typedOpt = typedOpt, resOpt = resOpt))
                        return r
                      case x =>
                        reporter.error(r.posOpt, TypeChecker.typeCheckerKind,
                          "Attribute usages must be typed by attribute definitions")
                    }
                  case _ =>
                    reporter.error(r.posOpt, TypeChecker.typeCheckerKind,
                      s"Attribute usages must be typed")
                }
              case _ =>
            }
            return item

          case _ =>
            reporter.error(item.posOpt, TypeChecker.typeCheckerKind,
              "Attribute usages currently must have an identification")
        }
        return item
      }

      case item: SysmlAst.ReferenceUsage => {
        item.attr.resOpt match {
          case Some(_: SAST.ResolvedInfo.ReferenceUsage) => return item
          case _ =>
        }
        Util.getId(item.identification, item.specializations, item.posOpt, TypeChecker.typeCheckerKind, reporter) match {
          case (Some(id), posOpt) =>
            var r = item
            val resOpt: Option[SAST.ResolvedInfo] = scope.resolveName(typeHierarchy.nameMap, ISZ(id)) match {
              case Some(info: Info.ReferenceUsage) =>
                r = info.ast
                info.resOpt
              case x =>
                halt(s"Couldn't resolve $x")
            }

            val expectedOpt: Option[SAST.Typed] = r.tipeOpt match {
              case Some(tipe) =>
                tipe.typedOpt match {
                  case tOpt@Some(_) => tOpt
                  case _ =>
                    val newTipeOpt = typeHierarchy.typed(scope, tipe, reporter)
                    newTipeOpt match {
                      case Some(newTipe) =>
                        r = r(tipeOpt = newTipeOpt)
                        newTipe.typedOpt
                      case _ =>
                        None()
                    }
                }
              case _ => None()
            }

            val (fvalue, tOpt): (Option[SAST.SysmlAst.FeatureValue], Option[SAST.Typed]) = r.featureValue match {
              case Some(fv) =>
                reporter.error(r.posOpt, TypeChecker.typeCheckerKind, "Not expecting reference usages to have feature values")
                (Some(fv), expectedOpt)
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

                      case x =>
                        if (r.definitionBodyItems.nonEmpty) {
                          reporter.error(r.posOpt, TypeChecker.typeCheckerKind,
                            s"Need to resolve reference usages with bodies")
                        }
                    }
                  case _ =>
                    reporter.error(r.posOpt, TypeChecker.typeCheckerKind,
                      s"Reference usages must be typed")
                }
              case _ =>
            }

            return item
          case _ =>
            reporter.error(item.posOpt, TypeChecker.typeCheckerKind,
              "Reference usages currently must have an identification")
        }
        return item
      }

      case item: SysmlAst.PortUsage => {
        item.attr.resOpt match {
          case Some(_: SAST.ResolvedInfo.PortUsage) => return item
          case _ =>
        }
        Util.getId(item.identification, item.specializations, item.posOpt, TypeChecker.typeCheckerKind, reporter) match {
          case (Some(id), posOpt) =>
            var r = item
            val resOpt: Option[SAST.ResolvedInfo] = scope.resolveName(typeHierarchy.nameMap, ISZ(id)) match {
              case Some(info: Info.PortUsage) =>
                r = info.ast
                info.resOpt
              case x =>
                halt(s"Couldn't resolve $x")
            }

            val expectedOpt: Option[SAST.Typed] = r.tipeOpt match {
              case Some(tipe) =>
                tipe.typedOpt match {
                  case tOpt@Some(_) => tOpt
                  case _ =>
                    val newTipeOpt = typeHierarchy.typed(scope, tipe, reporter)
                    newTipeOpt match {
                      case Some(newTipe) =>
                        r = r(tipeOpt = newTipeOpt)
                        newTipe.typedOpt
                      case _ =>
                        None()
                    }
                }
              case _ => None()
            }

            val (fvalue, tOpt): (Option[SAST.SysmlAst.FeatureValue], Option[SAST.Typed]) = r.featureValue match {
              case Some(fv) =>
                reporter.error(r.posOpt, TypeChecker.typeCheckerKind, "Not expecting port usages to have feature values")
                (Some(fv), expectedOpt)
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
                      case Some(pd: TypeInfo.PortDefinition) =>
                        // create a scope that contains the names from the port
                        // def (along with anything that inherits)
                        var partUsageScope = Scope.Local.create(scope)
                        partUsageScope = addMembersToScope(pd.members, partUsageScope)

                        val newPartUsageBodyItems = checkBodyItems(
                          ISZ(partUsageScope), r.definitionBodyItems, reporter)

                        r = r(
                          definitionBodyItems = newPartUsageBodyItems,
                          attr = r.attr(typedOpt = typedOpt, resOpt = resOpt))

                        return r
                      case _ =>
                        reporter.error(r.posOpt, TypeChecker.typeCheckerKind,
                          "Part usages must be typed by port definitions")
                    }
                  case _ =>
                    reporter.error(r.posOpt, TypeChecker.typeCheckerKind,
                      s"Port usages must be typed")
                }
              case _ =>
            }
            return item

          case _ =>
            reporter.error(item.posOpt, TypeChecker.typeCheckerKind,
              "Port usages currently must have an identification")
        }
        return item
      }
      case x =>
        reporter.warn(item.posOpt, TypeChecker.typeCheckerKind,
          s"Need to handle body item $x")
        return x
    }
  }

  def checkInfo(value: Some[Scope], usage: Info.AttributeUsage, value1: Option[Position], reporter: Reporter): (Option[SAST.Typed], Option[SAST.ResolvedInfo]) = {
    halt("todo")
  }

  def checkId(scope: Scope, id: AST.Id, reporter: Reporter): (Option[SAST.Typed], Option[SAST.ResolvedInfo]) = {
    val nid = ISZ(id.value)
    val infoOpt = scope.resolveName(typeHierarchy.nameMap, nid)
    infoOpt match {
      case Some(info: Info.AttributeUsage) => return checkInfo(Some(scope), info, id.attr.posOpt, reporter)
      case x =>
        halt(s"TODO $x")
    }
    reporter.error(id.attr.posOpt, TypeChecker.typeCheckerKind, s"Could not resolve '${id.value}'")
    return (None(), None())
  }

  def checkSelectH(local: Scope.Local, typed: Typed, id: AST.Id, reporter: Reporter): (Option[SAST.Typed], Option[SAST.ResolvedInfo]) = {
    halt("")
  }
  def checkExp(expectedOpt: Option[Typed], scope: Scope.Local, exp: AST.Exp, reporter: Reporter): (AST.Exp, Option[SAST.Typed]) = {

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
        case _ =>
          halt(s"TODO $exp")
      }
    }
    val r = checkExpH()
    r._2 match {
      case Some(t) =>
        expectedOpt match {
          case Some(expected) if expected == t =>
          case Some(expected) =>
            if (!typeHierarchy.isSubType(t, expected)) {
              reporter.error(exp.posOpt, TypeChecker.typeCheckerKind, s"Expecting type '$expected' but '$t' found.")
            }
          case _ =>
        }
      case _ =>
    }
    return r
  }
}
