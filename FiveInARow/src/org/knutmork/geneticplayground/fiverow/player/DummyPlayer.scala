package org.knutmork.geneticplayground.fiverow.player
import org.knutmork.geneticplayground.fiverow.game.Board
import org.knutmork.geneticplayground.fiverow.game.Player
import scala.util.Random

class DummyPlayer(board: Board) extends Player {

  board.addPlayer(this)
  val rand = new Random(System.currentTimeMillis());
  
  var init = true

  def yourTurn() {
//    val x = rand.nextInt(1);
//    val y = rand.nextInt(1);
//    if (!board.placeMarker(x, y)) yourTurn()
    if (init) {
      board.placeMarker(0,-1)
      init = false
    } else {
      board.placeMarker(-1, -1)}
  }
}