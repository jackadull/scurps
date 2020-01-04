package scurps.meta.algebra

import scurps.meta.context.{ContextKey, GameContext}
import scurps.meta.data.{PMap, PMapLike, WrapKey}
import scurps.meta.math.Add

trait ScurpsOpsImplicits {
  final implicit class RichAlgebraic[A[+_],T](v:A[T]) {
    @inline def :+(rhs:A[T])(implicit add:Add[T], ops:ScurpsOps[A]):A[T] = ops.added(v, rhs)
    @inline def orElse(defaultValue:A[T])(implicit ops:ScurpsOps[A]):A[T] = ops.orElse(v, defaultValue)
  }

  final implicit class RichAlgebraicContext[A[+_]](v:A[GameContext]) {
    @inline def get[T](contextKey:ContextKey[T])(implicit ops:ScurpsOps[A]):A[T] = ops.getFromContext(v, contextKey)
    @inline def mod[T,T2](contextKey:ContextKey[T])(f:A[T]=>A[T2])(implicit ops:ScurpsOps[A]):A[T2] = ??? // TODO implement
  }

  final implicit class RichAlgebraicPMap[A[+_],K[_]](v:A[PMap[K]]) {
    @inline def get[T](key:K[T])(implicit ops:ScurpsOps[A]):A[T] = ops.getFromPMap(v, key)
    @inline def get[W,T](keyToWrap:A[W])(implicit wrapKey:WrapKey[W,K[T]], ops:ScurpsOps[A]):A[T] =
      ops.getFromPMapWrapped(v, keyToWrap)
    @inline def updated[T](key:A[K[T]], value:A[T])(implicit ops:ScurpsOps[A]):A[PMap[K]] = ??? // TODO implement
    @inline def updated[W,T](keyToWrap:A[W], value:A[T])(implicit wrapKey:WrapKey[W,K[T]], ops:ScurpsOps[A]):A[PMap[K]] = ??? // TODO
  }

  final implicit class RichAny[T](v:T) {
    @inline def constant[A[_]](implicit ops:ScurpsOps[A]):A[T] = ops.constant(v)
  }
}
