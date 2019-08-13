package scurps.meta

import scala.language.higherKinds

/** Polymorphic map, whose value types are derived from the reference keys of type [[K]]. */
trait PMap[K[_]] extends PMapLike[K,PMap[K]] {
  def contains(key:K[_]):Boolean
  def get[V](key:K[V]):Option[V]
  def isEmpty:Boolean
}
