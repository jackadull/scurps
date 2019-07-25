package scurps.bib

/** Bibliographic reference. */
sealed trait BibRef
object BibRef {
  def work(work:Work):WorkRef = WorkRef(work, chapterHierarchy = Seq.empty, page = None)

  final case class WorkRef(work:Work, chapterHierarchy:Seq[String], page:Option[PageRef]) extends BibRef {
    def chapter(hierarchy:String*):WorkRef = copy(chapterHierarchy = hierarchy.toSeq)
    def page(ref:PageRef):WorkRef = copy(page = Some(ref))
    def page(pageNumber:Int):WorkRef = page(PageRef.single(pageNumber))

    override val toString:String =
      (work.toString +: chapterHierarchy.map("\"" + _ + "\"")).mkString(", ") + (page match {
        case Some(p) => s" ($p)"
        case None => ""
      })
  }
}
