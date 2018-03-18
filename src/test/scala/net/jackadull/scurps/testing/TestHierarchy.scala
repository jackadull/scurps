package net.jackadull.scurps.testing

import org.scalactic.source.Position
import org.scalatest.Assertions.withClue
import org.scalatest.exceptions.{StackDepthException, TestFailedException}

import scala.language.postfixOps

sealed trait TestHierarchy {
  def description:String
}
object TestHierarchy {
  def testLeaf(description:String)(f: ⇒Unit):TestLeaf = new TestLeaf(description) {def execute() = f}

  def inline(hierarchy:TestHierarchy) {hierarchy match {
    case TestBranch(description, children) ⇒ withClue(s"[$description]") {inline(children)}
    case leaf:TestLeaf ⇒ withClue(s"[${leaf description}]") {leaf execute()}
  }}

  def inline(hierarchy:Seq[TestHierarchy]) {hierarchy foreach inline}

  def assert(condition:Boolean)(implicit pos:Position)
  {if(!condition) throw new TestFailedException({_:StackDepthException ⇒ None}, None, pos)}

  def assert(condition:Boolean, errorMessage:String)(implicit pos:Position)
  {if(!condition) throw new TestFailedException({_:StackDepthException ⇒ Some(errorMessage)}, None, pos)}

  def fail(errorMessage:String)(implicit pos:Position)
  {throw new TestFailedException({_:StackDepthException ⇒ Some(errorMessage)}, None, pos)}

  final case class TestBranch(description:String, children:Seq[TestHierarchy]) extends TestHierarchy
  abstract class TestLeaf(val description:String) extends TestHierarchy {def execute()}
}
