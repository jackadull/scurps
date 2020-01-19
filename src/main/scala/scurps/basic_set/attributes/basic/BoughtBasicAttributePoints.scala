package scurps.basic_set.attributes.basic

import scurps.meta.algebra.WrapKey
import scurps.meta.data.ShowKey.{ShowProductKey, ShowSingletonKey}
import scurps.meta.data.ValueProperty
import scurps.meta.rule.RuleKey.RuleKey1
import scurps.meta.unit.Score.IntScore

final case class BoughtBasicAttributePoints(attribute:BasicAttribute) extends ValueProperty[IntScore] with ShowProductKey
object BoughtBasicAttributePoints
extends RuleKey1[BasicAttribute,IntScore] with WrapKey[BasicAttribute,BoughtBasicAttributePoints]
with ShowSingletonKey {
  override def apply(attribute:BasicAttribute):BoughtBasicAttributePoints = attribute.boughtPointsKey
  override def toString:String = super[ShowSingletonKey].toString
}
