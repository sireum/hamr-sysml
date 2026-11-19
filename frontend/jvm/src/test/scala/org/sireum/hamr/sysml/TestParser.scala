package org.sireum.hamr.sysml

import org.sireum._
import org.sireum.hamr.sysml.parser.SysMLParser_Ext
import org.sireum.message.Reporter
import org.sireum.test.TestSuite

class TestParser extends TestSuite {
  val resourceDir: Os.Path = Os.path(implicitly[sourcecode.File].value).up.up.up.up.up.up / "resources"

  TestUtil.fetchSysmlLibrary(resourceDir)

  val exclude: ISZ[String] = ISZ(
    "AGREE-SysMLv2",
    // a syntax error on purpose: TestFrontEnd_GumboLints checks its message
    "gumbo/lints/keyword-as-parameter"
  )

  for (f <- Os.Path.walk(resourceDir / "models", T, T, p => p.ext.native == "sysml" || p.ext.native == "kerml")) {
    // match on the URI, whose separators are '/' on every OS (f.value uses '\' on Windows)
    if (!exclude.elements.exists(e => ops.StringOps(f.toUri).contains(e))) {
      registerTest(resourceDir.relativize(f).toString) {
        val reporter = Reporter.create
        println(s"Parsing ${f.toUri}")
        val tree = SysMLParser_Ext.parseSK(Some(f.toUri), f.read, f.ext.native == "sysml", reporter)

        reporter.printMessages()
        assert(!reporter.hasIssue)
      }
    }
  }
}

