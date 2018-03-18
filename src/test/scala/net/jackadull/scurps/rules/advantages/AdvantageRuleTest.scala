package net.jackadull.scurps.rules.advantages

import net.jackadull.scurps.context.GContext
import net.jackadull.scurps.meta.data.GCharacterData
import net.jackadull.scurps.testing.HierarchySpec

import scala.language.postfixOps

class AdvantageRuleTest extends HierarchySpec {
  testHierarchy(AdvantageRuleTestGenerator.initialConditions(GCharacterData empty, GContext default))
}
