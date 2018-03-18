package net.jackadull.scurps.validation

import net.jackadull.scurps.testing.TestHierarchy
import net.jackadull.scurps.testing.TestHierarchy.testLeaf
import net.jackadull.scurps.validation.ValidationResult.{IssueParameters, ValidationIssue}
import org.scalatest.Assertions.fail

import scala.language.postfixOps
import scala.reflect.ClassTag

abstract class ValidationIssueParametersGeneratorBase[A<:IssueParameters]
(matchingIssueCode:String⇒Boolean)(implicit ev:ClassTag[A]) {
  protected def expectedMapEntries(params:A):Iterable[(String,Any)]

  def apply(validationResult:ValidationResult):Seq[TestHierarchy] = validationResult match {
    case ValidationIssue(issueCode, params) if matchingIssueCode(issueCode) ⇒ params match {
      case a:A ⇒ Seq(testLeaf("validating issue parameters") {
        val asMap = params.asMap
        expectedMapEntries(a) foreach {case (key, value) ⇒ asMap get key match {
          case Some(`value`) ⇒ ()
          case Some(unexpectedValue) ⇒ fail(s"expected issue parameter entry '$key': '$value' for issue code '$issueCode', but found value: $unexpectedValue")
          case None ⇒ fail(s"expected issue parameter entry '$key' for issue code '$issueCode', but no such entry found")
        }}
        val expectedEntryKeys:Set[String] = expectedMapEntries(a) map {_ _1} toSet;
        asMap.keys foreach {foundKey⇒
          if(!expectedEntryKeys(foundKey)) fail(s"unexpected key '$foundKey' found in issue parameters for issue code '$issueCode'")
        }
      })
      case _ ⇒ Seq(testLeaf("validating issue parameters") {
        fail(s"expected issue parameters matching $ev for issue code '$issueCode', but found: $params")
      })
    }
    case _ ⇒ Seq()
  }
}
