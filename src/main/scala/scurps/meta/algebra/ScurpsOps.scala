package scurps.meta.algebra

import scurps.bib.BibRef
import scurps.meta.context.{ContextKey, GameContext}
import scurps.meta.data.{PMap, WrapKey}
import scurps.meta.math.{Add, IsZero, Subtract}
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

  /** Mathematical addition of the two given values. */
  def added[T](value1:A[T], value2:A[T])(implicit add:Add[T]):A[T]

  /** Calculate the result of the rule denoted by the given key, with the given parameters. If no rule is found in the
   * context's catalog for that key, or if the context is undefined, the result is undefined.
   *
   * The implicit [[ScurpsOps]] is passed so that the implementation can pass on the top-level ops instance to the
   * invoked rule. */
  def applyRuleByKey[P[_[_]],R](key:RuleKey[P,R], params:P[A], context:A[GameContext])(implicit ops:ScurpsOps[A]):A[R]

  /** Wrap the given value in [[A]]. */
  def constant[T](value:T):A[T]

  /** Get a context value denoted by key. Is undefined if no value is defined for the key in the context. */
  def getFromContext[T](context:A[GameContext], key:ContextKey[T]):A[T]

  /** Get a value from a given [[PMap]], denoted by the value key. If the map does not contain a value for the key, the
   * result is undefined. */
  def getFromPMap[K[_],T](pMap:A[PMap[K]], key:A[K[T]]):A[T]

  /** If the given is defined, the given `_then` gets applied to it, returning the result. Otherwise, the result is
   * undefined. */
  def ifDefined[T,T2](value:A[T], _then:A[T]=>A[T2]):A[T2]

  /** If the given value is zero, the given `then` gets returned, or otherwise the given `else`. */
  def ifZero[T,T2](value:A[T], _then: =>A[T2], _else: =>A[T2])(implicit isZero:IsZero[T]):A[T2]

  /** Leave the given value untouched if defined, or return the other given value in case it is undefined. */
  def orElse[T](value:A[T], defaultValue: =>A[T]):A[T]

  /** Modify the given context, by applying the given function to the value stored for the given context key, returning
   * a new context in which the new value is stored. If the value is not defined in the context, the result is
   * undefined. */
  def modInContext[T](context:A[GameContext], key:ContextKey[T], f:A[T]=>A[T]):A[GameContext]

  /** Remove the entry from the given [[PMap]], defined by its key. Return the modified [[PMap]]. If the there is no
   * entry for the key, return the map unchanged. */
  def removedFromPMap[K[_]](pMap:A[PMap[K]], key:A[K[_]]):A[PMap[K]]

  /** Mathematical subtraction of the two given values. */
  def subtracted[T](value1:A[T], value2:A[T])(implicit subtract:Subtract[T]):A[T]

  /** Update the entry for the given key in the [[PMap]], returning the modified map. If there already is a value
   * stored for the key, it gets overwritten. Otherwise, a new entry is added. */
  def updatedInPMap[T,K[_]](pMap:A[PMap[K]], key:A[K[T]], value:A[T]):A[PMap[K]]

  /** Wrap the given value in a key that can be looked up in a [[PMap]]. */
  def wrapKey[T,K](value:A[T], wrap:WrapKey[T,K]):A[K]
}
