// #Sireum

package org.sireum.hamr.sysml.ast

import org.sireum._
import org.sireum.lang.{ast => AST}


object Util {
  def ids2string(ids: ISZ[SysmlAst.Id]): ISZ[String] = {
    return (for(id <- ids) yield id.value)
  }

  def buildSlangTypedNamed(name: AST.Name): AST.Type.Named = {
    val typedName = AST.Typed.Name(
      ids = for(id <- name.ids) yield id.value,
      args = ISZ())
    // TODO: probably need to populate typedOpt after sym res in order to get the fqn
    val typedAttr = AST.TypedAttr(posOpt = name.attr.posOpt, typedOpt = Some(typedName))
    return AST.Type.Named(
      name = name,
      typeArgs = ISZ(),
      attr = typedAttr)
  }

}
