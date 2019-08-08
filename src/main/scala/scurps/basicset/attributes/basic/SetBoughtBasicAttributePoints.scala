package scurps.basicset.attributes.basic

import scurps.meta.GameContext
import scurps.meta.RuleKey.RuleKey1
import scurps.meta.Score.IntScore

final case class SetBoughtBasicAttributePoints(attribute:BasicAttribute) extends RuleKey1[IntScore,GameContext]
