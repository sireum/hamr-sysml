::/*#! 2> /dev/null                                                                                         #
@ 2>/dev/null # 2>nul & echo off & goto BOF                                                                 #
export SIREUM_HOME=$(cd -P $(dirname "$0")/.. && pwd -P)                                                    #
if [ ! -z ${SIREUM_PROVIDED_SCALA++} ]; then                                                                #
  SIREUM_PROVIDED_JAVA=true                                                                                 #
fi                                                                                                          #
"${SIREUM_HOME}/bin/init.sh" || exit $?                                                                     #
if [ -n "$COMSPEC" -a -x "$COMSPEC" ]; then                                                                 #
  export SIREUM_HOME=$(cygpath -C OEM -w -a ${SIREUM_HOME})                                                 #
  if [ -z ${SIREUM_PROVIDED_JAVA++} ]; then                                                                 #
    export PATH="${SIREUM_HOME}/bin/win/java":"${SIREUM_HOME}/bin/win/z3":"$PATH"                           #
    export PATH="$(cygpath -C OEM -w -a ${JAVA_HOME}/bin)":"$(cygpath -C OEM -w -a ${Z3_HOME}/bin)":"$PATH" #
  fi                                                                                                        #
elif [ "$(uname)" = "Darwin" ]; then                                                                        #
  if [ -z ${SIREUM_PROVIDED_JAVA++} ]; then                                                                 #
    export PATH="${SIREUM_HOME}/bin/mac/java/bin":"${SIREUM_HOME}/bin/mac/z3/bin":"$PATH"                   #
  fi                                                                                                        #
elif [ "$(expr substr $(uname -s) 1 5)" = "Linux" ]; then                                                   #
  if [ -z ${SIREUM_PROVIDED_JAVA++} ]; then                                                                 #
    if [ "$(uname -m)" = "aarch64" ]; then                                                                  #
      export PATH="${SIREUM_HOME}/bin/linux/arm/java/bin":"$PATH"                                           #
    else                                                                                                    #
      export PATH="${SIREUM_HOME}/bin/linux/java/bin":"${SIREUM_HOME}/bin/linux/z3/bin":"$PATH"             #
    fi                                                                                                      #
  fi                                                                                                        #
fi                                                                                                          #
if [ -f "$0.com" ] && [ "$0.com" -nt "$0" ]; then                                                           #
  exec "$0.com" "$@"                                                                                        #
else                                                                                                        #
  rm -fR "$0.com"                                                                                           #
  exec "${SIREUM_HOME}/bin/sireum" slang run -n "$0" "$@"                                                   #
fi                                                                                                          #
:BOF
setlocal
set SIREUM_HOME=%~dp0../
call "%~dp0init.bat" || exit /B %errorlevel%
if defined SIREUM_PROVIDED_SCALA set SIREUM_PROVIDED_JAVA=true
if not defined SIREUM_PROVIDED_JAVA set PATH=%~dp0win\java\bin;%~dp0win\z3\bin;%PATH%
set NEWER=False
if exist %~dpnx0.com for /f %%i in ('powershell -noprofile -executionpolicy bypass -command "(Get-Item %~dpnx0.com).LastWriteTime -gt (Get-Item %~dpnx0).LastWriteTime"') do @set NEWER=%%i
if "%NEWER%" == "True" goto native
del "%~dpnx0.com" > nul 2>&1
"%~dp0sireum.bat" slang run -n "%0" %*
exit /B %errorlevel%
:native
%~dpnx0.com %*
exit /B %errorlevel%
::!#*/
// #Sireum

import org.sireum._

val homeBin: Os.Path = Os.slashDir
val home: Os.Path = homeBin.up
val sireum: Os.Path = homeBin / (if (Os.isWin) "sireum.bat" else "sireum")
val appDir: Os.Path = homeBin / (if (Os.isMac) "mac" else if (Os.isWin) "win" else "linux")

if (Os.cliArgs.isEmpty) {
  usage()
  Os.exit(0)
}

def regenTransformers(): Unit = {
  val path = home / "ast" / "shared" / "src" / "main" / "scala" / "org" / "sireum" / "hamr" / "sysml" / "ast"

  val license = home / "license.txt"

  Os.proc(ISZ[String](sireum.value, "tools", "trafo", "-l", license.value,
    "-m", "immutable,mutable") :+ (path / "SysmlAst.scala").value).at(path).console.runCheck()
}

for (i <- 0 until Os.cliArgs.size) {
  Os.cliArgs(i) match {
    case string"regen-trans" => regenTransformers()
    case cmd =>
      usage()
      eprintln(s"Unrecognized command: $cmd")
      Os.exit(-1)
  }
}

def usage(): Unit = {
  println("SysMLv2 /build")
  println(st"""Usage: (regen-trans)""".render)
}