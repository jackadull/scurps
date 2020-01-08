import scurps.meta.algebra.ScurpsOpsImplicits
import scurps.meta.rule.RuleCatalog

package object scurps extends ScurpsOpsImplicits {
  val basicSetRules:RuleCatalog = basic_set.basicSetRules
}
