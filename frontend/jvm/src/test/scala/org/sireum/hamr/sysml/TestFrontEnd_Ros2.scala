package org.sireum.hamr.sysml

import org.sireum._

class TestFrontEnd_Ros2 extends TestFrontEnd {

  override def generateExpected: B = F || super.generateExpected

  override def verbose: B = F || super.verbose

  override def par: Z = super.par

  val hamrDir: Os.Path = Os.path(implicitly[sourcecode.File].value).up.up.up.up.up.up.up.up.up.up.up

  val ros2ModelsDir: Os.Path = hamrDir / "codegen" / "jvm" / "src" / "test" / "resources" / "models" / "Ros2"

  if (!ros2ModelsDir.exists) {
    println(s"Ros2 models dir not found: ${ros2ModelsDir.toUri}")
    assert(F)
  }

  "sysml-temp-control-mixed-microros" in {
    val root = ros2ModelsDir / "sysml-temp-control-mixed-microros"
    assert(root.exists, root.value)
    println(s"Resolving: ${root.toUri}")
    test(ISZ("TempControlSystem_Instance"), ISZ(), root)
  }
}