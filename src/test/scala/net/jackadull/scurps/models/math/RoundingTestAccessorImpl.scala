package net.jackadull.scurps.models.math

import net.jackadull.scurps.models.math.Rounding.{roundCombatResult, roundFeat, roundPointCost}
import spire.math.Rational

object RoundingTestAccessorImpl extends RoundingTestAccessor {
  def combatResultDoubleRounding = roundCombatResult(_:Double)
  def combatResultRationalRounding = roundCombatResult(_:Rational)
  def featDoubleRounding = roundFeat(_:Double)
  def featRationalRounding = roundFeat(_:Rational)
  def pointCostDoubleRounding = roundPointCost(_:Double)
  def pointCostRationalRounding = roundPointCost(_:Rational)
}
