package scurps.meta.rule

import scurps.meta.algebra.ScurpsOps

sealed trait Params extends Any
object Params {
  @inline def p[T1](p1:T1):Params1[T1] = PCons(p1, PNil)
  @inline def p[A[+_],T1](p1:T1)(implicit ops:ScurpsOps[A]):Params1[A[T1]] = p(ops.constant(p1))

  type Params0 = PNil
  type Params1[+T1] = PCons[T1,PNil]
  type Params2[+T1,+T2] = PCons[T1,Params1[T2]]
  type Params3[+T1,+T2,+T3] = PCons[T1,Params2[T2,T3]]

  sealed trait PNil extends Params
  case object PNil extends PNil

  final case class PCons[+H,+T<:Params] private(head:H, tail:T) extends Params
}
