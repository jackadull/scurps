package scurps.meta

import scurps.bib.BibRef

sealed trait Derivation[+A]
object Derivation {
  sealed trait Defined[+A] extends Derivation[A] {
    def value:A
  }

  sealed trait Undefined extends Derivation[Nothing]

  final case class DefaultZero[+A](key:ValueKey[_], value:A) extends Defined[A]
  final case class DefinedAsConstant[+A](key:RuleKey[_<:Rule], value:A, bibRef:BibRef) extends Defined[A]
  final case class MissingContextValue(key:ContextKey[_]) extends Undefined
  final case class MissingRule(key:RuleKey[_<:Rule]) extends Undefined
  final case class Plain[+A](value:A) extends Defined[A]
  final case class StoredValue[+A](key:ValueKey[_], value:A) extends Defined[A]
}
