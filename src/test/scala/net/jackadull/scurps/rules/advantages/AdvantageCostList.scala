package net.jackadull.scurps.rules.advantages

import net.jackadull.scurps.rules.advantages.list._

object AdvantageCostList {
  val exampleAdvantageCosts:Map[Advantage[_<:AdvantageVariant],Int] = Map(
    `360° Vision`.advantage → 25,
    `Absolute Direction`.advantage → 5,
    `Absolute Direction (3D Spatial Sense)`.advantage → 10,
    `Absolute Timing`.advantage → 2,
    `Absolute Timing (Chronolocation)`.advantage → 5,
    `Acute Hearing`(1).advantage → 2,
    `Acute Hearing`(4).advantage → 8,
    `Acute Taste and Smell`(1).advantage → 2,
    `Acute Taste and Smell`(4).advantage → 8,
    `Acute Touch`(1).advantage → 2,
    `Acute Touch`(4).advantage → 8,
    `Acute Vision`(1).advantage → 2,
    `Acute Vision`(4).advantage → 8,
    `Altered Time Rate`(1).advantage → 100,
    `Altered Time Rate`(3).advantage → 300,
    `Alternate Identity (Illegal)`("Heimi Lipschnitz").advantage → 15,
    `Alternate Identity (Legal)`("Funky Punky").advantage → 5,
    Ambidexterity.advantage → 5,
    `Bad Smell`.advantage → -10
  )

  val exampleAdvantageCombinationCosts:Map[Set[Advantage[_<:AdvantageVariant]],Int] = Map(
    Set[Advantage[_<:AdvantageVariant]](`Absolute Direction (3D Spatial Sense)`.advantage,
      `Absolute Timing`.advantage) → 12,
    Set[Advantage[_<:AdvantageVariant]](`Acute Hearing`(1).advantage, `Acute Taste and Smell`(2).advantage,
      `Acute Touch`(3).advantage, `Acute Vision`(4).advantage) → 20,
    Set[Advantage[_<:AdvantageVariant]](`Altered Time Rate`(3).advantage,
      `Alternate Identity (Illegal)`("Heimi Lipschnitz").advantage) → 315
  )
}
