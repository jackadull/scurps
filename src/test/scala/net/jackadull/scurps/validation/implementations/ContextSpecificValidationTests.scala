package net.jackadull.scurps.validation.implementations

import net.jackadull.scurps.context.GContext
import net.jackadull.scurps.context.campaign.CampaignContext
import net.jackadull.scurps.meta.data.GCharacterData
import net.jackadull.scurps.rules.GRules
import net.jackadull.scurps.validation.ValidationResult.ValidationIssue
import org.scalatest.{FreeSpec, Matchers}

import scala.language.postfixOps

class ContextSpecificValidationTests extends FreeSpec with Matchers {
  "DisadvantageLimitValidation" - {
    "for campaing disadvantage limit = 0 CP" in {
      val context = GContext(CampaignContext.default.copy(disadvantageLimitMinTotalCP = 0), GRules default)
      val ch = {
        val st12 = context.rules.strengthRule.score.set(12, GCharacterData.empty, context)
        val iq9 = context.rules.intelligenceRule.score.set(9, st12, context)
        iq9
      }
      context.rules.cpTotalRule(ch, context) should be (0)
      DisadvantageLimitValidation(ch, context) match {
        case ValidationIssue(code, parameters) ⇒
          code should be (DisadvantageLimitValidation issueCode)
          parameters.asMap get "disadvantageQuota" should be (Some(None))
        case anotherResult ⇒ fail(s"expected ValidationIssue, but got: $anotherResult")
      }
    }
  }
}
