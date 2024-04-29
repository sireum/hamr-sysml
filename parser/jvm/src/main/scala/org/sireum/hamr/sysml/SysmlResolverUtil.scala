// #Sireum
package org.sireum.hamr.sysml

import org.sireum._
import org.sireum.hamr.ir._

object SysMLUtil {

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

  def emptyName(): Name = {
    return Name(name = ISZ(""), pos = None())
  }
}
