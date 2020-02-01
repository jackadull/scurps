package scurps.meta.semantics

trait UnconsSemantics[C[_]] {def uncons[T](collection:C[T]):Option[(T,C[T])]}
object UnconsSemantics {
  // TODO toString
  implicit object IterableUnconsSemantics extends UnconsSemantics[Iterable] {
    override def uncons[T](collection:Iterable[T]):Option[(T,Iterable[T])] =
      if(collection.isEmpty) None else Some((collection.head, collection.tail))
  }
}
