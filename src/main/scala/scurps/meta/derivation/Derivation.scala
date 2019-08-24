package scurps.meta.derivation

import scurps.meta.Concept
import scurps.meta.context.RuleContext
import scurps.meta.derivation.Derivation.{Added, Constant, ForAny, RuleAppliedByKey}
import scurps.meta.math.Add
import scurps.meta.rule.{Rule, RuleCatalog, RuleKey}

trait Derivation[+A] {
  def accessRuleCatalogInRuleContext(implicit ev:this.type=>Derivation[RuleContext]):Derivation[RuleCatalog] =
    ev(this).valueE match {
      case Right(ctx) => create.constant(ctx.ruleCatalog)
      case Left(undefined) => undefined
    }
  def add[A2>:A](that:Derivation[A2])(implicit add:Add[A2]):Derivation[A2] = valueE match {
    case Right(rVal) => that.valueE match {
      case Right(lVal) => create.added(add(rVal, lVal), this, that)
      case Left(undefined) => undefined
    }
    case Left(undefined) => undefined
  }
  def applyRule[P<:Params,R](params:P, context:Derivation[RuleContext])(implicit ev:this.type=>Derivation[Rule[P,R]]):Derivation[R] =
    ev(this).valueE match {
      case Right(rule) => rule(params, context)
      case Left(undefined) => undefined
    }
  def lookupRule[P<:Params,R](key:RuleKey[P,R])(implicit ev:this.type=>Derivation[RuleCatalog]):Derivation[Rule[P,R]] =
    ev(this).valueE match {
      case Right(rule) => ??? // TODO denote lookup of rule definition
      case Left(undefined) => ??? // TODO MissingRule(key, BibRef.Empty) -- should `undefined` be preserved? (if not, `use` valueOpt instead)
    }
  def ruleContextFor[P<:Params,R](key:RuleKey[P,R])(implicit ev:this.type=>Derivation[RuleContext]):Derivation[RuleContext] =
    ev(this).valueE match {
      case Right(context) => create.constant(context.ruleContextForCalling(key))
      case Left(undefined) => undefined
    }

  def valueE:Either[Derivation[Nothing],A]

  val create:DerivationFactory = new DerivationFactory {
    override def added[A2](value:A2, lhs:Derivation[A2], rhs:Derivation[A2]):Derivation[A2] = Added(value, lhs, rhs)
    override def constant[A2](value:A2):Derivation[A2] = Constant(value)
    override def forAny[A2,C](inner:Derivation[A2],concept:Concept[C]):Derivation[A2] = ForAny(inner, concept)
    override def ruleAppliedByKey[P<:Params,R](ruleKey:RuleKey[P,R], value:Derivation[R]):Derivation[R] = RuleAppliedByKey(ruleKey, value)
  }
}
object Derivation {
  final case class Added[+A] private(value:A, lhs:Derivation[A], rhs:Derivation[A]) extends Derivation[A] {
    override def valueE:Either[Derivation[Nothing],A] = Right(value)
  }
  final case class Constant[+A] private(value:A) extends Derivation[A] {
    override def valueE:Either[Derivation[Nothing],A] = Right(value)
  }
  final case class ForAny[+A,C] private(inner:Derivation[A], concept:Concept[C]) extends Derivation[A] {
    override def valueE:Either[Derivation[Nothing],A] = inner.valueE
  }
  final case class RuleAppliedByKey[-P<:Params,+R] private(ruleKey:RuleKey[P,R], value:Derivation[R]) extends Derivation[R] {
    override def valueE:Either[Derivation[Nothing],R] = value.valueE
  }
}
