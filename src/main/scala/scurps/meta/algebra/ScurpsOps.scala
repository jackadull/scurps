package scurps.meta.algebra

import scurps.bib.BibRef
import scurps.meta.context.{ContextKey, GameContext}
import scurps.meta.data.{PMap, WrapKey}
import scurps.meta.math.{Add, Subtract}
import scurps.meta.rule.{Params, RuleKey}

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

  /** Mathematical addition of the two given values. */
  def added[T](value1:A[T], value2:A[T])(implicit add:Add[T]):A[T]

  /** Calculate the result of the rule denoted by the given key, with the given parameters. If no rule is found in the
   * context's catalog for that key, or if the context is undefined, the result is undefined. */
  def applyRuleByKey[P[_[_]]<:Params,R](key:RuleKey[P,R], params:P[A], context:A[GameContext]):A[R]

  /** @return the given value wrapped in a [[A]]. */
  def constant[T](value:T):A[T]

  /** Get a context value denoted by key. Is undefined if no value is defined for the key in the context. */
  def getFromContext[T](context:A[GameContext], key:ContextKey[T]):A[T]

  /** Get a value from a given [[PMap]], denoted by the value key. If the map does not contain a value for the key, the
   * result is undefined. */
  def getFromPMap[K[_],T](pMap:A[PMap[K]], key:A[K[T]]):A[T]

  /** Leave the given value untouched if defined, or return the other given value in case it is undefined. */
  def orElse[T](value:A[T], defaultValue:A[T]):A[T]

  /** Mathematical subtraction of the two given values. */
  def subtracted[T](value1:A[T], value2:A[T])(implicit subtract:Subtract[T]):A[T]

  /** Wrap the given value in a key that can be looked up in a [[PMap]]. */
  def wrapKey[T,K](value:A[T], wrap:WrapKey[T,K]):A[K]
}
