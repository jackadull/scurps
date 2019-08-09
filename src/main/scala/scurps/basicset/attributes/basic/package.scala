package scurps.basicset.attributes

import scurps.meta.RuleCatalog

package object basic {
  val basicAttributeRules:RuleCatalog = RuleCatalog(
    BasicAttributeScore -> BasicAttributeScoreRule,
    BoughtBasicAttributePoints -> BoughtBasicAttributePointsRule,
    FreeAttributeScore -> FreeAttributeScoreRule
  )
}
