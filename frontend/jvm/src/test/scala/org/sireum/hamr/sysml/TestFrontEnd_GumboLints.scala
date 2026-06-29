package org.sireum.hamr.sysml

import org.sireum._
import org.sireum.message.Reporter

// Negative tests for the GUMBO lints that GclResolver enforces for the SysMLv2
// pipeline (which bypasses the OSATE/Xtext GumboValidator). Each model triggers
// exactly one placement lint; the test asserts the corresponding error is
// reported during front-end type checking.
class TestFrontEnd_GumboLints extends TestFrontEnd {

  def resolve(name: String): Reporter = {
    val root = w_internal_models / "gumbo" / "lints" / name
    assert(root.exists, root.value)
    val sysMlFiles = getSysmlFiles(root, F)
    val inputs = libDefs ++ (for (f <- sysMlFiles) yield toInput(f))
    val reporter = Reporter.create
    FrontEnd.typeCheck(par = par, inputs = inputs, store = Map.empty, reporter = reporter)
    return reporter
  }

  def expectError(name: String, expected: String): Unit = {
    val reporter = resolve(name)
    assert(reporter.hasError, s"$name: expected a resolution error but none was reported")
    val msgs: ISZ[String] = for (m <- reporter.messages) yield m.text
    val found = ops.ISZOps(msgs).exists((t: String) => ops.StringOps(t).contains(expected))
    assert(found,
      st"""$name: expected an error message containing:
          |  $expected
          |but got:
          |  ${(msgs, "\n  ")}""".render)
  }

  def expectNoError(name: String): Unit = {
    val reporter = resolve(name)
    assert(!reporter.hasError,
      st"""$name: expected resolution to succeed but got errors:
          |  ${(for (m <- reporter.messages if m.isError) yield m.text, "\n  ")}""".render)
  }

  "thread-invariants" in {
    expectError("thread-invariants", "Invariants cannot be attached to thread components")
  }

  "system-state" in {
    expectError("system-state", "State variables cannot be attached to system implementations")
  }

  "system-compute" in {
    expectError("system-compute", "Compute clauses cannot be attached to system implementations")
  }

  "composition-duplicate-alias" in {
    expectError("composition-duplicate-alias", "Duplicate alias name 'dup'")
  }

  "composition-duplicate-component-alias" in {
    expectError("composition-duplicate-component-alias", "Duplicate alias name 'a'")
  }

  "composition-bad-point" in {
    expectError("composition-bad-point", "'nonexistent_label' does not name a schema label")
  }

  "composition-cross-inconsistent" in {
    expectError("composition-cross-inconsistent", "an alias reused across compositions must refer to the same element")
  }

  "composition-cross-consistent" in {
    expectNoError("composition-cross-consistent")
  }
}
