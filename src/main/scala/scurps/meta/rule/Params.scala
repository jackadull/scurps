package scurps.meta.rule

sealed trait Params extends Any
object Params {
  def apply[T1](p1:T1):ParamsA1[T1] = PCons(p1, PNil)

  type ParamsA0 = PNil
  type ParamsA1[+T1] = PCons[T1,PNil]
  type ParamsA2[+T1,+T2] = PCons[T1,ParamsA1[T2]]
  type ParamsA3[+T1,+T2,+T3] = PCons[T1,ParamsA2[T2,T3]]

  sealed trait PNil extends Params
  case object PNil extends PNil

  final case class PCons[+H,+T<:Params] private(head:H, tail:T) extends Params
}
