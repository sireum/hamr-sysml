package org.sireum.hamr.sysml.parser

import org.sireum._
import org.sireum.message._
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.TerminalNodeImpl
import org.sireum.hamr.sysml.parser.SysMLAstBuilder.{binOpsUifs, interpolates, isReservedSequenceName, logikaUifs, numeric_interpolates, portUifs}
import org.sireum.hamr.ir.SysmlAst._
import org.sireum.hamr.ir.{Attr, GclAssume, GclCaseStatement, GclCompute, GclComputeSpec, GclGuarantee, GclHandle, GclInitialize, GclIntegration, GclInvariant, GclLib, GclMethod, GclSpec, GclStateVar, GclSubclause, InfoFlowClause, ResolvedAttr, Name => AirName}
import org.sireum.hamr.sysml.parser.SysmlAstUtil.isRegularComment
import org.sireum.hamr.sysml.parser.SlangUtil.{Placeholders, mergePos}
import org.sireum.hamr.sysml.parser.SysMLv2Parser._
import org.sireum.message.{Position, Reporter}
import org.sireum.lang.{ast => AST}
import org.sireum.lang.ast.Exp

import java.util
import scala.jdk.CollectionConverters._

object SysMLAstBuilder {

  def build(tree: ParserRuleContext, uriOpt: Option[String], isSysML: B, reporter: Reporter): Option[TopUnit] = {
    if (isSysML) {
      assert(!reporter.hasError)
      val builder = SysMLAstBuilder(uriOpt)
      val root = builder.visitEntryRuleRootNamespace(tree.asInstanceOf[SysMLv2Parser.EntryRuleRootNamespaceContext])
      reporter.reports(builder.reporter.messages)
      return Some(root)
    } else {
      reporter.warn(None(), "SysMLAstBuilder", "Need to handle kerml")
      return None()
    }
  }

  val nonnumeric_interpolates: ISZ[String] = ISZ("c", "string")

  val numeric_interpolates: ISZ[String] = {

    var ret = ISZ[String](
      "z",
      "z8",
      "z16",
      "z32",
      "z64",

      "s8",
      "s16",
      "s32",
      "s64",

      "n",
      "n8",
      "n16",
      "n32",
      "n64",

      "r",

      "f32",
      "f64"
    )
    for (i <- 1 to 64) {
      ret = ret :+ s"u$i"
    }
    ret
  }

  val interpolates: ISZ[String] = nonnumeric_interpolates ++ numeric_interpolates

  val portUifs: ISZ[String] = ISZ(
    "MaySend",
    "MustSend",
    "NoSend",
    "HasEvent")

  val binOpsUifs: ISZ[String] = ISZ(
    "'->:'",
    "'-->:'" // KerML has short circuit implication but provide this uif for symmetry
  )

  val logikaUifs: ISZ[String] = ISZ(
    "In"
  )

  val reservedUifNames: ops.ISZOps[String] = ops.ISZOps(portUifs ++ binOpsUifs ++ logikaUifs)

  val reservedSequenceNames: ops.ISZOps[String] = ops.ISZOps(interpolates)

  def isReservedUifName(s: String): B = {
    return reservedUifNames.contains(s)
  }

  def isReservedSequenceName(s: String): B = {
    return reservedSequenceNames.contains(s)
  }

}

case class SysMLAstBuilder(uriOpt: Option[String]) {
  val reporter: Reporter = Reporter.create
  val kind: String = "SysMLResolver"

  def visitEntryRuleRootNamespace(ctx: EntryRuleRootNamespaceContext): TopUnit = {

    var packageBodyElements: ISZ[PackageBodyElement] = ISZ()
    for (packageBodyElementNode <- ctx.ruleRootNamespace().rulePackageBodyElement().asScala) {
      packageBodyElementNode match {
        case r1: RulePackageBodyElement1Context =>

          visitPackageMember(r1.rulePackageMember()) match {
            case x if x != Placeholders.emptyDefinitionElement => packageBodyElements = packageBodyElements :+ x
            case _ =>
          }

        case r2: RulePackageBodyElement2Context =>
          val m2 = r2.ruleElementFilterMember()
          reportError(m2, "Not currently supporting filter members")

        case r3: RulePackageBodyElement3Context =>
          packageBodyElements = packageBodyElements :+ visitAliasMember(r3.ruleAliasMember())

        case r4: RulePackageBodyElement4Context =>
          packageBodyElements = packageBodyElements :+ visitImport(r4.ruleImport())

      }
    }
    return TopUnit(fileUri = uriOpt, packageBodyElements = packageBodyElements)
  }

  def visitPackageMember(packageBodyMember: RulePackageMemberContext): PackageMember = {
    // rulePackageMember: ruleMemberPrefix (ruleDefinitionElement | ruleUsageElement);
    val visibility = visitVisibilityIndicator(packageBodyMember.ruleMemberPrefix().ruleVisibilityIndicator())
    if (packageBodyMember.ruleDefinitionElement() != null) {
      assert(packageBodyMember.ruleUsageElement() == null)
      return visitDefinitionElement(visibility, packageBodyMember.ruleDefinitionElement())
    } else {
      return visitUsageElement(visibility, packageBodyMember.ruleUsageElement())
    }
  }

  def visitDefinitionElement(visibility: Visibility.Type, o: RuleDefinitionElementContext): DefinitionElement = {
    o match {
      case d1: RuleDefinitionElement1Context =>
        return helper_definitionElementPackage(
          visibility = visibility,
          standard = F,
          library = F,
          prefixMetadataMembers = d1.rulePackage().rulePrefixMetadataMember(),
          identification = d1.rulePackage().rulePackageDeclaration().ruleIdentification(),
          body = d1.rulePackage().rulePackageBody(),
          parent = o)

      case d2: RuleDefinitionElement2Context =>
        assert(d2.ruleLibraryPackage().K_LIBRARY() != null)

        return helper_definitionElementPackage(
          visibility = visibility,
          standard = d2.ruleLibraryPackage().K_STANDARD() != null,
          library = T,
          prefixMetadataMembers = d2.ruleLibraryPackage().rulePrefixMetadataMember(),
          identification = d2.ruleLibraryPackage().rulePackageDeclaration().ruleIdentification(),
          body = d2.ruleLibraryPackage().rulePackageBody(),
          parent = o)

      case d3: RuleDefinitionElement3Context =>

        if (!SysmlAstUtil.isRegularComment(d3.ruleAnnotatingElement())) {
          return visitAnnotatingElement(visibility, d3.ruleAnnotatingElement())
        } else {
          return Placeholders.emptyDefinitionElement
        }

      case d5: RuleDefinitionElement5Context =>
        return visitAttributeDefinition(visibility, d5.ruleAttributeDefinition())

      case d6: RuleDefinitionElement6Context =>
        return visitEnumerationDefinition(visibility, d6.ruleEnumerationDefinition())

      case d11: RuleDefinitionElement11Context =>
        return visitPartDefinition(visibility, d11.rulePartDefinition())

      case d12: RuleDefinitionElement12Context =>
        return visitConnectionDefinition(visibility, d12.ruleConnectionDefinition())

      case d15: RuleDefinitionElement15Context =>
        return visitAllocationDefinition(visibility, d15.ruleAllocationDefinition())

      case d16: RuleDefinitionElement16Context =>
        return visitPortDefinition(visibility, d16.rulePortDefinition())

      case x =>
        reportError(x, s"Not currently handling the following definition element: ${x.getClass.getSimpleName}")
        return Placeholders.emptyDefinitionElement
    }
  }

  private def visitAllocationDefinition(visibility: Visibility.Type, context: RuleAllocationDefinitionContext): AllocationDefinition = {
    val occurDef = visitOccurrenceDefinitionPrefix(context.ruleOccurrenceDefinitionPrefix())

    val (identification, subClassifications, bodyItems) = visitDefinition(context.ruleDefinition())

    return AllocationDefinition(
      visibility = visibility,
      occurrenceDefPrefix = occurDef,
      identification = identification,
      subClassifications = subClassifications,
      parents = ISZ(),
      bodyItems = bodyItems,
      attr = toAttr(context)
    )
  }

  private def visitAttributeDefinition(visibility: Visibility.Type, o: RuleAttributeDefinitionContext): AttributeDefinition = {
    val defPrefix = visitDefinitionPrefix(o.ruleDefinitionPrefix())

    val (identifier, subClassifications, bodyItems) = visitDefinition(o.ruleDefinition())

    return AttributeDefinition(
      visibility = visibility,
      defPrefix = defPrefix,
      identification = identifier,
      subClassifications = subClassifications,
      parents = ISZ(),
      bodyItems = bodyItems,
      attr = toAttr(o)
    )
  }

  private def visitConnectionDefinition(visibility: Visibility.Type, o: RuleConnectionDefinitionContext): ConnectionDefinition = {
    val occurrenceDefPrefix = visitOccurrenceDefinitionPrefix(o.ruleOccurrenceDefinitionPrefix())

    val (identification, subClassifications, bodyItems) = visitDefinition(o.ruleDefinition())

    return ConnectionDefinition(
      visibility = visibility,
      occurrenceDefPrefix = occurrenceDefPrefix,
      identification = identification,
      subClassifications = subClassifications,
      parents = ISZ(),
      bodyItems = bodyItems,
      attr = toAttr(o)
    )
  }

  private def helper_definitionElementPackage(visibility: Visibility.Type,
                                              standard: B,
                                              library: B,
                                              prefixMetadataMembers: util.List[RulePrefixMetadataMemberContext],
                                              identification: RuleIdentificationContext,
                                              body: RulePackageBodyContext,
                                              parent: RuleDefinitionElementContext): DefinitionElement = {

    if (!prefixMetadataMembers.isEmpty) {
      reportError(parent, "Package metadata not currently supported")
    }

    val id: Option[Identification] =
      if (isEmpty(identification)) None()
      else Some(visitIdentification(identification))

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
                visitDefinitionElement(visibility, i.ruleDefinitionElement()) match {
                  case x if x != Placeholders.emptyDefinitionElement => packageElements = packageElements :+ x
                  case _ =>
                }
              } else {
                packageElements = packageElements :+ visitUsageElement(visibility, i.ruleUsageElement())
              }

            case i: RuleElementFilterMemberContext =>
              reportError(i, "Filter members are not currently supported")

            case i: RuleAliasMemberContext =>
              packageElements = packageElements :+ visitAliasMember(i)

            case i: RuleImportContext =>
              packageElements = packageElements :+ visitImport(i)

            case i: org.antlr.v4.runtime.tree.TerminalNode =>
            // do nothing
          }
        }
    }

    return Package(
      visibility = visibility,
      identification = id,
      packageElements = packageElements,
      attr = toAttr(parent)
    )
  }

  def visitAnnotatingElement(visiblilty: Visibility.Type, o: RuleAnnotatingElementContext): AnnotatingElement = {
    assert(!SysmlAstUtil.isRegularComment(o), s"Filter out regular comments before calling this method")
    o match {
      case i1: RuleAnnotatingElement1Context =>
        // ruleComment: ('comment' ruleIdentification? ('about' ruleAnnotation (',' ruleAnnotation)*)?)? ('locale' RULE_STRING_VALUE)? RULE_REGULAR_COMMENT;
        val comment = i1.ruleComment()

        val identification: Option[Identification] =
          if (isEmpty(comment.ruleIdentification())) None()
          else Some(visitIdentification(comment.ruleIdentification()))

        var abouts: ISZ[Name] = ISZ()
        for (a <- comment.ruleAnnotation().asScala) {
          abouts = abouts :+ visitQualifiedName(a.ruleQualifiedName())
        }

        val locale: Option[String] =
          if (comment.K_LOCALE() == null) None()
          else Some(comment.RULE_STRING_VALUE().string)

        return Comment(
          visibility = visiblilty,
          identification = identification,
          abouts = abouts,
          locale = locale,
          comment = comment.RULE_REGULAR_COMMENT().string,
          attr = toAttr(o))

      case i2: RuleAnnotatingElement2Context =>
        // ruleDocumentation: 'doc' ruleIdentification? ('locale' RULE_STRING_VALUE)? RULE_REGULAR_COMMENT;
        val doc = i2.ruleDocumentation()

        val identification: Option[Identification] =
          if (isEmpty(doc.ruleIdentification())) None()
          else Some(visitIdentification(doc.ruleIdentification()))

        val locale: Option[String] =
          if (doc.K_LOCALE() == null) None()
          else Some(doc.RULE_STRING_VALUE().string)

        return Documentation(
          visibility = visiblilty,
          identification = identification,
          locale = locale,
          comment = doc.RULE_REGULAR_COMMENT().string,
          attr = toAttr(o))

      case i3: RuleAnnotatingElement3Context =>
        // Original Version
        // ruleTextualRepresentation: ('rep' ruleIdentification?)? 'language' RULE_STRING_VALUE RULE_REGULAR_COMMENT;
        //
        // GUMBO Version
        //
        // ruleTextualRepresentation: ('rep' ruleIdentification?)? 'language'
        //   (('"GUMBO"' '/*{' (('library' ruleGumboLibrary) | ruleGumboSubclause) '}*/' )
        //    | (RULE_STRING_VALUE RULE_REGULAR_COMMENT));
        val text = i3.ruleTextualRepresentation()

        val identification: Option[Identification] =
          if (isEmpty(text.ruleIdentification())) None()
          else Some(visitIdentification(text.ruleIdentification()))

        if (nonEmpty(text.ruleGumboLibrary())) {
          assert(text.K_REP() == null, "Not expecting 'rep' for gumbo annotations")
          return GumboAnnotation(visitGumboLibrary(text.ruleGumboLibrary()))
        } else if (nonEmpty(text.ruleGumboSubclause())) {
          assert(text.K_REP() == null, "Not expecting 'rep' for gumbo annotations")
          return GumboAnnotation(visitGumboSubclause(text.ruleGumboSubclause()))
        } else {
          return TextualRepresentation(
            visibility = visiblilty,
            identification = identification,
            language = text.RULE_STRING_VALUE().string,
            comment = text.RULE_REGULAR_COMMENT().string,
            attr = toAttr(o))
        }

      case i4: RuleAnnotatingElement4Context =>
        val dummy = i4.ruleMetadataUsage()
        halt("Not yet handling metadata annotations")

      case x =>
        halt(s"Not expecting this annotating element $x")
    }
  }

  private def visitEnumerationDefinition(visibility: Visibility.Type, o: RuleEnumerationDefinitionContext): EnumerationDefinition = {
    reportError(o.ruleDefinitionExtensionKeyword().isEmpty, o,
      "Enum extensions are not currently handled")

    val (identifications, subClassifications) = visitDefinitionDeclaration(o.ruleDefinitionDeclaration())

    var annotations: ISZ[AnnotatingElement] = ISZ()
    var enumValues: ISZ[EnumeratedValue] = ISZ()

    o.ruleEnumerationBody() match {
      case i1: RuleEnumerationBody1Context =>
        assert(i1.OP_SEMI() != null)
      case i2: RuleEnumerationBody2Context =>
        for (am <- listToISZ(i2.ruleAnnotatingMember()) if !isRegularComment(am.ruleAnnotatingElement())) {
          annotations = annotations :+ visitAnnotatingElement(Visibility.Public, am.ruleAnnotatingElement())
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
      visibility = visibility,
      identification = identifications,
      subClassifications = subClassifications,
      annotations = annotations,
      enumValues = enumValues,
      attr = toAttr(o))
  }

  private def visitPortDefinition(visibility: Visibility.Type, o: RulePortDefinitionContext): PortDefinition = {
    val defPrefix = visitDefinitionPrefix(o.ruleDefinitionPrefix())

    val (identifier, subClassifications, bodyItems) = visitDefinition(o.ruleDefinition())

    // TODO: conjugated port def leads to an empty rule
    val empty = o.ruleConjugatedPortDefinitionMember().ruleConjugatedPortDefinition().rulePortConjugation()

    return PortDefinition(
      visibility = visibility,
      defPrefix = defPrefix,
      identification = identifier,
      subClassifications = subClassifications,
      parents = ISZ(),
      bodyItems = bodyItems,
      attr = toAttr(o))
  }

  private def visitDefinitionPrefix(o: RuleDefinitionPrefixContext): DefinitionPrefix = {

    var isAbstract = F
    var isVariation = F

    if (nonEmpty(o.ruleBasicDefinitionPrefix())) {
      o.ruleBasicDefinitionPrefix() match {
        case p1: RuleBasicDefinitionPrefix1Context =>
          assert(p1.K_ABSTRACT() != null)
          isAbstract = T
        case p2: RuleBasicDefinitionPrefix2Context =>
          assert(p2.K_VARIATION() != null)
          isVariation = T
      }
    }

    reportError(o.ruleDefinitionExtensionKeyword().isEmpty, o, "Need to handle definition extensions")

    return DefinitionPrefix(
      isAbstract = isAbstract,
      isVariation = isVariation
    )
  }

  private def visitMetadataDefinition(visibility: Visibility.Type, o: RuleMetadataDefinitionContext): MetadataDefinition = {
    reportError(o, "Need to handle metadata definitions")
    val (identification, subClassifications, bodyItems) = visitDefinition(o.ruleDefinition())
    return MetadataDefinition(
      isAbstract = o.K_ABSTRACT() != null,
      visibility = visibility,
      identification = identification,
      subClassifications = subClassifications,
      bodyItems = bodyItems,
      attr = toAttr(o))
  }

  private def visitPartDefinition(visibility: Visibility.Type, o: RulePartDefinitionContext): PartDefinition = {

    val occurrenceDefPrefix = visitOccurrenceDefinitionPrefix(o.ruleOccurrenceDefinitionPrefix())

    val (identification, subClassifications, bodyItems) = visitDefinition(o.ruleDefinition())

    return PartDefinition(
      visibility = visibility,
      occurrenceDefPrefix = occurrenceDefPrefix,
      identification = identification,
      subClassifications = subClassifications,
      parents = ISZ(),
      bodyItems = bodyItems,
      attr = toAttr(o))
  }

  private def visitUsageElement(visibility: Visibility.Type, context: RuleUsageElementContext): UsageElement = {
    context match {
      case i: RuleUsageElement1Context =>
        return visitNonOccurrenceUsageElement(visibility, i.ruleNonOccurrenceUsageElement())
      case i: RuleUsageElement2Context =>
        return visitOccurrenceUsageElement(visibility, i.ruleOccurrenceUsageElement())
    }
  }

  private def visitItemUsage(visibility: Visibility.Type, o: RuleItemUsageContext): ItemUsage = {
    val occurrenceUsagePrefix: OccurrenceUsagePrefix = visitOccurrenceUsagePrefix(o.ruleOccurrenceUsagePrefix())

    val u = visitUsage(o.ruleUsage())

    return ItemUsage(
      occurrenceUsagePrefix = occurrenceUsagePrefix,

      commonUsageElements = CommonUsageElements(
        visibility = visibility,
        identification = u.identification,
        specializations = u.specializations,
        featureValue = u.featureValue,
        definitionBodyItems = u.definitionBodyItems,
        tipeOpt = None(),
        attr = toResolvedAttr(o)))
  }

  private def visitPartUsage(visibility: Visibility.Type, o: RulePartUsageContext): PartUsage = {
    /** eg.
      * part tempSensor : TempSensor;
		  * part tempControl : TempControl;
		  */
    val occurrenceUsagePrefix: OccurrenceUsagePrefix = visitOccurrenceUsagePrefix(o.ruleOccurrenceUsagePrefix())

    val u = visitUsage(o.ruleUsage())

    return PartUsage(
      occurrenceUsagePrefix = occurrenceUsagePrefix,

      commonUsageElements = CommonUsageElements(
        visibility = visibility,
        identification = u.identification,
        specializations = u.specializations,
        featureValue = u.featureValue,
        definitionBodyItems = u.definitionBodyItems,
        tipeOpt = None(),
        attr = toResolvedAttr(o)))
  }

  def visitPortUsage(visibility: Visibility.Type, o: RulePortUsageContext): PortUsage = {
    /** e.g.
      * out port currentTemp : DataPort { out :> type : Temperature; }
		  * out port tempChanged : EventPort;
      */

    //  A port usage is a kind of occurrence usage definition that is a usage of a port definition.
    val occurrenceUsagePrefix: OccurrenceUsagePrefix = visitOccurrenceUsagePrefix(o.ruleOccurrenceUsagePrefix())

    val u = visitUsage(o.ruleUsage())

    return PortUsage(
      occurrenceUsagePrefix = occurrenceUsagePrefix,

      commonUsageElements = CommonUsageElements(
        visibility = visibility,

        identification = u.identification,
        specializations = u.specializations,
        featureValue = u.featureValue,
        definitionBodyItems = u.definitionBodyItems,
        tipeOpt = None(),
        attr = toResolvedAttr(o)))
  }

  def visitDefinitionDeclaration(decl: RuleDefinitionDeclarationContext): (Option[Identification], ISZ[Name]) = {

    val indentification: Option[Identification] =
      if (isEmpty(decl.ruleIdentification())) None()
      else Some(visitIdentification(decl.ruleIdentification()))

    val subClassifications: ISZ[Name] = {
      if (isEmpty(decl.ruleSubclassificationPart())) ISZ()
      else
        for (subclass <- listToISZ(decl.ruleSubclassificationPart().ruleOwnedSubclassification())) yield
          visitQualifiedName(subclass.ruleQualifiedName())
    }

    return (indentification, subClassifications)
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

  case class UsageHolder(val identification: Option[Identification],
                         val specializations: ISZ[FeatureSpecialization],
                         val featureValue: Option[FeatureValue],
                         val definitionBodyItems: ISZ[DefinitionBodyItem])

  private def visitUsage(ruleUsage: RuleUsageContext): UsageHolder = {
    val (identification, specializations): (Option[Identification], ISZ[FeatureSpecialization]) =
      if (isEmpty(ruleUsage.ruleUsageDeclaration())) {
        reportError(ruleUsage, "Must provide a usage declaration")
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

    val definitionBodyItems: ISZ[DefinitionBodyItem] = visitDefinitionBody(ruleUsage.ruleUsageCompletion().ruleUsageBody().ruleDefinitionBody())

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
  (Option[Identification], ISZ[Name], ISZ[DefinitionBodyItem]) = {
    val (identification, subClassifications) = visitDefinitionDeclaration(ruleDefinition.ruleDefinitionDeclaration())

    val bodyItems: ISZ[DefinitionBodyItem] = visitDefinitionBody(ruleDefinition.ruleDefinitionBody())

    return (identification, subClassifications, bodyItems)
  }

  private def visitDefinitionBody(body: RuleDefinitionBodyContext): ISZ[DefinitionBodyItem] = {
    body match {
      case i: RuleDefinitionBody1Context =>
        assert(i.OP_SEMI() != null)
        return ISZ()

      case i: RuleDefinitionBody2Context =>
        var items: ISZ[DefinitionBodyItem] = ISZ()
        for (bi <- i.ruleDefinitionBodyItem().asScala) {
          bi match {
            case bi1: RuleDefinitionBodyItem1Context =>
              val defMember = bi1.ruleDefinitionMember()
              val visibility = visitVisibilityIndicator(defMember.ruleMemberPrefix().ruleVisibilityIndicator())
              visitDefinitionElement(visibility, defMember.ruleDefinitionElement()) match {
                case x if x != Placeholders.emptyDefinitionElement => items = items :+ x
                case _ =>
              }

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
              items = items :+ visitAliasMember(bi5.ruleAliasMember())

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

          case su12: RuleStructureUsageElement12Context =>
            return visitAllocationUsage(visibility, su12.ruleAllocationUsage())

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
  private def visitConnectionUsage(visibility: Visibility.Type, o: RuleConnectionUsageContext): ConnectionUsage = {
    val occurrenceUsagePrefix = visitOccurrenceUsagePrefix(o.ruleOccurrenceUsagePrefix())

    // NOTE: can't use visitUsage as ConnectionUsage doesn't use ruleUsage, though it still
    // has the same usage fields

    val (identification, specializations): (Option[Identification], ISZ[FeatureSpecialization]) =
      if (isEmpty(o.ruleUsageDeclaration())) {
        (None(), ISZ())
      } else {
        visitFeatureDeclaration(o.ruleUsageDeclaration().ruleFeatureDeclaration())
      }

    val featureValue: Option[FeatureValue] =
      if (isEmpty(o.ruleValuePart())) None()
      else Some(visitFeatureValue(o.ruleValuePart().ruleFeatureValue()))

    val connectorPart: Option[ConnectorPart] =
      if (isEmpty(o.ruleConnectorKeyword())) {
        assert(isEmpty(o.ruleConnectorPart()))
        None()
      } else {
        o.ruleConnectorPart() match {
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

    val bodyItems = visitDefinitionBody(o.ruleUsageBody().ruleDefinitionBody())

    return ConnectionUsage(
      occurrenceUsagePrefix = occurrenceUsagePrefix,
      connectorPart = connectorPart,

      commonUsageElements = CommonUsageElements(
        visibility = visibility,
        identification = identification,
        specializations = specializations,
        featureValue = featureValue,
        definitionBodyItems = bodyItems,
        tipeOpt = None(),
        attr = toResolvedAttr(o)))

  }

  /*
   * ruleAllocationUsage: ruleOccurrenceUsagePrefix ruleAllocationUsageDeclaration ruleUsageBody;
   *
   * ruleAllocationUsageDeclaration:
   *     ruleAllocationUsageKeyword ruleUsageDeclaration? (ruleAllocateKeyword ruleConnectorPart)? #ruleAllocationUsageDeclaration1
   *   | ruleAllocateKeyword ruleConnectorPart #ruleAllocationUsageDeclaration2;

   */
  def visitAllocationUsage(visibility: Visibility.Type, o: SysMLv2Parser.RuleAllocationUsageContext): AllocationUsage = {
    val occurrenceUsagePrefix: OccurrenceUsagePrefix = visitOccurrenceUsagePrefix(o.ruleOccurrenceUsagePrefix())

    val (id, specs, connectorPart) :  (Option[Identification], ISZ[FeatureSpecialization], Option[ConnectorPart]) =
      o.ruleAllocationUsageDeclaration() match {
      case r1: RuleAllocationUsageDeclaration1Context =>
        val (identification, specializations): (Option[Identification], ISZ[FeatureSpecialization]) =
          if (isEmpty(r1.ruleUsageDeclaration())) {
            (None(), ISZ())
          } else {
            visitFeatureDeclaration(r1.ruleUsageDeclaration().ruleFeatureDeclaration())
          }

        val connectorPart: Option[ConnectorPart] =
          if (isEmpty(r1.ruleAllocateKeyword())) {
            assert(isEmpty(r1.ruleConnectorPart()))
            None()
          } else {
            r1.ruleConnectorPart() match {
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

        (identification, specializations, connectorPart)

      case r2: RuleAllocationUsageDeclaration2Context =>
        reportError(r2, "Not currently handling RuleAllocationUsageDeclaration2Context")
        (None(), ISZ(), None())
      case _ => halt("Infeasible")
    }

    val bodyItems = visitDefinitionBody(o.ruleUsageBody().ruleDefinitionBody())

    return AllocationUsage(
      occurrenceUsagePrefix = occurrenceUsagePrefix,
      connectorPart = connectorPart,

      commonUsageElements = CommonUsageElements(
        visibility = visibility,
        identification = id,
        specializations = specs,
        featureValue = None(),
        definitionBodyItems = bodyItems,
        tipeOpt = None(),
        attr = toResolvedAttr(o)))
  }


  private def visitConnectorEnd(context: RuleConnectorEndContext): ConnectorEnd = {
    if (nonEmpty(context.ruleName())) {
      halt("Need to see and example of this")
    }

    val ref: ISZ[Name] = context.ruleOwnedReferenceSubsetting() match {
      case s1: RuleOwnedReferenceSubsetting1Context =>
        ISZ(visitQualifiedName(s1.ruleQualifiedName()))
      case s2: RuleOwnedReferenceSubsetting2Context =>
        visitOwnedFeatureChain(s2.ruleOwnedFeatureChain())
    }

    reportError(isEmpty(context.ruleOwnedMultiplicity()), context, "Multiplicities are not currently supported")

    return ConnectorEnd(reference = ref, tipeOpt = None(), resOpt = toResolvedAttr(context))
  }

  private def visitFeatureChainMember(context: RuleFeatureChainMemberContext): ISZ[Name] = {
    context match {
      case i: RuleFeatureChainMember1Context =>
        return ISZ(visitQualifiedName(i.ruleQualifiedName()))
      case i: RuleFeatureChainMember2Context =>
        return visitOwnedFeatureChain(i.ruleOwnedFeatureChain())

    }
  }

  private def visitOwnedFeatureChain(context: RuleOwnedFeatureChainContext): ISZ[Name] = {
    // ruleFeatureChain: ruleOwnedFeatureChaining ('.' ruleOwnedFeatureChaining)+;
    // ruleOwnedFeatureChaining: ruleQualifiedName;
    var chain = ISZ(visitQualifiedName(context.ruleFeatureChain().ruleOwnedFeatureChaining(0).ruleQualifiedName()))
    for (i <- 1 until context.ruleFeatureChain().ruleOwnedFeatureChaining().size()) {
      chain = chain :+ visitQualifiedName(context.ruleFeatureChain().ruleOwnedFeatureChaining(i).ruleQualifiedName())
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

  private def visitDefaultReferenceUsage(visibility: Visibility.Type, o: RuleDefaultReferenceUsageContext): ReferenceUsage = {
    val refPrefix = visitRefPrefix(o.ruleRefPrefix())

    val (identification, specializations) = visitFeatureDeclaration(o.ruleUsageDeclaration().ruleFeatureDeclaration())

    val featureValue: Option[FeatureValue] =
      if (isEmpty(o.ruleValuePart())) {
        None()
      } else {
        Some(visitFeatureValue(o.ruleValuePart().ruleFeatureValue()))
      }

    val bodyItems = visitDefinitionBody(o.ruleUsageBody().ruleDefinitionBody())

    return ReferenceUsage(
      prefix = refPrefix,

      commonUsageElements = CommonUsageElements(
        visibility = visibility,
        identification = identification,
        specializations = specializations,
        featureValue = featureValue,
        definitionBodyItems = bodyItems,
        tipeOpt = None(),
        attr = toResolvedAttr(o)))
  }

  def visitReferenceUsage(visibility: Visibility.Type, o: RuleReferenceUsageContext): ReferenceUsage = {
    val p = visitRefPrefix(o.ruleRefPrefix())
    val u = visitUsage(o.ruleUsage())

    return ReferenceUsage(
      prefix = p,

      commonUsageElements = CommonUsageElements(
        visibility = visibility,
        identification = u.identification,
        specializations = u.specializations,
        featureValue = u.featureValue,
        definitionBodyItems = u.definitionBodyItems,
        tipeOpt = None(),
        attr = toResolvedAttr(o)))
  }

  def visitAttributeUsage(visibility: Visibility.Type, o: RuleAttributeUsageContext): AttributeUsage = {

    val p = visitUsagePrefix(o.ruleUsagePrefix())
    val u = visitUsage(o.ruleUsage())

    return AttributeUsage(
      prefix = p,

      commonUsageElements = CommonUsageElements(
        visibility = visibility,
        identification = u.identification,
        specializations = u.specializations,
        featureValue = u.featureValue,
        definitionBodyItems = u.definitionBodyItems,
        tipeOpt = None(),
        attr = toResolvedAttr(o)))
  }

  /* ruleIdentification:
   *   '<' ruleName '>' ruleName? #ruleIdentification1
   *   | ruleName #ruleIdentification2;
   */
  private def visitIdentification(id: RuleIdentificationContext): Identification = {
    id match {
      case i1: RuleIdentification1Context =>
        val shortName = visitName(i1.ruleName().get(0))
        if (i1.ruleName().size() == 2) {
          return Identification(shortName = Some(shortName), name = Some(visitName(i1.ruleName().get(1))), attr = toAttr(id))
        } else {
          return Identification(shortName = Some(shortName), name = None(), attr = toAttr(id))
        }
      case i2: RuleIdentification2Context =>
        return Identification(shortName = None(), name = Some(visitName(i2.ruleName())), attr = toAttr(id))
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

        var types: ISZ[Name] = ISZ()
        for (t <- featureTypings) {
          t match {
            case t1: RuleFeatureTyping1Context =>
              t1.ruleOwnedFeatureTyping() match {
                case ot1: RuleOwnedFeatureTyping1Context =>
                  types = types :+ visitQualifiedName(ot1.ruleQualifiedName())
                case ot2: RuleOwnedFeatureTyping2Context =>
                  types = types ++ visitOwnedFeatureChain(ot2.ruleOwnedFeatureChain())
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

        var subsets: ISZ[Name] = ISZ()
        for (s <- ownedSubsettings) {
          s match {
            case i: RuleOwnedSubsetting1Context =>
              subsets = subsets :+ visitQualifiedName(i.ruleQualifiedName())
            case i: RuleOwnedSubsetting2Context =>
              subsets = subsets ++ visitOwnedFeatureChain(i.ruleOwnedFeatureChain())
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
            return ReferencesSpecialization(visitOwnedFeatureChain(i.ruleOwnedFeatureChain()))
        }

      case x: RuleFeatureSpecialization4Context =>
        // ruleRedefinitions: ruleRedefines (',' ruleOwnedRedefinition)*;
        // ruleRedefines: ruleRedefinesKeyword ruleOwnedRedefinition;

        val redefs = x.ruleRedefinitions()
        val ownedRedefinitions =
          ISZ(redefs.ruleRedefines().ruleOwnedRedefinition()) ++
            listToISZ(redefs.ruleOwnedRedefinition())

        var redefines: ISZ[Name] = ISZ()
        for (o <- ownedRedefinitions) {
          o match {
            case i: RuleOwnedRedefinition1Context =>
              redefines = redefines :+ visitQualifiedName(i.ruleQualifiedName())
            case i: RuleOwnedRedefinition2Context =>
              redefines = redefines ++ visitOwnedFeatureChain(i.ruleOwnedFeatureChain())
          }
        }
        return RedefinitionsSpecialization(redefines)

      case x => halt(s"Not expecting $x")
    }
  }

  private def visitUsagePrefix(o: RuleUsagePrefixContext): UsagePrefix = {

    val refPrefix = visitRefPrefix(o.ruleBasicUsagePrefix().ruleRefPrefix())

    var usageExtensions: ISZ[Name] = ISZ()
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

    var usageExtensions: ISZ[Name] = ISZ()
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

  // ruleAliasMember: ruleMemberPrefix 'alias' ('<' ruleName '>')? ruleName?
  //   'for' ruleQualifiedName ruleRelationshipBody;
  def visitAliasMember(o: RuleAliasMemberContext): AliasMember = {
    val visibility: Visibility.Type = visitVisibilityIndicator(o.ruleMemberPrefix().ruleVisibilityIndicator())

    var annotations: ISZ[AnnotatingElement] = ISZ()
    o.ruleRelationshipBody() match {
      case rb1: RuleRelationshipBody1Context =>
        assert(rb1.OP_SEMI() != null)

      case rb2: RuleRelationshipBody2Context =>
        for (oa <- rb2.ruleOwnedAnnotation().asScala if !SysmlAstUtil.isRegularComment(oa.ruleAnnotatingElement())) {
          annotations = annotations :+ visitAnnotatingElement(Visibility.Public, oa.ruleAnnotatingElement())
        }
    }

    val identification: Option[Identification] =
      if (o.ruleName().isEmpty) {
        None()
      }
      else if (o.ruleName().size() == 1) {
        if (o.LANGLE() != null) {
          Some(Identification(shortName = Some(visitName(o.ruleName(0))), name = None(), attr = toAttr(o.ruleName(0))))
        } else {
          Some(Identification(shortName = None(), name = Some(visitName(o.ruleName(0))), attr = toAttr(o.ruleName(0))))
        }
      } else {
        assert(o.ruleName().size() == 2)
        Some(Identification(
          shortName = Some(visitName(o.ruleName(0))),
          name = Some(visitName(o.ruleName(1))),
          attr = Attr(SlangUtil.mergePos(toPosOpt(o.ruleName(0)), toPosOpt(o.ruleName(1))))
        ))
      }

    val target = visitQualifiedName(o.ruleQualifiedName())

    return AliasMember(
      visibility = visibility,
      identification = identification,
      target = target,
      annotations = annotations,
      attr = toAttr(o))
  }

  // ruleImport: (ruleMembershipImport | ruleNamespaceImport) ruleRelationshipBody;
  def visitImport(o: RuleImportContext): Import = {
    var annotations: ISZ[AnnotatingElement] = ISZ()

    o.ruleRelationshipBody() match {
      case rb1: RuleRelationshipBody1Context =>
        assert(rb1.OP_SEMI() != null)

      case rb2: RuleRelationshipBody2Context =>
        for (oa <- rb2.ruleOwnedAnnotation().asScala if !SysmlAstUtil.isRegularComment(oa.ruleAnnotatingElement())) {
          annotations = annotations :+ visitAnnotatingElement(Visibility.Public, oa.ruleAnnotatingElement())
        }
    }

    if (nonEmpty(o.ruleMembershipImport())) {
      // ruleMembershipImport: ruleImportPrefix ruleImportedMembership;
      assert(isEmpty(o.ruleNamespaceImport()))

      val mimport = o.ruleMembershipImport()

      val visibility = visitVisibilityIndicator(mimport.ruleImportPrefix().ruleVisibilityIndicator())

      val qualifiedName = visitQualifiedName(mimport.ruleImportedMembership().ruleQualifiedName())

      return Import(
        visibility = visibility,
        all = mimport.ruleImportPrefix().K_ALL() != null,
        name = qualifiedName,
        star = F,
        starStar = mimport.ruleImportedMembership().OP_STAR_STAR() != null,
        annotations = annotations,
        attr = toAttr(o))
    } else {
      val nimport = o.ruleNamespaceImport()

      val visibility = visitVisibilityIndicator(nimport.ruleImportPrefix().ruleVisibilityIndicator())

      reportError(isEmpty(nimport.ruleFilterPackage()), nimport.ruleFilterPackage(),
        "Not currently handling filtered imports")

      // ruleImportedNamespace: ruleQualifiedName '::' '*' ('::' '**')?;
      val namespace = nimport.ruleImportedNamespace()

      val qualifiedName = visitQualifiedName(namespace.ruleQualifiedName())

      return Import(
        visibility = visibility,
        all = nimport.ruleImportPrefix().K_ALL() != null,
        name = qualifiedName,
        star = T,
        starStar = nimport.ruleImportedNamespace().OP_STAR_STAR() != null,
        annotations = annotations,
        attr = toAttr(o))
    }
  }

  def visitQualifiedName(o: RuleQualifiedNameContext): Name = {
    // ruleQualifiedName: ruleQualification? ruleName;
    // ruleQualification: (ruleName '::')+;
    var ret: ISZ[Id] = ISZ()
    if (o.ruleQualification() != null) {
      ret = ret ++ (for (seg <- listToISZ(o.ruleQualification().ruleName())) yield visitName(seg))
    }
    return Name(ids = ret :+ visitName(o.ruleName()), attr = toAttr(o))
  }

  def visitQualifiedNameAsSlangName(o: SysMLv2Parser.RuleQualifiedNameContext): AST.Name = {
    val name = visitQualifiedName(o)
    val ids = for (id <- name.ids) yield AST.Id(value = id.value, AST.Attr(posOpt = id.attr.posOpt))
    return AST.Name(ids = ids, attr = AST.Attr(posOpt = name.attr.posOpt))
  }

  def visitName(o: RuleNameContext): Id = {
    // ruleName:
    //   RULE_ID #ruleName1
    //   | RULE_UNRESTRICTED_NAME #ruleName2;
    o match {
      case r1: RuleName1Context => return Id(value = r1.getText, attr = toAttr(o))
      case r2: RuleName2Context => return Id(value = r2.getText, attr = toAttr(o))
      case x => halt(s"Unexpected name: $x")
    }
  }

  /*
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
  */

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

    /* 7.4.9.2 Operator Expressions
     *   ...
     *   ... the ... and implication (implies) operators all correspond to control functions in which
     *   their second operand is only evaluated depending on a certain condition of the value of their
     *   first operand (whether it is null, true, false, or true, respectively)
     *
     *  NOTE: KerML does not provide a non-short circuit implication operator so a uif should be used
     *        instead, e.g.
     *        '->:' (lhs, rhs) instead of lhs ->: rhs
     */
    return SlangUtil.collapse1(lhs, AST.Exp.BinaryOp.CondImply, s)
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

    return SlangUtil.collapse2(lhs, s)
  }

  // ruleXorExpression: ruleAndExpression ( ruleXorOperator ruleAndExpression)*;
  private def visitXorExpression(o: RuleXorExpressionContext): AST.Exp = {
    val exprs = ops.ISZOps(listToISZ(o.ruleAndExpression()))
    val lhs = visitAndExpression(exprs.first)

    val s = Stack(for (e <- exprs.tail) yield visitAndExpression(e))

    return SlangUtil.collapse1(lhs, AST.Exp.BinaryOp.Xor, s)
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
    return SlangUtil.collapse2(lhs, s)
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

    return SlangUtil.collapse2(lhs, s)
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

    return SlangUtil.collapse2(lhs, s)
  }

  // ruleRangeExpression: ruleAdditiveExpression ( '..' ruleAdditiveExpression)?;
  private def visitRangeExpression(o: RuleRangeExpressionContext): AST.Exp = {
    val lhs = visitAdditiveExpression(o.ruleAdditiveExpression(0))

    if (o.ruleAdditiveExpression().size() > 1) {
      val rhs = visitAdditiveExpression(o.ruleAdditiveExpression(1))

      val ident = AST.Exp.Ident(id = AST.Id(value = UIF.RangeExpression, attr = toSlangAttr(o)), attr = toSlangResolvedAttr(o))
      return AST.Exp.Invoke(
        receiverOpt = None(),
        ident = ident,
        targs = ISZ(),
        args = ISZ(lhs, rhs),
        attr = toSlangResolvedAttr(o))
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

    return SlangUtil.collapse2(lhs, s)
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

    return SlangUtil.collapse2(lhs, s)
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
              // e.g.  e::x
              val slangName: AST.Name = visitQualifiedNameAsSlangName(i.ruleQualifiedName())
              assert(slangName.ids.size == 1)

              baseExp = AST.Exp.Select(
                receiverOpt = Some(baseExp),
                id = slangName.ids(0),
                targs = ISZ(),
                attr = Placeholders.emptyResolvedAttr(mergePos(baseExp.posOpt, slangName.ids(0).attr.posOpt))
              )
            case i: RuleFeatureChainMember2Context =>
              // e.blah::x.blah2::y.blah3::z
              // currentTemp.degrees
              var ids: ISZ[AST.Id] = ISZ()
              for (f <- listToISZ(i.ruleOwnedFeatureChain().ruleFeatureChain().ruleOwnedFeatureChaining())) {
                val n = visitQualifiedNameAsSlangName(f.ruleQualifiedName())
                ids = ids ++ n.ids
              }
              baseExp = SlangUtil.toSelect(baseExp, ids)
          }
          index = index + 1
        case _ =>
      }

      assert(o.getChildCount == o.children.size())

      ret = baseExp

      val initIndex = index
      while (index < o.getChildCount) {

        o.getChild(index) match {
          case i: TerminalNodeImpl if i.getText == "#" =>
            // ( '#' '(' ruleSequenceExpression ')'
            val se = visitSequenceExpression(o.getChild(index + 2).asInstanceOf[RuleSequenceExpressionContext])
            index = index + 4
            reportError(i, s"Need example of primary expression alt 1: $se")
          case i: TerminalNodeImpl if i.getText == "[" =>
            //     | '[' ruleSequenceExpression ']'
            visitSequenceExpression(o.getChild(index + 1).asInstanceOf[RuleSequenceExpressionContext]) match {
              case i: AST.Exp.Ident if index == initIndex && index + 3 == o.getChildCount =>

                if (isReservedSequenceName(i.id.value)) {
                  return handleReservedSequenceName(ret, i, o)
                } else {
                  // e.g. 1000 [ms] ~> ms(1000)
                  ret = AST.Exp.Invoke(
                    receiverOpt = None(),
                    ident = i,
                    targs = ISZ(),
                    args = ISZ(ret),
                    attr = toSlangResolvedAttr(o))
                }
              case x =>
                reportError(i, s"The sequencing expression '$x' is not currently handled")
            }
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
            reportError(i, s"Need example of primary expression alt 2: $subExp")
          case i: TerminalNodeImpl if i.getText == "." =>
            o.getChild(index + 1) match {
              case i: TerminalNodeImpl =>
                //     | '.?' ruleBodyExpression
                assert(i.getText == "?")
                val be = visitBodyExpression(o.getChild(index + 2).asInstanceOf[RuleBodyExpressionContext])
                index = index + 3
                reportError(i, s"Need example of primary expression alt 3: $be")
              case i =>
                //     | '.' ruleBodyExpression
                val be = visitBodyExpression(i.asInstanceOf[RuleBodyExpressionContext])
                index = index + 2
                reportError(i, s"Need example of primary expression alt 4: $be")
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
              reportError(i, "Need example of primary expression alt 5")

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
        val s = scala.collection.mutable.Stack.from[Any](visitQualifiedNameAsSlangName(i.ruleFeatureReferenceExpression().ruleFeatureReferenceMember().ruleQualifiedName()).ids.elements)

        if (s.size > 1) {
          val a = s.pop()
          val b = s.pop().asInstanceOf[AST.Id]

          a match {
            case aAsId: AST.Id =>
              val posOpt = SlangUtil.mergePos(aAsId.attr.posOpt, b.attr.posOpt)
              val ident = AST.Exp.Ident(id = aAsId, attr = Placeholders.emptyResolvedAttr(posOpt))
              s.push(AST.Exp.Select(receiverOpt = Some(ident), id = b, targs = ISZ(), attr = Placeholders.emptyResolvedAttr(posOpt)))
            case aAsSelect: AST.Exp.Select =>
              val posOpt = SlangUtil.mergePos(aAsSelect.posOpt, b.attr.posOpt)
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
    if (nonEmpty(o.ruleArgumentList().ruleNamedArgumentList())) {
      halt("todo")
      /*
      return AST.Exp.InvokeNamed (
        receiverOpt = ???,
        ident = ???,
        targs = ???,
        args = ???,
        attr = toSlangResolvedAttr(o))
       */
    } else {
      val (receiverOpt, ident): (Option[Exp], AST.Exp.Ident) = o.ruleOwnedFeatureTyping() match {
        case i: RuleOwnedFeatureTyping1Context =>
          val name = visitQualifiedNameAsSlangName(i.ruleQualifiedName())
          val ids = ops.ISZOps(name.ids)

          val receiver: Option[Exp] = if (ids.s.size > 1) {
            Some(SlangUtil.toSelectH(ids.dropRight(1)))
          } else {
            None()
          }

          val ident = AST.Exp.Ident(
            id = ids.last,
            attr = AST.ResolvedAttr(posOpt = ids.last.attr.posOpt, resOpt = None(), typedOpt = None()))
          (receiver, ident)

        case i: RuleOwnedFeatureTyping2Context =>
          halt("Need and example of an invocation expression where the name is a chain")
      }
      var args: ISZ[Exp] = ISZ()
      if (nonEmpty(o.ruleArgumentList().rulePositionalArgumentList())) {
        args = for (e <- listToISZ(o.ruleArgumentList().rulePositionalArgumentList().ruleArgumentMember())) yield
          visitOwnedExpression(e.ruleArgument().ruleArgumentValue().ruleOwnedExpression())
      }

      if (SysMLAstBuilder.isReservedUifName(ident.id.value)) {
        return handleUif(receiverOpt, ident, args, toSlangResolvedAttr(o))
      } else {
        return AST.Exp.Invoke(
          receiverOpt = receiverOpt,
          ident = ident,
          targs = ISZ(),
          args = args,
          attr = toSlangResolvedAttr(o))
      }
    }
  }

  def handleReservedSequenceName(numeric: Exp, ident: Exp.Ident, origin: RulePrimaryExpressionContext): AST.Exp = {
    val sequenceId = ident.id.value

    def handleNumeric: Option[String] = {
      numeric match {
        case u@AST.Exp.Unary(_, l: AST.Exp.LitF64) => return Some(s"${u.opString}${l.value.string}")
        case u@AST.Exp.Unary(_, l: AST.Exp.LitZ) => return Some(s"${u.opString}${l.value.string}")
        case l: AST.Exp.LitF64 => return Some(l.value.string)
        case l: AST.Exp.LitZ => return Some(l.value.string)
        case x =>
          reportError(ident.posOpt, s"'${sequenceId} requires a numeric argument")
          return None()
      }
    }

    val dummy = AST.Exp.LitString(value = "???", attr = toSlangAttr(origin))

    if (ops.ISZOps(interpolates).contains(sequenceId)) {
      sequenceId match {
        case string"c" =>
          numeric match {
            case AST.Exp.LitString(v) =>
              val vv = conversions.String.toCis(SlangUtil.unquoteString(v))
              if (vv.size == 1) {
                return AST.Exp.LitC(vv(0), toSlangAttr(origin))
              }
              reportError(numeric.posOpt, "Slang c interpolator can only have a single character")
            case _ =>
              reportError(numeric.posOpt, "Was expecting a string literal")
          }
          return dummy

        case string"string" =>
          numeric match {
            case AST.Exp.LitString(v) =>
              return AST.Exp.LitString(SlangUtil.unquoteString(v), toSlangAttr(origin))
            case _ =>
              reportError(numeric.posOpt, "Was expecting a string literal")
              return dummy
          }

        case string"f32" =>
          handleNumeric match {
            case Some(str) =>
              F32(str) match {
                case Some(f32) => return AST.Exp.LitF32(f32, toSlangAttr(origin))
                case _ =>
                  reportError(ident.posOpt, s"'$str' is not a valid F32")
                  return dummy
              }
            case _ => return dummy
          }

        case string"f64" =>
          handleNumeric match {
            case Some(str) =>
              F64(str) match {
                case Some(f64) => return AST.Exp.LitF64(f64, toSlangAttr(origin))
                case _ =>
                  reportError(ident.posOpt, s"'$str' is not a valid F64")
                  return dummy
              }
            case _ => return dummy
          }

        case string"r" =>
          handleNumeric match {
            case Some(str) =>
              R(str) match {
                case Some(r) => return AST.Exp.LitR(r, toSlangAttr(origin))
                case _ =>
                  reportError(ident.posOpt, s"'$str' is not a valid R")
                  return dummy
              }
            case _ => return dummy
          }

        case string"z" =>
          handleNumeric match {
            case Some(str) =>
              Z(str) match {
                case Some(z) => return AST.Exp.LitZ(z, toSlangAttr(origin))
                case _ =>
                  reportError(ident.posOpt, s"'$str' is not a valid Z'")
                  return dummy
              }
            case _ => return dummy
          }

        case _ =>
          if (ops.ISZOps(numeric_interpolates).contains(sequenceId)) {
            handleNumeric match {
              case Some(str) =>
                val lit = AST.Exp.LitString(str, AST.Attr(ident.posOpt))
                return AST.Exp.StringInterpolate (sequenceId, ISZ(lit), ISZ(), toSlangTypedAttr (origin) )
              case _ =>
                reportError(numeric.posOpt, "Not a valid numeric")
                return dummy
            }
          } else {
            reportError(ident.posOpt, s"Not currently handling interpolate '$sequenceId'")
            return dummy
          }
      }
    } else {
      reportError(ident.posOpt, s"Not currently handling reserved sequence identifier '$sequenceId'")
      return dummy
    }
  }

  def handleUif(receiver: Option[Exp], ident: Exp.Ident, args: ISZ[Exp], attr: AST.ResolvedAttr): AST.Exp = {

    val dummy = AST.Exp.LitString(value = "???", attr = AST.Attr(ident.posOpt))

    val uif = ident.id.value
    if (ops.ISZOps(portUifs).contains(uif)) {
      def toInvoke(subName: String): AST.Exp.Invoke = {
        return AST.Exp.Invoke(receiver, ident(id = ident.id(value = subName)), ISZ(), args, attr)
      }

      uif match {
        case string"HasEvent" =>
          args match {
            case ISZ(port) => return toInvoke("uif__HasEvent")
            case _ =>
              reportError(ident.posOpt, s"$uif only accepts a port name")
              return dummy
          }
        case string"MaySend" =>
          args match {
            case ISZ(port) => return toInvoke("uif__MaySend")
            case _ =>
              reportError(ident.posOpt, s"$uif only accepts a port name")
              return dummy
          }
        case string"MustSend" =>
          args match {
            case ISZ(port) => return toInvoke("uif__MustSend")
            case ISZ(port, expectedValue) => return toInvoke("uif__MustSendWithExpectedValue")
            case _ =>
              reportError(ident.posOpt, s"$uif only accepts a port name and optionally the expected value")
              return dummy
          }
        case string"NoSend" =>
          args match {
            case ISZ(port) => return toInvoke("uif__NoSend")
            case _ =>
              reportError(ident.posOpt, s"$uif only accepts a port name")
              return dummy
          }
        case _ =>
          halt("Infeasible")
      }
    } else if (ops.ISZOps(binOpsUifs).contains(uif)) {
      def toBinary(lhs: AST.Exp, binOp: String, rhs: AST.Exp): AST.Exp = {
        return AST.Exp.Binary(lhs, binOp, rhs, attr, attr.posOpt)
      }

      val op: String = uif match {
        case string"'->:'" => AST.Exp.BinaryOp.Imply
        case string"'-->:'" => AST.Exp.BinaryOp.CondImply
        case _ =>
          halt(s"Infeasible binary op uif $uif")
          "??"
      }
      if (args.size == 2) {
        return toBinary(args(0), op, args(1))
      } else {
        reportError(ident.posOpt, s"Binary op $uif requires two arguments")
        return dummy
      }
    } else if (ops.ISZOps(logikaUifs).contains(uif)) {
      if (args.size == 1) {
        return AST.Exp.Input(exp = args(0), attr = AST.Attr(attr.posOpt))
      } else {
        reportError(ident.posOpt, s"In accepts exactly one argument")
        return dummy
      }
    }
    else {
      reportError(ident.posOpt, s"Unexpected UIF '${uif}'")
      return dummy
    }
  }

  def visitMetadataAccessExpression(o: RuleMetadataAccessExpressionContext): AST.Exp = {
    halt("")
  }

  def visitLiteralExpression(o: RuleLiteralExpressionContext): AST.Exp = {
    o match {
      case i: RuleLiteralExpression1Context =>
        i.ruleLiteralBoolean().ruleBooleanValue() match {
          case i: RuleBooleanValue1Context =>
            assert(i.K_TRUE() != null)
            return AST.Exp.LitB(value = T, attr = toSlangAttr(o))
          case i: RuleBooleanValue2Context =>
            assert(i.K_FALSE() != null)
            return AST.Exp.LitB(value = F, attr = toSlangAttr(o))
        }

      case i: RuleLiteralExpression2Context =>
        return AST.Exp.LitString(value = i.ruleLiteralString().RULE_STRING_VALUE().string, attr = toSlangAttr(o))

      case i: RuleLiteralExpression3Context =>
        return AST.Exp.LitZ(value = Z(i.ruleLiteralInteger().RULE_DECIMAL_VALUE().string).get, attr = toSlangAttr(o))

      case i: RuleLiteralExpression4Context =>
        /* ruleRealValue:
         *   RULE_DECIMAL_VALUE? '.' (RULE_DECIMAL_VALUE | RULE_EXP_VALUE) #ruleRealValue1
         *   | RULE_EXP_VALUE #ruleRealValue2;
         */
        i.ruleLiteralReal().ruleRealValue() match {
          case i: RuleRealValue1Context =>
            val num: String = if (i.RULE_DECIMAL_VALUE().size() > 1) {
              i.RULE_DECIMAL_VALUE(0).string
            } else {
              "0"
            }
            val decimal = if (i.RULE_EXP_VALUE() != null) {
              reportError(i, "Exponents are not currently supported")
              "0"
            } else {
              if (i.RULE_DECIMAL_VALUE().size() == 1) {
                i.RULE_DECIMAL_VALUE(0).string
              } else {
                i.RULE_DECIMAL_VALUE(1).string
              }
            }
            return AST.Exp.LitF64(value = F64(s"$num.$decimal").get, attr = toSlangAttr(i))
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

  def visitGumboLibrary(o: SysMLv2Parser.RuleGumboLibraryContext): GclLib = {
    val methods = for (m <- listToISZ(o.ruleFunctions().ruleFuncSpec())) yield visitGumboSlangDefDef(m.ruleSlangDefDef())
    return GclLib(
      containingPackage = AirName(name = ISZ(), pos = None()),
      methods = methods,
      attr = toAttr(o)
    )
  }

  def visitGumboSubclause(o: SysMLv2Parser.RuleGumboSubclauseContext): GclSubclause = {
    var state: ISZ[GclStateVar] = ISZ()
    if (nonEmpty(o.ruleSpecSection().ruleState())) {
      state = for (s <- listToISZ(o.ruleSpecSection().ruleState().ruleStateVarDecl())) yield
        visitStateVarDecl(s)
    }

    var methods: ISZ[GclMethod] = ISZ()
    if (nonEmpty(o.ruleSpecSection().ruleFunctions())) {
      methods = for (m <- listToISZ(o.ruleSpecSection().ruleFunctions().ruleFuncSpec())) yield visitGumboSlangDefDef(m.ruleSlangDefDef())
    }

    var invariants: ISZ[GclInvariant] = ISZ()
    if (nonEmpty(o.ruleSpecSection().ruleInvariants())) {
      for (i <- listToISZ(o.ruleSpecSection().ruleInvariants().ruleInvSpec())) {
        invariants = invariants :+ GclInvariant(
          id = i.RULE_ID().string,
          descriptor = if (i.RULE_STRING_VALUE() != null) Some(i.RULE_STRING_VALUE().string) else None(),
          exp = visitOwnedExpression(i.ruleOwnedExpression()),
          attr = toAttr(i))
      }
    }

    var integration: Option[GclIntegration] = None()
    if (nonEmpty(o.ruleSpecSection().ruleIntegration())) {
      val specs = for (s <- listToISZ(o.ruleSpecSection().ruleIntegration().ruleSpecStatement())) yield visitSpecStatement(s)
      integration = Some(GclIntegration(specs = specs, attr = toAttr(o.ruleSpecSection().ruleIntegration())))
    }

    var initializes: Option[GclInitialize] = None()
    if (nonEmpty(o.ruleSpecSection().ruleInitialize())) {
      initializes = Some(visitInitialize(o.ruleSpecSection().ruleInitialize()))
    }

    var compute: Option[GclCompute] = None()
    if (nonEmpty(o.ruleSpecSection().ruleCompute())) {
      compute = Some(visitCompute(o.ruleSpecSection().ruleCompute()))
    }

    return GclSubclause(state = state, methods = methods, invariants = invariants, initializes = initializes, integration = integration, compute = compute, attr = toAttr(o))
  }

  def visitCompute(o: RuleComputeContext): GclCompute = {
    var modifies: ISZ[AST.Exp] = ISZ()
    if (nonEmpty(o.ruleSlangModifies())) {
      modifies = for (m <- listToISZ(o.ruleSlangModifies().ruleOwnedExpression())) yield visitOwnedExpression(m)
    }

    val specs = for (s <- listToISZ(o.ruleSpecStatement())) yield visitSpecStatement(s).asInstanceOf[GclComputeSpec]

    val cases = for (c <- listToISZ(o.ruleCaseStatementClause())) yield visitCaseStatementClause(c)

    val handlers = for (h <- listToISZ(o.ruleHandlerClause())) yield visitHandlerClause(h)

    val flows = for (f <- listToISZ(o.ruleInfoFlowClause())) yield visitInfoFlowClause(f)

    return GclCompute(
      modifies = modifies,
      specs = specs,
      cases = cases,
      handlers = handlers,
      flows = flows,
      attr = toAttr(o))
  }

  def visitHandlerClause(o: RuleHandlerClauseContext): GclHandle = {
    val port = AST.Exp.Ident(id = AST.Id(value = o.RULE_ID().string, attr = toSlangAttr(o)), attr = toSlangResolvedAttr(o))

    var modifies: ISZ[AST.Exp] = ISZ()
    if (nonEmpty(o.ruleSlangModifies())) {
      modifies = for (m <- listToISZ(o.ruleSlangModifies().ruleOwnedExpression())) yield visitOwnedExpression(m)
    }

    val guarantees = for (g <- listToISZ(o.ruleGuaranteeStatement())) yield visitGuaranteeStatement(g)

    return GclHandle(
      port = port,
      modifies = modifies,
      guarantees = guarantees,
      attr = toAttr(o))
  }

  def visitCaseStatementClause(o: RuleCaseStatementClauseContext): GclCaseStatement = {
    return GclCaseStatement(
      id = o.RULE_ID().string,
      descriptor = if (o.RULE_STRING_VALUE() != null) Some(o.RULE_STRING_VALUE().string) else None(),
      assumes = visitOwnedExpression(o.ruleAnonAssumeStatement().ruleOwnedExpression()),
      guarantees = visitOwnedExpression(o.ruleAnonGuaranteeStatement().ruleOwnedExpression()),
      attr = toAttr(o))
  }

  def visitInitialize(o: RuleInitializeContext): GclInitialize = {
    var modifies: ISZ[AST.Exp] = ISZ()
    if (nonEmpty(o.ruleSlangModifies())) {
      modifies = for (m <- listToISZ(o.ruleSlangModifies().ruleOwnedExpression())) yield visitOwnedExpression(m)
    }
    val guarantees = for (g <- listToISZ(o.ruleInitializeSpecStatement())) yield visitGuaranteeStatement(g.ruleGuaranteeStatement())

    val flows = for (f <- listToISZ(o.ruleInfoFlowClause())) yield visitInfoFlowClause(f)

    return GclInitialize(
      modifies = modifies,
      guarantees = guarantees,
      flows = flows,
      attr = toAttr(o))
  }

  def visitInfoFlowClause(o: RuleInfoFlowClauseContext): InfoFlowClause = {
    var froms: ISZ[Exp] = ISZ()
    var tos: ISZ[Exp] = ISZ()
    val index = if (o.RULE_STRING_VALUE() != null) 3 else 2
    var processingFroms: B = T
    for (i <- index until o.getChildCount) {
      o.getChild(i) match {
        case t: TerminalNodeImpl =>
          t.getSymbol.getType match {
            case SysMLv2Parser.K_TO => processingFroms = F
            case SysMLv2Parser.RULE_ID =>
              val id = AST.Exp.Ident(id = AST.Id(value = o.getChild(i).getText, attr = toSlangAttrT(t)), toSlangResolvedAttrT(t))
              if (processingFroms) {
                froms = froms :+ id
              } else {
                tos = tos :+ id
              }
          }
        case _ =>
      }
    }
    return InfoFlowClause(
      id = o.RULE_ID(0).string,
      descriptor = if (o.RULE_STRING_VALUE() != null) Some(o.RULE_STRING_VALUE().string) else None(),
      from = froms,
      to = tos,
      attr = toAttr(o))
  }

  def visitSpecStatement(s: RuleSpecStatementContext): GclSpec = {
    s match {
      case i: RuleSpecStatement1Context => return visitAssumeStatement(i.ruleAssumeStatement())
      case i: RuleSpecStatement2Context => return visitGuaranteeStatement(i.ruleGuaranteeStatement())
    }
  }

  def visitGuaranteeStatement(i: RuleGuaranteeStatementContext): GclGuarantee = {
    return GclGuarantee(
      id = i.RULE_ID().string,
      descriptor = if (i.RULE_STRING_VALUE() != null) Some(i.RULE_STRING_VALUE().string) else None(),
      exp = visitOwnedExpression(i.ruleOwnedExpression()),
      attr = toAttr(i))
  }

  def visitAssumeStatement(i: RuleAssumeStatementContext): GclAssume = {
    return GclAssume(
      id = i.RULE_ID().string,
      descriptor = if (i.RULE_STRING_VALUE() != null) Some(i.RULE_STRING_VALUE().string) else None(),
      exp = visitOwnedExpression(i.ruleOwnedExpression()),
      attr = toAttr(i))
  }

  def visitStateVarDecl(s: RuleStateVarDeclContext): GclStateVar = {
    val typName = st"${(for (t <- visitQualifiedName(s.ruleQualifiedName()).ids) yield t.value, "::")}".render
    return GclStateVar(name = s.RULE_ID().string, classifier = typName, attr = toAttr(s))
  }


  def visitGumboSlangDefDef(o: RuleSlangDefDefContext): GclMethod = {
    reportError(isEmpty(o.ruleSlangDefExt()), o, "Not handling def extensions yet")

    var typeParams: ISZ[AST.TypeParam] = ISZ()
    if (nonEmpty(o.ruleSlangTypeParams())) {
      for (typeParam <- o.ruleSlangTypeParams().ruleSlangTypeParam().asScala) {
        reportError(typeParam.K_MUT() == null, o, "'mut' is not supported for gumbo type params")
        typeParams = typeParams :+ AST.TypeParam(
          id = AST.Id(value = typeParam.RULE_ID().string, attr = toSlangAttr(typeParam)),
          kind = AST.Typed.VarKind.Immutable)
      }
    }

    val methodId: AST.Id = AST.Id(value = o.ruleSlangDefID().RULE_ID().string, attr = toSlangAttr(o.ruleSlangDefID()))

    var params: ISZ[AST.Param] = ISZ()
    for (p <- o.ruleSlangDefParams().ruleSlangDefParam().asScala) {
      val paramName = visitQualifiedNameAsSlangName(p.ruleSlangType().ruleSlangBaseType().ruleQualifiedName())
      params = params :+ AST.Param(
        isHidden = F,
        id = AST.Id(value = p.RULE_ID().string, attr = toSlangAttr(p)),
        tipe = SlangUtil.buildSlangTypedNamed(paramName))
    }

    val retType = SlangUtil.buildSlangTypedNamed(visitQualifiedNameAsSlangName(o.ruleSlangType().ruleSlangBaseType().ruleQualifiedName()))

    val ret = AST.Stmt.Return(expOpt = Some(visitOwnedExpression(o.ruleOwnedExpression())), attr = retType.attr)
    val body = AST.Body(stmts = ISZ(ret), undecls = ISZ())

    var hasContract: B = F

    var readsClause = AST.MethodContract.Accesses.empty
    if (nonEmpty(o.ruleSlangDefContract().ruleSlangReads())) {
      hasContract = T
      var refs: ISZ[AST.Exp.Ref] = ISZ()
      for (read <- o.ruleSlangDefContract().ruleSlangReads().ruleOwnedExpression().asScala) {
        visitOwnedExpression(read) match {
          case i: AST.Exp.Ref => refs = refs :+ i
          case _ => reportError(read, "Only select expressions or simple names are allowed for read clauses")
        }
      }
      readsClause = AST.MethodContract.Accesses(refs = refs, attr = toSlangAttr(o.ruleSlangDefContract().ruleSlangReads()))
    }

    var requiresClause = AST.MethodContract.Claims.empty
    if (nonEmpty(o.ruleSlangDefContract().ruleSlangRequires())) {
      hasContract = T
      val exps: ISZ[AST.Exp] =
        for (e <- listToISZ(o.ruleSlangDefContract().ruleSlangRequires().ruleOwnedExpression())) yield
          visitOwnedExpression(e)
      requiresClause = AST.MethodContract.Claims(claims = exps, attr = toSlangAttr(o.ruleSlangDefContract().ruleSlangRequires()))
    }

    var modifiesClause = AST.MethodContract.Accesses.empty
    if (nonEmpty(o.ruleSlangDefContract().ruleSlangModifies())) {
      hasContract = T
      hasContract = T
      var refs: ISZ[AST.Exp.Ref] = ISZ()
      for (mod <- o.ruleSlangDefContract().ruleSlangModifies().ruleOwnedExpression().asScala) {
        visitOwnedExpression(mod) match {
          case i: AST.Exp.Ref => refs = refs :+ i
          case _ => reportError(mod, "Only select expressions or simple names are allowed for modifies clauses")
        }
      }
      modifiesClause = AST.MethodContract.Accesses(refs = refs, attr = toSlangAttr(o.ruleSlangDefContract().ruleSlangModifies()))
    }

    var ensuresClause = AST.MethodContract.Claims.empty
    if (nonEmpty(o.ruleSlangDefContract().ruleSlangEnsures())) {
      hasContract = T
      val exps: ISZ[AST.Exp] =
        for (e <- listToISZ(o.ruleSlangDefContract().ruleSlangEnsures().ruleOwnedExpression())) yield
          visitOwnedExpression(e)
      ensuresClause = AST.MethodContract.Claims(claims = exps, attr = toSlangAttr(o.ruleSlangDefContract().ruleSlangEnsures()))
    }

    val mcontract = if (hasContract) {
      val infoFlows = AST.MethodContract.InfoFlows.empty
      AST.MethodContract.Simple(
        readsClause = readsClause, requiresClause = requiresClause, modifiesClause = modifiesClause, ensuresClause = ensuresClause, infoFlowsClause = infoFlows,
        attr = toSlangAttr(o.ruleSlangDefContract()))
    } else {
      AST.MethodContract.Simple.empty
    }

    val purity = if (hasContract) AST.Purity.Pure else AST.Purity.StrictPure

    val sig = AST.MethodSig(
      purity = purity,
      id = methodId,
      typeParams = typeParams,
      hasParams = params.nonEmpty,
      params = params,
      returnType = retType)

    val method = AST.Stmt.Method(
      typeChecked = F,
      purity = purity,
      modifiers = ISZ(),
      sig = sig,
      mcontract = mcontract,
      bodyOpt = Some(body),
      attr = toSlangResolvedAttr(o))

    return GclMethod(method)
  }

  def toSlangAttr(o: ParserRuleContext): AST.Attr = {
    return AST.Attr(posOpt = toPosOpt(o))
  }

  def toAttr(o: ParserRuleContext): Attr = {
    return Attr(posOpt = toPosOpt(o))
  }

  def toSlangResolvedAttrT(t: TerminalNodeImpl): AST.ResolvedAttr = {
    return AST.ResolvedAttr(posOpt = toPosOptT(t), resOpt = None(), typedOpt = None())
  }

  def toSlangAttrT(t: TerminalNodeImpl): AST.Attr = {
    return AST.Attr(posOpt = toPosOptT(t))
  }

  def toSlangResolvedAttr(o: ParserRuleContext): AST.ResolvedAttr = {
    return AST.ResolvedAttr(posOpt = toPosOpt(o), resOpt = None(), typedOpt = None())
  }

  def toResolvedAttr(o: ParserRuleContext): ResolvedAttr = {
    return ResolvedAttr(posOpt = toPosOpt(o), resOpt = None(), typedOpt = None())
  }

  def toSlangTypedAttr(o: ParserRuleContext): AST.TypedAttr = {
    return AST.TypedAttr(posOpt = toPosOpt(o), typedOpt = None())
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

  def toPosOptT(t: TerminalNodeImpl): Option[Position] = {
    val length = t.getSourceInterval.length()
    return Some(
      FlatPos(
        uriOpt = uriOpt,
        beginLine32 = conversions.Z.toU32(t.getSymbol.getLine),
        beginColumn32 = conversions.Z.toU32(t.getSymbol.getCharPositionInLine),
        endLine32 = conversions.Z.toU32(t.getSymbol.getLine),
        endColumn32 = conversions.Z.toU32(t.getSymbol.getCharPositionInLine + length),
        offset32 = conversions.Z.toU32(t.getSymbol.getStartIndex),
        length32 = conversions.Z.toU32(length)
      )
    )
  }


  def reportWarn(nodeOrList: Object, message: String): Unit = {
    reportWarn(F, nodeOrList, message)
  }

  def reportWarn(cond: B, nodeOrList: Object, message: String): Unit = {
    if (!cond) {
      val posOpt: Option[Position] = nodeOrList match {
        case i: ParserRuleContext => toPosOpt(i)
        case i: TerminalNodeImpl => toPosOptT(i)
        case i: java.util.List[x] => toPosOpt(i.get(0).asInstanceOf[ParserRuleContext])
        case x =>
          reportError(null, s"Trying to create a posOpt for a warning report but wasn't expecting $x")
          return
      }
      reporter.warn(posOpt, kind, message)
    }
  }

  def reportError(nodeOrList: Object, message: String): Unit = {
    reportError(F, nodeOrList, message)
  }

  def reportError(cond: B, nodeOrList: Object, message: String): Unit = {
    if (!cond) {
      val posOpt: Option[Position] = nodeOrList match {
        case i: ParserRuleContext => toPosOpt(i)
        case i: TerminalNodeImpl => toPosOptT(i)
        case i: java.util.List[x] => toPosOpt(i.get(0).asInstanceOf[ParserRuleContext])
        case null => None()
        case x =>
          reportError(None(), s"Trying to create a posOpt for an error report but wasn't expecting $x")
          return
      }
      reportError(posOpt, message)
    }
  }

  def reportError(posOpt: Option[Position], message: String): Unit = {
    reporter.error(posOpt, kind, message)
  }
}
