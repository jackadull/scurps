package scurps.meta.data

import scurps.meta.math.{Add, IsZero, Subtract}

// TODO CPImplicits, for e.g. `10.CP`
/** Character points. They only come in integer values. */
final case class CP(intValue:Int) extends AnyVal
object CP {
  implicit object CPArithmetic extends Add[CP] with IsZero[CP] with Subtract[CP] {
    @inline override def add(lhs:CP, rhs:CP):CP = CP(lhs.intValue + rhs.intValue)
    @inline override def isZero(v:CP):Boolean = v.intValue==0
    @inline override def subtract(lhs:CP, rhs:CP):CP = CP(lhs.intValue - rhs.intValue)
  }
}
