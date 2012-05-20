package org.knutmork.geneticplayground.fiverow
import org.knutmork.geneticplayground.fiverow.game.Board
import org.knutmork.geneticplayground.fiverow.gui.HumanPlayer
import org.knutmork.geneticplayground.fiverow.player._

object FiveInARow {

  def main(args: Array[String]): Unit = {
    
  	println("Initiating new game...")
  	val board = new Board()
  	//val human = new HumanPlayer(board)
  	//val computer = new DummyPlayer(board)
  	//val computer = new HandcraftedPlayer(board)
  	val computer1 = new GeneticPlayer("1", board)
  	val computer2 = new GeneticPlayer("2", board)
  	println("Game ready")

  	//human.startup() 	
  	computer1.firstMove()
  }
}