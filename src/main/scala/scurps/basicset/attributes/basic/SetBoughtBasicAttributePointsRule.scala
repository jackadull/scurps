package scurps.basicset.attributes.basic

import scurps.meta.Rule.Rule1
import scurps.meta.Score.IntScore
import scurps.meta.{Derivation, GameContext}

final case class SetBoughtBasicAttributePointsRule(attribute:BasicAttribute) extends Rule1[IntScore,GameContext] {
  override def apply(newBoughtPoints:IntScore)(implicit context:GameContext):Derivation[GameContext] = ???
  /* TODO `map` got removed
    context(Subject).map {subject =>
      val valueKey = BoughtBasicAttributePoints(attribute)
      val newSubject =
        if(newBoughtPoints.intValue == 0) subject.removed(valueKey) else subject.updated(valueKey, newBoughtPoints)
      context.updated(Subject, newSubject)
    }
   */
}
