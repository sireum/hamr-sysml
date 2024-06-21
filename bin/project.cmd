::/*#! 2> /dev/null                                 #
@ 2>/dev/null # 2>nul & echo off & goto BOF         #
if [ -z ${SIREUM_HOME} ]; then                      #
  echo "Please set SIREUM_HOME env var"             #
  exit -1                                           #
fi                                                  #
exec ${SIREUM_HOME}/bin/sireum slang run "$0" "$@"  #
:BOF
setlocal
if not defined SIREUM_HOME (
  echo Please set SIREUM_HOME env var
  exit /B -1
)
%SIREUM_HOME%\bin\sireum.bat slang run "%0" %*
exit /B %errorlevel%
::!#*/
// #Sireum

import org.sireum._
import org.sireum.project.ProjectUtil._
import org.sireum.project.Project

val hamr = "hamr"
val sysml = "sysml"
val parser = "parser"
val air = "hamr-air"
val ast = "ast"
val frontend = "frontend"
val slangAst = "slang-ast"
val stipe = "stipe"

val homeDir = Os.slashDir.up.canon

val sysmlAstShared = moduleSharedPub(
  id = s"$sysml-$ast",
  baseDir = homeDir / ast,
  sharedDeps = ISZ(slangAst),
  sharedIvyDeps = ISZ(),
  pubOpt = pub(
    desc = "Sireum HAMR SysML Abstract Syntax Trees (AST)",
    url = "github.com/sireum/hamr-sysml",
    licenses = bsd2,
    devs = ISZ(jasonBelt)
  )
)

val sysmlParserJvm = moduleJvmPub(
  id = s"$hamr-$sysml-$parser",
  baseDir = homeDir / parser,
  jvmDeps = ISZ(parser, sysmlAstShared.id, air),
  jvmIvyDeps = ISZ("org.sireum:hamr-sysml-parser:"),
  pubOpt = pub(
    desc = "Sireum HAMR SysML v2 Parser",
    url = "github.com/sireum/hamr-sysml",
    licenses = bsd2,
    devs = ISZ(robby, jasonBelt)
  )
)

val sysmlStipeShared = moduleSharedPub(
  id = s"$hamr-$sysml-$stipe",
  baseDir = homeDir / stipe,
  sharedDeps = ISZ(sysmlAstShared.id),
  sharedIvyDeps = ISZ(),
  pubOpt = pub(
    desc = "Sireum HAMR SysML Type",
    url = "github.com/sireum/hamr-sysml",
    licenses = bsd2,
    devs = ISZ(jasonBelt)
  )
)

val sysmlFrontend = moduleJvmPub(
  id = s"$hamr-$sysml-$frontend",
  baseDir = homeDir / frontend,
  jvmDeps = ISZ(sysmlParserJvm.id, sysmlStipeShared.id),
  jvmIvyDeps = ISZ(),
  pubOpt = pub(
    desc = "Sireum HAMR SysML Frontend",
    url = "github.com/sireum/hamr-sysml",
    licenses = bsd2,
    devs = ISZ(jasonBelt)
  )
)
val project = Project.empty + sysmlAstShared + sysmlParserJvm + sysmlStipeShared + sysmlFrontend

projectCli(Os.cliArgs, project)