package scurps.basicset.attributes.basic

import scurps.meta.algebra.ScurpsOps
import scurps.meta.context.RuleContext
import scurps.meta.data.Score
import scurps.meta.data.Score.IntScore
import scurps.meta.rule.Params.ParamsA1
import scurps.meta.rule.Rule.RuleA1
import scurps.meta.rule.RuleKey.RuleKeyA1

case object FreeAttributeScore extends RuleKeyA1[BasicAttribute,IntScore] {
  // TODO move somewhere else
  object BasicSetDefinition extends RuleA1[BasicAttribute,IntScore] {
    override def apply[A[+_]](params:ParamsA1[A[BasicAttribute]], context:A[RuleContext])(implicit ops:ScurpsOps[A]):A[IntScore] =
      ops.constant(Score(10)) // TODO accordingTo; Score(10).constant.forAny[BasicAttribute]
  }
}
