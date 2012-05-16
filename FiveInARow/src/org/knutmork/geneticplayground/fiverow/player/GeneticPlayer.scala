package org.knutmork.geneticplayground.fiverow.player
import org.knutmork.geneticplayground.fiverow.game.Board
import org.knutmork.geneticplayground.fiverow.game.Player
import scala.util.Random
import scala.collection.mutable.ArrayBuffer
import org.knutmork.geneticplayground.fiverow.game.CellState

class GeneticPlayer(board: Board) extends Player {

  board.addPlayer(this)
  val rand = new Random(System.currentTimeMillis());
  
  val dna : DNAEngine = new DNAEngine(CellState.Y) // // Hard coded for now 
  
  def yourTurn() {
    println("Computers turn")
    val chosenMove = dna.process(board.findLegalMoves(), board)
    println("Computer chosen move: " + chosenMove.pos._1 + ", " + chosenMove.pos._2)
    board.placeMarker(chosenMove.pos._1, chosenMove.pos._2)
  }
}