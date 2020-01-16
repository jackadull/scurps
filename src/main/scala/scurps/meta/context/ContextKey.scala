package scurps.meta.context

import scurps.meta.data.PMap
import scurps.meta.data.ShowKey.{ShowKey, ShowSingletonKey}

trait ContextKey[+T] extends ShowKey
object ContextKey {
  case object Subject extends ContextKey[PMap[ValueKey]] with ShowSingletonKey
}
