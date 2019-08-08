package scurps.basicset.attributes.basic

import scurps.meta.Score.IntScore
import scurps.meta.{Rule0, RuleKey, ValueKey}

final case class BoughtBasicAttributePoints(attribute:BasicAttribute) extends RuleKey[Rule0[IntScore]] with ValueKey[IntScore]
