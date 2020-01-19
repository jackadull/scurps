package scurps.meta.algebra

sealed trait Optic
object Optic {
  trait GetOptional[-S,+T] {def getOptional(source:S):Option[T]}
}
