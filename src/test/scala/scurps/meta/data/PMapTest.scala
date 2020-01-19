package scurps.meta.data

import org.scalatest.{FreeSpec, Matchers}
import scurps.basic_set.attributes.basic.BasicAttribute.Strength
import scurps.basic_set.attributes.basic.BoughtBasicAttributePoints
import scurps.meta.unit.Score

class PMapTest extends FreeSpec with Matchers {
  "PMap:" - {
    "has a nice toString in the default implementation." in {
      PMap.empty.updated(BoughtBasicAttributePoints(Strength), Score(-4)).toString should be
        ("PMap(BoughtBasicAttributePoints--scurps.basic_set.attributes.basic(Strength) -> -4)")
    }
  }
}
