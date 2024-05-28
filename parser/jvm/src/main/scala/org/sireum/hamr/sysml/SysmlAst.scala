// #Sireum

package org.sireum.hamr.sysml

import org.sireum._

object SysmlAst {
  type QualifiedName = ISZ[String]

  @sig trait DefinitionElement

  @sig trait UsageElement

  @datatype class UsageHolder(val identification: Option[Identification],
                              val specializations: ISZ[FeatureSpecialization],
                              val definitionBodyItems: ISZ[BodyElement])

  @datatype class EnumerationDefinition(val identification: Identification,
                                        val annotations: ISZ[AnnotatingElement],
                                        val enumValues: ISZ[EnumeratedValue]
                                       ) extends DefinitionElement with PackageElement

  @datatype class EnumeratedValue(val visibility: Visibility.Type,
                                  val identification: Option[Identification],
                                  val specializations: ISZ[FeatureSpecialization],
                                  val definitionBodyItems: ISZ[BodyElement])

  @sig trait PackageBodyElement extends DefinitionElement with UsageElement


  @datatype class OccurrenceDefinition(val isAbstract: B)

  @enum object FeatureDirection {
    "In"
    "Out"
    "InOut"
  }

  @datatype class Root(val packageBodyElements: ISZ[PackageBodyElement])


  @datatype class PackageMember(val isLibrary: B,
                                val isStandard: B,
                               ) extends PackageElement

  @sig trait PackageElement

  @datatype class PlaceholderPackageElement extends PackageElement

  @datatype class PartDefinition(val occurrenceDef: OccurrenceDefinition,
                                 val identifier: Option[Identification],
                                 val specializations: ISZ[FeatureSpecialization],
                                 val bodyItems: ISZ[BodyElement]) extends PackageElement

  @datatype class PortDefinition(val isAbstract: B,
                                 val isVariation: B,
                                 val identification: Option[Identification],
                                 val specializations: ISZ[FeatureSpecialization],
                                 val definitionBodyItems: ISZ[BodyElement]) extends PackageElement


  @sig trait BodyElement

  @datatype class PartUsage(val occurrenceUsage: OccurrenceUsage,
                            val identification: Option[Identification],
                            val specializations: ISZ[FeatureSpecialization],
                            val definitionBodyItems: ISZ[BodyElement]) extends BodyElement

  @datatype class PortUsage(val occurrenceUsage: OccurrenceUsage,
                            val identification: Option[Identification],
                            val specializations: ISZ[FeatureSpecialization],
                            val definitionBodyItems: ISZ[BodyElement]) extends BodyElement

  @datatype class AttributeUsage(val prefix: UsagePrefix,
                                 val identification: Option[Identification],
                                 val specializations: ISZ[FeatureSpecialization],
                                 val definitionBodyItems: ISZ[BodyElement]) extends BodyElement

  @datatype class OccurrenceUsage( // ref prefix
                                   val direction: Option[FeatureDirection.Type],
                                   val isAbstract: B,
                                   val isVariation: B,
                                   val isReadOnly: B,
                                   val isDerived: B,
                                   val isEnd: B,

                                   // basic usage prefix
                                   val isRef: B,

                                   // occurrence usage prefix
                                   val isIndividual: B,

                                   // portion kind
                                   val isSnapshot: B,
                                   val isTimeslice: B,

                                   val usageExtensions: ISZ[QualifiedName]
                                 )

  @datatype class UsagePrefix(val direction: Option[FeatureDirection.Type],
                              val isAbstract: B,
                              val isVariation: B,
                              val isReadOnly: B,
                              val isDerived: B,
                              val isEnd: B,

                              // basic usage prefix
                              val isRef: B,
                              val usageExtensions: ISZ[QualifiedName])

  @enum object Visibility {
    "Public"
    "Private"
    "Protected"
  }

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


  @sig trait FeatureSpecialization

  @datatype class QualifiedNameSpecialization(val names: ISZ[String]) extends FeatureSpecialization

  @datatype class RedefinitionSpecialization(val qualifiedNames: ISZ[QualifiedName]) extends FeatureSpecialization
}
