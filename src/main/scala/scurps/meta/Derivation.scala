package scurps.meta

import scurps.bib.BibRef

sealed trait Derivation[+A]
object Derivation {
  sealed trait Defined[+A] extends Derivation[A] {
    def value:A
  }

  final case class DefinedAsConstant[+A](key:RuleKey[_<:Rule], value:A, bibRef:BibRef) extends Defined[A]
}
