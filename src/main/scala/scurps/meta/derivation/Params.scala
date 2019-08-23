package scurps.meta.derivation

sealed trait Params extends Any with Product
object Params {
  @inline def apply[T1](v1:Derivation[T1]):Params1[T1] = Params1Impl(v1)
  @inline def apply[T1,T2](v1:Derivation[T1], v2:Derivation[T2]):Params2[T1,T2] = Params2Impl(v1, v2)
  @inline def apply[T1,T2,T3](v1:Derivation[T1], v2:Derivation[T2], v3:Derivation[T3]):Params3[T1,T2,T3] = Params3Impl(v1, v2, v3)

  sealed trait PNil extends Params
  case object PNil extends PNil

  type Params1[+T1] = PList[T1,PNil] with Product1[Derivation[T1]]
  type Params2[+T1,+T2] = PList[T1,PList[T2,PNil]] with Product2[Derivation[T1],Derivation[T2]]
  type Params3[+T1,+T2,+T3] = PList[T1,PList[T2,PList[T3,PNil]]] with Product3[Derivation[T1],Derivation[T2],Derivation[T3]]

  sealed trait PList[+H,+T<:Params] extends Params {
    def head:Derivation[H]
    def tail:T
  }

  final case class Params1Impl[+T1] private(_1:Derivation[T1]) extends PList[T1,PNil] with Product1[Derivation[T1]] {
    @inline override def head:Derivation[T1] = _1
    @inline override def tail:PNil = PNil
  }
  final case class Params2Impl[+T1,+T2] private(_1:Derivation[T1], _2:Derivation[T2]) extends PList[T1,PList[T2,PNil]] with Product2[Derivation[T1],Derivation[T2]] {
    @inline override def head:Derivation[T1] = _1
    @inline override def tail:Params1Impl[T2] = Params1Impl(_2)
  }
  final case class Params3Impl[+T1,+T2,+T3] private(_1:Derivation[T1], _2:Derivation[T2], _3:Derivation[T3]) extends PList[T1,PList[T2,PList[T3,PNil]]] with Product3[Derivation[T1],Derivation[T2],Derivation[T3]] {
    @inline override def head:Derivation[T1] = _1
    @inline override def tail:Params2Impl[T2,T3] = Params2Impl(_2, _3)
  }
}
