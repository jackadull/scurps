package scurps.meta.algebra

import scurps.bib.BibRef
import scurps.meta.algebra.Arithmetic.{ArithmeticOp1, ArithmeticOp2, IsZero}
import scurps.meta.algebra.Optic.{OptionGetter, OptionLens, Setter, Unsetter}
import scurps.meta.data.{GameContext, WrapKey}
import scurps.meta.rule.RuleKey

/** The basic operations out of which all rule implementations are composed. Concrete values are wrapped inside the type
 * constructor [[A]]. It is intentional that it is impossible to "unwrap" an [[A]] instance to get hold of its contents.
 * Users of [[ScurpsOps]] are forced to use the provided operations in order to manipulate the contents of an [[A]].
 * Some [[ScurpsOps]] implementations might not even calculate the contained value, for example when just describing the
 * operations, in order to make them human readable.
 *
 * Formally, this is an algebra. It is intended to be passed around implicitly. */
trait ScurpsOps[A[_]] {
  /** Attach a bibliographic reference to the given value. The reference denotes the place where the rule is described
   * in a rulebook. This is the rule that defines the last operation that created the value. */
  def accordingTo[T](value:A[T], ref:BibRef):A[T]

  /** Calculate the result of the rule denoted by the given key, with the given parameters. If no rule is found in the
   * context's catalog for that key, or if the context is undefined, the result is undefined.
   *
   * The implicit [[ScurpsOps]] is passed so that the implementation can pass on the top-level ops instance to the
   * invoked rule. */
  def applyRuleByKey[P[_[_]],R](key:RuleKey[P,R], params:P[A], context:A[GameContext])(implicit ops:ScurpsOps[A]):A[R]

  /** Result of applying the given single-parameter arithmetic operation. */
  def arithmetic[T1,R](v:A[T1], aop:ArithmeticOp1[T1,R]):A[R]

  /** Result of applying the given two-parameter arithmetic operation. */
  def arithmetic[T1,T2,R](lhs:A[T1], rhs:A[T2], aop:ArithmeticOp2[T1,T2,R]):A[R]

  /** If the given is defined, the given `_then` gets applied to it, returning the result. Otherwise, the result is
   * undefined. */
  def ifDefined[T,T2](value:A[T], _then:A[T]=>A[T2]):A[T2]

  // TODO optics, see `fp_notes.md`
  /** If the given value is contained in the given set, the given `_then` gets applied to it, otherwise the given
   * `_else`. */
  def ifIsOneOf[T,T2](value:A[T], set:A[Set[T]], _then:A[T]=>A[T2], _else:A[T]=>A[T2]):A[T2]

  /** If the given value is zero, the given `then` gets returned, or otherwise the given `else`. */
  def ifZero[T,T2](value:A[T], _then: =>A[T2], _else: =>A[T2])(implicit isZero:IsZero[T]):A[T2]

  /** Get the optional value from the source, undefined if not present */
  def opticGet[S,T](source:A[S], optic:A[OptionGetter[S,T]]):A[T]

  /** Modify a value, if present. */
  def opticMod[S,T](source:A[S], optic:A[OptionLens[S,T]], f:A[T]=>A[T]):A[S]

  /** Set the value in the source. */
  def opticSet[S,T](source:A[S], optic:A[Setter[S,T]], newValue:A[T]):A[S]

  /** Unset a value in the source. */
  def opticUnset[S](source:A[S], optic:A[Unsetter[S]]):A[S]

  /** Leave the given value untouched if defined, or return the other given value in case it is undefined. */
  def orElse[T](value:A[T], defaultValue: =>A[T]):A[T]

  /** Wrap the given value in [[A]]. */
  def pure[T](value:T):A[T]

  // TODO optics, see `fp_notes.md` (might be a prism?)
  /** Wrap the given value in a key that can be looked up in a [[PMap]]. */
  def wrapKey[T,K](value:A[T], wrap:WrapKey[T,K]):A[K]
}
