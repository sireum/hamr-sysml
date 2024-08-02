// #Sireum

package org.sireum.hamr.sysml.ast

import org.sireum._
import org.sireum.hamr.sysml.ast.SysmlAst.Name
import org.sireum.lang.{ast => AST}
import org.sireum.message.Position

object SysmlAst {

  @datatype class Id(val value: String, @hidden val attr: Attr) {
    @strictpure def prettyST: ST = st"$value"

    def posOpt: Option[Position] = {
      return attr.posOpt
    }
  }

  @datatype class Name(val ids: ISZ[Id], @hidden val attr: Attr) {
    @strictpure def prettyST: ST = st"${(for (id <- ids) yield id.prettyST, ".")}"

    @strictpure def posOpt: Option[Position] = attr.posOpt
  }

  object TopUnit {
    def empty: TopUnit = {
      return TopUnit(None(), ISZ())
    }
  }
  @datatype class TopUnit(val fileUri: Option[String],
                          val packageBodyElements: ISZ[PackageBodyElement])

  @sig trait AttrNode {
    def posOpt: Option[Position]
  }

  /*
  rulePackageBodyElement:
    rulePackageMember #rulePackageBodyElement1
    | ruleElementFilterMember #rulePackageBodyElement2
    | ruleAliasMember #rulePackageBodyElement3
    | ruleImport #rulePackageBodyElement4;
  */

  @sig trait PackageBodyElement extends AttrNode

  @sig trait DefinitionBodyItem extends AttrNode

  @enum object Visibility {
    "Public"
    "Private"
    "Protected"
  }

  @datatype class FeatureValue (val isBound: B,
                                val isInitial: B,
                                val isDefault: B,
                                val exp: AST.Exp)

  @datatype class EnumeratedValue(val visibility: Visibility.Type,
                                  val identification: Option[Identification],
                                  val specializations: ISZ[FeatureSpecialization],
                                  val definitionBodyItems: ISZ[DefinitionBodyItem])

  @datatype class Import(val visibility: Visibility.Type,
                         val all: B,
                         val name: Name,
                         val star: B,
                         val starStar: B,
                         val annotations: ISZ[AnnotatingElement],
                         @hidden val attr: Attr) extends PackageBodyElement with DefinitionBodyItem {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }


  @datatype class AliasMember(val visibility: Visibility.Type,
                              val identification: Option[Identification],
                              val target: Name,
                              val annotations: ISZ[AnnotatingElement],
                              @hidden val attr: Attr) extends PackageBodyElement with DefinitionBodyItem {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }


  @datatype class Identification(val shortName: Option[Id],
                                 val name: Option[Id],
                                 @hidden val attr: Attr) extends AttrNode {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }

  @enum object FeatureDirection {
    "In"
    "Out"
    "InOut"
  }

  @sig trait PackageMember extends PackageBodyElement

  /****************************************************************
   * C O N N E C T O R S
   *****************************************************************/

  @sig trait ConnectorPart

  @datatype class ConnectorEnd(val reference: ISZ[Name])

  @datatype class BinaryConnectorPart(val src: ConnectorEnd,
                                      val dst: ConnectorEnd) extends  ConnectorPart

  @datatype class NaryConnectorPart(val connectorEnds: ISZ[ConnectorEnd]) extends ConnectorPart


  /****************************************************************
   * S P E C I A L I Z A T I O N S
   *****************************************************************/

  @sig trait FeatureSpecialization

  @datatype class TypingsSpecialization(val names: ISZ[Name]) extends FeatureSpecialization

  @datatype class SubsettingsSpecialization(val subsettings: ISZ[Name]) extends FeatureSpecialization

  @datatype class ReferencesSpecialization(val references: ISZ[Name]) extends FeatureSpecialization

  @datatype class RedefinitionsSpecialization(val references: ISZ[Name]) extends FeatureSpecialization

  /****************************************************************
   * D E F I N I T I O N S
   *****************************************************************/

  @sig trait DefinitionMember extends DefinitionBodyItem

  @sig trait DefinitionElement extends DefinitionMember with PackageMember {
    def visibility: Visibility.Type
    def identification: Option[Identification]
  }

  @datatype class DefinitionPrefix(val isAbstract: B,
                                   val isVariation: B)

  @datatype class Package (val visibility: Visibility.Type,
                           val identification: Option[Identification],
                           val packageElements: ISZ[PackageBodyElement],
                           @hidden val attr: Attr) extends DefinitionElement {

    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }

  @datatype class AttributeDefinition(val visibility: Visibility.Type,
                                      val defPrefix: DefinitionPrefix,
                                      val identification: Option[Identification],
                                      val subClassifications: ISZ[Name],
                                      val parents: ISZ[Type.Named],
                                      val bodyItems: ISZ[DefinitionBodyItem],
                                      @hidden val attr: Attr) extends DefinitionElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt

    /*
    @pure def parents: ISZ[Type.Named] = {
      for (parentName <- subClassifications) yield
        Type.Named(name = parentName, attr = TypedAttr(posOpt = parentName.posOpt, typedOpt = None()))
    }
     */
  }

  @datatype class OccurrenceDefinitionPrefix(val isAbstract: B,
                                             val isVariation: B)

  @datatype class AllocationDefinition(val visibility: Visibility.Type,
                                       val occurrenceDefPrefix: OccurrenceDefinitionPrefix,
                                       val identification: Option[Identification],
                                       val subClassifications: ISZ[Name],
                                       val parents: ISZ[Type.Named],
                                       val bodyItems: ISZ[DefinitionBodyItem],
                                       @hidden val attr: Attr) extends DefinitionElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt

    /*
    @pure def parents: ISZ[Type.Named] = {
      for (parentName <- subClassifications) yield
        Type.Named(name = parentName, attr = TypedAttr(posOpt = parentName.posOpt, typedOpt = None()))
    }
     */
  }

  @datatype class ConnectionDefinition(val visibility: Visibility.Type,
                                       val occurrenceDefPrefix: OccurrenceDefinitionPrefix,
                                       val identification: Option[Identification],
                                       val subClassifications: ISZ[Name],
                                       val parents: ISZ[Type.Named],
                                       val bodyItems: ISZ[DefinitionBodyItem],
                                       @hidden val attr: Attr) extends DefinitionElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt

    /*
    @pure def parents: ISZ[Type.Named] = {
      for (parentName <- subClassifications) yield
        Type.Named(name = parentName, attr = TypedAttr(posOpt = parentName.posOpt, typedOpt = None()))
    }
     */
  }

  @datatype class EnumerationDefinition(val visibility: Visibility.Type,
                                        val identification: Option[Identification],
                                        val subClassifications: ISZ[Name],
                                        val annotations: ISZ[AnnotatingElement],
                                        val enumValues: ISZ[EnumeratedValue],
                                        @hidden val attr: Attr) extends DefinitionElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }

  @datatype class PartDefinition(val visibility: Visibility.Type,
                                 val occurrenceDefPrefix: OccurrenceDefinitionPrefix,
                                 val identification: Option[Identification],
                                 val subClassifications: ISZ[Name],
                                 val parents: ISZ[Type.Named],
                                 val bodyItems: ISZ[DefinitionBodyItem],
                                 @hidden val attr: Attr) extends DefinitionElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt

    /*
    @pure def parents: ISZ[Type.Named] = {
      for (parentName <- subClassifications) yield
        Type.Named(name = parentName, attr = TypedAttr(posOpt = parentName.posOpt, typedOpt = None()))
    }
    */
  }

  @datatype class PortDefinition(val visibility: Visibility.Type,
                                 val defPrefix: DefinitionPrefix,
                                 val identification: Option[Identification],
                                 val subClassifications: ISZ[Name],
                                 val parents: ISZ[Type.Named],
                                 val bodyItems: ISZ[DefinitionBodyItem],
                                 @hidden val attr: Attr) extends DefinitionElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt

    /*
    @pure def parents: ISZ[Type.Named] = {
      for (parentName <- subClassifications) yield
        Type.Named(name = parentName, attr = TypedAttr(posOpt = parentName.posOpt, typedOpt = None()))
    }
     */
  }

  @datatype class MetadataDefinition(val isAbstract: B,
                                     val visibility: Visibility.Type,
                                     val identification: Option[Identification],
                                     val subClassifications: ISZ[Name],
                                     val bodyItems: ISZ[DefinitionBodyItem],
                                     @hidden val attr: Attr) extends DefinitionElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }

  /****************************************************************
   * U S A G E S
   *****************************************************************/

  /*
  ruleUsageElement:
    ruleNonOccurrenceUsageElement #ruleUsageElement1
    | ruleOccurrenceUsageElement #ruleUsageElement2;
  */

  @sig trait UsageElement extends PackageMember {
    def visibility: Visibility.Type
    def identification: Option[Identification]
    def specializations: ISZ[FeatureSpecialization]
    def featureValue: Option[FeatureValue]
    def definitionBodyItems: ISZ[DefinitionBodyItem]
  }

  // Non-Occurrence Usages

  /* ruleNonOccurrenceUsageElement:
    ruleDefaultReferenceUsage #ruleNonOccurrenceUsageElement1
    | ruleReferenceUsage #ruleNonOccurrenceUsageElement2
    | ruleAttributeUsage #ruleNonOccurrenceUsageElement3
    | ruleEnumerationUsage #ruleNonOccurrenceUsageElement4
    | ruleBindingConnectorAsUsage #ruleNonOccurrenceUsageElement5
    | ruleSuccessionAsUsage #ruleNonOccurrenceUsageElement6
    | ruleExtendedUsage #ruleNonOccurrenceUsageElement7;
  */

  @sig trait NonOccurrenceUsageMember extends DefinitionBodyItem

  @sig trait NonOccurrenceUsageElement extends NonOccurrenceUsageMember with UsageElement

  @datatype class RefPrefix(val direction: Option[FeatureDirection.Type],
                            val isAbstract: B,
                            val isVariation: B,
                            val isReadOnly: B,
                            val isDerived: B,
                            val isEnd: B)

  @datatype class UsagePrefix(val refPrefix: RefPrefix,

                              // basic usage prefix
                              val isRef: B,
                              val usageExtensions: ISZ[Name])

  @datatype class AttributeUsage(val visibility: Visibility.Type,
                                 val prefix: UsagePrefix,
                                 val identification: Option[Identification],
                                 val specializations: ISZ[FeatureSpecialization],
                                 val featureValue: Option[FeatureValue],
                                 val definitionBodyItems: ISZ[DefinitionBodyItem],
                                 val tipeOpt: Option[Type],
                                 @hidden val attr: ResolvedAttr) extends NonOccurrenceUsageElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }

  @datatype class ReferenceUsage(val visibility: Visibility.Type,
                                 val prefix: RefPrefix,
                                 val identification: Option[Identification],
                                 val specializations: ISZ[FeatureSpecialization],
                                 val featureValue: Option[FeatureValue],
                                 val definitionBodyItems: ISZ[DefinitionBodyItem],
                                 val tipeOpt: Option[Type],
                                 @hidden val attr: ResolvedAttr) extends NonOccurrenceUsageElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }

  // Occurrence Usages
  /*
  ruleOccurrenceUsageElement:
    ruleStructureUsageElement #ruleOccurrenceUsageElement1
    | ruleBehaviorUsageElement #ruleOccurrenceUsageElement2;
  */

  @sig trait OccurrenceUsageMember extends DefinitionBodyItem

  @sig trait OccurrenceUsageElement extends OccurrenceUsageMember with UsageElement

  @sig trait StructureUsageElement extends OccurrenceUsageElement

  @datatype class OccurrenceUsagePrefix(val refPrefix: RefPrefix,
                                        val isRef: B,
                                        val isIndividual: B,
                                        val isSnapshot: B,
                                        val isTimeslice: B,
                                        val usageExtensions: ISZ[Name])

  @datatype class ConnectionUsage(val visibility: Visibility.Type,
                                  val occurrenceUsagePrefix: OccurrenceUsagePrefix,
                                  val identification: Option[Identification],
                                  val specializations: ISZ[FeatureSpecialization],
                                  val featureValue: Option[FeatureValue],
                                  val definitionBodyItems: ISZ[DefinitionBodyItem],
                                  val connectorPart: Option[ConnectorPart],
                                  val tipeOpt: Option[Type],
                                  @hidden val attr: ResolvedAttr) extends StructureUsageElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }

  @datatype class ItemUsage(val visibility: Visibility.Type,
                            val occurrenceUsagePrefix: OccurrenceUsagePrefix,
                            val identification: Option[Identification],
                            val specializations: ISZ[FeatureSpecialization],
                            val featureValue: Option[FeatureValue],
                            val definitionBodyItems: ISZ[DefinitionBodyItem],
                            val tipeOpt: Option[Type],
                            @hidden val attr: ResolvedAttr) extends StructureUsageElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }

  @datatype class PartUsage(val visibility: Visibility.Type,
                            val occurrenceUsagePrefix: OccurrenceUsagePrefix,
                            val identification: Option[Identification],
                            val specializations: ISZ[FeatureSpecialization],
                            val featureValue: Option[FeatureValue],
                            val definitionBodyItems: ISZ[DefinitionBodyItem],
                            val tipeOpt: Option[Type],
                            @hidden val attr: ResolvedAttr) extends StructureUsageElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }

  @datatype class PortUsage(val visibility: Visibility.Type,
                            val occurrenceUsagePrefix: OccurrenceUsagePrefix,
                            val identification: Option[Identification],
                            val specializations: ISZ[FeatureSpecialization],
                            val featureValue: Option[FeatureValue],
                            val definitionBodyItems: ISZ[DefinitionBodyItem],
                            val tipeOpt: Option[Type],
                            @hidden val attr: ResolvedAttr) extends StructureUsageElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }

  //@sig trait BehaviorUsageElement extends OccurrenceUsageElement


  /****************************************************************
   * A N N O T A T I O N S
   *****************************************************************/

  /*
  ruleAnnotatingElement:
    ruleComment #ruleAnnotatingElement1
    | ruleDocumentation #ruleAnnotatingElement2
    | ruleTextualRepresentation #ruleAnnotatingElement3
    | ruleMetadataUsage #ruleAnnotatingElement4;
  */

  @sig trait AnnotatingElement extends DefinitionElement {
    def comment: String
  }

  @datatype class Comment(val visibility: Visibility.Type,
                          val identification: Option[Identification],
                          val abouts: ISZ[Name],
                          val locale: Option[String],
                          val comment: String,
                          @hidden val attr: Attr) extends AnnotatingElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }

  @datatype class Documentation(val visibility: Visibility.Type,
                                val identification: Option[Identification],
                                val locale: Option[String],
                                val comment: String,
                                @hidden val attr: Attr) extends AnnotatingElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }

  @datatype class TextualRepresentation(val visibility: Visibility.Type,
                                        val identification: Option[Identification],
                                        val language: String,
                                        val comment: String,
                                        @hidden val attr: Attr) extends AnnotatingElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }


  @datatype class GumboAnnotation(val gumboNode: GumboAST.GclSymbol) extends AnnotatingElement {
    @strictpure override def visibility: Visibility.Type = Visibility.Public
    @strictpure override def identification: Option[Identification] = None()
    @strictpure override def comment: String = ""
    @pure override def posOpt: Option[Position] = {
      return gumboNode.posOpt
    }
  }
}

@datatype class Attr(val posOpt: Option[Position])

@datatype class ResolvedAttr(@hidden val posOpt: Option[Position],
                             val resOpt: Option[ResolvedInfo],
                             val typedOpt: Option[Typed])

@datatype trait ResolvedInfo {

}


object ResolvedInfo {
  @datatype class Package(val name: ISZ[String]) extends ResolvedInfo

  @datatype class Enum(val name: ISZ[String]) extends ResolvedInfo

  @datatype class EnumElement(val owner: ISZ[String], val name: String, val ordinal: Z) extends ResolvedInfo


  @datatype class AttributeUsage(val owner: ISZ[String], val name: String) extends ResolvedInfo

  @datatype class ConnectionUsage(val owner: ISZ[String], val name: String) extends ResolvedInfo

  @datatype class ItemUsage(val owner: ISZ[String], val name: String) extends ResolvedInfo

  @datatype class PartUsage(val owner: ISZ[String], val name: String) extends ResolvedInfo

  @datatype class PortUsage(val owner: ISZ[String], val name: String) extends ResolvedInfo

  @datatype class ReferenceUsage(val owner: ISZ[String], val name: String) extends ResolvedInfo
}

@datatype trait Type {

  @strictpure def posOpt: Option[Position]

  @strictpure def typedOpt: Option[Typed]

  @strictpure def typed(t: Typed): Type

  @strictpure def prettyST: ST

  override def string: String = {
    typedOpt match {
      case Some(t) => return t.string
      case _ => return prettyST.render
    }
  }
}

object Type {
  @datatype class Named(val name: Name, @hidden attr: TypedAttr) extends Type {

    @strictpure override def posOpt: Option[Position] = attr.posOpt
    @strictpure override def typedOpt: Option[Typed] = attr.typedOpt
    @strictpure override def typed(t: Typed): Type.Named = this (name, attr(typedOpt = Some(t)))

    @pure def isEqual(other: Named): B = {
      (typedOpt, other.typedOpt) match {
        case (Some(t1), Some(t2)) => return t1 == t2
        case _ => return name == other.name
      }
    }

    @pure override def hash: Z = {
      typedOpt match {
        case Some(t) => return t.hash
        case _ => return (name).hash
      }
    }

    @strictpure override def prettyST: ST = {
      st"${(for (id <- name.ids) yield id.value, ".")}"
    }
  }
}

@datatype class TypedAttr(val posOpt: Option[Position], val typedOpt: Option[Typed])


object GumboAST {

  @sig trait GclSymbol extends SysmlAst.AttrNode

  @datatype class GclSubclause(val state: ISZ[GclStateVar],
                               val methods: ISZ[GclMethod],
                               val invariants: ISZ[GclInvariant],
                               val initializes: Option[GclInitialize],
                               val integration: Option[GclIntegration],
                               val compute: Option[GclCompute],
                               @hidden val attr: Attr) extends GclSymbol {
    @strictpure override def posOpt: Option[Position] = attr.posOpt

    override def string: String = {
      return prettyST.render
    }

    @pure def prettyST: ST = {
      val sstate: Option[ST] =
        if (state.nonEmpty) Some(
          st"""state
              |  ${(state, "\n")}""")
        else None()
      val smethods: Option[ST] =
        if (methods.nonEmpty) Some(
          st"""functions
              |  ${(methods, "\n")}""")
        else None()
      val sinvariants: Option[ST] =
        if (invariants.nonEmpty) Some(
          st"""invariants
              |  ${(invariants, "\n")}""")
        else None()
      val sintegration: Option[ST] =
        if (integration.nonEmpty) Some(
          st"""integration
              |  ${integration.get.string}""")
        else None()
      val scompute: Option[ST] =
        if (compute.nonEmpty) Some(
          st"""compute
              |  ${compute.get.string}""")
        else None()

      return (
      st"""$sstate
          |$smethods
          |$sinvariants
          |$sintegration
          |$scompute""")
    }
  }

  @datatype class GclMethod(val method: org.sireum.lang.ast.Stmt.Method) extends GclSymbol {
    @strictpure override def posOpt: Option[Position] = method.posOpt

    override def string: String = {
      return method.string
    }
  }

  @datatype class GclStateVar(val name: String,
                              val classifier: String,
                              val posOpt: Option[Position]) extends GclSymbol {
    override def string: String = {
      return prettyST.render
    }

    @pure def prettyST: ST = {
      return st"$name: $classifier"
    }
  }

  @sig trait GclClause extends GclSymbol {
    def id: String

    def descriptor: Option[String]

    def posOpt: Option[Position]
  }

  @sig trait GclSpec extends GclClause {

    def exp: org.sireum.lang.ast.Exp

  }

  @datatype class GclInvariant(val id: String,
                               val descriptor: Option[String],
                               val exp: org.sireum.lang.ast.Exp,
                               @hidden val attr: Attr) extends GclSpec {
    @strictpure override def posOpt: Option[Position] = attr.posOpt

    override def string: String = {
      return prettyST.render
    }

    @pure def prettyST: ST = {
      return st"""$id $descriptor :
                 |  ${exp.string}"""
    }
  }

  @sig trait GclComputeSpec extends GclSpec

  @datatype class GclAssume(val id: String,
                            val descriptor: Option[String],
                            val exp: org.sireum.lang.ast.Exp,
                            @hidden val attr: Attr) extends GclComputeSpec {
    @strictpure override def posOpt: Option[Position] = attr.posOpt

    override def string: String = {
      return prettyST.render
    }

    @pure def prettyST: ST = {
      return(
        st"""assume ${id} $descriptor:
            |  $exp""")
    }
  }

  @datatype class GclGuarantee(val id: String,
                               val descriptor: Option[String],
                               val exp: org.sireum.lang.ast.Exp,
                               @hidden val attr: Attr) extends GclComputeSpec {
    @strictpure override def posOpt: Option[Position] = attr.posOpt

    override def string: String = {
      return prettyST.render
    }

    @pure def prettyST: ST = {
      return(
        st"""guarantee ${id} $descriptor:
            |  $exp""")
    }

  }

  @datatype class GclIntegration(val specs: ISZ[GclSpec],
                                 @hidden val attr: Attr) extends GclSymbol {
    @strictpure override def posOpt: Option[Position] = attr.posOpt

    override def string: String = {
      return prettyST.render
    }

    @pure def prettyST: ST = {
      return st"${(specs, "\n")}"
    }
  }

  @datatype class GclCaseStatement(val id: String,
                                   val descriptor: Option[String],
                                   val assumes: org.sireum.lang.ast.Exp,
                                   val guarantees: org.sireum.lang.ast.Exp,
                                   @hidden val attr: Attr) extends GclSymbol {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }

  @datatype class GclInitialize(val modifies: ISZ[org.sireum.lang.ast.Exp],
                                val guarantees: ISZ[GclGuarantee],
                                val flows: ISZ[InfoFlowClause],
                                @hidden val attr: Attr) extends GclSymbol {
    @strictpure override def posOpt: Option[Position] = attr.posOpt

    override def string: String = {
      return prettyST.render
    }

    @pure def prettyST: ST = {
      val smodifies: Option[ST] =
        if (modifies.nonEmpty) Some(
          st"""modifies (${(modifies, ",\n")})""")
        else None()
      val sguarantees: Option[ST] =
        if (guarantees.nonEmpty) Some(
          st"""guarantees
              |  ${(guarantees, "\n")}""")
        else None()
      val sflows: Option[ST] =
        if (flows.nonEmpty) Some(
          st"""flows
              |  ${(flows, "\n")}""")
        else None()
      return (
      st"""$smodifies
          |$sguarantees
          |$sflows""")
    }
  }

  @datatype class GclCompute(val modifies: ISZ[org.sireum.lang.ast.Exp],
                             val specs: ISZ[GclComputeSpec],
                             val cases: ISZ[GclCaseStatement],
                             val handlers: ISZ[GclHandle],
                             val flows: ISZ[InfoFlowClause],
                             @hidden val attr: Attr) extends GclSymbol {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }

  @datatype class GclHandle(val port: org.sireum.lang.ast.Exp,
                            val modifies: ISZ[org.sireum.lang.ast.Exp],
                            val guarantees: ISZ[GclGuarantee],
                            @hidden val attr: Attr) extends GclSymbol {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }

  @datatype class GclTODO extends GclSymbol {
    @strictpure override def posOpt: Option[Position] = None()
  }

  @datatype class GclLib(val containingPackage: Name,
                         val methods: ISZ[GclMethod],
                         @hidden val attr: Attr) extends GclSymbol {
    @strictpure override def posOpt: Option[Position] = attr.posOpt

    override def string: String = {
      return prettyST.render
    }

    @pure def prettyST: ST = {
      return(
      st"""functions
          |  ${(methods, "\n")}""")
    }
  }

  @datatype class InfoFlowClause(val id: String,
                                 val descriptor: Option[String],
                                 val from: ISZ[org.sireum.lang.ast.Exp],
                                 val to: ISZ[org.sireum.lang.ast.Exp],
                                 @hidden val attr: Attr
                                ) extends GclClause {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }
}