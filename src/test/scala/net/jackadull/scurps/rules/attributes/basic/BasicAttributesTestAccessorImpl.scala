package net.jackadull.scurps.rules.attributes.basic

import net.jackadull.scurps.context.GContext
import net.jackadull.scurps.meta.data.GCharacterData
import net.jackadull.scurps.rules.attributes.basic.BasicAttributeTestModel._

import scala.language.postfixOps

trait BasicAttributesTestAccessorImpl extends BasicAttributesTestAccessor[(GCharacterData,GContext)] {
  private def selectBARule(attribute:BasicAttributeTestModel, context:GContext) = attribute match {
    case DexterityTestModel ⇒ context.rules.dexterityRule
    case HealthTestModel ⇒ context.rules.healthRule
    case IntelligenceTestModel ⇒ context.rules.intelligenceRule
    case StrengthTestModel ⇒ context.rules.strengthRule
    case _ ⇒ sys error s"unrecognized basic attribute $attribute"
  }

  def basicAttributeLevelsBought(attribute:BasicAttributeTestModel, character:(GCharacterData,GContext)):Int =
    selectBARule(attribute, character _2).pointsBought(character _1, character _2)

  def basicAttributeScore(attribute:BasicAttributeTestModel, character:(GCharacterData,GContext)):Int =
    selectBARule(attribute, character _2).score(character _1, character _2)

  def cpCostForBasicAttribute(attribute:BasicAttributeTestModel, character:(GCharacterData,GContext)):Int =
    selectBARule(attribute, character _2).cpCostForScore(character _1, character _2)

  val initialCharacter:(GCharacterData,GContext) = (GCharacterData empty, GContext default)

  def setBasicAttributeScore(attribute:BasicAttributeTestModel, newScore:Int, character:(GCharacterData,GContext)) =
    (selectBARule(attribute, character _2).score.set(newScore, character _1, character _2), character _2)

  def setBasicAttributeLevelsBought(attribute:BasicAttributeTestModel, newLevelsBought:Int, character:(GCharacterData,GContext)):(GCharacterData,GContext) =
    (selectBARule(attribute, character _2).pointsBought.set(newLevelsBought, character _1, character _2), character _2)
}
object BasicAttributesTestAccessorImpl extends BasicAttributesTestAccessorImpl
