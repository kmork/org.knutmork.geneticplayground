package org.knutmork.geneticplayground.fiverow.player
import scala.collection.immutable.StringOps
import scala.util.Random

object Gene {
  val UNKNOWN: Int = 0
  val NOT_SET: Int = 1
  val ME: Int = 2
  val OPPONENT: Int = 3
  
  def newRandomGene(): Gene = {
    val rand = new Random(System.currentTimeMillis());
    var geneBase = ""
    (0 until Gene.size).foreach (i => {
      geneBase += rand.nextInt(4)
    })
    new Gene(geneBase)
  }
  
  def newInitialGene(): Gene = {
    new Gene("00000000000000000000000000000000000000000000000000000000000000000000000000000000") // 80 digits
  }
  
  def newFromString(s: String) = {
    new Gene(s)
  }
  
  def size: Int = 80
}

class Gene private (val base: StringOps) {
  override def toString = base
}