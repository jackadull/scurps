package scurps.basicset.attributes.basic

import scurps.meta.Score.IntScore
import scurps.meta.{Rule1, RuleKey, ValueKey}

final case class BoughtBasicAttributePoints(attribute:BasicAttribute) extends ValueKey[IntScore]
object BoughtBasicAttributePoints extends RuleKey[Rule1[BasicAttribute,IntScore]]
