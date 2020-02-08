package scurps.meta.unit

import scurps._
import scurps.meta.algebra.Collection.Accumulate

final case class CPBalance(sumOfNegatives:CP = 0.cp, sumOfPositives:CP = 0.cp) {
  def accumulate(value:CP):CPBalance =
    if(value > 0.cp) copy(sumOfPositives = sumOfPositives + value)
    else if(value < 0.cp) copy(sumOfNegatives = sumOfNegatives + value)
    else this
  def sum:CP = sumOfNegatives + sumOfPositives
}
object CPBalance {
  // TODO toString
  implicit object CPBalanceAccumulate extends Accumulate[CPBalance,CP]
    {override def accumulate(accumulator:CPBalance, value:CP):CPBalance = accumulator.accumulate(value)}
}
