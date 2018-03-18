package net.jackadull.scurps.rules.advantages

import net.jackadull.scurps.context.GContext
import net.jackadull.scurps.meta.data.GCharacterData
import net.jackadull.scurps.rules.advantages.AdvantageStorageModel.{PerKeySingletonAdvantage, SingletonAdvantage}
import net.jackadull.scurps.rules.advantages.AdvantageVariant.{LeveledAdvantageVariant, NamedAdvantageVariant}
import net.jackadull.scurps.rules.advantages.AdvantageVariantModel.{EnumerableVariantModel, LeveledAndKeyedVariantModel, LeveledVariantModel, NamedAndKeyedVariantModel}
import net.jackadull.scurps.testing.TestHierarchy
import net.jackadull.scurps.testing.TestHierarchy.{TestBranch, assert, fail, testLeaf}

import scala.language.postfixOps
import scala.util.Random

object AdvantageRuleTestGenerator {
  // TODO make abstract (abstract over implementation)
  def globalInvariants(data:GCharacterData, context:GContext):Seq[TestHierarchy] =
    Seq(
      testLeaf("getting an advantage rule via its group from the rules returns the same advantage rule") {
        context.rules.advantageRules.all foreach {rule ⇒ assert(context.rules.advantageRules.get(rule group) contains rule)}
      }
    ) ++
    (context.rules.advantageRules.all.toSeq map {rule ⇒ TestBranch(s"${technicalName(rule)} advantage invariants",
      globalInvariantsForRule(rule, data, context))})

  def globalInvariantsForRule[A<:AdvantageVariant](rule:AdvantageRule[A], data:GCharacterData,
    context:GContext):Seq[TestHierarchy] = testVariantModel(rule, data, context)

  def initialConditions(initial:GCharacterData, context:GContext):Seq[TestHierarchy] = Seq(
    TestBranch("advantage rules intial conditions",
      costTests(initial, context) ++ Seq(
        TestBranch("global invariants for initial conditions", globalInvariants(initial, context))
      )
    )
  )

  private def costTests(initial:GCharacterData, context:GContext):Seq[TestHierarchy] = Seq(
    testLeaf("costs of standard advantages") {
      AdvantageCostList.exampleAdvantageCosts foreach {case (advantage, expectedCost)⇒
        context.rules.advantageRules.get(advantage group) match {
          case None ⇒ fail(s"no rule found for advantage group ${advantage group}")
          case Some(rule) ⇒
            val foundCost = rule.asInstanceOf[AdvantageRule[AdvantageVariant]].cpCostOf(advantage.asInstanceOf[Advantage[AdvantageVariant]])
            assert(expectedCost==foundCost, s"for advantage '$advantage', expected CP cost of $expectedCost, but found $foundCost")
        }
      }
    },
    testLeaf("costs of standard advantage combinations") {
      AdvantageCostList.exampleAdvantageCombinationCosts foreach {case (advantages, expectedCost)⇒
        val data2 = advantages.foldLeft(initial) {(d2, advantage)⇒
          context.rules.advantageRules.get(advantage group).get.asInstanceOf[AdvantageRule[AdvantageVariant]] + (advantage.asInstanceOf[Advantage[AdvantageVariant]], d2, context)
        }
        val foundCost = context.rules.cpTotalRule(data2, context)
        assert(expectedCost == foundCost, s"expected total CP cost of $expectedCost, but found $foundCost, for advantage combination: $advantages")
      }
    }
  )

  private def testVariantModel[A<:AdvantageVariant](rule:AdvantageRule[A], data:GCharacterData,
    context:GContext):Seq[TestHierarchy] = rule variantModel match {
    case e:EnumerableVariantModel[A] ⇒
      val variantTests = testEnumerableVariantModel(rule, e)
      val storageAndVariantTests = rule storageModel match {
        case _:SingletonAdvantage[A] ⇒ testSingletonAdvantageRule(rule, e variants, data, context)
        case _:PerKeySingletonAdvantage[A,_] ⇒ sys error "no test implemented for enumerable variants with keyed storage"
      }
      variantTests ++ storageAndVariantTests
    case l:LeveledAndKeyedVariantModel[A,_] ⇒
      type A2 = A with LeveledAdvantageVariant
      testLeveledAndKeyedAdvantageRule(rule.asInstanceOf[AdvantageRule[A2]],
        l.asInstanceOf[LeveledAndKeyedVariantModel[A2,Any]], data, context)
    case l:LeveledVariantModel[A] ⇒
      val variants = (1 to 5) map {level ⇒ l variantFor level}
      testSingletonAdvantageRule(rule, variants, data, context)
    case n:NamedAndKeyedVariantModel[A,_] ⇒
      rule storageModel match {
        case _:SingletonAdvantage[A] ⇒ ??? // TODO testSingletonAdvantageRule with generated variants
        case pk:PerKeySingletonAdvantage[A,_] ⇒
          type A2 = A with NamedAdvantageVariant
          testNamedAndKeyedAdvantageRuleStoredByKey(rule.asInstanceOf[AdvantageRule[A2]],
            n.asInstanceOf[NamedAndKeyedVariantModel[A2,Any]], pk.asInstanceOf[PerKeySingletonAdvantage[A2,Any]], data,
            context)
      }
  }

  private def testEnumerableVariantModel[A<:AdvantageVariant](rule:AdvantageRule[A], model:EnumerableVariantModel[A]):Seq[TestHierarchy] = Seq(
    testLeaf("enumerable variant model contains at least one variant") {assert(model.variants nonEmpty)}
  )

  private def testLeveledAndKeyedAdvantageRule[A<:LeveledAdvantageVariant,B](rule:AdvantageRule[A],
    variants:LeveledAndKeyedVariantModel[A,B], data:GCharacterData, context:GContext):Seq[TestHierarchy] = Seq(
    testLeaf("each keyed variant behaves like a singleton regarding the level") {
      val level1Advantages:Set[Advantage[A]] = variants.keys map {key ⇒ Advantage(variants variantFor (key, 1), rule group, Set())}
      val data2 = level1Advantages.foldLeft(data) {(d,adv) ⇒ rule + (adv, d, context)}
      val found1 = rule.getAll(data2, context)
      assert(found1.size == variants.keys.size, "after adding one advantage per variant, expected to find as many advantages as there are keys")
      val found2 = rule.getFirstOption(data2, context)
      assert(found2.isDefined && level1Advantages(found2.get), s"expected getFirstOption to return any of the contained advantages, but got: $found2")
      variants.keys.foreach(key ⇒ for(level ← 2 to 5) {
        val level1Advantage = Advantage(variants variantFor (key, 1), rule group, Set())
        val advantage = Advantage(variants variantFor (key, level), rule group, Set())
        val data3 = rule + (advantage, data2, context)
        val found3 = rule.getAll(data3, context)
        val expected3 = level1Advantages - level1Advantage + advantage
        assert(found3==expected3, s"expected $expected3, but found: $found3")
        val data4 = rule - (advantage, data3, context)
        val found4 = rule.getAll(data4, context)
        val expected4 = level1Advantages - level1Advantage
        assert(found4==expected4, s"expected $expected4, but found; $found4")
        val data5 = rule.removeAll(data4, context)
        val found5 = rule.getAll(data5, context)
        val expected5:Set[Advantage[A]] = Set()
        assert(found5==expected5, s"expected $expected5, but found: $found5")
        val data6 = level1Advantages.foldLeft(data4) {(d,adv) ⇒ rule - (adv, d, context)}
        assert(data5==data6, "expected that the character data is the same, no matter in which way all advantages of this type are removed")
      })
    }
  )

  private def testNamedAndKeyedAdvantageRuleStoredByKey[A<:NamedAdvantageVariant,VK,SK](rule:AdvantageRule[A],
    variants:NamedAndKeyedVariantModel[A,VK], storage:PerKeySingletonAdvantage[A,SK], data:GCharacterData,
    context:GContext):Seq[TestHierarchy] = Seq(
    testLeaf("variants are keyed and named, but uniqueness is determined by the storage key") {
      val exampleNames:Seq[String] = Seq("Name 1", "Name 2", "Name 3", "Name 4")
      val exampleVariants:Seq[A] = exampleNames flatMap {name ⇒ variants.keys.toSeq map {key ⇒ variants.variantFor(key, name)}}
      val orderedAdvantages:Seq[Advantage[A]] = exampleVariants map {variant ⇒ Advantage(variant, rule group, Set())}
      val shuffledAdvantages = new Random(orderedAdvantages hashCode).shuffle(orderedAdvantages)
      val data2 = rule.removeAll(data, context)
      val initialExpectedAdvantages:Map[SK,Advantage[A]] = Map()
      shuffledAdvantages.foldLeft[(GCharacterData,Map[SK,Advantage[A]])](data2→initialExpectedAdvantages) {case ((data3, expectedAdvantages),advantage)⇒
        val data4 = rule + (advantage, data3, context)
        val newExpectedAdvantages = expectedAdvantages + ((storage keyOf advantage) → advantage)
        val found1 = rule.getAll(data4, context)
        val expected1 = newExpectedAdvantages.values.toSet
        assert(found1==expected1, s"expected $expected1, but found: $found1")
        data4 → newExpectedAdvantages
      }
    }
  )

  private def testSingletonAdvantageRule[A<:AdvantageVariant](rule:AdvantageRule[A], variants:Iterable[A],
    data:GCharacterData, context:GContext):Seq[TestHierarchy] = Seq(
    testLeaf("singleton behavior") {
      val data1 = rule.removeAll(data, context)
      variants.foldLeft(data1) {(data2, variant) ⇒
        val advantage = Advantage(variant, rule group, Set())
        val data3 = rule + (advantage, data2, context)
        val found1 = rule.getAll(data3, context)
        val found2 = rule.getFirstOption(data3, context)
        assert(found1 == Set(advantage), s"expected Set($advantage), but found: $found1")
        assert(found2 contains advantage, s"expected Some($advantage), but found: $found2")
        val data3_2 = rule - (advantage, data3, context)
        val found3 = rule.getAll(data3_2, context)
        val found4 = rule.getFirstOption(data3_2, context)
        assert(found3 isEmpty, s"expected empty set, but found: $found3")
        assert(found4 isEmpty, s"exepected None, but found: $found4")
        data3
      }
    }
  )

  private def technicalName(rule:AdvantageRule[_]):String = {
    def recurse(str:String):String = if(str endsWith "$") recurse(str dropRight 1) else str
    recurse(rule.getClass.getSimpleName)
  }
}
