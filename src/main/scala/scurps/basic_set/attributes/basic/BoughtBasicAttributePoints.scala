package scurps.basic_set.attributes.basic

import scurps.meta.data.GCharacterProperty
import scurps.meta.data.ShowKey.{ShowProductKey, ShowSingletonKey}
import scurps.meta.rule.RuleKey.RuleKey1
import scurps.meta.unit.Score.IntScore

final case class BoughtBasicAttributePoints(attribute:BasicAttribute) extends GCharacterProperty[IntScore] with ShowProductKey
object BoughtBasicAttributePoints
extends RuleKey1[BasicAttribute,IntScore]
with ShowSingletonKey {
  override def toString:String = super[ShowSingletonKey].toString
}
