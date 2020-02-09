package scurps.meta.algebra

/** Witnesses that the first type [[C]] is some form of container for [[E]]. */
trait TypeContains[C,E] // TODO not sure about the variance
object TypeContains {
  implicit def everyTypeContainsItself[T]:TypeContains[T,T] = new TypeContains[T,T] {} // TODO toString, singleton
}
