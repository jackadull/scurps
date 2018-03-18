package net.jackadull.scurps.rules.attributes.basic

import net.jackadull.scurps.testing.HierarchySpec

import scala.language.postfixOps

class BasicAttributesTest extends HierarchySpec
{testHierarchy(BasicAttributesTestGenerator.initialConditions(BasicAttributesTestAccessorImpl))}
