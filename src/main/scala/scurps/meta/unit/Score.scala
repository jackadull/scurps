package scurps.meta.unit

import scurps.meta.algebra.ArithmeticOperation.{Addition, IsZero, Multiplication, Subtraction}

sealed trait Score[+A] extends Any
object Score {
  @inline def apply(intValue:Int):IntScore = IntScore(intValue)

  final case class IntScore private(intValue:Int) extends AnyVal with Score[Int]
  object IntScore {
    implicit val intScoreAddition:Addition[IntScore] = new Addition[IntScore] {
      override def apply(lhs:IntScore, rhs:IntScore):IntScore = IntScore(lhs.intValue + rhs.intValue)
      override def toString():String = "Addition[IntScore]"
    }
    implicit val intScoreIsZero:IsZero[IntScore] = new IsZero[IntScore] {
      override def apply(v:IntScore):Boolean = v.intValue==0
      override def toString():String = "IsZero[IntScore]"
    }
    implicit val intScoreSubtraction:Subtraction[IntScore] = new Subtraction[IntScore] {
      override def apply(lhs:IntScore, rhs:IntScore):IntScore = IntScore(lhs.intValue - rhs.intValue)
      override def toString():String = "Subtraction[IntScore]"
    }

    implicit val intScoreWithCPMultiplication:Multiplication[IntScore,CP,CP] = new Multiplication[IntScore,CP,CP] {
      override def apply(lhs:IntScore, rhs:CP):CP = CP(lhs.intValue * rhs.intValue)
      override def toString():String = "Multiplication[IntScore,CP,CP]"
    }
  }
}
