package scurps.meta.semantics

trait ElementSemantics[-C[_]] {def isElement[E](collection:C[E], element:E):Boolean}
object ElementSemantics {
  // TODO toString
  implicit object SetElementSemantics extends ElementSemantics[Set]
    {override def isElement[E](collection:Set[E], element:E):Boolean = collection(element)}

  // TODO toString
  implicit object SeqElementSemantics extends ElementSemantics[Seq]
    {override def isElement[E](collection:Seq[E], element:E):Boolean = collection.contains(element)}
}
