package scurps.meta.derivation

import scurps.meta.Concept
import scurps.meta.rule.RuleKey

trait DerivationFactory {
  def added[A](value:A, lhs:Derivation[A], rhs:Derivation[A]):Derivation[A]
  def constant[A](value:A):Derivation[A]
  def forAny[A,C](inner:Derivation[A], concept:Concept[C]):Derivation[A]
  def ruleAppliedByKey[P<:Params,R](ruleKey:RuleKey[P,R], value:Derivation[R]):Derivation[R]
}
