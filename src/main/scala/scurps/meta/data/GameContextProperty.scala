package scurps.meta.data

import scurps.meta.algebra.Optic.OptionGetter
import scurps.meta.data.ShowKey.ShowKey

trait GameContextProperty[+T] extends OptionGetter[GameContext,T] with ShowKey { // TODO make T invariant
  override final def getOption(source:GameContext):Option[T] = source.get(this)
}
