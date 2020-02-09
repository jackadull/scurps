package scurps.meta.algebra

import scurps.meta.algebra.ArithmeticOperation.{Addition, Division, Multiplication, Subtraction}

trait ArithmeticSugar {
  // TODO can we put O and the algebra in this constructor, instead of on every method?
  implicit final class ArithmeticOperators[I[-_[_[_]],+_],P[_[_]],T](lhs:I[P,T]) {
    // TODO remove that plus
    @inline def :+[O[-_[_[_]],+_]](rhs:I[P,T])(implicit operation:Addition[T], algebra:ArithmeticAlgebra[I,O]):O[P,T] =
      algebra.arithmeticOperation(operation, lhs, rhs)
    @inline def :-[O[-_[_[_]],+_]](rhs:I[P,T])(implicit operation:Subtraction[T], algebra:ArithmeticAlgebra[I,O]):O[P,T] =
      algebra.arithmeticOperation(operation, lhs, rhs)
    @inline def :*[O[-_[_[_]],+_],T2,R](rhs:I[P,T2])(implicit operation:Multiplication[T,T2,R], algebra:ArithmeticAlgebra[I,O]):O[P,R] =
      algebra.arithmeticOperation(operation, lhs, rhs)
    @inline def :/[O[-_[_[_]],+_],T2,R](rhs:I[P,T2])(implicit operation:Division[T,T2,R], algebra:ArithmeticAlgebra[I,O]):O[P,R] =
      algebra.arithmeticOperation(operation, lhs, rhs)
  }
}
