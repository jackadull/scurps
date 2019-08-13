package scurps.basicset.attributes

import scurps.basicset.bib.G4e_Characters.Ch01_Creating_A_Character
import scurps.meta.RuleCatalog
import scurps.meta.Score.IntScore

package object basic {
  val basicAttributeRules:RuleCatalog = RuleCatalog(
    BasicAttributeScore -> BasicAttributeScoreRule,
    BoughtBasicAttributePoints -> BoughtBasicAttributePointsRule,
    FreeAttributeScore -> // TODO should this be undefined if there is no subject?
      IntScore(10).asConstant(Ch01_Creating_A_Character.Basic_Attributes.chapter.page(14)).forAny[BasicAttribute]
  )
}
