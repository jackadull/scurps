package scurps.meta.data

import scurps.meta.algebra.ArithmeticOp.{Addition, IsZero, Multiplication, Subtraction}

sealed trait Score[+A] extends Any
object Score {
  @inline def apply(intValue:Int):IntScore = IntScore(intValue)

  final case class IntScore private(intValue:Int) extends AnyVal with Score[Int]
  object IntScore {
    implicit val intScoreAddition:Addition[IntScore] = {(lhs,rhs) => IntScore(lhs.intValue + rhs.intValue)}
    implicit val intScoreIsZero:IsZero[IntScore] = _.intValue==0
    implicit val intScoreSubtraction:Subtraction[IntScore] = {(lhs,rhs) => IntScore(lhs.intValue - rhs.intValue)}

    implicit val intScoreWithCPMultiplication:Multiplication[IntScore,CP,CP] = {(lhs,rhs) => CP(lhs.intValue * rhs.intValue)}
  }
}
