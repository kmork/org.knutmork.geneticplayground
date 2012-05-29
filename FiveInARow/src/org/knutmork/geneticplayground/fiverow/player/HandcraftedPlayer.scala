package org.knutmork.geneticplayground.fiverow.player
import org.knutmork.geneticplayground.fiverow.game.Board
import org.knutmork.geneticplayground.fiverow.game.Player
import scala.util.Random
import org.knutmork.geneticplayground.fiverow.game.Marker
import scala.collection.mutable.ArrayBuffer
import org.knutmork.geneticplayground.fiverow.game.CellState

// Offensive player, don't care about defense
class HandcraftedPlayer() extends Player {

  val rand = new Random(System.currentTimeMillis());

  def yourTurn(board: Board) {
    println("Computers turn")
    var chosenMove: Marker = null
    val possibleMoves = board.findLegalMoves()
    val possibleWinnerMoves = possibleMoves.filter(marker => findSequence(board, marker, 5))
    if (possibleWinnerMoves.size > 0) {
      chosenMove = possibleWinnerMoves.head
    } else {
      val possibleBetterMoves = possibleMoves.filter(marker => findSequence(board, marker, 4))
      if (possibleBetterMoves.size > 0) {
        chosenMove = possibleBetterMoves.head
      } else {
        val possibleOkMoves = possibleMoves.filter(marker => findSequence(board, marker, 3))
        if (possibleOkMoves.size > 0) {
          chosenMove = possibleOkMoves.head
        } else {
          chosenMove = possibleMoves(rand.nextInt(possibleMoves.size))
        }
      }
    }
    println("Computer chosen move: " + chosenMove.pos._1 + ", " + chosenMove.pos._2)
    board.placeMarker(chosenMove.pos._1, chosenMove.pos._2)
  }

  def findSequence(board: Board, marker: Marker, num: Int): Boolean = {
    var found = false
    val x = marker.pos._1
    val y = marker.pos._2
    val me = new Marker(x, y)
    me.state = CellState.Y // Hard coded for now
    val subMatrix = ArrayBuffer.empty[Marker]
    subMatrix += (board.m(x - 4, y - 4), board.m(x - 3, y - 3), board.m(x - 2, y - 2), board.m(x - 1, y - 1), me, board.m(x + 1, y + 1), board.m(x + 2, y + 2), board.m(x + 3, y + 3), board.m(x + 4, y + 4), new Marker(-1, -1))
    subMatrix += (board.m(x + 4, y - 4), board.m(x + 3, y - 3), board.m(x + 2, y - 2), board.m(x + 1, y - 1), me, board.m(x - 1, y + 1), board.m(x - 2, y + 2), board.m(x - 3, y + 3), board.m(x - 4, y + 4), new Marker(-1, -1))
    subMatrix += (board.m(x, y - 4), board.m(x, y - 3), board.m(x, y - 2), board.m(x, y - 1), me, board.m(x, y + 1), board.m(x, y + 2), board.m(x, y + 3), board.m(x, y + 4), new Marker(-1, -1))
    subMatrix += (board.m(x - 4, y), board.m(x - 3, y), board.m(x - 2, y), board.m(x - 1, y), me, board.m(x + 1, y), board.m(x + 2, y), board.m(x + 3, y), board.m(x + 4, y))

    var count = 0
    for (i <- 0 until subMatrix.length) {
      if (me.state.equals(subMatrix(i).state)) count += 1 else count = 0
      if (count == num) {
        found = true
      }
    }
    found
  }
}