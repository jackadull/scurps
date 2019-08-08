package scurps.basicset.attributes.basic

import scurps.basicset.bib.G4e_Characters
import scurps.meta.Derivation.DefinedAsConstant
import scurps.meta.Rule.Rule1
import scurps.meta.Score.IntScore
import scurps.meta.{Derivation, GameContext}

object FreeAttributeScoreRule extends Rule1[BasicAttribute,IntScore] {
  override def apply(attribute:BasicAttribute)(implicit context:GameContext):Derivation[IntScore] =
    DefinedAsConstant(FreeAttributeScore, IntScore(10),
      G4e_Characters.Ch01_Creating_A_Character.Basic_Attributes.chapter.page(14))
}
