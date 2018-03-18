package net.jackadull.scurps.models.measurement

case class Weight[A] private(pounds:A)
object Weight {
  def ofPounds[A](pounds:A):Weight[A] = Weight(pounds)
}
