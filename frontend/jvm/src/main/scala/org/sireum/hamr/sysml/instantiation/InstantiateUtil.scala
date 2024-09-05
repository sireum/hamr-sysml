// #Sireum
package org.sireum.hamr.sysml.instantiation

import org.sireum._
import org.sireum.hamr.ir.Typed
import org.sireum.hamr.sysml.stipe.TypeHierarchy
import org.sireum.hamr.sysml.symbol.TypeInfo
import org.sireum.hamr.sysml.symbol.TypeInfo.PartDefinition

object InstantiateUtil {

  val AadlPackageNames: ISZ[String] = ISZ(
    "AADL",
    "AADL_Project",
    "Base_Types",
    "Deployment_Properties",
    "Thread_Properties",
    "Timing_Properties"
  )

  val AadlSystemName: ISZ[String] = ISZ("AADL", "System")

  val AadlProcessorName: ISZ[String] = ISZ("AADL", "Processor")

  val AadlProcessName: ISZ[String] = ISZ("AADL", "Process")

  val AadlThreadName: ISZ[String] = ISZ("AADL", "Thread")

  val AadlDataName: ISZ[String] = ISZ("AADL", "Data")

  val AadlAbstractName: ISZ[String] = ISZ("AADL", "Abstract")

  val AadlBaseTypeNames: ISZ[ISZ[String]] = for(t <- ISZ(
    "Boolean",

    "Integer",
    "Integer_8",
    "Integer_16",
    "Integer_32",
    "Integer_64",

    "Unsigned_8",
    "Unsigned_16",
    "Unsigned_32",
    "Unsigned_64",

    "Float",
    "Float_32",
    "Float_64",

    "Character",

    "String")) yield ISZ("Base_Types", t)

  def isAadlAttribute(name: ISZ[String]): B = {
    if (name.isEmpty) {
      return F
    } else {
      return ops.ISZOps(AadlPackageNames).contains(name(0))
    }
  }

  def isAadlComponent(name: ISZ[String], typeHierarchy: TypeHierarchy): B = {
    return (
      InstantiateUtil.isAadlSystem(name, typeHierarchy) ||
        InstantiateUtil.isAadlProcesssor(name, typeHierarchy) ||
        InstantiateUtil.isAadlProcesss(name, typeHierarchy) ||
        InstantiateUtil.isAadlThread(name, typeHierarchy) ||
        InstantiateUtil.isAadlData(name, typeHierarchy) ||
        InstantiateUtil.isAadlAbstract(name, typeHierarchy))
  }

  def isAadlComponentOpt(opt: Option[Typed], typeHierarchy: TypeHierarchy): B = {
    opt match {
      case Some(t: Typed.Name) => return isAadlComponent(t.ids, typeHierarchy)
      case _ => return F
    }
  }

  def isAadlSystem(name: ISZ[String], typeHierarchy: TypeHierarchy): B = {
    return typeHierarchy.poset.ancestorsOf(name).contains(InstantiateUtil.AadlSystemName)
  }

  def isAadlSystemOpt(opt: Option[Typed], typeHierarchy: TypeHierarchy): B = {
    opt match {
      case Some(t: Typed.Name) => return isAadlSystem(t.ids, typeHierarchy)
      case _ => return F
    }
  }

  def isAadlSystemDefinition(pd: TypeInfo.PartDefinition, typeHierarchy: TypeHierarchy): B = {
    return isAadlSystem(pd.name, typeHierarchy)
  }

  def isAadlProcesssor(name: ISZ[String], typeHierarchy: TypeHierarchy): B = {
    return typeHierarchy.poset.ancestorsOf(name).contains(InstantiateUtil.AadlProcessorName)
  }

  def isAadlProcessorOpt(opt: Option[Typed], typeHierarchy: TypeHierarchy): B = {
    opt match {
      case Some(t: Typed.Name) => return isAadlProcesssor(t.ids, typeHierarchy)
      case _ => return F
    }
  }

  def isAadlProcesss(name: ISZ[String], typeHierarchy: TypeHierarchy): B = {
    return typeHierarchy.poset.ancestorsOf(name).contains(InstantiateUtil.AadlProcessName)
  }

  def isAadlProcessOpt(opt: Option[Typed], typeHierarchy: TypeHierarchy): B = {
    opt match {
      case Some(t: Typed.Name) => return isAadlProcesss(t.ids, typeHierarchy)
      case _ => return F
    }
  }

  def isAadlThread(name: ISZ[String], typeHierarchy: TypeHierarchy): B = {
    return typeHierarchy.poset.ancestorsOf(name).contains(InstantiateUtil.AadlThreadName)
  }

  def isAadlThreadOpt(opt: Option[Typed], typeHierarchy: TypeHierarchy): B = {
    opt match {
      case Some(t: Typed.Name) => return isAadlThread(t.ids, typeHierarchy)
      case _ => return F
    }
  }

  def isAadlData(name: ISZ[String], typeHierarchy: TypeHierarchy): B = {
    return typeHierarchy.poset.ancestorsOf(name).contains(InstantiateUtil.AadlDataName)
  }

  def isAadlDataOpt(opt: Option[Typed], typeHierarchy: TypeHierarchy): B = {
    opt match {
      case Some(t: Typed.Name) => return isAadlData(t.ids, typeHierarchy)
      case _ => return F
    }
  }

  def isAadlAbstract(name: ISZ[String], typeHierarchy: TypeHierarchy): B = {
    return typeHierarchy.poset.ancestorsOf(name).contains(InstantiateUtil.AadlAbstractName)
  }

  def isAadlAbstractOpt(opt: Option[Typed], typeHierarchy: TypeHierarchy): B = {
    opt match {
      case Some(t: Typed.Name) => return isAadlAbstract(t.ids, typeHierarchy)
      case _ => return F
    }
  }

  def getSystemRoots(typeHierarchy: TypeHierarchy): ISZ[TypeInfo.PartDefinition] = {
    var systemRoots = ISZ[PartDefinition]()
    for (n <- typeHierarchy.typeMap.entries) {
      n._2 match {
        case pd: TypeInfo.PartDefinition if isAadlSystemDefinition(pd, typeHierarchy) =>
          systemRoots = systemRoots :+ pd
        case _ =>
      }
    }

    return systemRoots
  }
}

