package scurps.meta.algebra

import scurps.meta.algebra.Optic.{Getter, OptionGetter}

import scala.language.implicitConversions

trait OpticImplicits {
  @inline implicit final def liftAlgebraicOptionGetter[A[+_],S,T](getter:A[OptionGetter[S,T]])(implicit alg:ScurpsOps[A]):Getter[A[S],A[T]] =
    new Getter[A[S],A[T]] {
      // TODO toString
      override final def get(source:A[S]):A[T] = alg.opticOptionGet(source, getter)
    }
  @inline implicit final def liftOptionGetter[A[+_],S,T](optionGetter:OptionGetter[S,T])(implicit alg:ScurpsOps[A]):Getter[A[S],A[T]] =
    new Getter[A[S],A[T]] {
      // TODO toString
      override final def get(source:A[S]):A[T] = alg.opticOptionGet(source, alg.pure(optionGetter))
    }

  final implicit class RichOptic[S](value:S) {
    @inline def \[T](getter:Getter[S,T]):T = get(getter)

    @inline def get[T](getter:Getter[S,T]):T = getter.get(value)
  }
}
