// #Sireum
package org.sireum.hamr.sysml

import org.sireum._
import org.sireum.hamr.sysml.ast.SysmlAst.TopUnit

@ext object SysMLParser {

  def parse(uriOpt: Option[String], content: String, reporter: message.Reporter): Option[TopUnit] = $

  def parseH(uriOpt: Option[String], content: String, isSysML: B, reporter: message.Reporter): Option[TopUnit] = $

}
