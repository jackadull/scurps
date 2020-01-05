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
    override def updated[V](key:K[V], value:V):Repr = baseMap.get(key) match {
      case Some(`value`) | None => this
      case _ => withBaseMap(baseMap.updated(key, value))
    }
    // TODO toString
  }

  private final case class Impl[K[_]](baseMap:Map[K[_],Any]) extends MapBasedPMap[K,Impl[K]] {
    override protected def withBaseMap(newBaseMap:Map[K[_],Any]):Impl[K] = Impl[K](newBaseMap)
    // TODO toString
  }
}
