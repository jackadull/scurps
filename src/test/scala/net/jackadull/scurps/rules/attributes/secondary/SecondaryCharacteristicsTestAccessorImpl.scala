package net.jackadull.scurps.rules.attributes.secondary

import net.jackadull.scurps.context.GContext
import net.jackadull.scurps.meta.data.GCharacterData
import net.jackadull.scurps.rules.attributes.basic.BasicAttributesTestAccessorImpl
import net.jackadull.scurps.rules.attributes.secondary.SecondaryCharacteristicTestModel._

import scala.language.postfixOps

trait SecondaryCharacteristicsTestAccessorImpl
extends BasicAttributesTestAccessorImpl with SecondaryCharacteristicsTestAccessor[(GCharacterData,GContext)] {
  private def resolveASCRule[A](sc:AdjustableSecondaryCharacteristicTestModel[A], context:GContext):AdjustableSecondaryCharacteristicRule[A] =
    (sc match {
      case BasicMoveTestModel ⇒ context.rules.basicMoveRule
      case BasicSpeedTestModel ⇒ context.rules.basicSpeedRule
      case FatiguePointsTestModel ⇒ context.rules.fatiguePointsRule
      case HitPointsTestModel ⇒ context.rules.hitPointsRule
      case WillTestModel ⇒ context.rules.willRule
      case PerceptionTestModel ⇒ context.rules.perceptionRule
    }).asInstanceOf[AdjustableSecondaryCharacteristicRule[A]]

  private def resolveNSCRule[A](sc:NonAdjustableSecondaryCharacteristicTestModel[A], context:GContext):NonAdjustableSecondaryCharacteristicRule[A] =
    (sc match {
      case BasicLiftTestModel ⇒ context.rules.basicLiftRule
      case DamageTestModel ⇒ context.rules.damageRule
      case DodgeTestModel ⇒ context.rules.dodgeRule
    }).asInstanceOf[NonAdjustableSecondaryCharacteristicRule[A]]

  private def resolveSCRule[A](sc:SecondaryCharacteristicTestModel[A], context:GContext):SecondaryCharacteristicRule[A] =
    sc match {
      case asc:AdjustableSecondaryCharacteristicTestModel[A] ⇒ resolveASCRule(asc, context)
      case nsc:NonAdjustableSecondaryCharacteristicTestModel[A] ⇒ resolveNSCRule(nsc, context)
    }

  def cpCostForSecondaryCharacteristic(sc:AdjustableSecondaryCharacteristicTestModel[_], character:(GCharacterData,GContext)) =
    resolveASCRule(sc, character _2).cpCostForScore(character _1, character _2)

  def secondaryCharacteristicLevelsBought(sc:AdjustableSecondaryCharacteristicTestModel[_], character:(GCharacterData,GContext)) =
    resolveASCRule(sc, character _2).levelsBought(character _1, character _2)

  def secondaryCharacteristicScore[B](sc:SecondaryCharacteristicTestModel[B], character:(GCharacterData,GContext)) =
    resolveSCRule(sc, character _2).score(character _1, character _2)

  def setSecondaryCharacteristicLevelsBought(sc:AdjustableSecondaryCharacteristicTestModel[_], newLevelsBought:Int, character:(GCharacterData,GContext)) =
    (resolveASCRule(sc, character _2).levelsBought.set(newLevelsBought, character _1, character _2), character _2)
}
object SecondaryCharacteristicsTestAccessorImpl extends SecondaryCharacteristicsTestAccessorImpl
