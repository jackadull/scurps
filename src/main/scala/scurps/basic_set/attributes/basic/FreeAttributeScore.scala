package scurps.basic_set.attributes.basic

import scurps.meta.data.ShowKey.ShowSingletonKey
import scurps.meta.rule.RuleKey.RuleKey1
import scurps.meta.unit.Score.IntScore

object FreeAttributeScore extends RuleKey1[BasicAttribute,IntScore] with ShowSingletonKey
