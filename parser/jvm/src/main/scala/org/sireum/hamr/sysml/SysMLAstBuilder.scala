package org.sireum.hamr.sysml

import org.sireum._
import org.sireum.message._
import org.antlr.v4.runtime.ParserRuleContext
import org.sireum.hamr.ir._
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
          val m1: RulePackageMemberContext = r1.rulePackageMember()
          var visibility: String = visitRuleVisibilityIndicator(m1.ruleMemberPrefix().ruleVisibilityIndicator())
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
      case x =>
          reportError(x, s"Not currently handling the following package level node: $x")
          return
    }
    if (prefixMetaDataMembers.nonEmpty) {
      reportError(packageDeclaration, "Package metadata not currently supported")
    }
    var packageName: ISZ[String] = ISZ()
    if (packageDeclaration.ruleIdentification() != null) {
      packageName = visitRuleIdentification(packageDeclaration.ruleIdentification())
    } else {
      reportError(packageDeclaration, "Why is package identification optional?")
    }
    packageBody match {
      case b1: RulePackageBody1Context =>
        reportError(b1, "Empty package definitions are not currently supported")
      case b2: RulePackageBody2Context =>
        reportError(b2.ruleElementFilterMember().isEmpty, b2, "Package filter members are not currently supported")

        val imports: ISZ[ISZ[String]] = visitImports(b2.ruleImport().asScala)
        val aliases: ISZ[(String, ISZ[String])] = visitAliases(b2.ruleAliasMember().asScala)

        reportWarn(imports.isEmpty, b2, s"Need to add imports to AST: $imports")
        reportWarn(aliases.isEmpty, b2, s"Need to add aliasing to AST: $aliases")

        var components: ISZ[Component] = ISZ()
        for (member <- b2.rulePackageMember().asScala) {
          val visibility = visitRuleVisibilityIndicator(member.ruleMemberPrefix().ruleVisibilityIndicator())
          reportWarn(visibility == string"public", member, "Need to add visibility to AST")

          if (member.ruleUsageElement() != null) {
            reportWarn(member.ruleUsageElement(), "Why not just have user select the system part def they want to instantiate?")
          } else {
            components = components :+ visitPackageMember(member.ruleDefinitionElement())
          }
        }
    }
  }

  def visitPackageMember(context: RuleDefinitionElementContext): Component = {
    context match {
      case d11: RuleDefinitionElement11Context =>
        return visitPackage_PartDefinition(d11.rulePartDefinition())

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

      case d16: RuleDefinitionElement16Context =>
        return visitPortDefintion(d16.rulePortDefinition())

      case x =>
        reportError(x, s"Not currently supporting package members of type: ${x.getClass.getSimpleName}")
        return SysMLUtil.emptyComponent
    }
  }

  private def visitPortDefintion(portDef: RulePortDefinitionContext): Component = {
    reportWarn(portDef, "Need to handle port definitions")
    return SysMLUtil.emptyComponent
  }

  private def visitMetadataDefinition(metadataDefinition: RuleMetadataDefinitionContext): Component = {
    reportWarn(metadataDefinition, "Need to handle metadata definitions")
    return SysMLUtil.emptyComponent
  }

  private def visitPackage_PartDefinition(partDef: RulePartDefinitionContext): Component = {
    var isAbstract: B = F
    if (partDef.ruleOccurrenceDefinitionPrefix().getChildCount != 0) {
      reportError(partDef.ruleOccurrenceDefinitionPrefix().K_INDIVIDUAL() == null, partDef.ruleOccurrenceDefinitionPrefix(),
        "The 'individual' keyword is not currently supported")
      reportError(partDef.ruleOccurrenceDefinitionPrefix().ruleLifeClassMembership() == null, partDef.ruleOccurrenceDefinitionPrefix().ruleLifeClassMembership(),
        "Life Classes are not currently supported")
      reportError(partDef.ruleOccurrenceDefinitionPrefix().ruleDefinitionExtensionKeyword().isEmpty, partDef.ruleOccurrenceDefinitionPrefix(),
        "Extension definitions are not currently supported")

      if (partDef.ruleOccurrenceDefinitionPrefix().ruleBasicDefinitionPrefix() != null) {
        partDef.ruleOccurrenceDefinitionPrefix().ruleBasicDefinitionPrefix() match {
          case x: RuleBasicDefinitionPrefix1Context => isAbstract = T
          case x: RuleBasicDefinitionPrefix2Context =>
            reportError(x, "The 'variation' keyword is not currently supported")
        }
      }
    }

    reportError(partDef.ruleDefinition().ruleDefinitionDeclaration().ruleIdentification() != null, partDef.ruleDefinition().ruleDefinitionDeclaration().ruleIdentification(),
      "Part definitions must have an identification")

    val identifier: Name =
      partDef.ruleDefinition().ruleDefinitionDeclaration().ruleIdentification() match {
      case i2: RuleIdentification2Context =>
        Name(name = ISZ(visitName(i2.ruleName())), pos = toPosOpt(i2.ruleName()))
      case i1: RuleIdentification1Context =>
          reportError(i1, "Not currently supporting short names")
          SysMLUtil.emptyName()
    }

    val specializations: ISZ[ISZ[String]] = {
      if (partDef.ruleDefinition().ruleDefinitionDeclaration().ruleSubclassificationPart() != null) {
        for (subclass <- listToISZ(partDef.ruleDefinition().ruleDefinitionDeclaration().ruleSubclassificationPart().ruleOwnedSubclassification())) yield
          visitQualifiedName(subclass.ruleQualifiedName())
      } else if (!isAbstract) {
        reportError(partDef, "Non-abstract package level part definitions must specialize a component type")
        ISZ()
      } else {
        ISZ()
      }
    }

    var subComponents: ISZ[Component] = ISZ()
    var features: ISZ[Feature] = ISZ()
    var connections: ISZ[Connection] = ISZ()

    partDef.ruleDefinition().ruleDefinitionBody() match {
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
                        val partUsage = su6.rulePartUsage()
                        reportWarn(partUsage, "Need to handle part usages")
                      case su9: RuleStructureUsageElement9Context =>
                        val portUsage = su9.rulePortUsage()
                        reportWarn(portUsage, "Need to handle port usages")
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
      case i1: RuleDefinitionBody1Context =>
        if (!isAbstract) {
          reportError(i1, "Non-abstract part definitions cannot be empty")
        }
    }

    return Component(
      identifier = identifier,
      category = ComponentCategory.Process,
      classifier = None(),
      features = features,
      subComponents = subComponents,
      connections = connections,
      connectionInstances = ISZ(),
      properties = ISZ(),
      flows = ISZ(),
      modes = ISZ(),
      annexes = ISZ(),
      uriFrag = "")

  }

  private def listToISZ[T](l: util.List[T]): ISZ[T] = {
    return ISZ(l.asScala.toSeq:_*)
  }

  def visitRuleIdentification(id: RuleIdentificationContext): ISZ[String] = {
    return id match {
      case id1: RuleIdentification1Context =>
        reportError(id1, "Not currently supporting ruleIdentification1")
        return ISZ()
      case id2: RuleIdentification2Context =>
        return ISZ(visitName(id2.ruleName()))
    }
  }

  def visitAliases(aliases: mutable.Buffer[RuleAliasMemberContext]): ISZ[(String, ISZ[String])] = {
    var ret: ISZ[(String, ISZ[String])] = ISZ()
    for (alias <- aliases) {
      reportError(alias.ruleMemberPrefix().getChildCount == 0, alias.ruleMemberPrefix(), "Visibility annotations not currently supported for aliases")
      reportError(alias.LANGLE() == null && alias.RANGLE() == null, alias, "Alias short names are not currently supported")
      reportError(SysMLUtil.isEmptyRelationshipBody(alias.ruleRelationshipBody()), alias.ruleRelationshipBody(), "Alias relationship bodies are not currently supported")
      reportError(alias.ruleName().size() == 1, alias, "Only expecting a single name before 'for' keyword")

      var theAlias: String = ""
      if (!alias.ruleName().isEmpty) {
        theAlias = visitName(alias.ruleName.get(0))
      }

      val theFor: ISZ[String] = visitQualifiedName(alias.ruleQualifiedName())

      ret = ret :+ (theAlias, theFor)
    }

    return ret
  }

  def visitImports(imports: mutable.Buffer[RuleImportContext]): ISZ[ISZ[String]] = {
    var ret: ISZ[ISZ[String]] = ISZ()
    for (importe <- imports ){
      reportError(importe.ruleRelationshipBody().isInstanceOf[RuleRelationshipBody1Context], importe.ruleRelationshipBody(),
        "Not currently handling import annotations")

      if (importe.ruleMembershipImport() != null) {
        assert (importe.ruleNamespaceImport() == null)
        val mimport = importe.ruleMembershipImport()

        reportError(mimport.ruleImportPrefix().ruleVisibilityIndicator() == null, mimport, "Not currently handling visibility annotations for imports")
        reportError(mimport.ruleImportPrefix().K_ALL() == null, mimport, "Not currently handling 'all' import annotations")

        ret = ret :+ visitQualifiedName(mimport.ruleImportedMembership().ruleQualifiedName())
      } else {
        val nimport = importe.ruleNamespaceImport()

        reportError(nimport.ruleImportPrefix().ruleVisibilityIndicator() == null, nimport, "Not currently handling visibility annotations for imports")
        reportError(nimport.ruleImportPrefix().K_ALL() == null, nimport, "Not currently handling 'all' import annotations")

        if (nimport.ruleFilterPackage() != null) {
          reportError(nimport, "Not currently handling filtered imports")
        } else {
          val namespace = nimport.ruleImportedNamespace()
          ret = ret :+ (visitQualifiedName(namespace.ruleQualifiedName()) :+ "*")

          reportError(namespace.OP_STAR_STAR() == null, namespace, "Not currently handling '**' imports")
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

  def visitRuleVisibilityIndicator(context: RuleVisibilityIndicatorContext): String = {
      context match {
        case null =>
          return "public" // TODO: assuming default visibility is public in sysmlv2
        case v1: RuleVisibilityIndicator1Context =>
          val dummyTest = v1.K_PUBLIC()
          return "public"
        case v2:RuleVisibilityIndicator2Context =>
          val dummyTest = v2.K_PRIVATE()
          return "private"
        case v3:RuleVisibilityIndicator3Context =>
          val dummyTest = v3.K_PROTECTED()
          return "protected"
        case x =>
          reportError(x, s"Not expecting visibility: $x ")
          return ""
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

  def reportWarn(node: ParserRuleContext, message: String): Unit = {
   reportWarn(F, node, message)
  }

  def reportWarn(cond: B, node: ParserRuleContext, message: String): Unit = {
    if (!cond) {
      reporter.warn(toPosOpt(node), kind, message)
    }
  }

  def reportError(node: ParserRuleContext, message: String): Unit = {
    reportError(F, node, message)
  }

  def reportError(cond: B, node: ParserRuleContext, message: String): Unit = {
    if (!cond) {
      reporter.error(toPosOpt(node), kind, message)
    }
  }
}
