package org.sireum.hamr.sysml

import org.sireum._
import org.sireum.hamr.ir
import org.sireum.hamr.codegen.common.util.ModelUtil
import org.sireum.hamr.sysml.FrontEnd.Input
import org.sireum.message.Reporter

/** Device, Memory, Bus and VirtualBus part usages are valid subcomponents of a System.  The
  * instantiator used to reject them ("Part usages of System must be a descendant of one of the
  * following: ...", a list that included them) because it only recognized system, processor,
  * virtual processor, process, thread, data and abstract ancestry.  Device ports take part in
  * port connections like thread ports do. */
class TestFrontEnd_HardwareSubcomponents extends TestFrontEnd {

  val hardwareModel: String =
    st"""package HardwareSubcomponents {
        |    private import AADL::*;
        |
        |    part def Peripheral :> Device;
        |    part def Ram :> Memory;
        |    part def Wire :> Bus;
        |    part def Channel :> VirtualBus;
        |
        |    part def Root :> System {
        |        part peripheral : Peripheral;
        |        part ram : Ram;
        |        part wire : Wire;
        |        part channel : Channel;
        |    }
        |}
        |""".render

  /** A sensor device feeds a thread through its process, the thread drives an actuator device,
    * and the two devices are also connected directly.  extraConnections is spliced into Root. */
  def devicePortsModel(extraConnections: String): String =
    st"""package DevicePorts {
        |    private import AADL::*;
        |
        |    part def Sensor :> Device {
        |        port reading : DataPort { out :>> type : Base_Types::Integer_32; }
        |        out port tick : EventPort;
        |    }
        |
        |    part def Actuator :> Device {
        |        port cmd : EventDataPort { in :>> type : Base_Types::Integer_32; }
        |        in port tick : EventPort;
        |    }
        |
        |    part def Ctrl :> Thread {
        |        port reading : DataPort { in :>> type : Base_Types::Integer_32; }
        |        port cmd : EventDataPort { out :>> type : Base_Types::Integer_32; }
        |    }
        |
        |    part def CtrlProc :> Process {
        |        part ctrl : Ctrl;
        |
        |        port reading : DataPort { in :>> type : Base_Types::Integer_32; }
        |        port cmd : EventDataPort { out :>> type : Base_Types::Integer_32; }
        |
        |        connection readingIn : PortConnection
        |            connect reading to ctrl.reading;
        |
        |        connection cmdOut : PortConnection
        |            connect ctrl.cmd to cmd;
        |    }
        |
        |    part def Root :> System {
        |        part sensor : Sensor;
        |        part actuator : Actuator;
        |        part proc : CtrlProc;
        |
        |        connection sensorToProc : PortConnection
        |            connect sensor.reading to proc.reading;
        |
        |        connection procToActuator : PortConnection
        |            connect proc.cmd to actuator.cmd;
        |
        |        connection deviceToDevice : PortConnection
        |            connect sensor.tick to actuator.tick;
        |        $extraConnections
        |    }
        |}
        |""".render

  def typeCheck(content: String, uri: String, reporter: Reporter): ISZ[ModelUtil.ModelElements] = {
    val input = Input(content = content, fileUri = Some(uri))
    val (_, modelElements, _) = FrontEnd.typeCheck(par = par, inputs = libDefs :+ input, store = Map.empty, reporter = reporter)
    return modelElements
  }

  def root(modelElements: ISZ[ModelUtil.ModelElements]): ir.Component = {
    val roots = modelElements.filter(m => ops.StringOps(m.model.components(0).identifier.name(0)).startsWith("Root"))
    assert(roots.size == 1, modelElements.map(m => m.model.components(0).identifier.name).string)
    return roots(0).model.components(0)
  }

  def assertNoErrors(reporter: Reporter): Unit = {
    if (reporter.hasError) {
      reporter.printMessages()
    }
    assert(!reporter.hasError)
  }

  "device, memory, bus and virtual bus parts instantiate as subcomponents of a system" in {
    val reporter = Reporter.create
    val r = root(typeCheck(hardwareModel, "file:///hardware-subcomponents.sysml", reporter))
    assertNoErrors(reporter)

    val actual: ISZ[(String, ir.ComponentCategory.Type)] =
      for (s <- r.subComponents) yield (s.identifier.name(s.identifier.name.lastIndex), s.category)
    val expected: ISZ[(String, ir.ComponentCategory.Type)] = ISZ(
      (String("peripheral"), ir.ComponentCategory.Device),
      (String("ram"), ir.ComponentCategory.Memory),
      (String("wire"), ir.ComponentCategory.Bus),
      (String("channel"), ir.ComponentCategory.VirtualBus))
    assert(actual == expected, actual.string)
  }

  "device ports connect to thread ports through a process, and to other devices" in {
    val reporter = Reporter.create
    val r = root(typeCheck(devicePortsModel(""), "file:///device-ports.sysml", reporter))
    assertNoErrors(reporter)
    val rootName = r.identifier.name

    // a name relative to the root instance, e.g. "sensor.reading"
    def rel(name: ISZ[String]): String = {
      return st"${(ops.ISZOps(name).drop(rootName.size), ".")}".render
    }

    val devices = r.subComponents.filter(s => s.category == ir.ComponentCategory.Device)
    val deviceFeatures: ISZ[(String, ir.Direction.Type, ir.FeatureCategory.Type)] =
      for (d <- devices; f <- d.features) yield f match {
        case fe: ir.FeatureEnd => (rel(fe.identifier.name), fe.direction, fe.category)
        case _ => (rel(f.identifier.name), ir.Direction.None, ir.FeatureCategory.AbstractFeature)
      }
    val expectedDeviceFeatures: ISZ[(String, ir.Direction.Type, ir.FeatureCategory.Type)] = ISZ(
      (String("sensor.reading"), ir.Direction.Out, ir.FeatureCategory.DataPort),
      (String("sensor.tick"), ir.Direction.Out, ir.FeatureCategory.EventPort),
      (String("actuator.cmd"), ir.Direction.In, ir.FeatureCategory.EventDataPort),
      (String("actuator.tick"), ir.Direction.In, ir.FeatureCategory.EventPort))
    assert(deviceFeatures == expectedDeviceFeatures, deviceFeatures.string)

    // connection instances run end to end: through the process boundary to the thread
    val instances: ISZ[(String, String)] =
      for (ci <- r.connectionInstances) yield (rel(ci.src.feature.get.name), rel(ci.dst.feature.get.name))
    val expectedInstances: ISZ[(String, String)] = ISZ(
      (String("sensor.reading"), String("proc.ctrl.reading")),
      (String("proc.ctrl.cmd"), String("actuator.cmd")),
      (String("sensor.tick"), String("actuator.tick")))
    assert(HashSSet.empty[(String, String)] ++ instances == HashSSet.empty[(String, String)] ++ expectedInstances &&
      instances.size == expectedInstances.size, instances.string)
  }

  "a connection from a device's in port to a device's out port is rejected" in {
    val reporter = Reporter.create
    typeCheck(devicePortsModel(
      st"""
          |connection backwards : PortConnection
          |    connect actuator.tick to sensor.tick;""".render),
      "file:///device-ports-backwards.sysml", reporter)
    assert(reporter.hasError)
    assert(reporter.errors.filter(m => ops.StringOps(m.text).contains("Source must be outgoing and destination incoming")).nonEmpty,
      reporter.errors.string)
  }
}
