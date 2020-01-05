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
      override def apply[A[+_]](params:Params1[A[BasicAttribute]], context:A[GameContext])(implicit ops:ScurpsOps[A]):A[IntScore] =
        context.get(Subject).get(params._1).orElse(Score(0).constant) // TODO accordingTo; if there is no subject, result should be undefined
    },
    FreeAttributeScore -> new Rule1[BasicAttribute,IntScore] {
      override def apply[A[+_]](params:Params1[A[BasicAttribute]], context:A[GameContext])(implicit ops:ScurpsOps[A]):A[IntScore] =
        ops.constant(Score(10)) // TODO accordingTo; Score(10).constant.forAny[BasicAttribute]
    },
    SetBasicAttribute -> new Rule2[BasicAttribute,IntScore,GameContext] {
      override def apply[A[+_]](params:Params2[A[BasicAttribute],A[IntScore]], context:A[GameContext])(implicit ops:ScurpsOps[A]):A[GameContext] = {
        val (attribute, newScore) = (params._1, params._2) // TODO unapply extractor
        context.mod(Subject) {_.updated(attribute, newScore :- FreeAttributeScore(p(attribute), context))} // TODO delete if zero; accordingTo
      }
    }
  )
}
