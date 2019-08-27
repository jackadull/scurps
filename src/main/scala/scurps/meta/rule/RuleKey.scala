package scurps.meta.rule

import scurps.meta.derivation.Params.{PNil, Params1}
import scurps.meta.derivation.{Derivation, DerivationF0, Params}

trait RuleKey[-P<:Params,+R] extends DerivationF0[RuleCatalog,Rule[P,R]] {
  override def apply(pNil:PNil, catalog:Derivation[RuleCatalog]):Derivation[Rule[P,R]] = catalog.lookupRule(this)
  val rule:Rule[P,R] = Rule.evalKey(this)
}
object RuleKey {
  type RuleKey0[+R] = RuleKey[PNil,R]
  type RuleKey1[-T1,+R] = RuleKey[Params1[T1],R]
}
