package net.jackadull.scurps.validation

import net.jackadull.scurps.validation.implementations.DisadvantageLimitValidation

import scala.collection.SortedMap
import scala.language.postfixOps

trait GValidations {
  def all:Iterable[GValidation]
  def get(id:String):Option[GValidation]
  def put(validation:GValidation):GValidations
  def putAll(validations:GValidation*):GValidations = validations.foldLeft(this) {_ put _}
}
object GValidations {
  val empty:GValidations = DefaultImpl(SortedMap())

  private[scurps] val default:GValidations = empty.putAll(DisadvantageLimitValidation)

  private case class DefaultImpl(map:SortedMap[String,GValidation]) extends GValidations {
    def all:Iterable[GValidation] = map values
    def get(id:String):Option[GValidation] = map get id
    def put(validation:GValidation):GValidations = map get (validation id) match {
      case Some(`validation`) ⇒ this
      case _ ⇒ copy(map = map + ((validation id) → validation))
    }
  }
}
