package scurps.meta

import scurps.meta.Rule.Rule1

import scala.language.higherKinds

/** Compilation proof for rule type requirements. */
class RuleTypeTest {
  trait PropertyKey[V]

  object AKey extends PropertyKey[Int]
  object ARuleKey extends RuleKey[Rule1[PMap[PropertyKey],Int]]

  val aRuleImpl:Rule1[PMap[PropertyKey],Int] = new Rule1[PMap[PropertyKey],Int] {
    override def apply(v1:PMap[PropertyKey])(implicit context:GameContext):Derivation[Int] = {
      val result:Option[Int] = v1.get(AKey)
      ???
    }
  }

  val emptyCatalog:RuleCatalog = null

  // Add a rule
  val catalogWithARule:RuleCatalog = emptyCatalog + (ARuleKey -> aRuleImpl)

  // Get a rule
  val Some(rule1:Rule1[PMap[PropertyKey],Int]) = catalogWithARule.get(ARuleKey)

  // Get rule with variance assignment
  val Some(rule2:Rule1[PMap[PropertyKey],Any]) = catalogWithARule.get(ARuleKey)
}
