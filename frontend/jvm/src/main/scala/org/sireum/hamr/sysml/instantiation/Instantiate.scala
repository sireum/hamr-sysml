// #Sireum
package org.sireum.hamr.sysml.instantiation

import org.sireum._
import org.sireum.hamr.ir
import org.sireum.hamr.ir.SysmlAst.GumboAnnotation
import org.sireum.hamr.ir.util.AadlUtil
import org.sireum.hamr.ir.{Aadl, GclLib, GclMethod, SysmlAst, Typed}
import org.sireum.hamr.sysml.stipe.{TypeChecker, TypeHierarchy}
import org.sireum.hamr.sysml.symbol.{Info, Scope, TypeInfo, Util}
import org.sireum.hamr.sysml.symbol.TypeInfo.PartDefinition
import org.sireum.message.{Position, Reporter}
import org.sireum.lang.{ast => AST}

object Instantiate {

  val instantiatorKey: String = "Declarative AIR Instantiator"

  def instantiate(topUnits: ISZ[SysmlAst.TopUnit], typeHierarchy: TypeHierarchy, reporter: Reporter): (Option[(TypeHierarchy, ISZ[Aadl])]) = {

    var dataComponents: Map[ISZ[String], ir.Component] = Map.empty

    def getDefinition(t: Typed): Option[TypeInfo.DefinitionTypeInfo] = {
      t match {
        case t: Typed.Name =>
          typeHierarchy.typeMap.get(t.ids) match {
            case Some(t: TypeInfo.PartDefinition) => return Some(t)
            case _ => return None()
          }
        case _ =>
          return None()
      }
    }

    def isAadlComponent(name: ISZ[String]): B = {
      return (
        isAadlSystem(name) ||
          isAadlProcesssor(name) ||
          isAadlProcesss(name) ||
          isAadlThread(name) ||
          isAadlData(name) ||
          isAadlAbstract(name))
    }

    def isAadlComponentOpt(opt: Option[Typed]): B = {
      opt match {
        case Some(t: Typed.Name) => return isAadlComponent(t.ids)
        case _ => return F
      }
    }

    def isAadlSystem(name: ISZ[String]): B = {
      return typeHierarchy.poset.ancestorsOf(name).contains(InstantiateUtil.AadlSystemName)
    }

    def isAadlSystemOpt(opt: Option[Typed]): B = {
      opt match {
        case Some(t: Typed.Name) => return isAadlSystem(t.ids)
        case _ => return F
      }
    }

    def isAadlSystemDefinition(pd: TypeInfo.PartDefinition): B = {
      return isAadlSystem(pd.name)
    }

    def isAadlProcesssor(name: ISZ[String]): B = {
      return typeHierarchy.poset.ancestorsOf(name).contains(InstantiateUtil.AadlProcessorName)
    }

    def isAadlProcessorOpt(opt: Option[Typed]): B = {
      opt match {
        case Some(t: Typed.Name) => return isAadlProcesssor(t.ids)
        case _ => return F
      }
    }

    def isAadlProcesss(name: ISZ[String]): B = {
      return typeHierarchy.poset.ancestorsOf(name).contains(InstantiateUtil.AadlProcessName)
    }

    def isAadlProcessOpt(opt: Option[Typed]): B = {
      opt match {
        case Some(t: Typed.Name) => return isAadlProcesss(t.ids)
        case _ => return F
      }
    }

    def isAadlThread(name: ISZ[String]): B = {
      return typeHierarchy.poset.ancestorsOf(name).contains(InstantiateUtil.AadlThreadName)
    }

    def isAadlThreadOpt(opt: Option[Typed]): B = {
      opt match {
        case Some(t: Typed.Name) => return isAadlThread(t.ids)
        case _ => return F
      }
    }

    def isAadlData(name: ISZ[String]): B = {
      return typeHierarchy.poset.ancestorsOf(name).contains(InstantiateUtil.AadlDataName)
    }

    def isAadlDataOpt(opt: Option[Typed]): B = {
      opt match {
        case Some(t: Typed.Name) => return isAadlData(t.ids)
        case _ => return F
      }
    }

    def isAadlAbstract(name: ISZ[String]): B = {
      return typeHierarchy.poset.ancestorsOf(name).contains(InstantiateUtil.AadlAbstractName)
    }

    def isAadlAbstractOpt(opt: Option[Typed]): B = {
      opt match {
        case Some(t: Typed.Name) => return isAadlAbstract(t.ids)
        case _ => return F
      }
    }

    def process(): Option[(TypeHierarchy, ISZ[Aadl])] = {

      val systemRoots: ISZ[TypeInfo.PartDefinition] = getSystemRoots()

      if (systemRoots.isEmpty) {
        reporter.warn(None(), instantiatorKey, "Could not find any system roots to instantiate")
        return (None())
      }

      val gumboLibraries: ISZ[ir.AnnexLib] = getGumboLibraries()

      var aadls: ISZ[ir.Aadl] = ISZ()
      for (sysRoot <- systemRoots) {
        val projRoot = getPath(sysRoot.posOpt)

        addDatatypes(projRoot.get.up)

        val rootIdName = st"${sysRoot.id}_Instance".render
        val system = instantiateComponent(sysRoot, F, ISZ(rootIdName), sysRoot.posOpt)
        aadls = aadls :+ ir.Aadl(
          components = ISZ(system),
          annexLib = gumboLibraries,
          dataComponents = dataComponents.values)
      }

      return Some((typeHierarchy, aadls))
    }

    def getSystemRoots(): ISZ[TypeInfo.PartDefinition] = {
      var systemRoots = ISZ[PartDefinition]()
      for (n <- typeHierarchy.typeMap.entries) {
        n._2 match {
          case pd: TypeInfo.PartDefinition if isAadlSystemDefinition(pd) =>
            systemRoots = systemRoots :+ pd
          case _ =>
        }
      }

      return systemRoots
    }

    def getGumboLibraries(): ISZ[ir.AnnexLib] = {
      var gumboLibraries: ISZ[ir.AnnexLib] = ISZ()
      for (t <- topUnits;
           te <- t.packageBodyElements) {
        te match {
          case p: SysmlAst.Package =>
            for(e <- p.packageElements) {
              e match {
                case GumboAnnotation(lib: GclLib) =>
                  Util.getId(id = p.identification, specializations = ISZ(),
                    posOpt = p.posOpt, toolKind = Instantiate.instantiatorKey, reporter = reporter) match {
                    case (Some(id), posOpt) =>

                      // FIXME: package level gumbo libraries should be resolved during type checking
                      var methods: ISZ[GclMethod] = ISZ()
                      for (m <- lib.methods) {
                        val scope = Scope.Global(ISZ(id), ISZ(), ISZ(id))
                        methods = methods :+ TypeChecker.resolveMethod(m, scope, typeHierarchy, reporter)
                      }

                      gumboLibraries = gumboLibraries :+ lib (
                        containingPackage = ir.Name (ISZ (id), posOpt),
                        methods = methods)
                    case _ =>
                      reporter.error(p.posOpt, Instantiate.instantiatorKey, "Could not resolve package name")
                  }
                case _ =>
              }
            }
        }
      }

      if (gumboLibraries.nonEmpty) {
        injectAadlBaseTypes()
      }

      return gumboLibraries
    }

    def getDirection(v: Option[SysmlAst.FeatureDirection.Type]): ir.Direction.Type = {
      v match {
        case Some(SysmlAst.FeatureDirection.In) => return ir.Direction.In
        case Some(SysmlAst.FeatureDirection.Out) => return ir.Direction.Out
        case Some(SysmlAst.FeatureDirection.InOut) => return ir.Direction.InOut
        case _ =>
          halt("Infeasible: type checking should have insured a direction was provided")
      }
    }

    def instantiateComponent(p: TypeInfo.DefinitionTypeInfo, processingDatatype: B, idPath: ISZ[String], posOpt: Option[Position]): ir.Component = {
      assert(isAadlComponent(p.name))

      val category: ir.ComponentCategory.Type = {
        if (isAadlSystemOpt(p.typedOpt)) ir.ComponentCategory.System
        else if (isAadlProcessorOpt(p.typedOpt)) ir.ComponentCategory.Processor
        else if (isAadlProcessOpt(p.typedOpt)) ir.ComponentCategory.Process
        else if (isAadlThreadOpt(p.typedOpt)) ir.ComponentCategory.Thread
        else if (isAadlDataOpt(p.typedOpt)) ir.ComponentCategory.Data
        else if (isAadlAbstractOpt(p.typedOpt)) ir.ComponentCategory.Abstract
        else ir.ComponentCategory.Subprogram
      }
      if (category == ir.ComponentCategory.Subprogram) {
        reporter.error(p.posOpt, Instantiate.instantiatorKey,
          s"Only expecting system, processor, process, thread, data or abstract components: ${p}")
      }
      if (p.members.itemUsages.nonEmpty) {
        reporter.error(p.posOpt, Instantiate.instantiatorKey, s"Currently not expecting item usages at the AADL process level")
      }
      for (m <- p.members.referenceUsages.values) {
        reporter.error(m.posOpt, Instantiate.instantiatorKey, s"Currently not expecting reference usages at the AADL process level")
      }

      val componentName = st"${(p.name, "::")}".render

      val members: ISZ[Info.UsageInfo] = {
        var ret: ISZ[Info.UsageInfo] = ISZ()
        if (processingDatatype) {
          for (p <- p.members.partUsages.values if isDatatype(p.typedOpt.get)) {
            ret = ret :+ p
          }
          for (p <- p.members.attributeUsages.values if isDatatype(p.typedOpt.get)) {
            ret = ret :+ p
          }
        } else {
          ret = p.members.partUsages.values.asInstanceOf[ISZ[Info.UsageInfo]]
        }
        ret
      }

      var subcomponents: ISZ[ir.Component] = ISZ()
      for (member <- members) {
        if (isAadlComponentOpt(member.typedOpt) ) {
          getDefinition(member.typedOpt.get) match {
            case Some(x) =>
              if (!processingDatatype || isDatatype(member.typedOpt.get)) {
                subcomponents = subcomponents :+ instantiateComponent(
                  x, processingDatatype, idPath :+ member.id, member.posOpt)
              }

              if (processingDatatype) {
                processDatatype(getDefinition(member.typedOpt.get).get.name, idPath :+ member.id)
              }
            case _ =>
              getEnumerationDefinition(member.typedOpt.get) match {
                case Some(e) =>
                  subcomponents = subcomponents :+ processEnum (idPath :+ member.id, e)
                case _ =>
              }
          }

        } else {
          reporter.error(member.posOpt, Instantiate.instantiatorKey,
            st"Part usages of $category must be a descendant of one of the following: ${(AadlUtil.validSubcomponents.get(category).get, ", ")}".render)
        }
      }

      def getPayloadType(portName: String, optPosOpt: Option[Position], definitionBodyItems: ISZ[SysmlAst.DefinitionBodyItem]): (Option[ir.Classifier], Option[SysmlAst.FeatureDirection.Type]) = {
        if (definitionBodyItems.size != 1) {
          reporter.error(optPosOpt , Instantiate.instantiatorKey, "Currently expecting a single body item for data ports that refines 'type'")
          return (None(), None())
        }

        for(b <- definitionBodyItems) {
          b match {
            case r: SysmlAst.ReferenceUsage =>
              r.commonUsageElements.attr.typedOpt match {
                case Some(n: Typed.Name) =>

                  if (isValidDatatype(n.ids)) {
                    processDatatype(n.ids, ISZ(portName))
                    return (Some(ir.Classifier(st"${(n.ids, "::")}".render)), r.prefix.direction)
                  } else {
                    reporter.error(posOpt = optPosOpt, kind = Instantiate.instantiatorKey, message =
                    st"Data port type must be an enum or a descendant of ${(InstantiateUtil.AadlDataName, "::")}".render)
                  }
                case _ =>
                  reporter.warn(r.posOpt, Instantiate.instantiatorKey,
                    s"The payload type for ${componentName}'s $portName data port was not resolved")
                  return (None(), None())
              }
            case x =>
              reporter.error(posOpt, Instantiate.instantiatorKey, s"Currently only expecting reference usages in port bodies")
          }
        }
        return (None(), None())
      }

      var features: HashSMap[ISZ[String], ir.FeatureEnd] = HashSMap.empty
      for (portUsage <- p.members.portUsages.entries) {
        val portName = idPath :+ portUsage._1
        portUsage._2.typedOpt match {
          case Some(t: Typed.Name) =>
            t.ids match {
              case ISZ("AADL", "DataPort") =>
                val usageDir = getDirection(portUsage._2.ast.occurrenceUsagePrefix.refPrefix.direction)
                val (pType, refDir) = getPayloadType(portUsage._1, portUsage._2.posOpt, portUsage._2.ast.commonUsageElements.definitionBodyItems)
                if (refDir.nonEmpty && usageDir != getDirection(refDir)) {
                  reporter.error(portUsage._2.posOpt, Instantiate.instantiatorKey, "Port usage direction and the direction of the body reference usage must be the same")
                }
                features = features + portName ~> ir.FeatureEnd(
                  identifier = ir.Name(portName, portUsage._2.posOpt),
                  direction = usageDir,
                  category = ir.FeatureCategory.DataPort,
                  classifier = pType,
                  properties = ISZ(),
                  uriFrag = "")
              case ISZ("AADL", "EventDataPort") =>
                val usageDir = getDirection(portUsage._2.ast.occurrenceUsagePrefix.refPrefix.direction)
                val (pType, refDir) = getPayloadType(portUsage._1, portUsage._2.posOpt, portUsage._2.ast.commonUsageElements.definitionBodyItems)
                if (refDir.nonEmpty && usageDir != getDirection(refDir)) {
                  reporter.error(portUsage._2.posOpt, Instantiate.instantiatorKey, "Port usage direction and the direction of the body reference usage must be the same")
                }
                features = features + portName ~> ir.FeatureEnd(
                  identifier = ir.Name(portName, portUsage._2.posOpt),
                  direction = usageDir,
                  category = ir.FeatureCategory.EventDataPort,
                  classifier = pType,
                  properties = ISZ(),
                  uriFrag = "")
              case ISZ("AADL", "EventPort") =>
                features = features + portName ~> ir.FeatureEnd(
                  identifier = ir.Name(portName, portUsage._2.posOpt),
                  direction = getDirection(portUsage._2.ast.occurrenceUsagePrefix.refPrefix.direction),
                  category = ir.FeatureCategory.EventPort,
                  classifier = None(),
                  properties = ISZ(),
                  uriFrag = "")
              case x =>
                reporter.error(portUsage._2.posOpt, Instantiate.instantiatorKey, s"Unexpected port type $x p")
            }
          case x =>
            reporter.error(portUsage._2.posOpt, Instantiate.instantiatorKey,  s"Port usages should have been resolved to Typed.Name but found $x")
        }
      }

      var connections: ISZ[ir.Connection] = ISZ()
      for (f <- p.members.connectionUsages.entries) {
        connections = connections :+
          processConnection(
            idPath = idPath,
            connectionName = f._1,
            c = f._2,
            features = features,
            posOpt = f._2.posOpt)
      }

      val classifier: Option[ir.Classifier] = Some(ir.Classifier(st"${(p.name, "::")}".render))

      val (properties, annexes): (ISZ[ir.Property], ISZ[ir.Annex]) = p match {
        case t: TypeInfo.PartDefinition =>

          val properties_ : ISZ[ir.Property] = processProperties(t)

          val annexes_ : ISZ[ir.Annex] = {
            var as: ISZ[ir.Annex] = ISZ()
            for (b <- t.ast.bodyItems) {
              b match {
                case SysmlAst.GumboAnnotation(gumbo: ir.GclSubclause) =>
                  as = as :+ ir.Annex("GUMBO", gumbo)
                  injectAadlBaseTypes()
                case _ =>
              }
            }
            as
          }
          (properties_, annexes_)
        case _ => (ISZ(), ISZ())
      }

      val id: ISZ[String] =
        if (processingDatatype && idPath.nonEmpty)
          ISZ(ops.ISZOps(idPath).last)
        else idPath

      return ir.Component(
        identifier = ir.Name(name = id, pos = posOpt),
        category = category,
        classifier = classifier,
        features = features.values.asInstanceOf[ISZ[ir.Feature]],
        subComponents = subcomponents,
        connections = connections,
        connectionInstances = ISZ(),
        properties = properties,
        flows = ISZ(),
        modes = ISZ(),
        annexes = annexes,
        uriFrag = "")
    }


    def processProperties(ti: TypeInfo.PartDefinition): ISZ[ir.Property] = {
      var props: ISZ[ir.Property] = ISZ()
      for (b <- ti.ast.bodyItems) {
        b match {
          case a: SysmlAst.AttributeUsage if a.commonUsageElements.featureValue.nonEmpty =>
            a.commonUsageElements.attr.typedOpt match {
              case Some(n: Typed.Name) =>
                typeHierarchy.typeMap.get(n.ids) match {
                  case Some(typed) =>
                    if (typeHierarchy.poset.isChildOf(ISZ("AADL", "Property"), typed.name)) {
                      val propertyValues: ISZ[ir.PropertyValue] = {
                        a.commonUsageElements.featureValue.get.exp match {
                          case sel: AST.Exp.Select =>
                            val s = sel.string
                            if (s == "Periodic" || s == "Supported_Dispatch_Protocols.Periodic") {
                              ISZ(ir.ValueProp("Periodic"))
                            } else if (s == "Sporadic" || s == "Supported_Dispatch_Protocols.Sporadic") {
                              ISZ(ir.ValueProp("Sporadic"))
                            } else {
                              halt(s"Unexpected property ${s}")
                            }
                          case f: AST.Exp.Invoke =>
                            val value = f.ident.id.value
                            val inPs: R =
                              if (value == "picosecond" || value == "ps") {
                                R(f.args(0).string).get
                              } else if(value == "nanosecond" || value == "ns") {
                                R(f.args(0).string).get * R("1.0E3").get
                              } else if(value == "microsecond" || value == "us") {
                                R(f.args(0).string).get * R("1.0E6").get
                              } else if(value == "millisecond" || value == "ms") {
                                R(f.args(0).string).get * R("1.0E9").get
                              } else  {
                                halt(s"Unexpected uif: $value")
                              }

                            ISZ(ir.UnitProp(value = inPs.string, unit = Some("ps")))
                          case x =>
                            halt ("Unexpected expresion: $x")
                        }
                      }
                      props = props :+ ir.Property(
                        name = ir.Name(name = ISZ(st"${(typed.name, "::")}".render), pos = b.posOpt),
                        propertyValues = propertyValues,
                        appliesTo = ISZ())
                    }
                  case _ =>
                }
              case x =>
            }
          case _ =>
        }
      }

      return props
    }

    def processConnection(idPath: ISZ[String],
                          connectionName: String,
                          c: Info.ConnectionUsage,
                          features: HashSMap[ISZ[String], ir.FeatureEnd],
                          posOpt: Option[Position]): ir.Connection = {
      val ast = c.ast

      val (src, dst): (ir.EndPoint, ir.EndPoint) = ast.connectorPart match {
        case Some(b: SysmlAst.BinaryConnectorPart) =>

          var srcComponentName: ISZ[String] = idPath
          for(n <- ops.ISZOps(b.src.reference).dropRight(1)) {
            srcComponentName = srcComponentName ++ Util.ids2string(n.ids)
          }
          val srcFeatureName = srcComponentName ++ Util.ids2string(b.src.reference(b.src.reference.lastIndex).ids)
          val srcDirection = getDirection(c.srcAst.get.occurrenceUsagePrefix.refPrefix.direction)
          val src_ = ir.EndPoint(
            component = ir.Name(name = srcComponentName , pos = b.src.reference(0).posOpt),
            feature = Some(ir.Name(srcFeatureName, b.src.reference(0).posOpt)),
            direction = Some(srcDirection)
          )

          var dstComponentName: ISZ[String] = idPath
          for(n <- ops.ISZOps(b.dst.reference).dropRight(1)) {
            dstComponentName = dstComponentName ++ Util.ids2string(n.ids)
          }
          val dstFeatureName = dstComponentName ++ Util.ids2string(b.dst.reference(b.dst.reference.lastIndex).ids)

          val dstDirection = getDirection(c.dstAst.get.occurrenceUsagePrefix.refPrefix.direction)
          val dst_ = ir.EndPoint(
            component = ir.Name(name = dstComponentName, pos = b.dst.reference(0).posOpt),
            feature = Some(ir.Name(dstFeatureName, b.dst.reference(0).posOpt)),
            direction = Some(dstDirection)
          )

          (src_, dst_)
        case _ => halt("Infeasible: only binary connectors are allowed")
      }

      return ir.Connection(
        name = ir.Name(name = idPath :+ connectionName, pos = posOpt),
        src = ISZ(src),
        dst = ISZ(dst),
        kind = ir.ConnectionKind.Port,
        isBiDirectional = F,
        connectionInstances = ISZ(),
        properties = ISZ(),
        uriFrag = "")
    }

    def isValidDatatype(typeName: ISZ[String]): B = {
      return isDatatypeH(typeName) && typeName != InstantiateUtil.AadlDataName
    }

    def isDatatype(t: Typed): B = {
      t match {
        case t: Typed.Name => return isDatatypeH(t.ids)
        case _ => return F
      }
    }

    def isDatatypeH(name: ISZ[String]): B = {
      typeHierarchy.typeMap.get(name) match {
        case Some(p: TypeInfo.PartDefinition) => return isDescendantOf(InstantiateUtil.AadlDataName, p.name)
        case Some(e: TypeInfo.EnumDefinition) if !InstantiateUtil.isAadlAttribute(name) =>
          return T
        case _ => return F
      }
    }

    def getEnumerationDefinition(t: Typed): Option[TypeInfo.EnumDefinition] = {
      t match {
        case t: Typed.Name => return getEnumerationDefinitionH(t.ids)
        case _ => return None()
      }
    }

    def getEnumerationDefinitionH(name: ISZ[String]): Option[TypeInfo.EnumDefinition] = {
      typeHierarchy.typeMap.get(name) match {
        case Some(e: TypeInfo.EnumDefinition) => return Some(e)
        case _ => return None()
      }
    }

    def processEnum(name: ISZ[String], e: TypeInfo.EnumDefinition): ir.Component = {
      if (dataComponents.contains(name)) {
        return dataComponents.get(name).get
      }
      val properties: ISZ[ir.Property] = ISZ(
        ir.Property(
          name = ir.Name(ISZ("Data_Model::Data_Representation"), None()),
          propertyValues = ISZ(ir.ValueProp("Enum")),
          appliesTo = ISZ()),
        ir.Property(
          name = ir.Name(ISZ("Data_Model::Enumerators"), None()),
          propertyValues = for (e <- e.elements.keys) yield ir.ValueProp(e),
          appliesTo = ISZ())
      )

      val id: ISZ[String] =
        if (name.nonEmpty) ISZ(ops.ISZOps(name).last)
        else ISZ()

      return ir.Component(
        identifier = ir.Name(id, None()),
        category = ir.ComponentCategory.Data,
        classifier = Some(ir.Classifier(st"${(e.name, "::")}".render)),
        features = ISZ(),
        subComponents = ISZ(),
        connections = ISZ(),
        connectionInstances = ISZ(),
        properties = properties,
        flows = ISZ(),
        modes = ISZ(),
        annexes = ISZ(),
        uriFrag = "")
    }

    def addDatatypes(projectRoot: Os.Path): Unit = {
      for(e <- typeHierarchy.typeMap.entries) {
        e._2.tpe match {
          case t: Typed.Name =>
            val p = getPath(e._2.posOpt)
            if (isInProject(projectRoot, p)) {
              processDatatype(t.ids, ISZ())
            }
          case _ =>
        }
      }
      injectAadlBaseTypes()
    }

    def injectAadlBaseTypes(): Unit = {
      for (baseType <- InstantiateUtil.AadlBaseTypeNames) {
        processDatatype(baseType, ISZ())
      }
    }

    def processDatatype(typeName: ISZ[String], idPath: ISZ[String]): Unit = {
      if (dataComponents.contains(typeName) || !isDatatypeH(typeName)) {
        return
      }

      typeHierarchy.typeMap.get(typeName) match {
        case Some(p: TypeInfo.PartDefinition) =>
          dataComponents = dataComponents + typeName ~> instantiateComponent(p, T, idPath, p.posOpt)
        case Some(e: TypeInfo.EnumDefinition) =>
          dataComponents = dataComponents + typeName ~> processEnum(idPath, e)
        case _ =>
          halt(st"Infeasible: datatype ${(typeName, "::")} was not resolved".render)
      }
    }

    def isDescendantOf(a: ISZ[String], b: ISZ[String]): B = {
      return ops.ISZOps(typeHierarchy.poset.ancestorsOf(b).elements).contains(a)
    }

    def getPath(posOpt: Option[Position]): Option[Os.Path] = {
      posOpt match {
        case Some(p) =>
          p.uriOpt match {
            case Some(p) if ops.StringOps(p).startsWith("file://") =>
              // non mock library file
              return Some(Os.Path.fromUri(p))
            case _ => return None()
          }
        case _ => return None()
      }
    }

    def isInProject(projRoot: Os.Path, cand: Option[Os.Path]): B = {
      cand match {
        case Some(p) =>
          val pr = ops.StringOps(projRoot.toUri).split(c => c == '/')
          val c = ops.StringOps(p.toUri).split(c => c == '/')
          if (c.size >= pr.size) {
            for (i <- 0 until pr.size) {
              if (pr(i) != c(i)) {
                return F
              }
            }
            return T
          }
          return F
        case _ => return F
      }
    }
    return process()
  }

}
