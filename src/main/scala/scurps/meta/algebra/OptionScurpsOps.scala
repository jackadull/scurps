package scurps.meta.algebra

import scurps.bib.BibRef
import scurps.meta.algebra.Arithmetic.IsZero
import scurps.meta.algebra.Optic._
import scurps.meta.data.GameContext
import scurps.meta.rule.{Rule, RuleKey}
import scurps.meta.semantics.ElementSemantics

import scala.collection.IterableOnceOps

object OptionScurpsOps extends ScurpsOps[Option] {
  override def accordingTo[T](value:Option[T], ref:BibRef):Option[T] = value

  override def applyRule[P[_[_]],R](rule:Option[Rule[P,R]], params:P[Option], context:Option[GameContext])(implicit ops:ScurpsOps[Option]):Option[R] =
    rule.flatMap(_.applyP(params, context))

  override def applyRuleByKey[P[_[_]],R](key:RuleKey[P,R], params:P[Option], context:Option[GameContext])(implicit ops:ScurpsOps[Option]):Option[R] =
    for(ctx<-context; rule<-ctx.ruleCatalog.get(key); result<-rule.applyP(params, context)) yield result

  override def arithmetic[T1,R](v:Option[T1], aop:Arithmetic.ArithmeticOp1[T1,R]):Option[R] = v.map(aop)

  override def arithmetic[T1, T2, R](lhs:Option[T1], rhs:Option[T2], aop:Arithmetic.ArithmeticOp2[T1, T2, R]):Option[R] =
    for(l<-lhs; r<-rhs) yield aop(l, r)

  override def fold[T,F<:Accumulator[T,F]](iterable:Option[Iterable[T]], f:Option[F]):Option[F] =
    for(i<-iterable; ff<-f) yield i.foldLeft(ff) {(acc, value) => acc.accumulate(value)}

  override def ifDefined[T,T2](value:Option[T], _then: =>Option[T2]):Option[T2] = value match {
    case Some(_) => _then
    case _ => None
  }

  override def ifIsElement[C[_],T,T2](collection:Option[C[T]], element:Option[T], _then: =>Option[T2], _else: =>Option[T2], elementSemantics:ElementSemantics[C]):Option[T2] =
    for(c<-collection; e<-element; result<-if(elementSemantics.isElement(c, e)) _then else _else) yield result

  override def ifZero[T,T2](value:Option[T], _then: =>Option[T2], _else: =>Option[T2])(implicit isZero:IsZero[T]):Option[T2] =
    value match {
      case Some(v) if isZero(v) => _then
      case Some(_) => _else
      case None => None
    }

  override def map[T,T2,CC[_],C](iterable:Option[IterableOnceOps[T,CC,C]], f:Option[T]=>Option[T2]):Option[CC[T2]] =
    iterable.map(i => i.flatMap(v => f(Some(v))))

  override def orElse[T](value:Option[T], defaultValue: =>Option[T]):Option[T] = value.orElse(defaultValue)

  override def pure[T](value:T):Some[T] = Some(value)

  override def opticMod[S,T](source:Option[S], optic:Option[OptionLens[S,T]], f:Option[T]=>Option[T]):Option[S] =
    for(s<-source; o<-optic; v<-o.getOption(s); n<-f(Some(v))) yield o.set(s, n)

  override def opticOptionGet[S,T](source:Option[S], optic:Option[OptionGetter[S,T]]):Option[T] =
    for(s<-source; o<-optic; result<-o.getOption(s)) yield result

  override def opticSet[S,T](source:Option[S], optic:Option[Setter[S,T]], newValue:Option[T]):Option[S] =
    for(s<-source; o<-optic; n<-newValue) yield o.set(s, n)

  override def opticUnset[S](source:Option[S], optic:Option[Unsetter[S]]):Option[S] =
    for(s<-source; o<-optic) yield o.unset(s)
}
