package scurps.basicset.attributes.basic

import scurps.meta.Rule.Rule0
import scurps.meta.RuleKey
import scurps.meta.Score.IntScore

trait BasicAttribute extends RuleKey[Rule0[IntScore]]
object BasicAttribute {
  object Strength extends BasicAttribute
}
