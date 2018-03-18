package net.jackadull.scurps.rules.attributes.secondary

import net.jackadull.scurps.context.GContext
import net.jackadull.scurps.meta.data.GCharacterData
import net.jackadull.scurps.rules.GRules

import scala.language.postfixOps

object DodgeRule extends NonAdjustableSecondaryCharacteristicRule.Specific[Int] {
  private[attributes] def defaultLevels(data:GCharacterData, context:GContext) =
    context.rules.basicSpeedRule.score(data, context).intValue + 3
  private[attributes] def levelsToScore(levels:Int) = levels
  private[attributes] def resolveSCRule(rules:GRules) = rules dodgeRule
}
