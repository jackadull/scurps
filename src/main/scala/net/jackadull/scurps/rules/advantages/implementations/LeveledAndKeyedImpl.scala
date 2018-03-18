package net.jackadull.scurps.rules.advantages.implementations

import net.jackadull.scurps.rules.advantages.AdvantageStorageModel.PerKeySingletonAdvantage
import net.jackadull.scurps.rules.advantages.AdvantageVariant.LeveledAdvantageVariant
import net.jackadull.scurps.rules.advantages.AdvantageVariantModel.LeveledAndKeyedVariantModel
import net.jackadull.scurps.rules.advantages.{Advantage, AdvantageRule, AdvantageStorageModel, AdvantageVariantModel}

import scala.language.postfixOps

private[implementations] abstract class LeveledAndKeyedImpl[A<:LeveledAdvantageVariant,B](keys:Set[B])
extends AdvantageRule[A] {
  protected def keyOf(variant:A):B
  protected def variantFor(key:B, level:Int):A

  val storageModel:AdvantageStorageModel[A] = new PerKeySingletonAdvantage[A,B](group) {
    def keyOf(advantage:Advantage[A]) = LeveledAndKeyedImpl.this.keyOf(advantage variant)
  }

  val variantModel:AdvantageVariantModel[A] = new LeveledAndKeyedVariantModel[A,B] {
    def keys:Set[B] = LeveledAndKeyedImpl.this.keys
    def variantFor(key:B, level:Int):A = LeveledAndKeyedImpl.this.variantFor(key, level)
  }
}
