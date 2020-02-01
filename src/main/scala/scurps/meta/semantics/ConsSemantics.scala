package scurps.meta.semantics

import scala.language.existentials

trait ConsSemantics[C[_]] {
  def cons[T](head:T, tail:C[T]):C[T]
  def empty[T]:C[T]
}
object ConsSemantics {
  // TODO toString
  implicit object SetConsSemantics extends ConsSemantics[Set] {
    override def cons[T](head:T, tail:Set[T]):Set[T] = tail + head
    override def empty[T]:Set[T] = Set.empty
  }
  // TODO toString
  implicit object SeqConsSemantics extends ConsSemantics[Seq] {
    override def cons[T](head:T, tail:Seq[T]):Seq[T] = head +: tail
    override def empty[T]:Seq[T] = Seq.empty
  }
}
