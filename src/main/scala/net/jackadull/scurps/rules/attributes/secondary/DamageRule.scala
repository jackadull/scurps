package net.jackadull.scurps.rules.attributes.secondary

import net.jackadull.scurps.context.GContext
import net.jackadull.scurps.meta.data.GCharacterData
import net.jackadull.scurps.models.damage.Damage
import net.jackadull.scurps.models.dice.Dice
import net.jackadull.scurps.rules.GRules

import scala.language.postfixOps

object DamageRule extends NonAdjustableSecondaryCharacteristicRule.Specific[Damage] {
  private[attributes] def defaultLevels(data:GCharacterData, context:GContext) =
    context.rules.strengthRule.score(data, context)

  private[attributes] def levelsToScore(levels:Int) = levels match {
    case ltz if ltz<0 ⇒ damageTableUpTo39(0)
    case lt40 if lt40<40 ⇒ damageTableUpTo39(lt40)
    case lt101 if lt101<101 ⇒ damageTableFrom40To100In5Steps((lt101-40)/5)
    case gt100 ⇒
      val extraDice = (gt100-100)/10
      Damage(Dice(11+extraDice,0), Dice(13+extraDice,0))
  }

  private[attributes] def resolveSCRule(rules:GRules) = rules damageRule

  private val damageTableUpTo39:Vector[Damage] = Vector(Damage(Dice(0,0), Dice(0,0)), Damage(Dice(1,-6), Dice(1,-5)),
    Damage(Dice(1,-6), Dice(1,-5)), Damage(Dice(1,-5), Dice(1,-4)), Damage(Dice(1,-5), Dice(1,-4)),
    Damage(Dice(1,-4), Dice(1,-3)), Damage(Dice(1,-4), Dice(1,-3)), Damage(Dice(1,-3), Dice(1,-2)),
    Damage(Dice(1,-3), Dice(1,-2)), Damage(Dice(1,-2), Dice(1,-1)), Damage(Dice(1,-2), Dice(1,0)),
    Damage(Dice(1,-1), Dice(1,+1)), Damage(Dice(1,-1), Dice(1,+2)), Damage(Dice(1,0), Dice(2,-1)),
    Damage(Dice(1,0), Dice(2,0)), Damage(Dice(1,1), Dice(2,1)), Damage(Dice(1,1), Dice(2,2)),
    Damage(Dice(1,2), Dice(3,-1)), Damage(Dice(1,2), Dice(3,0)), Damage(Dice(2,-1), Dice(3,1)),
    Damage(Dice(2,-1), Dice(3,2)), Damage(Dice(2,0), Dice(4,-1)), Damage(Dice(2,0), Dice(4,0)),
    Damage(Dice(2,1), Dice(4,1)), Damage(Dice(2,1), Dice(4,2)), Damage(Dice(2,2), Dice(5,-1)),
    Damage(Dice(2,2), Dice(5,0)), Damage(Dice(3,-1), Dice(5,1)), Damage(Dice(3,-1), Dice(5,1)),
    Damage(Dice(3,0), Dice(5,2)), Damage(Dice(3,0), Dice(5,2)), Damage(Dice(3,1), Dice(6,-1)),
    Damage(Dice(3,1), Dice(6,-1)), Damage(Dice(3,2), Dice(6,0)), Damage(Dice(3,2), Dice(6,0)),
    Damage(Dice(4,-1), Dice(6,1)), Damage(Dice(4,-1), Dice(6,1)), Damage(Dice(4,0), Dice(6,2)),
    Damage(Dice(4,0), Dice(6,2)), Damage(Dice(4,1), Dice(7,-1)))

  private val damageTableFrom40To100In5Steps:Vector[Damage] = Vector(Damage(Dice(4,1), Dice(7,-1)),
    Damage(Dice(5,0), Dice(7,+1)), Damage(Dice(5,2), Dice(8,-1)), Damage(Dice(6,0), Dice(8,+1)),
    Damage(Dice(7,-1), Dice(9,0)), Damage(Dice(7,1), Dice(9,2)), Damage(Dice(8,0), Dice(10,0)),
    Damage(Dice(8,2), Dice(10,2)), Damage(Dice(9,0), Dice(11,0)), Damage(Dice(9,+2), Dice(11,+2)),
    Damage(Dice(10,0), Dice(12,0)), Damage(Dice(10,2), Dice(12,2)), Damage(Dice(11,0), Dice(13,0)))
}
