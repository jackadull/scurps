package scurps.meta.data

import scurps.meta.data.Score.IntScore
import scurps.meta.algebra.ArithmeticOp.{Addition, IsZero, Multiplication, Subtraction}

/** Character points. They only come in integer values. */
final case class CP(intValue:Int) extends AnyVal
object CP {
  implicit val cpAddition:Addition[CP] = {(lhs,rhs) => CP(lhs.intValue + rhs.intValue)}
  implicit val cpIsZero:IsZero[CP] = _.intValue==0
  implicit val cpSubtraction:Subtraction[CP] = {(lhs,rhs) => CP(lhs.intValue - rhs.intValue)}

  implicit val cpWithIntScoreMultiplication:Multiplication[CP,IntScore,CP] = {(lhs,rhs) => CP(lhs.intValue * rhs.intValue)}
}
