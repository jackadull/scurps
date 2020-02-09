import scurps.meta.algebra.Arithmetic.{Addition, ArithmeticCombinator2, Subtraction}
import scurps.meta.algebra._
import scurps.meta.rule.{Rule, RuleCatalog}
import scurps.meta.unit.CPImplicits

package object scurps extends CPImplicits with CollectionImplicits with OpticImplicits with ScurpsOpsImplicits {
  val basicSetRules:RuleCatalog = basic_set.basicSetRules

  implicit final class ArithmeticOperators[V](private val v:V) {
    @inline def :+[AT>:V,T](v2:AT)(implicit ev:TypeContains[AT,T], combinator:ArithmeticCombinator2[AT,AT,AT,T,T,T], operation:Addition[T]):AT = // TODO check if we can leave out ev (and TypeContains)
      combinator.combine(operation, v, v2)
    @inline def :-[AT>:V,T](v2:AT)(implicit ev:TypeContains[AT,T], combinator:ArithmeticCombinator2[AT,AT,AT,T,T,T], operation:Subtraction[T]):AT =
      combinator.combine(operation, v, v2)
  }

  implicit def algebraicTypeContainsItsValue[A[+_],T](implicit alg:ScurpsOps[A]):TypeContains[A[T],T] = new TypeContains[A[T],T] {} // TODO toString, singleton
  implicit def ruleContainsItsResult[P[_[_]],R]:TypeContains[Rule[P,R],R] = new TypeContains[Rule[P,R],R] {} // TODO toString, singleton
}
