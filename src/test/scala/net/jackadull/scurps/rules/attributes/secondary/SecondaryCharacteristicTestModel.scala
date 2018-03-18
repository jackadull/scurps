package net.jackadull.scurps.rules.attributes.secondary

import net.jackadull.scurps.models.damage.Damage
import net.jackadull.scurps.models.dice.Dice
import net.jackadull.scurps.models.measurement.{Speed, Weight}
import net.jackadull.scurps.testing.Modification
import spire.math.Rational

sealed trait SecondaryCharacteristicTestModel[A] {
  def initialValue:A
  def name:String
  override def toString = name
}
object SecondaryCharacteristicTestModel {
  sealed trait AdjustableSecondaryCharacteristicTestModel[A] extends SecondaryCharacteristicTestModel[A] {
    def cpCostPerLevel:Int

    def modSetLevels[B](newLevels:Int, accessor:SecondaryCharacteristicsTestAccessor[B]):Modification[B] =
      new Modification[B] {
        def apply(v:B):B = accessor.setSecondaryCharacteristicLevelsBought(AdjustableSecondaryCharacteristicTestModel.this, newLevels, v)
        def description:String = newLevels match {
          case ltz if ltz<0 ⇒ s"$name - ${-newLevels}"
          case gtz if gtz>0 ⇒ s"$name + $newLevels"
          case _ ⇒ s"$name + 0"
        }
      }
  }
  object AdjustableSecondaryCharacteristicTestModel {
    lazy val all:Seq[AdjustableSecondaryCharacteristicTestModel[_]] = allOfInt ++ allOfIntSpeed ++ allOfRational
    lazy val allOfInt:Seq[AdjustableSecondaryCharacteristicTestModel[Int]] = Seq(HitPointsTestModel, WillTestModel,
      PerceptionTestModel, FatiguePointsTestModel)
    lazy val allOfIntSpeed:Seq[AdjustableSecondaryCharacteristicTestModel[Speed[Int]]] = Seq(BasicMoveTestModel)
    lazy val allOfRational:Seq[AdjustableSecondaryCharacteristicTestModel[Rational]] = Seq(BasicSpeedTestModel)
  }

  sealed trait NonAdjustableSecondaryCharacteristicTestModel[A] extends SecondaryCharacteristicTestModel[A]
  object NonAdjustableSecondaryCharacteristicTestModel {
    lazy val all:Seq[NonAdjustableSecondaryCharacteristicTestModel[_]] = allOfDamage ++ allOfInt ++ allOfRationalWeight
    lazy val allOfDamage:Seq[NonAdjustableSecondaryCharacteristicTestModel[Damage]] = Seq(DamageTestModel)
    lazy val allOfInt:Seq[NonAdjustableSecondaryCharacteristicTestModel[Int]] = Seq(DodgeTestModel)
    lazy val allOfRationalWeight:Seq[NonAdjustableSecondaryCharacteristicTestModel[Weight[Rational]]] = Seq(
      BasicLiftTestModel)
  }

  lazy val all:Seq[SecondaryCharacteristicTestModel[_]] =
    AdjustableSecondaryCharacteristicTestModel.all ++ NonAdjustableSecondaryCharacteristicTestModel.all

  object BasicLiftTestModel extends NonAdjustableSecondaryCharacteristicTestModel[Weight[Rational]] {
    def initialValue:Weight[Rational] = Weight ofPounds Rational(20,1)
    def name:String = "BL"
  }
  object BasicMoveTestModel extends AdjustableSecondaryCharacteristicTestModel[Speed[Int]] {
    def cpCostPerLevel:Int = 5
    def initialValue:Speed[Int] = Speed(5)
    def name:String = "Basic Move"
  }
  object BasicSpeedTestModel extends AdjustableSecondaryCharacteristicTestModel[Rational] {
    def cpCostPerLevel:Int = 5
    def initialValue:Rational = Rational(5, 1)
    def name:String = "Basic Speed"
  }
  object DamageTestModel extends NonAdjustableSecondaryCharacteristicTestModel[Damage] {
    def initialValue:Damage = Damage(Dice(1,-2), Dice(1,0))
    def name:String = "Dmg"
  }
  object DodgeTestModel extends NonAdjustableSecondaryCharacteristicTestModel[Int] {
    def initialValue:Int = 8
    def name:String = "Dodge"
  }
  object FatiguePointsTestModel extends AdjustableSecondaryCharacteristicTestModel[Int] {
    def cpCostPerLevel:Int = 3
    def initialValue:Int = 10
    def name:String = "FP"
  }
  object HitPointsTestModel extends AdjustableSecondaryCharacteristicTestModel[Int] {
    def cpCostPerLevel:Int = 2
    def initialValue:Int = 10
    def name:String = "HP"
  }
  object PerceptionTestModel extends AdjustableSecondaryCharacteristicTestModel[Int] {
    def cpCostPerLevel:Int = 5
    def initialValue:Int = 10
    def name:String = "Per"
  }
  object WillTestModel extends AdjustableSecondaryCharacteristicTestModel[Int] {
    def cpCostPerLevel:Int = 5
    def initialValue:Int = 10
    def name:String = "Will"
  }
}
