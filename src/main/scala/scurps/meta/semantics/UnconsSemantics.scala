package scurps.meta.semantics

import scala.collection.IterableOps

trait UnconsSemantics[C[_]] {def uncons[T](collection:C[T]):Option[(T,C[T])]}
object UnconsSemantics {
  // TODO toString
  implicit def iterableOpsUnconsSemantics[I[T]<:IterableOps[T,I,I[T]]]:UnconsSemantics[I] =
    new UnconsSemantics[I] {
      override def uncons[T](collection:I[T]):Option[(T,I[T])] =
        if(collection.isEmpty) None else Some((collection.head, collection.tail))
    }
}
