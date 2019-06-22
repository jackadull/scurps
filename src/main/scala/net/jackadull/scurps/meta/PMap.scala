package net.jackadull.scurps.meta

import scala.language.higherKinds

/** Polymorphic map, whose value types are derived from the reference keys of type [[K]]. */
trait PMap[K[_]] extends PGet[K] {
  def isEmpty:Boolean
  def removed(key:K[_]):PMap[K]
  def updated[V](key:K[V], value:V):PMap[K]
}
