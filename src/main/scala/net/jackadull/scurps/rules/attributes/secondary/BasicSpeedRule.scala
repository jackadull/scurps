package net.jackadull.scurps.rules.attributes.secondary

import net.jackadull.scurps.context.GContext
import net.jackadull.scurps.meta.data.GCharacterData
import net.jackadull.scurps.rules.GRules
import spire.math.Rational

import scala.language.postfixOps

object BasicSpeedRule extends AdjustableSecondaryCharacteristicRule.Specific[Rational] {
  private[attributes] def cpCostPerLevel = 5
  private[attributes] def defaultLevels(data:GCharacterData, context:GContext) =
    context.rules.dexterityRule.score(data, context) + context.rules.healthRule.score(data, context)
  private[attributes] def levelsToScore(levels:Int) = Rational(levels, 4)
  private[attributes] def resolveSCRule(rules:GRules) = rules basicSpeedRule
}
