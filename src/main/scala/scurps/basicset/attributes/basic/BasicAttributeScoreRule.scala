package scurps.basicset.attributes.basic

import scurps.meta.Derivation.{Defined, Undefined}
import scurps.meta.Score.IntScore
import scurps.meta.{Derivation, GameContext, Rule1}

object BasicAttributeScoreRule extends Rule1[BasicAttribute,IntScore] {
  override def apply(attribute:BasicAttribute)(implicit context:GameContext):Derivation[IntScore] =
    (context(FreeAttributeScore, attribute), context(BoughtBasicAttributePoints, attribute)) match {
      case (freeD:Defined[IntScore], boughtD:Defined[IntScore]) => ??? // TODO sum
      case (undefined:Undefined, _) => undefined
      case (_, undefined:Undefined) => undefined
    }
}
