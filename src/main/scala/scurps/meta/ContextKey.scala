package scurps.meta

import scurps.meta.Rule.{ContextGet, Rule0}

trait ContextKey[V] {
  val fromContext:Rule0[V] = ContextGet(this) // TODO change to `eval`
}
object ContextKey {
  case object Subject extends ContextKey[PMap[ValueKey]]
}
