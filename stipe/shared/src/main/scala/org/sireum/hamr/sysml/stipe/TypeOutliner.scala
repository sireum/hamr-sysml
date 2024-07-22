// #Sireum
package org.sireum.hamr.sysml.stipe

import org.sireum._
import org.sireum.hamr.sysml.ast.SysmlAst.{TypingsSpecialization, Visibility}
import org.sireum.hamr.sysml.{ast => SAST}
import org.sireum.hamr.sysml.ast.{Type, TypedAttr}
import org.sireum.hamr.sysml.symbol.Resolver.{QName, TypeMap, addBuiltIns}
import org.sireum.hamr.sysml.symbol.{Info, Scope, TypeInfo, Util}
import org.sireum.message.{Message, Position, Reporter}

object TypeOutliner {

  def checkOutline(par: Z, typeHierarchy: TypeHierarchy, reporter: Reporter): TypeHierarchy = {
    def parentsOutlined(name: QName, typeMap: TypeMap): B = {
      def isOutlined(ids: QName): B = {
        typeMap.get(ids).get match {
          case ti: TypeInfo.AllocationDefinition => return ti.outlined
          case ti: TypeInfo.AttributeDefinition => return ti.outlined
          case ti: TypeInfo.ConnectionDefinition => return ti.outlined
          case ti: TypeInfo.PartDefinition => return ti.outlined
          case ti: TypeInfo.PortDefinition => return ti.outlined
          case _ => return T
        }
      }
      var r = T
      for (p <- typeHierarchy.poset.parentsOf(name).elements if r) {
        typeMap.get(p).get match {
          // TODO: only other slang case is TypeAlias
          case parentTi =>
            r = r & isOutlined(parentTi.name)
        }
      }
      return r
    }

    var th = typeHierarchy

    def outlineDefinitions(): Unit = {
      var workList = typeHierarchy.poset.rootNodes

      var jobs = ISZ[() => (TypeHierarchy => (TypeHierarchy, ISZ[Message])@pure)@pure]()

      def addJob(name: QName, acc: ISZ[QName]): ISZ[QName] = {
        var r = acc
        val ti = th.typeMap.get(name).get
        var ok: B = F
        val to = TypeOutliner(th)
        ti match {
          case ti: TypeInfo.AllocationDefinition =>
            if (!ti.outlined) {
              val po = parentsOutlined(ti.name, th.typeMap)
              if (po) {
                jobs = jobs :+ (() => to.outlineAllocationDefinition(ti))
                ok = T
              }
            } else {
              ok = T
            }
          case ti: TypeInfo.AttributeDefinition =>
            if (!ti.outlined) {
              val po = parentsOutlined(ti.name, th.typeMap)
              if (po) {
                jobs = jobs :+ (() => to.outlineAttributeDefinition(ti))
                ok = T
              }
            } else {
              ok = T
            }
          case ti: TypeInfo.ConnectionDefinition =>
            if (!ti.outlined) {
              val po = parentsOutlined(ti.name, th.typeMap)
              if (po) {
                jobs = jobs :+ (() => to.outlineConnectionDefinition(ti))
                ok = T
              }
            } else {
              ok = T
            }
          case ti: TypeInfo.PartDefinition =>
            if (!ti.outlined) {
              val po = parentsOutlined(ti.name, th.typeMap)
              if (po) {
                jobs = jobs :+ (() => to.outlinePartDefinition(ti))
                ok = T
              }
            } else {
              ok = T
            }
          case ti: TypeInfo.PortDefinition =>
            if (!ti.outlined) {
              val po = parentsOutlined(ti.name, th.typeMap)
              if (po) {
                jobs = jobs :+ (() => to.outlinePortDefinition(ti))
                ok = T
              }
            } else {
              ok = T
            }
          case _ =>
        }
        if (ok) {
          val children = typeHierarchy.poset.childrenOf(name).elements
          for(n <- children) {
            r = r :+ n
          }
        }
        return r
      }

      while (workList.nonEmpty && !reporter.hasError) {
        var l = ISZ[QName]()
        for (name <- workList) {
          l = addJob(name, l)
        }
        val init = (th, ISZ[Message]())
        val r = ops.ISZOps(jobs).parMapFoldLeftCores(
          (f: () => (TypeHierarchy => (TypeHierarchy, ISZ[Message])@pure)@pure) => f(),
          TypeHierarchy.combine _,
          init,
          par)
        reporter.reports(r._2)
        th = r._1
        workList = l
        jobs = ISZ()
      }
    }

    outlineDefinitions()

    return th
  }
}

@datatype class TypeOutliner(val typeHierarchy: TypeHierarchy) {

  @pure def outlineAllocationDefinition(info: TypeInfo.AllocationDefinition): TypeHierarchy => (TypeHierarchy, ISZ[Message])@pure = {
    val reporter = Reporter.create
    val scope = Scope.Local.create(info.scope)
    val members = outlineMembers(info.members, scope, reporter)
    val (
      newMembers,
      ancestors, newParents) =
      outlineInheritedMembers(info.owner, info.id, info.name, info.ast.parents, scope, members, reporter)

    val newInfo: TypeInfo.AllocationDefinition =
      info(
        outlined = T,
        ancestors = ancestors,
        members = newMembers,
        ast = info.ast(parents = newParents)
      )

    val messages = reporter.messages
    return (th: TypeHierarchy) => (th(typeMap = th.typeMap + info.name ~> newInfo), messages)
  }

  @pure def outlineAttributeDefinition(info: TypeInfo.AttributeDefinition): TypeHierarchy => (TypeHierarchy, ISZ[Message])@pure = {
    val reporter = Reporter.create
    val scope = Scope.Local.create(info.scope)

    val members = outlineMembers(info.members, scope, reporter)
    val (
      newMembers,
      ancestors, newParents) =
      outlineInheritedMembers(info.owner, info.id, info.name, info.ast.parents, scope, members, reporter)

    val newInfo: TypeInfo.AttributeDefinition =
      info(
        outlined = T,
        ancestors = ancestors,
        members = newMembers,
        ast = info.ast(parents = newParents)
      )

    val messages = reporter.messages
    return (th: TypeHierarchy) => (th(typeMap = th.typeMap + info.name ~> newInfo), messages)
  }

  @pure def outlineConnectionDefinition(info: TypeInfo.ConnectionDefinition): TypeHierarchy => (TypeHierarchy, ISZ[Message])@pure = {
    val reporter = Reporter.create
    val scope = Scope.Local.create(info.scope)
    val members = outlineMembers(info.members, scope, reporter)
    val (
      newMembers,
      ancestors, newParents) =
      outlineInheritedMembers(info.owner, info.id, info.name, info.ast.parents, scope, members, reporter)

    val newInfo: TypeInfo.ConnectionDefinition =
      info(
        outlined = T,
        ancestors = ancestors,
        members = newMembers,
        ast = info.ast(parents = newParents)
      )

    val messages = reporter.messages
    return (th: TypeHierarchy) => (th(typeMap = th.typeMap + info.name ~> newInfo), messages)

  }

  @pure def outlinePartDefinition(info: TypeInfo.PartDefinition): TypeHierarchy => (TypeHierarchy, ISZ[Message])@pure = {
    val reporter = Reporter.create
    val scope = Scope.Local.create(info.scope)

    val members = outlineMembers(info.members, scope, reporter)

    val (
      newMembers,
      ancestors, newParents) =
      outlineInheritedMembers(info.owner, info.id, info.name, info.ast.parents, scope, members, reporter)

    val newInfo: TypeInfo.PartDefinition =
      info(
        outlined = T,
        ancestors = ancestors,
        members = newMembers,
        ast = info.ast(parents = newParents)
      )

    val messages = reporter.messages
    return (th: TypeHierarchy) => (th(typeMap = th.typeMap + info.name ~> newInfo), messages)
  }

  @pure def outlinePortDefinition(info: TypeInfo.PortDefinition): TypeHierarchy => (TypeHierarchy, ISZ[Message])@pure = {
    val reporter = Reporter.create
    val scope = Scope.Local.create(info.scope)
    val members = outlineMembers(info.members, scope, reporter)

    val (
      newMembers,
      ancestors, newParents) =
      outlineInheritedMembers(info.owner, info.id, info.name, info.ast.parents, scope, members, reporter)

    val newInfo: TypeInfo.PortDefinition =
      info(
        outlined = T,
        ancestors = ancestors,
        members = newMembers,
        ast = info.ast(parents = newParents)
      )

    val messages = reporter.messages
    return (th: TypeHierarchy) => (th(typeMap = th.typeMap + info.name ~> newInfo), messages)
  }

  def outlineInheritedMembers(nameOwner: ISZ[String],
                              nameId: String,
                              name: QName,
                              parents: ISZ[Type.Named],
                              scope: Scope.Local,
                              info: TypeInfo.Members,
                              reporter: Reporter): (TypeInfo.Members, ISZ[SAST.Typed.Name], ISZ[SAST.Type.Named]) = {
    var attributeUsages = info.attributeUsages
    var connectionUsages = info.connectionUsages
    var itemUsages = info.itemUsages
    var partUsages = info.partUsages
    var portUsages = info.portUsages
    var referenceUsages = info.referenceUsages

    def checkInherit(id: String, owner: ISZ[String], posOpt: Option[Position]): B = {
      def check(map: HashSMap[String, Info.UsageInfo]): B = {
        map.get(id) match {
          case Some(otherInfo) =>
            if (name != owner) {
              reporter.error(posOpt, TypeChecker.typeCheckerKind,
                st"Cannot inherit $id becuase i hs been previously inherited from ${(otherInfo.owner, "::")}.".render)
            } else {
              reporter.error(posOpt, TypeChecker.typeCheckerKind,
                s"Cannot inherit $id because it has been previously declared")
            }
            return F
          case _ =>
            return T
        }
      }
      var ok: B = T
      ok = ok & check(attributeUsages.asInstanceOf[HashSMap[String, Info.UsageInfo]])
      ok = ok & check(connectionUsages.asInstanceOf[HashSMap[String, Info.UsageInfo]])
      ok = ok & check(itemUsages.asInstanceOf[HashSMap[String, Info.UsageInfo]])
      ok = ok & check(partUsages.asInstanceOf[HashSMap[String, Info.UsageInfo]])
      ok = ok & check(portUsages.asInstanceOf[HashSMap[String, Info.UsageInfo]])
      return ok
    }

    def inheritAttributeUsages(attributeUsage: Info.AttributeUsage, posOpt: Option[Position]): Unit = {
      val owner = attributeUsage.owner
      val id = attributeUsage.id
      if (attributeUsage.ast.visibility != Visibility.Public) {
        reporter.error(attributeUsage.ast.posOpt, TypeChecker.typeCheckerKind,
          "Currently only supporting public visibilities")
      }
      if (checkInherit(id, owner, posOpt)) {
        attributeUsages = attributeUsages + id ~> attributeUsage
      }
    }
    def inheritConnectionUsages(connectionUsage: Info.ConnectionUsage, posOpt: Option[Position]): Unit = {
      val owner = connectionUsage.owner
      val id = connectionUsage.id
      if (connectionUsage.ast.visibility != Visibility.Public) {
        reporter.error(connectionUsage.ast.posOpt, TypeChecker.typeCheckerKind,
          "Currently only supporting public visibilities")
      }
      if (checkInherit(id, owner, posOpt)) {
        connectionUsages = connectionUsages + id ~> connectionUsage
      }
    }
    def inheritItemUsages(itemUsage: Info.ItemUsage, posOpt: Option[Position]): Unit = {
      val owner = itemUsage.owner
      val id = itemUsage.id
      if (itemUsage.ast.visibility != Visibility.Public) {
        reporter.error(itemUsage.ast.posOpt, TypeChecker.typeCheckerKind,
          "Currently only supporting public visibilities")
      }
      if (checkInherit(id, owner, posOpt)) {
        itemUsages = itemUsages + id ~> itemUsage
      }
    }
    def inheritPartUsages(partUsage: Info.PartUsage, posOpt: Option[Position]): Unit = {
      val owner = partUsage.owner
      val id = partUsage.id
      if (partUsage.ast.visibility != Visibility.Public) {
        reporter.error(partUsage.ast.posOpt, TypeChecker.typeCheckerKind,
          "Currently only supporting public visibilities")
      }
      if (checkInherit(id, owner, posOpt)) {
        partUsages = partUsages + id ~> partUsage
      }
    }
    def inheritPortUsages(portUsage: Info.PortUsage, posOpt: Option[Position]): Unit = {
      val owner = portUsage.owner
      val id = portUsage.id
      if (portUsage.ast.visibility != Visibility.Public) {
        reporter.error(portUsage.ast.posOpt, TypeChecker.typeCheckerKind,
          "Currently only supporting public visibilities")
      }
      if (checkInherit(id, owner, posOpt)) {
        portUsages = portUsages + id ~> portUsage
      }
    }
    def inheritReferenceUsages(referenceUsage: Info.ReferenceUsage, posOpt: Option[Position]): Unit = {
      val owner = referenceUsage.owner
      val id = referenceUsage.id
      if (referenceUsage.ast.visibility != Visibility.Public) {
        reporter.error(referenceUsage.ast.posOpt, TypeChecker.typeCheckerKind,
          "Currently only supporting public visibilities")
      }
      if (checkInherit(id, owner, posOpt)) {
        referenceUsages = referenceUsages + id ~> referenceUsage
      }
    }

    def getTypedName(a: SAST.Typed): SAST.Typed.Name = {
      a match {
        case i: SAST.Typed.Name => return i
        case _ => halt(s"No Typed info for: ${a}")
      }
    }

    var ancestors = HashSSet.empty[SAST.Typed.Name]
    var newParents = ISZ[SAST.Type.Named]()
    for (parent <- parents) {
      val tipeOpt = typeHierarchy.typed(scope, parent, reporter)
      tipeOpt match {
        case Some(parentNamed: SAST.Type.Named) =>
          newParents = newParents :+ parentNamed
          parentNamed.typedOpt match {
            case Some(parentTyped: SAST.Typed.Name) =>
              typeHierarchy.typeMap.get(parentTyped.ids) match {

                case Some(parentAllocDef: TypeInfo.AllocationDefinition) =>
                  ancestors = ancestors + getTypedName(parentAllocDef.tpe)
                  val posOpt = parent.posOpt
                  for (parentAllocDefAncestor <- parentAllocDef.ancestors) {
                    ancestors = ancestors + parentAllocDefAncestor
                  }

                  for (parentAttributeUsage <- parentAllocDef.members.attributeUsages.values) {
                    inheritAttributeUsages(parentAttributeUsage, posOpt)
                  }
                  for (parentConnectionUsage <- parentAllocDef.members.connectionUsages.values) {
                    inheritConnectionUsages(parentConnectionUsage, posOpt)
                  }
                  for (parentItemUsage <- parentAllocDef.members.itemUsages.values) {
                    inheritItemUsages(parentItemUsage, posOpt)
                  }
                  for (parentPartUsage <- parentAllocDef.members.partUsages.values) {
                    inheritPartUsages(parentPartUsage, posOpt)
                  }
                  for (parentPortUsage <- parentAllocDef.members.portUsages.values) {
                    inheritPortUsages(parentPortUsage, posOpt)
                  }
                  for (parentReferenceUsage <- parentAllocDef.members.referenceUsages.values) {
                    inheritReferenceUsages(parentReferenceUsage, posOpt)
                  }

                case Some(parentAttrDef: TypeInfo.AttributeDefinition) =>
                  ancestors = ancestors + getTypedName(parentAttrDef.tpe)
                  val posOpt = parent.posOpt
                  for (parentAttrDefAncestor <- parentAttrDef.ancestors) {
                    ancestors = ancestors + parentAttrDefAncestor
                  }

                  for (parentAttributeUsage <- parentAttrDef.members.attributeUsages.values) {
                    inheritAttributeUsages(parentAttributeUsage, posOpt)
                  }
                  for (parentConnectionUsage <- parentAttrDef.members.connectionUsages.values) {
                    inheritConnectionUsages(parentConnectionUsage, posOpt)
                  }
                  for (parentItemUsage <- parentAttrDef.members.itemUsages.values) {
                    inheritItemUsages(parentItemUsage, posOpt)
                  }
                  for (parentPartUsage <- parentAttrDef.members.partUsages.values) {
                    inheritPartUsages(parentPartUsage, posOpt)
                  }
                  for (parentPortUsage <- parentAttrDef.members.portUsages.values) {
                    inheritPortUsages(parentPortUsage, posOpt)
                  }
                  for (parentReferenceUsage <- parentAttrDef.members.referenceUsages.values) {
                    inheritReferenceUsages(parentReferenceUsage, posOpt)
                  }


                case Some(parentConnDef: TypeInfo.ConnectionDefinition) =>
                  ancestors = ancestors + getTypedName(parentConnDef.tpe)
                  val posOpt = parent.posOpt
                  for (parentConnDefAncestor <- parentConnDef.ancestors) {
                    ancestors = ancestors + parentConnDefAncestor
                  }

                  for (parentAttributeUsage <- parentConnDef.members.attributeUsages.values) {
                    inheritAttributeUsages(parentAttributeUsage, posOpt)
                  }
                  for (parentConnectionUsage <- parentConnDef.members.connectionUsages.values) {
                    inheritConnectionUsages(parentConnectionUsage, posOpt)
                  }
                  for (parentItemUsage <- parentConnDef.members.itemUsages.values) {
                    inheritItemUsages(parentItemUsage, posOpt)
                  }
                  for (parentPartUsage <- parentConnDef.members.partUsages.values) {
                    inheritPartUsages(parentPartUsage, posOpt)
                  }
                  for (parentPortUsage <- parentConnDef.members.portUsages.values) {
                    inheritPortUsages(parentPortUsage, posOpt)
                  }
                  for (parentReferenceUsage <- parentConnDef.members.referenceUsages.values) {
                    inheritReferenceUsages(parentReferenceUsage, posOpt)
                  }

                case Some(parentEnumDef: TypeInfo.EnumDefinition) =>
                  // TODO will we allow enum specializations?

                case Some(parentPartDef: TypeInfo.PartDefinition) =>
                  ancestors = ancestors + getTypedName(parentPartDef.tpe)
                  val posOpt = parent.posOpt
                  for (parentPartDefAncestor <- parentPartDef.ancestors) {
                    ancestors = ancestors + parentPartDefAncestor
                  }

                  for (parentAttributeUsage <- parentPartDef.members.attributeUsages.values) {
                    inheritAttributeUsages(parentAttributeUsage, posOpt)
                  }
                  for (parentConnectionUsage <- parentPartDef.members.connectionUsages.values) {
                    inheritConnectionUsages(parentConnectionUsage, posOpt)
                  }
                  for (parentItemUsage <- parentPartDef.members.itemUsages.values) {
                    inheritItemUsages(parentItemUsage, posOpt)
                  }
                  for (parentPartUsage <- parentPartDef.members.partUsages.values) {
                    inheritPartUsages(parentPartUsage, posOpt)
                  }
                  for (parentPortUsage <- parentPartDef.members.portUsages.values) {
                    inheritPortUsages(parentPortUsage, posOpt)
                  }
                  for (parentReferenceUsage <- parentPartDef.members.referenceUsages.values) {
                    inheritReferenceUsages(parentReferenceUsage, posOpt)
                  }

                case Some(parentPortDef: TypeInfo.PortDefinition) =>
                  ancestors = ancestors + getTypedName(parentPortDef.tpe)
                  val posOpt = parent.posOpt
                  for (parentPortDefAncestor <- parentPortDef.ancestors) {
                    ancestors = ancestors + parentPortDefAncestor
                  }

                  for (parentAttributeUsage <- parentPortDef.members.attributeUsages.values) {
                    inheritAttributeUsages(parentAttributeUsage, posOpt)
                  }
                  for (parentConnectionUsage <- parentPortDef.members.connectionUsages.values) {
                    inheritConnectionUsages(parentConnectionUsage, posOpt)
                  }
                  for (parentItemUsage <- parentPortDef.members.itemUsages.values) {
                    inheritItemUsages(parentItemUsage, posOpt)
                  }
                  for (parentPartUsage <- parentPortDef.members.partUsages.values) {
                    inheritPartUsages(parentPartUsage, posOpt)
                  }
                  for (parentPortUsage <- parentPortDef.members.portUsages.values) {
                    inheritPortUsages(parentPortUsage, posOpt)
                  }
                  for (parentReferenceUsage <- parentPortDef.members.referenceUsages.values) {
                    inheritReferenceUsages(parentReferenceUsage, posOpt)
                  }

                case _ =>
              }
            case Some(_) => halt("Infeasible: type hierarchy phase should have checked type parents who should be typed names")
            case _ =>
          }
        case Some(x) => halt(s"Infeasible: $x")
        case _ =>
      }
    }

    return (TypeInfo.Members(
      attributeUsages = attributeUsages,
      connectionUsages = connectionUsages,
      itemUsages = itemUsages,
      partUsages = partUsages,
      portUsages = portUsages,
      referenceUsages = referenceUsages),
      ancestors.elements,
      newParents)
  }

  def outlineMembers(info: TypeInfo.Members, scope: Scope.Local, reporter: Reporter): TypeInfo.Members = {
    var attributeUsages = HashSMap.empty[String, Info.AttributeUsage]
    var connectionUsages = HashSMap.empty[String, Info.ConnectionUsage]
    var itemUsages = HashSMap.empty[String, Info.ItemUsage]
    var partUsages = HashSMap.empty[String, Info.PartUsage]
    var portUsages = HashSMap.empty[String, Info.PortUsage]
    var referenceUsages = HashSMap.empty[String, Info.ReferenceUsage]


    def isDeclared(id: String): B = {
      return (
        attributeUsages.contains(id) ||
          connectionUsages.contains(id) ||
          itemUsages.contains(id) ||
          partUsages.contains(id) ||
          portUsages.contains(id) ||
          referenceUsages.contains(id))
    }

    def getType(owner: String,
                ownerPos: Option[Position],
                specializations: ISZ[SAST.SysmlAst.FeatureSpecialization],
                local: Scope.Local): Option[Type]= {
      val tipeOpt: Option[Type] = {
        specializations match {
          case ISZ(TypingsSpecialization(ISZ(name))) =>
            // TODO should the name be linked to the specialization name or to the actual
            // SysmlAST.Name of the the thing being specialized?
            Some(SAST.Type.Named(name = name, attr = TypedAttr(name.posOpt, None())))
          case _ =>
            reporter.error(ownerPos, TypeChecker.typeCheckerKind,
              s"Usage members must have exactly one type but $ownerPos has ${specializations.size}")
            None()
        }
      }
      tipeOpt match {
        case Some(tipe) => return typeHierarchy.typed(scope, tipe, reporter)
        case _ => return None()
      }
    }

    def checkAttributeUsage(aInfo: Info.AttributeUsage): Unit = {
      val aAst = aInfo.ast
      val id = aInfo.id
      if (isDeclared(id)) {
        reporter.error(aAst.posOpt, TypeChecker.typeCheckerKind, s"Cannot redeclare $id.")
      }
      // NOTE: this is bit different than slang as usage like entities (e.g. vars)
      // will have their tipeOpt field populated during AST building. For sysml
      // the type maps are built first and then the usage's specializations are
      // used to lookup up the usage's type (and then build the typed info) so
      // this is when tipeOpt fields are populated
      val tipeOpt = getType(aInfo.id, aInfo.posOpt, aAst.specializations, scope)
      tipeOpt match {
        case Some(tipe) =>
          attributeUsages = attributeUsages + id ~>
            aInfo(
              ast = aAst(tipeOpt = Some(tipe), attr = aAst.attr(typedOpt = tipe.typedOpt)))
        case _ =>
      }
    }

    def checkConnectionUsage(cInfo: Info.ConnectionUsage): Unit = {
      val cAst = cInfo.ast
      val id = cInfo.id
      if (isDeclared(id)) {
        reporter.error(cAst.posOpt, TypeChecker.typeCheckerKind, s"Cannot redeclare $id.")
      }
      // NOTE: this is bit different than slang as usage like entities (e.g. vars)
      // will have their tipeOpt field populated during AST building. For sysml
      // the type maps are built first and then the usage's specializations are
      // used to lookup up the usage's type (and then build the typed info) so
      // this is when tipeOpt fields are populated
      val tipeOpt = getType(cInfo.id, cInfo.posOpt, cAst.specializations, scope)
      tipeOpt match {
        case Some(tipe) =>
          connectionUsages = connectionUsages + id ~>
            cInfo(
              ast = cAst(tipeOpt = Some(tipe), attr = cAst.attr(typedOpt = tipe.typedOpt)))
        case _ =>
      }
    }

    def checkItemUsage(iInfo: Info.ItemUsage): Unit = {
      val iAst = iInfo.ast
      val id = iInfo.id
      if (isDeclared(id)) {
        reporter.error(iAst.posOpt, TypeChecker.typeCheckerKind, s"Cannot redeclare $id.")
      }
      // NOTE: this is bit different than slang as usage like entities (e.g. vars)
      // will have their tipeOpt field populated during AST building. For sysml
      // the type maps are built first and then the usage's specializations are
      // used to lookup up the usage's type (and then build the typed info) so
      // this is when tipeOpt fields are populated
      val tipeOpt = getType(iInfo.id, iInfo.posOpt, iAst.specializations, scope)
      tipeOpt match {
        case Some(tipe) =>
          itemUsages = itemUsages + id ~>
            iInfo(
              ast = iAst(tipeOpt = Some(tipe), attr = iAst.attr(typedOpt = tipe.typedOpt)))
        case _ =>
      }
    }

    def checkPartUsage(pInfo: Info.PartUsage): Unit = {
      val pAst = pInfo.ast
      val id = pInfo.id
      if (isDeclared(id)) {
        reporter.error(pAst.posOpt, TypeChecker.typeCheckerKind, s"Cannot redeclare $id.")
      }
      // NOTE: this is bit different than slang as usage like entities (e.g. vars)
      // will have their tipeOpt field populated during AST building. For sysml
      // the type maps are built first and then the usage's specializations are
      // used to lookup up the usage's type (and then build the typed info) so
      // this is when tipeOpt fields are populated
      val tipeOpt = getType(pInfo.id, pInfo.posOpt, pAst.specializations, scope)
      tipeOpt match {
        case Some(tipe) =>
          partUsages = partUsages + id ~>
            pInfo(
              ast = pAst(tipeOpt = Some(tipe), attr = pAst.attr(typedOpt = tipe.typedOpt)))
        case _ =>
      }
    }

    def checkPortUsage(pInfo: Info.PortUsage): Unit = {
      val pAst = pInfo.ast
      val id = pInfo.id
      if (isDeclared(id)) {
        reporter.error(pAst.posOpt, TypeChecker.typeCheckerKind, s"Cannot redeclare $id.")
      }
      // NOTE: this is bit different than slang as usage like entities (e.g. vars)
      // will have their tipeOpt field populated during AST building. For sysml
      // the type maps are built first and then the usage's specializations are
      // used to lookup up the usage's type (and then build the typed info) so
      // this is when tipeOpt fields are populated
      val tipeOpt = getType(pInfo.id, pInfo.posOpt, pAst.specializations, scope)
      tipeOpt match {
        case Some(tipe) =>
          portUsages = portUsages + id ~>
            pInfo(
              ast = pAst(tipeOpt = Some(tipe), attr = pAst.attr(typedOpt = tipe.typedOpt)))
        case _ =>
      }
    }

    def checkReferenceUsage(pInfo: Info.ReferenceUsage): Unit = {
      val pAst = pInfo.ast
      val id = pInfo.id
      if (isDeclared(id)) {
        reporter.error(pAst.posOpt, TypeChecker.typeCheckerKind, s"Cannot redeclare $id.")
      }
      // NOTE: this is bit different than slang as usage like entities (e.g. vars)
      // will have their tipeOpt field populated during AST building. For sysml
      // the type maps are built first and then the usage's specializations are
      // used to lookup up the usage's type (and then build the typed info) so
      // this is when tipeOpt fields are populated
      val tipeOpt = getType(pInfo.id, pInfo.posOpt, pAst.specializations, scope)
      tipeOpt match {
        case Some(tipe) =>
          referenceUsages = referenceUsages + id ~>
            pInfo(
              ast = pAst(tipeOpt = Some(tipe), attr = pAst.attr(typedOpt = tipe.typedOpt)))
        case _ =>
      }
    }

    for (p <- info.attributeUsages.values) {
      checkAttributeUsage(p)
    }

    for (p <- info.connectionUsages.values) {
      checkConnectionUsage(p)
    }

    for (p <- info.itemUsages.values) {
      checkItemUsage(p)
    }

    for (p <- info.partUsages.values) {
      checkPartUsage(p)
    }

    for (p <- info.portUsages.values) {
      checkPortUsage(p)
    }

    for (p <- info.referenceUsages.values) {
      checkReferenceUsage(p)
    }

    return TypeInfo.Members(
      attributeUsages = attributeUsages,
      connectionUsages = connectionUsages,
      itemUsages = itemUsages,
      partUsages = partUsages,
      portUsages = portUsages,
      referenceUsages = referenceUsages
      )
  }


}