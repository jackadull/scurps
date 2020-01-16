package scurps.meta.data

/** `toString` implementations for key types. */
object ShowKey {
  trait ShowKey {
    def showKey:String
    override def toString:String = showKey
  }

  trait ShowParameterizedKey extends ShowKey {
    def showParameterLists:Seq[String] // TODO try using Shapeless for this
    override def showKey:String = ofParameterizedKey(this, showParameterLists)
  }

  trait ShowSingletonKey extends ShowKey {
    override def showKey:String = ofSingletonKey(this)
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
    s"$keyName--$packageName${paramLists.map(pl => s"($pl)").mkString}"
}
