// #Sireum

package org.sireum.hamr.sysml.ast

import org.sireum._
import org.sireum.lang.{ast => AST}
import org.sireum.message.Position

object SysmlAst {

  @datatype class Id(val value: String, @hidden val attr: Attr) {
    @strictpure def prettyST: ST = st"$value"
  }

  @datatype class Name(val ids: ISZ[Id], @hidden val attr: Attr) {
    @strictpure def prettyST: ST = st"${(for (id <- ids) yield id.prettyST, ".")}"
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

  @sig trait PackageBodyElement extends AttrNode

  @sig trait BodyElement extends AttrNode

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
                                  val definitionBodyItems: ISZ[BodyElement])

  @datatype class Import(val visibility: Visibility.Type,
                         val all: B,
                         val name: Name,
                         val star: B,
                         val starStar: B,
                         val annotations: ISZ[AnnotatingElement],
                         @hidden val attr: Attr) extends PackageBodyElement with BodyElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }


  @datatype class Alias(val visibility: Visibility.Type,
                        val identification: Option[Identification],
                        val target: Name,
                        val annotations: ISZ[AnnotatingElement],
                        @hidden val attr: Attr) extends PackageBodyElement with BodyElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }


  @datatype class Identification(val shortName: Option[Id],
                                 val name: Option[Id])

  @enum object FeatureDirection {
    "In"
    "Out"
    "InOut"
  }

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

  @sig trait DefinitionElement extends PackageBodyElement

  @datatype class DefinitionPrefix(val isAbstract: B,
                                   val isVariation: B)

  @datatype class OccurrenceDefinitionPrefix(val isAbstract: B,
                                             val isVariation: B)

  @datatype class AttributeDefinition(val defPrefix: DefinitionPrefix,
                                      val identification: Option[Identification],
                                      val subClassifications: ISZ[Name],
                                      val definitionBodyItems: ISZ[BodyElement],
                                      @hidden val attr: Attr) extends DefinitionElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }

  @datatype class AllocationDefinition(val occurrenceDefPrefix: OccurrenceDefinitionPrefix,
                                       val identifier: Option[Identification],
                                       val subClassifications: ISZ[Name],
                                       val bodyItems: ISZ[BodyElement],
                                       @hidden val attr: Attr) extends DefinitionElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }

  @datatype class ConnectionDefinition(val occurrenceDefPrefix: OccurrenceDefinitionPrefix,
                                       val identifier: Option[Identification],
                                       val subClassifications: ISZ[Name],
                                       val bodyItems: ISZ[BodyElement],
                                       @hidden val attr: Attr) extends DefinitionElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }

  @datatype class EnumerationDefinition(val identification: Option[Identification],
                                        val subClassifications: ISZ[Name],
                                        val annotations: ISZ[AnnotatingElement],
                                        val enumValues: ISZ[EnumeratedValue],
                                        @hidden val attr: Attr) extends DefinitionElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }

  @datatype class Package (val identification: Option[Identification],
                           val packageElements: ISZ[PackageBodyElement],
                           @hidden val attr: Attr) extends DefinitionElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }

  @datatype class PartDefinition(val occurrenceDefPrefix: OccurrenceDefinitionPrefix,
                                 val identifier: Option[Identification],
                                 val subClassifications: ISZ[Name],
                                 val bodyItems: ISZ[BodyElement],
                                 @hidden val attr: Attr) extends DefinitionElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }

  @datatype class PortDefinition(val defPrefix: DefinitionPrefix,
                                 val identification: Option[Identification],
                                 val subClassifications: ISZ[Name],
                                 val definitionBodyItems: ISZ[BodyElement],
                                 @hidden val attr: Attr) extends DefinitionElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }

  @datatype class MetadataDefinition(@hidden val attr: Attr) extends DefinitionElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }

  /****************************************************************
   * U S A G E S
   *****************************************************************/

  @datatype class UsagePrefix(val refPrefix: RefPrefix,

                              // basic usage prefix
                              val isRef: B,
                              val usageExtensions: ISZ[Name])

  @datatype class OccurrenceUsagePrefix(val refPrefix: RefPrefix,
                                        val isRef: B,
                                        val isIndividual: B,
                                        val isSnapshot: B,
                                        val isTimeslice: B,
                                        val usageExtensions: ISZ[Name])

  @datatype class RefPrefix(val direction: Option[FeatureDirection.Type],
                            val isAbstract: B,
                            val isVariation: B,
                            val isReadOnly: B,
                            val isDerived: B,
                            val isEnd: B)

  @datatype class UsageHolder(val identification: Option[Identification],
                              val specializations: ISZ[FeatureSpecialization],
                              val featureValue: Option[FeatureValue],
                              val definitionBodyItems: ISZ[BodyElement])

  @sig trait UsageElement extends PackageBodyElement with BodyElement

  // Non-Occurrence Usages

  @sig trait NonOccurrenceUsageElement extends UsageElement

  @datatype class AttributeUsage(val visibility: Visibility.Type,
                                 val prefix: UsagePrefix,
                                 val identification: Option[Identification],
                                 val specializations: ISZ[FeatureSpecialization],
                                 val definitionBodyItems: ISZ[BodyElement],
                                 @hidden val attr: Attr) extends NonOccurrenceUsageElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }

  @datatype class ReferenceUsage(val visibility: Visibility.Type,
                                 val prefix: RefPrefix,
                                 val identification: Option[Identification],
                                 val specializations: ISZ[FeatureSpecialization],
                                 val definitionBodyItems: ISZ[BodyElement],
                                 @hidden val attr: Attr) extends NonOccurrenceUsageElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }

  @datatype class DefaultReferenceUsage(val visibility: Visibility.Type,
                                        val prefix: RefPrefix,
                                        val identification: Option[Identification],
                                        val specializations: ISZ[FeatureSpecialization],
                                        val featureValue: Option[FeatureValue],
                                        val definitionBodyItems: ISZ[BodyElement],
                                        @hidden val attr: Attr) extends NonOccurrenceUsageElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }

  // Occurrence Usages

  @sig trait OccurrenceUsageElement extends UsageElement

  @sig trait StructureUsageElement extends OccurrenceUsageElement

  @datatype class ConnectionUsage(val visibility: Visibility.Type,
                                  val occurrenceUsagePrefix: OccurrenceUsagePrefix,
                                  val identification: Option[Identification],
                                  val specializations: ISZ[FeatureSpecialization],
                                  val featureValue: Option[FeatureValue],
                                  val connectorPart: Option[ConnectorPart],
                                  val bodyItems: ISZ[BodyElement],
                                  @hidden val attr: Attr) extends StructureUsageElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }

  @datatype class ItemUsage(val visibility: Visibility.Type,
                            val occurrenceUsagePrefix: OccurrenceUsagePrefix,
                            val identification: Option[Identification],
                            val specializations: ISZ[FeatureSpecialization],
                            val definitionBodyItems: ISZ[BodyElement],
                            @hidden val attr: Attr) extends StructureUsageElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }

  @datatype class PartUsage(val visibility: Visibility.Type,
                            val occurrenceUsagePrefix: OccurrenceUsagePrefix,
                            val identification: Option[Identification],
                            val specializations: ISZ[FeatureSpecialization],
                            val definitionBodyItems: ISZ[BodyElement],
                            @hidden val attr: Attr) extends StructureUsageElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }

  @datatype class PortUsage(val visibility: Visibility.Type,
                            val occurrenceUsagePrefix: OccurrenceUsagePrefix,
                            val identification: Option[Identification],
                            val specializations: ISZ[FeatureSpecialization],
                            val definitionBodyItems: ISZ[BodyElement],
                            @hidden val attr: Attr) extends StructureUsageElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }

  /****************************************************************
   * A N N O T A T I O N S
   *****************************************************************/

  @sig trait AnnotatingElement extends DefinitionElement {
    def id: Option[Identification]

    def comment: String
  }

  @datatype class Comment(val id: Option[Identification],
                          val abouts: ISZ[Name],
                          val locale: Option[String],
                          val comment: String,
                          @hidden val attr: Attr) extends AnnotatingElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }

  @datatype class Documentation(val id: Option[Identification],
                                val locale: Option[String],
                                val comment: String,
                                @hidden val attr: Attr) extends AnnotatingElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }

  @datatype class TextualRepresentation(val id: Option[Identification],
                                        val language: String,
                                        val comment: String,
                                        @hidden val attr: Attr) extends AnnotatingElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }

}

@datatype class Attr(val posOpt: Option[Position])