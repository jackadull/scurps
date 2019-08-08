package scurps.basicset.attributes

import scurps.basicset.attributes.basic.BasicAttribute.Strength
import scurps.meta.RuleCatalog

package object basic {
  val basicAttributeRules:RuleCatalog = RuleCatalog(
    Strength -> BasicAttributeRule(Strength),
    BasicAttributeScore -> BasicAttributeScoreRule,
    BoughtBasicAttributePoints -> BoughtBasicAttributePointsRule,
    FreeAttributeScore -> FreeAttributeScoreRule
  )
}
