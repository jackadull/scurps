package scurps.meta

import scurps.meta.Rule.{DefineAsConstant, Rule0}
import scurps.meta.math.Add

sealed trait Score[+A] extends Any {
  def asConstant:Rule0[this.type] = DefineAsConstant(this)
}
object Score {
  @inline def apply(intValue:Int):IntScore = IntScore(intValue)

  final case class IntScore private(intValue:Int) extends AnyVal with Score[Int]
  object IntScore {
    implicit object IntScoreAdd extends Add[IntScore]
      {@inline override def apply(v1:IntScore, v2:IntScore):IntScore = Score(v1.intValue + v2.intValue)}
  }
}
