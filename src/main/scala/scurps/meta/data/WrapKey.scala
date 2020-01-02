package scurps.meta.data

trait WrapKey[-T,+K] extends (T=>K)
