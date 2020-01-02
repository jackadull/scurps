package scurps.basicset.attributes.basic

import scurps.meta.algebra.ScurpsOps
import scurps.meta.algebra.ScurpsOpsImplicits._
import scurps.meta.context.{RuleContext, ValueKey}
import scurps.meta.data.Score.IntScore
import scurps.meta.data.WrapKey
import scurps.meta.rule.Params
import scurps.meta.rule.Params.Params0
import scurps.meta.rule.Rule.RuleA0
import scurps.meta.rule.RuleKey.RuleKeyA1

trait BasicAttribute extends RuleA0[IntScore] {
  override def apply[A[+_]](params:Params0, context:A[RuleContext])(implicit ops:ScurpsOps[A]):A[IntScore] =
    BasicAttributeScore(Params(this.constant), context) // TODO maybe p(this.constant) instead of ParamsA(...)
}
object BasicAttribute {
  object Strength extends BasicAttribute

  implicit val wrapBought:WrapKey[BasicAttribute,BoughtBasicAttributePoints] = BoughtBasicAttributePoints(_)
}

case object BasicAttributeScore extends RuleKeyA1[BasicAttribute,IntScore]

final case class BoughtBasicAttributePoints(attribute:BasicAttribute) extends ValueKey[IntScore]
case object BoughtBasicAttributePoints extends RuleKeyA1[BasicAttribute,IntScore]

case object FreeAttributeScore extends RuleKeyA1[BasicAttribute,IntScore]
