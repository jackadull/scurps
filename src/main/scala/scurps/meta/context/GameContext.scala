package scurps.meta.context

import scurps.meta.data.PMap.MapBasedPMap
import scurps.meta.data.{PMap, PMapLike}
import scurps.meta.rule.RuleCatalog

trait GameContext extends PMap[ContextKey] with PMapLike[ContextKey,GameContext] {
  def ruleCatalog:RuleCatalog
}
object GameContext {
  val empty:GameContext = Impl(Map.empty, RuleCatalog.empty)

  private final case class Impl(baseMap:Map[ContextKey[_],Any], ruleCatalog:RuleCatalog)
  extends GameContext with MapBasedPMap[ContextKey,Impl] {
    override def isEmpty:Boolean = super.isEmpty && ruleCatalog.isEmpty
    override protected def withBaseMap(newBaseMap:Map[ContextKey[_],Any]):Impl = copy(baseMap = newBaseMap)
    // TODO toString
  }
}
