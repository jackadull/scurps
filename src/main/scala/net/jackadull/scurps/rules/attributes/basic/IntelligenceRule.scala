package net.jackadull.scurps.rules.attributes.basic

import net.jackadull.scurps.rules.GRules

import scala.language.postfixOps

object IntelligenceRule extends BasicAttributeRule.Specific {
  private[attributes] def cpCostPerPoint = 20
  private[attributes] def resolveBARule(rules:GRules) = rules intelligenceRule
}
