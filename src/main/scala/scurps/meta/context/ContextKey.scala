package scurps.meta.context

import scurps.meta.algebra.Optic.OptionGetter
import scurps.meta.data.PMap
import scurps.meta.data.ShowKey.{ShowKey, ShowSingletonKey}

trait ContextKey[+T] extends OptionGetter[GameContext,T] with ShowKey { // TODO check out toString
  override final def getOptional(source:GameContext):Option[T] = source.get(this)
}
object ContextKey {
  case object Subject extends ContextKey[PMap[ValueKey]] with ShowSingletonKey
}
