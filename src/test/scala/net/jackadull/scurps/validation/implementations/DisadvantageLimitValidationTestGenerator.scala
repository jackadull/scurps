package net.jackadull.scurps.validation.implementations

import net.jackadull.scurps.context.GContext
import net.jackadull.scurps.meta.data.GCharacterData
import net.jackadull.scurps.models.math.Rounding
import net.jackadull.scurps.testing.TestHierarchy
import net.jackadull.scurps.testing.TestHierarchy._
import net.jackadull.scurps.validation.ValidationIssueParametersGeneratorBase
import net.jackadull.scurps.validation.ValidationResult.{Okay, ValidationIssue}

import scala.language.postfixOps

object DisadvantageLimitValidationTestGenerator {
  def globalInvariants(data:GCharacterData, context:GContext):Seq[TestHierarchy] =
    context.campaign.disadvantageLimitMinTotalCP match {
      case moreThanCharacterTotalCP if moreThanCharacterTotalCP > context.rules.cpTotalRule(data, context) ⇒ Seq(
        testLeaf("disadvantage limit does not apply (because character's total CP are below threshold)") {
          val result = context.rules.validations.get(DisadvantageLimitValidation id).get(data, context)
          assert(result == Okay, s"expected 'Okay' for the disadvantage limit validation, but got: $result")
          inline(IssueParametersGenerator(result))
        }
      )
      case _ ⇒ // disadvantage limit applies
        val limit = -Rounding.roundPointCost(context.campaign.disadvantageLimit * spire.math.Rational(context.rules.cpTotalRule(data, context), 1))
        val negativeCPSum = context.rules.sumOfNegativeCPCostRule(data, context)
        Seq(testLeaf("disadvantage limit applies") {
          val result = context.rules.validations.get(DisadvantageLimitValidation id).get(data, context)
          if(limit <= negativeCPSum) assert(result == Okay, s"expected 'Okay' for disadvantage limit validation because limit ($limit) <= total negative CP ($negativeCPSum), but got: $result")
          else result match {
            case issue:ValidationIssue if issue.code == DisadvantageLimitValidation.id ⇒ ()
            case _ ⇒ fail(s"expected an issue for disadvantage limit validation because limit ($limit) > total negative CP ($negativeCPSum), but got: $result")
          }
          inline(IssueParametersGenerator(result))
        })
    }

  def initialConditions(context:GContext):Seq[TestHierarchy] = Seq(TestBranch("disadvantage limit", Seq(
    TestBranch("initial character", globalInvariants(GCharacterData empty, context)),
    TestBranch("character one advantage only", globalInvariants(context.rules.strengthRule.score.set(12, GCharacterData empty, context), context)),
    TestBranch("character that hits the limit", globalInvariants({
      val int18 = context.rules.intelligenceRule.score.set(18, GCharacterData empty, context)
      val dex6 = context.rules.dexterityRule.score.set(6, int18, context)
      dex6
    }, context)),
    TestBranch("character below the limit", globalInvariants({
      val int18 = context.rules.intelligenceRule.score.set(18, GCharacterData empty, context)
      val dex9 = context.rules.dexterityRule.score.set(9, int18, context)
      dex9
    }, context))
  )))

  private object IssueParametersGenerator extends ValidationIssueParametersGeneratorBase
  [DisadvantageLimitValidation.FailureParameters](Set(DisadvantageLimitValidation issueCode)) {
    protected def expectedMapEntries(params:DisadvantageLimitValidation.FailureParameters):Iterable[(String,Any)] = Seq(
      "campaignDisadvantageLimit" → params.campaignDisadvantageLimit,
      "campaignDisadvantageLimitMinTotalCP" → params.campaignDisadvantageLimitMinTotalCP,
      "disadvantageQuota" → params.disadvantageQuota,
      "totalCP" → params.totalCP,
      "totalNegativeCPAbs" → params.totalNegativeCPAbs
    )
  }
}
