// #Sireum
package org.sireum.hamr.sysml.symbol

import org.sireum._
import org.sireum.hamr.{ir => SAST}
import org.sireum.hamr.ir.{GclLib, GclSubclause, SysmlAst}
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
        // TODO
        //reporter.warn(a.posOpt, resolverKind, "Not currently resolving aliases")

      case p: SysmlAst.Package =>
        declarePackageName(p, packageName) match {
          case Some(info) =>
            packageName = info.name()
            currentName = packageName
          case _ =>
        }

        for (elem <- p.packageElements) {
          resolveElement(elem)
        }

        val name = currentName
        val sc = scope(packageName, currentImports, name)
        val packageUsageItems: ISZ[SysmlAst.DefinitionBodyItem] =
          for(e <- p.packageElements.filter(e => e.isInstanceOf[SysmlAst.UsageElement])) yield e.asInstanceOf[SysmlAst.DefinitionBodyItem]
        val members: TypeInfo.Members = resolveMembers(name, sc, packageUsageItems)

        if(members.nonEmpty) {
          declareTypeH(
            isEnumOrPackage = T,
            entity = "package",
            name = name,
            info = TypeInfo.Package(
              id = name,
              outlined = F,
              typeChecked = F,
              members = members,
              scope = sc,
              ast = p
            ),
            posOpt = p.posOpt)
        }

      case e: SysmlAst.EnumerationDefinition =>

        getId(e.identification, e.posOpt) match {
          case (Some(id), posOpt) =>
            val name = currentName :+ id
            var elements = Map.empty[String, SAST.ResolvedInfo]
            val elementsTypeName = name
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

            declareTypeH(
              isEnumOrPackage = T,
              entity = "enumeration",
              name = elementsTypeName,
              info = TypeInfo.EnumDefinition(
                owner = name,
                outlined = F,
                ancestors = ISZ(),
                elements = elements,
                posOpt = posOpt),
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

        getId(e.commonUsageElements.identification, e.posOpt) match {
          case (Some(id), posOpt) =>
            val name = currentName :+ id
            val sc = scope(packageName, currentImports, name)

            val members: TypeInfo.Members = resolveMembers(name, sc, e.commonUsageElements.definitionBodyItems)

            declareName(
              entity = "attribute usage",
              name = name,
              info = Info.AttributeUsage(
                owner = packageName,
                id = id,
                scope = sc,
                ast = e(commonUsageElements =
                  e.commonUsageElements(attr = e.commonUsageElements.attr(
                    resOpt = Some(SAST.ResolvedInfo.AttributeUsage(owner = packageName, name = id)))))
              ),
              posOpt = e.posOpt
            )
          case _ =>
        }
      case e: SysmlAst.AnnotatingElement =>
        e match {
          case c: SysmlAst.Comment if (c.abouts.nonEmpty) =>
            reporter.warn(e.posOpt, resolverKind, "Need to resolve comment 'abouts'")

          case g @ SysmlAst.GumboAnnotation(lib: GclLib) =>

            // for now rely on instantiating the systems and then letting the AIR
            // resolver type check the gumbo contracts

          // reporter.warn(e.posOpt, resolverKind, "Need to resolve gumbo libraries")

          case SysmlAst.GumboAnnotation(lib: GclSubclause) =>
            halt(s"Not expecting a GUMBO subclause declared at the package level: ${lib.posOpt}")
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

    def update(ast: SysmlAst.CommonUsageElements, id: String): SAST.SysmlAst.CommonUsageElements = {
      return ast(attr = ast.attr(resOpt = Some(SAST.ResolvedInfo.AttributeUsage(owner = owner, name = id))))
    }

    for (item <- items) {
      item match {
        case ast: SysmlAst.AttributeUsage =>
          getId(ast.commonUsageElements.identification, ast.posOpt) match {
            case (Some(id), posOpt) =>
              checkId(id, posOpt)
              attributeUsages = attributeUsages +
                id ~> Info.AttributeUsage(owner = owner, id = id, scope = scope,
                  ast = ast(commonUsageElements = update(ast.commonUsageElements, id)))
            case x =>
              //reporter.warn(ast.posOpt, resolverKind, s"How to handle usages without identification")
          }
        case ast: SysmlAst.ConnectionUsage =>
          getId(ast.commonUsageElements.identification, ast.posOpt) match {
            case (Some(id), posOpt) =>
              checkId(id, posOpt)
              connectionUsages = connectionUsages +
                id ~> Info.ConnectionUsage(owner = owner, id = id, scope = scope,
                  ast = ast(commonUsageElements = update(ast.commonUsageElements, id)),
                  srcAst = None(), dstAst = None())
            case _ =>
              //reporter.warn(ast.posOpt, resolverKind, s"How to handle usages without identification")
          }
        case ast: SysmlAst.ItemUsage =>
          getId(ast.commonUsageElements.identification, ast.posOpt) match {
            case (Some(id), posOpt) =>
              checkId(id, posOpt)
              itemUsages = itemUsages +
                id ~> Info.ItemUsage(owner = owner, id = id, scope = scope,
                  ast = ast(commonUsageElements = update(ast.commonUsageElements, id)))
            case _ =>
              //reporter.warn(ast.posOpt, resolverKind, s"How to handle usages without identification")
          }
        case ast: SysmlAst.PartUsage =>
          getId(ast.commonUsageElements.identification, ast.posOpt) match {
            case (Some(id), posOpt) =>
              checkId(id, posOpt)
              partUsages = partUsages +
                id ~> Info.PartUsage(owner = owner, id = id, scope = scope,
                  ast = ast(commonUsageElements = update(ast.commonUsageElements, id)))
            case _ =>
              //reporter.warn(ast.posOpt, resolverKind, s"How to handle usages without identification")
          }

        case ast: SysmlAst.PortUsage =>
          getId(ast.commonUsageElements.identification, ast.posOpt) match {
            case (Some(id), posOpt) =>
              checkId(id, posOpt)
              portUsages = portUsages +
                id ~> Info.PortUsage(owner = owner, id = id, scope = scope,
                  ast = ast(commonUsageElements = update(ast.commonUsageElements, id)))
            case _ =>
              //reporter.warn(ast.posOpt, resolverKind, s"How to handle usages without identification")
          }

        case ast: SysmlAst.ReferenceUsage =>
          getId(ast.commonUsageElements.identification, ast.posOpt) match {
            case (Some(id), posOpt) =>
              checkId(id, posOpt)
              referenceUsages = referenceUsages +
                id ~> Info.ReferenceUsage(owner = owner, id = id, scope = scope,
                  ast = ast(commonUsageElements = update(ast.commonUsageElements, id)))
            case _ =>
              //reporter.warn(ast.posOpt, resolverKind, s"How to handle usages without identification")
          }
        case ast: SysmlAst.AnnotatingElement => // do nothing
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
    declareTypeH(F, entity, name, info, posOpt)
  }

  def declareTypeH(isEnumOrPackage: B, entity: String, name: ISZ[String], info: TypeInfo, posOpt: Option[Position]): Unit = {
    assert(name == info.name)

    globalNameMap.get(name) match {
      case Some(_) if !isEnumOrPackage =>
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
      case Some(_: Info.Package) => reporter.error(posOpt, resolverKind, "Cannot declare package because it has been previously declared")
      case Some(_) => reporter.error(posOpt, resolverKind, "Cannot declare package because the has has already been used for a non-package entity")
      case _ =>
        globalNameMap = globalNameMap + name ~> Info.Package(
          name, Some(SAST.Typed.Package(name)), Some(SAST.ResolvedInfo.Package(name)))
    }
  }

  def declarePackageName(p: SysmlAst.Package, parentPackages: ISZ[String]): Option[Info.Package] = {

    val (name, posOpt) = getId(p.identification, p.posOpt)

    if (name.isEmpty) {
      reporter.error(posOpt, resolverKind, "Packages must have full names")
    } else {

      val currName = parentPackages :+ name.get

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
          // TODO
          //reporter.warn(id.shortName.get.posOpt, resolverKind, "Short names are not currently supported")
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


