package scurps.meta.algebra

// TODO remove this when POC is implemented
object ArithmeticRelationPOC extends App {
  trait MyAddition[A] extends ((A,A)=>A)
  implicit val intAddition:MyAddition[Int] = (l,r)=>l+r

  trait MyOps[A[+_]] {
    def add[T](addition:MyAddition[T], v1:A[T], v2:A[T]):A[T]
  }

  trait MyRule[-P[_[_]],+R] {
    def applyP[A[+_]](params:P[A])(implicit ops:MyOps[A]):A[R]
  }

  final implicit class ArithmeticOperators[T](lhs:T) {
    @inline def /+[T2](rhs:T)(implicit operation:MyAddition[T2], relation:ArithmeticRelation[T,T2]):T =
      relation.add(operation, lhs, rhs)
  }

  trait ArithmeticRelation[A,B] {
    def add(op:MyAddition[B], v1:A, v2:A):A
  }

  implicit def idArithmeticRelation[A]:ArithmeticRelation[A,A] = new ArithmeticRelation[A,A] {
    override def add(op:MyAddition[A], v1:A, v2:A):A = op(v1, v2)
  }
  implicit def algebraicArithmeticRelation[A[+_],T](implicit ops:MyOps[A]):ArithmeticRelation[A[T],T] = new ArithmeticRelation[A[T],T] {
    override def add(op:MyAddition[T], v1:A[T], v2:A[T]):A[T] = ops.add(op, v1, v2)
  }
  implicit def ruleArithmeticRelation[P[_[_]],R]:ArithmeticRelation[MyRule[P,R],R] = new ArithmeticRelation[MyRule[P,R],R] {
    override def add(op:MyAddition[R], v1:MyRule[P,R], v2:MyRule[P,R]):MyRule[P,R] = new MyRule[P,R] {
      override def applyP[A[+_]](params:P[A])(implicit ops:MyOps[A]):A[R] = ops.add(op, v1.applyP(params), v2.applyP(params))
    }
  }

  //-----

  def addWithSugar[A[+_]](v1:A[Int], v2:A[Int])(implicit ops:MyOps[A]):A[Int] = v1 /+ v2

  //-----

  implicit object OptionMyOps extends MyOps[Option] {
    override def add[T](addition:MyAddition[T], v1:Option[T], v2:Option[T]):Option[T] =
      for(a1<-v1; a2<-v2) yield addition(a1, a2)
  }

  val addRule:MyRule[({type P[A[+_]]=(A[Int],A[Int])})#P,Int] = new MyRule[({type P[A[+_]]=(A[Int],A[Int])})#P,Int] {
    override def applyP[A[+_]](params:(A[Int], A[Int]))(implicit ops:MyOps[A]):A[Int] = params._1 /+ params._2
  }

  val doubleAddRule:MyRule[({type P[A[+_]]=(A[Int],A[Int])})#P,Int] = addRule /+ addRule

  //-----

  val plainResult = 10 /+ 20
  val algebraicResult = addWithSugar(Option(20), Option(30))
  val ruleResult = doubleAddRule.applyP((Option(10), Option(10)))

  assert(plainResult == 30)
  assert(algebraicResult == Some(50))
  assert(ruleResult == Some(40))
}
