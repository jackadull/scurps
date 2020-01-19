package scurps.meta.data

import scurps.meta.data.PMap.MapBasedPMap
import scurps.meta.data.ShowKey.ShowSingletonKey
import scurps.meta.rule.RuleCatalog

trait GameContext extends PMap[GameContextProperty] with PMapLike[GameContextProperty,GameContext] {
  def ruleCatalog:RuleCatalog
  def withRuleCatalog(newRuleCatalog:RuleCatalog):GameContext
}
object GameContext {
  val basicSet:GameContext = Impl(Map.empty, scurps.basicSetRules)
  val empty:GameContext = Impl(Map.empty, RuleCatalog.empty)

  object Subject extends GameContextProperty[GCharacter] with ShowSingletonKey

  private final case class Impl(baseMap:Map[GameContextProperty[_],Any], ruleCatalog:RuleCatalog)
  extends GameContext with MapBasedPMap[GameContextProperty,Impl] {
    override def isEmpty:Boolean = super.isEmpty && ruleCatalog.isEmpty
    override def toString:String = s"GameContext(entries=$baseMap, ruleCatalog=$ruleCatalog)"
    override protected def withBaseMap(newBaseMap:Map[GameContextProperty[_],Any]):Impl = copy(baseMap = newBaseMap)
    override def withRuleCatalog(newRuleCatalog:RuleCatalog):GameContext = copy(ruleCatalog = newRuleCatalog)
  }
}
