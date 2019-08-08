package scurps.basicset.attributes.basic

import scurps.meta.RuleKey.RuleKey1
import scurps.meta.Score.IntScore
import scurps.meta.ValueKey

final case class BoughtBasicAttributePoints(attribute:BasicAttribute) extends ValueKey[IntScore]
object BoughtBasicAttributePoints extends RuleKey1[BasicAttribute,IntScore]
