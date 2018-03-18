package net.jackadull.scurps.rules.attributes.basic

import net.jackadull.scurps.rules.GRules

import scala.language.postfixOps

object DexterityRule extends BasicAttributeRule.Specific {
  private[attributes] def cpCostPerPoint = 20
  private[attributes] def resolveBARule(rules:GRules):BasicAttributeRule = rules dexterityRule
}
