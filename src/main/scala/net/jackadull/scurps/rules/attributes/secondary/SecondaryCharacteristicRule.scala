package net.jackadull.scurps.rules.attributes.secondary

import net.jackadull.scurps.context.GContext
import net.jackadull.scurps.meta.data.GCharacterData
import net.jackadull.scurps.meta.rules.PropertyFunction1.PropertyFunction1Proxy
import net.jackadull.scurps.meta.rules.PropertyGet.{IntPropertyGet, IntPropertyGetProxy}
import net.jackadull.scurps.meta.rules.{PropertyFunction1, PropertyGet}
import net.jackadull.scurps.rules.GRules

import scala.language.postfixOps

trait SecondaryCharacteristicRule[A] {
  def defaultLevels:IntPropertyGet
  def levelsToScore:PropertyFunction1[Int,A]
  def levelsTotal:IntPropertyGet
  def score:PropertyGet[A]
}
object SecondaryCharacteristicRule {
  trait Specific[A] {
    private[attributes] def defaultLevels(data:GCharacterData, context:GContext):Int
    private[attributes] def levelsToScore(levels:Int):A
    private[attributes] def resolveSCRule(rules:GRules):SecondaryCharacteristicRule[A]

    object DefaultLevels extends IntPropertyGetProxy
    {def resolveRule(rules:GRules):PropertyGet[Int] = resolveSCRule(rules) defaultLevels}

    object LevelsToScore extends PropertyFunction1Proxy[Int,A]
    {def resolveRule(rules:GRules):PropertyFunction1[Int,A] = resolveSCRule(rules) levelsToScore}

    protected object DefaultLevelsImpl extends IntPropertyGet
    {def apply(data:GCharacterData, context:GContext):Int = defaultLevels(data, context)}

    protected object LevelsToScoreImpl extends PropertyFunction1[Int,A]
    {def apply(levels:Int, data:GCharacterData, context:GContext):A = levelsToScore(levels)}
  }
}