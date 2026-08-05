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

  "turtle-control-structure" in {
    val root = ros2ModelsDir / "turtle-control-structure"
    assert(root.exists, root.value)
    println(s"Resolving: ${root.toUri}")
    test(ISZ("TurtleControlSystem_Instance"), ISZ(), root)
  }

  "turtle-control-naming" in {
    val root = ros2ModelsDir / "turtle-control-naming"
    assert(root.exists, root.value)
    println(s"Resolving: ${root.toUri}")
    test(ISZ("TurtleControlSystem_Instance"), ISZ(), root)
  }


  val urosDemoDir: Os.Path = Os.home / "devel" / "microros" / "uros_demo" / "sysml"

  if(urosDemoDir.exists) {
    "uros-demo-structure" in {
      val root = urosDemoDir / "structure"
      assert(root.exists, root.value)
      println(s"Resolving: ${root.toUri}")
      test(ISZ("UrosDemoSystem_Instance"), ISZ(), root)
    }

    "uros-demo-naming" in {
      val root = urosDemoDir / "naming"
      assert(root.exists, root.value)
      println(s"Resolving: ${root.toUri}")
      test(ISZ("UrosDemoSystem_Instance"), ISZ(), root)
    }

    "uros-demo-gumbo" in {
      val root = urosDemoDir / "gumbo"
      assert(root.exists, root.value)
      println(s"Resolving: ${root.toUri}")
      test(ISZ("UrosDemoSystem_Instance"), ISZ(), root)
    }
  }
}