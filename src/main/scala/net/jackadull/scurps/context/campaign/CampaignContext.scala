package net.jackadull.scurps.context.campaign

import net.jackadull.scurps.rules.advantages.{Advantage, AdvantageGroup, AdvantageVariant}
import spire.math.Rational

case class CampaignContext(
  campaignAdvantageGroups:Set[AdvantageGroup[_<:AdvantageVariant]],
  campaignAdvantageVariants:Set[AdvantageVariant],
  campaignAdvantages:Set[Advantage[_<:AdvantageVariant]],
  disadvantageLimit:Rational,
  disadvantageLimitMinTotalCP:Int // TODO document deviation
)
object CampaignContext {
  val default:CampaignContext = CampaignContext(
    campaignAdvantageGroups = Set(),
    campaignAdvantageVariants = Set(),
    campaignAdvantages = Set(),
    disadvantageLimit = Rational(1, 2),
    disadvantageLimitMinTotalCP = 10
  )
}
