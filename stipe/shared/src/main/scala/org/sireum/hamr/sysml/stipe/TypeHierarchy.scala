// #Sireum
package org.sireum.hamr.sysml.stipe

import org.sireum._
import org.sireum.hamr.ir.{Type, Typed}
import org.sireum.hamr.sysml.symbol.Resolver.{NameMap, QName, TypeMap}
import org.sireum.hamr.sysml.symbol.{Scope, TypeInfo, Util}
import org.sireum.hamr.{ir => SAST}
import org.sireum.message.{Message, Position, Reporter}

object TypeHierarchy {
  val resolverKind: String = "Type Hierarchy"

  def build(force: B, init: TypeHierarchy, reporter: Reporter): TypeHierarchy = {
    val typeMap = init.typeMap

    def resolveType(scope: Scope, t: SAST.Type): SAST.Typed = {
      t match {
        case t: SAST.Type.Named =>
          var name = Util.ids2string(t.name.ids)
          scope.resolveType(typeMap, name) match {
            case Some(ti) => name = ti.name
            case _ =>
              reportError(t.name.posOpt, st"Could not resolve type named '${(name, "::")}'.'".render, reporter)
          }
          return SAST.Typed.Name(name)
      }
    }

    def resolveTypeNameds(posOpt: Option[Position], scope: Scope.Global, parents: ISZ[Type.Named]): ISZ[SAST.Typed.Name] = {
      var r = ISZ[SAST.Typed.Name]()
      for (parent <- parents) {
        val typed = resolveType(scope, parent)
        typed match {
          case typed: SAST.Typed.Name => r = r :+ typed
          case _ => reportError(posOpt, "Expected a named type", reporter)
        }
      }
      return r
    }

    var r: TypeHierarchy = init

    def getParentTypes(parentNames: ISZ[SAST.SysmlAst.Name], scope: Scope, posOpt: Option[Position]): ISZ[SAST.Type.Named] = {
      var ret: ISZ[Type.Named] = ISZ()
      for (parentName <- parentNames) {
        val pName = Util.ids2string(parentName.ids)
        scope.resolveType(r.typeMap, pName) match {
          case Some(parentType) =>
            val typed = SAST.Typed.Name(parentType.name)
            ret = ret :+ Type.Named(
              name = parentName,
              attr = SAST.TypedAttr(parentName.posOpt, Some(typed)))
          case _ =>
            reportError(posOpt, st"Could not resolve type named '${(pName, "::")}'".render, reporter)
        }
      }
      return ret
    }

    for (infox <- r.typeMap.entries) {
      infox._2 match {
        case info: TypeInfo.EnumDefinition =>
          val typed = typedInfo(info)
          // TODO: possibly do this as a rewrite
          r = r(poset = r.poset.addParents(typed.ids, ISZ(ISZ("AADL", "Data"))))

        case info: TypeInfo.PartDefinition =>
          if (!info.outlined || force) {
            val typed = typedInfo(info)

            // NOTE: slang populates 'parents' ast field during parsing (via meta).  For sysml
            // we need to resolve the subclassifications and then update the ast/typeMap now -- along
            // with updating the poset info

            //val parents = resolveTypeNameds(info.posOpt, info.scope, info.ast.parents)
            //val parentTypeNames = for(p <- parents) yield p.ids
            //r = r(poset = r.poset.addParents(typed.ids, parentTypeNames))

            val parentsNameds: ISZ[Type.Named] = getParentTypes(info.ast.subClassifications, info.scope, info.posOpt)
            val parents: ISZ[SAST.Typed.Name] = resolveTypeNameds(info.posOpt, info.scope, parentsNameds)
            val parentTypeNames: ISZ[ISZ[String]] = for(p <- parents) yield p.ids
            r = r(
              poset = r.poset.addParents(typed.ids, parentTypeNames),
              typeMap = r.typeMap + info.name ~> info(ast = info.ast(parents = parentsNameds)))
          }
        case info: TypeInfo.PortDefinition =>
          if (!info.outlined || force) {
            val typed = typedInfo(info)

            //val parents = resolveTypeNameds(info.posOpt, info.scope, info.ast.parents)
            //val parentTypeNames = for(p <- parents) yield p.ids
            //r = r(poset = r.poset.addParents(typed.ids, parentTypeNames))

            val parentsNameds: ISZ[Type.Named] = getParentTypes(info.ast.subClassifications, info.scope, info.posOpt)
            val parents: ISZ[SAST.Typed.Name] = resolveTypeNameds(info.posOpt, info.scope, parentsNameds)
            val parentTypeNames: ISZ[ISZ[String]] = for(p <- parents) yield p.ids
            r = r(
              poset = r.poset.addParents(typed.ids, parentTypeNames),
              typeMap = r.typeMap + info.name ~> info(ast = info.ast(parents = parentsNameds)))
          }
        case info: TypeInfo.ConnectionDefinition =>
          if (!info.outlined || force) {
            val typed = typedInfo(info)

            //val parents = resolveTypeNameds(info.posOpt, info.scope, info.ast.parents)
            //val parentTypeNames = for(p <- parents) yield p.ids
            //r = r(poset = r.poset.addParents(typed.ids, parentTypeNames))

            val parentsNameds: ISZ[Type.Named] = getParentTypes(info.ast.subClassifications, info.scope, info.posOpt)
            val parents: ISZ[SAST.Typed.Name] = resolveTypeNameds(info.posOpt, info.scope, parentsNameds)
            val parentTypeNames: ISZ[ISZ[String]] = for(p <- parents) yield p.ids
            r = r(
              poset = r.poset.addParents(typed.ids, parentTypeNames),
              typeMap = r.typeMap + info.name ~> info(ast = info.ast(parents = parentsNameds)))
          }
        case info: TypeInfo.InterfaceDefinition =>
          if (!info.outlined || force) {
            val typed = typedInfo(info)

            //val parents = resolveTypeNameds(info.posOpt, info.scope, info.ast.parents)
            //val parentTypeNames = for(p <- parents) yield p.ids
            //r = r(poset = r.poset.addParents(typed.ids, parentTypeNames))

            val parentsNameds: ISZ[Type.Named] = getParentTypes(info.ast.subClassifications, info.scope, info.posOpt)
            val parents: ISZ[SAST.Typed.Name] = resolveTypeNameds(info.posOpt, info.scope, parentsNameds)
            val parentTypeNames: ISZ[ISZ[String]] = for (p <- parents) yield p.ids
            r = r(
              poset = r.poset.addParents(typed.ids, parentTypeNames),
              typeMap = r.typeMap + info.name ~> info(ast = info.ast(
                parents = parentsNameds)))
          }
        case info: TypeInfo.AttributeDefinition =>
          if (!info.outlined || force) {
            val typed = typedInfo(info)
            //val parents = resolveTypeNameds(info.posOpt, info.scope, info.ast.parents)
            //val parentTypeNames = for(p <- parents) yield p.ids
            //r = r(poset = r.poset.addParents(typed.ids, parentTypeNames))

            val parentsNameds: ISZ[Type.Named] = getParentTypes(info.ast.subClassifications, info.scope, info.posOpt)
            val parents: ISZ[SAST.Typed.Name] = resolveTypeNameds(info.posOpt, info.scope, parentsNameds)
            val parentTypeNames: ISZ[ISZ[String]] = for(p <- parents) yield p.ids
            r = r(
              poset = r.poset.addParents(typed.ids, parentTypeNames),
              typeMap = r.typeMap + info.name ~> info(ast = info.ast(parents = parentsNameds)))
          }
        case info: TypeInfo.AllocationDefinition =>
          if (!info.outlined || force) {
            val typed = typedInfo(info)
            //val parents = resolveTypeNameds(info.posOpt, info.scope, info.ast.parents)
            //val parentTypeNames = for(p <- parents) yield p.ids
            //r = r(poset = r.poset.addParents(typed.ids, parentTypeNames))

            val parentsNameds: ISZ[Type.Named] = getParentTypes(info.ast.subClassifications, info.scope, info.posOpt)
            val parents: ISZ[SAST.Typed.Name] = resolveTypeNameds(info.posOpt, info.scope, parentsNameds)
            val parentTypeNames: ISZ[ISZ[String]] = for(p <- parents) yield p.ids
            r = r(
              poset = r.poset.addParents(typed.ids, parentTypeNames),
              typeMap = r.typeMap + info.name ~> info(ast = info.ast(parents = parentsNameds)))
          }
        case info: TypeInfo.Package =>
          if (!info.outlined || force) {
            val typed = typedInfo(info)
            r = r(poset = r.poset.addNode(typed.ids))
          }
        case x =>
          reportWarn(x.posOpt, s"Need to handle $x", reporter)
      }
    }

    if (reporter.hasError) {
      return r
    }
    r.checkCyclic(reporter)
    return r
  }



  @pure def typedInfo(info: TypeInfo): SAST.Typed.Name = {
    info match {
      case info: TypeInfo.Package => return SAST.Typed.Name(info.name)
      case info: TypeInfo.AllocationDefinition => return SAST.Typed.Name(info.name)
      case info: TypeInfo.AttributeDefinition => return SAST.Typed.Name(info.name)
      case info: TypeInfo.ConnectionDefinition => return SAST.Typed.Name(info.name)
      case info: TypeInfo.EnumDefinition => return SAST.Typed.Name(info.name)
      case info: TypeInfo.InterfaceDefinition => return SAST.Typed.Name(info.name)
      case info: TypeInfo.PartDefinition => return SAST.Typed.Name(info.name)
      case info: TypeInfo.PortDefinition => return SAST.Typed.Name(info.name)
      case _ =>
        halt(s"Wasn't expecting: ${info}")
    }
  }

  @pure def combine(r: (TypeHierarchy, ISZ[Message]),
                    f: TypeHierarchy => (TypeHierarchy, ISZ[Message]) @pure): (TypeHierarchy, ISZ[Message]) = {
    val p = f(r._1)
    return (p._1, r._2 ++ p._2)
  }


  def reportWarnCond(cond: B, posOpt: Option[Position], message: String, reporter: Reporter): Unit = {
    if (!cond) {
      reportWarn(posOpt, message, reporter)
    }
  }

  def reportWarn(posOpt: Option[Position], message: String, reporter: Reporter): Unit = {
    reporter.warn(posOpt, resolverKind, s"TypeHierarchy Warning: $message")
  }

  def reportErrorCond(cond: B, posOpt: Option[Position], message: String, reporter: Reporter): Unit = {
    if (!cond) {
      reportError(posOpt, message, reporter)
    }
  }

  def reportError(posOpt: Option[Position], message: String, reporter: Reporter): Unit = {
    reporter.error(posOpt, resolverKind, s"TypeHierarchy Error: $message")
  }
}

@datatype class TypeHierarchy(val nameMap: NameMap,
                              val typeMap: TypeMap,
                              val poset: Poset[QName],
                              val aliases: HashSMap[QName, SAST.Typed]) {

  def checkCyclic(reporter: Reporter): Unit = {
    // TODO
    //reporter.warn(None(), TypeHierarchy.resolverKind, "Need to implement checkCyclic")
  }

  def checkTyped(posOpt: Option[Position], t: SAST.Typed, reporter: Reporter): Unit = {
    // NOTE: no type args in sysml so probably nothing to do here
    return
  }

  def typed(scope: Scope.Local, typ: Type, reporter: Reporter): Option[SAST.Type] = {
    def typedH(tipe: SAST.Type): Option[SAST.Type] = {
      tipe match {
        case tipe: SAST.Type.Named =>

          val name = Util.ids2string(tipe.name.ids)
          scope.resolveType(typeMap, name) match {
            case Some(ti) =>
              val typed = SAST.Typed.Name(ti.name)
              checkTyped(tipe.posOpt, typed, reporter)
              return Some(tipe(attr = tipe.attr(typedOpt = Some(typed))))
            case _ =>
              TypeHierarchy.reportError(tipe.posOpt,
                st"Could not find a type named ${(name, "::")}.".render, reporter)
              return None()
          }
      }
    }
    val r = typedH(typ)
    return r
  }

  def isSubType(t1: Typed, t2: Typed): B = {
    if (t1 == t2) {
      return T
    }
    (t1, t2) match {
      case (n1: SAST.Typed.Name, n2: SAST.Typed.Name) =>
        if (!poset.ancestorsOf(n1.ids).contains(n2.ids)) {
          return F
        }

        val (outlined, ancestors): (B, ISZ[SAST.Typed.Name]) =
          typeMap.get(n1.ids) match {
            case Some(info: TypeInfo.AllocationDefinition) => (info.outlined, info.ancestors)
            case Some(info: TypeInfo.AttributeDefinition) => (info.outlined, info.ancestors)
            case Some(info: TypeInfo.ConnectionDefinition) => (info.outlined, info.ancestors)
            case Some(info: TypeInfo.EnumDefinition) => (info.outlined, info.ancestors)
            case Some(info: TypeInfo.PartDefinition) => (info.outlined, info.ancestors)
            case Some(info: TypeInfo.PortDefinition) => (info.outlined, info.ancestors)
            case _ => return F
          }
        if (!outlined) {
          return T
        }
        for (ancestor <- ancestors if ancestor.ids == n2.ids) {
          return T
        }
        return F
      case (te: SAST.Typed.Enum, tn: SAST.Typed.Name) =>
        if (!poset.ancestorsOf(te.name).contains(tn.ids)) {
          return F
        }
        val (outlined, ancestors): (B, ISZ[SAST.Typed.Name]) =
          typeMap.get(te.name) match {
            case Some(info: TypeInfo.AttributeDefinition) => (info.outlined, info.ancestors)
            case Some(info: TypeInfo.EnumDefinition) => (info.outlined, info.ancestors)
            case _ => halt("Infeasible")
          }
        if (!outlined) {
          return T
        }
        for (ancestor <- ancestors if ancestor.ids == tn.ids) {
          return T
        }
        return F
      case (tn: SAST.Typed.Name, en: SAST.Typed.Enum) =>
        if (!poset.ancestorsOf(tn.ids).contains(en.name)) {
          return F
        }
        val (outlined, ancestors): (B, ISZ[SAST.Typed.Name]) =
          typeMap.get(tn.ids) match {
            case Some(info: TypeInfo.AttributeDefinition) => (info.outlined, info.ancestors)
            case Some(info: TypeInfo.EnumDefinition) => (info.outlined, info.ancestors)
            case x => halt(s"Infeasible: $x")
          }
        if (!outlined) {
          return T
        }
        for (ancestor <- ancestors if ancestor.ids == en.name) {
          return T
        }
        return F
      case _ =>
        halt(s"TODO: $t1 vs $t2")
    }
  }

  def mimicSysml(t1: Typed, t2: Typed): B = {
    if (t1 == t2) {
      return T
    }
    (t1, t2) match {
      case (n1: SAST.Typed.Name, n2: SAST.Typed.Name) =>
        if ((n1.ids == ISZ("Timing_Properties", "Period") || n1.ids == ISZ("Timing_Properties","Frame_Period") || n1.ids == ISZ("Timing_Properties","Clock_Period")) &&
          n2.ids == ISZ("SI", "DurationUnit")) {
          return T
        } else if ((n1.ids == ISZ("CASE_Scheduling", "Max_Domain") || n1.ids == ISZ("CASE_Scheduling", "Domain")) && n2.ids == ISZ("org", "sireum", "Z")) {
          return T
        } else if (n1.ids == ISZ("Memory_Properties", "Data_Size") && n2.ids == ISZ("SI", "StorageCapacityUnit")) {
          return T
        }
      case _ =>
    }
    return F
  }
}
