// #Sireum

package org.sireum.hamr.sysml

import org.sireum._

// the following can be used when constructing command line arguments for sysmlCodegen
// from an external tool (e.g. OSATE).

object LongKeys {
  val sourcepath: String = "--sourcepath"
  val line: String = "--line"
  val system: String = "--system-name"
  val verbose: String = "--verbose"
  val runtimeMonitoring: String = "--runtime-monitoring"
  val platform: String = "--platform"
  val parseableMessages: String = "--parseable-messages"
  val Slang_slangOutputDir: String = "--slang-output-dir"
  val Slang_packageName: String = "--package-name"
  val Slang_noProyekIve: String = "--no-proyek-ive"
  val Slang_noEmbedArt: String = "--no-embed-art"
  val Slang_devicesAsThreads: String = "--devices-as-thread"
  val Slang_genSbtMill: String = "--sbt-mill"
  val Transpiler_slangAuxCodeDirs: String = "--aux-code-dirs"
  val Transpiler_slangOutputCDir: String = "--output-c-dir"
  val Transpiler_excludeComponentImpl: String = "--exclude-component-impl"
  val Transpiler_bitWidth: String = "--bit-width"
  val Transpiler_maxStringSize: String = "--max-string-size"
  val Transpiler_maxArraySize: String = "--max-array-size"
  val Transpiler_runTranspiler: String = "--run-transpiler"
  val CAmkES_camkesOutputDir: String = "--camkes-output-dir"
  val CAmkES_camkesAuxCodeDirs: String = "--camkes-aux-code-dirs"
  val CAmkES_workspaceRootDir: String = "--workspace-root-dir"
  val ROS2_strictAadlMode: String = "--strict-aadl-mode"
  val ROS2_ros2OutputWorkspaceDir: String = "--ros2-output-workspace-dir"
  val ROS2_ros2Dir: String = "--ros2-dir"
  val ROS2_ros2NodesLanguage: String = "--ros2-nodes-language"
  val ROS2_ros2LaunchLanguage: String = "--ros2-launch-language"
  val Experimental_experimentalOptions: String = "--experimental-options"

  val allKeys: Set[String] = Set.empty[String] ++ ISZ(sourcepath, line, system, verbose, runtimeMonitoring, platform, parseableMessages, Slang_slangOutputDir, Slang_packageName, Slang_noProyekIve, Slang_noEmbedArt, Slang_devicesAsThreads, Slang_genSbtMill, Transpiler_slangAuxCodeDirs, Transpiler_slangOutputCDir, Transpiler_excludeComponentImpl, Transpiler_bitWidth, Transpiler_maxStringSize, Transpiler_maxArraySize, Transpiler_runTranspiler, CAmkES_camkesOutputDir, CAmkES_camkesAuxCodeDirs, CAmkES_workspaceRootDir, ROS2_strictAadlMode, ROS2_ros2OutputWorkspaceDir, ROS2_ros2Dir, ROS2_ros2NodesLanguage, ROS2_ros2LaunchLanguage, Experimental_experimentalOptions)

  @strictpure def sameKeys(keys: ISZ[String]): B = allKeys.elements == keys

  // Paste the following into a java program if you want to ensure the known keys match.
  // To regenerate this, run '$SIREUM_HOME/hamr/codegen/build.cmd regen-cli'.
  // If this does fail then the CLI arguments being constructed for codegen will need
  // to be updated (that could be delayed if only new options were added).

  // scala.collection.Seq<org.sireum.String> seq = scala.jdk.javaapi.CollectionConverters.asScala(new java.util.ArrayList<org.sireum.String>());
  // scala.collection.immutable.Seq<org.sireum.String> iseq = ((scala.collection.IterableOnceOps<org.sireum.String, ?, ?>) seq).toSeq();
  // org.sireum.IS<org.sireum.Z, org.sireum.String> knownKeys = org.sireum.IS$.MODULE$.apply(iseq, org.sireum.Z$.MODULE$).$colon$plus(new org.sireum.String(org.sireum.hamr.sysml.LongKeys.sourcepath())).$colon$plus(new org.sireum.String(org.sireum.hamr.sysml.LongKeys.line())).$colon$plus(new org.sireum.String(org.sireum.hamr.sysml.LongKeys.system())).$colon$plus(new org.sireum.String(org.sireum.hamr.sysml.LongKeys.verbose())).$colon$plus(new org.sireum.String(org.sireum.hamr.sysml.LongKeys.runtimeMonitoring())).$colon$plus(new org.sireum.String(org.sireum.hamr.sysml.LongKeys.platform())).$colon$plus(new org.sireum.String(org.sireum.hamr.sysml.LongKeys.parseableMessages())).$colon$plus(new org.sireum.String(org.sireum.hamr.sysml.LongKeys.Slang_slangOutputDir())).$colon$plus(new org.sireum.String(org.sireum.hamr.sysml.LongKeys.Slang_packageName())).$colon$plus(new org.sireum.String(org.sireum.hamr.sysml.LongKeys.Slang_noProyekIve())).$colon$plus(new org.sireum.String(org.sireum.hamr.sysml.LongKeys.Slang_noEmbedArt())).$colon$plus(new org.sireum.String(org.sireum.hamr.sysml.LongKeys.Slang_devicesAsThreads())).$colon$plus(new org.sireum.String(org.sireum.hamr.sysml.LongKeys.Slang_genSbtMill())).$colon$plus(new org.sireum.String(org.sireum.hamr.sysml.LongKeys.Transpiler_slangAuxCodeDirs())).$colon$plus(new org.sireum.String(org.sireum.hamr.sysml.LongKeys.Transpiler_slangOutputCDir())).$colon$plus(new org.sireum.String(org.sireum.hamr.sysml.LongKeys.Transpiler_excludeComponentImpl())).$colon$plus(new org.sireum.String(org.sireum.hamr.sysml.LongKeys.Transpiler_bitWidth())).$colon$plus(new org.sireum.String(org.sireum.hamr.sysml.LongKeys.Transpiler_maxStringSize())).$colon$plus(new org.sireum.String(org.sireum.hamr.sysml.LongKeys.Transpiler_maxArraySize())).$colon$plus(new org.sireum.String(org.sireum.hamr.sysml.LongKeys.Transpiler_runTranspiler())).$colon$plus(new org.sireum.String(org.sireum.hamr.sysml.LongKeys.CAmkES_camkesOutputDir())).$colon$plus(new org.sireum.String(org.sireum.hamr.sysml.LongKeys.CAmkES_camkesAuxCodeDirs())).$colon$plus(new org.sireum.String(org.sireum.hamr.sysml.LongKeys.CAmkES_workspaceRootDir())).$colon$plus(new org.sireum.String(org.sireum.hamr.sysml.LongKeys.ROS2_strictAadlMode())).$colon$plus(new org.sireum.String(org.sireum.hamr.sysml.LongKeys.ROS2_ros2OutputWorkspaceDir())).$colon$plus(new org.sireum.String(org.sireum.hamr.sysml.LongKeys.ROS2_ros2Dir())).$colon$plus(new org.sireum.String(org.sireum.hamr.sysml.LongKeys.ROS2_ros2NodesLanguage())).$colon$plus(new org.sireum.String(org.sireum.hamr.sysml.LongKeys.ROS2_ros2LaunchLanguage())).$colon$plus(new org.sireum.String(org.sireum.hamr.sysml.LongKeys.Experimental_experimentalOptions()));
  // boolean sameKeys = org.sireum.hamr.codegen.LongKeys.sameKeys(knownKeys);

}

object ShortKeys {
  val verbose: String = "-v"
  val runtimeMonitoring: String = "-m"
  val platform: String = "-p"
  val Slang_slangOutputDir: String = "-o"
  val Slang_packageName: String = "-n"
  val Transpiler_excludeComponentImpl: String = "-e"
  val Transpiler_bitWidth: String = "-b"
  val Transpiler_maxStringSize: String = "-s"
  val Transpiler_maxArraySize: String = "-a"
  val Transpiler_runTranspiler: String = "-t"
  val CAmkES_workspaceRootDir: String = "-r"
  val ROS2_ros2Dir: String = "-r"
  val ROS2_ros2NodesLanguage: String = "-p"
  val ROS2_ros2LaunchLanguage: String = "-p"
  val Experimental_experimentalOptions: String = "-x"

  val allKeys: Set[String] = Set.empty[String] ++ ISZ(verbose, runtimeMonitoring, platform, Slang_slangOutputDir, Slang_packageName, Transpiler_excludeComponentImpl, Transpiler_bitWidth, Transpiler_maxStringSize, Transpiler_maxArraySize, Transpiler_runTranspiler, CAmkES_workspaceRootDir, ROS2_ros2Dir, ROS2_ros2NodesLanguage, ROS2_ros2LaunchLanguage, Experimental_experimentalOptions)

  @strictpure def sameKeys(keys: ISZ[String]): B = allKeys.elements == keys

  // Paste the following into a java program if you want to ensure the known keys match.
  // To regenerate this, run '$SIREUM_HOME/hamr/codegen/build.cmd regen-cli'.
  // If this does fail then the CLI arguments being constructed for codegen will need
  // to be updated (that could be delayed if only new options were added).

  // scala.collection.Seq<org.sireum.String> seq = scala.jdk.javaapi.CollectionConverters.asScala(new java.util.ArrayList<org.sireum.String>());
  // scala.collection.immutable.Seq<org.sireum.String> iseq = ((scala.collection.IterableOnceOps<org.sireum.String, ?, ?>) seq).toSeq();
  // org.sireum.IS<org.sireum.Z, org.sireum.String> knownKeys = org.sireum.IS$.MODULE$.apply(iseq, org.sireum.Z$.MODULE$).$colon$plus(new org.sireum.String(org.sireum.hamr.sysml.ShortKeys.verbose())).$colon$plus(new org.sireum.String(org.sireum.hamr.sysml.ShortKeys.runtimeMonitoring())).$colon$plus(new org.sireum.String(org.sireum.hamr.sysml.ShortKeys.platform())).$colon$plus(new org.sireum.String(org.sireum.hamr.sysml.ShortKeys.Slang_slangOutputDir())).$colon$plus(new org.sireum.String(org.sireum.hamr.sysml.ShortKeys.Slang_packageName())).$colon$plus(new org.sireum.String(org.sireum.hamr.sysml.ShortKeys.Transpiler_excludeComponentImpl())).$colon$plus(new org.sireum.String(org.sireum.hamr.sysml.ShortKeys.Transpiler_bitWidth())).$colon$plus(new org.sireum.String(org.sireum.hamr.sysml.ShortKeys.Transpiler_maxStringSize())).$colon$plus(new org.sireum.String(org.sireum.hamr.sysml.ShortKeys.Transpiler_maxArraySize())).$colon$plus(new org.sireum.String(org.sireum.hamr.sysml.ShortKeys.Transpiler_runTranspiler())).$colon$plus(new org.sireum.String(org.sireum.hamr.sysml.ShortKeys.CAmkES_workspaceRootDir())).$colon$plus(new org.sireum.String(org.sireum.hamr.sysml.ShortKeys.ROS2_ros2Dir())).$colon$plus(new org.sireum.String(org.sireum.hamr.sysml.ShortKeys.ROS2_ros2NodesLanguage())).$colon$plus(new org.sireum.String(org.sireum.hamr.sysml.ShortKeys.ROS2_ros2LaunchLanguage())).$colon$plus(new org.sireum.String(org.sireum.hamr.sysml.ShortKeys.Experimental_experimentalOptions()));
  // boolean sameKeys = org.sireum.hamr.codegen.ShortKeys.sameKeys(knownKeys);
}