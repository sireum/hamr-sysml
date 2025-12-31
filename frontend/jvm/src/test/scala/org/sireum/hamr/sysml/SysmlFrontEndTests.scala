package org.sireum.hamr.sysml

import org.sireum._
import org.sireum.hamr.sysml.FrontEnd.Input
import org.sireum.hamr.codegen.common.util.HamrCli.{CodegenHamrPlatform, CodegenLaunchCodeLanguage, CodegenNodesCodeLanguage, CodegenOption}
import org.sireum.message.Reporter
import org.sireum.test.TestSuite

class SysmlFrontEndTests extends TestSuite {

  val par: Z = 1
  val verbose: B = F

  val resourceDir: Os.Path = Os.path(implicitly[sourcecode.File].value).up.up.up.up.up.up / "resources"

  val sysmlv2ModelsDir: Os.Path = resourceDir / "models" / "sysmlv2-models"

  val sysmlv2Models: String = "https://github.com/santoslab/sysmlv2-models.git"


  val w_aadl_sysml_workspace: Os.Path = resourceDir / "models" / "aadl-sysml-workspace"

  val w_sysmlv2Models: Os.Path = w_aadl_sysml_workspace / "sysmlv2-models"

  val w_hamrModelsDir: Os.Path = w_aadl_sysml_workspace / "models"

  val w_internal_models = w_aadl_sysml_workspace / "internal"

  val internalModels = resourceDir / "models" / "internal"

  if (!sysmlv2ModelsDir.exists) {
    println(s"Cloning $sysmlv2Models to $sysmlv2ModelsDir")
    proc"git clone --rec $sysmlv2Models ${sysmlv2ModelsDir.name}".at(sysmlv2ModelsDir.up).runCheck()
  } else {
    proc"git pull --rec".at(sysmlv2ModelsDir.up).runCheck()
  }

  // check out the main/master branch for each submodule
  Os.proc(ISZ[String]("bash", "-c", "git submodule foreach 'git checkout main 2>/dev/null || git checkout master 2>/dev/null'")).at(sysmlv2ModelsDir).runCheck()

  w_aadl_sysml_workspace.mkdir()

  if (!w_sysmlv2Models.exists) {
    val dest = sysmlv2ModelsDir
    println(s"Linking $w_sysmlv2Models to $dest")
    w_sysmlv2Models.mklink(dest)
  }

  val w_sysml_aadl_libraries: Os.Path = w_sysmlv2Models / "sysml-aadl-libraries"
  val w_hamrModels: Os.Path = w_sysmlv2Models / "models"

  if (!w_internal_models.exists) {
    val dest = internalModels
    println(s"Linking $w_internal_models to $dest")
    w_internal_models.mklink(dest)
  }

  val libDefs: ISZ[Input] = for (s <- Os.Path.walk(w_sysml_aadl_libraries, T, T, p => p.ext == string"sysml")) yield toInput(s)

  def toInput(o: Os.Path): Input = {
    return Input(content = o.read, fileUri = Some(o.toUri))
  }

  "gumbo-structs-arrays" in {
    val root = w_hamrModelsDir / "temp-control" / "sysml-temp-control-mixed"
    val files = Os.Path.walk(root, F, F, x => x.ext.native == "sysml")
    println(s"Resolving: ${root.toUri}")
    val inputs: ISZ[Input] = libDefs ++ (for (r <- files) yield toInput(r))
    test(inputs)
  }

  "temp-control-mixed" in {
    val root = w_hamrModelsDir / "temp-control" / "sysml-temp-control-mixed"
    val files = Os.Path.walk(root, F, F, x => x.ext.native == "sysml")
    println(s"Resolving: ${root.toUri}")
    val inputs: ISZ[Input] = libDefs ++ (for (r <- files) yield toInput(r))
    test(inputs)
  }

  "temp-control-mixed-sel4-camkes" in {
    val root = w_hamrModelsDir / "temp-control" / "sysml-temp-control-mixed-sel4-camkes"
    val files = Os.Path.walk(root, F, F, x => x.ext.native == "sysml")
    println(s"Resolving: ${root.toUri}")
    val inputs: ISZ[Input] = libDefs ++ (for (r <- files) yield toInput(r))
    test(inputs)
  }

  "temp-control-periodic" in {
    val root = w_hamrModelsDir / "temp-control" / "sysml-temp-control-periodic"
    val files = Os.Path.walk(root, F, F, x => x.ext.native == "sysml")
    println(s"Resolving: ${root.toUri}")
    val inputs: ISZ[Input] = libDefs ++ (for (r <- files) yield toInput(r))
    test(inputs)
  }

  "rts" in {
    val root = w_hamrModelsDir / "sysml-rts"
    val files = Os.Path.walk(root, F, F, x => x.ext.native == "sysml")
    println(s"Resolving: ${root.toUri}")
    val inputs: ISZ[Input] = libDefs ++ (for (r <- files) yield toInput(r))
    test(inputs)
  }

  "isolette" in {
    val root = w_hamrModelsDir / "sysml-isolette"
    val files = Os.Path.walk(root, F, F, x => x.ext.native == "sysml")
    println(s"Resolving: ${root.toUri}")
    val inputs: ISZ[Input] = libDefs ++ (for (r <- files) yield toInput(r))
    test(inputs)
  }

  "data-invariants" in {
    val root = w_internal_models / "gumbo" / "data-invariants"
    val files = Os.Path.walk(root, F, F, x => x.ext.native == "sysml")
    println(s"Resolving: ${root.toUri}")
    val inputs: ISZ[Input] = libDefs ++ (for (r <- files) yield toInput(r))
    test(inputs)
  }

  def test(inputs: ISZ[Input]): Unit = {

    val reporter = Reporter.create
    val (sysmlTh, modelElements, store) = FrontEnd.typeCheck(par = par, inputs = inputs, store = Map.empty, reporter = reporter)

    if (!reporter.hasError) {

      val integerationConstraints = FrontEnd.getIntegerationConstraints(modelElements, reporter)

      if (!TestUtil.isCI) {
        for (i <- integerationConstraints) {
          var conns: ISZ[ST] = ISZ()
          for (c <- i.connections) {
            val refs: ISZ[ST] = for (r <- c.connectionReferences.entries) yield st"[${(r._1, ".")}, ${r._2}]"

            val connCon: Option[ST] = (c.srcConstraint, c.dstConstraint) match {
              case (Some(s), Some(d)) =>
                Some(
                  st"""
                      | $s __>:
                      |   $d""")
              case (None(), Some(d)) =>
                Some(
                  st"""
                      | <nil> __>:
                      |   $d""")
              case (Some(s), None()) =>
                Some(
                  st"""
                      | $s __>:
                      |   <nil>""")
              case _ => None()
            }

            conns = conns :+
              st"""Connection Instance: ${(c.srcPort.path, ".")} -> ${(c.dstPort.path, ".")}
                  |
                  |  Connection References: ${(refs, " -> ")}
                  |  $connCon
                  |"""
          }
          println(
            st"""System Root: ${(i.systemRootName, "::")} [${i.systemRootPos}]
                |
                |${(conns, "\n")}""".render)
        }
      }
    }

    if (reporter.hasError) {
      reporter.printMessages()
      assert(F)
    }
    if (verbose && reporter.messages.nonEmpty) {
      reporter.printMessages()
    }
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
