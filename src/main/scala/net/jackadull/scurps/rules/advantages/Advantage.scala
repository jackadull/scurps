package net.jackadull.scurps.rules.advantages

import scala.language.postfixOps

case class Advantage[A<:AdvantageVariant](variant:A, group:AdvantageGroup[A], modifiers:Set[AdvantageModifier])
