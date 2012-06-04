package org.knutmork.geneticplayground.gomoku.player
import scala.collection.immutable.StringOps
import scala.util.Random

object Gene {
  val UNKNOWN: Int = 0
  val NOT_SET: Int = 1
  val ME: Int = 2
  val OPPONENT: Int = 3
  
  val rand = new Random(System.currentTimeMillis());
  
  def newRandomGene(): Gene = {
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
  
  // Returns randomly one of the other three types of a gene base
  def newMutatedBase(c: Char): Char = {
    val rand = new Random(System.currentTimeMillis());
    var mc = c
    while(mc.equals(c)) (mc = ("" + rand.nextInt(4)).head)
    mc
  }
  
  def size: Int = 80
}

class Gene private (val base: StringOps) {
  override def toString = base
}