package scurps.meta

import scurps.basicset.attributes.basic.PMapLike
import scurps.meta.RuleKey.{RuleKey0, RuleKey1}

trait GameContext extends PMap[ContextKey] with PMapLike[ContextKey,GameContext] {
  def ruleCatalog:RuleCatalog

  def apply[R](key:RuleKey0[R]):Derivation[R] = key.derive(this)
  def apply[T1,R](key:RuleKey1[T1,R], v1:T1):Derivation[R] = key.derive(v1)(this)
}
