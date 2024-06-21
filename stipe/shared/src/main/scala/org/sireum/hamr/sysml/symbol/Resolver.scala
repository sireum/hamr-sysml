// #Sireum
package org.sireum.hamr.sysml.symbol

import org.sireum._

object Resolver {

  type QName = ISZ[String]

  type NameMap = HashSMap[QName, Info]

  type TypeMap = HashSMap[QName, TypeInfo]

  def addBuiltIns(nameMap: NameMap, typeMap: TypeMap): (NameMap, TypeMap) = {
    return (nameMap, typeMap)
  }
}
