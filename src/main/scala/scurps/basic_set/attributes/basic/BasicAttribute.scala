package scurps.basic_set.attributes.basic

import scurps.meta.algebra.ScurpsOps
import scurps.meta.context.GameContext
import scurps.meta.data.Score.IntScore
import scurps.meta.rule.Rule.{Rule0, Rule1}

trait BasicAttribute extends Rule0[IntScore] {
  override def apply[A[+_]](context:A[GameContext])(implicit ops:ScurpsOps[A]):A[IntScore] =
    BasicAttributeScore(this.constant, context)

  val set:Rule1[IntScore,GameContext] = new Rule1[IntScore,GameContext] {
    override def apply[A[+_]](newScore:A[IntScore], context:A[GameContext])(implicit ops:ScurpsOps[A]):A[GameContext] =
      SetBasicAttribute(BasicAttribute.this.constant, newScore, context)
  }

  private[basic] val boughtPointsKey:BoughtBasicAttributePoints = new BoughtBasicAttributePoints(this)
}
object BasicAttribute {
  object Strength extends BasicAttribute
}