package scurps.meta.rule

import scurps.meta.context.RuleContext
import scurps.meta.derivation.Params.{PNil, Params1}
import scurps.meta.derivation.{Derivation, Params}
import scurps.meta.rule.Rule.RuleKeyBase

trait RuleKey[-P<:Params,+R] extends RuleKeyBase[P,R] {
  override final def apply(pNil:PNil, context:Derivation[RuleContext]):Derivation[Rule[P,R]] =
    context.lookupRule(this)
}
object RuleKey {
  type RuleKey0[+R] = RuleKey[PNil,R]
  type RuleKey1[-T1,+R] = RuleKey[Params1[T1],R]
}
