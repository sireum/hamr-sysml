package org.sireum.hamr.sysml.parser

import org.sireum._
import org.antlr.v4.runtime.{BaseErrorListener, CharStreams, ParserRuleContext, Recognizer}
import org.sireum.hamr.ir.SysmlAst.TopUnit

import java.io.StringReader

object SysMLParser_Ext {

  /** A hint for a syntax error where the parser expected a name and found a keyword, e.g. a
    * GUMBO parameter called `out`. ANTLR's own message ("mismatched input 'out' expecting
    * {')', RULE_ID}") does not say the word is reserved, and the errors that follow it
    * point away from the cause. Empty for every other error. */
  def reservedWordHint(recognizer: Recognizer[_, _], offendingSymbol: Object,
                       e: org.antlr.v4.runtime.RecognitionException, ruleIdType: Int): Predef.String = {
    offendingSymbol match {
      case tok: org.antlr.v4.runtime.Token =>
        val literal = recognizer.getVocabulary.getLiteralName(tok.getType)
        // a keyword token's literal name is its text in quotes; RULE_ID and the like have none.
        // Punctuation such as '}*/' is a quoted literal too, so require a name-like word.
        if (literal == null || literal != s"'${tok.getText}'" || !tok.getText.matches("[A-Za-z_][A-Za-z0-9_]*")) return ""
        val expected: org.antlr.v4.runtime.misc.IntervalSet =
          if (e != null && e.getExpectedTokens != null) e.getExpectedTokens
          else recognizer match {
            case p: org.antlr.v4.runtime.Parser =>
              try p.getExpectedTokens catch { case _: Throwable => null }
            case _ => null
          }
        if (expected != null && expected.contains(ruleIdType))
          s" ('${tok.getText}' is a reserved word, so it cannot be used as a name here; rename it)"
        else ""
      case _ => ""
    }
  }

  def parseSK(uriOpt: Option[String], content: String, isSysML: B, reporter: message.Reporter): ParserRuleContext = {
    def parseK(uriOpt: Option[String], content: String, reporter: message.Reporter): ParserRuleContext= {
      val docInfo = message.DocInfo.create(uriOpt, content)
      val input = CharStreams.fromReader(new StringReader(content.value))

      val lexer = new KerMLv2Lexer(input)
      val tokens = new org.antlr.v4.runtime.CommonTokenStream(lexer)
      val parser = new KerMLv2Parser(tokens)
      parser.removeErrorListeners()
      parser.addErrorListener(new BaseErrorListener {
        override def syntaxError(recognizer: Recognizer[_, _], offendingSymbol: Object, line: Int, charPositionInLine: Int,
                                 msg: Predef.String, e: org.antlr.v4.runtime.RecognitionException): Unit = {
          val column = U32(charPositionInLine)
          val length = if (offendingSymbol == null) 1 else offendingSymbol.asInstanceOf[org.antlr.v4.runtime.Token].getText.length
          val offsetLength = (conversions.U32.toU64(docInfo.lineOffsets(line - 1) + column) << U64(32)) | U64(length)
          reporter.error(Some(message.PosInfo(docInfo, offsetLength)), "KerMLv2Parser", msg + reservedWordHint(recognizer, offendingSymbol, e, KerMLv2Parser.RULE_ID))
        }
      })
      return parser.entryRuleRootNamespace()
    }

    def parseS(uriOpt: Option[String], content: String, reporter: message.Reporter): ParserRuleContext = {
      val docInfo = message.DocInfo.create(uriOpt, content)
      val input = CharStreams.fromReader(new StringReader(content.value))
      val lexer = new SysMLv2Lexer(input)
      val tokens = new org.antlr.v4.runtime.CommonTokenStream(lexer)
      val parser = new SysMLv2Parser(tokens)
      parser.removeErrorListeners()
      parser.addErrorListener(new BaseErrorListener {
        override def syntaxError(recognizer: Recognizer[_, _], offendingSymbol: Object, line: Int, charPositionInLine: Int,
                                 msg: Predef.String, e: org.antlr.v4.runtime.RecognitionException): Unit = {
          val column = U32(charPositionInLine)
          val length = if (offendingSymbol == null) 1 else offendingSymbol.asInstanceOf[org.antlr.v4.runtime.Token].getText.length
          val offsetLength = (conversions.U32.toU64(docInfo.lineOffsets(line - 1) + column) << U64(32)) | U64(length)
          reporter.error(Some(message.PosInfo(docInfo, offsetLength)), "SysMLv2Parser", msg + reservedWordHint(recognizer, offendingSymbol, e, SysMLv2Parser.RULE_ID))
        }
      })
      return parser.entryRuleRootNamespace()
    }

    val ret: ParserRuleContext =(
      if (isSysML)
        parseS(uriOpt, content, reporter)
      else
        parseK(uriOpt, content, reporter))

    return ret
  }

  def parse(uriOpt: Option[String], content: String, reporter: message.Reporter): Option[TopUnit] = {
    return parseH(uriOpt, content, T, reporter)
  }

  def parseH(uriOpt: Option[String], content: String, isSysML: B, reporter: message.Reporter): Option[TopUnit] = {

    val tree: ParserRuleContext = parseSK(uriOpt, content, isSysML, reporter)

    if (!reporter.hasError) {
      return SysMLAstBuilder.build(tree, uriOpt, isSysML, reporter)
    } else {
      return None()
    }
  }
}
