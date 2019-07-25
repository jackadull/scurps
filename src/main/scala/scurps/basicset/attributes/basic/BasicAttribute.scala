package scurps.basicset.attributes.basic

import scurps.meta.{Rule0, RuleKey}

trait BasicAttribute extends RuleKey[Rule0[Int]]
object BasicAttribute {
  object Strength extends BasicAttribute
}
