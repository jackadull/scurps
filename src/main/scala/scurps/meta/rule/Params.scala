package scurps.meta.rule

// TODO consider adding the context to the params
// TODO consider unifying context and params
sealed trait Params extends Any
object Params {
  // TODO this syntactic sugar should be imported with scurps._
  @inline def p[T1](p1:T1):Params1[T1] = PCons(p1, PNil)
  @inline def p[T1,T2](p1:T1, p2:T2):Params2[T1,T2] = PCons(p1, PCons(p2, PNil))

  type Params0 = PNil
  type Params1[+T1] = PCons[T1,PNil]
  type Params2[+T1,+T2] = PCons[T1,Params1[T2]]
  type Params3[+T1,+T2,+T3] = PCons[T1,Params2[T2,T3]]

  object Params1 {
    @inline def unapply[T1](p:Params1[T1]):Some[T1] = Some(p.head)
  }
  object Params2 {
    @inline def unapply[T1,T2](p:Params2[T1,T2]):Some[(T1,T2)] = Some((p.head, p.tail.head))
  }
  object Params3 {
    @inline def unapply[T1,T2,T3](p:Params3[T1,T2,T3]):Some[(T1,T2,T3)] = Some((p.head, p.tail.head, p.tail.tail.head))
  }

  sealed trait PNil extends Params
  case object PNil extends PNil

  final case class PCons[+H,+T<:Params] private(head:H, tail:T) extends Params
}
