package net.jackadull.scurps.meta

import scala.language.higherKinds

/** Polymorphic lookup of a value type that depends on the key of type [[K]]. */
trait PGet[K[_]] {
  def contains(key:K[_]):Boolean
  def get[V](key:K[V]):Option[V]
}
