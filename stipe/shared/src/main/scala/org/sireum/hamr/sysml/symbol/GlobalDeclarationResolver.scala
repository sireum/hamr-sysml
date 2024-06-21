// #Sireum
package org.sireum.hamr.sysml.symbol

import org.sireum._
import org.sireum.hamr.sysml.ast.SysmlAst
import org.sireum.hamr.sysml.symbol.Resolver.{NameMap, TypeMap}
import org.sireum.message.Reporter

object GlobalDeclarationResolver {

}

@record class GlobalDeclarationResolver(var globalNameMap: NameMap, var globalTypeMap: TypeMap, val reporter: Reporter) {
  def resolveProgram(topUnit: SysmlAst.TopUnit): Unit = {
    halt("todo")
  }

}


