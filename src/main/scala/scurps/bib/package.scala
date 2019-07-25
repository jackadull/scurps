package scurps

package object bib {
  val G4e_Campaigns:Work = Work(s"$GURPS_Basic_Set: Campaigns", edition = Fourth_Edition)
  val G4e_Characters:Work = Work(s"$GURPS_Basic_Set: Characters", edition = Fourth_Edition)

  private lazy val GURPS_Basic_Set = "GURPS Basic Set"
  private lazy val Fourth_Edition = Some("4e")
}
