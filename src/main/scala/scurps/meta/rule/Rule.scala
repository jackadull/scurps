package scurps.meta.rule

import scurps.meta.algebra.ScurpsOps
import scurps.meta.context.RuleContext
import scurps.meta.math.Add
import scurps.meta.rule.Params.{ParamsA0, ParamsA1, ParamsA2, ParamsA3}

trait Rule[-P[_[_]]<:Params,+R] {
  def apply[A[+_]](params:P[A], context:A[RuleContext])(implicit ops:ScurpsOps[A]):A[R]
}
object Rule {
  type RuleA0[+R] = Rule[({type P[A[+_]]=ParamsA0})#P,R]
  type RuleA1[-T1,+R] = Rule[({type P[A[+_]]=ParamsA1[A[T1]]})#P,R]
  type RuleA2[-T1,-T2,+R] = Rule[({type P[A[+_]]=ParamsA2[A[T1],A[T2]]})#P,R]
  type RuleA3[-T1,-T2,-T3,+R] = Rule[({type P[A[+_]]=ParamsA3[A[T1],A[T2],A[T3]]})#P,R]

  private final class AddRule[T](implicit add:Add[T]) extends RuleA2[T,T,T] {
    override def apply[A[+_]](params:ParamsA2[A[T],A[T]], context:A[RuleContext])(implicit ops:ScurpsOps[A]):A[T] =
      ops.added(params.head, params.tail.head)
  }
}
