package org.sireum.hamr.sysml

import org.sireum._
import org.sireum.message._
import org.antlr.v4.runtime.ParserRuleContext
import org.sireum.hamr.ir._
import org.sireum.hamr.sysml.SysmlAst._
import org.sireum.hamr.sysml.SysmlAstUtil.{Placeholders, isRegularComment}
import org.sireum.hamr.sysml.parser.SysMLv2Parser._
import org.sireum.hamr.sysml.parser.SysMLv2Parser
import org.sireum.message.{Position, Reporter}
import org.sireum.lang.{ast => AST}
import org.sireum.lang.ast.Exp

import java.util
import scala.collection.mutable
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
            visitPackage(m1.ruleDefinitionElement())
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

  def visitPackage(context: RuleDefinitionElementContext): Unit = {
    var isLibrary = F
    var isStandard = F
    val (prefixMetaDataMembers, packageDeclaration, packageBody): (mutable.Buffer[RulePrefixMetadataMemberContext], RulePackageDeclarationContext, RulePackageBodyContext) =
      context match {
        case d1: RuleDefinitionElement1Context =>
          (d1.rulePackage().rulePrefixMetadataMember().asScala, d1.rulePackage().rulePackageDeclaration(), d1.rulePackage().rulePackageBody())
        case d2: RuleDefinitionElement2Context =>
          assert(d2.ruleLibraryPackage().K_LIBRARY() != null)
          isLibrary = T
          isStandard = d2.ruleLibraryPackage().K_STANDARD() != null
          (d2.ruleLibraryPackage.rulePrefixMetadataMember().asScala, d2.ruleLibraryPackage().rulePackageDeclaration(), d2.ruleLibraryPackage().rulePackageBody())
        case d3: RuleDefinitionElement3Context =>

          if (!SysmlAstUtil.isRegularComment(d3.ruleAnnotatingElement())) {
            val annotatingElem = visitAnnotatingElement(d3.ruleAnnotatingElement())
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

    var packageElements: ISZ[PackageElement] = ISZ()
    packageBody match {
      case b1: RulePackageBody1Context =>
        assert(b1.OP_SEMI() != null)

      case b2: RulePackageBody2Context =>

        reportError(b2.ruleElementFilterMember().isEmpty, b2, "Package filter members are not currently supported")

        val imports: ISZ[Import] = visitImports(b2.ruleImport().asScala)
        val aliases: ISZ[Alias] = visitAliases(b2.ruleAliasMember().asScala)

        reportWarn(imports.isEmpty, b2.ruleImport(), s"Need to add imports to AST: $imports")
        reportWarn(aliases.isEmpty, b2.ruleAliasMember(), s"Need to add aliasing to AST: $aliases")

        for (member <- b2.rulePackageMember().asScala) {
          val visibility = visitVisibilityIndicator(member.ruleMemberPrefix().ruleVisibilityIndicator())

          if (member.ruleUsageElement() != null) {
            reportWarn(member.ruleUsageElement(), "Why not just have user select the system part def they want to instantiate?")
          } else {
            packageElements = packageElements :+
              visitPackageMember(member.ruleDefinitionElement())
          }
        }
    }
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

  def visitPackageMember(context: RuleDefinitionElementContext): PackageElement = {
    context match {
      case d1: RuleDefinitionElement1Context =>
        reportWarn(d1, "Need to handle nested package definitions")
        return PlaceholderPackageElement()

      case d6: RuleDefinitionElement6Context =>
        return visitEnumerationDefinition(d6.ruleEnumerationDefinition())

      case d5: RuleDefinitionElement5Context =>
        reportWarn(d5, "Need to handle attribute definition (aka data type defs)")
        val attributeDef = d5.ruleAttributeDefinition()
        return PlaceholderPackageElement()

      case d10: RuleDefinitionElement10Context =>
        return visitMetadataDefinition(d10.ruleMetadataDefinition())

      case d11: RuleDefinitionElement11Context =>
        return visitPartDefinition(d11.rulePartDefinition())

      case d12: RuleDefinitionElement12Context =>
        // e.g. connection def PortConnection :> BinaryConnection {
        //		    end source : AbstractFeature[0..*] :>> BinaryConnection::source;
        val connectionDef = d12.ruleConnectionDefinition()
        reportWarn(connectionDef, "Need to handle connection definitions at the package level")
        return PlaceholderPackageElement()

      case d15: RuleDefinitionElement15Context =>
        val allocationDef = d15.ruleAllocationDefinition()
        reportWarn(allocationDef, "Need to handle allocation definitions at the package level")
        return PlaceholderPackageElement()

      case d16: RuleDefinitionElement16Context =>
        return visitPortDefinition(d16.rulePortDefinition())

      case x =>
        reportError(x, s"Not currently supporting package members of type: ${x.getClass.getSimpleName}")
        return PlaceholderPackageElement()
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

  private def visitPortDefinition(portDef: RulePortDefinitionContext): PackageElement = {

    var isAbstract = F
    var isVariation = F

    if (nonEmpty(portDef.ruleDefinitionPrefix().ruleBasicDefinitionPrefix())) {
      portDef.ruleDefinitionPrefix().ruleBasicDefinitionPrefix() match {
        case p1: RuleBasicDefinitionPrefix1Context =>
          assert(p1.K_ABSTRACT() != null)
          isAbstract = T
        case p2: RuleBasicDefinitionPrefix2Context =>
          assert(p2.K_VARIATION() != null)
          isVariation = T
      }
    }

    if (!portDef.ruleDefinitionPrefix().ruleDefinitionExtensionKeyword().isEmpty) {
      reportError(portDef.ruleDefinitionPrefix(), "Need to handle definition extensions")
    }

    val (identifier, specializations, bodyItems) = visitDefinition(portDef.ruleDefinition())

    // TODO: conjugated port def leads to an empty rule
    val empty = portDef.ruleConjugatedPortDefinitionMember().ruleConjugatedPortDefinition().rulePortConjugation()

    return PortDefinition(
      isAbstract = isAbstract,
      isVariation = isVariation,
      identification = identifier,
      specializations = specializations,
      definitionBodyItems = bodyItems
    )
  }

  private def visitMetadataDefinition(metadataDefinition: RuleMetadataDefinitionContext): PackageElement = {
    reportWarn(metadataDefinition, "Need to handle metadata definitions")
    return PlaceholderPackageElement()
  }

  private def visitPartDefinition(partDef: RulePartDefinitionContext): PackageElement = {

    val occurrenceDefPrefix = visitOccurrenceDefinitionPrefix(partDef.ruleOccurrenceDefinitionPrefix())

    val (identifier, specializations, bodyItems) = visitDefinition(partDef.ruleDefinition())

    return PartDefinition(
      occurrenceDef = occurrenceDefPrefix,
      identifier = identifier,
      specializations = specializations,
      bodyItems = bodyItems
    )
  }


  private def visitPartUsage(partUsage: RulePartUsageContext): PartUsage = {
    /** eg.
      * part tempSensor : TempSensor;
		  * part tempControl : TempControl;
		  */
    val occurrenceUsage: OccurrenceUsage = visitOccurrenceUsagePrefix(partUsage.ruleOccurrenceUsagePrefix())

    val u = visitUsage(partUsage.ruleUsage())

    return PartUsage(
      occurrenceUsage = occurrenceUsage,
      identification = u.identification,
      specializations = u.specializations,
      definitionBodyItems = u.definitionBodyItems)
  }

  def visitPortUsage(portUsage: RulePortUsageContext): PortUsage = {
    /** e.g.
      * out port currentTemp : DataPort { out :> type : Temperature; }
		  * out port tempChanged : EventPort;
      */

    //  A port usage is a kind of occurrence usage definition that is a usage of a port definition.
    val occurrenceUsage: OccurrenceUsage = visitOccurrenceUsagePrefix(portUsage.ruleOccurrenceUsagePrefix())

    val u = visitUsage(portUsage.ruleUsage())

    return PortUsage(
      occurrenceUsage = occurrenceUsage,
      identification = u.identification,
      specializations = u.specializations,
      definitionBodyItems = u.definitionBodyItems)
  }

  private def visitUsage(ruleUsage: RuleUsageContext): UsageHolder = {

    var identification: Option[Identification] = None()
    var specializations: ISZ[FeatureSpecialization] = ISZ()
    if (nonEmpty(ruleUsage.ruleUsageDeclaration())) {
      ruleUsage.ruleUsageDeclaration().ruleFeatureDeclaration() match {
        case i1: RuleFeatureDeclaration1Context =>
          identification = Some(visitIdentification(i1.ruleIdentification()))
          if (nonEmpty(i1.ruleFeatureSpecializationPart())) {
            specializations = visitFeatureSpecialization(i1.ruleFeatureSpecializationPart())
          }
        case i2: RuleFeatureDeclaration2Context =>
          specializations = visitFeatureSpecialization(i2.ruleFeatureSpecializationPart())
      }
    }

    if (nonEmpty(ruleUsage.ruleUsageCompletion().ruleValuePart())) {
      val v = ruleUsage.ruleUsageCompletion().ruleValuePart().ruleFeatureValue()
      val isBound = v.OP_EQ() != null
      val isInitial = v.OP_COLON_EQ() != null
      val isDefault = v.K_DEFAULT() != null
      val exp = visitOwnedExpression(v.ruleOwnedExpression())
    }


    val definitionBodyItems: ISZ[BodyElement] = visitDefinitionBody(ruleUsage.ruleUsageCompletion().ruleUsageBody().ruleDefinitionBody())

    return UsageHolder(
      identification,
      specializations,
      definitionBodyItems
    )
  }


  private def visitDefinition(ruleDefinition: RuleDefinitionContext):
  (Option[Identification], ISZ[FeatureSpecialization], ISZ[BodyElement]) = {
    val decl = ruleDefinition.ruleDefinitionDeclaration()
    val identifier: Option[Identification] =
      if (isEmpty(decl.ruleIdentification())) None()
      else Some(visitIdentification(decl.ruleIdentification()))

    val specializations: ISZ[FeatureSpecialization] = {
      if (nonEmpty(decl.ruleSubclassificationPart()))
        for (subclass <- listToISZ(decl.ruleSubclassificationPart().ruleOwnedSubclassification())) yield
          QualifiedNameSpecialization(visitQualifiedName(subclass.ruleQualifiedName()))
      else
        ISZ()
    }

    val bodyItems: ISZ[BodyElement] = visitDefinitionBody(ruleDefinition.ruleDefinitionBody())

    return (identifier, specializations, bodyItems)
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
              val defElem = defMember.ruleDefinitionElement()

              reportWarn(defElem, "Need to handle definition member in definition body")

            case bi2: RuleDefinitionBodyItem2Context =>
              val variant = bi2.ruleVariantUsageMember()
              val visibility = visitVisibilityIndicator(variant.ruleMemberPrefix().ruleVisibilityIndicator())
              val variantElem = variant.ruleVariantUsageElement()

              reportError(variantElem, "Variants are not currently handled in definition body")

            case bi3: RuleDefinitionBodyItem3Context =>
              items = items :+ visitNonOccurrenceUsage(bi3.ruleNonOccurrenceUsageMember())

            case bi4: RuleDefinitionBodyItem4Context =>
              if (nonEmpty(bi4.ruleEmptySuccessionMember())) {
                reportError(bi4.ruleEmptySuccessionMember(),
                  "Successions are not currently handled in definition body")
              }
              items = items :+ visitOccurrenceUsage(bi4.ruleOccurrenceUsageMember())

            case bi5: RuleDefinitionBodyItem5Context =>
              items = items :+ visitAlias(bi5.ruleAliasMember())

            case bi6: RuleDefinitionBodyItem6Context =>
              items = items :+ visitImport(bi6.ruleImport())
          }
        }
        return items
    }
  }

  private def visitOccurrenceUsage(o: RuleOccurrenceUsageMemberContext): BodyElement = {
    val visibility = visitVisibilityIndicator(o.ruleMemberPrefix().ruleVisibilityIndicator())
    o.ruleOccurrenceUsageElement() match {
      case i1: RuleOccurrenceUsageElement1Context =>
        i1.ruleStructureUsageElement() match {
          case su6: RuleStructureUsageElement6Context =>
            return visitPartUsage(su6.rulePartUsage())

          case su9: RuleStructureUsageElement9Context =>
            return visitPortUsage(su9.rulePortUsage())

          case su10: RuleStructureUsageElement10Context =>
            val connectionUsage = su10.ruleConnectionUsage()
            reportError(connectionUsage, "Need to handle occurrence connection usages")
            return Placeholders.BodyItemPlaceholder

          case x =>
            reportError(x, s"${x.getClass.getSimpleName} are not currently supported")
            return Placeholders.BodyItemPlaceholder
        }

      case i2: RuleOccurrenceUsageElement2Context =>
        val elem = i2.ruleBehaviorUsageElement()
        reportError(elem, "Not currently supporting behavior usages")
        return Placeholders.BodyItemPlaceholder
    }
  }

  private def visitNonOccurrenceUsage(o: RuleNonOccurrenceUsageMemberContext): BodyElement = {
    val visibility = visitVisibilityIndicator(o.ruleMemberPrefix().ruleVisibilityIndicator())
    o.ruleNonOccurrenceUsageElement() match {
      case i1: RuleNonOccurrenceUsageElement1Context =>
        val refDefaultUsage = i1.ruleDefaultReferenceUsage()
        reportError(refDefaultUsage, "Need to handle non-occurrence default usages")
        return Placeholders.BodyItemPlaceholder

      case i2: RuleNonOccurrenceUsageElement2Context =>
        val refUsage = i2.ruleReferenceUsage()
        reportError(refUsage, "Need to handle non-occurrence reference usages")
        return Placeholders.BodyItemPlaceholder

      case i3: RuleNonOccurrenceUsageElement3Context =>
        return visitAttributeUsage(i3.ruleAttributeUsage())

      case i4: RuleNonOccurrenceUsageElement4Context =>
        val enumUsage = i4.ruleEnumerationUsage()
        reportError(enumUsage, "Need to handle non-occurrence enum usages")
        return Placeholders.BodyItemPlaceholder

      case i5: RuleNonOccurrenceUsageElement5Context =>
        val bindingConnector = i5.ruleBindingConnectorAsUsage()
        reportError(bindingConnector, "Need to handle non-occurrence binding connectors")
        return Placeholders.BodyItemPlaceholder

      case i6: RuleNonOccurrenceUsageElement6Context =>
        val successionUsage = i6.ruleSuccessionAsUsage()
        reportError(successionUsage, "Need to handle non-occurrence succession usages")
        return Placeholders.BodyItemPlaceholder

      case i7: RuleNonOccurrenceUsageElement7Context =>
        val extendedUsage = i7.ruleExtendedUsage()
        reportError(extendedUsage, "Need to handle non-occurrence extended usages")
        return Placeholders.BodyItemPlaceholder
    }
  }

  def visitAttributeUsage(o: RuleAttributeUsageContext): AttributeUsage = {

    val p = visitUsagePrefix(o.ruleUsagePrefix())
    val u = visitUsage(o.ruleUsage())

    return AttributeUsage(
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

  private def visitFeatureSpecialization(fs: RuleFeatureSpecializationPartContext): ISZ[FeatureSpecialization] = {
    var ret: ISZ[FeatureSpecialization] = ISZ()
    fs match {
      case s1: RuleFeatureSpecializationPart1Context =>
        reportError(isEmpty(s1.ruleMultiplicityPart()), s1.ruleMultiplicityPart(), "Multiplicities are not currently supported")
        for (s <- s1.ruleFeatureSpecialization().asScala) {
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
              def qns(or: RuleOwnedRedefinitionContext): QualifiedName = {
                or match {
                  case o1: RuleOwnedRedefinition1Context => return visitQualifiedName(o1.ruleQualifiedName())
                  case o2: RuleOwnedRedefinition2Context =>
                    reportError(o2, "Feature chaining is not currently supported")
                    return ISZ()
                }
              }

              val redefinitions = x.ruleRedefinitions()
              var qualifiedNames: ISZ[QualifiedName] = ISZ(qns(redefinitions.ruleRedefines().ruleOwnedRedefinition()))
              qualifiedNames = qualifiedNames ++ (for (rod <- listToISZ(redefinitions.ruleOwnedRedefinition())) yield qns(rod))

              ret = ret :+ RedefinitionSpecialization(qualifiedNames)

            case x => halt(s"Not expecting $x")
          }
        }
      case s2: RuleFeatureSpecializationPart2Context =>
        reportError(s2.ruleMultiplicityPart(), "Not currently handling payload specialization")
    }
    return ret
  }

  private def visitUsagePrefix(o: RuleUsagePrefixContext): UsagePrefix = {

    var direction: Option[FeatureDirection.Type] = None()
    var isAbstract: B = F
    var isVariation: B = F
    var isReadOnly: B = F
    var isDerived: B = F
    var isEnd: B = F
    var isRef: B = F
    var isIndividual: B = F
    var usageExtensions: ISZ[QualifiedName] = ISZ()


    if (nonEmpty(o.ruleBasicUsagePrefix())) {
      val bup = o.ruleBasicUsagePrefix()
      if (nonEmpty(bup.ruleRefPrefix().ruleFeatureDirection())) {
        bup.ruleRefPrefix().ruleFeatureDirection() match {
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

      isAbstract = bup.ruleRefPrefix().K_ABSTRACT() != null
      isVariation = bup.ruleRefPrefix().K_VARIATION() != null
      isReadOnly = bup.ruleRefPrefix().K_READONLY() != null
      isDerived = bup.ruleRefPrefix().K_DERIVED() != null
      isEnd = bup.ruleRefPrefix().K_END() != null

      isRef = bup.K_REF() != null
    }

    if (!o.ruleUsageExtensionKeyword().isEmpty) {
      for (ue <- o.ruleUsageExtensionKeyword().asScala) {
        usageExtensions = usageExtensions :+
          visitQualifiedName(ue.rulePrefixMetadataMember().rulePrefixMetadataUsage().ruleMetadataTyping().ruleQualifiedName())
      }
    }

    return UsagePrefix(
      direction = direction,
      isAbstract = isAbstract,
      isVariation = isVariation,
      isReadOnly = isReadOnly,
      isDerived = isDerived,
      isEnd = isEnd,
      isRef = isRef,
      usageExtensions = usageExtensions)
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
          assert(i.K_SNAPSHOT() != null)
          isSnapshot = T
        case i: RulePortionKind2Context =>
          assert(i.K_TIMESLICE() != null)
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
    return ISZ(l.asScala.toSeq: _*)
  }

  /** ruleAliasMember: ruleMemberPrefix 'alias' ('<' ruleName '>')? ruleName? 'for' ruleQualifiedName ruleRelationshipBody;
   */
  def visitAliases(aliases: mutable.Buffer[RuleAliasMemberContext]): ISZ[Alias] = {
    var ret: ISZ[Alias] = ISZ()
    for (alias <- aliases) {
      ret = ret :+ visitAlias(alias)
    }
    return ret
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

  def visitImports(imports: mutable.Buffer[RuleImportContext]): ISZ[Import] = {
    var ret: ISZ[Import] = ISZ()
    for (importe <- imports) {
      ret = ret :+ visitImport(importe)
    }
    return ret
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

      val qualfiedName = visitQualifiedName(namespace.ruleQualifiedName())

      assert(nimport.ruleImportedNamespace().OP_STAR() != null, "The star is not optional")

      return Import(
        visibility = visibility,
        qualifiedName = qualfiedName,
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
          reportWarn(i, "TODO, can sysmlv2's === be mapped to Slang's ===")
          AST.Exp.BinaryOp.Equiv
        case i: RuleEqualityOperator4Context =>
          assert(i.OP_BANG_EQ_EQ() != null)
          reportWarn(i, "TODO, can sysmlv2's !== be mapped to Slang's =!=")
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

  private def visitPrimaryExpression(o: RulePrimaryExpressionContext): AST.Exp = {
    val e = visitBaseExpression(o.ruleBaseExpression())

    reportError(o.ruleFeatureChainMember().isEmpty, o, "Chaining not yet supported")
    reportError(o.ruleSequenceExpression().isEmpty, o, "Sequencing is not yet supported")
    reportError(o.ruleReferenceTyping().isEmpty, o, "Reference typing is not yet supported")
    reportError(o.ruleBodyExpression().isEmpty, o, "Body expressions are not yet supported")
    reportError(o.ruleFunctionReferenceExpression().isEmpty, o, "Function references are not yet supported")
    reportError(o.ruleArgumentList().isEmpty, o, "Argument lists are not yet supported")

    return e
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
    halt("")
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
            assert (i.K_TRUE() != null)
            return AST.Exp.LitB(value = T, attr = Placeholders.emptyAttr)
          case i: RuleBooleanValue2Context =>
            assert (i.K_FALSE() != null)
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
