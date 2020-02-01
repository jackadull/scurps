package scurps.meta.algebra

import scurps.meta.algebra.Semantic.{Cons, IsElement, Uncons}

import scala.collection.IterableOps

trait SemanticImplicits {
  // TODO toString
  implicit def iterableOpsUncons[I[T]<:IterableOps[T,I,I[T]]]:Uncons[I] = new Uncons[I] {
    override def uncons[T](collection:I[T]):Option[(T,I[T])] =
      if(collection.isEmpty) None else Some((collection.head, collection.tail))
  }

  // TODO toString
  implicit object SetSemantics extends Cons[Set] with IsElement[Set] with Uncons[Set] {
    override def cons[T](head:T, tail:Set[T]):Set[T] = tail + head
    override def empty[T]:Set[T] = Set.empty
    override def isElement[E](collection:Set[E], element:E):Boolean = collection(element)
    override def uncons[T](collection:Set[T]):Option[(T, Set[T])] =
      if(collection.isEmpty) None else Some((collection.head, collection.tail))
  }

  // TODO toString
  implicit object SeqSemantics extends Cons[Seq] with IsElement[Seq] with Uncons[Seq] {
    override def cons[T](head:T, tail:Seq[T]):Seq[T] = head +: tail
    override def empty[T]:Seq[T] = Seq.empty
    override def isElement[E](collection:Seq[E], element:E):Boolean = collection.contains(element)
    override def uncons[T](collection:Seq[T]):Option[(T, Seq[T])] =
      if(collection.isEmpty) None else Some((collection.head, collection.tail))
  }
}
