package org.sireum.hamr.sysml

import org.sireum._
import org.antlr.v4.runtime.{BaseErrorListener, CharStreams, ParserRuleContext, Recognizer}
import org.sireum.hamr.sysml.ast.SysmlAst.TopUnit
import org.sireum.hamr.sysml.parser.{KerMLv2Lexer, KerMLv2Parser, SysMLv2Lexer, SysMLv2Parser}

import java.io.StringReader

object SysMLParser_Ext {

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
          reporter.error(Some(message.PosInfo(docInfo, offsetLength)), "KerMLv2Parser", msg)
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
          reporter.error(Some(message.PosInfo(docInfo, offsetLength)), "SysMLv2Parser", msg)
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
