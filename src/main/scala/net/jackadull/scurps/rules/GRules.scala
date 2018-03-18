package net.jackadull.scurps.rules

import net.jackadull.scurps.context.GContext
import net.jackadull.scurps.meta.data.GCharacterData
import net.jackadull.scurps.meta.rules.PropertyGet.IntPropertyGet
import net.jackadull.scurps.meta.rules.{CPCostEntries, PropertyGetSet}
import net.jackadull.scurps.models.damage.Damage
import net.jackadull.scurps.models.measurement.{Speed, Weight}
import net.jackadull.scurps.rules.advantages._
import net.jackadull.scurps.rules.attributes.basic._
import net.jackadull.scurps.rules.attributes.secondary._
import net.jackadull.scurps.validation.GValidations
import spire.math.Rational

import scala.language.postfixOps

/** Refers to all individual '''GURPS''' rule implementations. */
case class GRules(
  advantageDataRule:PropertyGetSet[AdvantageData],
  advantageRules:AdvantageRules,
  basicSpeedRule:AdjustableSecondaryCharacteristicRule[Rational],
  basicLiftRule:NonAdjustableSecondaryCharacteristicRule[Weight[Rational]],
  basicMoveRule:AdjustableSecondaryCharacteristicRule[Speed[Int]],
  cpCostEntriesRule:CPCostEntries,
  cpTotalRule:IntPropertyGet,
  damageRule:NonAdjustableSecondaryCharacteristicRule[Damage],
  dexterityRule:BasicAttributeRule,
  dodgeRule:NonAdjustableSecondaryCharacteristicRule[Int],
  fatiguePointsRule:AdjustableSecondaryCharacteristicRule[Int],
  healthRule:BasicAttributeRule,
  hitPointsRule:AdjustableSecondaryCharacteristicRule[Int],
  intelligenceRule:BasicAttributeRule,
  perceptionRule:AdjustableSecondaryCharacteristicRule[Int],
  strengthRule:BasicAttributeRule,
  sumOfNegativeCPCostRule:IntPropertyGet,
  sumOfPositiveCPCostRule:IntPropertyGet,
  validations:GValidations,
  willRule:AdjustableSecondaryCharacteristicRule[Int]
)
object GRules {
  val default:GRules = GRules(
    advantageDataRule = AdvantageDataRule default,
    advantageRules = AdvantageRules default,
    basicSpeedRule = BasicSpeedRule default,
    basicLiftRule = BasicLiftRule default,
    basicMoveRule = BasicMoveRule default,
    cpCostEntriesRule = defaultCPCostEntriesRule,
    cpTotalRule = defaultCPTotalRule,
    damageRule = DamageRule default,
    dodgeRule = DodgeRule default,
    dexterityRule = DexterityRule default,
    fatiguePointsRule = FatiguePointsRule default,
    healthRule = HealthRule default,
    hitPointsRule = HitPointsRule default,
    intelligenceRule = IntelligenceRule default,
    perceptionRule = PerceptionRule default,
    strengthRule = StrengthRule default,
    sumOfNegativeCPCostRule = defaultSumOfNegativeCPCostRule,
    sumOfPositiveCPCostRule = defaultSumOfPositiveCPCostRule,
    validations = GValidations default,
    willRule = WillRule default
  )

  private lazy val defaultCPCostEntriesRule:CPCostEntries = new CPCostEntries {
    def cpCostEntries(data:GCharacterData, context:GContext):Traversable[Int] = {
      // TODO advantages
      import context.rules._
      (Traversable(basicSpeedRule, basicMoveRule, dexterityRule, fatiguePointsRule, healthRule, hitPointsRule,
        intelligenceRule, perceptionRule, strengthRule, willRule) flatMap {attr⇒
        Some(attr cpCostForScore (data, context)) filterNot (_ == 0)
      }) ++
      (advantageDataRule(data, context).allAdvantages map {advantage⇒
        advantageRules get (advantage group) match {
          case None ⇒ 0
          case Some(rule) ⇒
            rule.asInstanceOf[AdvantageRule[AdvantageVariant]] cpCostOf advantage.asInstanceOf[Advantage[AdvantageVariant]]
        }
      })
    }
  }

  private lazy val defaultCPTotalRule:IntPropertyGet = new IntPropertyGet {
    def apply(data:GCharacterData, context:GContext):Int =
      context.rules.cpCostEntriesRule.cpCostEntries(data, context) sum
  }

  private lazy val defaultSumOfNegativeCPCostRule:IntPropertyGet = new IntPropertyGet {
    def apply(data:GCharacterData, context:GContext) =
      context.rules.cpCostEntriesRule.cpCostEntries(data, context) filter {_ < 0} sum
  }

  private lazy val defaultSumOfPositiveCPCostRule:IntPropertyGet = new IntPropertyGet {
    def apply(data:GCharacterData, context:GContext) =
      context.rules.cpCostEntriesRule.cpCostEntries(data, context) filter {_ > 0} sum
  }
}
