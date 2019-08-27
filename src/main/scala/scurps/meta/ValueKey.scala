package scurps.meta

import scurps.meta.Rule.{PGet, Rule0}

trait ValueKey[V] {
  def getFrom(inner:Rule0[PMap[ValueKey]]):Rule0[V] = PGet(this, inner)
}
