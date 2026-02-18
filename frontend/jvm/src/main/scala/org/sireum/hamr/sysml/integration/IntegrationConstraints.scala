// #Sireum
package org.sireum.hamr.sysml.integration

import org.sireum._
import org.sireum.hamr.codegen.common.symbols.AadlPort
import org.sireum.hamr.ir.{GclAssume, GclGuarantee}
import org.sireum.lang.ast.Exp
import org.sireum.lang.symbol.TypeInfo
import org.sireum.lang.tipe.TypeHierarchy
import org.sireum.lang.{ast => AST}
import org.sireum.message.{Position, Reporter}
import org.sireum.lang.tipe.{TypeHierarchy => SlangTypeHierarchy}

@record class ArrayConstraints extends AST.MTransformer {
  var receiverOpt: Option[Exp] = None()
  var ident: Option[AST.Exp.Ident] = None()

  def exp: Option[AST.Exp] = {
    receiverOpt match {
      case Some(ro) => return Some(AST.Exp.Select(receiverOpt = receiverOpt, id = ident.get.id, targs = ISZ(), attr = ident.get.attr))
      case _ => return ident.asInstanceOf[Option[AST.Exp]]
    }
  }

  var resOpt: Option[AST.ResolvedInfo] = None()

  val reporter: Reporter = Reporter.create

  def isArray(value: Option[AST.Typed]): B = {
    value match {
      case Some(AST.Typed.Name(ids, _)) =>
        ids match {
          case ISZ("org", "sireum", "IS") => return T
          case _ =>
        }
      case _ =>
    }
    return F
  }

  def add(receiverOptP: Option[AST.Exp], identP: AST.Exp.Ident, res: AST.ResolvedInfo): Unit = {
    if (ident.nonEmpty && (resOpt.get != res)) {
      reporter.error(identP.posOpt, "IntegrationConstraints", "Nested arrays are not currently supported")
    }
    receiverOpt = receiverOptP
    ident = Some(identP)
    resOpt = Some(res)
  }

  override def postExpInvoke(o: Exp.Invoke): MOption[Exp] = {
    if (isArray(o.ident.typedOpt)) {
      add(o.receiverOpt, o.ident, o.ident.resOpt.get)
    }
    return MNone()
  }

  override def postExpSelect(o: Exp.Select): MOption[Exp] = {
    if (isArray(o.typedOpt)) {
      add(o.receiverOpt, AST.Exp.Ident(o.id, o.attr), o.resOpt.get)
    }
    return MNone()
  }

  override def postExpIdent(o: Exp.Ident): MOption[Exp] = {
    if (isArray(o.typedOpt)) {
      add (None(), o, o.resOpt.get)
    }
    return MNone()
  }
}

object IntegrationConstraints {


  val integration_constraint_title_prefix: String = "Integration constraints of"

  @datatype class IntegrationConstraints(val systemRootName: ISZ[String],
                                         val systemRootPos: Option[Position],
                                         val typeHierarchy: SlangTypeHierarchy,

                                         // title ~> IntegrationConnection
                                         val connections: HashSMap[String, IntegrationConnection])

  @datatype class IntegrationConnection(val srcPort: AadlPort,
                                        val srcPortExp: AST.Exp,
                                        val srcGclSpec: Option[GclGuarantee],

                                        val arrayConstraints: Option[AST.Exp],

                                        val dstPort: AadlPort,
                                        val dstPortExp: AST.Exp,
                                        val dstGclSpec: Option[GclAssume],

                                        val srcConstraint: Option[AST.Exp], // assume this
                                        val dstConstraint: Option[AST.Exp], // assert this

                                        // connectionMidPoint is the identifier path and position info of the
                                        // connection references where out connections change to in connections
                                        val connectionMidPoint: (ISZ[String], Option[Position]),

                                        val connectionReferences: HashSMap[ISZ[String], Option[Position]]) {

    val title: String = st"$integration_constraint_title_prefix ${(connectionMidPoint._1, ".")}".render

    @pure def claim: AST.Exp = {
      val condAndResOpt: AST.ResolvedInfo = AST.ResolvedInfo.BuiltIn(AST.ResolvedInfo.BuiltIn.Kind.BinaryCondAnd)
      val eqResOpt: AST.ResolvedInfo = AST.ResolvedInfo.BuiltIn(AST.ResolvedInfo.BuiltIn.Kind.BinaryEq)
      val condImplyResOpt: AST.ResolvedInfo = AST.ResolvedInfo.BuiltIn(AST.ResolvedInfo.BuiltIn.Kind.BinaryImply)

      val src: AST.Exp = srcConstraint match {
        case Some(e) => e
        case _ => AST.Exp.LitB(T, AST.Attr(srcPort.feature.identifier.pos))
      }

      val dst = dstConstraint.get

      val (ids, midPointPos) = connectionMidPoint

      val lhs_portEquiv = AST.Exp.Binary(srcPortExp, AST.Exp.BinaryOp.Eq, dstPortExp,
        AST.ResolvedAttr(midPointPos, Some(eqResOpt), AST.Typed.bOpt), midPointPos)

      val lhsImply = AST.Exp.Binary(lhs_portEquiv, AST.Exp.BinaryOp.CondAnd, src,
        AST.ResolvedAttr(midPointPos, Some(condAndResOpt), AST.Typed.bOpt), midPointPos)

      val impl = AST.Exp.Binary(lhsImply, AST.Exp.BinaryOp.CondImply, dst,
        AST.ResolvedAttr(midPointPos, Some(condImplyResOpt), AST.Typed.bOpt), midPointPos)

      return impl
    }
  }

  @pure def getArrayConstraints(e: Exp, th: TypeHierarchy, reporter: Reporter): Option[AST.Exp] = {
    val a = ArrayConstraints()
    a.transformExp(e)

    reporter.reports(a.reporter.messages)

    a.exp match {
      case Some(c) =>
        val tn = c.typedOpt.get.asInstanceOf[AST.Typed.Name]

        tn.args(0) match {
          case AST.Typed.Name(indexingType, _) =>
            th.typeMap.get(indexingType) match {
              case Some(s: TypeInfo.SubZ) =>
                assert (s.ast.min == 0, s.ast.min.string)

                val max = s.ast.max

                val eqResOpt: AST.ResolvedInfo = AST.ResolvedInfo.BuiltIn(AST.ResolvedInfo.BuiltIn.Kind.BinaryEq)

                val attr = AST.Attr(c.posOpt)

                val res = AST.ResolvedInfo.Method(
                  isInObject = F,
                  mode = AST.MethodMode.Method,
                  typeParams = ISZ(),
                  owner = ISZ("org", "sireum", "IS"),
                  id = "size",
                  paramNames = ISZ(),
                  tpeOpt = Some(AST.Typed.Fun(
                    purity = AST.Purity.Pure,
                    isByName = T,
                    args = ISZ(),
                    ret = AST.Typed.z)),
                  reads = ISZ(),
                  writes = ISZ())

                val size = AST.Exp.Select(
                  receiverOpt = Some(c),
                  id = AST.Id("size", attr),
                  targs = ISZ(),
                  attr = AST.ResolvedAttr(
                    posOpt = c.posOpt,
                    resOpt = Some(res),
                    typedOpt = Some(AST.Typed.Method(
                      isInObject = res.isInObject,
                      mode = res.mode,
                      typeParams = ISZ(),
                      owner = res.owner,
                      name = res.id,
                      paramNames = res.paramNames,
                      tpe = res.tpeOpt.get))))

                return Some(AST.Exp.Binary(size, AST.Exp.BinaryOp.Eq, AST.Exp.LitZ(max + 1, attr),
                  AST.ResolvedAttr(c.posOpt, Some(eqResOpt), AST.Typed.bOpt), c.posOpt))

              case _ =>
            }
          case _ =>
        }

        return None()
      case _ => return a.exp
    }
  }
}
