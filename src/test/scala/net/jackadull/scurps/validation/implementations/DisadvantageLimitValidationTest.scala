package net.jackadull.scurps.validation.implementations

import net.jackadull.scurps.context.GContext
import net.jackadull.scurps.testing.HierarchySpec

import scala.language.postfixOps

class DisadvantageLimitValidationTest extends HierarchySpec {
  testHierarchy(DisadvantageLimitValidationTestGenerator initialConditions(GContext default))
}
