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

object MTransformer {

  @record class PreResult[T](val continu: B,
                             val resultOpt: MOption[T])

  val PreResultSysmlAstId: PreResult[SysmlAst.Id] = PreResult(T, MNone())

  val PostResultSysmlAstId: MOption[SysmlAst.Id] = MNone()

  val PreResultSysmlAstName: PreResult[SysmlAst.Name] = PreResult(T, MNone())

  val PostResultSysmlAstName: MOption[SysmlAst.Name] = MNone()

  def transformISZ[T](s: IS[Z, T], f: T => MOption[T]): MOption[IS[Z, T]] = {
    val s2: MS[Z, T] = s.toMS
    var changed: B = F
    for (i <- s2.indices) {
      val e: T = s(i)
      val r: MOption[T] = f(e)
      changed = changed || r.nonEmpty
      s2(i) = r.getOrElse(e)
    }
    if (changed) {
      return MSome(s2.toIS)
    } else {
      return MNone()
    }
  }

  val PreResultSysmlAstTopUnit: PreResult[SysmlAst.TopUnit] = PreResult(T, MNone())

  val PostResultSysmlAstTopUnit: MOption[SysmlAst.TopUnit] = MNone()

  def transformOption[T](option: Option[T], f: T => MOption[T]): MOption[Option[T]] = {
    option match {
      case Some(v) =>
        val r = f(v)
        r match {
          case MSome(v2) => return MSome(Some(v2))
          case _ => return MNone()
        }
      case _ => return MNone()
    }
  }

  val PreResultSysmlAstFeatureValue: PreResult[SysmlAst.FeatureValue] = PreResult(T, MNone())

  val PostResultSysmlAstFeatureValue: MOption[SysmlAst.FeatureValue] = MNone()

  val PreResultSysmlAstEnumeratedValue: PreResult[SysmlAst.EnumeratedValue] = PreResult(T, MNone())

  val PostResultSysmlAstEnumeratedValue: MOption[SysmlAst.EnumeratedValue] = MNone()

  val PreResultSysmlAstImport: PreResult[SysmlAst.Import] = PreResult(T, MNone())

  val PostResultSysmlAstImport: MOption[SysmlAst.Import] = MNone()

  val PreResultSysmlAstAliasMember: PreResult[SysmlAst.AliasMember] = PreResult(T, MNone())

  val PostResultSysmlAstAliasMember: MOption[SysmlAst.AliasMember] = MNone()

  val PreResultSysmlAstIdentification: PreResult[SysmlAst.Identification] = PreResult(T, MNone())

  val PostResultSysmlAstIdentification: MOption[SysmlAst.Identification] = MNone()

  val PreResultSysmlAstConnectorEnd: PreResult[SysmlAst.ConnectorEnd] = PreResult(T, MNone())

  val PostResultSysmlAstConnectorEnd: MOption[SysmlAst.ConnectorEnd] = MNone()

  val PreResultSysmlAstBinaryConnectorPart: PreResult[SysmlAst.BinaryConnectorPart] = PreResult(T, MNone())

  val PostResultSysmlAstBinaryConnectorPart: MOption[SysmlAst.BinaryConnectorPart] = MNone()

  val PreResultSysmlAstNaryConnectorPart: PreResult[SysmlAst.NaryConnectorPart] = PreResult(T, MNone())

  val PostResultSysmlAstNaryConnectorPart: MOption[SysmlAst.NaryConnectorPart] = MNone()

  val PreResultSysmlAstTypingsSpecialization: PreResult[SysmlAst.TypingsSpecialization] = PreResult(T, MNone())

  val PostResultSysmlAstTypingsSpecialization: MOption[SysmlAst.TypingsSpecialization] = MNone()

  val PreResultSysmlAstSubsettingsSpecialization: PreResult[SysmlAst.SubsettingsSpecialization] = PreResult(T, MNone())

  val PostResultSysmlAstSubsettingsSpecialization: MOption[SysmlAst.SubsettingsSpecialization] = MNone()

  val PreResultSysmlAstReferencesSpecialization: PreResult[SysmlAst.ReferencesSpecialization] = PreResult(T, MNone())

  val PostResultSysmlAstReferencesSpecialization: MOption[SysmlAst.ReferencesSpecialization] = MNone()

  val PreResultSysmlAstRedefinitionsSpecialization: PreResult[SysmlAst.RedefinitionsSpecialization] = PreResult(T, MNone())

  val PostResultSysmlAstRedefinitionsSpecialization: MOption[SysmlAst.RedefinitionsSpecialization] = MNone()

  val PreResultSysmlAstDefinitionPrefix: PreResult[SysmlAst.DefinitionPrefix] = PreResult(T, MNone())

  val PostResultSysmlAstDefinitionPrefix: MOption[SysmlAst.DefinitionPrefix] = MNone()

  val PreResultSysmlAstPackage: PreResult[SysmlAst.Package] = PreResult(T, MNone())

  val PostResultSysmlAstPackage: MOption[SysmlAst.Package] = MNone()

  val PreResultSysmlAstAttributeDefinition: PreResult[SysmlAst.AttributeDefinition] = PreResult(T, MNone())

  val PostResultSysmlAstAttributeDefinition: MOption[SysmlAst.AttributeDefinition] = MNone()

  val PreResultSysmlAstOccurrenceDefinitionPrefix: PreResult[SysmlAst.OccurrenceDefinitionPrefix] = PreResult(T, MNone())

  val PostResultSysmlAstOccurrenceDefinitionPrefix: MOption[SysmlAst.OccurrenceDefinitionPrefix] = MNone()

  val PreResultSysmlAstAllocationDefinition: PreResult[SysmlAst.AllocationDefinition] = PreResult(T, MNone())

  val PostResultSysmlAstAllocationDefinition: MOption[SysmlAst.AllocationDefinition] = MNone()

  val PreResultSysmlAstConnectionDefinition: PreResult[SysmlAst.ConnectionDefinition] = PreResult(T, MNone())

  val PostResultSysmlAstConnectionDefinition: MOption[SysmlAst.ConnectionDefinition] = MNone()

  val PreResultSysmlAstEnumerationDefinition: PreResult[SysmlAst.EnumerationDefinition] = PreResult(T, MNone())

  val PostResultSysmlAstEnumerationDefinition: MOption[SysmlAst.EnumerationDefinition] = MNone()

  val PreResultSysmlAstPartDefinition: PreResult[SysmlAst.PartDefinition] = PreResult(T, MNone())

  val PostResultSysmlAstPartDefinition: MOption[SysmlAst.PartDefinition] = MNone()

  val PreResultSysmlAstPortDefinition: PreResult[SysmlAst.PortDefinition] = PreResult(T, MNone())

  val PostResultSysmlAstPortDefinition: MOption[SysmlAst.PortDefinition] = MNone()

  val PreResultSysmlAstMetadataDefinition: PreResult[SysmlAst.MetadataDefinition] = PreResult(T, MNone())

  val PostResultSysmlAstMetadataDefinition: MOption[SysmlAst.MetadataDefinition] = MNone()

  val PreResultSysmlAstRefPrefix: PreResult[SysmlAst.RefPrefix] = PreResult(T, MNone())

  val PostResultSysmlAstRefPrefix: MOption[SysmlAst.RefPrefix] = MNone()

  val PreResultSysmlAstUsagePrefix: PreResult[SysmlAst.UsagePrefix] = PreResult(T, MNone())

  val PostResultSysmlAstUsagePrefix: MOption[SysmlAst.UsagePrefix] = MNone()

  val PreResultSysmlAstAttributeUsage: PreResult[SysmlAst.AttributeUsage] = PreResult(T, MNone())

  val PostResultSysmlAstAttributeUsage: MOption[SysmlAst.AttributeUsage] = MNone()

  val PreResultSysmlAstReferenceUsage: PreResult[SysmlAst.ReferenceUsage] = PreResult(T, MNone())

  val PostResultSysmlAstReferenceUsage: MOption[SysmlAst.ReferenceUsage] = MNone()

  val PreResultSysmlAstOccurrenceUsagePrefix: PreResult[SysmlAst.OccurrenceUsagePrefix] = PreResult(T, MNone())

  val PostResultSysmlAstOccurrenceUsagePrefix: MOption[SysmlAst.OccurrenceUsagePrefix] = MNone()

  val PreResultSysmlAstConnectionUsage: PreResult[SysmlAst.ConnectionUsage] = PreResult(T, MNone())

  val PostResultSysmlAstConnectionUsage: MOption[SysmlAst.ConnectionUsage] = MNone()

  val PreResultSysmlAstItemUsage: PreResult[SysmlAst.ItemUsage] = PreResult(T, MNone())

  val PostResultSysmlAstItemUsage: MOption[SysmlAst.ItemUsage] = MNone()

  val PreResultSysmlAstPartUsage: PreResult[SysmlAst.PartUsage] = PreResult(T, MNone())

  val PostResultSysmlAstPartUsage: MOption[SysmlAst.PartUsage] = MNone()

  val PreResultSysmlAstPortUsage: PreResult[SysmlAst.PortUsage] = PreResult(T, MNone())

  val PostResultSysmlAstPortUsage: MOption[SysmlAst.PortUsage] = MNone()

  val PreResultSysmlAstComment: PreResult[SysmlAst.Comment] = PreResult(T, MNone())

  val PostResultSysmlAstComment: MOption[SysmlAst.Comment] = MNone()

  val PreResultSysmlAstDocumentation: PreResult[SysmlAst.Documentation] = PreResult(T, MNone())

  val PostResultSysmlAstDocumentation: MOption[SysmlAst.Documentation] = MNone()

  val PreResultSysmlAstTextualRepresentation: PreResult[SysmlAst.TextualRepresentation] = PreResult(T, MNone())

  val PostResultSysmlAstTextualRepresentation: MOption[SysmlAst.TextualRepresentation] = MNone()

  val PreResultSysmlAstGumboAnnotation: PreResult[SysmlAst.GumboAnnotation] = PreResult(T, MNone())

  val PostResultSysmlAstGumboAnnotation: MOption[SysmlAst.GumboAnnotation] = MNone()

  val PreResultAttr: PreResult[Attr] = PreResult(T, MNone())

  val PostResultAttr: MOption[Attr] = MNone()

  val PreResultResolvedAttr: PreResult[ResolvedAttr] = PreResult(T, MNone())

  val PostResultResolvedAttr: MOption[ResolvedAttr] = MNone()

  val PreResultResolvedInfoPackage: PreResult[ResolvedInfo] = PreResult(T, MNone())

  val PostResultResolvedInfoPackage: MOption[ResolvedInfo] = MNone()

  val PreResultResolvedInfoEnum: PreResult[ResolvedInfo] = PreResult(T, MNone())

  val PostResultResolvedInfoEnum: MOption[ResolvedInfo] = MNone()

  val PreResultResolvedInfoEnumElement: PreResult[ResolvedInfo] = PreResult(T, MNone())

  val PostResultResolvedInfoEnumElement: MOption[ResolvedInfo] = MNone()

  val PreResultResolvedInfoAttributeUsage: PreResult[ResolvedInfo] = PreResult(T, MNone())

  val PostResultResolvedInfoAttributeUsage: MOption[ResolvedInfo] = MNone()

  val PreResultResolvedInfoConnectionUsage: PreResult[ResolvedInfo] = PreResult(T, MNone())

  val PostResultResolvedInfoConnectionUsage: MOption[ResolvedInfo] = MNone()

  val PreResultResolvedInfoItemUsage: PreResult[ResolvedInfo] = PreResult(T, MNone())

  val PostResultResolvedInfoItemUsage: MOption[ResolvedInfo] = MNone()

  val PreResultResolvedInfoPartUsage: PreResult[ResolvedInfo] = PreResult(T, MNone())

  val PostResultResolvedInfoPartUsage: MOption[ResolvedInfo] = MNone()

  val PreResultResolvedInfoPortUsage: PreResult[ResolvedInfo] = PreResult(T, MNone())

  val PostResultResolvedInfoPortUsage: MOption[ResolvedInfo] = MNone()

  val PreResultResolvedInfoReferenceUsage: PreResult[ResolvedInfo] = PreResult(T, MNone())

  val PostResultResolvedInfoReferenceUsage: MOption[ResolvedInfo] = MNone()

  val PreResultTypeNamed: PreResult[Type] = PreResult(T, MNone())

  val PostResultTypeNamed: MOption[Type] = MNone()

  val PreResultTypedAttr: PreResult[TypedAttr] = PreResult(T, MNone())

  val PostResultTypedAttr: MOption[TypedAttr] = MNone()

  val PreResultGumboASTGclSubclause: PreResult[GumboAST.GclSubclause] = PreResult(T, MNone())

  val PostResultGumboASTGclSubclause: MOption[GumboAST.GclSubclause] = MNone()

  val PreResultGumboASTGclMethod: PreResult[GumboAST.GclMethod] = PreResult(T, MNone())

  val PostResultGumboASTGclMethod: MOption[GumboAST.GclMethod] = MNone()

  val PreResultGumboASTGclStateVar: PreResult[GumboAST.GclStateVar] = PreResult(T, MNone())

  val PostResultGumboASTGclStateVar: MOption[GumboAST.GclStateVar] = MNone()

  val PreResultGumboASTGclInvariant: PreResult[GumboAST.GclInvariant] = PreResult(T, MNone())

  val PostResultGumboASTGclInvariant: MOption[GumboAST.GclInvariant] = MNone()

  val PreResultGumboASTGclAssume: PreResult[GumboAST.GclAssume] = PreResult(T, MNone())

  val PostResultGumboASTGclAssume: MOption[GumboAST.GclAssume] = MNone()

  val PreResultGumboASTGclGuarantee: PreResult[GumboAST.GclGuarantee] = PreResult(T, MNone())

  val PostResultGumboASTGclGuarantee: MOption[GumboAST.GclGuarantee] = MNone()

  val PreResultGumboASTGclIntegration: PreResult[GumboAST.GclIntegration] = PreResult(T, MNone())

  val PostResultGumboASTGclIntegration: MOption[GumboAST.GclIntegration] = MNone()

  val PreResultGumboASTGclCaseStatement: PreResult[GumboAST.GclCaseStatement] = PreResult(T, MNone())

  val PostResultGumboASTGclCaseStatement: MOption[GumboAST.GclCaseStatement] = MNone()

  val PreResultGumboASTGclInitialize: PreResult[GumboAST.GclInitialize] = PreResult(T, MNone())

  val PostResultGumboASTGclInitialize: MOption[GumboAST.GclInitialize] = MNone()

  val PreResultGumboASTGclCompute: PreResult[GumboAST.GclCompute] = PreResult(T, MNone())

  val PostResultGumboASTGclCompute: MOption[GumboAST.GclCompute] = MNone()

  val PreResultGumboASTGclHandle: PreResult[GumboAST.GclHandle] = PreResult(T, MNone())

  val PostResultGumboASTGclHandle: MOption[GumboAST.GclHandle] = MNone()

  val PreResultGumboASTGclTODO: PreResult[GumboAST.GclTODO] = PreResult(T, MNone())

  val PostResultGumboASTGclTODO: MOption[GumboAST.GclTODO] = MNone()

  val PreResultGumboASTGclLib: PreResult[GumboAST.GclLib] = PreResult(T, MNone())

  val PostResultGumboASTGclLib: MOption[GumboAST.GclLib] = MNone()

  val PreResultGumboASTInfoFlowClause: PreResult[GumboAST.InfoFlowClause] = PreResult(T, MNone())

  val PostResultGumboASTInfoFlowClause: MOption[GumboAST.InfoFlowClause] = MNone()

}

import MTransformer._

@msig trait MTransformer {

  def preSysmlAstId(o: SysmlAst.Id): PreResult[SysmlAst.Id] = {
    return PreResultSysmlAstId
  }

  def preSysmlAstName(o: SysmlAst.Name): PreResult[SysmlAst.Name] = {
    return PreResultSysmlAstName
  }

  def preSysmlAstTopUnit(o: SysmlAst.TopUnit): PreResult[SysmlAst.TopUnit] = {
    return PreResultSysmlAstTopUnit
  }

  def preSysmlAstAttrNode(o: SysmlAst.AttrNode): PreResult[SysmlAst.AttrNode] = {
    o match {
      case o: SysmlAst.Import =>
        val r: PreResult[SysmlAst.AttrNode] = preSysmlAstImport(o) match {
         case PreResult(continu, MSome(r: SysmlAst.AttrNode)) => PreResult(continu, MSome[SysmlAst.AttrNode](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.AttrNode]())
        }
        return r
      case o: SysmlAst.AliasMember =>
        val r: PreResult[SysmlAst.AttrNode] = preSysmlAstAliasMember(o) match {
         case PreResult(continu, MSome(r: SysmlAst.AttrNode)) => PreResult(continu, MSome[SysmlAst.AttrNode](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.AttrNode]())
        }
        return r
      case o: SysmlAst.Identification =>
        val r: PreResult[SysmlAst.AttrNode] = preSysmlAstIdentification(o) match {
         case PreResult(continu, MSome(r: SysmlAst.AttrNode)) => PreResult(continu, MSome[SysmlAst.AttrNode](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.AttrNode]())
        }
        return r
      case o: SysmlAst.Package =>
        val r: PreResult[SysmlAst.AttrNode] = preSysmlAstPackage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.AttrNode)) => PreResult(continu, MSome[SysmlAst.AttrNode](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.AttrNode]())
        }
        return r
      case o: SysmlAst.AttributeDefinition =>
        val r: PreResult[SysmlAst.AttrNode] = preSysmlAstAttributeDefinition(o) match {
         case PreResult(continu, MSome(r: SysmlAst.AttrNode)) => PreResult(continu, MSome[SysmlAst.AttrNode](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.AttrNode]())
        }
        return r
      case o: SysmlAst.AllocationDefinition =>
        val r: PreResult[SysmlAst.AttrNode] = preSysmlAstAllocationDefinition(o) match {
         case PreResult(continu, MSome(r: SysmlAst.AttrNode)) => PreResult(continu, MSome[SysmlAst.AttrNode](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.AttrNode]())
        }
        return r
      case o: SysmlAst.ConnectionDefinition =>
        val r: PreResult[SysmlAst.AttrNode] = preSysmlAstConnectionDefinition(o) match {
         case PreResult(continu, MSome(r: SysmlAst.AttrNode)) => PreResult(continu, MSome[SysmlAst.AttrNode](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.AttrNode]())
        }
        return r
      case o: SysmlAst.EnumerationDefinition =>
        val r: PreResult[SysmlAst.AttrNode] = preSysmlAstEnumerationDefinition(o) match {
         case PreResult(continu, MSome(r: SysmlAst.AttrNode)) => PreResult(continu, MSome[SysmlAst.AttrNode](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.AttrNode]())
        }
        return r
      case o: SysmlAst.PartDefinition =>
        val r: PreResult[SysmlAst.AttrNode] = preSysmlAstPartDefinition(o) match {
         case PreResult(continu, MSome(r: SysmlAst.AttrNode)) => PreResult(continu, MSome[SysmlAst.AttrNode](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.AttrNode]())
        }
        return r
      case o: SysmlAst.PortDefinition =>
        val r: PreResult[SysmlAst.AttrNode] = preSysmlAstPortDefinition(o) match {
         case PreResult(continu, MSome(r: SysmlAst.AttrNode)) => PreResult(continu, MSome[SysmlAst.AttrNode](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.AttrNode]())
        }
        return r
      case o: SysmlAst.MetadataDefinition =>
        val r: PreResult[SysmlAst.AttrNode] = preSysmlAstMetadataDefinition(o) match {
         case PreResult(continu, MSome(r: SysmlAst.AttrNode)) => PreResult(continu, MSome[SysmlAst.AttrNode](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.AttrNode]())
        }
        return r
      case o: SysmlAst.AttributeUsage =>
        val r: PreResult[SysmlAst.AttrNode] = preSysmlAstAttributeUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.AttrNode)) => PreResult(continu, MSome[SysmlAst.AttrNode](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.AttrNode]())
        }
        return r
      case o: SysmlAst.ReferenceUsage =>
        val r: PreResult[SysmlAst.AttrNode] = preSysmlAstReferenceUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.AttrNode)) => PreResult(continu, MSome[SysmlAst.AttrNode](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.AttrNode]())
        }
        return r
      case o: SysmlAst.ConnectionUsage =>
        val r: PreResult[SysmlAst.AttrNode] = preSysmlAstConnectionUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.AttrNode)) => PreResult(continu, MSome[SysmlAst.AttrNode](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.AttrNode]())
        }
        return r
      case o: SysmlAst.ItemUsage =>
        val r: PreResult[SysmlAst.AttrNode] = preSysmlAstItemUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.AttrNode)) => PreResult(continu, MSome[SysmlAst.AttrNode](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.AttrNode]())
        }
        return r
      case o: SysmlAst.PartUsage =>
        val r: PreResult[SysmlAst.AttrNode] = preSysmlAstPartUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.AttrNode)) => PreResult(continu, MSome[SysmlAst.AttrNode](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.AttrNode]())
        }
        return r
      case o: SysmlAst.PortUsage =>
        val r: PreResult[SysmlAst.AttrNode] = preSysmlAstPortUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.AttrNode)) => PreResult(continu, MSome[SysmlAst.AttrNode](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.AttrNode]())
        }
        return r
      case o: SysmlAst.Comment =>
        val r: PreResult[SysmlAst.AttrNode] = preSysmlAstComment(o) match {
         case PreResult(continu, MSome(r: SysmlAst.AttrNode)) => PreResult(continu, MSome[SysmlAst.AttrNode](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.AttrNode]())
        }
        return r
      case o: SysmlAst.Documentation =>
        val r: PreResult[SysmlAst.AttrNode] = preSysmlAstDocumentation(o) match {
         case PreResult(continu, MSome(r: SysmlAst.AttrNode)) => PreResult(continu, MSome[SysmlAst.AttrNode](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.AttrNode]())
        }
        return r
      case o: SysmlAst.TextualRepresentation =>
        val r: PreResult[SysmlAst.AttrNode] = preSysmlAstTextualRepresentation(o) match {
         case PreResult(continu, MSome(r: SysmlAst.AttrNode)) => PreResult(continu, MSome[SysmlAst.AttrNode](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.AttrNode]())
        }
        return r
      case o: SysmlAst.GumboAnnotation =>
        val r: PreResult[SysmlAst.AttrNode] = preSysmlAstGumboAnnotation(o) match {
         case PreResult(continu, MSome(r: SysmlAst.AttrNode)) => PreResult(continu, MSome[SysmlAst.AttrNode](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.AttrNode]())
        }
        return r
      case o: GumboAST.GclSubclause =>
        val r: PreResult[SysmlAst.AttrNode] = preGumboASTGclSubclause(o) match {
         case PreResult(continu, MSome(r: SysmlAst.AttrNode)) => PreResult(continu, MSome[SysmlAst.AttrNode](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.AttrNode]())
        }
        return r
      case o: GumboAST.GclMethod =>
        val r: PreResult[SysmlAst.AttrNode] = preGumboASTGclMethod(o) match {
         case PreResult(continu, MSome(r: SysmlAst.AttrNode)) => PreResult(continu, MSome[SysmlAst.AttrNode](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.AttrNode]())
        }
        return r
      case o: GumboAST.GclStateVar =>
        val r: PreResult[SysmlAst.AttrNode] = preGumboASTGclStateVar(o) match {
         case PreResult(continu, MSome(r: SysmlAst.AttrNode)) => PreResult(continu, MSome[SysmlAst.AttrNode](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.AttrNode]())
        }
        return r
      case o: GumboAST.GclInvariant =>
        val r: PreResult[SysmlAst.AttrNode] = preGumboASTGclInvariant(o) match {
         case PreResult(continu, MSome(r: SysmlAst.AttrNode)) => PreResult(continu, MSome[SysmlAst.AttrNode](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.AttrNode]())
        }
        return r
      case o: GumboAST.GclAssume =>
        val r: PreResult[SysmlAst.AttrNode] = preGumboASTGclAssume(o) match {
         case PreResult(continu, MSome(r: SysmlAst.AttrNode)) => PreResult(continu, MSome[SysmlAst.AttrNode](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.AttrNode]())
        }
        return r
      case o: GumboAST.GclGuarantee =>
        val r: PreResult[SysmlAst.AttrNode] = preGumboASTGclGuarantee(o) match {
         case PreResult(continu, MSome(r: SysmlAst.AttrNode)) => PreResult(continu, MSome[SysmlAst.AttrNode](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.AttrNode]())
        }
        return r
      case o: GumboAST.GclIntegration =>
        val r: PreResult[SysmlAst.AttrNode] = preGumboASTGclIntegration(o) match {
         case PreResult(continu, MSome(r: SysmlAst.AttrNode)) => PreResult(continu, MSome[SysmlAst.AttrNode](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.AttrNode]())
        }
        return r
      case o: GumboAST.GclCaseStatement =>
        val r: PreResult[SysmlAst.AttrNode] = preGumboASTGclCaseStatement(o) match {
         case PreResult(continu, MSome(r: SysmlAst.AttrNode)) => PreResult(continu, MSome[SysmlAst.AttrNode](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.AttrNode]())
        }
        return r
      case o: GumboAST.GclInitialize =>
        val r: PreResult[SysmlAst.AttrNode] = preGumboASTGclInitialize(o) match {
         case PreResult(continu, MSome(r: SysmlAst.AttrNode)) => PreResult(continu, MSome[SysmlAst.AttrNode](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.AttrNode]())
        }
        return r
      case o: GumboAST.GclCompute =>
        val r: PreResult[SysmlAst.AttrNode] = preGumboASTGclCompute(o) match {
         case PreResult(continu, MSome(r: SysmlAst.AttrNode)) => PreResult(continu, MSome[SysmlAst.AttrNode](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.AttrNode]())
        }
        return r
      case o: GumboAST.GclHandle =>
        val r: PreResult[SysmlAst.AttrNode] = preGumboASTGclHandle(o) match {
         case PreResult(continu, MSome(r: SysmlAst.AttrNode)) => PreResult(continu, MSome[SysmlAst.AttrNode](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.AttrNode]())
        }
        return r
      case o: GumboAST.GclTODO =>
        val r: PreResult[SysmlAst.AttrNode] = preGumboASTGclTODO(o) match {
         case PreResult(continu, MSome(r: SysmlAst.AttrNode)) => PreResult(continu, MSome[SysmlAst.AttrNode](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.AttrNode]())
        }
        return r
      case o: GumboAST.GclLib =>
        val r: PreResult[SysmlAst.AttrNode] = preGumboASTGclLib(o) match {
         case PreResult(continu, MSome(r: SysmlAst.AttrNode)) => PreResult(continu, MSome[SysmlAst.AttrNode](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.AttrNode]())
        }
        return r
      case o: GumboAST.InfoFlowClause =>
        val r: PreResult[SysmlAst.AttrNode] = preGumboASTInfoFlowClause(o) match {
         case PreResult(continu, MSome(r: SysmlAst.AttrNode)) => PreResult(continu, MSome[SysmlAst.AttrNode](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.AttrNode")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.AttrNode]())
        }
        return r
    }
  }

  def preSysmlAstPackageBodyElement(o: SysmlAst.PackageBodyElement): PreResult[SysmlAst.PackageBodyElement] = {
    o match {
      case o: SysmlAst.Import =>
        val r: PreResult[SysmlAst.PackageBodyElement] = preSysmlAstImport(o) match {
         case PreResult(continu, MSome(r: SysmlAst.PackageBodyElement)) => PreResult(continu, MSome[SysmlAst.PackageBodyElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.PackageBodyElement]())
        }
        return r
      case o: SysmlAst.AliasMember =>
        val r: PreResult[SysmlAst.PackageBodyElement] = preSysmlAstAliasMember(o) match {
         case PreResult(continu, MSome(r: SysmlAst.PackageBodyElement)) => PreResult(continu, MSome[SysmlAst.PackageBodyElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.PackageBodyElement]())
        }
        return r
      case o: SysmlAst.Package =>
        val r: PreResult[SysmlAst.PackageBodyElement] = preSysmlAstPackage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.PackageBodyElement)) => PreResult(continu, MSome[SysmlAst.PackageBodyElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.PackageBodyElement]())
        }
        return r
      case o: SysmlAst.AttributeDefinition =>
        val r: PreResult[SysmlAst.PackageBodyElement] = preSysmlAstAttributeDefinition(o) match {
         case PreResult(continu, MSome(r: SysmlAst.PackageBodyElement)) => PreResult(continu, MSome[SysmlAst.PackageBodyElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.PackageBodyElement]())
        }
        return r
      case o: SysmlAst.AllocationDefinition =>
        val r: PreResult[SysmlAst.PackageBodyElement] = preSysmlAstAllocationDefinition(o) match {
         case PreResult(continu, MSome(r: SysmlAst.PackageBodyElement)) => PreResult(continu, MSome[SysmlAst.PackageBodyElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.PackageBodyElement]())
        }
        return r
      case o: SysmlAst.ConnectionDefinition =>
        val r: PreResult[SysmlAst.PackageBodyElement] = preSysmlAstConnectionDefinition(o) match {
         case PreResult(continu, MSome(r: SysmlAst.PackageBodyElement)) => PreResult(continu, MSome[SysmlAst.PackageBodyElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.PackageBodyElement]())
        }
        return r
      case o: SysmlAst.EnumerationDefinition =>
        val r: PreResult[SysmlAst.PackageBodyElement] = preSysmlAstEnumerationDefinition(o) match {
         case PreResult(continu, MSome(r: SysmlAst.PackageBodyElement)) => PreResult(continu, MSome[SysmlAst.PackageBodyElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.PackageBodyElement]())
        }
        return r
      case o: SysmlAst.PartDefinition =>
        val r: PreResult[SysmlAst.PackageBodyElement] = preSysmlAstPartDefinition(o) match {
         case PreResult(continu, MSome(r: SysmlAst.PackageBodyElement)) => PreResult(continu, MSome[SysmlAst.PackageBodyElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.PackageBodyElement]())
        }
        return r
      case o: SysmlAst.PortDefinition =>
        val r: PreResult[SysmlAst.PackageBodyElement] = preSysmlAstPortDefinition(o) match {
         case PreResult(continu, MSome(r: SysmlAst.PackageBodyElement)) => PreResult(continu, MSome[SysmlAst.PackageBodyElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.PackageBodyElement]())
        }
        return r
      case o: SysmlAst.MetadataDefinition =>
        val r: PreResult[SysmlAst.PackageBodyElement] = preSysmlAstMetadataDefinition(o) match {
         case PreResult(continu, MSome(r: SysmlAst.PackageBodyElement)) => PreResult(continu, MSome[SysmlAst.PackageBodyElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.PackageBodyElement]())
        }
        return r
      case o: SysmlAst.AttributeUsage =>
        val r: PreResult[SysmlAst.PackageBodyElement] = preSysmlAstAttributeUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.PackageBodyElement)) => PreResult(continu, MSome[SysmlAst.PackageBodyElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.PackageBodyElement]())
        }
        return r
      case o: SysmlAst.ReferenceUsage =>
        val r: PreResult[SysmlAst.PackageBodyElement] = preSysmlAstReferenceUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.PackageBodyElement)) => PreResult(continu, MSome[SysmlAst.PackageBodyElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.PackageBodyElement]())
        }
        return r
      case o: SysmlAst.ConnectionUsage =>
        val r: PreResult[SysmlAst.PackageBodyElement] = preSysmlAstConnectionUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.PackageBodyElement)) => PreResult(continu, MSome[SysmlAst.PackageBodyElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.PackageBodyElement]())
        }
        return r
      case o: SysmlAst.ItemUsage =>
        val r: PreResult[SysmlAst.PackageBodyElement] = preSysmlAstItemUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.PackageBodyElement)) => PreResult(continu, MSome[SysmlAst.PackageBodyElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.PackageBodyElement]())
        }
        return r
      case o: SysmlAst.PartUsage =>
        val r: PreResult[SysmlAst.PackageBodyElement] = preSysmlAstPartUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.PackageBodyElement)) => PreResult(continu, MSome[SysmlAst.PackageBodyElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.PackageBodyElement]())
        }
        return r
      case o: SysmlAst.PortUsage =>
        val r: PreResult[SysmlAst.PackageBodyElement] = preSysmlAstPortUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.PackageBodyElement)) => PreResult(continu, MSome[SysmlAst.PackageBodyElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.PackageBodyElement]())
        }
        return r
      case o: SysmlAst.Comment =>
        val r: PreResult[SysmlAst.PackageBodyElement] = preSysmlAstComment(o) match {
         case PreResult(continu, MSome(r: SysmlAst.PackageBodyElement)) => PreResult(continu, MSome[SysmlAst.PackageBodyElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.PackageBodyElement]())
        }
        return r
      case o: SysmlAst.Documentation =>
        val r: PreResult[SysmlAst.PackageBodyElement] = preSysmlAstDocumentation(o) match {
         case PreResult(continu, MSome(r: SysmlAst.PackageBodyElement)) => PreResult(continu, MSome[SysmlAst.PackageBodyElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.PackageBodyElement]())
        }
        return r
      case o: SysmlAst.TextualRepresentation =>
        val r: PreResult[SysmlAst.PackageBodyElement] = preSysmlAstTextualRepresentation(o) match {
         case PreResult(continu, MSome(r: SysmlAst.PackageBodyElement)) => PreResult(continu, MSome[SysmlAst.PackageBodyElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.PackageBodyElement]())
        }
        return r
      case o: SysmlAst.GumboAnnotation =>
        val r: PreResult[SysmlAst.PackageBodyElement] = preSysmlAstGumboAnnotation(o) match {
         case PreResult(continu, MSome(r: SysmlAst.PackageBodyElement)) => PreResult(continu, MSome[SysmlAst.PackageBodyElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.PackageBodyElement]())
        }
        return r
    }
  }

  def preSysmlAstDefinitionBodyItem(o: SysmlAst.DefinitionBodyItem): PreResult[SysmlAst.DefinitionBodyItem] = {
    o match {
      case o: SysmlAst.Import =>
        val r: PreResult[SysmlAst.DefinitionBodyItem] = preSysmlAstImport(o) match {
         case PreResult(continu, MSome(r: SysmlAst.DefinitionBodyItem)) => PreResult(continu, MSome[SysmlAst.DefinitionBodyItem](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.DefinitionBodyItem")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.DefinitionBodyItem]())
        }
        return r
      case o: SysmlAst.AliasMember =>
        val r: PreResult[SysmlAst.DefinitionBodyItem] = preSysmlAstAliasMember(o) match {
         case PreResult(continu, MSome(r: SysmlAst.DefinitionBodyItem)) => PreResult(continu, MSome[SysmlAst.DefinitionBodyItem](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.DefinitionBodyItem")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.DefinitionBodyItem]())
        }
        return r
      case o: SysmlAst.Package =>
        val r: PreResult[SysmlAst.DefinitionBodyItem] = preSysmlAstPackage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.DefinitionBodyItem)) => PreResult(continu, MSome[SysmlAst.DefinitionBodyItem](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.DefinitionBodyItem")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.DefinitionBodyItem]())
        }
        return r
      case o: SysmlAst.AttributeDefinition =>
        val r: PreResult[SysmlAst.DefinitionBodyItem] = preSysmlAstAttributeDefinition(o) match {
         case PreResult(continu, MSome(r: SysmlAst.DefinitionBodyItem)) => PreResult(continu, MSome[SysmlAst.DefinitionBodyItem](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.DefinitionBodyItem")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.DefinitionBodyItem]())
        }
        return r
      case o: SysmlAst.AllocationDefinition =>
        val r: PreResult[SysmlAst.DefinitionBodyItem] = preSysmlAstAllocationDefinition(o) match {
         case PreResult(continu, MSome(r: SysmlAst.DefinitionBodyItem)) => PreResult(continu, MSome[SysmlAst.DefinitionBodyItem](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.DefinitionBodyItem")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.DefinitionBodyItem]())
        }
        return r
      case o: SysmlAst.ConnectionDefinition =>
        val r: PreResult[SysmlAst.DefinitionBodyItem] = preSysmlAstConnectionDefinition(o) match {
         case PreResult(continu, MSome(r: SysmlAst.DefinitionBodyItem)) => PreResult(continu, MSome[SysmlAst.DefinitionBodyItem](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.DefinitionBodyItem")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.DefinitionBodyItem]())
        }
        return r
      case o: SysmlAst.EnumerationDefinition =>
        val r: PreResult[SysmlAst.DefinitionBodyItem] = preSysmlAstEnumerationDefinition(o) match {
         case PreResult(continu, MSome(r: SysmlAst.DefinitionBodyItem)) => PreResult(continu, MSome[SysmlAst.DefinitionBodyItem](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.DefinitionBodyItem")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.DefinitionBodyItem]())
        }
        return r
      case o: SysmlAst.PartDefinition =>
        val r: PreResult[SysmlAst.DefinitionBodyItem] = preSysmlAstPartDefinition(o) match {
         case PreResult(continu, MSome(r: SysmlAst.DefinitionBodyItem)) => PreResult(continu, MSome[SysmlAst.DefinitionBodyItem](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.DefinitionBodyItem")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.DefinitionBodyItem]())
        }
        return r
      case o: SysmlAst.PortDefinition =>
        val r: PreResult[SysmlAst.DefinitionBodyItem] = preSysmlAstPortDefinition(o) match {
         case PreResult(continu, MSome(r: SysmlAst.DefinitionBodyItem)) => PreResult(continu, MSome[SysmlAst.DefinitionBodyItem](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.DefinitionBodyItem")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.DefinitionBodyItem]())
        }
        return r
      case o: SysmlAst.MetadataDefinition =>
        val r: PreResult[SysmlAst.DefinitionBodyItem] = preSysmlAstMetadataDefinition(o) match {
         case PreResult(continu, MSome(r: SysmlAst.DefinitionBodyItem)) => PreResult(continu, MSome[SysmlAst.DefinitionBodyItem](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.DefinitionBodyItem")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.DefinitionBodyItem]())
        }
        return r
      case o: SysmlAst.AttributeUsage =>
        val r: PreResult[SysmlAst.DefinitionBodyItem] = preSysmlAstAttributeUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.DefinitionBodyItem)) => PreResult(continu, MSome[SysmlAst.DefinitionBodyItem](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.DefinitionBodyItem")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.DefinitionBodyItem]())
        }
        return r
      case o: SysmlAst.ReferenceUsage =>
        val r: PreResult[SysmlAst.DefinitionBodyItem] = preSysmlAstReferenceUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.DefinitionBodyItem)) => PreResult(continu, MSome[SysmlAst.DefinitionBodyItem](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.DefinitionBodyItem")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.DefinitionBodyItem]())
        }
        return r
      case o: SysmlAst.ConnectionUsage =>
        val r: PreResult[SysmlAst.DefinitionBodyItem] = preSysmlAstConnectionUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.DefinitionBodyItem)) => PreResult(continu, MSome[SysmlAst.DefinitionBodyItem](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.DefinitionBodyItem")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.DefinitionBodyItem]())
        }
        return r
      case o: SysmlAst.ItemUsage =>
        val r: PreResult[SysmlAst.DefinitionBodyItem] = preSysmlAstItemUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.DefinitionBodyItem)) => PreResult(continu, MSome[SysmlAst.DefinitionBodyItem](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.DefinitionBodyItem")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.DefinitionBodyItem]())
        }
        return r
      case o: SysmlAst.PartUsage =>
        val r: PreResult[SysmlAst.DefinitionBodyItem] = preSysmlAstPartUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.DefinitionBodyItem)) => PreResult(continu, MSome[SysmlAst.DefinitionBodyItem](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.DefinitionBodyItem")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.DefinitionBodyItem]())
        }
        return r
      case o: SysmlAst.PortUsage =>
        val r: PreResult[SysmlAst.DefinitionBodyItem] = preSysmlAstPortUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.DefinitionBodyItem)) => PreResult(continu, MSome[SysmlAst.DefinitionBodyItem](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.DefinitionBodyItem")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.DefinitionBodyItem]())
        }
        return r
      case o: SysmlAst.Comment =>
        val r: PreResult[SysmlAst.DefinitionBodyItem] = preSysmlAstComment(o) match {
         case PreResult(continu, MSome(r: SysmlAst.DefinitionBodyItem)) => PreResult(continu, MSome[SysmlAst.DefinitionBodyItem](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.DefinitionBodyItem")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.DefinitionBodyItem]())
        }
        return r
      case o: SysmlAst.Documentation =>
        val r: PreResult[SysmlAst.DefinitionBodyItem] = preSysmlAstDocumentation(o) match {
         case PreResult(continu, MSome(r: SysmlAst.DefinitionBodyItem)) => PreResult(continu, MSome[SysmlAst.DefinitionBodyItem](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.DefinitionBodyItem")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.DefinitionBodyItem]())
        }
        return r
      case o: SysmlAst.TextualRepresentation =>
        val r: PreResult[SysmlAst.DefinitionBodyItem] = preSysmlAstTextualRepresentation(o) match {
         case PreResult(continu, MSome(r: SysmlAst.DefinitionBodyItem)) => PreResult(continu, MSome[SysmlAst.DefinitionBodyItem](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.DefinitionBodyItem")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.DefinitionBodyItem]())
        }
        return r
      case o: SysmlAst.GumboAnnotation =>
        val r: PreResult[SysmlAst.DefinitionBodyItem] = preSysmlAstGumboAnnotation(o) match {
         case PreResult(continu, MSome(r: SysmlAst.DefinitionBodyItem)) => PreResult(continu, MSome[SysmlAst.DefinitionBodyItem](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.DefinitionBodyItem")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.DefinitionBodyItem]())
        }
        return r
    }
  }

  def preSysmlAstFeatureValue(o: SysmlAst.FeatureValue): PreResult[SysmlAst.FeatureValue] = {
    return PreResultSysmlAstFeatureValue
  }

  def preSysmlAstEnumeratedValue(o: SysmlAst.EnumeratedValue): PreResult[SysmlAst.EnumeratedValue] = {
    return PreResultSysmlAstEnumeratedValue
  }

  def preSysmlAstImport(o: SysmlAst.Import): PreResult[SysmlAst.Import] = {
    return PreResultSysmlAstImport
  }

  def preSysmlAstAliasMember(o: SysmlAst.AliasMember): PreResult[SysmlAst.AliasMember] = {
    return PreResultSysmlAstAliasMember
  }

  def preSysmlAstIdentification(o: SysmlAst.Identification): PreResult[SysmlAst.Identification] = {
    return PreResultSysmlAstIdentification
  }

  def preSysmlAstPackageMember(o: SysmlAst.PackageMember): PreResult[SysmlAst.PackageMember] = {
    o match {
      case o: SysmlAst.Package =>
        val r: PreResult[SysmlAst.PackageMember] = preSysmlAstPackage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.PackageMember)) => PreResult(continu, MSome[SysmlAst.PackageMember](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.PackageMember")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.PackageMember]())
        }
        return r
      case o: SysmlAst.AttributeDefinition =>
        val r: PreResult[SysmlAst.PackageMember] = preSysmlAstAttributeDefinition(o) match {
         case PreResult(continu, MSome(r: SysmlAst.PackageMember)) => PreResult(continu, MSome[SysmlAst.PackageMember](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.PackageMember")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.PackageMember]())
        }
        return r
      case o: SysmlAst.AllocationDefinition =>
        val r: PreResult[SysmlAst.PackageMember] = preSysmlAstAllocationDefinition(o) match {
         case PreResult(continu, MSome(r: SysmlAst.PackageMember)) => PreResult(continu, MSome[SysmlAst.PackageMember](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.PackageMember")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.PackageMember]())
        }
        return r
      case o: SysmlAst.ConnectionDefinition =>
        val r: PreResult[SysmlAst.PackageMember] = preSysmlAstConnectionDefinition(o) match {
         case PreResult(continu, MSome(r: SysmlAst.PackageMember)) => PreResult(continu, MSome[SysmlAst.PackageMember](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.PackageMember")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.PackageMember]())
        }
        return r
      case o: SysmlAst.EnumerationDefinition =>
        val r: PreResult[SysmlAst.PackageMember] = preSysmlAstEnumerationDefinition(o) match {
         case PreResult(continu, MSome(r: SysmlAst.PackageMember)) => PreResult(continu, MSome[SysmlAst.PackageMember](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.PackageMember")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.PackageMember]())
        }
        return r
      case o: SysmlAst.PartDefinition =>
        val r: PreResult[SysmlAst.PackageMember] = preSysmlAstPartDefinition(o) match {
         case PreResult(continu, MSome(r: SysmlAst.PackageMember)) => PreResult(continu, MSome[SysmlAst.PackageMember](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.PackageMember")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.PackageMember]())
        }
        return r
      case o: SysmlAst.PortDefinition =>
        val r: PreResult[SysmlAst.PackageMember] = preSysmlAstPortDefinition(o) match {
         case PreResult(continu, MSome(r: SysmlAst.PackageMember)) => PreResult(continu, MSome[SysmlAst.PackageMember](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.PackageMember")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.PackageMember]())
        }
        return r
      case o: SysmlAst.MetadataDefinition =>
        val r: PreResult[SysmlAst.PackageMember] = preSysmlAstMetadataDefinition(o) match {
         case PreResult(continu, MSome(r: SysmlAst.PackageMember)) => PreResult(continu, MSome[SysmlAst.PackageMember](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.PackageMember")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.PackageMember]())
        }
        return r
      case o: SysmlAst.AttributeUsage =>
        val r: PreResult[SysmlAst.PackageMember] = preSysmlAstAttributeUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.PackageMember)) => PreResult(continu, MSome[SysmlAst.PackageMember](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.PackageMember")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.PackageMember]())
        }
        return r
      case o: SysmlAst.ReferenceUsage =>
        val r: PreResult[SysmlAst.PackageMember] = preSysmlAstReferenceUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.PackageMember)) => PreResult(continu, MSome[SysmlAst.PackageMember](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.PackageMember")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.PackageMember]())
        }
        return r
      case o: SysmlAst.ConnectionUsage =>
        val r: PreResult[SysmlAst.PackageMember] = preSysmlAstConnectionUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.PackageMember)) => PreResult(continu, MSome[SysmlAst.PackageMember](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.PackageMember")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.PackageMember]())
        }
        return r
      case o: SysmlAst.ItemUsage =>
        val r: PreResult[SysmlAst.PackageMember] = preSysmlAstItemUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.PackageMember)) => PreResult(continu, MSome[SysmlAst.PackageMember](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.PackageMember")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.PackageMember]())
        }
        return r
      case o: SysmlAst.PartUsage =>
        val r: PreResult[SysmlAst.PackageMember] = preSysmlAstPartUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.PackageMember)) => PreResult(continu, MSome[SysmlAst.PackageMember](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.PackageMember")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.PackageMember]())
        }
        return r
      case o: SysmlAst.PortUsage =>
        val r: PreResult[SysmlAst.PackageMember] = preSysmlAstPortUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.PackageMember)) => PreResult(continu, MSome[SysmlAst.PackageMember](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.PackageMember")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.PackageMember]())
        }
        return r
      case o: SysmlAst.Comment =>
        val r: PreResult[SysmlAst.PackageMember] = preSysmlAstComment(o) match {
         case PreResult(continu, MSome(r: SysmlAst.PackageMember)) => PreResult(continu, MSome[SysmlAst.PackageMember](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.PackageMember")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.PackageMember]())
        }
        return r
      case o: SysmlAst.Documentation =>
        val r: PreResult[SysmlAst.PackageMember] = preSysmlAstDocumentation(o) match {
         case PreResult(continu, MSome(r: SysmlAst.PackageMember)) => PreResult(continu, MSome[SysmlAst.PackageMember](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.PackageMember")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.PackageMember]())
        }
        return r
      case o: SysmlAst.TextualRepresentation =>
        val r: PreResult[SysmlAst.PackageMember] = preSysmlAstTextualRepresentation(o) match {
         case PreResult(continu, MSome(r: SysmlAst.PackageMember)) => PreResult(continu, MSome[SysmlAst.PackageMember](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.PackageMember")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.PackageMember]())
        }
        return r
      case o: SysmlAst.GumboAnnotation =>
        val r: PreResult[SysmlAst.PackageMember] = preSysmlAstGumboAnnotation(o) match {
         case PreResult(continu, MSome(r: SysmlAst.PackageMember)) => PreResult(continu, MSome[SysmlAst.PackageMember](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.PackageMember")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.PackageMember]())
        }
        return r
    }
  }

  def preSysmlAstConnectorPart(o: SysmlAst.ConnectorPart): PreResult[SysmlAst.ConnectorPart] = {
    o match {
      case o: SysmlAst.BinaryConnectorPart =>
        val r: PreResult[SysmlAst.ConnectorPart] = preSysmlAstBinaryConnectorPart(o) match {
         case PreResult(continu, MSome(r: SysmlAst.ConnectorPart)) => PreResult(continu, MSome[SysmlAst.ConnectorPart](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.ConnectorPart")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.ConnectorPart]())
        }
        return r
      case o: SysmlAst.NaryConnectorPart =>
        val r: PreResult[SysmlAst.ConnectorPart] = preSysmlAstNaryConnectorPart(o) match {
         case PreResult(continu, MSome(r: SysmlAst.ConnectorPart)) => PreResult(continu, MSome[SysmlAst.ConnectorPart](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.ConnectorPart")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.ConnectorPart]())
        }
        return r
    }
  }

  def preSysmlAstConnectorEnd(o: SysmlAst.ConnectorEnd): PreResult[SysmlAst.ConnectorEnd] = {
    return PreResultSysmlAstConnectorEnd
  }

  def preSysmlAstBinaryConnectorPart(o: SysmlAst.BinaryConnectorPart): PreResult[SysmlAst.BinaryConnectorPart] = {
    return PreResultSysmlAstBinaryConnectorPart
  }

  def preSysmlAstNaryConnectorPart(o: SysmlAst.NaryConnectorPart): PreResult[SysmlAst.NaryConnectorPart] = {
    return PreResultSysmlAstNaryConnectorPart
  }

  def preSysmlAstFeatureSpecialization(o: SysmlAst.FeatureSpecialization): PreResult[SysmlAst.FeatureSpecialization] = {
    o match {
      case o: SysmlAst.TypingsSpecialization =>
        val r: PreResult[SysmlAst.FeatureSpecialization] = preSysmlAstTypingsSpecialization(o) match {
         case PreResult(continu, MSome(r: SysmlAst.FeatureSpecialization)) => PreResult(continu, MSome[SysmlAst.FeatureSpecialization](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.FeatureSpecialization")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.FeatureSpecialization]())
        }
        return r
      case o: SysmlAst.SubsettingsSpecialization =>
        val r: PreResult[SysmlAst.FeatureSpecialization] = preSysmlAstSubsettingsSpecialization(o) match {
         case PreResult(continu, MSome(r: SysmlAst.FeatureSpecialization)) => PreResult(continu, MSome[SysmlAst.FeatureSpecialization](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.FeatureSpecialization")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.FeatureSpecialization]())
        }
        return r
      case o: SysmlAst.ReferencesSpecialization =>
        val r: PreResult[SysmlAst.FeatureSpecialization] = preSysmlAstReferencesSpecialization(o) match {
         case PreResult(continu, MSome(r: SysmlAst.FeatureSpecialization)) => PreResult(continu, MSome[SysmlAst.FeatureSpecialization](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.FeatureSpecialization")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.FeatureSpecialization]())
        }
        return r
      case o: SysmlAst.RedefinitionsSpecialization =>
        val r: PreResult[SysmlAst.FeatureSpecialization] = preSysmlAstRedefinitionsSpecialization(o) match {
         case PreResult(continu, MSome(r: SysmlAst.FeatureSpecialization)) => PreResult(continu, MSome[SysmlAst.FeatureSpecialization](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.FeatureSpecialization")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.FeatureSpecialization]())
        }
        return r
    }
  }

  def preSysmlAstTypingsSpecialization(o: SysmlAst.TypingsSpecialization): PreResult[SysmlAst.TypingsSpecialization] = {
    return PreResultSysmlAstTypingsSpecialization
  }

  def preSysmlAstSubsettingsSpecialization(o: SysmlAst.SubsettingsSpecialization): PreResult[SysmlAst.SubsettingsSpecialization] = {
    return PreResultSysmlAstSubsettingsSpecialization
  }

  def preSysmlAstReferencesSpecialization(o: SysmlAst.ReferencesSpecialization): PreResult[SysmlAst.ReferencesSpecialization] = {
    return PreResultSysmlAstReferencesSpecialization
  }

  def preSysmlAstRedefinitionsSpecialization(o: SysmlAst.RedefinitionsSpecialization): PreResult[SysmlAst.RedefinitionsSpecialization] = {
    return PreResultSysmlAstRedefinitionsSpecialization
  }

  def preSysmlAstDefinitionMember(o: SysmlAst.DefinitionMember): PreResult[SysmlAst.DefinitionMember] = {
    o match {
      case o: SysmlAst.Package =>
        val r: PreResult[SysmlAst.DefinitionMember] = preSysmlAstPackage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.DefinitionMember)) => PreResult(continu, MSome[SysmlAst.DefinitionMember](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.DefinitionMember")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.DefinitionMember]())
        }
        return r
      case o: SysmlAst.AttributeDefinition =>
        val r: PreResult[SysmlAst.DefinitionMember] = preSysmlAstAttributeDefinition(o) match {
         case PreResult(continu, MSome(r: SysmlAst.DefinitionMember)) => PreResult(continu, MSome[SysmlAst.DefinitionMember](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.DefinitionMember")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.DefinitionMember]())
        }
        return r
      case o: SysmlAst.AllocationDefinition =>
        val r: PreResult[SysmlAst.DefinitionMember] = preSysmlAstAllocationDefinition(o) match {
         case PreResult(continu, MSome(r: SysmlAst.DefinitionMember)) => PreResult(continu, MSome[SysmlAst.DefinitionMember](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.DefinitionMember")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.DefinitionMember]())
        }
        return r
      case o: SysmlAst.ConnectionDefinition =>
        val r: PreResult[SysmlAst.DefinitionMember] = preSysmlAstConnectionDefinition(o) match {
         case PreResult(continu, MSome(r: SysmlAst.DefinitionMember)) => PreResult(continu, MSome[SysmlAst.DefinitionMember](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.DefinitionMember")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.DefinitionMember]())
        }
        return r
      case o: SysmlAst.EnumerationDefinition =>
        val r: PreResult[SysmlAst.DefinitionMember] = preSysmlAstEnumerationDefinition(o) match {
         case PreResult(continu, MSome(r: SysmlAst.DefinitionMember)) => PreResult(continu, MSome[SysmlAst.DefinitionMember](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.DefinitionMember")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.DefinitionMember]())
        }
        return r
      case o: SysmlAst.PartDefinition =>
        val r: PreResult[SysmlAst.DefinitionMember] = preSysmlAstPartDefinition(o) match {
         case PreResult(continu, MSome(r: SysmlAst.DefinitionMember)) => PreResult(continu, MSome[SysmlAst.DefinitionMember](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.DefinitionMember")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.DefinitionMember]())
        }
        return r
      case o: SysmlAst.PortDefinition =>
        val r: PreResult[SysmlAst.DefinitionMember] = preSysmlAstPortDefinition(o) match {
         case PreResult(continu, MSome(r: SysmlAst.DefinitionMember)) => PreResult(continu, MSome[SysmlAst.DefinitionMember](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.DefinitionMember")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.DefinitionMember]())
        }
        return r
      case o: SysmlAst.MetadataDefinition =>
        val r: PreResult[SysmlAst.DefinitionMember] = preSysmlAstMetadataDefinition(o) match {
         case PreResult(continu, MSome(r: SysmlAst.DefinitionMember)) => PreResult(continu, MSome[SysmlAst.DefinitionMember](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.DefinitionMember")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.DefinitionMember]())
        }
        return r
      case o: SysmlAst.Comment =>
        val r: PreResult[SysmlAst.DefinitionMember] = preSysmlAstComment(o) match {
         case PreResult(continu, MSome(r: SysmlAst.DefinitionMember)) => PreResult(continu, MSome[SysmlAst.DefinitionMember](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.DefinitionMember")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.DefinitionMember]())
        }
        return r
      case o: SysmlAst.Documentation =>
        val r: PreResult[SysmlAst.DefinitionMember] = preSysmlAstDocumentation(o) match {
         case PreResult(continu, MSome(r: SysmlAst.DefinitionMember)) => PreResult(continu, MSome[SysmlAst.DefinitionMember](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.DefinitionMember")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.DefinitionMember]())
        }
        return r
      case o: SysmlAst.TextualRepresentation =>
        val r: PreResult[SysmlAst.DefinitionMember] = preSysmlAstTextualRepresentation(o) match {
         case PreResult(continu, MSome(r: SysmlAst.DefinitionMember)) => PreResult(continu, MSome[SysmlAst.DefinitionMember](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.DefinitionMember")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.DefinitionMember]())
        }
        return r
      case o: SysmlAst.GumboAnnotation =>
        val r: PreResult[SysmlAst.DefinitionMember] = preSysmlAstGumboAnnotation(o) match {
         case PreResult(continu, MSome(r: SysmlAst.DefinitionMember)) => PreResult(continu, MSome[SysmlAst.DefinitionMember](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.DefinitionMember")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.DefinitionMember]())
        }
        return r
    }
  }

  def preSysmlAstDefinitionElement(o: SysmlAst.DefinitionElement): PreResult[SysmlAst.DefinitionElement] = {
    o match {
      case o: SysmlAst.Package =>
        val r: PreResult[SysmlAst.DefinitionElement] = preSysmlAstPackage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.DefinitionElement)) => PreResult(continu, MSome[SysmlAst.DefinitionElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.DefinitionElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.DefinitionElement]())
        }
        return r
      case o: SysmlAst.AttributeDefinition =>
        val r: PreResult[SysmlAst.DefinitionElement] = preSysmlAstAttributeDefinition(o) match {
         case PreResult(continu, MSome(r: SysmlAst.DefinitionElement)) => PreResult(continu, MSome[SysmlAst.DefinitionElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.DefinitionElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.DefinitionElement]())
        }
        return r
      case o: SysmlAst.AllocationDefinition =>
        val r: PreResult[SysmlAst.DefinitionElement] = preSysmlAstAllocationDefinition(o) match {
         case PreResult(continu, MSome(r: SysmlAst.DefinitionElement)) => PreResult(continu, MSome[SysmlAst.DefinitionElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.DefinitionElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.DefinitionElement]())
        }
        return r
      case o: SysmlAst.ConnectionDefinition =>
        val r: PreResult[SysmlAst.DefinitionElement] = preSysmlAstConnectionDefinition(o) match {
         case PreResult(continu, MSome(r: SysmlAst.DefinitionElement)) => PreResult(continu, MSome[SysmlAst.DefinitionElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.DefinitionElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.DefinitionElement]())
        }
        return r
      case o: SysmlAst.EnumerationDefinition =>
        val r: PreResult[SysmlAst.DefinitionElement] = preSysmlAstEnumerationDefinition(o) match {
         case PreResult(continu, MSome(r: SysmlAst.DefinitionElement)) => PreResult(continu, MSome[SysmlAst.DefinitionElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.DefinitionElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.DefinitionElement]())
        }
        return r
      case o: SysmlAst.PartDefinition =>
        val r: PreResult[SysmlAst.DefinitionElement] = preSysmlAstPartDefinition(o) match {
         case PreResult(continu, MSome(r: SysmlAst.DefinitionElement)) => PreResult(continu, MSome[SysmlAst.DefinitionElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.DefinitionElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.DefinitionElement]())
        }
        return r
      case o: SysmlAst.PortDefinition =>
        val r: PreResult[SysmlAst.DefinitionElement] = preSysmlAstPortDefinition(o) match {
         case PreResult(continu, MSome(r: SysmlAst.DefinitionElement)) => PreResult(continu, MSome[SysmlAst.DefinitionElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.DefinitionElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.DefinitionElement]())
        }
        return r
      case o: SysmlAst.MetadataDefinition =>
        val r: PreResult[SysmlAst.DefinitionElement] = preSysmlAstMetadataDefinition(o) match {
         case PreResult(continu, MSome(r: SysmlAst.DefinitionElement)) => PreResult(continu, MSome[SysmlAst.DefinitionElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.DefinitionElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.DefinitionElement]())
        }
        return r
      case o: SysmlAst.Comment =>
        val r: PreResult[SysmlAst.DefinitionElement] = preSysmlAstComment(o) match {
         case PreResult(continu, MSome(r: SysmlAst.DefinitionElement)) => PreResult(continu, MSome[SysmlAst.DefinitionElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.DefinitionElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.DefinitionElement]())
        }
        return r
      case o: SysmlAst.Documentation =>
        val r: PreResult[SysmlAst.DefinitionElement] = preSysmlAstDocumentation(o) match {
         case PreResult(continu, MSome(r: SysmlAst.DefinitionElement)) => PreResult(continu, MSome[SysmlAst.DefinitionElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.DefinitionElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.DefinitionElement]())
        }
        return r
      case o: SysmlAst.TextualRepresentation =>
        val r: PreResult[SysmlAst.DefinitionElement] = preSysmlAstTextualRepresentation(o) match {
         case PreResult(continu, MSome(r: SysmlAst.DefinitionElement)) => PreResult(continu, MSome[SysmlAst.DefinitionElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.DefinitionElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.DefinitionElement]())
        }
        return r
      case o: SysmlAst.GumboAnnotation =>
        val r: PreResult[SysmlAst.DefinitionElement] = preSysmlAstGumboAnnotation(o) match {
         case PreResult(continu, MSome(r: SysmlAst.DefinitionElement)) => PreResult(continu, MSome[SysmlAst.DefinitionElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.DefinitionElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.DefinitionElement]())
        }
        return r
    }
  }

  def preSysmlAstDefinitionPrefix(o: SysmlAst.DefinitionPrefix): PreResult[SysmlAst.DefinitionPrefix] = {
    return PreResultSysmlAstDefinitionPrefix
  }

  def preSysmlAstPackage(o: SysmlAst.Package): PreResult[SysmlAst.Package] = {
    return PreResultSysmlAstPackage
  }

  def preSysmlAstAttributeDefinition(o: SysmlAst.AttributeDefinition): PreResult[SysmlAst.AttributeDefinition] = {
    return PreResultSysmlAstAttributeDefinition
  }

  def preSysmlAstOccurrenceDefinitionPrefix(o: SysmlAst.OccurrenceDefinitionPrefix): PreResult[SysmlAst.OccurrenceDefinitionPrefix] = {
    return PreResultSysmlAstOccurrenceDefinitionPrefix
  }

  def preSysmlAstAllocationDefinition(o: SysmlAst.AllocationDefinition): PreResult[SysmlAst.AllocationDefinition] = {
    return PreResultSysmlAstAllocationDefinition
  }

  def preSysmlAstConnectionDefinition(o: SysmlAst.ConnectionDefinition): PreResult[SysmlAst.ConnectionDefinition] = {
    return PreResultSysmlAstConnectionDefinition
  }

  def preSysmlAstEnumerationDefinition(o: SysmlAst.EnumerationDefinition): PreResult[SysmlAst.EnumerationDefinition] = {
    return PreResultSysmlAstEnumerationDefinition
  }

  def preSysmlAstPartDefinition(o: SysmlAst.PartDefinition): PreResult[SysmlAst.PartDefinition] = {
    return PreResultSysmlAstPartDefinition
  }

  def preSysmlAstPortDefinition(o: SysmlAst.PortDefinition): PreResult[SysmlAst.PortDefinition] = {
    return PreResultSysmlAstPortDefinition
  }

  def preSysmlAstMetadataDefinition(o: SysmlAst.MetadataDefinition): PreResult[SysmlAst.MetadataDefinition] = {
    return PreResultSysmlAstMetadataDefinition
  }

  def preSysmlAstUsageElement(o: SysmlAst.UsageElement): PreResult[SysmlAst.UsageElement] = {
    o match {
      case o: SysmlAst.AttributeUsage =>
        val r: PreResult[SysmlAst.UsageElement] = preSysmlAstAttributeUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.UsageElement)) => PreResult(continu, MSome[SysmlAst.UsageElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.UsageElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.UsageElement]())
        }
        return r
      case o: SysmlAst.ReferenceUsage =>
        val r: PreResult[SysmlAst.UsageElement] = preSysmlAstReferenceUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.UsageElement)) => PreResult(continu, MSome[SysmlAst.UsageElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.UsageElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.UsageElement]())
        }
        return r
      case o: SysmlAst.ConnectionUsage =>
        val r: PreResult[SysmlAst.UsageElement] = preSysmlAstConnectionUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.UsageElement)) => PreResult(continu, MSome[SysmlAst.UsageElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.UsageElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.UsageElement]())
        }
        return r
      case o: SysmlAst.ItemUsage =>
        val r: PreResult[SysmlAst.UsageElement] = preSysmlAstItemUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.UsageElement)) => PreResult(continu, MSome[SysmlAst.UsageElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.UsageElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.UsageElement]())
        }
        return r
      case o: SysmlAst.PartUsage =>
        val r: PreResult[SysmlAst.UsageElement] = preSysmlAstPartUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.UsageElement)) => PreResult(continu, MSome[SysmlAst.UsageElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.UsageElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.UsageElement]())
        }
        return r
      case o: SysmlAst.PortUsage =>
        val r: PreResult[SysmlAst.UsageElement] = preSysmlAstPortUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.UsageElement)) => PreResult(continu, MSome[SysmlAst.UsageElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.UsageElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.UsageElement]())
        }
        return r
    }
  }

  def preSysmlAstNonOccurrenceUsageMember(o: SysmlAst.NonOccurrenceUsageMember): PreResult[SysmlAst.NonOccurrenceUsageMember] = {
    o match {
      case o: SysmlAst.AttributeUsage =>
        val r: PreResult[SysmlAst.NonOccurrenceUsageMember] = preSysmlAstAttributeUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.NonOccurrenceUsageMember)) => PreResult(continu, MSome[SysmlAst.NonOccurrenceUsageMember](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.NonOccurrenceUsageMember")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.NonOccurrenceUsageMember]())
        }
        return r
      case o: SysmlAst.ReferenceUsage =>
        val r: PreResult[SysmlAst.NonOccurrenceUsageMember] = preSysmlAstReferenceUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.NonOccurrenceUsageMember)) => PreResult(continu, MSome[SysmlAst.NonOccurrenceUsageMember](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.NonOccurrenceUsageMember")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.NonOccurrenceUsageMember]())
        }
        return r
    }
  }

  def preSysmlAstNonOccurrenceUsageElement(o: SysmlAst.NonOccurrenceUsageElement): PreResult[SysmlAst.NonOccurrenceUsageElement] = {
    o match {
      case o: SysmlAst.AttributeUsage =>
        val r: PreResult[SysmlAst.NonOccurrenceUsageElement] = preSysmlAstAttributeUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.NonOccurrenceUsageElement)) => PreResult(continu, MSome[SysmlAst.NonOccurrenceUsageElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.NonOccurrenceUsageElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.NonOccurrenceUsageElement]())
        }
        return r
      case o: SysmlAst.ReferenceUsage =>
        val r: PreResult[SysmlAst.NonOccurrenceUsageElement] = preSysmlAstReferenceUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.NonOccurrenceUsageElement)) => PreResult(continu, MSome[SysmlAst.NonOccurrenceUsageElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.NonOccurrenceUsageElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.NonOccurrenceUsageElement]())
        }
        return r
    }
  }

  def preSysmlAstRefPrefix(o: SysmlAst.RefPrefix): PreResult[SysmlAst.RefPrefix] = {
    return PreResultSysmlAstRefPrefix
  }

  def preSysmlAstUsagePrefix(o: SysmlAst.UsagePrefix): PreResult[SysmlAst.UsagePrefix] = {
    return PreResultSysmlAstUsagePrefix
  }

  def preSysmlAstAttributeUsage(o: SysmlAst.AttributeUsage): PreResult[SysmlAst.AttributeUsage] = {
    return PreResultSysmlAstAttributeUsage
  }

  def preSysmlAstReferenceUsage(o: SysmlAst.ReferenceUsage): PreResult[SysmlAst.ReferenceUsage] = {
    return PreResultSysmlAstReferenceUsage
  }

  def preSysmlAstOccurrenceUsageMember(o: SysmlAst.OccurrenceUsageMember): PreResult[SysmlAst.OccurrenceUsageMember] = {
    o match {
      case o: SysmlAst.ConnectionUsage =>
        val r: PreResult[SysmlAst.OccurrenceUsageMember] = preSysmlAstConnectionUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.OccurrenceUsageMember)) => PreResult(continu, MSome[SysmlAst.OccurrenceUsageMember](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.OccurrenceUsageMember")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.OccurrenceUsageMember]())
        }
        return r
      case o: SysmlAst.ItemUsage =>
        val r: PreResult[SysmlAst.OccurrenceUsageMember] = preSysmlAstItemUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.OccurrenceUsageMember)) => PreResult(continu, MSome[SysmlAst.OccurrenceUsageMember](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.OccurrenceUsageMember")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.OccurrenceUsageMember]())
        }
        return r
      case o: SysmlAst.PartUsage =>
        val r: PreResult[SysmlAst.OccurrenceUsageMember] = preSysmlAstPartUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.OccurrenceUsageMember)) => PreResult(continu, MSome[SysmlAst.OccurrenceUsageMember](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.OccurrenceUsageMember")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.OccurrenceUsageMember]())
        }
        return r
      case o: SysmlAst.PortUsage =>
        val r: PreResult[SysmlAst.OccurrenceUsageMember] = preSysmlAstPortUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.OccurrenceUsageMember)) => PreResult(continu, MSome[SysmlAst.OccurrenceUsageMember](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.OccurrenceUsageMember")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.OccurrenceUsageMember]())
        }
        return r
    }
  }

  def preSysmlAstOccurrenceUsageElement(o: SysmlAst.OccurrenceUsageElement): PreResult[SysmlAst.OccurrenceUsageElement] = {
    o match {
      case o: SysmlAst.ConnectionUsage =>
        val r: PreResult[SysmlAst.OccurrenceUsageElement] = preSysmlAstConnectionUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.OccurrenceUsageElement)) => PreResult(continu, MSome[SysmlAst.OccurrenceUsageElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.OccurrenceUsageElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.OccurrenceUsageElement]())
        }
        return r
      case o: SysmlAst.ItemUsage =>
        val r: PreResult[SysmlAst.OccurrenceUsageElement] = preSysmlAstItemUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.OccurrenceUsageElement)) => PreResult(continu, MSome[SysmlAst.OccurrenceUsageElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.OccurrenceUsageElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.OccurrenceUsageElement]())
        }
        return r
      case o: SysmlAst.PartUsage =>
        val r: PreResult[SysmlAst.OccurrenceUsageElement] = preSysmlAstPartUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.OccurrenceUsageElement)) => PreResult(continu, MSome[SysmlAst.OccurrenceUsageElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.OccurrenceUsageElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.OccurrenceUsageElement]())
        }
        return r
      case o: SysmlAst.PortUsage =>
        val r: PreResult[SysmlAst.OccurrenceUsageElement] = preSysmlAstPortUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.OccurrenceUsageElement)) => PreResult(continu, MSome[SysmlAst.OccurrenceUsageElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.OccurrenceUsageElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.OccurrenceUsageElement]())
        }
        return r
    }
  }

  def preSysmlAstStructureUsageElement(o: SysmlAst.StructureUsageElement): PreResult[SysmlAst.StructureUsageElement] = {
    o match {
      case o: SysmlAst.ConnectionUsage =>
        val r: PreResult[SysmlAst.StructureUsageElement] = preSysmlAstConnectionUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.StructureUsageElement)) => PreResult(continu, MSome[SysmlAst.StructureUsageElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.StructureUsageElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.StructureUsageElement]())
        }
        return r
      case o: SysmlAst.ItemUsage =>
        val r: PreResult[SysmlAst.StructureUsageElement] = preSysmlAstItemUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.StructureUsageElement)) => PreResult(continu, MSome[SysmlAst.StructureUsageElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.StructureUsageElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.StructureUsageElement]())
        }
        return r
      case o: SysmlAst.PartUsage =>
        val r: PreResult[SysmlAst.StructureUsageElement] = preSysmlAstPartUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.StructureUsageElement)) => PreResult(continu, MSome[SysmlAst.StructureUsageElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.StructureUsageElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.StructureUsageElement]())
        }
        return r
      case o: SysmlAst.PortUsage =>
        val r: PreResult[SysmlAst.StructureUsageElement] = preSysmlAstPortUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.StructureUsageElement)) => PreResult(continu, MSome[SysmlAst.StructureUsageElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.StructureUsageElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.StructureUsageElement]())
        }
        return r
    }
  }

  def preSysmlAstOccurrenceUsagePrefix(o: SysmlAst.OccurrenceUsagePrefix): PreResult[SysmlAst.OccurrenceUsagePrefix] = {
    return PreResultSysmlAstOccurrenceUsagePrefix
  }

  def preSysmlAstConnectionUsage(o: SysmlAst.ConnectionUsage): PreResult[SysmlAst.ConnectionUsage] = {
    return PreResultSysmlAstConnectionUsage
  }

  def preSysmlAstItemUsage(o: SysmlAst.ItemUsage): PreResult[SysmlAst.ItemUsage] = {
    return PreResultSysmlAstItemUsage
  }

  def preSysmlAstPartUsage(o: SysmlAst.PartUsage): PreResult[SysmlAst.PartUsage] = {
    return PreResultSysmlAstPartUsage
  }

  def preSysmlAstPortUsage(o: SysmlAst.PortUsage): PreResult[SysmlAst.PortUsage] = {
    return PreResultSysmlAstPortUsage
  }

  def preSysmlAstAnnotatingElement(o: SysmlAst.AnnotatingElement): PreResult[SysmlAst.AnnotatingElement] = {
    o match {
      case o: SysmlAst.Comment =>
        val r: PreResult[SysmlAst.AnnotatingElement] = preSysmlAstComment(o) match {
         case PreResult(continu, MSome(r: SysmlAst.AnnotatingElement)) => PreResult(continu, MSome[SysmlAst.AnnotatingElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.AnnotatingElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.AnnotatingElement]())
        }
        return r
      case o: SysmlAst.Documentation =>
        val r: PreResult[SysmlAst.AnnotatingElement] = preSysmlAstDocumentation(o) match {
         case PreResult(continu, MSome(r: SysmlAst.AnnotatingElement)) => PreResult(continu, MSome[SysmlAst.AnnotatingElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.AnnotatingElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.AnnotatingElement]())
        }
        return r
      case o: SysmlAst.TextualRepresentation =>
        val r: PreResult[SysmlAst.AnnotatingElement] = preSysmlAstTextualRepresentation(o) match {
         case PreResult(continu, MSome(r: SysmlAst.AnnotatingElement)) => PreResult(continu, MSome[SysmlAst.AnnotatingElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.AnnotatingElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.AnnotatingElement]())
        }
        return r
      case o: SysmlAst.GumboAnnotation =>
        val r: PreResult[SysmlAst.AnnotatingElement] = preSysmlAstGumboAnnotation(o) match {
         case PreResult(continu, MSome(r: SysmlAst.AnnotatingElement)) => PreResult(continu, MSome[SysmlAst.AnnotatingElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.AnnotatingElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.AnnotatingElement]())
        }
        return r
    }
  }

  def preSysmlAstComment(o: SysmlAst.Comment): PreResult[SysmlAst.Comment] = {
    return PreResultSysmlAstComment
  }

  def preSysmlAstDocumentation(o: SysmlAst.Documentation): PreResult[SysmlAst.Documentation] = {
    return PreResultSysmlAstDocumentation
  }

  def preSysmlAstTextualRepresentation(o: SysmlAst.TextualRepresentation): PreResult[SysmlAst.TextualRepresentation] = {
    return PreResultSysmlAstTextualRepresentation
  }

  def preSysmlAstGumboAnnotation(o: SysmlAst.GumboAnnotation): PreResult[SysmlAst.GumboAnnotation] = {
    return PreResultSysmlAstGumboAnnotation
  }

  def preAttr(o: Attr): PreResult[Attr] = {
    return PreResultAttr
  }

  def preResolvedAttr(o: ResolvedAttr): PreResult[ResolvedAttr] = {
    return PreResultResolvedAttr
  }

  def preResolvedInfo(o: ResolvedInfo): PreResult[ResolvedInfo] = {
    o match {
      case o: ResolvedInfo.Package => return preResolvedInfoPackage(o)
      case o: ResolvedInfo.Enum => return preResolvedInfoEnum(o)
      case o: ResolvedInfo.EnumElement => return preResolvedInfoEnumElement(o)
      case o: ResolvedInfo.AttributeUsage => return preResolvedInfoAttributeUsage(o)
      case o: ResolvedInfo.ConnectionUsage => return preResolvedInfoConnectionUsage(o)
      case o: ResolvedInfo.ItemUsage => return preResolvedInfoItemUsage(o)
      case o: ResolvedInfo.PartUsage => return preResolvedInfoPartUsage(o)
      case o: ResolvedInfo.PortUsage => return preResolvedInfoPortUsage(o)
      case o: ResolvedInfo.ReferenceUsage => return preResolvedInfoReferenceUsage(o)
    }
  }

  def preResolvedInfoPackage(o: ResolvedInfo.Package): PreResult[ResolvedInfo] = {
    return PreResultResolvedInfoPackage
  }

  def preResolvedInfoEnum(o: ResolvedInfo.Enum): PreResult[ResolvedInfo] = {
    return PreResultResolvedInfoEnum
  }

  def preResolvedInfoEnumElement(o: ResolvedInfo.EnumElement): PreResult[ResolvedInfo] = {
    return PreResultResolvedInfoEnumElement
  }

  def preResolvedInfoAttributeUsage(o: ResolvedInfo.AttributeUsage): PreResult[ResolvedInfo] = {
    return PreResultResolvedInfoAttributeUsage
  }

  def preResolvedInfoConnectionUsage(o: ResolvedInfo.ConnectionUsage): PreResult[ResolvedInfo] = {
    return PreResultResolvedInfoConnectionUsage
  }

  def preResolvedInfoItemUsage(o: ResolvedInfo.ItemUsage): PreResult[ResolvedInfo] = {
    return PreResultResolvedInfoItemUsage
  }

  def preResolvedInfoPartUsage(o: ResolvedInfo.PartUsage): PreResult[ResolvedInfo] = {
    return PreResultResolvedInfoPartUsage
  }

  def preResolvedInfoPortUsage(o: ResolvedInfo.PortUsage): PreResult[ResolvedInfo] = {
    return PreResultResolvedInfoPortUsage
  }

  def preResolvedInfoReferenceUsage(o: ResolvedInfo.ReferenceUsage): PreResult[ResolvedInfo] = {
    return PreResultResolvedInfoReferenceUsage
  }

  def preType(o: Type): PreResult[Type] = {
    o match {
      case o: Type.Named => return preTypeNamed(o)
    }
  }

  def preTypeNamed(o: Type.Named): PreResult[Type] = {
    return PreResultTypeNamed
  }

  def preTypedAttr(o: TypedAttr): PreResult[TypedAttr] = {
    return PreResultTypedAttr
  }

  def preGumboASTGclSymbol(o: GumboAST.GclSymbol): PreResult[GumboAST.GclSymbol] = {
    o match {
      case o: GumboAST.GclSubclause =>
        val r: PreResult[GumboAST.GclSymbol] = preGumboASTGclSubclause(o) match {
         case PreResult(continu, MSome(r: GumboAST.GclSymbol)) => PreResult(continu, MSome[GumboAST.GclSymbol](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type GumboAST.GclSymbol")
         case PreResult(continu, _) => PreResult(continu, MNone[GumboAST.GclSymbol]())
        }
        return r
      case o: GumboAST.GclMethod =>
        val r: PreResult[GumboAST.GclSymbol] = preGumboASTGclMethod(o) match {
         case PreResult(continu, MSome(r: GumboAST.GclSymbol)) => PreResult(continu, MSome[GumboAST.GclSymbol](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type GumboAST.GclSymbol")
         case PreResult(continu, _) => PreResult(continu, MNone[GumboAST.GclSymbol]())
        }
        return r
      case o: GumboAST.GclStateVar =>
        val r: PreResult[GumboAST.GclSymbol] = preGumboASTGclStateVar(o) match {
         case PreResult(continu, MSome(r: GumboAST.GclSymbol)) => PreResult(continu, MSome[GumboAST.GclSymbol](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type GumboAST.GclSymbol")
         case PreResult(continu, _) => PreResult(continu, MNone[GumboAST.GclSymbol]())
        }
        return r
      case o: GumboAST.GclInvariant =>
        val r: PreResult[GumboAST.GclSymbol] = preGumboASTGclInvariant(o) match {
         case PreResult(continu, MSome(r: GumboAST.GclSymbol)) => PreResult(continu, MSome[GumboAST.GclSymbol](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type GumboAST.GclSymbol")
         case PreResult(continu, _) => PreResult(continu, MNone[GumboAST.GclSymbol]())
        }
        return r
      case o: GumboAST.GclAssume =>
        val r: PreResult[GumboAST.GclSymbol] = preGumboASTGclAssume(o) match {
         case PreResult(continu, MSome(r: GumboAST.GclSymbol)) => PreResult(continu, MSome[GumboAST.GclSymbol](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type GumboAST.GclSymbol")
         case PreResult(continu, _) => PreResult(continu, MNone[GumboAST.GclSymbol]())
        }
        return r
      case o: GumboAST.GclGuarantee =>
        val r: PreResult[GumboAST.GclSymbol] = preGumboASTGclGuarantee(o) match {
         case PreResult(continu, MSome(r: GumboAST.GclSymbol)) => PreResult(continu, MSome[GumboAST.GclSymbol](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type GumboAST.GclSymbol")
         case PreResult(continu, _) => PreResult(continu, MNone[GumboAST.GclSymbol]())
        }
        return r
      case o: GumboAST.GclIntegration =>
        val r: PreResult[GumboAST.GclSymbol] = preGumboASTGclIntegration(o) match {
         case PreResult(continu, MSome(r: GumboAST.GclSymbol)) => PreResult(continu, MSome[GumboAST.GclSymbol](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type GumboAST.GclSymbol")
         case PreResult(continu, _) => PreResult(continu, MNone[GumboAST.GclSymbol]())
        }
        return r
      case o: GumboAST.GclCaseStatement =>
        val r: PreResult[GumboAST.GclSymbol] = preGumboASTGclCaseStatement(o) match {
         case PreResult(continu, MSome(r: GumboAST.GclSymbol)) => PreResult(continu, MSome[GumboAST.GclSymbol](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type GumboAST.GclSymbol")
         case PreResult(continu, _) => PreResult(continu, MNone[GumboAST.GclSymbol]())
        }
        return r
      case o: GumboAST.GclInitialize =>
        val r: PreResult[GumboAST.GclSymbol] = preGumboASTGclInitialize(o) match {
         case PreResult(continu, MSome(r: GumboAST.GclSymbol)) => PreResult(continu, MSome[GumboAST.GclSymbol](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type GumboAST.GclSymbol")
         case PreResult(continu, _) => PreResult(continu, MNone[GumboAST.GclSymbol]())
        }
        return r
      case o: GumboAST.GclCompute =>
        val r: PreResult[GumboAST.GclSymbol] = preGumboASTGclCompute(o) match {
         case PreResult(continu, MSome(r: GumboAST.GclSymbol)) => PreResult(continu, MSome[GumboAST.GclSymbol](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type GumboAST.GclSymbol")
         case PreResult(continu, _) => PreResult(continu, MNone[GumboAST.GclSymbol]())
        }
        return r
      case o: GumboAST.GclHandle =>
        val r: PreResult[GumboAST.GclSymbol] = preGumboASTGclHandle(o) match {
         case PreResult(continu, MSome(r: GumboAST.GclSymbol)) => PreResult(continu, MSome[GumboAST.GclSymbol](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type GumboAST.GclSymbol")
         case PreResult(continu, _) => PreResult(continu, MNone[GumboAST.GclSymbol]())
        }
        return r
      case o: GumboAST.GclTODO =>
        val r: PreResult[GumboAST.GclSymbol] = preGumboASTGclTODO(o) match {
         case PreResult(continu, MSome(r: GumboAST.GclSymbol)) => PreResult(continu, MSome[GumboAST.GclSymbol](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type GumboAST.GclSymbol")
         case PreResult(continu, _) => PreResult(continu, MNone[GumboAST.GclSymbol]())
        }
        return r
      case o: GumboAST.GclLib =>
        val r: PreResult[GumboAST.GclSymbol] = preGumboASTGclLib(o) match {
         case PreResult(continu, MSome(r: GumboAST.GclSymbol)) => PreResult(continu, MSome[GumboAST.GclSymbol](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type GumboAST.GclSymbol")
         case PreResult(continu, _) => PreResult(continu, MNone[GumboAST.GclSymbol]())
        }
        return r
      case o: GumboAST.InfoFlowClause =>
        val r: PreResult[GumboAST.GclSymbol] = preGumboASTInfoFlowClause(o) match {
         case PreResult(continu, MSome(r: GumboAST.GclSymbol)) => PreResult(continu, MSome[GumboAST.GclSymbol](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type GumboAST.GclSymbol")
         case PreResult(continu, _) => PreResult(continu, MNone[GumboAST.GclSymbol]())
        }
        return r
    }
  }

  def preGumboASTGclSubclause(o: GumboAST.GclSubclause): PreResult[GumboAST.GclSubclause] = {
    return PreResultGumboASTGclSubclause
  }

  def preGumboASTGclMethod(o: GumboAST.GclMethod): PreResult[GumboAST.GclMethod] = {
    return PreResultGumboASTGclMethod
  }

  def preGumboASTGclStateVar(o: GumboAST.GclStateVar): PreResult[GumboAST.GclStateVar] = {
    return PreResultGumboASTGclStateVar
  }

  def preGumboASTGclClause(o: GumboAST.GclClause): PreResult[GumboAST.GclClause] = {
    o match {
      case o: GumboAST.GclInvariant =>
        val r: PreResult[GumboAST.GclClause] = preGumboASTGclInvariant(o) match {
         case PreResult(continu, MSome(r: GumboAST.GclClause)) => PreResult(continu, MSome[GumboAST.GclClause](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type GumboAST.GclClause")
         case PreResult(continu, _) => PreResult(continu, MNone[GumboAST.GclClause]())
        }
        return r
      case o: GumboAST.GclAssume =>
        val r: PreResult[GumboAST.GclClause] = preGumboASTGclAssume(o) match {
         case PreResult(continu, MSome(r: GumboAST.GclClause)) => PreResult(continu, MSome[GumboAST.GclClause](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type GumboAST.GclClause")
         case PreResult(continu, _) => PreResult(continu, MNone[GumboAST.GclClause]())
        }
        return r
      case o: GumboAST.GclGuarantee =>
        val r: PreResult[GumboAST.GclClause] = preGumboASTGclGuarantee(o) match {
         case PreResult(continu, MSome(r: GumboAST.GclClause)) => PreResult(continu, MSome[GumboAST.GclClause](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type GumboAST.GclClause")
         case PreResult(continu, _) => PreResult(continu, MNone[GumboAST.GclClause]())
        }
        return r
      case o: GumboAST.InfoFlowClause =>
        val r: PreResult[GumboAST.GclClause] = preGumboASTInfoFlowClause(o) match {
         case PreResult(continu, MSome(r: GumboAST.GclClause)) => PreResult(continu, MSome[GumboAST.GclClause](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type GumboAST.GclClause")
         case PreResult(continu, _) => PreResult(continu, MNone[GumboAST.GclClause]())
        }
        return r
    }
  }

  def preGumboASTGclSpec(o: GumboAST.GclSpec): PreResult[GumboAST.GclSpec] = {
    o match {
      case o: GumboAST.GclInvariant =>
        val r: PreResult[GumboAST.GclSpec] = preGumboASTGclInvariant(o) match {
         case PreResult(continu, MSome(r: GumboAST.GclSpec)) => PreResult(continu, MSome[GumboAST.GclSpec](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type GumboAST.GclSpec")
         case PreResult(continu, _) => PreResult(continu, MNone[GumboAST.GclSpec]())
        }
        return r
      case o: GumboAST.GclAssume =>
        val r: PreResult[GumboAST.GclSpec] = preGumboASTGclAssume(o) match {
         case PreResult(continu, MSome(r: GumboAST.GclSpec)) => PreResult(continu, MSome[GumboAST.GclSpec](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type GumboAST.GclSpec")
         case PreResult(continu, _) => PreResult(continu, MNone[GumboAST.GclSpec]())
        }
        return r
      case o: GumboAST.GclGuarantee =>
        val r: PreResult[GumboAST.GclSpec] = preGumboASTGclGuarantee(o) match {
         case PreResult(continu, MSome(r: GumboAST.GclSpec)) => PreResult(continu, MSome[GumboAST.GclSpec](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type GumboAST.GclSpec")
         case PreResult(continu, _) => PreResult(continu, MNone[GumboAST.GclSpec]())
        }
        return r
    }
  }

  def preGumboASTGclInvariant(o: GumboAST.GclInvariant): PreResult[GumboAST.GclInvariant] = {
    return PreResultGumboASTGclInvariant
  }

  def preGumboASTGclComputeSpec(o: GumboAST.GclComputeSpec): PreResult[GumboAST.GclComputeSpec] = {
    o match {
      case o: GumboAST.GclAssume =>
        val r: PreResult[GumboAST.GclComputeSpec] = preGumboASTGclAssume(o) match {
         case PreResult(continu, MSome(r: GumboAST.GclComputeSpec)) => PreResult(continu, MSome[GumboAST.GclComputeSpec](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type GumboAST.GclComputeSpec")
         case PreResult(continu, _) => PreResult(continu, MNone[GumboAST.GclComputeSpec]())
        }
        return r
      case o: GumboAST.GclGuarantee =>
        val r: PreResult[GumboAST.GclComputeSpec] = preGumboASTGclGuarantee(o) match {
         case PreResult(continu, MSome(r: GumboAST.GclComputeSpec)) => PreResult(continu, MSome[GumboAST.GclComputeSpec](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type GumboAST.GclComputeSpec")
         case PreResult(continu, _) => PreResult(continu, MNone[GumboAST.GclComputeSpec]())
        }
        return r
    }
  }

  def preGumboASTGclAssume(o: GumboAST.GclAssume): PreResult[GumboAST.GclAssume] = {
    return PreResultGumboASTGclAssume
  }

  def preGumboASTGclGuarantee(o: GumboAST.GclGuarantee): PreResult[GumboAST.GclGuarantee] = {
    return PreResultGumboASTGclGuarantee
  }

  def preGumboASTGclIntegration(o: GumboAST.GclIntegration): PreResult[GumboAST.GclIntegration] = {
    return PreResultGumboASTGclIntegration
  }

  def preGumboASTGclCaseStatement(o: GumboAST.GclCaseStatement): PreResult[GumboAST.GclCaseStatement] = {
    return PreResultGumboASTGclCaseStatement
  }

  def preGumboASTGclInitialize(o: GumboAST.GclInitialize): PreResult[GumboAST.GclInitialize] = {
    return PreResultGumboASTGclInitialize
  }

  def preGumboASTGclCompute(o: GumboAST.GclCompute): PreResult[GumboAST.GclCompute] = {
    return PreResultGumboASTGclCompute
  }

  def preGumboASTGclHandle(o: GumboAST.GclHandle): PreResult[GumboAST.GclHandle] = {
    return PreResultGumboASTGclHandle
  }

  def preGumboASTGclTODO(o: GumboAST.GclTODO): PreResult[GumboAST.GclTODO] = {
    return PreResultGumboASTGclTODO
  }

  def preGumboASTGclLib(o: GumboAST.GclLib): PreResult[GumboAST.GclLib] = {
    return PreResultGumboASTGclLib
  }

  def preGumboASTInfoFlowClause(o: GumboAST.InfoFlowClause): PreResult[GumboAST.InfoFlowClause] = {
    return PreResultGumboASTInfoFlowClause
  }

  def postSysmlAstId(o: SysmlAst.Id): MOption[SysmlAst.Id] = {
    return PostResultSysmlAstId
  }

  def postSysmlAstName(o: SysmlAst.Name): MOption[SysmlAst.Name] = {
    return PostResultSysmlAstName
  }

  def postSysmlAstTopUnit(o: SysmlAst.TopUnit): MOption[SysmlAst.TopUnit] = {
    return PostResultSysmlAstTopUnit
  }

  def postSysmlAstAttrNode(o: SysmlAst.AttrNode): MOption[SysmlAst.AttrNode] = {
    o match {
      case o: SysmlAst.Import =>
        val r: MOption[SysmlAst.AttrNode] = postSysmlAstImport(o) match {
         case MSome(result: SysmlAst.AttrNode) => MSome[SysmlAst.AttrNode](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.AttrNode")
         case _ => MNone[SysmlAst.AttrNode]()
        }
        return r
      case o: SysmlAst.AliasMember =>
        val r: MOption[SysmlAst.AttrNode] = postSysmlAstAliasMember(o) match {
         case MSome(result: SysmlAst.AttrNode) => MSome[SysmlAst.AttrNode](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.AttrNode")
         case _ => MNone[SysmlAst.AttrNode]()
        }
        return r
      case o: SysmlAst.Identification =>
        val r: MOption[SysmlAst.AttrNode] = postSysmlAstIdentification(o) match {
         case MSome(result: SysmlAst.AttrNode) => MSome[SysmlAst.AttrNode](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.AttrNode")
         case _ => MNone[SysmlAst.AttrNode]()
        }
        return r
      case o: SysmlAst.Package =>
        val r: MOption[SysmlAst.AttrNode] = postSysmlAstPackage(o) match {
         case MSome(result: SysmlAst.AttrNode) => MSome[SysmlAst.AttrNode](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.AttrNode")
         case _ => MNone[SysmlAst.AttrNode]()
        }
        return r
      case o: SysmlAst.AttributeDefinition =>
        val r: MOption[SysmlAst.AttrNode] = postSysmlAstAttributeDefinition(o) match {
         case MSome(result: SysmlAst.AttrNode) => MSome[SysmlAst.AttrNode](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.AttrNode")
         case _ => MNone[SysmlAst.AttrNode]()
        }
        return r
      case o: SysmlAst.AllocationDefinition =>
        val r: MOption[SysmlAst.AttrNode] = postSysmlAstAllocationDefinition(o) match {
         case MSome(result: SysmlAst.AttrNode) => MSome[SysmlAst.AttrNode](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.AttrNode")
         case _ => MNone[SysmlAst.AttrNode]()
        }
        return r
      case o: SysmlAst.ConnectionDefinition =>
        val r: MOption[SysmlAst.AttrNode] = postSysmlAstConnectionDefinition(o) match {
         case MSome(result: SysmlAst.AttrNode) => MSome[SysmlAst.AttrNode](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.AttrNode")
         case _ => MNone[SysmlAst.AttrNode]()
        }
        return r
      case o: SysmlAst.EnumerationDefinition =>
        val r: MOption[SysmlAst.AttrNode] = postSysmlAstEnumerationDefinition(o) match {
         case MSome(result: SysmlAst.AttrNode) => MSome[SysmlAst.AttrNode](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.AttrNode")
         case _ => MNone[SysmlAst.AttrNode]()
        }
        return r
      case o: SysmlAst.PartDefinition =>
        val r: MOption[SysmlAst.AttrNode] = postSysmlAstPartDefinition(o) match {
         case MSome(result: SysmlAst.AttrNode) => MSome[SysmlAst.AttrNode](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.AttrNode")
         case _ => MNone[SysmlAst.AttrNode]()
        }
        return r
      case o: SysmlAst.PortDefinition =>
        val r: MOption[SysmlAst.AttrNode] = postSysmlAstPortDefinition(o) match {
         case MSome(result: SysmlAst.AttrNode) => MSome[SysmlAst.AttrNode](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.AttrNode")
         case _ => MNone[SysmlAst.AttrNode]()
        }
        return r
      case o: SysmlAst.MetadataDefinition =>
        val r: MOption[SysmlAst.AttrNode] = postSysmlAstMetadataDefinition(o) match {
         case MSome(result: SysmlAst.AttrNode) => MSome[SysmlAst.AttrNode](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.AttrNode")
         case _ => MNone[SysmlAst.AttrNode]()
        }
        return r
      case o: SysmlAst.AttributeUsage =>
        val r: MOption[SysmlAst.AttrNode] = postSysmlAstAttributeUsage(o) match {
         case MSome(result: SysmlAst.AttrNode) => MSome[SysmlAst.AttrNode](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.AttrNode")
         case _ => MNone[SysmlAst.AttrNode]()
        }
        return r
      case o: SysmlAst.ReferenceUsage =>
        val r: MOption[SysmlAst.AttrNode] = postSysmlAstReferenceUsage(o) match {
         case MSome(result: SysmlAst.AttrNode) => MSome[SysmlAst.AttrNode](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.AttrNode")
         case _ => MNone[SysmlAst.AttrNode]()
        }
        return r
      case o: SysmlAst.ConnectionUsage =>
        val r: MOption[SysmlAst.AttrNode] = postSysmlAstConnectionUsage(o) match {
         case MSome(result: SysmlAst.AttrNode) => MSome[SysmlAst.AttrNode](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.AttrNode")
         case _ => MNone[SysmlAst.AttrNode]()
        }
        return r
      case o: SysmlAst.ItemUsage =>
        val r: MOption[SysmlAst.AttrNode] = postSysmlAstItemUsage(o) match {
         case MSome(result: SysmlAst.AttrNode) => MSome[SysmlAst.AttrNode](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.AttrNode")
         case _ => MNone[SysmlAst.AttrNode]()
        }
        return r
      case o: SysmlAst.PartUsage =>
        val r: MOption[SysmlAst.AttrNode] = postSysmlAstPartUsage(o) match {
         case MSome(result: SysmlAst.AttrNode) => MSome[SysmlAst.AttrNode](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.AttrNode")
         case _ => MNone[SysmlAst.AttrNode]()
        }
        return r
      case o: SysmlAst.PortUsage =>
        val r: MOption[SysmlAst.AttrNode] = postSysmlAstPortUsage(o) match {
         case MSome(result: SysmlAst.AttrNode) => MSome[SysmlAst.AttrNode](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.AttrNode")
         case _ => MNone[SysmlAst.AttrNode]()
        }
        return r
      case o: SysmlAst.Comment =>
        val r: MOption[SysmlAst.AttrNode] = postSysmlAstComment(o) match {
         case MSome(result: SysmlAst.AttrNode) => MSome[SysmlAst.AttrNode](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.AttrNode")
         case _ => MNone[SysmlAst.AttrNode]()
        }
        return r
      case o: SysmlAst.Documentation =>
        val r: MOption[SysmlAst.AttrNode] = postSysmlAstDocumentation(o) match {
         case MSome(result: SysmlAst.AttrNode) => MSome[SysmlAst.AttrNode](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.AttrNode")
         case _ => MNone[SysmlAst.AttrNode]()
        }
        return r
      case o: SysmlAst.TextualRepresentation =>
        val r: MOption[SysmlAst.AttrNode] = postSysmlAstTextualRepresentation(o) match {
         case MSome(result: SysmlAst.AttrNode) => MSome[SysmlAst.AttrNode](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.AttrNode")
         case _ => MNone[SysmlAst.AttrNode]()
        }
        return r
      case o: SysmlAst.GumboAnnotation =>
        val r: MOption[SysmlAst.AttrNode] = postSysmlAstGumboAnnotation(o) match {
         case MSome(result: SysmlAst.AttrNode) => MSome[SysmlAst.AttrNode](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.AttrNode")
         case _ => MNone[SysmlAst.AttrNode]()
        }
        return r
      case o: GumboAST.GclSubclause =>
        val r: MOption[SysmlAst.AttrNode] = postGumboASTGclSubclause(o) match {
         case MSome(result: SysmlAst.AttrNode) => MSome[SysmlAst.AttrNode](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.AttrNode")
         case _ => MNone[SysmlAst.AttrNode]()
        }
        return r
      case o: GumboAST.GclMethod =>
        val r: MOption[SysmlAst.AttrNode] = postGumboASTGclMethod(o) match {
         case MSome(result: SysmlAst.AttrNode) => MSome[SysmlAst.AttrNode](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.AttrNode")
         case _ => MNone[SysmlAst.AttrNode]()
        }
        return r
      case o: GumboAST.GclStateVar =>
        val r: MOption[SysmlAst.AttrNode] = postGumboASTGclStateVar(o) match {
         case MSome(result: SysmlAst.AttrNode) => MSome[SysmlAst.AttrNode](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.AttrNode")
         case _ => MNone[SysmlAst.AttrNode]()
        }
        return r
      case o: GumboAST.GclInvariant =>
        val r: MOption[SysmlAst.AttrNode] = postGumboASTGclInvariant(o) match {
         case MSome(result: SysmlAst.AttrNode) => MSome[SysmlAst.AttrNode](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.AttrNode")
         case _ => MNone[SysmlAst.AttrNode]()
        }
        return r
      case o: GumboAST.GclAssume =>
        val r: MOption[SysmlAst.AttrNode] = postGumboASTGclAssume(o) match {
         case MSome(result: SysmlAst.AttrNode) => MSome[SysmlAst.AttrNode](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.AttrNode")
         case _ => MNone[SysmlAst.AttrNode]()
        }
        return r
      case o: GumboAST.GclGuarantee =>
        val r: MOption[SysmlAst.AttrNode] = postGumboASTGclGuarantee(o) match {
         case MSome(result: SysmlAst.AttrNode) => MSome[SysmlAst.AttrNode](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.AttrNode")
         case _ => MNone[SysmlAst.AttrNode]()
        }
        return r
      case o: GumboAST.GclIntegration =>
        val r: MOption[SysmlAst.AttrNode] = postGumboASTGclIntegration(o) match {
         case MSome(result: SysmlAst.AttrNode) => MSome[SysmlAst.AttrNode](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.AttrNode")
         case _ => MNone[SysmlAst.AttrNode]()
        }
        return r
      case o: GumboAST.GclCaseStatement =>
        val r: MOption[SysmlAst.AttrNode] = postGumboASTGclCaseStatement(o) match {
         case MSome(result: SysmlAst.AttrNode) => MSome[SysmlAst.AttrNode](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.AttrNode")
         case _ => MNone[SysmlAst.AttrNode]()
        }
        return r
      case o: GumboAST.GclInitialize =>
        val r: MOption[SysmlAst.AttrNode] = postGumboASTGclInitialize(o) match {
         case MSome(result: SysmlAst.AttrNode) => MSome[SysmlAst.AttrNode](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.AttrNode")
         case _ => MNone[SysmlAst.AttrNode]()
        }
        return r
      case o: GumboAST.GclCompute =>
        val r: MOption[SysmlAst.AttrNode] = postGumboASTGclCompute(o) match {
         case MSome(result: SysmlAst.AttrNode) => MSome[SysmlAst.AttrNode](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.AttrNode")
         case _ => MNone[SysmlAst.AttrNode]()
        }
        return r
      case o: GumboAST.GclHandle =>
        val r: MOption[SysmlAst.AttrNode] = postGumboASTGclHandle(o) match {
         case MSome(result: SysmlAst.AttrNode) => MSome[SysmlAst.AttrNode](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.AttrNode")
         case _ => MNone[SysmlAst.AttrNode]()
        }
        return r
      case o: GumboAST.GclTODO =>
        val r: MOption[SysmlAst.AttrNode] = postGumboASTGclTODO(o) match {
         case MSome(result: SysmlAst.AttrNode) => MSome[SysmlAst.AttrNode](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.AttrNode")
         case _ => MNone[SysmlAst.AttrNode]()
        }
        return r
      case o: GumboAST.GclLib =>
        val r: MOption[SysmlAst.AttrNode] = postGumboASTGclLib(o) match {
         case MSome(result: SysmlAst.AttrNode) => MSome[SysmlAst.AttrNode](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.AttrNode")
         case _ => MNone[SysmlAst.AttrNode]()
        }
        return r
      case o: GumboAST.InfoFlowClause =>
        val r: MOption[SysmlAst.AttrNode] = postGumboASTInfoFlowClause(o) match {
         case MSome(result: SysmlAst.AttrNode) => MSome[SysmlAst.AttrNode](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.AttrNode")
         case _ => MNone[SysmlAst.AttrNode]()
        }
        return r
    }
  }

  def postSysmlAstPackageBodyElement(o: SysmlAst.PackageBodyElement): MOption[SysmlAst.PackageBodyElement] = {
    o match {
      case o: SysmlAst.Import =>
        val r: MOption[SysmlAst.PackageBodyElement] = postSysmlAstImport(o) match {
         case MSome(result: SysmlAst.PackageBodyElement) => MSome[SysmlAst.PackageBodyElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
         case _ => MNone[SysmlAst.PackageBodyElement]()
        }
        return r
      case o: SysmlAst.AliasMember =>
        val r: MOption[SysmlAst.PackageBodyElement] = postSysmlAstAliasMember(o) match {
         case MSome(result: SysmlAst.PackageBodyElement) => MSome[SysmlAst.PackageBodyElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
         case _ => MNone[SysmlAst.PackageBodyElement]()
        }
        return r
      case o: SysmlAst.Package =>
        val r: MOption[SysmlAst.PackageBodyElement] = postSysmlAstPackage(o) match {
         case MSome(result: SysmlAst.PackageBodyElement) => MSome[SysmlAst.PackageBodyElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
         case _ => MNone[SysmlAst.PackageBodyElement]()
        }
        return r
      case o: SysmlAst.AttributeDefinition =>
        val r: MOption[SysmlAst.PackageBodyElement] = postSysmlAstAttributeDefinition(o) match {
         case MSome(result: SysmlAst.PackageBodyElement) => MSome[SysmlAst.PackageBodyElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
         case _ => MNone[SysmlAst.PackageBodyElement]()
        }
        return r
      case o: SysmlAst.AllocationDefinition =>
        val r: MOption[SysmlAst.PackageBodyElement] = postSysmlAstAllocationDefinition(o) match {
         case MSome(result: SysmlAst.PackageBodyElement) => MSome[SysmlAst.PackageBodyElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
         case _ => MNone[SysmlAst.PackageBodyElement]()
        }
        return r
      case o: SysmlAst.ConnectionDefinition =>
        val r: MOption[SysmlAst.PackageBodyElement] = postSysmlAstConnectionDefinition(o) match {
         case MSome(result: SysmlAst.PackageBodyElement) => MSome[SysmlAst.PackageBodyElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
         case _ => MNone[SysmlAst.PackageBodyElement]()
        }
        return r
      case o: SysmlAst.EnumerationDefinition =>
        val r: MOption[SysmlAst.PackageBodyElement] = postSysmlAstEnumerationDefinition(o) match {
         case MSome(result: SysmlAst.PackageBodyElement) => MSome[SysmlAst.PackageBodyElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
         case _ => MNone[SysmlAst.PackageBodyElement]()
        }
        return r
      case o: SysmlAst.PartDefinition =>
        val r: MOption[SysmlAst.PackageBodyElement] = postSysmlAstPartDefinition(o) match {
         case MSome(result: SysmlAst.PackageBodyElement) => MSome[SysmlAst.PackageBodyElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
         case _ => MNone[SysmlAst.PackageBodyElement]()
        }
        return r
      case o: SysmlAst.PortDefinition =>
        val r: MOption[SysmlAst.PackageBodyElement] = postSysmlAstPortDefinition(o) match {
         case MSome(result: SysmlAst.PackageBodyElement) => MSome[SysmlAst.PackageBodyElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
         case _ => MNone[SysmlAst.PackageBodyElement]()
        }
        return r
      case o: SysmlAst.MetadataDefinition =>
        val r: MOption[SysmlAst.PackageBodyElement] = postSysmlAstMetadataDefinition(o) match {
         case MSome(result: SysmlAst.PackageBodyElement) => MSome[SysmlAst.PackageBodyElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
         case _ => MNone[SysmlAst.PackageBodyElement]()
        }
        return r
      case o: SysmlAst.AttributeUsage =>
        val r: MOption[SysmlAst.PackageBodyElement] = postSysmlAstAttributeUsage(o) match {
         case MSome(result: SysmlAst.PackageBodyElement) => MSome[SysmlAst.PackageBodyElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
         case _ => MNone[SysmlAst.PackageBodyElement]()
        }
        return r
      case o: SysmlAst.ReferenceUsage =>
        val r: MOption[SysmlAst.PackageBodyElement] = postSysmlAstReferenceUsage(o) match {
         case MSome(result: SysmlAst.PackageBodyElement) => MSome[SysmlAst.PackageBodyElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
         case _ => MNone[SysmlAst.PackageBodyElement]()
        }
        return r
      case o: SysmlAst.ConnectionUsage =>
        val r: MOption[SysmlAst.PackageBodyElement] = postSysmlAstConnectionUsage(o) match {
         case MSome(result: SysmlAst.PackageBodyElement) => MSome[SysmlAst.PackageBodyElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
         case _ => MNone[SysmlAst.PackageBodyElement]()
        }
        return r
      case o: SysmlAst.ItemUsage =>
        val r: MOption[SysmlAst.PackageBodyElement] = postSysmlAstItemUsage(o) match {
         case MSome(result: SysmlAst.PackageBodyElement) => MSome[SysmlAst.PackageBodyElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
         case _ => MNone[SysmlAst.PackageBodyElement]()
        }
        return r
      case o: SysmlAst.PartUsage =>
        val r: MOption[SysmlAst.PackageBodyElement] = postSysmlAstPartUsage(o) match {
         case MSome(result: SysmlAst.PackageBodyElement) => MSome[SysmlAst.PackageBodyElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
         case _ => MNone[SysmlAst.PackageBodyElement]()
        }
        return r
      case o: SysmlAst.PortUsage =>
        val r: MOption[SysmlAst.PackageBodyElement] = postSysmlAstPortUsage(o) match {
         case MSome(result: SysmlAst.PackageBodyElement) => MSome[SysmlAst.PackageBodyElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
         case _ => MNone[SysmlAst.PackageBodyElement]()
        }
        return r
      case o: SysmlAst.Comment =>
        val r: MOption[SysmlAst.PackageBodyElement] = postSysmlAstComment(o) match {
         case MSome(result: SysmlAst.PackageBodyElement) => MSome[SysmlAst.PackageBodyElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
         case _ => MNone[SysmlAst.PackageBodyElement]()
        }
        return r
      case o: SysmlAst.Documentation =>
        val r: MOption[SysmlAst.PackageBodyElement] = postSysmlAstDocumentation(o) match {
         case MSome(result: SysmlAst.PackageBodyElement) => MSome[SysmlAst.PackageBodyElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
         case _ => MNone[SysmlAst.PackageBodyElement]()
        }
        return r
      case o: SysmlAst.TextualRepresentation =>
        val r: MOption[SysmlAst.PackageBodyElement] = postSysmlAstTextualRepresentation(o) match {
         case MSome(result: SysmlAst.PackageBodyElement) => MSome[SysmlAst.PackageBodyElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
         case _ => MNone[SysmlAst.PackageBodyElement]()
        }
        return r
      case o: SysmlAst.GumboAnnotation =>
        val r: MOption[SysmlAst.PackageBodyElement] = postSysmlAstGumboAnnotation(o) match {
         case MSome(result: SysmlAst.PackageBodyElement) => MSome[SysmlAst.PackageBodyElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.PackageBodyElement")
         case _ => MNone[SysmlAst.PackageBodyElement]()
        }
        return r
    }
  }

  def postSysmlAstDefinitionBodyItem(o: SysmlAst.DefinitionBodyItem): MOption[SysmlAst.DefinitionBodyItem] = {
    o match {
      case o: SysmlAst.Import =>
        val r: MOption[SysmlAst.DefinitionBodyItem] = postSysmlAstImport(o) match {
         case MSome(result: SysmlAst.DefinitionBodyItem) => MSome[SysmlAst.DefinitionBodyItem](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.DefinitionBodyItem")
         case _ => MNone[SysmlAst.DefinitionBodyItem]()
        }
        return r
      case o: SysmlAst.AliasMember =>
        val r: MOption[SysmlAst.DefinitionBodyItem] = postSysmlAstAliasMember(o) match {
         case MSome(result: SysmlAst.DefinitionBodyItem) => MSome[SysmlAst.DefinitionBodyItem](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.DefinitionBodyItem")
         case _ => MNone[SysmlAst.DefinitionBodyItem]()
        }
        return r
      case o: SysmlAst.Package =>
        val r: MOption[SysmlAst.DefinitionBodyItem] = postSysmlAstPackage(o) match {
         case MSome(result: SysmlAst.DefinitionBodyItem) => MSome[SysmlAst.DefinitionBodyItem](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.DefinitionBodyItem")
         case _ => MNone[SysmlAst.DefinitionBodyItem]()
        }
        return r
      case o: SysmlAst.AttributeDefinition =>
        val r: MOption[SysmlAst.DefinitionBodyItem] = postSysmlAstAttributeDefinition(o) match {
         case MSome(result: SysmlAst.DefinitionBodyItem) => MSome[SysmlAst.DefinitionBodyItem](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.DefinitionBodyItem")
         case _ => MNone[SysmlAst.DefinitionBodyItem]()
        }
        return r
      case o: SysmlAst.AllocationDefinition =>
        val r: MOption[SysmlAst.DefinitionBodyItem] = postSysmlAstAllocationDefinition(o) match {
         case MSome(result: SysmlAst.DefinitionBodyItem) => MSome[SysmlAst.DefinitionBodyItem](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.DefinitionBodyItem")
         case _ => MNone[SysmlAst.DefinitionBodyItem]()
        }
        return r
      case o: SysmlAst.ConnectionDefinition =>
        val r: MOption[SysmlAst.DefinitionBodyItem] = postSysmlAstConnectionDefinition(o) match {
         case MSome(result: SysmlAst.DefinitionBodyItem) => MSome[SysmlAst.DefinitionBodyItem](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.DefinitionBodyItem")
         case _ => MNone[SysmlAst.DefinitionBodyItem]()
        }
        return r
      case o: SysmlAst.EnumerationDefinition =>
        val r: MOption[SysmlAst.DefinitionBodyItem] = postSysmlAstEnumerationDefinition(o) match {
         case MSome(result: SysmlAst.DefinitionBodyItem) => MSome[SysmlAst.DefinitionBodyItem](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.DefinitionBodyItem")
         case _ => MNone[SysmlAst.DefinitionBodyItem]()
        }
        return r
      case o: SysmlAst.PartDefinition =>
        val r: MOption[SysmlAst.DefinitionBodyItem] = postSysmlAstPartDefinition(o) match {
         case MSome(result: SysmlAst.DefinitionBodyItem) => MSome[SysmlAst.DefinitionBodyItem](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.DefinitionBodyItem")
         case _ => MNone[SysmlAst.DefinitionBodyItem]()
        }
        return r
      case o: SysmlAst.PortDefinition =>
        val r: MOption[SysmlAst.DefinitionBodyItem] = postSysmlAstPortDefinition(o) match {
         case MSome(result: SysmlAst.DefinitionBodyItem) => MSome[SysmlAst.DefinitionBodyItem](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.DefinitionBodyItem")
         case _ => MNone[SysmlAst.DefinitionBodyItem]()
        }
        return r
      case o: SysmlAst.MetadataDefinition =>
        val r: MOption[SysmlAst.DefinitionBodyItem] = postSysmlAstMetadataDefinition(o) match {
         case MSome(result: SysmlAst.DefinitionBodyItem) => MSome[SysmlAst.DefinitionBodyItem](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.DefinitionBodyItem")
         case _ => MNone[SysmlAst.DefinitionBodyItem]()
        }
        return r
      case o: SysmlAst.AttributeUsage =>
        val r: MOption[SysmlAst.DefinitionBodyItem] = postSysmlAstAttributeUsage(o) match {
         case MSome(result: SysmlAst.DefinitionBodyItem) => MSome[SysmlAst.DefinitionBodyItem](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.DefinitionBodyItem")
         case _ => MNone[SysmlAst.DefinitionBodyItem]()
        }
        return r
      case o: SysmlAst.ReferenceUsage =>
        val r: MOption[SysmlAst.DefinitionBodyItem] = postSysmlAstReferenceUsage(o) match {
         case MSome(result: SysmlAst.DefinitionBodyItem) => MSome[SysmlAst.DefinitionBodyItem](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.DefinitionBodyItem")
         case _ => MNone[SysmlAst.DefinitionBodyItem]()
        }
        return r
      case o: SysmlAst.ConnectionUsage =>
        val r: MOption[SysmlAst.DefinitionBodyItem] = postSysmlAstConnectionUsage(o) match {
         case MSome(result: SysmlAst.DefinitionBodyItem) => MSome[SysmlAst.DefinitionBodyItem](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.DefinitionBodyItem")
         case _ => MNone[SysmlAst.DefinitionBodyItem]()
        }
        return r
      case o: SysmlAst.ItemUsage =>
        val r: MOption[SysmlAst.DefinitionBodyItem] = postSysmlAstItemUsage(o) match {
         case MSome(result: SysmlAst.DefinitionBodyItem) => MSome[SysmlAst.DefinitionBodyItem](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.DefinitionBodyItem")
         case _ => MNone[SysmlAst.DefinitionBodyItem]()
        }
        return r
      case o: SysmlAst.PartUsage =>
        val r: MOption[SysmlAst.DefinitionBodyItem] = postSysmlAstPartUsage(o) match {
         case MSome(result: SysmlAst.DefinitionBodyItem) => MSome[SysmlAst.DefinitionBodyItem](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.DefinitionBodyItem")
         case _ => MNone[SysmlAst.DefinitionBodyItem]()
        }
        return r
      case o: SysmlAst.PortUsage =>
        val r: MOption[SysmlAst.DefinitionBodyItem] = postSysmlAstPortUsage(o) match {
         case MSome(result: SysmlAst.DefinitionBodyItem) => MSome[SysmlAst.DefinitionBodyItem](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.DefinitionBodyItem")
         case _ => MNone[SysmlAst.DefinitionBodyItem]()
        }
        return r
      case o: SysmlAst.Comment =>
        val r: MOption[SysmlAst.DefinitionBodyItem] = postSysmlAstComment(o) match {
         case MSome(result: SysmlAst.DefinitionBodyItem) => MSome[SysmlAst.DefinitionBodyItem](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.DefinitionBodyItem")
         case _ => MNone[SysmlAst.DefinitionBodyItem]()
        }
        return r
      case o: SysmlAst.Documentation =>
        val r: MOption[SysmlAst.DefinitionBodyItem] = postSysmlAstDocumentation(o) match {
         case MSome(result: SysmlAst.DefinitionBodyItem) => MSome[SysmlAst.DefinitionBodyItem](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.DefinitionBodyItem")
         case _ => MNone[SysmlAst.DefinitionBodyItem]()
        }
        return r
      case o: SysmlAst.TextualRepresentation =>
        val r: MOption[SysmlAst.DefinitionBodyItem] = postSysmlAstTextualRepresentation(o) match {
         case MSome(result: SysmlAst.DefinitionBodyItem) => MSome[SysmlAst.DefinitionBodyItem](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.DefinitionBodyItem")
         case _ => MNone[SysmlAst.DefinitionBodyItem]()
        }
        return r
      case o: SysmlAst.GumboAnnotation =>
        val r: MOption[SysmlAst.DefinitionBodyItem] = postSysmlAstGumboAnnotation(o) match {
         case MSome(result: SysmlAst.DefinitionBodyItem) => MSome[SysmlAst.DefinitionBodyItem](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.DefinitionBodyItem")
         case _ => MNone[SysmlAst.DefinitionBodyItem]()
        }
        return r
    }
  }

  def postSysmlAstFeatureValue(o: SysmlAst.FeatureValue): MOption[SysmlAst.FeatureValue] = {
    return PostResultSysmlAstFeatureValue
  }

  def postSysmlAstEnumeratedValue(o: SysmlAst.EnumeratedValue): MOption[SysmlAst.EnumeratedValue] = {
    return PostResultSysmlAstEnumeratedValue
  }

  def postSysmlAstImport(o: SysmlAst.Import): MOption[SysmlAst.Import] = {
    return PostResultSysmlAstImport
  }

  def postSysmlAstAliasMember(o: SysmlAst.AliasMember): MOption[SysmlAst.AliasMember] = {
    return PostResultSysmlAstAliasMember
  }

  def postSysmlAstIdentification(o: SysmlAst.Identification): MOption[SysmlAst.Identification] = {
    return PostResultSysmlAstIdentification
  }

  def postSysmlAstPackageMember(o: SysmlAst.PackageMember): MOption[SysmlAst.PackageMember] = {
    o match {
      case o: SysmlAst.Package =>
        val r: MOption[SysmlAst.PackageMember] = postSysmlAstPackage(o) match {
         case MSome(result: SysmlAst.PackageMember) => MSome[SysmlAst.PackageMember](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.PackageMember")
         case _ => MNone[SysmlAst.PackageMember]()
        }
        return r
      case o: SysmlAst.AttributeDefinition =>
        val r: MOption[SysmlAst.PackageMember] = postSysmlAstAttributeDefinition(o) match {
         case MSome(result: SysmlAst.PackageMember) => MSome[SysmlAst.PackageMember](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.PackageMember")
         case _ => MNone[SysmlAst.PackageMember]()
        }
        return r
      case o: SysmlAst.AllocationDefinition =>
        val r: MOption[SysmlAst.PackageMember] = postSysmlAstAllocationDefinition(o) match {
         case MSome(result: SysmlAst.PackageMember) => MSome[SysmlAst.PackageMember](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.PackageMember")
         case _ => MNone[SysmlAst.PackageMember]()
        }
        return r
      case o: SysmlAst.ConnectionDefinition =>
        val r: MOption[SysmlAst.PackageMember] = postSysmlAstConnectionDefinition(o) match {
         case MSome(result: SysmlAst.PackageMember) => MSome[SysmlAst.PackageMember](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.PackageMember")
         case _ => MNone[SysmlAst.PackageMember]()
        }
        return r
      case o: SysmlAst.EnumerationDefinition =>
        val r: MOption[SysmlAst.PackageMember] = postSysmlAstEnumerationDefinition(o) match {
         case MSome(result: SysmlAst.PackageMember) => MSome[SysmlAst.PackageMember](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.PackageMember")
         case _ => MNone[SysmlAst.PackageMember]()
        }
        return r
      case o: SysmlAst.PartDefinition =>
        val r: MOption[SysmlAst.PackageMember] = postSysmlAstPartDefinition(o) match {
         case MSome(result: SysmlAst.PackageMember) => MSome[SysmlAst.PackageMember](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.PackageMember")
         case _ => MNone[SysmlAst.PackageMember]()
        }
        return r
      case o: SysmlAst.PortDefinition =>
        val r: MOption[SysmlAst.PackageMember] = postSysmlAstPortDefinition(o) match {
         case MSome(result: SysmlAst.PackageMember) => MSome[SysmlAst.PackageMember](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.PackageMember")
         case _ => MNone[SysmlAst.PackageMember]()
        }
        return r
      case o: SysmlAst.MetadataDefinition =>
        val r: MOption[SysmlAst.PackageMember] = postSysmlAstMetadataDefinition(o) match {
         case MSome(result: SysmlAst.PackageMember) => MSome[SysmlAst.PackageMember](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.PackageMember")
         case _ => MNone[SysmlAst.PackageMember]()
        }
        return r
      case o: SysmlAst.AttributeUsage =>
        val r: MOption[SysmlAst.PackageMember] = postSysmlAstAttributeUsage(o) match {
         case MSome(result: SysmlAst.PackageMember) => MSome[SysmlAst.PackageMember](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.PackageMember")
         case _ => MNone[SysmlAst.PackageMember]()
        }
        return r
      case o: SysmlAst.ReferenceUsage =>
        val r: MOption[SysmlAst.PackageMember] = postSysmlAstReferenceUsage(o) match {
         case MSome(result: SysmlAst.PackageMember) => MSome[SysmlAst.PackageMember](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.PackageMember")
         case _ => MNone[SysmlAst.PackageMember]()
        }
        return r
      case o: SysmlAst.ConnectionUsage =>
        val r: MOption[SysmlAst.PackageMember] = postSysmlAstConnectionUsage(o) match {
         case MSome(result: SysmlAst.PackageMember) => MSome[SysmlAst.PackageMember](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.PackageMember")
         case _ => MNone[SysmlAst.PackageMember]()
        }
        return r
      case o: SysmlAst.ItemUsage =>
        val r: MOption[SysmlAst.PackageMember] = postSysmlAstItemUsage(o) match {
         case MSome(result: SysmlAst.PackageMember) => MSome[SysmlAst.PackageMember](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.PackageMember")
         case _ => MNone[SysmlAst.PackageMember]()
        }
        return r
      case o: SysmlAst.PartUsage =>
        val r: MOption[SysmlAst.PackageMember] = postSysmlAstPartUsage(o) match {
         case MSome(result: SysmlAst.PackageMember) => MSome[SysmlAst.PackageMember](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.PackageMember")
         case _ => MNone[SysmlAst.PackageMember]()
        }
        return r
      case o: SysmlAst.PortUsage =>
        val r: MOption[SysmlAst.PackageMember] = postSysmlAstPortUsage(o) match {
         case MSome(result: SysmlAst.PackageMember) => MSome[SysmlAst.PackageMember](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.PackageMember")
         case _ => MNone[SysmlAst.PackageMember]()
        }
        return r
      case o: SysmlAst.Comment =>
        val r: MOption[SysmlAst.PackageMember] = postSysmlAstComment(o) match {
         case MSome(result: SysmlAst.PackageMember) => MSome[SysmlAst.PackageMember](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.PackageMember")
         case _ => MNone[SysmlAst.PackageMember]()
        }
        return r
      case o: SysmlAst.Documentation =>
        val r: MOption[SysmlAst.PackageMember] = postSysmlAstDocumentation(o) match {
         case MSome(result: SysmlAst.PackageMember) => MSome[SysmlAst.PackageMember](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.PackageMember")
         case _ => MNone[SysmlAst.PackageMember]()
        }
        return r
      case o: SysmlAst.TextualRepresentation =>
        val r: MOption[SysmlAst.PackageMember] = postSysmlAstTextualRepresentation(o) match {
         case MSome(result: SysmlAst.PackageMember) => MSome[SysmlAst.PackageMember](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.PackageMember")
         case _ => MNone[SysmlAst.PackageMember]()
        }
        return r
      case o: SysmlAst.GumboAnnotation =>
        val r: MOption[SysmlAst.PackageMember] = postSysmlAstGumboAnnotation(o) match {
         case MSome(result: SysmlAst.PackageMember) => MSome[SysmlAst.PackageMember](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.PackageMember")
         case _ => MNone[SysmlAst.PackageMember]()
        }
        return r
    }
  }

  def postSysmlAstConnectorPart(o: SysmlAst.ConnectorPart): MOption[SysmlAst.ConnectorPart] = {
    o match {
      case o: SysmlAst.BinaryConnectorPart =>
        val r: MOption[SysmlAst.ConnectorPart] = postSysmlAstBinaryConnectorPart(o) match {
         case MSome(result: SysmlAst.ConnectorPart) => MSome[SysmlAst.ConnectorPart](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.ConnectorPart")
         case _ => MNone[SysmlAst.ConnectorPart]()
        }
        return r
      case o: SysmlAst.NaryConnectorPart =>
        val r: MOption[SysmlAst.ConnectorPart] = postSysmlAstNaryConnectorPart(o) match {
         case MSome(result: SysmlAst.ConnectorPart) => MSome[SysmlAst.ConnectorPart](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.ConnectorPart")
         case _ => MNone[SysmlAst.ConnectorPart]()
        }
        return r
    }
  }

  def postSysmlAstConnectorEnd(o: SysmlAst.ConnectorEnd): MOption[SysmlAst.ConnectorEnd] = {
    return PostResultSysmlAstConnectorEnd
  }

  def postSysmlAstBinaryConnectorPart(o: SysmlAst.BinaryConnectorPart): MOption[SysmlAst.BinaryConnectorPart] = {
    return PostResultSysmlAstBinaryConnectorPart
  }

  def postSysmlAstNaryConnectorPart(o: SysmlAst.NaryConnectorPart): MOption[SysmlAst.NaryConnectorPart] = {
    return PostResultSysmlAstNaryConnectorPart
  }

  def postSysmlAstFeatureSpecialization(o: SysmlAst.FeatureSpecialization): MOption[SysmlAst.FeatureSpecialization] = {
    o match {
      case o: SysmlAst.TypingsSpecialization =>
        val r: MOption[SysmlAst.FeatureSpecialization] = postSysmlAstTypingsSpecialization(o) match {
         case MSome(result: SysmlAst.FeatureSpecialization) => MSome[SysmlAst.FeatureSpecialization](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.FeatureSpecialization")
         case _ => MNone[SysmlAst.FeatureSpecialization]()
        }
        return r
      case o: SysmlAst.SubsettingsSpecialization =>
        val r: MOption[SysmlAst.FeatureSpecialization] = postSysmlAstSubsettingsSpecialization(o) match {
         case MSome(result: SysmlAst.FeatureSpecialization) => MSome[SysmlAst.FeatureSpecialization](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.FeatureSpecialization")
         case _ => MNone[SysmlAst.FeatureSpecialization]()
        }
        return r
      case o: SysmlAst.ReferencesSpecialization =>
        val r: MOption[SysmlAst.FeatureSpecialization] = postSysmlAstReferencesSpecialization(o) match {
         case MSome(result: SysmlAst.FeatureSpecialization) => MSome[SysmlAst.FeatureSpecialization](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.FeatureSpecialization")
         case _ => MNone[SysmlAst.FeatureSpecialization]()
        }
        return r
      case o: SysmlAst.RedefinitionsSpecialization =>
        val r: MOption[SysmlAst.FeatureSpecialization] = postSysmlAstRedefinitionsSpecialization(o) match {
         case MSome(result: SysmlAst.FeatureSpecialization) => MSome[SysmlAst.FeatureSpecialization](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.FeatureSpecialization")
         case _ => MNone[SysmlAst.FeatureSpecialization]()
        }
        return r
    }
  }

  def postSysmlAstTypingsSpecialization(o: SysmlAst.TypingsSpecialization): MOption[SysmlAst.TypingsSpecialization] = {
    return PostResultSysmlAstTypingsSpecialization
  }

  def postSysmlAstSubsettingsSpecialization(o: SysmlAst.SubsettingsSpecialization): MOption[SysmlAst.SubsettingsSpecialization] = {
    return PostResultSysmlAstSubsettingsSpecialization
  }

  def postSysmlAstReferencesSpecialization(o: SysmlAst.ReferencesSpecialization): MOption[SysmlAst.ReferencesSpecialization] = {
    return PostResultSysmlAstReferencesSpecialization
  }

  def postSysmlAstRedefinitionsSpecialization(o: SysmlAst.RedefinitionsSpecialization): MOption[SysmlAst.RedefinitionsSpecialization] = {
    return PostResultSysmlAstRedefinitionsSpecialization
  }

  def postSysmlAstDefinitionMember(o: SysmlAst.DefinitionMember): MOption[SysmlAst.DefinitionMember] = {
    o match {
      case o: SysmlAst.Package =>
        val r: MOption[SysmlAst.DefinitionMember] = postSysmlAstPackage(o) match {
         case MSome(result: SysmlAst.DefinitionMember) => MSome[SysmlAst.DefinitionMember](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.DefinitionMember")
         case _ => MNone[SysmlAst.DefinitionMember]()
        }
        return r
      case o: SysmlAst.AttributeDefinition =>
        val r: MOption[SysmlAst.DefinitionMember] = postSysmlAstAttributeDefinition(o) match {
         case MSome(result: SysmlAst.DefinitionMember) => MSome[SysmlAst.DefinitionMember](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.DefinitionMember")
         case _ => MNone[SysmlAst.DefinitionMember]()
        }
        return r
      case o: SysmlAst.AllocationDefinition =>
        val r: MOption[SysmlAst.DefinitionMember] = postSysmlAstAllocationDefinition(o) match {
         case MSome(result: SysmlAst.DefinitionMember) => MSome[SysmlAst.DefinitionMember](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.DefinitionMember")
         case _ => MNone[SysmlAst.DefinitionMember]()
        }
        return r
      case o: SysmlAst.ConnectionDefinition =>
        val r: MOption[SysmlAst.DefinitionMember] = postSysmlAstConnectionDefinition(o) match {
         case MSome(result: SysmlAst.DefinitionMember) => MSome[SysmlAst.DefinitionMember](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.DefinitionMember")
         case _ => MNone[SysmlAst.DefinitionMember]()
        }
        return r
      case o: SysmlAst.EnumerationDefinition =>
        val r: MOption[SysmlAst.DefinitionMember] = postSysmlAstEnumerationDefinition(o) match {
         case MSome(result: SysmlAst.DefinitionMember) => MSome[SysmlAst.DefinitionMember](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.DefinitionMember")
         case _ => MNone[SysmlAst.DefinitionMember]()
        }
        return r
      case o: SysmlAst.PartDefinition =>
        val r: MOption[SysmlAst.DefinitionMember] = postSysmlAstPartDefinition(o) match {
         case MSome(result: SysmlAst.DefinitionMember) => MSome[SysmlAst.DefinitionMember](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.DefinitionMember")
         case _ => MNone[SysmlAst.DefinitionMember]()
        }
        return r
      case o: SysmlAst.PortDefinition =>
        val r: MOption[SysmlAst.DefinitionMember] = postSysmlAstPortDefinition(o) match {
         case MSome(result: SysmlAst.DefinitionMember) => MSome[SysmlAst.DefinitionMember](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.DefinitionMember")
         case _ => MNone[SysmlAst.DefinitionMember]()
        }
        return r
      case o: SysmlAst.MetadataDefinition =>
        val r: MOption[SysmlAst.DefinitionMember] = postSysmlAstMetadataDefinition(o) match {
         case MSome(result: SysmlAst.DefinitionMember) => MSome[SysmlAst.DefinitionMember](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.DefinitionMember")
         case _ => MNone[SysmlAst.DefinitionMember]()
        }
        return r
      case o: SysmlAst.Comment =>
        val r: MOption[SysmlAst.DefinitionMember] = postSysmlAstComment(o) match {
         case MSome(result: SysmlAst.DefinitionMember) => MSome[SysmlAst.DefinitionMember](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.DefinitionMember")
         case _ => MNone[SysmlAst.DefinitionMember]()
        }
        return r
      case o: SysmlAst.Documentation =>
        val r: MOption[SysmlAst.DefinitionMember] = postSysmlAstDocumentation(o) match {
         case MSome(result: SysmlAst.DefinitionMember) => MSome[SysmlAst.DefinitionMember](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.DefinitionMember")
         case _ => MNone[SysmlAst.DefinitionMember]()
        }
        return r
      case o: SysmlAst.TextualRepresentation =>
        val r: MOption[SysmlAst.DefinitionMember] = postSysmlAstTextualRepresentation(o) match {
         case MSome(result: SysmlAst.DefinitionMember) => MSome[SysmlAst.DefinitionMember](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.DefinitionMember")
         case _ => MNone[SysmlAst.DefinitionMember]()
        }
        return r
      case o: SysmlAst.GumboAnnotation =>
        val r: MOption[SysmlAst.DefinitionMember] = postSysmlAstGumboAnnotation(o) match {
         case MSome(result: SysmlAst.DefinitionMember) => MSome[SysmlAst.DefinitionMember](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.DefinitionMember")
         case _ => MNone[SysmlAst.DefinitionMember]()
        }
        return r
    }
  }

  def postSysmlAstDefinitionElement(o: SysmlAst.DefinitionElement): MOption[SysmlAst.DefinitionElement] = {
    o match {
      case o: SysmlAst.Package =>
        val r: MOption[SysmlAst.DefinitionElement] = postSysmlAstPackage(o) match {
         case MSome(result: SysmlAst.DefinitionElement) => MSome[SysmlAst.DefinitionElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.DefinitionElement")
         case _ => MNone[SysmlAst.DefinitionElement]()
        }
        return r
      case o: SysmlAst.AttributeDefinition =>
        val r: MOption[SysmlAst.DefinitionElement] = postSysmlAstAttributeDefinition(o) match {
         case MSome(result: SysmlAst.DefinitionElement) => MSome[SysmlAst.DefinitionElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.DefinitionElement")
         case _ => MNone[SysmlAst.DefinitionElement]()
        }
        return r
      case o: SysmlAst.AllocationDefinition =>
        val r: MOption[SysmlAst.DefinitionElement] = postSysmlAstAllocationDefinition(o) match {
         case MSome(result: SysmlAst.DefinitionElement) => MSome[SysmlAst.DefinitionElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.DefinitionElement")
         case _ => MNone[SysmlAst.DefinitionElement]()
        }
        return r
      case o: SysmlAst.ConnectionDefinition =>
        val r: MOption[SysmlAst.DefinitionElement] = postSysmlAstConnectionDefinition(o) match {
         case MSome(result: SysmlAst.DefinitionElement) => MSome[SysmlAst.DefinitionElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.DefinitionElement")
         case _ => MNone[SysmlAst.DefinitionElement]()
        }
        return r
      case o: SysmlAst.EnumerationDefinition =>
        val r: MOption[SysmlAst.DefinitionElement] = postSysmlAstEnumerationDefinition(o) match {
         case MSome(result: SysmlAst.DefinitionElement) => MSome[SysmlAst.DefinitionElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.DefinitionElement")
         case _ => MNone[SysmlAst.DefinitionElement]()
        }
        return r
      case o: SysmlAst.PartDefinition =>
        val r: MOption[SysmlAst.DefinitionElement] = postSysmlAstPartDefinition(o) match {
         case MSome(result: SysmlAst.DefinitionElement) => MSome[SysmlAst.DefinitionElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.DefinitionElement")
         case _ => MNone[SysmlAst.DefinitionElement]()
        }
        return r
      case o: SysmlAst.PortDefinition =>
        val r: MOption[SysmlAst.DefinitionElement] = postSysmlAstPortDefinition(o) match {
         case MSome(result: SysmlAst.DefinitionElement) => MSome[SysmlAst.DefinitionElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.DefinitionElement")
         case _ => MNone[SysmlAst.DefinitionElement]()
        }
        return r
      case o: SysmlAst.MetadataDefinition =>
        val r: MOption[SysmlAst.DefinitionElement] = postSysmlAstMetadataDefinition(o) match {
         case MSome(result: SysmlAst.DefinitionElement) => MSome[SysmlAst.DefinitionElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.DefinitionElement")
         case _ => MNone[SysmlAst.DefinitionElement]()
        }
        return r
      case o: SysmlAst.Comment =>
        val r: MOption[SysmlAst.DefinitionElement] = postSysmlAstComment(o) match {
         case MSome(result: SysmlAst.DefinitionElement) => MSome[SysmlAst.DefinitionElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.DefinitionElement")
         case _ => MNone[SysmlAst.DefinitionElement]()
        }
        return r
      case o: SysmlAst.Documentation =>
        val r: MOption[SysmlAst.DefinitionElement] = postSysmlAstDocumentation(o) match {
         case MSome(result: SysmlAst.DefinitionElement) => MSome[SysmlAst.DefinitionElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.DefinitionElement")
         case _ => MNone[SysmlAst.DefinitionElement]()
        }
        return r
      case o: SysmlAst.TextualRepresentation =>
        val r: MOption[SysmlAst.DefinitionElement] = postSysmlAstTextualRepresentation(o) match {
         case MSome(result: SysmlAst.DefinitionElement) => MSome[SysmlAst.DefinitionElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.DefinitionElement")
         case _ => MNone[SysmlAst.DefinitionElement]()
        }
        return r
      case o: SysmlAst.GumboAnnotation =>
        val r: MOption[SysmlAst.DefinitionElement] = postSysmlAstGumboAnnotation(o) match {
         case MSome(result: SysmlAst.DefinitionElement) => MSome[SysmlAst.DefinitionElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.DefinitionElement")
         case _ => MNone[SysmlAst.DefinitionElement]()
        }
        return r
    }
  }

  def postSysmlAstDefinitionPrefix(o: SysmlAst.DefinitionPrefix): MOption[SysmlAst.DefinitionPrefix] = {
    return PostResultSysmlAstDefinitionPrefix
  }

  def postSysmlAstPackage(o: SysmlAst.Package): MOption[SysmlAst.Package] = {
    return PostResultSysmlAstPackage
  }

  def postSysmlAstAttributeDefinition(o: SysmlAst.AttributeDefinition): MOption[SysmlAst.AttributeDefinition] = {
    return PostResultSysmlAstAttributeDefinition
  }

  def postSysmlAstOccurrenceDefinitionPrefix(o: SysmlAst.OccurrenceDefinitionPrefix): MOption[SysmlAst.OccurrenceDefinitionPrefix] = {
    return PostResultSysmlAstOccurrenceDefinitionPrefix
  }

  def postSysmlAstAllocationDefinition(o: SysmlAst.AllocationDefinition): MOption[SysmlAst.AllocationDefinition] = {
    return PostResultSysmlAstAllocationDefinition
  }

  def postSysmlAstConnectionDefinition(o: SysmlAst.ConnectionDefinition): MOption[SysmlAst.ConnectionDefinition] = {
    return PostResultSysmlAstConnectionDefinition
  }

  def postSysmlAstEnumerationDefinition(o: SysmlAst.EnumerationDefinition): MOption[SysmlAst.EnumerationDefinition] = {
    return PostResultSysmlAstEnumerationDefinition
  }

  def postSysmlAstPartDefinition(o: SysmlAst.PartDefinition): MOption[SysmlAst.PartDefinition] = {
    return PostResultSysmlAstPartDefinition
  }

  def postSysmlAstPortDefinition(o: SysmlAst.PortDefinition): MOption[SysmlAst.PortDefinition] = {
    return PostResultSysmlAstPortDefinition
  }

  def postSysmlAstMetadataDefinition(o: SysmlAst.MetadataDefinition): MOption[SysmlAst.MetadataDefinition] = {
    return PostResultSysmlAstMetadataDefinition
  }

  def postSysmlAstUsageElement(o: SysmlAst.UsageElement): MOption[SysmlAst.UsageElement] = {
    o match {
      case o: SysmlAst.AttributeUsage =>
        val r: MOption[SysmlAst.UsageElement] = postSysmlAstAttributeUsage(o) match {
         case MSome(result: SysmlAst.UsageElement) => MSome[SysmlAst.UsageElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.UsageElement")
         case _ => MNone[SysmlAst.UsageElement]()
        }
        return r
      case o: SysmlAst.ReferenceUsage =>
        val r: MOption[SysmlAst.UsageElement] = postSysmlAstReferenceUsage(o) match {
         case MSome(result: SysmlAst.UsageElement) => MSome[SysmlAst.UsageElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.UsageElement")
         case _ => MNone[SysmlAst.UsageElement]()
        }
        return r
      case o: SysmlAst.ConnectionUsage =>
        val r: MOption[SysmlAst.UsageElement] = postSysmlAstConnectionUsage(o) match {
         case MSome(result: SysmlAst.UsageElement) => MSome[SysmlAst.UsageElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.UsageElement")
         case _ => MNone[SysmlAst.UsageElement]()
        }
        return r
      case o: SysmlAst.ItemUsage =>
        val r: MOption[SysmlAst.UsageElement] = postSysmlAstItemUsage(o) match {
         case MSome(result: SysmlAst.UsageElement) => MSome[SysmlAst.UsageElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.UsageElement")
         case _ => MNone[SysmlAst.UsageElement]()
        }
        return r
      case o: SysmlAst.PartUsage =>
        val r: MOption[SysmlAst.UsageElement] = postSysmlAstPartUsage(o) match {
         case MSome(result: SysmlAst.UsageElement) => MSome[SysmlAst.UsageElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.UsageElement")
         case _ => MNone[SysmlAst.UsageElement]()
        }
        return r
      case o: SysmlAst.PortUsage =>
        val r: MOption[SysmlAst.UsageElement] = postSysmlAstPortUsage(o) match {
         case MSome(result: SysmlAst.UsageElement) => MSome[SysmlAst.UsageElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.UsageElement")
         case _ => MNone[SysmlAst.UsageElement]()
        }
        return r
    }
  }

  def postSysmlAstNonOccurrenceUsageMember(o: SysmlAst.NonOccurrenceUsageMember): MOption[SysmlAst.NonOccurrenceUsageMember] = {
    o match {
      case o: SysmlAst.AttributeUsage =>
        val r: MOption[SysmlAst.NonOccurrenceUsageMember] = postSysmlAstAttributeUsage(o) match {
         case MSome(result: SysmlAst.NonOccurrenceUsageMember) => MSome[SysmlAst.NonOccurrenceUsageMember](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.NonOccurrenceUsageMember")
         case _ => MNone[SysmlAst.NonOccurrenceUsageMember]()
        }
        return r
      case o: SysmlAst.ReferenceUsage =>
        val r: MOption[SysmlAst.NonOccurrenceUsageMember] = postSysmlAstReferenceUsage(o) match {
         case MSome(result: SysmlAst.NonOccurrenceUsageMember) => MSome[SysmlAst.NonOccurrenceUsageMember](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.NonOccurrenceUsageMember")
         case _ => MNone[SysmlAst.NonOccurrenceUsageMember]()
        }
        return r
    }
  }

  def postSysmlAstNonOccurrenceUsageElement(o: SysmlAst.NonOccurrenceUsageElement): MOption[SysmlAst.NonOccurrenceUsageElement] = {
    o match {
      case o: SysmlAst.AttributeUsage =>
        val r: MOption[SysmlAst.NonOccurrenceUsageElement] = postSysmlAstAttributeUsage(o) match {
         case MSome(result: SysmlAst.NonOccurrenceUsageElement) => MSome[SysmlAst.NonOccurrenceUsageElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.NonOccurrenceUsageElement")
         case _ => MNone[SysmlAst.NonOccurrenceUsageElement]()
        }
        return r
      case o: SysmlAst.ReferenceUsage =>
        val r: MOption[SysmlAst.NonOccurrenceUsageElement] = postSysmlAstReferenceUsage(o) match {
         case MSome(result: SysmlAst.NonOccurrenceUsageElement) => MSome[SysmlAst.NonOccurrenceUsageElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.NonOccurrenceUsageElement")
         case _ => MNone[SysmlAst.NonOccurrenceUsageElement]()
        }
        return r
    }
  }

  def postSysmlAstRefPrefix(o: SysmlAst.RefPrefix): MOption[SysmlAst.RefPrefix] = {
    return PostResultSysmlAstRefPrefix
  }

  def postSysmlAstUsagePrefix(o: SysmlAst.UsagePrefix): MOption[SysmlAst.UsagePrefix] = {
    return PostResultSysmlAstUsagePrefix
  }

  def postSysmlAstAttributeUsage(o: SysmlAst.AttributeUsage): MOption[SysmlAst.AttributeUsage] = {
    return PostResultSysmlAstAttributeUsage
  }

  def postSysmlAstReferenceUsage(o: SysmlAst.ReferenceUsage): MOption[SysmlAst.ReferenceUsage] = {
    return PostResultSysmlAstReferenceUsage
  }

  def postSysmlAstOccurrenceUsageMember(o: SysmlAst.OccurrenceUsageMember): MOption[SysmlAst.OccurrenceUsageMember] = {
    o match {
      case o: SysmlAst.ConnectionUsage =>
        val r: MOption[SysmlAst.OccurrenceUsageMember] = postSysmlAstConnectionUsage(o) match {
         case MSome(result: SysmlAst.OccurrenceUsageMember) => MSome[SysmlAst.OccurrenceUsageMember](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.OccurrenceUsageMember")
         case _ => MNone[SysmlAst.OccurrenceUsageMember]()
        }
        return r
      case o: SysmlAst.ItemUsage =>
        val r: MOption[SysmlAst.OccurrenceUsageMember] = postSysmlAstItemUsage(o) match {
         case MSome(result: SysmlAst.OccurrenceUsageMember) => MSome[SysmlAst.OccurrenceUsageMember](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.OccurrenceUsageMember")
         case _ => MNone[SysmlAst.OccurrenceUsageMember]()
        }
        return r
      case o: SysmlAst.PartUsage =>
        val r: MOption[SysmlAst.OccurrenceUsageMember] = postSysmlAstPartUsage(o) match {
         case MSome(result: SysmlAst.OccurrenceUsageMember) => MSome[SysmlAst.OccurrenceUsageMember](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.OccurrenceUsageMember")
         case _ => MNone[SysmlAst.OccurrenceUsageMember]()
        }
        return r
      case o: SysmlAst.PortUsage =>
        val r: MOption[SysmlAst.OccurrenceUsageMember] = postSysmlAstPortUsage(o) match {
         case MSome(result: SysmlAst.OccurrenceUsageMember) => MSome[SysmlAst.OccurrenceUsageMember](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.OccurrenceUsageMember")
         case _ => MNone[SysmlAst.OccurrenceUsageMember]()
        }
        return r
    }
  }

  def postSysmlAstOccurrenceUsageElement(o: SysmlAst.OccurrenceUsageElement): MOption[SysmlAst.OccurrenceUsageElement] = {
    o match {
      case o: SysmlAst.ConnectionUsage =>
        val r: MOption[SysmlAst.OccurrenceUsageElement] = postSysmlAstConnectionUsage(o) match {
         case MSome(result: SysmlAst.OccurrenceUsageElement) => MSome[SysmlAst.OccurrenceUsageElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.OccurrenceUsageElement")
         case _ => MNone[SysmlAst.OccurrenceUsageElement]()
        }
        return r
      case o: SysmlAst.ItemUsage =>
        val r: MOption[SysmlAst.OccurrenceUsageElement] = postSysmlAstItemUsage(o) match {
         case MSome(result: SysmlAst.OccurrenceUsageElement) => MSome[SysmlAst.OccurrenceUsageElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.OccurrenceUsageElement")
         case _ => MNone[SysmlAst.OccurrenceUsageElement]()
        }
        return r
      case o: SysmlAst.PartUsage =>
        val r: MOption[SysmlAst.OccurrenceUsageElement] = postSysmlAstPartUsage(o) match {
         case MSome(result: SysmlAst.OccurrenceUsageElement) => MSome[SysmlAst.OccurrenceUsageElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.OccurrenceUsageElement")
         case _ => MNone[SysmlAst.OccurrenceUsageElement]()
        }
        return r
      case o: SysmlAst.PortUsage =>
        val r: MOption[SysmlAst.OccurrenceUsageElement] = postSysmlAstPortUsage(o) match {
         case MSome(result: SysmlAst.OccurrenceUsageElement) => MSome[SysmlAst.OccurrenceUsageElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.OccurrenceUsageElement")
         case _ => MNone[SysmlAst.OccurrenceUsageElement]()
        }
        return r
    }
  }

  def postSysmlAstStructureUsageElement(o: SysmlAst.StructureUsageElement): MOption[SysmlAst.StructureUsageElement] = {
    o match {
      case o: SysmlAst.ConnectionUsage =>
        val r: MOption[SysmlAst.StructureUsageElement] = postSysmlAstConnectionUsage(o) match {
         case MSome(result: SysmlAst.StructureUsageElement) => MSome[SysmlAst.StructureUsageElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.StructureUsageElement")
         case _ => MNone[SysmlAst.StructureUsageElement]()
        }
        return r
      case o: SysmlAst.ItemUsage =>
        val r: MOption[SysmlAst.StructureUsageElement] = postSysmlAstItemUsage(o) match {
         case MSome(result: SysmlAst.StructureUsageElement) => MSome[SysmlAst.StructureUsageElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.StructureUsageElement")
         case _ => MNone[SysmlAst.StructureUsageElement]()
        }
        return r
      case o: SysmlAst.PartUsage =>
        val r: MOption[SysmlAst.StructureUsageElement] = postSysmlAstPartUsage(o) match {
         case MSome(result: SysmlAst.StructureUsageElement) => MSome[SysmlAst.StructureUsageElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.StructureUsageElement")
         case _ => MNone[SysmlAst.StructureUsageElement]()
        }
        return r
      case o: SysmlAst.PortUsage =>
        val r: MOption[SysmlAst.StructureUsageElement] = postSysmlAstPortUsage(o) match {
         case MSome(result: SysmlAst.StructureUsageElement) => MSome[SysmlAst.StructureUsageElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.StructureUsageElement")
         case _ => MNone[SysmlAst.StructureUsageElement]()
        }
        return r
    }
  }

  def postSysmlAstOccurrenceUsagePrefix(o: SysmlAst.OccurrenceUsagePrefix): MOption[SysmlAst.OccurrenceUsagePrefix] = {
    return PostResultSysmlAstOccurrenceUsagePrefix
  }

  def postSysmlAstConnectionUsage(o: SysmlAst.ConnectionUsage): MOption[SysmlAst.ConnectionUsage] = {
    return PostResultSysmlAstConnectionUsage
  }

  def postSysmlAstItemUsage(o: SysmlAst.ItemUsage): MOption[SysmlAst.ItemUsage] = {
    return PostResultSysmlAstItemUsage
  }

  def postSysmlAstPartUsage(o: SysmlAst.PartUsage): MOption[SysmlAst.PartUsage] = {
    return PostResultSysmlAstPartUsage
  }

  def postSysmlAstPortUsage(o: SysmlAst.PortUsage): MOption[SysmlAst.PortUsage] = {
    return PostResultSysmlAstPortUsage
  }

  def postSysmlAstAnnotatingElement(o: SysmlAst.AnnotatingElement): MOption[SysmlAst.AnnotatingElement] = {
    o match {
      case o: SysmlAst.Comment =>
        val r: MOption[SysmlAst.AnnotatingElement] = postSysmlAstComment(o) match {
         case MSome(result: SysmlAst.AnnotatingElement) => MSome[SysmlAst.AnnotatingElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.AnnotatingElement")
         case _ => MNone[SysmlAst.AnnotatingElement]()
        }
        return r
      case o: SysmlAst.Documentation =>
        val r: MOption[SysmlAst.AnnotatingElement] = postSysmlAstDocumentation(o) match {
         case MSome(result: SysmlAst.AnnotatingElement) => MSome[SysmlAst.AnnotatingElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.AnnotatingElement")
         case _ => MNone[SysmlAst.AnnotatingElement]()
        }
        return r
      case o: SysmlAst.TextualRepresentation =>
        val r: MOption[SysmlAst.AnnotatingElement] = postSysmlAstTextualRepresentation(o) match {
         case MSome(result: SysmlAst.AnnotatingElement) => MSome[SysmlAst.AnnotatingElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.AnnotatingElement")
         case _ => MNone[SysmlAst.AnnotatingElement]()
        }
        return r
      case o: SysmlAst.GumboAnnotation =>
        val r: MOption[SysmlAst.AnnotatingElement] = postSysmlAstGumboAnnotation(o) match {
         case MSome(result: SysmlAst.AnnotatingElement) => MSome[SysmlAst.AnnotatingElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.AnnotatingElement")
         case _ => MNone[SysmlAst.AnnotatingElement]()
        }
        return r
    }
  }

  def postSysmlAstComment(o: SysmlAst.Comment): MOption[SysmlAst.Comment] = {
    return PostResultSysmlAstComment
  }

  def postSysmlAstDocumentation(o: SysmlAst.Documentation): MOption[SysmlAst.Documentation] = {
    return PostResultSysmlAstDocumentation
  }

  def postSysmlAstTextualRepresentation(o: SysmlAst.TextualRepresentation): MOption[SysmlAst.TextualRepresentation] = {
    return PostResultSysmlAstTextualRepresentation
  }

  def postSysmlAstGumboAnnotation(o: SysmlAst.GumboAnnotation): MOption[SysmlAst.GumboAnnotation] = {
    return PostResultSysmlAstGumboAnnotation
  }

  def postAttr(o: Attr): MOption[Attr] = {
    return PostResultAttr
  }

  def postResolvedAttr(o: ResolvedAttr): MOption[ResolvedAttr] = {
    return PostResultResolvedAttr
  }

  def postResolvedInfo(o: ResolvedInfo): MOption[ResolvedInfo] = {
    o match {
      case o: ResolvedInfo.Package => return postResolvedInfoPackage(o)
      case o: ResolvedInfo.Enum => return postResolvedInfoEnum(o)
      case o: ResolvedInfo.EnumElement => return postResolvedInfoEnumElement(o)
      case o: ResolvedInfo.AttributeUsage => return postResolvedInfoAttributeUsage(o)
      case o: ResolvedInfo.ConnectionUsage => return postResolvedInfoConnectionUsage(o)
      case o: ResolvedInfo.ItemUsage => return postResolvedInfoItemUsage(o)
      case o: ResolvedInfo.PartUsage => return postResolvedInfoPartUsage(o)
      case o: ResolvedInfo.PortUsage => return postResolvedInfoPortUsage(o)
      case o: ResolvedInfo.ReferenceUsage => return postResolvedInfoReferenceUsage(o)
    }
  }

  def postResolvedInfoPackage(o: ResolvedInfo.Package): MOption[ResolvedInfo] = {
    return PostResultResolvedInfoPackage
  }

  def postResolvedInfoEnum(o: ResolvedInfo.Enum): MOption[ResolvedInfo] = {
    return PostResultResolvedInfoEnum
  }

  def postResolvedInfoEnumElement(o: ResolvedInfo.EnumElement): MOption[ResolvedInfo] = {
    return PostResultResolvedInfoEnumElement
  }

  def postResolvedInfoAttributeUsage(o: ResolvedInfo.AttributeUsage): MOption[ResolvedInfo] = {
    return PostResultResolvedInfoAttributeUsage
  }

  def postResolvedInfoConnectionUsage(o: ResolvedInfo.ConnectionUsage): MOption[ResolvedInfo] = {
    return PostResultResolvedInfoConnectionUsage
  }

  def postResolvedInfoItemUsage(o: ResolvedInfo.ItemUsage): MOption[ResolvedInfo] = {
    return PostResultResolvedInfoItemUsage
  }

  def postResolvedInfoPartUsage(o: ResolvedInfo.PartUsage): MOption[ResolvedInfo] = {
    return PostResultResolvedInfoPartUsage
  }

  def postResolvedInfoPortUsage(o: ResolvedInfo.PortUsage): MOption[ResolvedInfo] = {
    return PostResultResolvedInfoPortUsage
  }

  def postResolvedInfoReferenceUsage(o: ResolvedInfo.ReferenceUsage): MOption[ResolvedInfo] = {
    return PostResultResolvedInfoReferenceUsage
  }

  def postType(o: Type): MOption[Type] = {
    o match {
      case o: Type.Named => return postTypeNamed(o)
    }
  }

  def postTypeNamed(o: Type.Named): MOption[Type] = {
    return PostResultTypeNamed
  }

  def postTypedAttr(o: TypedAttr): MOption[TypedAttr] = {
    return PostResultTypedAttr
  }

  def postGumboASTGclSymbol(o: GumboAST.GclSymbol): MOption[GumboAST.GclSymbol] = {
    o match {
      case o: GumboAST.GclSubclause =>
        val r: MOption[GumboAST.GclSymbol] = postGumboASTGclSubclause(o) match {
         case MSome(result: GumboAST.GclSymbol) => MSome[GumboAST.GclSymbol](result)
         case MSome(_) => halt("Can only produce object of type GumboAST.GclSymbol")
         case _ => MNone[GumboAST.GclSymbol]()
        }
        return r
      case o: GumboAST.GclMethod =>
        val r: MOption[GumboAST.GclSymbol] = postGumboASTGclMethod(o) match {
         case MSome(result: GumboAST.GclSymbol) => MSome[GumboAST.GclSymbol](result)
         case MSome(_) => halt("Can only produce object of type GumboAST.GclSymbol")
         case _ => MNone[GumboAST.GclSymbol]()
        }
        return r
      case o: GumboAST.GclStateVar =>
        val r: MOption[GumboAST.GclSymbol] = postGumboASTGclStateVar(o) match {
         case MSome(result: GumboAST.GclSymbol) => MSome[GumboAST.GclSymbol](result)
         case MSome(_) => halt("Can only produce object of type GumboAST.GclSymbol")
         case _ => MNone[GumboAST.GclSymbol]()
        }
        return r
      case o: GumboAST.GclInvariant =>
        val r: MOption[GumboAST.GclSymbol] = postGumboASTGclInvariant(o) match {
         case MSome(result: GumboAST.GclSymbol) => MSome[GumboAST.GclSymbol](result)
         case MSome(_) => halt("Can only produce object of type GumboAST.GclSymbol")
         case _ => MNone[GumboAST.GclSymbol]()
        }
        return r
      case o: GumboAST.GclAssume =>
        val r: MOption[GumboAST.GclSymbol] = postGumboASTGclAssume(o) match {
         case MSome(result: GumboAST.GclSymbol) => MSome[GumboAST.GclSymbol](result)
         case MSome(_) => halt("Can only produce object of type GumboAST.GclSymbol")
         case _ => MNone[GumboAST.GclSymbol]()
        }
        return r
      case o: GumboAST.GclGuarantee =>
        val r: MOption[GumboAST.GclSymbol] = postGumboASTGclGuarantee(o) match {
         case MSome(result: GumboAST.GclSymbol) => MSome[GumboAST.GclSymbol](result)
         case MSome(_) => halt("Can only produce object of type GumboAST.GclSymbol")
         case _ => MNone[GumboAST.GclSymbol]()
        }
        return r
      case o: GumboAST.GclIntegration =>
        val r: MOption[GumboAST.GclSymbol] = postGumboASTGclIntegration(o) match {
         case MSome(result: GumboAST.GclSymbol) => MSome[GumboAST.GclSymbol](result)
         case MSome(_) => halt("Can only produce object of type GumboAST.GclSymbol")
         case _ => MNone[GumboAST.GclSymbol]()
        }
        return r
      case o: GumboAST.GclCaseStatement =>
        val r: MOption[GumboAST.GclSymbol] = postGumboASTGclCaseStatement(o) match {
         case MSome(result: GumboAST.GclSymbol) => MSome[GumboAST.GclSymbol](result)
         case MSome(_) => halt("Can only produce object of type GumboAST.GclSymbol")
         case _ => MNone[GumboAST.GclSymbol]()
        }
        return r
      case o: GumboAST.GclInitialize =>
        val r: MOption[GumboAST.GclSymbol] = postGumboASTGclInitialize(o) match {
         case MSome(result: GumboAST.GclSymbol) => MSome[GumboAST.GclSymbol](result)
         case MSome(_) => halt("Can only produce object of type GumboAST.GclSymbol")
         case _ => MNone[GumboAST.GclSymbol]()
        }
        return r
      case o: GumboAST.GclCompute =>
        val r: MOption[GumboAST.GclSymbol] = postGumboASTGclCompute(o) match {
         case MSome(result: GumboAST.GclSymbol) => MSome[GumboAST.GclSymbol](result)
         case MSome(_) => halt("Can only produce object of type GumboAST.GclSymbol")
         case _ => MNone[GumboAST.GclSymbol]()
        }
        return r
      case o: GumboAST.GclHandle =>
        val r: MOption[GumboAST.GclSymbol] = postGumboASTGclHandle(o) match {
         case MSome(result: GumboAST.GclSymbol) => MSome[GumboAST.GclSymbol](result)
         case MSome(_) => halt("Can only produce object of type GumboAST.GclSymbol")
         case _ => MNone[GumboAST.GclSymbol]()
        }
        return r
      case o: GumboAST.GclTODO =>
        val r: MOption[GumboAST.GclSymbol] = postGumboASTGclTODO(o) match {
         case MSome(result: GumboAST.GclSymbol) => MSome[GumboAST.GclSymbol](result)
         case MSome(_) => halt("Can only produce object of type GumboAST.GclSymbol")
         case _ => MNone[GumboAST.GclSymbol]()
        }
        return r
      case o: GumboAST.GclLib =>
        val r: MOption[GumboAST.GclSymbol] = postGumboASTGclLib(o) match {
         case MSome(result: GumboAST.GclSymbol) => MSome[GumboAST.GclSymbol](result)
         case MSome(_) => halt("Can only produce object of type GumboAST.GclSymbol")
         case _ => MNone[GumboAST.GclSymbol]()
        }
        return r
      case o: GumboAST.InfoFlowClause =>
        val r: MOption[GumboAST.GclSymbol] = postGumboASTInfoFlowClause(o) match {
         case MSome(result: GumboAST.GclSymbol) => MSome[GumboAST.GclSymbol](result)
         case MSome(_) => halt("Can only produce object of type GumboAST.GclSymbol")
         case _ => MNone[GumboAST.GclSymbol]()
        }
        return r
    }
  }

  def postGumboASTGclSubclause(o: GumboAST.GclSubclause): MOption[GumboAST.GclSubclause] = {
    return PostResultGumboASTGclSubclause
  }

  def postGumboASTGclMethod(o: GumboAST.GclMethod): MOption[GumboAST.GclMethod] = {
    return PostResultGumboASTGclMethod
  }

  def postGumboASTGclStateVar(o: GumboAST.GclStateVar): MOption[GumboAST.GclStateVar] = {
    return PostResultGumboASTGclStateVar
  }

  def postGumboASTGclClause(o: GumboAST.GclClause): MOption[GumboAST.GclClause] = {
    o match {
      case o: GumboAST.GclInvariant =>
        val r: MOption[GumboAST.GclClause] = postGumboASTGclInvariant(o) match {
         case MSome(result: GumboAST.GclClause) => MSome[GumboAST.GclClause](result)
         case MSome(_) => halt("Can only produce object of type GumboAST.GclClause")
         case _ => MNone[GumboAST.GclClause]()
        }
        return r
      case o: GumboAST.GclAssume =>
        val r: MOption[GumboAST.GclClause] = postGumboASTGclAssume(o) match {
         case MSome(result: GumboAST.GclClause) => MSome[GumboAST.GclClause](result)
         case MSome(_) => halt("Can only produce object of type GumboAST.GclClause")
         case _ => MNone[GumboAST.GclClause]()
        }
        return r
      case o: GumboAST.GclGuarantee =>
        val r: MOption[GumboAST.GclClause] = postGumboASTGclGuarantee(o) match {
         case MSome(result: GumboAST.GclClause) => MSome[GumboAST.GclClause](result)
         case MSome(_) => halt("Can only produce object of type GumboAST.GclClause")
         case _ => MNone[GumboAST.GclClause]()
        }
        return r
      case o: GumboAST.InfoFlowClause =>
        val r: MOption[GumboAST.GclClause] = postGumboASTInfoFlowClause(o) match {
         case MSome(result: GumboAST.GclClause) => MSome[GumboAST.GclClause](result)
         case MSome(_) => halt("Can only produce object of type GumboAST.GclClause")
         case _ => MNone[GumboAST.GclClause]()
        }
        return r
    }
  }

  def postGumboASTGclSpec(o: GumboAST.GclSpec): MOption[GumboAST.GclSpec] = {
    o match {
      case o: GumboAST.GclInvariant =>
        val r: MOption[GumboAST.GclSpec] = postGumboASTGclInvariant(o) match {
         case MSome(result: GumboAST.GclSpec) => MSome[GumboAST.GclSpec](result)
         case MSome(_) => halt("Can only produce object of type GumboAST.GclSpec")
         case _ => MNone[GumboAST.GclSpec]()
        }
        return r
      case o: GumboAST.GclAssume =>
        val r: MOption[GumboAST.GclSpec] = postGumboASTGclAssume(o) match {
         case MSome(result: GumboAST.GclSpec) => MSome[GumboAST.GclSpec](result)
         case MSome(_) => halt("Can only produce object of type GumboAST.GclSpec")
         case _ => MNone[GumboAST.GclSpec]()
        }
        return r
      case o: GumboAST.GclGuarantee =>
        val r: MOption[GumboAST.GclSpec] = postGumboASTGclGuarantee(o) match {
         case MSome(result: GumboAST.GclSpec) => MSome[GumboAST.GclSpec](result)
         case MSome(_) => halt("Can only produce object of type GumboAST.GclSpec")
         case _ => MNone[GumboAST.GclSpec]()
        }
        return r
    }
  }

  def postGumboASTGclInvariant(o: GumboAST.GclInvariant): MOption[GumboAST.GclInvariant] = {
    return PostResultGumboASTGclInvariant
  }

  def postGumboASTGclComputeSpec(o: GumboAST.GclComputeSpec): MOption[GumboAST.GclComputeSpec] = {
    o match {
      case o: GumboAST.GclAssume =>
        val r: MOption[GumboAST.GclComputeSpec] = postGumboASTGclAssume(o) match {
         case MSome(result: GumboAST.GclComputeSpec) => MSome[GumboAST.GclComputeSpec](result)
         case MSome(_) => halt("Can only produce object of type GumboAST.GclComputeSpec")
         case _ => MNone[GumboAST.GclComputeSpec]()
        }
        return r
      case o: GumboAST.GclGuarantee =>
        val r: MOption[GumboAST.GclComputeSpec] = postGumboASTGclGuarantee(o) match {
         case MSome(result: GumboAST.GclComputeSpec) => MSome[GumboAST.GclComputeSpec](result)
         case MSome(_) => halt("Can only produce object of type GumboAST.GclComputeSpec")
         case _ => MNone[GumboAST.GclComputeSpec]()
        }
        return r
    }
  }

  def postGumboASTGclAssume(o: GumboAST.GclAssume): MOption[GumboAST.GclAssume] = {
    return PostResultGumboASTGclAssume
  }

  def postGumboASTGclGuarantee(o: GumboAST.GclGuarantee): MOption[GumboAST.GclGuarantee] = {
    return PostResultGumboASTGclGuarantee
  }

  def postGumboASTGclIntegration(o: GumboAST.GclIntegration): MOption[GumboAST.GclIntegration] = {
    return PostResultGumboASTGclIntegration
  }

  def postGumboASTGclCaseStatement(o: GumboAST.GclCaseStatement): MOption[GumboAST.GclCaseStatement] = {
    return PostResultGumboASTGclCaseStatement
  }

  def postGumboASTGclInitialize(o: GumboAST.GclInitialize): MOption[GumboAST.GclInitialize] = {
    return PostResultGumboASTGclInitialize
  }

  def postGumboASTGclCompute(o: GumboAST.GclCompute): MOption[GumboAST.GclCompute] = {
    return PostResultGumboASTGclCompute
  }

  def postGumboASTGclHandle(o: GumboAST.GclHandle): MOption[GumboAST.GclHandle] = {
    return PostResultGumboASTGclHandle
  }

  def postGumboASTGclTODO(o: GumboAST.GclTODO): MOption[GumboAST.GclTODO] = {
    return PostResultGumboASTGclTODO
  }

  def postGumboASTGclLib(o: GumboAST.GclLib): MOption[GumboAST.GclLib] = {
    return PostResultGumboASTGclLib
  }

  def postGumboASTInfoFlowClause(o: GumboAST.InfoFlowClause): MOption[GumboAST.InfoFlowClause] = {
    return PostResultGumboASTInfoFlowClause
  }

  def transformSysmlAstId(o: SysmlAst.Id): MOption[SysmlAst.Id] = {
    val preR: PreResult[SysmlAst.Id] = preSysmlAstId(o)
    val r: MOption[SysmlAst.Id] = if (preR.continu) {
      val o2: SysmlAst.Id = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[Attr] = transformAttr(o2.attr)
      if (hasChanged || r0.nonEmpty)
        MSome(o2(attr = r0.getOrElse(o2.attr)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.Id = r.getOrElse(o)
    val postR: MOption[SysmlAst.Id] = postSysmlAstId(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstName(o: SysmlAst.Name): MOption[SysmlAst.Name] = {
    val preR: PreResult[SysmlAst.Name] = preSysmlAstName(o)
    val r: MOption[SysmlAst.Name] = if (preR.continu) {
      val o2: SysmlAst.Name = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[IS[Z, SysmlAst.Id]] = transformISZ(o2.ids, transformSysmlAstId _)
      val r1: MOption[Attr] = transformAttr(o2.attr)
      if (hasChanged || r0.nonEmpty || r1.nonEmpty)
        MSome(o2(ids = r0.getOrElse(o2.ids), attr = r1.getOrElse(o2.attr)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.Name = r.getOrElse(o)
    val postR: MOption[SysmlAst.Name] = postSysmlAstName(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstTopUnit(o: SysmlAst.TopUnit): MOption[SysmlAst.TopUnit] = {
    val preR: PreResult[SysmlAst.TopUnit] = preSysmlAstTopUnit(o)
    val r: MOption[SysmlAst.TopUnit] = if (preR.continu) {
      val o2: SysmlAst.TopUnit = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[IS[Z, SysmlAst.PackageBodyElement]] = transformISZ(o2.packageBodyElements, transformSysmlAstPackageBodyElement _)
      if (hasChanged || r0.nonEmpty)
        MSome(o2(packageBodyElements = r0.getOrElse(o2.packageBodyElements)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.TopUnit = r.getOrElse(o)
    val postR: MOption[SysmlAst.TopUnit] = postSysmlAstTopUnit(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstAttrNode(o: SysmlAst.AttrNode): MOption[SysmlAst.AttrNode] = {
    val preR: PreResult[SysmlAst.AttrNode] = preSysmlAstAttrNode(o)
    val r: MOption[SysmlAst.AttrNode] = if (preR.continu) {
      val o2: SysmlAst.AttrNode = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val rOpt: MOption[SysmlAst.AttrNode] = o2 match {
        case o2: SysmlAst.Import =>
          val r0: MOption[SysmlAst.Name] = transformSysmlAstName(o2.name)
          val r1: MOption[IS[Z, SysmlAst.AnnotatingElement]] = transformISZ(o2.annotations, transformSysmlAstAnnotatingElement _)
          val r2: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty)
            MSome(o2(name = r0.getOrElse(o2.name), annotations = r1.getOrElse(o2.annotations), attr = r2.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.AliasMember =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r1: MOption[SysmlAst.Name] = transformSysmlAstName(o2.target)
          val r2: MOption[IS[Z, SysmlAst.AnnotatingElement]] = transformISZ(o2.annotations, transformSysmlAstAnnotatingElement _)
          val r3: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty)
            MSome(o2(identification = r0.getOrElse(o2.identification), target = r1.getOrElse(o2.target), annotations = r2.getOrElse(o2.annotations), attr = r3.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.Identification =>
          val r0: MOption[Option[SysmlAst.Id]] = transformOption(o2.shortName, transformSysmlAstId _)
          val r1: MOption[Option[SysmlAst.Id]] = transformOption(o2.name, transformSysmlAstId _)
          val r2: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty)
            MSome(o2(shortName = r0.getOrElse(o2.shortName), name = r1.getOrElse(o2.name), attr = r2.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.Package =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r1: MOption[IS[Z, SysmlAst.PackageBodyElement]] = transformISZ(o2.packageElements, transformSysmlAstPackageBodyElement _)
          val r2: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty)
            MSome(o2(identification = r0.getOrElse(o2.identification), packageElements = r1.getOrElse(o2.packageElements), attr = r2.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.AttributeDefinition =>
          val r0: MOption[SysmlAst.DefinitionPrefix] = transformSysmlAstDefinitionPrefix(o2.defPrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r3: MOption[IS[Z, Type.Named]] = transformISZ(o2.parents, transformTypeNamed _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.bodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(defPrefix = r0.getOrElse(o2.defPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), parents = r3.getOrElse(o2.parents), bodyItems = r4.getOrElse(o2.bodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.AllocationDefinition =>
          val r0: MOption[SysmlAst.OccurrenceDefinitionPrefix] = transformSysmlAstOccurrenceDefinitionPrefix(o2.occurrenceDefPrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r3: MOption[IS[Z, Type.Named]] = transformISZ(o2.parents, transformTypeNamed _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.bodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(occurrenceDefPrefix = r0.getOrElse(o2.occurrenceDefPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), parents = r3.getOrElse(o2.parents), bodyItems = r4.getOrElse(o2.bodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.ConnectionDefinition =>
          val r0: MOption[SysmlAst.OccurrenceDefinitionPrefix] = transformSysmlAstOccurrenceDefinitionPrefix(o2.occurrenceDefPrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r3: MOption[IS[Z, Type.Named]] = transformISZ(o2.parents, transformTypeNamed _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.bodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(occurrenceDefPrefix = r0.getOrElse(o2.occurrenceDefPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), parents = r3.getOrElse(o2.parents), bodyItems = r4.getOrElse(o2.bodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.EnumerationDefinition =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r1: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r2: MOption[IS[Z, SysmlAst.AnnotatingElement]] = transformISZ(o2.annotations, transformSysmlAstAnnotatingElement _)
          val r3: MOption[IS[Z, SysmlAst.EnumeratedValue]] = transformISZ(o2.enumValues, transformSysmlAstEnumeratedValue _)
          val r4: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty)
            MSome(o2(identification = r0.getOrElse(o2.identification), subClassifications = r1.getOrElse(o2.subClassifications), annotations = r2.getOrElse(o2.annotations), enumValues = r3.getOrElse(o2.enumValues), attr = r4.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.PartDefinition =>
          val r0: MOption[SysmlAst.OccurrenceDefinitionPrefix] = transformSysmlAstOccurrenceDefinitionPrefix(o2.occurrenceDefPrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r3: MOption[IS[Z, Type.Named]] = transformISZ(o2.parents, transformTypeNamed _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.bodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(occurrenceDefPrefix = r0.getOrElse(o2.occurrenceDefPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), parents = r3.getOrElse(o2.parents), bodyItems = r4.getOrElse(o2.bodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.PortDefinition =>
          val r0: MOption[SysmlAst.DefinitionPrefix] = transformSysmlAstDefinitionPrefix(o2.defPrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r3: MOption[IS[Z, Type.Named]] = transformISZ(o2.parents, transformTypeNamed _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.bodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(defPrefix = r0.getOrElse(o2.defPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), parents = r3.getOrElse(o2.parents), bodyItems = r4.getOrElse(o2.bodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.MetadataDefinition =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r1: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r2: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.bodyItems, transformSysmlAstDefinitionBodyItem _)
          val r3: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty)
            MSome(o2(identification = r0.getOrElse(o2.identification), subClassifications = r1.getOrElse(o2.subClassifications), bodyItems = r2.getOrElse(o2.bodyItems), attr = r3.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.AttributeUsage =>
          val r0: MOption[SysmlAst.UsagePrefix] = transformSysmlAstUsagePrefix(o2.prefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
          val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
            MSome(o2(prefix = r0.getOrElse(o2.prefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), tipeOpt = r5.getOrElse(o2.tipeOpt), attr = r6.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.ReferenceUsage =>
          val r0: MOption[SysmlAst.RefPrefix] = transformSysmlAstRefPrefix(o2.prefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
          val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
            MSome(o2(prefix = r0.getOrElse(o2.prefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), tipeOpt = r5.getOrElse(o2.tipeOpt), attr = r6.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.ConnectionUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Option[SysmlAst.ConnectorPart]] = transformOption(o2.connectorPart, transformSysmlAstConnectorPart _)
          val r6: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
          val r7: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty || r7.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), connectorPart = r5.getOrElse(o2.connectorPart), tipeOpt = r6.getOrElse(o2.tipeOpt), attr = r7.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.ItemUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
          val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), tipeOpt = r5.getOrElse(o2.tipeOpt), attr = r6.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.PartUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
          val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), tipeOpt = r5.getOrElse(o2.tipeOpt), attr = r6.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.PortUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
          val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), tipeOpt = r5.getOrElse(o2.tipeOpt), attr = r6.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.Comment =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r1: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.abouts, transformSysmlAstName _)
          val r2: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty)
            MSome(o2(identification = r0.getOrElse(o2.identification), abouts = r1.getOrElse(o2.abouts), attr = r2.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.Documentation =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r1: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty)
            MSome(o2(identification = r0.getOrElse(o2.identification), attr = r1.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.TextualRepresentation =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r1: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty)
            MSome(o2(identification = r0.getOrElse(o2.identification), attr = r1.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.GumboAnnotation =>
          val r0: MOption[GumboAST.GclSymbol] = transformGumboASTGclSymbol(o2.gumboNode)
          if (hasChanged || r0.nonEmpty)
            MSome(o2(gumboNode = r0.getOrElse(o2.gumboNode)))
          else
            MNone()
        case o2: GumboAST.GclSubclause =>
          val r0: MOption[IS[Z, GumboAST.GclStateVar]] = transformISZ(o2.state, transformGumboASTGclStateVar _)
          val r1: MOption[IS[Z, GumboAST.GclMethod]] = transformISZ(o2.methods, transformGumboASTGclMethod _)
          val r2: MOption[IS[Z, GumboAST.GclInvariant]] = transformISZ(o2.invariants, transformGumboASTGclInvariant _)
          val r3: MOption[Option[GumboAST.GclInitialize]] = transformOption(o2.initializes, transformGumboASTGclInitialize _)
          val r4: MOption[Option[GumboAST.GclIntegration]] = transformOption(o2.integration, transformGumboASTGclIntegration _)
          val r5: MOption[Option[GumboAST.GclCompute]] = transformOption(o2.compute, transformGumboASTGclCompute _)
          val r6: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
            MSome(o2(state = r0.getOrElse(o2.state), methods = r1.getOrElse(o2.methods), invariants = r2.getOrElse(o2.invariants), initializes = r3.getOrElse(o2.initializes), integration = r4.getOrElse(o2.integration), compute = r5.getOrElse(o2.compute), attr = r6.getOrElse(o2.attr)))
          else
            MNone()
        case o2: GumboAST.GclMethod =>
          if (hasChanged)
            MSome(o2)
          else
            MNone()
        case o2: GumboAST.GclStateVar =>
          if (hasChanged)
            MSome(o2)
          else
            MNone()
        case o2: GumboAST.GclInvariant =>
          val r0: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty)
            MSome(o2(attr = r0.getOrElse(o2.attr)))
          else
            MNone()
        case o2: GumboAST.GclAssume =>
          val r0: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty)
            MSome(o2(attr = r0.getOrElse(o2.attr)))
          else
            MNone()
        case o2: GumboAST.GclGuarantee =>
          val r0: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty)
            MSome(o2(attr = r0.getOrElse(o2.attr)))
          else
            MNone()
        case o2: GumboAST.GclIntegration =>
          val r0: MOption[IS[Z, GumboAST.GclSpec]] = transformISZ(o2.specs, transformGumboASTGclSpec _)
          val r1: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty)
            MSome(o2(specs = r0.getOrElse(o2.specs), attr = r1.getOrElse(o2.attr)))
          else
            MNone()
        case o2: GumboAST.GclCaseStatement =>
          val r0: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty)
            MSome(o2(attr = r0.getOrElse(o2.attr)))
          else
            MNone()
        case o2: GumboAST.GclInitialize =>
          val r0: MOption[IS[Z, GumboAST.GclGuarantee]] = transformISZ(o2.guarantees, transformGumboASTGclGuarantee _)
          val r1: MOption[IS[Z, GumboAST.InfoFlowClause]] = transformISZ(o2.flows, transformGumboASTInfoFlowClause _)
          val r2: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty)
            MSome(o2(guarantees = r0.getOrElse(o2.guarantees), flows = r1.getOrElse(o2.flows), attr = r2.getOrElse(o2.attr)))
          else
            MNone()
        case o2: GumboAST.GclCompute =>
          val r0: MOption[IS[Z, GumboAST.GclComputeSpec]] = transformISZ(o2.specs, transformGumboASTGclComputeSpec _)
          val r1: MOption[IS[Z, GumboAST.GclCaseStatement]] = transformISZ(o2.cases, transformGumboASTGclCaseStatement _)
          val r2: MOption[IS[Z, GumboAST.GclHandle]] = transformISZ(o2.handlers, transformGumboASTGclHandle _)
          val r3: MOption[IS[Z, GumboAST.InfoFlowClause]] = transformISZ(o2.flows, transformGumboASTInfoFlowClause _)
          val r4: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty)
            MSome(o2(specs = r0.getOrElse(o2.specs), cases = r1.getOrElse(o2.cases), handlers = r2.getOrElse(o2.handlers), flows = r3.getOrElse(o2.flows), attr = r4.getOrElse(o2.attr)))
          else
            MNone()
        case o2: GumboAST.GclHandle =>
          val r0: MOption[IS[Z, GumboAST.GclGuarantee]] = transformISZ(o2.guarantees, transformGumboASTGclGuarantee _)
          val r1: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty)
            MSome(o2(guarantees = r0.getOrElse(o2.guarantees), attr = r1.getOrElse(o2.attr)))
          else
            MNone()
        case o2: GumboAST.GclTODO =>
          if (hasChanged)
            MSome(o2)
          else
            MNone()
        case o2: GumboAST.GclLib =>
          val r0: MOption[SysmlAst.Name] = transformSysmlAstName(o2.containingPackage)
          val r1: MOption[IS[Z, GumboAST.GclMethod]] = transformISZ(o2.methods, transformGumboASTGclMethod _)
          val r2: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty)
            MSome(o2(containingPackage = r0.getOrElse(o2.containingPackage), methods = r1.getOrElse(o2.methods), attr = r2.getOrElse(o2.attr)))
          else
            MNone()
        case o2: GumboAST.InfoFlowClause =>
          val r0: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty)
            MSome(o2(attr = r0.getOrElse(o2.attr)))
          else
            MNone()
      }
      rOpt
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.AttrNode = r.getOrElse(o)
    val postR: MOption[SysmlAst.AttrNode] = postSysmlAstAttrNode(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstPackageBodyElement(o: SysmlAst.PackageBodyElement): MOption[SysmlAst.PackageBodyElement] = {
    val preR: PreResult[SysmlAst.PackageBodyElement] = preSysmlAstPackageBodyElement(o)
    val r: MOption[SysmlAst.PackageBodyElement] = if (preR.continu) {
      val o2: SysmlAst.PackageBodyElement = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val rOpt: MOption[SysmlAst.PackageBodyElement] = o2 match {
        case o2: SysmlAst.Import =>
          val r0: MOption[SysmlAst.Name] = transformSysmlAstName(o2.name)
          val r1: MOption[IS[Z, SysmlAst.AnnotatingElement]] = transformISZ(o2.annotations, transformSysmlAstAnnotatingElement _)
          val r2: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty)
            MSome(o2(name = r0.getOrElse(o2.name), annotations = r1.getOrElse(o2.annotations), attr = r2.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.AliasMember =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r1: MOption[SysmlAst.Name] = transformSysmlAstName(o2.target)
          val r2: MOption[IS[Z, SysmlAst.AnnotatingElement]] = transformISZ(o2.annotations, transformSysmlAstAnnotatingElement _)
          val r3: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty)
            MSome(o2(identification = r0.getOrElse(o2.identification), target = r1.getOrElse(o2.target), annotations = r2.getOrElse(o2.annotations), attr = r3.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.Package =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r1: MOption[IS[Z, SysmlAst.PackageBodyElement]] = transformISZ(o2.packageElements, transformSysmlAstPackageBodyElement _)
          val r2: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty)
            MSome(o2(identification = r0.getOrElse(o2.identification), packageElements = r1.getOrElse(o2.packageElements), attr = r2.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.AttributeDefinition =>
          val r0: MOption[SysmlAst.DefinitionPrefix] = transformSysmlAstDefinitionPrefix(o2.defPrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r3: MOption[IS[Z, Type.Named]] = transformISZ(o2.parents, transformTypeNamed _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.bodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(defPrefix = r0.getOrElse(o2.defPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), parents = r3.getOrElse(o2.parents), bodyItems = r4.getOrElse(o2.bodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.AllocationDefinition =>
          val r0: MOption[SysmlAst.OccurrenceDefinitionPrefix] = transformSysmlAstOccurrenceDefinitionPrefix(o2.occurrenceDefPrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r3: MOption[IS[Z, Type.Named]] = transformISZ(o2.parents, transformTypeNamed _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.bodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(occurrenceDefPrefix = r0.getOrElse(o2.occurrenceDefPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), parents = r3.getOrElse(o2.parents), bodyItems = r4.getOrElse(o2.bodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.ConnectionDefinition =>
          val r0: MOption[SysmlAst.OccurrenceDefinitionPrefix] = transformSysmlAstOccurrenceDefinitionPrefix(o2.occurrenceDefPrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r3: MOption[IS[Z, Type.Named]] = transformISZ(o2.parents, transformTypeNamed _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.bodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(occurrenceDefPrefix = r0.getOrElse(o2.occurrenceDefPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), parents = r3.getOrElse(o2.parents), bodyItems = r4.getOrElse(o2.bodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.EnumerationDefinition =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r1: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r2: MOption[IS[Z, SysmlAst.AnnotatingElement]] = transformISZ(o2.annotations, transformSysmlAstAnnotatingElement _)
          val r3: MOption[IS[Z, SysmlAst.EnumeratedValue]] = transformISZ(o2.enumValues, transformSysmlAstEnumeratedValue _)
          val r4: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty)
            MSome(o2(identification = r0.getOrElse(o2.identification), subClassifications = r1.getOrElse(o2.subClassifications), annotations = r2.getOrElse(o2.annotations), enumValues = r3.getOrElse(o2.enumValues), attr = r4.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.PartDefinition =>
          val r0: MOption[SysmlAst.OccurrenceDefinitionPrefix] = transformSysmlAstOccurrenceDefinitionPrefix(o2.occurrenceDefPrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r3: MOption[IS[Z, Type.Named]] = transformISZ(o2.parents, transformTypeNamed _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.bodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(occurrenceDefPrefix = r0.getOrElse(o2.occurrenceDefPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), parents = r3.getOrElse(o2.parents), bodyItems = r4.getOrElse(o2.bodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.PortDefinition =>
          val r0: MOption[SysmlAst.DefinitionPrefix] = transformSysmlAstDefinitionPrefix(o2.defPrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r3: MOption[IS[Z, Type.Named]] = transformISZ(o2.parents, transformTypeNamed _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.bodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(defPrefix = r0.getOrElse(o2.defPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), parents = r3.getOrElse(o2.parents), bodyItems = r4.getOrElse(o2.bodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.MetadataDefinition =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r1: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r2: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.bodyItems, transformSysmlAstDefinitionBodyItem _)
          val r3: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty)
            MSome(o2(identification = r0.getOrElse(o2.identification), subClassifications = r1.getOrElse(o2.subClassifications), bodyItems = r2.getOrElse(o2.bodyItems), attr = r3.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.AttributeUsage =>
          val r0: MOption[SysmlAst.UsagePrefix] = transformSysmlAstUsagePrefix(o2.prefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
          val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
            MSome(o2(prefix = r0.getOrElse(o2.prefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), tipeOpt = r5.getOrElse(o2.tipeOpt), attr = r6.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.ReferenceUsage =>
          val r0: MOption[SysmlAst.RefPrefix] = transformSysmlAstRefPrefix(o2.prefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
          val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
            MSome(o2(prefix = r0.getOrElse(o2.prefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), tipeOpt = r5.getOrElse(o2.tipeOpt), attr = r6.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.ConnectionUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Option[SysmlAst.ConnectorPart]] = transformOption(o2.connectorPart, transformSysmlAstConnectorPart _)
          val r6: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
          val r7: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty || r7.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), connectorPart = r5.getOrElse(o2.connectorPart), tipeOpt = r6.getOrElse(o2.tipeOpt), attr = r7.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.ItemUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
          val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), tipeOpt = r5.getOrElse(o2.tipeOpt), attr = r6.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.PartUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
          val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), tipeOpt = r5.getOrElse(o2.tipeOpt), attr = r6.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.PortUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
          val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), tipeOpt = r5.getOrElse(o2.tipeOpt), attr = r6.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.Comment =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r1: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.abouts, transformSysmlAstName _)
          val r2: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty)
            MSome(o2(identification = r0.getOrElse(o2.identification), abouts = r1.getOrElse(o2.abouts), attr = r2.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.Documentation =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r1: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty)
            MSome(o2(identification = r0.getOrElse(o2.identification), attr = r1.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.TextualRepresentation =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r1: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty)
            MSome(o2(identification = r0.getOrElse(o2.identification), attr = r1.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.GumboAnnotation =>
          val r0: MOption[GumboAST.GclSymbol] = transformGumboASTGclSymbol(o2.gumboNode)
          if (hasChanged || r0.nonEmpty)
            MSome(o2(gumboNode = r0.getOrElse(o2.gumboNode)))
          else
            MNone()
      }
      rOpt
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.PackageBodyElement = r.getOrElse(o)
    val postR: MOption[SysmlAst.PackageBodyElement] = postSysmlAstPackageBodyElement(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstDefinitionBodyItem(o: SysmlAst.DefinitionBodyItem): MOption[SysmlAst.DefinitionBodyItem] = {
    val preR: PreResult[SysmlAst.DefinitionBodyItem] = preSysmlAstDefinitionBodyItem(o)
    val r: MOption[SysmlAst.DefinitionBodyItem] = if (preR.continu) {
      val o2: SysmlAst.DefinitionBodyItem = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val rOpt: MOption[SysmlAst.DefinitionBodyItem] = o2 match {
        case o2: SysmlAst.Import =>
          val r0: MOption[SysmlAst.Name] = transformSysmlAstName(o2.name)
          val r1: MOption[IS[Z, SysmlAst.AnnotatingElement]] = transformISZ(o2.annotations, transformSysmlAstAnnotatingElement _)
          val r2: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty)
            MSome(o2(name = r0.getOrElse(o2.name), annotations = r1.getOrElse(o2.annotations), attr = r2.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.AliasMember =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r1: MOption[SysmlAst.Name] = transformSysmlAstName(o2.target)
          val r2: MOption[IS[Z, SysmlAst.AnnotatingElement]] = transformISZ(o2.annotations, transformSysmlAstAnnotatingElement _)
          val r3: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty)
            MSome(o2(identification = r0.getOrElse(o2.identification), target = r1.getOrElse(o2.target), annotations = r2.getOrElse(o2.annotations), attr = r3.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.Package =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r1: MOption[IS[Z, SysmlAst.PackageBodyElement]] = transformISZ(o2.packageElements, transformSysmlAstPackageBodyElement _)
          val r2: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty)
            MSome(o2(identification = r0.getOrElse(o2.identification), packageElements = r1.getOrElse(o2.packageElements), attr = r2.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.AttributeDefinition =>
          val r0: MOption[SysmlAst.DefinitionPrefix] = transformSysmlAstDefinitionPrefix(o2.defPrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r3: MOption[IS[Z, Type.Named]] = transformISZ(o2.parents, transformTypeNamed _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.bodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(defPrefix = r0.getOrElse(o2.defPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), parents = r3.getOrElse(o2.parents), bodyItems = r4.getOrElse(o2.bodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.AllocationDefinition =>
          val r0: MOption[SysmlAst.OccurrenceDefinitionPrefix] = transformSysmlAstOccurrenceDefinitionPrefix(o2.occurrenceDefPrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r3: MOption[IS[Z, Type.Named]] = transformISZ(o2.parents, transformTypeNamed _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.bodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(occurrenceDefPrefix = r0.getOrElse(o2.occurrenceDefPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), parents = r3.getOrElse(o2.parents), bodyItems = r4.getOrElse(o2.bodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.ConnectionDefinition =>
          val r0: MOption[SysmlAst.OccurrenceDefinitionPrefix] = transformSysmlAstOccurrenceDefinitionPrefix(o2.occurrenceDefPrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r3: MOption[IS[Z, Type.Named]] = transformISZ(o2.parents, transformTypeNamed _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.bodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(occurrenceDefPrefix = r0.getOrElse(o2.occurrenceDefPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), parents = r3.getOrElse(o2.parents), bodyItems = r4.getOrElse(o2.bodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.EnumerationDefinition =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r1: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r2: MOption[IS[Z, SysmlAst.AnnotatingElement]] = transformISZ(o2.annotations, transformSysmlAstAnnotatingElement _)
          val r3: MOption[IS[Z, SysmlAst.EnumeratedValue]] = transformISZ(o2.enumValues, transformSysmlAstEnumeratedValue _)
          val r4: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty)
            MSome(o2(identification = r0.getOrElse(o2.identification), subClassifications = r1.getOrElse(o2.subClassifications), annotations = r2.getOrElse(o2.annotations), enumValues = r3.getOrElse(o2.enumValues), attr = r4.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.PartDefinition =>
          val r0: MOption[SysmlAst.OccurrenceDefinitionPrefix] = transformSysmlAstOccurrenceDefinitionPrefix(o2.occurrenceDefPrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r3: MOption[IS[Z, Type.Named]] = transformISZ(o2.parents, transformTypeNamed _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.bodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(occurrenceDefPrefix = r0.getOrElse(o2.occurrenceDefPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), parents = r3.getOrElse(o2.parents), bodyItems = r4.getOrElse(o2.bodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.PortDefinition =>
          val r0: MOption[SysmlAst.DefinitionPrefix] = transformSysmlAstDefinitionPrefix(o2.defPrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r3: MOption[IS[Z, Type.Named]] = transformISZ(o2.parents, transformTypeNamed _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.bodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(defPrefix = r0.getOrElse(o2.defPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), parents = r3.getOrElse(o2.parents), bodyItems = r4.getOrElse(o2.bodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.MetadataDefinition =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r1: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r2: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.bodyItems, transformSysmlAstDefinitionBodyItem _)
          val r3: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty)
            MSome(o2(identification = r0.getOrElse(o2.identification), subClassifications = r1.getOrElse(o2.subClassifications), bodyItems = r2.getOrElse(o2.bodyItems), attr = r3.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.AttributeUsage =>
          val r0: MOption[SysmlAst.UsagePrefix] = transformSysmlAstUsagePrefix(o2.prefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
          val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
            MSome(o2(prefix = r0.getOrElse(o2.prefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), tipeOpt = r5.getOrElse(o2.tipeOpt), attr = r6.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.ReferenceUsage =>
          val r0: MOption[SysmlAst.RefPrefix] = transformSysmlAstRefPrefix(o2.prefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
          val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
            MSome(o2(prefix = r0.getOrElse(o2.prefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), tipeOpt = r5.getOrElse(o2.tipeOpt), attr = r6.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.ConnectionUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Option[SysmlAst.ConnectorPart]] = transformOption(o2.connectorPart, transformSysmlAstConnectorPart _)
          val r6: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
          val r7: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty || r7.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), connectorPart = r5.getOrElse(o2.connectorPart), tipeOpt = r6.getOrElse(o2.tipeOpt), attr = r7.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.ItemUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
          val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), tipeOpt = r5.getOrElse(o2.tipeOpt), attr = r6.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.PartUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
          val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), tipeOpt = r5.getOrElse(o2.tipeOpt), attr = r6.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.PortUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
          val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), tipeOpt = r5.getOrElse(o2.tipeOpt), attr = r6.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.Comment =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r1: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.abouts, transformSysmlAstName _)
          val r2: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty)
            MSome(o2(identification = r0.getOrElse(o2.identification), abouts = r1.getOrElse(o2.abouts), attr = r2.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.Documentation =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r1: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty)
            MSome(o2(identification = r0.getOrElse(o2.identification), attr = r1.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.TextualRepresentation =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r1: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty)
            MSome(o2(identification = r0.getOrElse(o2.identification), attr = r1.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.GumboAnnotation =>
          val r0: MOption[GumboAST.GclSymbol] = transformGumboASTGclSymbol(o2.gumboNode)
          if (hasChanged || r0.nonEmpty)
            MSome(o2(gumboNode = r0.getOrElse(o2.gumboNode)))
          else
            MNone()
      }
      rOpt
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.DefinitionBodyItem = r.getOrElse(o)
    val postR: MOption[SysmlAst.DefinitionBodyItem] = postSysmlAstDefinitionBodyItem(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstFeatureValue(o: SysmlAst.FeatureValue): MOption[SysmlAst.FeatureValue] = {
    val preR: PreResult[SysmlAst.FeatureValue] = preSysmlAstFeatureValue(o)
    val r: MOption[SysmlAst.FeatureValue] = if (preR.continu) {
      val o2: SysmlAst.FeatureValue = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      if (hasChanged)
        MSome(o2)
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.FeatureValue = r.getOrElse(o)
    val postR: MOption[SysmlAst.FeatureValue] = postSysmlAstFeatureValue(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstEnumeratedValue(o: SysmlAst.EnumeratedValue): MOption[SysmlAst.EnumeratedValue] = {
    val preR: PreResult[SysmlAst.EnumeratedValue] = preSysmlAstEnumeratedValue(o)
    val r: MOption[SysmlAst.EnumeratedValue] = if (preR.continu) {
      val o2: SysmlAst.EnumeratedValue = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
      val r1: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
      val r2: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
      if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty)
        MSome(o2(identification = r0.getOrElse(o2.identification), specializations = r1.getOrElse(o2.specializations), definitionBodyItems = r2.getOrElse(o2.definitionBodyItems)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.EnumeratedValue = r.getOrElse(o)
    val postR: MOption[SysmlAst.EnumeratedValue] = postSysmlAstEnumeratedValue(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstImport(o: SysmlAst.Import): MOption[SysmlAst.Import] = {
    val preR: PreResult[SysmlAst.Import] = preSysmlAstImport(o)
    val r: MOption[SysmlAst.Import] = if (preR.continu) {
      val o2: SysmlAst.Import = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[SysmlAst.Name] = transformSysmlAstName(o2.name)
      val r1: MOption[IS[Z, SysmlAst.AnnotatingElement]] = transformISZ(o2.annotations, transformSysmlAstAnnotatingElement _)
      val r2: MOption[Attr] = transformAttr(o2.attr)
      if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty)
        MSome(o2(name = r0.getOrElse(o2.name), annotations = r1.getOrElse(o2.annotations), attr = r2.getOrElse(o2.attr)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.Import = r.getOrElse(o)
    val postR: MOption[SysmlAst.Import] = postSysmlAstImport(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstAliasMember(o: SysmlAst.AliasMember): MOption[SysmlAst.AliasMember] = {
    val preR: PreResult[SysmlAst.AliasMember] = preSysmlAstAliasMember(o)
    val r: MOption[SysmlAst.AliasMember] = if (preR.continu) {
      val o2: SysmlAst.AliasMember = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
      val r1: MOption[SysmlAst.Name] = transformSysmlAstName(o2.target)
      val r2: MOption[IS[Z, SysmlAst.AnnotatingElement]] = transformISZ(o2.annotations, transformSysmlAstAnnotatingElement _)
      val r3: MOption[Attr] = transformAttr(o2.attr)
      if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty)
        MSome(o2(identification = r0.getOrElse(o2.identification), target = r1.getOrElse(o2.target), annotations = r2.getOrElse(o2.annotations), attr = r3.getOrElse(o2.attr)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.AliasMember = r.getOrElse(o)
    val postR: MOption[SysmlAst.AliasMember] = postSysmlAstAliasMember(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstIdentification(o: SysmlAst.Identification): MOption[SysmlAst.Identification] = {
    val preR: PreResult[SysmlAst.Identification] = preSysmlAstIdentification(o)
    val r: MOption[SysmlAst.Identification] = if (preR.continu) {
      val o2: SysmlAst.Identification = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[Option[SysmlAst.Id]] = transformOption(o2.shortName, transformSysmlAstId _)
      val r1: MOption[Option[SysmlAst.Id]] = transformOption(o2.name, transformSysmlAstId _)
      val r2: MOption[Attr] = transformAttr(o2.attr)
      if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty)
        MSome(o2(shortName = r0.getOrElse(o2.shortName), name = r1.getOrElse(o2.name), attr = r2.getOrElse(o2.attr)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.Identification = r.getOrElse(o)
    val postR: MOption[SysmlAst.Identification] = postSysmlAstIdentification(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstPackageMember(o: SysmlAst.PackageMember): MOption[SysmlAst.PackageMember] = {
    val preR: PreResult[SysmlAst.PackageMember] = preSysmlAstPackageMember(o)
    val r: MOption[SysmlAst.PackageMember] = if (preR.continu) {
      val o2: SysmlAst.PackageMember = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val rOpt: MOption[SysmlAst.PackageMember] = o2 match {
        case o2: SysmlAst.Package =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r1: MOption[IS[Z, SysmlAst.PackageBodyElement]] = transformISZ(o2.packageElements, transformSysmlAstPackageBodyElement _)
          val r2: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty)
            MSome(o2(identification = r0.getOrElse(o2.identification), packageElements = r1.getOrElse(o2.packageElements), attr = r2.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.AttributeDefinition =>
          val r0: MOption[SysmlAst.DefinitionPrefix] = transformSysmlAstDefinitionPrefix(o2.defPrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r3: MOption[IS[Z, Type.Named]] = transformISZ(o2.parents, transformTypeNamed _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.bodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(defPrefix = r0.getOrElse(o2.defPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), parents = r3.getOrElse(o2.parents), bodyItems = r4.getOrElse(o2.bodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.AllocationDefinition =>
          val r0: MOption[SysmlAst.OccurrenceDefinitionPrefix] = transformSysmlAstOccurrenceDefinitionPrefix(o2.occurrenceDefPrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r3: MOption[IS[Z, Type.Named]] = transformISZ(o2.parents, transformTypeNamed _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.bodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(occurrenceDefPrefix = r0.getOrElse(o2.occurrenceDefPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), parents = r3.getOrElse(o2.parents), bodyItems = r4.getOrElse(o2.bodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.ConnectionDefinition =>
          val r0: MOption[SysmlAst.OccurrenceDefinitionPrefix] = transformSysmlAstOccurrenceDefinitionPrefix(o2.occurrenceDefPrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r3: MOption[IS[Z, Type.Named]] = transformISZ(o2.parents, transformTypeNamed _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.bodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(occurrenceDefPrefix = r0.getOrElse(o2.occurrenceDefPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), parents = r3.getOrElse(o2.parents), bodyItems = r4.getOrElse(o2.bodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.EnumerationDefinition =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r1: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r2: MOption[IS[Z, SysmlAst.AnnotatingElement]] = transformISZ(o2.annotations, transformSysmlAstAnnotatingElement _)
          val r3: MOption[IS[Z, SysmlAst.EnumeratedValue]] = transformISZ(o2.enumValues, transformSysmlAstEnumeratedValue _)
          val r4: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty)
            MSome(o2(identification = r0.getOrElse(o2.identification), subClassifications = r1.getOrElse(o2.subClassifications), annotations = r2.getOrElse(o2.annotations), enumValues = r3.getOrElse(o2.enumValues), attr = r4.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.PartDefinition =>
          val r0: MOption[SysmlAst.OccurrenceDefinitionPrefix] = transformSysmlAstOccurrenceDefinitionPrefix(o2.occurrenceDefPrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r3: MOption[IS[Z, Type.Named]] = transformISZ(o2.parents, transformTypeNamed _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.bodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(occurrenceDefPrefix = r0.getOrElse(o2.occurrenceDefPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), parents = r3.getOrElse(o2.parents), bodyItems = r4.getOrElse(o2.bodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.PortDefinition =>
          val r0: MOption[SysmlAst.DefinitionPrefix] = transformSysmlAstDefinitionPrefix(o2.defPrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r3: MOption[IS[Z, Type.Named]] = transformISZ(o2.parents, transformTypeNamed _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.bodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(defPrefix = r0.getOrElse(o2.defPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), parents = r3.getOrElse(o2.parents), bodyItems = r4.getOrElse(o2.bodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.MetadataDefinition =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r1: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r2: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.bodyItems, transformSysmlAstDefinitionBodyItem _)
          val r3: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty)
            MSome(o2(identification = r0.getOrElse(o2.identification), subClassifications = r1.getOrElse(o2.subClassifications), bodyItems = r2.getOrElse(o2.bodyItems), attr = r3.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.AttributeUsage =>
          val r0: MOption[SysmlAst.UsagePrefix] = transformSysmlAstUsagePrefix(o2.prefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
          val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
            MSome(o2(prefix = r0.getOrElse(o2.prefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), tipeOpt = r5.getOrElse(o2.tipeOpt), attr = r6.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.ReferenceUsage =>
          val r0: MOption[SysmlAst.RefPrefix] = transformSysmlAstRefPrefix(o2.prefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
          val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
            MSome(o2(prefix = r0.getOrElse(o2.prefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), tipeOpt = r5.getOrElse(o2.tipeOpt), attr = r6.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.ConnectionUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Option[SysmlAst.ConnectorPart]] = transformOption(o2.connectorPart, transformSysmlAstConnectorPart _)
          val r6: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
          val r7: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty || r7.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), connectorPart = r5.getOrElse(o2.connectorPart), tipeOpt = r6.getOrElse(o2.tipeOpt), attr = r7.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.ItemUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
          val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), tipeOpt = r5.getOrElse(o2.tipeOpt), attr = r6.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.PartUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
          val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), tipeOpt = r5.getOrElse(o2.tipeOpt), attr = r6.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.PortUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
          val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), tipeOpt = r5.getOrElse(o2.tipeOpt), attr = r6.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.Comment =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r1: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.abouts, transformSysmlAstName _)
          val r2: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty)
            MSome(o2(identification = r0.getOrElse(o2.identification), abouts = r1.getOrElse(o2.abouts), attr = r2.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.Documentation =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r1: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty)
            MSome(o2(identification = r0.getOrElse(o2.identification), attr = r1.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.TextualRepresentation =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r1: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty)
            MSome(o2(identification = r0.getOrElse(o2.identification), attr = r1.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.GumboAnnotation =>
          val r0: MOption[GumboAST.GclSymbol] = transformGumboASTGclSymbol(o2.gumboNode)
          if (hasChanged || r0.nonEmpty)
            MSome(o2(gumboNode = r0.getOrElse(o2.gumboNode)))
          else
            MNone()
      }
      rOpt
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.PackageMember = r.getOrElse(o)
    val postR: MOption[SysmlAst.PackageMember] = postSysmlAstPackageMember(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstConnectorPart(o: SysmlAst.ConnectorPart): MOption[SysmlAst.ConnectorPart] = {
    val preR: PreResult[SysmlAst.ConnectorPart] = preSysmlAstConnectorPart(o)
    val r: MOption[SysmlAst.ConnectorPart] = if (preR.continu) {
      val o2: SysmlAst.ConnectorPart = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val rOpt: MOption[SysmlAst.ConnectorPart] = o2 match {
        case o2: SysmlAst.BinaryConnectorPart =>
          val r0: MOption[SysmlAst.ConnectorEnd] = transformSysmlAstConnectorEnd(o2.src)
          val r1: MOption[SysmlAst.ConnectorEnd] = transformSysmlAstConnectorEnd(o2.dst)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty)
            MSome(o2(src = r0.getOrElse(o2.src), dst = r1.getOrElse(o2.dst)))
          else
            MNone()
        case o2: SysmlAst.NaryConnectorPart =>
          val r0: MOption[IS[Z, SysmlAst.ConnectorEnd]] = transformISZ(o2.connectorEnds, transformSysmlAstConnectorEnd _)
          if (hasChanged || r0.nonEmpty)
            MSome(o2(connectorEnds = r0.getOrElse(o2.connectorEnds)))
          else
            MNone()
      }
      rOpt
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.ConnectorPart = r.getOrElse(o)
    val postR: MOption[SysmlAst.ConnectorPart] = postSysmlAstConnectorPart(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstConnectorEnd(o: SysmlAst.ConnectorEnd): MOption[SysmlAst.ConnectorEnd] = {
    val preR: PreResult[SysmlAst.ConnectorEnd] = preSysmlAstConnectorEnd(o)
    val r: MOption[SysmlAst.ConnectorEnd] = if (preR.continu) {
      val o2: SysmlAst.ConnectorEnd = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.reference, transformSysmlAstName _)
      if (hasChanged || r0.nonEmpty)
        MSome(o2(reference = r0.getOrElse(o2.reference)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.ConnectorEnd = r.getOrElse(o)
    val postR: MOption[SysmlAst.ConnectorEnd] = postSysmlAstConnectorEnd(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstBinaryConnectorPart(o: SysmlAst.BinaryConnectorPart): MOption[SysmlAst.BinaryConnectorPart] = {
    val preR: PreResult[SysmlAst.BinaryConnectorPart] = preSysmlAstBinaryConnectorPart(o)
    val r: MOption[SysmlAst.BinaryConnectorPart] = if (preR.continu) {
      val o2: SysmlAst.BinaryConnectorPart = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[SysmlAst.ConnectorEnd] = transformSysmlAstConnectorEnd(o2.src)
      val r1: MOption[SysmlAst.ConnectorEnd] = transformSysmlAstConnectorEnd(o2.dst)
      if (hasChanged || r0.nonEmpty || r1.nonEmpty)
        MSome(o2(src = r0.getOrElse(o2.src), dst = r1.getOrElse(o2.dst)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.BinaryConnectorPart = r.getOrElse(o)
    val postR: MOption[SysmlAst.BinaryConnectorPart] = postSysmlAstBinaryConnectorPart(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstNaryConnectorPart(o: SysmlAst.NaryConnectorPart): MOption[SysmlAst.NaryConnectorPart] = {
    val preR: PreResult[SysmlAst.NaryConnectorPart] = preSysmlAstNaryConnectorPart(o)
    val r: MOption[SysmlAst.NaryConnectorPart] = if (preR.continu) {
      val o2: SysmlAst.NaryConnectorPart = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[IS[Z, SysmlAst.ConnectorEnd]] = transformISZ(o2.connectorEnds, transformSysmlAstConnectorEnd _)
      if (hasChanged || r0.nonEmpty)
        MSome(o2(connectorEnds = r0.getOrElse(o2.connectorEnds)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.NaryConnectorPart = r.getOrElse(o)
    val postR: MOption[SysmlAst.NaryConnectorPart] = postSysmlAstNaryConnectorPart(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstFeatureSpecialization(o: SysmlAst.FeatureSpecialization): MOption[SysmlAst.FeatureSpecialization] = {
    val preR: PreResult[SysmlAst.FeatureSpecialization] = preSysmlAstFeatureSpecialization(o)
    val r: MOption[SysmlAst.FeatureSpecialization] = if (preR.continu) {
      val o2: SysmlAst.FeatureSpecialization = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val rOpt: MOption[SysmlAst.FeatureSpecialization] = o2 match {
        case o2: SysmlAst.TypingsSpecialization =>
          val r0: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.names, transformSysmlAstName _)
          if (hasChanged || r0.nonEmpty)
            MSome(o2(names = r0.getOrElse(o2.names)))
          else
            MNone()
        case o2: SysmlAst.SubsettingsSpecialization =>
          val r0: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subsettings, transformSysmlAstName _)
          if (hasChanged || r0.nonEmpty)
            MSome(o2(subsettings = r0.getOrElse(o2.subsettings)))
          else
            MNone()
        case o2: SysmlAst.ReferencesSpecialization =>
          val r0: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.references, transformSysmlAstName _)
          if (hasChanged || r0.nonEmpty)
            MSome(o2(references = r0.getOrElse(o2.references)))
          else
            MNone()
        case o2: SysmlAst.RedefinitionsSpecialization =>
          val r0: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.references, transformSysmlAstName _)
          if (hasChanged || r0.nonEmpty)
            MSome(o2(references = r0.getOrElse(o2.references)))
          else
            MNone()
      }
      rOpt
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.FeatureSpecialization = r.getOrElse(o)
    val postR: MOption[SysmlAst.FeatureSpecialization] = postSysmlAstFeatureSpecialization(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstTypingsSpecialization(o: SysmlAst.TypingsSpecialization): MOption[SysmlAst.TypingsSpecialization] = {
    val preR: PreResult[SysmlAst.TypingsSpecialization] = preSysmlAstTypingsSpecialization(o)
    val r: MOption[SysmlAst.TypingsSpecialization] = if (preR.continu) {
      val o2: SysmlAst.TypingsSpecialization = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.names, transformSysmlAstName _)
      if (hasChanged || r0.nonEmpty)
        MSome(o2(names = r0.getOrElse(o2.names)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.TypingsSpecialization = r.getOrElse(o)
    val postR: MOption[SysmlAst.TypingsSpecialization] = postSysmlAstTypingsSpecialization(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstSubsettingsSpecialization(o: SysmlAst.SubsettingsSpecialization): MOption[SysmlAst.SubsettingsSpecialization] = {
    val preR: PreResult[SysmlAst.SubsettingsSpecialization] = preSysmlAstSubsettingsSpecialization(o)
    val r: MOption[SysmlAst.SubsettingsSpecialization] = if (preR.continu) {
      val o2: SysmlAst.SubsettingsSpecialization = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subsettings, transformSysmlAstName _)
      if (hasChanged || r0.nonEmpty)
        MSome(o2(subsettings = r0.getOrElse(o2.subsettings)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.SubsettingsSpecialization = r.getOrElse(o)
    val postR: MOption[SysmlAst.SubsettingsSpecialization] = postSysmlAstSubsettingsSpecialization(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstReferencesSpecialization(o: SysmlAst.ReferencesSpecialization): MOption[SysmlAst.ReferencesSpecialization] = {
    val preR: PreResult[SysmlAst.ReferencesSpecialization] = preSysmlAstReferencesSpecialization(o)
    val r: MOption[SysmlAst.ReferencesSpecialization] = if (preR.continu) {
      val o2: SysmlAst.ReferencesSpecialization = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.references, transformSysmlAstName _)
      if (hasChanged || r0.nonEmpty)
        MSome(o2(references = r0.getOrElse(o2.references)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.ReferencesSpecialization = r.getOrElse(o)
    val postR: MOption[SysmlAst.ReferencesSpecialization] = postSysmlAstReferencesSpecialization(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstRedefinitionsSpecialization(o: SysmlAst.RedefinitionsSpecialization): MOption[SysmlAst.RedefinitionsSpecialization] = {
    val preR: PreResult[SysmlAst.RedefinitionsSpecialization] = preSysmlAstRedefinitionsSpecialization(o)
    val r: MOption[SysmlAst.RedefinitionsSpecialization] = if (preR.continu) {
      val o2: SysmlAst.RedefinitionsSpecialization = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.references, transformSysmlAstName _)
      if (hasChanged || r0.nonEmpty)
        MSome(o2(references = r0.getOrElse(o2.references)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.RedefinitionsSpecialization = r.getOrElse(o)
    val postR: MOption[SysmlAst.RedefinitionsSpecialization] = postSysmlAstRedefinitionsSpecialization(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstDefinitionMember(o: SysmlAst.DefinitionMember): MOption[SysmlAst.DefinitionMember] = {
    val preR: PreResult[SysmlAst.DefinitionMember] = preSysmlAstDefinitionMember(o)
    val r: MOption[SysmlAst.DefinitionMember] = if (preR.continu) {
      val o2: SysmlAst.DefinitionMember = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val rOpt: MOption[SysmlAst.DefinitionMember] = o2 match {
        case o2: SysmlAst.Package =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r1: MOption[IS[Z, SysmlAst.PackageBodyElement]] = transformISZ(o2.packageElements, transformSysmlAstPackageBodyElement _)
          val r2: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty)
            MSome(o2(identification = r0.getOrElse(o2.identification), packageElements = r1.getOrElse(o2.packageElements), attr = r2.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.AttributeDefinition =>
          val r0: MOption[SysmlAst.DefinitionPrefix] = transformSysmlAstDefinitionPrefix(o2.defPrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r3: MOption[IS[Z, Type.Named]] = transformISZ(o2.parents, transformTypeNamed _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.bodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(defPrefix = r0.getOrElse(o2.defPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), parents = r3.getOrElse(o2.parents), bodyItems = r4.getOrElse(o2.bodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.AllocationDefinition =>
          val r0: MOption[SysmlAst.OccurrenceDefinitionPrefix] = transformSysmlAstOccurrenceDefinitionPrefix(o2.occurrenceDefPrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r3: MOption[IS[Z, Type.Named]] = transformISZ(o2.parents, transformTypeNamed _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.bodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(occurrenceDefPrefix = r0.getOrElse(o2.occurrenceDefPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), parents = r3.getOrElse(o2.parents), bodyItems = r4.getOrElse(o2.bodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.ConnectionDefinition =>
          val r0: MOption[SysmlAst.OccurrenceDefinitionPrefix] = transformSysmlAstOccurrenceDefinitionPrefix(o2.occurrenceDefPrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r3: MOption[IS[Z, Type.Named]] = transformISZ(o2.parents, transformTypeNamed _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.bodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(occurrenceDefPrefix = r0.getOrElse(o2.occurrenceDefPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), parents = r3.getOrElse(o2.parents), bodyItems = r4.getOrElse(o2.bodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.EnumerationDefinition =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r1: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r2: MOption[IS[Z, SysmlAst.AnnotatingElement]] = transformISZ(o2.annotations, transformSysmlAstAnnotatingElement _)
          val r3: MOption[IS[Z, SysmlAst.EnumeratedValue]] = transformISZ(o2.enumValues, transformSysmlAstEnumeratedValue _)
          val r4: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty)
            MSome(o2(identification = r0.getOrElse(o2.identification), subClassifications = r1.getOrElse(o2.subClassifications), annotations = r2.getOrElse(o2.annotations), enumValues = r3.getOrElse(o2.enumValues), attr = r4.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.PartDefinition =>
          val r0: MOption[SysmlAst.OccurrenceDefinitionPrefix] = transformSysmlAstOccurrenceDefinitionPrefix(o2.occurrenceDefPrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r3: MOption[IS[Z, Type.Named]] = transformISZ(o2.parents, transformTypeNamed _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.bodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(occurrenceDefPrefix = r0.getOrElse(o2.occurrenceDefPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), parents = r3.getOrElse(o2.parents), bodyItems = r4.getOrElse(o2.bodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.PortDefinition =>
          val r0: MOption[SysmlAst.DefinitionPrefix] = transformSysmlAstDefinitionPrefix(o2.defPrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r3: MOption[IS[Z, Type.Named]] = transformISZ(o2.parents, transformTypeNamed _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.bodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(defPrefix = r0.getOrElse(o2.defPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), parents = r3.getOrElse(o2.parents), bodyItems = r4.getOrElse(o2.bodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.MetadataDefinition =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r1: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r2: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.bodyItems, transformSysmlAstDefinitionBodyItem _)
          val r3: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty)
            MSome(o2(identification = r0.getOrElse(o2.identification), subClassifications = r1.getOrElse(o2.subClassifications), bodyItems = r2.getOrElse(o2.bodyItems), attr = r3.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.Comment =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r1: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.abouts, transformSysmlAstName _)
          val r2: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty)
            MSome(o2(identification = r0.getOrElse(o2.identification), abouts = r1.getOrElse(o2.abouts), attr = r2.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.Documentation =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r1: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty)
            MSome(o2(identification = r0.getOrElse(o2.identification), attr = r1.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.TextualRepresentation =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r1: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty)
            MSome(o2(identification = r0.getOrElse(o2.identification), attr = r1.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.GumboAnnotation =>
          val r0: MOption[GumboAST.GclSymbol] = transformGumboASTGclSymbol(o2.gumboNode)
          if (hasChanged || r0.nonEmpty)
            MSome(o2(gumboNode = r0.getOrElse(o2.gumboNode)))
          else
            MNone()
      }
      rOpt
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.DefinitionMember = r.getOrElse(o)
    val postR: MOption[SysmlAst.DefinitionMember] = postSysmlAstDefinitionMember(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstDefinitionElement(o: SysmlAst.DefinitionElement): MOption[SysmlAst.DefinitionElement] = {
    val preR: PreResult[SysmlAst.DefinitionElement] = preSysmlAstDefinitionElement(o)
    val r: MOption[SysmlAst.DefinitionElement] = if (preR.continu) {
      val o2: SysmlAst.DefinitionElement = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val rOpt: MOption[SysmlAst.DefinitionElement] = o2 match {
        case o2: SysmlAst.Package =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r1: MOption[IS[Z, SysmlAst.PackageBodyElement]] = transformISZ(o2.packageElements, transformSysmlAstPackageBodyElement _)
          val r2: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty)
            MSome(o2(identification = r0.getOrElse(o2.identification), packageElements = r1.getOrElse(o2.packageElements), attr = r2.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.AttributeDefinition =>
          val r0: MOption[SysmlAst.DefinitionPrefix] = transformSysmlAstDefinitionPrefix(o2.defPrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r3: MOption[IS[Z, Type.Named]] = transformISZ(o2.parents, transformTypeNamed _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.bodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(defPrefix = r0.getOrElse(o2.defPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), parents = r3.getOrElse(o2.parents), bodyItems = r4.getOrElse(o2.bodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.AllocationDefinition =>
          val r0: MOption[SysmlAst.OccurrenceDefinitionPrefix] = transformSysmlAstOccurrenceDefinitionPrefix(o2.occurrenceDefPrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r3: MOption[IS[Z, Type.Named]] = transformISZ(o2.parents, transformTypeNamed _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.bodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(occurrenceDefPrefix = r0.getOrElse(o2.occurrenceDefPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), parents = r3.getOrElse(o2.parents), bodyItems = r4.getOrElse(o2.bodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.ConnectionDefinition =>
          val r0: MOption[SysmlAst.OccurrenceDefinitionPrefix] = transformSysmlAstOccurrenceDefinitionPrefix(o2.occurrenceDefPrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r3: MOption[IS[Z, Type.Named]] = transformISZ(o2.parents, transformTypeNamed _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.bodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(occurrenceDefPrefix = r0.getOrElse(o2.occurrenceDefPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), parents = r3.getOrElse(o2.parents), bodyItems = r4.getOrElse(o2.bodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.EnumerationDefinition =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r1: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r2: MOption[IS[Z, SysmlAst.AnnotatingElement]] = transformISZ(o2.annotations, transformSysmlAstAnnotatingElement _)
          val r3: MOption[IS[Z, SysmlAst.EnumeratedValue]] = transformISZ(o2.enumValues, transformSysmlAstEnumeratedValue _)
          val r4: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty)
            MSome(o2(identification = r0.getOrElse(o2.identification), subClassifications = r1.getOrElse(o2.subClassifications), annotations = r2.getOrElse(o2.annotations), enumValues = r3.getOrElse(o2.enumValues), attr = r4.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.PartDefinition =>
          val r0: MOption[SysmlAst.OccurrenceDefinitionPrefix] = transformSysmlAstOccurrenceDefinitionPrefix(o2.occurrenceDefPrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r3: MOption[IS[Z, Type.Named]] = transformISZ(o2.parents, transformTypeNamed _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.bodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(occurrenceDefPrefix = r0.getOrElse(o2.occurrenceDefPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), parents = r3.getOrElse(o2.parents), bodyItems = r4.getOrElse(o2.bodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.PortDefinition =>
          val r0: MOption[SysmlAst.DefinitionPrefix] = transformSysmlAstDefinitionPrefix(o2.defPrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r3: MOption[IS[Z, Type.Named]] = transformISZ(o2.parents, transformTypeNamed _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.bodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(defPrefix = r0.getOrElse(o2.defPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), parents = r3.getOrElse(o2.parents), bodyItems = r4.getOrElse(o2.bodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.MetadataDefinition =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r1: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r2: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.bodyItems, transformSysmlAstDefinitionBodyItem _)
          val r3: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty)
            MSome(o2(identification = r0.getOrElse(o2.identification), subClassifications = r1.getOrElse(o2.subClassifications), bodyItems = r2.getOrElse(o2.bodyItems), attr = r3.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.Comment =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r1: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.abouts, transformSysmlAstName _)
          val r2: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty)
            MSome(o2(identification = r0.getOrElse(o2.identification), abouts = r1.getOrElse(o2.abouts), attr = r2.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.Documentation =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r1: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty)
            MSome(o2(identification = r0.getOrElse(o2.identification), attr = r1.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.TextualRepresentation =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r1: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty)
            MSome(o2(identification = r0.getOrElse(o2.identification), attr = r1.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.GumboAnnotation =>
          val r0: MOption[GumboAST.GclSymbol] = transformGumboASTGclSymbol(o2.gumboNode)
          if (hasChanged || r0.nonEmpty)
            MSome(o2(gumboNode = r0.getOrElse(o2.gumboNode)))
          else
            MNone()
      }
      rOpt
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.DefinitionElement = r.getOrElse(o)
    val postR: MOption[SysmlAst.DefinitionElement] = postSysmlAstDefinitionElement(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstDefinitionPrefix(o: SysmlAst.DefinitionPrefix): MOption[SysmlAst.DefinitionPrefix] = {
    val preR: PreResult[SysmlAst.DefinitionPrefix] = preSysmlAstDefinitionPrefix(o)
    val r: MOption[SysmlAst.DefinitionPrefix] = if (preR.continu) {
      val o2: SysmlAst.DefinitionPrefix = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      if (hasChanged)
        MSome(o2)
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.DefinitionPrefix = r.getOrElse(o)
    val postR: MOption[SysmlAst.DefinitionPrefix] = postSysmlAstDefinitionPrefix(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstPackage(o: SysmlAst.Package): MOption[SysmlAst.Package] = {
    val preR: PreResult[SysmlAst.Package] = preSysmlAstPackage(o)
    val r: MOption[SysmlAst.Package] = if (preR.continu) {
      val o2: SysmlAst.Package = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
      val r1: MOption[IS[Z, SysmlAst.PackageBodyElement]] = transformISZ(o2.packageElements, transformSysmlAstPackageBodyElement _)
      val r2: MOption[Attr] = transformAttr(o2.attr)
      if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty)
        MSome(o2(identification = r0.getOrElse(o2.identification), packageElements = r1.getOrElse(o2.packageElements), attr = r2.getOrElse(o2.attr)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.Package = r.getOrElse(o)
    val postR: MOption[SysmlAst.Package] = postSysmlAstPackage(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstAttributeDefinition(o: SysmlAst.AttributeDefinition): MOption[SysmlAst.AttributeDefinition] = {
    val preR: PreResult[SysmlAst.AttributeDefinition] = preSysmlAstAttributeDefinition(o)
    val r: MOption[SysmlAst.AttributeDefinition] = if (preR.continu) {
      val o2: SysmlAst.AttributeDefinition = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[SysmlAst.DefinitionPrefix] = transformSysmlAstDefinitionPrefix(o2.defPrefix)
      val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
      val r2: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
      val r3: MOption[IS[Z, Type.Named]] = transformISZ(o2.parents, transformTypeNamed _)
      val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.bodyItems, transformSysmlAstDefinitionBodyItem _)
      val r5: MOption[Attr] = transformAttr(o2.attr)
      if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
        MSome(o2(defPrefix = r0.getOrElse(o2.defPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), parents = r3.getOrElse(o2.parents), bodyItems = r4.getOrElse(o2.bodyItems), attr = r5.getOrElse(o2.attr)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.AttributeDefinition = r.getOrElse(o)
    val postR: MOption[SysmlAst.AttributeDefinition] = postSysmlAstAttributeDefinition(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstOccurrenceDefinitionPrefix(o: SysmlAst.OccurrenceDefinitionPrefix): MOption[SysmlAst.OccurrenceDefinitionPrefix] = {
    val preR: PreResult[SysmlAst.OccurrenceDefinitionPrefix] = preSysmlAstOccurrenceDefinitionPrefix(o)
    val r: MOption[SysmlAst.OccurrenceDefinitionPrefix] = if (preR.continu) {
      val o2: SysmlAst.OccurrenceDefinitionPrefix = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      if (hasChanged)
        MSome(o2)
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.OccurrenceDefinitionPrefix = r.getOrElse(o)
    val postR: MOption[SysmlAst.OccurrenceDefinitionPrefix] = postSysmlAstOccurrenceDefinitionPrefix(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstAllocationDefinition(o: SysmlAst.AllocationDefinition): MOption[SysmlAst.AllocationDefinition] = {
    val preR: PreResult[SysmlAst.AllocationDefinition] = preSysmlAstAllocationDefinition(o)
    val r: MOption[SysmlAst.AllocationDefinition] = if (preR.continu) {
      val o2: SysmlAst.AllocationDefinition = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[SysmlAst.OccurrenceDefinitionPrefix] = transformSysmlAstOccurrenceDefinitionPrefix(o2.occurrenceDefPrefix)
      val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
      val r2: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
      val r3: MOption[IS[Z, Type.Named]] = transformISZ(o2.parents, transformTypeNamed _)
      val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.bodyItems, transformSysmlAstDefinitionBodyItem _)
      val r5: MOption[Attr] = transformAttr(o2.attr)
      if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
        MSome(o2(occurrenceDefPrefix = r0.getOrElse(o2.occurrenceDefPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), parents = r3.getOrElse(o2.parents), bodyItems = r4.getOrElse(o2.bodyItems), attr = r5.getOrElse(o2.attr)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.AllocationDefinition = r.getOrElse(o)
    val postR: MOption[SysmlAst.AllocationDefinition] = postSysmlAstAllocationDefinition(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstConnectionDefinition(o: SysmlAst.ConnectionDefinition): MOption[SysmlAst.ConnectionDefinition] = {
    val preR: PreResult[SysmlAst.ConnectionDefinition] = preSysmlAstConnectionDefinition(o)
    val r: MOption[SysmlAst.ConnectionDefinition] = if (preR.continu) {
      val o2: SysmlAst.ConnectionDefinition = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[SysmlAst.OccurrenceDefinitionPrefix] = transformSysmlAstOccurrenceDefinitionPrefix(o2.occurrenceDefPrefix)
      val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
      val r2: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
      val r3: MOption[IS[Z, Type.Named]] = transformISZ(o2.parents, transformTypeNamed _)
      val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.bodyItems, transformSysmlAstDefinitionBodyItem _)
      val r5: MOption[Attr] = transformAttr(o2.attr)
      if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
        MSome(o2(occurrenceDefPrefix = r0.getOrElse(o2.occurrenceDefPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), parents = r3.getOrElse(o2.parents), bodyItems = r4.getOrElse(o2.bodyItems), attr = r5.getOrElse(o2.attr)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.ConnectionDefinition = r.getOrElse(o)
    val postR: MOption[SysmlAst.ConnectionDefinition] = postSysmlAstConnectionDefinition(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstEnumerationDefinition(o: SysmlAst.EnumerationDefinition): MOption[SysmlAst.EnumerationDefinition] = {
    val preR: PreResult[SysmlAst.EnumerationDefinition] = preSysmlAstEnumerationDefinition(o)
    val r: MOption[SysmlAst.EnumerationDefinition] = if (preR.continu) {
      val o2: SysmlAst.EnumerationDefinition = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
      val r1: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
      val r2: MOption[IS[Z, SysmlAst.AnnotatingElement]] = transformISZ(o2.annotations, transformSysmlAstAnnotatingElement _)
      val r3: MOption[IS[Z, SysmlAst.EnumeratedValue]] = transformISZ(o2.enumValues, transformSysmlAstEnumeratedValue _)
      val r4: MOption[Attr] = transformAttr(o2.attr)
      if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty)
        MSome(o2(identification = r0.getOrElse(o2.identification), subClassifications = r1.getOrElse(o2.subClassifications), annotations = r2.getOrElse(o2.annotations), enumValues = r3.getOrElse(o2.enumValues), attr = r4.getOrElse(o2.attr)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.EnumerationDefinition = r.getOrElse(o)
    val postR: MOption[SysmlAst.EnumerationDefinition] = postSysmlAstEnumerationDefinition(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstPartDefinition(o: SysmlAst.PartDefinition): MOption[SysmlAst.PartDefinition] = {
    val preR: PreResult[SysmlAst.PartDefinition] = preSysmlAstPartDefinition(o)
    val r: MOption[SysmlAst.PartDefinition] = if (preR.continu) {
      val o2: SysmlAst.PartDefinition = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[SysmlAst.OccurrenceDefinitionPrefix] = transformSysmlAstOccurrenceDefinitionPrefix(o2.occurrenceDefPrefix)
      val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
      val r2: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
      val r3: MOption[IS[Z, Type.Named]] = transformISZ(o2.parents, transformTypeNamed _)
      val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.bodyItems, transformSysmlAstDefinitionBodyItem _)
      val r5: MOption[Attr] = transformAttr(o2.attr)
      if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
        MSome(o2(occurrenceDefPrefix = r0.getOrElse(o2.occurrenceDefPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), parents = r3.getOrElse(o2.parents), bodyItems = r4.getOrElse(o2.bodyItems), attr = r5.getOrElse(o2.attr)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.PartDefinition = r.getOrElse(o)
    val postR: MOption[SysmlAst.PartDefinition] = postSysmlAstPartDefinition(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstPortDefinition(o: SysmlAst.PortDefinition): MOption[SysmlAst.PortDefinition] = {
    val preR: PreResult[SysmlAst.PortDefinition] = preSysmlAstPortDefinition(o)
    val r: MOption[SysmlAst.PortDefinition] = if (preR.continu) {
      val o2: SysmlAst.PortDefinition = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[SysmlAst.DefinitionPrefix] = transformSysmlAstDefinitionPrefix(o2.defPrefix)
      val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
      val r2: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
      val r3: MOption[IS[Z, Type.Named]] = transformISZ(o2.parents, transformTypeNamed _)
      val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.bodyItems, transformSysmlAstDefinitionBodyItem _)
      val r5: MOption[Attr] = transformAttr(o2.attr)
      if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
        MSome(o2(defPrefix = r0.getOrElse(o2.defPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), parents = r3.getOrElse(o2.parents), bodyItems = r4.getOrElse(o2.bodyItems), attr = r5.getOrElse(o2.attr)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.PortDefinition = r.getOrElse(o)
    val postR: MOption[SysmlAst.PortDefinition] = postSysmlAstPortDefinition(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstMetadataDefinition(o: SysmlAst.MetadataDefinition): MOption[SysmlAst.MetadataDefinition] = {
    val preR: PreResult[SysmlAst.MetadataDefinition] = preSysmlAstMetadataDefinition(o)
    val r: MOption[SysmlAst.MetadataDefinition] = if (preR.continu) {
      val o2: SysmlAst.MetadataDefinition = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
      val r1: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
      val r2: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.bodyItems, transformSysmlAstDefinitionBodyItem _)
      val r3: MOption[Attr] = transformAttr(o2.attr)
      if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty)
        MSome(o2(identification = r0.getOrElse(o2.identification), subClassifications = r1.getOrElse(o2.subClassifications), bodyItems = r2.getOrElse(o2.bodyItems), attr = r3.getOrElse(o2.attr)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.MetadataDefinition = r.getOrElse(o)
    val postR: MOption[SysmlAst.MetadataDefinition] = postSysmlAstMetadataDefinition(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstUsageElement(o: SysmlAst.UsageElement): MOption[SysmlAst.UsageElement] = {
    val preR: PreResult[SysmlAst.UsageElement] = preSysmlAstUsageElement(o)
    val r: MOption[SysmlAst.UsageElement] = if (preR.continu) {
      val o2: SysmlAst.UsageElement = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val rOpt: MOption[SysmlAst.UsageElement] = o2 match {
        case o2: SysmlAst.AttributeUsage =>
          val r0: MOption[SysmlAst.UsagePrefix] = transformSysmlAstUsagePrefix(o2.prefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
          val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
            MSome(o2(prefix = r0.getOrElse(o2.prefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), tipeOpt = r5.getOrElse(o2.tipeOpt), attr = r6.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.ReferenceUsage =>
          val r0: MOption[SysmlAst.RefPrefix] = transformSysmlAstRefPrefix(o2.prefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
          val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
            MSome(o2(prefix = r0.getOrElse(o2.prefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), tipeOpt = r5.getOrElse(o2.tipeOpt), attr = r6.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.ConnectionUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Option[SysmlAst.ConnectorPart]] = transformOption(o2.connectorPart, transformSysmlAstConnectorPart _)
          val r6: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
          val r7: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty || r7.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), connectorPart = r5.getOrElse(o2.connectorPart), tipeOpt = r6.getOrElse(o2.tipeOpt), attr = r7.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.ItemUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
          val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), tipeOpt = r5.getOrElse(o2.tipeOpt), attr = r6.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.PartUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
          val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), tipeOpt = r5.getOrElse(o2.tipeOpt), attr = r6.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.PortUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
          val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), tipeOpt = r5.getOrElse(o2.tipeOpt), attr = r6.getOrElse(o2.attr)))
          else
            MNone()
      }
      rOpt
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.UsageElement = r.getOrElse(o)
    val postR: MOption[SysmlAst.UsageElement] = postSysmlAstUsageElement(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstNonOccurrenceUsageMember(o: SysmlAst.NonOccurrenceUsageMember): MOption[SysmlAst.NonOccurrenceUsageMember] = {
    val preR: PreResult[SysmlAst.NonOccurrenceUsageMember] = preSysmlAstNonOccurrenceUsageMember(o)
    val r: MOption[SysmlAst.NonOccurrenceUsageMember] = if (preR.continu) {
      val o2: SysmlAst.NonOccurrenceUsageMember = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val rOpt: MOption[SysmlAst.NonOccurrenceUsageMember] = o2 match {
        case o2: SysmlAst.AttributeUsage =>
          val r0: MOption[SysmlAst.UsagePrefix] = transformSysmlAstUsagePrefix(o2.prefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
          val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
            MSome(o2(prefix = r0.getOrElse(o2.prefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), tipeOpt = r5.getOrElse(o2.tipeOpt), attr = r6.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.ReferenceUsage =>
          val r0: MOption[SysmlAst.RefPrefix] = transformSysmlAstRefPrefix(o2.prefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
          val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
            MSome(o2(prefix = r0.getOrElse(o2.prefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), tipeOpt = r5.getOrElse(o2.tipeOpt), attr = r6.getOrElse(o2.attr)))
          else
            MNone()
      }
      rOpt
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.NonOccurrenceUsageMember = r.getOrElse(o)
    val postR: MOption[SysmlAst.NonOccurrenceUsageMember] = postSysmlAstNonOccurrenceUsageMember(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstNonOccurrenceUsageElement(o: SysmlAst.NonOccurrenceUsageElement): MOption[SysmlAst.NonOccurrenceUsageElement] = {
    val preR: PreResult[SysmlAst.NonOccurrenceUsageElement] = preSysmlAstNonOccurrenceUsageElement(o)
    val r: MOption[SysmlAst.NonOccurrenceUsageElement] = if (preR.continu) {
      val o2: SysmlAst.NonOccurrenceUsageElement = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val rOpt: MOption[SysmlAst.NonOccurrenceUsageElement] = o2 match {
        case o2: SysmlAst.AttributeUsage =>
          val r0: MOption[SysmlAst.UsagePrefix] = transformSysmlAstUsagePrefix(o2.prefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
          val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
            MSome(o2(prefix = r0.getOrElse(o2.prefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), tipeOpt = r5.getOrElse(o2.tipeOpt), attr = r6.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.ReferenceUsage =>
          val r0: MOption[SysmlAst.RefPrefix] = transformSysmlAstRefPrefix(o2.prefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
          val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
            MSome(o2(prefix = r0.getOrElse(o2.prefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), tipeOpt = r5.getOrElse(o2.tipeOpt), attr = r6.getOrElse(o2.attr)))
          else
            MNone()
      }
      rOpt
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.NonOccurrenceUsageElement = r.getOrElse(o)
    val postR: MOption[SysmlAst.NonOccurrenceUsageElement] = postSysmlAstNonOccurrenceUsageElement(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstRefPrefix(o: SysmlAst.RefPrefix): MOption[SysmlAst.RefPrefix] = {
    val preR: PreResult[SysmlAst.RefPrefix] = preSysmlAstRefPrefix(o)
    val r: MOption[SysmlAst.RefPrefix] = if (preR.continu) {
      val o2: SysmlAst.RefPrefix = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      if (hasChanged)
        MSome(o2)
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.RefPrefix = r.getOrElse(o)
    val postR: MOption[SysmlAst.RefPrefix] = postSysmlAstRefPrefix(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstUsagePrefix(o: SysmlAst.UsagePrefix): MOption[SysmlAst.UsagePrefix] = {
    val preR: PreResult[SysmlAst.UsagePrefix] = preSysmlAstUsagePrefix(o)
    val r: MOption[SysmlAst.UsagePrefix] = if (preR.continu) {
      val o2: SysmlAst.UsagePrefix = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[SysmlAst.RefPrefix] = transformSysmlAstRefPrefix(o2.refPrefix)
      val r1: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.usageExtensions, transformSysmlAstName _)
      if (hasChanged || r0.nonEmpty || r1.nonEmpty)
        MSome(o2(refPrefix = r0.getOrElse(o2.refPrefix), usageExtensions = r1.getOrElse(o2.usageExtensions)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.UsagePrefix = r.getOrElse(o)
    val postR: MOption[SysmlAst.UsagePrefix] = postSysmlAstUsagePrefix(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstAttributeUsage(o: SysmlAst.AttributeUsage): MOption[SysmlAst.AttributeUsage] = {
    val preR: PreResult[SysmlAst.AttributeUsage] = preSysmlAstAttributeUsage(o)
    val r: MOption[SysmlAst.AttributeUsage] = if (preR.continu) {
      val o2: SysmlAst.AttributeUsage = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[SysmlAst.UsagePrefix] = transformSysmlAstUsagePrefix(o2.prefix)
      val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
      val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
      val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
      val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
      val r5: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
      val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
      if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
        MSome(o2(prefix = r0.getOrElse(o2.prefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), tipeOpt = r5.getOrElse(o2.tipeOpt), attr = r6.getOrElse(o2.attr)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.AttributeUsage = r.getOrElse(o)
    val postR: MOption[SysmlAst.AttributeUsage] = postSysmlAstAttributeUsage(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstReferenceUsage(o: SysmlAst.ReferenceUsage): MOption[SysmlAst.ReferenceUsage] = {
    val preR: PreResult[SysmlAst.ReferenceUsage] = preSysmlAstReferenceUsage(o)
    val r: MOption[SysmlAst.ReferenceUsage] = if (preR.continu) {
      val o2: SysmlAst.ReferenceUsage = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[SysmlAst.RefPrefix] = transformSysmlAstRefPrefix(o2.prefix)
      val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
      val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
      val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
      val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
      val r5: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
      val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
      if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
        MSome(o2(prefix = r0.getOrElse(o2.prefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), tipeOpt = r5.getOrElse(o2.tipeOpt), attr = r6.getOrElse(o2.attr)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.ReferenceUsage = r.getOrElse(o)
    val postR: MOption[SysmlAst.ReferenceUsage] = postSysmlAstReferenceUsage(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstOccurrenceUsageMember(o: SysmlAst.OccurrenceUsageMember): MOption[SysmlAst.OccurrenceUsageMember] = {
    val preR: PreResult[SysmlAst.OccurrenceUsageMember] = preSysmlAstOccurrenceUsageMember(o)
    val r: MOption[SysmlAst.OccurrenceUsageMember] = if (preR.continu) {
      val o2: SysmlAst.OccurrenceUsageMember = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val rOpt: MOption[SysmlAst.OccurrenceUsageMember] = o2 match {
        case o2: SysmlAst.ConnectionUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Option[SysmlAst.ConnectorPart]] = transformOption(o2.connectorPart, transformSysmlAstConnectorPart _)
          val r6: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
          val r7: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty || r7.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), connectorPart = r5.getOrElse(o2.connectorPart), tipeOpt = r6.getOrElse(o2.tipeOpt), attr = r7.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.ItemUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
          val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), tipeOpt = r5.getOrElse(o2.tipeOpt), attr = r6.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.PartUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
          val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), tipeOpt = r5.getOrElse(o2.tipeOpt), attr = r6.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.PortUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
          val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), tipeOpt = r5.getOrElse(o2.tipeOpt), attr = r6.getOrElse(o2.attr)))
          else
            MNone()
      }
      rOpt
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.OccurrenceUsageMember = r.getOrElse(o)
    val postR: MOption[SysmlAst.OccurrenceUsageMember] = postSysmlAstOccurrenceUsageMember(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstOccurrenceUsageElement(o: SysmlAst.OccurrenceUsageElement): MOption[SysmlAst.OccurrenceUsageElement] = {
    val preR: PreResult[SysmlAst.OccurrenceUsageElement] = preSysmlAstOccurrenceUsageElement(o)
    val r: MOption[SysmlAst.OccurrenceUsageElement] = if (preR.continu) {
      val o2: SysmlAst.OccurrenceUsageElement = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val rOpt: MOption[SysmlAst.OccurrenceUsageElement] = o2 match {
        case o2: SysmlAst.ConnectionUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Option[SysmlAst.ConnectorPart]] = transformOption(o2.connectorPart, transformSysmlAstConnectorPart _)
          val r6: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
          val r7: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty || r7.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), connectorPart = r5.getOrElse(o2.connectorPart), tipeOpt = r6.getOrElse(o2.tipeOpt), attr = r7.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.ItemUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
          val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), tipeOpt = r5.getOrElse(o2.tipeOpt), attr = r6.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.PartUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
          val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), tipeOpt = r5.getOrElse(o2.tipeOpt), attr = r6.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.PortUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
          val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), tipeOpt = r5.getOrElse(o2.tipeOpt), attr = r6.getOrElse(o2.attr)))
          else
            MNone()
      }
      rOpt
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.OccurrenceUsageElement = r.getOrElse(o)
    val postR: MOption[SysmlAst.OccurrenceUsageElement] = postSysmlAstOccurrenceUsageElement(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstStructureUsageElement(o: SysmlAst.StructureUsageElement): MOption[SysmlAst.StructureUsageElement] = {
    val preR: PreResult[SysmlAst.StructureUsageElement] = preSysmlAstStructureUsageElement(o)
    val r: MOption[SysmlAst.StructureUsageElement] = if (preR.continu) {
      val o2: SysmlAst.StructureUsageElement = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val rOpt: MOption[SysmlAst.StructureUsageElement] = o2 match {
        case o2: SysmlAst.ConnectionUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Option[SysmlAst.ConnectorPart]] = transformOption(o2.connectorPart, transformSysmlAstConnectorPart _)
          val r6: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
          val r7: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty || r7.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), connectorPart = r5.getOrElse(o2.connectorPart), tipeOpt = r6.getOrElse(o2.tipeOpt), attr = r7.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.ItemUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
          val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), tipeOpt = r5.getOrElse(o2.tipeOpt), attr = r6.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.PartUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
          val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), tipeOpt = r5.getOrElse(o2.tipeOpt), attr = r6.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.PortUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
          val r5: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
          val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), tipeOpt = r5.getOrElse(o2.tipeOpt), attr = r6.getOrElse(o2.attr)))
          else
            MNone()
      }
      rOpt
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.StructureUsageElement = r.getOrElse(o)
    val postR: MOption[SysmlAst.StructureUsageElement] = postSysmlAstStructureUsageElement(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstOccurrenceUsagePrefix(o: SysmlAst.OccurrenceUsagePrefix): MOption[SysmlAst.OccurrenceUsagePrefix] = {
    val preR: PreResult[SysmlAst.OccurrenceUsagePrefix] = preSysmlAstOccurrenceUsagePrefix(o)
    val r: MOption[SysmlAst.OccurrenceUsagePrefix] = if (preR.continu) {
      val o2: SysmlAst.OccurrenceUsagePrefix = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[SysmlAst.RefPrefix] = transformSysmlAstRefPrefix(o2.refPrefix)
      val r1: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.usageExtensions, transformSysmlAstName _)
      if (hasChanged || r0.nonEmpty || r1.nonEmpty)
        MSome(o2(refPrefix = r0.getOrElse(o2.refPrefix), usageExtensions = r1.getOrElse(o2.usageExtensions)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.OccurrenceUsagePrefix = r.getOrElse(o)
    val postR: MOption[SysmlAst.OccurrenceUsagePrefix] = postSysmlAstOccurrenceUsagePrefix(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstConnectionUsage(o: SysmlAst.ConnectionUsage): MOption[SysmlAst.ConnectionUsage] = {
    val preR: PreResult[SysmlAst.ConnectionUsage] = preSysmlAstConnectionUsage(o)
    val r: MOption[SysmlAst.ConnectionUsage] = if (preR.continu) {
      val o2: SysmlAst.ConnectionUsage = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
      val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
      val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
      val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
      val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
      val r5: MOption[Option[SysmlAst.ConnectorPart]] = transformOption(o2.connectorPart, transformSysmlAstConnectorPart _)
      val r6: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
      val r7: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
      if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty || r7.nonEmpty)
        MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), connectorPart = r5.getOrElse(o2.connectorPart), tipeOpt = r6.getOrElse(o2.tipeOpt), attr = r7.getOrElse(o2.attr)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.ConnectionUsage = r.getOrElse(o)
    val postR: MOption[SysmlAst.ConnectionUsage] = postSysmlAstConnectionUsage(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstItemUsage(o: SysmlAst.ItemUsage): MOption[SysmlAst.ItemUsage] = {
    val preR: PreResult[SysmlAst.ItemUsage] = preSysmlAstItemUsage(o)
    val r: MOption[SysmlAst.ItemUsage] = if (preR.continu) {
      val o2: SysmlAst.ItemUsage = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
      val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
      val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
      val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
      val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
      val r5: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
      val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
      if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
        MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), tipeOpt = r5.getOrElse(o2.tipeOpt), attr = r6.getOrElse(o2.attr)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.ItemUsage = r.getOrElse(o)
    val postR: MOption[SysmlAst.ItemUsage] = postSysmlAstItemUsage(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstPartUsage(o: SysmlAst.PartUsage): MOption[SysmlAst.PartUsage] = {
    val preR: PreResult[SysmlAst.PartUsage] = preSysmlAstPartUsage(o)
    val r: MOption[SysmlAst.PartUsage] = if (preR.continu) {
      val o2: SysmlAst.PartUsage = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
      val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
      val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
      val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
      val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
      val r5: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
      val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
      if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
        MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), tipeOpt = r5.getOrElse(o2.tipeOpt), attr = r6.getOrElse(o2.attr)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.PartUsage = r.getOrElse(o)
    val postR: MOption[SysmlAst.PartUsage] = postSysmlAstPartUsage(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstPortUsage(o: SysmlAst.PortUsage): MOption[SysmlAst.PortUsage] = {
    val preR: PreResult[SysmlAst.PortUsage] = preSysmlAstPortUsage(o)
    val r: MOption[SysmlAst.PortUsage] = if (preR.continu) {
      val o2: SysmlAst.PortUsage = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
      val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
      val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
      val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
      val r4: MOption[IS[Z, SysmlAst.DefinitionBodyItem]] = transformISZ(o2.definitionBodyItems, transformSysmlAstDefinitionBodyItem _)
      val r5: MOption[Option[Type]] = transformOption(o2.tipeOpt, transformType _)
      val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
      if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
        MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), tipeOpt = r5.getOrElse(o2.tipeOpt), attr = r6.getOrElse(o2.attr)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.PortUsage = r.getOrElse(o)
    val postR: MOption[SysmlAst.PortUsage] = postSysmlAstPortUsage(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstAnnotatingElement(o: SysmlAst.AnnotatingElement): MOption[SysmlAst.AnnotatingElement] = {
    val preR: PreResult[SysmlAst.AnnotatingElement] = preSysmlAstAnnotatingElement(o)
    val r: MOption[SysmlAst.AnnotatingElement] = if (preR.continu) {
      val o2: SysmlAst.AnnotatingElement = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val rOpt: MOption[SysmlAst.AnnotatingElement] = o2 match {
        case o2: SysmlAst.Comment =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r1: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.abouts, transformSysmlAstName _)
          val r2: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty)
            MSome(o2(identification = r0.getOrElse(o2.identification), abouts = r1.getOrElse(o2.abouts), attr = r2.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.Documentation =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r1: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty)
            MSome(o2(identification = r0.getOrElse(o2.identification), attr = r1.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.TextualRepresentation =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r1: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty)
            MSome(o2(identification = r0.getOrElse(o2.identification), attr = r1.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.GumboAnnotation =>
          val r0: MOption[GumboAST.GclSymbol] = transformGumboASTGclSymbol(o2.gumboNode)
          if (hasChanged || r0.nonEmpty)
            MSome(o2(gumboNode = r0.getOrElse(o2.gumboNode)))
          else
            MNone()
      }
      rOpt
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.AnnotatingElement = r.getOrElse(o)
    val postR: MOption[SysmlAst.AnnotatingElement] = postSysmlAstAnnotatingElement(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstComment(o: SysmlAst.Comment): MOption[SysmlAst.Comment] = {
    val preR: PreResult[SysmlAst.Comment] = preSysmlAstComment(o)
    val r: MOption[SysmlAst.Comment] = if (preR.continu) {
      val o2: SysmlAst.Comment = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
      val r1: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.abouts, transformSysmlAstName _)
      val r2: MOption[Attr] = transformAttr(o2.attr)
      if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty)
        MSome(o2(identification = r0.getOrElse(o2.identification), abouts = r1.getOrElse(o2.abouts), attr = r2.getOrElse(o2.attr)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.Comment = r.getOrElse(o)
    val postR: MOption[SysmlAst.Comment] = postSysmlAstComment(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstDocumentation(o: SysmlAst.Documentation): MOption[SysmlAst.Documentation] = {
    val preR: PreResult[SysmlAst.Documentation] = preSysmlAstDocumentation(o)
    val r: MOption[SysmlAst.Documentation] = if (preR.continu) {
      val o2: SysmlAst.Documentation = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
      val r1: MOption[Attr] = transformAttr(o2.attr)
      if (hasChanged || r0.nonEmpty || r1.nonEmpty)
        MSome(o2(identification = r0.getOrElse(o2.identification), attr = r1.getOrElse(o2.attr)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.Documentation = r.getOrElse(o)
    val postR: MOption[SysmlAst.Documentation] = postSysmlAstDocumentation(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstTextualRepresentation(o: SysmlAst.TextualRepresentation): MOption[SysmlAst.TextualRepresentation] = {
    val preR: PreResult[SysmlAst.TextualRepresentation] = preSysmlAstTextualRepresentation(o)
    val r: MOption[SysmlAst.TextualRepresentation] = if (preR.continu) {
      val o2: SysmlAst.TextualRepresentation = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
      val r1: MOption[Attr] = transformAttr(o2.attr)
      if (hasChanged || r0.nonEmpty || r1.nonEmpty)
        MSome(o2(identification = r0.getOrElse(o2.identification), attr = r1.getOrElse(o2.attr)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.TextualRepresentation = r.getOrElse(o)
    val postR: MOption[SysmlAst.TextualRepresentation] = postSysmlAstTextualRepresentation(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformSysmlAstGumboAnnotation(o: SysmlAst.GumboAnnotation): MOption[SysmlAst.GumboAnnotation] = {
    val preR: PreResult[SysmlAst.GumboAnnotation] = preSysmlAstGumboAnnotation(o)
    val r: MOption[SysmlAst.GumboAnnotation] = if (preR.continu) {
      val o2: SysmlAst.GumboAnnotation = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[GumboAST.GclSymbol] = transformGumboASTGclSymbol(o2.gumboNode)
      if (hasChanged || r0.nonEmpty)
        MSome(o2(gumboNode = r0.getOrElse(o2.gumboNode)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.GumboAnnotation = r.getOrElse(o)
    val postR: MOption[SysmlAst.GumboAnnotation] = postSysmlAstGumboAnnotation(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformAttr(o: Attr): MOption[Attr] = {
    val preR: PreResult[Attr] = preAttr(o)
    val r: MOption[Attr] = if (preR.continu) {
      val o2: Attr = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      if (hasChanged)
        MSome(o2)
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: Attr = r.getOrElse(o)
    val postR: MOption[Attr] = postAttr(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformResolvedAttr(o: ResolvedAttr): MOption[ResolvedAttr] = {
    val preR: PreResult[ResolvedAttr] = preResolvedAttr(o)
    val r: MOption[ResolvedAttr] = if (preR.continu) {
      val o2: ResolvedAttr = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[Option[ResolvedInfo]] = transformOption(o2.resOpt, transformResolvedInfo _)
      if (hasChanged || r0.nonEmpty)
        MSome(o2(resOpt = r0.getOrElse(o2.resOpt)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: ResolvedAttr = r.getOrElse(o)
    val postR: MOption[ResolvedAttr] = postResolvedAttr(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformResolvedInfo(o: ResolvedInfo): MOption[ResolvedInfo] = {
    val preR: PreResult[ResolvedInfo] = preResolvedInfo(o)
    val r: MOption[ResolvedInfo] = if (preR.continu) {
      val o2: ResolvedInfo = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val rOpt: MOption[ResolvedInfo] = o2 match {
        case o2: ResolvedInfo.Package =>
          if (hasChanged)
            MSome(o2)
          else
            MNone()
        case o2: ResolvedInfo.Enum =>
          if (hasChanged)
            MSome(o2)
          else
            MNone()
        case o2: ResolvedInfo.EnumElement =>
          if (hasChanged)
            MSome(o2)
          else
            MNone()
        case o2: ResolvedInfo.AttributeUsage =>
          if (hasChanged)
            MSome(o2)
          else
            MNone()
        case o2: ResolvedInfo.ConnectionUsage =>
          if (hasChanged)
            MSome(o2)
          else
            MNone()
        case o2: ResolvedInfo.ItemUsage =>
          if (hasChanged)
            MSome(o2)
          else
            MNone()
        case o2: ResolvedInfo.PartUsage =>
          if (hasChanged)
            MSome(o2)
          else
            MNone()
        case o2: ResolvedInfo.PortUsage =>
          if (hasChanged)
            MSome(o2)
          else
            MNone()
        case o2: ResolvedInfo.ReferenceUsage =>
          if (hasChanged)
            MSome(o2)
          else
            MNone()
      }
      rOpt
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: ResolvedInfo = r.getOrElse(o)
    val postR: MOption[ResolvedInfo] = postResolvedInfo(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformType(o: Type): MOption[Type] = {
    val preR: PreResult[Type] = preType(o)
    val r: MOption[Type] = if (preR.continu) {
      val o2: Type = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val rOpt: MOption[Type] = o2 match {
        case o2: Type.Named =>
          val r0: MOption[SysmlAst.Name] = transformSysmlAstName(o2.name)
          val r1: MOption[TypedAttr] = transformTypedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty)
            MSome(o2(name = r0.getOrElse(o2.name), attr = r1.getOrElse(o2.attr)))
          else
            MNone()
      }
      rOpt
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: Type = r.getOrElse(o)
    val postR: MOption[Type] = postType(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformTypedAttr(o: TypedAttr): MOption[TypedAttr] = {
    val preR: PreResult[TypedAttr] = preTypedAttr(o)
    val r: MOption[TypedAttr] = if (preR.continu) {
      val o2: TypedAttr = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      if (hasChanged)
        MSome(o2)
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: TypedAttr = r.getOrElse(o)
    val postR: MOption[TypedAttr] = postTypedAttr(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformGumboASTGclSymbol(o: GumboAST.GclSymbol): MOption[GumboAST.GclSymbol] = {
    val preR: PreResult[GumboAST.GclSymbol] = preGumboASTGclSymbol(o)
    val r: MOption[GumboAST.GclSymbol] = if (preR.continu) {
      val o2: GumboAST.GclSymbol = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val rOpt: MOption[GumboAST.GclSymbol] = o2 match {
        case o2: GumboAST.GclSubclause =>
          val r0: MOption[IS[Z, GumboAST.GclStateVar]] = transformISZ(o2.state, transformGumboASTGclStateVar _)
          val r1: MOption[IS[Z, GumboAST.GclMethod]] = transformISZ(o2.methods, transformGumboASTGclMethod _)
          val r2: MOption[IS[Z, GumboAST.GclInvariant]] = transformISZ(o2.invariants, transformGumboASTGclInvariant _)
          val r3: MOption[Option[GumboAST.GclInitialize]] = transformOption(o2.initializes, transformGumboASTGclInitialize _)
          val r4: MOption[Option[GumboAST.GclIntegration]] = transformOption(o2.integration, transformGumboASTGclIntegration _)
          val r5: MOption[Option[GumboAST.GclCompute]] = transformOption(o2.compute, transformGumboASTGclCompute _)
          val r6: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
            MSome(o2(state = r0.getOrElse(o2.state), methods = r1.getOrElse(o2.methods), invariants = r2.getOrElse(o2.invariants), initializes = r3.getOrElse(o2.initializes), integration = r4.getOrElse(o2.integration), compute = r5.getOrElse(o2.compute), attr = r6.getOrElse(o2.attr)))
          else
            MNone()
        case o2: GumboAST.GclMethod =>
          if (hasChanged)
            MSome(o2)
          else
            MNone()
        case o2: GumboAST.GclStateVar =>
          if (hasChanged)
            MSome(o2)
          else
            MNone()
        case o2: GumboAST.GclInvariant =>
          val r0: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty)
            MSome(o2(attr = r0.getOrElse(o2.attr)))
          else
            MNone()
        case o2: GumboAST.GclAssume =>
          val r0: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty)
            MSome(o2(attr = r0.getOrElse(o2.attr)))
          else
            MNone()
        case o2: GumboAST.GclGuarantee =>
          val r0: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty)
            MSome(o2(attr = r0.getOrElse(o2.attr)))
          else
            MNone()
        case o2: GumboAST.GclIntegration =>
          val r0: MOption[IS[Z, GumboAST.GclSpec]] = transformISZ(o2.specs, transformGumboASTGclSpec _)
          val r1: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty)
            MSome(o2(specs = r0.getOrElse(o2.specs), attr = r1.getOrElse(o2.attr)))
          else
            MNone()
        case o2: GumboAST.GclCaseStatement =>
          val r0: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty)
            MSome(o2(attr = r0.getOrElse(o2.attr)))
          else
            MNone()
        case o2: GumboAST.GclInitialize =>
          val r0: MOption[IS[Z, GumboAST.GclGuarantee]] = transformISZ(o2.guarantees, transformGumboASTGclGuarantee _)
          val r1: MOption[IS[Z, GumboAST.InfoFlowClause]] = transformISZ(o2.flows, transformGumboASTInfoFlowClause _)
          val r2: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty)
            MSome(o2(guarantees = r0.getOrElse(o2.guarantees), flows = r1.getOrElse(o2.flows), attr = r2.getOrElse(o2.attr)))
          else
            MNone()
        case o2: GumboAST.GclCompute =>
          val r0: MOption[IS[Z, GumboAST.GclComputeSpec]] = transformISZ(o2.specs, transformGumboASTGclComputeSpec _)
          val r1: MOption[IS[Z, GumboAST.GclCaseStatement]] = transformISZ(o2.cases, transformGumboASTGclCaseStatement _)
          val r2: MOption[IS[Z, GumboAST.GclHandle]] = transformISZ(o2.handlers, transformGumboASTGclHandle _)
          val r3: MOption[IS[Z, GumboAST.InfoFlowClause]] = transformISZ(o2.flows, transformGumboASTInfoFlowClause _)
          val r4: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty)
            MSome(o2(specs = r0.getOrElse(o2.specs), cases = r1.getOrElse(o2.cases), handlers = r2.getOrElse(o2.handlers), flows = r3.getOrElse(o2.flows), attr = r4.getOrElse(o2.attr)))
          else
            MNone()
        case o2: GumboAST.GclHandle =>
          val r0: MOption[IS[Z, GumboAST.GclGuarantee]] = transformISZ(o2.guarantees, transformGumboASTGclGuarantee _)
          val r1: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty)
            MSome(o2(guarantees = r0.getOrElse(o2.guarantees), attr = r1.getOrElse(o2.attr)))
          else
            MNone()
        case o2: GumboAST.GclTODO =>
          if (hasChanged)
            MSome(o2)
          else
            MNone()
        case o2: GumboAST.GclLib =>
          val r0: MOption[SysmlAst.Name] = transformSysmlAstName(o2.containingPackage)
          val r1: MOption[IS[Z, GumboAST.GclMethod]] = transformISZ(o2.methods, transformGumboASTGclMethod _)
          val r2: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty)
            MSome(o2(containingPackage = r0.getOrElse(o2.containingPackage), methods = r1.getOrElse(o2.methods), attr = r2.getOrElse(o2.attr)))
          else
            MNone()
        case o2: GumboAST.InfoFlowClause =>
          val r0: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty)
            MSome(o2(attr = r0.getOrElse(o2.attr)))
          else
            MNone()
      }
      rOpt
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: GumboAST.GclSymbol = r.getOrElse(o)
    val postR: MOption[GumboAST.GclSymbol] = postGumboASTGclSymbol(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformGumboASTGclSubclause(o: GumboAST.GclSubclause): MOption[GumboAST.GclSubclause] = {
    val preR: PreResult[GumboAST.GclSubclause] = preGumboASTGclSubclause(o)
    val r: MOption[GumboAST.GclSubclause] = if (preR.continu) {
      val o2: GumboAST.GclSubclause = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[IS[Z, GumboAST.GclStateVar]] = transformISZ(o2.state, transformGumboASTGclStateVar _)
      val r1: MOption[IS[Z, GumboAST.GclMethod]] = transformISZ(o2.methods, transformGumboASTGclMethod _)
      val r2: MOption[IS[Z, GumboAST.GclInvariant]] = transformISZ(o2.invariants, transformGumboASTGclInvariant _)
      val r3: MOption[Option[GumboAST.GclInitialize]] = transformOption(o2.initializes, transformGumboASTGclInitialize _)
      val r4: MOption[Option[GumboAST.GclIntegration]] = transformOption(o2.integration, transformGumboASTGclIntegration _)
      val r5: MOption[Option[GumboAST.GclCompute]] = transformOption(o2.compute, transformGumboASTGclCompute _)
      val r6: MOption[Attr] = transformAttr(o2.attr)
      if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
        MSome(o2(state = r0.getOrElse(o2.state), methods = r1.getOrElse(o2.methods), invariants = r2.getOrElse(o2.invariants), initializes = r3.getOrElse(o2.initializes), integration = r4.getOrElse(o2.integration), compute = r5.getOrElse(o2.compute), attr = r6.getOrElse(o2.attr)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: GumboAST.GclSubclause = r.getOrElse(o)
    val postR: MOption[GumboAST.GclSubclause] = postGumboASTGclSubclause(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformGumboASTGclMethod(o: GumboAST.GclMethod): MOption[GumboAST.GclMethod] = {
    val preR: PreResult[GumboAST.GclMethod] = preGumboASTGclMethod(o)
    val r: MOption[GumboAST.GclMethod] = if (preR.continu) {
      val o2: GumboAST.GclMethod = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      if (hasChanged)
        MSome(o2)
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: GumboAST.GclMethod = r.getOrElse(o)
    val postR: MOption[GumboAST.GclMethod] = postGumboASTGclMethod(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformGumboASTGclStateVar(o: GumboAST.GclStateVar): MOption[GumboAST.GclStateVar] = {
    val preR: PreResult[GumboAST.GclStateVar] = preGumboASTGclStateVar(o)
    val r: MOption[GumboAST.GclStateVar] = if (preR.continu) {
      val o2: GumboAST.GclStateVar = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      if (hasChanged)
        MSome(o2)
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: GumboAST.GclStateVar = r.getOrElse(o)
    val postR: MOption[GumboAST.GclStateVar] = postGumboASTGclStateVar(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformGumboASTGclClause(o: GumboAST.GclClause): MOption[GumboAST.GclClause] = {
    val preR: PreResult[GumboAST.GclClause] = preGumboASTGclClause(o)
    val r: MOption[GumboAST.GclClause] = if (preR.continu) {
      val o2: GumboAST.GclClause = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val rOpt: MOption[GumboAST.GclClause] = o2 match {
        case o2: GumboAST.GclInvariant =>
          val r0: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty)
            MSome(o2(attr = r0.getOrElse(o2.attr)))
          else
            MNone()
        case o2: GumboAST.GclAssume =>
          val r0: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty)
            MSome(o2(attr = r0.getOrElse(o2.attr)))
          else
            MNone()
        case o2: GumboAST.GclGuarantee =>
          val r0: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty)
            MSome(o2(attr = r0.getOrElse(o2.attr)))
          else
            MNone()
        case o2: GumboAST.InfoFlowClause =>
          val r0: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty)
            MSome(o2(attr = r0.getOrElse(o2.attr)))
          else
            MNone()
      }
      rOpt
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: GumboAST.GclClause = r.getOrElse(o)
    val postR: MOption[GumboAST.GclClause] = postGumboASTGclClause(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformGumboASTGclSpec(o: GumboAST.GclSpec): MOption[GumboAST.GclSpec] = {
    val preR: PreResult[GumboAST.GclSpec] = preGumboASTGclSpec(o)
    val r: MOption[GumboAST.GclSpec] = if (preR.continu) {
      val o2: GumboAST.GclSpec = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val rOpt: MOption[GumboAST.GclSpec] = o2 match {
        case o2: GumboAST.GclInvariant =>
          val r0: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty)
            MSome(o2(attr = r0.getOrElse(o2.attr)))
          else
            MNone()
        case o2: GumboAST.GclAssume =>
          val r0: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty)
            MSome(o2(attr = r0.getOrElse(o2.attr)))
          else
            MNone()
        case o2: GumboAST.GclGuarantee =>
          val r0: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty)
            MSome(o2(attr = r0.getOrElse(o2.attr)))
          else
            MNone()
      }
      rOpt
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: GumboAST.GclSpec = r.getOrElse(o)
    val postR: MOption[GumboAST.GclSpec] = postGumboASTGclSpec(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformGumboASTGclInvariant(o: GumboAST.GclInvariant): MOption[GumboAST.GclInvariant] = {
    val preR: PreResult[GumboAST.GclInvariant] = preGumboASTGclInvariant(o)
    val r: MOption[GumboAST.GclInvariant] = if (preR.continu) {
      val o2: GumboAST.GclInvariant = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[Attr] = transformAttr(o2.attr)
      if (hasChanged || r0.nonEmpty)
        MSome(o2(attr = r0.getOrElse(o2.attr)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: GumboAST.GclInvariant = r.getOrElse(o)
    val postR: MOption[GumboAST.GclInvariant] = postGumboASTGclInvariant(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformGumboASTGclComputeSpec(o: GumboAST.GclComputeSpec): MOption[GumboAST.GclComputeSpec] = {
    val preR: PreResult[GumboAST.GclComputeSpec] = preGumboASTGclComputeSpec(o)
    val r: MOption[GumboAST.GclComputeSpec] = if (preR.continu) {
      val o2: GumboAST.GclComputeSpec = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val rOpt: MOption[GumboAST.GclComputeSpec] = o2 match {
        case o2: GumboAST.GclAssume =>
          val r0: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty)
            MSome(o2(attr = r0.getOrElse(o2.attr)))
          else
            MNone()
        case o2: GumboAST.GclGuarantee =>
          val r0: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty)
            MSome(o2(attr = r0.getOrElse(o2.attr)))
          else
            MNone()
      }
      rOpt
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: GumboAST.GclComputeSpec = r.getOrElse(o)
    val postR: MOption[GumboAST.GclComputeSpec] = postGumboASTGclComputeSpec(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformGumboASTGclAssume(o: GumboAST.GclAssume): MOption[GumboAST.GclAssume] = {
    val preR: PreResult[GumboAST.GclAssume] = preGumboASTGclAssume(o)
    val r: MOption[GumboAST.GclAssume] = if (preR.continu) {
      val o2: GumboAST.GclAssume = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[Attr] = transformAttr(o2.attr)
      if (hasChanged || r0.nonEmpty)
        MSome(o2(attr = r0.getOrElse(o2.attr)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: GumboAST.GclAssume = r.getOrElse(o)
    val postR: MOption[GumboAST.GclAssume] = postGumboASTGclAssume(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformGumboASTGclGuarantee(o: GumboAST.GclGuarantee): MOption[GumboAST.GclGuarantee] = {
    val preR: PreResult[GumboAST.GclGuarantee] = preGumboASTGclGuarantee(o)
    val r: MOption[GumboAST.GclGuarantee] = if (preR.continu) {
      val o2: GumboAST.GclGuarantee = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[Attr] = transformAttr(o2.attr)
      if (hasChanged || r0.nonEmpty)
        MSome(o2(attr = r0.getOrElse(o2.attr)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: GumboAST.GclGuarantee = r.getOrElse(o)
    val postR: MOption[GumboAST.GclGuarantee] = postGumboASTGclGuarantee(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformGumboASTGclIntegration(o: GumboAST.GclIntegration): MOption[GumboAST.GclIntegration] = {
    val preR: PreResult[GumboAST.GclIntegration] = preGumboASTGclIntegration(o)
    val r: MOption[GumboAST.GclIntegration] = if (preR.continu) {
      val o2: GumboAST.GclIntegration = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[IS[Z, GumboAST.GclSpec]] = transformISZ(o2.specs, transformGumboASTGclSpec _)
      val r1: MOption[Attr] = transformAttr(o2.attr)
      if (hasChanged || r0.nonEmpty || r1.nonEmpty)
        MSome(o2(specs = r0.getOrElse(o2.specs), attr = r1.getOrElse(o2.attr)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: GumboAST.GclIntegration = r.getOrElse(o)
    val postR: MOption[GumboAST.GclIntegration] = postGumboASTGclIntegration(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformGumboASTGclCaseStatement(o: GumboAST.GclCaseStatement): MOption[GumboAST.GclCaseStatement] = {
    val preR: PreResult[GumboAST.GclCaseStatement] = preGumboASTGclCaseStatement(o)
    val r: MOption[GumboAST.GclCaseStatement] = if (preR.continu) {
      val o2: GumboAST.GclCaseStatement = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[Attr] = transformAttr(o2.attr)
      if (hasChanged || r0.nonEmpty)
        MSome(o2(attr = r0.getOrElse(o2.attr)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: GumboAST.GclCaseStatement = r.getOrElse(o)
    val postR: MOption[GumboAST.GclCaseStatement] = postGumboASTGclCaseStatement(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformGumboASTGclInitialize(o: GumboAST.GclInitialize): MOption[GumboAST.GclInitialize] = {
    val preR: PreResult[GumboAST.GclInitialize] = preGumboASTGclInitialize(o)
    val r: MOption[GumboAST.GclInitialize] = if (preR.continu) {
      val o2: GumboAST.GclInitialize = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[IS[Z, GumboAST.GclGuarantee]] = transformISZ(o2.guarantees, transformGumboASTGclGuarantee _)
      val r1: MOption[IS[Z, GumboAST.InfoFlowClause]] = transformISZ(o2.flows, transformGumboASTInfoFlowClause _)
      val r2: MOption[Attr] = transformAttr(o2.attr)
      if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty)
        MSome(o2(guarantees = r0.getOrElse(o2.guarantees), flows = r1.getOrElse(o2.flows), attr = r2.getOrElse(o2.attr)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: GumboAST.GclInitialize = r.getOrElse(o)
    val postR: MOption[GumboAST.GclInitialize] = postGumboASTGclInitialize(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformGumboASTGclCompute(o: GumboAST.GclCompute): MOption[GumboAST.GclCompute] = {
    val preR: PreResult[GumboAST.GclCompute] = preGumboASTGclCompute(o)
    val r: MOption[GumboAST.GclCompute] = if (preR.continu) {
      val o2: GumboAST.GclCompute = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[IS[Z, GumboAST.GclComputeSpec]] = transformISZ(o2.specs, transformGumboASTGclComputeSpec _)
      val r1: MOption[IS[Z, GumboAST.GclCaseStatement]] = transformISZ(o2.cases, transformGumboASTGclCaseStatement _)
      val r2: MOption[IS[Z, GumboAST.GclHandle]] = transformISZ(o2.handlers, transformGumboASTGclHandle _)
      val r3: MOption[IS[Z, GumboAST.InfoFlowClause]] = transformISZ(o2.flows, transformGumboASTInfoFlowClause _)
      val r4: MOption[Attr] = transformAttr(o2.attr)
      if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty)
        MSome(o2(specs = r0.getOrElse(o2.specs), cases = r1.getOrElse(o2.cases), handlers = r2.getOrElse(o2.handlers), flows = r3.getOrElse(o2.flows), attr = r4.getOrElse(o2.attr)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: GumboAST.GclCompute = r.getOrElse(o)
    val postR: MOption[GumboAST.GclCompute] = postGumboASTGclCompute(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformGumboASTGclHandle(o: GumboAST.GclHandle): MOption[GumboAST.GclHandle] = {
    val preR: PreResult[GumboAST.GclHandle] = preGumboASTGclHandle(o)
    val r: MOption[GumboAST.GclHandle] = if (preR.continu) {
      val o2: GumboAST.GclHandle = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[IS[Z, GumboAST.GclGuarantee]] = transformISZ(o2.guarantees, transformGumboASTGclGuarantee _)
      val r1: MOption[Attr] = transformAttr(o2.attr)
      if (hasChanged || r0.nonEmpty || r1.nonEmpty)
        MSome(o2(guarantees = r0.getOrElse(o2.guarantees), attr = r1.getOrElse(o2.attr)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: GumboAST.GclHandle = r.getOrElse(o)
    val postR: MOption[GumboAST.GclHandle] = postGumboASTGclHandle(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformGumboASTGclTODO(o: GumboAST.GclTODO): MOption[GumboAST.GclTODO] = {
    val preR: PreResult[GumboAST.GclTODO] = preGumboASTGclTODO(o)
    val r: MOption[GumboAST.GclTODO] = if (preR.continu) {
      val o2: GumboAST.GclTODO = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      if (hasChanged)
        MSome(o2)
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: GumboAST.GclTODO = r.getOrElse(o)
    val postR: MOption[GumboAST.GclTODO] = postGumboASTGclTODO(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformGumboASTGclLib(o: GumboAST.GclLib): MOption[GumboAST.GclLib] = {
    val preR: PreResult[GumboAST.GclLib] = preGumboASTGclLib(o)
    val r: MOption[GumboAST.GclLib] = if (preR.continu) {
      val o2: GumboAST.GclLib = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[SysmlAst.Name] = transformSysmlAstName(o2.containingPackage)
      val r1: MOption[IS[Z, GumboAST.GclMethod]] = transformISZ(o2.methods, transformGumboASTGclMethod _)
      val r2: MOption[Attr] = transformAttr(o2.attr)
      if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty)
        MSome(o2(containingPackage = r0.getOrElse(o2.containingPackage), methods = r1.getOrElse(o2.methods), attr = r2.getOrElse(o2.attr)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: GumboAST.GclLib = r.getOrElse(o)
    val postR: MOption[GumboAST.GclLib] = postGumboASTGclLib(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformGumboASTInfoFlowClause(o: GumboAST.InfoFlowClause): MOption[GumboAST.InfoFlowClause] = {
    val preR: PreResult[GumboAST.InfoFlowClause] = preGumboASTInfoFlowClause(o)
    val r: MOption[GumboAST.InfoFlowClause] = if (preR.continu) {
      val o2: GumboAST.InfoFlowClause = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[Attr] = transformAttr(o2.attr)
      if (hasChanged || r0.nonEmpty)
        MSome(o2(attr = r0.getOrElse(o2.attr)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: GumboAST.InfoFlowClause = r.getOrElse(o)
    val postR: MOption[GumboAST.InfoFlowClause] = postGumboASTInfoFlowClause(o2)
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

  def transformTypeNamed(o: Type.Named): MOption[Type.Named] = {
    val preR: PreResult[Type.Named] = preTypeNamed(o) match {
     case PreResult(continu, MSome(r: Type.Named)) => PreResult(continu, MSome[Type.Named](r))
     case PreResult(_, MSome(_)) => halt("Can only produce object of type Type.Named")
     case PreResult(continu, _) => PreResult(continu, MNone[Type.Named]())
    }
    val r: MOption[Type.Named] = if (preR.continu) {
      val o2: Type.Named = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[SysmlAst.Name] = transformSysmlAstName(o2.name)
      val r1: MOption[TypedAttr] = transformTypedAttr(o2.attr)
      if (hasChanged || r0.nonEmpty || r1.nonEmpty)
        MSome(o2(name = r0.getOrElse(o2.name), attr = r1.getOrElse(o2.attr)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: Type.Named = r.getOrElse(o)
    val postR: MOption[Type.Named] = postTypeNamed(o2) match {
     case MSome(result: Type.Named) => MSome[Type.Named](result)
     case MSome(_) => halt("Can only produce object of type Type.Named")
     case _ => MNone[Type.Named]()
    }
    if (postR.nonEmpty) {
      return postR
    } else if (hasChanged) {
      return MSome(o2)
    } else {
      return MNone()
    }
  }

}