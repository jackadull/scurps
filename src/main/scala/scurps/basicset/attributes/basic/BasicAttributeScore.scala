package scurps.basicset.attributes.basic

import scurps.meta.{Rule0, RuleKey}
import scurps.meta.Score.IntScore

final case class BasicAttributeScore(attribute:BasicAttribute) extends RuleKey[Rule0[IntScore]]
