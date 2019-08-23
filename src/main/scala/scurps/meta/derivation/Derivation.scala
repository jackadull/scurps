package scurps.meta.derivation

import scurps.meta.rule.{Rule, RuleCatalog, RuleKey}

trait Derivation[+A] {
  def lookupRule[P<:Params,R](key:RuleKey[P,R])(implicit ev:this.type=>Derivation[RuleCatalog]):Derivation[Rule[P,R]] =
    ev(this).valueE match {
      case Right(rule) => ??? // TODO denote lookup of rule definition
      case Left(undefined) => ??? // TODO MissingRule(key, BibRef.Empty) -- should `undefined` be preserved? (if not, `use` valueOpt instead)
    }

  def valueE:Either[Derivation[Nothing],A]
}
