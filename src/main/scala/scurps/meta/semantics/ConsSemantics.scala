package scurps.meta.semantics

trait ConsSemantics[C[_]] {
  def headOption[T](collection:C[T]):Option[T]
  def tailOption[T](collection:C[T]):Option[C[T]]
}
object ConsSemantics {
  // TODO toString
  implicit object IterableConsSemantics extends ConsSemantics[Iterable] {
    override def headOption[T](collection:Iterable[T]):Option[T] = collection.headOption
    override def tailOption[T](collection:Iterable[T]):Option[Iterable[T]] =
      if(collection.isEmpty) None else Some(collection.tail)
  }
}
