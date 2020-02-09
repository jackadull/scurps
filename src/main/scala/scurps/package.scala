import scurps.meta.algebra.{ArithmeticSugar, CollectionImplicits, OpticImplicits, ScurpsOpsImplicits}
import scurps.meta.rule.RuleCatalog
import scurps.meta.unit.CPImplicits

package object scurps extends ArithmeticSugar with CPImplicits with CollectionImplicits with OpticImplicits
with ScurpsOpsImplicits {
  val basicSetRules:RuleCatalog = basic_set.basicSetRules
}
