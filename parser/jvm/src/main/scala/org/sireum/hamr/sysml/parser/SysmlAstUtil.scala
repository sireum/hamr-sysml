package org.sireum.hamr.sysml.parser

import org.sireum._
import org.sireum.hamr.sysml.parser.{SysMLv2Parser => Sysml}
import org.sireum.lang.ast.Exp
import org.sireum.lang.{ast => AST}

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

  def getRangeExpressions(e: Exp): Option[(AST.Exp, AST.Exp)] = {
    e match {
      case AST.Exp.Invoke(
        None(),
        AST.Exp.Ident(AST.Id(string"RangeExpression")),
        _,
        ISZ(l, h)) => return Some((l,h))
      case _ => return None()
    }
  }

  def isRangeExp(e: Exp): Boolean = getRangeExpressions(e).nonEmpty
}
