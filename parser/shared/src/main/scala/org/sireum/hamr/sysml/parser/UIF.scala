// #Sireum
package org.sireum.hamr.sysml.parser

import org.sireum._

object UIF {
  val RangeExpression: String = "RangeExpression"

  @pure def isUif(s: String): B = {
    s match {
      case RangeExpression => return T
      case _ => return F
    }
  }
}

