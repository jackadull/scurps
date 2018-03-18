package net.jackadull.scurps.meta.data

import net.jackadull.scurps.meta.data.GCharacterData.Key
import net.jackadull.scurps.testing.TestHierarchy
import net.jackadull.scurps.testing.TestHierarchy.{TestBranch, TestLeaf, assert}

object GCharacterDataTestGenerator {
  def globalInvariants(data:GCharacterData):Seq[TestHierarchy] = Seq(
    new TestLeaf("isEmpty is the same as size==0") {def execute() {assert(data.isEmpty==(data.size==0))}},
    new TestLeaf("isEmpty is the same as !nonEmpty") {def execute() {assert(data.isEmpty== !data.nonEmpty)}},
    new TestLeaf("adding the same k/v pair returns the identical instance") {def execute() {
      val key = new Key[Int]{}
      val data2 = data + (key → 23)
      val data3 = data2 + (key → 23)
      assert(data2 eq data3)
    }},
    new TestLeaf("after adding a k/v pair, the value is contained") {def execute() {
      val key = new Key[Int]{}
      val data2 = data + (key → 23)
      assert(data2 contains key, "contains is false, even though the pair was added")
      val contained = data2 get key
      val expected = Some(23)
      assert(contained == expected, s"contained value was $contained, but expected $expected")
    }},
    new TestLeaf("after adding a k/v pair, then removing it, the value is not contained") {def execute() {
      val key = new Key[Int]{}
      val data2 = data + (key → 23)
      val data3 = data2 - key
      assert(!(data3 contains key), "contains is true, even though the key was removed")
      val contained = data3 get key
      val expected = None
      assert(contained == expected, s"contained value was $contained, but expected $expected")
    }}
  )
  def initialState(data:GCharacterData):Seq[TestHierarchy] = Seq(
    new TestLeaf("empty character data has size 0") {def execute() {assert(data.size == 0)}},
    TestBranch("invariants for initial character data", globalInvariants(data)),
    TestBranch("invariants for initial character data, plus one property k/v pair", globalInvariants(
      data + (new Key[Int]{} → 0)
    ))
  )
}
