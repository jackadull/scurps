package net.jackadull.scurps.rules.advantages

trait AdvantageVariant {
  type VariantType<:AdvantageVariant
  def advantage:Advantage[VariantType]
  def group:AdvantageGroup[VariantType]
}
object AdvantageVariant {
  trait AdvantageVariantWithRepr[Repr<:AdvantageVariant] extends AdvantageVariant {
    this:Repr⇒
    type VariantType = Repr
    def advantage:Advantage[Repr] = Advantage(this, group, Set())
  }
  trait LeveledAdvantageVariant extends AdvantageVariant {def level:Int} // TODO no negative levels, level 0 == remove
  trait NamedAdvantageVariant extends AdvantageVariant {def name:String}
}
