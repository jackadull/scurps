package scurps.meta

import scurps.bib.BibRef
import scurps.meta.Derivation.DefinedAsConstant

sealed trait Rule
object Rule {
  type RuleTuple[R<:Rule] = (RuleKey[R],R) // TODO lambda and remove?

  trait Rule0[+R] extends Rule {
    def apply()(implicit context:GameContext):Derivation[R]
    def forAny[T1](implicit concept:Concept[T1]):Rule1[T1,R] = ForAny(this)
  }
  trait Rule1[-T1,+R] extends Rule {
    def apply(v1:T1)(implicit context:GameContext):Derivation[R]
  }
  trait Rule2[-T1,-T2,+R] extends Rule {
    def apply(v1:T1, v2:T2)(implicit context:GameContext):Derivation[R]
  }

  final case class DefineAsConstant[+R](constantValue:R, bibRef:BibRef) extends Rule0[R] {
    private val constant = DefinedAsConstant(constantValue, bibRef)
    override def apply()(implicit context:GameContext):Derivation[R] = constant
  }
  final case class ForAny[-T1,+R](inner:Rule0[R])(implicit concept:Concept[T1]) extends Rule1[T1,R] {
    override def apply(v1:T1)(implicit context:GameContext):Derivation[R] = Derivation.ForAny(inner(), concept)
  }
}
