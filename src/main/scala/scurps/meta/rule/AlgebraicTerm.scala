package scurps.meta.rule

import scurps.bib.BibRef
import scurps.meta.algebra.Arithmetic.{ArithmeticOp1, ArithmeticOp2, IsZero}
import scurps.meta.algebra.ScurpsOps
import scurps.meta.rule.Term.{Term0, Term1}

/** Subtypes of [[Term]] that reflect [[ScurpsOps]] methods. */
object AlgebraicTerm {
  final case class AccordingTo[-P[_[_]],+R](bibRef:BibRef, value:Term[P,R]) extends Term[P,R] {
    override def accordingTo(ref:BibRef):Term[P,R] = {
      val newRef = bibRef + ref
      if(newRef ne bibRef) copy(bibRef = newRef) else this
    }
    override def apply[A[+_]](params:P[A])(implicit algebra:ScurpsOps[A]):A[R] =
      algebra.accordingTo(value(params), bibRef)
    override def toString:String = s"($value).accordingTo($bibRef)"
  }

  final case class Arithmetic1[-P[_[_]],R,+R2](operand:Term[P,R], aop:ArithmeticOp1[R,R2]) extends Term[P,R2] {
    override def apply[A[+_]](params:P[A])(implicit algebra:ScurpsOps[A]):A[R2] =
      algebra.arithmetic(operand(params), aop)
    override def toString:String = s"$aop($operand)"
  }

  final case class Arithmetic2[-P[_[_]],R1,R2,+R3](lhs:Term[P,R1], rhs:Term[P,R2], aop:ArithmeticOp2[R1,R2,R3])
  extends Term[P,R3] {
    override def apply[A[+_]](params:P[A])(implicit algebra:ScurpsOps[A]):A[R3] =
      algebra.arithmetic[R1,R2,R3](lhs(params), rhs(params), aop)
    override def toString:String = s"($lhs).$aop($rhs)"
  }

  final case class IfDefined[-P[_[_]],R1,R2](value:Term[P,R1], _then:Term1[R1,R2]) extends Term[P,R2] {
    override def apply[A[+_]](params:P[A])(implicit algebra:ScurpsOps[A]):A[R2] = {
      val v = value(params)
      algebra.ifDefined(v, _then(v))
    }
    override def toString:String = s"($value).ifDefined(${_then})"
  }

  final case class IfZero[-P[_[_]],R1,R2](value:Term[P,R1], _then:Term0[R2], _else:Term1[R1,R2], isZero:IsZero[R1])
  extends Term[P,R2] {
    override def apply[A[+_]](params:P[A])(implicit algebra:ScurpsOps[A]):A[R2] = {
      val v = value(params)
      algebra.ifZero(v, _then(()), _else(v))(isZero)
    }
    override def toString:String = s"($value).$isZero(${_then}, ${_else})"
  }

  //final case class OrElse[-P[_[_]]]
}
