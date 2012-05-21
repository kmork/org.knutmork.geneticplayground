package org.knutmork.geneticplayground.fiverow.player
import org.knutmork.geneticplayground.fiverow.game.Board
import org.knutmork.geneticplayground.fiverow.game.Player
import scala.util.Random
import scala.collection.mutable.ArrayBuffer
import org.knutmork.geneticplayground.fiverow.game.CellState

class GeneticPlayer(name: String) extends Player {

  var marker = CellState.Y
  val dna : DNAEngine = new DNAEngine(this)
  var survivalCount = 0
  
  def yourTurn(board: Board) {
    val chosenMove = dna.process(board.findLegalMoves(), board)
    board.placeMarker(chosenMove.pos._1, chosenMove.pos._2)
  }
  
  def firstMove(board: Board) {
    marker = CellState.X
    board.placeMarker(0, 0)
  }
  
  def youWon(board: Board) {
    survivalCount += 1
    println("Player " + name + " won game")
    //dna.printGenes()
  }
  
  override def toString = "GeneticPlayer: " + name
}