package scurps.meta.context

import scurps.meta.derivation.{Derivation, Params}
import scurps.meta.rule.{RuleCatalog, RuleKey}

import scala.language.implicitConversions

trait RuleContext {
  def gameContext:GameContext
  def ruleContextForCalling[P<:Params,R](key:RuleKey[P,R]):RuleContext

  def ruleCatalog:RuleCatalog = gameContext.ruleCatalog
}
object RuleContext {
  implicit def accessCatalog(d:Derivation[RuleContext]):Derivation[RuleCatalog] = d.accessRuleCatalogInRuleContext
}
