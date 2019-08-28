package scurps.meta.math

import scurps.meta.derivation.Params
import scurps.meta.rule.Rule

trait Add[A] extends ((A,A)=>A)
object Add {
  implicit def addRules[P<:Params,R](implicit addR:Add[R]):Add[Rule[P,R]] = AddRules()

  private final case class AddRules[P<:Params,R]()(implicit val addR:Add[R]) extends Add[Rule[P,R]] {
    override def apply(lhs:Rule[P,R], rhs:Rule[P,R]):Rule[P,R] = lhs :+ rhs
  }
}
