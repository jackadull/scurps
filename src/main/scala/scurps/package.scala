import scurps.basic_set.attributes.basic.BasicAttribute
import scurps.meta.algebra.Arithmetic.{Addition, ArithmeticCombinator2, Subtraction}
import scurps.meta.algebra._
import scurps.meta.rule.Rule.Rule1
import scurps.meta.rule.{Rule, RuleCatalog}
import scurps.meta.unit.CPImplicits
import scurps.meta.unit.Score.IntScore

package object scurps extends CPImplicits with CollectionImplicits with OpticImplicits with ScurpsOpsImplicits {
  val basicSetRules:RuleCatalog = basic_set.basicSetRules

  implicit final class ArithmeticOperators[V](private val v:V) {
    // TODO simplify arithmetic combinator to [A[+_],T1,T2,R], or better to [A[+_]] with two methods; T1,T2,R
    @inline def :+[AT>:V,T](v2:AT)(implicit combinator:ArithmeticCombinator2[AT,AT,AT,T,T,T], operation:Addition[T]):AT = // TODO check if we can leave out ev (and TypeContains)
      combinator.combine(operation, v, v2)
    @inline def :-[AT>:V,T](v2:AT)(implicit combinator:ArithmeticCombinator2[AT,AT,AT,T,T,T], operation:Subtraction[T]):AT =
      combinator.combine(operation, v, v2)
  }

  implicit def algebraicTypeContainsItsValue[A[+_],T](implicit alg:ScurpsOps[A]):TypeContains[A[T],T] = new TypeContains[A[T],T] {} // TODO toString, singleton
  implicit def ruleContainsItsResult[P[_[_]],R]:TypeContains[Rule[P,R],R] = new TypeContains[Rule[P,R],R] {} // TODO toString, singleton

  // TODO remove below
  val r1:Rule1[BasicAttribute,IntScore] = ???
  val r2:Rule1[BasicAttribute,IntScore] = ???
  val r3 = r1 :+ r2
}
