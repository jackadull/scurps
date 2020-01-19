package scurps.meta.algebra

import scurps._
import scurps.meta.unit.CP

trait Accumulator[-T,+Repr<:Accumulator[T,Repr]] {def accumulate(value:T):Repr}
object Accumulator {
  final case class CPBalance(sumOfNegatives:CP=0.cp, sumOfPositives:CP=0.cp) extends Accumulator[CP,CPBalance] {
    override def accumulate(value:CP):CPBalance =
      if(value > 0.cp) copy(sumOfPositives = sumOfPositives + value)
      else if(value < 0.cp) copy(sumOfNegatives = sumOfNegatives + value)
      else this
    def sum:CP = sumOfNegatives + sumOfPositives
  }
}
