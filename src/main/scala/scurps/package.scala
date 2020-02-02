import scurps.meta.algebra.{ArithmeticImplicits, OpticImplicits, ScurpsOpsImplicits, SemanticImplicits}
import scurps.meta.rule.RuleCatalog
import scurps.meta.unit.CPImplicits

package object scurps extends ArithmeticImplicits with CPImplicits with OpticImplicits with ScurpsOpsImplicits
with SemanticImplicits {
  val basicSetRules:RuleCatalog = basic_set.basicSetRules
}
