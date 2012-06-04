package org.knutmork.geneticplayground.gomoku
import scala.collection.mutable.ArrayBuffer
import RichFile.enrichFile
import org.knutmork.geneticplayground.gomoku.game.Board
import org.knutmork.geneticplayground.gomoku.player.DNAEngine
import org.knutmork.geneticplayground.gomoku.player.GeneticPlayer
import java.io.File
import org.knutmork.geneticplayground.gomoku.player.GeneticAlgorithm._

object ComputerGame {
  def main(args: Array[String]): Unit = {
    var players = initPlayers()

    (currentGeneration() until NUM_GENERATIONS).foreach(g => {
      println("Playing games for " + g + ". generation players...")
      (0 until NUM_INDIVIDUALS).foreach(i => {
        (0 until NUM_INDIVIDUALS).foreach(j => playGame(players(i), players(j)))
        println("Round " + i + " finished")
      })

      println("Total score:")
      players.foreach(println)

      players = DNAEngine.createNextGeneration(players, NUM_INDIVIDUALS * NUM_INDIVIDUALS)
      savePlayers(g, players)
    })
  }

  def initPlayers(): ArrayBuffer[GeneticPlayer] = {
    var players = loadPlayers()
    if (players.size == 0) {
      println("Initiating " + NUM_INDIVIDUALS + " new players...")
      (0 until NUM_INDIVIDUALS).foreach(i => players += GeneticPlayer("C" + i))
    }
    players
  }

  def savePlayers(generationNum: Int, players: Seq[GeneticPlayer]) {
    val f = new File("dnaString/generation" + generationNum + ".txt")
    var generationDNA = ""
    players.foreach(p => {
      generationDNA += p.dna.genes.mkString + "\n"
    })
    f.text = generationDNA
  }

  def currentGeneration(): Int = {
    new File("dnaString").mkdir();
    new File("dnaString").listFiles().length
  }

  def loadPlayers(): ArrayBuffer[GeneticPlayer] = {
    val p = new ArrayBuffer[GeneticPlayer]
    if (currentGeneration() > 0) {
      val f = new File("dnaString/generation" + (currentGeneration() - 1) + ".txt")
      f.text.split("\n").foreach(dna => p += GeneticPlayer("D", dna))
    }
    p
  }

  def playGame(player1: GeneticPlayer, player2: GeneticPlayer) {
    val board = new Board()
    board.addPlayer(player1)
    board.addPlayer(player2)
    player1.firstMove(board)
  }
}

