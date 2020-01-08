package scurps.meta.algebra

import java.util.concurrent.atomic.AtomicInteger

import scurps.bib.BibRef
import scurps.meta.context.{ContextKey, GameContext}
import scurps.meta.data.{PMap, WrapKey}
import scurps.meta.math.{Add, IsZero, Subtract}
import scurps.meta.rule.RuleKey

class DebugScurpsOps[A[_]](base:ScurpsOps[A]) extends ScurpsOps[A] {
  private val indentation = new AtomicInteger(0)
  private val opCount = new AtomicInteger(1)
  private def debug[T](op:String, params:String, v: =>T):T = {
    val opNumber = opCount.getAndIncrement()
    def indent():Unit = 0.until(indentation.get()).foreach(_ => print("  "))
    def indented(str:String):Unit = {indent(); print(str)}
    indented(s"[$opNumber] $op($params)\n")
    indentation.incrementAndGet()
    val result:T = v
    indentation.decrementAndGet()
    indented(s"[$opNumber] $op = $result\n")
    result
  }
  private def debugFmtParams(params:Any):String = s"$params"

  override def accordingTo[T](value:A[T], ref:BibRef):A[T] =
    debug("accordingTo", s"value=$value, ref=$ref", base.accordingTo(value, ref))

  override def added[T](value1:A[T], value2:A[T])(implicit add:Add[T]):A[T] =
    debug("added", s"value1=$value1, value2=$value2", base.added(value1, value2))

  override def applyRuleByKey[P[_[_]],R](key:RuleKey[P,R], params:P[A], context:A[GameContext])(implicit ops:ScurpsOps[A]):A[R] =
    debug("applyRuleByKey", s"key=$key, params=${debugFmtParams(params)}, context=$context", base.applyRuleByKey(key, params, context))

  override def constant[T](value:T):A[T] =
    debug("constant", s"value=$value", base.constant(value))

  override def getFromContext[T](context:A[GameContext], key:ContextKey[T]):A[T] =
    debug("getFromContext", s"context=$context, key=$key", base.getFromContext(context, key))

  override def getFromPMap[K[_],T](pMap:A[PMap[K]], key:A[K[T]]):A[T] =
    debug("getFromPMap", s"pMap=$pMap, key=$key", base.getFromPMap(pMap, key))

  override def ifDefined[T,T2](value:A[T], _then:A[T]=>A[T2]):A[T2] =
    debug("ifDefined", s"value=$value, _then=???", base.ifDefined(value, _then))

  override def ifZero[T, T2](value:A[T], _then: =>A[T2], _else: =>A[T2])(implicit isZero:IsZero[T]):A[T2] =
    debug("ifZero", s"value=$value, _then=???, _else=???", base.ifZero(value, _then, _else))

  override def orElse[T](value:A[T], defaultValue: =>A[T]):A[T] =
    debug("orElse", s"value=$value, defaultValue=???", base.orElse(value, defaultValue))

  override def modInContext[T](context:A[GameContext], key:ContextKey[T], f:A[T]=>A[T]):A[GameContext] =
    debug("modInContext", s"context=$context, key=$key, f=???", base.modInContext(context, key, f))

  override def removedFromPMap[K[_]](pMap:A[PMap[K]], key:A[K[_]]):A[PMap[K]] =
    debug("removedFromPMap", s"pMap=$pMap, key=$key", base.removedFromPMap[K](pMap, key))

  override def subtracted[T](value1:A[T], value2:A[T])(implicit subtract:Subtract[T]):A[T] =
    debug("subracted", s"value1=$value1, value2=$value2", base.subtracted(value1, value2))

  override def updatedInPMap[T,K[_]](pMap:A[PMap[K]], key:A[K[T]], value:A[T]):A[PMap[K]] =
    debug("updatedInPMap", s"pMap=$pMap, key=$key, value=$value", base.updatedInPMap(pMap, key, value))

  override def wrapKey[T,K](value:A[T], wrap:WrapKey[T,K]):A[K] =
    debug("wrapKey", s"value=$value, wrap=$wrap", base.wrapKey(value, wrap))
}
