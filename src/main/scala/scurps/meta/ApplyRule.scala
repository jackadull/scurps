package scurps.meta

/** Subtypes can apply a rule, by passing in the rule and its parameters. The context does not need to be provided on
 * every call; the [[ApplyRule]] instance injects it. */
trait ApplyRule {
  def apply[R](rule:Rule0[R]):Derivation[R]
  def apply[T1,R](rule:Rule1[T1,R], v1:T1):Derivation[R]
  def apply[T1,T2,R](rule:Rule2[T1,T2,R], v1:T1, v2:T2):Derivation[R]
}
