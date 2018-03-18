package net.jackadull.scurps.rules.attributes.secondary

import net.jackadull.scurps.models.damage.Damage
import net.jackadull.scurps.models.dice.Dice

/** Very close to the table in the rule book, but not very optimal in performance. For testing purposes only. */
object TestDamageTable extends (Int⇒Damage) {
  def apply(st:Int):Damage = if(st<0) TestDamageTable(0) else lookupTable get st match {
    case Some(dmg) ⇒ dmg
    case None ⇒
      if(st>100 && st%10==0) {
        val Damage(Dice(a,b), Dice(c,d)) = TestDamageTable(st-1)
        Damage(Dice(a+1,b), Dice(c+1,d))
      }
      else TestDamageTable(st-1)
  }

  private val lookupTable:Map[Int,Damage] = Map(
    0 → Damage(Dice(0, 0), Dice(0, 0)),
    1 → Damage(Dice(1, -6), Dice(1, -5)),
    2 → Damage(Dice(1, -6), Dice(1, -5)),
    3 → Damage(Dice(1, -5), Dice(1, -4)),
    4 → Damage(Dice(1, -5), Dice(1, -4)),
    5 → Damage(Dice(1, -4), Dice(1, -3)),
    6 → Damage(Dice(1, -4), Dice(1, -3)),
    7 → Damage(Dice(1, -3), Dice(1, -2)),
    8 → Damage(Dice(1, -3), Dice(1, -2)),
    9 → Damage(Dice(1, -2), Dice(1, -1)),
    10 → Damage(Dice(1, -2), Dice(1, 0)),
    11 → Damage(Dice(1, -1), Dice(1, 1)),
    12 → Damage(Dice(1, -1), Dice(1, 2)),
    13 → Damage(Dice(1, 0), Dice(2, -1)),
    14 → Damage(Dice(1, 0), Dice(2, 0)),
    15 → Damage(Dice(1, 1), Dice(2, 1)),
    16 → Damage(Dice(1, 1), Dice(2, 2)),
    17 → Damage(Dice(1, 2), Dice(3, -1)),
    18 → Damage(Dice(1, 2), Dice(3, 0)),
    19 → Damage(Dice(2, -1), Dice(3, 1)),
    20 → Damage(Dice(2, -1), Dice(3, 2)),
    21 → Damage(Dice(2, 0), Dice(4, -1)),
    22 → Damage(Dice(2, 0), Dice(4, 0)),
    23 → Damage(Dice(2, 1), Dice(4, 1)),
    24 → Damage(Dice(2, 1), Dice(4, 2)),
    25 → Damage(Dice(2, 2), Dice(5, -1)),
    26 → Damage(Dice(2, 2), Dice(5, 0)),
    27 → Damage(Dice(3, -1), Dice(5, 1)),
    28 → Damage(Dice(3, -1), Dice(5, 1)),
    29 → Damage(Dice(3, 0), Dice(5, 2)),
    30 → Damage(Dice(3, 0), Dice(5, 2)),
    31 → Damage(Dice(3, 1), Dice(6, -1)),
    32 → Damage(Dice(3, 1), Dice(6, -1)),
    33 → Damage(Dice(3, 2), Dice(6, 0)),
    34 → Damage(Dice(3, 2), Dice(6, 0)),
    35 → Damage(Dice(4, -1), Dice(6, 1)),
    36 → Damage(Dice(4, -1), Dice(6, 1)),
    37 → Damage(Dice(4, 0), Dice(6, 2)),
    38 → Damage(Dice(4, 0), Dice(6, 2)),
    39 → Damage(Dice(4, 1), Dice(7, -1)),
    40 → Damage(Dice(4, 1), Dice(7, -1)),
    45 → Damage(Dice(5, 0), Dice(7, 1)),
    50 → Damage(Dice(5, 2), Dice(8, -1)),
    55 → Damage(Dice(6, 0), Dice(8, 1)),
    60 → Damage(Dice(7, -1), Dice(9, 0)),
    65 → Damage(Dice(7, 1), Dice(9, 2)),
    70 → Damage(Dice(8, 0), Dice(10, 0)),
    75 → Damage(Dice(8, 2), Dice(10, 2)),
    80 → Damage(Dice(9, 0), Dice(11, 0)),
    85 → Damage(Dice(9, 2), Dice(11, 2)),
    90 → Damage(Dice(10, 0), Dice(12, 0)),
    95 → Damage(Dice(10, 2), Dice(12, 2)),
    100 → Damage(Dice(11, 0), Dice(13, 0))
  )
}
