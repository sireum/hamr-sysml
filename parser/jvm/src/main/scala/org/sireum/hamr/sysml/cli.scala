// #Sireum
/*
 Copyright (c) 2017-2023, Robby, Kansas State University
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions are met:

 1. Redistributions of source code must retain the above copyright notice, this
    list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright notice,
    this list of conditions and the following disclaimer in the documentation
    and/or other materials provided with the distribution.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.sireum.hamr.sysml

import org.sireum._
import org.sireum.cli.CliOpt._

object cli {

  val sysmlTranslator: Tool = Tool(
    name = "translator",
    command = "translator",
    description = "SysML v2 Grammar Translator to ANTLR v4",
    header = "Sireum HAMR SysML v2 Grammar Translator",
    usage = "<option>* <output>",
    usageDescOpt = None(),
    opts = ISZ(
      Opt(name = "version", longKey = "version", shortKey = Some('v'),
        tpe = Type.Str(sep = None(), default = Some("2023-08")),
        description = "SysML v2 grammar version"),
      Opt(name = "url", longKey = "url", shortKey = Some('u'),
        tpe = Type.Str(sep = None(), default = Some("https://raw.githubusercontent.com/Systems-Modeling/SysML-v2-Pilot-Implementation/%version/org.omg.sysml.xtext/src-gen/org/omg/sysml/xtext/parser/antlr/internal/InternalSysML.g")),
        description = "SysML v2 ANTLR v3 grammar URL (%version is replaced with --version option, if any)")
    ),
    groups = ISZ()
  )

  val group: Group = Group(
    name = "sysml",
    description = "SysML v2 Tools",
    header = "Sireum HAMR SysML v2 Tools",
    unlisted = F,
    subs = ISZ(sysmlTranslator)
  )
}
