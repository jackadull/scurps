package scurps.meta.data

// TODO maybe reverse the type parameter order, so instances can be created without the custom apply method, and implicit resolution works without the need for an import statement
trait KeyWrapper[+K,T] {
  def apply(value:T):K
}
object KeyWrapper {
  def apply[K,T](f:T=>K):KeyWrapper[K,T] = (value:T) => f(value)

  trait KeyWrapperSupport[+K,T] {
    def wrap(value:T):K
    implicit val wrapper:KeyWrapper[K,T] = KeyWrapper(wrap)
  }
}
