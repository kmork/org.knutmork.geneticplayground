package org.knutmork.geneticplayground.fiverow
import scala.collection.mutable.ArrayBuffer

import org.knutmork.geneticplayground.fiverow.game.Board
import org.knutmork.geneticplayground.fiverow.player.DNAEngine
import org.knutmork.geneticplayground.fiverow.player.GeneticPlayer

object ComputerTournament {
  val NUM_PLAYERS: Int = 10

  def main(args: Array[String]): Unit = {

    println("Initiating " + NUM_PLAYERS + " new players...")
    val players = new ArrayBuffer[GeneticPlayer]
    (0 until NUM_PLAYERS).foreach(i => players += GeneticPlayer("C" + i))

    println("Playing games for 1. generation players...")
    (0 until NUM_PLAYERS).foreach(i => {
      (0 until NUM_PLAYERS).foreach(j => new ComputerGame(players(i), players(j)))
      println("Round " + i + " finished")
    })

    println("Total score:")
    players.foreach(println)
    
    val players2 = DNAEngine.createNextGeneration(players, NUM_PLAYERS*NUM_PLAYERS)
    println("Playing games for 2. generation players...")
    (0 until NUM_PLAYERS).foreach(i => {
      (0 until NUM_PLAYERS).foreach(j => new ComputerGame(players2(i), players2(j)))
      println("Round " + i + " finished")
    })

    println("Total score:")
    players2.foreach(println)


  }
}

class ComputerGame(player1: GeneticPlayer, player2: GeneticPlayer) {
  val board = new Board()
  board.addPlayer(player1)
  board.addPlayer(player2)
  player1.firstMove(board)
}
