import scurps.meta.algebra.ScurpsOpsImplicits
import scurps.meta.rule.RuleCatalog
import scurps.meta.unit.CPImplicits

package object scurps extends CPImplicits with ScurpsOpsImplicits {
  val basicSetRules:RuleCatalog = basic_set.basicSetRules
}
