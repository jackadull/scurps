package scurps.meta.context

import scurps.meta.data.{PMap, PMapLike}
import scurps.meta.rule.RuleCatalog

trait GameContext extends PMap[ContextKey] with PMapLike[ContextKey,GameContext] {
  def ruleCatalog:RuleCatalog
}
