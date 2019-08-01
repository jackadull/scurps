package scurps.basicset.attributes.basic

import scurps.basicset.bib.G4e_Characters
import scurps.meta.Derivation.DefinedAsConstant
import scurps.meta.Score.IntScore
import scurps.meta.{Derivation, GameContext, Rule0}

final case class FreeAttributeScoreRule(attribute:BasicAttribute) extends Rule0[IntScore] {
  override def apply()(implicit context:GameContext):Derivation[IntScore] =
    DefinedAsConstant(FreeAttributeScore(attribute), IntScore(10),
      G4e_Characters.Ch01_Creating_A_Character.Basic_Attributes.chapter.page(14))
}
