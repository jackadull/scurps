package net.jackadull.scurps.models.math

import spire.math.Rational

trait RoundingTestAccessor {
  def combatResultDoubleRounding:Double⇒Int
  def combatResultRationalRounding:Rational⇒Int
  def featDoubleRounding:Double⇒Int
  def featRationalRounding:Rational⇒Int
  def pointCostDoubleRounding:Double⇒Int
  def pointCostRationalRounding:Rational⇒Int
}
