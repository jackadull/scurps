package net.jackadull.scurps.meta

sealed trait Rule
trait Rule0[+R] extends Rule {
  def apply()(implicit context:GameContext):Derivation[R]
}
trait Rule1[-T1,+R] extends Rule {
  def apply(v1:T1)(implicit context:GameContext):Derivation[R]
}
trait Rule2[-T1,-T2,+R] extends Rule {
  def apply(v1:T1, v2:T2)(implicit context:GameContext):Derivation[R]
}
