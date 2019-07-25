package scurps.bib

import scurps.bib.BibRef.WorkRef

/** Refers to one body of work, such as a book. */
final case class Work(name:String, edition:Option[String]) {
  def bib:WorkRef = BibRef.work(this)

  override val toString:String = edition match {
    case Some(ed) => s"$name ($ed)"
    case None => name
  }
}
