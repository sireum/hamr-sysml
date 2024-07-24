package org.sireum.hamr.sysml

import org.sireum._
import org.sireum.hamr.sysml.FrontEnd.Input
import org.sireum.hamr.sysml.stipe.{TypeChecker, TypeHierarchy, TypeOutliner}
import org.sireum.hamr.sysml.symbol.DelineableTypeInfo
import org.sireum.test.TestSuite
import org.sireum.hamr.sysml.symbol.Resolver._
import org.sireum.message.Reporter

class SysmlFrontEndTests extends TestSuite {

  val par: Z = 1

  val resourceDir: Os.Path = Os.path(implicitly[sourcecode.File].value).up.up.up.up.up.up / "resources"
  val omgDir = resourceDir / "models" / "sysmlv2-models" / "omg"
  val aadl_library = omgDir / "aadl.library"
  val omg_models = omgDir / "models"
  //val sysmlLibrary = resourceDir / "models" / "sysml.library"
  val sysmlLibrary = omgDir / "mock.sysml.library"

  val omgDefs = for (s <- ISZ(
    "AADL.sysml",
    "AADL_Project.sysml",
    "AADL_Base_Types.sysml",
    "Deployment_Properties.sysml",
    "Thread_Properties.sysml",
    "Timing_Properties.sysml")) yield toInput(aadl_library / s)

  val sysLibDefs = {
    //val cands = sysmlLibrary.list.filter(p => ops.StringOps(p.name).startsWith("SysML-v2-Release-"))
    //assert(cands.size == 1, s"Found ${cands.size} sysml libraries: ${cands}")
    for (s <- ISZ(
      "Domain Libraries/Quantities and Units/ISQ.sysml",
      "Kernel Libraries/Kernel Data Type Library/ScalarValues.sysml",
      "Kernel Libraries/Kernel Semantic Library/Base.sysml",
      "Systems Library/Connections.sysml")) yield toInput(sysmlLibrary / s)
  }

  def toInput(o: Os.Path): Input = {
    return Input(content = o.read, fileUri = Some(o.toUri))
  }

  "temp-control" in {
    val input = omg_models / "temp-control-mixed-hybrid" / "sysml" / "TempControlAadl.sysml"
    println(s"Resolving: ${input.toUri}")
    val inputs: ISZ[Input] = sysLibDefs ++ omgDefs :+ toInput(input)
    test(inputs)
  }

  "RTS-sysml" in {
    val root = omg_models / "RTS-sysml" / "sysml"
    val rtsFiles = Os.Path.walk(root, F, F, x => x.ext.native == "sysml")

    println(s"Resolving: ${root.toUri}")
    val inputs: ISZ[Input] = sysLibDefs ++ omgDefs ++ (for(r <- rtsFiles) yield toInput(r))
    test(inputs)
  }

  def test(inputs: ISZ[Input]): Unit = {
    var initNameMap: NameMap = HashSMap.empty
    var initTypeMap: TypeMap = HashSMap.empty

    val (reporter_, topUnits, globalNameMap, globalTypeMap) = FrontEnd.parseAndGloballyResolve(1, inputs, initNameMap, initTypeMap)

    var reporter = reporter_
    def report(stage: String, verbose: B): Unit = {
      if (reporter.hasError) {
        println(s"Errors from $stage")
        val r = Reporter.create
        r.reports(reporter.errors)
        r.printMessages()
        assert(F)
      }
      if (verbose && reporter.messages.nonEmpty) {
        println(s"Messages from $stage")
        reporter.printMessages()
        println(s"####################################################################")
      }
      reporter = Reporter.create
    }

    report("Global Resolver", F)

    var th = TypeHierarchy.build(F, TypeHierarchy(globalNameMap, globalTypeMap, Poset.empty, HashSMap.empty), reporter)
    report("Type Hierarchy", T)

    th = TypeOutliner.checkOutline(par, th, reporter)
    report("Type Outliner", T)

    for (th <- th.typeMap.values) {
      th match {
        case i: DelineableTypeInfo =>
          assert (i.outlined) // sanity check
        case _ =>
      }
    }

    th = TypeChecker.checkDefinitions(par, th: TypeHierarchy, reporter)
    report("Type Checker", T)

  }
}
