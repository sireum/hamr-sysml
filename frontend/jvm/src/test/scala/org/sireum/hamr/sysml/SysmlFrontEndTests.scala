package org.sireum.hamr.sysml

import org.sireum._
import org.sireum.hamr.sysml.FrontEnd.Input
import org.sireum.hamr.codegen.common.util.HamrCli.{CodegenHamrPlatform, CodegenLaunchCodeLanguage, CodegenNodesCodeLanguage, CodegenOption}
import org.sireum.message.Reporter
import org.sireum.test.TestSuite
import sysml.TestUtil

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

  val propertySets = for (s <- ISZ(
    "CASE_Scheduling.sysml")) yield toInput(omgDir / "aadl.property.sets" / s)

  def toInput(o: Os.Path): Input = {
    return Input(content = o.read, fileUri = Some(o.toUri))
  }

  "temp-control-case-omg" in {
    val root = omg_models / "temp-control-case-omg"
    val files = Os.Path.walk(root, F, F, x => x.up.name != string"aadl.library" && x.ext.native == "sysml")
    println(s"Resolving: ${root.toUri}")
    val inputs: ISZ[Input] = omgDefs ++ propertySets ++ (for(r <- files) yield toInput(r))
    test(inputs)
  }

  "temp-control-mixed-hybrid-omg" in {
    val root = omg_models / "temp-control-mixed-hybrid-omg" / "sysml"
    val files = Os.Path.walk(root, F, F, x => x.ext.native == "sysml")
    println(s"Resolving: ${root.toUri}")
    val inputs: ISZ[Input] = omgDefs ++ propertySets ++ (for(r <- files) yield toInput(r))
    test(inputs)
  }

  "temp-control-periodic-hybrid-omg" in {
    val root = omg_models / "temp-control-periodic-hybrid-omg" / "sysml"
    val files = Os.Path.walk(root, F, F, x => x.ext.native == "sysml")
    println(s"Resolving: ${root.toUri}")
    val inputs: ISZ[Input] = omgDefs ++ propertySets ++ (for(r <- files) yield toInput(r))
    test(inputs)
  }

  "rts-hybrid-omg" in {
    val root = omg_models / "rts-hybrid-omg" / "sysml"
    val files = Os.Path.walk(root, F, F, x => x.ext.native == "sysml")
    println(s"Resolving: ${root.toUri}")
    val inputs: ISZ[Input] = omgDefs ++ propertySets ++ (for(r <- files) yield toInput(r))
    test(inputs)
  }

  "isolette-hybrid-omg" in {
    val root = omg_models / "isolette-hybrid-omg"
    val files = Os.Path.walk(root, F, F, x => x.ext.native == "sysml")
    println(s"Resolving: ${root.toUri}")
    val inputs: ISZ[Input] = omgDefs ++ propertySets ++ (for(r <- files) yield toInput(r))
    test(inputs)
  }

  val gumbo_models: Os.Path = resourceDir / "models" / "internal" / "gumbo"

  "data-invariants" in {
    val root = gumbo_models / "data-invariants"
    val files = Os.Path.walk(root, F, F, x => x.ext.native == "sysml")
    println(s"Resolving: ${root.toUri}")
    val inputs: ISZ[Input] = omgDefs ++ propertySets ++ (for(r <- files) yield toInput(r))
    test(inputs)
  }

  def test(inputs: ISZ[Input]): Unit = {

    val reporter = Reporter.create
    val (sysmlTh, modelElements) = FrontEnd.typeCheck(par = par, inputs = inputs, reporter = reporter)

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
                      | $s ->:
                      |   $d""")
              case (None(), Some(d)) =>
                Some(
                  st"""
                      | <nil> ->:
                      |   $d""")
              case (Some(s), None()) =>
                Some(
                  st"""
                      | $s ->:
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
      assert (F)
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
    camkesOutputDir = None(),
    camkesAuxCodeDirs = ISZ(),
    workspaceRootDir = None(),
    //
    strictAadlMode = F,
    ros2OutputWorkspaceDir = None(),
    ros2Dir = None(),
    ros2NodesLanguage = CodegenNodesCodeLanguage.Cpp,
    ros2LaunchLanguage = CodegenLaunchCodeLanguage.Xml,
    //
    experimentalOptions = ISZ()
  )
}
