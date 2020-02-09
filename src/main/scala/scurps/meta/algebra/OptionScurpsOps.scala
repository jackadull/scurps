package scurps.meta.algebra

import scurps.bib.BibRef
import scurps.meta.algebra.ArithmeticOperation.IsZero
import scurps.meta.algebra.Optic._
import scurps.meta.algebra.Collection.{Accumulate, Cons, IsElement, Uncons}
import scurps.meta.data.GameContext
import scurps.meta.rule.{Rule, RuleKey}

import scala.annotation.tailrec

object OptionScurpsOps extends ScurpsOps[Option] {
  override def accordingTo[T](value:Option[T], ref:BibRef):Option[T] = value

  @tailrec override final def accumulate[C[_],T,F](collection:Option[C[T]], f:Option[F])(implicit uncons:Uncons[C], accumulate:Accumulate[F,T]):Option[F] =
    collection match {
      case None => None
      case Some(c) => uncons.uncons(c) match {
        case None => f
        case Some((head, tail)) => f match {
          case None => None
          case Some(acc) => this.accumulate(Some(tail), Some(accumulate.accumulate(acc, head)))
        }
      }
    }

  override def applyRule[P[_[_]],R](rule:Option[Rule[P,R]], params:P[Option], context:Option[GameContext])(implicit ops:ScurpsOps[Option]):Option[R] =
    rule.flatMap(_.applyP(params, context))

  override def applyRuleByKey[P[_[_]],R](key:RuleKey[P,R], params:P[Option], context:Option[GameContext])(implicit ops:ScurpsOps[Option]):Option[R] =
    for(ctx<-context; rule<-ctx.ruleCatalog.get(key); result<-rule.applyP(params, context)) yield result

  override def arithmetic[T1,R](v:Option[T1], aop:ArithmeticOperation.ArithmeticOperation1[T1,R]):Option[R] = v.map(aop)

  override def arithmetic[T1, T2, R](lhs:Option[T1], rhs:Option[T2], aop:ArithmeticOperation.ArithmeticOperation2[T1, T2, R]):Option[R] =
    for(l<-lhs; r<-rhs) yield aop(l, r)

  override def arithmeticOperation[P[_[_]],T1,R](operation:ArithmeticOperation.ArithmeticOperation1[T1,R], v1:Option[T1]):Option[R] =
    v1.map(operation)

  override def arithmeticOperation[P[_[_]],T1,T2,R](operation:ArithmeticOperation.ArithmeticOperation2[T1,T2,R], v1:Option[T1], v2:Option[T2]):Option[R] =
    for(a1<-v1; a2<-v2) yield operation(a1, a2)

  override def ifDefined[T,T2](value:Option[T], _then: =>Option[T2]):Option[T2] = value match {
    case Some(_) => _then
    case _ => None
  }

  override def ifIsElement[C[_],T,T2](collection:Option[C[T]], element:Option[T], _then: =>Option[T2], _else: =>Option[T2], isElement:IsElement[C]):Option[T2] =
    for(c<-collection; e<-element; result<-if(isElement.isElement(c, e)) _then else _else) yield result

  override def ifZero[T,T2](value:Option[T], _then: =>Option[T2], _else: =>Option[T2])(implicit isZero:IsZero[T]):Option[T2] =
    value match {
      case Some(v) if isZero(v) => _then
      case Some(_) => _else
      case None => None
    }

  // TODO will this be reversed?
  override def map[T,T2,C[_]](collection:Option[C[T]], f:Option[T]=>Option[T2], cons:Cons[C], uncons:Uncons[C]):Option[C[T2]] = {
    @tailrec def recurse(remaining:C[T], soFar:C[T2]):Option[C[T2]] = uncons.uncons(remaining) match {
      case None => Some(soFar)
      case Some((head, tail)) => f(Some(head)) match {
        case None => None
        case Some(head2) => recurse(tail, cons.cons(head2, soFar))
      }
    }
    collection.flatMap(c => recurse(c, cons.empty))
  }

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
