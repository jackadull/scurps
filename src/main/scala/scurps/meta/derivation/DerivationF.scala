package scurps.meta.derivation

import scurps.meta.derivation.Params.PNil

trait DerivationF[-P<:Params,-C,+R] extends ((P,Derivation[C])=>Derivation[R])
object DerivationF {
  trait DerivationF0[-C,+R] extends DerivationF[PNil,C,R] {
    protected def derive(context:Derivation[C]):Derivation[R]
    @inline override final def apply(pNil:PNil, context:Derivation[C]):Derivation[R] = derive(context)
  }
}
