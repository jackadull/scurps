package scurps.meta

import scurps.meta.derivation.Params.{PNil, Params1, Params2}

package object derivation {
  type DerivationF[-P<:Params,-C,+R] = (P,Derivation[C])=>Derivation[R]
  type DerivationF0[-C,+R] = DerivationF[PNil,C,R]
  type DerivationF1[-T1,-C,+R] = DerivationF[Params1[T1],C,R]
  type DerivationF2[-T1,-T2,-C,+R] = DerivationF[Params2[T1,T2],C,R]
}
