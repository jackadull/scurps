package scurps.basic_set.attributes.basic

import org.scalatest.{FreeSpec, Matchers}
import scurps._
import scurps.basic_set.attributes.basic.BasicAttribute.{Dexterity, Health, Intelligence, Strength}
import scurps.meta.algebra.{OptionScurpsOps, ScurpsOps}
import scurps.meta.data.GameContext.Subject
import scurps.meta.data.{GameContext, PMap}
import scurps.meta.unit.{CP, Score}

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
    val context = Subject.set(GameContext.basicSet, PMap.empty)
    "Strength is 10." in
      {Strength[Option](context) should be (Some(Score(10)))}
    "Bought strenght points is 0." in
      {BoughtBasicAttributePoints[Option](Strength, context) should be (Some(Score(0)))}
    "Free strength points is 10." in
      {FreeAttributeScore[Option](Strength, context) should be (Some(Score(10)))}
    "CP spent on strength is 0." in
      {CPSpentOnBasicAttribute[Option](Strength, context) should be (Some(CP(0)))}
    "After setting strength to 8, it remains 8." in {
      val str8 = Strength.set[Option](Score(8), context)
      Strength(str8) should be (Some(Score(8)))
    }
  }
  "Basic attribute CP cost:" - {
    val context = Subject.set(GameContext.basicSet, PMap.empty)
    "ST 8 costs -20 CP." in {
      val str8 = Strength.set[Option](Score(8), context)
      CPSpentOnBasicAttribute[Option](Strength, str8) should be (Some(CP(-20)))
    }
    "DX 16 costs 120 CP." in {
      val dx16 = Dexterity.set[Option](Score(16), context)
      CPSpentOnBasicAttribute[Option](Dexterity, dx16) should be (Some(CP(120)))
    }
    "IQ 1 costs -180 CP." in {
      val iq1 = Intelligence.set[Option](Score(1), context)
      CPSpentOnBasicAttribute[Option](Intelligence, iq1) should be (Some(CP(-180)))
    }
    "HT 25 costs 150 CP." in {
      val ht25 = Health.set[Option](Score(25), context)
      CPSpentOnBasicAttribute[Option](Health, ht25) should be (Some(CP(150)))
    }
  }
}
