package net.jackadull.scurps.meta.rules

import net.jackadull.scurps.context.GContext
import net.jackadull.scurps.meta.data.GCharacterData
import net.jackadull.scurps.meta.data.GCharacterData.Key
import net.jackadull.scurps.meta.rules.PropertyGet.PropertyGetProxy
import net.jackadull.scurps.rules.GRules

import scala.language.postfixOps

/** Abstract base that can read a property value, like [[PropertyGet]], and also update the value. */
trait PropertyGetSet[A] extends PropertyGet[A] {
  import net.jackadull.scurps.meta.rules.PropertyGetSet.{PropertyGetSetProxy, PropertyGetSetSumOnLeft}

  def mod(data:GCharacterData, context:GContext)(f:A⇒A):GCharacterData = {
    val prevValue = this(data, context)
    val newValue = f(prevValue)
    if(prevValue == newValue) data else set(newValue, data, context)
  }

  def set(newValue:A, data:GCharacterData, context:GContext):GCharacterData

  def :+(rhs:PropertyGet[A])(implicit num:Numeric[A]):PropertyGetSet[A] = {
    if(!this.isInstanceOf[PropertyGetSetProxy[_]]) sys error ":+ should only be called on a proxy, to avoid binding to a specific rule implementation"
    if(!rhs.isInstanceOf[PropertyGetProxy[_]]) sys error ":+ is called with an RHS that is not a proxy, but is should be, to avoid binding to a specific rule implementation"
    PropertyGetSetSumOnLeft(this, rhs)
  }
}
object PropertyGetSet {
  def ofKey[A](key:Key[A], defaultValue:A):PropertyGetSet[A] = PropertyGetSetForKey(key, defaultValue)

  type IntPropertyGetSet = PropertyGetSet[Int]
  type IntPropertyGetSetProxy = PropertyGetSetProxy[Int]

  abstract class PropertyGetSetProxy[A] extends PropertyGetProxy[A] with PropertyGetSet[A] {
    def set(newValue:A, data:GCharacterData, context:GContext):GCharacterData =
      resolveRule(context rules) set (newValue, data, context)
    def resolveRule(rules:GRules):PropertyGetSet[A]
  }

  private case class PropertyGetSetForKey[A](key:Key[A], defaultValue:A) extends PropertyGetSet[A] {
    def apply(data:GCharacterData, context:GContext):A = data getOrElse (key, defaultValue)
    def set(newValue:A, data:GCharacterData, context:GContext):GCharacterData =
      if(newValue == defaultValue) data - key else data updated (key, newValue)
  }

  private case class PropertyGetSetSumOnLeft[A](lhs:PropertyGetSet[A], rhs:PropertyGet[A])(implicit num:Numeric[A])
  extends PropertyGetSet[A] {
    def apply(data:GCharacterData, context:GContext):A = num plus (lhs(data, context), rhs(data, context))
    def set(newValue:A, data:GCharacterData, context:GContext):GCharacterData =
      lhs set (num minus (newValue, rhs(data, context)), data, context)
  }
}
