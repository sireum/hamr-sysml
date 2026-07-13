package org.sireum.hamr.sysml

import org.sireum._
import org.sireum.hamr.codegen.common.util.HamrCli

class TestFrontEnd_SysMLv2_models extends TestFrontEnd {

  override def generateExpected: B = F || super.generateExpected

  override def verbose: B = F || super.verbose

  override def par: Z = super.par

  "temp-control-mixed" in {
    val root = w_hamrModelsDir / "temp-control" / "sysml-temp-control-mixed"
    assert (root.exists, root.value)
    println(s"Resolving: ${root.toUri}")
    test(ISZ("TempControlSystem_Instance"), ISZ(), root)
  }

  "temp-control-mixed-sel4-camkes" in {
    val root = w_hamrModelsDir / "temp-control" / "sysml-temp-control-mixed-sel4-camkes"
    assert (root.exists, root.value)
    println(s"Resolving: ${root.toUri}")
    test(ISZ("TempControlSystem_Instance"), ISZ(), root)
  }

  "temp-control-periodic" in {
    val root = w_hamrModelsDir / "temp-control" / "sysml-temp-control-periodic"
    assert (root.exists, root.value)
    println(s"Resolving: ${root.toUri}")
    test(ISZ("TempControlSystem_Instance"), ISZ(), root)
  }

  "rts" in {
    val root = w_hamrModelsDir / "sysml-rts"
    assert (root.exists, root.value)
    println(s"Resolving: ${root.toUri}")
    test(ISZ("RTS_Instance"), ISZ(), root)
  }

  "isolette" in {
    val root = w_hamrModelsDir / "sysml-isolette"
    assert (root.exists, root.value)
    println(s"Resolving: ${root.toUri}")
    test(ISZ("Isolette_Instance"), ISZ(), root)
  }

  "data-invariants" in {
    val root = w_internal_models / "gumbo" / "data-invariants"
    println(s"Resolving: ${root.toUri}")
    test(ISZ("s_impl_Instance"), ISZ(), root)
  }
}

object SysmlFrontEndTests {
  val baseOptions = HamrCli.CodegenOption(
    help = "",
    args = ISZ(),
    msgpack = F,
    verbose = F,
    runtimeMonitoring = F,
    platform = HamrCli.CodegenHamrPlatform.JVM,
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
    scheduling = HamrCli.CodegenScheduling.Domain,
    verusAttributeSyntax = F,
    sel4OutputDir = None(),
    sel4AuxCodeDirs = ISZ(),
    sel4AuxCodeSymlink = F,
    workspaceRootDir = None(),
    //
    strictAadlMode = F,
    ros2OutputWorkspaceDir = None(),
    ros2Dir = None(),
    ros2NodesLanguage = HamrCli.CodegenNodesCodeLanguage.Cpp,
    ros2LaunchLanguage = HamrCli.CodegenLaunchCodeLanguage.Xml,
    invertTopicBinding = F,
    //
    experimentalOptions = ISZ()
  )
}
