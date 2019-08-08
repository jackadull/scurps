package scurps.basicset.attributes.basic

import scurps.meta.Score.IntScore
import scurps.meta.{Rule1, RuleKey}

case object FreeAttributeScore extends RuleKey[Rule1[BasicAttribute,IntScore]]
