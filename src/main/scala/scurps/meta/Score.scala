package scurps.meta

import scurps.bib.BibRef
import scurps.meta.Rule.{DefineAsConstant, Rule0}

sealed trait Score[+A] extends Any {
  def asConstant(bibRef:BibRef):Rule0[this.type] = DefineAsConstant(this, bibRef)
}
object Score {
  final case class IntScore(intValue:Int) extends AnyVal with Score[Int]
}
