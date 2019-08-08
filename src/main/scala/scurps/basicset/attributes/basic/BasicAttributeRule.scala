package scurps.basicset.attributes.basic

import scurps.meta.Derivation.{Defined, Undefined}
import scurps.meta.Rule.Rule0
import scurps.meta.Score.IntScore
import scurps.meta.{Derivation, GameContext}

final case class BasicAttributeRule(attribute:BasicAttribute) extends Rule0[IntScore] {
  override def apply()(implicit context:GameContext):Derivation[IntScore] =
    (FreeAttributeScore.derivation(attribute), BoughtBasicAttributePoints.derivation(attribute)) match {
      case (freeD:Defined[IntScore], boughtD:Defined[IntScore]) => ??? // TODO sum
      case (undefined:Undefined, _) => undefined
      case (_, undefined:Undefined) => undefined
    }
}
