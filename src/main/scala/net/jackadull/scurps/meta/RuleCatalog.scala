package net.jackadull.scurps.meta

import scala.language.higherKinds

trait RuleCatalog {
  def get[R<:Rule](key:RuleKey[R]):Option[R]

  def +[R<:Rule](kv:(RuleKey[R],R)):RuleCatalog
}
