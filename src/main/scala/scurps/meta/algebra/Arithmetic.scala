package scurps.meta.algebra

import scurps.meta.data.GameContext
import scurps.meta.rule.Rule

/** An arithmetic operation yielding a certain result. Sub-traits define the arity and parameter types of specific
 * arithmetic operations. Instances of this type are supposed to be passed implicitly, to support abstracing over
 * different arithmetic operations across datatypes. */
object Arithmetic {
  sealed trait ArithmeticOp1[-T1,+R] extends (T1=>R)
  sealed trait ArithmeticOp2[-T1,-T2,+R] extends ((T1,T2)=>R)

  trait IsZero[T] extends ArithmeticOp1[T,Boolean]

  trait Addition[T] extends ArithmeticOp2[T,T,T]
  trait Subtraction[T] extends ArithmeticOp2[T,T,T]

  trait Multiplication[-T1,-T2,+R] extends ArithmeticOp2[T1,T2,R]
  trait Division[-T1,-T2,+R] extends ArithmeticOp2[T1,T2,R]

  trait ArithmeticCombinator1[-AT1,+AR,+T1,-R] {
    def combine(operation:ArithmeticOp1[T1,R], v:AT1):AR
  }
  object ArithmeticCombinator1 {
    implicit def plain[T1,R]:ArithmeticCombinator1[T1,R,T1,R] = new ArithmeticCombinator1[T1,R,T1,R] {
      // TODO toString
      // TODO singleton instance
      override def combine(operation:ArithmeticOp1[T1,R], v:T1):R = operation(v)
    }
  }

  trait ArithmeticCombinator2[-AT1,-AT2,+AR,+T1,+T2,-R] {
    def combine(operation:ArithmeticOp2[T1,T2,R], v1:AT1, v2:AT2):AR
  }
  object ArithmeticCombinator2 {
    implicit def algebraic[A[+_],T1,T2,R](implicit alg:ScurpsOps[A]):ArithmeticCombinator2[A[T1],A[T2],A[R],T1,T2,R] =
      new ArithmeticCombinator2[A[T1],A[T2],A[R],T1,T2,R] {
        // TODO toString
        // TODO singleton instance
        override def combine(operation:ArithmeticOp2[T1, T2, R], v1:A[T1], v2:A[T2]):A[R] = alg.arithmetic(v1, v2, operation)
      }

    implicit def plain[T1,T2,R]:ArithmeticCombinator2[T1,T2,R,T1,T2,R] = new ArithmeticCombinator2[T1,T2,R,T1,T2,R] {
      // TODO toString
      // TODO singleton instance
      override def combine(operation:ArithmeticOp2[T1,T2,R], v1:T1, v2:T2):R = operation(v1, v2)
    }

    implicit def rule[P[_[_]],T1,T2,R]:ArithmeticCombinator2[Rule[P,T1],Rule[P,T2],Rule[P,R],T1,T2,R] =
      new ArithmeticCombinator2[Rule[P,T1],Rule[P,T2],Rule[P,R],T1,T2,R] {
        // TODO toString
        // TODO singleton instance
        override def combine(operation:ArithmeticOp2[T1,T2,R], v1:Rule[P,T1], v2:Rule[P,T2]):Rule[P,R] =
          new Rule[P,R] {
            // TODO toString
            override def applyP[A[+_]](params:P[A], context:A[GameContext])(implicit ops:ScurpsOps[A]):A[R] =
              ops.arithmetic(v1.applyP(params, context), v2.applyP(params, context), operation)
          }
      }
  }
}
