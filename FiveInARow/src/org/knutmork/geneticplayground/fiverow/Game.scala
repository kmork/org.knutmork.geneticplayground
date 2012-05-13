package org.knutmork.geneticplayground.fiverow
import org.knutmork.geneticplayground.fiverow.game.Board
import org.knutmork.geneticplayground.fiverow.gui.Main

object FiveInARow {

  def main(args: Array[String]): Unit = {
    
  	println("Initiating new game...")
  	val board = new Board()
  	new Main(board).startup()
  	println("Game ready")
  	
  }
}