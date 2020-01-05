package scurps.meta.data

import scurps.meta.algebra.ScurpsOps

trait WrapKey[-T,+K] extends (T=>K) {
  def of[A[+_]](v:A[T])(implicit ops:ScurpsOps[A]):A[K] = ops.wrapKey(v, this)
}
