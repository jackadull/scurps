package scurps.meta.algebra

import scurps.bib.BibRef
import scurps.meta.algebra.Arithmetic.{ArithmeticOp1, ArithmeticOp2, IsZero}
import scurps.meta.algebra.Optic._
import scurps.meta.data.GameContext
import scurps.meta.rule.{Rule, RuleKey}

import scala.collection.IterableOnceOps

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


  /** Calculate the result of the given rule, with the given parameters.
   *
   * The implicit [[ScurpsOps]] is passed so that the implementation can pass on the top-level ops instance to the
   * invoked rule. */
  def applyRule[P[_[_]],R](rule:A[Rule[P,R]], params:P[A], context:A[GameContext])(implicit ops:ScurpsOps[A]):A[R]

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

  /** Accumulate the values of the given iterable. */
  def fold[T,F<:Accumulator[T,F]](iterable:A[Iterable[T]], f:A[F]):A[F]

  /** If the given is defined, the given `_then` gets applied to it, returning the result. Otherwise, the result is
   * undefined. */
  def ifDefined[T,T2](value:A[T], _then: =>A[T2]):A[T2]

  /** If the given element is contained in the given source, the given `_then` gets applied to it, otherwise the given
   * `_else`. */
  def ifIsElement[S,T,T2](source:A[S], element:A[T], _then: =>A[T2], _else: =>A[T2], optic:Element[S,T]):A[T2]

  /** If the given value is zero, the given `then` gets returned, or otherwise the given `else`. */
  def ifZero[T,T2](value:A[T], _then: =>A[T2], _else: =>A[T2])(implicit isZero:IsZero[T]):A[T2]

  /** Convert the given iterable by applying the given collection to all of its elements. If any of the converted
   * elements are undefined, they are dropped in the result. */
  def map[T,T2,CC[_],C](iterable:A[IterableOnceOps[T,CC,C]], f:A[T]=>A[T2]):A[CC[T2]]

  /** Modify a value, if present. */
  def opticMod[S,T](source:A[S], optic:A[OptionLens[S,T]], f:A[T]=>A[T]):A[S]

  /** Get an optional value from the source, undefined if not present */
  def opticOptionGet[S,T](source:A[S], optic:A[OptionGetter[S,T]]):A[T]

  /** Set the value in the source. */
  def opticSet[S,T](source:A[S], optic:A[Setter[S,T]], newValue:A[T]):A[S]

  /** Unset a value in the source. */
  def opticUnset[S](source:A[S], optic:A[Unsetter[S]]):A[S]

  /** Leave the given value untouched if defined, or return the other given value in case it is undefined. */
  def orElse[T](value:A[T], defaultValue: =>A[T]):A[T]

  /** Wrap the given value in [[A]]. */
  def pure[T](value:T):A[T]
}
