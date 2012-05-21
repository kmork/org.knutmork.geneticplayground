package org.knutmork.geneticplayground.fiverow
import org.knutmork.geneticplayground.fiverow.game.Board
import org.knutmork.geneticplayground.fiverow.gui.HumanPlayer
import org.knutmork.geneticplayground.fiverow.player._
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.ListBuffer

object ComputerTournament {

  def main(args: Array[String]): Unit = {
    
  	println("Initiating and playing 50 games with 100 new players...")
  	val games = new ListBuffer[Game]
  	(0 to 49).foreach(i => games += new Game() )
  	println("Round 1 finished")
  	
  	games.foreach(g => println(g.winner()))
  	println("Finished")
  }
}

