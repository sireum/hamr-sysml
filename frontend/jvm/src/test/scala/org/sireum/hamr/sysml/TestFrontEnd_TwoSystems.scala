package org.sireum.hamr.sysml

import org.sireum._
import org.sireum.hamr.codegen.common.util.{ModelUtil, TimeUtil}
import org.sireum.message.Reporter

/** A time-rounding warning is reported once although the front end resolves every system and
  * codegen then resolves the chosen one again, with the same reporter (hamr-codegen#12,
  * doc/ExactTime-design.md D3) */
class TestFrontEnd_TwoSystems extends TestFrontEnd {

  def roundingWarnings(r: Reporter): Z = r.warnings.filter(m => m.kind == TimeUtil.timeRoundingKind).size

  "two-systems" in {
    val root = w_internal_models / "two-systems"
    val inputs = libDefs ++ (for (f <- getSysmlFiles(root, F)) yield toInput(f))

    val reporter = Reporter.create
    val (_, modelElements, store) = FrontEnd.typeCheck(par = par, inputs = inputs, store = Map.empty, reporter = reporter)
    if (reporter.hasError) {
      reporter.printMessages()
    }
    assert(!reporter.hasError)

    val second = modelElements.filter(m => m.model.components(0).identifier.name == ISZ[String]("second_impl_Instance"))
    assert(second.size == 1, modelElements.map(m => m.model.components(0).identifier.name).string)

    // the front end has resolved both systems; only the second has an inexact time
    assert(roundingWarnings(reporter) == 1, reporter.warnings.string)
    assert(ops.StringOps(reporter.warnings.filter(m => m.kind == TimeUtil.timeRoundingKind)(0).text).contains("second_impl_Instance.pp.t"))

    // as codegen does for the chosen system: resolve it again with the same reporter
    ModelUtil.resolve(second(0).model, second(0).modelPosOpt, "model", FrontEnd.baseOptions, store, reporter)
    assert(!reporter.hasError)
    assert(roundingWarnings(reporter) == 1, reporter.warnings.string)
  }
}
