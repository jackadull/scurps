package net.jackadull.scurps.rules.attributes.basic

import net.jackadull.scurps.testing.Modification

case class BasicAttributeTestModel(name:String, cpCostPerLevel:Int) {
  def modSetScore[A](newScore:Int, accessor:BasicAttributesTestAccessor[A]):Modification[A] =
    new Modification[A] {
      def apply(v:A):A = accessor.setBasicAttributeScore(BasicAttributeTestModel.this, newScore, v)
      def description:String = s"$name: $newScore"
    }

  override def toString = name
}
object BasicAttributeTestModel {
  lazy val all:Seq[BasicAttributeTestModel] = Seq(StrengthTestModel, DexterityTestModel, IntelligenceTestModel,
    HealthTestModel)

  val DexterityTestModel = BasicAttributeTestModel("DX", 20)
  val HealthTestModel = BasicAttributeTestModel("HT", 10)
  val IntelligenceTestModel = BasicAttributeTestModel("IQ", 20)
  val StrengthTestModel = BasicAttributeTestModel("ST", 10)
}
