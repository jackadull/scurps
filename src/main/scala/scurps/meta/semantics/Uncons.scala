package scurps.meta.semantics

import scala.collection.IterableOps

trait Uncons[C[_]] {def uncons[T](collection:C[T]):Option[(T,C[T])]}
object Uncons {
  // TODO toString
  implicit def iterableOpsUncons[I[T]<:IterableOps[T,I,I[T]]]:Uncons[I] = new Uncons[I] {
    override def uncons[T](collection:I[T]):Option[(T,I[T])] =
      if(collection.isEmpty) None else Some((collection.head, collection.tail))
  }
}
