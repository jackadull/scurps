package net.jackadull.scurps.rules.cp

import net.jackadull.scurps.context.GContext
import net.jackadull.scurps.meta.data.GCharacterData

import scala.language.postfixOps

trait CPSumsTestAccessorImpl extends CPSumsTestAccessor[(GCharacterData,GContext)] {
  def cpSum(character:(GCharacterData,GContext)) = character._2.rules.cpTotalRule(character _1, character _2)
  def initialCharacter = (GCharacterData empty, GContext default)
  def sumOfNegativeCPCosts(character:(GCharacterData, GContext)) = character._2.rules.sumOfNegativeCPCostRule(character _1, character _2)
  def sumOfPositiveCPCosts(character:(GCharacterData, GContext)) = character._2.rules.sumOfPositiveCPCostRule(character _1, character _2)
}
object CPSumsTestAccessorImpl extends CPSumsTestAccessorImpl
