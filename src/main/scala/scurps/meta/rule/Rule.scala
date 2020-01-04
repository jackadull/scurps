package scurps.meta.rule

import scurps.meta.algebra.ScurpsOps
import scurps.meta.context.GameContext
import scurps.meta.rule.Params.{Params0, Params1, Params2, Params3}

trait Rule[-P[_[_]]<:Params,+R] {
  def apply[A[+_]](params:P[A], context:A[GameContext])(implicit ops:ScurpsOps[A]):A[R]
}
object Rule {
  type Rule0[+R] = Rule[({type P[A[+_]]=Params0})#P,R]
  type Rule1[-T1,+R] = Rule[({type P[A[+_]]=Params1[A[T1]]})#P,R]
  type Rule2[-T1,-T2,+R] = Rule[({type P[A[+_]]=Params2[A[T1],A[T2]]})#P,R]
  type Rule3[-T1,-T2,-T3,+R] = Rule[({type P[A[+_]]=Params3[A[T1],A[T2],A[T3]]})#P,R]
}
