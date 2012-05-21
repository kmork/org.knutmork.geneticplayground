package org.knutmork.geneticplayground.fiverow.player
import org.knutmork.geneticplayground.fiverow.game.Board
import org.knutmork.geneticplayground.fiverow.game.Player
import scala.util.Random

class DummyPlayer() extends Player {

  val rand = new Random(System.currentTimeMillis());

  def youWon(board: Board) {}
  
  def yourTurn(board: Board) {
    println("Computers turn")
    val possibleMoves = board.findLegalMoves()
    val chosenMove = possibleMoves(rand.nextInt(possibleMoves.size))
    println("Computer chosen move: " + chosenMove.pos._1 + ", " + chosenMove.pos._2)
    board.placeMarker(chosenMove.pos._1, chosenMove.pos._2)
  }
}