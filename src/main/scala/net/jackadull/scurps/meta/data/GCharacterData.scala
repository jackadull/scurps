package net.jackadull.scurps.meta.data

import net.jackadull.scurps.meta.data.GCharacterData.Key

import scala.language.postfixOps

/** A key-value map that contains the raw data of a '''GURPS''' character, or other entity that has character-like
  * traits. Think of this as a raw key-value map with just the bare data that needs to be stored for a character,
  * without any further rules or computation associated with it. Instances of `GCharacterData` are used by rule
  * implementations in order to find out the basic values of a character. If one were to store the character in a
  * serialized form, this would be equivalent to store the `GCharacterData` of it.
  *
  * Unlike a regular `scala.collection.Map`, not all the values are of the same type. This is because a character's
  * name, his or her strength value, and his or her weight are of different data types. Therefore, the `GCharacterData`
  * kind of map uses special keys that define the type of value for that particular key.
  *
  * Note that, quite like `scala.collection.Map`, not every key needs to have a value associated in a particular
  * `GCharacterData` instance, not even key-value pairs that model mandatory attributes of a character, such as the
  * strength value. Therefore, every caller must take into account that even crucial values can be missing, and model
  * a reasonable default for such cases. */
trait GCharacterData {
  def +[A](kv:(Key[A],A)):GCharacterData
  def -[A](k:Key[A]):GCharacterData
  def get[A](key:Key[A]):Option[A]
  def size:Int

  def contains[A](k:Key[A]):Boolean = get(k) isDefined
  def getOrElse[A](k:Key[A], default: ⇒A):A = get(k) getOrElse default
  def isEmpty:Boolean = size==0
  def nonEmpty:Boolean = !isEmpty
  def updated[A](key:Key[A], value:A):GCharacterData = this + (key→value)
}
object GCharacterData {
  val empty:GCharacterData = GCharacterDataImpl(Map())

  trait Key[A]
}
