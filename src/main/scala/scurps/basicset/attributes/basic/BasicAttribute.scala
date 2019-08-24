package scurps.basicset.attributes.basic

import scurps.meta.Concept
import scurps.meta.Score.IntScore
import scurps.meta.rule.RuleKey.RuleKey0

trait BasicAttribute extends RuleKey0[IntScore]
object BasicAttribute {
  object Strength extends BasicAttribute

  implicit object BasicAttributeConcept extends Concept[BasicAttribute]
}
