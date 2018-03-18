package net.jackadull.scurps.validation

import net.jackadull.scurps.rules.GRules
import org.scalatest.{FreeSpec, Matchers}

import scala.language.postfixOps

class GValidationsTest extends FreeSpec with Matchers {
  "for all validations contained in the default rules, 'get' is defined" in {
    val validations = GRules.default.validations
    validations.all foreach {validation ⇒ validations get (validation id) should be ('defined)}
  }
}
