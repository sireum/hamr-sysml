// #Sireum
package org.sireum.hamr.sysml.stipe

import org.sireum._
import org.sireum.hamr.sysml.ast.SysmlAst.TypingsSpecialization
import org.sireum.hamr.sysml.{ast => SAST}
import org.sireum.hamr.sysml.ast.{Type, TypedAttr}
import org.sireum.hamr.sysml.symbol.Resolver.{QName, TypeMap, addBuiltIns}
import org.sireum.hamr.sysml.symbol.{Info, Scope, TypeInfo}
import org.sireum.message.{Message, Position, Reporter}

object TypeOutliner {

  def checkOutline(par: Z, typeHierarchy: TypeHierarchy, reporter: Reporter): TypeHierarchy = {
    def parentsOutlined(name: QName, typeMap: TypeMap): B = {
      def isOutlined(ids: QName): B = {
        typeMap.get(ids).get match {
          case ti: TypeInfo.AttributeDefinition => return ti.outlined
          case ti: TypeInfo.PartDefinition => return ti.outlined
          case ti: TypeInfo.PortDefinition => return ti.outlined
          case ti: TypeInfo.ConnectionDefinition => return ti.outlined
          case ti: TypeInfo.AllocationDefinition => return ti.outlined
          case _ => return T
        }
      }
      var r = T
      for (p <- typeHierarchy.poset.parentsOf(name).elements if r) {
        typeMap.get(p).get match {
          // TODO: only other slang case is TypeAlias
          case ti =>
            r = isOutlined(ti.name)
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

  private def outlineInheritedMembers(nameOwner: ISZ[String],
                                      nameId: String,
                                      name: QName,
                                      parents: ISZ[Type.Named],
                                      scope: Scope.Local,
                                      info: TypeInfo.Members,
                                      reporter: Reporter): (TypeInfo.Members, ISZ[SAST.Typed.Name], ISZ[SAST.Type.Named]) = {
    var attributeUsages = HashSMap.empty[String, Info.AttributeUsage]
    var itemUsages = HashSMap.empty[String, Info.ItemUsage]
    var partUsages = HashSMap.empty[String, Info.PartUsage]
    var portUsages = HashSMap.empty[String, Info.PortUsage]
    var connectionUsages = HashSMap.empty[String, Info.ConnectionUsage]

    var ancestors = HashSSet.empty[SAST.Typed.Name]
    var newParents = ISZ[SAST.Type.Named]()
    for (parent <- parents) {
      val tipeOpt = typeHierarchy.typed(scope, parent, reporter)
      tipeOpt match {
        case Some(tipe: SAST.Type.Named) =>
          newParents = newParents :+ tipe
          tipe.typedOpt match {
            case Some(t: SAST.Typed.Name) =>
              typeHierarchy.typeMap.get(t.ids) match {
                case Some(x) =>
                  reporter.warn(x.posOpt, TypeChecker.typeCheckerKind, "STOPPED HERE")
                case _ =>
              }
            case _ =>
          }
        case Some(x) => halt(s"Infeasible: $x")
        case _ =>
      }
    }

    return (info, ISZ(), ISZ())
  }

  def outlineMembers(info: TypeInfo.Members, scope: Scope.Local, reporter: Reporter): TypeInfo.Members = {
    var attributeUsages = HashSMap.empty[String, Info.AttributeUsage]
    var itemUsages = HashSMap.empty[String, Info.ItemUsage]
    var partUsages = HashSMap.empty[String, Info.PartUsage]
    var portUsages = HashSMap.empty[String, Info.PortUsage]
    var connectionUsages = HashSMap.empty[String, Info.ConnectionUsage]

    def isDeclared(id: String): B = {
      return (
        attributeUsages.contains(id) ||
        itemUsages.contains(id) ||
        partUsages.contains(id) ||
        portUsages.contains(id) ||
        connectionUsages.contains(id))
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

    for (p <- info.attributeUsages.values) {
      checkAttributeUsage(p)
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

    for (p <- info.connectionUsages.values) {
      checkConnectionUsage(p)
    }

    return TypeInfo.Members(
      attributeUsages = attributeUsages,
      itemUsages = itemUsages,
      partUsages = partUsages,
      portUsages = portUsages,
      connectionUsages = connectionUsages)
  }


}