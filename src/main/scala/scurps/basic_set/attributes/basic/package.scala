package scurps.basic_set.attributes

import scurps._
import scurps.basic_set.attributes.basic.BasicAttribute.{BoughtPointsProperty, Health, Strength}
import scurps.basic_set.bib.G4e_Characters.Ch01_Creating_A_Character.Basic_Attributes
import scurps.meta.algebra.ScurpsOps
import scurps.meta.data.GameContext
import scurps.meta.data.GameContext.Subject
import scurps.meta.rule.Rule.{Rule1, Rule2}
import scurps.meta.rule.RuleCatalog
import scurps.meta.unit.Score.IntScore
import scurps.meta.unit.{CP, Score}

package object basic {
  val basicSetRules:RuleCatalog = RuleCatalog(
    BasicAttributeScore -> new Rule1[BasicAttribute,IntScore] {
      override def apply[A[+_]](attribute:A[BasicAttribute], context:A[GameContext])(implicit ops:ScurpsOps[A]):A[IntScore] =
        (FreeAttributeScore(attribute, context) :+ BoughtBasicAttributePoints(attribute, context))
          .accordingTo(basicAttributesIntro)
    },
    BoughtBasicAttributePoints -> new Rule1[BasicAttribute,IntScore] {
      override def apply[A[+_]](attribute:A[BasicAttribute], context:A[GameContext])(implicit ops:ScurpsOps[A]):A[IntScore] =
        context.get(Subject).ifDefined(_.get(attribute.get(BoughtPointsProperty)).orElse(Score(0)))
    },
    CPCostPerBasicAttributePoint -> new Rule1[BasicAttribute,CP] {
      override def apply[A[+_]](attribute:A[BasicAttribute], context:A[GameContext])(implicit ops:ScurpsOps[A]):A[CP] =
        attribute.ifElementOf(Set[BasicAttribute](Strength, Health), _ => 10.cp, _ => 20.cp)
          .accordingTo(basicAttributesIntro)
    },
    CPSpentOnBasicAttribute -> new Rule1[BasicAttribute,CP] {
      override def apply[A[+_]](attribute:A[BasicAttribute], context:A[GameContext])(implicit ops:ScurpsOps[A]):A[CP] =
        BoughtBasicAttributePoints(attribute, context) :* CPCostPerBasicAttributePoint(attribute, context)
    },
    FreeAttributeScore -> new Rule1[BasicAttribute,IntScore] {
      override def apply[A[+_]](attribute:A[BasicAttribute], context:A[GameContext])(implicit ops:ScurpsOps[A]):A[IntScore] =
        context.get(Subject).ifDefined(_ => Score(10)).accordingTo(basicAttributesIntro)
    },
    SetBasicAttribute -> new Rule2[BasicAttribute,IntScore,GameContext] {
      override def apply[A[+_]](attribute:A[BasicAttribute], newScore:A[IntScore], context:A[GameContext])(implicit ops:ScurpsOps[A]):A[GameContext] = {
        val boughtPointsKey = attribute.get(BoughtPointsProperty)
        context.mod(Subject) {_.setNonZero(boughtPointsKey, newScore :- FreeAttributeScore(attribute, context))}
      }
    }
  )

  private val basicAttributesIntro = Basic_Attributes.chapter.page(14)
}
