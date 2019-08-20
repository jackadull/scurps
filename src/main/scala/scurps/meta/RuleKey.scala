package scurps.meta

import scurps.bib.BibRef
import scurps.meta.Derivation.MissingRule
import scurps.meta.Rule.{Eval1, Rule0, Rule1}

import scala.language.higherKinds

sealed trait RuleKey[+Ru<:Rule]
object RuleKey {
  trait RuleKey0[+R] extends RuleKey[Rule0[R]] {
    def derive(implicit context:GameContext):Derivation[R] = context.ruleCatalog.get(this) match {
      case Some(rule) => rule() // TODO wrap in some RuleDerived wrapper
      case None => MissingRule(this, BibRef.Empty)
    }
  }
  trait RuleKey1[-T1,+R] extends RuleKey[Rule1[T1,R]] {
    val eval:Rule1[T1,R] = Eval1(this)
    def derive(v1:T1)(implicit context:GameContext):Derivation[R] = context.ruleCatalog.get(this) match {
      case Some(rule) => rule(v1) // TODO wrap in some RuleDerived wrapper
      case None => MissingRule(this, BibRef.Empty)
    }
  }
}
