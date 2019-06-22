package net.jackadull.scurps.meta

sealed trait Rule[E,R]
trait Rule0[E,R] extends Rule[E,R] {
  def apply()(implicit environment:E):Derivation[R]
}
trait Rule1[T1,E,R] extends Rule[E,R] {
  def apply(v1:T1)(implicit environment:E):Derivation[R]
}
trait Rule2[T1,T2,E,R] extends Rule[E,R] {
  def apply(v1:T1, v2:T2)(implicit environment:E):Derivation[R]
}
