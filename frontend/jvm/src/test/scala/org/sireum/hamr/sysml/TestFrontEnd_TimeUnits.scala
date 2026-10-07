package org.sireum.hamr.sysml

import org.sireum._
import org.sireum.hamr.ir
import org.sireum.message.Reporter

/** Checks that the front end converts every HAMR_Time_Units literal to picoseconds exactly
  * (hamr-codegen#12: microseconds were multiplied by 1.06 instead of 1.0E6) */
class TestFrontEnd_TimeUnits extends TestFrontEnd {

  "time-units" in {
    val root = w_internal_models / "time-units"
    val inputs = libDefs ++ (for (f <- getSysmlFiles(root, F)) yield toInput(f))

    val reporter = Reporter.create
    val (_, modelElements, _) = FrontEnd.typeCheck(par = par, inputs = inputs, store = Map.empty, reporter = reporter)
    if (reporter.hasError) {
      reporter.printMessages()
    }
    assert(!reporter.hasError)

    val models = modelElements.filter(m => m.model.components(0).identifier.name == ISZ[String]("top_impl_Instance"))
    assert(models.size == 1, modelElements.map(m => m.model.components(0).identifier.name).string)

    def allComponents(c: ir.Component): ISZ[ir.Component] = {
      return c +: (for (s <- c.subComponents; d <- allComponents(s)) yield d)
    }

    // each thread t_<unit> has a period of 2 <unit>
    val expectedPs: Map[String, R] = Map.empty[String, R] ++ ISZ[(String, R)](
      ("t_ps", R("2").get),
      ("t_ns", R("2000").get),
      ("t_us", R("2000000").get),
      ("t_ms", R("2000000000").get),
      ("t_s", R("2000000000000").get))

    var checked: ISZ[String] = ISZ()
    for (c <- allComponents(models(0).model.components(0)) if c.category == ir.ComponentCategory.Thread) {
      val thread = c.identifier.name(c.identifier.name.size - 1)
      // matched as codegen does (PropertyUtil.getPropertyValues): the qualified name is the last element
      val periods = c.properties.filter(p => p.name.name(p.name.name.size - 1) == string"Timing_Properties::Period")
      assert(periods.size == 1, s"$thread: ${periods.size} Period properties")
      periods(0).propertyValues match {
        case ISZ(ir.UnitProp(value, unit)) =>
          assert(unit == Some(string"ps"), s"$thread: unit $unit")
          assert(R(value).get == expectedPs.get(thread).get, s"$thread: $value ps, expected ${expectedPs.get(thread).get}")
        case x => assert(F, s"$thread: unexpected Period value $x")
      }
      checked = checked :+ thread
    }
    assert(checked.size == expectedPs.size, checked.string)
  }
}
