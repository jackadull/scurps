package scurps.meta.rule

import scurps.meta.algebra.ScurpsOps
import scurps.meta.context.GameContext
import scurps.meta.data.ShowKey.ShowKey

trait RuleKey[-P[_[_]],+R] extends Rule[P,R] with ShowKey {
  override def applyP[A[+_]](params:P[A], context:A[GameContext])(implicit ops:ScurpsOps[A]):A[R] =
    ops.applyRuleByKey(this, params, context)
}
object RuleKey {
  trait RuleKey0[R] extends RuleKey[({type P[A[+_]]=Unit})#P,R] with Rule[({type P[A[+_]]=Unit})#P,R] {
    final def apply[A[+_]](context:A[GameContext])(implicit ops:ScurpsOps[A]):A[R] =
      applyP((), context)
  }
  trait RuleKey1[T1,R] extends RuleKey[({type P[A[+_]]=A[T1]})#P,R] with Rule[({type P[A[+_]]=A[T1]})#P,R] {
    final def apply[A[+_]](v1:A[T1], context:A[GameContext])(implicit ops:ScurpsOps[A]):A[R] =
      applyP(v1, context)
  }
  trait RuleKey2[T1,T2,R] extends RuleKey[({type P[A[+_]]=(A[T1],A[T2])})#P,R] with Rule[({type P[A[+_]]=(A[T1],A[T2])})#P,R] {
    final def apply[A[+_]](v1:A[T1], v2:A[T2], context:A[GameContext])(implicit ops:ScurpsOps[A]):A[R] =
      applyP((v1, v2), context)
  }
  trait RuleKey3[T1,T2,T3,R] extends RuleKey[({type P[A[+_]]=(A[T1],A[T2],A[T3])})#P,R] with Rule[({type P[A[+_]]=(A[T1],A[T2],A[T3])})#P,R] {
    final def apply[A[+_]](v1:A[T1], v2:A[T2], v3:A[T3], context:A[GameContext])(implicit ops:ScurpsOps[A]):A[R] =
      applyP((v1, v2, v3), context)
  }
}
