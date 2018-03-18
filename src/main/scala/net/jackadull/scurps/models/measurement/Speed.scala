package net.jackadull.scurps.models.measurement

case class Speed[A] private(yardsPerSecond:A)
object Speed {
  def ofYardsPerSecond[A](yardsPerSecond:A):Speed[A] = Speed(yardsPerSecond)
}
