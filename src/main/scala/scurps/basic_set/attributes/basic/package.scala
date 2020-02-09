package scurps.basic_set.attributes

import scurps._
import scurps.basic_set.attributes.basic.BasicAttribute.{BoughtPointsProperty, Dexterity, Health, Intelligence, Strength}
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
    AllBasicAttributes -> pure(Set(Dexterity, Health, Intelligence, Strength)),
    BasicAttributeScore -> (FreeAttributeScore + BoughtBasicAttributePoints).accordingTo(basicAttributesIntro),
    BoughtBasicAttributePoints -> new Rule1[BasicAttribute,IntScore] {
      override def apply[A[+_]](attribute:A[BasicAttribute], context:A[GameContext])(implicit ops:ScurpsOps[A]):A[IntScore] =
        (context \ Subject).ifDefined {subject => (subject \ (attribute \ BoughtPointsProperty)).orElse(Score(0))}
    },
    CPCostPerBasicAttributePoint -> new Rule1[BasicAttribute,CP] {
      override def apply[A[+_]](attribute:A[BasicAttribute], context:A[GameContext])(implicit ops:ScurpsOps[A]):A[CP] =
        attribute.ifElementOf(Set[BasicAttribute](Strength, Health), _ => 10.cp, _ => 20.cp)
          .accordingTo(basicAttributesIntro)
    },
    CPSpentOnBasicAttribute -> BoughtBasicAttributePoints * CPCostPerBasicAttributePoint,
    FreeAttributeScore -> new Rule1[BasicAttribute,IntScore] {
      override def apply[A[+_]](attribute:A[BasicAttribute], context:A[GameContext])(implicit ops:ScurpsOps[A]):A[IntScore] =
        (context \ Subject).ifDefined(_ => Score(10)).accordingTo(basicAttributesIntro)
    },
    SetBasicAttribute -> new Rule2[BasicAttribute,IntScore,GameContext] {
      override def apply[A[+_]](attribute:A[BasicAttribute], newScore:A[IntScore], context:A[GameContext])(implicit ops:ScurpsOps[A]):A[GameContext] = {
        val boughtPointsKey = attribute \ BoughtPointsProperty
        context.mod(Subject) {_.setNonZero(boughtPointsKey, newScore - FreeAttributeScore(attribute, context))}
      }
    }
  )

  private lazy val basicAttributesIntro = Basic_Attributes.chapter.page(14)
}
