package scurps.meta.data

trait KeyWrapper[+K,T] {
  def apply(value:T):K
}
