// #Sireum
package org.sireum.hamr.sysml.symbol

import org.sireum._
import org.sireum.hamr.{ir => SAST}
import org.sireum.lang.{ast => AST}
import org.sireum.hamr.ir.{SysmlAst, Typed}
import org.sireum.message.{Position, Reporter}

object Util {

  // Base::Anything is the ultimate root type of the type system.
  val Base__Anything: ISZ[String] = ISZ("Base", "Anything")

  def ids2string(ids: ISZ[SysmlAst.Id]): ISZ[String] = {
    return (for(id <- ids) yield id.value)
  }

  def slangIds2string(ids: ISZ[AST.Id]): ISZ[String] = {
    return (for(id <- ids) yield id.value)
  }

  def getId(idOpt: Option[SysmlAst.Identification],
            specializations: ISZ[SysmlAst.FeatureSpecialization],
            posOpt: Option[Position], toolKind: String, reporter: Reporter): (Option[String], Option[Position]) = {

    idOpt match {
      case Some(id) =>
        if (id.shortName.nonEmpty) {
          // TODO
          //reporter.warn(id.shortName.get.posOpt, toolKind, "Short names are not currently supported")
        }
        id.name match {
          case Some(id2) =>
            return (Some(id2.value), id2.posOpt)
          case _ =>
            reporter.error(id.posOpt, toolKind, "Names must be provided")
        }
      case _ =>
        if (specializations.nonEmpty) {
          specializations(0) match {
            case SysmlAst.RedefinitionsSpecialization(names) =>
              if (names(0).ids.size == 1) {
                return (Some(names(0).ids(0).value), names(0).ids(0).posOpt)
              } else {
                reporter.error(posOpt, toolKind,
                  "Currently expecting a simple name for redefinition specializations")
              }
            case SysmlAst.SubsettingsSpecialization(names) =>
              if (names(0).ids.size == 1) {
                return (Some(names(0).ids(0).value), names(0).ids(0).posOpt)
              } else {
                reporter.error(posOpt, toolKind,
                  "Currently expecting a simple name for subsetting specializations")
              }
            case _ =>

          }
        }
    }
    return (None(), None())
  }

  def toSlangResolvedOpt(r: Option[SAST.ResolvedInfo]): Option[AST.ResolvedInfo] = {
    if (r.isEmpty) {
      return None()
    }
    r.get match {
      case SAST.ResolvedInfo.Package(name) => return None()
      case SAST.ResolvedInfo.Enum(name) =>
        return Some(AST.ResolvedInfo.Enum(name = name))
      case SAST.ResolvedInfo.EnumElement(owner, name, ordinal) =>
        return Some(AST.ResolvedInfo.EnumElement(owner = owner, name = name, ordinal = ordinal))
      case SAST.ResolvedInfo.AllocationUsage(owner, name) =>
        return Some(AST.ResolvedInfo.Var(isInObject = T, isSpec = F, isVal = T, owner = owner, id = name))
      case SAST.ResolvedInfo.AttributeUsage(owner, name) =>
        return Some(AST.ResolvedInfo.Var(isInObject = T, isSpec = F, isVal = T, owner = owner, id = name))
      case SAST.ResolvedInfo.ConnectionUsage(owner, name) =>
        return Some(AST.ResolvedInfo.Var(isInObject = T, isSpec = F, isVal = T, owner = owner, id = name))
      case SAST.ResolvedInfo.ItemUsage(owner, name) =>
        return Some(AST.ResolvedInfo.Var(isInObject = T, isSpec = F, isVal = T, owner = owner, id = name))
      case SAST.ResolvedInfo.PartUsage(owner, name) =>
        return Some(AST.ResolvedInfo.Var(isInObject = T, isSpec = F, isVal = T, owner = owner, id = name))
      case SAST.ResolvedInfo.PortUsage(owner, name) =>
        return Some(AST.ResolvedInfo.Var(isInObject = T, isSpec = F, isVal = T, owner = owner, id = name))
      case SAST.ResolvedInfo.ReferenceUsage(owner, name) =>
        return Some(AST.ResolvedInfo.Var(isInObject = T, isSpec = F, isVal = T, owner = owner, id = name))
    }
  }

  def toSlangTypedOpt(t: Option[SAST.Typed]): Option[AST.Typed] = {
    t match {
      case Some(SAST.Typed.Package(name)) =>
        return Some(AST.Typed.Name(ids = name, args = ISZ()))
      case Some(SAST.Typed.Name(ids)) =>
        return Some(AST.Typed.Name(ids = ids, args = ISZ()))
      case Some(SAST.Typed.Enum(name)) =>
        return Some(AST.Typed.Enum(name))
      case x => return None()
    }
  }

  def toSysmlTypedOpt(tOpt: Option[AST.Typed]): Option[SAST.Typed] = {
    val ret: Option[SAST.Typed] =
      tOpt match {
        case Some(t: AST.Typed.Name) => return Some(SAST.Typed.Name(t.ids))
        case _ => None()
      }
    return ret
  }

  def printTyped(t: Typed): String = {
    t match {
      case t: Typed.Name => return st"${(t.ids, "::")}".render
      case _ => return t.string
    }
  }
}
