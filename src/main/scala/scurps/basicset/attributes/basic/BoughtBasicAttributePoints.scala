package scurps.basicset.attributes.basic

import scurps.meta.RuleKey.RuleKey1
import scurps.meta.Score.IntScore
import scurps.meta.{KeyMapping, ValueKey}

final case class BoughtBasicAttributePoints(attribute:BasicAttribute) extends ValueKey[IntScore]
object BoughtBasicAttributePoints extends RuleKey1[BasicAttribute,IntScore] with KeyMapping[BasicAttribute,ValueKey,IntScore] {
  override def mapValueKey(attribute:BasicAttribute):ValueKey[IntScore] = BoughtBasicAttributePoints(attribute)
}
