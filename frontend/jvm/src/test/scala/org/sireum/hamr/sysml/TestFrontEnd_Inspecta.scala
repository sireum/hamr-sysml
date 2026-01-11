package org.sireum.hamr.sysml

import org.sireum._
import org.sireum.hamr.sysml.FrontEnd.Input

class TestFrontEnd_Inspecta extends TestFrontEnd {

  override def generateExpected: B = F || super.generateExpected

  override def verbose: B = F || super.verbose

  override def par: Z = super.par

  val hamrDir: Os.Path = Os.path(implicitly[sourcecode.File].value).up.up.up.up.up.up.up.up.up.up.up

  val codegenResourceDir: Os.Path = hamrDir / "codegen" / "jvm" / "src" / "test" / "resources"

  val inspectaModels: Os.Path = codegenResourceDir / "models" / "INSPECTA-models"

  if (!inspectaModels.exists) {
    println(st"""Inspecta models repo not found in codegen. Run one of codegen's Microkit tests in order to populate:
                |
                |  ${inspectaModels.toUri}""".render)
    assert (F)
  }

  val w_inspectaModels = w_aadl_sysml_workspace / "inspecta-models"

  if (!w_inspectaModels.exists) {
    val dest = inspectaModels
    println(s"Linking $w_inspectaModels to $dest")
    w_inspectaModels.mklink(dest)
  }

  val sysmlDirs: ISZ[Os.Path] = Os.Path.walk(w_inspectaModels, T, T, p => {
    var include = F
    if (p.isDir && ops.ISZOps(p.list).filter(f => f.ext.native == "sysml").nonEmpty) {
      val o = ops.StringOps(p.value)
      include = !o.contains("aadl.library")
    }
    include
  })

  //println(st"${(for (d <- sysmlDirs) yield d.toUri, "\n")}".render)

  "isolette" in {
    val root = w_inspectaModels / "isolette" / "sysml"
    assert (root.exists, root.value)
    val files = getSysmlFiles(root, F)
    println(s"Resolving: ${root.toUri}")
    val inputs: ISZ[Input] = libDefs ++ (for (r <- files) yield toInput(r))
    test(ISZ("Isolette_Single_Sensor_Instance"), inputs, root)
  }

  "structs_arrays" in {
    val root = w_inspectaModels / "micro-examples" / "microkit" / "gumbo-verus" / "structs_arrays" / "sysml"
    assert (root.exists, root.value)
    val files = getSysmlFiles(root, F)
    println(s"Resolving: ${root.toUri}")
    val inputs: ISZ[Input] = libDefs ++ (for (r <- files) yield toInput(r))
    test(ISZ("Sys_i_Instance"), inputs, root)
  }

  "firewall-simple" in {
    val root = w_inspectaModels / "open-platform-models" / "firewall-simple" / "sysmlv2"
    assert (root.exists, root.value)
    val files = getSysmlFiles(root, F)
    println(s"Resolving: ${root.toUri}")
    val inputs: ISZ[Input] = libDefs ++ (for (r <- files) yield toInput(r))
    test(ISZ("seL4_Instance"), inputs, root)
  }
}
