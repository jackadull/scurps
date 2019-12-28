package scurps.meta.data

trait PMapLike[K[_],+Repr<:PMapLike[K,Repr]] {
  def removed(key:K[_]):Repr
  def updated[V](key:K[V], value:V):Repr
}