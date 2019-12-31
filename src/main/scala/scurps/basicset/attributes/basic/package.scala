package scurps.basicset.attributes

import scurps.basicset.attributes.basic.BoughtBasicAttributePoints.wrapper
import scurps.meta.algebra.ScurpsOps
import scurps.meta.algebra.ScurpsOpsImplicits._
import scurps.meta.context.ContextKey.Subject
import scurps.meta.context.{RuleContext, ValueKey}
import scurps.meta.data.KeyWrapper.KeyWrapperSupport
import scurps.meta.data.Score
import scurps.meta.data.Score.IntScore
import scurps.meta.rule.Params.ParamsA1
import scurps.meta.rule.Rule.RuleA1
import scurps.meta.rule.RuleCatalog
import scurps.meta.rule.RuleKey.RuleKeyA1

package object basic {
  val rules:RuleCatalog = RuleCatalog(
    BasicAttributeScore -> new RuleA1[BasicAttribute,IntScore] {
      override def apply[A[+_]](params:ParamsA1[A[BasicAttribute]], context:A[RuleContext])(implicit ops:ScurpsOps[A]):A[IntScore] =
        FreeAttributeScore(params, context) :+ BoughtBasicAttributePoints(params, context) // TODO accordingTo
    },
    BoughtBasicAttributePoints -> new RuleA1[BasicAttribute,IntScore] {
      override def apply[A[+_]](params:ParamsA1[A[BasicAttribute]], context:A[RuleContext])(implicit ops:ScurpsOps[A]):A[IntScore] =
        context.get(Subject).get(params.head).orElse(Score(0).constant) // TODO accordingTo; params._1 (or p1); if there is no subject, result should be undefined
    },
    FreeAttributeScore -> new RuleA1[BasicAttribute,IntScore] {
      override def apply[A[+_]](params:ParamsA1[A[BasicAttribute]], context:A[RuleContext])(implicit ops:ScurpsOps[A]):A[IntScore] =
        ops.constant(Score(10)) // TODO accordingTo; Score(10).constant.forAny[BasicAttribute]
    }
  )

  case object BasicAttributeScore extends RuleKeyA1[BasicAttribute,IntScore]

  final case class BoughtBasicAttributePoints(attribute:BasicAttribute) extends ValueKey[IntScore]
  case object BoughtBasicAttributePoints extends RuleKeyA1[BasicAttribute,IntScore]
    with KeyWrapperSupport[BoughtBasicAttributePoints,BasicAttribute] {
    override def wrap(attr:BasicAttribute):BoughtBasicAttributePoints = BoughtBasicAttributePoints(attr)
  }

  case object FreeAttributeScore extends RuleKeyA1[BasicAttribute,IntScore]
}
