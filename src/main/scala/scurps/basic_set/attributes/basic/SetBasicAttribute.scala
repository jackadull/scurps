package scurps.basic_set.attributes.basic

import scurps.meta.data.GameContext
import scurps.meta.data.ShowKey.ShowSingletonKey
import scurps.meta.rule.RuleKey.RuleKey2
import scurps.meta.unit.Score.IntScore

object SetBasicAttribute extends RuleKey2[BasicAttribute,IntScore,GameContext] with ShowSingletonKey
