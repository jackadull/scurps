package scurps.meta.data

/** `toString` implementations for key types. */
object ShowKey {
  trait ShowKey {
    def showKey:String
    override def toString:String = showKey
  }

  trait ShowParameterizedKey extends ShowKey {
    def showParameterLists:Seq[String]
    override def showKey:String = ofParameterizedKey(this, showParameterLists)
  }

  trait ShowProductKey extends ShowParameterizedKey with Product {
    override def showParameterLists:Seq[String] = productIterator.map(_.toString).toSeq
  }

  trait ShowSingletonKey extends ShowKey {
    override def showKey:String = ofSingletonKey(this)
  }

  def ofSingletonKey(key:AnyRef):String = {
    val cls = key.getClass
    showString(showPackageAndEnclosing(cls), showName(cls), Seq.empty)
  }

  def ofParameterizedKey(key:AnyRef, parameterLists:Seq[String]):String = {
    val cls = key.getClass
    showString(showPackageAndEnclosing(cls), cls.getSimpleName, parameterLists)
  }

  private def showName(cls:Class[_]):String = cls.getSimpleName.replaceAll("\\$$", "")

  def showPackageAndEnclosing(cls:Class[_]):String = Option(cls.getEnclosingClass) match {
    case None => cls.getPackage.getName
    case Some(enclosing) => s"${showPackageAndEnclosing(enclosing)}.${showName(enclosing)}"
  }

  private def showString(packageName:String, keyName:String, paramLists:Seq[String]):String =
    s"$keyName--$packageName${paramLists.map(pl => s"($pl)").mkString}"
}
