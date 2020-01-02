package scurps.meta.data

trait KeyWrapper[-T,+K] {
  def apply(value:T):K
}
