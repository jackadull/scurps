package scurps.meta

import scurps.bib.BibRef
import scurps.meta.Derivation._
import scurps.meta.RuleKey.RuleKey1
import scurps.meta.math.Add

import scala.language.higherKinds

// TODO seal all sub-traits
// TODO make case class constructors private
sealed trait Rule {
  def accordingTo(bibRef:BibRef):Rule
  def bibRef:BibRef
}
object Rule {
  type RuleTuple[R<:Rule] = (RuleKey[R],R) // TODO lambda and remove?

  trait Rule0[+R] extends Rule {
    def apply()(implicit context:GameContext):Derivation[R]
    protected def withBibRef(bibRef:BibRef):Rule0[R]

    override def accordingTo(bibRef:BibRef):Rule0[R] = withBibRef(this.bibRef + bibRef)
    def forAny[T1](implicit concept:Concept[T1]):Rule1[T1,R] = ForAny(this)
    def orDefault[R2>:R](default:R2):Rule0[R2] = OrDefault0(this, default)
  }
  trait Rule1[-T1,+R] extends Rule {
    def apply(v1:T1)(implicit context:GameContext):Derivation[R]
    protected def withBibRef(bibRef:BibRef):Rule1[T1,R]

    def :+[T1_2<:T1,R2>:R](rhs:Rule1[T1_2,R2])(implicit add:Add[R2]):Rule1[T1_2,R2] = Add1(this, rhs)
    override def accordingTo(bibRef:BibRef):Rule1[T1,R] = withBibRef(this.bibRef + bibRef)
    def orDefault[R2>:R](default:R2):Rule1[T1,R2] = OrDefault1(this, default)
  }
  trait Rule2[-T1,-T2,+R] extends Rule {
    def apply(v1:T1, v2:T2)(implicit context:GameContext):Derivation[R]
    protected def withBibRef(bibRef:BibRef):Rule2[T1,T2,R]

    override def accordingTo(bibRef:BibRef):Rule2[T1,T2,R] = withBibRef(this.bibRef + bibRef)
  }

  final case class Add1[-T1,+R](lhs:Rule1[T1,R], rhs:Rule1[T1,R], bibRef:BibRef=BibRef.Empty)(implicit add:Add[R]) extends Rule1[T1,R] {
    override def apply(v1:T1)(implicit context:GameContext):Derivation[R] = {
      val lDerivation = lhs(v1)
      lDerivation.valueE match {
        case Right(lVal) =>
          val rDerivation = rhs(v1)
          rDerivation.valueE match {
            case Right(rVal) => Added(add(lVal, rVal), lDerivation, rDerivation, bibRef)
            case Left(undefined) => undefined
          }
        case Left(undefined) => undefined
      }
    }
    override protected def withBibRef(bibRef:BibRef):Rule1[T1,R] = copy(bibRef = bibRef)
  }
  final case class ContextGet[R](key:ContextKey[R], bibRef:BibRef=BibRef.Empty) extends Rule0[R] {
    override def apply()(implicit context:GameContext):Derivation[R] = context.get(key) match {
      case Some(value) => ContextValue(key, value, bibRef)
      case None => MissingContextValue(key, bibRef)
    }
    override protected def withBibRef(bibRef:BibRef):Rule0[R] = copy(bibRef = bibRef)
  }
  final case class DefineAsConstant[+R](constantValue:R, bibRef:BibRef=BibRef.Empty) extends Rule0[R] {
    private val constant = DefinedAsConstant(constantValue, bibRef)
    override def apply()(implicit context:GameContext):Derivation[R] = constant
    override protected def withBibRef(bibRef:BibRef):Rule0[R] = copy(bibRef = bibRef)
  }
  final case class Eval1[-T1,+R](key:RuleKey1[T1,R], bibRef:BibRef=BibRef.Empty) extends Rule1[T1,R] {
    override def apply(v1:T1)(implicit context:GameContext):Derivation[R] = key.derive(v1)
    override protected def withBibRef(bibRef:BibRef):Rule1[T1,R] = copy(bibRef = bibRef)
  }
  final case class ForAny[-T1,+R](inner:Rule0[R], bibRef:BibRef=BibRef.Empty)(implicit concept:Concept[T1]) extends Rule1[T1,R] {
    override def apply(v1:T1)(implicit context:GameContext):Derivation[R] = Derivation.ForAny(inner(), concept, bibRef)
    override protected def withBibRef(bibRef:BibRef):Rule1[T1,R] = copy(bibRef = bibRef)
  }
  final case class OrDefault0[+R](inner:Rule0[R], default:R, bibRef:BibRef=BibRef.Empty) extends Rule0[R] {
    override def apply()(implicit context:GameContext):Derivation[R] = {
      val innerDerivation = inner()
      innerDerivation.valueE match {
        case Right(_) => innerDerivation
        case Left(undefined) => DefaultValue(default, undefined, bibRef)
      }
    }
    override protected def withBibRef(bibRef:BibRef):Rule0[R] = copy(bibRef = bibRef)
  }
  final case class OrDefault1[-T1,+R](inner:Rule1[T1,R], default:R, bibRef:BibRef = BibRef.Empty) extends Rule1[T1,R] {
    override def apply(v1:T1)(implicit context:GameContext):Derivation[R] = {
      val innerDerivation = inner(v1)
      innerDerivation.valueE match  {
        case Right(_) => innerDerivation
        case Left(undefined) => DefaultValue(default, undefined, bibRef)
      }
    }
    override protected def withBibRef(bibRef:BibRef):Rule1[T1, R] = copy(bibRef = bibRef)
  }
  final case class PGet[R,K[_]](key:K[R], inner:Rule0[PMap[K]], bibRef:BibRef=BibRef.Empty) extends Rule0[R] {
    override def apply()(implicit context:GameContext):Derivation[R] = {
      val derivedPMap = inner()
      derivedPMap.valueE match {
        case Right(pMap) => pMap.get(key) match {
          case Some(value) => PValue(value, derivedPMap, key, bibRef)
          case None => MissingPValue(derivedPMap, key, bibRef)
        }
        case Left(undefined) => undefined
      }
    }
    override protected def withBibRef(bibRef:BibRef):Rule0[R] = copy(bibRef = bibRef)
  }
  final case class PGetMapped[R,-A,K[_]](keyMapping:KeyMapping[A,K,R], inner:Rule0[PMap[K]], bibRef:BibRef=BibRef.Empty) extends Rule1[A,R] {
    override def apply(a:A)(implicit context:GameContext):Derivation[R] = {
      val key = keyMapping.mapValueKey(a)
      val derivedPMap = inner()
      derivedPMap.valueE match {
        case Right(pMap) => pMap.get(key) match {
          case Some(value) => PValue(value, derivedPMap, key, bibRef)
          case None => MissingPValue(derivedPMap, key, bibRef)
        }
        case Left(undefined) => undefined
      }
    }
    override protected def withBibRef(bibRef:BibRef):Rule1[A, R] = copy(bibRef = bibRef)
  }
}
