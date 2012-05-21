package org.knutmork.geneticplayground.fiverow
import org.knutmork.geneticplayground.fiverow.game.Board
import org.knutmork.geneticplayground.fiverow.gui.HumanPlayer
import org.knutmork.geneticplayground.fiverow.player._

object Game {

  def main(args: Array[String]): Unit = {
    
  	println("Initiating new game...")
  	val board = new Board()
  	//val human = new HumanPlayer(board)
  	//val computer = new DummyPlayer()
  	//val computer = new HandcraftedPlayer()
  	val computer1 = new GeneticPlayer("1")
  	board.addPlayer(computer1)
  	val computer2 = new GeneticPlayer("2")
  	board.addPlayer(computer2)
  	println("Game ready")

  	//human.startup() 	
  	computer1.firstMove(board)
  }
}

class Game {
  val board = new Board()
  val player1 = new GeneticPlayer("1")
  board.addPlayer(player1)
  val player2 = new GeneticPlayer("2")
  board.addPlayer(player2)
  player1.firstMove(board)
  
  def winner(): GeneticPlayer = {
    if (player1.survivalCount > 0) player1 else player2
  }
}