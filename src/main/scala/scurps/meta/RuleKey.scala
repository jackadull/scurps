package scurps.meta

import scurps.meta.Derivation.MissingRule
import scurps.meta.Rule.{Rule0, Rule1}

import scala.language.higherKinds

sealed trait RuleKey[+Ru<:Rule]
object RuleKey {
  trait RuleKey0[+R] extends RuleKey[Rule0[R]] {
    def derive(implicit context:GameContext):Derivation[R] = context.ruleCatalog.get(this) match {
      case Some(rule) => rule()
      case None => MissingRule(this)
    }
  }
  trait RuleKey1[-T1,+R] extends RuleKey[Rule1[T1,R]] {
    def derive(v1:T1)(implicit context:GameContext):Derivation[R] = context.ruleCatalog.get(this) match {
      case Some(rule) => rule(v1)
      case None => MissingRule(this)
    }
  }
}
