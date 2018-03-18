package net.jackadull.scurps.rules.attributes.basic

trait BasicAttributesTestAccessor[A] {
  def basicAttributeLevelsBought(attribute:BasicAttributeTestModel, character:A):Int
  def basicAttributeScore(attribute:BasicAttributeTestModel, character:A):Int
  def cpCostForBasicAttribute(attribute:BasicAttributeTestModel, character:A):Int
  def initialCharacter:A
  def setBasicAttributeLevelsBought(attribute:BasicAttributeTestModel, newLevelsBought:Int, character:A):A
  def setBasicAttributeScore(attribute:BasicAttributeTestModel, newScore:Int, character:A):A
}
