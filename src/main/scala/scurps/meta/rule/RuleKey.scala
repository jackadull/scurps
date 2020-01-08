package scurps.meta.rule

import scurps.meta.algebra.ScurpsOps
import scurps.meta.context.GameContext

trait RuleKey[-P[_[_]],+R] extends Rule[P,R] {
  override def apply[A[+_]](params:P[A], context:A[GameContext])(implicit ops:ScurpsOps[A]):A[R] =
    ops.applyRuleByKey(this, params, context)
}
object RuleKey {
  type RuleKey0[+R] = RuleKey[({type P[A[+_]]=Unit})#P,R]
  type RuleKey1[-T1,+R] = RuleKey[({type P[A[+_]]=A[T1]})#P,R]
  type RuleKey2[-T1,-T2,+R] = RuleKey[({type P[A[+_]]=(A[T1],A[T2])})#P,R]
}
