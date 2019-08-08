package scurps.basicset.attributes.basic

import scurps.meta.ContextKey.Subject
import scurps.meta.Derivation.{MissingContextValue, Plain}
import scurps.meta.Rule.Rule1
import scurps.meta.Score.IntScore
import scurps.meta.{Derivation, GameContext}

final case class SetBoughtBasicAttributePointsRule(attribute:BasicAttribute) extends Rule1[IntScore,GameContext] {
  override def apply(newBoughtPoints:IntScore)(implicit context:GameContext):Derivation[GameContext] =
    context.get(Subject) match {
      case None => MissingContextValue(Subject)
      case Some(subject) =>
        val valueKey = BoughtBasicAttributePoints(attribute)
        val newSubject =
          if(newBoughtPoints.intValue == 0) subject.removed(valueKey) else subject.updated(valueKey, newBoughtPoints)
        Plain(context.updated(Subject, newSubject))
    }
}
