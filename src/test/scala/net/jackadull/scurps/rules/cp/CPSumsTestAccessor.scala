package net.jackadull.scurps.rules.cp

trait CPSumsTestAccessor[A] {
  def cpSum(character:A):Int
  def initialCharacter:A
  def sumOfNegativeCPCosts(character:A):Int
  def sumOfPositiveCPCosts(character:A):Int
}
