// #Sireum
package org.sireum.hamr.sysml.instantiation

import org.sireum._
import org.sireum.hamr.ir
import org.sireum.hamr.sysml.stipe.TypeHierarchy
import org.sireum.hamr.sysml.symbol.TypeInfo
import org.sireum.hamr.sysml.symbol.TypeInfo.PartDefinition
import org.sireum.message.{Position, Reporter}

object InstantiateUtil {

  val AadlPackageNames: ISZ[String] = ISZ(
    "AADL",
    "AADL_Project",
    "Base_Types",
    "Deployment_Properties",
    "Thread_Properties",
    "Timing_Properties"
  )
  val AadlComponentName: ISZ[String] = ISZ("AADL", "Component")

  val AadlSystemName: ISZ[String] = ISZ("AADL", "System")

  val AadlVirtualProcessorName: ISZ[String] = ISZ("AADL", "VirtualProcessor")

  val AadlProcessorName: ISZ[String] = ISZ("AADL", "Processor")

  val AadlProcessName: ISZ[String] = ISZ("AADL", "Process")

  val AadlThreadName: ISZ[String] = ISZ("AADL", "Thread")

  val AadlDataName: ISZ[String] = ISZ("AADL", "Data")

  val AadlAbstractName: ISZ[String] = ISZ("AADL", "Abstract")


  val HAMR_AADL_Array: ISZ[String]= ISZ("HAMR_AADL", "Array")
  val HAMR_AADL_Struct: ISZ[String]= ISZ("HAMR_AADL", "Struct")

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

  @pure def isAadlArray(p: TypeInfo.DefinitionTypeInfo, th: TypeHierarchy): B = {
    return th.poset.ancestorsOf(p.name).contains(HAMR_AADL_Array)
  }

  @pure def isAadlStruct(p: TypeInfo.DefinitionTypeInfo, th: TypeHierarchy): B = {
    return th.poset.ancestorsOf(p.name).contains(HAMR_AADL_Struct)
  }

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
        InstantiateUtil.isAadlVirtualProcesssor(name, typeHierarchy) ||
        InstantiateUtil.isAadlProcesssor(name, typeHierarchy) ||
        InstantiateUtil.isAadlProcesss(name, typeHierarchy) ||
        InstantiateUtil.isAadlThread(name, typeHierarchy) ||
        InstantiateUtil.isAadlData(name, typeHierarchy) ||
        InstantiateUtil.isAadlAbstract(name, typeHierarchy))
  }

  def isAadlComponentOpt(opt: Option[ir.Typed], typeHierarchy: TypeHierarchy): B = {
    opt match {
      case Some(t: ir.Typed.Name) => return isAadlComponent(t.ids, typeHierarchy)
      case _ => return F
    }
  }

  def isAadlSystem(name: ISZ[String], typeHierarchy: TypeHierarchy): B = {
    return typeHierarchy.poset.ancestorsOf(name).contains(InstantiateUtil.AadlSystemName)
  }

  def isAadlSystemOpt(opt: Option[ir.Typed], typeHierarchy: TypeHierarchy): B = {
    opt match {
      case Some(t: ir.Typed.Name) => return isAadlSystem(t.ids, typeHierarchy)
      case _ => return F
    }
  }

  def isAadlSystemDefinition(pd: TypeInfo.PartDefinition, typeHierarchy: TypeHierarchy): B = {
    return isAadlSystem(pd.name, typeHierarchy)
  }

  def isAadlVirtualProcesssor(name: ISZ[String], typeHierarchy: TypeHierarchy): B = {
    return typeHierarchy.poset.ancestorsOf(name).contains(InstantiateUtil.AadlVirtualProcessorName)
  }

  def isAadlVirtualProcessorOpt(opt: Option[ir.Typed], typeHierarchy: TypeHierarchy): B = {
    opt match {
      case Some(t: ir.Typed.Name) => return isAadlVirtualProcesssor(t.ids, typeHierarchy)
      case _ => return F
    }
  }

  def isAadlProcesssor(name: ISZ[String], typeHierarchy: TypeHierarchy): B = {
    return typeHierarchy.poset.ancestorsOf(name).contains(InstantiateUtil.AadlProcessorName)
  }

  def isAadlProcessorOpt(opt: Option[ir.Typed], typeHierarchy: TypeHierarchy): B = {
    opt match {
      case Some(t: ir.Typed.Name) => return isAadlProcesssor(t.ids, typeHierarchy)
      case _ => return F
    }
  }

  def isAadlProcesss(name: ISZ[String], typeHierarchy: TypeHierarchy): B = {
    return typeHierarchy.poset.ancestorsOf(name).contains(InstantiateUtil.AadlProcessName)
  }

  def isAadlProcessOpt(opt: Option[ir.Typed], typeHierarchy: TypeHierarchy): B = {
    opt match {
      case Some(t: ir.Typed.Name) => return isAadlProcesss(t.ids, typeHierarchy)
      case _ => return F
    }
  }

  def isAadlThread(name: ISZ[String], typeHierarchy: TypeHierarchy): B = {
    return typeHierarchy.poset.ancestorsOf(name).contains(InstantiateUtil.AadlThreadName)
  }

  def isAadlThreadOpt(opt: Option[ir.Typed], typeHierarchy: TypeHierarchy): B = {
    opt match {
      case Some(t: ir.Typed.Name) => return isAadlThread(t.ids, typeHierarchy)
      case _ => return F
    }
  }

  def isAadlData(name: ISZ[String], typeHierarchy: TypeHierarchy): B = {
    return typeHierarchy.poset.ancestorsOf(name).contains(InstantiateUtil.AadlDataName)
  }

  def isAadlDataOpt(opt: Option[ir.Typed], typeHierarchy: TypeHierarchy): B = {
    opt match {
      case Some(t: ir.Typed.Name) => return isAadlData(t.ids, typeHierarchy)
      case _ => return F
    }
  }

  def isAadlAbstract(name: ISZ[String], typeHierarchy: TypeHierarchy): B = {
    return typeHierarchy.poset.ancestorsOf(name).contains(InstantiateUtil.AadlAbstractName)
  }

  def isAadlAbstractOpt(opt: Option[ir.Typed], typeHierarchy: TypeHierarchy): B = {
    opt match {
      case Some(t: ir.Typed.Name) => return isAadlAbstract(t.ids, typeHierarchy)
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

  @sig trait PropertyValue

  @datatype class ValueProp extends PropertyValue
  @datatype class UnitProp (val unitOptional: B) extends PropertyValue
  @datatype class RangeProp(val low: PropertyValue,
                            val high: PropertyValue) extends PropertyValue

  @datatype class Property(val name: ISZ[String],
                           val expectedPropertyValues: ISZ[PropertyValue])

  val handledProperties: Map[ISZ[String], ISZ[PropertyValue]] =
    Map.empty[ISZ[String], ISZ[PropertyValue]] ++ ISZ[(ISZ[String], ISZ[PropertyValue])](
      ISZ[String]("CASE_Scheduling", "Domain") ~> ISZ[PropertyValue](UnitProp(T)),
      ISZ[String]("CASE_Scheduling", "Max_Domain") ~> ISZ[PropertyValue](UnitProp(T)),

      ISZ[String]("HAMR", "Implementation_Language") ~> ISZ[PropertyValue](ValueProp()),

      ISZ[String]("HAMR_Microkit", "Passive") ~> ISZ[PropertyValue](ValueProp()),
      ISZ[String]("HAMR_Microkit", "SMC") ~> ISZ[PropertyValue](ValueProp()),
      ISZ[String]("HAMR_Microkit", "Scheduling") ~> ISZ[PropertyValue](ValueProp()),

      ISZ[String]("Thread_Properties", "Dispatch_Protocol") ~> ISZ[PropertyValue](ValueProp()),

      ISZ[String]("Memory_Properties", "Data_Size") ~> ISZ[PropertyValue](UnitProp(F)),
      ISZ[String]("Memory_Properties", "Stack_Size") ~> ISZ[PropertyValue](UnitProp(F)),

      ISZ[String]("Timing_Properties", "Clock_Period") ~> ISZ[PropertyValue](UnitProp(F)),
      ISZ[String]("Timing_Properties", "Compute_Execution_Time") ~> ISZ[PropertyValue](RangeProp(low = UnitProp(F), high = UnitProp(F))),
      ISZ[String]("Timing_Properties", "Frame_Period") ~> ISZ[PropertyValue](UnitProp(F)),
      ISZ[String]("Timing_Properties", "Period") ~> ISZ[PropertyValue](UnitProp(F)),
      ISZ[String]("Timing_Properties", "Timing_Period") ~> ISZ[PropertyValue](UnitProp(F))
    )

  @pure def validateProperty(name: ISZ[String], propertyValues: ISZ[ir.PropertyValue], posOpt: Option[Position], reporter: Reporter): B = {
    val propValues = handledProperties.get(name).get
    if (propValues.size != propertyValues.size) {
      Instantiate.reportError(posOpt, s"Expecting a ${propValues.size} but found ${propertyValues.size}", reporter)
      return F
    }

    def valid (a: PropertyValue, b: ir.PropertyValue): Unit = {
      (a, b) match {
        case (UnitProp(optionalUnit), ir.UnitProp(value, bOp)) =>
          if (!optionalUnit && bOp.isEmpty) {
            Instantiate.reportError(posOpt, "Unit must be provided", reporter)
          }
        case (RangeProp(al, ah), ir.RangeProp(bl, bh)) =>
          valid(al, bl)
          valid(ah, bh)
        case (ValueProp(), ir.ValueProp(_)) =>
        case _ =>
          val an: String = a match {
            case i:UnitProp => "UnitProp"
            case i:ValueProp => "ValueProp"
            case i:RangeProp => "RangeProp"
          }
          val bn: String = b match {
            case i: ir.UnitProp => "UnitPop"
            case i: ir.ValueProp => "ValueProp"
            case i: ir.RangeProp => "RangeProp"
            case _ =>
              halt(s"Need to handle ${b}")
          }
          Instantiate.reportError(posOpt = posOpt, message = s"Expecting $an but found $bn", reporter = reporter)
      }
    }

    for (i <- 0 until propValues.size) {
      valid(propValues(i), propertyValues(i))
    }

    return T
  }
}

