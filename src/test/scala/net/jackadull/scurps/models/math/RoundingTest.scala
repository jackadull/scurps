package net.jackadull.scurps.models.math

import net.jackadull.scurps.testing.HierarchySpec

class RoundingTest extends HierarchySpec {
  testHierarchy(RoundingTestGenerator(RoundingTestAccessorImpl))
}
