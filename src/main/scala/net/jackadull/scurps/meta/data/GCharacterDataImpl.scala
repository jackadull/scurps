package net.jackadull.scurps.meta.data

import net.jackadull.scurps.meta.data.GCharacterData.Key

import scala.language.postfixOps

private case class GCharacterDataImpl(map:Map[Key[_],Any]) extends GCharacterData {
  def +[A](kv:(Key[A], A)):GCharacterData = get(kv _1) match {
    case Some(givenValue) if givenValue == kv._2 ⇒ this
    case _ ⇒ copy(map = map + kv)
  }
  def -[A](k:Key[A]):GCharacterData = if(map contains k) copy(map = map - k) else this
  def get[A](key:Key[A]):Option[A] = (map get key).asInstanceOf[Option[A]]
  def size:Int = map size
}
