package scurps

package object meta {
  trait ContextKey[V]
  trait RuleKey[R<:Rule]
}
