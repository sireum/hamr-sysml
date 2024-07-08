// #Sireum

package org.sireum.hamr.sysml.ast

import org.sireum._
import org.sireum.hamr.sysml.ast.SysmlAst.Name
import org.sireum.lang.{ast => AST}
import org.sireum.message.Position

object SysmlAst {

  @datatype class Id(val value: String, @hidden val attr: Attr) {
    @strictpure def prettyST: ST = st"$value"

    def posOpt: Option[Position] = {
      return attr.posOpt
    }
  }

  @datatype class Name(val ids: ISZ[Id], @hidden val attr: Attr) {
    @strictpure def prettyST: ST = st"${(for (id <- ids) yield id.prettyST, ".")}"

    @strictpure def posOpt: Option[Position] = attr.posOpt
  }

  object TopUnit {
    def empty: TopUnit = {
      return TopUnit(None(), ISZ())
    }
  }
  @datatype class TopUnit(val fileUri: Option[String],
                          val packageBodyElements: ISZ[PackageBodyElement])

  @sig trait AttrNode {
    def posOpt: Option[Position]
  }

  @sig trait PackageBodyElement extends AttrNode

  @sig trait BodyElement extends AttrNode

  @enum object Visibility {
    "Public"
    "Private"
    "Protected"
  }

  @datatype class FeatureValue (val isBound: B,
                                val isInitial: B,
                                val isDefault: B,
                                val exp: AST.Exp)

  @datatype class EnumeratedValue(val visibility: Visibility.Type,
                                  val identification: Option[Identification],
                                  val specializations: ISZ[FeatureSpecialization],
                                  val definitionBodyItems: ISZ[BodyElement])

  @datatype class Import(val visibility: Visibility.Type,
                         val all: B,
                         val name: Name,
                         val star: B,
                         val starStar: B,
                         val annotations: ISZ[AnnotatingElement],
                         @hidden val attr: Attr) extends PackageBodyElement with BodyElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }


  @datatype class Alias(val visibility: Visibility.Type,
                        val identification: Option[Identification],
                        val target: Name,
                        val annotations: ISZ[AnnotatingElement],
                        @hidden val attr: Attr) extends PackageBodyElement with BodyElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }


  @datatype class Identification(val shortName: Option[Id],
                                 val name: Option[Id],
                                 @hidden val attr: Attr) extends AttrNode {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }

  @enum object FeatureDirection {
    "In"
    "Out"
    "InOut"
  }

  /****************************************************************
   * C O N N E C T O R S
   *****************************************************************/

  @sig trait ConnectorPart

  @datatype class ConnectorEnd(val reference: ISZ[Name])

  @datatype class BinaryConnectorPart(val src: ConnectorEnd,
                                      val dst: ConnectorEnd) extends  ConnectorPart

  @datatype class NaryConnectorPart(val connectorEnds: ISZ[ConnectorEnd]) extends ConnectorPart


  /****************************************************************
   * S P E C I A L I Z A T I O N S
   *****************************************************************/

  @sig trait FeatureSpecialization

  @datatype class TypingsSpecialization(val names: ISZ[Name]) extends FeatureSpecialization

  @datatype class SubsettingsSpecialization(val subsettings: ISZ[Name]) extends FeatureSpecialization

  @datatype class ReferencesSpecialization(val references: ISZ[Name]) extends FeatureSpecialization

  @datatype class RedefinitionsSpecialization(val references: ISZ[Name]) extends FeatureSpecialization

  /****************************************************************
   * D E F I N I T I O N S
   *****************************************************************/

  @sig trait DefinitionElement extends PackageBodyElement

  @datatype class DefinitionPrefix(val isAbstract: B,
                                   val isVariation: B)

  @datatype class OccurrenceDefinitionPrefix(val isAbstract: B,
                                             val isVariation: B)



  @datatype class Package (val identification: Option[Identification],
                           val packageElements: ISZ[PackageBodyElement],
                           @hidden val attr: Attr) extends DefinitionElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }

  @datatype class AttributeDefinition(val defPrefix: DefinitionPrefix,
                                      val identification: Option[Identification],
                                      val subClassifications: ISZ[Name],
                                      val parents: ISZ[Type.Named],
                                      val bodyItems: ISZ[BodyElement],
                                      @hidden val attr: Attr) extends DefinitionElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt

    /*
    @pure def parents: ISZ[Type.Named] = {
      for (parentName <- subClassifications) yield
        Type.Named(name = parentName, attr = TypedAttr(posOpt = parentName.posOpt, typedOpt = None()))
    }
     */
  }

  @datatype class AllocationDefinition(val occurrenceDefPrefix: OccurrenceDefinitionPrefix,
                                       val identification: Option[Identification],
                                       val subClassifications: ISZ[Name],
                                       val parents: ISZ[Type.Named],
                                       val bodyItems: ISZ[BodyElement],
                                       @hidden val attr: Attr) extends DefinitionElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt

    /*
    @pure def parents: ISZ[Type.Named] = {
      for (parentName <- subClassifications) yield
        Type.Named(name = parentName, attr = TypedAttr(posOpt = parentName.posOpt, typedOpt = None()))
    }
     */
  }

  @datatype class ConnectionDefinition(val occurrenceDefPrefix: OccurrenceDefinitionPrefix,
                                       val identification: Option[Identification],
                                       val subClassifications: ISZ[Name],
                                       val parents: ISZ[Type.Named],
                                       val bodyItems: ISZ[BodyElement],
                                       @hidden val attr: Attr) extends DefinitionElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt

    /*
    @pure def parents: ISZ[Type.Named] = {
      for (parentName <- subClassifications) yield
        Type.Named(name = parentName, attr = TypedAttr(posOpt = parentName.posOpt, typedOpt = None()))
    }
     */
  }

  @datatype class EnumerationDefinition(val identification: Option[Identification],
                                        val subClassifications: ISZ[Name],
                                        val annotations: ISZ[AnnotatingElement],
                                        val enumValues: ISZ[EnumeratedValue],
                                        @hidden val attr: Attr) extends DefinitionElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }

  @datatype class PartDefinition(val occurrenceDefPrefix: OccurrenceDefinitionPrefix,
                                 val identification: Option[Identification],
                                 val subClassifications: ISZ[Name],
                                 val parents: ISZ[Type.Named],
                                 val bodyItems: ISZ[BodyElement],
                                 @hidden val attr: Attr) extends DefinitionElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt

    /*
    @pure def parents: ISZ[Type.Named] = {
      for (parentName <- subClassifications) yield
        Type.Named(name = parentName, attr = TypedAttr(posOpt = parentName.posOpt, typedOpt = None()))
    }
    */
  }

  @datatype class PortDefinition(val defPrefix: DefinitionPrefix,
                                 val identification: Option[Identification],
                                 val subClassifications: ISZ[Name],
                                 val parents: ISZ[Type.Named],
                                 val bodyItems: ISZ[BodyElement],
                                 @hidden val attr: Attr) extends DefinitionElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt

    /*
    @pure def parents: ISZ[Type.Named] = {
      for (parentName <- subClassifications) yield
        Type.Named(name = parentName, attr = TypedAttr(posOpt = parentName.posOpt, typedOpt = None()))
    }
     */
  }

  @datatype class MetadataDefinition(@hidden val attr: Attr) extends DefinitionElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }

  /****************************************************************
   * U S A G E S
   *****************************************************************/

  @datatype class UsagePrefix(val refPrefix: RefPrefix,

                              // basic usage prefix
                              val isRef: B,
                              val usageExtensions: ISZ[Name])

  @datatype class OccurrenceUsagePrefix(val refPrefix: RefPrefix,
                                        val isRef: B,
                                        val isIndividual: B,
                                        val isSnapshot: B,
                                        val isTimeslice: B,
                                        val usageExtensions: ISZ[Name])

  @datatype class RefPrefix(val direction: Option[FeatureDirection.Type],
                            val isAbstract: B,
                            val isVariation: B,
                            val isReadOnly: B,
                            val isDerived: B,
                            val isEnd: B)

  @sig trait UsageElement extends PackageBodyElement with BodyElement {
    def identification: Option[Identification]
    def specializations: ISZ[FeatureSpecialization]
    def featureValue: Option[FeatureValue]
    def definitionBodyItems: ISZ[BodyElement]
  }

  // Non-Occurrence Usages

  @sig trait NonOccurrenceUsageElement extends UsageElement

  @datatype class AttributeUsage(val visibility: Visibility.Type,
                                 val prefix: UsagePrefix,
                                 val identification: Option[Identification],
                                 val specializations: ISZ[FeatureSpecialization],
                                 val featureValue: Option[FeatureValue],
                                 val definitionBodyItems: ISZ[BodyElement],
                                 val tipeOpt: Option[Type],
                                 @hidden val attr: ResolvedAttr) extends NonOccurrenceUsageElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }

  @datatype class ReferenceUsage(val visibility: Visibility.Type,
                                 val prefix: RefPrefix,
                                 val identification: Option[Identification],
                                 val specializations: ISZ[FeatureSpecialization],
                                 val featureValue: Option[FeatureValue],
                                 val definitionBodyItems: ISZ[BodyElement],
                                 val tipeOpt: Option[Type],
                                 @hidden val attr: Attr) extends NonOccurrenceUsageElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }

  @datatype class DefaultReferenceUsage(val visibility: Visibility.Type,
                                        val prefix: RefPrefix,
                                        val identification: Option[Identification],
                                        val specializations: ISZ[FeatureSpecialization],
                                        val featureValue: Option[FeatureValue],
                                        val definitionBodyItems: ISZ[BodyElement],
                                        val tipeOpt: Option[Type],
                                        @hidden val attr: Attr) extends NonOccurrenceUsageElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }

  // Occurrence Usages

  @sig trait OccurrenceUsageElement extends UsageElement

  @sig trait StructureUsageElement extends OccurrenceUsageElement

  @datatype class ConnectionUsage(val visibility: Visibility.Type,
                                  val occurrenceUsagePrefix: OccurrenceUsagePrefix,
                                  val identification: Option[Identification],
                                  val specializations: ISZ[FeatureSpecialization],
                                  val featureValue: Option[FeatureValue],
                                  val connectorPart: Option[ConnectorPart],
                                  val definitionBodyItems: ISZ[BodyElement],
                                  val tipeOpt: Option[Type],
                                  @hidden val attr: ResolvedAttr) extends StructureUsageElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }

  @datatype class ItemUsage(val visibility: Visibility.Type,
                            val occurrenceUsagePrefix: OccurrenceUsagePrefix,
                            val identification: Option[Identification],
                            val specializations: ISZ[FeatureSpecialization],
                            val featureValue: Option[FeatureValue],
                            val definitionBodyItems: ISZ[BodyElement],
                            val tipeOpt: Option[Type],
                            @hidden val attr: ResolvedAttr) extends StructureUsageElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }

  @datatype class PartUsage(val visibility: Visibility.Type,
                            val occurrenceUsagePrefix: OccurrenceUsagePrefix,
                            val identification: Option[Identification],
                            val specializations: ISZ[FeatureSpecialization],
                            val featureValue: Option[FeatureValue],
                            val definitionBodyItems: ISZ[BodyElement],
                            val tipeOpt: Option[Type],
                            @hidden val attr: ResolvedAttr) extends StructureUsageElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }

  @datatype class PortUsage(val visibility: Visibility.Type,
                            val occurrenceUsagePrefix: OccurrenceUsagePrefix,
                            val identification: Option[Identification],
                            val specializations: ISZ[FeatureSpecialization],
                            val featureValue: Option[FeatureValue],
                            val definitionBodyItems: ISZ[BodyElement],
                            val tipeOpt: Option[Type],
                            @hidden val attr: ResolvedAttr) extends StructureUsageElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }

  /****************************************************************
   * A N N O T A T I O N S
   *****************************************************************/

  @sig trait AnnotatingElement extends DefinitionElement {
    def id: Option[Identification]

    def comment: String
  }

  @datatype class Comment(val id: Option[Identification],
                          val abouts: ISZ[Name],
                          val locale: Option[String],
                          val comment: String,
                          @hidden val attr: Attr) extends AnnotatingElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }

  @datatype class Documentation(val id: Option[Identification],
                                val locale: Option[String],
                                val comment: String,
                                @hidden val attr: Attr) extends AnnotatingElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }

  @datatype class TextualRepresentation(val id: Option[Identification],
                                        val language: String,
                                        val comment: String,
                                        @hidden val attr: Attr) extends AnnotatingElement {
    @strictpure override def posOpt: Option[Position] = attr.posOpt
  }

}

@datatype class Attr(val posOpt: Option[Position])

@datatype class ResolvedAttr(@hidden val posOpt: Option[Position],
                             val resOpt: Option[ResolvedInfo],
                             val typedOpt: Option[Typed])

@datatype trait ResolvedInfo {

}

@datatype trait Type {

  @strictpure def posOpt: Option[Position]

  @strictpure def typedOpt: Option[Typed]

  @strictpure def typed(t: Typed): Type

  @strictpure def prettyST: ST

  override def string: String = {
    typedOpt match {
      case Some(t) => return t.string
      case _ => return prettyST.render
    }
  }
}

object Type {
  @datatype class Named(val name: Name, @hidden attr: TypedAttr) extends Type {

    @strictpure override def posOpt: Option[Position] = attr.posOpt
    @strictpure override def typedOpt: Option[Typed] = attr.typedOpt
    @strictpure override def typed(t: Typed): Type.Named = this (name, attr(typedOpt = Some(t)))

    @pure def isEqual(other: Named): B = {
      (typedOpt, other.typedOpt) match {
        case (Some(t1), Some(t2)) => return t1 == t2
        case _ => return name == other.name
      }
    }

    @pure override def hash: Z = {
      typedOpt match {
        case Some(t) => return t.hash
        case _ => return (name).hash
      }
    }

    @strictpure override def prettyST: ST = {
      st"${(for (id <- name.ids) yield id.value, ".")}"
    }
  }
}

@datatype class TypedAttr(val posOpt: Option[Position], val typedOpt: Option[Typed])

object ResolvedInfo {
  @datatype class Package(val name: ISZ[String]) extends ResolvedInfo

  @datatype class Enum(val name: ISZ[String]) extends ResolvedInfo

  @datatype class EnumElement(val owner: ISZ[String], val name: String, val ordinal: Z) extends ResolvedInfo

  @datatype class AttributeUsage(val owner: ISZ[String], val name: String) extends ResolvedInfo

  @datatype class ItemUsage(val owner: ISZ[String], val name: String) extends ResolvedInfo

  @datatype class PartUsage(val owner: ISZ[String], val name: String) extends ResolvedInfo

  @datatype class PortUsage(val owner: ISZ[String], val name: String) extends ResolvedInfo

  @datatype class ConnectionUsage(val owner: ISZ[String], val name: String) extends ResolvedInfo
}