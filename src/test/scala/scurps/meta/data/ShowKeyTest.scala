package scurps.meta.data

import org.scalatest.prop.TableDrivenPropertyChecks._
import org.scalatest.{FreeSpec, Matchers}
import scurps.basic_set.attributes.basic.BasicAttribute.Strength
import scurps.basic_set.attributes.basic.{BasicAttributeScore, BoughtBasicAttributePoints, FreeAttributeScore, SetBasicAttribute}
import scurps.meta.data.GameContext.Subject
import scurps.meta.data.ShowKey.ShowSingletonKey
import scurps.meta.data.ShowKeyTest.Container.TestKey

class ShowKeyTest extends FreeSpec with Matchers {
  private val toStrings = Table(
    ("key", "expected toString"),
    (BasicAttributeScore, "BasicAttributeScore--scurps.basic_set.attributes.basic"),
    (BoughtBasicAttributePoints, "BoughtBasicAttributePoints--scurps.basic_set.attributes.basic"),
    (BoughtBasicAttributePoints(Strength), "BoughtBasicAttributePoints--scurps.basic_set.attributes.basic(Strength)"),
    (FreeAttributeScore, "FreeAttributeScore--scurps.basic_set.attributes.basic"),
    (SetBasicAttribute, "SetBasicAttribute--scurps.basic_set.attributes.basic"),
    (Subject, "Subject--scurps.meta.data.GameContext"),
    (TestKey, "TestKey--scurps.meta.data.ShowKeyTest.Container")
  )

  "ShowKey produces proper toString representations." in
    {forAll(toStrings) {case (key, expectedToString) => key.toString should be (expectedToString)}
  }
}
object ShowKeyTest {
  object Container {
    object TestKey extends ShowSingletonKey
  }
}
