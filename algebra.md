# Rules algebra ideas

* Instead of methods on `Deviation`, define operations in an algebra.
* Rule implementations use a given algebra.
* An algebra implementation may also create an explaination instead of calculating the result.
* This should render the `Deviation` type useless, so it can be removed.
* What would be a good DSL for writing rules easily?
  It would be too bad if everything would have to be called on the algebra instance explicitly.

```scala
trait RuleAlgebra[V[_]] {
  def add[A](v1:V[A], v2:V[A])(implicit add:Add[A]):V[A]
  def applyRule[P<:Params,R](key:RuleKey[P,R], params:P, context:V[RuleContext]):V[R]
  def constant[A](value:A):V[A]
}

trait Add[A]
trait Params
trait RuleContext
trait RuleKey[P,R]

class Test[V[_]](implicit alg:RuleAlgebra[V]) {
  def strength(context:V[RuleContext]) = {
    val freeStrength = alg.applyRule(freeStrengthRuleKey, noParams, context)
    val boughtStrength = alg.applyRule(boughtStrengthRuleKey, noParams, context)
    alg.add(freeStrength, boughtStrength)
  }
}
```
