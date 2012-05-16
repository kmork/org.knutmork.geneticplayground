package org.knutmork.geneticplayground.fiverow.player
import scala.collection.immutable.StringOps

object Gene {
  val UNKNOWN: Int = 0
  val NOT_SET: Int = 1
  val ME: Int = 2
  val OPPONENT: Int = 3  
}

class Gene {
  val base : StringOps = "00000000000000000000000000000000000000000000000000000000000000000000000000000000" // 80 digits
}