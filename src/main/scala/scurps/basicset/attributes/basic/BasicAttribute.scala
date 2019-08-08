package scurps.basicset.attributes.basic

import scurps.meta.Score.IntScore
import scurps.meta.{Rule0, RuleKey}

trait BasicAttribute extends RuleKey[Rule0[IntScore]]
object BasicAttribute {
  object Strength extends BasicAttribute
}
