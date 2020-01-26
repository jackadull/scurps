package scurps.meta.rule

import scurps.meta.algebra.ScurpsOps
import scurps.meta.data.GameContext

trait Expr[+T] {
  def apply[A[+_]](algebra:ScurpsOps[A], context:A[GameContext]):A[T]
}
