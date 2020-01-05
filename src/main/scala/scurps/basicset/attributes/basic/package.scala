package scurps.basicset.attributes

import scurps._
import scurps.meta.algebra.ScurpsOps
import scurps.meta.context.ContextKey.Subject
import scurps.meta.context.GameContext
import scurps.meta.data.Score
import scurps.meta.data.Score.IntScore
import scurps.meta.rule.Params.{Params1, Params2, p}
import scurps.meta.rule.Rule.{Rule1, Rule2}
import scurps.meta.rule.RuleCatalog

package object basic {
  val basicSetRules:RuleCatalog = RuleCatalog(
    BasicAttributeScore -> new Rule1[BasicAttribute,IntScore] {
      override def apply[A[+_]](params:Params1[A[BasicAttribute]], context:A[GameContext])(implicit ops:ScurpsOps[A]):A[IntScore] =
        FreeAttributeScore(params, context) :+ BoughtBasicAttributePoints(params, context) // TODO accordingTo
    },
    BoughtBasicAttributePoints -> new Rule1[BasicAttribute,IntScore] {
      override def apply[A[+_]](params:Params1[A[BasicAttribute]], context:A[GameContext])(implicit ops:ScurpsOps[A]):A[IntScore] = {
        val Params1(attribute) = params
        context.get(Subject).ifDefined(_.get(BoughtBasicAttributePoints.of(attribute)).orElse(Score(0).constant)) // TODO accordingTo
      }
    },
    FreeAttributeScore -> new Rule1[BasicAttribute,IntScore] {
      override def apply[A[+_]](params:Params1[A[BasicAttribute]], context:A[GameContext])(implicit ops:ScurpsOps[A]):A[IntScore] =
        context.get(Subject).ifDefined(_ => Score(10).constant) // TODO accordingTo
    },
    SetBasicAttribute -> new Rule2[BasicAttribute,IntScore,GameContext] {
      override def apply[A[+_]](params:Params2[A[BasicAttribute],A[IntScore]], context:A[GameContext])(implicit ops:ScurpsOps[A]):A[GameContext] = {
        val Params2(attribute, newScore) = params
        val newBoughtPoints = newScore :- FreeAttributeScore(p(attribute), context)
        val boughtPointsKey = BoughtBasicAttributePoints.of(attribute)
        newBoughtPoints.ifZero(
          _then = context.mod(Subject) {_.removed(boughtPointsKey)},
          _else = context.mod(Subject) {_.updated(boughtPointsKey, newBoughtPoints)}
        ) // TODO accordingTo; the ifZero construct might become a useful implicit
      }
    }
  )
}
