package org.sireum.hamr.sysml

import org.sireum._
import org.sireum.hamr.sysml.parser.{SysMLv2Parser => Sysml}

object SysmlAstUtil {

  def isRegularComment(context: Sysml.RuleAnnotatingElementContext): B = {
    context match {
      case i: Sysml.RuleAnnotatingElement1Context =>
        val c = i.ruleComment()
        return c.K_COMMENT() == null && c.K_ABOUT() == null && c.K_LOCALE() == null
      case _ => F
    }
  }

}
