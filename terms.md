# Term-based approach

## Idea

* Everything that interacts with an algebra may be called an algebraic `Term`.
* Terms can have parameters, e.g. `Term0`, `Term1`, `Term2` etc.
* A constant is a `Term0`:
  For a constant of type `T`, given an algebra for `A[_]`, it generates an `A[T]`.
* `Term` is what is currently called `Rule`.
* Terms can be combined:
  * Adding two terms with the same parameters generates a new term with the same parameters, returning the result of
    the addition.
  * Combining terms in other ways is also possible, such as folding.
  * However, for those other cases, we cannot make any assumptions on what `T` is.
  * For example, we cannot assume that `T` is an instance of `Iterable`.
  * Therefore, we need a new implicit typeclass for iteration (for example).

## Plan

* Create `Term`, with its arity-specific subtypes.
* Should the context still be passed to every term?
  Or should be just another parameter?
* Add all of the relevant methods of `ScurpsOps` to it.
  * Thereby, create the implicit "accessor" types.
    (We need a better name for this.)
* Rename `ScurpsOps` to `ScurpsAlgebra` (or `ScAlgebra`, or `ScurpsAlg`, or `SAlgebra`, or `RuleAlgebra`).
* Remove `Rule`, replacing it with `Term`.
* Rename `RuleKey` to `Rule`.
* Make types like `Score` or `CP` implement `Term0`.
  * Still, add operators like `:+` without an algebra, as a quick alternative.
* Check how much of the implicit syntactic sugar for rules and constants we now can remove.
* Check if we still need `ScurpsOps.applyRule`.
* Check if we can remove the plus in front of the operators, e.g. `:+` -> `+`.
* Check if with these changes in place, some algebraic operations can be expressed in terms of others.
  This might reduce the number of `ScurpsOps` methods.
