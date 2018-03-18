package net.jackadull.scurps.rules.advantages

import scala.language.postfixOps

sealed trait AdvantageStorageModel[A<:AdvantageVariant] {
  def +(advantage:Advantage[A]):AdvantageData⇒AdvantageData
  def -(advantage:Advantage[A]):AdvantageData⇒AdvantageData
  def all(advantageData:AdvantageData):Set[Advantage[A]]
  def headOption(advantageData:AdvantageData):Option[Advantage[A]]
  def removeAll:AdvantageData⇒AdvantageData
}
object AdvantageStorageModel {
  abstract class PerKeySingletonAdvantage[A<:AdvantageVariant,B](group:AdvantageGroup[A]) extends AdvantageStorageModel[A] {
    def keyOf(advantage:Advantage[A]):B
    def +(advantage:Advantage[A]):AdvantageData⇒AdvantageData =
      {_ putPerVariantKeySingleton (advantage, keyOf(advantage))}
    def -(advantage:Advantage[A]):AdvantageData⇒AdvantageData =
      {_ removePerVariantKeySingletonForKey (advantage group, keyOf(advantage))}
    def all(advantageData:AdvantageData):Set[Advantage[A]] = advantageData getPerVariantKeySingleton group
    def headOption(advantageData:AdvantageData):Option[Advantage[A]] = all(advantageData) headOption
    def removeAll:AdvantageData⇒AdvantageData = {_ removeAll group}
  }

  final case class SingletonAdvantage[A<:AdvantageVariant](group:AdvantageGroup[A]) extends AdvantageStorageModel[A] {
    def +(advantage:Advantage[A]):AdvantageData⇒AdvantageData = {_ setSingleton advantage}
    def -(advantage:Advantage[A]):AdvantageData⇒AdvantageData = {advantageData⇒
      advantageData getSingleton (advantage group) match {
        case Some(`advantage`) ⇒ advantageData removeAll (advantage group)
        case _ ⇒ advantageData
      }
    }
    def all(advantageData:AdvantageData):Set[Advantage[A]] = (advantageData getSingleton group) toSet
    def headOption(advantageData:AdvantageData):Option[Advantage[A]] = advantageData getSingleton group
    def removeAll:AdvantageData⇒AdvantageData = {_ removeAll group}
  }
}
