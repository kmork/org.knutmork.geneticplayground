package org.knutmork.geneticplayground.fiverow
import scala.collection.mutable.ArrayBuffer

import org.knutmork.geneticplayground.fiverow.game.Board
import org.knutmork.geneticplayground.fiverow.player.DNAEngine
import org.knutmork.geneticplayground.fiverow.player.GeneticPlayer

object ComputerTournament {

  def main(args: Array[String]): Unit = {

    println("Initiating 100 new players...")
    val players = new ArrayBuffer[GeneticPlayer]
    (0 to 99).foreach(i => players += new GeneticPlayer("C" + i))

    println("Playing games for 1. generation players...")
    (0 to 99).foreach(i => {
      (0 to 99).foreach(j => new ComputerGame(players(i), players(j)))
      println("Round " + i + " finished")
    })

    println("Total score:")
    players.foreach(println)
    
    val players2 = DNAEngine.createNextGeneration(players)


  }
}

class ComputerGame(player1: GeneticPlayer, player2: GeneticPlayer) {
  val board = new Board()
  board.addPlayer(player1)
  board.addPlayer(player2)
  player1.firstMove(board)
}
