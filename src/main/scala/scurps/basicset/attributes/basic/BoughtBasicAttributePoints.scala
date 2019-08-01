package scurps.basicset.attributes.basic

import scurps.meta.{Rule0, RuleKey, ValueKey}
import scurps.meta.Score.IntScore

final case class BoughtBasicAttributePoints(attribute:BasicAttribute) extends RuleKey[Rule0[IntScore]] with ValueKey[IntScore]
