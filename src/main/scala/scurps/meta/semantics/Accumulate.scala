package scurps.meta.semantics

trait Accumulate[A,T] {def accumulate(accumulator:A, value:T):A}
