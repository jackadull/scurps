package scurps.basicset.attributes

import scurps.basicset.attributes.basic.BoughtBasicAttributePoints.Wrapper
import scurps.meta.algebra.ScurpsOps
import scurps.meta.algebra.ScurpsOpsImplicits._
import scurps.meta.context.ContextKey.Subject
import scurps.meta.context.{RuleContext, ValueKey}
import scurps.meta.data.Score.IntScore
import scurps.meta.data.{KeyWrapper, Score}
import scurps.meta.rule.Params.ParamsA1
import scurps.meta.rule.Rule.RuleA1
import scurps.meta.rule.RuleCatalog
import scurps.meta.rule.RuleKey.RuleKeyA1

package object basic {
  val rules:RuleCatalog = RuleCatalog(
    BasicAttributeScore -> BasicAttributeScoreRule,
    BoughtBasicAttributePoints -> BoughtBasicAttributePointsRule,
    FreeAttributeScore -> FreeAttributeScoreRule
  )

  case object BasicAttributeScore extends RuleKeyA1[BasicAttribute,IntScore]

  final case class BoughtBasicAttributePoints(attribute:BasicAttribute) extends ValueKey[IntScore]
  case object BoughtBasicAttributePoints extends RuleKeyA1[BasicAttribute,IntScore] {
    // TODO re-structure to a one-liner
    implicit object Wrapper extends KeyWrapper[BoughtBasicAttributePoints,BasicAttribute] {
      override def apply(value:BasicAttribute):BoughtBasicAttributePoints = BoughtBasicAttributePoints(value)
    }
  }

  case object FreeAttributeScore extends RuleKeyA1[BasicAttribute,IntScore]

  // TODO check if the rules below can be methods of an object

  private object BasicAttributeScoreRule extends RuleA1[BasicAttribute,IntScore] {
    override def apply[A[+_]](params:ParamsA1[A[BasicAttribute]], context:A[RuleContext])(implicit ops:ScurpsOps[A]):A[IntScore] =
      FreeAttributeScore(params, context) :+ BoughtBasicAttributePoints(params, context) // TODO accordingTo
  }

  private object BoughtBasicAttributePointsRule extends RuleA1[BasicAttribute,IntScore] {
    override def apply[A[+_]](params:ParamsA1[A[BasicAttribute]], context:A[RuleContext])(implicit ops:ScurpsOps[A]):A[IntScore] =
      context.get(Subject).get(params.head).orElse(Score(0).constant) // TODO accordingTo; params._1 (or p1); if there is no subject, result should be undefined
  }

  private object FreeAttributeScoreRule extends RuleA1[BasicAttribute,IntScore] {
    override def apply[A[+_]](params:ParamsA1[A[BasicAttribute]], context:A[RuleContext])(implicit ops:ScurpsOps[A]):A[IntScore] =
      ops.constant(Score(10)) // TODO accordingTo; Score(10).constant.forAny[BasicAttribute]
  }
}
