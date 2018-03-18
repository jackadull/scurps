package net.jackadull.scurps.rules.attributes.secondary

import net.jackadull.scurps.context.GContext
import net.jackadull.scurps.meta.data.GCharacterData
import net.jackadull.scurps.models.measurement.Weight
import net.jackadull.scurps.rules.GRules
import spire.math.Rational

import scala.language.postfixOps

object BasicLiftRule extends NonAdjustableSecondaryCharacteristicRule.Specific[Weight[Rational]] {
  private[attributes] def defaultLevels(data:GCharacterData, context:GContext) = {
    val strength = context.rules.strengthRule.score(data, context)
    strength * strength
  }

  private[attributes] def levelsToScore(levels:Int) =
    if(levels>=50) Weight ofPounds Rational((levels.toDouble/5).toInt, 1) else Weight ofPounds Rational(levels, 5)

  private[attributes] def resolveSCRule(rules:GRules) = rules basicLiftRule
}
