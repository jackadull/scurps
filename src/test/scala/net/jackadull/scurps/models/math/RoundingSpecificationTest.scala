package net.jackadull.scurps.models.math

import net.jackadull.specdriven.Specification
import net.jackadull.specdriven.bridge.scalatest.SpecificationForScalaTest
import net.jackadull.gurps.specification.math.{RoundingModel, RoundingSpecification}
import spire.math.Rational

class RoundingSpecificationTest extends SpecificationForScalaTest {
  def specifications:Seq[Specification[()⇒Unit]] = Seq(
    new RoundingSpecification {
      def roundingModel = new RoundingModel {
        def roundCombatResult(value:Double) = Rounding.roundCombatResult(value)
        def roundCombatResult(value:Rational) = Rounding.roundCombatResult(value)
        def roundFeat(value:Double) = Rounding.roundFeat(value)
        def roundFeat(value:Rational) = Rounding.roundFeat(value)
        def roundPointCosts(value:Double) = Rounding.roundPointCost(value)
        def roundPointCosts(value:Rational) = Rounding.roundPointCost(value)
      }
    }
  )
}
