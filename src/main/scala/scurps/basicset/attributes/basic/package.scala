package scurps.basicset.attributes

import scurps.meta.Score
import scurps.meta.rule.RuleCatalog

package object basic {
  val basicAttributeRules:RuleCatalog = RuleCatalog(
    BasicAttributeScore -> (FreeAttributeScore.rule :+ BoughtBasicAttributePoints.rule),
//    BoughtBasicAttributePoints ->
//      BoughtBasicAttributePoints.getFrom(Subject.fromContext).orDefault(Score(0)),

//    BoughtBasicAttributePoints ->
//      BoughtBasicAttributePoints.of[BasicAttribute].getFrom(Subject.fromContext).orDefault(Score(0)),

//    BoughtBasicAttributePoints ->
//      Subject.fromContext.getR(BoughtBasicAttributePoints.of[BasicAttribute]).orDefault(Score(0)),

//    BoughtBasicAttributePoints ->
//      Subject.fromContext.get(BoughtBasicAttributePoints.of(BasicAttribute.concept.param)).orDefault(Score(0)),

//    BoughtBasicAttributePoints ->
//      GameContext.get(Subject).get(BoughtBasicAttributePoints.of(BasicAttribute.concept.param)).orDefault(Score(0)),

//    BoughtBasicAttributePoints ->
//      GameContext.get(Subject).get(BoughtBasicAttributePoints.of(Given[BasicAttribute])).orDefault(Score(0)),

//    BoughtBasicAttributePoints -> (Rule {attribute:BasicAttribute =>
//      Subject.getWithDefault0(BoughtBasicAttributePoints.of(attribute))
//    }),

//    BoughtBasicAttributePoints -> (Rule {Subject.getWithDefault0(BoughtBasicAttributePoints.of(_))}),

//    BoughtBasicAttributePoints -> {Subject.getWithDefault0(BoughtBasicAttributePoints.of(_))}, // with implicit


    // ---

//    BoughtBasicAttributePoints ->
//      Subject.modInContext(BoughtBasicAttributePoints.of[BasicAttribute].setWithDefault(IntScore.concept.param, Score(0)))

//    BoughtBasicAttributePoints ->
//      GameContext.mod(Subject, BoughtBasicAttributePoints.of[BasicAttribute].setWithDefault(IntScore.concept.param, Score(0)))

//    BoughtBasicAttributePoints ->
//      GameContext.mod(Subject, BoughtBasicAttributePoints.of(Given[BasicAttribute]).setWithDefault(Given[IntScore], Score(0)))

//    BoughtBasicAttributePoints -> (Params[BasicAttribute,IntScore] {(attribute, score) =>
//      GameContext.mod(Subject, BoughtBasicAttributePoints.of(attribute).setWithDefault0(score))
//    })

    FreeAttributeScore -> Score(10).asConstant.forAny[BasicAttribute] // TODO accordingTo
  )
}
