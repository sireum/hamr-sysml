// #Sireum
package org.sireum.hamr.sysml.symbol

import org.sireum._
import org.sireum.hamr.sysml.ast.{ResolvedInfo, Type, Typed}
import org.sireum.hamr.sysml.symbol.Resolver.{NameMap, TypeMap}
import org.sireum.hamr.sysml.{ast => SAST}
import org.sireum.message.Position

@datatype trait Scope {
  @pure def packageName: ISZ[String]

  @pure def outerOpt: Option[Scope]

  @pure def resolveName(globalNameMap: Resolver.NameMap, name: ISZ[String]): Option[Info]

  @pure def resolveType(globalTypeMap: Resolver.TypeMap, name: ISZ[String]): Option[TypeInfo]

  //@pure def resolveIndex(id: String): Option[SAST.Typed]

  //@pure def returnOpt: Option[SAST.Typed]

  //@pure def thisOpt: Option[SAST.Typed]
}

object Scope {

  object Local {
    @pure def create(outer: Scope): Local = {
      return Local(HashMap.empty, HashMap.empty, Some(outer))
    }
  }

  @datatype class Local(val nameMap: HashMap[String, Info],
                        val typeMap: HashMap[String, TypeInfo],
                        val outerOpt: Option[Scope]) extends Scope {

    @pure override def packageName: ISZ[String] = {
      outerOpt match {
        case Some(outer) => return outer.packageName
        case _ => return ISZ()
      }
    }

    @pure override def resolveName(globalNameMap: Resolver.NameMap,
                                   name: ISZ[String]): Option[Info] = {
      if (name.size == 1) {
        val infoOpt = nameMap.get(name(0))
        if (infoOpt.nonEmpty) {
          return infoOpt
        }
      }
      outerOpt match {
        case Some(scope) => return scope.resolveName(globalNameMap, name)
        case _ => return None()
      }
    }

    @pure override def resolveType(globalTypeMap: Resolver.TypeMap, name: ISZ[String]): Option[TypeInfo] = {
      if (name.size == z"1") {
        val typeInfoOpt = typeMap.get(name(0))
        if (typeInfoOpt.nonEmpty) {
          return typeInfoOpt
        }
      }
      outerOpt match {
        case Some(scope) => return scope.resolveType(globalTypeMap, name)
        case _ => return None()
      }
    }
  }

  @datatype class Global(val packageName: ISZ[String], val imports: ISZ[SAST.SysmlAst.Import], val enclosingName: ISZ[String])
    extends Scope {

    override def outerOpt: Option[Scope] = {
      return None()
    }

    override def resolveName(globalNameMap: Resolver.NameMap, name: ISZ[String]): Option[Info] = {
      return resolveNameMemoized(globalNameMap, name)
    }

    @memoize def resolveNameMemoized(@hidden globalNameMap: NameMap,
                                     name: ISZ[String]): Option[Info] = {
      val globalOpt = globalNameMap.get(name)
      if (globalOpt.nonEmpty) {
        return globalOpt
      }

      var en = enclosingName
      while (en.size >= packageName.size && en != packageName) {
        val enclosedOpt = globalNameMap.get(en ++ name)
        if (enclosedOpt.nonEmpty) {
          return enclosedOpt
        }
        en = ops.ISZOps(en).dropRight(1)
      }

      val importedOpt = resolveImported(globalNameMap, name)
      if (importedOpt.nonEmpty) {
        return importedOpt
      }

      return globalNameMap.get(packageName ++ name)
    }

    @pure def resolveImported(globalNameMap: Resolver.NameMap, name: ISZ[String]): Option[Info] = {
      for (i <- imports.size - 1 to 0 by -1) {
        val impor: SAST.SysmlAst.Import = imports(i)

        val contextName = SAST.Util.ids2string(impor.name.ids)

        if (impor.all) {
          halt("'all' imports are not currently supported")
        } else if (impor.starStar) {
          halt("'**' imports are not currently supported")
        } else if (impor.star) {
          val n = contextName ++ name
          val rOpt = globalNameMap.get(n)
          if (rOpt.nonEmpty) {
            return rOpt
          }
          val rGlobalOpt = globalNameMap.get(packageName ++ n)
          if (rGlobalOpt.nonEmpty) {
            return rGlobalOpt
          }
        } else {
          val name0 = name(0)
          val contextLast = contextName(contextName.size - 1)
          if (contextLast == name0) {
            val n = contextName ++ ops.ISZOps(name).drop(1)
            val rOpt = globalNameMap.get(packageName ++ n)
            if (rOpt.nonEmpty) {
              return rOpt
            }
            val rGlobalOpt = globalNameMap.get(n)
            if (rGlobalOpt.nonEmpty) {
              return rGlobalOpt
            }
          }
        }
      }
      return None()
    }

    override def resolveType(globalTypeMap: Resolver.TypeMap, name: ISZ[String]): Option[TypeInfo] = {
      return resolveTypeMemoized(globalTypeMap, name)
    }

    @memoize def resolveTypeMemoized(globalTypeMap: Resolver.TypeMap,
                                     name: ISZ[String]): Option[TypeInfo] = {
      val globalTypeOpt = globalTypeMap.get(name)
      if (globalTypeOpt.nonEmpty) {
        return globalTypeOpt
      }

      var en = enclosingName
      while (en.size >= packageName.size && en != packageName) {
        val enclosedTypeOpt = globalTypeMap.get(en ++ name)
        if (enclosedTypeOpt.nonEmpty) {
          return enclosedTypeOpt
        }
        en = ops.ISZOps(en).dropRight(1)
      }

      val importedTypeOpt = resolveImportedType(globalTypeMap, name)
      if (importedTypeOpt.nonEmpty) {
        return importedTypeOpt
      }

      return globalTypeMap.get(packageName ++ name)
    }

    @pure def resolveImportedType(globalTypeMap: TypeMap, name: ISZ[String]): Option[TypeInfo] = {
      for (i <- imports.size - 1 to 0 by -1) {
        val impor = imports(i)
        val contextName = SAST.Util.ids2string(impor.name.ids)

        if (impor.all) {
          halt("'all' imports are not currently supported")
        } else if (impor.starStar) {
          halt("'**' imports are not currently supported")
        } else if (impor.star) {
          val n = contextName ++ name
          val rOpt = globalTypeMap.get(packageName ++ n)
          if (rOpt.nonEmpty) {
            return rOpt
          }
          val rGlobalOpt = globalTypeMap.get(n)
          if (rGlobalOpt.nonEmpty) {
            return rGlobalOpt
          }
        } else {
          val name0 = name(0)
          val contextLast = contextName(contextName.size - 1)
          if (contextLast == name0) {
            val n = contextName ++ ops.ISZOps(name).drop(1)
            val rOpt = globalTypeMap.get(packageName ++ n)
            if (rOpt.nonEmpty) {
              return rOpt
            }
            val rGlobalOpt = globalTypeMap.get(n)
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
}

object Info {
  @datatype class Package (val name: ISZ[String], val typeOpt: Option[SAST.Typed], val resOpt: Option[SAST.ResolvedInfo]) extends Info {
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
    @pure override def name: _root_.org.sireum.ISZ[String] = {
      return owner :+ id
    }
  }

  @datatype class AttributeDefinition(val owner: ISZ[String],
                                      val isInPackage: B,
                                      val scope: Scope,
                                      val ast: SAST.SysmlAst.AttributeDefinition)

  @datatype trait UsageInfo extends Info {
    def owner: ISZ[String]
    def id: String
  }

  @datatype class AttributeUsage(val owner: ISZ[String],
                                 val id: String,
                                 val scope: Scope,
                                 val ast: SAST.SysmlAst.AttributeUsage) extends UsageInfo {
    @strictpure def posOpt: Option[Position] = ast.attr.posOpt

    @strictpure def name: ISZ[String] = {
      return owner :+ id
    }
  }

  @datatype class ItemUsage(val owner: ISZ[String],
                            val id: String,
                            val scope: Scope,
                            val ast: SAST.SysmlAst.ItemUsage) extends UsageInfo {
    @strictpure def posOpt: Option[Position] = ast.attr.posOpt

    @strictpure def name: ISZ[String] = {
      return owner :+ id
    }
  }

  @datatype class PartUsage(val owner: ISZ[String],
                            val id: String,
                            val scope: Scope,
                            val ast: SAST.SysmlAst.PartUsage) extends UsageInfo {
    @strictpure def posOpt: Option[Position] = ast.attr.posOpt

    @strictpure def name: ISZ[String] = {
      return owner :+ id
    }
  }

  @datatype class PortUsage(val owner: ISZ[String],
                            val id: String,
                            val scope: Scope,
                            val ast: SAST.SysmlAst.PortUsage) extends UsageInfo {
    @strictpure def posOpt: Option[Position] = ast.attr.posOpt

    @strictpure def name: ISZ[String] = {
      return owner :+ id
    }
  }

  @datatype class ConnectionUsage(val owner: ISZ[String],
                                  val id: String,
                                  val scope: Scope,
                                  val ast: SAST.SysmlAst.ConnectionUsage) extends UsageInfo {
    @strictpure def posOpt: Option[Position] = ast.attr.posOpt

    @strictpure def name: ISZ[String] = {
      return owner :+ id
    }
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
  @datatype class EnumDefinition(val owner: ISZ[String],
                                 val elements: Map[String, SAST.ResolvedInfo],
                                 val posOpt: Option[Position]) extends TypeInfo {

    val typedOpt: Option[SAST.Typed] = Some(SAST.Typed.Name(name))

    @pure override def name: ISZ[String] = {
      return owner// :+ Info.EnumDefinition.elementTypeSuffix
    }

    @strictpure override def tpe: SAST.Typed = typedOpt.get
  }

  @datatype class PartDefinition(val owner: ISZ[String],
                                 val id: String,
                                 val outlined: B,
                                 val typeChecked: B,
                                 val ancestors: ISZ[SAST.Typed.Name],
                                 val members: TypeInfo.Members,
                                 val scope: Scope.Global,
                                 val ast: SAST.SysmlAst.PartDefinition) extends DelineableTypeInfo {

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
        r = r :+ SAST.Typed.Name(ids = SAST.Util.ids2string(p.name.ids))
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
                                 val ast: SAST.SysmlAst.PortDefinition) extends DelineableTypeInfo {

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
                                       val ast: SAST.SysmlAst.ConnectionDefinition) extends DelineableTypeInfo {

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
                                      val ast: SAST.SysmlAst.AttributeDefinition) extends DelineableTypeInfo {

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
                                       val ast: SAST.SysmlAst.AllocationDefinition) extends DelineableTypeInfo {

    val typedOpt: Option[SAST.Typed] = Some(SAST.Typed.Name(name))

    @pure override def name: ISZ[String] = {
      return owner :+ id
    }

    @pure override def posOpt: Option[Position] = {
      return ast.attr.posOpt
    }

    @strictpure override def tpe: Typed = typedOpt.get
  }

  @datatype class Members(val attributeUsages: HashSMap[String, Info.AttributeUsage],
                          val connectionUsages: HashSMap[String, Info.ConnectionUsage],
                          val itemUsages: HashSMap[String, Info.ItemUsage],
                          val partUsages: HashSMap[String, Info.PartUsage],
                          val portUsages: HashSMap[String, Info.PortUsage])
}