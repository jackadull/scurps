package scurps

import scurps.meta.algebra.ArithmeticOperation.{Addition, ArithmeticOperation2}
import scurps.meta.algebra.ScurpsOps
import scurps.meta.rule.Rule

// TODO remove this once POC is implemented
object ParticularAlgebraPOC {
  trait Arithmetic2Algebra[A[-_[_[_]],+_]] {
    def arithmetic2[P[_[_]],T1,T2,R](lhs:A[P,T1], rhs:A[P,T2], operation:ArithmeticOperation2[T1,T2,R]):A[P,R]
  }

  implicit object RuleArithmetic2Algebra extends Arithmetic2Algebra[Rule] {
    override def arithmetic2[P[_[_]],T1,T2,R](lhs:Rule[P,T1], rhs:Rule[P,T2], operation:ArithmeticOperation2[T1,T2,R]):Rule[P,R] = ???
  }

  implicit def algebraicArithmetic2Algebra[A[+_]](implicit ops:ScurpsOps[A]):Arithmetic2Algebra[({type CA[-P[_[_]],+T] = A[T]})#CA] =
    new Arithmetic2Algebra[({type CA[-P[_[_]],+T] = A[T]})#CA] {
      override def arithmetic2[P[_[_]],T1,T2,R](lhs:A[T1], rhs:A[T2], operation:ArithmeticOperation2[T1,T2,R]):A[R] =
        ops.arithmetic(lhs, rhs, operation)
    }

  implicit final class ArithmeticSugar[A[-_[_[_]],+_],P[_[_]],T](lhs:A[P,T]) {
    def :+(rhs:A[P,T])(implicit addition:Addition[T], algebra:Arithmetic2Algebra[A]):A[P,T] =
      algebra.arithmetic2(lhs, rhs, addition)
  }
}
