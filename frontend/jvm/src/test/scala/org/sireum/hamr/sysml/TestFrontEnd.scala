package org.sireum.hamr.sysml

import org.sireum.{Either, _}
import org.sireum.hamr.sysml.FrontEnd.Input
import org.sireum.hamr.ir.{Aadl, JSON => irJSON}
import org.sireum.message.Reporter
import org.sireum.test.TestSuite

abstract class TestFrontEnd extends TestSuite {

  @pure def generateExpected: B = F

  @pure def verbose: B = F

  @pure def par: Z = 0

  val resourceDir: Os.Path = Os.path(implicitly[sourcecode.File].value).up.up.up.up.up.up / "resources"


  val sysmlv2ModelsDir: Os.Path = resourceDir / "models" / "sysmlv2-models"

  val sysmlv2Models: String = "https://github.com/santoslab/sysmlv2-models.git"


  val w_aadl_sysml_workspace: Os.Path = resourceDir / "models" / "aadl-sysml-workspace"

  val w_sysmlv2Models: Os.Path = w_aadl_sysml_workspace / "sysmlv2-models"

  val w_hamrModelsDir: Os.Path = w_sysmlv2Models / "models"


  val w_internal_models: Os.Path = w_aadl_sysml_workspace / "internal"

  val internalModels: Os.Path = resourceDir / "models" / "internal"

  if (!sysmlv2ModelsDir.exists) {
    println(s"Cloning $sysmlv2Models to $sysmlv2ModelsDir")
    proc"git clone --rec $sysmlv2Models ${sysmlv2ModelsDir.name}".at(sysmlv2ModelsDir.up).runCheck()
  }

  w_aadl_sysml_workspace.mkdir()

  if (!w_sysmlv2Models.exists) {
    val dest = sysmlv2ModelsDir
    println(s"Linking $w_sysmlv2Models to $dest")
    w_sysmlv2Models.mklink(dest)
  }

  val w_sysml_aadl_libraries: Os.Path = w_sysmlv2Models / "sysml-aadl-libraries"
  val w_hamrModels: Os.Path = w_sysmlv2Models / "models"

  assert(w_sysml_aadl_libraries.exists, w_sysml_aadl_libraries.value)
  assert(w_hamrModels.exists, w_hamrModels.value)

  if (!w_internal_models.exists) {
    val dest = internalModels
    println(s"Linking $w_internal_models to $dest")
    w_internal_models.mklink(dest)
  }

  val libDefs: ISZ[Input] = for (f <- getSysmlFiles(w_sysml_aadl_libraries, T)) yield toInput(f)

  def getSysmlFiles(root: Os.Path, includeAadlLibraries: B): ISZ[Os.Path] = {
    val files = for (f <- Os.Path.walk(root, T, T, x => x.ext.native == "sysml")) yield f.canon
    if (!includeAadlLibraries)
      return ops.ISZOps(files).filter(p => !ops.StringOps(p.value).contains("aadl.library"))
    else
      return files
  }

  def toInput(o: Os.Path): Input = {
    return Input(content = o.read, fileUri = Some(o.toUri))
  }

  def test(instanceName: ISZ[String], inputs: ISZ[Input], root: Os.Path): Unit = {

    val reporter = Reporter.create
    val (sysmlTh, modelElements, store) = FrontEnd.typeCheck(par = par, inputs = inputs, store = Map.empty, reporter = reporter)

    if (!reporter.hasError) {

      assert(root.exists, root.value)
      assert(modelElements.size.nonEmpty)

      Os.env("USER") match {
        case Some(string"belt") =>

          val aadlModel: Aadl = {
            var model: Option[Aadl] = None()
            var avail: ISZ[String] = ISZ()
            for (m <- modelElements) {
              assert(m.model.components.size == 1)
              avail = avail :+ st"${(m.model.components(0).identifier.name, "::")}".render
              if (m.model.components(0).identifier.name == instanceName) {
                model = Some(m.model)
              }
            }
            if (model.nonEmpty) model.get
            else halt(
              st"""Didn't locate instance model for $instanceName in $root. Avaliable instances:
                  |  ${(avail, "\n")}""".render)
          }

          val slangDir = root / ".slang"

          val airPath = (slangDir / st"${(aadlModel.components(0).identifier.name, "_")}.json".render).canon

          val resultAirContent = irJSON.fromAadl(aadlModel, F)

          if (generateExpected) {
            airPath.writeOver(resultAirContent)
            airPath.up.mkdirAll()
            println(s"Wrote: $airPath")
          } else {
            assert(airPath.exists, s"AIR filed doesn't exist: ${airPath.value}")

            irJSON.toAadl(airPath.read) match {
              case Either.Left(expectedModel) =>
                val scrubbedExpectedModel = scrub(expectedModel)
                val scrubbedResultModel = scrub(aadlModel)

                if (scrubbedResultModel != scrubbedExpectedModel) {
                  val expectedAirPath = slangDir / st"${(aadlModel.components(0).identifier.name, "_")}_expected.json".render
                  expectedAirPath.writeOver(irJSON.fromAadl(scrubbedExpectedModel, F))

                  val resultAirPath = slangDir / st"${(aadlModel.components(0).identifier.name, "_")}_result.json".render
                  resultAirPath.writeOver(irJSON.fromAadl(scrubbedResultModel, F))

                  val gitIgnore = slangDir / ".gitignore"
                  val gicontent = ops.StringOps(gitIgnore.read)
                  var add: Option[ST] = None()
                  if (!gicontent.contains("*_result*")) {
                    add = Some(
                      st"""$add
                          |*_result*""")
                  }
                  if (!gicontent.contains("*_expected*")) {
                    add = Some(
                      st"""$add
                          |*_expected*""")
                  }
                  if (add.nonEmpty) {
                    gitIgnore.writeOver(
                      st"""${gicontent.s}
                          |$add""".render)
                  }

                  println(s"Testing Dir: ${slangDir.toUri}")
                  assert(F, "Expected AIR contents did not match the results")
                }
              case Either.Right(m) =>
                assert(F, m)
            }
          }

          val integerationConstraints = FrontEnd.getIntegerationConstraints(modelElements, reporter)

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
            val sysRoot = st"${(i.systemRootName, "::")}".render

            val content =
              st"""System Root: $sysRoot [${i.systemRootPos}]
                  |
                  |${(conns, "\n")}""".render

            val expectedConstraintPath = slangDir / s"integration_$sysRoot.txt"

            if (generateExpected) {
              expectedConstraintPath.writeOver(content)
              println(s"Wrote: $expectedConstraintPath")
            } else {
              assert(expectedConstraintPath.exists, expectedConstraintPath.value)

              val expectedContent = expectedConstraintPath.read

              if (expectedContent != content) {
                val resultsConstraintPath = slangDir / s"integration_${sysRoot}_results.txt"
                resultsConstraintPath.writeOver(content)

                println(s"Testing Dir: ${slangDir.toUri}")
                assert(F, "Expected integration constraints did not match the results")
              }
            }
          }
        case _ =>
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

  val m = TestUtil.AirScrubber()

  def scrub(model: Aadl): Aadl = {
    return m.transformAadl(model).get
  }
}
