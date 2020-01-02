package scurps.meta.algebra

import scurps.meta.context.{ContextKey, GameContext}
import scurps.meta.data.{KeyWrapper, PMap}
import scurps.meta.math.Add

trait ScurpsOpsImplicits {
  final implicit class RichAlgebraic[A[+_],T](v:A[T]) {
    @inline def :+(rhs:A[T])(implicit add:Add[T], ops:ScurpsOps[A]):A[T] = ops.added(v, rhs)
    @inline def orElse(defaultValue:A[T])(implicit ops:ScurpsOps[A]):A[T] = ops.orElse(v, defaultValue)
  }

  final implicit class RichAlgebraicContext[A[+_]](v:A[GameContext]) {
    @inline def get[T](contextKey:ContextKey[T])(implicit ops:ScurpsOps[A]):A[T] = ops.getFromContext(v, contextKey)
  }

  final implicit class RichAlgebraicPMap[A[+_],K[_]](v:A[PMap[K]]) {
    @inline def get[T](key:K[T])(implicit ops:ScurpsOps[A]):A[T] = ops.getFromPMap(v, key)
    @inline def get[T,V](valueToWrap:A[V])(implicit wrapper:KeyWrapper[V,K[T]], ops:ScurpsOps[A]):A[T] =
      ops.getFromPMapWrapped(v, valueToWrap)
  }

  final implicit class RichAny[T](v:T) {
    @inline def constant[A[_]](implicit ops:ScurpsOps[A]):A[T] = ops.constant(v)
  }
}
object ScurpsOpsImplicits extends ScurpsOpsImplicits
