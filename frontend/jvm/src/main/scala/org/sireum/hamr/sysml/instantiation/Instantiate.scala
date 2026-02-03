// #Sireum
package org.sireum.hamr.sysml.instantiation

import org.sireum._
import org.sireum.hamr.ir
import org.sireum.hamr.ir.SysmlAst.{GumboAnnotation, OccurrenceBasicUsagePrefix, OccurrenceEndUsagePrefix, OccurrenceUsagePrefix}
import org.sireum.hamr.ir.util.AadlUtil
import org.sireum.hamr.ir._
import org.sireum.hamr.sysml.parser.UIF
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
        reportWarn(None(), "Could not find any system roots to instantiate", reporter)
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
                          val rmethod = TypeChecker.resolveMethod(m, scope, typeHierarchy, reporter)
                          methods = methods :+ rmethod

                          for (p <- rmethod.sig.params) {
                            val t1 =  p.tipe.typedOpt.get.asInstanceOf[AST.Typed.Name].ids
                            processDatatype(t1, ISZ()) match {
                              case Some(x) =>
                              case _ =>
                                reportError(p.id.attr.posOpt, s"Unable to resolve parameter's type: ${p.id.value}", reporter)
                            }
                          }

                          val t2 = rmethod.sig.returnType.typedOpt.get.asInstanceOf[AST.Typed.Name].ids
                          processDatatype(t2, ISZ()) match {
                            case Some(x) =>
                            case _ =>
                              reportError(m.posOpt, s"Unable to resolve method's return type", reporter)
                          }
                        }

                        gumboLibraries = gumboLibraries :+ lib(
                          containingPackage = ir.Name(ISZ(id), posOpt),
                          methods = methods)
                      case _ =>
                        reportError(p.posOpt, "Could not resolve package name", reporter)
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
        reportError(p.posOpt, s"Only expecting system, processor, process, thread, data or abstract components: ${p}", reporter)
      }
      if (p.members.itemUsages.nonEmpty) {
        reportError(p.posOpt, s"Currently not expecting item usages at the AADL process level", reporter)
      }
      for (m <- p.members.referenceUsages.values) {
        reportError(m.posOpt, s"Currently not expecting reference usages at the AADL process level", reporter)
      }

      val componentName = st"${(p.name, "::")}".render

      val isArray = InstantiateUtil.isAadlArray(p, typeHierarchy)

      val members: ISZ[Info.UsageInfo] = {
        var ret: ISZ[Info.UsageInfo] = ISZ()
        if (processingDatatype) {
          for (pmember <- p.members.partUsages.values ) {
            if (isArray) {
              pmember.id match {
                case "Base_Type" =>
                  processDatatype(getDefinition(pmember.typedOpt.get).get.name, idPath :+ pmember.id)
                  ret = ret :+ pmember
                case x =>
                  reportError(pmember.posOpt, s"Unexpected part usage for an AADL array", reporter)
              }
            }
            else if (isDatatype(pmember.typedOpt.get)) {
              ret = ret :+ pmember
            } else if (!allowedDataComponentMembers(p, pmember)) {
              reportError(pmember.posOpt, s"Unexpected part usage for an AADL Data Component", reporter)
            }
          }

          for (pmember <- p.members.attributeUsages.values) {
            if (isArray) {
              pmember.id match {
                case "Data_Size" => ret = ret :+ pmember
                case "Dimensions" => ret = ret :+ pmember
                case "Array_Size_Kind" =>  ret = ret :+ pmember
                case x =>
                  if (!allowedDataComponentMembers(p, pmember)) {
                    reportError(p.posOpt, s"Unexpected attribute usage for an AADL array", reporter)
                  }
              }
            }
            else if (isDatatype(pmember.typedOpt.get)) {
              ret = ret :+ pmember
            } else if (!allowedDataComponentMembers(p, pmember)) {
              reportError(p.posOpt, s"Unexpected attribute usage for an AADL Data Component", reporter)
            }
          }
        } else {
          ret = p.members.partUsages.values.asInstanceOf[ISZ[Info.UsageInfo]]
        }
        ret
      }

      assert (isArray -->: (members.size <= 4), members.size.string)
      processActualProcessorBindings(idPath, p.members.allocationUsages)

      var subcomponents: ISZ[ir.Component] = ISZ()
      if (!isArray) {
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
            reportError(member.posOpt,
              st"Part usages of $category must be a descendant of one of the following: ${(AadlUtil.validSubcomponents.get(category).get, ", ")}".render, reporter)
          }
        }
      }

      def getPayloadType(portName: String, optPosOpt: Option[Position], definitionBodyItems: ISZ[SysmlAst.DefinitionBodyItem]): (Option[ir.Classifier], Option[SysmlAst.FeatureDirection.Type]) = {
        if (definitionBodyItems.size != 1) {
          reportError(optPosOpt, "Currently expecting a single body item for data ports that refines 'type'", reporter)
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
                    reportError(optPosOpt,
                      st"Data port type must be an enum or a descendant of ${(InstantiateUtil.AadlDataName, "::")}".render, reporter)
                  }
                case _ =>
                  reportWarn(r.posOpt, s"The payload type for ${componentName}'s $portName data port was not resolved", reporter)
                  return (None(), None())
              }
            case x =>
              reportError(optPosOpt, s"Currently only expecting reference usages in port bodies", reporter)
          }
        }
        return (None(), None())
      }

      assert(isArray -->: p.members.portUsages.isEmpty)

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
                  reportError(portUsage._2.posOpt, "Port usage direction and the direction of the body reference usage must be the same", reporter)
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
                  reportError(portUsage._2.posOpt, "Port usage direction and the direction of the body reference usage must be the same", reporter)
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
                reportError(portUsage._2.posOpt, s"Unexpected port type $x p", reporter)
            }
          case x =>
            reportError(portUsage._2.posOpt, s"Port usages should have been resolved to Typed.Name but found $x", reporter)
        }
      }

      assert (isArray -->: p.members.connectionUsages.isEmpty)
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

      val (properties, annexes): (ISZ[ir.Property], ISZ[ir.Annex]) = {
        if (isArray) {
          def getUsage(id: String): Option[Info.UsageInfo] = {
            for (m <- members if m.id == id) {
              return Some(m)
            }
            return None()
          }
          def getFeatureValue(id: String): Option[SysmlAst.FeatureValue]= {
            getUsage(id) match {
              case Some(u) => return u.ast.commonUsageElements.featureValue
              case _ =>
            }
            return None()
          }

          var arrayProperties = ISZ[ir.Property](
            ir.Property(
              name = ir.Name(ISZ("Data_Model::Data_Representation"), None()),
              propertyValues = ISZ(ir.ValueProp("Array")),
              appliesTo = ISZ())
          )

          getUsage("Base_Type") match {
            case Some(n) =>
              // TODO: check if base type extends data
              n.typedOpt match {
                case Some(t: Typed.Name)=>
                  val baseTypeClassifier = st"${(t.ids, "::")}".render
                  arrayProperties = arrayProperties :+ ir.Property(
                    name = ir.Name(ISZ("Data_Model::Base_Type"), None()),
                    propertyValues = ISZ(ir.ClassifierProp(baseTypeClassifier)),
                    appliesTo = ISZ())
                case _ =>
                  halt("")
              }
            case _ =>
          }

          getFeatureValue("Dimensions") match {
            case Some(fv) =>
              val x = processValue(fv.exp)
              arrayProperties = arrayProperties :+ ir.Property(
                name = ir.Name(ISZ("Data_Model::Dimension"), None()),
                propertyValues = x,
                appliesTo = ISZ())
            case _ =>
          }
          getFeatureValue("Data_Size") match {
            case Some(fv) =>
              val x = processValue(fv.exp)
              arrayProperties = arrayProperties :+ ir.Property(
                name = ir.Name(ISZ("Memory_Properties::Data_Size"), None()),
                propertyValues = x,
                appliesTo = ISZ())
            case _ =>
          }
          getFeatureValue("Array_Size_Kind") match {
            case Some(fv) =>
              fv.exp match {
                case r: AST.Exp.Ref =>
                  arrayProperties = arrayProperties :+ ir.Property(
                    name = ir.Name(ISZ("HAMR::Array_Size_Kind"), None()),
                    propertyValues = ISZ(ir.ValueProp("Fixed")),
                    appliesTo = ISZ())
                case _ =>
                  halt("")
              }
            case _ =>
          }
          (arrayProperties, ISZ())
        } else {
          p match {
            case t: TypeInfo.PartDefinition =>

              val properties_ : ISZ[ir.Property] = processProperties(t) ++ getActualProcessorBindings(idPath)

              var bodyItems: ISZ[SysmlAst.DefinitionBodyItem] = t.ast.bodyItems
              for (ancestor <- t.ancestors) {
                typeHierarchy.typeMap.get(ancestor.ids) match {
                  case Some(parent: TypeInfo.PartDefinition) =>
                    bodyItems = bodyItems ++ parent.ast.bodyItems
                  case _ =>
                }
              }

              val annexes_ : ISZ[ir.Annex] = {
                var as: ISZ[ir.Annex] = ISZ()
                for (b <- bodyItems) {
                  b match {
                    case SysmlAst.GumboAnnotation(gumbo: ir.GclSubclause) =>
                      hasGumbo = T

                      for (m <- gumbo.methods) {
                        for (p <- m.sig.params) {
                          val t1 = p.tipe.typedOpt.get.asInstanceOf[AST.Typed.Name].ids
                          val d1 = processDatatype(t1, ISZ())
                          assert(d1.nonEmpty, s"Type $t1 not resolved for subclause method ${m.sig.id.value}'s ${p.id.value} param")
                        }

                        val t2 = m.sig.returnType.typedOpt.get.asInstanceOf[AST.Typed.Name].ids
                        val d2 = processDatatype(t2, ISZ())
                        assert(d2.nonEmpty, s"Return type $t2 not resolved for subclause method ${m.sig.id.value}")
                      }

                      for (s <- gumbo.state) {
                        val t3 = ops.StringOps(ops.StringOps(s.classifier).replaceAllLiterally("::", "!")).split(c => c == '!')
                        val d3 = processDatatype(t3, ISZ())
                        assert(d3.nonEmpty, s"Type ${t3} not resolved for state var ${s.name}")
                      }

                      as = as :+ ir.Annex("GUMBO", gumbo)
                    case _ =>
                  }
                }
                as
              }
              (properties_, annexes_)
            case _ => (ISZ(), ISZ())
          }
        }
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
                reportError(au._2.posOpt, "Did not resolve one of the part usages", reporter)
            }
            assert(T)
          case _ =>
        }
      }
    }

    @pure def processValue(exp: AST.Exp): ISZ[ir.PropertyValue] = {
      exp match {
        case sel: AST.Exp.Ref =>
          sel.resOpt match {
            case Some(v: AST.ResolvedInfo.EnumElement) =>
              return ISZ(ir.ValueProp(v.name))
            case Some(v: AST.ResolvedInfo.Var) =>
              typeHierarchy.nameMap.get(v.owner :+ v.id) match {
                case Some(i: Info.AttributeUsage) =>
                  i.ast.commonUsageElements.featureValue match {
                    case Some(fv) =>
                      return processValue(fv.exp)
                    case _ =>
                      reportError(exp.posOpt, "Attribute requires a feature value", reporter)
                      return ISZ(ir.ValueProp("INVALID"))
                  }
                case x =>
                  halt(s"Infeasible: attribute usages can only refer to other attribute usages but found $x")
              }
            case x =>
              halt(s"Unexpected select: $sel: $x")
          }
        case l: AST.Exp.LitZ => return ISZ(ir.UnitProp(l.value.string, None()))
        case f: AST.Exp.Invoke =>
          if (UIF.isUif(f.ident.id.value)) {
            f.ident.id.value match {
              case UIF.SysmlUnitExpression =>
                assert (f.args.size == 2, s"Expecting the base exp and its unit exp: ${f.args}")
                val baseExp = f.args(0)
                val unitExp = f.args(1)
                unitExp match {
                  case i: AST.Exp.Ref if i.resOpt.nonEmpty =>
                    i.resOpt.get match {
                      case v: AST.ResolvedInfo.Var =>

                        @pure def time(value: R) : ISZ[ir.PropertyValue] = {
                          return ISZ(ir.UnitProp(value = value.string, unit = Some("ps")))
                        }
                        @pure def size(value: R) : ISZ[ir.PropertyValue] = {
                          return ISZ(ir.UnitProp(value = value.string, unit = Some("bits")))
                        }

                        v.owner match {
                          case (ISZ("SI")) =>
                            if (v.id == "s" || v.id == "seconds") {
                              return time(R(baseExp.string).get * R("1.0E12").get)
                            } else if (v.id == "byte") {
                              return size(R(baseExp.string).get * r"8")
                            }
                            else {
                              halt(s"Need to handle $v")
                            }
                          case (ISZ("HAMR_Time_Units")) =>
                            if (v.id == "ps" || v.id == "picoseconds") {
                              return time(R(baseExp.string).get)
                            } else if (v.id == "ns" || v.id == "nanoseconds") {
                              return time(R(baseExp.string).get * R("1.0E3").get)
                            } else if (v.id == "us" || v.id == "microseconds") {
                              return time(R(baseExp.string).get * R("1.06").get)
                            } else if (v.id == "ms" || v.id == "milliseconds") {
                              return time(R(baseExp.string).get * R("1.0E9").get)
                            } else if (v.id == "s" || v.id == "seconds") {
                              return time(R(baseExp.string).get * R("1.0E12").get)
                            } else {
                              halt(s"Unexpected duration unit ${v.id}")
                            }
                          case x =>
                            reportError(unitExp.posOpt, st"Unexpected feature value: ${(v.owner, "::")}::${v.id}".render, reporter)
                            return ISZ()
                        }
                      case x =>
                        halt(s"Expected unit exp to resolve to a var: $x")
                    }
                  case x =>
                    reporter.printMessages()
                    halt(s"Expected a resolved Ref for the unit exp: $x ${unitExp.posOpt}")
                }
              case x =>
                reportError(f.posOpt, s"Wasn't expecting UIF: $x", reporter)
                return ISZ()
            }
          } else {
            reportError(f.posOpt, s"Unable function: ${f.ident.id.value}", reporter)
            return ISZ()
          }
        case _ =>
          halt(s"Unexpected expression: $exp")
      }
    }

    def processProperties(ti: TypeInfo.PartDefinition): ISZ[ir.Property] = {
      var props: ISZ[ir.Property] = ISZ()

      for (m <- ti.members.attributeUsages.values) {
        m.ast.commonUsageElements.attr.typedOpt match {
          case Some(n: Typed.Name) =>
            typeHierarchy.typeMap.get(n.ids) match {
              case Some(typed) =>
                if (typeHierarchy.poset.isChildOf(ISZ("AADL", "Property"), typed.name) &&
                  m.ast.commonUsageElements.featureValue.nonEmpty &&
                  InstantiateUtil.isHandledProperty(typed.name)) {

                  val propertyValues: ISZ[ir.PropertyValue] = processValue(m.ast.commonUsageElements.featureValue.get.exp)

                  val v = ir.Property(
                    name = ir.Name(name = ISZ(st"${(typed.name, "::")}".render), pos = m.posOpt),
                    propertyValues = propertyValues,
                    appliesTo = ISZ())
                  props = props :+ v
                }
              case _ =>
            }
          case x =>
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

    def injectAadlBaseTypes(): Unit = {
      for (baseType <- InstantiateUtil.AadlBaseTypeNames) {
        processDatatype(baseType, ISZ())
      }
    }

    def processDatatype(typeName: ISZ[String], idPath: ISZ[String]): Option[ir.Component] = {
      if (dataComponents.contains(typeName)) {
        return dataComponents.get(typeName)
      }

      if (!isDatatypeH(typeName)) {
        return None()
      }

      typeHierarchy.typeMap.get(typeName) match {
        case Some(p: TypeInfo.PartDefinition) =>
          val v = instantiateComponent(p, T, idPath, p.posOpt)
          dataComponents = dataComponents + typeName ~> v
          return Some(v)
        case Some(e: TypeInfo.EnumDefinition) =>
          val v = processEnum(e, idPath)
          dataComponents = dataComponents + typeName ~> v
          return Some(v)
        case _ =>
          halt(st"Infeasible: datatype ${(typeName, "::")} was not resolved".render)
      }
    }

    def allowedDataComponentMembers(parent: TypeInfo.DefinitionTypeInfo, p: Info.UsageInfo): B = {
      assert(isDatatype(parent.tpe))

      p match {
        case i:Info.AttributeUsage =>
          if (i.owner == InstantiateUtil.AadlDataName ||
            i.owner == InstantiateUtil.AadlComponentName) {
            return T
          }
          halt("")
        case i =>

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

  def reportWarnCond(cond: B, posOpt: Option[Position], message: String, reporter: Reporter): Unit = {
    if (!cond) {
      reportWarn(posOpt, message, reporter)
    }
  }

  def reportWarn(posOpt: Option[Position], message: String, reporter: Reporter): Unit = {
    reporter.warn(posOpt, Instantiate.instantiatorKey, s"Instantiation Warning: $message")
  }

  def reportErrorCond(cond: B, posOpt: Option[Position], message: String, reporter: Reporter): Unit = {
    if (!cond) {
      reportError(posOpt, message, reporter)
    }
  }

  def reportError(posOpt: Option[Position], message: String, reporter: Reporter): Unit = {
    reporter.error(posOpt, Instantiate.instantiatorKey, s"Instantiation Error: $message")
  }

}
