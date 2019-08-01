package scurps.meta

trait ContextKey[V]
object ContextKey {
  case object Subject extends ContextKey[PMap[ValueKey]]
}
