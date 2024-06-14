package org.sireum.hamr.sysml

import org.sireum._
import org.sireum.message._
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.TerminalNodeImpl
import org.sireum.hamr.ir._
import org.sireum.hamr.sysml.SysmlAst._
import org.sireum.hamr.sysml.SysmlAstUtil.{Placeholders, isRegularComment, mergePos}
import org.sireum.hamr.sysml.parser.SysMLv2Parser._
import org.sireum.hamr.sysml.parser.SysMLv2Parser
import org.sireum.message.{Position, Reporter}
import org.sireum.lang.{ast => AST}
import org.sireum.lang.ast.Exp

import java.util
import scala.jdk.CollectionConverters._

object SysMLAstBuilder {

  def resolve(tree: ParserRuleContext, uriOpt: Option[String], isSysML: B, reporter: Reporter): Aadl = {
    if (isSysML) {
      assert(!reporter.hasError)
      val p = SysMLAstBuilder(uriOpt)
      p.visitEntryRuleRootNamespace(tree.asInstanceOf[SysMLv2Parser.EntryRuleRootNamespaceContext])
      reporter.reports(p.reporter.messages)
      return SysMLUtil.emptyAadl
    } else {
      reporter.warn(None(), "SysMLAstBuilder", "Need to handle kerml")
      return SysMLUtil.emptyAadl
    }
  }

  def isEmptyRelationshipBody(context: ParserRuleContext): B = {
    context match {
      case x: SysMLv2Parser.RuleRelationshipBody1Context =>
        assert(x.OP_SEMI().getChildCount >= 0) // sanity check
        return true
      case x =>
        return F
    }
  }
}

case class SysMLAstBuilder(uriOpt: Option[String]) {
  val reporter: Reporter = Reporter.create
  val kind: String = "SysMLResolver"

  def visitEntryRuleRootNamespace(ctx: EntryRuleRootNamespaceContext): Root = {

    var bodyElements: ISZ[PackageBodyElement] = ISZ()
    for (packageBodyElement <- ctx.ruleRootNamespace().rulePackageBodyElement().asScala) {
      packageBodyElement match {
        case r1: RulePackageBodyElement1Context =>
          // rulePackageMember: ruleMemberPrefix (ruleDefinitionElement | ruleUsageElement);
          val m1 = r1.rulePackageMember()
          var visibility = visitVisibilityIndicator(m1.ruleMemberPrefix().ruleVisibilityIndicator())
          if (m1.ruleDefinitionElement() != null) {
            assert(m1.ruleUsageElement() == null)
            visitDefinitionElement(visibility, m1.ruleDefinitionElement()) match {
              case p: Package => bodyElements = bodyElements :+ p
              case a: AnnotatingElement => bodyElements :+ a
              case x =>
                halt(s"Wasn't expecting package body element: ${x}")
            }

          } else {
            assert(m1.ruleUsageElement() != null)
            halt("todo")
          }

        case r2: RulePackageBodyElement2Context =>
          val m2 = r2.ruleElementFilterMember()
          reportError(m2, "Not currently supporting filter members")

        case r3: RulePackageBodyElement3Context =>
          bodyElements = bodyElements :+ visitAlias(r3.ruleAliasMember())

        case r4: RulePackageBodyElement4Context =>
          bodyElements = bodyElements :+ visitImport(r4.ruleImport())

      }
    }
    return Root(bodyElements)
  }

  def visitDefinitionElement(visibility: Visibility.Type, context: RuleDefinitionElementContext): DefinitionElement = {
    context match {
      case d1: RuleDefinitionElement1Context =>
        return helper_definitionElementPackage(
          standard = F,
          library = F,
          prefixMetadataMembers = d1.rulePackage().rulePrefixMetadataMember(),
          identification = d1.rulePackage().rulePackageDeclaration().ruleIdentification(),
          body = d1.rulePackage().rulePackageBody(),
          parent = context)

      case d2: RuleDefinitionElement2Context =>
        assert(d2.ruleLibraryPackage().K_LIBRARY() != null)

        return helper_definitionElementPackage(
          standard = d2.ruleLibraryPackage().K_STANDARD() != null,
          library = T,
          prefixMetadataMembers = d2.ruleLibraryPackage().rulePrefixMetadataMember(),
          identification = d2.ruleLibraryPackage().rulePackageDeclaration().ruleIdentification(),
          body = d2.ruleLibraryPackage().rulePackageBody(),
          parent = context)

      case d3: RuleDefinitionElement3Context =>

        if (!SysmlAstUtil.isRegularComment(d3.ruleAnnotatingElement())) {
          return visitAnnotatingElement(d3.ruleAnnotatingElement())
        } else {
          return Placeholders.emptyDefinitionElement
        }

      case d5: RuleDefinitionElement5Context =>
        return visitAttributeDefinition(d5.ruleAttributeDefinition())

      case d6: RuleDefinitionElement6Context =>
        return visitEnumerationDefinition(d6.ruleEnumerationDefinition())

      case d11: RuleDefinitionElement11Context =>
        return visitPartDefinition(d11.rulePartDefinition())

      case d12: RuleDefinitionElement12Context =>
        return visitConnectionDefinition(d12.ruleConnectionDefinition())

      case d15: RuleDefinitionElement15Context =>
        return visitAllocationDefinition(d15.ruleAllocationDefinition())

      case d16: RuleDefinitionElement16Context =>
        return visitPortDefinition(d16.rulePortDefinition())

      case x =>
        reportError(x, s"Not currently handling the following definition element: ${x.getClass.getSimpleName}")
        return Placeholders.emptyDefinitionElement
    }
  }

  private def visitAllocationDefinition(context: RuleAllocationDefinitionContext): AllocationDefinition = {
    val occurDef = visitOccurrenceDefinitionPrefix(context.ruleOccurrenceDefinitionPrefix())

    val (identifier, subClassifications, bodyItems) = visitDefinition(context.ruleDefinition())

    return AllocationDefinition(
      occurrenceDefPrefix = occurDef,
      identifier = identifier,
      subClassifications = subClassifications,
      bodyItems = bodyItems
    )
  }

  private def visitAttributeDefinition(context: RuleAttributeDefinitionContext): AttributeDefinition = {
    val defPrefix = visitDefinitionPrefix(context.ruleDefinitionPrefix())

    val (identifier, subClassifications, bodyItems) = visitDefinition(context.ruleDefinition())

    return AttributeDefinition(
      defPrefix = defPrefix,
      identification = identifier,
      subClassifications = subClassifications,
      definitionBodyItems = bodyItems
    )
  }

  private def visitConnectionDefinition(context: RuleConnectionDefinitionContext): ConnectionDefinition = {
    val occurrenceDefPrefix = visitOccurrenceDefinitionPrefix(context.ruleOccurrenceDefinitionPrefix())

    val (identifier, subClassifications, bodyItems) = visitDefinition(context.ruleDefinition())

    return ConnectionDefinition(
      occurrenceDefPrefix = occurrenceDefPrefix,
      identifier = identifier,
      subClassifications = subClassifications,
      bodyItems = bodyItems
    )
  }

  private def helper_definitionElementPackage(standard: B,
                                              library: B,
                                              prefixMetadataMembers: util.List[RulePrefixMetadataMemberContext],
                                              identification: RuleIdentificationContext,
                                              body: RulePackageBodyContext,
                                              parent: RuleDefinitionElementContext): DefinitionElement = {

    if (!prefixMetadataMembers.isEmpty) {
      reportError(parent, "Package metadata not currently supported")
    }

    var id: Option[Identification] =
      if (isEmpty(identification)) None()
      else Some(visitIdentification(identification))

    if (id.isEmpty) {
      reportError(parent, "Packages must have an identification")
      return Placeholders.emptyDefinitionElement
    }

    var packageElements: ISZ[PackageBodyElement] = ISZ()
    body match {
      case b1: RulePackageBody1Context =>
        assert(b1.OP_SEMI() != null)

      case b2: RulePackageBody2Context =>

        // iterate over children to preserve ordering
        for (c <- b2.children.asScala) {
          c match {
            case i: RulePackageMemberContext =>
              val visibility = visitVisibilityIndicator(i.ruleMemberPrefix().ruleVisibilityIndicator())

              if (nonEmpty(i.ruleDefinitionElement())) {
                packageElements = packageElements :+ visitDefinitionElement(visibility, i.ruleDefinitionElement())
              } else {
                packageElements = packageElements :+ visitUsageElement(visibility, i.ruleUsageElement())
              }

            case i: RuleElementFilterMemberContext =>
              reportError(i, "Filter members are not currently supported")

            case i: RuleAliasMemberContext =>
              packageElements = packageElements :+ visitAlias(i)

            case i: RuleImportContext =>
              packageElements = packageElements :+ visitImport(i)

            case i: org.antlr.v4.runtime.tree.TerminalNode =>
            // do nothing
          }
        }
    }

    return Package(
      identification = id.get,
      packageElements = packageElements
    )
  }

  def visitAnnotatingElement(context: RuleAnnotatingElementContext): AnnotatingElement = {
    assert(!SysmlAstUtil.isRegularComment(context), s"Filter out regular comments before calling this method")
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

  private def visitEnumerationDefinition(o: RuleEnumerationDefinitionContext): EnumerationDefinition = {
    reportError(o.ruleDefinitionExtensionKeyword().isEmpty, o,
      "Enum extensions are not currently handled")

    val id = visitIdentification(o.ruleDefinitionDeclaration().ruleIdentification())

    reportError(isEmpty(o.ruleDefinitionDeclaration().ruleSubclassificationPart()), o,
      "Enum specializations are not currently supported")

    var annotations: ISZ[AnnotatingElement] = ISZ()
    var enumValues: ISZ[EnumeratedValue] = ISZ()

    o.ruleEnumerationBody() match {
      case i1: RuleEnumerationBody1Context =>
        assert(i1.OP_SEMI() != null)
      case i2: RuleEnumerationBody2Context =>
        for (am <- listToISZ(i2.ruleAnnotatingMember()) if !isRegularComment(am.ruleAnnotatingElement())) {
          annotations = annotations :+ visitAnnotatingElement(am.ruleAnnotatingElement())
        }

        for (ev <- listToISZ(i2.ruleEnumerationUsageMember())) {
          reportError(ev.ruleEnumeratedValue().ruleUsageExtensionKeyword().isEmpty, ev,
            "Extensions are not currently supported for enum values")

          val visibility = visitVisibilityIndicator(ev.ruleMemberPrefix().ruleVisibilityIndicator())
          val u = visitUsage(ev.ruleEnumeratedValue().ruleUsage())
          enumValues = enumValues :+ EnumeratedValue(
            visibility = visibility,
            identification = u.identification,
            specializations = u.specializations,
            definitionBodyItems = u.definitionBodyItems
          )
        }
    }
    return EnumerationDefinition(
      identification = id,
      annotations = annotations,
      enumValues = enumValues)
  }

  private def visitPortDefinition(portDef: RulePortDefinitionContext): PortDefinition = {
    val defPrefix = visitDefinitionPrefix(portDef.ruleDefinitionPrefix())

    val (identifier, subClassifications, bodyItems) = visitDefinition(portDef.ruleDefinition())

    // TODO: conjugated port def leads to an empty rule
    val empty = portDef.ruleConjugatedPortDefinitionMember().ruleConjugatedPortDefinition().rulePortConjugation()

    return PortDefinition(
      defPrefix = defPrefix,
      identification = identifier,
      subClassifications = subClassifications,
      definitionBodyItems = bodyItems
    )
  }

  private def visitDefinitionPrefix(context: RuleDefinitionPrefixContext): DefinitionPrefix = {

    var isAbstract = F
    var isVariation = F

    if (nonEmpty(context.ruleBasicDefinitionPrefix())) {
      context.ruleBasicDefinitionPrefix() match {
        case p1: RuleBasicDefinitionPrefix1Context =>
          assert(p1.K_ABSTRACT() != null)
          isAbstract = T
        case p2: RuleBasicDefinitionPrefix2Context =>
          assert(p2.K_VARIATION() != null)
          isVariation = T
      }
    }

    reportError(context.ruleDefinitionExtensionKeyword().isEmpty, context, "Need to handle definition extensions")

    return DefinitionPrefix(
      isAbstract = isAbstract,
      isVariation = isVariation
    )
  }

  private def visitMetadataDefinition(metadataDefinition: RuleMetadataDefinitionContext): MetadataDefinition = {
    reportError(metadataDefinition, "Need to handle metadata definitions")
    return MetadataDefinition()
  }

  private def visitPartDefinition(partDef: RulePartDefinitionContext): PartDefinition = {

    val occurrenceDefPrefix = visitOccurrenceDefinitionPrefix(partDef.ruleOccurrenceDefinitionPrefix())

    val (identifier, subClassifications, bodyItems) = visitDefinition(partDef.ruleDefinition())

    return PartDefinition(
      occurrenceDefPrefix = occurrenceDefPrefix,
      identifier = identifier,
      subClassifications = subClassifications,
      bodyItems = bodyItems
    )
  }

  private def visitUsageElement(visibility: Visibility.Type, context: RuleUsageElementContext): UsageElement = {
    context match {
      case i: RuleUsageElement1Context =>
        return visitNonOccurrenceUsageElement(visibility, i.ruleNonOccurrenceUsageElement())
      case i: RuleUsageElement2Context =>
        return visitOccurrenceUsageElement(visibility, i.ruleOccurrenceUsageElement())
    }
  }

  private def visitItemUsage(visibility: Visibility.Type, context: RuleItemUsageContext): ItemUsage = {
    val occurrenceUsagePrefix: OccurrenceUsagePrefix = visitOccurrenceUsagePrefix(context.ruleOccurrenceUsagePrefix())

    val u = visitUsage(context.ruleUsage())

    return ItemUsage(
      visibility = visibility,
      occurrenceUsagePrefix = occurrenceUsagePrefix,
      identification = u.identification,
      specializations = u.specializations,
      definitionBodyItems = u.definitionBodyItems)
  }

  private def visitPartUsage(visibility: Visibility.Type, partUsage: RulePartUsageContext): PartUsage = {
    /** eg.
      * part tempSensor : TempSensor;
		  * part tempControl : TempControl;
		  */
    val occurrenceUsagePrefix: OccurrenceUsagePrefix = visitOccurrenceUsagePrefix(partUsage.ruleOccurrenceUsagePrefix())

    val u = visitUsage(partUsage.ruleUsage())

    return PartUsage(
      visibility = visibility,
      occurrenceUsagePrefix = occurrenceUsagePrefix,
      identification = u.identification,
      specializations = u.specializations,
      definitionBodyItems = u.definitionBodyItems)
  }

  def visitPortUsage(visibility: Visibility.Type, portUsage: RulePortUsageContext): PortUsage = {
    /** e.g.
      * out port currentTemp : DataPort { out :> type : Temperature; }
		  * out port tempChanged : EventPort;
      */

    //  A port usage is a kind of occurrence usage definition that is a usage of a port definition.
    val occurrenceUsagePrefix: OccurrenceUsagePrefix = visitOccurrenceUsagePrefix(portUsage.ruleOccurrenceUsagePrefix())

    val u = visitUsage(portUsage.ruleUsage())

    return PortUsage(
      visibility = visibility,
      occurrenceUsagePrefix = occurrenceUsagePrefix,
      identification = u.identification,
      specializations = u.specializations,
      definitionBodyItems = u.definitionBodyItems)
  }

  def visitFeatureDeclaration(o: RuleFeatureDeclarationContext): (Option[Identification], ISZ[FeatureSpecialization]) = {
    var identification: Option[Identification] = None()
    var specializations: ISZ[FeatureSpecialization] = ISZ()

    o match {
      case i1: RuleFeatureDeclaration1Context =>
        identification = Some(visitIdentification(i1.ruleIdentification()))
        if (nonEmpty(i1.ruleFeatureSpecializationPart())) {
          specializations = visitFeatureSpecializationPart(i1.ruleFeatureSpecializationPart())
        }
      case i2: RuleFeatureDeclaration2Context =>
        specializations = visitFeatureSpecializationPart(i2.ruleFeatureSpecializationPart())
    }

    return (identification, specializations)
  }

  private def visitUsage(ruleUsage: RuleUsageContext): UsageHolder = {
    val (identification, specializations): (Option[Identification], ISZ[FeatureSpecialization]) =
      if (isEmpty(ruleUsage.ruleUsageCompletion())) {
        (None(), ISZ())
      } else {
        visitFeatureDeclaration(ruleUsage.ruleUsageDeclaration().ruleFeatureDeclaration())
      }

    val featureValue: Option[FeatureValue] =
      if (isEmpty(ruleUsage.ruleUsageCompletion().ruleValuePart())) {
        None()
      } else {
        Some(visitFeatureValue(ruleUsage.ruleUsageCompletion().ruleValuePart().ruleFeatureValue()))
      }

    val definitionBodyItems: ISZ[BodyElement] = visitDefinitionBody(ruleUsage.ruleUsageCompletion().ruleUsageBody().ruleDefinitionBody())

    return UsageHolder(
      identification,
      specializations,
      featureValue,
      definitionBodyItems
    )
  }

  private def visitFeatureValue(context: RuleFeatureValueContext): FeatureValue = {
    val isBound = context.OP_EQ() != null
    val isInitial = context.OP_COLON_EQ() != null
    val isDefault = context.K_DEFAULT() != null
    val exp = visitOwnedExpression(context.ruleOwnedExpression())
    return FeatureValue(
      isBound = isBound,
      isInitial = isInitial,
      isDefault = isDefault,
      exp = exp
    )
  }


  private def visitDefinition(ruleDefinition: RuleDefinitionContext):
  (Option[Identification], ISZ[QualifiedName], ISZ[BodyElement]) = {
    val decl = ruleDefinition.ruleDefinitionDeclaration()

    val identifier: Option[Identification] =
      if (isEmpty(decl.ruleIdentification())) None()
      else Some(visitIdentification(decl.ruleIdentification()))

    val subclassifications: ISZ[QualifiedName] = {
      if (isEmpty(decl.ruleSubclassificationPart())) ISZ()
      else
        for (subclass <- listToISZ(decl.ruleSubclassificationPart().ruleOwnedSubclassification())) yield
          visitQualifiedName(subclass.ruleQualifiedName())
    }

    val bodyItems: ISZ[BodyElement] = visitDefinitionBody(ruleDefinition.ruleDefinitionBody())

    return (identifier, subclassifications, bodyItems)
  }

  private def visitDefinitionBody(body: RuleDefinitionBodyContext): ISZ[BodyElement] = {
    body match {
      case i: RuleDefinitionBody1Context =>
        assert(i.OP_SEMI() != null)
        return ISZ()

      case i: RuleDefinitionBody2Context =>
        var items: ISZ[BodyElement] = ISZ()
        for (bi <- i.ruleDefinitionBodyItem().asScala) {
          bi match {
            case bi1: RuleDefinitionBodyItem1Context =>
              val defMember = bi1.ruleDefinitionMember()
              val visibility = visitVisibilityIndicator(defMember.ruleMemberPrefix().ruleVisibilityIndicator())
              val defElem = visitDefinitionElement(visibility, defMember.ruleDefinitionElement())

              reportError(defElem, "Need to handle definition member in definition body")

            case bi2: RuleDefinitionBodyItem2Context =>
              val variant = bi2.ruleVariantUsageMember()
              val visibility = visitVisibilityIndicator(variant.ruleMemberPrefix().ruleVisibilityIndicator())
              val variantElem = variant.ruleVariantUsageElement()

              reportError(variantElem, "Variants are not currently handled in definition body")

            case bi3: RuleDefinitionBodyItem3Context =>
              val noum = bi3.ruleNonOccurrenceUsageMember()
              val visibility = visitVisibilityIndicator(noum.ruleMemberPrefix().ruleVisibilityIndicator())
              items = items :+ visitNonOccurrenceUsageElement(visibility, noum.ruleNonOccurrenceUsageElement())

            case bi4: RuleDefinitionBodyItem4Context =>
              if (nonEmpty(bi4.ruleEmptySuccessionMember())) {
                reportError(bi4.ruleEmptySuccessionMember(),
                  "Successions are not currently handled in definition body")
              }
              val visibility = visitVisibilityIndicator(bi4.ruleOccurrenceUsageMember().ruleMemberPrefix().ruleVisibilityIndicator())
              items = items :+ visitOccurrenceUsageElement(visibility, bi4.ruleOccurrenceUsageMember().ruleOccurrenceUsageElement())

            case bi5: RuleDefinitionBodyItem5Context =>
              items = items :+ visitAlias(bi5.ruleAliasMember())

            case bi6: RuleDefinitionBodyItem6Context =>
              items = items :+ visitImport(bi6.ruleImport())
          }
        }
        return items
    }
  }

  private def visitOccurrenceUsageElement(visibility: Visibility.Type, o: RuleOccurrenceUsageElementContext): OccurrenceUsageElement = {
    o match {
      case i1: RuleOccurrenceUsageElement1Context =>
        i1.ruleStructureUsageElement() match {
          case su5: RuleStructureUsageElement5Context =>
            return visitItemUsage(visibility, su5.ruleItemUsage())

          case su6: RuleStructureUsageElement6Context =>
            return visitPartUsage(visibility, su6.rulePartUsage())

          case su9: RuleStructureUsageElement9Context =>
            return visitPortUsage(visibility, su9.rulePortUsage())

          case su10: RuleStructureUsageElement10Context =>
            return visitConnectionUsage(visibility, su10.ruleConnectionUsage())

          case x =>
            reportError(x, s"${x.getClass.getSimpleName} are not currently supported")
            return Placeholders.OccurrenceUsageElementPlaceholder
        }

      case i2: RuleOccurrenceUsageElement2Context =>
        val elem = i2.ruleBehaviorUsageElement()
        reportError(elem, "Not currently supporting behavior usages")
        return Placeholders.OccurrenceUsageElementPlaceholder
    }
  }

  /*
   * ConnectionUsage returns SysML::ConnectionUsage :
	 * OccurrenceUsagePrefix
	 * ( ConnectionUsageKeyword UsageDeclaration? ValuePart? ( ConnectorKeyword ConnectorPart )?
	 *   | ConnectorKeyword ConnectorPart
	 * ) UsageBody;
   */
  private def visitConnectionUsage(visibility: Visibility.Type, context: RuleConnectionUsageContext): ConnectionUsage = {
    val occurrenceUsagePrefix = visitOccurrenceUsagePrefix(context.ruleOccurrenceUsagePrefix())

    val (identification, specializations): (Option[Identification], ISZ[FeatureSpecialization]) =
      if (isEmpty(context.ruleUsageDeclaration())) {
        (None(), ISZ())
      } else {
        visitFeatureDeclaration(context.ruleUsageDeclaration().ruleFeatureDeclaration())
      }

    val featureValue: Option[FeatureValue] =
      if (isEmpty(context.ruleValuePart())) None()
      else Some(visitFeatureValue(context.ruleValuePart().ruleFeatureValue()))

    val connectorPart: Option[ConnectorPart] =
      if (isEmpty(context.ruleConnectorKeyword())) {
        assert(isEmpty(context.ruleConnectorPart()))
        None()
      } else {
        context.ruleConnectorPart() match {
          case c1: RuleConnectorPart1Context =>
            assert(c1.ruleBinaryConnectorPart().ruleConnectorEndMember().size() == 2)
            val src = visitConnectorEnd(c1.ruleBinaryConnectorPart().ruleConnectorEndMember(0).ruleConnectorEnd())
            val dst = visitConnectorEnd(c1.ruleBinaryConnectorPart().ruleConnectorEndMember(1).ruleConnectorEnd())
            Some(BinaryConnectorPart(src, dst))
          case c2: RuleConnectorPart2Context =>
            val ends = for (c <- listToISZ(c2.ruleNaryConnectorPart().ruleConnectorEndMember())) yield visitConnectorEnd(c.ruleConnectorEnd())
            Some(NaryConnectorPart(connectorEnds = ends))
        }
      }

    val bodyItems = visitDefinitionBody(context.ruleUsageBody().ruleDefinitionBody())

    return ConnectionUsage(
      visibility = visibility,
      occurrenceUsagePrefix = occurrenceUsagePrefix,
      identification = identification,
      specializations = specializations,
      featureValue = featureValue,
      connectorPart = connectorPart,
      bodyItems = bodyItems)
  }

  private def visitConnectorEnd(context: RuleConnectorEndContext): ConnectorEnd = {
    if (nonEmpty(context.ruleName())) {
      halt("Need to see and example of this")
    }

    val ref: ISZ[QualifiedName] = context.ruleOwnedReferenceSubsetting() match {
      case s1: RuleOwnedReferenceSubsetting1Context =>
        ISZ(visitQualifiedName(s1.ruleQualifiedName()))
      case s2: RuleOwnedReferenceSubsetting2Context =>
        visitFeatureChain(s2.ruleOwnedFeatureChain().ruleFeatureChain())
    }

    reportError(isEmpty(context.ruleOwnedMultiplicity()), context, "Multiplicities are not currently supported")

    return ConnectorEnd(reference = ref)
  }

  private def visitFeatureChain(context: RuleFeatureChainContext): ISZ[QualifiedName] = {
    var chain = ISZ(visitQualifiedName(context.ruleOwnedFeatureChaining(0).ruleQualifiedName()))
    for (i <- 1 until context.ruleOwnedFeatureChaining().size()) {
      chain = chain :+ visitQualifiedName(context.ruleOwnedFeatureChaining(i).ruleQualifiedName())
    }
    return chain
  }

  private def visitNonOccurrenceUsageElement(visibility: Visibility.Type, o: RuleNonOccurrenceUsageElementContext): NonOccurrenceUsageElement = {
    o match {
      case i1: RuleNonOccurrenceUsageElement1Context =>
        return visitDefaultReferenceUsage(visibility, i1.ruleDefaultReferenceUsage())

      case i2: RuleNonOccurrenceUsageElement2Context =>
        return visitReferenceUsage(visibility, i2.ruleReferenceUsage())

      case i3: RuleNonOccurrenceUsageElement3Context =>
        return visitAttributeUsage(visibility, i3.ruleAttributeUsage())

      case i4: RuleNonOccurrenceUsageElement4Context =>
        val enumUsage = i4.ruleEnumerationUsage()
        reportError(enumUsage, "Need to handle non-occurrence enum usages")
        return Placeholders.NonOccurrenceUsageElementPlaceholder

      case i5: RuleNonOccurrenceUsageElement5Context =>
        val bindingConnector = i5.ruleBindingConnectorAsUsage()
        reportError(bindingConnector, "Need to handle non-occurrence binding connectors")
        return Placeholders.NonOccurrenceUsageElementPlaceholder

      case i6: RuleNonOccurrenceUsageElement6Context =>
        val successionUsage = i6.ruleSuccessionAsUsage()
        reportError(successionUsage, "Need to handle non-occurrence succession usages")
        return Placeholders.NonOccurrenceUsageElementPlaceholder

      case i7: RuleNonOccurrenceUsageElement7Context =>
        val extendedUsage = i7.ruleExtendedUsage()
        reportError(extendedUsage, "Need to handle non-occurrence extended usages")
        return Placeholders.NonOccurrenceUsageElementPlaceholder
    }
  }

  private def visitDefaultReferenceUsage(visibility: Visibility.Type, context: RuleDefaultReferenceUsageContext): DefaultReferenceUsage = {
    val refPrefix = visitRefPrefix(context.ruleRefPrefix())

    val (identification, specializations) = visitFeatureDeclaration(context.ruleUsageDeclaration().ruleFeatureDeclaration())

    val featureValue: Option[FeatureValue] =
      if (isEmpty(context.ruleValuePart())) {
        None()
      } else {
        Some(visitFeatureValue(context.ruleValuePart().ruleFeatureValue()))
      }

    val bodyItems = visitDefinitionBody(context.ruleUsageBody().ruleDefinitionBody())

    return DefaultReferenceUsage(
      visibility = visibility,
      prefix = refPrefix,
      identification = identification,
      specializations = specializations,
      featureValue = featureValue,
      definitionBodyItems = bodyItems
    )
  }

  def visitReferenceUsage(visibility: Visibility.Type, o: RuleReferenceUsageContext): ReferenceUsage = {
    val p = visitRefPrefix(o.ruleRefPrefix())
    val u = visitUsage(o.ruleUsage())

    return ReferenceUsage(
      visibility = visibility,
      prefix = p,
      identification = u.identification,
      specializations = u.specializations,
      definitionBodyItems = u.definitionBodyItems
    )
  }

  def visitAttributeUsage(visibility: Visibility.Type, o: RuleAttributeUsageContext): AttributeUsage = {

    val p = visitUsagePrefix(o.ruleUsagePrefix())
    val u = visitUsage(o.ruleUsage())

    return AttributeUsage(
      visibility = visibility,
      prefix = p,
      identification = u.identification,
      specializations = u.specializations,
      definitionBodyItems = u.definitionBodyItems
    )
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


  private def visitOccurrenceDefinitionPrefix(occurrenceDef: RuleOccurrenceDefinitionPrefixContext): OccurrenceDefinitionPrefix = {

    reportError(occurrenceDef.ruleLifeClassMembership() == null, occurrenceDef.ruleLifeClassMembership(),
      "Life Classes are not currently supported")
    reportError(occurrenceDef.ruleDefinitionExtensionKeyword().isEmpty, occurrenceDef,
      "Extension definitions are not currently supported")

    var isAbstract = F
    var isVariation = F
    if (occurrenceDef.ruleBasicDefinitionPrefix() != null) {
      occurrenceDef.ruleBasicDefinitionPrefix() match {
        case x: RuleBasicDefinitionPrefix1Context =>
          assert(x.K_ABSTRACT() != null) // sanity check
          isAbstract = T

        case x: RuleBasicDefinitionPrefix2Context =>
          assert(x.K_VARIATION() != null)
          isVariation = T
      }
    }

    return OccurrenceDefinitionPrefix(
      isAbstract = isAbstract,
      isVariation = isVariation
    )
  }

  private def visitFeatureSpecializationPart(fs: RuleFeatureSpecializationPartContext): ISZ[FeatureSpecialization] = {
    var ret: ISZ[FeatureSpecialization] = ISZ()
    fs match {
      case s1: RuleFeatureSpecializationPart1Context =>
        //ruleFeatureSpecialization+ ruleMultiplicityPart? ruleFeatureSpecialization* #ruleFeatureSpecializationPart1

        // iterate over children to preserve ordering
        for (c <- s1.children.asScala) {
          c match {
            case i: RuleFeatureSpecializationContext =>
              ret = ret :+ visitFeatureSpecialization(i)
            case i: RuleMultiplicityPartContext =>
              reportError(isEmpty(s1.ruleMultiplicityPart()), s1.ruleMultiplicityPart(), "Multiplicities are not currently supported")
          }
        }

      case s2: RuleFeatureSpecializationPart2Context =>
        reportError(s2.ruleMultiplicityPart(), "Not currently handling payload specialization")
    }
    return ret
  }

  def visitFeatureSpecialization(context: SysMLv2Parser.RuleFeatureSpecializationContext): FeatureSpecialization = {
    context match {
      case x: RuleFeatureSpecialization1Context =>
        // ruleTypings: ruleTypedBy (',' ruleFeatureTyping)*;
        // ruleTypedBy: ruleDefinedByKeyword ruleFeatureTyping;

        val typings = x.ruleTypings()
        val featureTypings: ISZ[RuleFeatureTypingContext] =
          ISZ(typings.ruleTypedBy().ruleFeatureTyping()) ++
            listToISZ(typings.ruleFeatureTyping())

        var types: ISZ[QualifiedName] = ISZ()
        for (t <- featureTypings) {
          t match {
            case t1: RuleFeatureTyping1Context =>
              t1.ruleOwnedFeatureTyping() match {
                case ot1: RuleOwnedFeatureTyping1Context =>
                  types = types :+ visitQualifiedName(ot1.ruleQualifiedName())
                case ot2: RuleOwnedFeatureTyping2Context =>
                  types = types ++ visitFeatureChain(ot2.ruleOwnedFeatureChain().ruleFeatureChain())
              }
            case t2: RuleFeatureTyping2Context =>
              reportError(t2, "Conjugated port typing is not currently supported")
          }
        }
        return TypingsSpecialization(types)

      case x: RuleFeatureSpecialization2Context =>
        // ruleSubsettings: ruleSubsets (',' ruleOwnedSubsetting)*;
        // ruleSubsets: ruleSubsetsKeyword ruleOwnedSubsetting;

        val subsettings = x.ruleSubsettings()
        val ownedSubsettings = ISZ(subsettings.ruleSubsets().ruleOwnedSubsetting()) ++ listToISZ(subsettings.ruleOwnedSubsetting())

        var subsets: ISZ[QualifiedName] = ISZ()
        for (s <- ownedSubsettings) {
          s match {
            case i: RuleOwnedSubsetting1Context =>
              subsets = subsets :+ visitQualifiedName(i.ruleQualifiedName())
            case i: RuleOwnedSubsetting2Context =>
              subsets = subsets ++ visitFeatureChain(i.ruleOwnedFeatureChain().ruleFeatureChain())
          }
        }
        return SubsettingsSpecialization(subsettings = subsets)

      case x: RuleFeatureSpecialization3Context =>
        // ruleReferences: ruleReferencesKeyword ruleOwnedReferenceSubsetting;

        val references = x.ruleReferences()
        reportError(references, "References are not currently supported")

        references.ruleOwnedReferenceSubsetting() match {
          case i: RuleOwnedReferenceSubsetting1Context =>
            return ReferencesSpecialization(ISZ(visitQualifiedName(i.ruleQualifiedName())))
          case i: RuleOwnedReferenceSubsetting2Context =>
            return ReferencesSpecialization(visitFeatureChain(i.ruleOwnedFeatureChain().ruleFeatureChain()))
        }

      case x: RuleFeatureSpecialization4Context =>
        // ruleRedefinitions: ruleRedefines (',' ruleOwnedRedefinition)*;
        // ruleRedefines: ruleRedefinesKeyword ruleOwnedRedefinition;

        val redefs = x.ruleRedefinitions()
        val ownedRedefinitions =
          ISZ(redefs.ruleRedefines().ruleOwnedRedefinition()) ++
            listToISZ(redefs.ruleOwnedRedefinition())

        var redefines: ISZ[QualifiedName] = ISZ()
        for (o <- ownedRedefinitions) {
          o match {
            case i: RuleOwnedRedefinition1Context =>
              redefines = redefines :+ visitQualifiedName(i.ruleQualifiedName())
            case i: RuleOwnedRedefinition2Context =>
              redefines = redefines ++ visitFeatureChain(i.ruleOwnedFeatureChain().ruleFeatureChain())
          }
        }
        return RedefinitionsSpecialization(redefines)

      case x => halt(s"Not expecting $x")
    }
  }

  private def visitUsagePrefix(o: RuleUsagePrefixContext): UsagePrefix = {

    val refPrefix = visitRefPrefix(o.ruleBasicUsagePrefix().ruleRefPrefix())

    var usageExtensions: ISZ[QualifiedName] = ISZ()
    if (!o.ruleUsageExtensionKeyword().isEmpty) {
      for (ue <- o.ruleUsageExtensionKeyword().asScala) {
        usageExtensions = usageExtensions :+
          visitQualifiedName(ue.rulePrefixMetadataMember().rulePrefixMetadataUsage().ruleMetadataTyping().ruleQualifiedName())
      }
    }

    return UsagePrefix(
      refPrefix = refPrefix,
      isRef = o.ruleBasicUsagePrefix().K_REF() != null,
      usageExtensions = usageExtensions)
  }

  private def visitRefPrefix(context: RuleRefPrefixContext): RefPrefix = {

    var direction: Option[FeatureDirection.Type] = None()
    if (nonEmpty(context.ruleFeatureDirection())) {
      context.ruleFeatureDirection() match {
        case x: RuleFeatureDirection1Context =>
          assert(x.K_IN() != null) // sanity
          direction = Some(FeatureDirection.In)
        case x: RuleFeatureDirection2Context =>
          assert(x.K_OUT() != null) // sanity
          direction = Some(FeatureDirection.Out)
        case x: RuleFeatureDirection3Context =>
          assert(x.K_INOUT() != null)
          direction = Some(FeatureDirection.InOut)
        case x =>
          reportError(x, s"Not expecting direction of type ${x.getClass.getSimpleName}")
      }
    }

    return RefPrefix(
      direction = direction,
      isAbstract = context.K_ABSTRACT() != null,
      isVariation = context.K_VARIATION() != null,
      isReadOnly = context.K_READONLY() != null,
      isDerived = context.K_DERIVED() != null,
      isEnd = context.K_END() != null)
  }

  private def visitOccurrenceUsagePrefix(ocup: RuleOccurrenceUsagePrefixContext): OccurrenceUsagePrefix = {
    val refPrefix = visitRefPrefix(ocup.ruleBasicUsagePrefix().ruleRefPrefix())
    val isRef = ocup.ruleBasicUsagePrefix().K_REF() != null

    var isSnapshot: B = F
    var isTimeslice: B = F
    if (nonEmpty(ocup.rulePortionKind())) {
      ocup.rulePortionKind() match {
        case i: RulePortionKind1Context =>
          assert(i.K_SNAPSHOT() != null)
          isSnapshot = T
        case i: RulePortionKind2Context =>
          assert(i.K_TIMESLICE() != null)
          isTimeslice = T
      }
    }

    var usageExtensions: ISZ[QualifiedName] = ISZ()
    if (!ocup.ruleUsageExtensionKeyword().isEmpty) {
      for (ue <- ocup.ruleUsageExtensionKeyword().asScala) {
        usageExtensions = usageExtensions :+
          visitQualifiedName(ue.rulePrefixMetadataMember().rulePrefixMetadataUsage().ruleMetadataTyping().ruleQualifiedName())
      }
    }

    return OccurrenceUsagePrefix(
      refPrefix = refPrefix,
      isRef = isRef,
      isIndividual = ocup.K_INDIVIDUAL() != null,
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
    return ISZ(l.asScala.toSeq: _*)
  }

  def visitAlias(alias: RuleAliasMemberContext): Alias = {
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

    return Alias(visibility = visibility, identification = id, target = target)
  }

  def visitImport(importe: RuleImportContext): Import = {
    var annotations: ISZ[AnnotatingElement] = ISZ()
    importe.ruleRelationshipBody() match {
      case rb1: RuleRelationshipBody1Context =>
        assert(rb1.OP_SEMI() != null) // sanity check

      case rb2: RuleRelationshipBody2Context =>
        for (oa <- rb2.ruleOwnedAnnotation().asScala if !SysmlAstUtil.isRegularComment(oa.ruleAnnotatingElement())) {
          annotations = annotations :+ visitAnnotatingElement(oa.ruleAnnotatingElement())
        }
    }

    if (nonEmpty(importe.ruleMembershipImport())) {
      assert(isEmpty(importe.ruleNamespaceImport()))

      val mimport = importe.ruleMembershipImport()

      val visibility = visitVisibilityIndicator(mimport.ruleImportPrefix().ruleVisibilityIndicator())

      // 'all' is not detailed in the spec
      reportError(mimport.ruleImportPrefix().K_ALL() == null, mimport,
        "Not currently handling 'all' import annotations")

      val qualifiedName = visitQualifiedName(mimport.ruleImportedMembership().ruleQualifiedName())

      return Import(
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

      reportError(isEmpty(nimport.ruleFilterPackage()), nimport.ruleFilterPackage(), "Not currently handling filtered imports")

      val namespace = nimport.ruleImportedNamespace()

      val qualifiedName = visitQualifiedName(namespace.ruleQualifiedName())

      assert(nimport.ruleImportedNamespace().OP_STAR() != null, "The star is not optional")

      return Import(
        visibility = visibility,
        qualifiedName = qualifiedName,
        star = T,
        starStar = nimport.ruleImportedNamespace().OP_STAR_STAR() != null,
        annotations = annotations
      )
    }
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

  def visitQualifiedNameAsId(name: RuleQualifiedNameContext): ISZ[AST.Id] = {
    var ret: ISZ[AST.Id] = ISZ()
    if (name.ruleQualification() != null) {
      ret = ret ++ (for (seg <- listToISZ(name.ruleQualification().ruleName())) yield visitNameAsId(seg))
    }
    return ret :+ visitNameAsId(name.ruleName())
  }

  def visitNameAsId(name: RuleNameContext): AST.Id = {
    name match {
      case r1: RuleName1Context => return AST.Id(value = r1.getText, attr = AST.Attr(posOpt = toPosOpt(name)))
      case r2: RuleName2Context => return AST.Id(value = s"`${r2.getText}`", attr = AST.Attr(posOpt = toPosOpt(name)))
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
      case v2: RuleVisibilityIndicator2Context =>
        assert(v2.K_PRIVATE() != null)
        return Visibility.Private
      case v3: RuleVisibilityIndicator3Context =>
        assert(v3.K_PROTECTED() != null)
        return Visibility.Protected
      case x =>
        reportError(x, s"Not expecting visibility: $x ")
        return Visibility.Public
    }
  }

  def visitOwnedExpression(o: SysMLv2Parser.RuleOwnedExpressionContext): AST.Exp = {
    o.ruleConditionalExpression() match {
      case e1: RuleConditionalExpression1Context =>
        return visitNullCoalescingExpression(e1.ruleNullCoalescingExpression())

      case e2: RuleConditionalExpression2Context =>
        assert(nonEmpty(e2.ruleConditionalOperator()))
        assert(e2.ruleOwnedExpressionReference().size == 2)

        val cond = visitNullCoalescingExpression(e2.ruleNullCoalescingExpression())
        val thenExp = visitOwnedExpression(e2.ruleOwnedExpressionReference(0).ruleOwnedExpressionMember().ruleOwnedExpression())
        val elseExp = visitOwnedExpression(e2.ruleOwnedExpressionReference(1).ruleOwnedExpressionMember().ruleOwnedExpression())

        return Exp.If(cond = cond, thenExp = thenExp, elseExp = elseExp, attr = Placeholders.emptyTypedAttr)
    }
  }

  def visitNullCoalescingExpression(o: RuleNullCoalescingExpressionContext): AST.Exp = {
    reportError(o.ruleNullCoalescingOperator().isEmpty, o, "Null coalescing expressions are not currently supported")

    return visitImpliesExpression(o.ruleImpliesExpression())
  }

  // ruleImpliesExpression: ruleOrExpression ( ruleImpliesOperator ruleOrExpressionReference)*;
  private def visitImpliesExpression(o: RuleImpliesExpressionContext): AST.Exp = {
    // TODO it appears KerML only supports short circuit implications

    val lhs = visitOrExpression(o.ruleOrExpression())

    val s = Stack(for (oe <- listToISZ(o.ruleOrExpressionReference())) yield visitOrExpression(oe.ruleOrExpressionMember().ruleOrExpression()))

    return SysmlAstUtil.collapse1(lhs, AST.Exp.BinaryOp.CondImply, s)
  }

  // ruleOrExpression: ruleXorExpression ( (ruleOrOperator ruleXorExpression | ruleConditionalOrOperator ruleXorExpressionReference))*;
  private def visitOrExpression(o: RuleOrExpressionContext): AST.Exp = {
    assert(o.getChild(0).isInstanceOf[RuleXorExpressionContext])
    val lhs = visitXorExpression(o.ruleXorExpression(0))

    var s = Stack.empty[(String, AST.Exp)]
    var i = 1
    while (i < o.getChildCount) {
      o.getChild(i) match {
        case isLogicalOr: RuleOrOperatorContext =>
          s = s.push(AST.Exp.BinaryOp.Or, visitXorExpression(o.getChild(i + 1).asInstanceOf[SysMLv2Parser.RuleXorExpressionContext]))
        case isCondOr: RuleConditionalOrOperatorContext =>
          s = s.push(AST.Exp.BinaryOp.CondOr, visitXorExpression(o.getChild(i + 1).asInstanceOf[RuleXorExpressionReferenceContext].ruleXorExpressionMember().ruleXorExpression()))
      }
      i = i + 2
    }

    return SysmlAstUtil.collapse2(lhs, s)
  }

  // ruleXorExpression: ruleAndExpression ( ruleXorOperator ruleAndExpression)*;
  private def visitXorExpression(o: RuleXorExpressionContext): AST.Exp = {
    val exprs = ops.ISZOps(listToISZ(o.ruleAndExpression()))
    val lhs = visitAndExpression(exprs.first)

    var s = Stack(for (e <- exprs.tail) yield visitAndExpression(e))

    return SysmlAstUtil.collapse1(lhs, AST.Exp.BinaryOp.Xor, s)
  }

  private def visitAndExpression(o: RuleAndExpressionContext): AST.Exp = {
    assert(o.getChild(0).isInstanceOf[RuleEqualityExpressionContext])
    val lhs = visitEqualityExpression(o.ruleEqualityExpression(0))

    var s = Stack.empty[(String, AST.Exp)]
    var i = 1
    while (i < o.getChildCount) {
      o.getChild(i) match {
        case isLogicalAnd: RuleAndOperatorContext =>
          s = s.push((AST.Exp.BinaryOp.And, visitEqualityExpression(o.getChild(i + 1).asInstanceOf[RuleEqualityExpressionContext])))
        case isCondAnd: RuleConditionalAndOperatorContext =>
          s = s.push((AST.Exp.BinaryOp.CondAnd, visitEqualityExpression(o.getChild(i + 1).asInstanceOf[RuleEqualityExpressionReferenceContext].ruleEqualityExpressionMember().ruleEqualityExpression())))
      }
      i = i + 2
    }
    return SysmlAstUtil.collapse2(lhs, s)
  }

  // ruleEqualityExpression: ruleClassificationExpression ( ruleEqualityOperator ruleClassificationExpression)*;
  private def visitEqualityExpression(o: RuleEqualityExpressionContext): AST.Exp = {
    val lhs = visitClassificationExpression(o.ruleClassificationExpression(0))

    var s = Stack.empty[(String, AST.Exp)]
    var i = 1
    while (i < o.getChildCount) {
      /* The operators == and != apply to operands that have single values, testing whether they are equal or unequal,
       * respectively. They also evaluate to true or false, respectively, if their operands are both null (no values).
       * The operators === and !== apply specifically to values that are occurrences (see 9.2.4). They test whether two
       * occurrences are portions (in space and/or time) of the same life occurrence. Informally, these operators test
       * whether or not two occurrences have the same "identity". For data values (values that are not occurrences),
       * === and !== are the same as == and !=.
       */
      val binaryOp: String = o.getChild(i).asInstanceOf[RuleEqualityOperatorContext] match {
        case i: RuleEqualityOperator1Context =>
          assert(i.OP_EQ_EQ() != null)
          AST.Exp.BinaryOp.Eq
        case i: RuleEqualityOperator2Context =>
          assert(i.OP_BANG_EQ() != null)
          AST.Exp.BinaryOp.Ne
        case i: RuleEqualityOperator3Context =>
          assert(i.OP_EQ_EQ_EQ() != null)
          reportError(i, "TODO, can sysmlv2's === be mapped to Slang's ===")
          AST.Exp.BinaryOp.Equiv
        case i: RuleEqualityOperator4Context =>
          assert(i.OP_BANG_EQ_EQ() != null)
          reportError(i, "TODO, can sysmlv2's !== be mapped to Slang's =!=")
          AST.Exp.BinaryOp.Inequiv
      }
      s = s.push((binaryOp, visitClassificationExpression(o.getChild(i + 1).asInstanceOf[RuleClassificationExpressionContext])))
      i = i + 2
    }

    return SysmlAstUtil.collapse2(lhs, s)
  }

  private def visitClassificationExpression(o: RuleClassificationExpressionContext): AST.Exp = {
    o match {
      case i1: RuleClassificationExpression1Context =>
        val lhs = visitRelationalExpression(i1.ruleRelationalExpression())

        if (i1.ruleClassificationTestOperator() != null) {
          reportError(i1.ruleClassificationTestOperator(), "Classification Test operations are not currently supported")
        } else if (i1.ruleCastOperator() != null) {
          reportError(i1.ruleCastOperator(), "Cast operations are not currently supported")
        }

        return lhs

      case i2: RuleClassificationExpression2Context =>
        reportError(o, "Need example of ruleClassification2")

      case i3: RuleClassificationExpression3Context =>
        assert(nonEmpty(i3.ruleMetaClassificationTestOperator()))
        reportError(o, "MetaClassification Test '@@' is not currently supported")

      case i4: RuleClassificationExpression4Context =>
        reportError(o, "Need example of ruleClassification4")

      case i5: RuleClassificationExpression5Context =>
        assert(nonEmpty(i5.ruleMetaCastOperator()))
        reportError(o, "MetaCast 'meta' is not currently supported")
    }
    return Placeholders.emptyExp
  }

  // ruleRelationalExpression: ruleRangeExpression ( ruleRelationalOperator ruleRangeExpression)*;
  private def visitRelationalExpression(o: RuleRelationalExpressionContext): AST.Exp = {
    val lhs = visitRangeExpression(o.ruleRangeExpression(0))

    var s = Stack.empty[(String, AST.Exp)]
    var i = 1
    while (i < o.getChildCount) {
      val binaryOp: String = o.getChild(i).asInstanceOf[RuleRelationalOperatorContext] match {
        case i: RuleRelationalOperator1Context =>
          assert(i.LANGLE() != null)
          AST.Exp.BinaryOp.Lt
        case i: RuleRelationalOperator2Context =>
          assert(i.RANGLE() != null)
          AST.Exp.BinaryOp.Gt
        case i: RuleRelationalOperator3Context =>
          assert(i.OP_LANGLE_EQ() != null)
          AST.Exp.BinaryOp.Le
        case i: RuleRelationalOperator4Context =>
          assert(i.OP_RANGLE_EQ() != null)
          AST.Exp.BinaryOp.Ge
      }
      s = s.push((binaryOp, visitRangeExpression(o.getChild(i + 1).asInstanceOf[RuleRangeExpressionContext])))
      i = i + 2
    }

    return SysmlAstUtil.collapse2(lhs, s)
  }

  // ruleRangeExpression: ruleAdditiveExpression ( '..' ruleAdditiveExpression)?;
  private def visitRangeExpression(o: RuleRangeExpressionContext): AST.Exp = {
    val lhs = visitAdditiveExpression(o.ruleAdditiveExpression(0))

    if (o.ruleAdditiveExpression().size() > 1) {
      reportError(o, "Range expressions are not currently handled")
    }

    return lhs
  }

  // ruleAdditiveExpression: ruleMultiplicativeExpression ( ruleAdditiveOperator ruleMultiplicativeExpression)*;
  private def visitAdditiveExpression(o: RuleAdditiveExpressionContext): AST.Exp = {
    val lhs = visitMultiplicativeExpression(o.ruleMultiplicativeExpression(0))

    var s = Stack.empty[(String, AST.Exp)]
    var i = 1
    while (i < o.getChildCount) {
      val binaryOp: String = o.getChild(i).asInstanceOf[RuleAdditiveOperatorContext] match {
        case i: RuleAdditiveOperator1Context =>
          assert(i.OP_PLUS() != null)
          AST.Exp.BinaryOp.Add
        case i: RuleAdditiveOperator2Context =>
          assert(i.OP_MINUS() != null)
          AST.Exp.BinaryOp.Sub
      }
      s = s.push((binaryOp, visitMultiplicativeExpression(o.getChild(i + 1).asInstanceOf[RuleMultiplicativeExpressionContext])))
      i = i + 2
    }

    return SysmlAstUtil.collapse2(lhs, s)
  }

  // ruleMultiplicativeExpression: ruleExponentiationExpression ( ruleMultiplicativeOperator ruleExponentiationExpression)*;
  private def visitMultiplicativeExpression(o: RuleMultiplicativeExpressionContext): AST.Exp = {
    val lhs = visitExponentiationExpression(o.ruleExponentiationExpression(0))

    var s = Stack.empty[(String, AST.Exp)]
    var i = 1
    while (i < o.getChildCount) {
      val binaryOp: String = o.getChild(i).asInstanceOf[RuleMultiplicativeOperatorContext] match {
        case i: RuleMultiplicativeOperator1Context =>
          assert(i.OP_STAR() != null)
          AST.Exp.BinaryOp.Mul
        case i: RuleMultiplicativeOperator2Context =>
          assert(i.OP_SLASH() != null)
          AST.Exp.BinaryOp.Div
        case i: RuleMultiplicativeOperator3Context =>
          assert(i.OP_PERCENT() != null)
          AST.Exp.BinaryOp.Rem
      }
      s = s.push((binaryOp, visitExponentiationExpression(o.getChild(i + 1).asInstanceOf[RuleExponentiationExpressionContext])))
      i = i + 2
    }

    return SysmlAstUtil.collapse2(lhs, s)
  }

  private def visitExponentiationExpression(o: RuleExponentiationExpressionContext): AST.Exp = {
    val lhs = visitUnaryExpression(o.ruleUnaryExpression())

    if (nonEmpty(o.ruleExponentiationOperator())) {
      // TODO: perhaps uif?
      reportError(o.ruleExponentiationOperator(), "Exponentiation operations are not currently supported")
    }

    return lhs
  }

  private def visitUnaryExpression(o: RuleUnaryExpressionContext): AST.Exp = {
    o match {
      case u1: RuleUnaryExpression1Context =>
        val op = u1.ruleUnaryOperator() match {
          case i: RuleUnaryOperator1Context =>
            assert(i.OP_PLUS() != null)
            AST.Exp.UnaryOp.Plus
          case i: RuleUnaryOperator2Context =>
            assert(i.OP_MINUS() != null)
            AST.Exp.UnaryOp.Minus
          case i: RuleUnaryOperator3Context =>
            assert(i.OP_TILDE() != null)
            AST.Exp.UnaryOp.Complement
          case i: RuleUnaryOperator4Context =>
            assert(i.K_NOT() != null)
            AST.Exp.UnaryOp.Not
        }
        val exp = visitExtentExpression(u1.ruleExtentExpression())
        return AST.Exp.Unary(op = op, exp = exp, attr = Placeholders.emptyResolvedAttr(toPosOpt(o)), opPosOpt = toPosOpt(o))

      case u2: RuleUnaryExpression2Context =>
        return visitExtentExpression(u2.ruleExtentExpression())
    }
  }

  private def visitExtentExpression(o: RuleExtentExpressionContext): AST.Exp = {
    o match {
      case i: RuleExtentExpression1Context =>
        assert(i.K_ALL() != null)
        reportError(i, "Extend expressions are not currently handled")
        return Placeholders.emptyExp

      case i: RuleExtentExpression2Context =>
        return visitPrimaryExpression(i.rulePrimaryExpression())
    }
  }

  // rulePrimaryExpression:
  //   ruleBaseExpression ( '.' ruleFeatureChainMember )?
  //   ( ( '#' '(' ruleSequenceExpression ')'
  //     | '[' ruleSequenceExpression ']'
  //     | '->' ruleReferenceTyping (ruleBodyExpression | ruleFunctionReferenceExpression | ruleArgumentList)
  //     | '.' ruleBodyExpression
  //     | '.?' ruleBodyExpression
  //     )
  //     ( '.' ruleFeatureChainMember)?
  //   )*;
  private def visitPrimaryExpression(o: RulePrimaryExpressionContext): AST.Exp = {
    var baseExp = visitBaseExpression(o.ruleBaseExpression())

    var ret = baseExp
    if (o.children.size() > 1) {
      var index = 1
      o.getChild(index) match {
        case i: TerminalNodeImpl if i.getText == "." && o.getChild(index + 1).isInstanceOf[RuleFeatureChainMemberContext] =>
          // ( '.' ruleFeatureChainMember )?
          index = index + 1
          o.getChild(index) match {
            case i: RuleFeatureChainMember1Context =>
              // e.g.  e.id
              val id = visitQualifiedNameAsId(i.ruleQualifiedName())
              assert(id.size == 1)

              baseExp = AST.Exp.Select(
                receiverOpt = Some(baseExp),
                id = id(0),
                targs = ISZ(),
                attr = Placeholders.emptyResolvedAttr(mergePos(baseExp.posOpt, id(0).attr.posOpt))
              )
            case i: RuleFeatureChainMember2Context =>
              // e.blah::x.blah2::y
              halt("todo, need example of this")
          }
          index = index + 1
        case _ =>
      }

      assert(o.getChildCount == o.children.size())

      ret = baseExp

      while (index < o.getChildCount) {

        o.getChild(index) match {
          case i: TerminalNodeImpl if i.getText == "#" =>
            // ( '#' '(' ruleSequenceExpression ')'
            val se = visitSequenceExpression(o.getChild(index + 2).asInstanceOf[RuleSequenceExpressionContext])
            index = index + 4
            halt("")
          case i: TerminalNodeImpl if i.getText == "[" =>
            //     | '[' ruleSequenceExpression ']'
            val se = visitSequenceExpression(o.getChild(index + 1).asInstanceOf[RuleSequenceExpressionContext])
            reportWarn(o.getChild(index + 1), s"The sequencing expression '$se' is currently ignored")
            index = index + 3

          case i: TerminalNodeImpl if i.getText == "->" =>
            //     | '->' ruleReferenceTyping (ruleBodyExpression | ruleFunctionReferenceExpression | ruleArgumentList)
            val rt = visitReferenceTyping(o.getChild(index + 1).asInstanceOf[RuleReferenceTypingContext])
            val subExp = o.getChild(index + 2) match {
              case bec: RuleBodyExpressionContext =>
                visitBodyExpression(bec)
              case fre: RuleFunctionReferenceExpressionContext =>
                visitFunctionReferenceExpression(fre)
              case al: RuleArgumentListContext =>
                visitArgumentList(al)
            }
            index = index + 3
            halt("Need example of this")
          case i: TerminalNodeImpl if i.getText == "." =>
            o.getChild(index + 1) match {
              case i: TerminalNodeImpl =>
                //     | '.?' ruleBodyExpression
                assert(i.getText == "?")
                val be = visitBodyExpression(o.getChild(index + 2).asInstanceOf[RuleBodyExpressionContext])
                index = index + 3
                halt("Need example of this")
              case i =>
                //     | '.' ruleBodyExpression
                val be = visitBodyExpression(i.asInstanceOf[RuleBodyExpressionContext])
                index = index + 2
                halt("Need example of this")
            }
          case x =>
            halt(s"wasn't expecting ${x}")
        }

        if (index < o.getChildCount) {
          o.getChild(index) match {
            case i: TerminalNodeImpl if i.getText == "." && o.getChild(index + 1).isInstanceOf[RuleFeatureChainMemberContext] =>
              //     ( '.' ruleFeatureChainMember)?
              index = index + 1
              // blah
              index = index + 1
              halt("Need example of this")

            case _ =>
          }
        }

      }
    }

    return ret
  }

  private def visitArgumentList(al: RuleArgumentListContext): AST.Exp = {
    halt("todo")
  }

  private def visitFunctionReferenceExpression(fre: RuleFunctionReferenceExpressionContext): AST.Exp = {
    halt("todo")
  }

  private def visitReferenceTyping(context: RuleReferenceTypingContext): AST.Exp = {
    halt("todo")
  }

  private def visitBaseExpression(o: RuleBaseExpressionContext): AST.Exp = {
    o match {
      case i: RuleBaseExpression1Context =>
        return visitNullExpression(i.ruleNullExpression())

      case i: RuleBaseExpression2Context =>
        return visitLiteralExpression(i.ruleLiteralExpression())

      case i: RuleBaseExpression3Context =>
        val s = scala.collection.mutable.Stack.from[Any](visitQualifiedNameAsId(i.ruleFeatureReferenceExpression().ruleFeatureReferenceMember().ruleQualifiedName()).elements)

        if (s.size > 1) {
          val a = s.pop()
          val b = s.pop().asInstanceOf[AST.Id]

          a match {
            case aAsId: AST.Id =>
              val posOpt = SysmlAstUtil.mergePos(aAsId.attr.posOpt, b.attr.posOpt)
              val ident = AST.Exp.Ident(id = aAsId, attr = Placeholders.emptyResolvedAttr(posOpt))
              s.push(AST.Exp.Select(receiverOpt = Some(ident), id = b, targs = ISZ(), attr = Placeholders.emptyResolvedAttr(posOpt)))
            case aAsSelect: AST.Exp.Select =>
              val posOpt = SysmlAstUtil.mergePos(aAsSelect.posOpt, b.attr.posOpt)
              s.push(AST.Exp.Select(receiverOpt = Some(aAsSelect), id = b, targs = ISZ(), attr = Placeholders.emptyResolvedAttr(posOpt)))
          }
          return s.pop().asInstanceOf[AST.Exp.Select]
        } else {
          val id = s.pop().asInstanceOf[AST.Id]
          return AST.Exp.Ident(id = id, attr = Placeholders.emptyResolvedAttr(id.attr.posOpt))
        }

      case i: RuleBaseExpression4Context =>
        return visitMetadataAccessExpression(i.ruleMetadataAccessExpression())

      case i: RuleBaseExpression5Context =>
        return visitInvocationExpression(i.ruleInvocationExpression())

      case i: RuleBaseExpression6Context =>
        return visitBodyExpression(i.ruleBodyExpression())

      case i: RuleBaseExpression7Context =>
        return visitSequenceExpression(i.ruleSequenceExpression())
    }
  }

  private def visitSequenceExpression(o: RuleSequenceExpressionContext): AST.Exp = {
    val oe = visitOwnedExpression(o.ruleOwnedExpression())

    if (o.getChildCount > 1) {
      reportError(o, "comma separated sequencing expressions are not currently supported")
    }

    return oe
  }

  private def visitBodyExpression(o: RuleBodyExpressionContext): AST.Exp = {
    halt("")
  }

  private def visitInvocationExpression(o: RuleInvocationExpressionContext): AST.Exp = {
    halt("")
  }

  private def visitMetadataAccessExpression(o: RuleMetadataAccessExpressionContext): AST.Exp = {
    halt("")
  }

  private def visitLiteralExpression(o: RuleLiteralExpressionContext): AST.Exp = {
    o match {
      case i: RuleLiteralExpression1Context =>
        i.ruleLiteralBoolean().ruleBooleanValue() match {
          case i: RuleBooleanValue1Context =>
            assert(i.K_TRUE() != null)
            return AST.Exp.LitB(value = T, attr = Placeholders.emptyAttr)
          case i: RuleBooleanValue2Context =>
            assert(i.K_FALSE() != null)
            return AST.Exp.LitB(value = F, attr = Placeholders.emptyAttr)
        }

      case i: RuleLiteralExpression2Context =>
        return AST.Exp.LitString(value = i.ruleLiteralString().RULE_STRING_VALUE().string, attr = Placeholders.emptyAttr)

      case i: RuleLiteralExpression3Context =>
        return AST.Exp.LitZ(value = Z(i.ruleLiteralInteger().RULE_DECIMAL_VALUE().string).get, attr = Placeholders.emptyAttr)

      case i: RuleLiteralExpression4Context =>
        /* ruleRealValue:
         *   RULE_DECIMAL_VALUE? '.' (RULE_DECIMAL_VALUE | RULE_EXP_VALUE) #ruleRealValue1
         *   | RULE_EXP_VALUE #ruleRealValue2;
         */
        i.ruleLiteralReal().ruleRealValue() match {
          case i: RuleRealValue1Context =>
            halt("???")
          case i: RuleRealValue2Context =>
            halt("???")
        }

      case i: RuleLiteralExpression5Context =>
        // TODO perhaps uif
        reportError(i, "Infinity is not currently supported")
        return Placeholders.emptyExp
    }
  }

  private def visitNullExpression(o: RuleNullExpressionContext): AST.Exp = {
    halt("")
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
      reporter.warn(toPosOpt(o.get), kind, message)
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
