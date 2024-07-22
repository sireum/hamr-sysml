// #Sireum
package org.sireum.hamr.sysml.stipe

import org.sireum._
import org.sireum.hamr.sysml.ast.{Type, Typed}
import org.sireum.hamr.sysml.symbol.Resolver.{NameMap, QName, TypeMap}
import org.sireum.hamr.sysml.symbol.{Scope, TypeInfo}
import org.sireum.hamr.sysml.{ast => SAST}
import org.sireum.message.{Message, Position, Reporter}

object TypeHierarchy {
  val resolverKind: String = "Type Hierarchy"

  def build(force: B, init: TypeHierarchy, reporter: Reporter): TypeHierarchy = {
    val typeMap = init.typeMap

    def resolveType(scope: Scope, t: SAST.Type): SAST.Typed = {
      t match {
        case t: SAST.Type.Named =>
          var name = SAST.Util.ids2string(t.name.ids)
          scope.resolveType(typeMap, name) match {
            case Some(ti) => name = ti.name
            case _ =>
              reporter.error(t.name.posOpt, resolverKind, st"Could not resolve type named '${(name, ".")}'.'".render)
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
          case _ => reporter.error(posOpt, resolverKind, "Expected a named type")
        }
      }
      return r
    }

    var r: TypeHierarchy = init

    def getParentTypes(parentNames: ISZ[SAST.SysmlAst.Name], scope: Scope, posOpt: Option[Position]): ISZ[SAST.Type.Named] = {
      var ret: ISZ[Type.Named] = ISZ()
      for (parentName <- parentNames) {
        val pName = SAST.Util.ids2string(parentName.ids)
        scope.resolveType(r.typeMap, pName) match {
          case Some(parentType) =>
            val typed = SAST.Typed.Name(parentType.name)
            ret = ret :+ Type.Named(
              name = parentName,
              attr = SAST.TypedAttr(parentName.posOpt, Some(typed)))
          case _ =>
            reporter.error(posOpt, TypeHierarchy.resolverKind, s"Could not resolve type ${pName}")
        }
      }
      return ret
    }

    for (infox <- r.typeMap.entries) {
      infox._2 match {
        case info: TypeInfo.EnumDefinition =>
          val typed = typedInfo(info)
          r = r(poset = r.poset.addNode(typed.ids))
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
        case x =>
          reporter.warn(x.posOpt, resolverKind, s"Need to handle $x")
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
      case info: TypeInfo.AllocationDefinition => return SAST.Typed.Name(info.name)
      case info: TypeInfo.AttributeDefinition => return SAST.Typed.Name(info.name)
      case info: TypeInfo.ConnectionDefinition => return SAST.Typed.Name(info.name)
      case info: TypeInfo.EnumDefinition => return SAST.Typed.Name(info.name)
      case info: TypeInfo.PartDefinition => return SAST.Typed.Name(info.name)
      case info: TypeInfo.PortDefinition => return SAST.Typed.Name(info.name)
      case _ =>
        halt("")
    }
  }

  @pure def combine(r: (TypeHierarchy, ISZ[Message]),
                    f: TypeHierarchy => (TypeHierarchy, ISZ[Message]) @pure): (TypeHierarchy, ISZ[Message]) = {
    val p = f(r._1)
    return (p._1, r._2 ++ p._2)
  }
}

@datatype class TypeHierarchy(val nameMap: NameMap,
                              val typeMap: TypeMap,
                              val poset: Poset[QName],
                              val aliases: HashSMap[QName, SAST.Typed]) {
  def checkCyclic(reporter: Reporter): Unit = {
    reporter.warn(None(), TypeHierarchy.resolverKind, "Need to implement checkCyclic")
  }

  def checkTyped(posOpt: Option[Position], t: SAST.Typed, reporter: Reporter): Unit = {
    // NOTE: no type args in sysml so probably nothing to do here
    return
  }

  def typed(scope: Scope.Local, typ: Type, reporter: Reporter): Option[SAST.Type] = {
    def typedH(tipe: SAST.Type): Option[SAST.Type] = {
      tipe match {
        case tipe: SAST.Type.Named =>

          val name = SAST.Util.ids2string(tipe.name.ids)
          scope.resolveType(typeMap, name) match {
            case Some(ti) =>
              val typed = SAST.Typed.Name(ti.name)
              checkTyped(tipe.posOpt, typed, reporter)
              return Some(tipe(attr = tipe.attr(typedOpt = Some(typed))))
            case _ =>
              reporter.error(tipe.posOpt, TypeChecker.typeCheckerKind,
                st"Could not find a type named ${(name, "::")}.".render)
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
      case (t1: SAST.Typed.Name, t2: SAST.Typed.Name) =>
        if (!poset.ancestorsOf(t1.ids).contains(t2.ids)) {
          return F
        }

        val (outlined, ancestors): (B, ISZ[SAST.Typed.Name]) =
          typeMap.get(t1.ids) match {
            //case Some(info: TypeInfo.AllocationDefinition) => (info.outlined, info.ancestors)
            case Some(info: TypeInfo.AttributeDefinition) => (info.outlined, info.ancestors)
            case Some(info: TypeInfo.ConnectionDefinition) => (info.outlined, info.ancestors)
            case Some(info: TypeInfo.PartDefinition) => (info.outlined, info.ancestors)
            case Some(info: TypeInfo.PortDefinition) => (info.outlined, info.ancestors)
            case _ => return F
          }
        if (!outlined) {
          return T
        }
        for (ancestor <- ancestors if ancestor.ids == t2.ids) {
          return T
        }
        return F
      case _ =>
        halt(s"TODO: $t1 vs $t2")
    }
  }

}
