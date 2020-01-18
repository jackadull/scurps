package scurps.meta.algebra

import scurps.bib.BibRef
import scurps.meta.context.{ContextKey, GameContext}
import scurps.meta.data.{PMap, WrapKey}
import scurps.meta.math.ArithmeticOp
import scurps.meta.math.ArithmeticOp.IsZero
import scurps.meta.rule.RuleKey

object OptionScurpsOps extends ScurpsOps[Option] {
  override def accordingTo[T](value:Option[T], ref:BibRef):Option[T] = value

  override def applyRuleByKey[P[_[_]],R](key:RuleKey[P,R], params:P[Option], context:Option[GameContext])(implicit ops:ScurpsOps[Option]):Option[R] =
    for(ctx<-context; rule<-ctx.ruleCatalog.get(key); result<-rule.applyP(params, context)) yield result

  override def arithmetic[T1,R](v:Option[T1], aop:ArithmeticOp.ArithmeticOp1[T1,R]):Option[R] = v.map(aop)

  override def arithmetic[T1, T2, R](lhs:Option[T1], rhs:Option[T2], aop:ArithmeticOp.ArithmeticOp2[T1, T2, R]):Option[R] =
    for(l<-lhs; r<-rhs) yield aop(l, r)

  override def getFromContext[T](context:Option[GameContext], key:ContextKey[T]):Option[T] =
    for(ctx<-context; result<-ctx.get(key)) yield result

  override def getFromPMap[K[_],T](pMap:Option[PMap[K]], key:Option[K[T]]):Option[T] =
    for(m<-pMap; k<-key; result<-m.get(k)) yield result

  override def ifDefined[T,T2](value:Option[T], _then:Option[T]=>Option[T2]):Option[T2] = value match {
    case v@Some(_) => _then(v)
    case _ => None
  }

  override def ifIsOneOf[T,T2](value:Option[T], set:Option[Set[T]], _then:Option[T]=>Option[T2], _else:Option[T]=>Option[T2]):Option[T2] =
    for(v<-value; s<-set; result<-if(s.contains(v)) _then(value) else _else(value)) yield result

  override def ifZero[T,T2](value:Option[T], _then: =>Option[T2], _else: =>Option[T2])(implicit isZero:IsZero[T]):Option[T2] =
    value match {
      case Some(v) if isZero(v) => _then
      case Some(_) => _else
      case None => None
    }

  override def orElse[T](value:Option[T], defaultValue: =>Option[T]):Option[T] = value.orElse(defaultValue)

  override def pure[T](value:T):Some[T] = Some(value)

  override def modInContext[T](context:Option[GameContext], key:ContextKey[T], f:Option[T]=>Option[T]):Option[GameContext] =
    for(ctx<-context; current<-ctx.get(key); newValue<-f(Some(current))) yield ctx.updated(key, newValue)

  override def removedFromPMap[K[_]](pMap:Option[PMap[K]], key:Option[K[_]]):Option[PMap[K]] =
    for(m<-pMap; k<-key) yield m.removed(k)

  override def updatedInPMap[T,K[_]](pMap:Option[PMap[K]], key:Option[K[T]], value:Option[T]):Option[PMap[K]] =
    for(m<-pMap; k<-key; v<-value) yield m.updated(k, v)

  override def wrapKey[T,K](value:Option[T], wrap:WrapKey[T,K]):Option[K] = value.map(wrap)
}
