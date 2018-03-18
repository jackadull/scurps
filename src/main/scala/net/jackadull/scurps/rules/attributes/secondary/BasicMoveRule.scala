package net.jackadull.scurps.rules.attributes.secondary

import net.jackadull.scurps.context.GContext
import net.jackadull.scurps.meta.data.GCharacterData
import net.jackadull.scurps.models.measurement.Speed
import net.jackadull.scurps.rules.GRules

import scala.language.postfixOps

object BasicMoveRule extends AdjustableSecondaryCharacteristicRule.Specific[Speed[Int]] {
  private[attributes] def cpCostPerLevel = 5
  private[attributes] def defaultLevels(data:GCharacterData, context:GContext) =
    context.rules.basicSpeedRule.score(data, context).intValue
  private[attributes] def levelsToScore(levels:Int) = Speed ofYardsPerSecond levels
  private[attributes] def resolveSCRule(rules:GRules) = rules basicMoveRule
}
