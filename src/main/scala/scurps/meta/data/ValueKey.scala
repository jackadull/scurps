package scurps.meta.data

import scurps.meta.algebra.Optic.OptionGetter
import scurps.meta.data.ShowKey.ShowKey

trait ValueKey[T] extends OptionGetter[PMap[ValueKey],T] with ShowKey { // TODO check out toString
  override final def getOptional(source:PMap[ValueKey]):Option[T] = source.get(this)
}
