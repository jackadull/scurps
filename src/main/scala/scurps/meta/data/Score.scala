package scurps.meta.data

import scurps.meta.math.{Add, Subtract}

sealed trait Score[+A] extends Any
object Score {
  @inline def apply(intValue:Int):IntScore = IntScore(intValue)

  final case class IntScore private(intValue:Int) extends AnyVal with Score[Int]
  object IntScore {
    implicit object IntScoreAdd extends Add[IntScore]
      {@inline override def apply(v1:IntScore, v2:IntScore):IntScore = Score(v1.intValue + v2.intValue)}
    implicit object IntScoreSubtract extends Subtract[IntScore]
      {@inline override def apply(v1:IntScore, v2:IntScore):IntScore = Score(v1.intValue - v2.intValue)}
  }
}
