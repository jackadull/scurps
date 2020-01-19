package scurps.meta.algebra

object Optic {
  trait Setter[S,-T] {def set(source:S, newValue:T):S}
  trait Unsetter[S] {def unset(source:S):S}

  trait OptionGetter[-S,+T] {def getOption(source:S):Option[T]}
  trait OptionSetter[S,-T] extends Setter[S,T] with Unsetter[S]

  trait OptionLens[S,T] extends OptionGetter[S,T] with OptionSetter[S,T] {
    def mod(source:S, f:T=>T):S = getOption(source).map(v => set(source, f(v))).getOrElse(source)
  }
}
