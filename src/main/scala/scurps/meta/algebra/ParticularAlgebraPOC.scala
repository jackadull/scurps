package scurps.meta.algebra

import scurps.meta.algebra.ArithmeticOperation.ArithmeticOperation1
import scurps.meta.rule.Rule

// TODO remove this once POC is implemented
object ParticularAlgebraPOC {
  //val result = 10.cp :+ 20.cp

  trait ArithmeticAlgebra2[A[+_]] {
    def arithmeticOperation[T,R](operation:ArithmeticOperation1[T,R], v:A[T]):A[R]
  }

  trait RuleRelationship[+R] {
    type P[_[_]]
    type Rel = Rule[P,R]
  }
}
