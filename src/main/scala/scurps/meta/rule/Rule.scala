package scurps.meta.rule

import scurps.meta.Concept
import scurps.meta.context.RuleContext
import scurps.meta.derivation.Params.{PList, PNil, Params1, Params2}
import scurps.meta.derivation.{Derivation, Params}
import scurps.meta.math.Add
import scurps.meta.rule.Rule.{AddRule, ForAny}

sealed trait Rule[-P<:Params,+R] extends ((P,Derivation[RuleContext])=>Derivation[R]) {
  def :+[P2<:P,R2>:R](that:Rule[P2,R2])(implicit add:Add[R2]):Rule[P2,R2] = AddRule(this, that)
  def forAny[C](implicit concept:Concept[C]):Rule[PList[C,P],R] = ForAny(this, concept)
}
object Rule {
  type Rule0[+R] = Rule[PNil,R]
  type Rule1[-T1,+R] = Rule[Params1[T1],R]
  type Rule2[-T1,-T2,+R] = Rule[Params2[T1,T2],R]

  def apply[T1,R](createRule:Rule0[T1]=>Rule0[R]):Rule1[T1,R] = ??? // TODO
  def apply[T1,T2,R](createRule:(Rule0[T1],Rule0[T2])=>Rule0[R]):Rule2[T1,T2,R] = ??? // TODO

  def constant[A](value:A):Rule0[A] = Constant(value)

  private[rule] def evalKey[P<:Params,R](key:RuleKey[P,R]):Rule[P,R] = EvalKey(key)

  final case class AddRule[-P<:Params,+R] private(lhs:Rule[P,R], rhs:Rule[P,R])(implicit add:Add[R]) extends Rule[P,R] {
    override def apply(params:P, context:Derivation[RuleContext]):Derivation[R] =
      lhs(params, context).add(rhs(params, context))
  }
  final case class EvalKey[-P<:Params,+R] private(key:RuleKey[P,R]) extends Rule[P,R] {
    override def apply(params:P, context:Derivation[RuleContext]):Derivation[R] =
      context.create.ruleAppliedByKey(key, context.lookupRule(key).applyRule(params, context.ruleContextFor(key)))
  }
  final case class Constant[+R] private(value:R) extends Rule0[R] {
    override def apply(pNil:PNil, context:Derivation[RuleContext]):Derivation[R] = context.create.constant(value)
  }
  final case class ForAny[-P1<:Params,C,+R] private(inner:Rule[P1,R], concept:Concept[C]) extends Rule[PList[C,P1],R] {
    override def apply(params:PList[C,P1], context:Derivation[RuleContext]):Derivation[R] =
      context.create.forAny(inner(params.tail, context), concept)
  }
  private[rule] trait RuleKeyBase[-P<:Params,+R] extends Rule0[Rule[P,R]]
}
