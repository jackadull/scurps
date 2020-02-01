package scurps.meta.algebra

object Semantic {
  trait Accumulate[A,T] {def accumulate(accumulator:A, value:T):A}

  trait Cons[C[_]] {
    def cons[T](head:T, tail:C[T]):C[T]
    def empty[T]:C[T]
  }

  trait IsElement[-C[_]] {def isElement[E](collection:C[E], element:E):Boolean}

  trait Uncons[C[_]] {def uncons[T](collection:C[T]):Option[(T,C[T])]}
}
