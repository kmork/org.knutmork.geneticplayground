package org.knutmork.geneticplayground.gomoku.player
import org.knutmork.geneticplayground.gomoku.game.Board
import org.knutmork.geneticplayground.gomoku.game.Player
import scala.util.Random

class DummyPlayer() extends Player {

  val rand = new Random(System.currentTimeMillis());

  override def youWon(board: Board) {println("DummyPlayer won!")}
  
  def yourTurn(board: Board) {
    val possibleMoves = board.findLegalMoves()
    val (chosenMovePos1, chosenMovePos2) = possibleMoves(rand.nextInt(possibleMoves.size)).pos
    board.placeMarker(chosenMovePos1, chosenMovePos2)
  }
}