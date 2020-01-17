package scurps.meta.data

import scurps.meta.math.{Add, IsZero, Subtract}

sealed trait Score[+A] extends Any
object Score {
  @inline def apply(intValue:Int):IntScore = IntScore(intValue)

  final case class IntScore private(intValue:Int) extends AnyVal with Score[Int]
  object IntScore {
    implicit val addIntScore:Add[IntScore] = {(lhs,rhs) => IntScore(lhs.intValue + rhs.intValue)}
    implicit val isZeroIntScore:IsZero[IntScore] = _.intValue==0
    implicit val subtactIntScore:Subtract[IntScore] = {(lhs,rhs) => IntScore(lhs.intValue - rhs.intValue)}
  }
}
