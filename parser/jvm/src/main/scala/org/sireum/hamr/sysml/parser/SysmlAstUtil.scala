package org.sireum.hamr.sysml.parser

import org.sireum._
import org.sireum.hamr.sysml.parser.{SysMLv2Parser => Sysml}

object SysmlAstUtil {

  def isRegularComment(context: Sysml.RuleAnnotatingElementContext): B = {
    // ruleAnnotatingElement:
    //   ruleComment #ruleAnnotatingElement1
    //   | ruleDocumentation #ruleAnnotatingElement2
    //   | ruleTextualRepresentation #ruleAnnotatingElement3
    //   | ruleMetadataUsage #ruleAnnotatingElement4;
    //
    // ruleComment:
    //   ('comment' ruleIdentification? ('about' ruleAnnotation (',' ruleAnnotation)*)?)? ('locale' RULE_STRING_VALUE)?
    //   RULE_REGULAR_COMMENT;
    context match {
      case i: Sysml.RuleAnnotatingElement1Context =>
        return i.ruleComment().K_COMMENT() == null && i.ruleComment().K_ABOUT() == null && i.ruleComment().K_LOCALE() == null
      case _ => F
    }
  }
}
