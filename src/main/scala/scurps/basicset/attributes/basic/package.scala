package scurps.basicset.attributes

import scurps.basicset.bib.G4e_Characters.Ch01_Creating_A_Character
import scurps.meta.ContextKey.Subject
import scurps.meta.Score.IntScore
import scurps.meta.{RuleCatalog, Score}

package object basic {
  val basicAttributeRules:RuleCatalog = RuleCatalog(
    BasicAttributeScore ->
      (FreeAttributeScore.eval :+ BoughtBasicAttributePoints.eval),
    BoughtBasicAttributePoints ->
      BoughtBasicAttributePoints.getFrom(Subject.fromContext).orDefault(Score(0)),
    FreeAttributeScore -> // TODO should this be undefined if there is no subject?
      IntScore(10).asConstant.forAny[BasicAttribute].accordingTo(Ch01_Creating_A_Character.Basic_Attributes.chapter.page(14))
  )
}
