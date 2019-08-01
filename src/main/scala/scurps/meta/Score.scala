package scurps.meta

sealed trait Score[+A] extends Any
object Score {
  final case class IntScore(intValue:Int) extends AnyVal with Score[Int]
}
