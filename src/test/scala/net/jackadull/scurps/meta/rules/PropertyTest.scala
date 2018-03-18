package net.jackadull.scurps.meta.rules

import net.jackadull.scurps.context.GContext
import net.jackadull.scurps.meta.data.GCharacterData
import net.jackadull.scurps.meta.data.GCharacterData.Key
import net.jackadull.scurps.meta.rules.PropertyFunction1.PropertyFunction1Proxy
import net.jackadull.scurps.meta.rules.PropertyGet.PropertyGetProxy
import net.jackadull.scurps.meta.rules.PropertyGetSet.PropertyGetSetProxy
import net.jackadull.scurps.rules.GRules
import org.scalatest.{FreeSpec, Matchers}

import scala.language.postfixOps

class PropertyTest extends FreeSpec with Matchers {
  object ExamplePropertyFunction1 extends PropertyFunction1[String,Int]
  {def apply(str:String, data:GCharacterData, context:GContext):Int = str length}

  object ExamplePropertyFunction1Proxy extends PropertyFunction1Proxy[String,Int]
  {def resolveRule(rules:GRules):PropertyFunction1[String,Int] = ExamplePropertyFunction1}

  object ExamplePropertyGetInt1 extends PropertyGet[Int]
  {def apply(data:GCharacterData, context:GContext):Int = 47}

  object ExamplePropertyGetInt1Proxy extends PropertyGetProxy[Int]
  {def resolveRule(rules:GRules):PropertyGet[Int] = ExamplePropertyGetInt1}

  object ExamplePropertyGetInt2 extends PropertyGet[Int]
  {def apply(data:GCharacterData, context:GContext):Int = 11}

  object ExamplePropertyGetInt2Proxy extends PropertyGetProxy[Int]
  {def resolveRule(rules:GRules):PropertyGet[Int] = ExamplePropertyGetInt2}

  object ExamplePropertyGetSetInt extends PropertyGetSet[Int] {
    private val key = new Key[Int]{}
    def apply(data:GCharacterData, ctx:GContext):Int = data getOrElse (key, 0)
    def set(newValue:Int, data:GCharacterData, context:GContext):GCharacterData = data + (key → newValue)
  }

  object ExamplePropertyGetSetIntProxy extends PropertyGetSetProxy[Int]
  {def resolveRule(rules:GRules):PropertyGetSet[Int] = ExamplePropertyGetSetInt}

  object ExamplePropertyGetString extends PropertyGet[String]
  {def apply(data:GCharacterData, context:GContext):String = "testing"}

  object ExamplePropertyGetStringProxy extends PropertyGetProxy[String]
  {def resolveRule(rules:GRules):PropertyGet[String] = ExamplePropertyGetString}

  "PropertyFunction1" - {
    "cannot be curried when" - {
      "both function and lhs are non-proxies" in
      {assertThrows[Exception] {ExamplePropertyFunction1 curry1 ExamplePropertyGetString}}
      "function is a non-proxy, lhs is a proxy" in
      {assertThrows[Exception] {ExamplePropertyFunction1Proxy curry1 ExamplePropertyGetString}}
      "function is a proxy, lhs is a non-proxy" in
      {assertThrows[Exception] {ExamplePropertyFunction1 curry1 ExamplePropertyGetStringProxy}}
    }
  }
  "PropertyGet" - {
    "cannot be combined when" - {
      "both properties are non-proxies" in
      {assertThrows[Exception] {ExamplePropertyGetInt1 :* ExamplePropertyGetInt2}}
      "lhs property is a non-proxy, lhs is a proxy" in
      {assertThrows[Exception] {ExamplePropertyGetInt1 :* ExamplePropertyGetInt2Proxy}}
      "lhs property is a proxy, rhs is a non-proxy" in
      {assertThrows[Exception] {ExamplePropertyGetInt1Proxy :* ExamplePropertyGetInt2}}
    }
  }
  "PropertyGetSet" - {
    "cannot be combined when" - {
      "both properties are non-proxies" in
        {assertThrows[Exception] {ExamplePropertyGetSetInt :+ ExamplePropertyGetInt1}}
      "lhs property is a non-proxy, lhs is a proxy" in
        {assertThrows[Exception] {ExamplePropertyGetSetInt :+ ExamplePropertyGetInt1Proxy}}
      "lhs property is a proxy, rhs is a non-proxy" in
        {assertThrows[Exception] {ExamplePropertyGetSetIntProxy :+ ExamplePropertyGetInt1}}
    }
  }
}
