package net.jackadull.scurps.meta

import scala.language.higherKinds

/** Compilation proof for rule type requirements. */
class RuleTypeTest {
  trait PropertyKey[V]

  trait Ctx {
    def catalog:RuleCatalog
  }
  trait Ctx2 extends Ctx

  object AKey extends PropertyKey[Int]
  object ARuleKey extends RuleKey[Rule1[PMap[PropertyKey],Int,Ctx]]

  val aRuleImpl:Rule1[PMap[PropertyKey],Int,Ctx] = new Rule1[PMap[PropertyKey],Int,Ctx] {
    override def apply(v1:PMap[PropertyKey])(implicit context:Ctx):Derivation[Int] = {
      val result:Option[Int] = v1.get(AKey)
      ???
    }
  }

  val emptyCatalog:RuleCatalog = null

  // Add a rule
  val catalogWithARule:RuleCatalog = emptyCatalog + (ARuleKey -> aRuleImpl)

  // Get a rule
  val Some(rule1:Rule1[PMap[PropertyKey],Int,Ctx]) = catalogWithARule.get(ARuleKey)

  // Get rule with variance assignment
  val Some(rule2:Rule1[PMap[PropertyKey],Any,Ctx2]) = catalogWithARule.get(ARuleKey)
}
