package scurps.basicset.attributes.basic

import scurps.meta.Score.IntScore
import scurps.meta.{GameContext, Rule1, RuleKey}

final case class SetBoughtBasicAttributePoints(attribute:BasicAttribute) extends RuleKey[Rule1[IntScore,GameContext]]
