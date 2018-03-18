package net.jackadull.scurps.validation.implementations

import net.jackadull.scurps.context.GContext
import net.jackadull.scurps.meta.data.GCharacterData
import net.jackadull.scurps.validation.ValidationResult.{Okay, ValidationIssue}
import net.jackadull.scurps.validation.{GValidation, ValidationResult}
import spire.math.Rational

object DisadvantageLimitValidation extends GValidation {
  def id = "scurps.basic.DisadvantageLimit"
  def issueCode = id
  def apply(data:GCharacterData, context:GContext) = {
    val totalCP:Int = context.rules.cpTotalRule(data, context)
    if(totalCP >= context.campaign.disadvantageLimitMinTotalCP) {
      val totalNegativeCPAbs:Int = -context.rules.sumOfNegativeCPCostRule(data, context)

      def failure(quotaOpt:Option[Rational]) = ValidationIssue(issueCode, FailureParameters(
        context.campaign.disadvantageLimit, context.campaign.disadvantageLimitMinTotalCP, quotaOpt, totalCP,
        totalNegativeCPAbs))

      if(totalNegativeCPAbs > 0)
        if(totalCP == 0) failure(None)
        else {
          val disadvantageQuota = Rational(totalNegativeCPAbs, totalCP)
          if(disadvantageQuota > context.campaign.disadvantageLimit) failure(Some(disadvantageQuota))
          else Okay
        }
      else Okay
    } else Okay
  }

  case class FailureParameters(campaignDisadvantageLimit:Rational, campaignDisadvantageLimitMinTotalCP:Int,
    disadvantageQuota:Option[Rational], totalCP:Int, totalNegativeCPAbs:Int) extends ValidationResult.IssueParameters {
    def asMap:Map[String,Any] = Map(
      "campaignDisadvantageLimit" → campaignDisadvantageLimit,
      "campaignDisadvantageLimitMinTotalCP" → campaignDisadvantageLimitMinTotalCP,
      "disadvantageQuota" → disadvantageQuota,
      "totalCP" → totalCP,
      "totalNegativeCPAbs" → totalNegativeCPAbs
    )
  }
}
