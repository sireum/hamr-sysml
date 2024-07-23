/*
 Copyright (c) 2017-2024, Robby, Kansas State University
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
import org.antlr.runtime._
import org.antlr.runtime.tree.Tree
import org.sireum.parser.{ANTLRv3Lexer, ANTLRv3Parser}
import org.sireum.parser.ANTLRv3Parser._

object SysMLGrammar_Ext {

  private val ERROR_URL = -1
  private val PARSING_FAILED = -2
  private val hiddenIds: HashSet[String] = HashSet ++ ISZ[String]("RULE_ML_NOTE", "RULE_SL_NOTE", "RULE_WS")

  def translate(url: String, version: String, outFile: Os.Path): Z = {
    var keywords = HashSSet.empty[String]
    var operators = HashSSet.empty[String]
    var parens = HashSSet.empty[C]
    var isParser = F
    def addLit(text: Predef.String): Unit = {
      if (!isParser) {
        return
      }
      val str = text.substring(1, text.length - 1)
      str match {
        case "<" | ">" | "[" | "]" | "{" | "}" | "(" | ")" => parens = parens + str.charAt(0)
        case _ =>
          if (str.charAt(0).isLetter) {
            if (str == "L") {
              println("Here")
            }
            keywords = keywords + str
          }
          else operators = operators + str
      }
    }

    def printGrammar(tree: Tree): ST = {
      def printRangeOrNotSet(t: Tree): ST = {
        t.getType match {
          case CHAR_RANGE => st"${t.getChild(0).getText}..${t.getChild(1)}"
          case _ =>
            val sub = t.getChild(0)
            sub.getType match {
              case BLOCK => printBlock(sub, None(), F)
              case _ => st"${t.getText}"
            }
        }
      }

      def printAtom(t: Tree): ST = {
        t.getType match {
          case CHAR_LITERAL | STRING_LITERAL => addLit(t.getText); st"${t.getText}"
          case TOKEN_REF | RULE_REF => st"${t.getText}"
          case _ =>
            t.getText match {
              case "^" | "!" =>
                val sub = t.getChild(0)
                sub.getType match {
                  case RULE_REF => st"${sub.getText}"
                  case _ => printRangeOrNotSet(sub)
                }
              case "." => st"."
              case _ if t.getChildCount > 0 => printRangeOrNotSet(t)
              case x =>
                halt(s"Infeasible: $x")
            }
        }
      }

      def printBlock(t: Tree, opOpt: Option[String], isSuffix: B): ST = {
        var alts = ISZ[ST]()
        var maxAltChildCount: Z = 0
        for (i <- 0 until t.getChildCount) {
          val sub = t.getChild(i)
          sub.getType match {
            case ALT =>
              val alt = printAlt(sub)
              if (alt.size > maxAltChildCount) {
                maxAltChildCount = alt.size
              }
              alts = alts :+ st"${(alt, " ")}"
            case _ =>
          }
        }
        val r = alts.size.toInt match {
          case 0 => st""
          case 1 if opOpt.isEmpty => alts(0)
          case 1 if maxAltChildCount == 1 => if (isSuffix) st"${alts(0)}$opOpt" else st"$opOpt${alts(0)}"
          case _ => if (isSuffix) st"(${(alts, " | ")})$opOpt" else st"$opOpt(${(alts, " | ")})"
        }
        if (opOpt.nonEmpty && (opOpt.get.value == "*")) { // WORKAROUND
          if (r.render.value == ".*") st".*?" else r
        } else r
      }

      def printAlt(t: Tree): ISZ[ST] = {
        var r = ISZ[ST]()
        for (i <- 0 until t.getChildCount) {
          val sub = t.getChild(i)
          val op = sub.getText
          op match {
            case "=" | "+=" => r = r :+ printAtom(sub.getChild(1))
            case "?" | "*" | "+" =>
              sub.getChild(0).getType match {
                case BLOCK =>
                  val ssub = sub.getChild(0)
                  var b = st"${printBlock(ssub, Some(op), T)}"
                  if (op == "*" && b.render.value == ".") {
                    b = st".*?"
                  }
                  r = r :+ b
                case _ =>
                  val ssub = sub.getChild(0).getChild(0).getChild(0)
                  ssub.getType match {
                    case BLOCK => r = r :+ st"${printBlock(ssub, Some(op), T)}"
                    case _ => r = r :+ st"${printAtom(ssub)}$op"
                  }
              }
            case "EOF" => r = r :+ st"EOF"
            case "~" => r = r :+ st"${printBlock(sub.getChild(0), Some("~"), F)}"
            case "." => r = r :+ st"."
            case _ =>
              sub.getType match {
                case CHAR_LITERAL | STRING_LITERAL => addLit(sub.getText); r = r :+ st"${sub.getText}"
                case TOKEN_REF | RULE_REF => r = r :+ st"${sub.getText}"
                case CHAR_RANGE => r = r :+ st"${sub.getChild(0).getText}..${sub.getChild(1).getText}"
                case BLOCK => r = r :+ printBlock(sub, None(), F)
                case ACTION | EOA | EOB | SYN_SEMPRED => // skip
                case tpe =>
                  halt(s"Infeasible: $tpe")
              }
          }
        }
        r
      }

      def printRule(t: Tree): (B, ST) = {
        val id = t.getChild(0).getText
        var alts = ISZ[ST]()

        def processBlock(b: Tree): Unit = {
          for (j <- 0 until b.getChildCount) {
            val ssub = b.getChild(j)
            ssub.getType match {
              case ALT => alts = alts :+ st"${(printAlt(ssub), " ")}"
              case _ =>
            }
          }
        }

        var single = F
        val blocks = for (i <- 0 until t.getChildCount if t.getChild(i).getType == BLOCK) yield t.getChild(i)
        if (blocks.length == 1) {
          val b = blocks(0)
          val alts = for (i <- 0 until b.getChildCount if b.getChild(i).getType == ALT) yield b.getChild(i)
          if (alts.length == 1) {
            val a = alts(0)
            if (a.getChildCount == 2 && a.getChild(0).getType == BLOCK && a.getChild(1).getType == EOA) {
              single = T
              processBlock(a.getChild(0))
            }
          }
        }
        if (!single) {
          for (b <- blocks) {
            processBlock(b)
          }
        }
        isParser = !id.forall(c => c.isUpper || c == '_')
        val hiddenOpt = if (hiddenIds.contains(id)) Some(st" -> channel(HIDDEN)") else None()
        (isParser,
          if (id == "RULE_SL_NOTE") st"$id: '//' ~'*' (~('\\n' | '\\r') ~('\\n' | '\\r')*)? ('\\r'? '\\n')?$hiddenOpt;"
          else if (alts.size == 1) st"$id: ${alts(0)}$hiddenOpt;"
          else {
            //alts = for (i <- 1 to alts.size) yield st"${alts(i - 1)} #$id$i"
            alts = for (i <- 1 to alts.size) yield st"${alts(i - 1)} ${if (isParser) s"#$id$i" else "" }"
            st"""$id:
                |  ${(alts, "\n| ")}$hiddenOpt;"""
          })
      }

      var prules = ISZ[ST]()
      var lrules = ISZ[ST]()

      def rec(t: Tree): Unit = {
        t.getType match {
          case RULE =>
            if (prules.isEmpty || !ops.StringOps(t.getChild(0).getText).startsWith("entry")) {
              val (isParser, r) = printRule(t)
              if (isParser) {
                prules = prules :+ r
              } else {
                lrules = lrules :+ r
              }
            }
          case _ =>
            for (i <- 0 until t.getChildCount) {
              rec(t.getChild(i))
            }
        }
      }

      rec(tree)

      def getSymbolName(c: C): String = c.value match {
        case '~' => "TILDE"
        case '`' => "BQUOTE"
        case '!' => "BANG"
        case '@' => "AT"
        case '#' => "HASH"
        case '$' => "DOLLAR"
        case '%' => "PERCENT"
        case '^' => "HAT"
        case '&' => "AND"
        case '*' => "STAR"
        case '-' => "MINUS"
        case '+' => "PLUS"
        case '=' => "EQ"
        case '|' => "BAR"
        case '\\' => "BSLASH"
        case '/' => "SLASH"
        case ':' => "COLON"
        case ';' => "SEMI"
        case '"' => "DQUOTE"
        case '\'' => "SQUOTE"
        case ',' => "COMMA"
        case '.' => "DOT"
        case '<' => "LANGLE"
        case '>' => "RANGLE"
        case '(' => "LPAREN"
        case ')' => "RPAREN"
        case '[' => "LSQUARE"
        case ']' => "RSQUARE"
        case '{' => "LBRACE"
        case '}' => "RBRACE"
        case '?' => "QMARK"
        case x =>
          //halt(s"Infeasible: $c")
        s"____${x}____"
      }

      def getParenTokenDef(c: C): ST = st"${getSymbolName(c)}: '$c';"

      def getOpTokenDef(op: String): ST = st"OP_${(for (c <- op.value) yield getSymbolName(c), "_")}: '$op';"

      val grammar = ops.StringOps(outFile.name).replaceAllLiterally(s".${outFile.ext}", "")
      st"""grammar $grammar;
          |
          |@parser::members {
          |  public static boolean isKeyword(int tokenType) {
          |    switch (tokenType) {
          |      case ${(for (k <- keywords.elements) yield st"${grammar}Lexer.K_${k.value.toUpperCase}", " |\n")}: return true;
          |      default: return false;
          |    }
          |  }
          |}
          |
          |${(prules, "\n\n")}
          |
          |${(for (k <- keywords.elements) yield st"K_${k.value.toUpperCase}: '$k';", "\n")}
          |
          |${(for (p <- parens.elements) yield getParenTokenDef(p), "\n")}
          |
          |${(for (op <- operators.elements) yield getOpTokenDef(op), "\n")}
          |
          |${(lrules, "\n\n")}"""
    }

    def parseGrammar(uriOpt: Option[String],
                     input: String,
                     reporter: message.Reporter): Tree = {
      val docInfo = message.DocInfo.create(uriOpt, input)

      val lex = new ANTLRv3Lexer(new ANTLRStringStream(input.value)) {
        override def displayRecognitionError(tokenNames: Array[Predef.String], e: RecognitionException): Unit = {
          val msg = getErrorMessage(e, tokenNames)
          val line = e.line
          val column = U32(e.charPositionInLine)
          val offsetLength = (conversions.U32.toU64(docInfo.lineOffsets(line - 1) + column) << U64(32)) | U64(1)
          reporter.error(Some(message.PosInfo(docInfo, offsetLength)), "ANTLRv3Lexer", msg)
        }
      }
      val cts = new CommonTokenStream(lex)
      val r = new ANTLRv3Parser(cts) {
        override def displayRecognitionError(tokenNames: Array[Predef.String], e: RecognitionException): Unit = {
          val msg = getErrorMessage(e, tokenNames)
          val line = e.line
          val column = U32(e.charPositionInLine)
          val offsetLength = (conversions.U32.toU64(docInfo.lineOffsets(line - 1) + column) << U64(32)) | U64(e.token.getText.length)
          reporter.error(Some(message.PosInfo(docInfo, offsetLength)), "ANTLRv3Parser", msg)
        }
      }
      return r.grammarDef().getTree
    }

    outFile.up.mkdirAll()
    val input = Os.tempFix(outFile.name, ".g")
    input.removeAll()
    val u = ops.StringOps(url).replaceAllLiterally("%version", version)
    input.downloadFrom(u)
    input.removeOnExit()
    if (!input.exists) {
      eprintln(s"Could not download from $u")
      return ERROR_URL
    }
    println(input)
    val content = input.read
    val reporter = message.Reporter.create
    val tree = parseGrammar(Some(input.toUri), content, reporter)
    if (reporter.hasIssue) {
      reporter.printMessages()
      return PARSING_FAILED
    }
    val treeST = printGrammar(tree)

    outFile.writeOver(
      st"""// Auto-generated from $u
          |$treeST""".render
    )
    println(s"Generated $outFile")
    return 0
  }

}
