package scurps.meta.data

import scurps.meta.algebra.Optic.OptionGetter
import scurps.meta.data.ShowKey.{ShowKey, ShowSingletonKey}

trait GameContextProperty[+T] extends OptionGetter[GameContext,T] with ShowKey {
  override final def getOptional(source:GameContext):Option[T] = source.get(this)
}
