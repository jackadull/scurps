package net.jackadull.scurps.models.math

import net.jackadull.specdriven.Specification
import net.jackadull.specdriven.bridge.scalatest.SpecificationForScalaTest
import net.jackadull.gurps.specification.math.RoundingSpecification

class RoundingSpecificationTest extends SpecificationForScalaTest {
  def specifications:Seq[Specification[()⇒Unit]] = Seq(
    new RoundingSpecification {}
  )
}
