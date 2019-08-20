package scurps.meta

import scurps.bib.BibRef

import scala.language.higherKinds

sealed trait Derivation[+A] {
  def bibRef:BibRef
  def valueE:Either[Derivation[Nothing],A]
  def valueOpt:Option[A] = valueE.toOption
}
object Derivation {
  // TODO check if all of those are still needed
  final case class Added[+A](value:A, lhs:Derivation[A], rhs:Derivation[A], bibRef:BibRef) extends Derivation[A] {
    override def valueE:Either[Derivation[Nothing],A] = Right(value)
  }
  final case class ContextValue[+A](key:ContextKey[_], value:A, bibRef:BibRef) extends Derivation[A] {
    override def valueE:Either[Derivation[Nothing],A] = Right(value)
  }
  final case class DefaultValue[+A](value:A, undefined:Derivation[Nothing], bibRef:BibRef) extends Derivation[A] {
    override def valueE:Either[Derivation[Nothing],A] = Right(value)
  }
  final case class DefinedAsConstant[+A](value:A, bibRef:BibRef) extends Derivation[A] {
    override def valueE:Either[Derivation[Nothing],A] = Right(value)
  }
  final case class ForAny[T1,+A](value:Derivation[A], concept:Concept[T1], bibRef:BibRef) extends Derivation[A] {
    override def valueE:Either[Derivation[Nothing],A] = value.valueE
  }
  final case class MissingContextValue(key:ContextKey[_], bibRef:BibRef) extends Derivation[Nothing] {
    override def valueE:Either[Derivation[Nothing],Nothing] = Left(this)
  }
  final case class MissingPValue[A,K[_]](map:Derivation[PMap[K]], key:K[A], bibRef:BibRef) extends Derivation[Nothing] {
    override def valueE:Either[Derivation[Nothing],Nothing] = Left(this)
  }
  final case class MissingRule(key:RuleKey[_<:Rule], bibRef:BibRef) extends Derivation[Nothing] {
    override def valueE:Either[Derivation[Nothing],Nothing] = Left(this)
  }
  final case class Plain[+A](value:A, bibRef:BibRef) extends Derivation[A] {
    override def valueE:Either[Derivation[Nothing],A] = Right(value)
  }
  final case class PValue[A,K[_]](value:A, map:Derivation[PMap[K]], key:K[A], bibRef:BibRef) extends Derivation[A] {
    override def valueE:Either[Derivation[Nothing],A] = Right(value)
  }
}
