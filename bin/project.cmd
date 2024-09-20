::/*#! 2> /dev/null                                   #
@ 2>/dev/null # 2>nul & echo off & goto BOF           #
if [ -z "${SIREUM_HOME}" ]; then                      #
  echo "Please set SIREUM_HOME env var"               #
  exit -1                                             #
fi                                                    #
exec "${SIREUM_HOME}/bin/sireum" slang run "$0" "$@"  #
:BOF
setlocal
if not defined SIREUM_HOME (
  echo Please set SIREUM_HOME env var
  exit /B -1
)
"%SIREUM_HOME%\bin\sireum.bat" slang run %0 %*
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
val air_shared = "hamr-air-shared"
val frontend = "frontend"
val stipe = "stipe"
val hamr_common_shared = "hamr-common-shared"
val library = "library"

val homeDir = Os.slashDir.up.canon

val sysmlParserSharedJvm = moduleSharedJvmPub(
  baseId = s"$hamr-$sysml-$parser",
  baseDir = homeDir / parser,
  sharedDeps = sharedId(library),
  sharedIvyDeps = ISZ(),
  jvmDeps = ISZ(parser, air),
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
  sharedDeps = ISZ(air_shared, sysmlParserSharedJvm._1.id),
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
  jvmDeps = ISZ(sysmlParserSharedJvm._2.id, sysmlStipeShared.id, hamr_common_shared),
  jvmIvyDeps = ISZ(),
  pubOpt = pub(
    desc = "Sireum HAMR SysML Frontend",
    url = "github.com/sireum/hamr-sysml",
    licenses = bsd2,
    devs = ISZ(jasonBelt)
  )
)
val project = Project.empty + sysmlParserSharedJvm._1 + sysmlParserSharedJvm._2 + sysmlStipeShared + sysmlFrontend

projectCli(Os.cliArgs, project)