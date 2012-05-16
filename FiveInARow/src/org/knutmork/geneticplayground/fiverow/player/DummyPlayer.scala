package org.knutmork.geneticplayground.fiverow.player
import org.knutmork.geneticplayground.fiverow.game.Board
import org.knutmork.geneticplayground.fiverow.game.Player
import scala.util.Random

class DummyPlayer(board: Board) extends Player {

  board.addPlayer(this)
  val rand = new Random(System.currentTimeMillis());

  var init = true

  def yourTurn() {
    val possibleMoves = board.findLegalMoves()
    board.placeMarker(possibleMoves(0).pos._1, possibleMoves(0).pos._2)
  }
}