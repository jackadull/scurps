package net.jackadull.scurps.rules.advantages

import net.jackadull.scurps.models.senses.Sense._
import net.jackadull.scurps.rules.advantages.AdvantageStorageModel.PerKeySingletonAdvantage
import net.jackadull.scurps.rules.advantages.AdvantageVariantModel.NamedAndKeyedVariantModel
import net.jackadull.scurps.rules.advantages.list._

import scala.language.postfixOps

package object implementations {
  private[advantages] val all:Seq[AdvantageRule[_<:AdvantageVariant]] = Seq(Rule_360_Degree_Vision,
    Rule_Absolute_Direction, Rule_Absolute_Timing, Rule_Acute_Senses, Rule_Altered_Time_Rate, Rule_Alternate_Identity,
    Rule_Ambidexterity, Rule_Bad_Smell)

  private object Rule_360_Degree_Vision extends SingletonEnumerableImpl[Advantage_360_Degree_Vision](`360° Vision`) {
    def baseCPCostOfVariant(variant:Advantage_360_Degree_Vision):Int = 25
    def group:AdvantageGroup[Advantage_360_Degree_Vision] = Advantage_360_Degree_Vision
  }

  private object Rule_Absolute_Direction extends SingletonEnumerableImpl(
    `Absolute Direction`, `Absolute Direction (3D Spatial Sense)`) {
    def baseCPCostOfVariant(variant:Advantage_Absolute_Direction):Int =
      variant match {case `Absolute Direction` ⇒ 5; case `Absolute Direction (3D Spatial Sense)` ⇒ 10}
    def group:AdvantageGroup[Advantage_Absolute_Direction] = Advantage_Absolute_Direction
  }

  private object Rule_Absolute_Timing extends SingletonEnumerableImpl(
    `Absolute Timing`, `Absolute Timing (Chronolocation)`) {
    def baseCPCostOfVariant(variant:Advantage_Absolute_Timing):Int =
      variant match {case `Absolute Timing` ⇒ 2; case `Absolute Timing (Chronolocation)` ⇒ 5}
    def group:AdvantageGroup[Advantage_Absolute_Timing] = Advantage_Absolute_Timing
  }

  private object Rule_Acute_Senses extends LeveledAndKeyedImpl[Advantage_Acute_Senses,RegularSense](allRegularSenses) {
    def baseCPCostOfVariant(variant:Advantage_Acute_Senses):Int = 2 * (variant level)
    def group:AdvantageGroup[Advantage_Acute_Senses] = Advantage_Acute_Senses
    protected def keyOf(variant:Advantage_Acute_Senses):RegularSense = variant match {
      case `Acute Hearing`(_) ⇒ Hearing; case `Acute Taste and Smell`(_) ⇒ `Taste and Smell`
      case `Acute Touch`(_) ⇒ Touch; case `Acute Vision`(_) ⇒ Vision}
    protected def variantFor(key:RegularSense, level:Int):Advantage_Acute_Senses = key match {
      case Hearing ⇒ `Acute Hearing`(level); case `Taste and Smell` ⇒ `Acute Taste and Smell`(level)
      case Touch ⇒ `Acute Touch`(level); case Vision ⇒ `Acute Vision`(level)}
  }

  private object Rule_Altered_Time_Rate extends LeveledImpl[Advantage_Altered_Time_Rate] {
    def baseCPCostOfVariant(variant:Advantage_Altered_Time_Rate):Int = 100 * (variant level)
    def group:AdvantageGroup[Advantage_Altered_Time_Rate] = Advantage_Altered_Time_Rate
    protected def variantFor(level:Int):Advantage_Altered_Time_Rate = `Altered Time Rate`(level)
  }

  private object Rule_Alternate_Identity extends AdvantageRule[Advantage_Alternate_Identity] {
    def baseCPCostOfVariant(variant:Advantage_Alternate_Identity):Int =
      variant match {case `Alternate Identity (Legal)`(_) ⇒ 5; case `Alternate Identity (Illegal)`(_) ⇒ 15}
    def group:AdvantageGroup[Advantage_Alternate_Identity] = Advantage_Alternate_Identity
    val storageModel:AdvantageStorageModel[Advantage_Alternate_Identity] =
      new PerKeySingletonAdvantage[Advantage_Alternate_Identity,String](group)
      {def keyOf(advantage:Advantage[Advantage_Alternate_Identity]):String = (advantage variant) name}
    val variantModel:AdvantageVariantModel[Advantage_Alternate_Identity] =
      new NamedAndKeyedVariantModel[Advantage_Alternate_Identity,Boolean] {
        def keys:Set[Boolean] = Set(true,false)
        def variantFor(legal:Boolean, name:String):Advantage_Alternate_Identity =
          if(legal) `Alternate Identity (Legal)`(name) else `Alternate Identity (Illegal)`(name)
      }
  }

  private object Rule_Ambidexterity extends SingletonEnumerableImpl[Advantage_Ambidexterity](Ambidexterity) {
    def baseCPCostOfVariant(variant:Advantage_Ambidexterity):Int = 5
    def group:AdvantageGroup[Advantage_Ambidexterity] = Advantage_Ambidexterity
  }

  private object Rule_Bad_Smell extends SingletonEnumerableImpl[Disadvantage_Bad_Smell](`Bad Smell`) {
    def baseCPCostOfVariant(variant:Disadvantage_Bad_Smell):Int = -10
    def group:AdvantageGroup[Disadvantage_Bad_Smell] = Disadvantage_Bad_Smell
  }
}
