// #Sireum
package org.sireum.hamr.sysml

import org.sireum._
import org.sireum.cli.CliOpt.Type
import org.sireum.hamr.sysml.cli
import org.sireum.hamr.ir.{GclAssume, GclCaseStatement, GclCompute, GclGuarantee, GclHandle, GclInitialize, GclIntegration, GclInvariant, GclLib, GclStateVar, GclSubclause, InfoFlowClause, MTransformer, Name}
import org.sireum.lang.ast.{Attr, Exp, ResolvedAttr, TypedAttr}
import org.sireum.message.{FlatPos, Position}


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
        val url = s"https://github.com/Systems-Modeling/SysML-v2-Pilot-Implementation/archive/refs/tags/$c.zip"
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
              !(p.ext == "kerml" || p.ext == "sysml" ||
                p.name == ".project" || ops.StringOps(p.name).startsWith("LICENSE")))
          for (d <- ops.ISZOps(del).reverse if d.isFile || d.list.isEmpty) {
            d.removeAll()
          }
          t2.moveOverTo(destDir)
          (destDir / ".version").writeOver(version.s)
        }
      }
    }
  }

  @record class AirScrubber() extends MTransformer {
    def scrubPos(pos: Option[Position]): Option[Position] = {
      pos match {
        case Some(f: FlatPos) =>
          f.uriOpt match {
            case Some(uri) =>
              val oo = ops.StringOps(uri)
              val l = oo.lastIndexOfFrom('/', oo.lastIndexOf('/') - 1)
              return Some(f(uriOpt = Some(oo.substring(l, oo.s.size))))
            case _ => return pos
          }
        case Some(x) => halt(s"Unexpected position type: $x")
        case None() => return pos
      }
    }

    override def preName(o: Name): MTransformer.PreResult[Name] = {
      return MTransformer.PreResult(T, MSome(o(pos = scrubPos(o.pos))))
    }

    override def pre_langastAttr(o: Attr): MTransformer.PreResult[Attr] = {
      return MTransformer.PreResult(T, MSome(o(posOpt = scrubPos(o.posOpt))))
    }

    override def pre_langastTypedAttr(o: TypedAttr): MTransformer.PreResult[TypedAttr] = {
      return MTransformer.PreResult(T, MSome(o(posOpt = scrubPos(o.posOpt))))
    }

    override def pre_langastResolvedAttr(o: ResolvedAttr): MTransformer.PreResult[ResolvedAttr] = {
      return MTransformer.PreResult(T, MSome(o(posOpt = scrubPos(o.posOpt))))
    }

    override def pre_langastExpBinary(o: Exp.Binary): MTransformer.PreResult[Exp] = {
      return MTransformer.PreResult(T, MSome(o(opPosOpt = scrubPos(o.opPosOpt))))
    }

    override def preGclSubclause(o: GclSubclause): MTransformer.PreResult[GclSubclause] = {
      return MTransformer.PreResult(T, MSome(o(attr = o.attr(scrubPos(o.posOpt)))))
    }

    override def preGclGuarantee(o: GclGuarantee): MTransformer.PreResult[GclGuarantee] = {
      return MTransformer.PreResult(T, MSome(o(attr = o.attr(scrubPos(o.posOpt)))))
    }

    override def preGclInvariant(o: GclInvariant): MTransformer.PreResult[GclInvariant] = {
      return MTransformer.PreResult(T, MSome(o(attr = o.attr(scrubPos(o.posOpt)))))
    }

    override def preGclAssume(o: GclAssume): MTransformer.PreResult[GclAssume] = {
      return MTransformer.PreResult(T, MSome(o(attr = o.attr(scrubPos(o.posOpt)))))
    }

    override def preGclIntegration(o: GclIntegration): MTransformer.PreResult[GclIntegration] = {
      return MTransformer.PreResult(T, MSome(o(attr = o.attr(scrubPos(o.posOpt)))))
    }

    override def preGclCaseStatement(o: GclCaseStatement): MTransformer.PreResult[GclCaseStatement] = {
      return MTransformer.PreResult(T, MSome(o(attr = o.attr(scrubPos(o.posOpt)))))
    }

    override def preGclInitialize(o: GclInitialize): MTransformer.PreResult[GclInitialize] = {
      return MTransformer.PreResult(T, MSome(o(attr = o.attr(scrubPos(o.posOpt)))))
    }

    override def preGclCompute(o: GclCompute): MTransformer.PreResult[GclCompute] = {
      return MTransformer.PreResult(T, MSome(o(attr = o.attr(scrubPos(o.posOpt)))))
    }

    override def preGclHandle(o: GclHandle): MTransformer.PreResult[GclHandle] = {
      return MTransformer.PreResult(T, MSome(o(attr = o.attr(scrubPos(o.posOpt)))))
    }

    override def preGclLib(o: GclLib): MTransformer.PreResult[GclLib] = {
      return MTransformer.PreResult(T, MSome(o(attr = o.attr(scrubPos(o.posOpt)))))
    }

    override def preInfoFlowClause(o: InfoFlowClause): MTransformer.PreResult[InfoFlowClause] = {
      return MTransformer.PreResult(T, MSome(o(attr = o.attr(scrubPos(o.posOpt)))))
    }

    override def preGclStateVar(o: GclStateVar): MTransformer.PreResult[GclStateVar] = {
      return MTransformer.PreResult(T, MSome(o(attr = o.attr(scrubPos(o.posOpt)))))
    }
  }
}
