package org.knutmork.geneticplayground.fiverow
import org.knutmork.geneticplayground.fiverow.game.Board
import org.knutmork.geneticplayground.fiverow.gui.HumanPlayer
import org.knutmork.geneticplayground.fiverow.player._
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.ListBuffer

object ComputerTournament {

  def main(args: Array[String]): Unit = {
    
  	println("Initiating 100 new players...")
  	
  	val players = new ArrayBuffer[GeneticPlayer]
  	(0 to 99).foreach(i => players += new GeneticPlayer("C"+ i ))
  	
  	println("Playing 50 first games...") 
  	(0 to 99 by 2).foreach(i => new Game(players(i), players(i+1)))
  	println("Round 1 finished")
  	
  	println("Total score:")
  	players.foreach(println)

  }
}

