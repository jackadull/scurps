package net.jackadull.scurps.models.math

import spire.math.Rational

object Rounding {
  def roundCombatResult(result:Double):Int = roundDown(result)
  def roundCombatResult(result:Rational):Int = roundDown(result)
  def roundFeat(feat:Double):Int = roundDown(feat)
  def roundFeat(feat:Rational):Int = roundDown(feat)
  def roundPointCost(pointCost:Double):Int = roundUp(pointCost)
  def roundPointCost(pointCost:Rational):Int = roundUp(pointCost)

  private def roundDown(raw:Double):Int = raw.floor.toInt
  private def roundDown(raw:Rational):Int = raw.floor.toInt
  private def roundUp(raw:Double):Int = raw.ceil.toInt
  private def roundUp(raw:Rational):Int = raw.ceil.toInt
}
