# FP notes on Scurps

Notes on comparing the current algebra based implementation with typical functional programming structures.
Possibly useful for better functional modelling of Scurps.

Motivations:

* With the current approach, it is difficult to create a dynamic CP balance.
  This would imply having a rule that returns a list of rules, which would evaluate to individual CP costs.
  In order to summate the positive and negative costs individually, it would be required to call different "add" methods
   in some kind of object representing the balance.

  However, since everything is wrapped in an arithmetic datatype, there is no way to directly call an individual method
   in the balance object.
  The only feasible way would be to make the balance implement `PMap` and then have diffent keys for the sum of
   negatives, the sum of positives, and the total sum.
  This is really ugly though, and looks like an artifical construct.

* For the upcoming advantages and skill models, similar problems will occur.
  Every advantage can have an individual rule for determining its CP cost.
  This rule must be dynamically determined, similar to getting a member of the advantage object that is a rule.

  More than that, skills can modify or enable other rules.
  This will lead to a whole host of similar problems.

All of this gives rise to the need of a better functional model of algebraic operations.
Somehow, it must be possible to relate to certain instance-specific properties and also change or accumulate over them,
 beyond that which is possible with `PMap`.

Upon closer investigation, it might even be possible to get rid of `PMap`, in favor of more fitting FP concepts.

This is why a closer look at a comprehensive FP library is noteworthy, seeing how those concepts relate to the current
 algebraic Scurps model.

## Comparing with Scalaz

* Applying a rule is similar to using a [Functor], where the rule is passed in, and the result gets returned.
* Getting a key from the context or a `PMap` is like using an [Apply], passing in the map first and then the key.
* The pair of a rule key and a rule are similar to an [APair].

## Comparing with Cats

(See [Cats typeclasses].)

* Simple addition and subtraction is a semigroup, or a monoid.
* Applying a rule is a bit like passing the rule and its parameters to an Applicative or Functor.

# Conclusion

Traditional FP abstractions abstract over the wrong things for this case.
FP abstractions are _operations_, but here we want to abstract over the parameters and results of those operations.
The operations themselves are already modelled by the algebra.

## Folding

For creating a CP balance, the proper operation would be `fold`.
So the FP abstraction to use would be a `Fold` type.
However, in this case, the fold operation itself is not a problem, in that it can be just another method of `ScurpsOps`.
Rather, we would like to abstract over its parameters and result type.

Looking at those, we get something like: `[A,B](A)((A,B)=>A):A`
This might be called a "foldee", or "accumulation", like this:

```scala
trait Accumulation[T,+Repr<:Accumulation[T,Repr]] {
  def accumulate(v:T):Repr
}
```

The corresponding method in `ScurpsOps` might look like this:

```scala
def fold[T,R<:Accumulation[T,R]](seq:A[Iterable[T]], acc:A[R]):A[R]
```

## Optics

For accessing certain members of an algebraic values in a semantically transparent way, it seems that optics are a good
 way of doing it.
For example, if an advantage instance has a member giving the rule for calculating its CP cost, then accessing that
 member inside an `A[Advantage]` would mean applying a `Lens[Advantage,Rule1[Advantage,CP]]` to it via the algebra.

The same goes for changing properties of algebraic values.

Seen this way, consequently using optics for such cases might render types like `ContextKey` or `ValueKey` entirely
 redundant, by replacing them with proper lenses.

However, a standard lens library might not be sufficient for this.
The individual lenses used in conjunction with `ScurpsOps` should be describable and usable in explanations.
While this concept is not at all explored at this stage, it likely will require attaching conceptual representation
 values to the lens types, which can then be used to explain those operations and translate them to natural language.

Therefore, it makes sense to introduce a minimum of optics to Scurps Meta, and consequently replace the current
 key-based lookup and set methods in `ScurpsOps` with them.

This should also solve the likely upcoming problem of the current `PMap` modification methods not returning the proper
 result subtype.

## Reconsider arithmetics

Let's also reconsider arithmetic operations.
Currently, `ScurpsOps` has different methods for addition, subtraction and multiplication, even though their type
 signatures are similar.
(The exception is multiplication, because all three involved types may be different.)

Maybe it would be more convenient to model arithmetic operations as a sealed trait, so the algebra implementation can
 decide on itself wether to look into the specific subtype, or just blindly applying the operation.

The common type for most arithmetic operations would be `[A,B,C](A,B)=>C`.
But it would be better to restrict the type to a specific sealed trait, so as not to allow any arbitrary `Function2` to
 creep in.

## Remaining operations

With all the changes above considered, this would be the methods of `ScurpsOps` left unchanged:
* `accordingTo[T](A[T], BibRef):A[T]`
* `applyRuleByKey[P[_[_]],R](RuleKey[P,R], P[A], A[GameContext])(implicit ScurpsOps[A]):A[R]`
* `ifDefined[T,T2](A[T], A[T]=>A[T2]):A[T2]`
* `pure[T](T):A[T]`
* `orElse[T](A[T], =>A[T]):A[T]`

All of these methods deal with the immediate semantics of the algebraic datatype.
Arithmetic or optical methods simply lift their respective non-algebraic counterparts into the algebraic context.

For the methods above though, there would be no way to "unlift" them.
Therefore, it makes sense to keep them as fundamental algebraic operations.

[APair]: https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/APair.scala
[Apply]: https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Apply.scala
[Cats typeclasses]: https://typelevel.org/cats/typeclasses
[Functor]: https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Functor.scala
