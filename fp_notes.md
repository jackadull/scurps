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

[APair]: https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/APair.scala
[Apply]: https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Apply.scala
[Cats typeclasses]: https://typelevel.org/cats/typeclasses
[Functor]: https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Functor.scala
