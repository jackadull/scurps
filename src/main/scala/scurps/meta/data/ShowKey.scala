package scurps.meta.data

/** `toString` implementations for key types. */
object ShowKey {
  trait ShowParameterizedKey {
    def showParameterLists:Seq[String] // TODO try using Shapeless for this
    override def toString:String = ofParameterizedKey(this, showParameterLists)
  }

  trait ShowSingletonKey {
    override def toString:String = ofSingletonKey(this)
  }

  def ofSingletonKey(key:AnyRef):String = {
    val cls = key.getClass
    showString(cls.getPackageName, cls.getSimpleName.replaceAll("\\$$", ""), Seq.empty) // TODO what about inner classes?
  }

  def ofParameterizedKey(key:AnyRef, parameterLists:Seq[String]):String = {
    val cls = key.getClass
    showString(cls.getPackageName, cls.getSimpleName, parameterLists) // TODO what about inner classes?
  }

  private def showString(packageName:String, keyName:String, paramLists:Seq[String]):String =
    s"$keyName${paramLists.map(pl => s"($pl)").mkString}--$packageName"
}
