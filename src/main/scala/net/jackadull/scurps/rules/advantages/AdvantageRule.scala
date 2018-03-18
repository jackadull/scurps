package net.jackadull.scurps.rules.advantages

import net.jackadull.scurps.context.GContext
import net.jackadull.scurps.meta.data.GCharacterData

import scala.language.postfixOps

trait AdvantageRule[A<:AdvantageVariant] {
  def group:AdvantageGroup[A]
  def storageModel:AdvantageStorageModel[A]
  def variantModel:AdvantageVariantModel[A]

  def +(advantage:Advantage[A], data:GCharacterData, context:GContext):GCharacterData =
    context.rules.advantageDataRule.mod(data, context)(storageModel + advantage)
  def -(advantage:Advantage[A], data:GCharacterData, context:GContext):GCharacterData =
    context.rules.advantageDataRule.mod(data, context)(storageModel - advantage)
  def baseCPCostOfVariant(variant:A):Int
  def cpCostOf(advantage:Advantage[A]):Int = baseCPCostOfVariant(advantage variant) // TODO factor in modifiers
  def getAll(data:GCharacterData, context:GContext):Set[Advantage[A]] =
    storageModel.all(context.rules.advantageDataRule(data, context))
  def getFirstOption(data:GCharacterData, context:GContext):Option[Advantage[A]] =
    storageModel.headOption(context.rules.advantageDataRule(data, context))
  def removeAll(data:GCharacterData, context:GContext):GCharacterData =
    context.rules.advantageDataRule.mod(data, context)(storageModel removeAll)
}
