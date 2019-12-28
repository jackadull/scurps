package scurps.meta.rule

import scurps.meta.algebra.ScurpsOps
import scurps.meta.context.RuleContext
import scurps.meta.rule.Params.{ParamsA0, ParamsA1}

trait RuleKey[-P[_[_]]<:Params,+R] extends Rule[P,R] {
  override def apply[A[+_]](params:P[A], context:A[RuleContext])(implicit ops:ScurpsOps[A]):A[R] = ??? // TODO
}
object RuleKey {
  type RuleKeyA0[+R] = RuleKey[({type P[A[+_]]=ParamsA0})#P,R]
  type RuleKeyA1[-T1, +R] = RuleKey[({type P[A[+_]]=ParamsA1[A[T1]]})#P,R]
}
