package scurps.basic_set.attributes

import scurps._
import scurps.meta.algebra.ScurpsOps
import scurps.meta.context.ContextKey.Subject
import scurps.meta.context.GameContext
import scurps.meta.data.Score
import scurps.meta.data.Score.IntScore
import scurps.meta.rule.Rule.{Rule1, Rule2}
import scurps.meta.rule.RuleCatalog

package object basic {
  val basicSetRules:RuleCatalog = RuleCatalog(
    BasicAttributeScore -> new Rule1[BasicAttribute,IntScore] {
      override def apply[A[+_]](attribute:A[BasicAttribute], context:A[GameContext])(implicit ops:ScurpsOps[A]):A[IntScore] =
        FreeAttributeScore(attribute, context) :+ BoughtBasicAttributePoints(attribute, context) // TODO accordingTo
    },
    BoughtBasicAttributePoints -> new Rule1[BasicAttribute,IntScore] {
      override def apply[A[+_]](attribute:A[BasicAttribute], context:A[GameContext])(implicit ops:ScurpsOps[A]):A[IntScore] =
        context.get(Subject).ifDefined(_.get(BoughtBasicAttributePoints.of(attribute)).orElse(Score(0))) // TODO accordingTo
    },
    FreeAttributeScore -> new Rule1[BasicAttribute,IntScore] {
      override def apply[A[+_]](attribute:A[BasicAttribute], context:A[GameContext])(implicit ops:ScurpsOps[A]):A[IntScore] =
        context.get(Subject).ifDefined(_ => Score(10)) // TODO accordingTo
    },
    SetBasicAttribute -> new Rule2[BasicAttribute,IntScore,GameContext] {
      override def apply[A[+_]](attribute:A[BasicAttribute], newScore:A[IntScore], context:A[GameContext])(implicit ops:ScurpsOps[A]):A[GameContext] = {
        val newBoughtPoints = newScore :- FreeAttributeScore(attribute, context)
        val boughtPointsKey = BoughtBasicAttributePoints.of(attribute)
        newBoughtPoints.ifZero(
          _then = context.mod(Subject) {_.removed(boughtPointsKey)},
          _else = context.mod(Subject) {_.updated(boughtPointsKey, newBoughtPoints)}
        ) // TODO accordingTo; the ifZero construct might become a useful implicit
      }
    }
  )
}
