import scurps.meta.algebra.ScurpsOpsImplicits
import scurps.meta.data.CPImplicits
import scurps.meta.rule.RuleCatalog

package object scurps extends CPImplicits with ScurpsOpsImplicits {
  val basicSetRules:RuleCatalog = basic_set.basicSetRules
}
