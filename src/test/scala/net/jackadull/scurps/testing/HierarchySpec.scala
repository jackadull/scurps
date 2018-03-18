package net.jackadull.scurps.testing

import net.jackadull.scurps.testing.TestHierarchy.{TestBranch, TestLeaf}
import org.scalatest.FreeSpec

trait HierarchySpec extends FreeSpec {
  def testHierarchy(hierarchy:Seq[TestHierarchy]) {hierarchy foreach testHierarchy}
  def testHierarchy(hierarchy:TestHierarchy) {hierarchy match {
    case TestBranch(description, children) ⇒ description - {testHierarchy(children)}
    case leaf:TestLeaf ⇒ leaf.description in {leaf execute()}
  }}
}
