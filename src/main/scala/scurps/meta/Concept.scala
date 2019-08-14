package scurps.meta

/** Refers to a type of entity that is relevant for the rules. Usually used for the purpose of description. For
 * example, there might be the concept of _a basic attribute_, such that a derivation can contain the equivalent
 * meaning of: "For any basic attribute, the default score is 10."
 *
 * Intuitively, everything described by rules or derivations is conceptual. But an instance of [[Concept]] is only
 * required when the information would otherwise be lost when applying a rule. For example, the
 * `ForAny[BasicAttribute]` derivation would lose the "basic attribute" information at runtime if the basic attribute
 * concept would not be contained in it.
 *
 * While `Class[BasicAttribute]` would also be a valid representation of the concept of a basic attribute, using
 * `Class` would be too general for allowing features such as translating the derivation into a natural-language text.
 *
 * Instances of [[Concept]] are usually found via implicit resolution. */
trait Concept[A]
