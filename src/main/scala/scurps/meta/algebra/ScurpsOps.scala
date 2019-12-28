package scurps.meta.algebra

import scurps.bib.BibRef
import scurps.meta.math.Add

/** The basic operations out of which all rule implementations are composed. Concrete values are wrapped inside the type
 * constructor [[A]]. It is intentional that it is impossible to "unwrap" a [[A]] instance to get hold of its contents.
 * Users of [[ScurpsOps]] are forced to use the provided operations in order to manipulate the contents of a [[A]]. Some
 * [[ScurpsOps]] implementations might not even calculate the contained value, for example when just describing the
 * operations, in order to make them human readable.
 *
 * Formally, this is an algebra. It is intended to be passed around implicitly. */
trait ScurpsOps[A[_]] {

  /** Attach a bibliographic reference to the given value. The reference denotes the place where the rule is described
   * in a rulebook. This is the rule that defines the last operation that created the value. */
  def accordingTo[T](value:A[T], ref:BibRef):A[T]

  /** @return mathematical addition of the two given values. */
  def added[T](value1:A[T], value2:A[T])(implicit add:Add[T]):A[T]

  /** @return the given value wrapped in a [[A]]. */
  def constant[T](value:T):A[T]
}
