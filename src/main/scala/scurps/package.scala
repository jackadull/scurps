import scurps.meta.algebra.{ArithmeticImplicits, CollectionImplicits, OpticImplicits, ScurpsOpsImplicits}
import scurps.meta.rule.RuleCatalog
import scurps.meta.unit.CPImplicits

package object scurps extends ArithmeticImplicits with CPImplicits with CollectionImplicits with OpticImplicits
with ScurpsOpsImplicits {
  val basicSetRules:RuleCatalog = basic_set.basicSetRules
}
