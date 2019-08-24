package scurps.meta.context

import scurps.meta.rule.RuleCatalog

trait GameContext {
  def ruleCatalog:RuleCatalog
}
