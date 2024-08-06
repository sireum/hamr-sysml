package org.sireum.hamr.sysml

import org.sireum._
import org.sireum.hamr.codegen.common.util.{CodeGenConfig, CodeGenIpcMechanism, CodeGenPlatform, ModelUtil}
import org.sireum.hamr.sysml.FrontEnd.Input
import org.sireum.hamr.sysml.instantiation.Instantiate
import org.sireum.hamr.sysml.stipe.{TypeChecker, TypeHierarchy, TypeOutliner}
import org.sireum.hamr.sysml.symbol.DelineableTypeInfo
import org.sireum.test.TestSuite
import org.sireum.hamr.sysml.symbol.Resolver._
import org.sireum.message.Reporter
import SysmlFrontEndTests._
import org.sireum.hamr.ir.instantiation.ConnectionInstantiator

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
    "Base_Types.sysml",
    "Deployment_Properties.sysml",
    "Thread_Properties.sysml",
    "Timing_Properties.sysml")) yield toInput(aadl_library / s)

  val sysLibDefs = {
    //val cands = sysmlLibrary.list.filter(p => ops.StringOps(p.name).startsWith("SysML-v2-Release-"))
    //assert(cands.size == 1, s"Found ${cands.size} sysml libraries: ${cands}")
    for (s <- ISZ(
      "Domain Libraries/Quantities and Units/ISQ.sysml",
      "Domain Libraries/Quantities and Units/SI.sysml",
      "Domain Libraries/Quantities and Units/MeasurementReferences.sysml",
      "Kernel Libraries/Kernel Data Type Library/ScalarValues.sysml",
      "Kernel Libraries/Kernel Semantic Library/Base.sysml",
      "Systems Library/Connections.sysml")) yield toInput(sysmlLibrary / s)
  }

  def toInput(o: Os.Path): Input = {
    return Input(content = o.read, fileUri = Some(o.toUri))
  }

  "temp-control-mixed-hybrid-omg" in {
    val root = omg_models / "temp-control-mixed-hybrid-omg" / "sysml"
    val files = Os.Path.walk(root, F, F, x => x.ext.native == "sysml")
    println(s"Resolving: ${root.toUri}")
    val inputs: ISZ[Input] = sysLibDefs ++ omgDefs ++ (for(r <- files) yield toInput(r))
    test(inputs)
  }

  "temp-control-periodic-hybrid-omg" in {
    val root = omg_models / "temp-control-periodic-hybrid-omg" / "sysml"
    val files = Os.Path.walk(root, F, F, x => x.ext.native == "sysml")
    println(s"Resolving: ${root.toUri}")
    val inputs: ISZ[Input] = sysLibDefs ++ omgDefs ++ (for(r <- files) yield toInput(r))
    test(inputs)
  }

  "rts-hybrid-omg" in {
    val root = omg_models / "rts-hybrid-omg" / "sysml"
    val files = Os.Path.walk(root, F, F, x => x.ext.native == "sysml")
    println(s"Resolving: ${root.toUri}")
    val inputs: ISZ[Input] = sysLibDefs ++ omgDefs ++ (for(r <- files) yield toInput(r))
    test(inputs)
  }

  "isolette-hybrid-omg" in {
    val root = omg_models / "isolette-hybrid-omg"
    val files = Os.Path.walk(root, F, F, x => x.ext.native == "sysml")
    println(s"Resolving: ${root.toUri}")
    val inputs: ISZ[Input] = sysLibDefs ++ omgDefs ++ (for(r <- files) yield toInput(r))
    test(inputs)
  }

  def test(inputs: ISZ[Input]): Unit = {
    var initNameMap: NameMap = HashSMap.empty
    var initTypeMap: TypeMap = HashSMap.empty

    val (reporter_, topUnits, globalNameMap, globalTypeMap) = FrontEnd.parseAndGloballyResolve(par, inputs, initNameMap, initTypeMap)

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

    report("Global Resolver", T)

    var th = TypeHierarchy.build(F, TypeHierarchy(globalNameMap, globalTypeMap, Poset.empty, HashSMap.empty), reporter)
    report("Type Hierarchy", T)

    th = TypeOutliner.checkOutline(par, th, reporter)
    report("Type Outliner", T)

    for (th <- th.typeMap.values) {
      th match {
        case i: DelineableTypeInfo =>
          assert (i.outlined, s"${i.name} was not outlined") // sanity check
        case _ =>
      }
    }

    th = TypeChecker.checkDefinitions(par, th, reporter)
    report("Type Checker", T)

    val iopts = Instantiate.instantiate(topUnits, th, reporter)

    report("Instantiator", T)

    iopts match {
      case Some(th_, models) =>
        th = th_
        for (model <- models) {
          ModelUtil.resolve(model, "model", baseOptions, reporter) match {
            case Some(modelElements) =>
              val c = ConnectionInstantiator.instantiateConnections(modelElements.model, reporter)
            case _ =>
          }

          report("AADL Model Resolution", T)
        }
      case _ =>
        halt("Infeasible")
    }
  }
}

object SysmlFrontEndTests {
  val baseOptions = CodeGenConfig(
    writeOutResources = T,
    ipc = CodeGenIpcMechanism.SharedMemory,

    runtimeMonitoring = F,
    verbose = F,
    platform = CodeGenPlatform.JVM,
    slangOutputDir = None(),
    packageName = None(),
    noProyekIve = T,
    noEmbedArt = F,
    devicesAsThreads = T,
    genSbtMill = T,
    slangAuxCodeDirs = ISZ(),
    slangOutputCDir = None(),
    excludeComponentImpl = F,
    bitWidth = 64,
    maxStringSize = 256,
    maxArraySize = 1,
    runTranspiler = F,
    camkesOutputDir = None(),
    camkesAuxCodeDirs = ISZ(),
    aadlRootDir = None(),
    experimentalOptions = ISZ()
  )
}
