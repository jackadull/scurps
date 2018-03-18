package net.jackadull.scurps.rules.attributes.secondary

import net.jackadull.scurps.meta.rules.PropertyGet.{IntPropertyGet, IntPropertyGetProxy}
import net.jackadull.scurps.meta.rules.{PropertyFunction1, PropertyGet}
import net.jackadull.scurps.rules.GRules

import scala.language.postfixOps

case class NonAdjustableSecondaryCharacteristicRule[A](defaultLevels:IntPropertyGet,
  levelsToScore:PropertyFunction1[Int,A], levelsTotal:IntPropertyGet, score:PropertyGet[A])
extends SecondaryCharacteristicRule[A]
object NonAdjustableSecondaryCharacteristicRule {
  trait Specific[A] extends SecondaryCharacteristicRule.Specific[A] {
    object LevelsTotal extends IntPropertyGetProxy
    {def resolveRule(rules:GRules):PropertyGet[Int] = resolveSCRule(rules) levelsTotal}

    private[attributes] def resolveSCRule(rules:GRules):NonAdjustableSecondaryCharacteristicRule[A]

    private[rules] val default:NonAdjustableSecondaryCharacteristicRule[A] = NonAdjustableSecondaryCharacteristicRule[A](
      defaultLevels = DefaultLevelsImpl,
      levelsToScore = LevelsToScoreImpl,
      levelsTotal = DefaultLevels,
      score = LevelsToScore curry1 LevelsTotal
    )
  }
}