package scurps.bib

import scurps.bib.BibRef.Empty

/** Bibliographic reference. */
sealed trait BibRef {
  def +(that:BibRef):BibRef = (this, that) match {
    case (Empty,_) => that
    case (_,Empty) => this
    case _ => sys.error("combination with non-empty BibRef is not yet implemented") // TODO implement BibRef combinations
  }
}
object BibRef {
  def work(work:Work):WorkRef = WorkRef(work, chapterHierarchy = Seq.empty, page = None)

  object Empty extends BibRef

  final case class WorkRef(work:Work, chapterHierarchy:Seq[String], page:Option[PageRef]) extends BibRef {
    def chapter(hierarchy:String*):WorkRef = copy(chapterHierarchy = hierarchy.toSeq)
    def page(ref:PageRef):WorkRef = copy(page = Some(ref))
    def page(pageNumber:Int):WorkRef = page(PageRef.single(pageNumber))
    def subChapter(chapterName:String):WorkRef = copy(chapterHierarchy = chapterHierarchy :+ chapterName)

    override val toString:String =
      (work.toString +: chapterHierarchy.map("\"" + _ + "\"")).mkString(", ") + (page match {
        case Some(p) => s" ($p)"
        case None => ""
      })
  }
}
