package scurps.basic_set.bib

import scurps.bib.BibRef.WorkRef
import scurps.bib.Work

object G4e_Characters {
  val work:Work = Work(s"$GURPS_Basic_Set_Name: Characters", edition = Fourth_Edition_Opt)

  object Ch01_Creating_A_Character {
    val chapter:WorkRef = G4e_Characters.work.bib.subChapter("Creating A Character")

    object Basic_Attributes {
      val chapter:WorkRef = Ch01_Creating_A_Character.chapter.subChapter("Basic Attributes")
    }
  }
}
