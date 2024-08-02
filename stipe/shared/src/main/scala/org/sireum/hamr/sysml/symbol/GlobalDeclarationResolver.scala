// #Sireum
package org.sireum.hamr.sysml.symbol

import org.sireum._
import org.sireum.hamr.sysml.{ast => SAST}
import org.sireum.hamr.sysml.ast.SysmlAst
import org.sireum.hamr.sysml.symbol.Resolver._
import org.sireum.message.{Position, Reporter}

object GlobalDeclarationResolver {

}

@record class GlobalDeclarationResolver(var globalNameMap: NameMap, var globalTypeMap: TypeMap, val reporter: Reporter) {

  var packageName: QName = ISZ()
  var currentName: QName = ISZ()
  var currentImports: ISZ[SysmlAst.Import] = ISZ()

  def resolveTopUnit(topUnit: SysmlAst.TopUnit): Unit = {
    for (pbe <- topUnit.packageBodyElements) {
      resolveElement(pbe)
    }
  }

  def resolveElement(e: SysmlAst.PackageBodyElement): Unit = {
    e match {
      case i: SysmlAst.Import =>
        currentImports = currentImports :+ i

      case a: SysmlAst.AliasMember =>
        reporter.warn(a.posOpt, resolverKind, "Not currently resolving aliases")

      case p: SysmlAst.Package =>
        declarePackageName(p) match {
          case Some(info) =>
            packageName = info.name
            currentName = packageName
          case _ =>

        }
        for (elem <- p.packageElements) {
          resolveElement(elem)
        }

      case e: SysmlAst.EnumerationDefinition =>
        getId(e.identification, e.posOpt) match {
          case (Some(id), posOpt) =>
            val name = currentName :+ id
            var elements = Map.empty[String, SAST.ResolvedInfo]
            val elementsTypeName = name // :+ Info.EnumDefinition.elementTypeSuffix
            val elementTypedOpt: Option[SAST.Typed] = Some(SAST.Typed.Name(elementsTypeName))
            var elementPosOpts: ISZ[Option[Position]] = ISZ()
            var ordinal = 0

            for(e <- e.enumValues) {
              getId(e.identification, posOpt) match {
                case (Some(id), posOpt) =>
                  if (elements.contains(id)) {
                    reporter.error(posOpt, resolverKind, s"Redeclaration of enum value ${id}")
                  } else {
                    elements = elements + id ~> SAST.ResolvedInfo.EnumElement(name, id, ordinal)
                    elementPosOpts = elementPosOpts :+ posOpt
                  }
                case _ =>
                  elementPosOpts = elementPosOpts :+ None()
              }
              ordinal = ordinal + 1
            }

            /*
            declareName(
              entity = "enumeration",
              name = name,
              info = Info.EnumDefinition(
                name = name,
                elements = elements,
                typedOpt = Some(SAST.Typed.Enum(name)),
                resOpt = Some(SAST.ResolvedInfo.Enum(name)),
                elementTypedOpt = elementTypedOpt,
                posOpt = e.posOpt),
              posOpt = posOpt
            )
            */

            declareType(
              entity = "enumeration",
              name = elementsTypeName,
              info = TypeInfo.EnumDefinition(owner = name, elements = elements, posOpt = posOpt),
              posOpt = posOpt)

            var i = 0
            for (e <- elements.entries) {
              val elemPosOpt = elementPosOpts(i)
              declareName(
                entity = "enumeration element",
                name = name :+ e._1,
                info = Info.EnumElement(name, e._1, elementTypedOpt, Some(e._2), elemPosOpt),
                posOpt = elemPosOpt
              )
              i = i + 1
            }

            if (e.subClassifications.nonEmpty) {
              reporter.warn(e.subClassifications(0).attr.posOpt, resolverKind, "Enum subclassifications are not currently handled")
            }

            if (e.annotations.nonEmpty) {
              reporter.warn(e.annotations(0).posOpt, resolverKind, "Annotation attached to enums are not currently handled")
            }

          case _ =>
        }

      case e: SysmlAst.PartDefinition =>

        getId(e.identification, e.posOpt) match {
          case (Some(id), posOpt) =>
            val name = currentName :+ id
            val sc = scope(packageName, currentImports, name)

            val members: TypeInfo.Members = resolveMembers(name, sc, e.bodyItems)

            declareType(
              entity = "part definition",
              name = name,
              info = TypeInfo.PartDefinition(
                owner = currentName,
                id = id,
                outlined = F,
                typeChecked = F,
                ancestors = ISZ(),
                members = members,
                scope = sc,
                ast = e),
              posOpt = e.posOpt
            )
          case _ =>
        }

      case e: SysmlAst.PortDefinition =>

        getId(e.identification, e.posOpt) match {
          case (Some(id), posOpt) =>
            val name = currentName :+ id
            val sc = scope(packageName, currentImports, name)

            val members: TypeInfo.Members = resolveMembers(name, sc, e.bodyItems)

            declareType(
              entity = "port definition",
              name = name,
              info = TypeInfo.PortDefinition(
                owner = currentName,
                id = id,
                outlined = F,
                typeChecked = F,
                ancestors = ISZ(),
                members = members,
                scope = sc,
                ast = e),
              posOpt = e.posOpt
            )
          case _ =>
        }

      case e: SysmlAst.ConnectionDefinition =>
        getId(e.identification, e.posOpt) match {
          case (Some(id), posOpt) =>
            val name = currentName :+ id
            val sc = scope(packageName, currentImports, name)

            val members: TypeInfo.Members = resolveMembers(name, sc, e.bodyItems)

            declareType(
              entity = "connection definition",
              name = name,
              info = TypeInfo.ConnectionDefinition(
                owner = currentName,
                id = id,
                outlined = F,
                typeChecked = F,
                ancestors = ISZ(),
                members = members,
                scope = sc,
                ast = e),
              posOpt = e.posOpt
            )
          case _ =>
        }

      case e: SysmlAst.AttributeDefinition =>
        getId(e.identification, e.posOpt) match {
          case (Some(id), posOpt) =>
            val name = currentName :+ id
            val sc = scope(packageName, currentImports, name)

            val members: TypeInfo.Members = resolveMembers(name, sc, e.bodyItems)

            declareType(
              entity = "attribute definition",
              name = name,
              info = TypeInfo.AttributeDefinition(
                owner = currentName,
                id = id,
                outlined = F,
                typeChecked = F,
                ancestors = ISZ(),
                members = members,
                scope = sc,
                ast = e),
              posOpt = e.posOpt
            )
          case _ =>
        }
      case e: SysmlAst.AllocationDefinition =>
        getId(e.identification, e.posOpt) match {
          case (Some(id), posOpt) =>
            val name = currentName :+ id
            val sc = scope(packageName, currentImports, name)

            val members: TypeInfo.Members = resolveMembers(name, sc, e.bodyItems)

            declareType(
              entity = "allocation definition",
              name = name,
              info = TypeInfo.AllocationDefinition(
                owner = currentName,
                id = id,
                outlined = F,
                typeChecked = F,
                ancestors = ISZ(),
                members = members,
                scope = sc,
                ast = e),
              posOpt = e.posOpt
            )
          case _ =>
        }

      case e: SysmlAst.AttributeUsage =>
        reporter.warn(e.posOpt, resolverKind, "Need to handle attribute usages")

      case e: SysmlAst.AnnotatingElement =>
        e match {
          case c: SysmlAst.Comment if (c.abouts.nonEmpty) =>
            reporter.warn(e.posOpt, resolverKind, "Need to resolve comment 'abouts'")
          case _ =>
        }

      case x =>
        reporter.warn(x.posOpt, resolverKind, s"Need to handle ${x}")
    }
  }

  def resolveMembers(owner: IS[Z, String], scope: Scope, items: ISZ[SysmlAst.DefinitionBodyItem]): TypeInfo.Members = {
    var attributeUsages = HashSMap.empty[String, Info.AttributeUsage]
    var connectionUsages = HashSMap.empty[String, Info.ConnectionUsage]
    var itemUsages = HashSMap.empty[String, Info.ItemUsage]
    var partUsages = HashSMap.empty[String, Info.PartUsage]
    var portUsages = HashSMap.empty[String, Info.PortUsage]
    var referenceUsages = HashSMap.empty[String, Info.ReferenceUsage]


    @pure def checkId(id: String, posOpt: Option[Position]): Unit = {
      val declared: B = {
        if (attributeUsages.contains(id)) T
        else F
      }
      if (declared) {
        reporter.error(posOpt, resolverKind, s"Member $id has been previously declared.")
      }
    }

    for (item <- items) {
      item match {
        case i: SysmlAst.AttributeUsage =>
          getId(i.identification, i.posOpt) match {
            case (Some(id), posOpt) =>
              checkId(id, posOpt)
              attributeUsages = attributeUsages +
                id ~> Info.AttributeUsage(owner = owner, id = id, scope = scope,
                  ast = i(attr = i.attr(resOpt = Some(SAST.ResolvedInfo.AttributeUsage(owner = owner, name = id)))))
            case x =>
              reporter.warn(i.posOpt, resolverKind, s"How to handle usages without identification")
          }
        case i: SysmlAst.ConnectionUsage =>
          getId(i.identification, i.posOpt) match {
            case (Some(id), posOpt) =>
              checkId(id, posOpt)
              connectionUsages = connectionUsages +
                id ~> Info.ConnectionUsage(owner = owner, id = id, scope = scope,
                  ast = i(attr = i.attr(resOpt = Some(SAST.ResolvedInfo.ConnectionUsage(owner = owner, name = id)))))
            case _ =>
              reporter.warn(i.posOpt, resolverKind, s"How to handle usages without identification")
          }
        case i: SysmlAst.ItemUsage =>
          getId(i.identification, i.posOpt) match {
            case (Some(id), posOpt) =>
              checkId(id, posOpt)
              itemUsages = itemUsages +
                id ~> Info.ItemUsage(owner = owner, id = id, scope = scope,
                  ast = i(attr = i.attr(resOpt = Some(SAST.ResolvedInfo.ItemUsage(owner = owner, name = id)))))
            case _ =>
              reporter.warn(i.posOpt, resolverKind, s"How to handle usages without identification")
          }
        case i: SysmlAst.PartUsage =>
          getId(i.identification, i.posOpt) match {
            case (Some(id), posOpt) =>
              checkId(id, posOpt)
              partUsages = partUsages +
                id ~> Info.PartUsage(owner = owner, id = id, scope = scope,
                  ast = i(attr = i.attr(resOpt = Some(SAST.ResolvedInfo.PartUsage(owner = owner, name = id)))))
            case _ =>
              reporter.warn(i.posOpt, resolverKind, s"How to handle usages without identification")
          }

        case i: SysmlAst.PortUsage =>
          getId(i.identification, i.posOpt) match {
            case (Some(id), posOpt) =>
              checkId(id, posOpt)
              portUsages = portUsages +
                id ~> Info.PortUsage(owner = owner, id = id, scope = scope,
                  ast = i(attr = i.attr(resOpt = Some(SAST.ResolvedInfo.PortUsage(owner = owner, name = id)))))
            case _ =>
              reporter.warn(i.posOpt, resolverKind, s"How to handle usages without identification")
          }

        case i: SysmlAst.ReferenceUsage =>
          getId(i.identification, i.posOpt) match {
            case (Some(id), posOpt) =>
              checkId(id, posOpt)
              referenceUsages = referenceUsages +
                id ~> Info.ReferenceUsage(owner = owner, id = id, scope = scope,
                  ast = i(attr = i.attr(resOpt = Some(SAST.ResolvedInfo.ReferenceUsage(owner = owner, name = id)))))
            case _ =>
              reporter.warn(i.posOpt, resolverKind, s"How to handle usages without identification")
          }
        case i =>
          reporter.warn(i.posOpt, resolverKind, s"Need to handle member: ${i}")
      }
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

  def declareType(entity: String, name: ISZ[String], info: TypeInfo, posOpt: Option[Position]): Unit = {
    assert(name == info.name)

    globalNameMap.get(name) match {
      case Some(_) =>
        reporter.error(posOpt, resolverKind, s"Cannot declare $entity type $name because the name has already been declared previously as a name")
      case _ =>
    }
    globalTypeMap.get(name) match {
      case Some(_) =>
        reporter.error(posOpt, resolverKind, s"Cannot declare $entity type $name because the name has already been declared previously as a type")
      case _ =>
        globalTypeMap = globalTypeMap + name ~> info
    }
  }

  @memoize def scope(pName: QName, imports: ISZ[SysmlAst.Import], name: QName): Scope.Global = {
    return Scope.Global(pName, imports, ops.ISZOps(name).dropRight(1))
  }

  def declareName(entity: String, name: ISZ[String], info: Info, posOpt: Option[Position]): Unit = {
    assert(name == info.name)
    globalNameMap.get(name) match {
      case Some(_) =>
        reporter.error(posOpt, resolverKind, s"Cannot declare $entity because the name has already been declared previously")
      case _ => globalNameMap = globalNameMap + name ~> info
    }
  }

  def declarePackage(name: ISZ[String], posOpt: Option[Position]): Unit = {
    globalNameMap.get(name) match {
      case Some(_: Info.Package) => reporter.warn(posOpt, resolverKind, "Is this possible in SysMLv2?")
      case Some(_) => reporter.error(posOpt, resolverKind, "Cannot declare package because the has has already been used for a non-package entity")
      case _ =>
        globalNameMap = globalNameMap + name ~> Info.Package(
          name, Some(SAST.Typed.Package(name)), Some(SAST.ResolvedInfo.Package(name)))
    }
  }

  def declarePackageName(p: SysmlAst.Package): Option[Info.Package] = {

    val (name, posOpt) = getId(p.identification, p.posOpt)

    if (name.isEmpty) {
      reporter.error(posOpt, resolverKind, "Packages must have full names")
    } else {
      // TODO nested packages??

      val currName = ISZ(name.get)

      declarePackage(currName, posOpt)

      globalNameMap.get(currName) match {
        case Some(info: Info.Package) => return Some(info)
        case _ =>
      }
    }
    return None()
  }

  def getId(id: Option[SysmlAst.Identification], posOpt: Option[Position]): (Option[String], Option[Position]) = {
    id match {
      case Some(id) =>
        if (id.shortName.nonEmpty) {
          reporter.warn(id.shortName.get.posOpt, resolverKind, "Short names are not currently supported")
        }
        id.name match {
          case Some(id) =>
            return (Some(id.value), id.posOpt)
          case _ =>
            reporter.error(id.posOpt, resolverKind, "Names must be provided")
        }
      case _ =>
    }
    return (None(), None())
  }
}


