// #Sireum
package org.sireum.hamr.sysml.symbol

import org.sireum._
import org.sireum.hamr.ir.SysmlAst.Visibility
import org.sireum.hamr.ir.{ResolvedInfo, Type, Typed}
import org.sireum.hamr.sysml.stipe
import org.sireum.hamr.sysml.stipe.TypeHierarchy
import org.sireum.hamr.sysml.symbol.Resolver.{NameMap, TypeMap}
import org.sireum.hamr.{ir => SAST}
import org.sireum.message.Position

@datatype trait Scope {
  @pure def packageName: ISZ[String]

  @pure def outerOpt: Option[Scope]

  @pure def resolveName(th: TypeHierarchy, name: ISZ[String]): Option[Info]

  @pure def resolveType(th: TypeHierarchy, name: ISZ[String]): Option[TypeInfo]

  //@pure def resolveIndex(id: String): Option[SAST.Typed]

  //@pure def returnOpt: Option[SAST.Typed]

  @pure def thisOpt: Option[SAST.Typed]
}

object Scope {

  object Local {
    @pure def create(outer: Scope): Local = {
      return Local(HashMap.empty, HashMap.empty, None(), Some(outer))
    }
  }

  @datatype class Local(val nameMap: HashMap[String, Info],
                        val typeMap: HashMap[String, TypeInfo],
                        val localThisOpt: Option[SAST.Typed],
                        val outerOpt: Option[Scope]) extends Scope {

    @pure override def packageName: ISZ[String] = {
      outerOpt match {
        case Some(outer) => return outer.packageName
        case _ => return ISZ()
      }
    }

    @pure override def thisOpt: Option[SAST.Typed] = {
      localThisOpt match {
        case r@Some(_) => return r
        case _ =>
          outerOpt match {
            case Some(outer) => return outer.thisOpt
            case _ => return None()
          }
      }
    }

    @pure override def resolveName(th: stipe.TypeHierarchy,
                                   name: ISZ[String]): Option[Info] = {
      if (ops.ISZOps(name).contains("Dispatch_Protocol")) {
        assert(T)
      }
      if (name.size == 1) {
        val infoOpt = nameMap.get(name(0))
        if (infoOpt.nonEmpty) {
          return infoOpt
        }
      }
      outerOpt match {
        case Some(scope) => return scope.resolveName(th, name)
        case _ => return None()
      }
    }

    @pure override def resolveType(th: TypeHierarchy, name: ISZ[String]): Option[TypeInfo] = {
      if (name.size == z"1") {
        val typeInfoOpt = typeMap.get(name(0))
        if (typeInfoOpt.nonEmpty) {
          return typeInfoOpt
        }
      }
      outerOpt match {
        case Some(scope) => return scope.resolveType(th, name)
        case _ => return None()
      }
    }
  }

  @datatype class Global(val packageName: ISZ[String], val imports: ISZ[SAST.SysmlAst.Import], val enclosingName: ISZ[String])
    extends Scope {

    override def outerOpt: Option[Scope] = {
      return None()
    }

    @pure override def thisOpt: Option[SAST.Typed] = {
      return None()
    }

    override def resolveName(th: TypeHierarchy, name: ISZ[String]): Option[Info] = {
      return resolveNameMemoized(th, name)
    }

    @memoize def resolveNameMemoized(@hidden th: TypeHierarchy,
                                     name: ISZ[String]): Option[Info] = {
      val globalOpt = th.nameMap.get(name)
      if (globalOpt.nonEmpty) {
        return globalOpt
      }

      var en = enclosingName
      while (en.size >= packageName.size && en != packageName) {
        val enclosedOpt = th.nameMap.get(en ++ name)
        if (enclosedOpt.nonEmpty) {
          return enclosedOpt
        }
        en = ops.ISZOps(en).dropRight(1)
      }

      val importedOpt = resolveImported(th, name)
      if (importedOpt.nonEmpty) {
        return importedOpt
      }

      return th.nameMap.get(packageName ++ name)
    }

    @pure def processNameImports(th: TypeHierarchy): ISZ[SAST.SysmlAst.Import] = {
      var allImports: ISZ[SAST.SysmlAst.Import] = ISZ()

      def h(currImports: ISZ[SAST.SysmlAst.Import]): Unit = {
        for (i <- currImports.size - 1 to 0 by -1) {
          val impor = currImports(i)
          allImports = allImports :+ impor
          th.nameMap.get(Util.ids2string(impor.name.ids)) match {
            case Some(p: Info.Package) =>
              val packagePubImports = p.imports.filter(f => f.visibility == Visibility.Public)
              h(packagePubImports)
            case _ =>
          }
        }
      }

      h(imports)
      return allImports
    }

    @pure def resolveImported(th: TypeHierarchy, name: ISZ[String]): Option[Info] = {

      val allImports = processNameImports(th)

      for (i <- allImports.size - 1 to 0 by -1) {
        val impor: SAST.SysmlAst.Import = allImports(i)

        val contextName = Util.ids2string(impor.name.ids)

        if (impor.all) {
          halt("'all' imports are not currently supported")
        } else if (impor.starStar) {
          halt("'**' imports are not currently supported")
        } else if (impor.star) {
          val n = contextName ++ name

          val rOpt = th.nameMap.get(n)
          if (rOpt.nonEmpty) {
            return rOpt
          }
          val rGlobalOpt = th.nameMap.get(packageName ++ n)
          if (rGlobalOpt.nonEmpty) {
            return rGlobalOpt
          }
        } else {
          val name0 = name(0)
          val contextLast = contextName(contextName.size - 1)
          if (contextLast == name0) {
            val n = contextName ++ ops.ISZOps(name).drop(1)
            val rOpt = th.nameMap.get(packageName ++ n)
            if (rOpt.nonEmpty) {
              return rOpt
            }
            val rGlobalOpt = th.nameMap.get(n)
            if (rGlobalOpt.nonEmpty) {
              return rGlobalOpt
            }
          }
        }
      }
      return None()
    }

    override def resolveType(th: TypeHierarchy, name: ISZ[String]): Option[TypeInfo] = {
      return resolveTypeMemoized(th, name)
    }

    @memoize def resolveTypeMemoized(th: TypeHierarchy,
                                     name: ISZ[String]): Option[TypeInfo] = {
      val globalTypeOpt = th.typeMap.get(name)
      if (globalTypeOpt.nonEmpty) {
        return globalTypeOpt
      }

      var en = enclosingName
      while (en.size >= packageName.size && en != packageName) {
        val enclosedTypeOpt = th.typeMap.get(en ++ name)
        if (enclosedTypeOpt.nonEmpty) {
          return enclosedTypeOpt
        }
        en = ops.ISZOps(en).dropRight(1)
      }

      val importedTypeOpt = resolveImportedType(th, name)
      if (importedTypeOpt.nonEmpty) {
        return importedTypeOpt
      }

      return th.typeMap.get(packageName ++ name)
    }

    @pure def resolveImportedType(th: TypeHierarchy, name: ISZ[String]): Option[TypeInfo] = {
      val allImports = processNameImports(th)

      for (i <- allImports.size - 1 to 0 by -1) {
        val impor = allImports(i)
        val contextName = Util.ids2string(impor.name.ids)

        if (impor.all) {
          halt("'all' imports are not currently supported")
        } else if (impor.starStar) {
          halt("'**' imports are not currently supported")
        } else if (impor.star) {
          val n = contextName ++ name
          val rOpt = th.typeMap.get(packageName ++ n)
          if (rOpt.nonEmpty) {
            return rOpt
          }
          val rGlobalOpt = th.typeMap.get(n)
          if (rGlobalOpt.nonEmpty) {
            return rGlobalOpt
          }
        } else {
          val name0 = name(0)
          val contextLast = contextName(contextName.size - 1)
          if (contextLast == name0) {
            val n = contextName ++ ops.ISZOps(name).drop(1)
            val rOpt = th.typeMap.get(packageName ++ n)
            if (rOpt.nonEmpty) {
              return rOpt
            }
            val rGlobalOpt = th.typeMap.get(n)
            if (rGlobalOpt.nonEmpty) {
              return rGlobalOpt
            }

          }
        }
      }
      return None()
    }
  }
}

@datatype trait Info {
  @pure def name: ISZ[String]

  @pure def posOpt: Option[Position]

  def typedOpt: Option[SAST.Typed]

  def resOpt: Option[SAST.ResolvedInfo]
}

object Info {
  @datatype class Package (val name: ISZ[String],
                           val imports: ISZ[SAST.SysmlAst.Import],
                           val typedOpt: Option[SAST.Typed],
                           val resOpt: Option[SAST.ResolvedInfo]) extends Info {
    @pure override def posOpt: Option[Position]= {
      return None()
    }
  }

  object EnumDefinition {
    //val elementTypeSuffix: String = "Type"
  }

  @datatype class EnumDefinition(val name: ISZ[String],
                                 val elements: Map[String, SAST.ResolvedInfo],
                                 val typedOpt: Option[SAST.Typed],
                                 val resOpt: Option[SAST.ResolvedInfo],
                                 val elementTypedOpt: Option[SAST.Typed],
                                 val posOpt: Option[Position]) extends Info

  @datatype class EnumElement(val owner: ISZ[String],
                              val id: String,
                              val typedOpt: Option[Typed],
                              val resOpt: Some[ResolvedInfo],
                              val posOpt: Option[Position]) extends Info {
    @pure override def name: ISZ[String] = {
      return owner :+ id
    }
  }

  @datatype class AttributeDefinition(val owner: ISZ[String],
                                      val name: ISZ[String],
                                      val isInPackage: B,
                                      val typedOpt: Option[SAST.Typed],
                                      val resOpt: Option[SAST.ResolvedInfo],
                                      val scope: Scope,
                                      val ast: SAST.SysmlAst.AttributeDefinition) extends Info {

    @strictpure override def posOpt: Option[Position] = ast.posOpt

  }


  @datatype trait UsageInfo extends Info {
    def owner: ISZ[String]
    def id: String
    def ast: SAST.SysmlAst.UsageElement

    @pure override def typedOpt: Option[Typed] = {
      return ast.commonUsageElements.attr.typedOpt
    }

    @pure def resOpt: Option[SAST.ResolvedInfo] = {
      return ast.commonUsageElements.attr.resOpt
    }
  }

  @datatype class AttributeUsage(val owner: ISZ[String],
                                 val id: String,
                                 val hasId: B,
                                 val scope: Scope,
                                 val ast: SAST.SysmlAst.AttributeUsage) extends UsageInfo {

    @strictpure def posOpt: Option[Position] = ast.commonUsageElements.attr.posOpt

    @pure def name: ISZ[String] = {
      return owner :+ id
    }
  }

  @datatype class ItemUsage(val owner: ISZ[String],
                            val id: String,
                            val scope: Scope,
                            val ast: SAST.SysmlAst.ItemUsage) extends UsageInfo {
    @strictpure override def typedOpt: Option[Typed] = ast.commonUsageElements.attr.typedOpt

    @strictpure def posOpt: Option[Position] = ast.commonUsageElements.attr.posOpt

    @pure def name: ISZ[String] = {
      return owner :+ id
    }

    //@pure def resOpt: Option[SAST.ResolvedInfo] = {
    //  return ast.commonUsageElements.attr.resOpt
    //}
  }

  @datatype class PartUsage(val owner: ISZ[String],
                            val id: String,
                            val scope: Scope,
                            val ast: SAST.SysmlAst.PartUsage) extends UsageInfo {
    @strictpure def posOpt: Option[Position] = ast.commonUsageElements.attr.posOpt

    @pure def name: ISZ[String] = {
      return owner :+ id
    }

    //@pure def resOpt: Option[SAST.ResolvedInfo] = {
    //  return ast.commonUsageElements.attr.resOpt
    //}
  }

  @datatype class PortUsage(val owner: ISZ[String],
                            val id: String,
                            val scope: Scope,
                            val ast: SAST.SysmlAst.PortUsage) extends UsageInfo {
    @strictpure def posOpt: Option[Position] = ast.commonUsageElements.attr.posOpt

    @pure def name: ISZ[String] = {
      return owner :+ id
    }

    //@pure def resOpt: Option[SAST.ResolvedInfo] = {
    //  return ast.commonUsageElements.attr.resOpt
    //}
  }

  @datatype class AllocationUsage(val owner: ISZ[String],
                                  val id: String,
                                  val scope: Scope,
                                  val ast: SAST.SysmlAst.AllocationUsage,

                                  val srcName: ISZ[String],
                                  val srcAst: Option[SAST.SysmlAst.PartUsage],

                                  val dstName: ISZ[String],
                                  val dstAst: Option[SAST.SysmlAst.PartUsage]) extends UsageInfo {
    @strictpure def posOpt: Option[Position] = ast.commonUsageElements.attr.posOpt

    @pure def name: ISZ[String] = {
      return owner :+ id
    }

    //@pure def resOpt: Option[SAST.ResolvedInfo] = {
    //  return ast.commonUsageElements.attr.resOpt
    //}
  }

  @datatype class ConnectionUsage(val owner: ISZ[String],
                                  val id: String,
                                  val scope: Scope,
                                  val ast: SAST.SysmlAst.ConnectionUsage,

                                  val srcAst: Option[SAST.SysmlAst.PortUsage],
                                  val dstAst: Option[SAST.SysmlAst.PortUsage]) extends UsageInfo {
    @strictpure def posOpt: Option[Position] = ast.commonUsageElements.attr.posOpt

    @pure def name: ISZ[String] = {
      return owner :+ id
    }

    //@pure def resOpt: Option[SAST.ResolvedInfo] = {
    //  return ast.commonUsageElements.attr.resOpt
    //}
  }

  @datatype class ReferenceUsage(val owner: ISZ[String],
                                 val id: String,
                                 val scope: Scope,
                                 val ast: SAST.SysmlAst.ReferenceUsage) extends UsageInfo {
    @strictpure def posOpt: Option[Position] = ast.commonUsageElements.attr.posOpt

    @pure def name: ISZ[String] = {
      return owner :+ id
    }

    //@pure def resOpt: Option[SAST.ResolvedInfo] = {
    //  return ast.commonUsageElements.attr.resOpt
    //}
  }
}

@datatype trait TypeInfo {

  @pure def name: ISZ[String]

  @pure def posOpt: Option[Position]

  @pure def tpe: Typed
}

@datatype trait DelineableTypeInfo extends TypeInfo {
  def outlined: B
}

object TypeInfo {

  @sig trait DefinitionTypeInfo extends DelineableTypeInfo {
    def members: TypeInfo.Members

    def typedOpt: Option[SAST.Typed]
  }

  @datatype class EnumDefinition(val owner: ISZ[String],
                                 val outlined: B,
                                 val ancestors: ISZ[SAST.Typed.Name],
                                 val elements: Map[String, SAST.ResolvedInfo],
                                 val posOpt: Option[Position]) extends TypeInfo {

    val typedOpt: Option[SAST.Typed] = Some(SAST.Typed.Name(name))

    @pure override def name: ISZ[String] = {
      return owner// :+ Info.EnumDefinition.elementTypeSuffix
    }

    @strictpure override def tpe: SAST.Typed = typedOpt.get
  }

  @datatype class Package(val id: ISZ[String],
                          val outlined: B,
                          val typeChecked: B,
                          val members: TypeInfo.Members,
                          val scope: Scope.Global,
                          val ast: SAST.SysmlAst.Package
                         ) extends DefinitionTypeInfo {

    val typedOpt: Option[SAST.Typed] = Some(SAST.Typed.Name(name))

    @strictpure override def tpe: Typed = typedOpt.get

    @pure override def name: ISZ[String] = {
      return id
    }

    @pure override def posOpt: Option[Position] = {
      return ast.attr.posOpt
    }
  }

  @datatype class PartDefinition(val owner: ISZ[String],
                                 val id: String,
                                 val outlined: B,
                                 val typeChecked: B,
                                 val ancestors: ISZ[SAST.Typed.Name],
                                 val members: TypeInfo.Members,
                                 val scope: Scope.Global,
                                 val ast: SAST.SysmlAst.PartDefinition) extends DefinitionTypeInfo {

    val typedOpt: Option[SAST.Typed] = Some(SAST.Typed.Name(name))

    @pure override def name: ISZ[String] = {
      return owner :+ id
    }

    @pure override def posOpt: Option[Position] = {
      return ast.attr.posOpt
    }

    @strictpure override def tpe: Typed = typedOpt.get

    @memoize def parents: ISZ[SAST.Typed.Name] = {
      var r = ISZ[SAST.Typed.Name]()
      for (p <- ast.parents) {
        r = r :+ SAST.Typed.Name(ids = Util.ids2string(p.name.ids))
      }
      return r
    }
  }

  @datatype class PortDefinition(val owner: ISZ[String],
                                 val id: String,
                                 val outlined: B,
                                 val typeChecked: B,
                                 val ancestors: ISZ[SAST.Typed.Name],
                                 val members: TypeInfo.Members,
                                 val scope: Scope.Global,
                                 val ast: SAST.SysmlAst.PortDefinition) extends DefinitionTypeInfo {

    val typedOpt: Option[SAST.Typed] = Some(SAST.Typed.Name(name))

    @pure override def name: ISZ[String] = {
      return owner :+ id
    }

    @pure override def posOpt: Option[Position] = {
      return ast.attr.posOpt
    }

    @strictpure override def tpe: Typed = typedOpt.get
  }

  @datatype class InterfaceDefinition(val owner: ISZ[String],
                                      val id: String,
                                      val outlined: B,
                                      val typeChecked: B,
                                      val ancestors: ISZ[SAST.Typed.Name],
                                      val members: TypeInfo.Members,
                                      val scope: Scope.Global,
                                      val ast: SAST.SysmlAst.InterfaceDefinition) extends DefinitionTypeInfo {

    val typedOpt: Option[SAST.Typed] = Some(SAST.Typed.Name(name))

    @pure override def name: ISZ[String] = {
      return owner :+ id
    }

    @pure override def posOpt: Option[Position] = {
      return ast.attr.posOpt
    }

    @strictpure override def tpe: Typed = typedOpt.get
  }

  @datatype class ConnectionDefinition(val owner: ISZ[String],
                                       val id: String,
                                       val outlined: B,
                                       val typeChecked: B,
                                       val ancestors: ISZ[SAST.Typed.Name],
                                       val members: TypeInfo.Members,
                                       val scope: Scope.Global,
                                       val ast: SAST.SysmlAst.ConnectionDefinition) extends DefinitionTypeInfo {

    val typedOpt: Option[SAST.Typed] = Some(SAST.Typed.Name(name))

    @pure override def name: ISZ[String] = {
      return owner :+ id
    }

    @pure override def posOpt: Option[Position] = {
      return ast.attr.posOpt
    }

    @strictpure override def tpe: Typed = typedOpt.get
  }

  @datatype class AttributeDefinition(val owner: ISZ[String],
                                      val id: String,
                                      val outlined: B,
                                      val typeChecked: B,
                                      val ancestors: ISZ[SAST.Typed.Name],
                                      val members: TypeInfo.Members,
                                      val scope: Scope.Global,
                                      val ast: SAST.SysmlAst.AttributeDefinition) extends DefinitionTypeInfo {

    val typedOpt: Option[SAST.Typed] = Some(SAST.Typed.Name(name))

    @pure override def name: ISZ[String] = {
      return owner :+ id
    }

    @pure override def posOpt: Option[Position] = {
      return ast.attr.posOpt
    }

    @strictpure override def tpe: Typed = typedOpt.get
  }

  @datatype class AllocationDefinition(val owner: ISZ[String],
                                       val id: String,
                                       val outlined: B,
                                       val typeChecked: B,
                                       val ancestors: ISZ[SAST.Typed.Name],
                                       val members: TypeInfo.Members,
                                       val scope: Scope.Global,
                                       val ast: SAST.SysmlAst.AllocationDefinition) extends DefinitionTypeInfo {

    val typedOpt: Option[SAST.Typed] = Some(SAST.Typed.Name(name))

    @pure override def name: ISZ[String] = {
      return owner :+ id
    }

    @pure override def posOpt: Option[Position] = {
      return ast.attr.posOpt
    }

    @strictpure override def tpe: Typed = typedOpt.get
  }

  object Members {
    @strictpure def empty: Members = Members(HashSMap.empty, HashSMap.empty, ISZ(), HashSMap.empty, HashSMap.empty, HashSMap.empty, HashSMap.empty, HashSMap.empty)
  }

  @datatype class Members(val allocationUsages: HashSMap[String, Info.AllocationUsage],
                          val attributeUsages: HashSMap[String, Info.AttributeUsage],
                          val attributeUsagesIdLess: ISZ[Info.AttributeUsage],
                          val connectionUsages: HashSMap[String, Info.ConnectionUsage],
                          val itemUsages: HashSMap[String, Info.ItemUsage],
                          val partUsages: HashSMap[String, Info.PartUsage],
                          val portUsages: HashSMap[String, Info.PortUsage],
                          val referenceUsages: HashSMap[String, Info.ReferenceUsage]) {
    @strictpure def isEmpty: B =
      allocationUsages.isEmpty &&
        attributeUsages.isEmpty &&
        attributeUsagesIdLess.isEmpty &&
        connectionUsages.isEmpty &&
        itemUsages.isEmpty &&
        partUsages.isEmpty &&
        portUsages.isEmpty &&
        referenceUsages.isEmpty

    @strictpure def nonEmpty: B = !isEmpty
  }
}