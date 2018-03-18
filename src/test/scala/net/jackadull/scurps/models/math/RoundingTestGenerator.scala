package net.jackadull.scurps.models.math

import net.jackadull.scurps.testing.TestHierarchy
import net.jackadull.scurps.testing.TestHierarchy.{TestBranch, assert, testLeaf}
import spire.math.Rational

import scala.language.postfixOps

object RoundingTestGenerator {
  import NumberType._

  def apply(accessor:RoundingTestAccessor):Seq[TestHierarchy] = Seq(testForCombatResults(accessor),
    testForFeats(accessor), testForPointCosts(accessor))

  private def testForCombatResults(accessor:RoundingTestAccessor) = TestBranch("combat results are rounded down",
    genericTestRoundingDown(accessor combatResultDoubleRounding, accessor combatResultRationalRounding))

  private def testForFeats(accessor:RoundingTestAccessor) = TestBranch("feats are rounded down",
    genericTestRoundingDown(accessor featDoubleRounding, accessor featRationalRounding))

  private def testForPointCosts(accessor:RoundingTestAccessor) = TestBranch("point costs are rounded up",
    genericTestRoundingUp(accessor pointCostDoubleRounding, accessor pointCostRationalRounding))

  private def genericTestRoundingDown(roundDouble:Double⇒Int, roundRational:Rational⇒Int):Seq[TestHierarchy] = Seq(
    testLeaf("for Double numbers") {testRoundingDown(roundDouble)},
    testLeaf("for Rational numbers") {testRoundingDown(roundRational)}
  )

  private def genericTestRoundingUp(roundDouble:Double⇒Int, roundRational:Rational⇒Int):Seq[TestHierarchy] = Seq(
    testLeaf("for Double numbers") {testRoundingUp(roundDouble)},
    testLeaf("for Rational numbers") {testRoundingUp(roundRational)}
  )

  private def testRoundingDown[A](round:A⇒Int)(implicit nt:NumberType[A]) {
    testRounding(round, Map(nt.`-3.5` → -4, nt.`-0.7` → -1, nt.`-0.3` → -1, nt.`0` → 0, nt.`0.3` → 0, nt.`0.7` → 0,
      nt.`5` → 5, nt.`18.75` → 18, nt.`100.5` → 100))
  }

  private def testRoundingUp[A](round:A⇒Int)(implicit nt:NumberType[A]) {
    testRounding(round, Map(nt.`-3.5` → -3, nt.`-0.7` → 0, nt.`-0.3` → 0, nt.`0` → 0, nt.`0.3` → 1, nt.`0.7` → 1,
      nt.`5` → 5, nt.`18.75` → 19, nt.`100.5` → 101))
  }

  private def testRounding[A](round:A⇒Int, expected:Iterable[(A,Int)])(implicit nt:NumberType[A]) {
    expected foreach {case (rawNumber, expectedRoundedNumber)⇒
      val roundedNumber = round(rawNumber)
      assert(roundedNumber == expectedRoundedNumber, s"rounding ${nt numberToString rawNumber} yielded $roundedNumber, but $expectedRoundedNumber was expected")
    }
  }

  private trait NumberType[A] {def `-3.5`:A; def `-0.7`:A; def `-0.3`:A; def `0`:A; def `0.3`:A; def `0.7`:A; def `5`:A
    def `18.75`:A; def `100.5`:A; def numberToString(n:A):String}
  private object NumberType {
    implicit val doubleNumberType:NumberType[Double] = DoubleNumberType
    implicit val rationalNumberType:NumberType[Rational] = RationalNumberType
    object DoubleNumberType extends NumberType[Double] {def `-3.5`:Double = -3.5d
      def `-0.7`:Double = -0.7d; def `-0.3`:Double = -0.3d; def `0`:Double = 0d; def `0.3`:Double = 0.3d
      def `0.7`:Double = 0.7d; def `5`:Double = 5d; def `18.75`:Double = 18.75d; def `100.5`:Double = 100.5d
      def numberToString(n:Double):String = s"$n"}
    object RationalNumberType extends NumberType[Rational] {def `-3.5`:Rational = Rational(-35,10)
      def `-0.7`:Rational = Rational(-7,10); def `-0.3`:Rational = Rational(-3,10); def `0`:Rational = Rational.zero
      def `0.3`:Rational = Rational(3,10); def `0.7`:Rational = Rational(7,10); def `5`:Rational = Rational(5,1)
      def `18.75`:Rational = Rational(1875,100); def `100.5`:Rational = Rational(1005,10)
      def numberToString(n:Rational):String = s"$n"}
  }
}
