package scurps.basicset.attributes.basic

import scurps.meta.RuleKey.RuleKey0
import scurps.meta.Score.IntScore
import scurps.meta.{Derivation, GameContext}

trait BasicAttribute extends RuleKey0[IntScore] {
  override def apply()(implicit context:GameContext):Derivation[IntScore] = BasicAttributeScore(this)
}
object BasicAttribute {
  object Strength extends BasicAttribute
}
