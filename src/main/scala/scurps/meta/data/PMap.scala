package scurps.meta.data

/** Polymorphic map, whose value types are derived from the reference keys of type [[K]]. */
trait PMap[K[_]] extends PMapLike[K,PMap[K]] {
  def contains(key:K[_]):Boolean
  def get[V](key:K[V]):Option[V]
  def isEmpty:Boolean
}
object PMap {
  @inline def empty[K[_]]:PMap[K] = _empty.asInstanceOf[PMap[K]]

  private val _empty = Impl[Nothing](Map.empty)

  trait MapBasedPMap[K[_],+Repr<:MapBasedPMap[K,Repr]] extends PMap[K] with PMapLike[K,Repr] {
    this:Repr=>

    protected def baseMap:Map[K[_],Any]
    protected def withBaseMap(newBaseMap:Map[K[_],Any]):Repr

    override def contains(key:K[_]):Boolean = baseMap.contains(key)
    override def get[V](key:K[V]):Option[V] = baseMap.get(key).asInstanceOf[Option[V]]
    override def isEmpty:Boolean = baseMap.isEmpty
    override def removed(key:K[_]):Repr = if(contains(key)) withBaseMap(baseMap - key) else this
    override def toString:String = s"PMap$baseMap"
    override def updated[V](key:K[V], value:V):Repr = baseMap.get(key) match {
      case Some(`value`) => this
      case _ => withBaseMap(baseMap.updated(key, value))
    }
  }

  private final case class Impl[K[_]](baseMap:Map[K[_],Any]) extends MapBasedPMap[K,Impl[K]] {
    override protected def withBaseMap(newBaseMap:Map[K[_],Any]):Impl[K] = Impl[K](newBaseMap)
    override def toString:String = baseMap.toString
  }
}

trait PMapLike[K[_],+Repr<:PMapLike[K,Repr]] {
  def removed(key:K[_]):Repr
  def updated[V](key:K[V], value:V):Repr
}
