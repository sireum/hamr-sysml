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
  val verbose: B = F

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


  def toInput(o: Os.Path): Input = {
    return Input(content = o.read, fileUri = Some(o.toUri))
  }

  "temp-control-mixed-hybrid-omg" in {
    val root = omg_models / "temp-control-mixed-hybrid-omg" / "sysml"
    val files = Os.Path.walk(root, F, F, x => x.ext.native == "sysml")
    println(s"Resolving: ${root.toUri}")
    val inputs: ISZ[Input] = omgDefs ++ (for(r <- files) yield toInput(r))
    test(inputs)
  }

  "temp-control-periodic-hybrid-omg" in {
    val root = omg_models / "temp-control-periodic-hybrid-omg" / "sysml"
    val files = Os.Path.walk(root, F, F, x => x.ext.native == "sysml")
    println(s"Resolving: ${root.toUri}")
    val inputs: ISZ[Input] = omgDefs ++ (for(r <- files) yield toInput(r))
    test(inputs)
  }

  "rts-hybrid-omg" in {
    val root = omg_models / "rts-hybrid-omg" / "sysml"
    val files = Os.Path.walk(root, F, F, x => x.ext.native == "sysml")
    println(s"Resolving: ${root.toUri}")
    val inputs: ISZ[Input] = omgDefs ++ (for(r <- files) yield toInput(r))
    test(inputs)
  }

  "isolette-hybrid-omg" in {
    val root = omg_models / "isolette-hybrid-omg"
    val files = Os.Path.walk(root, F, F, x => x.ext.native == "sysml")
    println(s"Resolving: ${root.toUri}")
    val inputs: ISZ[Input] = omgDefs ++ (for(r <- files) yield toInput(r))
    test(inputs)
  }

  def test(inputs: ISZ[Input]): Unit = {

    val reporter = Reporter.create
    val (th, modelElements) = FrontEnd.typeCheck(par = par, inputs = inputs, reporter = reporter)

    if (reporter.hasError) {
      reporter.printMessages()
      assert (F)
    }
    if (verbose && reporter.messages.nonEmpty) {
      reporter.printMessages()
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
