// #Sireum

package org.sireum.hamr.sysml.ast

import org.sireum._

object Util {
  def ids2string(ids: ISZ[SysmlAst.Id]): ISZ[String] = {
    return (for(id <- ids) yield id.value)
  }
}
