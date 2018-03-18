package net.jackadull.scurps.rules.skills

import net.jackadull.scurps.meta.rules.PropertyGet
import net.jackadull.scurps.meta.rules.PropertyGet.IntPropertyGet

// TODO further abstract controlling attribute, as a skill may also default to another skill
// TODO according to the rules, "skill level" is acutally = attribute score + levels bought -- but what is "levels bought" called correctly?
case class SkillRule(controllingAttribute:PropertyGet[ControllingAttribute], constrollingAttributeScore:IntPropertyGet, level:IntPropertyGet, levelsBought:IntPropertyGet)
