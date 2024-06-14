// #Sireum

package org.sireum.hamr.sysml

import org.sireum._
import org.sireum.lang.{ast => AST}
object SysmlAst {

  type QualifiedName = ISZ[String]

  @datatype class Root(val packageBodyElements: ISZ[PackageBodyElement])

  @sig trait PackageBodyElement

  @sig trait BodyElement

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
                         val qualifiedName: QualifiedName,
                         val star: B,
                         val starStar: B,
                         val annotations: ISZ[AnnotatingElement])
    extends PackageBodyElement with BodyElement

  @datatype class Alias(val visibility: Visibility.Type,
                        val identification: Identification,
                        val target: QualifiedName)
    extends PackageBodyElement with BodyElement


  @datatype class Identification(val shortName: Option[String],
                                 val name: Option[String])

  @enum object FeatureDirection {
    "In"
    "Out"
    "InOut"
  }

  /****************************************************************
   * C O N N E C T O R S
   *****************************************************************/

  @sig trait ConnectorPart

  @datatype class ConnectorEnd(val reference: ISZ[QualifiedName])

  @datatype class BinaryConnectorPart(val src: ConnectorEnd,
                                      val dst: ConnectorEnd) extends  ConnectorPart

  @datatype class NaryConnectorPart(val connectorEnds: ISZ[ConnectorEnd]) extends ConnectorPart


  /****************************************************************
   * S P E C I A L I Z A T I O N S
   *****************************************************************/

  @sig trait FeatureSpecialization

  @datatype class TypingsSpecialization(val names: ISZ[QualifiedName]) extends FeatureSpecialization

  @datatype class SubsettingsSpecialization(val subsettings: ISZ[QualifiedName]) extends FeatureSpecialization

  @datatype class ReferencesSpecialization(val references: ISZ[QualifiedName]) extends FeatureSpecialization

  @datatype class RedefinitionsSpecialization(val references: ISZ[QualifiedName]) extends FeatureSpecialization

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
                                      val subClassifications: ISZ[QualifiedName],
                                      val definitionBodyItems: ISZ[BodyElement]) extends DefinitionElement

  @datatype class AllocationDefinition(val occurrenceDefPrefix: OccurrenceDefinitionPrefix,
                                       val identifier: Option[Identification],
                                       val subClassifications: ISZ[QualifiedName],
                                       val bodyItems: ISZ[BodyElement]) extends DefinitionElement

  @datatype class ConnectionDefinition(val occurrenceDefPrefix: OccurrenceDefinitionPrefix,
                                       val identifier: Option[Identification],
                                       val subClassifications: ISZ[QualifiedName],
                                       val bodyItems: ISZ[BodyElement]) extends DefinitionElement

  @datatype class EnumerationDefinition(val identification: Identification,
                                        val annotations: ISZ[AnnotatingElement],
                                        val enumValues: ISZ[EnumeratedValue]
                                       ) extends DefinitionElement

  @datatype class Package (val identification: Identification,
                           val packageElements: ISZ[PackageBodyElement]
                          ) extends DefinitionElement

  @datatype class PartDefinition(val occurrenceDefPrefix: OccurrenceDefinitionPrefix,
                                 val identifier: Option[Identification],
                                 val subClassifications: ISZ[QualifiedName],
                                 val bodyItems: ISZ[BodyElement]) extends DefinitionElement

  @datatype class PortDefinition(val defPrefix: DefinitionPrefix,
                                 val identification: Option[Identification],
                                 val subClassifications: ISZ[QualifiedName],
                                 val definitionBodyItems: ISZ[BodyElement]) extends DefinitionElement

  @datatype class MetadataDefinition() extends DefinitionElement

  /****************************************************************
   * U S A G E S
   *****************************************************************/

  @datatype class UsagePrefix(val refPrefix: RefPrefix,

                              // basic usage prefix
                              val isRef: B,
                              val usageExtensions: ISZ[QualifiedName])

  @datatype class OccurrenceUsagePrefix(val refPrefix: RefPrefix,
                                        val isRef: B,
                                        val isIndividual: B,
                                        val isSnapshot: B,
                                        val isTimeslice: B,
                                        val usageExtensions: ISZ[QualifiedName])

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
                                 val definitionBodyItems: ISZ[BodyElement]) extends NonOccurrenceUsageElement

  @datatype class ReferenceUsage(val visibility: Visibility.Type,
                                 val prefix: RefPrefix,
                                 val identification: Option[Identification],
                                 val specializations: ISZ[FeatureSpecialization],
                                 val definitionBodyItems: ISZ[BodyElement]) extends NonOccurrenceUsageElement

  @datatype class DefaultReferenceUsage(val visibility: Visibility.Type,
                                        val prefix: RefPrefix,
                                        val identification: Option[Identification],
                                        val specializations: ISZ[FeatureSpecialization],
                                        val featureValue: Option[FeatureValue],
                                        val definitionBodyItems: ISZ[BodyElement]) extends NonOccurrenceUsageElement

  // Occurrence Usages

  @sig trait OccurrenceUsageElement extends UsageElement

  @sig trait StructureUsageElement extends OccurrenceUsageElement

  @datatype class ConnectionUsage(val visibility: Visibility.Type,
                                  val occurrenceUsagePrefix: OccurrenceUsagePrefix,
                                  val identification: Option[Identification],
                                  val specializations: ISZ[FeatureSpecialization],
                                  val featureValue: Option[FeatureValue],
                                  val connectorPart: Option[ConnectorPart],
                                  val bodyItems: ISZ[BodyElement]) extends StructureUsageElement

  @datatype class ItemUsage(val visibility: Visibility.Type,
                            val occurrenceUsagePrefix: OccurrenceUsagePrefix,
                            val identification: Option[Identification],
                            val specializations: ISZ[FeatureSpecialization],
                            val definitionBodyItems: ISZ[BodyElement]) extends StructureUsageElement

  @datatype class PartUsage(val visibility: Visibility.Type,
                            val occurrenceUsagePrefix: OccurrenceUsagePrefix,
                            val identification: Option[Identification],
                            val specializations: ISZ[FeatureSpecialization],
                            val definitionBodyItems: ISZ[BodyElement]) extends StructureUsageElement

  @datatype class PortUsage(val visibility: Visibility.Type,
                            val occurrenceUsagePrefix: OccurrenceUsagePrefix,
                            val identification: Option[Identification],
                            val specializations: ISZ[FeatureSpecialization],
                            val definitionBodyItems: ISZ[BodyElement]) extends StructureUsageElement

  /****************************************************************
   * A N N O T A T I O N S
   *****************************************************************/

  @sig trait AnnotatingElement extends DefinitionElement {
    def id: Option[Identification]

    def comment: String
  }

  @datatype class Comment(val id: Option[Identification],
                          val abouts: ISZ[QualifiedName],
                          val locale: Option[String],
                          val comment: String) extends AnnotatingElement

  @datatype class Documentation(val id: Option[Identification],
                                val locale: Option[String],
                                val comment: String) extends AnnotatingElement

  @datatype class TextualRepresentation(val id: Option[Identification],
                                        val language: String,
                                        val comment: String) extends AnnotatingElement

}
