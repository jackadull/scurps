package net.jackadull.scurps.models.senses

sealed trait Sense
object Sense {
  sealed trait RegularSense extends Sense
  object Hearing extends RegularSense
  object `Taste and Smell` extends RegularSense
  object Touch extends RegularSense
  object Vision extends RegularSense

  val allRegularSenses:Set[RegularSense] = Set(Hearing, `Taste and Smell`, Touch, Vision)
}
