package scurps.meta.data

import scurps.meta.algebra.Optic.OptionLens
import scurps.meta.data.ShowKey.ShowKey

trait PMapProperty[S<:PMap[Self] with PMapLike[Self,S],Self[_],T] extends OptionLens[S,T] with ShowKey {
  this:Self[T] =>
  override final def getOption(source:S):Option[T] = source.get(this)
  override final def set(source:S, newValue:T):S = source.updated(this, newValue)
  override final def unset(source:S):S = source.removed(this)
}
