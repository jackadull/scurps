package scurps.basicset.attributes.basic

import scurps.meta.ContextKey.Subject
import scurps.meta.Derivation.{DefaultZero, MissingContextValue, StoredValue}
import scurps.meta.Score.IntScore
import scurps.meta.{Derivation, GameContext, Rule1}

case object BoughtBasicAttributePointsRule extends Rule1[BasicAttribute,IntScore] {
  override def apply(attribute:BasicAttribute)(implicit context:GameContext):Derivation[IntScore] =
    context.get(Subject) match {
      case None => MissingContextValue(Subject)
      case Some(subject) =>
        val valueKey = BoughtBasicAttributePoints(attribute)
        subject.get(valueKey) match {
          case None => DefaultZero(valueKey, IntScore(0))
          case Some(storedValue) => StoredValue(valueKey, storedValue)
        }
    }
}
