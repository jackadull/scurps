package net.jackadull.scurps.validation

sealed trait ValidationResult
object ValidationResult {
  object Okay extends ValidationResult
  case class ValidationIssue(code:String, parameters:IssueParameters) extends ValidationResult

  trait IssueParameters {
    def asMap:Map[String,Any]
  }
}
