package org.knutmork.geneticplayground.fiverow
import scala.collection.mutable.ArrayBuffer

import org.knutmork.geneticplayground.fiverow.game.Board
import org.knutmork.geneticplayground.fiverow.player.DNAEngine
import org.knutmork.geneticplayground.fiverow.player.GeneticPlayer

object ComputerTournament {
  val NUM_PLAYERS: Int = 30
  val NUM_GENERATIONS: Int = 30
  val MUTATION_RATE: Int = 0

  def main(args: Array[String]): Unit = {

    println("Initiating " + NUM_PLAYERS + " new players...")
    var players = new ArrayBuffer[GeneticPlayer]
    (0 until NUM_PLAYERS).foreach(i => players += GeneticPlayer("C" + i))

    (0 until NUM_GENERATIONS).foreach(g => {
      println("Playing games for " + g + ". generation players...")
      (0 until NUM_PLAYERS).foreach(i => {
        (0 until NUM_PLAYERS).foreach(j => new ComputerGame(players(i), players(j)))
        println("Round " + i + " finished")
      })

      println("Total score:")
      players.foreach(println)
    
      players = DNAEngine.createNextGeneration(players, NUM_PLAYERS*NUM_PLAYERS)
    })
  }
}

class ComputerGame(player1: GeneticPlayer, player2: GeneticPlayer) {
  val board = new Board()
  board.addPlayer(player1)
  board.addPlayer(player2)
  player1.firstMove(board)
}
