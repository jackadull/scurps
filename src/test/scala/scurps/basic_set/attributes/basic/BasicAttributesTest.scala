package scurps.basic_set.attributes.basic

import org.scalatest.{FreeSpec, Matchers}
import scurps._
import scurps.basic_set.TotalCPSpent
import scurps.basic_set.attributes.basic.BasicAttribute.{Dexterity, Health, Intelligence, Strength}
import scurps.meta.algebra.{OptionScurpsOps, ScurpsOps}
import scurps.meta.data.GameContext.Subject
import scurps.meta.data.{GCharacter, GameContext}
import scurps.meta.unit.{CPBalance, Score}

class BasicAttributesTest extends FreeSpec with Matchers {
  implicit val scurpsOps:ScurpsOps[Option] = OptionScurpsOps

  "When there is no subject:" - {
    "Strength is undefined." in
      {Strength[Option](GameContext.basicSet) should be (None)}
    "Bought strength points is undefined." in
      {BoughtBasicAttributePoints[Option](Strength, GameContext.basicSet) should be (None)}
    "Free strength points is undefined." in
      {FreeAttributeScore[Option](Strength, GameContext.basicSet) should be (None)}
    "CP spent on strength is undefined." in
      {CPSpentOnBasicAttribute[Option](Strength, GameContext.basicSet) should be (None)}
    "Setting strength returns undefined." in
      {Strength.set[Option](Score(12), GameContext.basicSet) should be (None)}
  }
  "For an empty subject" - {
    val context = Subject.set(GameContext.basicSet, GCharacter.empty)
    "Strength is 10." in
      {Strength[Option](context) should be (Some(Score(10)))}
    "Bought strenght points is 0." in
      {BoughtBasicAttributePoints[Option](Strength, context) should be (Some(Score(0)))}
    "Free strength points is 10." in
      {FreeAttributeScore[Option](Strength, context) should be (Some(Score(10)))}
    "CP spent on strength is 0." in
      {CPSpentOnBasicAttribute[Option](Strength, context) should be (Some(0.cp))}
    "After setting strength to 8, it remains 8." in {
      val str8 = Strength.set[Option](Score(8), context)
      Strength(str8) should be (Some(Score(8)))
    }
  }
  "Basic attribute CP cost:" - {
    val context = Subject.set(GameContext.basicSet, GCharacter.empty)
    "DX 16 costs 120 CP." in {
      val dx16 = Dexterity.set[Option](Score(16), context)
      CPSpentOnBasicAttribute[Option](Dexterity, dx16) should be (Some(120.cp))
    }
    "HT 25 costs 150 CP." in {
      val ht25 = Health.set[Option](Score(25), context)
      CPSpentOnBasicAttribute[Option](Health, ht25) should be (Some(150.cp))
    }
    "IQ 1 costs -180 CP." in {
      val iq1 = Intelligence.set[Option](Score(1), context)
      CPSpentOnBasicAttribute[Option](Intelligence, iq1) should be (Some(-180.cp))
    }
    "ST 8 costs -20 CP." in {
      val str8 = Strength.set[Option](Score(8), context)
      CPSpentOnBasicAttribute[Option](Strength, str8) should be (Some(-20.cp))
    }
    "DX 8, HT 25, IQ 1, ST 8 has a CP balance of 270 in positive and -200 in negative." in {
      val dx = Dexterity.set[Option](Score(16), context)
      val dx_ht = Health.set[Option](Score(25), dx)
      val dx_ht_iq = Intelligence.set[Option](Score(1), dx_ht)
      val dx_ht_iq_st = Strength.set[Option](Score(8), dx_ht_iq)
      TotalCPSpent(dx_ht_iq_st) should be (Some(CPBalance(sumOfNegatives = -200.cp, sumOfPositives = 270.cp)))
    }
  }
}
