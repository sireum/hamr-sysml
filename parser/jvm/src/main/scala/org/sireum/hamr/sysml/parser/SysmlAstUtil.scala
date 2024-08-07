package org.sireum.hamr.sysml

import org.sireum._
import org.sireum.hamr.sysml.ast.SysmlAst._
import org.sireum.hamr.sysml.parser.{SysMLv2Parser => Sysml}
import org.sireum.hamr.sysml.{ast => SAST}
import org.sireum.lang.ast.Exp
import org.sireum.lang.{ast => AST}
import org.sireum.message.{FlatPos, Position}

object SysmlAstUtil {

  def isRegularComment(context: Sysml.RuleAnnotatingElementContext): B = {
    context match {
      case i: Sysml.RuleAnnotatingElement1Context =>
        val c = i.ruleComment()
        return c.K_COMMENT() == null && c.K_ABOUT() == null && c.K_LOCALE() == null
      case _ => F
    }
  }

  object Placeholders {
    def emptyTypedAttr: AST.TypedAttr = {
      return AST.TypedAttr(
        posOpt = None(), typedOpt = None()
      )
    }

    def emptyDefinitionElement: DefinitionElement = {
      return Comment(
        visibility = Visibility.Public,
        identification = None(),
        abouts = ISZ(),
        locale = None(),
        comment = "PLACEHOLDER",
        attr = SAST.Attr(posOpt = None()))
    }

    def emptyResolvedAttr(posOpt: Option[Position]): AST.ResolvedAttr = {
      return AST.ResolvedAttr(posOpt = posOpt, resOpt = None(), typedOpt = None())
    }

    def emptyAttr: AST.Attr = {
      return AST.Attr(posOpt = None())
    }

    def emptyExp: org.sireum.lang.ast.Exp = {
      return AST.Exp.LitString(value = "TODO", attr = emptyAttr)
    }

    def emptyRefPrefix: RefPrefix = {
      return RefPrefix(direction = None(), isAbstract = F, isVariation = F, isReadOnly = F, isDerived = F, isEnd = F)
    }

    def emptyUsagePrefix: UsagePrefix = {
      return UsagePrefix(refPrefix = emptyRefPrefix,
        isRef = F, usageExtensions = ISZ())
    }

    def NonOccurrenceUsageElementPlaceholder: NonOccurrenceUsageElement = {
      return AttributeUsage(
        visibility = Visibility.Public,
        prefix = emptyUsagePrefix,
        identification = None(),
        specializations = ISZ(),
        featureValue = None(),
        definitionBodyItems = ISZ(),
        tipeOpt = None(),
        attr = SAST.ResolvedAttr(None(), None(), None()))
    }

    def emptyOccurrenceUsagePrefix: OccurrenceUsagePrefix = {
      return OccurrenceUsagePrefix(
        refPrefix = emptyRefPrefix,
        isRef = F,
        isIndividual = F,
        isSnapshot = F,
        isTimeslice = F,
        usageExtensions = ISZ()
      )
    }

    def OccurrenceUsageElementPlaceholder: OccurrenceUsageElement = {
      return ItemUsage(
        visibility = Visibility.Public,
        occurrenceUsagePrefix = emptyOccurrenceUsagePrefix,
        identification = None(),
        specializations = ISZ(),
        featureValue = None(),
        definitionBodyItems = ISZ(),
        tipeOpt = None(),
        attr = SAST.ResolvedAttr(None(), None(), None()))
    }
  }

  def mergePos(a: Option[Position], b: Option[Position]): Option[Position] = {
    (a, b) match {
      case (None(), _) => b
      case (_, None()) => a
      case (Some(af: FlatPos), Some(bf: FlatPos)) =>
        assert(af.offset32 <= bf.offset32, s"${af.offset32} vs ${bf.offset32}")

        val length = bf.offset32 - af.offset32 + bf.length32
        return Some(FlatPos(uriOpt = af.uriOpt,
          beginLine32 = af.beginLine32, beginColumn32 = af.beginColumn32,
          endLine32 = bf.endLine32, endColumn32 = bf.endColumn32,
          offset32 = af.offset32,
          length32 = length))
      case _ => halt(s"Not expecting $a $b")
    }
  }

  def collapse1(lhs: Exp, binaryOp: String, stack: Stack[Exp]): Exp = {
    var s = stack
    if (s.isEmpty) {
      return lhs
    } else {
      while (s.size > 1) {
        val (r, s0) = s.pop.get
        val (l, s1) = s0.pop.get
        val posOpt = SysmlAstUtil.mergePos(l.posOpt, r.posOpt)
        s = s1.push(AST.Exp.Binary(left = l, op = binaryOp, right = r, attr = Placeholders.emptyResolvedAttr(posOpt), opPosOpt = posOpt))
      }
      val rhs = s.pop.get._1
      val posOpt = SysmlAstUtil.mergePos(lhs.posOpt, rhs.posOpt)
      val ret = AST.Exp.Binary(left = lhs, op = binaryOp, right = rhs, attr = Placeholders.emptyResolvedAttr(posOpt), opPosOpt = posOpt)
      return ret
    }
  }

  def collapse2(lhs: Exp, stack: Stack[(String, Exp)]): Exp = {
    var s = stack
    if (s.isEmpty) {
      return lhs
    } else {
      while (s.size > 1) {
        val (r, s0) = s.pop.get
        val (l, s1) = s0.pop.get
        val posOpt = SysmlAstUtil.mergePos(l._2.posOpt, r._2.posOpt)
        s = s1.push((l._1, Exp.Binary(left = l._2, op = r._1, right = r._2, attr = Placeholders.emptyResolvedAttr(posOpt), opPosOpt = posOpt)))
      }
      val rhs = s.pop.get._1
      val posOpt = SysmlAstUtil.mergePos(lhs.posOpt, rhs._2.posOpt)
      val ret = AST.Exp.Binary(left = lhs, op = rhs._1, right = rhs._2, attr = Placeholders.emptyResolvedAttr(posOpt), opPosOpt = posOpt)
      return ret
    }
  }
  def toRA(posOpt: Option[Position]): AST.ResolvedAttr = {
    return AST.ResolvedAttr(posOpt, None(), None())
  }
  def toSelect(root: AST.Exp, ids: ISZ[AST.Id]): AST.Exp = {


    if (ids.size > 0) {
      val o = ops.ISZOps(ids)
      var select = AST.Exp.Select(
        receiverOpt = Some(root),
        id = ids(0),
        targs = ISZ(),
        attr = toRA(mergePos(root.posOpt, ids(0).attr.posOpt)))

      for (i <- 1 until ids.size) {
        select = AST.Exp.Select(
          receiverOpt = Some(select),
          id = ids(i),
          targs = ISZ(),
          attr = toRA(mergePos(select.posOpt, ids(i).attr.posOpt)))
      }
      return select
    } else {
      return root
    }
  }

  def toSelectH(ids: ISZ[AST.Id]): AST.Exp = {
    assert (ids.nonEmpty)
    val root = AST.Exp.Ident(id = ids(0), attr = toRA(ids(0).attr.posOpt))
    if (ids.size > 1) {
      toSelect(root, ops.ISZOps(ids).tail)
    } else {
      return root
    }
  }
}
