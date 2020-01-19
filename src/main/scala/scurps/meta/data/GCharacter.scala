package scurps.meta.data

import scurps.meta.data.PMap.MapBasedPMap

trait GCharacter extends PMap[GCharacterProperty] with PMapLike[GCharacterProperty,GCharacter]
object GCharacter {
  val empty:GCharacter = Impl(Map.empty)

  private final case class Impl(baseMap:Map[GCharacterProperty[_],Any])
  extends GCharacter with MapBasedPMap[GCharacterProperty,Impl] {
    override protected def withBaseMap(newBaseMap:Map[GCharacterProperty[_],Any]):Impl = copy(newBaseMap)
    override def toString:String = s"SCharacter$baseMap"
  }
}
