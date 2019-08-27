package scurps.meta.derivation

sealed trait Params extends Any
object Params {
  // TODO are those needed?
  @inline def apply[T1](v1:Derivation[T1]):Params1[T1] = PList(v1, PNil)
  @inline def apply[T1,T2](v1:Derivation[T1], v2:Derivation[T2]):Params2[T1,T2] = PList(v1, Params(v2))
  @inline def apply[T1,T2,T3](v1:Derivation[T1], v2:Derivation[T2], v3:Derivation[T3]):Params3[T1,T2,T3] = PList(v1, Params(v2, v3))

  // TODO are those needed?
  @inline def v1[T](params:PList[T,_]):Derivation[T] = params.head
  @inline def v2[T](params:PList[_,PList[T,_]]):Derivation[T] = params.tail.head
  @inline def v3[T](params:PList[_,PList[_,PList[T,_]]]):Derivation[T] = params.tail.tail.head

  sealed trait PNil extends Params
  case object PNil extends PNil

  type Params1[+T1] = PList[T1,PNil]
  type Params2[+T1,+T2] = PList[T1,Params1[T2]]
  type Params3[+T1,+T2,+T3] = PList[T1,Params2[T2,T3]]

  final case class PList[+H,+T<:Params] private(head:Derivation[H], tail:T) extends Params
}
