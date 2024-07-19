// #Sireum
// @formatter:off

/*
 Copyright (c) 2017-2024, Kansas State University
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

// This file is auto-generated from SysmlAst.scala

package org.sireum.hamr.sysml.ast

import org.sireum._

object Transformer {

  @datatype class PreResult[Context, T](val ctx: Context,
                                        val continu: B,
                                        val resultOpt: Option[T])

  @datatype class TPostResult[Context, T](val ctx: Context,
                                          val resultOpt: Option[T])

  @sig trait PrePost[Context] {

    @pure def preSysmlAstId(ctx: Context, o: SysmlAst.Id): PreResult[Context, SysmlAst.Id] = {
      return PreResult(ctx, T, None())
    }

    @pure def preSysmlAstName(ctx: Context, o: SysmlAst.Name): PreResult[Context, SysmlAst.Name] = {
      return PreResult(ctx, T, None())
    }

    @pure def preSysmlAstTopUnit(ctx: Context, o: SysmlAst.TopUnit): PreResult[Context, SysmlAst.TopUnit] = {
      return PreResult(ctx, T, None())
    }

    @pure def preSysmlAstAttrNode(ctx: Context, o: SysmlAst.AttrNode): PreResult[Context, SysmlAst.AttrNode] = {
      o match {
        case o: SysmlAst.Import =>
          val r: PreResult[Context, SysmlAst.AttrNode] = preSysmlAstImport(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.AttrNode)) => PreResult(preCtx, continu, Some[SysmlAst.AttrNode](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.AttrNode]())
          }
          return r
        case o: SysmlAst.Alias =>
          val r: PreResult[Context, SysmlAst.AttrNode] = preSysmlAstAlias(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.AttrNode)) => PreResult(preCtx, continu, Some[SysmlAst.AttrNode](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.AttrNode]())
          }
          return r
        case o: SysmlAst.Identification =>
          val r: PreResult[Context, SysmlAst.AttrNode] = preSysmlAstIdentification(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.AttrNode)) => PreResult(preCtx, continu, Some[SysmlAst.AttrNode](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.AttrNode]())
          }
          return r
        case o: SysmlAst.Package =>
          val r: PreResult[Context, SysmlAst.AttrNode] = preSysmlAstPackage(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.AttrNode)) => PreResult(preCtx, continu, Some[SysmlAst.AttrNode](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.AttrNode]())
          }
          return r
        case o: SysmlAst.AttributeDefinition =>
          val r: PreResult[Context, SysmlAst.AttrNode] = preSysmlAstAttributeDefinition(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.AttrNode)) => PreResult(preCtx, continu, Some[SysmlAst.AttrNode](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.AttrNode]())
          }
          return r
        case o: SysmlAst.AllocationDefinition =>
          val r: PreResult[Context, SysmlAst.AttrNode] = preSysmlAstAllocationDefinition(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.AttrNode)) => PreResult(preCtx, continu, Some[SysmlAst.AttrNode](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.AttrNode]())
          }
          return r
        case o: SysmlAst.ConnectionDefinition =>
          val r: PreResult[Context, SysmlAst.AttrNode] = preSysmlAstConnectionDefinition(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.AttrNode)) => PreResult(preCtx, continu, Some[SysmlAst.AttrNode](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.AttrNode]())
          }
          return r
        case o: SysmlAst.EnumerationDefinition =>
          val r: PreResult[Context, SysmlAst.AttrNode] = preSysmlAstEnumerationDefinition(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.AttrNode)) => PreResult(preCtx, continu, Some[SysmlAst.AttrNode](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.AttrNode]())
          }
          return r
        case o: SysmlAst.PartDefinition =>
          val r: PreResult[Context, SysmlAst.AttrNode] = preSysmlAstPartDefinition(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.AttrNode)) => PreResult(preCtx, continu, Some[SysmlAst.AttrNode](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.AttrNode]())
          }
          return r
        case o: SysmlAst.PortDefinition =>
          val r: PreResult[Context, SysmlAst.AttrNode] = preSysmlAstPortDefinition(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.AttrNode)) => PreResult(preCtx, continu, Some[SysmlAst.AttrNode](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.AttrNode]())
          }
          return r
        case o: SysmlAst.MetadataDefinition =>
          val r: PreResult[Context, SysmlAst.AttrNode] = preSysmlAstMetadataDefinition(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.AttrNode)) => PreResult(preCtx, continu, Some[SysmlAst.AttrNode](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.AttrNode]())
          }
          return r
        case o: SysmlAst.AttributeUsage =>
          val r: PreResult[Context, SysmlAst.AttrNode] = preSysmlAstAttributeUsage(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.AttrNode)) => PreResult(preCtx, continu, Some[SysmlAst.AttrNode](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.AttrNode]())
          }
          return r
        case o: SysmlAst.ReferenceUsage =>
          val r: PreResult[Context, SysmlAst.AttrNode] = preSysmlAstReferenceUsage(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.AttrNode)) => PreResult(preCtx, continu, Some[SysmlAst.AttrNode](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.AttrNode]())
          }
          return r
        case o: SysmlAst.ConnectionUsage =>
          val r: PreResult[Context, SysmlAst.AttrNode] = preSysmlAstConnectionUsage(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.AttrNode)) => PreResult(preCtx, continu, Some[SysmlAst.AttrNode](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.AttrNode]())
          }
          return r
        case o: SysmlAst.ItemUsage =>
          val r: PreResult[Context, SysmlAst.AttrNode] = preSysmlAstItemUsage(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.AttrNode)) => PreResult(preCtx, continu, Some[SysmlAst.AttrNode](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.AttrNode]())
          }
          return r
        case o: SysmlAst.PartUsage =>
          val r: PreResult[Context, SysmlAst.AttrNode] = preSysmlAstPartUsage(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.AttrNode)) => PreResult(preCtx, continu, Some[SysmlAst.AttrNode](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.AttrNode]())
          }
          return r
        case o: SysmlAst.PortUsage =>
          val r: PreResult[Context, SysmlAst.AttrNode] = preSysmlAstPortUsage(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.AttrNode)) => PreResult(preCtx, continu, Some[SysmlAst.AttrNode](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.AttrNode]())
          }
          return r
        case o: SysmlAst.Comment =>
          val r: PreResult[Context, SysmlAst.AttrNode] = preSysmlAstComment(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.AttrNode)) => PreResult(preCtx, continu, Some[SysmlAst.AttrNode](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.AttrNode]())
          }
          return r
        case o: SysmlAst.Documentation =>
          val r: PreResult[Context, SysmlAst.AttrNode] = preSysmlAstDocumentation(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.AttrNode)) => PreResult(preCtx, continu, Some[SysmlAst.AttrNode](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.AttrNode]())
          }
          return r
        case o: SysmlAst.TextualRepresentation =>
          val r: PreResult[Context, SysmlAst.AttrNode] = preSysmlAstTextualRepresentation(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.AttrNode)) => PreResult(preCtx, continu, Some[SysmlAst.AttrNode](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.AttrNode]())
          }
          return r
      }
    }

    @pure def preSysmlAstPackageBodyElement(ctx: Context, o: SysmlAst.PackageBodyElement): PreResult[Context, SysmlAst.PackageBodyElement] = {
      o match {
        case o: SysmlAst.Import =>
          val r: PreResult[Context, SysmlAst.PackageBodyElement] = preSysmlAstImport(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.PackageBodyElement)) => PreResult(preCtx, continu, Some[SysmlAst.PackageBodyElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.PackageBodyElement]())
          }
          return r
        case o: SysmlAst.Alias =>
          val r: PreResult[Context, SysmlAst.PackageBodyElement] = preSysmlAstAlias(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.PackageBodyElement)) => PreResult(preCtx, continu, Some[SysmlAst.PackageBodyElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.PackageBodyElement]())
          }
          return r
        case o: SysmlAst.Package =>
          val r: PreResult[Context, SysmlAst.PackageBodyElement] = preSysmlAstPackage(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.PackageBodyElement)) => PreResult(preCtx, continu, Some[SysmlAst.PackageBodyElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.PackageBodyElement]())
          }
          return r
        case o: SysmlAst.AttributeDefinition =>
          val r: PreResult[Context, SysmlAst.PackageBodyElement] = preSysmlAstAttributeDefinition(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.PackageBodyElement)) => PreResult(preCtx, continu, Some[SysmlAst.PackageBodyElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.PackageBodyElement]())
          }
          return r
        case o: SysmlAst.AllocationDefinition =>
          val r: PreResult[Context, SysmlAst.PackageBodyElement] = preSysmlAstAllocationDefinition(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.PackageBodyElement)) => PreResult(preCtx, continu, Some[SysmlAst.PackageBodyElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.PackageBodyElement]())
          }
          return r
        case o: SysmlAst.ConnectionDefinition =>
          val r: PreResult[Context, SysmlAst.PackageBodyElement] = preSysmlAstConnectionDefinition(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.PackageBodyElement)) => PreResult(preCtx, continu, Some[SysmlAst.PackageBodyElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.PackageBodyElement]())
          }
          return r
        case o: SysmlAst.EnumerationDefinition =>
          val r: PreResult[Context, SysmlAst.PackageBodyElement] = preSysmlAstEnumerationDefinition(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.PackageBodyElement)) => PreResult(preCtx, continu, Some[SysmlAst.PackageBodyElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.PackageBodyElement]())
          }
          return r
        case o: SysmlAst.PartDefinition =>
          val r: PreResult[Context, SysmlAst.PackageBodyElement] = preSysmlAstPartDefinition(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.PackageBodyElement)) => PreResult(preCtx, continu, Some[SysmlAst.PackageBodyElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.PackageBodyElement]())
          }
          return r
        case o: SysmlAst.PortDefinition =>
          val r: PreResult[Context, SysmlAst.PackageBodyElement] = preSysmlAstPortDefinition(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.PackageBodyElement)) => PreResult(preCtx, continu, Some[SysmlAst.PackageBodyElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.PackageBodyElement]())
          }
          return r
        case o: SysmlAst.MetadataDefinition =>
          val r: PreResult[Context, SysmlAst.PackageBodyElement] = preSysmlAstMetadataDefinition(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.PackageBodyElement)) => PreResult(preCtx, continu, Some[SysmlAst.PackageBodyElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.PackageBodyElement]())
          }
          return r
        case o: SysmlAst.AttributeUsage =>
          val r: PreResult[Context, SysmlAst.PackageBodyElement] = preSysmlAstAttributeUsage(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.PackageBodyElement)) => PreResult(preCtx, continu, Some[SysmlAst.PackageBodyElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.PackageBodyElement]())
          }
          return r
        case o: SysmlAst.ReferenceUsage =>
          val r: PreResult[Context, SysmlAst.PackageBodyElement] = preSysmlAstReferenceUsage(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.PackageBodyElement)) => PreResult(preCtx, continu, Some[SysmlAst.PackageBodyElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.PackageBodyElement]())
          }
          return r
        case o: SysmlAst.ConnectionUsage =>
          val r: PreResult[Context, SysmlAst.PackageBodyElement] = preSysmlAstConnectionUsage(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.PackageBodyElement)) => PreResult(preCtx, continu, Some[SysmlAst.PackageBodyElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.PackageBodyElement]())
          }
          return r
        case o: SysmlAst.ItemUsage =>
          val r: PreResult[Context, SysmlAst.PackageBodyElement] = preSysmlAstItemUsage(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.PackageBodyElement)) => PreResult(preCtx, continu, Some[SysmlAst.PackageBodyElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.PackageBodyElement]())
          }
          return r
        case o: SysmlAst.PartUsage =>
          val r: PreResult[Context, SysmlAst.PackageBodyElement] = preSysmlAstPartUsage(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.PackageBodyElement)) => PreResult(preCtx, continu, Some[SysmlAst.PackageBodyElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.PackageBodyElement]())
          }
          return r
        case o: SysmlAst.PortUsage =>
          val r: PreResult[Context, SysmlAst.PackageBodyElement] = preSysmlAstPortUsage(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.PackageBodyElement)) => PreResult(preCtx, continu, Some[SysmlAst.PackageBodyElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.PackageBodyElement]())
          }
          return r
        case o: SysmlAst.Comment =>
          val r: PreResult[Context, SysmlAst.PackageBodyElement] = preSysmlAstComment(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.PackageBodyElement)) => PreResult(preCtx, continu, Some[SysmlAst.PackageBodyElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.PackageBodyElement]())
          }
          return r
        case o: SysmlAst.Documentation =>
          val r: PreResult[Context, SysmlAst.PackageBodyElement] = preSysmlAstDocumentation(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.PackageBodyElement)) => PreResult(preCtx, continu, Some[SysmlAst.PackageBodyElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.PackageBodyElement]())
          }
          return r
        case o: SysmlAst.TextualRepresentation =>
          val r: PreResult[Context, SysmlAst.PackageBodyElement] = preSysmlAstTextualRepresentation(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.PackageBodyElement)) => PreResult(preCtx, continu, Some[SysmlAst.PackageBodyElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.PackageBodyElement]())
          }
          return r
      }
    }

    @pure def preSysmlAstBodyElement(ctx: Context, o: SysmlAst.BodyElement): PreResult[Context, SysmlAst.BodyElement] = {
      o match {
        case o: SysmlAst.Import =>
          val r: PreResult[Context, SysmlAst.BodyElement] = preSysmlAstImport(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.BodyElement)) => PreResult(preCtx, continu, Some[SysmlAst.BodyElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.BodyElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.BodyElement]())
          }
          return r
        case o: SysmlAst.Alias =>
          val r: PreResult[Context, SysmlAst.BodyElement] = preSysmlAstAlias(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.BodyElement)) => PreResult(preCtx, continu, Some[SysmlAst.BodyElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.BodyElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.BodyElement]())
          }
          return r
        case o: SysmlAst.AttributeUsage =>
          val r: PreResult[Context, SysmlAst.BodyElement] = preSysmlAstAttributeUsage(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.BodyElement)) => PreResult(preCtx, continu, Some[SysmlAst.BodyElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.BodyElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.BodyElement]())
          }
          return r
        case o: SysmlAst.ReferenceUsage =>
          val r: PreResult[Context, SysmlAst.BodyElement] = preSysmlAstReferenceUsage(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.BodyElement)) => PreResult(preCtx, continu, Some[SysmlAst.BodyElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.BodyElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.BodyElement]())
          }
          return r
        case o: SysmlAst.ConnectionUsage =>
          val r: PreResult[Context, SysmlAst.BodyElement] = preSysmlAstConnectionUsage(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.BodyElement)) => PreResult(preCtx, continu, Some[SysmlAst.BodyElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.BodyElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.BodyElement]())
          }
          return r
        case o: SysmlAst.ItemUsage =>
          val r: PreResult[Context, SysmlAst.BodyElement] = preSysmlAstItemUsage(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.BodyElement)) => PreResult(preCtx, continu, Some[SysmlAst.BodyElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.BodyElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.BodyElement]())
          }
          return r
        case o: SysmlAst.PartUsage =>
          val r: PreResult[Context, SysmlAst.BodyElement] = preSysmlAstPartUsage(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.BodyElement)) => PreResult(preCtx, continu, Some[SysmlAst.BodyElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.BodyElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.BodyElement]())
          }
          return r
        case o: SysmlAst.PortUsage =>
          val r: PreResult[Context, SysmlAst.BodyElement] = preSysmlAstPortUsage(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.BodyElement)) => PreResult(preCtx, continu, Some[SysmlAst.BodyElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.BodyElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.BodyElement]())
          }
          return r
      }
    }

    @pure def preSysmlAstFeatureValue(ctx: Context, o: SysmlAst.FeatureValue): PreResult[Context, SysmlAst.FeatureValue] = {
      return PreResult(ctx, T, None())
    }

    @pure def preSysmlAstEnumeratedValue(ctx: Context, o: SysmlAst.EnumeratedValue): PreResult[Context, SysmlAst.EnumeratedValue] = {
      return PreResult(ctx, T, None())
    }

    @pure def preSysmlAstImport(ctx: Context, o: SysmlAst.Import): PreResult[Context, SysmlAst.Import] = {
      return PreResult(ctx, T, None())
    }

    @pure def preSysmlAstAlias(ctx: Context, o: SysmlAst.Alias): PreResult[Context, SysmlAst.Alias] = {
      return PreResult(ctx, T, None())
    }

    @pure def preSysmlAstIdentification(ctx: Context, o: SysmlAst.Identification): PreResult[Context, SysmlAst.Identification] = {
      return PreResult(ctx, T, None())
    }

    @pure def preSysmlAstConnectorPart(ctx: Context, o: SysmlAst.ConnectorPart): PreResult[Context, SysmlAst.ConnectorPart] = {
      o match {
        case o: SysmlAst.BinaryConnectorPart =>
          val r: PreResult[Context, SysmlAst.ConnectorPart] = preSysmlAstBinaryConnectorPart(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.ConnectorPart)) => PreResult(preCtx, continu, Some[SysmlAst.ConnectorPart](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.ConnectorPart")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.ConnectorPart]())
          }
          return r
        case o: SysmlAst.NaryConnectorPart =>
          val r: PreResult[Context, SysmlAst.ConnectorPart] = preSysmlAstNaryConnectorPart(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.ConnectorPart)) => PreResult(preCtx, continu, Some[SysmlAst.ConnectorPart](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.ConnectorPart")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.ConnectorPart]())
          }
          return r
      }
    }

    @pure def preSysmlAstConnectorEnd(ctx: Context, o: SysmlAst.ConnectorEnd): PreResult[Context, SysmlAst.ConnectorEnd] = {
      return PreResult(ctx, T, None())
    }

    @pure def preSysmlAstBinaryConnectorPart(ctx: Context, o: SysmlAst.BinaryConnectorPart): PreResult[Context, SysmlAst.BinaryConnectorPart] = {
      return PreResult(ctx, T, None())
    }

    @pure def preSysmlAstNaryConnectorPart(ctx: Context, o: SysmlAst.NaryConnectorPart): PreResult[Context, SysmlAst.NaryConnectorPart] = {
      return PreResult(ctx, T, None())
    }

    @pure def preSysmlAstFeatureSpecialization(ctx: Context, o: SysmlAst.FeatureSpecialization): PreResult[Context, SysmlAst.FeatureSpecialization] = {
      o match {
        case o: SysmlAst.TypingsSpecialization =>
          val r: PreResult[Context, SysmlAst.FeatureSpecialization] = preSysmlAstTypingsSpecialization(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.FeatureSpecialization)) => PreResult(preCtx, continu, Some[SysmlAst.FeatureSpecialization](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.FeatureSpecialization")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.FeatureSpecialization]())
          }
          return r
        case o: SysmlAst.SubsettingsSpecialization =>
          val r: PreResult[Context, SysmlAst.FeatureSpecialization] = preSysmlAstSubsettingsSpecialization(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.FeatureSpecialization)) => PreResult(preCtx, continu, Some[SysmlAst.FeatureSpecialization](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.FeatureSpecialization")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.FeatureSpecialization]())
          }
          return r
        case o: SysmlAst.ReferencesSpecialization =>
          val r: PreResult[Context, SysmlAst.FeatureSpecialization] = preSysmlAstReferencesSpecialization(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.FeatureSpecialization)) => PreResult(preCtx, continu, Some[SysmlAst.FeatureSpecialization](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.FeatureSpecialization")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.FeatureSpecialization]())
          }
          return r
        case o: SysmlAst.RedefinitionsSpecialization =>
          val r: PreResult[Context, SysmlAst.FeatureSpecialization] = preSysmlAstRedefinitionsSpecialization(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.FeatureSpecialization)) => PreResult(preCtx, continu, Some[SysmlAst.FeatureSpecialization](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.FeatureSpecialization")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.FeatureSpecialization]())
          }
          return r
      }
    }

    @pure def preSysmlAstTypingsSpecialization(ctx: Context, o: SysmlAst.TypingsSpecialization): PreResult[Context, SysmlAst.TypingsSpecialization] = {
      return PreResult(ctx, T, None())
    }

    @pure def preSysmlAstSubsettingsSpecialization(ctx: Context, o: SysmlAst.SubsettingsSpecialization): PreResult[Context, SysmlAst.SubsettingsSpecialization] = {
      return PreResult(ctx, T, None())
    }

    @pure def preSysmlAstReferencesSpecialization(ctx: Context, o: SysmlAst.ReferencesSpecialization): PreResult[Context, SysmlAst.ReferencesSpecialization] = {
      return PreResult(ctx, T, None())
    }

    @pure def preSysmlAstRedefinitionsSpecialization(ctx: Context, o: SysmlAst.RedefinitionsSpecialization): PreResult[Context, SysmlAst.RedefinitionsSpecialization] = {
      return PreResult(ctx, T, None())
    }

    @pure def preSysmlAstDefinitionElement(ctx: Context, o: SysmlAst.DefinitionElement): PreResult[Context, SysmlAst.DefinitionElement] = {
      o match {
        case o: SysmlAst.Package =>
          val r: PreResult[Context, SysmlAst.DefinitionElement] = preSysmlAstPackage(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.DefinitionElement)) => PreResult(preCtx, continu, Some[SysmlAst.DefinitionElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.DefinitionElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.DefinitionElement]())
          }
          return r
        case o: SysmlAst.AttributeDefinition =>
          val r: PreResult[Context, SysmlAst.DefinitionElement] = preSysmlAstAttributeDefinition(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.DefinitionElement)) => PreResult(preCtx, continu, Some[SysmlAst.DefinitionElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.DefinitionElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.DefinitionElement]())
          }
          return r
        case o: SysmlAst.AllocationDefinition =>
          val r: PreResult[Context, SysmlAst.DefinitionElement] = preSysmlAstAllocationDefinition(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.DefinitionElement)) => PreResult(preCtx, continu, Some[SysmlAst.DefinitionElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.DefinitionElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.DefinitionElement]())
          }
          return r
        case o: SysmlAst.ConnectionDefinition =>
          val r: PreResult[Context, SysmlAst.DefinitionElement] = preSysmlAstConnectionDefinition(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.DefinitionElement)) => PreResult(preCtx, continu, Some[SysmlAst.DefinitionElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.DefinitionElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.DefinitionElement]())
          }
          return r
        case o: SysmlAst.EnumerationDefinition =>
          val r: PreResult[Context, SysmlAst.DefinitionElement] = preSysmlAstEnumerationDefinition(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.DefinitionElement)) => PreResult(preCtx, continu, Some[SysmlAst.DefinitionElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.DefinitionElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.DefinitionElement]())
          }
          return r
        case o: SysmlAst.PartDefinition =>
          val r: PreResult[Context, SysmlAst.DefinitionElement] = preSysmlAstPartDefinition(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.DefinitionElement)) => PreResult(preCtx, continu, Some[SysmlAst.DefinitionElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.DefinitionElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.DefinitionElement]())
          }
          return r
        case o: SysmlAst.PortDefinition =>
          val r: PreResult[Context, SysmlAst.DefinitionElement] = preSysmlAstPortDefinition(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.DefinitionElement)) => PreResult(preCtx, continu, Some[SysmlAst.DefinitionElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.DefinitionElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.DefinitionElement]())
          }
          return r
        case o: SysmlAst.MetadataDefinition =>
          val r: PreResult[Context, SysmlAst.DefinitionElement] = preSysmlAstMetadataDefinition(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.DefinitionElement)) => PreResult(preCtx, continu, Some[SysmlAst.DefinitionElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.DefinitionElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.DefinitionElement]())
          }
          return r
        case o: SysmlAst.Comment =>
          val r: PreResult[Context, SysmlAst.DefinitionElement] = preSysmlAstComment(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.DefinitionElement)) => PreResult(preCtx, continu, Some[SysmlAst.DefinitionElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.DefinitionElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.DefinitionElement]())
          }
          return r
        case o: SysmlAst.Documentation =>
          val r: PreResult[Context, SysmlAst.DefinitionElement] = preSysmlAstDocumentation(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.DefinitionElement)) => PreResult(preCtx, continu, Some[SysmlAst.DefinitionElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.DefinitionElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.DefinitionElement]())
          }
          return r
        case o: SysmlAst.TextualRepresentation =>
          val r: PreResult[Context, SysmlAst.DefinitionElement] = preSysmlAstTextualRepresentation(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.DefinitionElement)) => PreResult(preCtx, continu, Some[SysmlAst.DefinitionElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.DefinitionElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.DefinitionElement]())
          }
          return r
      }
    }

    @pure def preSysmlAstDefinitionPrefix(ctx: Context, o: SysmlAst.DefinitionPrefix): PreResult[Context, SysmlAst.DefinitionPrefix] = {
      return PreResult(ctx, T, None())
    }

    @pure def preSysmlAstOccurrenceDefinitionPrefix(ctx: Context, o: SysmlAst.OccurrenceDefinitionPrefix): PreResult[Context, SysmlAst.OccurrenceDefinitionPrefix] = {
      return PreResult(ctx, T, None())
    }

    @pure def preSysmlAstPackage(ctx: Context, o: SysmlAst.Package): PreResult[Context, SysmlAst.Package] = {
      return PreResult(ctx, T, None())
    }

    @pure def preSysmlAstAttributeDefinition(ctx: Context, o: SysmlAst.AttributeDefinition): PreResult[Context, SysmlAst.AttributeDefinition] = {
      return PreResult(ctx, T, None())
    }

    @pure def preSysmlAstAllocationDefinition(ctx: Context, o: SysmlAst.AllocationDefinition): PreResult[Context, SysmlAst.AllocationDefinition] = {
      return PreResult(ctx, T, None())
    }

    @pure def preSysmlAstConnectionDefinition(ctx: Context, o: SysmlAst.ConnectionDefinition): PreResult[Context, SysmlAst.ConnectionDefinition] = {
      return PreResult(ctx, T, None())
    }

    @pure def preSysmlAstEnumerationDefinition(ctx: Context, o: SysmlAst.EnumerationDefinition): PreResult[Context, SysmlAst.EnumerationDefinition] = {
      return PreResult(ctx, T, None())
    }

    @pure def preSysmlAstPartDefinition(ctx: Context, o: SysmlAst.PartDefinition): PreResult[Context, SysmlAst.PartDefinition] = {
      return PreResult(ctx, T, None())
    }

    @pure def preSysmlAstPortDefinition(ctx: Context, o: SysmlAst.PortDefinition): PreResult[Context, SysmlAst.PortDefinition] = {
      return PreResult(ctx, T, None())
    }

    @pure def preSysmlAstMetadataDefinition(ctx: Context, o: SysmlAst.MetadataDefinition): PreResult[Context, SysmlAst.MetadataDefinition] = {
      return PreResult(ctx, T, None())
    }

    @pure def preSysmlAstUsagePrefix(ctx: Context, o: SysmlAst.UsagePrefix): PreResult[Context, SysmlAst.UsagePrefix] = {
      return PreResult(ctx, T, None())
    }

    @pure def preSysmlAstOccurrenceUsagePrefix(ctx: Context, o: SysmlAst.OccurrenceUsagePrefix): PreResult[Context, SysmlAst.OccurrenceUsagePrefix] = {
      return PreResult(ctx, T, None())
    }

    @pure def preSysmlAstRefPrefix(ctx: Context, o: SysmlAst.RefPrefix): PreResult[Context, SysmlAst.RefPrefix] = {
      return PreResult(ctx, T, None())
    }

    @pure def preSysmlAstUsageElement(ctx: Context, o: SysmlAst.UsageElement): PreResult[Context, SysmlAst.UsageElement] = {
      o match {
        case o: SysmlAst.AttributeUsage =>
          val r: PreResult[Context, SysmlAst.UsageElement] = preSysmlAstAttributeUsage(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.UsageElement)) => PreResult(preCtx, continu, Some[SysmlAst.UsageElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.UsageElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.UsageElement]())
          }
          return r
        case o: SysmlAst.ReferenceUsage =>
          val r: PreResult[Context, SysmlAst.UsageElement] = preSysmlAstReferenceUsage(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.UsageElement)) => PreResult(preCtx, continu, Some[SysmlAst.UsageElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.UsageElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.UsageElement]())
          }
          return r
        case o: SysmlAst.ConnectionUsage =>
          val r: PreResult[Context, SysmlAst.UsageElement] = preSysmlAstConnectionUsage(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.UsageElement)) => PreResult(preCtx, continu, Some[SysmlAst.UsageElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.UsageElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.UsageElement]())
          }
          return r
        case o: SysmlAst.ItemUsage =>
          val r: PreResult[Context, SysmlAst.UsageElement] = preSysmlAstItemUsage(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.UsageElement)) => PreResult(preCtx, continu, Some[SysmlAst.UsageElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.UsageElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.UsageElement]())
          }
          return r
        case o: SysmlAst.PartUsage =>
          val r: PreResult[Context, SysmlAst.UsageElement] = preSysmlAstPartUsage(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.UsageElement)) => PreResult(preCtx, continu, Some[SysmlAst.UsageElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.UsageElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.UsageElement]())
          }
          return r
        case o: SysmlAst.PortUsage =>
          val r: PreResult[Context, SysmlAst.UsageElement] = preSysmlAstPortUsage(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.UsageElement)) => PreResult(preCtx, continu, Some[SysmlAst.UsageElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.UsageElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.UsageElement]())
          }
          return r
      }
    }

    @pure def preSysmlAstNonOccurrenceUsageElement(ctx: Context, o: SysmlAst.NonOccurrenceUsageElement): PreResult[Context, SysmlAst.NonOccurrenceUsageElement] = {
      o match {
        case o: SysmlAst.AttributeUsage =>
          val r: PreResult[Context, SysmlAst.NonOccurrenceUsageElement] = preSysmlAstAttributeUsage(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.NonOccurrenceUsageElement)) => PreResult(preCtx, continu, Some[SysmlAst.NonOccurrenceUsageElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.NonOccurrenceUsageElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.NonOccurrenceUsageElement]())
          }
          return r
        case o: SysmlAst.ReferenceUsage =>
          val r: PreResult[Context, SysmlAst.NonOccurrenceUsageElement] = preSysmlAstReferenceUsage(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.NonOccurrenceUsageElement)) => PreResult(preCtx, continu, Some[SysmlAst.NonOccurrenceUsageElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.NonOccurrenceUsageElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.NonOccurrenceUsageElement]())
          }
          return r
      }
    }

    @pure def preSysmlAstAttributeUsage(ctx: Context, o: SysmlAst.AttributeUsage): PreResult[Context, SysmlAst.AttributeUsage] = {
      return PreResult(ctx, T, None())
    }

    @pure def preSysmlAstReferenceUsage(ctx: Context, o: SysmlAst.ReferenceUsage): PreResult[Context, SysmlAst.ReferenceUsage] = {
      return PreResult(ctx, T, None())
    }

    @pure def preSysmlAstOccurrenceUsageElement(ctx: Context, o: SysmlAst.OccurrenceUsageElement): PreResult[Context, SysmlAst.OccurrenceUsageElement] = {
      o match {
        case o: SysmlAst.ConnectionUsage =>
          val r: PreResult[Context, SysmlAst.OccurrenceUsageElement] = preSysmlAstConnectionUsage(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.OccurrenceUsageElement)) => PreResult(preCtx, continu, Some[SysmlAst.OccurrenceUsageElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.OccurrenceUsageElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.OccurrenceUsageElement]())
          }
          return r
        case o: SysmlAst.ItemUsage =>
          val r: PreResult[Context, SysmlAst.OccurrenceUsageElement] = preSysmlAstItemUsage(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.OccurrenceUsageElement)) => PreResult(preCtx, continu, Some[SysmlAst.OccurrenceUsageElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.OccurrenceUsageElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.OccurrenceUsageElement]())
          }
          return r
        case o: SysmlAst.PartUsage =>
          val r: PreResult[Context, SysmlAst.OccurrenceUsageElement] = preSysmlAstPartUsage(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.OccurrenceUsageElement)) => PreResult(preCtx, continu, Some[SysmlAst.OccurrenceUsageElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.OccurrenceUsageElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.OccurrenceUsageElement]())
          }
          return r
        case o: SysmlAst.PortUsage =>
          val r: PreResult[Context, SysmlAst.OccurrenceUsageElement] = preSysmlAstPortUsage(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.OccurrenceUsageElement)) => PreResult(preCtx, continu, Some[SysmlAst.OccurrenceUsageElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.OccurrenceUsageElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.OccurrenceUsageElement]())
          }
          return r
      }
    }

    @pure def preSysmlAstStructureUsageElement(ctx: Context, o: SysmlAst.StructureUsageElement): PreResult[Context, SysmlAst.StructureUsageElement] = {
      o match {
        case o: SysmlAst.ConnectionUsage =>
          val r: PreResult[Context, SysmlAst.StructureUsageElement] = preSysmlAstConnectionUsage(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.StructureUsageElement)) => PreResult(preCtx, continu, Some[SysmlAst.StructureUsageElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.StructureUsageElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.StructureUsageElement]())
          }
          return r
        case o: SysmlAst.ItemUsage =>
          val r: PreResult[Context, SysmlAst.StructureUsageElement] = preSysmlAstItemUsage(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.StructureUsageElement)) => PreResult(preCtx, continu, Some[SysmlAst.StructureUsageElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.StructureUsageElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.StructureUsageElement]())
          }
          return r
        case o: SysmlAst.PartUsage =>
          val r: PreResult[Context, SysmlAst.StructureUsageElement] = preSysmlAstPartUsage(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.StructureUsageElement)) => PreResult(preCtx, continu, Some[SysmlAst.StructureUsageElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.StructureUsageElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.StructureUsageElement]())
          }
          return r
        case o: SysmlAst.PortUsage =>
          val r: PreResult[Context, SysmlAst.StructureUsageElement] = preSysmlAstPortUsage(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.StructureUsageElement)) => PreResult(preCtx, continu, Some[SysmlAst.StructureUsageElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.StructureUsageElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.StructureUsageElement]())
          }
          return r
      }
    }

    @pure def preSysmlAstConnectionUsage(ctx: Context, o: SysmlAst.ConnectionUsage): PreResult[Context, SysmlAst.ConnectionUsage] = {
      return PreResult(ctx, T, None())
    }

    @pure def preSysmlAstItemUsage(ctx: Context, o: SysmlAst.ItemUsage): PreResult[Context, SysmlAst.ItemUsage] = {
      return PreResult(ctx, T, None())
    }

    @pure def preSysmlAstPartUsage(ctx: Context, o: SysmlAst.PartUsage): PreResult[Context, SysmlAst.PartUsage] = {
      return PreResult(ctx, T, None())
    }

    @pure def preSysmlAstPortUsage(ctx: Context, o: SysmlAst.PortUsage): PreResult[Context, SysmlAst.PortUsage] = {
      return PreResult(ctx, T, None())
    }

    @pure def preSysmlAstAnnotatingElement(ctx: Context, o: SysmlAst.AnnotatingElement): PreResult[Context, SysmlAst.AnnotatingElement] = {
      o match {
        case o: SysmlAst.Comment =>
          val r: PreResult[Context, SysmlAst.AnnotatingElement] = preSysmlAstComment(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.AnnotatingElement)) => PreResult(preCtx, continu, Some[SysmlAst.AnnotatingElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.AnnotatingElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.AnnotatingElement]())
          }
          return r
        case o: SysmlAst.Documentation =>
          val r: PreResult[Context, SysmlAst.AnnotatingElement] = preSysmlAstDocumentation(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.AnnotatingElement)) => PreResult(preCtx, continu, Some[SysmlAst.AnnotatingElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.AnnotatingElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.AnnotatingElement]())
          }
          return r
        case o: SysmlAst.TextualRepresentation =>
          val r: PreResult[Context, SysmlAst.AnnotatingElement] = preSysmlAstTextualRepresentation(ctx, o) match {
           case PreResult(preCtx, continu, Some(r: SysmlAst.AnnotatingElement)) => PreResult(preCtx, continu, Some[SysmlAst.AnnotatingElement](r))
           case PreResult(_, _, Some(_)) => halt("Can only produce object of type SysmlAst.AnnotatingElement")
           case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[SysmlAst.AnnotatingElement]())
          }
          return r
      }
    }

    @pure def preSysmlAstComment(ctx: Context, o: SysmlAst.Comment): PreResult[Context, SysmlAst.Comment] = {
      return PreResult(ctx, T, None())
    }

    @pure def preSysmlAstDocumentation(ctx: Context, o: SysmlAst.Documentation): PreResult[Context, SysmlAst.Documentation] = {
      return PreResult(ctx, T, None())
    }

    @pure def preSysmlAstTextualRepresentation(ctx: Context, o: SysmlAst.TextualRepresentation): PreResult[Context, SysmlAst.TextualRepresentation] = {
      return PreResult(ctx, T, None())
    }

    @pure def preAttr(ctx: Context, o: Attr): PreResult[Context, Attr] = {
      return PreResult(ctx, T, None())
    }

    @pure def preResolvedAttr(ctx: Context, o: ResolvedAttr): PreResult[Context, ResolvedAttr] = {
      return PreResult(ctx, T, None())
    }

    @pure def preResolvedInfo(ctx: Context, o: ResolvedInfo): PreResult[Context, ResolvedInfo] = {
      o match {
        case o: ResolvedInfo.Package => return preResolvedInfoPackage(ctx, o)
        case o: ResolvedInfo.Enum => return preResolvedInfoEnum(ctx, o)
        case o: ResolvedInfo.EnumElement => return preResolvedInfoEnumElement(ctx, o)
        case o: ResolvedInfo.AttributeUsage => return preResolvedInfoAttributeUsage(ctx, o)
        case o: ResolvedInfo.ConnectionUsage => return preResolvedInfoConnectionUsage(ctx, o)
        case o: ResolvedInfo.ItemUsage => return preResolvedInfoItemUsage(ctx, o)
        case o: ResolvedInfo.PartUsage => return preResolvedInfoPartUsage(ctx, o)
        case o: ResolvedInfo.PortUsage => return preResolvedInfoPortUsage(ctx, o)
        case o: ResolvedInfo.ReferenceUsage => return preResolvedInfoReferenceUsage(ctx, o)
      }
    }

    @pure def preResolvedInfoPackage(ctx: Context, o: ResolvedInfo.Package): PreResult[Context, ResolvedInfo] = {
      return PreResult(ctx, T, None())
    }

    @pure def preResolvedInfoEnum(ctx: Context, o: ResolvedInfo.Enum): PreResult[Context, ResolvedInfo] = {
      return PreResult(ctx, T, None())
    }

    @pure def preResolvedInfoEnumElement(ctx: Context, o: ResolvedInfo.EnumElement): PreResult[Context, ResolvedInfo] = {
      return PreResult(ctx, T, None())
    }

    @pure def preResolvedInfoAttributeUsage(ctx: Context, o: ResolvedInfo.AttributeUsage): PreResult[Context, ResolvedInfo] = {
      return PreResult(ctx, T, None())
    }

    @pure def preResolvedInfoConnectionUsage(ctx: Context, o: ResolvedInfo.ConnectionUsage): PreResult[Context, ResolvedInfo] = {
      return PreResult(ctx, T, None())
    }

    @pure def preResolvedInfoItemUsage(ctx: Context, o: ResolvedInfo.ItemUsage): PreResult[Context, ResolvedInfo] = {
      return PreResult(ctx, T, None())
    }

    @pure def preResolvedInfoPartUsage(ctx: Context, o: ResolvedInfo.PartUsage): PreResult[Context, ResolvedInfo] = {
      return PreResult(ctx, T, None())
    }

    @pure def preResolvedInfoPortUsage(ctx: Context, o: ResolvedInfo.PortUsage): PreResult[Context, ResolvedInfo] = {
      return PreResult(ctx, T, None())
    }

    @pure def preResolvedInfoReferenceUsage(ctx: Context, o: ResolvedInfo.ReferenceUsage): PreResult[Context, ResolvedInfo] = {
      return PreResult(ctx, T, None())
    }

    @pure def preType(ctx: Context, o: Type): PreResult[Context, Type] = {
      o match {
        case o: Type.Named => return preTypeNamed(ctx, o)
      }
    }

    @pure def preTypeNamed(ctx: Context, o: Type.Named): PreResult[Context, Type] = {
      return PreResult(ctx, T, None())
    }

    @pure def preTypedAttr(ctx: Context, o: TypedAttr): PreResult[Context, TypedAttr] = {
      return PreResult(ctx, T, None())
    }

    @pure def postSysmlAstId(ctx: Context, o: SysmlAst.Id): TPostResult[Context, SysmlAst.Id] = {
      return TPostResult(ctx, None())
    }

    @pure def postSysmlAstName(ctx: Context, o: SysmlAst.Name): TPostResult[Context, SysmlAst.Name] = {
      return TPostResult(ctx, None())
    }

    @pure def postSysmlAstTopUnit(ctx: Context, o: SysmlAst.TopUnit): TPostResult[Context, SysmlAst.TopUnit] = {
      return TPostResult(ctx, None())
    }

    @pure def postSysmlAstAttrNode(ctx: Context, o: SysmlAst.AttrNode): TPostResult[Context, SysmlAst.AttrNode] = {
      o match {
        case o: SysmlAst.Import =>
          val r: TPostResult[Context, SysmlAst.AttrNode] = postSysmlAstImport(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.AttrNode)) => TPostResult(postCtx, Some[SysmlAst.AttrNode](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.AttrNode]())
          }
          return r
        case o: SysmlAst.Alias =>
          val r: TPostResult[Context, SysmlAst.AttrNode] = postSysmlAstAlias(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.AttrNode)) => TPostResult(postCtx, Some[SysmlAst.AttrNode](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.AttrNode]())
          }
          return r
        case o: SysmlAst.Identification =>
          val r: TPostResult[Context, SysmlAst.AttrNode] = postSysmlAstIdentification(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.AttrNode)) => TPostResult(postCtx, Some[SysmlAst.AttrNode](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.AttrNode]())
          }
          return r
        case o: SysmlAst.Package =>
          val r: TPostResult[Context, SysmlAst.AttrNode] = postSysmlAstPackage(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.AttrNode)) => TPostResult(postCtx, Some[SysmlAst.AttrNode](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.AttrNode]())
          }
          return r
        case o: SysmlAst.AttributeDefinition =>
          val r: TPostResult[Context, SysmlAst.AttrNode] = postSysmlAstAttributeDefinition(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.AttrNode)) => TPostResult(postCtx, Some[SysmlAst.AttrNode](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.AttrNode]())
          }
          return r
        case o: SysmlAst.AllocationDefinition =>
          val r: TPostResult[Context, SysmlAst.AttrNode] = postSysmlAstAllocationDefinition(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.AttrNode)) => TPostResult(postCtx, Some[SysmlAst.AttrNode](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.AttrNode]())
          }
          return r
        case o: SysmlAst.ConnectionDefinition =>
          val r: TPostResult[Context, SysmlAst.AttrNode] = postSysmlAstConnectionDefinition(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.AttrNode)) => TPostResult(postCtx, Some[SysmlAst.AttrNode](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.AttrNode]())
          }
          return r
        case o: SysmlAst.EnumerationDefinition =>
          val r: TPostResult[Context, SysmlAst.AttrNode] = postSysmlAstEnumerationDefinition(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.AttrNode)) => TPostResult(postCtx, Some[SysmlAst.AttrNode](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.AttrNode]())
          }
          return r
        case o: SysmlAst.PartDefinition =>
          val r: TPostResult[Context, SysmlAst.AttrNode] = postSysmlAstPartDefinition(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.AttrNode)) => TPostResult(postCtx, Some[SysmlAst.AttrNode](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.AttrNode]())
          }
          return r
        case o: SysmlAst.PortDefinition =>
          val r: TPostResult[Context, SysmlAst.AttrNode] = postSysmlAstPortDefinition(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.AttrNode)) => TPostResult(postCtx, Some[SysmlAst.AttrNode](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.AttrNode]())
          }
          return r
        case o: SysmlAst.MetadataDefinition =>
          val r: TPostResult[Context, SysmlAst.AttrNode] = postSysmlAstMetadataDefinition(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.AttrNode)) => TPostResult(postCtx, Some[SysmlAst.AttrNode](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.AttrNode]())
          }
          return r
        case o: SysmlAst.AttributeUsage =>
          val r: TPostResult[Context, SysmlAst.AttrNode] = postSysmlAstAttributeUsage(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.AttrNode)) => TPostResult(postCtx, Some[SysmlAst.AttrNode](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.AttrNode]())
          }
          return r
        case o: SysmlAst.ReferenceUsage =>
          val r: TPostResult[Context, SysmlAst.AttrNode] = postSysmlAstReferenceUsage(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.AttrNode)) => TPostResult(postCtx, Some[SysmlAst.AttrNode](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.AttrNode]())
          }
          return r
        case o: SysmlAst.ConnectionUsage =>
          val r: TPostResult[Context, SysmlAst.AttrNode] = postSysmlAstConnectionUsage(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.AttrNode)) => TPostResult(postCtx, Some[SysmlAst.AttrNode](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.AttrNode]())
          }
          return r
        case o: SysmlAst.ItemUsage =>
          val r: TPostResult[Context, SysmlAst.AttrNode] = postSysmlAstItemUsage(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.AttrNode)) => TPostResult(postCtx, Some[SysmlAst.AttrNode](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.AttrNode]())
          }
          return r
        case o: SysmlAst.PartUsage =>
          val r: TPostResult[Context, SysmlAst.AttrNode] = postSysmlAstPartUsage(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.AttrNode)) => TPostResult(postCtx, Some[SysmlAst.AttrNode](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.AttrNode]())
          }
          return r
        case o: SysmlAst.PortUsage =>
          val r: TPostResult[Context, SysmlAst.AttrNode] = postSysmlAstPortUsage(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.AttrNode)) => TPostResult(postCtx, Some[SysmlAst.AttrNode](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.AttrNode]())
          }
          return r
        case o: SysmlAst.Comment =>
          val r: TPostResult[Context, SysmlAst.AttrNode] = postSysmlAstComment(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.AttrNode)) => TPostResult(postCtx, Some[SysmlAst.AttrNode](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.AttrNode]())
          }
          return r
        case o: SysmlAst.Documentation =>
          val r: TPostResult[Context, SysmlAst.AttrNode] = postSysmlAstDocumentation(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.AttrNode)) => TPostResult(postCtx, Some[SysmlAst.AttrNode](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.AttrNode]())
          }
          return r
        case o: SysmlAst.TextualRepresentation =>
          val r: TPostResult[Context, SysmlAst.AttrNode] = postSysmlAstTextualRepresentation(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.AttrNode)) => TPostResult(postCtx, Some[SysmlAst.AttrNode](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.AttrNode]())
          }
          return r
      }
    }

    @pure def postSysmlAstPackageBodyElement(ctx: Context, o: SysmlAst.PackageBodyElement): TPostResult[Context, SysmlAst.PackageBodyElement] = {
      o match {
        case o: SysmlAst.Import =>
          val r: TPostResult[Context, SysmlAst.PackageBodyElement] = postSysmlAstImport(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.PackageBodyElement)) => TPostResult(postCtx, Some[SysmlAst.PackageBodyElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.PackageBodyElement]())
          }
          return r
        case o: SysmlAst.Alias =>
          val r: TPostResult[Context, SysmlAst.PackageBodyElement] = postSysmlAstAlias(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.PackageBodyElement)) => TPostResult(postCtx, Some[SysmlAst.PackageBodyElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.PackageBodyElement]())
          }
          return r
        case o: SysmlAst.Package =>
          val r: TPostResult[Context, SysmlAst.PackageBodyElement] = postSysmlAstPackage(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.PackageBodyElement)) => TPostResult(postCtx, Some[SysmlAst.PackageBodyElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.PackageBodyElement]())
          }
          return r
        case o: SysmlAst.AttributeDefinition =>
          val r: TPostResult[Context, SysmlAst.PackageBodyElement] = postSysmlAstAttributeDefinition(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.PackageBodyElement)) => TPostResult(postCtx, Some[SysmlAst.PackageBodyElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.PackageBodyElement]())
          }
          return r
        case o: SysmlAst.AllocationDefinition =>
          val r: TPostResult[Context, SysmlAst.PackageBodyElement] = postSysmlAstAllocationDefinition(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.PackageBodyElement)) => TPostResult(postCtx, Some[SysmlAst.PackageBodyElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.PackageBodyElement]())
          }
          return r
        case o: SysmlAst.ConnectionDefinition =>
          val r: TPostResult[Context, SysmlAst.PackageBodyElement] = postSysmlAstConnectionDefinition(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.PackageBodyElement)) => TPostResult(postCtx, Some[SysmlAst.PackageBodyElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.PackageBodyElement]())
          }
          return r
        case o: SysmlAst.EnumerationDefinition =>
          val r: TPostResult[Context, SysmlAst.PackageBodyElement] = postSysmlAstEnumerationDefinition(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.PackageBodyElement)) => TPostResult(postCtx, Some[SysmlAst.PackageBodyElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.PackageBodyElement]())
          }
          return r
        case o: SysmlAst.PartDefinition =>
          val r: TPostResult[Context, SysmlAst.PackageBodyElement] = postSysmlAstPartDefinition(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.PackageBodyElement)) => TPostResult(postCtx, Some[SysmlAst.PackageBodyElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.PackageBodyElement]())
          }
          return r
        case o: SysmlAst.PortDefinition =>
          val r: TPostResult[Context, SysmlAst.PackageBodyElement] = postSysmlAstPortDefinition(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.PackageBodyElement)) => TPostResult(postCtx, Some[SysmlAst.PackageBodyElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.PackageBodyElement]())
          }
          return r
        case o: SysmlAst.MetadataDefinition =>
          val r: TPostResult[Context, SysmlAst.PackageBodyElement] = postSysmlAstMetadataDefinition(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.PackageBodyElement)) => TPostResult(postCtx, Some[SysmlAst.PackageBodyElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.PackageBodyElement]())
          }
          return r
        case o: SysmlAst.AttributeUsage =>
          val r: TPostResult[Context, SysmlAst.PackageBodyElement] = postSysmlAstAttributeUsage(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.PackageBodyElement)) => TPostResult(postCtx, Some[SysmlAst.PackageBodyElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.PackageBodyElement]())
          }
          return r
        case o: SysmlAst.ReferenceUsage =>
          val r: TPostResult[Context, SysmlAst.PackageBodyElement] = postSysmlAstReferenceUsage(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.PackageBodyElement)) => TPostResult(postCtx, Some[SysmlAst.PackageBodyElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.PackageBodyElement]())
          }
          return r
        case o: SysmlAst.ConnectionUsage =>
          val r: TPostResult[Context, SysmlAst.PackageBodyElement] = postSysmlAstConnectionUsage(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.PackageBodyElement)) => TPostResult(postCtx, Some[SysmlAst.PackageBodyElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.PackageBodyElement]())
          }
          return r
        case o: SysmlAst.ItemUsage =>
          val r: TPostResult[Context, SysmlAst.PackageBodyElement] = postSysmlAstItemUsage(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.PackageBodyElement)) => TPostResult(postCtx, Some[SysmlAst.PackageBodyElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.PackageBodyElement]())
          }
          return r
        case o: SysmlAst.PartUsage =>
          val r: TPostResult[Context, SysmlAst.PackageBodyElement] = postSysmlAstPartUsage(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.PackageBodyElement)) => TPostResult(postCtx, Some[SysmlAst.PackageBodyElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.PackageBodyElement]())
          }
          return r
        case o: SysmlAst.PortUsage =>
          val r: TPostResult[Context, SysmlAst.PackageBodyElement] = postSysmlAstPortUsage(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.PackageBodyElement)) => TPostResult(postCtx, Some[SysmlAst.PackageBodyElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.PackageBodyElement]())
          }
          return r
        case o: SysmlAst.Comment =>
          val r: TPostResult[Context, SysmlAst.PackageBodyElement] = postSysmlAstComment(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.PackageBodyElement)) => TPostResult(postCtx, Some[SysmlAst.PackageBodyElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.PackageBodyElement]())
          }
          return r
        case o: SysmlAst.Documentation =>
          val r: TPostResult[Context, SysmlAst.PackageBodyElement] = postSysmlAstDocumentation(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.PackageBodyElement)) => TPostResult(postCtx, Some[SysmlAst.PackageBodyElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.PackageBodyElement]())
          }
          return r
        case o: SysmlAst.TextualRepresentation =>
          val r: TPostResult[Context, SysmlAst.PackageBodyElement] = postSysmlAstTextualRepresentation(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.PackageBodyElement)) => TPostResult(postCtx, Some[SysmlAst.PackageBodyElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.PackageBodyElement]())
          }
          return r
      }
    }

    @pure def postSysmlAstBodyElement(ctx: Context, o: SysmlAst.BodyElement): TPostResult[Context, SysmlAst.BodyElement] = {
      o match {
        case o: SysmlAst.Import =>
          val r: TPostResult[Context, SysmlAst.BodyElement] = postSysmlAstImport(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.BodyElement)) => TPostResult(postCtx, Some[SysmlAst.BodyElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.BodyElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.BodyElement]())
          }
          return r
        case o: SysmlAst.Alias =>
          val r: TPostResult[Context, SysmlAst.BodyElement] = postSysmlAstAlias(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.BodyElement)) => TPostResult(postCtx, Some[SysmlAst.BodyElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.BodyElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.BodyElement]())
          }
          return r
        case o: SysmlAst.AttributeUsage =>
          val r: TPostResult[Context, SysmlAst.BodyElement] = postSysmlAstAttributeUsage(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.BodyElement)) => TPostResult(postCtx, Some[SysmlAst.BodyElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.BodyElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.BodyElement]())
          }
          return r
        case o: SysmlAst.ReferenceUsage =>
          val r: TPostResult[Context, SysmlAst.BodyElement] = postSysmlAstReferenceUsage(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.BodyElement)) => TPostResult(postCtx, Some[SysmlAst.BodyElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.BodyElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.BodyElement]())
          }
          return r
        case o: SysmlAst.ConnectionUsage =>
          val r: TPostResult[Context, SysmlAst.BodyElement] = postSysmlAstConnectionUsage(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.BodyElement)) => TPostResult(postCtx, Some[SysmlAst.BodyElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.BodyElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.BodyElement]())
          }
          return r
        case o: SysmlAst.ItemUsage =>
          val r: TPostResult[Context, SysmlAst.BodyElement] = postSysmlAstItemUsage(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.BodyElement)) => TPostResult(postCtx, Some[SysmlAst.BodyElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.BodyElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.BodyElement]())
          }
          return r
        case o: SysmlAst.PartUsage =>
          val r: TPostResult[Context, SysmlAst.BodyElement] = postSysmlAstPartUsage(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.BodyElement)) => TPostResult(postCtx, Some[SysmlAst.BodyElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.BodyElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.BodyElement]())
          }
          return r
        case o: SysmlAst.PortUsage =>
          val r: TPostResult[Context, SysmlAst.BodyElement] = postSysmlAstPortUsage(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.BodyElement)) => TPostResult(postCtx, Some[SysmlAst.BodyElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.BodyElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.BodyElement]())
          }
          return r
      }
    }

    @pure def postSysmlAstFeatureValue(ctx: Context, o: SysmlAst.FeatureValue): TPostResult[Context, SysmlAst.FeatureValue] = {
      return TPostResult(ctx, None())
    }

    @pure def postSysmlAstEnumeratedValue(ctx: Context, o: SysmlAst.EnumeratedValue): TPostResult[Context, SysmlAst.EnumeratedValue] = {
      return TPostResult(ctx, None())
    }

    @pure def postSysmlAstImport(ctx: Context, o: SysmlAst.Import): TPostResult[Context, SysmlAst.Import] = {
      return TPostResult(ctx, None())
    }

    @pure def postSysmlAstAlias(ctx: Context, o: SysmlAst.Alias): TPostResult[Context, SysmlAst.Alias] = {
      return TPostResult(ctx, None())
    }

    @pure def postSysmlAstIdentification(ctx: Context, o: SysmlAst.Identification): TPostResult[Context, SysmlAst.Identification] = {
      return TPostResult(ctx, None())
    }

    @pure def postSysmlAstConnectorPart(ctx: Context, o: SysmlAst.ConnectorPart): TPostResult[Context, SysmlAst.ConnectorPart] = {
      o match {
        case o: SysmlAst.BinaryConnectorPart =>
          val r: TPostResult[Context, SysmlAst.ConnectorPart] = postSysmlAstBinaryConnectorPart(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.ConnectorPart)) => TPostResult(postCtx, Some[SysmlAst.ConnectorPart](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.ConnectorPart")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.ConnectorPart]())
          }
          return r
        case o: SysmlAst.NaryConnectorPart =>
          val r: TPostResult[Context, SysmlAst.ConnectorPart] = postSysmlAstNaryConnectorPart(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.ConnectorPart)) => TPostResult(postCtx, Some[SysmlAst.ConnectorPart](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.ConnectorPart")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.ConnectorPart]())
          }
          return r
      }
    }

    @pure def postSysmlAstConnectorEnd(ctx: Context, o: SysmlAst.ConnectorEnd): TPostResult[Context, SysmlAst.ConnectorEnd] = {
      return TPostResult(ctx, None())
    }

    @pure def postSysmlAstBinaryConnectorPart(ctx: Context, o: SysmlAst.BinaryConnectorPart): TPostResult[Context, SysmlAst.BinaryConnectorPart] = {
      return TPostResult(ctx, None())
    }

    @pure def postSysmlAstNaryConnectorPart(ctx: Context, o: SysmlAst.NaryConnectorPart): TPostResult[Context, SysmlAst.NaryConnectorPart] = {
      return TPostResult(ctx, None())
    }

    @pure def postSysmlAstFeatureSpecialization(ctx: Context, o: SysmlAst.FeatureSpecialization): TPostResult[Context, SysmlAst.FeatureSpecialization] = {
      o match {
        case o: SysmlAst.TypingsSpecialization =>
          val r: TPostResult[Context, SysmlAst.FeatureSpecialization] = postSysmlAstTypingsSpecialization(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.FeatureSpecialization)) => TPostResult(postCtx, Some[SysmlAst.FeatureSpecialization](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.FeatureSpecialization")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.FeatureSpecialization]())
          }
          return r
        case o: SysmlAst.SubsettingsSpecialization =>
          val r: TPostResult[Context, SysmlAst.FeatureSpecialization] = postSysmlAstSubsettingsSpecialization(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.FeatureSpecialization)) => TPostResult(postCtx, Some[SysmlAst.FeatureSpecialization](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.FeatureSpecialization")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.FeatureSpecialization]())
          }
          return r
        case o: SysmlAst.ReferencesSpecialization =>
          val r: TPostResult[Context, SysmlAst.FeatureSpecialization] = postSysmlAstReferencesSpecialization(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.FeatureSpecialization)) => TPostResult(postCtx, Some[SysmlAst.FeatureSpecialization](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.FeatureSpecialization")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.FeatureSpecialization]())
          }
          return r
        case o: SysmlAst.RedefinitionsSpecialization =>
          val r: TPostResult[Context, SysmlAst.FeatureSpecialization] = postSysmlAstRedefinitionsSpecialization(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.FeatureSpecialization)) => TPostResult(postCtx, Some[SysmlAst.FeatureSpecialization](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.FeatureSpecialization")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.FeatureSpecialization]())
          }
          return r
      }
    }

    @pure def postSysmlAstTypingsSpecialization(ctx: Context, o: SysmlAst.TypingsSpecialization): TPostResult[Context, SysmlAst.TypingsSpecialization] = {
      return TPostResult(ctx, None())
    }

    @pure def postSysmlAstSubsettingsSpecialization(ctx: Context, o: SysmlAst.SubsettingsSpecialization): TPostResult[Context, SysmlAst.SubsettingsSpecialization] = {
      return TPostResult(ctx, None())
    }

    @pure def postSysmlAstReferencesSpecialization(ctx: Context, o: SysmlAst.ReferencesSpecialization): TPostResult[Context, SysmlAst.ReferencesSpecialization] = {
      return TPostResult(ctx, None())
    }

    @pure def postSysmlAstRedefinitionsSpecialization(ctx: Context, o: SysmlAst.RedefinitionsSpecialization): TPostResult[Context, SysmlAst.RedefinitionsSpecialization] = {
      return TPostResult(ctx, None())
    }

    @pure def postSysmlAstDefinitionElement(ctx: Context, o: SysmlAst.DefinitionElement): TPostResult[Context, SysmlAst.DefinitionElement] = {
      o match {
        case o: SysmlAst.Package =>
          val r: TPostResult[Context, SysmlAst.DefinitionElement] = postSysmlAstPackage(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.DefinitionElement)) => TPostResult(postCtx, Some[SysmlAst.DefinitionElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.DefinitionElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.DefinitionElement]())
          }
          return r
        case o: SysmlAst.AttributeDefinition =>
          val r: TPostResult[Context, SysmlAst.DefinitionElement] = postSysmlAstAttributeDefinition(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.DefinitionElement)) => TPostResult(postCtx, Some[SysmlAst.DefinitionElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.DefinitionElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.DefinitionElement]())
          }
          return r
        case o: SysmlAst.AllocationDefinition =>
          val r: TPostResult[Context, SysmlAst.DefinitionElement] = postSysmlAstAllocationDefinition(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.DefinitionElement)) => TPostResult(postCtx, Some[SysmlAst.DefinitionElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.DefinitionElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.DefinitionElement]())
          }
          return r
        case o: SysmlAst.ConnectionDefinition =>
          val r: TPostResult[Context, SysmlAst.DefinitionElement] = postSysmlAstConnectionDefinition(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.DefinitionElement)) => TPostResult(postCtx, Some[SysmlAst.DefinitionElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.DefinitionElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.DefinitionElement]())
          }
          return r
        case o: SysmlAst.EnumerationDefinition =>
          val r: TPostResult[Context, SysmlAst.DefinitionElement] = postSysmlAstEnumerationDefinition(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.DefinitionElement)) => TPostResult(postCtx, Some[SysmlAst.DefinitionElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.DefinitionElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.DefinitionElement]())
          }
          return r
        case o: SysmlAst.PartDefinition =>
          val r: TPostResult[Context, SysmlAst.DefinitionElement] = postSysmlAstPartDefinition(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.DefinitionElement)) => TPostResult(postCtx, Some[SysmlAst.DefinitionElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.DefinitionElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.DefinitionElement]())
          }
          return r
        case o: SysmlAst.PortDefinition =>
          val r: TPostResult[Context, SysmlAst.DefinitionElement] = postSysmlAstPortDefinition(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.DefinitionElement)) => TPostResult(postCtx, Some[SysmlAst.DefinitionElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.DefinitionElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.DefinitionElement]())
          }
          return r
        case o: SysmlAst.MetadataDefinition =>
          val r: TPostResult[Context, SysmlAst.DefinitionElement] = postSysmlAstMetadataDefinition(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.DefinitionElement)) => TPostResult(postCtx, Some[SysmlAst.DefinitionElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.DefinitionElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.DefinitionElement]())
          }
          return r
        case o: SysmlAst.Comment =>
          val r: TPostResult[Context, SysmlAst.DefinitionElement] = postSysmlAstComment(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.DefinitionElement)) => TPostResult(postCtx, Some[SysmlAst.DefinitionElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.DefinitionElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.DefinitionElement]())
          }
          return r
        case o: SysmlAst.Documentation =>
          val r: TPostResult[Context, SysmlAst.DefinitionElement] = postSysmlAstDocumentation(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.DefinitionElement)) => TPostResult(postCtx, Some[SysmlAst.DefinitionElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.DefinitionElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.DefinitionElement]())
          }
          return r
        case o: SysmlAst.TextualRepresentation =>
          val r: TPostResult[Context, SysmlAst.DefinitionElement] = postSysmlAstTextualRepresentation(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.DefinitionElement)) => TPostResult(postCtx, Some[SysmlAst.DefinitionElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.DefinitionElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.DefinitionElement]())
          }
          return r
      }
    }

    @pure def postSysmlAstDefinitionPrefix(ctx: Context, o: SysmlAst.DefinitionPrefix): TPostResult[Context, SysmlAst.DefinitionPrefix] = {
      return TPostResult(ctx, None())
    }

    @pure def postSysmlAstOccurrenceDefinitionPrefix(ctx: Context, o: SysmlAst.OccurrenceDefinitionPrefix): TPostResult[Context, SysmlAst.OccurrenceDefinitionPrefix] = {
      return TPostResult(ctx, None())
    }

    @pure def postSysmlAstPackage(ctx: Context, o: SysmlAst.Package): TPostResult[Context, SysmlAst.Package] = {
      return TPostResult(ctx, None())
    }

    @pure def postSysmlAstAttributeDefinition(ctx: Context, o: SysmlAst.AttributeDefinition): TPostResult[Context, SysmlAst.AttributeDefinition] = {
      return TPostResult(ctx, None())
    }

    @pure def postSysmlAstAllocationDefinition(ctx: Context, o: SysmlAst.AllocationDefinition): TPostResult[Context, SysmlAst.AllocationDefinition] = {
      return TPostResult(ctx, None())
    }

    @pure def postSysmlAstConnectionDefinition(ctx: Context, o: SysmlAst.ConnectionDefinition): TPostResult[Context, SysmlAst.ConnectionDefinition] = {
      return TPostResult(ctx, None())
    }

    @pure def postSysmlAstEnumerationDefinition(ctx: Context, o: SysmlAst.EnumerationDefinition): TPostResult[Context, SysmlAst.EnumerationDefinition] = {
      return TPostResult(ctx, None())
    }

    @pure def postSysmlAstPartDefinition(ctx: Context, o: SysmlAst.PartDefinition): TPostResult[Context, SysmlAst.PartDefinition] = {
      return TPostResult(ctx, None())
    }

    @pure def postSysmlAstPortDefinition(ctx: Context, o: SysmlAst.PortDefinition): TPostResult[Context, SysmlAst.PortDefinition] = {
      return TPostResult(ctx, None())
    }

    @pure def postSysmlAstMetadataDefinition(ctx: Context, o: SysmlAst.MetadataDefinition): TPostResult[Context, SysmlAst.MetadataDefinition] = {
      return TPostResult(ctx, None())
    }

    @pure def postSysmlAstUsagePrefix(ctx: Context, o: SysmlAst.UsagePrefix): TPostResult[Context, SysmlAst.UsagePrefix] = {
      return TPostResult(ctx, None())
    }

    @pure def postSysmlAstOccurrenceUsagePrefix(ctx: Context, o: SysmlAst.OccurrenceUsagePrefix): TPostResult[Context, SysmlAst.OccurrenceUsagePrefix] = {
      return TPostResult(ctx, None())
    }

    @pure def postSysmlAstRefPrefix(ctx: Context, o: SysmlAst.RefPrefix): TPostResult[Context, SysmlAst.RefPrefix] = {
      return TPostResult(ctx, None())
    }

    @pure def postSysmlAstUsageElement(ctx: Context, o: SysmlAst.UsageElement): TPostResult[Context, SysmlAst.UsageElement] = {
      o match {
        case o: SysmlAst.AttributeUsage =>
          val r: TPostResult[Context, SysmlAst.UsageElement] = postSysmlAstAttributeUsage(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.UsageElement)) => TPostResult(postCtx, Some[SysmlAst.UsageElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.UsageElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.UsageElement]())
          }
          return r
        case o: SysmlAst.ReferenceUsage =>
          val r: TPostResult[Context, SysmlAst.UsageElement] = postSysmlAstReferenceUsage(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.UsageElement)) => TPostResult(postCtx, Some[SysmlAst.UsageElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.UsageElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.UsageElement]())
          }
          return r
        case o: SysmlAst.ConnectionUsage =>
          val r: TPostResult[Context, SysmlAst.UsageElement] = postSysmlAstConnectionUsage(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.UsageElement)) => TPostResult(postCtx, Some[SysmlAst.UsageElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.UsageElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.UsageElement]())
          }
          return r
        case o: SysmlAst.ItemUsage =>
          val r: TPostResult[Context, SysmlAst.UsageElement] = postSysmlAstItemUsage(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.UsageElement)) => TPostResult(postCtx, Some[SysmlAst.UsageElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.UsageElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.UsageElement]())
          }
          return r
        case o: SysmlAst.PartUsage =>
          val r: TPostResult[Context, SysmlAst.UsageElement] = postSysmlAstPartUsage(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.UsageElement)) => TPostResult(postCtx, Some[SysmlAst.UsageElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.UsageElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.UsageElement]())
          }
          return r
        case o: SysmlAst.PortUsage =>
          val r: TPostResult[Context, SysmlAst.UsageElement] = postSysmlAstPortUsage(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.UsageElement)) => TPostResult(postCtx, Some[SysmlAst.UsageElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.UsageElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.UsageElement]())
          }
          return r
      }
    }

    @pure def postSysmlAstNonOccurrenceUsageElement(ctx: Context, o: SysmlAst.NonOccurrenceUsageElement): TPostResult[Context, SysmlAst.NonOccurrenceUsageElement] = {
      o match {
        case o: SysmlAst.AttributeUsage =>
          val r: TPostResult[Context, SysmlAst.NonOccurrenceUsageElement] = postSysmlAstAttributeUsage(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.NonOccurrenceUsageElement)) => TPostResult(postCtx, Some[SysmlAst.NonOccurrenceUsageElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.NonOccurrenceUsageElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.NonOccurrenceUsageElement]())
          }
          return r
        case o: SysmlAst.ReferenceUsage =>
          val r: TPostResult[Context, SysmlAst.NonOccurrenceUsageElement] = postSysmlAstReferenceUsage(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.NonOccurrenceUsageElement)) => TPostResult(postCtx, Some[SysmlAst.NonOccurrenceUsageElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.NonOccurrenceUsageElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.NonOccurrenceUsageElement]())
          }
          return r
      }
    }

    @pure def postSysmlAstAttributeUsage(ctx: Context, o: SysmlAst.AttributeUsage): TPostResult[Context, SysmlAst.AttributeUsage] = {
      return TPostResult(ctx, None())
    }

    @pure def postSysmlAstReferenceUsage(ctx: Context, o: SysmlAst.ReferenceUsage): TPostResult[Context, SysmlAst.ReferenceUsage] = {
      return TPostResult(ctx, None())
    }

    @pure def postSysmlAstOccurrenceUsageElement(ctx: Context, o: SysmlAst.OccurrenceUsageElement): TPostResult[Context, SysmlAst.OccurrenceUsageElement] = {
      o match {
        case o: SysmlAst.ConnectionUsage =>
          val r: TPostResult[Context, SysmlAst.OccurrenceUsageElement] = postSysmlAstConnectionUsage(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.OccurrenceUsageElement)) => TPostResult(postCtx, Some[SysmlAst.OccurrenceUsageElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.OccurrenceUsageElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.OccurrenceUsageElement]())
          }
          return r
        case o: SysmlAst.ItemUsage =>
          val r: TPostResult[Context, SysmlAst.OccurrenceUsageElement] = postSysmlAstItemUsage(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.OccurrenceUsageElement)) => TPostResult(postCtx, Some[SysmlAst.OccurrenceUsageElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.OccurrenceUsageElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.OccurrenceUsageElement]())
          }
          return r
        case o: SysmlAst.PartUsage =>
          val r: TPostResult[Context, SysmlAst.OccurrenceUsageElement] = postSysmlAstPartUsage(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.OccurrenceUsageElement)) => TPostResult(postCtx, Some[SysmlAst.OccurrenceUsageElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.OccurrenceUsageElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.OccurrenceUsageElement]())
          }
          return r
        case o: SysmlAst.PortUsage =>
          val r: TPostResult[Context, SysmlAst.OccurrenceUsageElement] = postSysmlAstPortUsage(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.OccurrenceUsageElement)) => TPostResult(postCtx, Some[SysmlAst.OccurrenceUsageElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.OccurrenceUsageElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.OccurrenceUsageElement]())
          }
          return r
      }
    }

    @pure def postSysmlAstStructureUsageElement(ctx: Context, o: SysmlAst.StructureUsageElement): TPostResult[Context, SysmlAst.StructureUsageElement] = {
      o match {
        case o: SysmlAst.ConnectionUsage =>
          val r: TPostResult[Context, SysmlAst.StructureUsageElement] = postSysmlAstConnectionUsage(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.StructureUsageElement)) => TPostResult(postCtx, Some[SysmlAst.StructureUsageElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.StructureUsageElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.StructureUsageElement]())
          }
          return r
        case o: SysmlAst.ItemUsage =>
          val r: TPostResult[Context, SysmlAst.StructureUsageElement] = postSysmlAstItemUsage(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.StructureUsageElement)) => TPostResult(postCtx, Some[SysmlAst.StructureUsageElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.StructureUsageElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.StructureUsageElement]())
          }
          return r
        case o: SysmlAst.PartUsage =>
          val r: TPostResult[Context, SysmlAst.StructureUsageElement] = postSysmlAstPartUsage(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.StructureUsageElement)) => TPostResult(postCtx, Some[SysmlAst.StructureUsageElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.StructureUsageElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.StructureUsageElement]())
          }
          return r
        case o: SysmlAst.PortUsage =>
          val r: TPostResult[Context, SysmlAst.StructureUsageElement] = postSysmlAstPortUsage(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.StructureUsageElement)) => TPostResult(postCtx, Some[SysmlAst.StructureUsageElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.StructureUsageElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.StructureUsageElement]())
          }
          return r
      }
    }

    @pure def postSysmlAstConnectionUsage(ctx: Context, o: SysmlAst.ConnectionUsage): TPostResult[Context, SysmlAst.ConnectionUsage] = {
      return TPostResult(ctx, None())
    }

    @pure def postSysmlAstItemUsage(ctx: Context, o: SysmlAst.ItemUsage): TPostResult[Context, SysmlAst.ItemUsage] = {
      return TPostResult(ctx, None())
    }

    @pure def postSysmlAstPartUsage(ctx: Context, o: SysmlAst.PartUsage): TPostResult[Context, SysmlAst.PartUsage] = {
      return TPostResult(ctx, None())
    }

    @pure def postSysmlAstPortUsage(ctx: Context, o: SysmlAst.PortUsage): TPostResult[Context, SysmlAst.PortUsage] = {
      return TPostResult(ctx, None())
    }

    @pure def postSysmlAstAnnotatingElement(ctx: Context, o: SysmlAst.AnnotatingElement): TPostResult[Context, SysmlAst.AnnotatingElement] = {
      o match {
        case o: SysmlAst.Comment =>
          val r: TPostResult[Context, SysmlAst.AnnotatingElement] = postSysmlAstComment(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.AnnotatingElement)) => TPostResult(postCtx, Some[SysmlAst.AnnotatingElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.AnnotatingElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.AnnotatingElement]())
          }
          return r
        case o: SysmlAst.Documentation =>
          val r: TPostResult[Context, SysmlAst.AnnotatingElement] = postSysmlAstDocumentation(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.AnnotatingElement)) => TPostResult(postCtx, Some[SysmlAst.AnnotatingElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.AnnotatingElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.AnnotatingElement]())
          }
          return r
        case o: SysmlAst.TextualRepresentation =>
          val r: TPostResult[Context, SysmlAst.AnnotatingElement] = postSysmlAstTextualRepresentation(ctx, o) match {
           case TPostResult(postCtx, Some(result: SysmlAst.AnnotatingElement)) => TPostResult(postCtx, Some[SysmlAst.AnnotatingElement](result))
           case TPostResult(_, Some(_)) => halt("Can only produce object of type SysmlAst.AnnotatingElement")
           case TPostResult(postCtx, _) => TPostResult(postCtx, None[SysmlAst.AnnotatingElement]())
          }
          return r
      }
    }

    @pure def postSysmlAstComment(ctx: Context, o: SysmlAst.Comment): TPostResult[Context, SysmlAst.Comment] = {
      return TPostResult(ctx, None())
    }

    @pure def postSysmlAstDocumentation(ctx: Context, o: SysmlAst.Documentation): TPostResult[Context, SysmlAst.Documentation] = {
      return TPostResult(ctx, None())
    }

    @pure def postSysmlAstTextualRepresentation(ctx: Context, o: SysmlAst.TextualRepresentation): TPostResult[Context, SysmlAst.TextualRepresentation] = {
      return TPostResult(ctx, None())
    }

    @pure def postAttr(ctx: Context, o: Attr): TPostResult[Context, Attr] = {
      return TPostResult(ctx, None())
    }

    @pure def postResolvedAttr(ctx: Context, o: ResolvedAttr): TPostResult[Context, ResolvedAttr] = {
      return TPostResult(ctx, None())
    }

    @pure def postResolvedInfo(ctx: Context, o: ResolvedInfo): TPostResult[Context, ResolvedInfo] = {
      o match {
        case o: ResolvedInfo.Package => return postResolvedInfoPackage(ctx, o)
        case o: ResolvedInfo.Enum => return postResolvedInfoEnum(ctx, o)
        case o: ResolvedInfo.EnumElement => return postResolvedInfoEnumElement(ctx, o)
        case o: ResolvedInfo.AttributeUsage => return postResolvedInfoAttributeUsage(ctx, o)
        case o: ResolvedInfo.ConnectionUsage => return postResolvedInfoConnectionUsage(ctx, o)
        case o: ResolvedInfo.ItemUsage => return postResolvedInfoItemUsage(ctx, o)
        case o: ResolvedInfo.PartUsage => return postResolvedInfoPartUsage(ctx, o)
        case o: ResolvedInfo.PortUsage => return postResolvedInfoPortUsage(ctx, o)
        case o: ResolvedInfo.ReferenceUsage => return postResolvedInfoReferenceUsage(ctx, o)
      }
    }

    @pure def postResolvedInfoPackage(ctx: Context, o: ResolvedInfo.Package): TPostResult[Context, ResolvedInfo] = {
      return TPostResult(ctx, None())
    }

    @pure def postResolvedInfoEnum(ctx: Context, o: ResolvedInfo.Enum): TPostResult[Context, ResolvedInfo] = {
      return TPostResult(ctx, None())
    }

    @pure def postResolvedInfoEnumElement(ctx: Context, o: ResolvedInfo.EnumElement): TPostResult[Context, ResolvedInfo] = {
      return TPostResult(ctx, None())
    }

    @pure def postResolvedInfoAttributeUsage(ctx: Context, o: ResolvedInfo.AttributeUsage): TPostResult[Context, ResolvedInfo] = {
      return TPostResult(ctx, None())
    }

    @pure def postResolvedInfoConnectionUsage(ctx: Context, o: ResolvedInfo.ConnectionUsage): TPostResult[Context, ResolvedInfo] = {
      return TPostResult(ctx, None())
    }

    @pure def postResolvedInfoItemUsage(ctx: Context, o: ResolvedInfo.ItemUsage): TPostResult[Context, ResolvedInfo] = {
      return TPostResult(ctx, None())
    }

    @pure def postResolvedInfoPartUsage(ctx: Context, o: ResolvedInfo.PartUsage): TPostResult[Context, ResolvedInfo] = {
      return TPostResult(ctx, None())
    }

    @pure def postResolvedInfoPortUsage(ctx: Context, o: ResolvedInfo.PortUsage): TPostResult[Context, ResolvedInfo] = {
      return TPostResult(ctx, None())
    }

    @pure def postResolvedInfoReferenceUsage(ctx: Context, o: ResolvedInfo.ReferenceUsage): TPostResult[Context, ResolvedInfo] = {
      return TPostResult(ctx, None())
    }

    @pure def postType(ctx: Context, o: Type): TPostResult[Context, Type] = {
      o match {
        case o: Type.Named => return postTypeNamed(ctx, o)
      }
    }

    @pure def postTypeNamed(ctx: Context, o: Type.Named): TPostResult[Context, Type] = {
      return TPostResult(ctx, None())
    }

    @pure def postTypedAttr(ctx: Context, o: TypedAttr): TPostResult[Context, TypedAttr] = {
      return TPostResult(ctx, None())
    }

  }

  @pure def transformISZ[Context, T](ctx: Context, s: IS[Z, T], f: (Context, T) => TPostResult[Context, T] @pure): TPostResult[Context, IS[Z, T]] = {
    val s2: MS[Z, T] = s.toMS
    var changed: B = F
    var ctxi = ctx
    for (i <- s2.indices) {
      val e: T = s(i)
      val r: TPostResult[Context, T] = f(ctxi, e)
      ctxi = r.ctx
      changed = changed || r.resultOpt.nonEmpty
      s2(i) = r.resultOpt.getOrElse(e)
    }
    if (changed) {
      return TPostResult(ctxi, Some(s2.toIS))
    } else {
      return TPostResult[Context, IS[Z, T]](ctxi, None[IS[Z, T]]())
    }
  }

  @pure def transformOption[Context, T](ctx: Context, option: Option[T], f: (Context, T) => TPostResult[Context, T] @pure): TPostResult[Context, Option[T]] = {
    option match {
      case Some(v) =>
        val r = f(ctx, v)
        r.resultOpt match {
          case Some(_) => return TPostResult(r.ctx, Some(r.resultOpt))
          case _ => return TPostResult[Context, Option[T]](r.ctx, None[Option[T]]())
        }
      case _ => return TPostResult[Context, Option[T]](ctx, None[Option[T]]())
    }
  }

}

import Transformer._

@datatype class Transformer[Context](val pp: PrePost[Context]) {

  @pure def transformSysmlAstId(ctx: Context, o: SysmlAst.Id): TPostResult[Context, SysmlAst.Id] = {
    val preR: PreResult[Context, SysmlAst.Id] = pp.preSysmlAstId(ctx, o)
    val r: TPostResult[Context, SysmlAst.Id] = if (preR.continu) {
      val o2: SysmlAst.Id = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: TPostResult[Context, Attr] = transformAttr(preR.ctx, o2.attr)
      if (hasChanged || r0.resultOpt.nonEmpty)
        TPostResult(r0.ctx, Some(o2(attr = r0.resultOpt.getOrElse(o2.attr))))
      else
        TPostResult(r0.ctx, None())
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.Id = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.Id] = pp.postSysmlAstId(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformSysmlAstName(ctx: Context, o: SysmlAst.Name): TPostResult[Context, SysmlAst.Name] = {
    val preR: PreResult[Context, SysmlAst.Name] = pp.preSysmlAstName(ctx, o)
    val r: TPostResult[Context, SysmlAst.Name] = if (preR.continu) {
      val o2: SysmlAst.Name = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: TPostResult[Context, IS[Z, SysmlAst.Id]] = transformISZ(preR.ctx, o2.ids, transformSysmlAstId _)
      val r1: TPostResult[Context, Attr] = transformAttr(r0.ctx, o2.attr)
      if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty)
        TPostResult(r1.ctx, Some(o2(ids = r0.resultOpt.getOrElse(o2.ids), attr = r1.resultOpt.getOrElse(o2.attr))))
      else
        TPostResult(r1.ctx, None())
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.Name = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.Name] = pp.postSysmlAstName(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformSysmlAstTopUnit(ctx: Context, o: SysmlAst.TopUnit): TPostResult[Context, SysmlAst.TopUnit] = {
    val preR: PreResult[Context, SysmlAst.TopUnit] = pp.preSysmlAstTopUnit(ctx, o)
    val r: TPostResult[Context, SysmlAst.TopUnit] = if (preR.continu) {
      val o2: SysmlAst.TopUnit = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: TPostResult[Context, IS[Z, SysmlAst.PackageBodyElement]] = transformISZ(preR.ctx, o2.packageBodyElements, transformSysmlAstPackageBodyElement _)
      if (hasChanged || r0.resultOpt.nonEmpty)
        TPostResult(r0.ctx, Some(o2(packageBodyElements = r0.resultOpt.getOrElse(o2.packageBodyElements))))
      else
        TPostResult(r0.ctx, None())
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.TopUnit = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.TopUnit] = pp.postSysmlAstTopUnit(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformSysmlAstAttrNode(ctx: Context, o: SysmlAst.AttrNode): TPostResult[Context, SysmlAst.AttrNode] = {
    val preR: PreResult[Context, SysmlAst.AttrNode] = pp.preSysmlAstAttrNode(ctx, o)
    val r: TPostResult[Context, SysmlAst.AttrNode] = if (preR.continu) {
      val o2: SysmlAst.AttrNode = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val rOpt: TPostResult[Context, SysmlAst.AttrNode] = o2 match {
        case o2: SysmlAst.Import =>
          val r0: TPostResult[Context, SysmlAst.Name] = transformSysmlAstName(preR.ctx, o2.name)
          val r1: TPostResult[Context, IS[Z, SysmlAst.AnnotatingElement]] = transformISZ(r0.ctx, o2.annotations, transformSysmlAstAnnotatingElement _)
          val r2: TPostResult[Context, Attr] = transformAttr(r1.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty)
            TPostResult(r2.ctx, Some(o2(name = r0.resultOpt.getOrElse(o2.name), annotations = r1.resultOpt.getOrElse(o2.annotations), attr = r2.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r2.ctx, None())
        case o2: SysmlAst.Alias =>
          val r0: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(preR.ctx, o2.identification, transformSysmlAstIdentification _)
          val r1: TPostResult[Context, SysmlAst.Name] = transformSysmlAstName(r0.ctx, o2.target)
          val r2: TPostResult[Context, IS[Z, SysmlAst.AnnotatingElement]] = transformISZ(r1.ctx, o2.annotations, transformSysmlAstAnnotatingElement _)
          val r3: TPostResult[Context, Attr] = transformAttr(r2.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty)
            TPostResult(r3.ctx, Some(o2(identification = r0.resultOpt.getOrElse(o2.identification), target = r1.resultOpt.getOrElse(o2.target), annotations = r2.resultOpt.getOrElse(o2.annotations), attr = r3.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r3.ctx, None())
        case o2: SysmlAst.Identification =>
          val r0: TPostResult[Context, Option[SysmlAst.Id]] = transformOption(preR.ctx, o2.shortName, transformSysmlAstId _)
          val r1: TPostResult[Context, Option[SysmlAst.Id]] = transformOption(r0.ctx, o2.name, transformSysmlAstId _)
          val r2: TPostResult[Context, Attr] = transformAttr(r1.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty)
            TPostResult(r2.ctx, Some(o2(shortName = r0.resultOpt.getOrElse(o2.shortName), name = r1.resultOpt.getOrElse(o2.name), attr = r2.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r2.ctx, None())
        case o2: SysmlAst.Package =>
          val r0: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(preR.ctx, o2.identification, transformSysmlAstIdentification _)
          val r1: TPostResult[Context, IS[Z, SysmlAst.PackageBodyElement]] = transformISZ(r0.ctx, o2.packageElements, transformSysmlAstPackageBodyElement _)
          val r2: TPostResult[Context, Attr] = transformAttr(r1.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty)
            TPostResult(r2.ctx, Some(o2(identification = r0.resultOpt.getOrElse(o2.identification), packageElements = r1.resultOpt.getOrElse(o2.packageElements), attr = r2.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r2.ctx, None())
        case o2: SysmlAst.AttributeDefinition =>
          val r0: TPostResult[Context, SysmlAst.DefinitionPrefix] = transformSysmlAstDefinitionPrefix(preR.ctx, o2.defPrefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.Name]] = transformISZ(r1.ctx, o2.subClassifications, transformSysmlAstName _)
          val r3: TPostResult[Context, IS[Z, Type.Named]] = transformISZ(r2.ctx, o2.parents, transformTypeNamed _)
          val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.bodyItems, transformSysmlAstBodyElement _)
          val r5: TPostResult[Context, Attr] = transformAttr(r4.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty)
            TPostResult(r5.ctx, Some(o2(defPrefix = r0.resultOpt.getOrElse(o2.defPrefix), identification = r1.resultOpt.getOrElse(o2.identification), subClassifications = r2.resultOpt.getOrElse(o2.subClassifications), parents = r3.resultOpt.getOrElse(o2.parents), bodyItems = r4.resultOpt.getOrElse(o2.bodyItems), attr = r5.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r5.ctx, None())
        case o2: SysmlAst.AllocationDefinition =>
          val r0: TPostResult[Context, SysmlAst.OccurrenceDefinitionPrefix] = transformSysmlAstOccurrenceDefinitionPrefix(preR.ctx, o2.occurrenceDefPrefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.Name]] = transformISZ(r1.ctx, o2.subClassifications, transformSysmlAstName _)
          val r3: TPostResult[Context, IS[Z, Type.Named]] = transformISZ(r2.ctx, o2.parents, transformTypeNamed _)
          val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.bodyItems, transformSysmlAstBodyElement _)
          val r5: TPostResult[Context, Attr] = transformAttr(r4.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty)
            TPostResult(r5.ctx, Some(o2(occurrenceDefPrefix = r0.resultOpt.getOrElse(o2.occurrenceDefPrefix), identification = r1.resultOpt.getOrElse(o2.identification), subClassifications = r2.resultOpt.getOrElse(o2.subClassifications), parents = r3.resultOpt.getOrElse(o2.parents), bodyItems = r4.resultOpt.getOrElse(o2.bodyItems), attr = r5.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r5.ctx, None())
        case o2: SysmlAst.ConnectionDefinition =>
          val r0: TPostResult[Context, SysmlAst.OccurrenceDefinitionPrefix] = transformSysmlAstOccurrenceDefinitionPrefix(preR.ctx, o2.occurrenceDefPrefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.Name]] = transformISZ(r1.ctx, o2.subClassifications, transformSysmlAstName _)
          val r3: TPostResult[Context, IS[Z, Type.Named]] = transformISZ(r2.ctx, o2.parents, transformTypeNamed _)
          val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.bodyItems, transformSysmlAstBodyElement _)
          val r5: TPostResult[Context, Attr] = transformAttr(r4.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty)
            TPostResult(r5.ctx, Some(o2(occurrenceDefPrefix = r0.resultOpt.getOrElse(o2.occurrenceDefPrefix), identification = r1.resultOpt.getOrElse(o2.identification), subClassifications = r2.resultOpt.getOrElse(o2.subClassifications), parents = r3.resultOpt.getOrElse(o2.parents), bodyItems = r4.resultOpt.getOrElse(o2.bodyItems), attr = r5.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r5.ctx, None())
        case o2: SysmlAst.EnumerationDefinition =>
          val r0: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(preR.ctx, o2.identification, transformSysmlAstIdentification _)
          val r1: TPostResult[Context, IS[Z, SysmlAst.Name]] = transformISZ(r0.ctx, o2.subClassifications, transformSysmlAstName _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.AnnotatingElement]] = transformISZ(r1.ctx, o2.annotations, transformSysmlAstAnnotatingElement _)
          val r3: TPostResult[Context, IS[Z, SysmlAst.EnumeratedValue]] = transformISZ(r2.ctx, o2.enumValues, transformSysmlAstEnumeratedValue _)
          val r4: TPostResult[Context, Attr] = transformAttr(r3.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty)
            TPostResult(r4.ctx, Some(o2(identification = r0.resultOpt.getOrElse(o2.identification), subClassifications = r1.resultOpt.getOrElse(o2.subClassifications), annotations = r2.resultOpt.getOrElse(o2.annotations), enumValues = r3.resultOpt.getOrElse(o2.enumValues), attr = r4.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r4.ctx, None())
        case o2: SysmlAst.PartDefinition =>
          val r0: TPostResult[Context, SysmlAst.OccurrenceDefinitionPrefix] = transformSysmlAstOccurrenceDefinitionPrefix(preR.ctx, o2.occurrenceDefPrefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.Name]] = transformISZ(r1.ctx, o2.subClassifications, transformSysmlAstName _)
          val r3: TPostResult[Context, IS[Z, Type.Named]] = transformISZ(r2.ctx, o2.parents, transformTypeNamed _)
          val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.bodyItems, transformSysmlAstBodyElement _)
          val r5: TPostResult[Context, Attr] = transformAttr(r4.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty)
            TPostResult(r5.ctx, Some(o2(occurrenceDefPrefix = r0.resultOpt.getOrElse(o2.occurrenceDefPrefix), identification = r1.resultOpt.getOrElse(o2.identification), subClassifications = r2.resultOpt.getOrElse(o2.subClassifications), parents = r3.resultOpt.getOrElse(o2.parents), bodyItems = r4.resultOpt.getOrElse(o2.bodyItems), attr = r5.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r5.ctx, None())
        case o2: SysmlAst.PortDefinition =>
          val r0: TPostResult[Context, SysmlAst.DefinitionPrefix] = transformSysmlAstDefinitionPrefix(preR.ctx, o2.defPrefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.Name]] = transformISZ(r1.ctx, o2.subClassifications, transformSysmlAstName _)
          val r3: TPostResult[Context, IS[Z, Type.Named]] = transformISZ(r2.ctx, o2.parents, transformTypeNamed _)
          val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.bodyItems, transformSysmlAstBodyElement _)
          val r5: TPostResult[Context, Attr] = transformAttr(r4.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty)
            TPostResult(r5.ctx, Some(o2(defPrefix = r0.resultOpt.getOrElse(o2.defPrefix), identification = r1.resultOpt.getOrElse(o2.identification), subClassifications = r2.resultOpt.getOrElse(o2.subClassifications), parents = r3.resultOpt.getOrElse(o2.parents), bodyItems = r4.resultOpt.getOrElse(o2.bodyItems), attr = r5.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r5.ctx, None())
        case o2: SysmlAst.MetadataDefinition =>
          val r0: TPostResult[Context, Attr] = transformAttr(preR.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty)
            TPostResult(r0.ctx, Some(o2(attr = r0.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r0.ctx, None())
        case o2: SysmlAst.AttributeUsage =>
          val r0: TPostResult[Context, SysmlAst.UsagePrefix] = transformSysmlAstUsagePrefix(preR.ctx, o2.prefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(r1.ctx, o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: TPostResult[Context, Option[SysmlAst.FeatureValue]] = transformOption(r2.ctx, o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: TPostResult[Context, Option[Type]] = transformOption(r4.ctx, o2.tipeOpt, transformType _)
          val r6: TPostResult[Context, ResolvedAttr] = transformResolvedAttr(r5.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty || r6.resultOpt.nonEmpty)
            TPostResult(r6.ctx, Some(o2(prefix = r0.resultOpt.getOrElse(o2.prefix), identification = r1.resultOpt.getOrElse(o2.identification), specializations = r2.resultOpt.getOrElse(o2.specializations), featureValue = r3.resultOpt.getOrElse(o2.featureValue), definitionBodyItems = r4.resultOpt.getOrElse(o2.definitionBodyItems), tipeOpt = r5.resultOpt.getOrElse(o2.tipeOpt), attr = r6.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r6.ctx, None())
        case o2: SysmlAst.ReferenceUsage =>
          val r0: TPostResult[Context, SysmlAst.RefPrefix] = transformSysmlAstRefPrefix(preR.ctx, o2.prefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(r1.ctx, o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: TPostResult[Context, Option[SysmlAst.FeatureValue]] = transformOption(r2.ctx, o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: TPostResult[Context, Option[Type]] = transformOption(r4.ctx, o2.tipeOpt, transformType _)
          val r6: TPostResult[Context, ResolvedAttr] = transformResolvedAttr(r5.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty || r6.resultOpt.nonEmpty)
            TPostResult(r6.ctx, Some(o2(prefix = r0.resultOpt.getOrElse(o2.prefix), identification = r1.resultOpt.getOrElse(o2.identification), specializations = r2.resultOpt.getOrElse(o2.specializations), featureValue = r3.resultOpt.getOrElse(o2.featureValue), definitionBodyItems = r4.resultOpt.getOrElse(o2.definitionBodyItems), tipeOpt = r5.resultOpt.getOrElse(o2.tipeOpt), attr = r6.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r6.ctx, None())
        case o2: SysmlAst.ConnectionUsage =>
          val r0: TPostResult[Context, SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(preR.ctx, o2.occurrenceUsagePrefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(r1.ctx, o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: TPostResult[Context, Option[SysmlAst.FeatureValue]] = transformOption(r2.ctx, o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: TPostResult[Context, Option[SysmlAst.ConnectorPart]] = transformOption(r3.ctx, o2.connectorPart, transformSysmlAstConnectorPart _)
          val r5: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r4.ctx, o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r6: TPostResult[Context, Option[Type]] = transformOption(r5.ctx, o2.tipeOpt, transformType _)
          val r7: TPostResult[Context, ResolvedAttr] = transformResolvedAttr(r6.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty || r6.resultOpt.nonEmpty || r7.resultOpt.nonEmpty)
            TPostResult(r7.ctx, Some(o2(occurrenceUsagePrefix = r0.resultOpt.getOrElse(o2.occurrenceUsagePrefix), identification = r1.resultOpt.getOrElse(o2.identification), specializations = r2.resultOpt.getOrElse(o2.specializations), featureValue = r3.resultOpt.getOrElse(o2.featureValue), connectorPart = r4.resultOpt.getOrElse(o2.connectorPart), definitionBodyItems = r5.resultOpt.getOrElse(o2.definitionBodyItems), tipeOpt = r6.resultOpt.getOrElse(o2.tipeOpt), attr = r7.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r7.ctx, None())
        case o2: SysmlAst.ItemUsage =>
          val r0: TPostResult[Context, SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(preR.ctx, o2.occurrenceUsagePrefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(r1.ctx, o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: TPostResult[Context, Option[SysmlAst.FeatureValue]] = transformOption(r2.ctx, o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: TPostResult[Context, Option[Type]] = transformOption(r4.ctx, o2.tipeOpt, transformType _)
          val r6: TPostResult[Context, ResolvedAttr] = transformResolvedAttr(r5.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty || r6.resultOpt.nonEmpty)
            TPostResult(r6.ctx, Some(o2(occurrenceUsagePrefix = r0.resultOpt.getOrElse(o2.occurrenceUsagePrefix), identification = r1.resultOpt.getOrElse(o2.identification), specializations = r2.resultOpt.getOrElse(o2.specializations), featureValue = r3.resultOpt.getOrElse(o2.featureValue), definitionBodyItems = r4.resultOpt.getOrElse(o2.definitionBodyItems), tipeOpt = r5.resultOpt.getOrElse(o2.tipeOpt), attr = r6.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r6.ctx, None())
        case o2: SysmlAst.PartUsage =>
          val r0: TPostResult[Context, SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(preR.ctx, o2.occurrenceUsagePrefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(r1.ctx, o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: TPostResult[Context, Option[SysmlAst.FeatureValue]] = transformOption(r2.ctx, o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: TPostResult[Context, Option[Type]] = transformOption(r4.ctx, o2.tipeOpt, transformType _)
          val r6: TPostResult[Context, ResolvedAttr] = transformResolvedAttr(r5.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty || r6.resultOpt.nonEmpty)
            TPostResult(r6.ctx, Some(o2(occurrenceUsagePrefix = r0.resultOpt.getOrElse(o2.occurrenceUsagePrefix), identification = r1.resultOpt.getOrElse(o2.identification), specializations = r2.resultOpt.getOrElse(o2.specializations), featureValue = r3.resultOpt.getOrElse(o2.featureValue), definitionBodyItems = r4.resultOpt.getOrElse(o2.definitionBodyItems), tipeOpt = r5.resultOpt.getOrElse(o2.tipeOpt), attr = r6.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r6.ctx, None())
        case o2: SysmlAst.PortUsage =>
          val r0: TPostResult[Context, SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(preR.ctx, o2.occurrenceUsagePrefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(r1.ctx, o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: TPostResult[Context, Option[SysmlAst.FeatureValue]] = transformOption(r2.ctx, o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: TPostResult[Context, Option[Type]] = transformOption(r4.ctx, o2.tipeOpt, transformType _)
          val r6: TPostResult[Context, ResolvedAttr] = transformResolvedAttr(r5.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty || r6.resultOpt.nonEmpty)
            TPostResult(r6.ctx, Some(o2(occurrenceUsagePrefix = r0.resultOpt.getOrElse(o2.occurrenceUsagePrefix), identification = r1.resultOpt.getOrElse(o2.identification), specializations = r2.resultOpt.getOrElse(o2.specializations), featureValue = r3.resultOpt.getOrElse(o2.featureValue), definitionBodyItems = r4.resultOpt.getOrElse(o2.definitionBodyItems), tipeOpt = r5.resultOpt.getOrElse(o2.tipeOpt), attr = r6.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r6.ctx, None())
        case o2: SysmlAst.Comment =>
          val r0: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(preR.ctx, o2.id, transformSysmlAstIdentification _)
          val r1: TPostResult[Context, IS[Z, SysmlAst.Name]] = transformISZ(r0.ctx, o2.abouts, transformSysmlAstName _)
          val r2: TPostResult[Context, Attr] = transformAttr(r1.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty)
            TPostResult(r2.ctx, Some(o2(id = r0.resultOpt.getOrElse(o2.id), abouts = r1.resultOpt.getOrElse(o2.abouts), attr = r2.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r2.ctx, None())
        case o2: SysmlAst.Documentation =>
          val r0: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(preR.ctx, o2.id, transformSysmlAstIdentification _)
          val r1: TPostResult[Context, Attr] = transformAttr(r0.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty)
            TPostResult(r1.ctx, Some(o2(id = r0.resultOpt.getOrElse(o2.id), attr = r1.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r1.ctx, None())
        case o2: SysmlAst.TextualRepresentation =>
          val r0: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(preR.ctx, o2.id, transformSysmlAstIdentification _)
          val r1: TPostResult[Context, Attr] = transformAttr(r0.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty)
            TPostResult(r1.ctx, Some(o2(id = r0.resultOpt.getOrElse(o2.id), attr = r1.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r1.ctx, None())
      }
      rOpt
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.AttrNode = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.AttrNode] = pp.postSysmlAstAttrNode(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformSysmlAstPackageBodyElement(ctx: Context, o: SysmlAst.PackageBodyElement): TPostResult[Context, SysmlAst.PackageBodyElement] = {
    val preR: PreResult[Context, SysmlAst.PackageBodyElement] = pp.preSysmlAstPackageBodyElement(ctx, o)
    val r: TPostResult[Context, SysmlAst.PackageBodyElement] = if (preR.continu) {
      val o2: SysmlAst.PackageBodyElement = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val rOpt: TPostResult[Context, SysmlAst.PackageBodyElement] = o2 match {
        case o2: SysmlAst.Import =>
          val r0: TPostResult[Context, SysmlAst.Name] = transformSysmlAstName(preR.ctx, o2.name)
          val r1: TPostResult[Context, IS[Z, SysmlAst.AnnotatingElement]] = transformISZ(r0.ctx, o2.annotations, transformSysmlAstAnnotatingElement _)
          val r2: TPostResult[Context, Attr] = transformAttr(r1.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty)
            TPostResult(r2.ctx, Some(o2(name = r0.resultOpt.getOrElse(o2.name), annotations = r1.resultOpt.getOrElse(o2.annotations), attr = r2.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r2.ctx, None())
        case o2: SysmlAst.Alias =>
          val r0: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(preR.ctx, o2.identification, transformSysmlAstIdentification _)
          val r1: TPostResult[Context, SysmlAst.Name] = transformSysmlAstName(r0.ctx, o2.target)
          val r2: TPostResult[Context, IS[Z, SysmlAst.AnnotatingElement]] = transformISZ(r1.ctx, o2.annotations, transformSysmlAstAnnotatingElement _)
          val r3: TPostResult[Context, Attr] = transformAttr(r2.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty)
            TPostResult(r3.ctx, Some(o2(identification = r0.resultOpt.getOrElse(o2.identification), target = r1.resultOpt.getOrElse(o2.target), annotations = r2.resultOpt.getOrElse(o2.annotations), attr = r3.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r3.ctx, None())
        case o2: SysmlAst.Package =>
          val r0: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(preR.ctx, o2.identification, transformSysmlAstIdentification _)
          val r1: TPostResult[Context, IS[Z, SysmlAst.PackageBodyElement]] = transformISZ(r0.ctx, o2.packageElements, transformSysmlAstPackageBodyElement _)
          val r2: TPostResult[Context, Attr] = transformAttr(r1.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty)
            TPostResult(r2.ctx, Some(o2(identification = r0.resultOpt.getOrElse(o2.identification), packageElements = r1.resultOpt.getOrElse(o2.packageElements), attr = r2.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r2.ctx, None())
        case o2: SysmlAst.AttributeDefinition =>
          val r0: TPostResult[Context, SysmlAst.DefinitionPrefix] = transformSysmlAstDefinitionPrefix(preR.ctx, o2.defPrefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.Name]] = transformISZ(r1.ctx, o2.subClassifications, transformSysmlAstName _)
          val r3: TPostResult[Context, IS[Z, Type.Named]] = transformISZ(r2.ctx, o2.parents, transformTypeNamed _)
          val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.bodyItems, transformSysmlAstBodyElement _)
          val r5: TPostResult[Context, Attr] = transformAttr(r4.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty)
            TPostResult(r5.ctx, Some(o2(defPrefix = r0.resultOpt.getOrElse(o2.defPrefix), identification = r1.resultOpt.getOrElse(o2.identification), subClassifications = r2.resultOpt.getOrElse(o2.subClassifications), parents = r3.resultOpt.getOrElse(o2.parents), bodyItems = r4.resultOpt.getOrElse(o2.bodyItems), attr = r5.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r5.ctx, None())
        case o2: SysmlAst.AllocationDefinition =>
          val r0: TPostResult[Context, SysmlAst.OccurrenceDefinitionPrefix] = transformSysmlAstOccurrenceDefinitionPrefix(preR.ctx, o2.occurrenceDefPrefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.Name]] = transformISZ(r1.ctx, o2.subClassifications, transformSysmlAstName _)
          val r3: TPostResult[Context, IS[Z, Type.Named]] = transformISZ(r2.ctx, o2.parents, transformTypeNamed _)
          val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.bodyItems, transformSysmlAstBodyElement _)
          val r5: TPostResult[Context, Attr] = transformAttr(r4.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty)
            TPostResult(r5.ctx, Some(o2(occurrenceDefPrefix = r0.resultOpt.getOrElse(o2.occurrenceDefPrefix), identification = r1.resultOpt.getOrElse(o2.identification), subClassifications = r2.resultOpt.getOrElse(o2.subClassifications), parents = r3.resultOpt.getOrElse(o2.parents), bodyItems = r4.resultOpt.getOrElse(o2.bodyItems), attr = r5.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r5.ctx, None())
        case o2: SysmlAst.ConnectionDefinition =>
          val r0: TPostResult[Context, SysmlAst.OccurrenceDefinitionPrefix] = transformSysmlAstOccurrenceDefinitionPrefix(preR.ctx, o2.occurrenceDefPrefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.Name]] = transformISZ(r1.ctx, o2.subClassifications, transformSysmlAstName _)
          val r3: TPostResult[Context, IS[Z, Type.Named]] = transformISZ(r2.ctx, o2.parents, transformTypeNamed _)
          val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.bodyItems, transformSysmlAstBodyElement _)
          val r5: TPostResult[Context, Attr] = transformAttr(r4.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty)
            TPostResult(r5.ctx, Some(o2(occurrenceDefPrefix = r0.resultOpt.getOrElse(o2.occurrenceDefPrefix), identification = r1.resultOpt.getOrElse(o2.identification), subClassifications = r2.resultOpt.getOrElse(o2.subClassifications), parents = r3.resultOpt.getOrElse(o2.parents), bodyItems = r4.resultOpt.getOrElse(o2.bodyItems), attr = r5.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r5.ctx, None())
        case o2: SysmlAst.EnumerationDefinition =>
          val r0: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(preR.ctx, o2.identification, transformSysmlAstIdentification _)
          val r1: TPostResult[Context, IS[Z, SysmlAst.Name]] = transformISZ(r0.ctx, o2.subClassifications, transformSysmlAstName _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.AnnotatingElement]] = transformISZ(r1.ctx, o2.annotations, transformSysmlAstAnnotatingElement _)
          val r3: TPostResult[Context, IS[Z, SysmlAst.EnumeratedValue]] = transformISZ(r2.ctx, o2.enumValues, transformSysmlAstEnumeratedValue _)
          val r4: TPostResult[Context, Attr] = transformAttr(r3.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty)
            TPostResult(r4.ctx, Some(o2(identification = r0.resultOpt.getOrElse(o2.identification), subClassifications = r1.resultOpt.getOrElse(o2.subClassifications), annotations = r2.resultOpt.getOrElse(o2.annotations), enumValues = r3.resultOpt.getOrElse(o2.enumValues), attr = r4.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r4.ctx, None())
        case o2: SysmlAst.PartDefinition =>
          val r0: TPostResult[Context, SysmlAst.OccurrenceDefinitionPrefix] = transformSysmlAstOccurrenceDefinitionPrefix(preR.ctx, o2.occurrenceDefPrefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.Name]] = transformISZ(r1.ctx, o2.subClassifications, transformSysmlAstName _)
          val r3: TPostResult[Context, IS[Z, Type.Named]] = transformISZ(r2.ctx, o2.parents, transformTypeNamed _)
          val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.bodyItems, transformSysmlAstBodyElement _)
          val r5: TPostResult[Context, Attr] = transformAttr(r4.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty)
            TPostResult(r5.ctx, Some(o2(occurrenceDefPrefix = r0.resultOpt.getOrElse(o2.occurrenceDefPrefix), identification = r1.resultOpt.getOrElse(o2.identification), subClassifications = r2.resultOpt.getOrElse(o2.subClassifications), parents = r3.resultOpt.getOrElse(o2.parents), bodyItems = r4.resultOpt.getOrElse(o2.bodyItems), attr = r5.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r5.ctx, None())
        case o2: SysmlAst.PortDefinition =>
          val r0: TPostResult[Context, SysmlAst.DefinitionPrefix] = transformSysmlAstDefinitionPrefix(preR.ctx, o2.defPrefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.Name]] = transformISZ(r1.ctx, o2.subClassifications, transformSysmlAstName _)
          val r3: TPostResult[Context, IS[Z, Type.Named]] = transformISZ(r2.ctx, o2.parents, transformTypeNamed _)
          val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.bodyItems, transformSysmlAstBodyElement _)
          val r5: TPostResult[Context, Attr] = transformAttr(r4.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty)
            TPostResult(r5.ctx, Some(o2(defPrefix = r0.resultOpt.getOrElse(o2.defPrefix), identification = r1.resultOpt.getOrElse(o2.identification), subClassifications = r2.resultOpt.getOrElse(o2.subClassifications), parents = r3.resultOpt.getOrElse(o2.parents), bodyItems = r4.resultOpt.getOrElse(o2.bodyItems), attr = r5.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r5.ctx, None())
        case o2: SysmlAst.MetadataDefinition =>
          val r0: TPostResult[Context, Attr] = transformAttr(preR.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty)
            TPostResult(r0.ctx, Some(o2(attr = r0.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r0.ctx, None())
        case o2: SysmlAst.AttributeUsage =>
          val r0: TPostResult[Context, SysmlAst.UsagePrefix] = transformSysmlAstUsagePrefix(preR.ctx, o2.prefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(r1.ctx, o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: TPostResult[Context, Option[SysmlAst.FeatureValue]] = transformOption(r2.ctx, o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: TPostResult[Context, Option[Type]] = transformOption(r4.ctx, o2.tipeOpt, transformType _)
          val r6: TPostResult[Context, ResolvedAttr] = transformResolvedAttr(r5.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty || r6.resultOpt.nonEmpty)
            TPostResult(r6.ctx, Some(o2(prefix = r0.resultOpt.getOrElse(o2.prefix), identification = r1.resultOpt.getOrElse(o2.identification), specializations = r2.resultOpt.getOrElse(o2.specializations), featureValue = r3.resultOpt.getOrElse(o2.featureValue), definitionBodyItems = r4.resultOpt.getOrElse(o2.definitionBodyItems), tipeOpt = r5.resultOpt.getOrElse(o2.tipeOpt), attr = r6.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r6.ctx, None())
        case o2: SysmlAst.ReferenceUsage =>
          val r0: TPostResult[Context, SysmlAst.RefPrefix] = transformSysmlAstRefPrefix(preR.ctx, o2.prefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(r1.ctx, o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: TPostResult[Context, Option[SysmlAst.FeatureValue]] = transformOption(r2.ctx, o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: TPostResult[Context, Option[Type]] = transformOption(r4.ctx, o2.tipeOpt, transformType _)
          val r6: TPostResult[Context, ResolvedAttr] = transformResolvedAttr(r5.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty || r6.resultOpt.nonEmpty)
            TPostResult(r6.ctx, Some(o2(prefix = r0.resultOpt.getOrElse(o2.prefix), identification = r1.resultOpt.getOrElse(o2.identification), specializations = r2.resultOpt.getOrElse(o2.specializations), featureValue = r3.resultOpt.getOrElse(o2.featureValue), definitionBodyItems = r4.resultOpt.getOrElse(o2.definitionBodyItems), tipeOpt = r5.resultOpt.getOrElse(o2.tipeOpt), attr = r6.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r6.ctx, None())
        case o2: SysmlAst.ConnectionUsage =>
          val r0: TPostResult[Context, SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(preR.ctx, o2.occurrenceUsagePrefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(r1.ctx, o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: TPostResult[Context, Option[SysmlAst.FeatureValue]] = transformOption(r2.ctx, o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: TPostResult[Context, Option[SysmlAst.ConnectorPart]] = transformOption(r3.ctx, o2.connectorPart, transformSysmlAstConnectorPart _)
          val r5: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r4.ctx, o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r6: TPostResult[Context, Option[Type]] = transformOption(r5.ctx, o2.tipeOpt, transformType _)
          val r7: TPostResult[Context, ResolvedAttr] = transformResolvedAttr(r6.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty || r6.resultOpt.nonEmpty || r7.resultOpt.nonEmpty)
            TPostResult(r7.ctx, Some(o2(occurrenceUsagePrefix = r0.resultOpt.getOrElse(o2.occurrenceUsagePrefix), identification = r1.resultOpt.getOrElse(o2.identification), specializations = r2.resultOpt.getOrElse(o2.specializations), featureValue = r3.resultOpt.getOrElse(o2.featureValue), connectorPart = r4.resultOpt.getOrElse(o2.connectorPart), definitionBodyItems = r5.resultOpt.getOrElse(o2.definitionBodyItems), tipeOpt = r6.resultOpt.getOrElse(o2.tipeOpt), attr = r7.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r7.ctx, None())
        case o2: SysmlAst.ItemUsage =>
          val r0: TPostResult[Context, SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(preR.ctx, o2.occurrenceUsagePrefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(r1.ctx, o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: TPostResult[Context, Option[SysmlAst.FeatureValue]] = transformOption(r2.ctx, o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: TPostResult[Context, Option[Type]] = transformOption(r4.ctx, o2.tipeOpt, transformType _)
          val r6: TPostResult[Context, ResolvedAttr] = transformResolvedAttr(r5.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty || r6.resultOpt.nonEmpty)
            TPostResult(r6.ctx, Some(o2(occurrenceUsagePrefix = r0.resultOpt.getOrElse(o2.occurrenceUsagePrefix), identification = r1.resultOpt.getOrElse(o2.identification), specializations = r2.resultOpt.getOrElse(o2.specializations), featureValue = r3.resultOpt.getOrElse(o2.featureValue), definitionBodyItems = r4.resultOpt.getOrElse(o2.definitionBodyItems), tipeOpt = r5.resultOpt.getOrElse(o2.tipeOpt), attr = r6.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r6.ctx, None())
        case o2: SysmlAst.PartUsage =>
          val r0: TPostResult[Context, SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(preR.ctx, o2.occurrenceUsagePrefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(r1.ctx, o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: TPostResult[Context, Option[SysmlAst.FeatureValue]] = transformOption(r2.ctx, o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: TPostResult[Context, Option[Type]] = transformOption(r4.ctx, o2.tipeOpt, transformType _)
          val r6: TPostResult[Context, ResolvedAttr] = transformResolvedAttr(r5.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty || r6.resultOpt.nonEmpty)
            TPostResult(r6.ctx, Some(o2(occurrenceUsagePrefix = r0.resultOpt.getOrElse(o2.occurrenceUsagePrefix), identification = r1.resultOpt.getOrElse(o2.identification), specializations = r2.resultOpt.getOrElse(o2.specializations), featureValue = r3.resultOpt.getOrElse(o2.featureValue), definitionBodyItems = r4.resultOpt.getOrElse(o2.definitionBodyItems), tipeOpt = r5.resultOpt.getOrElse(o2.tipeOpt), attr = r6.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r6.ctx, None())
        case o2: SysmlAst.PortUsage =>
          val r0: TPostResult[Context, SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(preR.ctx, o2.occurrenceUsagePrefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(r1.ctx, o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: TPostResult[Context, Option[SysmlAst.FeatureValue]] = transformOption(r2.ctx, o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: TPostResult[Context, Option[Type]] = transformOption(r4.ctx, o2.tipeOpt, transformType _)
          val r6: TPostResult[Context, ResolvedAttr] = transformResolvedAttr(r5.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty || r6.resultOpt.nonEmpty)
            TPostResult(r6.ctx, Some(o2(occurrenceUsagePrefix = r0.resultOpt.getOrElse(o2.occurrenceUsagePrefix), identification = r1.resultOpt.getOrElse(o2.identification), specializations = r2.resultOpt.getOrElse(o2.specializations), featureValue = r3.resultOpt.getOrElse(o2.featureValue), definitionBodyItems = r4.resultOpt.getOrElse(o2.definitionBodyItems), tipeOpt = r5.resultOpt.getOrElse(o2.tipeOpt), attr = r6.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r6.ctx, None())
        case o2: SysmlAst.Comment =>
          val r0: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(preR.ctx, o2.id, transformSysmlAstIdentification _)
          val r1: TPostResult[Context, IS[Z, SysmlAst.Name]] = transformISZ(r0.ctx, o2.abouts, transformSysmlAstName _)
          val r2: TPostResult[Context, Attr] = transformAttr(r1.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty)
            TPostResult(r2.ctx, Some(o2(id = r0.resultOpt.getOrElse(o2.id), abouts = r1.resultOpt.getOrElse(o2.abouts), attr = r2.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r2.ctx, None())
        case o2: SysmlAst.Documentation =>
          val r0: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(preR.ctx, o2.id, transformSysmlAstIdentification _)
          val r1: TPostResult[Context, Attr] = transformAttr(r0.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty)
            TPostResult(r1.ctx, Some(o2(id = r0.resultOpt.getOrElse(o2.id), attr = r1.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r1.ctx, None())
        case o2: SysmlAst.TextualRepresentation =>
          val r0: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(preR.ctx, o2.id, transformSysmlAstIdentification _)
          val r1: TPostResult[Context, Attr] = transformAttr(r0.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty)
            TPostResult(r1.ctx, Some(o2(id = r0.resultOpt.getOrElse(o2.id), attr = r1.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r1.ctx, None())
      }
      rOpt
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.PackageBodyElement = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.PackageBodyElement] = pp.postSysmlAstPackageBodyElement(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformSysmlAstBodyElement(ctx: Context, o: SysmlAst.BodyElement): TPostResult[Context, SysmlAst.BodyElement] = {
    val preR: PreResult[Context, SysmlAst.BodyElement] = pp.preSysmlAstBodyElement(ctx, o)
    val r: TPostResult[Context, SysmlAst.BodyElement] = if (preR.continu) {
      val o2: SysmlAst.BodyElement = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val rOpt: TPostResult[Context, SysmlAst.BodyElement] = o2 match {
        case o2: SysmlAst.Import =>
          val r0: TPostResult[Context, SysmlAst.Name] = transformSysmlAstName(preR.ctx, o2.name)
          val r1: TPostResult[Context, IS[Z, SysmlAst.AnnotatingElement]] = transformISZ(r0.ctx, o2.annotations, transformSysmlAstAnnotatingElement _)
          val r2: TPostResult[Context, Attr] = transformAttr(r1.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty)
            TPostResult(r2.ctx, Some(o2(name = r0.resultOpt.getOrElse(o2.name), annotations = r1.resultOpt.getOrElse(o2.annotations), attr = r2.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r2.ctx, None())
        case o2: SysmlAst.Alias =>
          val r0: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(preR.ctx, o2.identification, transformSysmlAstIdentification _)
          val r1: TPostResult[Context, SysmlAst.Name] = transformSysmlAstName(r0.ctx, o2.target)
          val r2: TPostResult[Context, IS[Z, SysmlAst.AnnotatingElement]] = transformISZ(r1.ctx, o2.annotations, transformSysmlAstAnnotatingElement _)
          val r3: TPostResult[Context, Attr] = transformAttr(r2.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty)
            TPostResult(r3.ctx, Some(o2(identification = r0.resultOpt.getOrElse(o2.identification), target = r1.resultOpt.getOrElse(o2.target), annotations = r2.resultOpt.getOrElse(o2.annotations), attr = r3.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r3.ctx, None())
        case o2: SysmlAst.AttributeUsage =>
          val r0: TPostResult[Context, SysmlAst.UsagePrefix] = transformSysmlAstUsagePrefix(preR.ctx, o2.prefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(r1.ctx, o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: TPostResult[Context, Option[SysmlAst.FeatureValue]] = transformOption(r2.ctx, o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: TPostResult[Context, Option[Type]] = transformOption(r4.ctx, o2.tipeOpt, transformType _)
          val r6: TPostResult[Context, ResolvedAttr] = transformResolvedAttr(r5.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty || r6.resultOpt.nonEmpty)
            TPostResult(r6.ctx, Some(o2(prefix = r0.resultOpt.getOrElse(o2.prefix), identification = r1.resultOpt.getOrElse(o2.identification), specializations = r2.resultOpt.getOrElse(o2.specializations), featureValue = r3.resultOpt.getOrElse(o2.featureValue), definitionBodyItems = r4.resultOpt.getOrElse(o2.definitionBodyItems), tipeOpt = r5.resultOpt.getOrElse(o2.tipeOpt), attr = r6.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r6.ctx, None())
        case o2: SysmlAst.ReferenceUsage =>
          val r0: TPostResult[Context, SysmlAst.RefPrefix] = transformSysmlAstRefPrefix(preR.ctx, o2.prefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(r1.ctx, o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: TPostResult[Context, Option[SysmlAst.FeatureValue]] = transformOption(r2.ctx, o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: TPostResult[Context, Option[Type]] = transformOption(r4.ctx, o2.tipeOpt, transformType _)
          val r6: TPostResult[Context, ResolvedAttr] = transformResolvedAttr(r5.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty || r6.resultOpt.nonEmpty)
            TPostResult(r6.ctx, Some(o2(prefix = r0.resultOpt.getOrElse(o2.prefix), identification = r1.resultOpt.getOrElse(o2.identification), specializations = r2.resultOpt.getOrElse(o2.specializations), featureValue = r3.resultOpt.getOrElse(o2.featureValue), definitionBodyItems = r4.resultOpt.getOrElse(o2.definitionBodyItems), tipeOpt = r5.resultOpt.getOrElse(o2.tipeOpt), attr = r6.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r6.ctx, None())
        case o2: SysmlAst.ConnectionUsage =>
          val r0: TPostResult[Context, SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(preR.ctx, o2.occurrenceUsagePrefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(r1.ctx, o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: TPostResult[Context, Option[SysmlAst.FeatureValue]] = transformOption(r2.ctx, o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: TPostResult[Context, Option[SysmlAst.ConnectorPart]] = transformOption(r3.ctx, o2.connectorPart, transformSysmlAstConnectorPart _)
          val r5: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r4.ctx, o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r6: TPostResult[Context, Option[Type]] = transformOption(r5.ctx, o2.tipeOpt, transformType _)
          val r7: TPostResult[Context, ResolvedAttr] = transformResolvedAttr(r6.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty || r6.resultOpt.nonEmpty || r7.resultOpt.nonEmpty)
            TPostResult(r7.ctx, Some(o2(occurrenceUsagePrefix = r0.resultOpt.getOrElse(o2.occurrenceUsagePrefix), identification = r1.resultOpt.getOrElse(o2.identification), specializations = r2.resultOpt.getOrElse(o2.specializations), featureValue = r3.resultOpt.getOrElse(o2.featureValue), connectorPart = r4.resultOpt.getOrElse(o2.connectorPart), definitionBodyItems = r5.resultOpt.getOrElse(o2.definitionBodyItems), tipeOpt = r6.resultOpt.getOrElse(o2.tipeOpt), attr = r7.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r7.ctx, None())
        case o2: SysmlAst.ItemUsage =>
          val r0: TPostResult[Context, SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(preR.ctx, o2.occurrenceUsagePrefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(r1.ctx, o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: TPostResult[Context, Option[SysmlAst.FeatureValue]] = transformOption(r2.ctx, o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: TPostResult[Context, Option[Type]] = transformOption(r4.ctx, o2.tipeOpt, transformType _)
          val r6: TPostResult[Context, ResolvedAttr] = transformResolvedAttr(r5.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty || r6.resultOpt.nonEmpty)
            TPostResult(r6.ctx, Some(o2(occurrenceUsagePrefix = r0.resultOpt.getOrElse(o2.occurrenceUsagePrefix), identification = r1.resultOpt.getOrElse(o2.identification), specializations = r2.resultOpt.getOrElse(o2.specializations), featureValue = r3.resultOpt.getOrElse(o2.featureValue), definitionBodyItems = r4.resultOpt.getOrElse(o2.definitionBodyItems), tipeOpt = r5.resultOpt.getOrElse(o2.tipeOpt), attr = r6.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r6.ctx, None())
        case o2: SysmlAst.PartUsage =>
          val r0: TPostResult[Context, SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(preR.ctx, o2.occurrenceUsagePrefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(r1.ctx, o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: TPostResult[Context, Option[SysmlAst.FeatureValue]] = transformOption(r2.ctx, o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: TPostResult[Context, Option[Type]] = transformOption(r4.ctx, o2.tipeOpt, transformType _)
          val r6: TPostResult[Context, ResolvedAttr] = transformResolvedAttr(r5.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty || r6.resultOpt.nonEmpty)
            TPostResult(r6.ctx, Some(o2(occurrenceUsagePrefix = r0.resultOpt.getOrElse(o2.occurrenceUsagePrefix), identification = r1.resultOpt.getOrElse(o2.identification), specializations = r2.resultOpt.getOrElse(o2.specializations), featureValue = r3.resultOpt.getOrElse(o2.featureValue), definitionBodyItems = r4.resultOpt.getOrElse(o2.definitionBodyItems), tipeOpt = r5.resultOpt.getOrElse(o2.tipeOpt), attr = r6.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r6.ctx, None())
        case o2: SysmlAst.PortUsage =>
          val r0: TPostResult[Context, SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(preR.ctx, o2.occurrenceUsagePrefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(r1.ctx, o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: TPostResult[Context, Option[SysmlAst.FeatureValue]] = transformOption(r2.ctx, o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: TPostResult[Context, Option[Type]] = transformOption(r4.ctx, o2.tipeOpt, transformType _)
          val r6: TPostResult[Context, ResolvedAttr] = transformResolvedAttr(r5.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty || r6.resultOpt.nonEmpty)
            TPostResult(r6.ctx, Some(o2(occurrenceUsagePrefix = r0.resultOpt.getOrElse(o2.occurrenceUsagePrefix), identification = r1.resultOpt.getOrElse(o2.identification), specializations = r2.resultOpt.getOrElse(o2.specializations), featureValue = r3.resultOpt.getOrElse(o2.featureValue), definitionBodyItems = r4.resultOpt.getOrElse(o2.definitionBodyItems), tipeOpt = r5.resultOpt.getOrElse(o2.tipeOpt), attr = r6.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r6.ctx, None())
      }
      rOpt
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.BodyElement = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.BodyElement] = pp.postSysmlAstBodyElement(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformSysmlAstFeatureValue(ctx: Context, o: SysmlAst.FeatureValue): TPostResult[Context, SysmlAst.FeatureValue] = {
    val preR: PreResult[Context, SysmlAst.FeatureValue] = pp.preSysmlAstFeatureValue(ctx, o)
    val r: TPostResult[Context, SysmlAst.FeatureValue] = if (preR.continu) {
      val o2: SysmlAst.FeatureValue = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      if (hasChanged)
        TPostResult(preR.ctx, Some(o2))
      else
        TPostResult(preR.ctx, None())
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.FeatureValue = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.FeatureValue] = pp.postSysmlAstFeatureValue(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformSysmlAstEnumeratedValue(ctx: Context, o: SysmlAst.EnumeratedValue): TPostResult[Context, SysmlAst.EnumeratedValue] = {
    val preR: PreResult[Context, SysmlAst.EnumeratedValue] = pp.preSysmlAstEnumeratedValue(ctx, o)
    val r: TPostResult[Context, SysmlAst.EnumeratedValue] = if (preR.continu) {
      val o2: SysmlAst.EnumeratedValue = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(preR.ctx, o2.identification, transformSysmlAstIdentification _)
      val r1: TPostResult[Context, IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(r0.ctx, o2.specializations, transformSysmlAstFeatureSpecialization _)
      val r2: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r1.ctx, o2.definitionBodyItems, transformSysmlAstBodyElement _)
      if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty)
        TPostResult(r2.ctx, Some(o2(identification = r0.resultOpt.getOrElse(o2.identification), specializations = r1.resultOpt.getOrElse(o2.specializations), definitionBodyItems = r2.resultOpt.getOrElse(o2.definitionBodyItems))))
      else
        TPostResult(r2.ctx, None())
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.EnumeratedValue = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.EnumeratedValue] = pp.postSysmlAstEnumeratedValue(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformSysmlAstImport(ctx: Context, o: SysmlAst.Import): TPostResult[Context, SysmlAst.Import] = {
    val preR: PreResult[Context, SysmlAst.Import] = pp.preSysmlAstImport(ctx, o)
    val r: TPostResult[Context, SysmlAst.Import] = if (preR.continu) {
      val o2: SysmlAst.Import = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: TPostResult[Context, SysmlAst.Name] = transformSysmlAstName(preR.ctx, o2.name)
      val r1: TPostResult[Context, IS[Z, SysmlAst.AnnotatingElement]] = transformISZ(r0.ctx, o2.annotations, transformSysmlAstAnnotatingElement _)
      val r2: TPostResult[Context, Attr] = transformAttr(r1.ctx, o2.attr)
      if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty)
        TPostResult(r2.ctx, Some(o2(name = r0.resultOpt.getOrElse(o2.name), annotations = r1.resultOpt.getOrElse(o2.annotations), attr = r2.resultOpt.getOrElse(o2.attr))))
      else
        TPostResult(r2.ctx, None())
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.Import = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.Import] = pp.postSysmlAstImport(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformSysmlAstAlias(ctx: Context, o: SysmlAst.Alias): TPostResult[Context, SysmlAst.Alias] = {
    val preR: PreResult[Context, SysmlAst.Alias] = pp.preSysmlAstAlias(ctx, o)
    val r: TPostResult[Context, SysmlAst.Alias] = if (preR.continu) {
      val o2: SysmlAst.Alias = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(preR.ctx, o2.identification, transformSysmlAstIdentification _)
      val r1: TPostResult[Context, SysmlAst.Name] = transformSysmlAstName(r0.ctx, o2.target)
      val r2: TPostResult[Context, IS[Z, SysmlAst.AnnotatingElement]] = transformISZ(r1.ctx, o2.annotations, transformSysmlAstAnnotatingElement _)
      val r3: TPostResult[Context, Attr] = transformAttr(r2.ctx, o2.attr)
      if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty)
        TPostResult(r3.ctx, Some(o2(identification = r0.resultOpt.getOrElse(o2.identification), target = r1.resultOpt.getOrElse(o2.target), annotations = r2.resultOpt.getOrElse(o2.annotations), attr = r3.resultOpt.getOrElse(o2.attr))))
      else
        TPostResult(r3.ctx, None())
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.Alias = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.Alias] = pp.postSysmlAstAlias(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformSysmlAstIdentification(ctx: Context, o: SysmlAst.Identification): TPostResult[Context, SysmlAst.Identification] = {
    val preR: PreResult[Context, SysmlAst.Identification] = pp.preSysmlAstIdentification(ctx, o)
    val r: TPostResult[Context, SysmlAst.Identification] = if (preR.continu) {
      val o2: SysmlAst.Identification = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: TPostResult[Context, Option[SysmlAst.Id]] = transformOption(preR.ctx, o2.shortName, transformSysmlAstId _)
      val r1: TPostResult[Context, Option[SysmlAst.Id]] = transformOption(r0.ctx, o2.name, transformSysmlAstId _)
      val r2: TPostResult[Context, Attr] = transformAttr(r1.ctx, o2.attr)
      if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty)
        TPostResult(r2.ctx, Some(o2(shortName = r0.resultOpt.getOrElse(o2.shortName), name = r1.resultOpt.getOrElse(o2.name), attr = r2.resultOpt.getOrElse(o2.attr))))
      else
        TPostResult(r2.ctx, None())
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.Identification = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.Identification] = pp.postSysmlAstIdentification(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformSysmlAstConnectorPart(ctx: Context, o: SysmlAst.ConnectorPart): TPostResult[Context, SysmlAst.ConnectorPart] = {
    val preR: PreResult[Context, SysmlAst.ConnectorPart] = pp.preSysmlAstConnectorPart(ctx, o)
    val r: TPostResult[Context, SysmlAst.ConnectorPart] = if (preR.continu) {
      val o2: SysmlAst.ConnectorPart = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val rOpt: TPostResult[Context, SysmlAst.ConnectorPart] = o2 match {
        case o2: SysmlAst.BinaryConnectorPart =>
          val r0: TPostResult[Context, SysmlAst.ConnectorEnd] = transformSysmlAstConnectorEnd(preR.ctx, o2.src)
          val r1: TPostResult[Context, SysmlAst.ConnectorEnd] = transformSysmlAstConnectorEnd(r0.ctx, o2.dst)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty)
            TPostResult(r1.ctx, Some(o2(src = r0.resultOpt.getOrElse(o2.src), dst = r1.resultOpt.getOrElse(o2.dst))))
          else
            TPostResult(r1.ctx, None())
        case o2: SysmlAst.NaryConnectorPart =>
          val r0: TPostResult[Context, IS[Z, SysmlAst.ConnectorEnd]] = transformISZ(preR.ctx, o2.connectorEnds, transformSysmlAstConnectorEnd _)
          if (hasChanged || r0.resultOpt.nonEmpty)
            TPostResult(r0.ctx, Some(o2(connectorEnds = r0.resultOpt.getOrElse(o2.connectorEnds))))
          else
            TPostResult(r0.ctx, None())
      }
      rOpt
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.ConnectorPart = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.ConnectorPart] = pp.postSysmlAstConnectorPart(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformSysmlAstConnectorEnd(ctx: Context, o: SysmlAst.ConnectorEnd): TPostResult[Context, SysmlAst.ConnectorEnd] = {
    val preR: PreResult[Context, SysmlAst.ConnectorEnd] = pp.preSysmlAstConnectorEnd(ctx, o)
    val r: TPostResult[Context, SysmlAst.ConnectorEnd] = if (preR.continu) {
      val o2: SysmlAst.ConnectorEnd = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: TPostResult[Context, IS[Z, SysmlAst.Name]] = transformISZ(preR.ctx, o2.reference, transformSysmlAstName _)
      if (hasChanged || r0.resultOpt.nonEmpty)
        TPostResult(r0.ctx, Some(o2(reference = r0.resultOpt.getOrElse(o2.reference))))
      else
        TPostResult(r0.ctx, None())
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.ConnectorEnd = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.ConnectorEnd] = pp.postSysmlAstConnectorEnd(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformSysmlAstBinaryConnectorPart(ctx: Context, o: SysmlAst.BinaryConnectorPart): TPostResult[Context, SysmlAst.BinaryConnectorPart] = {
    val preR: PreResult[Context, SysmlAst.BinaryConnectorPart] = pp.preSysmlAstBinaryConnectorPart(ctx, o)
    val r: TPostResult[Context, SysmlAst.BinaryConnectorPart] = if (preR.continu) {
      val o2: SysmlAst.BinaryConnectorPart = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: TPostResult[Context, SysmlAst.ConnectorEnd] = transformSysmlAstConnectorEnd(preR.ctx, o2.src)
      val r1: TPostResult[Context, SysmlAst.ConnectorEnd] = transformSysmlAstConnectorEnd(r0.ctx, o2.dst)
      if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty)
        TPostResult(r1.ctx, Some(o2(src = r0.resultOpt.getOrElse(o2.src), dst = r1.resultOpt.getOrElse(o2.dst))))
      else
        TPostResult(r1.ctx, None())
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.BinaryConnectorPart = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.BinaryConnectorPart] = pp.postSysmlAstBinaryConnectorPart(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformSysmlAstNaryConnectorPart(ctx: Context, o: SysmlAst.NaryConnectorPart): TPostResult[Context, SysmlAst.NaryConnectorPart] = {
    val preR: PreResult[Context, SysmlAst.NaryConnectorPart] = pp.preSysmlAstNaryConnectorPart(ctx, o)
    val r: TPostResult[Context, SysmlAst.NaryConnectorPart] = if (preR.continu) {
      val o2: SysmlAst.NaryConnectorPart = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: TPostResult[Context, IS[Z, SysmlAst.ConnectorEnd]] = transformISZ(preR.ctx, o2.connectorEnds, transformSysmlAstConnectorEnd _)
      if (hasChanged || r0.resultOpt.nonEmpty)
        TPostResult(r0.ctx, Some(o2(connectorEnds = r0.resultOpt.getOrElse(o2.connectorEnds))))
      else
        TPostResult(r0.ctx, None())
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.NaryConnectorPart = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.NaryConnectorPart] = pp.postSysmlAstNaryConnectorPart(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformSysmlAstFeatureSpecialization(ctx: Context, o: SysmlAst.FeatureSpecialization): TPostResult[Context, SysmlAst.FeatureSpecialization] = {
    val preR: PreResult[Context, SysmlAst.FeatureSpecialization] = pp.preSysmlAstFeatureSpecialization(ctx, o)
    val r: TPostResult[Context, SysmlAst.FeatureSpecialization] = if (preR.continu) {
      val o2: SysmlAst.FeatureSpecialization = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val rOpt: TPostResult[Context, SysmlAst.FeatureSpecialization] = o2 match {
        case o2: SysmlAst.TypingsSpecialization =>
          val r0: TPostResult[Context, IS[Z, SysmlAst.Name]] = transformISZ(preR.ctx, o2.names, transformSysmlAstName _)
          if (hasChanged || r0.resultOpt.nonEmpty)
            TPostResult(r0.ctx, Some(o2(names = r0.resultOpt.getOrElse(o2.names))))
          else
            TPostResult(r0.ctx, None())
        case o2: SysmlAst.SubsettingsSpecialization =>
          val r0: TPostResult[Context, IS[Z, SysmlAst.Name]] = transformISZ(preR.ctx, o2.subsettings, transformSysmlAstName _)
          if (hasChanged || r0.resultOpt.nonEmpty)
            TPostResult(r0.ctx, Some(o2(subsettings = r0.resultOpt.getOrElse(o2.subsettings))))
          else
            TPostResult(r0.ctx, None())
        case o2: SysmlAst.ReferencesSpecialization =>
          val r0: TPostResult[Context, IS[Z, SysmlAst.Name]] = transformISZ(preR.ctx, o2.references, transformSysmlAstName _)
          if (hasChanged || r0.resultOpt.nonEmpty)
            TPostResult(r0.ctx, Some(o2(references = r0.resultOpt.getOrElse(o2.references))))
          else
            TPostResult(r0.ctx, None())
        case o2: SysmlAst.RedefinitionsSpecialization =>
          val r0: TPostResult[Context, IS[Z, SysmlAst.Name]] = transformISZ(preR.ctx, o2.references, transformSysmlAstName _)
          if (hasChanged || r0.resultOpt.nonEmpty)
            TPostResult(r0.ctx, Some(o2(references = r0.resultOpt.getOrElse(o2.references))))
          else
            TPostResult(r0.ctx, None())
      }
      rOpt
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.FeatureSpecialization = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.FeatureSpecialization] = pp.postSysmlAstFeatureSpecialization(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformSysmlAstTypingsSpecialization(ctx: Context, o: SysmlAst.TypingsSpecialization): TPostResult[Context, SysmlAst.TypingsSpecialization] = {
    val preR: PreResult[Context, SysmlAst.TypingsSpecialization] = pp.preSysmlAstTypingsSpecialization(ctx, o)
    val r: TPostResult[Context, SysmlAst.TypingsSpecialization] = if (preR.continu) {
      val o2: SysmlAst.TypingsSpecialization = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: TPostResult[Context, IS[Z, SysmlAst.Name]] = transformISZ(preR.ctx, o2.names, transformSysmlAstName _)
      if (hasChanged || r0.resultOpt.nonEmpty)
        TPostResult(r0.ctx, Some(o2(names = r0.resultOpt.getOrElse(o2.names))))
      else
        TPostResult(r0.ctx, None())
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.TypingsSpecialization = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.TypingsSpecialization] = pp.postSysmlAstTypingsSpecialization(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformSysmlAstSubsettingsSpecialization(ctx: Context, o: SysmlAst.SubsettingsSpecialization): TPostResult[Context, SysmlAst.SubsettingsSpecialization] = {
    val preR: PreResult[Context, SysmlAst.SubsettingsSpecialization] = pp.preSysmlAstSubsettingsSpecialization(ctx, o)
    val r: TPostResult[Context, SysmlAst.SubsettingsSpecialization] = if (preR.continu) {
      val o2: SysmlAst.SubsettingsSpecialization = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: TPostResult[Context, IS[Z, SysmlAst.Name]] = transformISZ(preR.ctx, o2.subsettings, transformSysmlAstName _)
      if (hasChanged || r0.resultOpt.nonEmpty)
        TPostResult(r0.ctx, Some(o2(subsettings = r0.resultOpt.getOrElse(o2.subsettings))))
      else
        TPostResult(r0.ctx, None())
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.SubsettingsSpecialization = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.SubsettingsSpecialization] = pp.postSysmlAstSubsettingsSpecialization(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformSysmlAstReferencesSpecialization(ctx: Context, o: SysmlAst.ReferencesSpecialization): TPostResult[Context, SysmlAst.ReferencesSpecialization] = {
    val preR: PreResult[Context, SysmlAst.ReferencesSpecialization] = pp.preSysmlAstReferencesSpecialization(ctx, o)
    val r: TPostResult[Context, SysmlAst.ReferencesSpecialization] = if (preR.continu) {
      val o2: SysmlAst.ReferencesSpecialization = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: TPostResult[Context, IS[Z, SysmlAst.Name]] = transformISZ(preR.ctx, o2.references, transformSysmlAstName _)
      if (hasChanged || r0.resultOpt.nonEmpty)
        TPostResult(r0.ctx, Some(o2(references = r0.resultOpt.getOrElse(o2.references))))
      else
        TPostResult(r0.ctx, None())
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.ReferencesSpecialization = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.ReferencesSpecialization] = pp.postSysmlAstReferencesSpecialization(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformSysmlAstRedefinitionsSpecialization(ctx: Context, o: SysmlAst.RedefinitionsSpecialization): TPostResult[Context, SysmlAst.RedefinitionsSpecialization] = {
    val preR: PreResult[Context, SysmlAst.RedefinitionsSpecialization] = pp.preSysmlAstRedefinitionsSpecialization(ctx, o)
    val r: TPostResult[Context, SysmlAst.RedefinitionsSpecialization] = if (preR.continu) {
      val o2: SysmlAst.RedefinitionsSpecialization = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: TPostResult[Context, IS[Z, SysmlAst.Name]] = transformISZ(preR.ctx, o2.references, transformSysmlAstName _)
      if (hasChanged || r0.resultOpt.nonEmpty)
        TPostResult(r0.ctx, Some(o2(references = r0.resultOpt.getOrElse(o2.references))))
      else
        TPostResult(r0.ctx, None())
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.RedefinitionsSpecialization = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.RedefinitionsSpecialization] = pp.postSysmlAstRedefinitionsSpecialization(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformSysmlAstDefinitionElement(ctx: Context, o: SysmlAst.DefinitionElement): TPostResult[Context, SysmlAst.DefinitionElement] = {
    val preR: PreResult[Context, SysmlAst.DefinitionElement] = pp.preSysmlAstDefinitionElement(ctx, o)
    val r: TPostResult[Context, SysmlAst.DefinitionElement] = if (preR.continu) {
      val o2: SysmlAst.DefinitionElement = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val rOpt: TPostResult[Context, SysmlAst.DefinitionElement] = o2 match {
        case o2: SysmlAst.Package =>
          val r0: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(preR.ctx, o2.identification, transformSysmlAstIdentification _)
          val r1: TPostResult[Context, IS[Z, SysmlAst.PackageBodyElement]] = transformISZ(r0.ctx, o2.packageElements, transformSysmlAstPackageBodyElement _)
          val r2: TPostResult[Context, Attr] = transformAttr(r1.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty)
            TPostResult(r2.ctx, Some(o2(identification = r0.resultOpt.getOrElse(o2.identification), packageElements = r1.resultOpt.getOrElse(o2.packageElements), attr = r2.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r2.ctx, None())
        case o2: SysmlAst.AttributeDefinition =>
          val r0: TPostResult[Context, SysmlAst.DefinitionPrefix] = transformSysmlAstDefinitionPrefix(preR.ctx, o2.defPrefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.Name]] = transformISZ(r1.ctx, o2.subClassifications, transformSysmlAstName _)
          val r3: TPostResult[Context, IS[Z, Type.Named]] = transformISZ(r2.ctx, o2.parents, transformTypeNamed _)
          val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.bodyItems, transformSysmlAstBodyElement _)
          val r5: TPostResult[Context, Attr] = transformAttr(r4.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty)
            TPostResult(r5.ctx, Some(o2(defPrefix = r0.resultOpt.getOrElse(o2.defPrefix), identification = r1.resultOpt.getOrElse(o2.identification), subClassifications = r2.resultOpt.getOrElse(o2.subClassifications), parents = r3.resultOpt.getOrElse(o2.parents), bodyItems = r4.resultOpt.getOrElse(o2.bodyItems), attr = r5.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r5.ctx, None())
        case o2: SysmlAst.AllocationDefinition =>
          val r0: TPostResult[Context, SysmlAst.OccurrenceDefinitionPrefix] = transformSysmlAstOccurrenceDefinitionPrefix(preR.ctx, o2.occurrenceDefPrefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.Name]] = transformISZ(r1.ctx, o2.subClassifications, transformSysmlAstName _)
          val r3: TPostResult[Context, IS[Z, Type.Named]] = transformISZ(r2.ctx, o2.parents, transformTypeNamed _)
          val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.bodyItems, transformSysmlAstBodyElement _)
          val r5: TPostResult[Context, Attr] = transformAttr(r4.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty)
            TPostResult(r5.ctx, Some(o2(occurrenceDefPrefix = r0.resultOpt.getOrElse(o2.occurrenceDefPrefix), identification = r1.resultOpt.getOrElse(o2.identification), subClassifications = r2.resultOpt.getOrElse(o2.subClassifications), parents = r3.resultOpt.getOrElse(o2.parents), bodyItems = r4.resultOpt.getOrElse(o2.bodyItems), attr = r5.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r5.ctx, None())
        case o2: SysmlAst.ConnectionDefinition =>
          val r0: TPostResult[Context, SysmlAst.OccurrenceDefinitionPrefix] = transformSysmlAstOccurrenceDefinitionPrefix(preR.ctx, o2.occurrenceDefPrefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.Name]] = transformISZ(r1.ctx, o2.subClassifications, transformSysmlAstName _)
          val r3: TPostResult[Context, IS[Z, Type.Named]] = transformISZ(r2.ctx, o2.parents, transformTypeNamed _)
          val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.bodyItems, transformSysmlAstBodyElement _)
          val r5: TPostResult[Context, Attr] = transformAttr(r4.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty)
            TPostResult(r5.ctx, Some(o2(occurrenceDefPrefix = r0.resultOpt.getOrElse(o2.occurrenceDefPrefix), identification = r1.resultOpt.getOrElse(o2.identification), subClassifications = r2.resultOpt.getOrElse(o2.subClassifications), parents = r3.resultOpt.getOrElse(o2.parents), bodyItems = r4.resultOpt.getOrElse(o2.bodyItems), attr = r5.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r5.ctx, None())
        case o2: SysmlAst.EnumerationDefinition =>
          val r0: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(preR.ctx, o2.identification, transformSysmlAstIdentification _)
          val r1: TPostResult[Context, IS[Z, SysmlAst.Name]] = transformISZ(r0.ctx, o2.subClassifications, transformSysmlAstName _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.AnnotatingElement]] = transformISZ(r1.ctx, o2.annotations, transformSysmlAstAnnotatingElement _)
          val r3: TPostResult[Context, IS[Z, SysmlAst.EnumeratedValue]] = transformISZ(r2.ctx, o2.enumValues, transformSysmlAstEnumeratedValue _)
          val r4: TPostResult[Context, Attr] = transformAttr(r3.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty)
            TPostResult(r4.ctx, Some(o2(identification = r0.resultOpt.getOrElse(o2.identification), subClassifications = r1.resultOpt.getOrElse(o2.subClassifications), annotations = r2.resultOpt.getOrElse(o2.annotations), enumValues = r3.resultOpt.getOrElse(o2.enumValues), attr = r4.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r4.ctx, None())
        case o2: SysmlAst.PartDefinition =>
          val r0: TPostResult[Context, SysmlAst.OccurrenceDefinitionPrefix] = transformSysmlAstOccurrenceDefinitionPrefix(preR.ctx, o2.occurrenceDefPrefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.Name]] = transformISZ(r1.ctx, o2.subClassifications, transformSysmlAstName _)
          val r3: TPostResult[Context, IS[Z, Type.Named]] = transformISZ(r2.ctx, o2.parents, transformTypeNamed _)
          val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.bodyItems, transformSysmlAstBodyElement _)
          val r5: TPostResult[Context, Attr] = transformAttr(r4.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty)
            TPostResult(r5.ctx, Some(o2(occurrenceDefPrefix = r0.resultOpt.getOrElse(o2.occurrenceDefPrefix), identification = r1.resultOpt.getOrElse(o2.identification), subClassifications = r2.resultOpt.getOrElse(o2.subClassifications), parents = r3.resultOpt.getOrElse(o2.parents), bodyItems = r4.resultOpt.getOrElse(o2.bodyItems), attr = r5.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r5.ctx, None())
        case o2: SysmlAst.PortDefinition =>
          val r0: TPostResult[Context, SysmlAst.DefinitionPrefix] = transformSysmlAstDefinitionPrefix(preR.ctx, o2.defPrefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.Name]] = transformISZ(r1.ctx, o2.subClassifications, transformSysmlAstName _)
          val r3: TPostResult[Context, IS[Z, Type.Named]] = transformISZ(r2.ctx, o2.parents, transformTypeNamed _)
          val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.bodyItems, transformSysmlAstBodyElement _)
          val r5: TPostResult[Context, Attr] = transformAttr(r4.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty)
            TPostResult(r5.ctx, Some(o2(defPrefix = r0.resultOpt.getOrElse(o2.defPrefix), identification = r1.resultOpt.getOrElse(o2.identification), subClassifications = r2.resultOpt.getOrElse(o2.subClassifications), parents = r3.resultOpt.getOrElse(o2.parents), bodyItems = r4.resultOpt.getOrElse(o2.bodyItems), attr = r5.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r5.ctx, None())
        case o2: SysmlAst.MetadataDefinition =>
          val r0: TPostResult[Context, Attr] = transformAttr(preR.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty)
            TPostResult(r0.ctx, Some(o2(attr = r0.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r0.ctx, None())
        case o2: SysmlAst.Comment =>
          val r0: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(preR.ctx, o2.id, transformSysmlAstIdentification _)
          val r1: TPostResult[Context, IS[Z, SysmlAst.Name]] = transformISZ(r0.ctx, o2.abouts, transformSysmlAstName _)
          val r2: TPostResult[Context, Attr] = transformAttr(r1.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty)
            TPostResult(r2.ctx, Some(o2(id = r0.resultOpt.getOrElse(o2.id), abouts = r1.resultOpt.getOrElse(o2.abouts), attr = r2.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r2.ctx, None())
        case o2: SysmlAst.Documentation =>
          val r0: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(preR.ctx, o2.id, transformSysmlAstIdentification _)
          val r1: TPostResult[Context, Attr] = transformAttr(r0.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty)
            TPostResult(r1.ctx, Some(o2(id = r0.resultOpt.getOrElse(o2.id), attr = r1.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r1.ctx, None())
        case o2: SysmlAst.TextualRepresentation =>
          val r0: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(preR.ctx, o2.id, transformSysmlAstIdentification _)
          val r1: TPostResult[Context, Attr] = transformAttr(r0.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty)
            TPostResult(r1.ctx, Some(o2(id = r0.resultOpt.getOrElse(o2.id), attr = r1.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r1.ctx, None())
      }
      rOpt
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.DefinitionElement = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.DefinitionElement] = pp.postSysmlAstDefinitionElement(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformSysmlAstDefinitionPrefix(ctx: Context, o: SysmlAst.DefinitionPrefix): TPostResult[Context, SysmlAst.DefinitionPrefix] = {
    val preR: PreResult[Context, SysmlAst.DefinitionPrefix] = pp.preSysmlAstDefinitionPrefix(ctx, o)
    val r: TPostResult[Context, SysmlAst.DefinitionPrefix] = if (preR.continu) {
      val o2: SysmlAst.DefinitionPrefix = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      if (hasChanged)
        TPostResult(preR.ctx, Some(o2))
      else
        TPostResult(preR.ctx, None())
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.DefinitionPrefix = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.DefinitionPrefix] = pp.postSysmlAstDefinitionPrefix(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformSysmlAstOccurrenceDefinitionPrefix(ctx: Context, o: SysmlAst.OccurrenceDefinitionPrefix): TPostResult[Context, SysmlAst.OccurrenceDefinitionPrefix] = {
    val preR: PreResult[Context, SysmlAst.OccurrenceDefinitionPrefix] = pp.preSysmlAstOccurrenceDefinitionPrefix(ctx, o)
    val r: TPostResult[Context, SysmlAst.OccurrenceDefinitionPrefix] = if (preR.continu) {
      val o2: SysmlAst.OccurrenceDefinitionPrefix = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      if (hasChanged)
        TPostResult(preR.ctx, Some(o2))
      else
        TPostResult(preR.ctx, None())
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.OccurrenceDefinitionPrefix = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.OccurrenceDefinitionPrefix] = pp.postSysmlAstOccurrenceDefinitionPrefix(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformSysmlAstPackage(ctx: Context, o: SysmlAst.Package): TPostResult[Context, SysmlAst.Package] = {
    val preR: PreResult[Context, SysmlAst.Package] = pp.preSysmlAstPackage(ctx, o)
    val r: TPostResult[Context, SysmlAst.Package] = if (preR.continu) {
      val o2: SysmlAst.Package = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(preR.ctx, o2.identification, transformSysmlAstIdentification _)
      val r1: TPostResult[Context, IS[Z, SysmlAst.PackageBodyElement]] = transformISZ(r0.ctx, o2.packageElements, transformSysmlAstPackageBodyElement _)
      val r2: TPostResult[Context, Attr] = transformAttr(r1.ctx, o2.attr)
      if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty)
        TPostResult(r2.ctx, Some(o2(identification = r0.resultOpt.getOrElse(o2.identification), packageElements = r1.resultOpt.getOrElse(o2.packageElements), attr = r2.resultOpt.getOrElse(o2.attr))))
      else
        TPostResult(r2.ctx, None())
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.Package = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.Package] = pp.postSysmlAstPackage(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformSysmlAstAttributeDefinition(ctx: Context, o: SysmlAst.AttributeDefinition): TPostResult[Context, SysmlAst.AttributeDefinition] = {
    val preR: PreResult[Context, SysmlAst.AttributeDefinition] = pp.preSysmlAstAttributeDefinition(ctx, o)
    val r: TPostResult[Context, SysmlAst.AttributeDefinition] = if (preR.continu) {
      val o2: SysmlAst.AttributeDefinition = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: TPostResult[Context, SysmlAst.DefinitionPrefix] = transformSysmlAstDefinitionPrefix(preR.ctx, o2.defPrefix)
      val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
      val r2: TPostResult[Context, IS[Z, SysmlAst.Name]] = transformISZ(r1.ctx, o2.subClassifications, transformSysmlAstName _)
      val r3: TPostResult[Context, IS[Z, Type.Named]] = transformISZ(r2.ctx, o2.parents, transformTypeNamed _)
      val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.bodyItems, transformSysmlAstBodyElement _)
      val r5: TPostResult[Context, Attr] = transformAttr(r4.ctx, o2.attr)
      if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty)
        TPostResult(r5.ctx, Some(o2(defPrefix = r0.resultOpt.getOrElse(o2.defPrefix), identification = r1.resultOpt.getOrElse(o2.identification), subClassifications = r2.resultOpt.getOrElse(o2.subClassifications), parents = r3.resultOpt.getOrElse(o2.parents), bodyItems = r4.resultOpt.getOrElse(o2.bodyItems), attr = r5.resultOpt.getOrElse(o2.attr))))
      else
        TPostResult(r5.ctx, None())
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.AttributeDefinition = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.AttributeDefinition] = pp.postSysmlAstAttributeDefinition(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformSysmlAstAllocationDefinition(ctx: Context, o: SysmlAst.AllocationDefinition): TPostResult[Context, SysmlAst.AllocationDefinition] = {
    val preR: PreResult[Context, SysmlAst.AllocationDefinition] = pp.preSysmlAstAllocationDefinition(ctx, o)
    val r: TPostResult[Context, SysmlAst.AllocationDefinition] = if (preR.continu) {
      val o2: SysmlAst.AllocationDefinition = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: TPostResult[Context, SysmlAst.OccurrenceDefinitionPrefix] = transformSysmlAstOccurrenceDefinitionPrefix(preR.ctx, o2.occurrenceDefPrefix)
      val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
      val r2: TPostResult[Context, IS[Z, SysmlAst.Name]] = transformISZ(r1.ctx, o2.subClassifications, transformSysmlAstName _)
      val r3: TPostResult[Context, IS[Z, Type.Named]] = transformISZ(r2.ctx, o2.parents, transformTypeNamed _)
      val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.bodyItems, transformSysmlAstBodyElement _)
      val r5: TPostResult[Context, Attr] = transformAttr(r4.ctx, o2.attr)
      if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty)
        TPostResult(r5.ctx, Some(o2(occurrenceDefPrefix = r0.resultOpt.getOrElse(o2.occurrenceDefPrefix), identification = r1.resultOpt.getOrElse(o2.identification), subClassifications = r2.resultOpt.getOrElse(o2.subClassifications), parents = r3.resultOpt.getOrElse(o2.parents), bodyItems = r4.resultOpt.getOrElse(o2.bodyItems), attr = r5.resultOpt.getOrElse(o2.attr))))
      else
        TPostResult(r5.ctx, None())
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.AllocationDefinition = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.AllocationDefinition] = pp.postSysmlAstAllocationDefinition(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformSysmlAstConnectionDefinition(ctx: Context, o: SysmlAst.ConnectionDefinition): TPostResult[Context, SysmlAst.ConnectionDefinition] = {
    val preR: PreResult[Context, SysmlAst.ConnectionDefinition] = pp.preSysmlAstConnectionDefinition(ctx, o)
    val r: TPostResult[Context, SysmlAst.ConnectionDefinition] = if (preR.continu) {
      val o2: SysmlAst.ConnectionDefinition = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: TPostResult[Context, SysmlAst.OccurrenceDefinitionPrefix] = transformSysmlAstOccurrenceDefinitionPrefix(preR.ctx, o2.occurrenceDefPrefix)
      val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
      val r2: TPostResult[Context, IS[Z, SysmlAst.Name]] = transformISZ(r1.ctx, o2.subClassifications, transformSysmlAstName _)
      val r3: TPostResult[Context, IS[Z, Type.Named]] = transformISZ(r2.ctx, o2.parents, transformTypeNamed _)
      val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.bodyItems, transformSysmlAstBodyElement _)
      val r5: TPostResult[Context, Attr] = transformAttr(r4.ctx, o2.attr)
      if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty)
        TPostResult(r5.ctx, Some(o2(occurrenceDefPrefix = r0.resultOpt.getOrElse(o2.occurrenceDefPrefix), identification = r1.resultOpt.getOrElse(o2.identification), subClassifications = r2.resultOpt.getOrElse(o2.subClassifications), parents = r3.resultOpt.getOrElse(o2.parents), bodyItems = r4.resultOpt.getOrElse(o2.bodyItems), attr = r5.resultOpt.getOrElse(o2.attr))))
      else
        TPostResult(r5.ctx, None())
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.ConnectionDefinition = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.ConnectionDefinition] = pp.postSysmlAstConnectionDefinition(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformSysmlAstEnumerationDefinition(ctx: Context, o: SysmlAst.EnumerationDefinition): TPostResult[Context, SysmlAst.EnumerationDefinition] = {
    val preR: PreResult[Context, SysmlAst.EnumerationDefinition] = pp.preSysmlAstEnumerationDefinition(ctx, o)
    val r: TPostResult[Context, SysmlAst.EnumerationDefinition] = if (preR.continu) {
      val o2: SysmlAst.EnumerationDefinition = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(preR.ctx, o2.identification, transformSysmlAstIdentification _)
      val r1: TPostResult[Context, IS[Z, SysmlAst.Name]] = transformISZ(r0.ctx, o2.subClassifications, transformSysmlAstName _)
      val r2: TPostResult[Context, IS[Z, SysmlAst.AnnotatingElement]] = transformISZ(r1.ctx, o2.annotations, transformSysmlAstAnnotatingElement _)
      val r3: TPostResult[Context, IS[Z, SysmlAst.EnumeratedValue]] = transformISZ(r2.ctx, o2.enumValues, transformSysmlAstEnumeratedValue _)
      val r4: TPostResult[Context, Attr] = transformAttr(r3.ctx, o2.attr)
      if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty)
        TPostResult(r4.ctx, Some(o2(identification = r0.resultOpt.getOrElse(o2.identification), subClassifications = r1.resultOpt.getOrElse(o2.subClassifications), annotations = r2.resultOpt.getOrElse(o2.annotations), enumValues = r3.resultOpt.getOrElse(o2.enumValues), attr = r4.resultOpt.getOrElse(o2.attr))))
      else
        TPostResult(r4.ctx, None())
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.EnumerationDefinition = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.EnumerationDefinition] = pp.postSysmlAstEnumerationDefinition(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformSysmlAstPartDefinition(ctx: Context, o: SysmlAst.PartDefinition): TPostResult[Context, SysmlAst.PartDefinition] = {
    val preR: PreResult[Context, SysmlAst.PartDefinition] = pp.preSysmlAstPartDefinition(ctx, o)
    val r: TPostResult[Context, SysmlAst.PartDefinition] = if (preR.continu) {
      val o2: SysmlAst.PartDefinition = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: TPostResult[Context, SysmlAst.OccurrenceDefinitionPrefix] = transformSysmlAstOccurrenceDefinitionPrefix(preR.ctx, o2.occurrenceDefPrefix)
      val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
      val r2: TPostResult[Context, IS[Z, SysmlAst.Name]] = transformISZ(r1.ctx, o2.subClassifications, transformSysmlAstName _)
      val r3: TPostResult[Context, IS[Z, Type.Named]] = transformISZ(r2.ctx, o2.parents, transformTypeNamed _)
      val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.bodyItems, transformSysmlAstBodyElement _)
      val r5: TPostResult[Context, Attr] = transformAttr(r4.ctx, o2.attr)
      if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty)
        TPostResult(r5.ctx, Some(o2(occurrenceDefPrefix = r0.resultOpt.getOrElse(o2.occurrenceDefPrefix), identification = r1.resultOpt.getOrElse(o2.identification), subClassifications = r2.resultOpt.getOrElse(o2.subClassifications), parents = r3.resultOpt.getOrElse(o2.parents), bodyItems = r4.resultOpt.getOrElse(o2.bodyItems), attr = r5.resultOpt.getOrElse(o2.attr))))
      else
        TPostResult(r5.ctx, None())
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.PartDefinition = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.PartDefinition] = pp.postSysmlAstPartDefinition(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformSysmlAstPortDefinition(ctx: Context, o: SysmlAst.PortDefinition): TPostResult[Context, SysmlAst.PortDefinition] = {
    val preR: PreResult[Context, SysmlAst.PortDefinition] = pp.preSysmlAstPortDefinition(ctx, o)
    val r: TPostResult[Context, SysmlAst.PortDefinition] = if (preR.continu) {
      val o2: SysmlAst.PortDefinition = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: TPostResult[Context, SysmlAst.DefinitionPrefix] = transformSysmlAstDefinitionPrefix(preR.ctx, o2.defPrefix)
      val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
      val r2: TPostResult[Context, IS[Z, SysmlAst.Name]] = transformISZ(r1.ctx, o2.subClassifications, transformSysmlAstName _)
      val r3: TPostResult[Context, IS[Z, Type.Named]] = transformISZ(r2.ctx, o2.parents, transformTypeNamed _)
      val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.bodyItems, transformSysmlAstBodyElement _)
      val r5: TPostResult[Context, Attr] = transformAttr(r4.ctx, o2.attr)
      if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty)
        TPostResult(r5.ctx, Some(o2(defPrefix = r0.resultOpt.getOrElse(o2.defPrefix), identification = r1.resultOpt.getOrElse(o2.identification), subClassifications = r2.resultOpt.getOrElse(o2.subClassifications), parents = r3.resultOpt.getOrElse(o2.parents), bodyItems = r4.resultOpt.getOrElse(o2.bodyItems), attr = r5.resultOpt.getOrElse(o2.attr))))
      else
        TPostResult(r5.ctx, None())
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.PortDefinition = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.PortDefinition] = pp.postSysmlAstPortDefinition(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformSysmlAstMetadataDefinition(ctx: Context, o: SysmlAst.MetadataDefinition): TPostResult[Context, SysmlAst.MetadataDefinition] = {
    val preR: PreResult[Context, SysmlAst.MetadataDefinition] = pp.preSysmlAstMetadataDefinition(ctx, o)
    val r: TPostResult[Context, SysmlAst.MetadataDefinition] = if (preR.continu) {
      val o2: SysmlAst.MetadataDefinition = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: TPostResult[Context, Attr] = transformAttr(preR.ctx, o2.attr)
      if (hasChanged || r0.resultOpt.nonEmpty)
        TPostResult(r0.ctx, Some(o2(attr = r0.resultOpt.getOrElse(o2.attr))))
      else
        TPostResult(r0.ctx, None())
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.MetadataDefinition = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.MetadataDefinition] = pp.postSysmlAstMetadataDefinition(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformSysmlAstUsagePrefix(ctx: Context, o: SysmlAst.UsagePrefix): TPostResult[Context, SysmlAst.UsagePrefix] = {
    val preR: PreResult[Context, SysmlAst.UsagePrefix] = pp.preSysmlAstUsagePrefix(ctx, o)
    val r: TPostResult[Context, SysmlAst.UsagePrefix] = if (preR.continu) {
      val o2: SysmlAst.UsagePrefix = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: TPostResult[Context, SysmlAst.RefPrefix] = transformSysmlAstRefPrefix(preR.ctx, o2.refPrefix)
      val r1: TPostResult[Context, IS[Z, SysmlAst.Name]] = transformISZ(r0.ctx, o2.usageExtensions, transformSysmlAstName _)
      if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty)
        TPostResult(r1.ctx, Some(o2(refPrefix = r0.resultOpt.getOrElse(o2.refPrefix), usageExtensions = r1.resultOpt.getOrElse(o2.usageExtensions))))
      else
        TPostResult(r1.ctx, None())
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.UsagePrefix = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.UsagePrefix] = pp.postSysmlAstUsagePrefix(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformSysmlAstOccurrenceUsagePrefix(ctx: Context, o: SysmlAst.OccurrenceUsagePrefix): TPostResult[Context, SysmlAst.OccurrenceUsagePrefix] = {
    val preR: PreResult[Context, SysmlAst.OccurrenceUsagePrefix] = pp.preSysmlAstOccurrenceUsagePrefix(ctx, o)
    val r: TPostResult[Context, SysmlAst.OccurrenceUsagePrefix] = if (preR.continu) {
      val o2: SysmlAst.OccurrenceUsagePrefix = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: TPostResult[Context, SysmlAst.RefPrefix] = transformSysmlAstRefPrefix(preR.ctx, o2.refPrefix)
      val r1: TPostResult[Context, IS[Z, SysmlAst.Name]] = transformISZ(r0.ctx, o2.usageExtensions, transformSysmlAstName _)
      if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty)
        TPostResult(r1.ctx, Some(o2(refPrefix = r0.resultOpt.getOrElse(o2.refPrefix), usageExtensions = r1.resultOpt.getOrElse(o2.usageExtensions))))
      else
        TPostResult(r1.ctx, None())
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.OccurrenceUsagePrefix = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.OccurrenceUsagePrefix] = pp.postSysmlAstOccurrenceUsagePrefix(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformSysmlAstRefPrefix(ctx: Context, o: SysmlAst.RefPrefix): TPostResult[Context, SysmlAst.RefPrefix] = {
    val preR: PreResult[Context, SysmlAst.RefPrefix] = pp.preSysmlAstRefPrefix(ctx, o)
    val r: TPostResult[Context, SysmlAst.RefPrefix] = if (preR.continu) {
      val o2: SysmlAst.RefPrefix = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      if (hasChanged)
        TPostResult(preR.ctx, Some(o2))
      else
        TPostResult(preR.ctx, None())
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.RefPrefix = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.RefPrefix] = pp.postSysmlAstRefPrefix(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformSysmlAstUsageElement(ctx: Context, o: SysmlAst.UsageElement): TPostResult[Context, SysmlAst.UsageElement] = {
    val preR: PreResult[Context, SysmlAst.UsageElement] = pp.preSysmlAstUsageElement(ctx, o)
    val r: TPostResult[Context, SysmlAst.UsageElement] = if (preR.continu) {
      val o2: SysmlAst.UsageElement = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val rOpt: TPostResult[Context, SysmlAst.UsageElement] = o2 match {
        case o2: SysmlAst.AttributeUsage =>
          val r0: TPostResult[Context, SysmlAst.UsagePrefix] = transformSysmlAstUsagePrefix(preR.ctx, o2.prefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(r1.ctx, o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: TPostResult[Context, Option[SysmlAst.FeatureValue]] = transformOption(r2.ctx, o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: TPostResult[Context, Option[Type]] = transformOption(r4.ctx, o2.tipeOpt, transformType _)
          val r6: TPostResult[Context, ResolvedAttr] = transformResolvedAttr(r5.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty || r6.resultOpt.nonEmpty)
            TPostResult(r6.ctx, Some(o2(prefix = r0.resultOpt.getOrElse(o2.prefix), identification = r1.resultOpt.getOrElse(o2.identification), specializations = r2.resultOpt.getOrElse(o2.specializations), featureValue = r3.resultOpt.getOrElse(o2.featureValue), definitionBodyItems = r4.resultOpt.getOrElse(o2.definitionBodyItems), tipeOpt = r5.resultOpt.getOrElse(o2.tipeOpt), attr = r6.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r6.ctx, None())
        case o2: SysmlAst.ReferenceUsage =>
          val r0: TPostResult[Context, SysmlAst.RefPrefix] = transformSysmlAstRefPrefix(preR.ctx, o2.prefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(r1.ctx, o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: TPostResult[Context, Option[SysmlAst.FeatureValue]] = transformOption(r2.ctx, o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: TPostResult[Context, Option[Type]] = transformOption(r4.ctx, o2.tipeOpt, transformType _)
          val r6: TPostResult[Context, ResolvedAttr] = transformResolvedAttr(r5.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty || r6.resultOpt.nonEmpty)
            TPostResult(r6.ctx, Some(o2(prefix = r0.resultOpt.getOrElse(o2.prefix), identification = r1.resultOpt.getOrElse(o2.identification), specializations = r2.resultOpt.getOrElse(o2.specializations), featureValue = r3.resultOpt.getOrElse(o2.featureValue), definitionBodyItems = r4.resultOpt.getOrElse(o2.definitionBodyItems), tipeOpt = r5.resultOpt.getOrElse(o2.tipeOpt), attr = r6.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r6.ctx, None())
        case o2: SysmlAst.ConnectionUsage =>
          val r0: TPostResult[Context, SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(preR.ctx, o2.occurrenceUsagePrefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(r1.ctx, o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: TPostResult[Context, Option[SysmlAst.FeatureValue]] = transformOption(r2.ctx, o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: TPostResult[Context, Option[SysmlAst.ConnectorPart]] = transformOption(r3.ctx, o2.connectorPart, transformSysmlAstConnectorPart _)
          val r5: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r4.ctx, o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r6: TPostResult[Context, Option[Type]] = transformOption(r5.ctx, o2.tipeOpt, transformType _)
          val r7: TPostResult[Context, ResolvedAttr] = transformResolvedAttr(r6.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty || r6.resultOpt.nonEmpty || r7.resultOpt.nonEmpty)
            TPostResult(r7.ctx, Some(o2(occurrenceUsagePrefix = r0.resultOpt.getOrElse(o2.occurrenceUsagePrefix), identification = r1.resultOpt.getOrElse(o2.identification), specializations = r2.resultOpt.getOrElse(o2.specializations), featureValue = r3.resultOpt.getOrElse(o2.featureValue), connectorPart = r4.resultOpt.getOrElse(o2.connectorPart), definitionBodyItems = r5.resultOpt.getOrElse(o2.definitionBodyItems), tipeOpt = r6.resultOpt.getOrElse(o2.tipeOpt), attr = r7.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r7.ctx, None())
        case o2: SysmlAst.ItemUsage =>
          val r0: TPostResult[Context, SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(preR.ctx, o2.occurrenceUsagePrefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(r1.ctx, o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: TPostResult[Context, Option[SysmlAst.FeatureValue]] = transformOption(r2.ctx, o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: TPostResult[Context, Option[Type]] = transformOption(r4.ctx, o2.tipeOpt, transformType _)
          val r6: TPostResult[Context, ResolvedAttr] = transformResolvedAttr(r5.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty || r6.resultOpt.nonEmpty)
            TPostResult(r6.ctx, Some(o2(occurrenceUsagePrefix = r0.resultOpt.getOrElse(o2.occurrenceUsagePrefix), identification = r1.resultOpt.getOrElse(o2.identification), specializations = r2.resultOpt.getOrElse(o2.specializations), featureValue = r3.resultOpt.getOrElse(o2.featureValue), definitionBodyItems = r4.resultOpt.getOrElse(o2.definitionBodyItems), tipeOpt = r5.resultOpt.getOrElse(o2.tipeOpt), attr = r6.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r6.ctx, None())
        case o2: SysmlAst.PartUsage =>
          val r0: TPostResult[Context, SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(preR.ctx, o2.occurrenceUsagePrefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(r1.ctx, o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: TPostResult[Context, Option[SysmlAst.FeatureValue]] = transformOption(r2.ctx, o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: TPostResult[Context, Option[Type]] = transformOption(r4.ctx, o2.tipeOpt, transformType _)
          val r6: TPostResult[Context, ResolvedAttr] = transformResolvedAttr(r5.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty || r6.resultOpt.nonEmpty)
            TPostResult(r6.ctx, Some(o2(occurrenceUsagePrefix = r0.resultOpt.getOrElse(o2.occurrenceUsagePrefix), identification = r1.resultOpt.getOrElse(o2.identification), specializations = r2.resultOpt.getOrElse(o2.specializations), featureValue = r3.resultOpt.getOrElse(o2.featureValue), definitionBodyItems = r4.resultOpt.getOrElse(o2.definitionBodyItems), tipeOpt = r5.resultOpt.getOrElse(o2.tipeOpt), attr = r6.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r6.ctx, None())
        case o2: SysmlAst.PortUsage =>
          val r0: TPostResult[Context, SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(preR.ctx, o2.occurrenceUsagePrefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(r1.ctx, o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: TPostResult[Context, Option[SysmlAst.FeatureValue]] = transformOption(r2.ctx, o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: TPostResult[Context, Option[Type]] = transformOption(r4.ctx, o2.tipeOpt, transformType _)
          val r6: TPostResult[Context, ResolvedAttr] = transformResolvedAttr(r5.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty || r6.resultOpt.nonEmpty)
            TPostResult(r6.ctx, Some(o2(occurrenceUsagePrefix = r0.resultOpt.getOrElse(o2.occurrenceUsagePrefix), identification = r1.resultOpt.getOrElse(o2.identification), specializations = r2.resultOpt.getOrElse(o2.specializations), featureValue = r3.resultOpt.getOrElse(o2.featureValue), definitionBodyItems = r4.resultOpt.getOrElse(o2.definitionBodyItems), tipeOpt = r5.resultOpt.getOrElse(o2.tipeOpt), attr = r6.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r6.ctx, None())
      }
      rOpt
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.UsageElement = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.UsageElement] = pp.postSysmlAstUsageElement(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformSysmlAstNonOccurrenceUsageElement(ctx: Context, o: SysmlAst.NonOccurrenceUsageElement): TPostResult[Context, SysmlAst.NonOccurrenceUsageElement] = {
    val preR: PreResult[Context, SysmlAst.NonOccurrenceUsageElement] = pp.preSysmlAstNonOccurrenceUsageElement(ctx, o)
    val r: TPostResult[Context, SysmlAst.NonOccurrenceUsageElement] = if (preR.continu) {
      val o2: SysmlAst.NonOccurrenceUsageElement = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val rOpt: TPostResult[Context, SysmlAst.NonOccurrenceUsageElement] = o2 match {
        case o2: SysmlAst.AttributeUsage =>
          val r0: TPostResult[Context, SysmlAst.UsagePrefix] = transformSysmlAstUsagePrefix(preR.ctx, o2.prefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(r1.ctx, o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: TPostResult[Context, Option[SysmlAst.FeatureValue]] = transformOption(r2.ctx, o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: TPostResult[Context, Option[Type]] = transformOption(r4.ctx, o2.tipeOpt, transformType _)
          val r6: TPostResult[Context, ResolvedAttr] = transformResolvedAttr(r5.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty || r6.resultOpt.nonEmpty)
            TPostResult(r6.ctx, Some(o2(prefix = r0.resultOpt.getOrElse(o2.prefix), identification = r1.resultOpt.getOrElse(o2.identification), specializations = r2.resultOpt.getOrElse(o2.specializations), featureValue = r3.resultOpt.getOrElse(o2.featureValue), definitionBodyItems = r4.resultOpt.getOrElse(o2.definitionBodyItems), tipeOpt = r5.resultOpt.getOrElse(o2.tipeOpt), attr = r6.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r6.ctx, None())
        case o2: SysmlAst.ReferenceUsage =>
          val r0: TPostResult[Context, SysmlAst.RefPrefix] = transformSysmlAstRefPrefix(preR.ctx, o2.prefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(r1.ctx, o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: TPostResult[Context, Option[SysmlAst.FeatureValue]] = transformOption(r2.ctx, o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: TPostResult[Context, Option[Type]] = transformOption(r4.ctx, o2.tipeOpt, transformType _)
          val r6: TPostResult[Context, ResolvedAttr] = transformResolvedAttr(r5.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty || r6.resultOpt.nonEmpty)
            TPostResult(r6.ctx, Some(o2(prefix = r0.resultOpt.getOrElse(o2.prefix), identification = r1.resultOpt.getOrElse(o2.identification), specializations = r2.resultOpt.getOrElse(o2.specializations), featureValue = r3.resultOpt.getOrElse(o2.featureValue), definitionBodyItems = r4.resultOpt.getOrElse(o2.definitionBodyItems), tipeOpt = r5.resultOpt.getOrElse(o2.tipeOpt), attr = r6.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r6.ctx, None())
      }
      rOpt
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.NonOccurrenceUsageElement = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.NonOccurrenceUsageElement] = pp.postSysmlAstNonOccurrenceUsageElement(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformSysmlAstAttributeUsage(ctx: Context, o: SysmlAst.AttributeUsage): TPostResult[Context, SysmlAst.AttributeUsage] = {
    val preR: PreResult[Context, SysmlAst.AttributeUsage] = pp.preSysmlAstAttributeUsage(ctx, o)
    val r: TPostResult[Context, SysmlAst.AttributeUsage] = if (preR.continu) {
      val o2: SysmlAst.AttributeUsage = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: TPostResult[Context, SysmlAst.UsagePrefix] = transformSysmlAstUsagePrefix(preR.ctx, o2.prefix)
      val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
      val r2: TPostResult[Context, IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(r1.ctx, o2.specializations, transformSysmlAstFeatureSpecialization _)
      val r3: TPostResult[Context, Option[SysmlAst.FeatureValue]] = transformOption(r2.ctx, o2.featureValue, transformSysmlAstFeatureValue _)
      val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.definitionBodyItems, transformSysmlAstBodyElement _)
      val r5: TPostResult[Context, Option[Type]] = transformOption(r4.ctx, o2.tipeOpt, transformType _)
      val r6: TPostResult[Context, ResolvedAttr] = transformResolvedAttr(r5.ctx, o2.attr)
      if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty || r6.resultOpt.nonEmpty)
        TPostResult(r6.ctx, Some(o2(prefix = r0.resultOpt.getOrElse(o2.prefix), identification = r1.resultOpt.getOrElse(o2.identification), specializations = r2.resultOpt.getOrElse(o2.specializations), featureValue = r3.resultOpt.getOrElse(o2.featureValue), definitionBodyItems = r4.resultOpt.getOrElse(o2.definitionBodyItems), tipeOpt = r5.resultOpt.getOrElse(o2.tipeOpt), attr = r6.resultOpt.getOrElse(o2.attr))))
      else
        TPostResult(r6.ctx, None())
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.AttributeUsage = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.AttributeUsage] = pp.postSysmlAstAttributeUsage(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformSysmlAstReferenceUsage(ctx: Context, o: SysmlAst.ReferenceUsage): TPostResult[Context, SysmlAst.ReferenceUsage] = {
    val preR: PreResult[Context, SysmlAst.ReferenceUsage] = pp.preSysmlAstReferenceUsage(ctx, o)
    val r: TPostResult[Context, SysmlAst.ReferenceUsage] = if (preR.continu) {
      val o2: SysmlAst.ReferenceUsage = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: TPostResult[Context, SysmlAst.RefPrefix] = transformSysmlAstRefPrefix(preR.ctx, o2.prefix)
      val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
      val r2: TPostResult[Context, IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(r1.ctx, o2.specializations, transformSysmlAstFeatureSpecialization _)
      val r3: TPostResult[Context, Option[SysmlAst.FeatureValue]] = transformOption(r2.ctx, o2.featureValue, transformSysmlAstFeatureValue _)
      val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.definitionBodyItems, transformSysmlAstBodyElement _)
      val r5: TPostResult[Context, Option[Type]] = transformOption(r4.ctx, o2.tipeOpt, transformType _)
      val r6: TPostResult[Context, ResolvedAttr] = transformResolvedAttr(r5.ctx, o2.attr)
      if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty || r6.resultOpt.nonEmpty)
        TPostResult(r6.ctx, Some(o2(prefix = r0.resultOpt.getOrElse(o2.prefix), identification = r1.resultOpt.getOrElse(o2.identification), specializations = r2.resultOpt.getOrElse(o2.specializations), featureValue = r3.resultOpt.getOrElse(o2.featureValue), definitionBodyItems = r4.resultOpt.getOrElse(o2.definitionBodyItems), tipeOpt = r5.resultOpt.getOrElse(o2.tipeOpt), attr = r6.resultOpt.getOrElse(o2.attr))))
      else
        TPostResult(r6.ctx, None())
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.ReferenceUsage = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.ReferenceUsage] = pp.postSysmlAstReferenceUsage(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformSysmlAstOccurrenceUsageElement(ctx: Context, o: SysmlAst.OccurrenceUsageElement): TPostResult[Context, SysmlAst.OccurrenceUsageElement] = {
    val preR: PreResult[Context, SysmlAst.OccurrenceUsageElement] = pp.preSysmlAstOccurrenceUsageElement(ctx, o)
    val r: TPostResult[Context, SysmlAst.OccurrenceUsageElement] = if (preR.continu) {
      val o2: SysmlAst.OccurrenceUsageElement = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val rOpt: TPostResult[Context, SysmlAst.OccurrenceUsageElement] = o2 match {
        case o2: SysmlAst.ConnectionUsage =>
          val r0: TPostResult[Context, SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(preR.ctx, o2.occurrenceUsagePrefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(r1.ctx, o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: TPostResult[Context, Option[SysmlAst.FeatureValue]] = transformOption(r2.ctx, o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: TPostResult[Context, Option[SysmlAst.ConnectorPart]] = transformOption(r3.ctx, o2.connectorPart, transformSysmlAstConnectorPart _)
          val r5: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r4.ctx, o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r6: TPostResult[Context, Option[Type]] = transformOption(r5.ctx, o2.tipeOpt, transformType _)
          val r7: TPostResult[Context, ResolvedAttr] = transformResolvedAttr(r6.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty || r6.resultOpt.nonEmpty || r7.resultOpt.nonEmpty)
            TPostResult(r7.ctx, Some(o2(occurrenceUsagePrefix = r0.resultOpt.getOrElse(o2.occurrenceUsagePrefix), identification = r1.resultOpt.getOrElse(o2.identification), specializations = r2.resultOpt.getOrElse(o2.specializations), featureValue = r3.resultOpt.getOrElse(o2.featureValue), connectorPart = r4.resultOpt.getOrElse(o2.connectorPart), definitionBodyItems = r5.resultOpt.getOrElse(o2.definitionBodyItems), tipeOpt = r6.resultOpt.getOrElse(o2.tipeOpt), attr = r7.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r7.ctx, None())
        case o2: SysmlAst.ItemUsage =>
          val r0: TPostResult[Context, SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(preR.ctx, o2.occurrenceUsagePrefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(r1.ctx, o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: TPostResult[Context, Option[SysmlAst.FeatureValue]] = transformOption(r2.ctx, o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: TPostResult[Context, Option[Type]] = transformOption(r4.ctx, o2.tipeOpt, transformType _)
          val r6: TPostResult[Context, ResolvedAttr] = transformResolvedAttr(r5.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty || r6.resultOpt.nonEmpty)
            TPostResult(r6.ctx, Some(o2(occurrenceUsagePrefix = r0.resultOpt.getOrElse(o2.occurrenceUsagePrefix), identification = r1.resultOpt.getOrElse(o2.identification), specializations = r2.resultOpt.getOrElse(o2.specializations), featureValue = r3.resultOpt.getOrElse(o2.featureValue), definitionBodyItems = r4.resultOpt.getOrElse(o2.definitionBodyItems), tipeOpt = r5.resultOpt.getOrElse(o2.tipeOpt), attr = r6.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r6.ctx, None())
        case o2: SysmlAst.PartUsage =>
          val r0: TPostResult[Context, SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(preR.ctx, o2.occurrenceUsagePrefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(r1.ctx, o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: TPostResult[Context, Option[SysmlAst.FeatureValue]] = transformOption(r2.ctx, o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: TPostResult[Context, Option[Type]] = transformOption(r4.ctx, o2.tipeOpt, transformType _)
          val r6: TPostResult[Context, ResolvedAttr] = transformResolvedAttr(r5.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty || r6.resultOpt.nonEmpty)
            TPostResult(r6.ctx, Some(o2(occurrenceUsagePrefix = r0.resultOpt.getOrElse(o2.occurrenceUsagePrefix), identification = r1.resultOpt.getOrElse(o2.identification), specializations = r2.resultOpt.getOrElse(o2.specializations), featureValue = r3.resultOpt.getOrElse(o2.featureValue), definitionBodyItems = r4.resultOpt.getOrElse(o2.definitionBodyItems), tipeOpt = r5.resultOpt.getOrElse(o2.tipeOpt), attr = r6.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r6.ctx, None())
        case o2: SysmlAst.PortUsage =>
          val r0: TPostResult[Context, SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(preR.ctx, o2.occurrenceUsagePrefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(r1.ctx, o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: TPostResult[Context, Option[SysmlAst.FeatureValue]] = transformOption(r2.ctx, o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: TPostResult[Context, Option[Type]] = transformOption(r4.ctx, o2.tipeOpt, transformType _)
          val r6: TPostResult[Context, ResolvedAttr] = transformResolvedAttr(r5.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty || r6.resultOpt.nonEmpty)
            TPostResult(r6.ctx, Some(o2(occurrenceUsagePrefix = r0.resultOpt.getOrElse(o2.occurrenceUsagePrefix), identification = r1.resultOpt.getOrElse(o2.identification), specializations = r2.resultOpt.getOrElse(o2.specializations), featureValue = r3.resultOpt.getOrElse(o2.featureValue), definitionBodyItems = r4.resultOpt.getOrElse(o2.definitionBodyItems), tipeOpt = r5.resultOpt.getOrElse(o2.tipeOpt), attr = r6.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r6.ctx, None())
      }
      rOpt
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.OccurrenceUsageElement = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.OccurrenceUsageElement] = pp.postSysmlAstOccurrenceUsageElement(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformSysmlAstStructureUsageElement(ctx: Context, o: SysmlAst.StructureUsageElement): TPostResult[Context, SysmlAst.StructureUsageElement] = {
    val preR: PreResult[Context, SysmlAst.StructureUsageElement] = pp.preSysmlAstStructureUsageElement(ctx, o)
    val r: TPostResult[Context, SysmlAst.StructureUsageElement] = if (preR.continu) {
      val o2: SysmlAst.StructureUsageElement = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val rOpt: TPostResult[Context, SysmlAst.StructureUsageElement] = o2 match {
        case o2: SysmlAst.ConnectionUsage =>
          val r0: TPostResult[Context, SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(preR.ctx, o2.occurrenceUsagePrefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(r1.ctx, o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: TPostResult[Context, Option[SysmlAst.FeatureValue]] = transformOption(r2.ctx, o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: TPostResult[Context, Option[SysmlAst.ConnectorPart]] = transformOption(r3.ctx, o2.connectorPart, transformSysmlAstConnectorPart _)
          val r5: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r4.ctx, o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r6: TPostResult[Context, Option[Type]] = transformOption(r5.ctx, o2.tipeOpt, transformType _)
          val r7: TPostResult[Context, ResolvedAttr] = transformResolvedAttr(r6.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty || r6.resultOpt.nonEmpty || r7.resultOpt.nonEmpty)
            TPostResult(r7.ctx, Some(o2(occurrenceUsagePrefix = r0.resultOpt.getOrElse(o2.occurrenceUsagePrefix), identification = r1.resultOpt.getOrElse(o2.identification), specializations = r2.resultOpt.getOrElse(o2.specializations), featureValue = r3.resultOpt.getOrElse(o2.featureValue), connectorPart = r4.resultOpt.getOrElse(o2.connectorPart), definitionBodyItems = r5.resultOpt.getOrElse(o2.definitionBodyItems), tipeOpt = r6.resultOpt.getOrElse(o2.tipeOpt), attr = r7.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r7.ctx, None())
        case o2: SysmlAst.ItemUsage =>
          val r0: TPostResult[Context, SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(preR.ctx, o2.occurrenceUsagePrefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(r1.ctx, o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: TPostResult[Context, Option[SysmlAst.FeatureValue]] = transformOption(r2.ctx, o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: TPostResult[Context, Option[Type]] = transformOption(r4.ctx, o2.tipeOpt, transformType _)
          val r6: TPostResult[Context, ResolvedAttr] = transformResolvedAttr(r5.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty || r6.resultOpt.nonEmpty)
            TPostResult(r6.ctx, Some(o2(occurrenceUsagePrefix = r0.resultOpt.getOrElse(o2.occurrenceUsagePrefix), identification = r1.resultOpt.getOrElse(o2.identification), specializations = r2.resultOpt.getOrElse(o2.specializations), featureValue = r3.resultOpt.getOrElse(o2.featureValue), definitionBodyItems = r4.resultOpt.getOrElse(o2.definitionBodyItems), tipeOpt = r5.resultOpt.getOrElse(o2.tipeOpt), attr = r6.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r6.ctx, None())
        case o2: SysmlAst.PartUsage =>
          val r0: TPostResult[Context, SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(preR.ctx, o2.occurrenceUsagePrefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(r1.ctx, o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: TPostResult[Context, Option[SysmlAst.FeatureValue]] = transformOption(r2.ctx, o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: TPostResult[Context, Option[Type]] = transformOption(r4.ctx, o2.tipeOpt, transformType _)
          val r6: TPostResult[Context, ResolvedAttr] = transformResolvedAttr(r5.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty || r6.resultOpt.nonEmpty)
            TPostResult(r6.ctx, Some(o2(occurrenceUsagePrefix = r0.resultOpt.getOrElse(o2.occurrenceUsagePrefix), identification = r1.resultOpt.getOrElse(o2.identification), specializations = r2.resultOpt.getOrElse(o2.specializations), featureValue = r3.resultOpt.getOrElse(o2.featureValue), definitionBodyItems = r4.resultOpt.getOrElse(o2.definitionBodyItems), tipeOpt = r5.resultOpt.getOrElse(o2.tipeOpt), attr = r6.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r6.ctx, None())
        case o2: SysmlAst.PortUsage =>
          val r0: TPostResult[Context, SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(preR.ctx, o2.occurrenceUsagePrefix)
          val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
          val r2: TPostResult[Context, IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(r1.ctx, o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: TPostResult[Context, Option[SysmlAst.FeatureValue]] = transformOption(r2.ctx, o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: TPostResult[Context, Option[Type]] = transformOption(r4.ctx, o2.tipeOpt, transformType _)
          val r6: TPostResult[Context, ResolvedAttr] = transformResolvedAttr(r5.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty || r6.resultOpt.nonEmpty)
            TPostResult(r6.ctx, Some(o2(occurrenceUsagePrefix = r0.resultOpt.getOrElse(o2.occurrenceUsagePrefix), identification = r1.resultOpt.getOrElse(o2.identification), specializations = r2.resultOpt.getOrElse(o2.specializations), featureValue = r3.resultOpt.getOrElse(o2.featureValue), definitionBodyItems = r4.resultOpt.getOrElse(o2.definitionBodyItems), tipeOpt = r5.resultOpt.getOrElse(o2.tipeOpt), attr = r6.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r6.ctx, None())
      }
      rOpt
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.StructureUsageElement = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.StructureUsageElement] = pp.postSysmlAstStructureUsageElement(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformSysmlAstConnectionUsage(ctx: Context, o: SysmlAst.ConnectionUsage): TPostResult[Context, SysmlAst.ConnectionUsage] = {
    val preR: PreResult[Context, SysmlAst.ConnectionUsage] = pp.preSysmlAstConnectionUsage(ctx, o)
    val r: TPostResult[Context, SysmlAst.ConnectionUsage] = if (preR.continu) {
      val o2: SysmlAst.ConnectionUsage = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: TPostResult[Context, SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(preR.ctx, o2.occurrenceUsagePrefix)
      val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
      val r2: TPostResult[Context, IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(r1.ctx, o2.specializations, transformSysmlAstFeatureSpecialization _)
      val r3: TPostResult[Context, Option[SysmlAst.FeatureValue]] = transformOption(r2.ctx, o2.featureValue, transformSysmlAstFeatureValue _)
      val r4: TPostResult[Context, Option[SysmlAst.ConnectorPart]] = transformOption(r3.ctx, o2.connectorPart, transformSysmlAstConnectorPart _)
      val r5: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r4.ctx, o2.definitionBodyItems, transformSysmlAstBodyElement _)
      val r6: TPostResult[Context, Option[Type]] = transformOption(r5.ctx, o2.tipeOpt, transformType _)
      val r7: TPostResult[Context, ResolvedAttr] = transformResolvedAttr(r6.ctx, o2.attr)
      if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty || r6.resultOpt.nonEmpty || r7.resultOpt.nonEmpty)
        TPostResult(r7.ctx, Some(o2(occurrenceUsagePrefix = r0.resultOpt.getOrElse(o2.occurrenceUsagePrefix), identification = r1.resultOpt.getOrElse(o2.identification), specializations = r2.resultOpt.getOrElse(o2.specializations), featureValue = r3.resultOpt.getOrElse(o2.featureValue), connectorPart = r4.resultOpt.getOrElse(o2.connectorPart), definitionBodyItems = r5.resultOpt.getOrElse(o2.definitionBodyItems), tipeOpt = r6.resultOpt.getOrElse(o2.tipeOpt), attr = r7.resultOpt.getOrElse(o2.attr))))
      else
        TPostResult(r7.ctx, None())
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.ConnectionUsage = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.ConnectionUsage] = pp.postSysmlAstConnectionUsage(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformSysmlAstItemUsage(ctx: Context, o: SysmlAst.ItemUsage): TPostResult[Context, SysmlAst.ItemUsage] = {
    val preR: PreResult[Context, SysmlAst.ItemUsage] = pp.preSysmlAstItemUsage(ctx, o)
    val r: TPostResult[Context, SysmlAst.ItemUsage] = if (preR.continu) {
      val o2: SysmlAst.ItemUsage = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: TPostResult[Context, SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(preR.ctx, o2.occurrenceUsagePrefix)
      val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
      val r2: TPostResult[Context, IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(r1.ctx, o2.specializations, transformSysmlAstFeatureSpecialization _)
      val r3: TPostResult[Context, Option[SysmlAst.FeatureValue]] = transformOption(r2.ctx, o2.featureValue, transformSysmlAstFeatureValue _)
      val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.definitionBodyItems, transformSysmlAstBodyElement _)
      val r5: TPostResult[Context, Option[Type]] = transformOption(r4.ctx, o2.tipeOpt, transformType _)
      val r6: TPostResult[Context, ResolvedAttr] = transformResolvedAttr(r5.ctx, o2.attr)
      if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty || r6.resultOpt.nonEmpty)
        TPostResult(r6.ctx, Some(o2(occurrenceUsagePrefix = r0.resultOpt.getOrElse(o2.occurrenceUsagePrefix), identification = r1.resultOpt.getOrElse(o2.identification), specializations = r2.resultOpt.getOrElse(o2.specializations), featureValue = r3.resultOpt.getOrElse(o2.featureValue), definitionBodyItems = r4.resultOpt.getOrElse(o2.definitionBodyItems), tipeOpt = r5.resultOpt.getOrElse(o2.tipeOpt), attr = r6.resultOpt.getOrElse(o2.attr))))
      else
        TPostResult(r6.ctx, None())
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.ItemUsage = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.ItemUsage] = pp.postSysmlAstItemUsage(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformSysmlAstPartUsage(ctx: Context, o: SysmlAst.PartUsage): TPostResult[Context, SysmlAst.PartUsage] = {
    val preR: PreResult[Context, SysmlAst.PartUsage] = pp.preSysmlAstPartUsage(ctx, o)
    val r: TPostResult[Context, SysmlAst.PartUsage] = if (preR.continu) {
      val o2: SysmlAst.PartUsage = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: TPostResult[Context, SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(preR.ctx, o2.occurrenceUsagePrefix)
      val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
      val r2: TPostResult[Context, IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(r1.ctx, o2.specializations, transformSysmlAstFeatureSpecialization _)
      val r3: TPostResult[Context, Option[SysmlAst.FeatureValue]] = transformOption(r2.ctx, o2.featureValue, transformSysmlAstFeatureValue _)
      val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.definitionBodyItems, transformSysmlAstBodyElement _)
      val r5: TPostResult[Context, Option[Type]] = transformOption(r4.ctx, o2.tipeOpt, transformType _)
      val r6: TPostResult[Context, ResolvedAttr] = transformResolvedAttr(r5.ctx, o2.attr)
      if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty || r6.resultOpt.nonEmpty)
        TPostResult(r6.ctx, Some(o2(occurrenceUsagePrefix = r0.resultOpt.getOrElse(o2.occurrenceUsagePrefix), identification = r1.resultOpt.getOrElse(o2.identification), specializations = r2.resultOpt.getOrElse(o2.specializations), featureValue = r3.resultOpt.getOrElse(o2.featureValue), definitionBodyItems = r4.resultOpt.getOrElse(o2.definitionBodyItems), tipeOpt = r5.resultOpt.getOrElse(o2.tipeOpt), attr = r6.resultOpt.getOrElse(o2.attr))))
      else
        TPostResult(r6.ctx, None())
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.PartUsage = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.PartUsage] = pp.postSysmlAstPartUsage(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformSysmlAstPortUsage(ctx: Context, o: SysmlAst.PortUsage): TPostResult[Context, SysmlAst.PortUsage] = {
    val preR: PreResult[Context, SysmlAst.PortUsage] = pp.preSysmlAstPortUsage(ctx, o)
    val r: TPostResult[Context, SysmlAst.PortUsage] = if (preR.continu) {
      val o2: SysmlAst.PortUsage = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: TPostResult[Context, SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(preR.ctx, o2.occurrenceUsagePrefix)
      val r1: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(r0.ctx, o2.identification, transformSysmlAstIdentification _)
      val r2: TPostResult[Context, IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(r1.ctx, o2.specializations, transformSysmlAstFeatureSpecialization _)
      val r3: TPostResult[Context, Option[SysmlAst.FeatureValue]] = transformOption(r2.ctx, o2.featureValue, transformSysmlAstFeatureValue _)
      val r4: TPostResult[Context, IS[Z, SysmlAst.BodyElement]] = transformISZ(r3.ctx, o2.definitionBodyItems, transformSysmlAstBodyElement _)
      val r5: TPostResult[Context, Option[Type]] = transformOption(r4.ctx, o2.tipeOpt, transformType _)
      val r6: TPostResult[Context, ResolvedAttr] = transformResolvedAttr(r5.ctx, o2.attr)
      if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty || r3.resultOpt.nonEmpty || r4.resultOpt.nonEmpty || r5.resultOpt.nonEmpty || r6.resultOpt.nonEmpty)
        TPostResult(r6.ctx, Some(o2(occurrenceUsagePrefix = r0.resultOpt.getOrElse(o2.occurrenceUsagePrefix), identification = r1.resultOpt.getOrElse(o2.identification), specializations = r2.resultOpt.getOrElse(o2.specializations), featureValue = r3.resultOpt.getOrElse(o2.featureValue), definitionBodyItems = r4.resultOpt.getOrElse(o2.definitionBodyItems), tipeOpt = r5.resultOpt.getOrElse(o2.tipeOpt), attr = r6.resultOpt.getOrElse(o2.attr))))
      else
        TPostResult(r6.ctx, None())
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.PortUsage = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.PortUsage] = pp.postSysmlAstPortUsage(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformSysmlAstAnnotatingElement(ctx: Context, o: SysmlAst.AnnotatingElement): TPostResult[Context, SysmlAst.AnnotatingElement] = {
    val preR: PreResult[Context, SysmlAst.AnnotatingElement] = pp.preSysmlAstAnnotatingElement(ctx, o)
    val r: TPostResult[Context, SysmlAst.AnnotatingElement] = if (preR.continu) {
      val o2: SysmlAst.AnnotatingElement = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val rOpt: TPostResult[Context, SysmlAst.AnnotatingElement] = o2 match {
        case o2: SysmlAst.Comment =>
          val r0: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(preR.ctx, o2.id, transformSysmlAstIdentification _)
          val r1: TPostResult[Context, IS[Z, SysmlAst.Name]] = transformISZ(r0.ctx, o2.abouts, transformSysmlAstName _)
          val r2: TPostResult[Context, Attr] = transformAttr(r1.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty)
            TPostResult(r2.ctx, Some(o2(id = r0.resultOpt.getOrElse(o2.id), abouts = r1.resultOpt.getOrElse(o2.abouts), attr = r2.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r2.ctx, None())
        case o2: SysmlAst.Documentation =>
          val r0: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(preR.ctx, o2.id, transformSysmlAstIdentification _)
          val r1: TPostResult[Context, Attr] = transformAttr(r0.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty)
            TPostResult(r1.ctx, Some(o2(id = r0.resultOpt.getOrElse(o2.id), attr = r1.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r1.ctx, None())
        case o2: SysmlAst.TextualRepresentation =>
          val r0: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(preR.ctx, o2.id, transformSysmlAstIdentification _)
          val r1: TPostResult[Context, Attr] = transformAttr(r0.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty)
            TPostResult(r1.ctx, Some(o2(id = r0.resultOpt.getOrElse(o2.id), attr = r1.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r1.ctx, None())
      }
      rOpt
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.AnnotatingElement = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.AnnotatingElement] = pp.postSysmlAstAnnotatingElement(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformSysmlAstComment(ctx: Context, o: SysmlAst.Comment): TPostResult[Context, SysmlAst.Comment] = {
    val preR: PreResult[Context, SysmlAst.Comment] = pp.preSysmlAstComment(ctx, o)
    val r: TPostResult[Context, SysmlAst.Comment] = if (preR.continu) {
      val o2: SysmlAst.Comment = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(preR.ctx, o2.id, transformSysmlAstIdentification _)
      val r1: TPostResult[Context, IS[Z, SysmlAst.Name]] = transformISZ(r0.ctx, o2.abouts, transformSysmlAstName _)
      val r2: TPostResult[Context, Attr] = transformAttr(r1.ctx, o2.attr)
      if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty || r2.resultOpt.nonEmpty)
        TPostResult(r2.ctx, Some(o2(id = r0.resultOpt.getOrElse(o2.id), abouts = r1.resultOpt.getOrElse(o2.abouts), attr = r2.resultOpt.getOrElse(o2.attr))))
      else
        TPostResult(r2.ctx, None())
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.Comment = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.Comment] = pp.postSysmlAstComment(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformSysmlAstDocumentation(ctx: Context, o: SysmlAst.Documentation): TPostResult[Context, SysmlAst.Documentation] = {
    val preR: PreResult[Context, SysmlAst.Documentation] = pp.preSysmlAstDocumentation(ctx, o)
    val r: TPostResult[Context, SysmlAst.Documentation] = if (preR.continu) {
      val o2: SysmlAst.Documentation = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(preR.ctx, o2.id, transformSysmlAstIdentification _)
      val r1: TPostResult[Context, Attr] = transformAttr(r0.ctx, o2.attr)
      if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty)
        TPostResult(r1.ctx, Some(o2(id = r0.resultOpt.getOrElse(o2.id), attr = r1.resultOpt.getOrElse(o2.attr))))
      else
        TPostResult(r1.ctx, None())
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.Documentation = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.Documentation] = pp.postSysmlAstDocumentation(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformSysmlAstTextualRepresentation(ctx: Context, o: SysmlAst.TextualRepresentation): TPostResult[Context, SysmlAst.TextualRepresentation] = {
    val preR: PreResult[Context, SysmlAst.TextualRepresentation] = pp.preSysmlAstTextualRepresentation(ctx, o)
    val r: TPostResult[Context, SysmlAst.TextualRepresentation] = if (preR.continu) {
      val o2: SysmlAst.TextualRepresentation = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: TPostResult[Context, Option[SysmlAst.Identification]] = transformOption(preR.ctx, o2.id, transformSysmlAstIdentification _)
      val r1: TPostResult[Context, Attr] = transformAttr(r0.ctx, o2.attr)
      if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty)
        TPostResult(r1.ctx, Some(o2(id = r0.resultOpt.getOrElse(o2.id), attr = r1.resultOpt.getOrElse(o2.attr))))
      else
        TPostResult(r1.ctx, None())
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: SysmlAst.TextualRepresentation = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, SysmlAst.TextualRepresentation] = pp.postSysmlAstTextualRepresentation(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformAttr(ctx: Context, o: Attr): TPostResult[Context, Attr] = {
    val preR: PreResult[Context, Attr] = pp.preAttr(ctx, o)
    val r: TPostResult[Context, Attr] = if (preR.continu) {
      val o2: Attr = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      if (hasChanged)
        TPostResult(preR.ctx, Some(o2))
      else
        TPostResult(preR.ctx, None())
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: Attr = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, Attr] = pp.postAttr(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformResolvedAttr(ctx: Context, o: ResolvedAttr): TPostResult[Context, ResolvedAttr] = {
    val preR: PreResult[Context, ResolvedAttr] = pp.preResolvedAttr(ctx, o)
    val r: TPostResult[Context, ResolvedAttr] = if (preR.continu) {
      val o2: ResolvedAttr = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: TPostResult[Context, Option[ResolvedInfo]] = transformOption(preR.ctx, o2.resOpt, transformResolvedInfo _)
      if (hasChanged || r0.resultOpt.nonEmpty)
        TPostResult(r0.ctx, Some(o2(resOpt = r0.resultOpt.getOrElse(o2.resOpt))))
      else
        TPostResult(r0.ctx, None())
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: ResolvedAttr = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, ResolvedAttr] = pp.postResolvedAttr(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformResolvedInfo(ctx: Context, o: ResolvedInfo): TPostResult[Context, ResolvedInfo] = {
    val preR: PreResult[Context, ResolvedInfo] = pp.preResolvedInfo(ctx, o)
    val r: TPostResult[Context, ResolvedInfo] = if (preR.continu) {
      val o2: ResolvedInfo = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val rOpt: TPostResult[Context, ResolvedInfo] = o2 match {
        case o2: ResolvedInfo.Package =>
          if (hasChanged)
            TPostResult(preR.ctx, Some(o2))
          else
            TPostResult(preR.ctx, None())
        case o2: ResolvedInfo.Enum =>
          if (hasChanged)
            TPostResult(preR.ctx, Some(o2))
          else
            TPostResult(preR.ctx, None())
        case o2: ResolvedInfo.EnumElement =>
          if (hasChanged)
            TPostResult(preR.ctx, Some(o2))
          else
            TPostResult(preR.ctx, None())
        case o2: ResolvedInfo.AttributeUsage =>
          if (hasChanged)
            TPostResult(preR.ctx, Some(o2))
          else
            TPostResult(preR.ctx, None())
        case o2: ResolvedInfo.ConnectionUsage =>
          if (hasChanged)
            TPostResult(preR.ctx, Some(o2))
          else
            TPostResult(preR.ctx, None())
        case o2: ResolvedInfo.ItemUsage =>
          if (hasChanged)
            TPostResult(preR.ctx, Some(o2))
          else
            TPostResult(preR.ctx, None())
        case o2: ResolvedInfo.PartUsage =>
          if (hasChanged)
            TPostResult(preR.ctx, Some(o2))
          else
            TPostResult(preR.ctx, None())
        case o2: ResolvedInfo.PortUsage =>
          if (hasChanged)
            TPostResult(preR.ctx, Some(o2))
          else
            TPostResult(preR.ctx, None())
        case o2: ResolvedInfo.ReferenceUsage =>
          if (hasChanged)
            TPostResult(preR.ctx, Some(o2))
          else
            TPostResult(preR.ctx, None())
      }
      rOpt
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: ResolvedInfo = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, ResolvedInfo] = pp.postResolvedInfo(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformType(ctx: Context, o: Type): TPostResult[Context, Type] = {
    val preR: PreResult[Context, Type] = pp.preType(ctx, o)
    val r: TPostResult[Context, Type] = if (preR.continu) {
      val o2: Type = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val rOpt: TPostResult[Context, Type] = o2 match {
        case o2: Type.Named =>
          val r0: TPostResult[Context, SysmlAst.Name] = transformSysmlAstName(preR.ctx, o2.name)
          val r1: TPostResult[Context, TypedAttr] = transformTypedAttr(r0.ctx, o2.attr)
          if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty)
            TPostResult(r1.ctx, Some(o2(name = r0.resultOpt.getOrElse(o2.name), attr = r1.resultOpt.getOrElse(o2.attr))))
          else
            TPostResult(r1.ctx, None())
      }
      rOpt
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: Type = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, Type] = pp.postType(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformTypedAttr(ctx: Context, o: TypedAttr): TPostResult[Context, TypedAttr] = {
    val preR: PreResult[Context, TypedAttr] = pp.preTypedAttr(ctx, o)
    val r: TPostResult[Context, TypedAttr] = if (preR.continu) {
      val o2: TypedAttr = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      if (hasChanged)
        TPostResult(preR.ctx, Some(o2))
      else
        TPostResult(preR.ctx, None())
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: TypedAttr = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, TypedAttr] = pp.postTypedAttr(r.ctx, o2)
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

  @pure def transformTypeNamed(ctx: Context, o: Type.Named): TPostResult[Context, Type.Named] = {
    val preR: PreResult[Context, Type.Named] = pp.preTypeNamed(ctx, o) match {
     case PreResult(preCtx, continu, Some(r: Type.Named)) => PreResult(preCtx, continu, Some[Type.Named](r))
     case PreResult(_, _, Some(_)) => halt("Can only produce object of type Type.Named")
     case PreResult(preCtx, continu, _) => PreResult(preCtx, continu, None[Type.Named]())
    }
    val r: TPostResult[Context, Type.Named] = if (preR.continu) {
      val o2: Type.Named = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: TPostResult[Context, SysmlAst.Name] = transformSysmlAstName(preR.ctx, o2.name)
      val r1: TPostResult[Context, TypedAttr] = transformTypedAttr(r0.ctx, o2.attr)
      if (hasChanged || r0.resultOpt.nonEmpty || r1.resultOpt.nonEmpty)
        TPostResult(r1.ctx, Some(o2(name = r0.resultOpt.getOrElse(o2.name), attr = r1.resultOpt.getOrElse(o2.attr))))
      else
        TPostResult(r1.ctx, None())
    } else if (preR.resultOpt.nonEmpty) {
      TPostResult(preR.ctx, Some(preR.resultOpt.getOrElse(o)))
    } else {
      TPostResult(preR.ctx, None())
    }
    val hasChanged: B = r.resultOpt.nonEmpty
    val o2: Type.Named = r.resultOpt.getOrElse(o)
    val postR: TPostResult[Context, Type.Named] = pp.postTypeNamed(r.ctx, o2) match {
     case TPostResult(postCtx, Some(result: Type.Named)) => TPostResult(postCtx, Some[Type.Named](result))
     case TPostResult(_, Some(_)) => halt("Can only produce object of type Type.Named")
     case TPostResult(postCtx, _) => TPostResult(postCtx, None[Type.Named]())
    }
    if (postR.resultOpt.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return TPostResult(postR.ctx, Some(o2))
    } else {
      return TPostResult(postR.ctx, None())
    }
  }

}