// #Sireum
/*
 Copyright (c) 2017-2025, Robby, Kansas State University
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
import org.sireum.hamr.codegen.common.util.CommonCli

object cli {

  val sysmlCodegen: Tool = Tool(
    name = "sysmlCodegen",
    command = "codegen",
    description = "SysML v2 Codegen",
    header = "Sireum HAMR SysML v2 Code Generator",
    usage = "<option>* [<sysmlv2-file>]",
    usageDescOpt = None(),
    opts = ISZ(
      Opt(name = "sourcepath", longKey = "sourcepath", shortKey = None(),
        tpe = Type.Path(multiple = T, default = None()),
        description = "Source paths of SysML v2 .sysml files"),
      Opt(name = "line", longKey = "line", shortKey = None(),
        tpe = Type.Num(sep = None(), default = 0, min = Some(0), max = None()),
        description = "Line number containing the system to instantiate in the <sysmlv2-file> argument"),
      Opt(name = "system", longKey = "system-name", shortKey = None(),
        tpe = Type.Str(sep = None(), default = None()),
        description = "Fully qualified name of the system to instantiate")
    )
     ++ CommonCli.commonOptions,
    groups = CommonCli.commonGroups
  )

  val sysmlTipe: Tool = Tool(
    name = "sysmlTipe",
    command = "tipe",
    description = "SysML v2 Type Checker",
    header = "Sireum HAMR SysML v2 Type Checker",
    usage = "<option>* [<sysmlv2-file>]",
    usageDescOpt = None(),
    opts = ISZ(
      Opt(name = "exclude", longKey = "exclude", shortKey = Some('x'),
        tpe = Type.Str(Some(','), None()),
        description = "Sourcepath exclusion as URI segment"),
      Opt(name = "sourcepath", longKey = "sourcepath", shortKey = Some('s'),
        tpe = Type.Path(multiple = T, default = None()),
        description = "Source paths of SysML v2 .sysml files"),
      Opt(name = "parseableMessages", longKey = "parseable-messages", shortKey = None(),
        tpe = Type.Flag(F),
        description = "Print parseable file messages")
    ),
    groups = ISZ()
  )

  val sysmlLogika: Tool = Tool(
    name = "sysmlLogika",
    command = "logika",
    description = "SysML v2 Verifier",
    header = "Sireum HAMR SysML v2 Logika Verifier",
    usage = "<option>* <sysmlv2-file>*",
    usageDescOpt = None(),
    opts = ISZ(
      Opt(name = "exclude", longKey = "exclude", shortKey = None(),
        tpe = Type.Str(Some(','), None()),
        description = "Sourcepath exclusion as URI segment"),
      Opt(name = "feedback", longKey = "feedback", shortKey = None(),
        tpe = Type.Path(multiple = F, default = None()),
        description = "Feedback output directory"),
      Opt(name = "sourcepath", longKey = "sourcepath", shortKey = None(),
        tpe = Type.Path(multiple = T, default = None()),
        description = "Source paths of SysML v2 .sysml files"),
      Opt(name = "parseableMessages", longKey = "parseable-messages", shortKey = None(),
        tpe = Type.Flag(F),
        description = "Print parseable file messages")
    ),
    groups = ISZ()
  )

  val sysmlTranslator: Tool = Tool(
    name = "translator",
    command = "translator",
    description = "SysML v2 Grammar Translator to ANTLR v4",
    header = "Sireum HAMR SysML v2 Grammar Translator",
    usage = "<option>* <output>",
    usageDescOpt = None(),
    opts = ISZ(
      Opt(name = "version", longKey = "version", shortKey = Some('v'),
        tpe = Type.Str(sep = None(), default = Some("2024-12")),
        description = "SysML v2 grammar version"),
      Opt(name = "grammar", longKey = "grammar", shortKey = Some('g'),
        tpe = Type.Str(sep = None(), default = None()),
        description = "File containing an ANTLR v3 grammar"),
      Opt(name = "url", longKey = "url", shortKey = Some('u'),
        tpe = Type.Str(sep = None(), default = None()),
        description = "URL of an ANTLR v3 grammar (%version is replaced with --version option, if any)"),
      Opt(name = "keywords", longKey = "keywords", shortKey = Some('k'),
        tpe = Type.Str(sep = Some(','), default = None()),
        description = "Strings that should be treated as keywords rather than operators")
    ),
    groups = ISZ()
  )

  val sysmlConfig: Tool = Tool(
    name = "sysmlConfig",
    command = "config",
    description = "SysML v2 CodeGen Config",
    header = "Sireum HAMR SysML v2 CodeGen Config",
    usage = "<option>* <sysmlv2-file>",
    usageDescOpt = None(),
    opts = ISZ(
      Opt(name = "theme", longKey = "theme", shortKey = Some('t'),
        tpe = Type.Choice(name = "Theme", sep = None(), elements = ISZ("dark", "light")),
        description = "Form color theme")
    ),
    groups = ISZ()
  )


  val group: Group = Group(
    name = "sysml",
    description = "SysML v2 Tools",
    header = "Sireum HAMR SysML v2 Tools",
    unlisted = F,
    subs = ISZ(sysmlCodegen, sysmlConfig, sysmlLogika, sysmlTipe, sysmlTranslator)
  )
}
