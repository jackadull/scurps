package scurps.meta

import scurps.basicset.attributes.basic.PMapLike
import scurps.meta.Derivation.MissingRule

trait GameContext extends PMap[ContextKey] with PMapLike[ContextKey,GameContext] {
  def ruleCatalog:RuleCatalog

  def apply[R](ruleKey:RuleKey[Rule0[R]]):Derivation[R] =
    ruleCatalog.get(ruleKey).map(_()(this)).getOrElse(MissingRule(ruleKey))
  def apply[T1,R](ruleKey:RuleKey[Rule1[T1,R]], v1:T1):Derivation[R] =
    ruleCatalog.get(ruleKey).map(_(v1)(this)).getOrElse(MissingRule(ruleKey))
  def apply[T1,T2,R](ruleKey:RuleKey[Rule2[T1,T2,R]], v1:T1, v2:T2):Derivation[R] =
    ruleCatalog.get(ruleKey).map(_(v1,v2)(this)).getOrElse(MissingRule(ruleKey))
}
