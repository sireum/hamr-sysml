// #Sireum

package org.sireum.hamr.sysml

import org.sireum._

object SysMLAstUtil {

  @datatype class OccurrenceDefinition(val isAbstract: B)

  @enum object FeatureDirection {
    "In"
    "Out"
    "InOut"
  }

  @datatype class OccurrenceUsage(val isAbstract: B,
                                  val direction: Option[FeatureDirection.Type],
                                  //val isVariation: B,
                                  //val isReadOnly: B,
                                  //val isDerived: B,
                                  //val isEnd: B
   )

  @sig trait FeatureSpecialization

  @datatype class QualifiedNameSpecialization(val names: ISZ[String]) extends FeatureSpecialization
}
