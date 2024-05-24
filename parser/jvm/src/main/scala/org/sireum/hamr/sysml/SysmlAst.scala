// #Sireum

package org.sireum.hamr.sysml

import org.sireum._

object SysmlAst {
  type QualifiedName = ISZ[String]

  @datatype class OccurrenceDefinition(val isAbstract: B)

  @enum object FeatureDirection {
    "In"
    "Out"
    "InOut"
  }

  @sig trait DefinitionBodyItem

  @datatype class PortUsage(val occurrenceUsage: OccurrenceUsage,
                            val identification: Option[Identification],
                            val specializations: ISZ[FeatureSpecialization],
                            val definitionBodyItems: ISZ[DefinitionBodyItem])

  @datatype class OccurrenceUsage(// ref prefix
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

  @enum object Visibility {
    "Public"
    "Private"
    "Protected"
  }

  @sig trait AnnotatingElement {
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


  @datatype class Alias(val visibility: Visibility.Type,
                        val identification: Identification,
                        val target: QualifiedName)


  @datatype class Identification(val shortName: Option[String],
                                 val name: Option[String])


  @sig trait FeatureSpecialization

  @datatype class QualifiedNameSpecialization(val names: ISZ[String]) extends FeatureSpecialization
}
