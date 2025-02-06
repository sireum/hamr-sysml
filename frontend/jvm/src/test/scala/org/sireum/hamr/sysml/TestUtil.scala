package org.sireum.hamr.sysml

import org.sireum._
import org.sireum.cli.CliOpt.Type
import org.sireum.hamr.sysml.cli

object TestUtil {

  val isCI: B = Os.env("GITLAB_CI").nonEmpty || Os.env("GITHUB_ACTIONS").nonEmpty || Os.env("BUILD_ID").nonEmpty

  def fetchSysmlLibrary(resourceDir: Os.Path): Unit = {
    val version = ops.StringOps(cli.sysmlTranslator.opts(0).tpe.asInstanceOf[Type.Str].default.get)
    val destDir = resourceDir / "models" / s"sysml.library"
    var candidates: ISZ[String] = ISZ(version.s)
    if (version.contains(".")) { // a release for 2024.04.1 wasn't made so fall back to 2024.04
      candidates = candidates :+ version.substring(0, version.indexOf('.'))
    }

    val versionFile = destDir / ".version"
    if (!destDir.exists || !versionFile.exists || versionFile.read != version.s) {
      destDir.removeAll()
      destDir.mkdirAll()
      for (c <- candidates) {
        val url = s"https://github.com/Systems-Modeling/SysML-v2-Pilot-Implementation/archive/refs/tags/${c}.zip"
        println(s"Attempting to download from $url ...")
        val t = Os.tempDir() / "s.zip"
        t.downloadFrom(url)
        if (t.exists) {
          // 7zz.com fails on windows 10 due to it not being able to extract files from the zip that
          // have very long path names when placed under the resources directory (even with long path
          // support enabled).  The workaround is to unzip to a tempDir that has a presumably shorter
          // path, delete the unneeded files (which includes the ones with long paths), and then move
          // the results to destDir
          println(s"Unzipping to $destDir")
          val t2 = Os.tempDir()
          t.unzipTo(t2)
          val del = Os.Path.walk(t2, T, T, p =>
            !(ops.StringOps(p.toUri).contains("/kerml/src") || ops.StringOps(p.toUri).contains("/sysml/src")) ||
              !(p.ext.native == "kerml" || p.ext.native == "sysml" ||
                p.name.native == ".project" || p.name.native.startsWith("LICENSE")))
          for (d <- del.elements.reverse if d.isFile || d.list.isEmpty) d.removeAll()
          t2.moveOverTo(destDir)
          (destDir / ".version").writeOver(version.s)
        }
      }
    }
  }
}
