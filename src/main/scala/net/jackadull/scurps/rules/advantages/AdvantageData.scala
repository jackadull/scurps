package net.jackadull.scurps.rules.advantages

import scala.language.postfixOps

trait AdvantageData {
  def allAdvantages:Traversable[Advantage[_<:AdvantageVariant]]
  def getPerVariantKeySingleton[A<:AdvantageVariant](group:AdvantageGroup[A]):Set[Advantage[A]]
  def getSingleton[A<:AdvantageVariant](group:AdvantageGroup[A]):Option[Advantage[A]]
  def putPerVariantKeySingleton[A<:AdvantageVariant](advantage:Advantage[A], key:Any):AdvantageData
  def removeAll[A<:AdvantageVariant](group:AdvantageGroup[A]):AdvantageData
  def removePerVariantKeySingletonForKey[A<:AdvantageVariant](group:AdvantageGroup[A], key:Any):AdvantageData
  def setSingleton[A<:AdvantageVariant](advantage:Advantage[A]):AdvantageData
}
object AdvantageData {
  val empty:AdvantageData = DefaultImpl(Map())

  private case class DefaultImpl(
    map:Map[AdvantageGroup[_],AnyRef]
  ) extends AdvantageData {
    def allAdvantages:Traversable[Advantage[_<:AdvantageVariant]] = map.values.view flatMap {
      case adv:Advantage[_] ⇒ Traversable(adv)
      case m:Map[_,_] ⇒ m.values.view.asInstanceOf[Traversable[Advantage[_<:AdvantageVariant]]]
    }

    def getPerVariantKeySingleton[A<:AdvantageVariant](group:AdvantageGroup[A]):Set[Advantage[A]] = map get group match {
      case Some(m:Map[_,_]) ⇒ m.values.toSet.asInstanceOf[Set[Advantage[A]]]
      case _ ⇒ Set()
    }

    def getSingleton[A<:AdvantageVariant](group:AdvantageGroup[A]):Option[Advantage[A]] = map get group match {
      case Some(a:Advantage[_]) ⇒ Some(a).asInstanceOf[Option[Advantage[A]]]
      case _ ⇒ None
    }

    def putPerVariantKeySingleton[A<:AdvantageVariant](advantage:Advantage[A], key:Any):AdvantageData = map get (advantage group) match {
      case Some(m:Map[_,_]) ⇒
        val m2:Map[Any,Advantage[A]] = m.asInstanceOf[Map[Any,Advantage[A]]]
        m2 get key match {
          case Some(`advantage`) ⇒ this
          case _ ⇒ copy(map = map + ((advantage group) → (m2 + (key → advantage))))
        }
      case _ ⇒ copy(map = map + ((advantage group) → Map(key → advantage)))
    }

    def removeAll[A<:AdvantageVariant](group:AdvantageGroup[A]):AdvantageData =
      if(map contains group) copy(map = map - group) else this

    def removePerVariantKeySingletonForKey[A<:AdvantageVariant](group:AdvantageGroup[A], key:Any):AdvantageData = map get group match {
      case Some(m:Map[_,_]) ⇒
        val m2:Map[Any,Advantage[A]] = m.asInstanceOf[Map[Any,Advantage[A]]]
        if(m2 contains key) {
          if(m2.size==1) removeAll(group) else copy(map = map + (group → (m2 - key)))
        } else this
      case _ ⇒ this
    }

    def setSingleton[A<:AdvantageVariant](advantage:Advantage[A]):AdvantageData = map get (advantage group) match {
      case Some(`advantage`) ⇒ this
      case _ ⇒ copy(map = map + ((advantage group) → advantage))
    }
  }
}
