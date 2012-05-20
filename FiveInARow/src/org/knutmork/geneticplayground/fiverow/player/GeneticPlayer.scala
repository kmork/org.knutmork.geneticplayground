package org.knutmork.geneticplayground.fiverow.player
import org.knutmork.geneticplayground.fiverow.game.Board
import org.knutmork.geneticplayground.fiverow.game.Player
import scala.util.Random
import scala.collection.mutable.ArrayBuffer
import org.knutmork.geneticplayground.fiverow.game.CellState

class GeneticPlayer(name: String, board: Board) extends Player {

  board.addPlayer(this)
  var dna : DNAEngine = new DNAEngine(CellState.Y) 
  
  def yourTurn() {
    println("Computer " + name + "'s turn")
    val chosenMove = dna.process(board.findLegalMoves(), board)
    println("Computer chosen move: " + chosenMove.pos._1 + ", " + chosenMove.pos._2)
    board.placeMarker(chosenMove.pos._1, chosenMove.pos._2)
  }
  
  def firstMove() {
    dna = new DNAEngine(CellState.X)
    board.placeMarker(0, 0)
  }
  
  def youWon() {
    println("Player won with the following gene bases: ")
    dna.printGenes()
  }
}