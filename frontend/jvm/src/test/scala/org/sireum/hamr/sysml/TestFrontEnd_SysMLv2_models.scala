package org.sireum.hamr.sysml

import org.sireum._
import org.sireum.hamr.sysml.FrontEnd.Input
import org.sireum.hamr.codegen.common.util.HamrCli.{CodegenHamrPlatform, CodegenLaunchCodeLanguage, CodegenNodesCodeLanguage, CodegenOption}

class TestFrontEnd_SysMLv2_models extends TestFrontEnd {

  override def generateExpected: B = F || super.generateExpected

  override def verbose: B = F || super.verbose

  override def par: Z = super.par

  "temp-control-mixed" in {
    val root = w_hamrModelsDir / "temp-control" / "sysml-temp-control-mixed"
    assert (root.exists, root.value)
    val files = getSysmlFiles(root, F)
    println(s"Resolving: ${root.toUri}")
    val inputs: ISZ[Input] = libDefs ++ (for (r <- files) yield toInput(r))
    test(ISZ("TempControlSystem_Instance"), inputs, root)
  }

  "temp-control-mixed-sel4-camkes" in {
    val root = w_hamrModelsDir / "temp-control" / "sysml-temp-control-mixed-sel4-camkes"
    assert (root.exists, root.value)
    val files = getSysmlFiles(root, F)
    println(s"Resolving: ${root.toUri}")
    val inputs: ISZ[Input] = libDefs ++ (for (r <- files) yield toInput(r))
    test(ISZ("TempControlSystem_Instance"), inputs, root)
  }

  "temp-control-periodic" in {
    val root = w_hamrModelsDir / "temp-control" / "sysml-temp-control-periodic"
    assert (root.exists, root.value)
    val files = getSysmlFiles(root, F)
    println(s"Resolving: ${root.toUri}")
    val inputs: ISZ[Input] = libDefs ++ (for (r <- files) yield toInput(r))
    test(ISZ("TempControlSystem_Instance"), inputs, root)
  }

  "rts" in {
    val root = w_hamrModelsDir / "sysml-rts"
    assert (root.exists, root.value)
    val files = getSysmlFiles(root, F)
    println(s"Resolving: ${root.toUri}")
    val inputs: ISZ[Input] = libDefs ++ (for (r <- files) yield toInput(r))
    test(ISZ("RTS_Instance"), inputs, root)
  }

  "isolette" in {
    val root = w_hamrModelsDir / "sysml-isolette"
    assert (root.exists, root.value)
    val files = getSysmlFiles(root, F)
    println(s"Resolving: ${root.toUri}")
    val inputs: ISZ[Input] = libDefs ++ (for (r <- files) yield toInput(r))
    test(ISZ("Isolette_Instance"), inputs, root)
  }

  "data-invariants" in {
    val root = w_internal_models / "gumbo" / "data-invariants"
    val files = getSysmlFiles(root, F)
    println(s"Resolving: ${root.toUri}")
    val inputs: ISZ[Input] = libDefs ++ (for (r <- files) yield toInput(r))
    test(ISZ("s_impl_Instance"), inputs, root)
  }
}

object SysmlFrontEndTests {
  val baseOptions = CodegenOption(
    help = "",
    args = ISZ(),
    msgpack = F,
    verbose = F,
    runtimeMonitoring = F,
    platform = CodegenHamrPlatform.JVM,
    outputDir = None(),
    parseableMessages = F,
    //
    slangOutputDir = None(),
    packageName = None(),
    noProyekIve = T,
    noEmbedArt = F,
    devicesAsThreads = T,
    genSbtMill = T,
    //
    slangAuxCodeDirs = ISZ(),
    slangOutputCDir = None(),
    excludeComponentImpl = F,
    bitWidth = 64,
    maxStringSize = 256,
    maxArraySize = 1,
    runTranspiler = F,
    //
    sel4OutputDir = None(),
    sel4AuxCodeDirs = ISZ(),
    workspaceRootDir = None(),
    //
    strictAadlMode = F,
    ros2OutputWorkspaceDir = None(),
    ros2Dir = None(),
    ros2NodesLanguage = CodegenNodesCodeLanguage.Cpp,
    ros2LaunchLanguage = CodegenLaunchCodeLanguage.Xml,
    invertTopicBinding = F,
    //
    experimentalOptions = ISZ()
  )
}
