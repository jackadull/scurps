package scurps.meta.algebra

import scurps.meta.algebra.ArithmeticOperation.{Addition, Division, Multiplication, Subtraction}

trait ArithmeticSugar {
  implicit final class GeneralArithmeticOperators[I[-_[_[_]],+_],O[-_[_[_]],+_],P[_[_]],T](lhs:I[P,T])(implicit algebra:ArithmeticAlgebra[I,O]) {
    // TODO remove that plus
    @inline def :+(rhs:I[P,T])(implicit operation:Addition[T]):O[P,T] =
      algebra.arithmeticOperation(operation, lhs, rhs)
    @inline def :-(rhs:I[P,T])(implicit operation:Subtraction[T]):O[P,T] =
      algebra.arithmeticOperation(operation, lhs, rhs)
    @inline def :*[T2,R](rhs:I[P,T2])(implicit operation:Multiplication[T,T2,R]):O[P,R] =
      algebra.arithmeticOperation(operation, lhs, rhs)
    @inline def :/[T2,R](rhs:I[P,T2])(implicit operation:Division[T,T2,R]):O[P,R] =
      algebra.arithmeticOperation(operation, lhs, rhs)
  }
}
