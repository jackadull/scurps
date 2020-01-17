package scurps.meta.data

import scurps.meta.math.{Add, IsZero, Subtract}

/** Character points. They only come in integer values. */
final case class CP(intValue:Int) extends AnyVal
object CP {
  implicit val addCP:Add[CP] = {(lhs,rhs) => CP(lhs.intValue + rhs.intValue)}
  implicit val isZeroCP:IsZero[CP] = _.intValue==0
  implicit val subtractCP:Subtract[CP] = {(lhs,rhs) => CP(lhs.intValue - rhs.intValue)}
}
