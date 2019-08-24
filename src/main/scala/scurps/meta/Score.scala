package scurps.meta

import scurps.meta.math.Add
import scurps.meta.rule.Rule
import scurps.meta.rule.Rule.Rule0

sealed trait Score[+A] extends Any {
  def asConstant:Rule0[this.type] = Rule.constant(this)
}
object Score {
  @inline def apply(intValue:Int):IntScore = IntScore(intValue)

  final case class IntScore private(intValue:Int) extends AnyVal with Score[Int]
  object IntScore {
    implicit object IntScoreAdd extends Add[IntScore]
      {@inline override def apply(v1:IntScore, v2:IntScore):IntScore = Score(v1.intValue + v2.intValue)}
  }
}
