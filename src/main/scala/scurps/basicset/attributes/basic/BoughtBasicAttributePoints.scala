package scurps.basicset.attributes.basic

import scurps.meta.context.ValueKey
import scurps.meta.data.KeyWrapper.KeyWrapperSupport
import scurps.meta.data.Score.IntScore
import scurps.meta.rule.RuleKey.RuleKeyA1

final case class BoughtBasicAttributePoints(attribute:BasicAttribute) extends ValueKey[IntScore]
case object BoughtBasicAttributePoints extends RuleKeyA1[BasicAttribute,IntScore]
  with KeyWrapperSupport[BoughtBasicAttributePoints,BasicAttribute] {
  override def wrap(attr:BasicAttribute):BoughtBasicAttributePoints = BoughtBasicAttributePoints(attr)
}
