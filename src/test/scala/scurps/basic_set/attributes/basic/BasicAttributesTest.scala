package scurps.basic_set.attributes.basic

import org.scalatest.{FreeSpec, Matchers}
import scurps._
import scurps.basic_set.attributes.basic.BasicAttribute.Strength
import scurps.meta.algebra.{OptionScurpsOps, ScurpsOps}
import scurps.meta.context.ContextKey.Subject
import scurps.meta.context.GameContext
import scurps.meta.data.{PMap, Score}

class BasicAttributesTest extends FreeSpec with Matchers {
  implicit val scurpsOps:ScurpsOps[Option] = OptionScurpsOps

  "When there is no subject:" - {
    "Strength is undefined." in
      {Strength(GameContext.basicSet.constant) should be (None)}
    "Bought strength points is undefined." in
      {BoughtBasicAttributePoints(Strength.constant, GameContext.basicSet.constant) should be (None)}
    "Free strength points is undefined." in
      {FreeAttributeScore(Strength.constant, GameContext.basicSet.constant) should be (None)}
    "Setting strength returns undefined." in
      {Strength.set(Score(12).constant, GameContext.basicSet.constant) should be (None)}
  }
  "For an empty subject" - {
    val context = GameContext.basicSet.updated(Subject, PMap.empty).constant
    "Strength is 10." in
      {Strength(context) should be (Some(Score(10)))}
    "Bought strenght points is 0." in
      {BoughtBasicAttributePoints(Strength.constant, context) should be (Some(Score(0)))}
    "Free strength points is 10." in
      {FreeAttributeScore(Strength.constant, context) should be (Some(Score(10)))}
    "After setting strength to 8, it remains 8." in {
      val str8 = Strength.set(Score(8).constant, context)
      Strength(str8) should be (Some(Score(8)))
    }
  }
}