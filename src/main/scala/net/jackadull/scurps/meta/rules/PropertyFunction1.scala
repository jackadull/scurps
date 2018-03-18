package net.jackadull.scurps.meta.rules

import net.jackadull.scurps.context.GContext
import net.jackadull.scurps.meta.data.GCharacterData
import net.jackadull.scurps.meta.rules.PropertyFunction1.{PropertyFunction1Curried1, PropertyFunction1Proxy}
import net.jackadull.scurps.meta.rules.PropertyGet.PropertyGetProxy
import net.jackadull.scurps.rules.GRules

import scala.language.postfixOps

trait PropertyFunction1[-T1,+R] extends ((T1,GCharacterData,GContext)⇒R) {
  def curry1(v1:PropertyGet[T1]):PropertyGet[R] = {
    if(!this.isInstanceOf[PropertyFunction1Proxy[_,_]]) sys error s"curry1 should only be called on a proxy, to avoid binding to a specific rule implementation"
    if(!v1.isInstanceOf[PropertyGetProxy[_]]) sys error s"curry1 is called with a v1 that is not a proxy, but it should be, to avoid binding to a specific rule implementation"
    PropertyFunction1Curried1(this, v1)
  }
}
object PropertyFunction1 {
  abstract class PropertyFunction1Proxy[-T1,+R] extends PropertyFunction1[T1,R] {
    def apply(value:T1, data:GCharacterData, context:GContext):R = resolveRule(context rules)(value, data, context)
    def resolveRule(rules:GRules):PropertyFunction1[T1,R]
  }

  private case class PropertyFunction1Curried1[T1,+R](f:PropertyFunction1[T1,R], v1:PropertyGet[T1])
  extends PropertyGet[R] {
    def apply(data:GCharacterData, context:GContext):R = f(v1(data, context), data, context)
  }
}
