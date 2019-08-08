package scurps.basicset.attributes.basic

import scurps.meta.Rule.Rule1
import scurps.meta.Score.IntScore
import scurps.meta.{GameContext, RuleKey}

final case class SetBoughtBasicAttributePoints(attribute:BasicAttribute) extends RuleKey[Rule1[IntScore,GameContext]]
