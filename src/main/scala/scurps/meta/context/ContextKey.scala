package scurps.meta.context

import scurps.meta.data.PMap

trait ContextKey[+T]
object ContextKey {
  case object Subject extends ContextKey[PMap[ValueKey]]
}
