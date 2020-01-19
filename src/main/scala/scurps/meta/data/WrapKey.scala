package scurps.meta.data

import scurps.meta.algebra.ScurpsOps

// TODO optics, see `fp_notes.md`
trait WrapKey[-T,+K] extends (T=>K) {
  def of[A[+_]](v:A[T])(implicit ops:ScurpsOps[A]):A[K] = ops.wrapKey(v, this)
}
