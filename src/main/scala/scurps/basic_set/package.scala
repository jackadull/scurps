package scurps

import scurps.basic_set.attributes.basic.{AllBasicAttributes, CPSpentOnBasicAttribute}
import scurps.meta.algebra.Accumulator.CPBalance
import scurps.meta.algebra.ScurpsOps
import scurps.meta.data.GameContext
import scurps.meta.rule.Rule.Rule0
import scurps.meta.rule.RuleCatalog

package object basic_set {
  val basicSetRules:RuleCatalog = attributes.basicSetRules ++ Seq(
    TotalCPSpent -> new Rule0[CPBalance] {
      override def apply[A[+_]](context:A[GameContext])(implicit ops:ScurpsOps[A]):A[CPBalance] =
        AllBasicAttributes(context).map(attr => CPSpentOnBasicAttribute(attr, context)).fold(CPBalance())
    }
  )
}
