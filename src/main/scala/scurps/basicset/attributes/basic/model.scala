package scurps.basicset.attributes.basic

import scurps.meta.algebra.ScurpsOps
import scurps.meta.context.{GameContext, RuleContext, ValueKey}
import scurps.meta.data.Score.IntScore
import scurps.meta.data.WrapKey
import scurps.meta.rule.Params.{Params0, Params1, p}
import scurps.meta.rule.Rule.{Rule0, Rule1}
import scurps.meta.rule.RuleKey.{RuleKey1, RuleKey2}

trait BasicAttribute extends Rule0[IntScore] {
  override def apply[A[+_]](params:Params0, context:A[RuleContext])(implicit ops:ScurpsOps[A]):A[IntScore] =
    BasicAttributeScore(p(this.constant), context)

  val set:Rule1[IntScore,GameContext] = new Rule1[IntScore,GameContext] {
    override def apply[A[+_]](params:Params1[A[IntScore]], context:A[RuleContext])(implicit ops:ScurpsOps[A]):A[GameContext] =
      SetBasicAttribute(p(BasicAttribute.this.constant, params._1), context)
  }
}
object BasicAttribute {
  object Strength extends BasicAttribute

  implicit val wrapBought:WrapKey[BasicAttribute,BoughtBasicAttributePoints] = BoughtBasicAttributePoints(_)
}

case object BasicAttributeScore extends RuleKey1[BasicAttribute,IntScore]

final case class BoughtBasicAttributePoints(attribute:BasicAttribute) extends ValueKey[IntScore]
case object BoughtBasicAttributePoints extends RuleKey1[BasicAttribute,IntScore]

case object FreeAttributeScore extends RuleKey1[BasicAttribute,IntScore]

case object SetBasicAttribute extends RuleKey2[BasicAttribute,IntScore,GameContext]
