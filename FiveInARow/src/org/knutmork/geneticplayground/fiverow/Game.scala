package org.knutmork.geneticplayground.fiverow
import org.knutmork.geneticplayground.fiverow.game.Board
import org.knutmork.geneticplayground.fiverow.gui.HumanPlayer
import org.knutmork.geneticplayground.fiverow.player.DummyPlayer

object FiveInARow {

  def main(args: Array[String]): Unit = {
    
  	println("Initiating new game...")
  	val board = new Board()
  	val human = new HumanPlayer(board).startup()
  	val computer = new DummyPlayer(board)
  	println("Game ready")

  	computer.playMove()
  	
  }
}