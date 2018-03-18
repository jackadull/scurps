package net.jackadull.scurps.testing

import net.jackadull.scurps.testing.Modification.CombinedModification

import scala.language.postfixOps

/** A change that can be applied to an object, usually a character. In addition to [[Function1]], a `Modification`
  * also has a description, to be used in test case descriptions and error messages, When combined with other
  * `Modification` instances, their descriptions are combined as well. */
trait Modification[A] extends (A⇒A) {
  def description:String
  def :+(that:Modification[A]):Modification[A] = that match {
    case CombinedModification(elements) ⇒ CombinedModification(this +: elements)
    case _ ⇒ CombinedModification(Seq(this, that))
  }
  override def toString = description
}
object Modification {
  case class CombinedModification[A](elements:Seq[Modification[A]]) extends Modification[A] {
    def apply(v:A):A = elements.foldLeft(v) {(a,mod) ⇒ mod(a)}
    def description:String = elements.map(_ description).mkString("(", ", ", ")")
    override def :+(that:Modification[A]):Modification[A] = that match {
      case CombinedModification(thatElements) ⇒ CombinedModification(elements ++ thatElements)
      case _ ⇒ CombinedModification(elements :+ that)
    }
  }
}
