package scurps.meta.rule

import scurps.meta.algebra.ScurpsOps
import scurps.meta.data.GameContext

trait Rule[-P[_[_]],+R] {
  def applyP[A[+_]](params:P[A], context:A[GameContext])(implicit ops:ScurpsOps[A]):A[R]
  override def toString:String = "<rule>"
}
object Rule {
  trait Rule0[R] extends Rule[({type P[A[+_]]=Unit})#P,R] {
    def apply[A[+_]](context:A[GameContext])(implicit ops:ScurpsOps[A]):A[R]
    override final def applyP[A[+_]](params:Unit, context:A[GameContext])(implicit ops:ScurpsOps[A]):A[R] =
      apply(context)
    override def toString:String = "<rule0>"
  }

  trait Rule1[T1,R] extends Rule[({type P[A[+_]]=A[T1]})#P,R] {
    def apply[A[+_]](v1:A[T1], context:A[GameContext])(implicit ops:ScurpsOps[A]):A[R]
    override final def applyP[A[+_]](params:A[T1], context:A[GameContext])(implicit ops:ScurpsOps[A]):A[R] =
      apply(params, context)
    override def toString:String = "<rule1>"
  }

  trait Rule2[T1,T2,R] extends Rule[({type P[A[+_]]=(A[T1],A[T2])})#P,R] {
    def apply[A[+_]](v1:A[T1], v2:A[T2], context:A[GameContext])(implicit ops:ScurpsOps[A]):A[R]
    override final def applyP[A[+_]](params:(A[T1], A[T2]), context:A[GameContext])(implicit ops:ScurpsOps[A]):A[R] =
      apply(params._1, params._2, context)
    override def toString:String = "<rule2>"
  }

  trait Rule3[T1,T2,T3,R] extends Rule[({type P[A[+_]]=(A[T1],A[T2],A[T3])})#P,R] {
    def apply[A[+_]](v1:A[T1], v2:A[T2], v3:A[T3], context:A[GameContext])(implicit ops:ScurpsOps[A]):A[R]
    override final def applyP[A[+_]](params:(A[T1], A[T2], A[T3]), context:A[GameContext])(implicit ops:ScurpsOps[A]):A[R] =
      apply(params._1, params._2, params._3, context)
    override def toString:String = "<rule3>"
  }
}
