package org.knutmork.geneticplayground.fiverow.player
import org.knutmork.geneticplayground.fiverow.game.Board
import org.knutmork.geneticplayground.fiverow.game.Player
import scala.util.Random

class DummyPlayer(board: Board) extends Player {

  board.addPlayer(this)
  val rand = new Random(System.currentTimeMillis());

  def yourTurn() {
    val x = rand.nextInt(board.NUM_ROWS);
    val y = rand.nextInt(board.NUM_COLS);
    if (!board.setMarker(x, y)) yourTurn()
  }
}