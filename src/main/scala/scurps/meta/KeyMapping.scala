package scurps.meta

import scurps.meta.Rule.{PGetMapped, Rule0, Rule1}

import scala.language.higherKinds

trait KeyMapping[-A,K[_],V] {
  def mapValueKey(a:A):K[V]

  def getFrom(inner:Rule0[PMap[K]]):Rule1[A,V] = PGetMapped(this, inner)
}
