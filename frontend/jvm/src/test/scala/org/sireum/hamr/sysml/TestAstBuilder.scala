package sysml

import org.sireum._
import org.sireum.hamr.sysml.parser.SysMLParser
import org.sireum.message.Reporter
import org.sireum.test.TestSuite

class TestAstBuilder extends TestSuite {
  val resourceDir: Os.Path = Os.path(implicitly[sourcecode.File].value).up.up.up.up.up.up / "resources"

  val santosDir: Os.Path = resourceDir / "models" / "sysmlv2-models" / "omg"

  TestUtil.fetchSysmlLibrary(resourceDir)

  val filter: B = F
  val filters: ISZ[Os.Path => B] = ISZ(
    p => p.name.native == "Deployment_Properties.sysml"
  )

  //val modelsDir = resourceDir / "models" // include models that may not be in our subset of sysml
  val modelsDir = santosDir

  for (f <- Os.Path.walk(modelsDir, F, F, p =>
    (p.ext.native == "sysml" || p.ext.native == "kerml"))) {

    val testName = resourceDir.relativize(f).toString
    if (!filter || filters.elements.exists(e => e(f))) {
      registerTest(testName) {
        val reporter = Reporter.create
        println(s"Processing: ${f.toUri}")
        val ast = SysMLParser.parseH(Some(f.toUri), f.read, f.ext.native == "sysml", reporter)

        reporter.printMessages()
        assert(!reporter.hasError)
      }
    }
  }
}

