package scurps.meta.math

/** An arithmetic operation yielding a certain result. Sub-traits define the arity and parameter types of specific
 * arithmetic operations. Instances of this type are supposed to be passed implicitly, to support abstracing over
 * different arithmetic operations across datatypes. */
sealed trait ArithmeticOp[+R]
object ArithmeticOp {
  sealed trait ArithmeticOp1[-T1,+R] extends (T1=>R)
  sealed trait ArithmeticOp2[-T1,-T2,+R] extends ((T1,T2)=>R)

  trait IsZero[T] extends ArithmeticOp1[T,Boolean]

  trait Addition[T] extends ArithmeticOp2[T,T,T]
  trait Subtraction[T] extends ArithmeticOp2[T,T,T]

  trait Multiplication[-T1,-T2,+R] extends ArithmeticOp2[T1,T2,R]
  trait Division[-T1,-T2,+R] extends ArithmeticOp2[T1,T2,R]
}
