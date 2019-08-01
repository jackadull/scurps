package scurps.meta

import scurps.basicset.attributes.basic.PMapLike

trait GameContext extends PMap[ContextKey] with PMapLike[ContextKey,GameContext] {
  def ruleCatalog:RuleCatalog
}
