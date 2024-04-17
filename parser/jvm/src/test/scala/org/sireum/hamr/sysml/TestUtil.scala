package org.sireum.hamr.sysml

import org.sireum._
import org.sireum.hamr.sysml.cli
import org.sireum.cli.CliOpt.Type

object TestUtil {
  def fetchSysmlLibrary(resourceDir: Os.Path): Unit = {
    val version = cli.sysmlTranslator.opts(0).tpe.asInstanceOf[Type.Str].default.get
    val destDir = resourceDir / "models" / s"sysml.library"
    val url = s"https://github.com/Systems-Modeling/SysML-v2-Release/archive/refs/tags/${version}.zip"
    val versionFile = destDir / ".version"
    if (!destDir.exists || !versionFile.exists || versionFile.read != version) {
      destDir.removeAll()
      destDir.mkdirAll()
      println(s"Downloading $url ...")
      val t = Os.tempDir() / "s.zip"
      t.downloadFrom(url)
      t.unzipTo(destDir)
      val del = Os.Path.walk(destDir, T, T, p =>
        !(p.ext.native == "kerml" || p.ext.native == "sysml" ||
          p.name.native == ".project" || p.name.native.startsWith("LICENSE")))
      for (d <- del.elements.reverse if d.isFile || d.list.isEmpty) d.removeAll()
      (destDir / ".version").writeOver(version)
    }
  }
}
