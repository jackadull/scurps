package net.jackadull.scurps.meta

import scala.language.higherKinds

/** Compilation proof for rule type requirements. */
class RuleTypeTest {
  trait PropertyKey[V]
  trait RuleKey[R]

  trait Env {
    def catalog:RuleCatalog[Env,RuleKey]
  }

  object AKey extends PropertyKey[Int]
  object ARuleKey extends RuleKey[Rule1[PMap[PropertyKey],Env,Int]]

  val aRuleImpl:Rule1[PMap[PropertyKey],Env,Int] = new Rule1[PMap[PropertyKey],Env,Int] {
    override def apply(v1:PMap[PropertyKey])(implicit environment:Env):Derivation[Int] = {
      val result:Option[Int] = v1.get(AKey)
      ???
    }
  }
}
