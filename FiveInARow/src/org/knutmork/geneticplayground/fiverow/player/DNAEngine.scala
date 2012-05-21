package org.knutmork.geneticplayground.fiverow.player
import scala.collection.mutable.ArrayBuffer
import scala.util.Random

import org.knutmork.geneticplayground.fiverow.game.Board
import org.knutmork.geneticplayground.fiverow.game.CellState
import org.knutmork.geneticplayground.fiverow.game.Marker

object DNAEngine {
  def createNextGeneration(players: ArrayBuffer[GeneticPlayer]): ArrayBuffer[GeneticPlayer] = {
    val nextGen = new ArrayBuffer[GeneticPlayer]
    while (nextGen.size < players.size) {
      val offsprings = mate(rouletteWheelSelection(players), rouletteWheelSelection(players))
      nextGen += (offsprings._1, offsprings._2)
    }
    nextGen
  }

  private def rouletteWheelSelection(players: ArrayBuffer[GeneticPlayer]): GeneticPlayer = {
    var rnd = new Random(System.currentTimeMillis()).nextInt(100 * 100) // 10000 max total score based on 100 * 100 games
    var i = 0
    while (rnd > 0) {
      rnd = rnd - players(i).survivalCount
      i += 1
    }
    players(i)
  }

  private def mate(player1: GeneticPlayer, player2: GeneticPlayer): (GeneticPlayer, GeneticPlayer) = {
    var rnd = new Random(System.currentTimeMillis()).nextInt(Gene.size * 100) // Number of bases - 100 genes of 80 digits each
    (player1, player2)
  }
}

class DNAEngine(player: GeneticPlayer) {
  val genes: ArrayBuffer[Gene] = new ArrayBuffer()
  (0 to 100).foreach(i => {
    genes += Gene.newRandomGene()
  })
  genes += Gene.newInitialGene() // At last keep a default gene which always makes a valid move

  def process(cells: Seq[Marker], b: Board): Marker = {
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
    genes.foreach(gene => println(gene))
  }
}
