package net.jackadull.scurps.rules.advantages.implementations

import net.jackadull.scurps.rules.advantages.AdvantageStorageModel.SingletonAdvantage
import net.jackadull.scurps.rules.advantages.{AdvantageRule, AdvantageVariant, AdvantageVariantModel}

private[implementations] abstract class SingletonEnumerableImpl[A<:AdvantageVariant](variants:A*)
extends AdvantageRule[A] {
  def storageModel = SingletonAdvantage(group)
  def variantModel = AdvantageVariantModel(variants:_*)
}
