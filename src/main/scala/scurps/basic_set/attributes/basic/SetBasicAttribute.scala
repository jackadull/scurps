package scurps.basic_set.attributes.basic

import scurps.meta.context.GameContext
import scurps.meta.data.Score.IntScore
import scurps.meta.data.ShowKey.ShowSingletonKey
import scurps.meta.rule.RuleKey.RuleKey2

case object SetBasicAttribute extends RuleKey2[BasicAttribute,IntScore,GameContext] with ShowSingletonKey
