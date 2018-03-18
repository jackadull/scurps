package net.jackadull.scurps.rules.attributes.basic

import net.jackadull.scurps.testing.TestHierarchy
import net.jackadull.scurps.testing.TestHierarchy.{TestBranch, TestLeaf, assert}

import scala.language.postfixOps

object BasicAttributesTestGenerator {
  def globalInvariants[A](basicAttributesTestAccessor:BasicAttributesTestAccessor[A], character:A):Seq[TestHierarchy] = {
    import basicAttributesTestAccessor._
    BasicAttributeTestModel.all flatMap {attribute ⇒ Seq(
      new TestLeaf(s"changing $attribute leaves other basic attributes unchanged") {def execute() {
        val after:A =
          setBasicAttributeLevelsBought(attribute, basicAttributeLevelsBought(attribute, character) + 5, character)
        for(otherAttribute←BasicAttributeTestModel.all; if otherAttribute!=attribute) {
          assert(
            basicAttributeScore(otherAttribute, character) == basicAttributeScore(otherAttribute, after),
            s"after changing $attribute, the score of $otherAttribute changed as well"
          )
          assert(
            cpCostForBasicAttribute(otherAttribute, character) == cpCostForBasicAttribute(otherAttribute, after),
            s"after changing $attribute, the CP cost of $otherAttribute changed as well"
          )
        }
      }},
      new TestLeaf(s"$attribute costs ${attribute cpCostPerLevel} CP per point") {def execute() {
        for(levelsBought←0 until 30) {
          val after = setBasicAttributeLevelsBought(attribute, levelsBought, character)
          val cpCost = cpCostForBasicAttribute(attribute, after)
          val expectedCPCost = attribute.cpCostPerLevel * levelsBought
          assert(cpCost == expectedCPCost, s"CP cost for $levelsBought bought points of $attribute is $cpCost, but should be $expectedCPCost")
        }
      }}
    )}
  }

  def initialConditions[A](basicAttributesTestAccessor:BasicAttributesTestAccessor[A]):Seq[TestHierarchy] = {
    import basicAttributesTestAccessor._
    (BasicAttributeTestModel.all map {attribute⇒
      new TestLeaf(s"initial $attribute score is 10") {def execute() {
        val score = basicAttributeScore(attribute, initialCharacter)
        assert(score == 10, s"expected 10 points for $attribute, but found $score")
      }}
    }) :+ TestBranch("basic attribute invariants for the initial character", globalInvariants(basicAttributesTestAccessor, initialCharacter))
  }
}
