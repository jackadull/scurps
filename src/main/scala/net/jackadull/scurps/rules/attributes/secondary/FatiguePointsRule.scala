package net.jackadull.scurps.rules.attributes.secondary

import net.jackadull.scurps.context.GContext
import net.jackadull.scurps.meta.data.GCharacterData
import net.jackadull.scurps.rules.GRules

import scala.language.postfixOps

object FatiguePointsRule extends AdjustableSecondaryCharacteristicRule.Specific[Int] {
  private[attributes] def cpCostPerLevel = 3
  private[attributes] def defaultLevels(data:GCharacterData, context:GContext) =
    context.rules.healthRule.score(data, context)
  private[attributes] def levelsToScore(levels:Int) = levels
  private[attributes] def resolveSCRule(rules:GRules) = rules fatiguePointsRule
}
