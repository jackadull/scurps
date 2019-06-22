package net.jackadull.scurps.meta

import scala.language.higherKinds

trait RuleCatalog[E,K[_]] extends PGet[K]
