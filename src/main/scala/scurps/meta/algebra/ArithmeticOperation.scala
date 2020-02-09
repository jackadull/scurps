package scurps.meta.algebra

/** Sub-traits define the arity and parameter types of specific arithmetic operations. Instances of this type are
 * supposed to be passed implicitly, to support abstracing over different arithmetic operations across datatypes. */
object ArithmeticOperation {
  sealed trait ArithmeticOperation1[-T1,+R] extends (T1=>R)
  sealed trait ArithmeticOperation2[-T1,-T2,+R] extends ((T1,T2)=>R)

  trait IsZero[T] extends ArithmeticOperation1[T,Boolean]

  trait Addition[T] extends ArithmeticOperation2[T,T,T]
  trait Subtraction[T] extends ArithmeticOperation2[T,T,T]

  trait Multiplication[-T1,-T2,+R] extends ArithmeticOperation2[T1,T2,R]
  trait Division[-T1,-T2,+R] extends ArithmeticOperation2[T1,T2,R]
}
