package net.jackadull.scurps.rules.cp

import net.jackadull.scurps.testing.TestHierarchy
import net.jackadull.scurps.testing.TestHierarchy.{TestBranch, assert, testLeaf}

import scala.language.postfixOps

object CPSumsTestGenerator {
  def globalInvariants[A](accessor:CPSumsTestAccessor[A], character:A):Seq[TestHierarchy] = Seq(
    testLeaf("cp sums invariants") {
      import accessor._
      assert(cpSum(character) == sumOfNegativeCPCosts(character)+sumOfPositiveCPCosts(character), "CP sum of the character is not equal to the sum of positive and negative CP costs")
      assert(sumOfPositiveCPCosts(character)>=0, "sum of positive CP costs is negative")
      assert(sumOfNegativeCPCosts(character)<=0, "sum of negative CP costs is greater than zero")
    }
  )

  def initialConditions[A](accessor:CPSumsTestAccessor[A]):Seq[TestHierarchy] = Seq(
    testLeaf("CP sum of initial character is 0") {
      assert(accessor.cpSum(accessor initialCharacter) == 0)
    },
    TestBranch("CP sum invariants for initial character", globalInvariants(accessor, accessor initialCharacter))
  )
}
