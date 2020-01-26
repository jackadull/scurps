package scurps.meta.rule

import scurps.bib.BibRef
import scurps.meta.algebra.Arithmetic.{Addition, ArithmeticOp1, ArithmeticOp2, IsZero}
import scurps.meta.algebra.ScurpsOps
import scurps.meta.rule.AlgebraicTerm._
import scurps.meta.rule.Term.{Term0, Term1}

// TODO rename to Expr
trait Term[-P[_[_]],+R] {
  def apply[A[+_]](params:P[A])(implicit algebra:ScurpsOps[A]):A[R]
  override def toString:String = "<term>"

  def +[P2[X[_]]<:P[X],R2>:R](lhs:Term[P2,R2])(implicit addition:Addition[R2]):Term[P2,R2] =
    arithmetic(lhs, addition)
  // TODO all other arithmetic, optical and sequence-like operations

  // TODO consider what params the _then or _else parts should get:
  // * Term0 would make sense, but what about passing on the GameContext?
  // * Check out what that would look like in actual rule implementations.
  // * Otherwise, give it the same params, with `P2[...]<:P[...]`, but make lower arities assignment compatible with
  //   greater arities.
  // * But this would mean reintroducing a `Params` type, so maybe look for a better solution.
  //   * One possiblity would be currying out the game context, but what value to curry it to?
  // * However, if we go the arity-based way, consider also passing through the algebra as first parameter, and the
  //   game context as second. This would remove implicitness from the algebra, but might end up making the whole
  //   `Term` principle much more general, maybe even deserving of its own library.
  def accordingTo(ref:BibRef):Term[P,R] = AccordingTo(ref, this)
  def arithmetic[R2](aop:ArithmeticOp1[R,R2]):Term[P,R2] = Arithmetic1(this, aop)
  def arithmetic[P2[X[_]]<:P[X],R2,R3](rhs:Term[P2,R2], aop:ArithmeticOp2[R,R2,R3]):Term[P2,R3] =
    Arithmetic2(this, rhs, aop)
  // TODO fold
  def ifDefined[R2>:R,R3](_then:Term1[R2,R3]):Term[P,R3] = IfDefined(this, _then)
  // TODO ifIsElement
  def ifZero[R2>:R,R3](_then:Term0[R3], _else:Term1[R2,R3])(implicit isZero:IsZero[R2]):Term[P,R3] =
    IfZero(this, _then, _else, isZero)
  // TODO map, opticMod, optionOptionGet, opticSet, opticUnset
  def orElse[R2>:R](default:Term0[R2]):Term[P,R2] = ??? // TODO implement
}
object Term {
  // TODO check if we even need the convenience subtypes
  trait Term0[R] extends Term[({type P[A[+_]]=Unit})#P,R] {
    override def toString:String = "<term0>"
  }

  trait Term1[T1,R] extends Term[({type P[A[+_]]=A[T1]})#P,R] {
    override def toString:String = "<term1>"
  }

  trait Term2[T1,T2,R] extends Term[({type P[A[+_]]=(A[T1],A[T2])})#P,R] {
    override def toString:String = "<term2>"
  }

  trait Term3[T1,T2,T3,R] extends Term[({type P[A[+_]]=(A[T1],A[T2],A[T3])})#P,R] {
    override def toString:String = "<term3>"
  }
}
