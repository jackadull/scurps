package net.jackadull.scurps.meta.rules

import net.jackadull.scurps.context.GContext
import net.jackadull.scurps.meta.data.GCharacterData
import net.jackadull.scurps.rules.GRules

import scala.language.postfixOps

/** Abstract base for a rule that determines a certain property's value of a certain type. */
trait PropertyGet[+A] extends ((GCharacterData,GContext)⇒A) {
  import net.jackadull.scurps.meta.rules.PropertyGet.{PropertyGetProduct, PropertyGetProxy}

  def :*[B>:A](rhs:PropertyGet[B])(implicit num:Numeric[B]):PropertyGet[B] = {
    if(!this.isInstanceOf[PropertyGetProxy[_]]) sys error ":* should only be called on a proxy, to avoid binding to a specific rule implementation"
    if(!rhs.isInstanceOf[PropertyGetProxy[_]]) sys error ":* is called with an RHS that is not a proxy, but is should be, to avoid binding to a specific rule implementation"
    PropertyGetProduct(this, rhs)
  }
}
object PropertyGet {
  def constant(v:Int):IntPropertyGet = ConstantPropertyGet(v)

  type IntPropertyGet = PropertyGet[Int]
  type IntPropertyGetProxy = PropertyGetProxy[Int]

  abstract class PropertyGetProxy[+A] extends PropertyGet[A] {
    def apply(data:GCharacterData, context:GContext):A = resolveRule(context rules)(data, context)
    def resolveRule(rules:GRules):PropertyGet[A]
  }

  private case class ConstantPropertyGet[A](constantValue:A) extends PropertyGet[A] {
    def apply(data:GCharacterData, context:GContext):A = constantValue
  }

  private case class PropertyGetProduct[+A](lhs:PropertyGet[A], rhs:PropertyGet[A])(implicit num:Numeric[A])
  extends PropertyGet[A] {
    def apply(data:GCharacterData, context:GContext):A = num times (lhs(data, context), rhs(data, context))
  }
}
