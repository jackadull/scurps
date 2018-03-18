package net.jackadull.scurps.rules.attributes.secondary

import net.jackadull.scurps.meta.data.GCharacterData.Key
import net.jackadull.scurps.meta.rules.PropertyGet.{IntPropertyGet, IntPropertyGetProxy}
import net.jackadull.scurps.meta.rules.PropertyGetSet.{IntPropertyGetSet, IntPropertyGetSetProxy}
import net.jackadull.scurps.meta.rules.{PropertyFunction1, PropertyGet, PropertyGetSet}
import net.jackadull.scurps.rules.GRules
import net.jackadull.scurps.rules.attributes.AttributeCPCostForScore

import scala.language.postfixOps

case class AdjustableSecondaryCharacteristicRule[A](cpCostForScore:IntPropertyGet, cpPerLevel:IntPropertyGet,
  defaultLevels:IntPropertyGet, levelsBought:IntPropertyGetSet, levelsToScore:PropertyFunction1[Int,A],
  levelsTotal:IntPropertyGetSet, score:PropertyGet[A])
extends SecondaryCharacteristicRule[A] with AttributeCPCostForScore
object AdjustableSecondaryCharacteristicRule {
  trait Specific[A] extends SecondaryCharacteristicRule.Specific[A] {
    private[attributes] def cpCostPerLevel:Int
    private[attributes] def resolveSCRule(rules:GRules):AdjustableSecondaryCharacteristicRule[A]

    object CPPerLevel extends IntPropertyGetProxy
    {def resolveRule(rules:GRules):PropertyGet[Int] = resolveSCRule(rules) cpPerLevel}

    object LevelsBought extends IntPropertyGetSetProxy
    {def resolveRule(rules:GRules):PropertyGetSet[Int] = resolveSCRule(rules) levelsBought}

    object LevelsTotal extends IntPropertyGetSetProxy
    {def resolveRule(rules:GRules):PropertyGetSet[Int] = resolveSCRule(rules) levelsTotal}

    private val LevelsBoughtKey:Key[Int] = new Key[Int] {} // TODO maybe ID-based key?
    private[rules] val default:AdjustableSecondaryCharacteristicRule[A] = AdjustableSecondaryCharacteristicRule[A](
      cpCostForScore = LevelsBought :* CPPerLevel,
      cpPerLevel = PropertyGet constant cpCostPerLevel,
      defaultLevels = DefaultLevelsImpl,
      levelsBought = PropertyGetSet ofKey (LevelsBoughtKey, 0),
      levelsToScore = LevelsToScoreImpl,
      levelsTotal = LevelsBought :+ DefaultLevels,
      score = LevelsToScore curry1 LevelsTotal
    )
  }
}
