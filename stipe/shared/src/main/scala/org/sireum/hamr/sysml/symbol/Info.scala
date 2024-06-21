// #Sireum
package org.sireum.hamr.sysml.symbol

import org.sireum._
import org.sireum.message.Position

@datatype trait Info

@datatype trait TypeInfo {

  @pure def name: ISZ[String]

  @pure def canHaveCompanion: B

  @pure def posOpt: Option[Position]

  //@pure def tpe: AST.Typed
}