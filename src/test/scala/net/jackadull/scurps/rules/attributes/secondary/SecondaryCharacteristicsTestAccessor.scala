package net.jackadull.scurps.rules.attributes.secondary

import net.jackadull.scurps.rules.attributes.basic.BasicAttributesTestAccessor
import net.jackadull.scurps.rules.attributes.secondary.SecondaryCharacteristicTestModel.AdjustableSecondaryCharacteristicTestModel

trait SecondaryCharacteristicsTestAccessor[A] extends BasicAttributesTestAccessor[A] {
  def cpCostForSecondaryCharacteristic(sc:AdjustableSecondaryCharacteristicTestModel[_], character:A):Int
  def secondaryCharacteristicLevelsBought(sc:AdjustableSecondaryCharacteristicTestModel[_], character:A):Int
  def secondaryCharacteristicScore[B](sc:SecondaryCharacteristicTestModel[B], character:A):B
  def setSecondaryCharacteristicLevelsBought(sc:AdjustableSecondaryCharacteristicTestModel[_], newLevelsBought:Int, character:A):A
}
