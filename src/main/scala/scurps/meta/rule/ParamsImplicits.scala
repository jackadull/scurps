package scurps.meta.rule

import scurps.meta.rule.Params.PCons

trait ParamsImplicits {
  implicit final class RichParams1[T1](params:PCons[T1,_]) {
    @inline def _1:T1 = params.head
  }
  implicit final class RichParams2[T2](params:PCons[_,PCons[T2,_]]) {
    @inline def _2:T2 = params.tail.head
  }
  implicit final class RichParams3[T3](params:PCons[_,PCons[_,PCons[T3,_]]]) {
    @inline def _3:T3 = params.tail.tail.head
  }
}
