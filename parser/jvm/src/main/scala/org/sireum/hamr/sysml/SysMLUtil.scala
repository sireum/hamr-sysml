// #Sireum
package org.sireum.hamr.sysml

import org.sireum._
import org.antlr.v4.runtime.ParserRuleContext
import org.sireum.hamr.ir._
import org.sireum.hamr.sysml.parser.SysMLv2Parser

object SysMLUtil {

  def emptyAadl: Aadl = {
    return Aadl(
      components = ISZ(),
      annexLib = ISZ(),
      dataComponents = ISZ()
    )
  }

  def emptyComponent: Component = {
    return Component(
      identifier = Name(name = ISZ("todo"), pos = None()),
      category = ComponentCategory.Data,
      classifier = None(),
      features = ISZ(),
      subComponents = ISZ(),
      connections = ISZ(),
      connectionInstances = ISZ(),
      properties = ISZ(),
      flows = ISZ(),
      modes = ISZ(),
      annexes = ISZ(),
      uriFrag = "")
  }

  def emptyFeature: Feature = {
    return FeatureEnd(
      identifier = Name(name = ISZ("todo"), pos = None()),
      direction = Direction.In,
      category = FeatureCategory.BusAccess,
      classifier = None(),
      properties = ISZ(),
      uriFrag = ""
    )
  }

  def emptyName(): Name = {
    return Name(name = ISZ(""), pos = None())
  }

}
