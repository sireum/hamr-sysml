package sysml

import org.sireum._
import org.sireum.hamr.sysml.cli
import org.sireum.cli.CliOpt.Type

object TestUtil {
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
        val url = s"https://github.com/Systems-Modeling/SysML-v2-Release/archive/refs/tags/${c}.zip"
        println(s"Attempting to download from $url ...")
        val t = Os.tempDir() / "s.zip"
        t.downloadFrom(url)
        if (t.exists) {
          t.unzipTo(destDir)
          val del = Os.Path.walk(destDir, T, T, p =>
            !(p.ext.native == "kerml" || p.ext.native == "sysml" ||
              p.name.native == ".project" || p.name.native.startsWith("LICENSE")))
          for (d <- del.elements.reverse if d.isFile || d.list.isEmpty) d.removeAll()
          (destDir / ".version").writeOver(version.s)
        }
      }
    }
  }
}
