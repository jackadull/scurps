package scurps.basicset.attributes.basic

import scala.language.higherKinds

trait PMapLike[K[_],+Repr<:PMapLike[K,Repr]] {
  def removed(key:K[_]):Repr
  def updated[V](key:K[V], value:V):Repr
}
