package net.jackadull.scurps.rules.advantages

import net.jackadull.scurps.rules.advantages.AdvantageVariant.{AdvantageVariantWithRepr, LeveledAdvantageVariant, NamedAdvantageVariant}

package object list {
  sealed trait Advantage_360_Degree_Vision extends AdvantageVariantWithRepr[Advantage_360_Degree_Vision]
  {def group:AdvantageGroup[Advantage_360_Degree_Vision] = Advantage_360_Degree_Vision}
  object Advantage_360_Degree_Vision extends AdvantageGroup[Advantage_360_Degree_Vision]
  object `360° Vision` extends Advantage_360_Degree_Vision

  sealed trait Advantage_Absolute_Direction extends AdvantageVariantWithRepr[Advantage_Absolute_Direction]
  {def group:AdvantageGroup[Advantage_Absolute_Direction] = Advantage_Absolute_Direction}
  object Advantage_Absolute_Direction extends AdvantageGroup[Advantage_Absolute_Direction]
  object `Absolute Direction` extends Advantage_Absolute_Direction
  object `Absolute Direction (3D Spatial Sense)` extends Advantage_Absolute_Direction

  sealed trait Advantage_Absolute_Timing extends AdvantageVariantWithRepr[Advantage_Absolute_Timing]
  {def group:AdvantageGroup[Advantage_Absolute_Timing] = Advantage_Absolute_Timing}
  object Advantage_Absolute_Timing extends AdvantageGroup[Advantage_Absolute_Timing]
  object `Absolute Timing` extends Advantage_Absolute_Timing
  object `Absolute Timing (Chronolocation)` extends Advantage_Absolute_Timing

  sealed trait Advantage_Acute_Senses extends LeveledAdvantageVariant with AdvantageVariantWithRepr[Advantage_Acute_Senses]
  {def group:AdvantageGroup[Advantage_Acute_Senses] = Advantage_Acute_Senses}
  object Advantage_Acute_Senses extends AdvantageGroup[Advantage_Acute_Senses]
  case class `Acute Hearing`(level:Int) extends Advantage_Acute_Senses
  case class `Acute Taste and Smell`(level:Int) extends Advantage_Acute_Senses
  case class `Acute Touch`(level:Int) extends Advantage_Acute_Senses
  case class `Acute Vision`(level:Int) extends Advantage_Acute_Senses

  sealed trait Advantage_Altered_Time_Rate extends LeveledAdvantageVariant with AdvantageVariantWithRepr[Advantage_Altered_Time_Rate]
  {def group:AdvantageGroup[Advantage_Altered_Time_Rate] = Advantage_Altered_Time_Rate}
  object Advantage_Altered_Time_Rate extends AdvantageGroup[Advantage_Altered_Time_Rate]
  case class `Altered Time Rate`(level:Int) extends Advantage_Altered_Time_Rate

  sealed trait Advantage_Alternate_Identity extends NamedAdvantageVariant with AdvantageVariantWithRepr[Advantage_Alternate_Identity]
  {def group:AdvantageGroup[Advantage_Alternate_Identity] = Advantage_Alternate_Identity}
  object Advantage_Alternate_Identity extends AdvantageGroup[Advantage_Alternate_Identity]
  case class `Alternate Identity (Legal)`(name:String) extends Advantage_Alternate_Identity
  case class `Alternate Identity (Illegal)`(name:String) extends Advantage_Alternate_Identity

  sealed trait Advantage_Ambidexterity extends AdvantageVariantWithRepr[Advantage_Ambidexterity]
  {def group:AdvantageGroup[Advantage_Ambidexterity] = Advantage_Ambidexterity}
  object Advantage_Ambidexterity extends AdvantageGroup[Advantage_Ambidexterity]
  object Ambidexterity extends Advantage_Ambidexterity

  sealed trait Disadvantage_Bad_Smell extends AdvantageVariantWithRepr[Disadvantage_Bad_Smell]
  {def group:AdvantageGroup[Disadvantage_Bad_Smell] = Disadvantage_Bad_Smell}
  object Disadvantage_Bad_Smell extends AdvantageGroup[Disadvantage_Bad_Smell]
  object `Bad Smell` extends Disadvantage_Bad_Smell
}
