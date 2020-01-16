package scurps.meta.data

import org.scalatest.{FreeSpec, Matchers}
import org.scalatest.prop.TableDrivenPropertyChecks._
import scurps.basic_set.attributes.basic.BasicAttribute.Strength
import scurps.basic_set.attributes.basic.{BasicAttributeScore, BoughtBasicAttributePoints, FreeAttributeScore, SetBasicAttribute}

class ShowKeyTest extends FreeSpec with Matchers {
  private val toStrings = Table(
    ("key", "expected toString"),
    (BasicAttributeScore, "BasicAttributeScore--scurps.basic_set.attributes.basic"),
    (BoughtBasicAttributePoints, "BoughtBasicAttributePoints--scurps.basic_set.attributes.basic"),
    (BoughtBasicAttributePoints(Strength), "BoughtBasicAttributePoints--scurps.basic_set.attributes.basic(Strength)"),
    (FreeAttributeScore, "FreeAttributeScore--scurps.basic_set.attributes.basic"),
    (SetBasicAttribute, "SetBasicAttribute--scurps.basic_set.attributes.basic")
  )

  "ShowKey produces proper toString representations." in
    {forAll(toStrings) {case (key, expectedToString) => key.toString should be (expectedToString)}
  }
}
