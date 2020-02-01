package scurps.meta.semantics

trait IsElement[-C[_]] {def isElement[E](collection:C[E], element:E):Boolean}
object IsElement {
  // TODO toString
  implicit object SetIsElement extends IsElement[Set]
    {override def isElement[E](collection:Set[E], element:E):Boolean = collection(element)}

  // TODO toString
  implicit object SeqIsElement extends IsElement[Seq]
    {override def isElement[E](collection:Seq[E], element:E):Boolean = collection.contains(element)}
}
