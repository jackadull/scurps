package scurps.meta.rule

sealed trait Params extends Any
object Params {
  @inline def p[T1](p1:T1):Params1[T1] = PCons(p1, PNil)
  @inline def p[T1,T2](p1:T1, p2:T2):Params2[T1,T2] = PCons(p1, PCons(p2, PNil))

  type Params0 = PNil
  type Params1[+T1] = PCons[T1,PNil]
  type Params2[+T1,+T2] = PCons[T1,Params1[T2]]
  type Params3[+T1,+T2,+T3] = PCons[T1,Params2[T2,T3]]

  sealed trait PNil extends Params
  case object PNil extends PNil

  final case class PCons[+H,+T<:Params] private(head:H, tail:T) extends Params
}
