package org.sireum.hamr.sysml

import org.sireum.hamr.ir.Aadl
import org.sireum.message.Reporter
import org.sireum.{F, Os, Some, println}
import org.sireum.test.TestSuite

class TestAstBuilder extends TestSuite {
  val resourceDir: Os.Path = Os.path(implicitly[sourcecode.File].value).up.up.up.up.up.up / "resources"

  val santosDir: Os.Path = resourceDir / "models" / "sysmlv2-models"

  TestUtil.fetchSysmlLibrary(resourceDir)

  for (f <- Os.Path.walk(santosDir, F, F, p => p.ext.native == "sysml" || p.ext.native == "kerml")) {
    registerTest(resourceDir.relativize(f).toString) {
      val reporter = Reporter.create
      println(s"Parsing ${f.toUri}")
      val model: Aadl = SysMLGrammar.parseH(Some(f.toUri), f.read, f.ext.native == "sysml", reporter)

      reporter.printMessages()
      assert(!reporter.hasError)
    }
  }
}

