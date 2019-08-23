package scurps.meta.rule

import scurps.meta.context.RuleContext
import scurps.meta.derivation.{DerivationF, Params}

sealed trait Rule[-P<:Params,+R] extends DerivationF[P,RuleContext,R]
