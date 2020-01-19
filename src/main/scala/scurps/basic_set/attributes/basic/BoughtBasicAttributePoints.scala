package scurps.basic_set.attributes.basic

import scurps.meta.data.Score.IntScore
import scurps.meta.data.ShowKey.{ShowProductKey, ShowSingletonKey}
import scurps.meta.data.{ValueKey, WrapKey}
import scurps.meta.rule.RuleKey.RuleKey1

final case class BoughtBasicAttributePoints(attribute:BasicAttribute) extends ValueKey[IntScore] with ShowProductKey
object BoughtBasicAttributePoints
extends RuleKey1[BasicAttribute,IntScore] with WrapKey[BasicAttribute,BoughtBasicAttributePoints]
with ShowSingletonKey {
  override def apply(attribute:BasicAttribute):BoughtBasicAttributePoints = attribute.boughtPointsKey
  override def toString:String = super[ShowSingletonKey].toString
}
