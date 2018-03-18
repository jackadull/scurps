package net.jackadull.scurps.rules.advantages.implementations

import net.jackadull.scurps.rules.advantages.AdvantageStorageModel.SingletonAdvantage
import net.jackadull.scurps.rules.advantages.AdvantageVariant.LeveledAdvantageVariant
import net.jackadull.scurps.rules.advantages.AdvantageVariantModel.LeveledVariantModel
import net.jackadull.scurps.rules.advantages.{AdvantageRule, AdvantageStorageModel, AdvantageVariantModel}

private[implementations] abstract class LeveledImpl[A<:LeveledAdvantageVariant]
extends AdvantageRule[A] {
  protected def variantFor(level:Int):A

  val storageModel:AdvantageStorageModel[A] = SingletonAdvantage(group)
  val variantModel:AdvantageVariantModel[A] =
    new LeveledVariantModel[A] {def variantFor(level:Int):A = LeveledImpl.this.variantFor(level)}
}
