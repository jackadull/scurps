package scurps.basicset.attributes

import scurps.meta.Score
import scurps.meta.rule.RuleCatalog

package object basic {
  val basicAttributeRules:RuleCatalog = RuleCatalog(
    BasicAttributeScore -> (FreeAttributeScore.rule :+ BoughtBasicAttributePoints.rule),
//    BoughtBasicAttributePoints ->
//      BoughtBasicAttributePoints.getFrom(Subject.fromContext).orDefault(Score(0)),

    FreeAttributeScore -> Score(10).asConstant.forAny[BasicAttribute] // TODO accordingTo
  )
}
