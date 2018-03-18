package net.jackadull.scurps.rules.advantages

import net.jackadull.scurps.meta.data.GCharacterData.Key
import net.jackadull.scurps.meta.rules.PropertyGetSet

import scala.language.postfixOps

private[rules] object AdvantageDataRule {
  private val key:Key[AdvantageData] = new Key[AdvantageData] {}
  val default:PropertyGetSet[AdvantageData] = PropertyGetSet ofKey (key, AdvantageData empty)
}
