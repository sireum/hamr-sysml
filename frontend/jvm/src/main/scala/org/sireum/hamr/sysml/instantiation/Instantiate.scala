// #Sireum
package org.sireum.hamr.sysml.instantiation

import org.sireum._
import org.sireum.hamr.ir
import org.sireum.hamr.ir.SysmlAst.{GumboAnnotation, OccurrenceBasicUsagePrefix, OccurrenceEndUsagePrefix, OccurrenceUsagePrefix}
import org.sireum.hamr.ir.util.AadlUtil
import org.sireum.hamr.ir._
import org.sireum.hamr.sysml.stipe.{TypeChecker, TypeHierarchy}
import org.sireum.hamr.sysml.symbol.{Info, Scope, TypeInfo, Util}
import org.sireum.lang.{ast => AST}
import org.sireum.message.{Position, Reporter}

object Instantiate {

  val instantiatorKey: String = "Declarative AIR Instantiator"

  def instantiate(topUnits: ISZ[SysmlAst.TopUnit], typeHierarchy: TypeHierarchy, reporter: Reporter): (Option[(TypeHierarchy, ISZ[(Aadl, Option[Position])])]) = {

    var actualProcessorBindings: HashMap[ISZ[String], ir.Property] = HashMap.empty

    var dataComponents: Map[ISZ[String], ir.Component] = Map.empty

    var hasGumbo: B = F

    def getDefinition(t: Typed): Option[TypeInfo.DefinitionTypeInfo] = {
      t match {
        case t: Typed.Name =>
          typeHierarchy.typeMap.get(t.ids) match {
            case Some(ti: TypeInfo.PartDefinition) => return Some(ti)
            case _ => return None()
          }
        case _ =>
          return None()
      }
    }

    def process(): Option[(TypeHierarchy, ISZ[(Aadl, Option[Position])])] = {

      val systemRoots: ISZ[TypeInfo.PartDefinition] = InstantiateUtil.getSystemRoots(typeHierarchy)

      if (systemRoots.isEmpty) {
        reporter.warn(None(), instantiatorKey, "Could not find any system roots to instantiate")
        return (None())
      }

      var aadls: ISZ[(ir.Aadl, Option[Position])] = ISZ()
      for (sysRoot <- systemRoots) {

        dataComponents = Map.empty
        actualProcessorBindings = HashMap.empty
        hasGumbo = F

        val projRoot = getPath(sysRoot.posOpt)

        val gumboLibraries: ISZ[ir.AnnexLib] = getGumboLibraries(projRoot.get.up)
        hasGumbo = gumboLibraries.nonEmpty

        //addDatatypes(projRoot.get.up)

        val rootIdName = st"${sysRoot.id}_Instance".render
        val system = instantiateComponent(sysRoot, F, ISZ(rootIdName), sysRoot.posOpt)

        if (hasGumbo) {
          injectAadlBaseTypes()
        }

        aadls = aadls :+ (ir.Aadl(
          components = ISZ(system),
          annexLib = gumboLibraries,
          dataComponents = dataComponents.values), sysRoot.posOpt)
      }

      return Some((typeHierarchy, aadls))
    }

    def getGumboLibraries(projRoot: Os.Path): ISZ[ir.AnnexLib] = {
      var gumboLibraries: ISZ[ir.AnnexLib] = ISZ()
      for (t <- topUnits;
           te <- t.packageBodyElements) {
        te match {
          case p: SysmlAst.Package =>
              for (e <- p.packageElements) {
                e match {
                  case GumboAnnotation(lib: GclLib) =>
                    Util.getId(idOpt = p.identification, specializations = ISZ(),
                      posOpt = p.posOpt, toolKind = Instantiate.instantiatorKey, reporter = reporter) match {
                      case (Some(id), posOpt) =>

                        // FIXME: package level gumbo libraries should be resolved during type checking
                        var methods: ISZ[GclMethod] = ISZ()
                        for (m <- lib.methods) {
                          val scope = Scope.Global(ISZ(id), ISZ(), ISZ(id))
                          methods = methods :+ TypeChecker.resolveMethod(m, scope, typeHierarchy, reporter)
                        }

                        gumboLibraries = gumboLibraries :+ lib(
                          containingPackage = ir.Name(ISZ(id), posOpt),
                          methods = methods)
                      case _ =>
                        reporter.error(p.posOpt, Instantiate.instantiatorKey, "Could not resolve package name")
                    }
                  case _ =>
                }
              }
        }
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

    def getDirectionFromUsage(usage: OccurrenceUsagePrefix): Direction.Type = {
      usage match {
        case b: OccurrenceBasicUsagePrefix => return getDirection(b.refPrefix.direction)
        case e: OccurrenceEndUsagePrefix => halt("Infeasible: a port cannot also be a cross feature")
      }
    }

    def instantiateComponent(p: TypeInfo.DefinitionTypeInfo, processingDatatype: B, idPath: ISZ[String], posOpt: Option[Position]): ir.Component = {
      assert(InstantiateUtil.isAadlComponent(p.name, typeHierarchy))

      val category: ir.ComponentCategory.Type = {
        if (InstantiateUtil.isAadlSystemOpt(p.typedOpt, typeHierarchy)) ir.ComponentCategory.System
        else if (InstantiateUtil.isAadlProcessorOpt(p.typedOpt, typeHierarchy)) ir.ComponentCategory.Processor
        else if (InstantiateUtil.isAadlProcessOpt(p.typedOpt, typeHierarchy)) ir.ComponentCategory.Process
        else if (InstantiateUtil.isAadlThreadOpt(p.typedOpt, typeHierarchy)) ir.ComponentCategory.Thread
        else if (InstantiateUtil.isAadlDataOpt(p.typedOpt, typeHierarchy)) ir.ComponentCategory.Data
        else if (InstantiateUtil.isAadlAbstractOpt(p.typedOpt, typeHierarchy)) ir.ComponentCategory.Abstract
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
          for (p <- p.members.partUsages.values ) {
            if (isDatatype(p.typedOpt.get)) {
              ret = ret :+ p
            } else if (!allowedDataComponentMembers(p)) {
              reporter.error(p.posOpt, Instantiate.instantiatorKey, s"Unexpected part usage for an AADL Data Component")
            }
          }
          for (p <- p.members.attributeUsages.values) {
            if (isDatatype(p.typedOpt.get)) {
              ret = ret :+ p
            } else if (!allowedDataComponentMembers(p)) {
              reporter.error(p.posOpt, Instantiate.instantiatorKey, s"Unexpected attribute usage for an AADL Data Component")
            }
          }
        } else {
          ret = p.members.partUsages.values.asInstanceOf[ISZ[Info.UsageInfo]]
        }
        ret
      }

      processActualProcessorBindings(idPath, p.members.allocationUsages)

      var subcomponents: ISZ[ir.Component] = ISZ()
      for (member <- members) {
        if (InstantiateUtil.isAadlComponentOpt(member.typedOpt, typeHierarchy)) {
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
                  subcomponents = subcomponents :+ processEnum(e, idPath :+ member.id)
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
          reporter.error(optPosOpt, Instantiate.instantiatorKey, "Currently expecting a single body item for data ports that refines 'type'")
          return (None(), None())
        }

        for (b <- definitionBodyItems) {
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
              reporter.error(optPosOpt, Instantiate.instantiatorKey, s"Currently only expecting reference usages in port bodies")
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
                val usageDir = getDirectionFromUsage(portUsage._2.ast.occurrenceUsagePrefix)
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
                val usageDir = getDirectionFromUsage(portUsage._2.ast.occurrenceUsagePrefix)
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
                  direction = getDirectionFromUsage(portUsage._2.ast.occurrenceUsagePrefix),
                  category = ir.FeatureCategory.EventPort,
                  classifier = None(),
                  properties = ISZ(),
                  uriFrag = "")
              case x =>
                reporter.error(portUsage._2.posOpt, Instantiate.instantiatorKey, s"Unexpected port type $x p")
            }
          case x =>
            reporter.error(portUsage._2.posOpt, Instantiate.instantiatorKey, s"Port usages should have been resolved to Typed.Name but found $x")
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

          val properties_ : ISZ[ir.Property] = processProperties(t) ++ getActualProcessorBindings(idPath)

          val annexes_ : ISZ[ir.Annex] = {
            var as: ISZ[ir.Annex] = ISZ()
            for (b <- t.ast.bodyItems) {
              b match {
                case SysmlAst.GumboAnnotation(gumbo: ir.GclSubclause) =>
                  hasGumbo = T
                  as = as :+ ir.Annex("GUMBO", gumbo)
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

    def getActualProcessorBindings(ids: ISZ[String]): ISZ[ir.Property] = {
      var ret: ISZ[Property] = ISZ()
      var currentPath = ISZ[String]()
      for (id <- ids) {
        currentPath = currentPath :+ id
        actualProcessorBindings.get(currentPath) match {
          case Some(p) => ret = ret :+ p
          case _ =>
        }
      }
      return ret
    }

    def processActualProcessorBindings(idPath: ISZ[String], allocationUsages: HashSMap[String, Info.AllocationUsage]): Unit = {
      for (au <- allocationUsages.entries) {
        au._2.ast.commonUsageElements.tipeOpt match {
          case Some(t: Type.Named) if (Util.ids2string(t.name.ids)) == ISZ("Deployment_Properties", "Actual_Processor_Binding") =>
            (au._2.srcAst, au._2.dstAst) match {
              case (Some(src), Some(dst)) =>
                val srcPath = idPath :+ au._2.srcName(au._2.srcName.lastIndex)
                val dstPath = idPath :+ au._2.dstName(au._2.dstName.lastIndex)
                val prop = ir.Property(
                  name = ir.Name(ISZ("Deployment_Properties::Actual_Processor_Binding"), au._2.posOpt),
                  propertyValues = ISZ(ir.ReferenceProp(value = ir.Name(dstPath, au._2.posOpt))),
                  appliesTo = ISZ())
                actualProcessorBindings = actualProcessorBindings + srcPath ~> prop
              case _ =>
                reporter.error(au._2.posOpt, Instantiate.instantiatorKey, "Did not resolve one of the part usages")
            }
            assert(T)
          case _ =>
        }
      }
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
                            } else if (s == "HAMR.Microkit_Languages.Rust") {
                              ISZ(ir.ValueProp("Rust"))
                            } else {
                              halt(s"Unexpected property ${s}")
                            }
                          case f: AST.Exp.Invoke =>
                            val value = f.ident.id.value
                            val inPs: R =
                              if (value == "picosecond" || value == "ps") {
                                R(f.args(0).string).get
                              } else if (value == "nanosecond" || value == "ns") {
                                R(f.args(0).string).get * R("1.0E3").get
                              } else if (value == "microsecond" || value == "us") {
                                R(f.args(0).string).get * R("1.0E6").get
                              } else if (value == "millisecond" || value == "ms") {
                                R(f.args(0).string).get * R("1.0E9").get
                              } else {
                                halt(s"Unexpected uif: $value")
                              }

                            ISZ(ir.UnitProp(value = inPs.string, unit = Some("ps")))
                          case l: AST.Exp.LitZ =>
                            ISZ(ir.UnitProp(value = l.value.string, unit = None()))
                          case x =>
                            val t = x
                            halt(s"Unexpected expression: $x")
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
          for (n <- ops.ISZOps(b.src.reference).dropRight(1)) {
            srcComponentName = srcComponentName ++ Util.ids2string(n.ids)
          }
          val srcFeatureName = srcComponentName ++ Util.ids2string(b.src.reference(b.src.reference.lastIndex).ids)
          val srcDirection = getDirectionFromUsage(c.srcAst.get.occurrenceUsagePrefix)
          val src_ = ir.EndPoint(
            component = ir.Name(name = srcComponentName, pos = b.src.reference(0).posOpt),
            feature = Some(ir.Name(srcFeatureName, b.src.reference(0).posOpt)),
            direction = Some(srcDirection)
          )

          var dstComponentName: ISZ[String] = idPath
          for (n <- ops.ISZOps(b.dst.reference).dropRight(1)) {
            dstComponentName = dstComponentName ++ Util.ids2string(n.ids)
          }
          val dstFeatureName = dstComponentName ++ Util.ids2string(b.dst.reference(b.dst.reference.lastIndex).ids)

          val dstDirection = getDirectionFromUsage(c.dstAst.get.occurrenceUsagePrefix)
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

    def processEnum(e: TypeInfo.EnumDefinition, idPath: ISZ[String]): ir.Component = {
      if (dataComponents.contains(e.name)) {
        return dataComponents.get(e.name).get
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
        if (idPath.nonEmpty) ISZ(ops.ISZOps(idPath).last)
        else ISZ()

      val enumComponent = ir.Component(
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

      dataComponents = dataComponents + e.name ~> enumComponent

      return enumComponent
    }

    def addDatatypes(projectRoot: Os.Path): Unit = {
      for (e <- typeHierarchy.typeMap.entries) {
        e._2.tpe match {
          case t: Typed.Name =>
            processDatatype(t.ids, ISZ())
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
          val v = instantiateComponent(p, T, idPath, p.posOpt)
          dataComponents = dataComponents + typeName ~> v
        case Some(e: TypeInfo.EnumDefinition) =>
          val v = processEnum(e, idPath)
          dataComponents = dataComponents + typeName ~> v
        case _ =>
          halt(st"Infeasible: datatype ${(typeName, "::")} was not resolved".render)
      }
    }

    def allowedDataComponentMembers(p: Info.UsageInfo): B = {
      p match {
        case i:Info.AttributeUsage =>
          return (i.owner == InstantiateUtil.AadlDataName ||
            i.owner == InstantiateUtil.AadlComponentName)
        case _ =>
          return F
      }
    }

    def isDescendantOf(a: ISZ[String], b: ISZ[String]): B = {
      return ops.ISZOps(typeHierarchy.poset.ancestorsOf(b).elements).contains(a)
    }

    def getPath(posOpt: Option[Position]): Option[Os.Path] = {
      posOpt match {
        case Some(p) =>
          p.uriOpt match {
            case Some(uri) if ops.StringOps(uri).startsWith("file://") =>
              // non mock library file
              return Some(Os.Path.fromUri(uri))
            case _ => return None()
          }
        case _ => return None()
      }
    }

    return process()
  }

}
