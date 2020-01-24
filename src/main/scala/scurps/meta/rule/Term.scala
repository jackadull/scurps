package scurps.meta.rule

import scurps.bib.BibRef
import scurps.meta.algebra.Arithmetic.{Addition, ArithmeticOp1, ArithmeticOp2}
import scurps.meta.algebra.ScurpsOps
import scurps.meta.data.GameContext
import scurps.meta.rule.Term.{Term0, Term1}

trait Term[-P[_[_]],+R] {
  def applyP[A[+_]](params:P[A], context:A[GameContext])(implicit algebra:ScurpsOps[A]):A[R]
  override def toString:String = "<term>"

  def :+[P2[X[_]]<:P[X],R2>:R](lhs:Term[P2,R2])(implicit addition:Addition[R2]):Term[P2,R2] =
    arithmetic(lhs, addition)
  // TODO all other arithmetic, optical and sequence-like operations

  def accordingTo(ref:BibRef):Term[P,R]
  def arithmetic[R2](aop:ArithmeticOp1[R,R2]):Term[P,R2]
  def arithmetic[P2[X[_]]<:P[X],R2,R3](lhs:Term[P2,R2], aop:ArithmeticOp2[R,R2,R3]):Term[P2,R3]
  // TODO fold
  def ifDefined[R2>:R,R3](_then: =>Term1[R2,R3]):Term[P,R3]
  // TODO ifIsElement
  def ifZero[R2>:R,R3](_then: =>Term0[R3], _else: =>Term1[R2,R3]):Term[P,R3]
  // TODO map, opticMod, optionOptionGet, opticSet, opticUnset
  def orElse[R2>:R](default: =>Term0[R2]):Term[P,R2]
}
object Term {
  trait Term0[R] extends Term[({type P[A[+_]]=Unit})#P,R] {
    def apply[A[+_]](context:A[GameContext])(implicit algebra:ScurpsOps[A]):A[R]
    override final def applyP[A[+_]](params:Unit, context:A[GameContext])(implicit algebra:ScurpsOps[A]):A[R] =
      apply(context)
    override def toString:String = "<term0>"
  }

  trait Term1[T1,R] extends Term[({type P[A[+_]]=A[T1]})#P,R] {
    def apply[A[+_]](v1:A[T1], context:A[GameContext])(implicit algebra:ScurpsOps[A]):A[R]
    override final def applyP[A[+_]](params:A[T1], context:A[GameContext])(implicit algebra:ScurpsOps[A]):A[R] =
      apply(params, context)
    override def toString:String = "<term1>"
  }

  trait Term2[T1,T2,R] extends Term[({type P[A[+_]]=(A[T1],A[T2])})#P,R] {
    def apply[A[+_]](v1:A[T1], v2:A[T2], context:A[GameContext])(implicit algebra:ScurpsOps[A]):A[R]
    override final def applyP[A[+_]](params:(A[T1], A[T2]), context:A[GameContext])(implicit algebra:ScurpsOps[A]):A[R] =
      apply(params._1, params._2, context)
    override def toString:String = "<term2>"
  }

  trait Term3[T1,T2,T3,R] extends Term[({type P[A[+_]]=(A[T1],A[T2],A[T3])})#P,R] {
    def apply[A[+_]](v1:A[T1], v2:A[T2], v3:A[T3], context:A[GameContext])(implicit algebra:ScurpsOps[A]):A[R]
    override final def applyP[A[+_]](params:(A[T1], A[T2], A[T3]), context:A[GameContext])(implicit algebra:ScurpsOps[A]):A[R] =
      apply(params._1, params._2, params._3, context)
    override def toString:String = "<term3>"
  }
}
