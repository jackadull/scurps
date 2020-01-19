package scurps.meta.algebra

import scurps.bib.BibRef
import scurps.meta.context.{ContextKey, GameContext}
import scurps.meta.data.PMap
import scurps.meta.algebra.Arithmetic.{Addition, IsZero, Multiplication, Subtraction}
import scurps.meta.algebra.Optic.OptionGetter

import scala.language.implicitConversions

trait ScurpsOpsImplicits {
  @inline final implicit def pure[T,A[+_]](v:T)(implicit ops:ScurpsOps[A]):A[T] = ops.pure(v)

  final implicit class RichAlgebraic[A[+_],T](v:A[T]) {
    @inline def :+(rhs:A[T])(implicit add:Addition[T], ops:ScurpsOps[A]):A[T] = ops.arithmetic(v, rhs, add)
    @inline def :-(rhs:A[T])(implicit subtract:Subtraction[T], ops:ScurpsOps[A]):A[T] = ops.arithmetic(v, rhs, subtract)
    @inline def :*[T2,R](rhs:A[T2])(implicit multiply:Multiplication[T,T2,R], ops:ScurpsOps[A]):A[R] =
      ops.arithmetic(v, rhs, multiply)
    @inline def accordingTo(ref:BibRef)(implicit ops:ScurpsOps[A]):A[T] = ops.accordingTo(v, ref)
    @inline def get[R](optic:A[OptionGetter[T,R]])(implicit ops:ScurpsOps[A]):A[R] = ops.opticGet(v, optic)
    @inline def ifDefined[T2](_then:A[T]=>A[T2])(implicit ops:ScurpsOps[A]):A[T2] = ops.ifDefined(v, _then)
    @inline def ifIsOneOf[T2](set:A[Set[T]], _then:A[T]=>A[T2], _else:A[T]=>A[T2])(implicit ops:ScurpsOps[A]):A[T2] =
      ops.ifIsOneOf(v, set, _then, _else)
    @inline def ifZero[T2](_then: =>A[T2], _else: =>A[T2])(implicit isZero:IsZero[T], ops:ScurpsOps[A]):A[T2] =
      ops.ifZero(v, _then, _else)
    @inline def orElse(defaultValue: =>A[T])(implicit ops:ScurpsOps[A]):A[T] = ops.orElse(v, defaultValue)
  }

  final implicit class RichAlgebraicContext[A[+_]](v:A[GameContext]) {
    @inline def mod[T](contextKey:ContextKey[T])(f:A[T]=>A[T])(implicit ops:ScurpsOps[A]):A[GameContext] =
      ops.modInContext(v, contextKey, f)
  }

  final implicit class RichAlgebraicPMap[A[+_],K[_]](v:A[PMap[K]]) {
    @inline def removed(key:A[K[_]])(implicit ops:ScurpsOps[A]):A[PMap[K]] = ops.removedFromPMap(v, key)
    @inline def updated[T](key:A[K[T]], value:A[T])(implicit ops:ScurpsOps[A]):A[PMap[K]] =
      ops.updatedInPMap(v, key, value)
    @inline def updatedNonZero[T](key:A[K[T]], value:A[T])(implicit isZero:IsZero[T], ops:ScurpsOps[A]):A[PMap[K]] =
      ops.ifZero(value, _then = ops.removedFromPMap(v, key), _else = ops.updatedInPMap(v, key, value))
  }
}
