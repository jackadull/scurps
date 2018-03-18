package net.jackadull.scurps.rules.attributes.basic

import net.jackadull.scurps.meta.data.GCharacterData.Key
import net.jackadull.scurps.meta.rules.PropertyGet.{IntPropertyGet, IntPropertyGetProxy}
import net.jackadull.scurps.meta.rules.PropertyGetSet.{IntPropertyGetSet, IntPropertyGetSetProxy}
import net.jackadull.scurps.meta.rules.{PropertyGet, PropertyGetSet}
import net.jackadull.scurps.rules.GRules
import net.jackadull.scurps.rules.attributes.AttributeCPCostForScore

import scala.language.postfixOps

case class BasicAttributeRule(cpCostForScore:IntPropertyGet, cpPerPoint:IntPropertyGet, defaultScore:IntPropertyGet,
  pointsBought:IntPropertyGetSet, score:IntPropertyGetSet) extends AttributeCPCostForScore
object BasicAttributeRule {
  trait Specific {
    private[attributes] def cpCostPerPoint:Int
    private[attributes] def resolveBARule(rules:GRules):BasicAttributeRule

    object CPPerPoint extends IntPropertyGetProxy
    {def resolveRule(rules:GRules):PropertyGet[Int] = resolveBARule(rules) cpPerPoint}

    object DefaultScore extends IntPropertyGetProxy
    {def resolveRule(rules:GRules):PropertyGet[Int] = resolveBARule(rules) defaultScore}

    object PointsBought extends IntPropertyGetSetProxy
    {def resolveRule(rules:GRules):PropertyGetSet[Int] = resolveBARule(rules) pointsBought}

    private val PointsBoughtKey:Key[Int] = new Key[Int] {} // TODO this might be a different instance per launch; so maybe use an ID?
    private[rules] val default:BasicAttributeRule = BasicAttributeRule(
      cpCostForScore = PointsBought :* CPPerPoint,
      cpPerPoint = PropertyGet constant cpCostPerPoint,
      defaultScore = PropertyGet constant 10,
      pointsBought = PropertyGetSet ofKey (PointsBoughtKey, 0),
      score = PointsBought :+ DefaultScore
    )
  }
}
