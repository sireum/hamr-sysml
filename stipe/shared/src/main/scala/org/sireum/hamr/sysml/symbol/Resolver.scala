// #Sireum
package org.sireum.hamr.sysml.symbol

import org.sireum._
import org.sireum.message.Position

object Resolver {

  type QName = ISZ[String]

  type NameMap = HashSMap[QName, Info]

  type TypeMap = HashSMap[QName, TypeInfo]

  val resolverKind: String = "SysML v2 Resolver"

  def addBuiltIns(nameMap: NameMap, typeMap: TypeMap): (NameMap, TypeMap) = {
    return (nameMap, typeMap)
  }

  def isPosUriSuffixEq(p1: Option[Position], p2: Option[Position]): B = {
    (p1, p2) match {
      case (Some(pos1), Some(pos2)) =>
        if (pos1.uriOpt.nonEmpty && pos2.uriOpt.nonEmpty) {
          val uri1 = pos1.uriOpt.get
          val uri2 = pos2.uriOpt.get
          return ops.StringOps(uri2).endsWith(uri1)
        }
      case (_, _) =>
    }
    return F
  }
}
