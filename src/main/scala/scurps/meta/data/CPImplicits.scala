package scurps.meta.data

trait CPImplicits {
  final implicit class RichInt(v:Int) {
    @inline def cp:CP = CP(v)
  }
}
