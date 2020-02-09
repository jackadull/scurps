package scurps.meta.unit

import scurps.meta.algebra.ArithmeticOperation.{Addition, IsZero, Multiplication, Subtraction}
import scurps.meta.unit.Score.IntScore

/** Character points. They only come in integer values. */
final case class CP(intValue:Int) extends AnyVal {
  @inline def +(that:CP):CP = CP(intValue + that.intValue)
  @inline def -(that:CP):CP = CP(intValue - that.intValue)
  @inline def <(that:CP):Boolean = intValue < that.intValue
  @inline def >(that:CP):Boolean = intValue > that.intValue
}
object CP {
  implicit val cpAddition:Addition[CP] = new Addition[CP] {
    override def apply(lhs:CP, rhs:CP):CP = CP(lhs.intValue + rhs.intValue)
    override def toString():String = "Addition[CP]"
  }
  implicit val cpIsZero:IsZero[CP] = new IsZero[CP] {
    override def apply(v:CP):Boolean = v.intValue==0
    override def toString():String = "IsZero[CP]"
  }
  implicit val cpSubtraction:Subtraction[CP] = new Subtraction[CP] {
    override def apply(lhs:CP, rhs:CP):CP = CP(lhs.intValue - rhs.intValue)
    override def toString():String = "Subtraction[CP]"
  }
  implicit val cpWithIntScoreMultiplication:Multiplication[CP,IntScore,CP] = new Multiplication[CP,IntScore,CP] {
    override def apply(lhs:CP, rhs:IntScore):CP = CP(lhs.intValue * rhs.intValue)
    override def toString():String = "Multiplication[CP,IntScore,CP]"
  }
}
