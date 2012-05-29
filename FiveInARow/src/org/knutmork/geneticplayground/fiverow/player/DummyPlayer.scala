package org.knutmork.geneticplayground.fiverow.player
import org.knutmork.geneticplayground.fiverow.game.Board
import org.knutmork.geneticplayground.fiverow.game.Player
import scala.util.Random

class DummyPlayer() extends Player {

  val rand = new Random(System.currentTimeMillis());

  override def youWon(board: Board) {println("DummyPlayer won!")}
  
  def yourTurn(board: Board) {
    val possibleMoves = board.findLegalMoves()
    val chosenMove = possibleMoves(rand.nextInt(possibleMoves.size))
    board.placeMarker(chosenMove.pos._1, chosenMove.pos._2)
  }
}