package scurps.meta.rule

import scurps.meta.derivation.Params

import scala.language.existentials

trait RuleCatalog {
  def get[P<:Params,R](key:RuleKey[P,R]):Option[Rule[P,R]]
  def +[P<:Params,R](kv:(RuleKey[P,R],Rule[P,R])):RuleCatalog
  def ++(kvs:(RuleKey[K,V],Rule[K,V]) forSome {type K<:Params;type V}*):RuleCatalog
}
object RuleCatalog {
  val empty:RuleCatalog = MapRuleCatalog(Map.empty)
  def apply(kvs:(RuleKey[K,V],Rule[K,V]) forSome {type K<:Params;type V}*):RuleCatalog = empty.++(kvs:_*)

  private final case class MapRuleCatalog(map:Map[RuleKey[_<:Params,_],Rule[_<:Params,_]]) extends RuleCatalog {
    override def get[P<:Params,R](key:RuleKey[P,R]):Option[Rule[P,R]] = map.get(key).asInstanceOf[Option[Rule[P,R]]]
    override def +[P<:Params,R](kv:(RuleKey[P,R],Rule[P,R])):MapRuleCatalog = map + kv match {
      case unchanged if unchanged eq map => this
      case changedVersion => copy(map = changedVersion)
    }
    override def ++(kvs:(RuleKey[K,V],Rule[K,V]) forSome {type K<:Params;type V}*):MapRuleCatalog =
      kvs.foldLeft(this)(_ + _)
  }
}
