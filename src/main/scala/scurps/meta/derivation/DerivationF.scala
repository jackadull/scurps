package scurps.meta.derivation

import scurps.meta.derivation.Params.{PNil, Params1, Params2}

trait DerivationF[-P<:Params,-C,+R] extends ((P,Derivation[C])=>Derivation[R])
object DerivationF {
  type DerivationF0[-C,+R] = DerivationF[PNil,C,R]
  type DerivationF1[-T1,-C,+R] = DerivationF[Params1[T1],C,R]
  type DerivationF2[-T1,-T2,-C,+R] = DerivationF[Params2[T1,T2],C,R]

  trait DerivationF0Base[-C,+R] extends DerivationF0[C,R] {
    protected def derive(context:Derivation[C]):Derivation[R]
    @inline override final def apply(pNil:PNil, context:Derivation[C]):Derivation[R] = derive(context)
  }

  trait DerivationF1Base[-T1,-C,+R] extends DerivationF1[T1,C,R] {
    protected def derive(v1:Derivation[T1], context:Derivation[C]):Derivation[R]
    @inline override final def apply(params:Params1[T1], context:Derivation[C]):Derivation[R] =
      derive(params.head, context)
  }

  trait DerivationF2Base[-T1,-T2,-C,+R] extends DerivationF2[T1,T2,C,R] {
    protected def derive(v1:Derivation[T1], v2:Derivation[T2], context:Derivation[C]):Derivation[R]
    @inline override final def apply(params:Params2[T1, T2], context:Derivation[C]):Derivation[R] =
      derive(params.head, params.tail.head, context)
  }
}
