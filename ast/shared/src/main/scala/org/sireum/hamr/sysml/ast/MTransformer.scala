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

  val PreResultSysmlAstAlias: PreResult[SysmlAst.Alias] = PreResult(T, MNone())

  val PostResultSysmlAstAlias: MOption[SysmlAst.Alias] = MNone()

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

  val PreResultSysmlAstOccurrenceDefinitionPrefix: PreResult[SysmlAst.OccurrenceDefinitionPrefix] = PreResult(T, MNone())

  val PostResultSysmlAstOccurrenceDefinitionPrefix: MOption[SysmlAst.OccurrenceDefinitionPrefix] = MNone()

  val PreResultSysmlAstPackage: PreResult[SysmlAst.Package] = PreResult(T, MNone())

  val PostResultSysmlAstPackage: MOption[SysmlAst.Package] = MNone()

  val PreResultSysmlAstAttributeDefinition: PreResult[SysmlAst.AttributeDefinition] = PreResult(T, MNone())

  val PostResultSysmlAstAttributeDefinition: MOption[SysmlAst.AttributeDefinition] = MNone()

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

  val PreResultSysmlAstUsagePrefix: PreResult[SysmlAst.UsagePrefix] = PreResult(T, MNone())

  val PostResultSysmlAstUsagePrefix: MOption[SysmlAst.UsagePrefix] = MNone()

  val PreResultSysmlAstOccurrenceUsagePrefix: PreResult[SysmlAst.OccurrenceUsagePrefix] = PreResult(T, MNone())

  val PostResultSysmlAstOccurrenceUsagePrefix: MOption[SysmlAst.OccurrenceUsagePrefix] = MNone()

  val PreResultSysmlAstRefPrefix: PreResult[SysmlAst.RefPrefix] = PreResult(T, MNone())

  val PostResultSysmlAstRefPrefix: MOption[SysmlAst.RefPrefix] = MNone()

  val PreResultSysmlAstAttributeUsage: PreResult[SysmlAst.AttributeUsage] = PreResult(T, MNone())

  val PostResultSysmlAstAttributeUsage: MOption[SysmlAst.AttributeUsage] = MNone()

  val PreResultSysmlAstReferenceUsage: PreResult[SysmlAst.ReferenceUsage] = PreResult(T, MNone())

  val PostResultSysmlAstReferenceUsage: MOption[SysmlAst.ReferenceUsage] = MNone()

  val PreResultSysmlAstDefaultReferenceUsage: PreResult[SysmlAst.DefaultReferenceUsage] = PreResult(T, MNone())

  val PostResultSysmlAstDefaultReferenceUsage: MOption[SysmlAst.DefaultReferenceUsage] = MNone()

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

  val PreResultAttr: PreResult[Attr] = PreResult(T, MNone())

  val PostResultAttr: MOption[Attr] = MNone()

  val PreResultResolvedAttr: PreResult[ResolvedAttr] = PreResult(T, MNone())

  val PostResultResolvedAttr: MOption[ResolvedAttr] = MNone()

  val PreResultTypeNamed: PreResult[Type] = PreResult(T, MNone())

  val PostResultTypeNamed: MOption[Type] = MNone()

  val PreResultTypedAttr: PreResult[TypedAttr] = PreResult(T, MNone())

  val PostResultTypedAttr: MOption[TypedAttr] = MNone()

  val PreResultResolvedInfoPackage: PreResult[ResolvedInfo] = PreResult(T, MNone())

  val PostResultResolvedInfoPackage: MOption[ResolvedInfo] = MNone()

  val PreResultResolvedInfoEnum: PreResult[ResolvedInfo] = PreResult(T, MNone())

  val PostResultResolvedInfoEnum: MOption[ResolvedInfo] = MNone()

  val PreResultResolvedInfoEnumElement: PreResult[ResolvedInfo] = PreResult(T, MNone())

  val PostResultResolvedInfoEnumElement: MOption[ResolvedInfo] = MNone()

  val PreResultResolvedInfoAttributeUsage: PreResult[ResolvedInfo] = PreResult(T, MNone())

  val PostResultResolvedInfoAttributeUsage: MOption[ResolvedInfo] = MNone()

  val PreResultResolvedInfoItemUsage: PreResult[ResolvedInfo] = PreResult(T, MNone())

  val PostResultResolvedInfoItemUsage: MOption[ResolvedInfo] = MNone()

  val PreResultResolvedInfoPartUsage: PreResult[ResolvedInfo] = PreResult(T, MNone())

  val PostResultResolvedInfoPartUsage: MOption[ResolvedInfo] = MNone()

  val PreResultResolvedInfoPortUsage: PreResult[ResolvedInfo] = PreResult(T, MNone())

  val PostResultResolvedInfoPortUsage: MOption[ResolvedInfo] = MNone()

  val PreResultResolvedInfoConnectionUsage: PreResult[ResolvedInfo] = PreResult(T, MNone())

  val PostResultResolvedInfoConnectionUsage: MOption[ResolvedInfo] = MNone()

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
      case o: SysmlAst.Alias =>
        val r: PreResult[SysmlAst.AttrNode] = preSysmlAstAlias(o) match {
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
      case o: SysmlAst.DefaultReferenceUsage =>
        val r: PreResult[SysmlAst.AttrNode] = preSysmlAstDefaultReferenceUsage(o) match {
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
      case o: SysmlAst.Alias =>
        val r: PreResult[SysmlAst.PackageBodyElement] = preSysmlAstAlias(o) match {
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
      case o: SysmlAst.DefaultReferenceUsage =>
        val r: PreResult[SysmlAst.PackageBodyElement] = preSysmlAstDefaultReferenceUsage(o) match {
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
    }
  }

  def preSysmlAstBodyElement(o: SysmlAst.BodyElement): PreResult[SysmlAst.BodyElement] = {
    o match {
      case o: SysmlAst.Import =>
        val r: PreResult[SysmlAst.BodyElement] = preSysmlAstImport(o) match {
         case PreResult(continu, MSome(r: SysmlAst.BodyElement)) => PreResult(continu, MSome[SysmlAst.BodyElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.BodyElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.BodyElement]())
        }
        return r
      case o: SysmlAst.Alias =>
        val r: PreResult[SysmlAst.BodyElement] = preSysmlAstAlias(o) match {
         case PreResult(continu, MSome(r: SysmlAst.BodyElement)) => PreResult(continu, MSome[SysmlAst.BodyElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.BodyElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.BodyElement]())
        }
        return r
      case o: SysmlAst.AttributeUsage =>
        val r: PreResult[SysmlAst.BodyElement] = preSysmlAstAttributeUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.BodyElement)) => PreResult(continu, MSome[SysmlAst.BodyElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.BodyElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.BodyElement]())
        }
        return r
      case o: SysmlAst.ReferenceUsage =>
        val r: PreResult[SysmlAst.BodyElement] = preSysmlAstReferenceUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.BodyElement)) => PreResult(continu, MSome[SysmlAst.BodyElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.BodyElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.BodyElement]())
        }
        return r
      case o: SysmlAst.DefaultReferenceUsage =>
        val r: PreResult[SysmlAst.BodyElement] = preSysmlAstDefaultReferenceUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.BodyElement)) => PreResult(continu, MSome[SysmlAst.BodyElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.BodyElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.BodyElement]())
        }
        return r
      case o: SysmlAst.ConnectionUsage =>
        val r: PreResult[SysmlAst.BodyElement] = preSysmlAstConnectionUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.BodyElement)) => PreResult(continu, MSome[SysmlAst.BodyElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.BodyElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.BodyElement]())
        }
        return r
      case o: SysmlAst.ItemUsage =>
        val r: PreResult[SysmlAst.BodyElement] = preSysmlAstItemUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.BodyElement)) => PreResult(continu, MSome[SysmlAst.BodyElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.BodyElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.BodyElement]())
        }
        return r
      case o: SysmlAst.PartUsage =>
        val r: PreResult[SysmlAst.BodyElement] = preSysmlAstPartUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.BodyElement)) => PreResult(continu, MSome[SysmlAst.BodyElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.BodyElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.BodyElement]())
        }
        return r
      case o: SysmlAst.PortUsage =>
        val r: PreResult[SysmlAst.BodyElement] = preSysmlAstPortUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.BodyElement)) => PreResult(continu, MSome[SysmlAst.BodyElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.BodyElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.BodyElement]())
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

  def preSysmlAstAlias(o: SysmlAst.Alias): PreResult[SysmlAst.Alias] = {
    return PreResultSysmlAstAlias
  }

  def preSysmlAstIdentification(o: SysmlAst.Identification): PreResult[SysmlAst.Identification] = {
    return PreResultSysmlAstIdentification
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
    }
  }

  def preSysmlAstDefinitionPrefix(o: SysmlAst.DefinitionPrefix): PreResult[SysmlAst.DefinitionPrefix] = {
    return PreResultSysmlAstDefinitionPrefix
  }

  def preSysmlAstOccurrenceDefinitionPrefix(o: SysmlAst.OccurrenceDefinitionPrefix): PreResult[SysmlAst.OccurrenceDefinitionPrefix] = {
    return PreResultSysmlAstOccurrenceDefinitionPrefix
  }

  def preSysmlAstPackage(o: SysmlAst.Package): PreResult[SysmlAst.Package] = {
    return PreResultSysmlAstPackage
  }

  def preSysmlAstAttributeDefinition(o: SysmlAst.AttributeDefinition): PreResult[SysmlAst.AttributeDefinition] = {
    return PreResultSysmlAstAttributeDefinition
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

  def preSysmlAstUsagePrefix(o: SysmlAst.UsagePrefix): PreResult[SysmlAst.UsagePrefix] = {
    return PreResultSysmlAstUsagePrefix
  }

  def preSysmlAstOccurrenceUsagePrefix(o: SysmlAst.OccurrenceUsagePrefix): PreResult[SysmlAst.OccurrenceUsagePrefix] = {
    return PreResultSysmlAstOccurrenceUsagePrefix
  }

  def preSysmlAstRefPrefix(o: SysmlAst.RefPrefix): PreResult[SysmlAst.RefPrefix] = {
    return PreResultSysmlAstRefPrefix
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
      case o: SysmlAst.DefaultReferenceUsage =>
        val r: PreResult[SysmlAst.UsageElement] = preSysmlAstDefaultReferenceUsage(o) match {
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
      case o: SysmlAst.DefaultReferenceUsage =>
        val r: PreResult[SysmlAst.NonOccurrenceUsageElement] = preSysmlAstDefaultReferenceUsage(o) match {
         case PreResult(continu, MSome(r: SysmlAst.NonOccurrenceUsageElement)) => PreResult(continu, MSome[SysmlAst.NonOccurrenceUsageElement](r))
         case PreResult(_, MSome(_)) => halt("Can only produce object of type SysmlAst.NonOccurrenceUsageElement")
         case PreResult(continu, _) => PreResult(continu, MNone[SysmlAst.NonOccurrenceUsageElement]())
        }
        return r
    }
  }

  def preSysmlAstAttributeUsage(o: SysmlAst.AttributeUsage): PreResult[SysmlAst.AttributeUsage] = {
    return PreResultSysmlAstAttributeUsage
  }

  def preSysmlAstReferenceUsage(o: SysmlAst.ReferenceUsage): PreResult[SysmlAst.ReferenceUsage] = {
    return PreResultSysmlAstReferenceUsage
  }

  def preSysmlAstDefaultReferenceUsage(o: SysmlAst.DefaultReferenceUsage): PreResult[SysmlAst.DefaultReferenceUsage] = {
    return PreResultSysmlAstDefaultReferenceUsage
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
      case o: ResolvedInfo.ItemUsage => return preResolvedInfoItemUsage(o)
      case o: ResolvedInfo.PartUsage => return preResolvedInfoPartUsage(o)
      case o: ResolvedInfo.PortUsage => return preResolvedInfoPortUsage(o)
      case o: ResolvedInfo.ConnectionUsage => return preResolvedInfoConnectionUsage(o)
    }
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

  def preResolvedInfoItemUsage(o: ResolvedInfo.ItemUsage): PreResult[ResolvedInfo] = {
    return PreResultResolvedInfoItemUsage
  }

  def preResolvedInfoPartUsage(o: ResolvedInfo.PartUsage): PreResult[ResolvedInfo] = {
    return PreResultResolvedInfoPartUsage
  }

  def preResolvedInfoPortUsage(o: ResolvedInfo.PortUsage): PreResult[ResolvedInfo] = {
    return PreResultResolvedInfoPortUsage
  }

  def preResolvedInfoConnectionUsage(o: ResolvedInfo.ConnectionUsage): PreResult[ResolvedInfo] = {
    return PreResultResolvedInfoConnectionUsage
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
      case o: SysmlAst.Alias =>
        val r: MOption[SysmlAst.AttrNode] = postSysmlAstAlias(o) match {
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
      case o: SysmlAst.DefaultReferenceUsage =>
        val r: MOption[SysmlAst.AttrNode] = postSysmlAstDefaultReferenceUsage(o) match {
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
      case o: SysmlAst.Alias =>
        val r: MOption[SysmlAst.PackageBodyElement] = postSysmlAstAlias(o) match {
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
      case o: SysmlAst.DefaultReferenceUsage =>
        val r: MOption[SysmlAst.PackageBodyElement] = postSysmlAstDefaultReferenceUsage(o) match {
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
    }
  }

  def postSysmlAstBodyElement(o: SysmlAst.BodyElement): MOption[SysmlAst.BodyElement] = {
    o match {
      case o: SysmlAst.Import =>
        val r: MOption[SysmlAst.BodyElement] = postSysmlAstImport(o) match {
         case MSome(result: SysmlAst.BodyElement) => MSome[SysmlAst.BodyElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.BodyElement")
         case _ => MNone[SysmlAst.BodyElement]()
        }
        return r
      case o: SysmlAst.Alias =>
        val r: MOption[SysmlAst.BodyElement] = postSysmlAstAlias(o) match {
         case MSome(result: SysmlAst.BodyElement) => MSome[SysmlAst.BodyElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.BodyElement")
         case _ => MNone[SysmlAst.BodyElement]()
        }
        return r
      case o: SysmlAst.AttributeUsage =>
        val r: MOption[SysmlAst.BodyElement] = postSysmlAstAttributeUsage(o) match {
         case MSome(result: SysmlAst.BodyElement) => MSome[SysmlAst.BodyElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.BodyElement")
         case _ => MNone[SysmlAst.BodyElement]()
        }
        return r
      case o: SysmlAst.ReferenceUsage =>
        val r: MOption[SysmlAst.BodyElement] = postSysmlAstReferenceUsage(o) match {
         case MSome(result: SysmlAst.BodyElement) => MSome[SysmlAst.BodyElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.BodyElement")
         case _ => MNone[SysmlAst.BodyElement]()
        }
        return r
      case o: SysmlAst.DefaultReferenceUsage =>
        val r: MOption[SysmlAst.BodyElement] = postSysmlAstDefaultReferenceUsage(o) match {
         case MSome(result: SysmlAst.BodyElement) => MSome[SysmlAst.BodyElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.BodyElement")
         case _ => MNone[SysmlAst.BodyElement]()
        }
        return r
      case o: SysmlAst.ConnectionUsage =>
        val r: MOption[SysmlAst.BodyElement] = postSysmlAstConnectionUsage(o) match {
         case MSome(result: SysmlAst.BodyElement) => MSome[SysmlAst.BodyElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.BodyElement")
         case _ => MNone[SysmlAst.BodyElement]()
        }
        return r
      case o: SysmlAst.ItemUsage =>
        val r: MOption[SysmlAst.BodyElement] = postSysmlAstItemUsage(o) match {
         case MSome(result: SysmlAst.BodyElement) => MSome[SysmlAst.BodyElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.BodyElement")
         case _ => MNone[SysmlAst.BodyElement]()
        }
        return r
      case o: SysmlAst.PartUsage =>
        val r: MOption[SysmlAst.BodyElement] = postSysmlAstPartUsage(o) match {
         case MSome(result: SysmlAst.BodyElement) => MSome[SysmlAst.BodyElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.BodyElement")
         case _ => MNone[SysmlAst.BodyElement]()
        }
        return r
      case o: SysmlAst.PortUsage =>
        val r: MOption[SysmlAst.BodyElement] = postSysmlAstPortUsage(o) match {
         case MSome(result: SysmlAst.BodyElement) => MSome[SysmlAst.BodyElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.BodyElement")
         case _ => MNone[SysmlAst.BodyElement]()
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

  def postSysmlAstAlias(o: SysmlAst.Alias): MOption[SysmlAst.Alias] = {
    return PostResultSysmlAstAlias
  }

  def postSysmlAstIdentification(o: SysmlAst.Identification): MOption[SysmlAst.Identification] = {
    return PostResultSysmlAstIdentification
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
    }
  }

  def postSysmlAstDefinitionPrefix(o: SysmlAst.DefinitionPrefix): MOption[SysmlAst.DefinitionPrefix] = {
    return PostResultSysmlAstDefinitionPrefix
  }

  def postSysmlAstOccurrenceDefinitionPrefix(o: SysmlAst.OccurrenceDefinitionPrefix): MOption[SysmlAst.OccurrenceDefinitionPrefix] = {
    return PostResultSysmlAstOccurrenceDefinitionPrefix
  }

  def postSysmlAstPackage(o: SysmlAst.Package): MOption[SysmlAst.Package] = {
    return PostResultSysmlAstPackage
  }

  def postSysmlAstAttributeDefinition(o: SysmlAst.AttributeDefinition): MOption[SysmlAst.AttributeDefinition] = {
    return PostResultSysmlAstAttributeDefinition
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

  def postSysmlAstUsagePrefix(o: SysmlAst.UsagePrefix): MOption[SysmlAst.UsagePrefix] = {
    return PostResultSysmlAstUsagePrefix
  }

  def postSysmlAstOccurrenceUsagePrefix(o: SysmlAst.OccurrenceUsagePrefix): MOption[SysmlAst.OccurrenceUsagePrefix] = {
    return PostResultSysmlAstOccurrenceUsagePrefix
  }

  def postSysmlAstRefPrefix(o: SysmlAst.RefPrefix): MOption[SysmlAst.RefPrefix] = {
    return PostResultSysmlAstRefPrefix
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
      case o: SysmlAst.DefaultReferenceUsage =>
        val r: MOption[SysmlAst.UsageElement] = postSysmlAstDefaultReferenceUsage(o) match {
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
      case o: SysmlAst.DefaultReferenceUsage =>
        val r: MOption[SysmlAst.NonOccurrenceUsageElement] = postSysmlAstDefaultReferenceUsage(o) match {
         case MSome(result: SysmlAst.NonOccurrenceUsageElement) => MSome[SysmlAst.NonOccurrenceUsageElement](result)
         case MSome(_) => halt("Can only produce object of type SysmlAst.NonOccurrenceUsageElement")
         case _ => MNone[SysmlAst.NonOccurrenceUsageElement]()
        }
        return r
    }
  }

  def postSysmlAstAttributeUsage(o: SysmlAst.AttributeUsage): MOption[SysmlAst.AttributeUsage] = {
    return PostResultSysmlAstAttributeUsage
  }

  def postSysmlAstReferenceUsage(o: SysmlAst.ReferenceUsage): MOption[SysmlAst.ReferenceUsage] = {
    return PostResultSysmlAstReferenceUsage
  }

  def postSysmlAstDefaultReferenceUsage(o: SysmlAst.DefaultReferenceUsage): MOption[SysmlAst.DefaultReferenceUsage] = {
    return PostResultSysmlAstDefaultReferenceUsage
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
      case o: ResolvedInfo.ItemUsage => return postResolvedInfoItemUsage(o)
      case o: ResolvedInfo.PartUsage => return postResolvedInfoPartUsage(o)
      case o: ResolvedInfo.PortUsage => return postResolvedInfoPortUsage(o)
      case o: ResolvedInfo.ConnectionUsage => return postResolvedInfoConnectionUsage(o)
    }
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

  def postResolvedInfoItemUsage(o: ResolvedInfo.ItemUsage): MOption[ResolvedInfo] = {
    return PostResultResolvedInfoItemUsage
  }

  def postResolvedInfoPartUsage(o: ResolvedInfo.PartUsage): MOption[ResolvedInfo] = {
    return PostResultResolvedInfoPartUsage
  }

  def postResolvedInfoPortUsage(o: ResolvedInfo.PortUsage): MOption[ResolvedInfo] = {
    return PostResultResolvedInfoPortUsage
  }

  def postResolvedInfoConnectionUsage(o: ResolvedInfo.ConnectionUsage): MOption[ResolvedInfo] = {
    return PostResultResolvedInfoConnectionUsage
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
        case o2: SysmlAst.Alias =>
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
          val r3: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.bodyItems, transformSysmlAstBodyElement _)
          val r4: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty)
            MSome(o2(defPrefix = r0.getOrElse(o2.defPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), bodyItems = r3.getOrElse(o2.bodyItems), attr = r4.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.AllocationDefinition =>
          val r0: MOption[SysmlAst.OccurrenceDefinitionPrefix] = transformSysmlAstOccurrenceDefinitionPrefix(o2.occurrenceDefPrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r3: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.bodyItems, transformSysmlAstBodyElement _)
          val r4: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty)
            MSome(o2(occurrenceDefPrefix = r0.getOrElse(o2.occurrenceDefPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), bodyItems = r3.getOrElse(o2.bodyItems), attr = r4.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.ConnectionDefinition =>
          val r0: MOption[SysmlAst.OccurrenceDefinitionPrefix] = transformSysmlAstOccurrenceDefinitionPrefix(o2.occurrenceDefPrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r3: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.bodyItems, transformSysmlAstBodyElement _)
          val r4: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty)
            MSome(o2(occurrenceDefPrefix = r0.getOrElse(o2.occurrenceDefPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), bodyItems = r3.getOrElse(o2.bodyItems), attr = r4.getOrElse(o2.attr)))
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
          val r3: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.bodyItems, transformSysmlAstBodyElement _)
          val r4: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty)
            MSome(o2(occurrenceDefPrefix = r0.getOrElse(o2.occurrenceDefPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), bodyItems = r3.getOrElse(o2.bodyItems), attr = r4.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.PortDefinition =>
          val r0: MOption[SysmlAst.DefinitionPrefix] = transformSysmlAstDefinitionPrefix(o2.defPrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r3: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.bodyItems, transformSysmlAstBodyElement _)
          val r4: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty)
            MSome(o2(defPrefix = r0.getOrElse(o2.defPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), bodyItems = r3.getOrElse(o2.bodyItems), attr = r4.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.MetadataDefinition =>
          val r0: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty)
            MSome(o2(attr = r0.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.AttributeUsage =>
          val r0: MOption[SysmlAst.UsagePrefix] = transformSysmlAstUsagePrefix(o2.prefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(prefix = r0.getOrElse(o2.prefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.ReferenceUsage =>
          val r0: MOption[SysmlAst.RefPrefix] = transformSysmlAstRefPrefix(o2.prefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(prefix = r0.getOrElse(o2.prefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.DefaultReferenceUsage =>
          val r0: MOption[SysmlAst.RefPrefix] = transformSysmlAstRefPrefix(o2.prefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(prefix = r0.getOrElse(o2.prefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.ConnectionUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[Option[SysmlAst.ConnectorPart]] = transformOption(o2.connectorPart, transformSysmlAstConnectorPart _)
          val r5: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), connectorPart = r4.getOrElse(o2.connectorPart), definitionBodyItems = r5.getOrElse(o2.definitionBodyItems), attr = r6.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.ItemUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.PartUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.PortUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.Comment =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.id, transformSysmlAstIdentification _)
          val r1: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.abouts, transformSysmlAstName _)
          val r2: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty)
            MSome(o2(id = r0.getOrElse(o2.id), abouts = r1.getOrElse(o2.abouts), attr = r2.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.Documentation =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.id, transformSysmlAstIdentification _)
          val r1: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty)
            MSome(o2(id = r0.getOrElse(o2.id), attr = r1.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.TextualRepresentation =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.id, transformSysmlAstIdentification _)
          val r1: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty)
            MSome(o2(id = r0.getOrElse(o2.id), attr = r1.getOrElse(o2.attr)))
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
        case o2: SysmlAst.Alias =>
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
          val r3: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.bodyItems, transformSysmlAstBodyElement _)
          val r4: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty)
            MSome(o2(defPrefix = r0.getOrElse(o2.defPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), bodyItems = r3.getOrElse(o2.bodyItems), attr = r4.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.AllocationDefinition =>
          val r0: MOption[SysmlAst.OccurrenceDefinitionPrefix] = transformSysmlAstOccurrenceDefinitionPrefix(o2.occurrenceDefPrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r3: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.bodyItems, transformSysmlAstBodyElement _)
          val r4: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty)
            MSome(o2(occurrenceDefPrefix = r0.getOrElse(o2.occurrenceDefPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), bodyItems = r3.getOrElse(o2.bodyItems), attr = r4.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.ConnectionDefinition =>
          val r0: MOption[SysmlAst.OccurrenceDefinitionPrefix] = transformSysmlAstOccurrenceDefinitionPrefix(o2.occurrenceDefPrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r3: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.bodyItems, transformSysmlAstBodyElement _)
          val r4: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty)
            MSome(o2(occurrenceDefPrefix = r0.getOrElse(o2.occurrenceDefPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), bodyItems = r3.getOrElse(o2.bodyItems), attr = r4.getOrElse(o2.attr)))
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
          val r3: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.bodyItems, transformSysmlAstBodyElement _)
          val r4: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty)
            MSome(o2(occurrenceDefPrefix = r0.getOrElse(o2.occurrenceDefPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), bodyItems = r3.getOrElse(o2.bodyItems), attr = r4.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.PortDefinition =>
          val r0: MOption[SysmlAst.DefinitionPrefix] = transformSysmlAstDefinitionPrefix(o2.defPrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r3: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.bodyItems, transformSysmlAstBodyElement _)
          val r4: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty)
            MSome(o2(defPrefix = r0.getOrElse(o2.defPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), bodyItems = r3.getOrElse(o2.bodyItems), attr = r4.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.MetadataDefinition =>
          val r0: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty)
            MSome(o2(attr = r0.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.AttributeUsage =>
          val r0: MOption[SysmlAst.UsagePrefix] = transformSysmlAstUsagePrefix(o2.prefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(prefix = r0.getOrElse(o2.prefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.ReferenceUsage =>
          val r0: MOption[SysmlAst.RefPrefix] = transformSysmlAstRefPrefix(o2.prefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(prefix = r0.getOrElse(o2.prefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.DefaultReferenceUsage =>
          val r0: MOption[SysmlAst.RefPrefix] = transformSysmlAstRefPrefix(o2.prefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(prefix = r0.getOrElse(o2.prefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.ConnectionUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[Option[SysmlAst.ConnectorPart]] = transformOption(o2.connectorPart, transformSysmlAstConnectorPart _)
          val r5: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), connectorPart = r4.getOrElse(o2.connectorPart), definitionBodyItems = r5.getOrElse(o2.definitionBodyItems), attr = r6.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.ItemUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.PartUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.PortUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.Comment =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.id, transformSysmlAstIdentification _)
          val r1: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.abouts, transformSysmlAstName _)
          val r2: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty)
            MSome(o2(id = r0.getOrElse(o2.id), abouts = r1.getOrElse(o2.abouts), attr = r2.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.Documentation =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.id, transformSysmlAstIdentification _)
          val r1: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty)
            MSome(o2(id = r0.getOrElse(o2.id), attr = r1.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.TextualRepresentation =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.id, transformSysmlAstIdentification _)
          val r1: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty)
            MSome(o2(id = r0.getOrElse(o2.id), attr = r1.getOrElse(o2.attr)))
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

  def transformSysmlAstBodyElement(o: SysmlAst.BodyElement): MOption[SysmlAst.BodyElement] = {
    val preR: PreResult[SysmlAst.BodyElement] = preSysmlAstBodyElement(o)
    val r: MOption[SysmlAst.BodyElement] = if (preR.continu) {
      val o2: SysmlAst.BodyElement = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val rOpt: MOption[SysmlAst.BodyElement] = o2 match {
        case o2: SysmlAst.Import =>
          val r0: MOption[SysmlAst.Name] = transformSysmlAstName(o2.name)
          val r1: MOption[IS[Z, SysmlAst.AnnotatingElement]] = transformISZ(o2.annotations, transformSysmlAstAnnotatingElement _)
          val r2: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty)
            MSome(o2(name = r0.getOrElse(o2.name), annotations = r1.getOrElse(o2.annotations), attr = r2.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.Alias =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r1: MOption[SysmlAst.Name] = transformSysmlAstName(o2.target)
          val r2: MOption[IS[Z, SysmlAst.AnnotatingElement]] = transformISZ(o2.annotations, transformSysmlAstAnnotatingElement _)
          val r3: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty)
            MSome(o2(identification = r0.getOrElse(o2.identification), target = r1.getOrElse(o2.target), annotations = r2.getOrElse(o2.annotations), attr = r3.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.AttributeUsage =>
          val r0: MOption[SysmlAst.UsagePrefix] = transformSysmlAstUsagePrefix(o2.prefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(prefix = r0.getOrElse(o2.prefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.ReferenceUsage =>
          val r0: MOption[SysmlAst.RefPrefix] = transformSysmlAstRefPrefix(o2.prefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(prefix = r0.getOrElse(o2.prefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.DefaultReferenceUsage =>
          val r0: MOption[SysmlAst.RefPrefix] = transformSysmlAstRefPrefix(o2.prefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(prefix = r0.getOrElse(o2.prefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.ConnectionUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[Option[SysmlAst.ConnectorPart]] = transformOption(o2.connectorPart, transformSysmlAstConnectorPart _)
          val r5: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), connectorPart = r4.getOrElse(o2.connectorPart), definitionBodyItems = r5.getOrElse(o2.definitionBodyItems), attr = r6.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.ItemUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.PartUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.PortUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), attr = r5.getOrElse(o2.attr)))
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
    val o2: SysmlAst.BodyElement = r.getOrElse(o)
    val postR: MOption[SysmlAst.BodyElement] = postSysmlAstBodyElement(o2)
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
      val r2: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.definitionBodyItems, transformSysmlAstBodyElement _)
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

  def transformSysmlAstAlias(o: SysmlAst.Alias): MOption[SysmlAst.Alias] = {
    val preR: PreResult[SysmlAst.Alias] = preSysmlAstAlias(o)
    val r: MOption[SysmlAst.Alias] = if (preR.continu) {
      val o2: SysmlAst.Alias = preR.resultOpt.getOrElse(o)
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
    val o2: SysmlAst.Alias = r.getOrElse(o)
    val postR: MOption[SysmlAst.Alias] = postSysmlAstAlias(o2)
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
          val r3: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.bodyItems, transformSysmlAstBodyElement _)
          val r4: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty)
            MSome(o2(defPrefix = r0.getOrElse(o2.defPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), bodyItems = r3.getOrElse(o2.bodyItems), attr = r4.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.AllocationDefinition =>
          val r0: MOption[SysmlAst.OccurrenceDefinitionPrefix] = transformSysmlAstOccurrenceDefinitionPrefix(o2.occurrenceDefPrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r3: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.bodyItems, transformSysmlAstBodyElement _)
          val r4: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty)
            MSome(o2(occurrenceDefPrefix = r0.getOrElse(o2.occurrenceDefPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), bodyItems = r3.getOrElse(o2.bodyItems), attr = r4.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.ConnectionDefinition =>
          val r0: MOption[SysmlAst.OccurrenceDefinitionPrefix] = transformSysmlAstOccurrenceDefinitionPrefix(o2.occurrenceDefPrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r3: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.bodyItems, transformSysmlAstBodyElement _)
          val r4: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty)
            MSome(o2(occurrenceDefPrefix = r0.getOrElse(o2.occurrenceDefPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), bodyItems = r3.getOrElse(o2.bodyItems), attr = r4.getOrElse(o2.attr)))
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
          val r3: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.bodyItems, transformSysmlAstBodyElement _)
          val r4: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty)
            MSome(o2(occurrenceDefPrefix = r0.getOrElse(o2.occurrenceDefPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), bodyItems = r3.getOrElse(o2.bodyItems), attr = r4.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.PortDefinition =>
          val r0: MOption[SysmlAst.DefinitionPrefix] = transformSysmlAstDefinitionPrefix(o2.defPrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
          val r3: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.bodyItems, transformSysmlAstBodyElement _)
          val r4: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty)
            MSome(o2(defPrefix = r0.getOrElse(o2.defPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), bodyItems = r3.getOrElse(o2.bodyItems), attr = r4.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.MetadataDefinition =>
          val r0: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty)
            MSome(o2(attr = r0.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.Comment =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.id, transformSysmlAstIdentification _)
          val r1: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.abouts, transformSysmlAstName _)
          val r2: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty)
            MSome(o2(id = r0.getOrElse(o2.id), abouts = r1.getOrElse(o2.abouts), attr = r2.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.Documentation =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.id, transformSysmlAstIdentification _)
          val r1: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty)
            MSome(o2(id = r0.getOrElse(o2.id), attr = r1.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.TextualRepresentation =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.id, transformSysmlAstIdentification _)
          val r1: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty)
            MSome(o2(id = r0.getOrElse(o2.id), attr = r1.getOrElse(o2.attr)))
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
      val r3: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.bodyItems, transformSysmlAstBodyElement _)
      val r4: MOption[Attr] = transformAttr(o2.attr)
      if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty)
        MSome(o2(defPrefix = r0.getOrElse(o2.defPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), bodyItems = r3.getOrElse(o2.bodyItems), attr = r4.getOrElse(o2.attr)))
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

  def transformSysmlAstAllocationDefinition(o: SysmlAst.AllocationDefinition): MOption[SysmlAst.AllocationDefinition] = {
    val preR: PreResult[SysmlAst.AllocationDefinition] = preSysmlAstAllocationDefinition(o)
    val r: MOption[SysmlAst.AllocationDefinition] = if (preR.continu) {
      val o2: SysmlAst.AllocationDefinition = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[SysmlAst.OccurrenceDefinitionPrefix] = transformSysmlAstOccurrenceDefinitionPrefix(o2.occurrenceDefPrefix)
      val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
      val r2: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.subClassifications, transformSysmlAstName _)
      val r3: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.bodyItems, transformSysmlAstBodyElement _)
      val r4: MOption[Attr] = transformAttr(o2.attr)
      if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty)
        MSome(o2(occurrenceDefPrefix = r0.getOrElse(o2.occurrenceDefPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), bodyItems = r3.getOrElse(o2.bodyItems), attr = r4.getOrElse(o2.attr)))
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
      val r3: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.bodyItems, transformSysmlAstBodyElement _)
      val r4: MOption[Attr] = transformAttr(o2.attr)
      if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty)
        MSome(o2(occurrenceDefPrefix = r0.getOrElse(o2.occurrenceDefPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), bodyItems = r3.getOrElse(o2.bodyItems), attr = r4.getOrElse(o2.attr)))
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
      val r3: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.bodyItems, transformSysmlAstBodyElement _)
      val r4: MOption[Attr] = transformAttr(o2.attr)
      if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty)
        MSome(o2(occurrenceDefPrefix = r0.getOrElse(o2.occurrenceDefPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), bodyItems = r3.getOrElse(o2.bodyItems), attr = r4.getOrElse(o2.attr)))
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
      val r3: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.bodyItems, transformSysmlAstBodyElement _)
      val r4: MOption[Attr] = transformAttr(o2.attr)
      if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty)
        MSome(o2(defPrefix = r0.getOrElse(o2.defPrefix), identification = r1.getOrElse(o2.identification), subClassifications = r2.getOrElse(o2.subClassifications), bodyItems = r3.getOrElse(o2.bodyItems), attr = r4.getOrElse(o2.attr)))
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
          val r4: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(prefix = r0.getOrElse(o2.prefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.ReferenceUsage =>
          val r0: MOption[SysmlAst.RefPrefix] = transformSysmlAstRefPrefix(o2.prefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(prefix = r0.getOrElse(o2.prefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.DefaultReferenceUsage =>
          val r0: MOption[SysmlAst.RefPrefix] = transformSysmlAstRefPrefix(o2.prefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(prefix = r0.getOrElse(o2.prefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.ConnectionUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[Option[SysmlAst.ConnectorPart]] = transformOption(o2.connectorPart, transformSysmlAstConnectorPart _)
          val r5: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), connectorPart = r4.getOrElse(o2.connectorPart), definitionBodyItems = r5.getOrElse(o2.definitionBodyItems), attr = r6.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.ItemUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.PartUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.PortUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), attr = r5.getOrElse(o2.attr)))
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
          val r4: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(prefix = r0.getOrElse(o2.prefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.ReferenceUsage =>
          val r0: MOption[SysmlAst.RefPrefix] = transformSysmlAstRefPrefix(o2.prefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(prefix = r0.getOrElse(o2.prefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.DefaultReferenceUsage =>
          val r0: MOption[SysmlAst.RefPrefix] = transformSysmlAstRefPrefix(o2.prefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(prefix = r0.getOrElse(o2.prefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), attr = r5.getOrElse(o2.attr)))
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

  def transformSysmlAstAttributeUsage(o: SysmlAst.AttributeUsage): MOption[SysmlAst.AttributeUsage] = {
    val preR: PreResult[SysmlAst.AttributeUsage] = preSysmlAstAttributeUsage(o)
    val r: MOption[SysmlAst.AttributeUsage] = if (preR.continu) {
      val o2: SysmlAst.AttributeUsage = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[SysmlAst.UsagePrefix] = transformSysmlAstUsagePrefix(o2.prefix)
      val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
      val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
      val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
      val r4: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.definitionBodyItems, transformSysmlAstBodyElement _)
      val r5: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
      if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
        MSome(o2(prefix = r0.getOrElse(o2.prefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), attr = r5.getOrElse(o2.attr)))
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
      val r4: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.definitionBodyItems, transformSysmlAstBodyElement _)
      val r5: MOption[Attr] = transformAttr(o2.attr)
      if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
        MSome(o2(prefix = r0.getOrElse(o2.prefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), attr = r5.getOrElse(o2.attr)))
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

  def transformSysmlAstDefaultReferenceUsage(o: SysmlAst.DefaultReferenceUsage): MOption[SysmlAst.DefaultReferenceUsage] = {
    val preR: PreResult[SysmlAst.DefaultReferenceUsage] = preSysmlAstDefaultReferenceUsage(o)
    val r: MOption[SysmlAst.DefaultReferenceUsage] = if (preR.continu) {
      val o2: SysmlAst.DefaultReferenceUsage = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[SysmlAst.RefPrefix] = transformSysmlAstRefPrefix(o2.prefix)
      val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
      val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
      val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
      val r4: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.definitionBodyItems, transformSysmlAstBodyElement _)
      val r5: MOption[Attr] = transformAttr(o2.attr)
      if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
        MSome(o2(prefix = r0.getOrElse(o2.prefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), attr = r5.getOrElse(o2.attr)))
      else
        MNone()
    } else if (preR.resultOpt.nonEmpty) {
      MSome(preR.resultOpt.getOrElse(o))
    } else {
      MNone()
    }
    val hasChanged: B = r.nonEmpty
    val o2: SysmlAst.DefaultReferenceUsage = r.getOrElse(o)
    val postR: MOption[SysmlAst.DefaultReferenceUsage] = postSysmlAstDefaultReferenceUsage(o2)
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
          val r4: MOption[Option[SysmlAst.ConnectorPart]] = transformOption(o2.connectorPart, transformSysmlAstConnectorPart _)
          val r5: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), connectorPart = r4.getOrElse(o2.connectorPart), definitionBodyItems = r5.getOrElse(o2.definitionBodyItems), attr = r6.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.ItemUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.PartUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.PortUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), attr = r5.getOrElse(o2.attr)))
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
          val r4: MOption[Option[SysmlAst.ConnectorPart]] = transformOption(o2.connectorPart, transformSysmlAstConnectorPart _)
          val r5: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), connectorPart = r4.getOrElse(o2.connectorPart), definitionBodyItems = r5.getOrElse(o2.definitionBodyItems), attr = r6.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.ItemUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.PartUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), attr = r5.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.PortUsage =>
          val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
          val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
          val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
          val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
          val r4: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.definitionBodyItems, transformSysmlAstBodyElement _)
          val r5: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
            MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), attr = r5.getOrElse(o2.attr)))
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

  def transformSysmlAstConnectionUsage(o: SysmlAst.ConnectionUsage): MOption[SysmlAst.ConnectionUsage] = {
    val preR: PreResult[SysmlAst.ConnectionUsage] = preSysmlAstConnectionUsage(o)
    val r: MOption[SysmlAst.ConnectionUsage] = if (preR.continu) {
      val o2: SysmlAst.ConnectionUsage = preR.resultOpt.getOrElse(o)
      val hasChanged: B = preR.resultOpt.nonEmpty
      val r0: MOption[SysmlAst.OccurrenceUsagePrefix] = transformSysmlAstOccurrenceUsagePrefix(o2.occurrenceUsagePrefix)
      val r1: MOption[Option[SysmlAst.Identification]] = transformOption(o2.identification, transformSysmlAstIdentification _)
      val r2: MOption[IS[Z, SysmlAst.FeatureSpecialization]] = transformISZ(o2.specializations, transformSysmlAstFeatureSpecialization _)
      val r3: MOption[Option[SysmlAst.FeatureValue]] = transformOption(o2.featureValue, transformSysmlAstFeatureValue _)
      val r4: MOption[Option[SysmlAst.ConnectorPart]] = transformOption(o2.connectorPart, transformSysmlAstConnectorPart _)
      val r5: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.definitionBodyItems, transformSysmlAstBodyElement _)
      val r6: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
      if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty || r6.nonEmpty)
        MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), connectorPart = r4.getOrElse(o2.connectorPart), definitionBodyItems = r5.getOrElse(o2.definitionBodyItems), attr = r6.getOrElse(o2.attr)))
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
      val r4: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.definitionBodyItems, transformSysmlAstBodyElement _)
      val r5: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
      if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
        MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), attr = r5.getOrElse(o2.attr)))
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
      val r4: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.definitionBodyItems, transformSysmlAstBodyElement _)
      val r5: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
      if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
        MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), attr = r5.getOrElse(o2.attr)))
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
      val r4: MOption[IS[Z, SysmlAst.BodyElement]] = transformISZ(o2.definitionBodyItems, transformSysmlAstBodyElement _)
      val r5: MOption[ResolvedAttr] = transformResolvedAttr(o2.attr)
      if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty || r3.nonEmpty || r4.nonEmpty || r5.nonEmpty)
        MSome(o2(occurrenceUsagePrefix = r0.getOrElse(o2.occurrenceUsagePrefix), identification = r1.getOrElse(o2.identification), specializations = r2.getOrElse(o2.specializations), featureValue = r3.getOrElse(o2.featureValue), definitionBodyItems = r4.getOrElse(o2.definitionBodyItems), attr = r5.getOrElse(o2.attr)))
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
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.id, transformSysmlAstIdentification _)
          val r1: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.abouts, transformSysmlAstName _)
          val r2: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty)
            MSome(o2(id = r0.getOrElse(o2.id), abouts = r1.getOrElse(o2.abouts), attr = r2.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.Documentation =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.id, transformSysmlAstIdentification _)
          val r1: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty)
            MSome(o2(id = r0.getOrElse(o2.id), attr = r1.getOrElse(o2.attr)))
          else
            MNone()
        case o2: SysmlAst.TextualRepresentation =>
          val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.id, transformSysmlAstIdentification _)
          val r1: MOption[Attr] = transformAttr(o2.attr)
          if (hasChanged || r0.nonEmpty || r1.nonEmpty)
            MSome(o2(id = r0.getOrElse(o2.id), attr = r1.getOrElse(o2.attr)))
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
      val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.id, transformSysmlAstIdentification _)
      val r1: MOption[IS[Z, SysmlAst.Name]] = transformISZ(o2.abouts, transformSysmlAstName _)
      val r2: MOption[Attr] = transformAttr(o2.attr)
      if (hasChanged || r0.nonEmpty || r1.nonEmpty || r2.nonEmpty)
        MSome(o2(id = r0.getOrElse(o2.id), abouts = r1.getOrElse(o2.abouts), attr = r2.getOrElse(o2.attr)))
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
      val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.id, transformSysmlAstIdentification _)
      val r1: MOption[Attr] = transformAttr(o2.attr)
      if (hasChanged || r0.nonEmpty || r1.nonEmpty)
        MSome(o2(id = r0.getOrElse(o2.id), attr = r1.getOrElse(o2.attr)))
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
      val r0: MOption[Option[SysmlAst.Identification]] = transformOption(o2.id, transformSysmlAstIdentification _)
      val r1: MOption[Attr] = transformAttr(o2.attr)
      if (hasChanged || r0.nonEmpty || r1.nonEmpty)
        MSome(o2(id = r0.getOrElse(o2.id), attr = r1.getOrElse(o2.attr)))
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
        case o2: ResolvedInfo.ConnectionUsage =>
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

}