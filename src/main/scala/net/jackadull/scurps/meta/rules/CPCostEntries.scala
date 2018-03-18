package net.jackadull.scurps.meta.rules

import net.jackadull.scurps.context.GContext
import net.jackadull.scurps.meta.data.GCharacterData

trait CPCostEntries {
  def cpCostEntries(data:GCharacterData, context:GContext):Traversable[Int]
}
