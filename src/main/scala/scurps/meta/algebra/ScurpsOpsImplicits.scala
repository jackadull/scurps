package scurps.meta.algebra

import scurps.meta.context.{ContextKey, GameContext}
import scurps.meta.data.PMap
import scurps.meta.math.{Add, IsZero, Subtract}

trait ScurpsOpsImplicits {
  // TODO rename to pure?
  @inline final implicit def constant[T,A[+_]](v:T)(implicit ops:ScurpsOps[A]):A[T] = ops.constant(v)

  final implicit class RichAlgebraic[A[+_],T](v:A[T]) {
    @inline def :+(rhs:A[T])(implicit add:Add[T], ops:ScurpsOps[A]):A[T] = ops.added(v, rhs)
    @inline def :-(rhs:A[T])(implicit subtract:Subtract[T], ops:ScurpsOps[A]):A[T] = ops.subtracted(v, rhs)
    @inline def ifDefined[T2](_then:A[T]=>A[T2])(implicit ops:ScurpsOps[A]):A[T2] = ops.ifDefined(v, _then)
    @inline def ifZero[T2](_then: =>A[T2], _else: =>A[T2])(implicit isZero:IsZero[T], ops:ScurpsOps[A]):A[T2] =
      ops.ifZero(v, _then, _else)
    @inline def orElse(defaultValue: =>A[T])(implicit ops:ScurpsOps[A]):A[T] = ops.orElse(v, defaultValue)
  }

  final implicit class RichAlgebraicContext[A[+_]](v:A[GameContext]) {
    @inline def get[T](contextKey:ContextKey[T])(implicit ops:ScurpsOps[A]):A[T] = ops.getFromContext(v, contextKey)
    @inline def mod[T](contextKey:ContextKey[T])(f:A[T]=>A[T])(implicit ops:ScurpsOps[A]):A[GameContext] =
      ops.modInContext(v, contextKey, f)
  }

  final implicit class RichAlgebraicPMap[A[+_],K[_]](v:A[PMap[K]]) {
    @inline def get[T](key:A[K[T]])(implicit ops:ScurpsOps[A]):A[T] = ops.getFromPMap(v, key)
    @inline def removed(key:A[K[_]])(implicit ops:ScurpsOps[A]):A[PMap[K]] = ops.removedFromPMap(v, key)
    @inline def updated[T](key:A[K[T]], value:A[T])(implicit ops:ScurpsOps[A]):A[PMap[K]] =
      ops.updatedInPMap(v, key, value)
  }
}
