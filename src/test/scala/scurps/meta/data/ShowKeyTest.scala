package scurps.meta.data

import org.scalatest.{FreeSpec, Matchers}
import org.scalatest.prop.TableDrivenPropertyChecks._
import scurps.basic_set.attributes.basic.{BoughtBasicAttributePoints, SetBasicAttribute}

class ShowKeyTest extends FreeSpec with Matchers {
  private val toStrings = Table(
    ("key", "expected toString"),
    (BoughtBasicAttributePoints, "BoughtBasicAttributePoints--scurps.basic_set.attributes.basic"),
    (SetBasicAttribute, "SetBasicAttribute--scurps.basic_set.attributes.basic")
  )

  "ShowKey produces proper toString representations." in
    {forAll(toStrings) {case (key, expectedToString) => key.toString should be (expectedToString)}
  }
}
