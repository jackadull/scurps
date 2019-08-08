package scurps.meta

import scurps.meta.Rule.RuleTuple

import scala.language.higherKinds

trait RuleCatalog {
  def get[R<:Rule](key:RuleKey[R]):Option[R]
  def +[R<:Rule](kv:(RuleKey[R],R)):RuleCatalog
  def ++(kvs:RuleTuple[_<:Rule]*):RuleCatalog
}
object RuleCatalog {
  val empty:RuleCatalog = MapRuleCatalog(Map.empty)
  def apply(kvs:RuleTuple[_<:Rule]*):RuleCatalog = empty.++(kvs:_*)

  private final case class MapRuleCatalog(map:Map[RuleKey[_<:Rule],Rule]) extends RuleCatalog {
    override def get[R<:Rule](key:RuleKey[R]):Option[R] = map.get(key).map(_.asInstanceOf[R])
    override def +[R<:Rule](kv:(RuleKey[R],R)):RuleCatalog = map + kv match {
      case unchanged if unchanged eq map => this
      case changedVersion => copy(map = changedVersion)
    }
    override def ++(kvs:RuleTuple[_<:Rule]*):RuleCatalog = kvs.foldLeft[RuleCatalog](this)(_ + _)
  }
}
