package scurps.basicset.attributes.basic

import scurps.meta.Derivation.DefinedAsConstant
import scurps.meta.{Derivation, GameContext, Rule1}

object FreeAttributeScoreRule extends Rule1[FreeAttributeScore,Int] {
  override def apply(freeAttribute:FreeAttributeScore)(implicit context:GameContext):Derivation[Int] =
    DefinedAsConstant(freeAttribute, 10, ???) // TODO
}
