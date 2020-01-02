package scurps.meta.data

trait KeyWrapper[-T,+K] extends (T=>K)
