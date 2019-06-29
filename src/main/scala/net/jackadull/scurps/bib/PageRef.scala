package net.jackadull.scurps.bib

/** Refers to one or more page numbers. */
sealed trait PageRef
object PageRef {
  def single(pageNumber:Int):SinglePageRef = SinglePageRef(pageNumber)

  final case class SinglePageRef(pageNumber:Int) extends PageRef {
    override def toString:String = s"p. $pageNumber"
  }
}
