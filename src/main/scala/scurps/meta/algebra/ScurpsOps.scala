package scurps.meta.algebra

import scurps.bib.BibRef
import scurps.meta.algebra.ArithmeticOperation.{ArithmeticOperation1, ArithmeticOperation2, IsZero}
import scurps.meta.algebra.Optic._
import scurps.meta.algebra.Collection.{Accumulate, Cons, IsElement, Uncons}
import scurps.meta.data.GameContext
import scurps.meta.rule.{Rule, RuleKey}

/** The basic operations out of which all rule implementations are composed. Concrete values are wrapped inside the type
 * constructor [[A]]. It is intentional that it is impossible to "unwrap" an [[A]] instance to get hold of its contents.
 * Users of [[ScurpsOps]] are forced to use the provided operations in order to manipulate the contents of an [[A]].
 * Some [[ScurpsOps]] implementations might not even calculate the contained value, for example when just describing the
 * operations, in order to make them human readable.
 *
 * Formally, this is an algebra. It is intended to be passed around implicitly. */
trait ScurpsOps[A[+_]] extends ArithmeticAlgebra[({type CA[-P[_[_]],+T] = A[T]})#CA,({type CA[-P[_[_]],+T] = A[T]})#CA] { // TODO make consistent which parameters to pass implicitly and which not
  // TODO see if we can make it that A[T] === Rule0[T], i.e. Rule[Unit,T], as this would enable universal operations that work on rules and arithmetic values alike
  /** Attach a bibliographic reference to the given value. The reference denotes the place where the rule is described
   * in a rulebook. This is the rule that defines the last operation that created the value. */
  def accordingTo[T](value:A[T], ref:BibRef):A[T]

  /** Accumulate the values of the given collection. */
  def accumulate[C[_],T,F](collection:A[C[T]], f:A[F])(implicit uncons:Uncons[C], accumulate:Accumulate[F,T]):A[F]

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

  // TODO remove
  /** Single-parameter arithmetic operation. */
  def arithmetic[T1,R](v:A[T1], aop:ArithmeticOperation1[T1,R]):A[R]

  // TODO remove
  /** Two-parameter arithmetic operation. */
  def arithmetic[T1,T2,R](lhs:A[T1], rhs:A[T2], aop:ArithmeticOperation2[T1,T2,R]):A[R]

  /** If the given value is defined, the given `_then` gets returned. Otherwise, the result is undefined. */
  def ifDefined[T,T2](value:A[T], _then: =>A[T2]):A[T2]

  /** If the given element is contained in the given collection, the given `_then` gets returned, otherwise the given
   * `_else`. */
  def ifIsElement[C[_],T,T2](collection:A[C[T]], element:A[T], _then: =>A[T2], _else: =>A[T2], isElement:IsElement[C]):A[T2]

  /** If the given value is zero, the given `_then` gets returned, or otherwise the given `_else`. */
  def ifZero[T,T2](value:A[T], _then: =>A[T2], _else: =>A[T2])(implicit isZero:IsZero[T]):A[T2]

  /** Convert the given collection by applying the given function to all of its elements. If any of the converted
   * elements ends up as undefined when converted, the result is undefined. */
  def map[T,T2,C[_]](collection:A[C[T]], f:A[T]=>A[T2], cons:Cons[C], uncons:Uncons[C]):A[C[T2]]

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
