import scurps.meta.algebra.ScurpsOpsImplicits
import scurps.meta.rule.{ParamsImplicits, RuleCatalog}

package object scurps extends ParamsImplicits with ScurpsOpsImplicits {
  val basicSetRules:RuleCatalog = basic_set.basicSetRules
}
