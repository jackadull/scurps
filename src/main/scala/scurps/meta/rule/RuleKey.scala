package scurps.meta.rule

import scurps.meta.derivation.DerivationF.DerivationF0Base
import scurps.meta.derivation.Params.{PNil, Params1}
import scurps.meta.derivation.{Derivation, Params}

trait RuleKey[-P<:Params,+R] extends DerivationF0Base[RuleCatalog,Rule[P,R]] {
  override def derive(context:Derivation[RuleCatalog]):Derivation[Rule[P,R]] = context.lookupRule(this)
  val rule:Rule[P,R] = Rule.evalKey(this)
}
object RuleKey {
  trait RuleKey0[+R] extends RuleKey[PNil,R]
  trait RuleKey1[-T1,+R] extends RuleKey[Params1[T1],R]
}
