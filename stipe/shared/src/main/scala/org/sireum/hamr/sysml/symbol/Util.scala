// #Sireum
package org.sireum.hamr.sysml.symbol

import org.sireum._
import org.sireum.hamr.sysml.ast.SysmlAst
import org.sireum.message.{Position, Reporter}

object Util {
  def getId(id: Option[SysmlAst.Identification],
            specializations: ISZ[SysmlAst.FeatureSpecialization],
            posOpt: Option[Position], toolKind: String, reporter: Reporter): (Option[String], Option[Position]) = {

    id match {
      case Some(id) =>
        if (id.shortName.nonEmpty) {
          reporter.warn(id.shortName.get.posOpt, toolKind, "Short names are not currently supported")
        }
        id.name match {
          case Some(id) =>
            return (Some(id.value), id.posOpt)
          case _ =>
            reporter.error(id.posOpt, toolKind, "Names must be provided")
        }
      case _ =>
        if (specializations.nonEmpty) {
          specializations match {
            case ISZ(SysmlAst.RedefinitionsSpecialization(names)) if names.size == 1 =>
              if (names(0).ids.size == 1) {
                return (Some(names(0).ids(0).value), names(0).ids(0).posOpt)
              } else {
                reporter.error(posOpt, toolKind,
                  "Currently expecting a simple name for redefinition specializations")
              }
            case _ =>

          }
        }
    }
    return (None(), None())
  }
}
