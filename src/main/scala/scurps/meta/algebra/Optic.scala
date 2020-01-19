package scurps.meta.algebra

object Optic {
  trait Element[-S,-T] {def isElement(source:S, element:T):Boolean}
  trait Setter[S,-T] {def set(source:S, newValue:T):S}
  trait Unsetter[S] {def unset(source:S):S}

  trait OptionGetter[-S,+T] {def getOption(source:S):Option[T]}
  trait OptionSetter[S,-T] extends Setter[S,T] with Unsetter[S]

  trait OptionLens[S,T] extends OptionGetter[S,T] with OptionSetter[S,T] {
    def mod(source:S, f:T=>T):S = getOption(source).map(v => set(source, f(v))).getOrElse(source)
  }

  object Element {
    implicit def setElement[A]:Element[Set[A],A] = SetElementImpl.asInstanceOf[Element[Set[A],A]]

    private object SetElementImpl extends Element[Set[Any],Any] {
      override def isElement(source:Set[Any], element:Any):Boolean = source.contains(element)
      override def toString:String = "Element[Set[_],_]"
    }
  }
}
