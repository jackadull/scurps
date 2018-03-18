package net.jackadull.scurps.rules.advantages

import scala.language.postfixOps

trait AdvantageRules {
  def all:Set[AdvantageRule[_<:AdvantageVariant]]
  def get[A<:AdvantageVariant](group:AdvantageGroup[A]):Option[AdvantageRule[A]]
  def put[A<:AdvantageVariant](rule:AdvantageRule[A]):AdvantageRules

  def putAll(rules:AdvantageRule[_]*):AdvantageRules = rules.foldLeft(this) {(rs,rule)⇒
    rs put rule.asInstanceOf[AdvantageRule[_<:AdvantageVariant]]
  }
}
object AdvantageRules {
  val default:AdvantageRules = empty.putAll(implementations.all:_*)

  private lazy val empty:AdvantageRules = DefaultImpl(Map())

  private case class DefaultImpl(map:Map[AdvantageGroup[_],AdvantageRule[_<:AdvantageVariant]]) extends AdvantageRules {
    lazy val all:Set[AdvantageRule[_<:AdvantageVariant]] = map.values.toSet
    def get[A<:AdvantageVariant](group:AdvantageGroup[A]):Option[AdvantageRule[A]] =
      (map get group).asInstanceOf[Option[AdvantageRule[A]]]
    def put[A<:AdvantageVariant](rule:AdvantageRule[A]):AdvantageRules = map get (rule group) match {
      case Some(`rule`) ⇒ this
      case _ ⇒ copy(map = map + ((rule group) → rule))
    }
  }
}
