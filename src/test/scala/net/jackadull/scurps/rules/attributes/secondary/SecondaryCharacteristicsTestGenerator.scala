package net.jackadull.scurps.rules.attributes.secondary

import net.jackadull.scurps.models.damage.Damage
import net.jackadull.scurps.models.measurement.{Speed, Weight}
import net.jackadull.scurps.rules.attributes.basic.BasicAttributeTestModel
import net.jackadull.scurps.rules.attributes.basic.BasicAttributeTestModel._
import net.jackadull.scurps.rules.attributes.secondary.SecondaryCharacteristicTestModel._
import net.jackadull.scurps.testing.TestHierarchy.{TestBranch, TestLeaf, assert}
import net.jackadull.scurps.testing.{Modification, TestHierarchy}
import spire.math.Rational

import scala.language.postfixOps

object SecondaryCharacteristicsTestGenerator {
  def globalInvariants[A](secondaryCharacteristicsTestAccessor:SecondaryCharacteristicsTestAccessor[A], character:A):Seq[TestHierarchy] = {
    import secondaryCharacteristicsTestAccessor._
    Seq(
      AdjustableSecondaryCharacteristicTestModel.all flatMap {sc ⇒ Seq(
        new TestLeaf(s"changing $sc does not change another attribute or characteristic${if(sc.name=="Basic Speed") " (expect Basic Move and Dodge)" else ""}") {def execute() {
          val after = setSecondaryCharacteristicLevelsBought(sc, secondaryCharacteristicLevelsBought(sc, character) + 4, character)
          for(other←SecondaryCharacteristicTestModel.all; if other!=sc; if Seq(sc,other)!=Seq(BasicSpeedTestModel,BasicMoveTestModel) && Seq(sc,other)!=Seq(BasicSpeedTestModel,DodgeTestModel)) {
            val scoreBefore = secondaryCharacteristicScore(other, character)
            val scoreAfter = secondaryCharacteristicScore(other, after)
            assert(scoreBefore==scoreAfter, s"after changing $sc, $other should not change, but it changed from $scoreBefore to $scoreAfter")
            other match {
              case asc:AdjustableSecondaryCharacteristicTestModel[_] ⇒
                val cpCostBefore = cpCostForSecondaryCharacteristic(asc, character)
                val cpCostAfter = cpCostForSecondaryCharacteristic(asc, after)
                assert(cpCostBefore==cpCostAfter, s"after changing $sc, the CP cost of $other should not change, but it changed from $cpCostBefore to $cpCostAfter")
              case _ ⇒ ()
            }
          }
          for(other←BasicAttributeTestModel.all) {
            val scoreBefore = basicAttributeScore(other, character)
            val scoreAfter = basicAttributeScore(other, after)
            assert(scoreBefore==scoreAfter, s"after changing $sc, $other should not change, but it changed from $scoreBefore to $scoreAfter")
            val cpCostBefore = cpCostForBasicAttribute(other, character)
            val cpCostAfter = cpCostForBasicAttribute(other, after)
            assert(cpCostBefore==cpCostAfter, s"after changing $sc, the CP cost of $other should not change, but it changed from $cpCostBefore to $cpCostAfter")
          }
        }},
        new TestLeaf(s"$sc costs ${sc cpCostPerLevel} CP per level") {def execute() {
          for(levels ← -10 to 10) {
            val after = setSecondaryCharacteristicLevelsBought(sc, levels, character)
            val cpCostAfter = cpCostForSecondaryCharacteristic(sc, after)
            val expectedCPCost = sc.cpCostPerLevel*levels
            assert(cpCostAfter==expectedCPCost, s"$levels of $sc should cost $expectedCPCost CP, but actually costs $cpCostAfter CP")
          }
        }}
      )},
      Seq(TestBranch(
        "secondary characteristics composition",
        compositionTemplates map {_ toTestLeaf (secondaryCharacteristicsTestAccessor, character)}
      ))
    ).flatten
  }

  def initialConditions[A](secondaryCharacteristicsTestAccessor:SecondaryCharacteristicsTestAccessor[A]):Seq[TestHierarchy] = {
    import secondaryCharacteristicsTestAccessor._
    (SecondaryCharacteristicTestModel.all map {sc⇒
      new TestLeaf(s"initial $sc score is ${sc initialValue}") {def execute() {
        val score = secondaryCharacteristicScore(sc, initialCharacter)
        val expectedScore = sc.initialValue
        assert(score==expectedScore, s"expected $sc $expectedScore, but found $score")
      }}
    }) :+ TestBranch("secondary characteristic invariants for the initial character", globalInvariants(secondaryCharacteristicsTestAccessor, initialCharacter))
  }

  private lazy val compositionTemplates:Seq[SecondaryCharacteristicCompositionTemplate[_]] = Seq(
    BasicLiftCompositionTemplate, BasicMoveCompositionTemplate, BasicSpeedCompositionTemplate,
    DamageCompositionTemplate, DodgeCompositionTemplate, FatiguePointsCompositionTemplate,
    HitPointsCompositionTemplate, PerceptionCompositionTemplate, WillCompositionTemplate
  )

  private abstract class SecondaryCharacteristicCompositionTemplate[A](val sc:SecondaryCharacteristicTestModel[A]) {
    def description:String
    def elementRanges:Seq[Range]
    def expectedScoreFor:PartialFunction[Seq[Int],A]
    def modFor[B](accessor:SecondaryCharacteristicsTestAccessor[B]):PartialFunction[Seq[Int],Modification[B]]

    def toTestLeaf[B](accessor:SecondaryCharacteristicsTestAccessor[B], character:B):TestLeaf =
      new TestLeaf(description) {def execute() {runTests(accessor, character)}}

    def runTests[B](accessor:SecondaryCharacteristicsTestAccessor[B], character:B) {
      def recurse(remainingRanges:Seq[Range], currentCombo:Seq[Int]) {remainingRanges match {
        case Seq() ⇒
          val expectedScore = expectedScoreFor(currentCombo)
          val mod = modFor(accessor)(currentCombo)
          val actualScore = accessor.secondaryCharacteristicScore(sc, mod(character))
          assert(expectedScore==actualScore, s"for $mod, $sc should be $expectedScore, but is $actualScore")
        case Seq(head, rst@_*) ⇒ head foreach {element ⇒ recurse(rst, currentCombo :+ element)}
      }}
      recurse(elementRanges, Seq())
    }
  }

  private object BasicLiftCompositionTemplate
  extends SecondaryCharacteristicCompositionTemplate[Weight[Rational]](BasicLiftTestModel) {
    def description:String = s"$sc is ST*ST/5 lbs"
    def elementRanges:Seq[Range] = Seq(0 to 30)
    def expectedScoreFor = {
      case Seq(st) ⇒
        val rawBL = Rational(st*st,5)
        if(rawBL >= Rational(10,1)) Weight ofPounds Rational(rawBL intValue, 1) else Weight ofPounds rawBL
    }
    def modFor[B](accessor:SecondaryCharacteristicsTestAccessor[B]) = {case Seq(st)⇒
      StrengthTestModel.modSetScore(st, accessor)
    }
  }

  private object BasicMoveCompositionTemplate
  extends SecondaryCharacteristicCompositionTemplate[Speed[Int]](BasicMoveTestModel) {
    def description:String = s"$sc is Basic Speed + levels bought"
    def elementRanges:Seq[Range] = Seq(0 to 20, 0 to 20, -5 to 5, -5 to 5)
    def expectedScoreFor = {case Seq(ht, dx, bs, bm) ⇒ Speed ofYardsPerSecond ((ht+dx+bs)/4 + bm)}
    def modFor[B](accessor:SecondaryCharacteristicsTestAccessor[B]) = {case Seq(ht, dx, bs, bm)⇒
      HealthTestModel.modSetScore(ht, accessor) :+ DexterityTestModel.modSetScore(dx, accessor) :+
        BasicSpeedTestModel.modSetLevels(bs, accessor) :+ BasicMoveTestModel.modSetLevels(bm, accessor)
    }
  }

  private object BasicSpeedCompositionTemplate
  extends SecondaryCharacteristicCompositionTemplate[Rational](BasicSpeedTestModel) {
    def description:String = s"$sc is (HT + DX + levels bought)/4"
    def elementRanges:Seq[Range] = Seq(0 to 20, 0 to 20, -5 to 5)
    def expectedScoreFor = {case Seq(ht, dx, bs) ⇒ Rational(ht+dx+bs, 4)}
    def modFor[B](accessor:SecondaryCharacteristicsTestAccessor[B]) = {case Seq(ht, dx, bs)⇒
      HealthTestModel.modSetScore(ht, accessor) :+ DexterityTestModel.modSetScore(dx, accessor) :+ BasicSpeedTestModel.modSetLevels(bs, accessor)
    }
  }

  private object DamageCompositionTemplate
  extends SecondaryCharacteristicCompositionTemplate[Damage](DamageTestModel) {
    def description:String = s"$sc is dependent on ST"
    def elementRanges:Seq[Range] = Seq(-10 to 150)
    def expectedScoreFor:PartialFunction[Seq[Int],Damage] = {case Seq(st) ⇒ TestDamageTable(st)}
    def modFor[B](accessor:SecondaryCharacteristicsTestAccessor[B]) = {case Seq(st)⇒
      StrengthTestModel.modSetScore(st, accessor)
    }
  }

  private object DodgeCompositionTemplate
  extends SecondaryCharacteristicCompositionTemplate[Int](DodgeTestModel) {
    def description:String = s"$sc is Basic Speed (without fractions) + 3"
    def elementRanges:Seq[Range] = Seq(0 to 20, 0 to 20, -5 to 5)
    def expectedScoreFor:PartialFunction[Seq[Int],Int] = {case Seq(ht, dx, bs) ⇒ (ht+dx+bs)/4 + 3}
    def modFor[B](accessor:SecondaryCharacteristicsTestAccessor[B]) = {case Seq(ht, dx, bs)⇒
      HealthTestModel.modSetScore(ht, accessor) :+ DexterityTestModel.modSetScore(dx, accessor) :+ BasicSpeedTestModel.modSetLevels(bs, accessor)
    }
  }

  private object FatiguePointsCompositionTemplate
  extends SecondaryCharacteristicCompositionTemplate[Int](FatiguePointsTestModel) {
    def description:String = s"$sc is HT + levels bought"
    def elementRanges:Seq[Range] = Seq(0 to 20, -5 to 5)
    def expectedScoreFor = {case Seq(ht, fp) ⇒ ht + fp}
    def modFor[B](accessor:SecondaryCharacteristicsTestAccessor[B]) = {case Seq(ht, fp)⇒
      HealthTestModel.modSetScore(ht, accessor) :+ FatiguePointsTestModel.modSetLevels(fp, accessor)
    }
  }

  private object HitPointsCompositionTemplate
  extends SecondaryCharacteristicCompositionTemplate[Int](HitPointsTestModel) {
    def description:String = s"$sc is ST + levels bought"
    def elementRanges:Seq[Range] = Seq(0 to 20, -5 to 5)
    def expectedScoreFor = {case Seq(st, hp) ⇒ st + hp}
    def modFor[B](accessor:SecondaryCharacteristicsTestAccessor[B]) = {case Seq(st, hp)⇒
      StrengthTestModel.modSetScore(st, accessor) :+ HitPointsTestModel.modSetLevels(hp, accessor)
    }
  }

  private object PerceptionCompositionTemplate
  extends SecondaryCharacteristicCompositionTemplate[Int](PerceptionTestModel) {
    def description:String = s"$sc is IQ + levels bought"
    def elementRanges:Seq[Range] = Seq(0 to 20, -5 to 5)
    def expectedScoreFor = {case Seq(iq, per) ⇒ iq+per}
    def modFor[B](accessor:SecondaryCharacteristicsTestAccessor[B]) = {case Seq(iq, per)⇒
      IntelligenceTestModel.modSetScore(iq, accessor) :+ PerceptionTestModel.modSetLevels(per, accessor)
    }
  }

  private object WillCompositionTemplate
  extends SecondaryCharacteristicCompositionTemplate[Int](WillTestModel) {
    def description:String = s"$sc is IQ + levels bought"
    def elementRanges:Seq[Range] = Seq(0 to 20, -5 to 5)
    def expectedScoreFor = {case Seq(iq, will) ⇒ iq + will}
    def modFor[B](accessor:SecondaryCharacteristicsTestAccessor[B]) = {case Seq(iq, will)⇒
      IntelligenceTestModel.modSetScore(iq, accessor) :+ WillTestModel.modSetLevels(will, accessor)
    }
  }
}
