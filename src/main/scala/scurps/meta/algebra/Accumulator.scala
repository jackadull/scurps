package scurps.meta.algebra

import scurps.meta.unit.CP

trait Accumulator[-T,+Repr<:Accumulator[T,Repr]] {def accumulate(value:T):Repr}
object Accumulator {
  final case class CPBalance(sumOfNegatives:CP=CP(0), sumOfPositives:CP=CP(0)) extends Accumulator[CP,CPBalance] {
    override def accumulate(value:CP):CPBalance =
      if(value > CP(0)) copy(sumOfPositives = sumOfPositives + value)
      else if(value < CP(0)) copy(sumOfNegatives = sumOfNegatives + value)
      else this
    def sum:CP = sumOfNegatives + sumOfPositives
  }
}
