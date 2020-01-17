package scurps.meta.data

import scurps.meta.math.{Add, IsZero, Subtract}

sealed trait Score[+A] extends Any
object Score {
  @inline def apply(intValue:Int):IntScore = IntScore(intValue)

  final case class IntScore private(intValue:Int) extends AnyVal with Score[Int]
  object IntScore {
    // TODO find a good super trait that combines those arithmetic operations, also for CP
    implicit object IntScoreArithmetic extends Add[IntScore] with IsZero[IntScore] with Subtract[IntScore] {
      @inline override def add(lhs:IntScore, rhs:IntScore):IntScore = Score(lhs.intValue + rhs.intValue)
      @inline override def isZero(v:IntScore):Boolean = v.intValue==0
      @inline override def subtract(lhs:IntScore, rhs:IntScore):IntScore = Score(lhs.intValue - rhs.intValue)
    }
  }
}
