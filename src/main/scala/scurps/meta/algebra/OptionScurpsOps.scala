package scurps.meta.algebra
import scurps.bib.BibRef
import scurps.meta.context.{ContextKey, GameContext}
import scurps.meta.data.{PMap, WrapKey}
import scurps.meta.math.{Add, IsZero, Subtract}
import scurps.meta.rule.RuleKey

object OptionScurpsOps extends ScurpsOps[Option] {
  override def accordingTo[T](value:Option[T], ref:BibRef):Option[T] = value

  override def added[T](value1:Option[T], value2:Option[T])(implicit add:Add[T]):Option[T] =
    for(v1<-value1; v2<-value2) yield add(v1, v2)

  override def applyRuleByKey[P[_[_]],R](key:RuleKey[P,R], params:P[Option], context:Option[GameContext])(implicit ops:ScurpsOps[Option]):Option[R] =
    for(ctx<-context; rule<-ctx.ruleCatalog.get(key); result<-rule(params, context)) yield result

  override def constant[T](value:T):Some[T] = Some(value)

  override def getFromContext[T](context:Option[GameContext], key:ContextKey[T]):Option[T] =
    for(ctx<-context; result<-ctx.get(key)) yield result

  override def getFromPMap[K[_],T](pMap:Option[PMap[K]], key:Option[K[T]]):Option[T] =
    for(m<-pMap; k<-key; result<-m.get(k)) yield result

  override def ifDefined[T,T2](value:Option[T], _then:Option[T]=>Option[T2]):Option[T2] = value match {
    case v@Some(_) => _then(v)
    case _ => None
  }

  override def ifZero[T,T2](value:Option[T], _then: =>Option[T2], _else: =>Option[T2])(implicit isZero:IsZero[T]):Option[T2] =
    value match {
      case Some(v) if isZero(v) => _then
      case Some(_) => _else
      case None => None
    }

  override def orElse[T](value:Option[T], defaultValue: =>Option[T]):Option[T] = value.orElse(defaultValue)

  override def modInContext[T](context:Option[GameContext], key:ContextKey[T], f:Option[T]=>Option[T]):Option[GameContext] =
    for(ctx<-context; current<-ctx.get(key); newValue<-f(Some(current))) yield ctx.updated(key, newValue)

  override def removedFromPMap[K[_]](pMap:Option[PMap[K]], key:Option[K[_]]):Option[PMap[K]] =
    for(m<-pMap; k<-key) yield m.removed(k)

  override def subtracted[T](value1:Option[T], value2:Option[T])(implicit subtract:Subtract[T]):Option[T] =
    for(v1<-value1; v2<-value2) yield subtract(v1, v2)

  override def updatedInPMap[T,K[_]](pMap:Option[PMap[K]], key:Option[K[T]], value:Option[T]):Option[PMap[K]] =
    for(m<-pMap; k<-key; v<-value) yield m.updated(k, v)

  override def wrapKey[T,K](value:Option[T], wrap:WrapKey[T,K]):Option[K] = value.map(wrap)
}
