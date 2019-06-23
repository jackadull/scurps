package net.jackadull.scurps.meta

sealed trait Rule
trait Rule0[+R,-C] extends Rule {
  def apply()(implicit context:C):Derivation[R]
}
trait Rule1[-T1,+R,-C] extends Rule {
  def apply(v1:T1)(implicit context:C):Derivation[R]
}
trait Rule2[-T1,-T2,+R,-C] extends Rule {
  def apply(v1:T1, v2:T2)(implicit context:C):Derivation[R]
}
