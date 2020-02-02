import scurps.meta.algebra.{ArithmeticImplicits, ScurpsOpsImplicits, SemanticImplicits}
import scurps.meta.rule.RuleCatalog
import scurps.meta.unit.CPImplicits

package object scurps extends ArithmeticImplicits with CPImplicits with ScurpsOpsImplicits with SemanticImplicits {
  val basicSetRules:RuleCatalog = basic_set.basicSetRules
}
