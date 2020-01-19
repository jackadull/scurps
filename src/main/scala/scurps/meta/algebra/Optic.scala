package scurps.meta.algebra

sealed trait Optic
object Optic {
  trait OptionGetter[-S,+T] {def getOptional(source:S):Option[T]}
}
