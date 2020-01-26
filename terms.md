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

## Wording

* What was called "term" here is actually an expression.
* Terms in math are either numbers or variables, or terms multiplied by a constant.
  * Therefore, terms in Scurps can be considered pure values.

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

## New plan

* BIG PROBLEM:
  When doing as described below, there is no way of keeping intermediate results in variables, to use them more often
   than once, yet only evaluate them once.
  Or is there?
* Rule implementations are plain old Scala functions.
* Rule keys are just keys, similar to `Subject` or `BoughtBasicAttributeScore`.
  They can be applied to a game context, which results in their value.
  The value type of a rule key is a function type.
* Rule functions are not dependently typed.
  They do not accept an algebra, or a game context.
* Their parameters and results are wrapped in `Expr[_]`.
* `Expr[T]` produces an `A[T]`, given a `ScurpsOps[A]` and a game context.
  * Maybe it makes sense to create a type that contains the algebra and context.
  * Maybe it is not necessary to hardwire the context type to `GameContext`.
    Every algebra might define its own kind of context.
* `Subject` is an `Expr`.
  `IntScore` is an `Expr[IntScore]`.
  * In the logical sense, this makes `IntScore` a term.
  * The rule implementation may be called a proposition, maybe.
  * It may also be called a term, or just as well a mathematical expression.
  * In math, expressions may have variables.
    They may be evaluated.
* Those implicit "companions" for sets, iterables and the like may be called "structures".
  * Structures may define operations.
  * Not sure if this also applies to addition etc., as those are more like operations or functions.
  * A structure defines certain structural operations for instances of a certain datatype.
  * It may also be used to "destructurize" certain properties.
    * As it can be used as "con-struct" or "de-struct", it might be abbreviated as "struct".
  * `Numeric` from the standard library is a structure in that sense.
  * In Haskell, this would be called a "class".
    Maybe "typeclass" would also be a valid name.
* A rule key is the expression of the rule's function, i.e. an `Expr[FunctionN[Expr[T1]...,Expr[R]]]`.
  * Therefore, when applied to an algebra and a context, the key evaluates to `A[FunctionN[Expr[T1]...,Expr[R]]]`.
  * Therefore, an algebra needs an operation to apply such an algebraic function.
  * As it should be possible to add functions as well, this needs to work on muliple arities.
  * Therefore, it might still be helpful to abstract over function arity.
    * The standard library helps with this however, as it offers `FunctionN.tupled`.
