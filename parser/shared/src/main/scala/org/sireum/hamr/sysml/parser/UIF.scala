// #Sireum
package org.sireum.hamr.sysml.parser

import org.sireum._

object UIF {
  val RangeExpression: String = "RangeExpression"

  val SysmlUnitExpression: String ="SysmlUnitExpression"

  @pure def isUif(s: String): B = {
    s match {
      case RangeExpression => return T
      case SysmlUnitExpression => return T
      case _ => return F
    }
  }
}

