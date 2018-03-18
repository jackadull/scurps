package net.jackadull.scurps.validation

import net.jackadull.scurps.context.GContext
import net.jackadull.scurps.meta.data.GCharacterData

trait GValidation extends ((GCharacterData,GContext)⇒ValidationResult) {
  def apply(data:GCharacterData, context:GContext):ValidationResult
  def id:String
  // TODO rulebook reference
}
