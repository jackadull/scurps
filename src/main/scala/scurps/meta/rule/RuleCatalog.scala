package scurps.meta.rule

import scala.language.existentials

trait RuleCatalog {
  def +[P[_[_]]<:Params,R](kv:(RuleKey[P,R],Rule[P,R])):RuleCatalog
  def ++(kvs:Seq[(RuleKey[P,R],Rule[P,R]) forSome {type P[_[_]]<:Params;type R}]):RuleCatalog
  def get[P[_[_]]<:Params,R](key:RuleKey[P,R]):Option[Rule[P,R]]
  def isEmpty:Boolean
}
object RuleCatalog {
  val empty:RuleCatalog = MapRuleCatalog(Map.empty)
  def apply(kvs:(RuleKey[P,R],Rule[P,R]) forSome {type P[_[_]]<:Params;type R}*):RuleCatalog = empty.++(kvs)

  private final case class MapRuleCatalog(map:Map[Any,Rule[Nothing,Any]]) extends RuleCatalog {
    override def +[P[_[_]]<:Params,R](kv:(RuleKey[P,R],Rule[P,R])):MapRuleCatalog = map + kv match {
      case unchanged if unchanged eq map => this
      case changedVersion => copy(map = changedVersion)
    }
    override def ++(kvs:Seq[(RuleKey[P,R],Rule[P,R]) forSome {type P[_[_]]<:Params;type R}]):MapRuleCatalog =
      kvs.foldLeft(this)(_ + _)
    override def get[P[_[_]]<:Params,R](key:RuleKey[P,R]):Option[Rule[P,R]] =
      map.get(key).asInstanceOf[Option[Rule[P,R]]]
    override def isEmpty:Boolean = map.isEmpty
  }
}
