// #Sireum
package org.sireum.hamr.sysml.parser

import org.sireum._
import org.sireum.hamr.ir.SysmlAst.TopUnit

@ext object SysMLParser {

  def parse(uriOpt: Option[String], content: String, reporter: message.Reporter): Option[TopUnit] = $

  def parseH(uriOpt: Option[String], content: String, isSysML: B, reporter: message.Reporter): Option[TopUnit] = $

}
