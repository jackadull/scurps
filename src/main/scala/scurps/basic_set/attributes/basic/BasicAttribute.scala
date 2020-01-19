package scurps.basic_set.attributes.basic

import scurps._
import scurps.meta.algebra.ScurpsOps
import scurps.meta.data.GameContext
import scurps.meta.rule.Rule.{Rule0, Rule1}
import scurps.meta.unit.Score.IntScore

trait BasicAttribute extends Rule0[IntScore] {
  override def apply[A[+_]](context:A[GameContext])(implicit ops:ScurpsOps[A]):A[IntScore] =
    BasicAttributeScore(this, context)

  val set:Rule1[IntScore,GameContext] = new Rule1[IntScore,GameContext] {
    override def apply[A[+_]](newScore:A[IntScore], context:A[GameContext])(implicit ops:ScurpsOps[A]):A[GameContext] =
      SetBasicAttribute(BasicAttribute.this, newScore, context)
  }

  private[basic] val boughtPointsKey:BoughtBasicAttributePoints = new BoughtBasicAttributePoints(this)
}
object BasicAttribute {
  case object Strength extends BasicAttribute {override def toString:String = "Strength"}
  case object Dexterity extends BasicAttribute {override def toString:String = "Dexterity"}
  case object Health extends BasicAttribute {override def toString:String = "Health"}
  case object Intelligence extends BasicAttribute {override def toString:String = "Intelligence"}
}
