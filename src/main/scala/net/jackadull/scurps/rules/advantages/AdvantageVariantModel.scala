package net.jackadull.scurps.rules.advantages

import net.jackadull.scurps.rules.advantages.AdvantageVariant.{LeveledAdvantageVariant, NamedAdvantageVariant}

import scala.collection.immutable.ListSet

sealed trait AdvantageVariantModel[A<:AdvantageVariant]
object AdvantageVariantModel {
  def apply[A<:AdvantageVariant](variants:A*):EnumerableVariantModel[A] = EnumerableVariantModel(ListSet(variants:_*))

  final case class EnumerableVariantModel[A<:AdvantageVariant](variants:ListSet[A]) extends AdvantageVariantModel[A]

  trait LeveledAndKeyedVariantModel[A<:LeveledAdvantageVariant,B] extends AdvantageVariantModel[A] {
    def keys:Set[B]
    def variantFor(key:B, level:Int):A
  }

  trait LeveledVariantModel[A<:LeveledAdvantageVariant] extends AdvantageVariantModel[A] {
    def variantFor(level:Int):A
  }

  trait NamedAndKeyedVariantModel[A<:NamedAdvantageVariant,B] extends AdvantageVariantModel[A] {
    def keys:Set[B]
    def variantFor(key:B, name:String):A
  }
}
