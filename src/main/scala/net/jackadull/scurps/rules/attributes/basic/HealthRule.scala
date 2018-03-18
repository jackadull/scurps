package net.jackadull.scurps.rules.attributes.basic

import net.jackadull.scurps.rules.GRules

import scala.language.postfixOps

object HealthRule extends BasicAttributeRule.Specific {
  private[attributes] def cpCostPerPoint = 10
  private[attributes] def resolveBARule(rules:GRules) = rules healthRule
}
