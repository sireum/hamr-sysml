package org.sireum.hamr.sysml

import org.sireum.hamr.ir.Aadl
import org.sireum.message.Reporter
import org.sireum._
import org.sireum.test.TestSuite

class TestAstBuilder extends TestSuite {
  val resourceDir: Os.Path = Os.path(implicitly[sourcecode.File].value).up.up.up.up.up.up / "resources"

  val santosDir: Os.Path = resourceDir / "models" / "sysmlv2-models"

  TestUtil.fetchSysmlLibrary(resourceDir)

  val filter: B = F
  val filters: ISZ[String] = ISZ("AADL_")

  //val modelsDir = resourceDir / "models" // include models that may not be in our subset of sysml
  val modelsDir = santosDir

  for (f <- Os.Path.walk(modelsDir, F, F, p => (p.ext.native == "sysml" || p.ext.native == "kerml"))) {

    val testName = resourceDir.relativize(f).toString
    if (!filter || filters.elements.exists(e => ops.StringOps(testName).contains(e))) {
      registerTest(testName) {
        val reporter = Reporter.create
        println(s"Parsing ${f.toUri}")
        val model: Aadl = SysMLGrammar.parseH(Some(f.toUri), f.read, f.ext.native == "sysml", reporter)

        reporter.printMessages()
        assert(!reporter.hasError)
      }
    }
  }
}

