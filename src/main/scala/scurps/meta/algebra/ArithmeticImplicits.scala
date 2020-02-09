package scurps.meta.algebra

import scurps.meta.algebra.Arithmetic.{Addition, Division, Multiplication, Subtraction}
import scurps.meta.data.GameContext
import scurps.meta.rule.Rule

trait ArithmeticImplicits {
  @inline implicit final def liftAddition[A[_],T](implicit addition:Addition[T], alg:ScurpsOps[A]):Addition[A[T]] =
    new Addition[A[T]] {
      // TODO toString
      override final def apply(lhs:A[T], rhs:A[T]):A[T] = alg.arithmetic(lhs, rhs, addition)
    }
  @inline implicit final def liftDivision[A[_],T1,T2,R](implicit division:Division[T1,T2,R], alg:ScurpsOps[A]):Division[A[T1],A[T2],A[R]] =
    new Division[A[T1],A[T2],A[R]] {
      // TODO toString
      override final def apply(lhs:A[T1], rhs:A[T2]):A[R] = alg.arithmetic(lhs, rhs, division)
    }
  @inline implicit final def liftMultiplication[A[_],T1,T2,R](implicit multiplication:Multiplication[T1,T2,R], alg:ScurpsOps[A]):Multiplication[A[T1],A[T2],A[R]] =
    new Multiplication[A[T1],A[T2],A[R]] {
      // TODO toString
      override final def apply(lhs:A[T1], rhs:A[T2]):A[R] = alg.arithmetic(lhs, rhs, multiplication)
    }
  @inline implicit final def liftSubtraction[A[_],T](implicit subtraction:Subtraction[T], alg:ScurpsOps[A]):Subtraction[A[T]] =
    new Subtraction[A[T]] {
      // TODO toString
      override final def apply(lhs:A[T], rhs:A[T]):A[T] = alg.arithmetic(lhs, rhs, subtraction)
    }

  final implicit class RichArithmetic[T](value:T) {
    @inline def +(that:T)(implicit addition:Addition[T]):T = addition(value, that)
    @inline def -(that:T)(implicit subtraction:Subtraction[T]):T = subtraction(value, that)
    @inline def *[T2,R](that:T2)(implicit multiplication:Multiplication[T,T2,R]):R = multiplication(value, that)
    @inline def /[T2,R](that:T2)(implicit division:Division[T,T2,R]):R = division(value, that)
  }

  final implicit class RichArithmeticRule[P[_[_]],R](self:Rule[P,R]) {
    @inline def +(rhs:Rule[P,R])(implicit addition:Addition[R]):Rule[P,R] = new Rule[P,R] {
      // TODO toString
      override def applyP[A[+_]](params:P[A], context:A[GameContext])(implicit ops:ScurpsOps[A]):A[R] =
        ops.arithmetic(self.applyP(params, context), rhs.applyP(params, context), addition)
    }
    @inline def *[R2,R3](rhs:Rule[P,R2])(implicit multiplication:Multiplication[R,R2,R3]):Rule[P,R3] = new Rule[P,R3] {
      // TODO toString
      override def applyP[A[+_]](params:P[A], context:A[GameContext])(implicit ops:ScurpsOps[A]):A[R3] =
        ops.arithmetic(self.applyP(params, context), rhs.applyP(params, context), multiplication)
    }
    // TODO finish arithmetic operations
  }
}
