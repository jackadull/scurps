package scurps.basic_set.attributes.basic

import scurps._
import scurps.meta.algebra.Optic.Getter
import scurps.meta.algebra.ScurpsOps
import scurps.meta.data.{GCharacterProperty, GameContext}
import scurps.meta.rule.Rule.{Rule0, Rule1}
import scurps.meta.unit.Score.IntScore

trait BasicAttribute extends Rule0[IntScore] {
  override def apply[A[+_]](context:A[GameContext])(implicit ops:ScurpsOps[A]):A[IntScore] =
    BasicAttributeScore(this, context)

  val boughtPointsProperty:BoughtBasicAttributePoints = new BoughtBasicAttributePoints(this)

  val set:Rule1[IntScore,GameContext] = new Rule1[IntScore,GameContext] {
    override def apply[A[+_]](newScore:A[IntScore], context:A[GameContext])(implicit ops:ScurpsOps[A]):A[GameContext] =
      SetBasicAttribute(BasicAttribute.this, newScore, context)
  }
}
object BasicAttribute {
  object Strength extends BasicAttribute {override def toString:String = "Strength"}
  object Dexterity extends BasicAttribute {override def toString:String = "Dexterity"}
  object Health extends BasicAttribute {override def toString:String = "Health"}
  object Intelligence extends BasicAttribute {override def toString:String = "Intelligence"}

  object BoughtPointsProperty extends Getter[BasicAttribute,GCharacterProperty[IntScore]] {
    override def get(source:BasicAttribute):GCharacterProperty[IntScore] = source.boughtPointsProperty
    override def toString:String = "BasicAttribute.BoughtPointsProperty"
  }
}
