package scurps.meta.algebra

import scurps.bib.BibRef
import scurps.meta.data.GameContextProperty
import scurps.meta.data.{GameContext, GameContextProperty, PMap}
import scurps.meta.algebra.Arithmetic.{Addition, IsZero, Multiplication, Subtraction}
import scurps.meta.algebra.Optic.{Element, OptionGetter, OptionLens, OptionSetter, Setter}

import scala.language.implicitConversions

trait ScurpsOpsImplicits {
  @inline final implicit def pure[T,A[+_]](v:T)(implicit ops:ScurpsOps[A]):A[T] = ops.pure(v)

  final implicit class RichAlgebraic[A[+_],T](v:A[T]) {
    @inline def :+(rhs:A[T])(implicit add:Addition[T], ops:ScurpsOps[A]):A[T] = ops.arithmetic(v, rhs, add)
    @inline def :-(rhs:A[T])(implicit subtract:Subtraction[T], ops:ScurpsOps[A]):A[T] = ops.arithmetic(v, rhs, subtract)
    @inline def :*[T2,R](rhs:A[T2])(implicit multiply:Multiplication[T,T2,R], ops:ScurpsOps[A]):A[R] =
      ops.arithmetic(v, rhs, multiply)
    @inline def accordingTo(ref:BibRef)(implicit ops:ScurpsOps[A]):A[T] = ops.accordingTo(v, ref)
    @inline def get[R](optic:A[OptionGetter[T,R]])(implicit ops:ScurpsOps[A]):A[R] = ops.opticOptionGet(v, optic)
    @inline def ifDefined[T2](_then:A[T]=>A[T2])(implicit ops:ScurpsOps[A]):A[T2] = ops.ifDefined(v, _then(v))
    @inline def ifElementOf[S,T2](source:A[S], _then:A[T]=>A[T2], _else:A[T]=>A[T2])(implicit optic:Element[S,T], ops:ScurpsOps[A]):A[T2] =
      ops.ifIsElement(source, v, _then(v), _else(v), optic)
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
}
