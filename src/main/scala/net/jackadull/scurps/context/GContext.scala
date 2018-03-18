package net.jackadull.scurps.context

import net.jackadull.scurps.context.campaign.CampaignContext
import net.jackadull.scurps.rules.GRules

import scala.language.postfixOps

/** Combines [[GRules rules]] and game situation specific properties into generic contextual information, to be used by
  * all implementations that do rule-spefic computation.
  *
  * Rule implementations need a context for two main reaons:
  *
  * 1. Rules can refer to other rules. For example, a rule for computing a character's hit points has to refer to the
  * rule that determines a character's strength score.
  *
  * 2. Even though many rules are described in a very straightforward, simple manner in the '''GURPS''' rule books,
  * other rules can always add cases that require special treatment. For example, after reading the chapter on
  * '''GURPS''' basic attributes, one might have the impression that in order to determine a character's strength
  * value, it is sufficient to read the value of a numerical strength field of the character and simply return it.
  * However, here is an example that shows it is not so easy: Simply imagine a new advantage, "Werewolf", that causes
  * the character's strength to increase by two levels, ''but only during the night when it is a full moon''. This
  * would mean that in order to determine a character's strength score, it is not enough any more to know just the
  * data of the character alone, but the rule must also be able to access the current time of day, and even be able to
  * determine if the moon is currently full or not.
  *
  * The [[GContext]] allows for such arbitrary dependencies by passing on the rules and arbitrary, situation-specific
  * properties through the rule implementations. */
case class GContext(
  campaign:CampaignContext,
  rules:GRules
)
object GContext {
  val default:GContext = GContext(
    campaign = CampaignContext default,
    rules = GRules default
  )
}