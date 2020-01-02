package scurps.basicset.attributes

import scurps.basicset.attributes.basic.BoughtBasicAttributePoints.wrapper
import scurps.meta.algebra.ScurpsOps
import scurps.meta.algebra.ScurpsOpsImplicits._
import scurps.meta.context.ContextKey.Subject
import scurps.meta.context.RuleContext
import scurps.meta.data.Score
import scurps.meta.data.Score.IntScore
import scurps.meta.rule.Params.ParamsA1
import scurps.meta.rule.Rule.RuleA1
import scurps.meta.rule.RuleCatalog

package object basic {
  val basicSetRules:RuleCatalog = RuleCatalog(
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
}
