package org.sireum.hamr.sysml

import org.sireum._
import org.sireum.message._
import org.antlr.v4.runtime.ParserRuleContext
import org.sireum.hamr.ir._
import org.sireum.hamr.sysml.SysmlAst._
import org.sireum.hamr.sysml.parser.SysMLv2Parser._
import org.sireum.hamr.sysml.parser.SysMLv2Parser
import org.sireum.message.{Position, Reporter}
import org.sireum.parser.SireumGrammarParser.kind

import java.util
import scala.collection.mutable
import scala.jdk.CollectionConverters._

object SysMLAstBuilder {

  def resolve(tree: ParserRuleContext, uriOpt: Option[String], isSysML: B, reporter: Reporter): Aadl = {
    if (isSysML) {
      assert (!reporter.hasError)
      val p = SysMLAstBuilder(uriOpt)
        p.visitEntryRuleRootNamespace(tree.asInstanceOf[SysMLv2Parser.EntryRuleRootNamespaceContext])
      reporter.reports(p.reporter.messages)
      return SysMLUtil.emptyAadl
    } else {
      reporter.warn(None(), kind, "Need to handle kerml")
      return SysMLUtil.emptyAadl
    }
  }

  def isEmptyRelationshipBody(context: ParserRuleContext): B = {
    context match {
      case x: SysMLv2Parser.RuleRelationshipBody1Context =>
        assert (x.OP_SEMI().getChildCount >= 0) // sanity check
        return true
      case x =>
        return F
    }
  }
}

case class SysMLAstBuilder(uriOpt: Option[String]) {
  val reporter: Reporter = Reporter.create
  val kind: String = "SysMLResolver"

  def visitEntryRuleRootNamespace(ctx: SysMLv2Parser.EntryRuleRootNamespaceContext): Unit = {
    assert (ctx.getChildCount == 2, ctx.getChildCount)
    if (reporter.hasError) {
      return
    }

    for (packageBodyElement <- ctx.ruleRootNamespace().rulePackageBodyElement().asScala) {
      packageBodyElement match {
        case r1: RulePackageBodyElement1Context =>
          // rulePackageMember: ruleMemberPrefix (ruleDefinitionElement | ruleUsageElement);
          val m1: RulePackageMemberContext = r1.rulePackageMember()
          var visibility = visitVisibilityIndicator(m1.ruleMemberPrefix().ruleVisibilityIndicator())
          if (m1.ruleDefinitionElement() != null) {
            assert (m1.ruleUsageElement() == null)
            visitPackage(m1.ruleDefinitionElement())
          } else {
            assert(m1.ruleUsageElement() != null)
            halt("todo")
          }
        case r2:  RulePackageBodyElement2Context =>
          val m2 = r2.ruleElementFilterMember()
          reportError(m2, "Not currently supporting filter members")
        case r3: RulePackageBodyElement3Context =>
          val m3 = r3.ruleAliasMember()
          reportError(m3, "Not expecting aliasing to occur here")
        case r4: RulePackageBodyElement4Context =>
          val m4 = r4.ruleImport()
          reportError(m4, "Not expecting imports to occur here")
      }
    }
    return
  }

  def visitPackage(context: RuleDefinitionElementContext): Unit = {
    var isLibrary = F
    var isStandard = F
    val (prefixMetaDataMembers, packageDeclaration, packageBody): (mutable.Buffer[RulePrefixMetadataMemberContext], RulePackageDeclarationContext, RulePackageBodyContext) =
      context match {
      case d1: RuleDefinitionElement1Context =>
        (d1.rulePackage().rulePrefixMetadataMember().asScala, d1.rulePackage().rulePackageDeclaration(), d1.rulePackage().rulePackageBody())
      case d2: RuleDefinitionElement2Context =>
        isLibrary = T
        isStandard = d2.ruleLibraryPackage().K_STANDARD() != null
        (d2.ruleLibraryPackage.rulePrefixMetadataMember().asScala, d2.ruleLibraryPackage().rulePackageDeclaration(), d2.ruleLibraryPackage().rulePackageBody())
      case d3: RuleDefinitionElement3Context =>

        if (!SysmlAstUtil.isRegularComment(d3.ruleAnnotatingElement())) {
          visitAnnotatingElement(d3.ruleAnnotatingElement())
        }

        return
      case x =>
          reportError(x, s"Not currently handling the following package level node: $x")
          return
    }
    if (prefixMetaDataMembers.nonEmpty) {
      reportError(packageDeclaration, "Package metadata not currently supported")
    }

    var identification: Option[Identification] =
      if (isEmpty(packageDeclaration.ruleIdentification())) None()
      else Some(visitIdentification(packageDeclaration.ruleIdentification()))

    packageBody match {
      case b1: RulePackageBody1Context =>
        reportError(b1, "Empty package definitions are not currently supported")
      case b2: RulePackageBody2Context =>
        reportError(b2.ruleElementFilterMember().isEmpty, b2, "Package filter members are not currently supported")

        val imports: ISZ[Import] = visitImports(b2.ruleImport().asScala)
        val aliases: ISZ[Alias] = visitAliases(b2.ruleAliasMember().asScala)

        reportWarn(imports.isEmpty, b2.ruleImport(), s"Need to add imports to AST: $imports")
        reportWarn(aliases.isEmpty, b2.ruleAliasMember(), s"Need to add aliasing to AST: $aliases")

        var components: ISZ[Component] = ISZ()
        for (member <- b2.rulePackageMember().asScala) {
          val visibility = visitVisibilityIndicator(member.ruleMemberPrefix().ruleVisibilityIndicator())

          if (member.ruleUsageElement() != null) {
            reportWarn(member.ruleUsageElement(), "Why not just have user select the system part def they want to instantiate?")
          } else {
            components = components :+ visitPackageMember(member.ruleDefinitionElement())
          }
        }
    }
  }

  def visitAnnotatingElement(context: RuleAnnotatingElementContext): AnnotatingElement = {
    assert (!SysmlAstUtil.isRegularComment(context), "Filter out regular comments")
    context match {
      case i1: RuleAnnotatingElement1Context =>
        val comment = i1.ruleComment()

        val id: Option[Identification] =
        if (isEmpty(comment.ruleIdentification())) None()
          else Some(visitIdentification(comment.ruleIdentification()))

        var abouts: ISZ[QualifiedName] = ISZ()
        for (a <- comment.ruleAnnotation().asScala) {
          abouts = abouts :+ visitQualifiedName(a.ruleQualifiedName())
        }

        val locale: Option[String] =
          if (comment.K_LOCALE() == null) None()
          else Some(comment.RULE_STRING_VALUE().string)

        return Comment(
          id = id,
          abouts = abouts,
          locale = locale,
          comment = comment.RULE_REGULAR_COMMENT().string)

      case i2: RuleAnnotatingElement2Context =>
        val doc = i2.ruleDocumentation()

        val id: Option[Identification] =
          if (isEmpty(doc.ruleIdentification())) None()
          else Some(visitIdentification(doc.ruleIdentification()))

        val locale: Option[String] =
          if (doc.K_LOCALE() == null) None()
          else Some(doc.RULE_STRING_VALUE().string)

        return Documentation(
          id = id,
          locale = locale,
          comment = doc.RULE_REGULAR_COMMENT().string)

      case i3: RuleAnnotatingElement3Context =>
        val text = i3.ruleTextualRepresentation()

        val id: Option[Identification] =
          if (isEmpty(text.ruleIdentification())) None()
          else Some(visitIdentification(text.ruleIdentification()))

        return TextualRepresentation(
          id = id,
          language = text.RULE_STRING_VALUE().string,
          comment = text.RULE_REGULAR_COMMENT().string)

      case i4: RuleAnnotatingElement4Context =>
        val dummy = i4.ruleMetadataUsage()
        halt("Not yet handling metadata annotations")

      case x =>
        halt(s"Not expecting this annotating element $x")
    }
  }

  def visitPackageMember(context: RuleDefinitionElementContext): Component = {
    context match {
      case d1: RuleDefinitionElement1Context =>
        reportWarn(d1, "Need to handle nested package definitions")
        return SysMLUtil.emptyComponent

      case d6: RuleDefinitionElement6Context =>
        reportWarn(d6, "Need to handle enum definitions")
        val enumeration = d6.ruleEnumerationDefinition()
        return SysMLUtil.emptyComponent

      case d5: RuleDefinitionElement5Context =>
        reportWarn(d5, "Need to handle attribute definition (aka data type defs)")
        val attributeDef = d5.ruleAttributeDefinition()
        return SysMLUtil.emptyComponent

      case d10: RuleDefinitionElement10Context =>
        return visitMetadataDefinition(d10.ruleMetadataDefinition())

      case d11: RuleDefinitionElement11Context =>
        return visitPackage_PartDefinition(d11.rulePartDefinition())

      case d12: RuleDefinitionElement12Context =>
        // e.g. connection def PortConnection :> BinaryConnection {
        //		    end source : AbstractFeature[0..*] :>> BinaryConnection::source;
        val connectionDef = d12.ruleConnectionDefinition()
        reportWarn(connectionDef, "Need to handle connection definitions at the package level")
        return SysMLUtil.emptyComponent

      case d15: RuleDefinitionElement15Context =>
        val allocationDef = d15.ruleAllocationDefinition()
        reportWarn(allocationDef, "Need to handle allocation definitions at the package level")
        return SysMLUtil.emptyComponent

      case d16: RuleDefinitionElement16Context =>
        return visitPackage_PortDefinition(d16.rulePortDefinition())

      case x =>
        reportError(x, s"Not currently supporting package members of type: ${x.getClass.getSimpleName}")
        return SysMLUtil.emptyComponent
    }
  }

  private def visitPackage_PortDefinition(portDef: RulePortDefinitionContext): Component = {
    reportWarn(portDef, "Need to handle package level port definitions")
    return SysMLUtil.emptyComponent
  }

  private def visitMetadataDefinition(metadataDefinition: RuleMetadataDefinitionContext): Component = {
    reportWarn(metadataDefinition, "Need to handle metadata definitions")
    return SysMLUtil.emptyComponent
  }

  private def visitPackage_PartDefinition(partDef: RulePartDefinitionContext): Component = {

    val occurrenceDefPrefix = visitOccurrenceDefinitionPrefix(partDef.ruleOccurrenceDefinitionPrefix())

    val decl = partDef.ruleDefinition().ruleDefinitionDeclaration()
    val identifier: Option[Identification] =
      if (isEmpty(decl.ruleIdentification())) None()
      else Some(visitIdentification(decl.ruleIdentification()))

    val specializations: ISZ[QualifiedName] = {
      if (nonEmpty(decl.ruleSubclassificationPart()))
        for (subclass <- listToISZ(decl.ruleSubclassificationPart().ruleOwnedSubclassification())) yield
          visitQualifiedName(subclass.ruleQualifiedName())
      else
        ISZ()
    }

    var subComponents: ISZ[Component] = ISZ()
    var features: ISZ[Feature] = ISZ()
    var connections: ISZ[Connection] = ISZ()

    partDef.ruleDefinition().ruleDefinitionBody() match {
      case i1: RuleDefinitionBody1Context =>
        assert(i1.OP_SEMI() != null)

      case i2: RuleDefinitionBody2Context =>
        for (item <- i2.ruleDefinitionBodyItem().asScala) {
          item match {
            case bi1: RuleDefinitionBodyItem1Context =>
              val member = bi1.ruleDefinitionMember()
              reportError(member.ruleMemberPrefix().getChildCount == 0, member.ruleMemberPrefix(), "Not currently supporting prefixes for component definitions")

              member.ruleDefinitionElement() match {
                case d3: RuleDefinitionElement3Context =>
                  val annotatingElement = d3.ruleAnnotatingElement()
                  reportWarn(annotatingElement, "How are we going to represent properties")
                case x =>
                  reportError(x, "Not currently this type of definition")
              }

            case bi3: RuleDefinitionBodyItem3Context =>
              val attr = bi3.ruleNonOccurrenceUsageMember()
              val prefix = attr.ruleMemberPrefix()
              val visibility: String = prefix.ruleVisibilityIndicator() match {
                case vi1 : RuleVisibilityIndicator1Context =>
                  assert(vi1.K_PUBLIC() != null)
                  "public"
                case vi2 : RuleVisibilityIndicator2Context =>
                  assert(vi2.K_PRIVATE() != null)
                  "private"
                case vi3 : RuleVisibilityIndicator3Context =>
                  assert(vi3.K_PROTECTED() != null)
                  "protected"
                case x if x == null => "public"
                case x =>
                  reportError(x, s"Not expecting visiblity type: $x")
                  ""
              }

              attr.ruleNonOccurrenceUsageElement() match {
                case ue1 : RuleNonOccurrenceUsageElement1Context =>
                  // e.g. 	part def System :> Component {
                  //		:>> category = ComponentCategory::System;
                  val refUsage = ue1.ruleDefaultReferenceUsage()
                  reportWarn(refUsage, "Need to handle component level default reference usages")

                case ue3 : RuleNonOccurrenceUsageElement3Context =>
                  // 	e.g. abstract part def Component {
                  //		     attribute category : ComponentCategory;
                  visitComponent_AttributeUsage(ue3.ruleAttributeUsage())

                case x =>
                  reportError(x, s"Not currently handling usage type ${x.getClass.getSimpleName}")
              }
            case bi4: RuleDefinitionBodyItem4Context =>
              if (bi4.ruleEmptySuccessionMember() != null) {
                reportError(bi4.ruleEmptySuccessionMember(), " Not currently supporting succession members")
              } else {
                val member = bi4.ruleOccurrenceUsageMember()
                reportError(member.ruleMemberPrefix().getChildCount == 0, member.ruleMemberPrefix(), "Not currently supporting occurrence usage prefixes")

                member.ruleOccurrenceUsageElement() match {
                  case u1: RuleOccurrenceUsageElement1Context =>
                    u1.ruleStructureUsageElement() match {
                      case su6: RuleStructureUsageElement6Context =>
                        // e.g. part actuationUnit1 : ActuationLogicUnit : System;
                        val partUsage = su6.rulePartUsage()
                        features = features :+ visitPackage_PartUsage(partUsage)
                      case su9: RuleStructureUsageElement9Context =>
                        // e.g. port ports { ... }
                        val portUsage: PortUsage = visitPortUsage(su9.rulePortUsage())
                        reportWarn(su9.rulePortUsage(), s"Need to handle port usages: $portUsage")
                      case su10: RuleStructureUsageElement10Context =>
                        val connectionUsage = su10.ruleConnectionUsage()
                        reportWarn(connectionUsage, "Need to handle connection usages")
                      case x =>
                        reportError(x, s"Not currently supporting ${x.getClass.getSimpleName} structure usages")
                    }
                  case u2: RuleOccurrenceUsageElement2Context =>
                    reportError(u2, "Not currently handling behavior usages")
                }
              }
            case x =>
              reportError(x, s"${x.getClass.getSimpleName} are not currently supported in component definitions")
          }
        }
    }

    return SysMLUtil.emptyComponent
  }

  def visitPortUsage(portUsage: RulePortUsageContext): PortUsage = {
    //  A port usage is a kind of occurrence usage definition that is a usage of a port definition.
    val occurrenceUsage: OccurrenceUsage = visitOccurrenceUsagePrefix(portUsage.ruleOccurrenceUsagePrefix())

    val u = portUsage.ruleUsage()

    var identification: Option[Identification] = None()
    var specializations: ISZ[FeatureSpecialization] = ISZ()
    if (nonEmpty(u.ruleUsageDeclaration())) {
      u.ruleUsageDeclaration().ruleFeatureDeclaration() match {
        case i1: RuleFeatureDeclaration1Context =>
          identification = Some(visitIdentification(i1.ruleIdentification()))
          specializations = visitFeatureSpecialization(i1.ruleFeatureSpecializationPart())
        case i2: RuleFeatureDeclaration2Context =>
          specializations = visitFeatureSpecialization(i2.ruleFeatureSpecializationPart())
      }
    }

    reportError(isEmpty(u.ruleUsageCompletion().ruleValuePart()), u, "Need an example of a port with a valuePart")

    var definitionBodyItems: ISZ[DefinitionBodyItem] = ISZ()
    u.ruleUsageCompletion().ruleUsageBody().ruleDefinitionBody() match {
      case i: RuleDefinitionBody1Context =>
        assert (i.OP_SEMI() != null)
      case i: RuleDefinitionBody2Context =>
        for (bi <- i.ruleDefinitionBodyItem().asScala) {
          reportWarn(bi, "Need to handle port usage body items")
        }
    }

    return PortUsage(
      occurrenceUsage = occurrenceUsage,
      identification = identification,
      specializations = specializations,
      definitionBodyItems = definitionBodyItems)
  }

  private def visitIdentification(id: RuleIdentificationContext): Identification = {
    id match {
      case i1: RuleIdentification1Context =>
        val shortName = visitName(i1.ruleName().get(0))
        if (i1.ruleName().size() == 2) {
          return Identification(shortName = None(), name = Some(visitName(i1.ruleName().get(1))))
        } else {
          return Identification(shortName = Some(shortName), name = None())
        }
      case i2: RuleIdentification2Context =>
        return Identification(shortName = None(), name = Some(visitName(i2.ruleName())))
    }
  }

  def visitComponent_AttributeUsage(context: RuleAttributeUsageContext): Unit = {
    reportWarn(context, "Need to handle component level attribute usages")
  }

  private def visitOccurrenceDefinitionPrefix(occurrenceDef: RuleOccurrenceDefinitionPrefixContext): OccurrenceDefinition = {
    reportError(occurrenceDef.K_INDIVIDUAL() == null, occurrenceDef, "The 'individual' keyword is not currently supported")
    reportError(occurrenceDef.ruleLifeClassMembership() == null, occurrenceDef.ruleLifeClassMembership(),
      "Life Classes are not currently supported")
    reportError(occurrenceDef.ruleDefinitionExtensionKeyword().isEmpty, occurrenceDef,
      "Extension definitions are not currently supported")

    var isAbstract = F
    if (occurrenceDef.ruleBasicDefinitionPrefix() != null) {
      occurrenceDef.ruleBasicDefinitionPrefix() match {
        case x: RuleBasicDefinitionPrefix1Context =>
        assert(x.K_ABSTRACT() != null) // sanity check
        isAbstract = T

        case x: RuleBasicDefinitionPrefix2Context =>
          reportError(x, "The 'variation' keyword is not currently supported")
      }
    }
    return OccurrenceDefinition(isAbstract = isAbstract)
  }

  private def visitPackage_PartUsage(partUsage: RulePartUsageContext): Feature = {
    val occurrenceUsage = visitOccurrenceUsagePrefix(partUsage.ruleOccurrenceUsagePrefix())

    assert (partUsage.rulePartUsageKeyword().rulePartKeyword().K_PART() != null, "No 'part' keyword") // sanity

    var identifier: Option[Identification] = None()
    var specializations: ISZ[FeatureSpecialization] = ISZ()
    if (nonEmpty(partUsage.ruleUsage().ruleUsageDeclaration())) {
      partUsage.ruleUsage().ruleUsageDeclaration().ruleFeatureDeclaration() match {
        case d1: RuleFeatureDeclaration1Context =>
          identifier = Some(visitIdentification(d1.ruleIdentification()))

          if(nonEmpty(d1.ruleFeatureSpecializationPart())) {
            specializations = visitFeatureSpecialization(d1.ruleFeatureSpecializationPart())
          }
        case d2: RuleFeatureDeclaration2Context =>
          specializations = visitFeatureSpecialization(d2.ruleFeatureSpecializationPart())
      }
    }

    reportError(isEmpty(partUsage.ruleUsage().ruleUsageCompletion().ruleValuePart()), partUsage.ruleUsage().ruleUsageCompletion().ruleValuePart(),
      "Not expecting a feature value here")

    partUsage.ruleUsage().ruleUsageCompletion().ruleUsageBody().ruleDefinitionBody() match {
      case db1: RuleDefinitionBody1Context =>
        assert(db1.OP_SEMI() != null)
      case db2: RuleDefinitionBody2Context =>
        reportError(db2, "Part usages defined at the package level cannot have bodies")
    }

    return SysMLUtil.emptyFeature
  }

  private def visitFeatureSpecialization(fs: RuleFeatureSpecializationPartContext): ISZ[FeatureSpecialization] = {
    var ret: ISZ[FeatureSpecialization] = ISZ()
    fs match {
      case s1: RuleFeatureSpecializationPart1Context =>
        reportError(isEmpty(s1.ruleMultiplicityPart()), s1.ruleMultiplicityPart(), "Multiplicities are not currently supported")
        for (s <- s1.ruleFeatureSpecialization().asScala){
          s match {
            case x: RuleFeatureSpecialization1Context =>
              val typings = x.ruleTypings()
              if (!typings.ruleFeatureTyping().isEmpty) {
                reportError(typings.ruleFeatureTyping().get(0), "Comma-separated feature typings are not currently supported")
              }
              typings.ruleTypedBy().ruleFeatureTyping() match {
                case t1: RuleFeatureTyping1Context =>
                  t1.ruleOwnedFeatureTyping() match {
                    case ot1: RuleOwnedFeatureTyping1Context =>
                      ret = ret :+ QualifiedNameSpecialization(visitQualifiedName(ot1.ruleQualifiedName()))
                    case ot2: RuleOwnedFeatureTyping2Context =>
                      reportError(ot2.ruleOwnedFeatureChain(), "Feature chaining is not currently supported")
                    case x =>
                      halt(s"Not expecting $x")
                  }

                case t2: RuleFeatureTyping2Context =>
                  reportError(t2, "Conjugated port typing is not currently supported")
              }
            case x: RuleFeatureSpecialization2Context =>
              val subsettings = x.ruleSubsettings()
              reportError(subsettings, "Subsets are not currently supported")
            case x: RuleFeatureSpecialization3Context =>
              val references = x.ruleReferences()
              reportError(references, "References are not currently supported")
            case x: RuleFeatureSpecialization4Context =>
              val redefinitions = x.ruleRedefinitions()
              reportError(redefinitions, "Redefinitions are not currently supported")
            case x => halt(s"Not expecting $x")
          }
        }
      case s2: RuleFeatureSpecializationPart2Context =>
        reportError(s2.ruleMultiplicityPart(), "Not currently handling payload specialization")
    }
    return ret
  }

  private def visitOccurrenceUsagePrefix(ocup: RuleOccurrenceUsagePrefixContext): OccurrenceUsage = {
    reportError(ocup.K_INDIVIDUAL() == null, ocup, "The 'individual' keyword is not currently supported")

    var direction: Option[FeatureDirection.Type] = None()
    var isAbstract: B = F
    var isVariation: B = F
    var isReadOnly: B = F
    var isDerived: B = F
    var isEnd: B = F
    var isRef: B = F
    var isIndividual: B = F
    var isSnapshot: B = F
    var isTimeslice: B = F
    var usageExtensions: ISZ[QualifiedName] = ISZ()

    if (nonEmpty(ocup.ruleBasicUsagePrefix())) {
      val bup = ocup.ruleBasicUsagePrefix()
      if (nonEmpty(bup.ruleRefPrefix().ruleFeatureDirection())) {
        bup.ruleRefPrefix().ruleFeatureDirection() match {
          case x: RuleFeatureDirection1Context =>
            assert (x.K_IN() != null) // sanity
            direction = Some(FeatureDirection.In)
          case x: RuleFeatureDirection2Context =>
            assert (x.K_OUT() != null) // sanity
            direction = Some(FeatureDirection.Out)
          case x: RuleFeatureDirection3Context =>
            assert (x.K_INOUT() != null)
            direction = Some(FeatureDirection.InOut)
          case x =>
            reportError(x, s"Not expecting direction of type ${x.getClass.getSimpleName}")
        }
      }

      isAbstract = bup.ruleRefPrefix().K_ABSTRACT() != null
      isVariation = bup.ruleRefPrefix().K_VARIATION() != null
      isReadOnly = bup.ruleRefPrefix().K_READONLY() != null
      isDerived = bup.ruleRefPrefix().K_DERIVED() != null
      isEnd = bup.ruleRefPrefix().K_END() != null

      isRef = bup.K_REF() != null
    }

    isIndividual = ocup.K_INDIVIDUAL() != null

    if (nonEmpty(ocup.rulePortionKind())) {
      ocup.rulePortionKind() match {
        case i: RulePortionKind1Context =>
          assert (i.K_SNAPSHOT() != null)
          isSnapshot = T
        case i: RulePortionKind2Context =>
          assert (i.K_TIMESLICE() != null)
          isTimeslice = T
      }
    }

    if (!ocup.ruleUsageExtensionKeyword().isEmpty) {
      for (ue <- ocup.ruleUsageExtensionKeyword().asScala) {
        usageExtensions = usageExtensions :+
          visitQualifiedName(ue.rulePrefixMetadataMember().rulePrefixMetadataUsage().ruleMetadataTyping().ruleQualifiedName())
      }
    }

    return OccurrenceUsage(
      direction = direction,
      isAbstract = isAbstract,
      isVariation = isVariation,
      isReadOnly = isReadOnly,
      isDerived = isDerived,
      isEnd = isEnd,
      isRef = isRef,
      isIndividual = isIndividual,
      isSnapshot = isSnapshot,
      isTimeslice = isTimeslice,
      usageExtensions = usageExtensions
    )
  }

  def isEmpty(p: ParserRuleContext): B = {
    return p == null || p.getChildCount == 0
  }

  def nonEmpty(p: ParserRuleContext): B = {
    return !isEmpty(p)
  }

  private def listToISZ[T](l: util.List[T]): ISZ[T] = {
    return ISZ(l.asScala.toSeq:_*)
  }

  /** ruleAliasMember: ruleMemberPrefix 'alias' ('<' ruleName '>')? ruleName? 'for' ruleQualifiedName ruleRelationshipBody;
   */
  def visitAliases(aliases: mutable.Buffer[RuleAliasMemberContext]): ISZ[Alias] = {
    var ret: ISZ[Alias] = ISZ()
    for (alias <- aliases) {
      reportError(SysMLAstBuilder.isEmptyRelationshipBody(alias.ruleRelationshipBody()), alias.ruleRelationshipBody(),
        "Alias relationship bodies are not currently supported")

      val visibility: Visibility.Type = visitVisibilityIndicator(alias.ruleMemberPrefix().ruleVisibilityIndicator())

      val id = alias.ruleName().size() match {
        case 0 =>
          reportError(alias, "Aliases must have a name")
          Identification(shortName = None(), name = None())
        case 1 =>
          if (alias.LANGLE() != null) { // just the short name
            Identification(shortName = Some(visitName(alias.ruleName(0))), name = None())
          } else {
            Identification(shortName = None(), name = Some(visitName(alias.ruleName(0))))
          }
        case 2 =>
          Identification(shortName = Some(visitName(alias.ruleName(0))), name = Some(visitName(alias.ruleName(1))))
        case x => halt(s"Infeasible: $x")
      }

      val target: ISZ[String] = visitQualifiedName(alias.ruleQualifiedName())

      ret = ret :+ Alias(visibility = visibility, identification = id, target = target)
    }

    return ret
  }

  def visitImports(imports: mutable.Buffer[RuleImportContext]): ISZ[Import] = {
    var ret: ISZ[Import] = ISZ()
    for (importe <- imports ) {
      var annotations: ISZ[AnnotatingElement] = ISZ()
      importe.ruleRelationshipBody() match {
        case rb1: RuleRelationshipBody1Context =>
          assert(rb1.OP_SEMI() != null) // sanity check

        case rb2: RuleRelationshipBody2Context =>
          for(oa <- rb2.ruleOwnedAnnotation().asScala if !SysmlAstUtil.isRegularComment(oa.ruleAnnotatingElement())) {
            annotations = annotations :+ visitAnnotatingElement(oa.ruleAnnotatingElement())
          }
      }

      if (nonEmpty(importe.ruleMembershipImport())) {
        assert (isEmpty(importe.ruleNamespaceImport()))

        val mimport = importe.ruleMembershipImport()

        val visibility = visitVisibilityIndicator(mimport.ruleImportPrefix().ruleVisibilityIndicator())

        // 'all' is not detailed in the spec
        reportError(mimport.ruleImportPrefix().K_ALL() == null, mimport,
          "Not currently handling 'all' import annotations")

        val qualifiedName = visitQualifiedName(mimport.ruleImportedMembership().ruleQualifiedName())

        ret = ret :+ Import(
          visibility = visibility,
          qualifiedName = qualifiedName,
          star = F,
          starStar = mimport.ruleImportedMembership().OP_STAR_STAR() != null,
          annotations = annotations)
      } else {
        val nimport = importe.ruleNamespaceImport()

        val visibility = visitVisibilityIndicator(nimport.ruleImportPrefix().ruleVisibilityIndicator())

        // 'all' is not detailed in the spec
        reportError(nimport.ruleImportPrefix().K_ALL() == null, nimport,
          "Not currently handling 'all' import annotations")

        if (nonEmpty(nimport.ruleFilterPackage())) {
          reportError(nimport, "Not currently handling filtered imports")
        } else {
          val namespace = nimport.ruleImportedNamespace()

          val qualfiedName = visitQualifiedName(namespace.ruleQualifiedName())

          assert (nimport.ruleImportedNamespace().OP_STAR() != null, "The star is not optional")

          ret = ret :+ Import(
            visibility = visibility,
            qualifiedName = qualfiedName,
            star = T,
            starStar = nimport.ruleImportedNamespace().OP_STAR_STAR() != null,
            annotations = annotations
          )
        }
      }
    }
    return ret
  }

  def visitQualifiedName(name: RuleQualifiedNameContext): ISZ[String] = {
    var ret: ISZ[String] = ISZ()
    if (name.ruleQualification() != null) {
      ret = ret ++ (for (seg <- listToISZ(name.ruleQualification().ruleName())) yield visitName(seg))
    }
    return ret :+ visitName(name.ruleName())
  }

  def visitName(name: RuleNameContext): String = {
    name match {
      case r1: RuleName1Context => return r1.getText
      case r2: RuleName2Context => return s"`${r2.getText}`"
      case x => halt(s"Unexpected name: $x")
    }
  }

  def visitVisibilityIndicator(context: RuleVisibilityIndicatorContext): Visibility.Type = {
      context match {
        case null =>
          return Visibility.Public
        case v1: RuleVisibilityIndicator1Context =>
          assert(v1.K_PUBLIC() != null)
          return Visibility.Public
        case v2:RuleVisibilityIndicator2Context =>
          assert(v2.K_PRIVATE() != null)
          return Visibility.Private
        case v3:RuleVisibilityIndicator3Context =>
          assert(v3.K_PROTECTED() != null)
          return Visibility.Protected
        case x =>
          reportError(x, s"Not expecting visibility: $x ")
          return Visibility.Public
      }
  }

  def toPosOpt(x: ParserRuleContext): Option[Position] = {
    return Some(
      FlatPos(
      uriOpt = uriOpt,
      beginLine32 = conversions.Z.toU32(x.start.getLine),
      beginColumn32 = conversions.Z.toU32(x.start.getCharPositionInLine),
      endLine32 = conversions.Z.toU32(x.stop.getLine),
      endColumn32 = conversions.Z.toU32(x.stop.getCharPositionInLine),
      offset32 = conversions.Z.toU32(x.start.getStartIndex),
      length32 = conversions.Z.toU32(x.stop.getStartIndex - x.start.getStartIndex))
    )
  }

  def reportWarn(nodeOrList: Object, message: String): Unit = {
   reportWarn(F, nodeOrList, message)
  }

  def reportWarn(cond: B, nodeOrList: Object, message: String): Unit = {
    if (!cond) {
      var o: Option[ParserRuleContext] = None()
      nodeOrList match {
        case i: ParserRuleContext => o = Some(i)
        case i: java.util.List[x] => o = Some(i.get(0).asInstanceOf[ParserRuleContext])
      }
      reporter.warn (toPosOpt (o.get), kind, message)
    }
  }

  def reportError(nodeOrList: Object, message: String): Unit = {
    reportError(F, nodeOrList, message)
  }

  def reportError(cond: B, nodeOrList: Object, message: String): Unit = {
    if (!cond) {
      var o: Option[ParserRuleContext] = None()
      nodeOrList match {
        case i: ParserRuleContext => o = Some(i)
        case i: java.util.List[x] => o = Some(i.get(0).asInstanceOf[ParserRuleContext])
      }
      reporter.error(toPosOpt(o.get), kind, message)
    }
  }
}
