package org.knutmork.geneticplayground.fiverow.player
import scala.collection.mutable.ArrayBuffer
import scala.util.Random

import org.knutmork.geneticplayground.fiverow.game.Board
import org.knutmork.geneticplayground.fiverow.game.CellState
import org.knutmork.geneticplayground.fiverow.game.Marker

object DNAEngine {
  val NUM_GENES: Int = 30
  val MUTATION_RATE: Int = 25 // max = 10.000
  val rand = new Random(System.currentTimeMillis())
  
  def createNextGeneration(players: ArrayBuffer[GeneticPlayer], numGames: Int): ArrayBuffer[GeneticPlayer] = {
    val nextGen = new ArrayBuffer[GeneticPlayer]
    while (nextGen.size < players.size) {
      val offsprings = mate(rouletteWheelSelection(players, numGames), rouletteWheelSelection(players, numGames))
      nextGen += (offsprings._1, offsprings._2)
    }
    nextGen
  }

  private def rouletteWheelSelection(players: ArrayBuffer[GeneticPlayer], numGames: Int): GeneticPlayer = {
    var rnd = rand.nextInt(numGames) + 1// 1 point per game
    var i = 0
    while (rnd > players(i).survivalCount) {
      rnd = rnd - players(i).survivalCount
      i += 1
    }
    players(i)
  }

  private def mate(player1: GeneticPlayer, player2: GeneticPlayer): (GeneticPlayer, GeneticPlayer) = {
    var rnd = new Random(System.currentTimeMillis()).nextInt(Gene.size * DNAEngine.NUM_GENES)
    val offspring1DNA = player1.dna.genes.mkString.substring(0, rnd) + player2.dna.genes.mkString.substring(rnd)
    val offspring2DNA = player2.dna.genes.mkString.substring(0, rnd) + player1.dna.genes.mkString.substring(rnd)
    (GeneticPlayer(player1.name, mutate(offspring1DNA)), GeneticPlayer(player2.name, mutate(offspring2DNA)))
  }
  
  private def mutate(playerDNA: String): String = {
    var mutatedStringBuffer = ""
    playerDNA.foreach(c => {
      if (rand.nextInt(10000) + 1 <= DNAEngine.MUTATION_RATE) {
        println("Mutated " + c)
        mutatedStringBuffer += Gene.newMutatedBase(c)
      } else {
        mutatedStringBuffer += c
      }
    })
    mutatedStringBuffer.toString()
  }
}

class DNAEngine(dnaString: String) {
  val genes: ArrayBuffer[Gene] = new ArrayBuffer()
  (0 until DNAEngine.NUM_GENES).foreach(i => {
    if (dnaString == null) {genes += Gene.newRandomGene()}
    else {genes += Gene.newFromString(dnaString.substring(i*Gene.size, (i+1)*Gene.size))}
  })
  genes += Gene.newInitialGene() // At last keep a default gene which always makes a valid move

  def process(player: GeneticPlayer, cells: Seq[Marker], b: Board): Marker = {
    cells.foldLeft[(Int, Marker)](Int.MaxValue, new Marker(0, 0)) { (lowestCellPoint, cell) =>
      val m = mapBoardToArray(cell, b)
      var points = genes.size
      var ok = false
      (0 until genes.size).toStream.takeWhile(_ => !ok).foreach(g => {
        points = g
        val gene = genes(g)
        ok = true
        (0 until gene.base.size).toStream.takeWhile(_ => ok).foreach(i => {
          ok = Integer.parseInt(gene.base.substring(i, i + 1)) match {
            case Gene.UNKNOWN => true
            case Gene.NOT_SET => m(i).state.equals(CellState.NOT_SET)
            case Gene.ME => m(i).state.equals(player.marker)
            case Gene.OPPONENT => (!m(i).state.equals(player.marker) && !m(i).state.equals(CellState.NOT_SET))
            case other => println("Error in Gene data - not recognizable digit: " + gene.base.substring(i, i + 1)); false
          }
        })
      })
      if (points < lowestCellPoint._1) { (points, cell) } else { lowestCellPoint }
    }._2
  }

  private def mapBoardToArray(cell: Marker, b: Board): ArrayBuffer[Marker] = {
    val m = new ArrayBuffer[Marker]
    for (x <- -4 to 4) {
      for (y <- -4 to 4) {
        if (x != 0 && y != 0) { // Not include the pos that's about to be evaluated
          m += b.m(x + cell.pos._1, y + cell.pos._2)
        }
      }
    }
    m
  }

  def printGenes() {
    genes.foreach(gene => print(gene))
    println("")
  }
}
