package scurps.basic_set.attributes.basic

import org.scalatest.{FreeSpec, Matchers}
import scurps._
import scurps.basic_set.attributes.basic.BasicAttribute.Strength
import scurps.meta.algebra.{DebugScurpsOps, OptionScurpsOps, ScurpsOps}
import scurps.meta.context.ContextKey.Subject
import scurps.meta.context.GameContext
import scurps.meta.data.{PMap, Score}
import scurps.meta.rule.Params.{PNil, p}

class BasicAttributesTest extends FreeSpec with Matchers {
  implicit val scurpsOps:ScurpsOps[Option] = new DebugScurpsOps(OptionScurpsOps)

  "When there is no subject" - {
    "strength is undefined" in
      {Strength(PNil, GameContext.basicSet.constant) should be (None)}
    "bought strength points is undefined" in
      {BoughtBasicAttributePoints(p(Strength.constant), GameContext.basicSet.constant) should be (None)}
    "free strength points is undefined" in
      {FreeAttributeScore(p(Strength.constant), GameContext.basicSet.constant) should be (None)}
    "setting strength returns undefined" in
      {Strength.set(p(Score(12).constant), GameContext.basicSet.constant) should be (None)}
  }
  "For an empty subject" - {
    val context = GameContext.basicSet.updated(Subject, PMap.empty).constant
    "strength is 10" in
      {Strength(PNil, context) should be (Some(Score(10)))}
  }
}
