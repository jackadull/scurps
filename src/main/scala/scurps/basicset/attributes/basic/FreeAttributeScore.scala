package scurps.basicset.attributes.basic

import scurps.meta.Score.IntScore
import scurps.meta.{Rule0, RuleKey}

final case class FreeAttributeScore(attribute:BasicAttribute) extends RuleKey[Rule0[IntScore]]
