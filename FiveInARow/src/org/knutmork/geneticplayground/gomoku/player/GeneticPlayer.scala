package org.knutmork.geneticplayground.gomoku.player
import org.knutmork.geneticplayground.gomoku.game.Board
import org.knutmork.geneticplayground.gomoku.game.Player
import scala.util.Random
import scala.collection.mutable.ArrayBuffer
import org.knutmork.geneticplayground.gomoku.game.CellState

object GeneticPlayer {
  def apply(name: String): GeneticPlayer = {
    new GeneticPlayer(name, null)
  }

  def apply(name: String, dna: String): GeneticPlayer = {
    new GeneticPlayer(name, dna)
  }
}

class GeneticPlayer(val name: String, dnaString: String) extends Player {

  var marker = CellState.Y
  val dna: DNAEngine = new DNAEngine(dnaString)
  var fitness = 0
  var usedGenes: Int = 0
  
  def yourTurn(board: Board) {
    val chosenMove = dna.process(this, board.findLegalMoves(), board)
    board.placeMarker(chosenMove.pos._1, chosenMove.pos._2)
  }

  def firstMove(board: Board) {
    marker = CellState.X
    board.placeMarker(0, 0)
  }

  override def youWon(board: Board) {
    if(usedGenes > 0) {
      fitness += 1
      usedGenes = 0
    }
  }
  
  override def youLost(board: Board) {
    usedGenes = 0
  }

  def addGenesInUse() {
    usedGenes += 1
  }

  override def toString = fitness + "," + dna.genes.mkString
}