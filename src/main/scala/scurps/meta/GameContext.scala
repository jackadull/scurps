package scurps.meta

trait GameContext extends PMap[ContextKey] with ApplyRule {
  def ruleCatalog:RuleCatalog

  def apply[R](rule:Rule0[R]):Derivation[R] = rule()(this)
  def apply[T1, R](rule:Rule1[T1, R], v1:T1):Derivation[R] = rule(v1)(this)
  def apply[T1, T2, R](rule:Rule2[T1, T2, R], v1:T1, v2:T2):Derivation[R] = rule(v1, v2)(this)
}
