// #Sireum
package org.sireum.hamr.sysml.instantiation

import org.sireum._

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
}

