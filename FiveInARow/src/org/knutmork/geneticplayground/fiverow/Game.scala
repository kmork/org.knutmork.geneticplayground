package org.knutmork.geneticplayground.fiverow
import org.knutmork.geneticplayground.fiverow.game.Board
import org.knutmork.geneticplayground.fiverow.gui.HumanPlayer
import org.knutmork.geneticplayground.fiverow.player._

object Game {

  def main(args: Array[String]): Unit = {
    
  	println("Initiating new game...")
  	val board = new Board()
  	val human = new HumanPlayer(board)
  	//val computer = new DummyPlayer()
  	//val computer = new HandcraftedPlayer()
  	val computer1 = new GeneticPlayer("1")
  	board.addPlayer(computer1)
  	println("Game ready")

  	human.startup() 	
  }
}