package scurps.meta.algebra

import scurps.meta.algebra.ArithmeticOperation.{ArithmeticOperation1, ArithmeticOperation2}

trait ArithmeticAlgebra[-I[-_[_[_]],+_],+O[-_[_[_]],+_]] {
  def arithmeticOperation[P[_[_]],T1,R](operation:ArithmeticOperation1[T1,R], v1:I[P,T1]):O[P,R]
  def arithmeticOperation[P[_[_]],T1,T2,R](operation:ArithmeticOperation2[T1,T2,R], v1:I[P,T1], v2:I[P,T2]):O[P,R]
}
object ArithmeticAlgebra {
  implicit case object PlainArithmeticAlgebra extends ArithmeticAlgebra[({type CI[-P[_[_]],+T]=T})#CI, ({type CO[-P[_[_]],+T]=T})#CO] {
    override def arithmeticOperation[P[_[_]],T1,R](operation:ArithmeticOperation1[T1, R], v1:T1):R = operation(v1)
    override def arithmeticOperation[P[_[_]],T1,T2,R](operation:ArithmeticOperation2[T1,T2,R], v1:T1, v2:T2):R =
      operation(v1, v2)
  }
}
