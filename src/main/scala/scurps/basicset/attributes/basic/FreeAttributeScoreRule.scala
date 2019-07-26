package scurps.basicset.attributes.basic

import scurps.basicset.bib.G4e_Characters
import scurps.meta.Derivation.DefinedAsConstant
import scurps.meta.{Derivation, GameContext, Rule1}

object FreeAttributeScoreRule extends Rule1[FreeAttributeScore,Int] {
  override def apply(freeAttribute:FreeAttributeScore)(implicit context:GameContext):Derivation[Int] =
    DefinedAsConstant(freeAttribute, 10, G4e_Characters.Ch01_Creating_A_Character.Basic_Attributes.chapter.page(14))
}
