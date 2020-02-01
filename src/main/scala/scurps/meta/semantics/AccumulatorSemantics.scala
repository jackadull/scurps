package scurps.meta.semantics

trait AccumulatorSemantics[A,T] {def accumulate(accumulator:A, value:T):A}
