package scurps.meta.algebra

import scurps.bib.BibRef
import scurps.meta.data.GameContextProperty
import scurps.meta.data.{GameContext, GameContextProperty, PMap}
import scurps.meta.algebra.Arithmetic.{Addition, IsZero, Multiplication, Subtraction}
import scurps.meta.algebra.Optic.{OptionGetter, OptionLens, OptionSetter, Setter}
import scurps.meta.rule.Rule.Rule0
import scurps.meta.algebra.Semantic.{Accumulate, Cons, IsElement, Uncons}

import scala.collection.IterableOnceOps
import scala.language.implicitConversions

trait ScurpsOpsImplicits {
  @inline final implicit def pure[T,A[+_]](v:T)(implicit ops:ScurpsOps[A]):A[T] = ops.pure(v)

  final implicit class RichAlgebraic[A[+_],T](v:A[T]) {
    @inline def accordingTo(ref:BibRef)(implicit ops:ScurpsOps[A]):A[T] = ops.accordingTo(v, ref)
    @inline def ifDefined[T2](_then:A[T]=>A[T2])(implicit ops:ScurpsOps[A]):A[T2] = ops.ifDefined(v, _then(v))
    @inline def ifElementOf[C[_],T2](collection:A[C[T]], _then:A[T]=>A[T2], _else:A[T]=>A[T2])(implicit isElement:IsElement[C], ops:ScurpsOps[A]):A[T2] =
      ops.ifIsElement(collection, v, _then(v), _else(v), isElement)
    @inline def ifZero[T2](_then: =>A[T2], _else: =>A[T2])(implicit isZero:IsZero[T], ops:ScurpsOps[A]):A[T2] =
      ops.ifZero(v, _then, _else)
    @inline def mod[T2](lens:A[OptionLens[T,T2]])(f:A[T2]=>A[T2])(implicit ops:ScurpsOps[A]):A[T] =
      ops.opticMod(v, lens, f)
    @inline def orElse(defaultValue: =>A[T])(implicit ops:ScurpsOps[A]):A[T] = ops.orElse(v, defaultValue)
    @inline def set[T2](setter:A[Setter[T,T2]], newValue:A[T2])(implicit ops:ScurpsOps[A]):A[T] =
      ops.opticSet(v, setter, newValue)
    @inline def setNonZero[T2](setter:A[OptionSetter[T,T2]], newValue:A[T2])(implicit isZero:IsZero[T2], ops:ScurpsOps[A]):A[T] =
      ops.ifZero(newValue, _then = ops.opticUnset(v, setter), _else = ops.opticSet(v, setter, newValue))
  }

  final implicit class RichAlgebraicTypeclass[A[+_],C[_],T](v:A[C[T]]) {
    @inline def fold[F](f:A[F])(implicit accumulate:Accumulate[F,T], uncons:Uncons[C], ops:ScurpsOps[A]):A[F] =
      ops.accumulate(v, f)
    @inline def map[T2](f:A[T]=>A[T2])(implicit cons:Cons[C], uncons:Uncons[C], ops:ScurpsOps[A]):A[C[T2]] =
      ops.map(v, f, cons, uncons)
  }

  final implicit class RichAlgebraicRule0[A[+_],R](v:A[Rule0[R]]) {
    @inline def apply(context:A[GameContext])(implicit ops:ScurpsOps[A]):A[R] =
      ops.applyRule[({type P[A2[+_]]=Unit})#P,R](v, (), context)
  }
}
