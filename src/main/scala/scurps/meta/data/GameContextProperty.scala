package scurps.meta.data

import scurps.meta.algebra.Optic.OptionLens
import scurps.meta.data.ShowKey.ShowKey

trait GameContextProperty[T] extends OptionLens[GameContext,T] with ShowKey {
  override final def getOption(source:GameContext):Option[T] = source.get(this)
  override def set(source:GameContext, newValue:T):GameContext = source.updated(this, newValue)
  override def unset(source:GameContext):GameContext = source.removed(this)
}
