package org.sireum.hamr.sysml

import org.sireum._
import org.sireum.hamr.ir
import org.sireum.hamr.codegen.common.util.ModelUtil
import org.sireum.hamr.sysml.FrontEnd.Input
import org.sireum.message.Reporter

/** An allocation end may be a path through nested part usages ('allocate software.vm to cpu'):
  * the type checker resolves each segment through the part types, and instantiation attaches
  * the binding to the nested instance.  It used to fail an assertion in TypeChecker.resolvePart,
  * which only accepted a single name. */
class TestFrontEnd_NestedAllocation extends TestFrontEnd {

  def model(srcEnd: String): String =
    st"""package NestedAllocation {
        |    private import AADL::*;
        |
        |    part def VM :> VirtualProcessor;
        |    part def CPU :> Processor;
        |
        |    part def Software :> System {
        |        part vm : VM;
        |    }
        |
        |    part def Root :> System {
        |        part software : Software;
        |        part cpu : CPU;
        |
        |        allocation vm_binding : Deployment_Properties::Actual_Processor_Binding
        |            allocate $srcEnd to cpu;
        |    }
        |}
        |""".render

  def typeCheck(srcEnd: String, reporter: Reporter): ISZ[ModelUtil.ModelElements] = {
    val input = Input(content = model(srcEnd), fileUri = Some("file:///nested-allocation.sysml"))
    val (_, modelElements, _) = FrontEnd.typeCheck(par = par, inputs = libDefs :+ input, store = Map.empty, reporter = reporter)
    return modelElements
  }

  def allComponents(c: ir.Component): ISZ[ir.Component] = {
    var r = ISZ(c)
    for (s <- c.subComponents) {
      r = r ++ allComponents(s)
    }
    return r
  }

  val bindingName: String = "Deployment_Properties::Actual_Processor_Binding"

  "a nested allocation end binds the nested part" in {
    val reporter = Reporter.create
    val modelElements = typeCheck("software.vm", reporter)
    if (reporter.hasError) {
      reporter.printMessages()
    }
    assert(!reporter.hasError)

    val roots = modelElements.filter(m => ops.StringOps(m.model.components(0).identifier.name(0)).startsWith("Root"))
    assert(roots.size == 1, modelElements.map(m => m.model.components(0).identifier.name).string)
    val root = roots(0).model.components(0)
    val rootName = root.identifier.name

    val bound = for (c <- allComponents(root) if c.properties.filter(p => p.name.name == ISZ(bindingName)).nonEmpty) yield c
    assert(bound.size == 1, s"expected one bound component, found ${bound.map((c: ir.Component) => c.identifier.name)}")
    assert(bound(0).identifier.name == rootName ++ ISZ[String]("software", "vm"), bound(0).identifier.name.string)

    val prop = bound(0).properties.filter(p => p.name.name == ISZ(bindingName))(0)
    prop.propertyValues match {
      case ISZ(ref: ir.ReferenceProp) =>
        assert(ref.value.name == rootName :+ String("cpu"), ref.value.name.string)
      case x => assert(F, s"expected a single reference to the processor, found $x")
    }
  }

  "a nested allocation end naming no part usage is an error, not a crash" in {
    val reporter = Reporter.create
    typeCheck("software.nope", reporter)
    assert(reporter.hasError)
    assert(reporter.errors.filter(m => ops.StringOps(m.text).contains("'nope' in 'software.nope' is not a valid part usage")).nonEmpty,
      reporter.errors.string)
  }
}
