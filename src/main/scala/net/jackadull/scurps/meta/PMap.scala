package net.jackadull.scurps.meta

import scala.language.higherKinds

/** Polymorphic map, whose value types are derived from the reference keys of type [[K]]. */
trait PMap[K[_]] {
  def contains(key:K[_]):Boolean
  def get[V](key:K[V]):Option[V]
  def isEmpty:Boolean
  def removed(key:K[_]):PMap[K]
  def updated[V](key:K[V], value:V):PMap[K]
}
