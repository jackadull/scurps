package scurps.meta.data

import scurps.meta.algebra.Optic.OptionLens
import scurps.meta.data.ShowKey.ShowKey

trait ValueKey[T] extends OptionLens[PMap[ValueKey],T] with ShowKey { // TODO check out toString
  override final def getOption(source:PMap[ValueKey]):Option[T] = source.get(this)
  override final def set(source:PMap[ValueKey], newValue:T):PMap[ValueKey] = source.updated(this, newValue)
  override final def unset(source:PMap[ValueKey]):PMap[ValueKey] = source.removed(this)
}
