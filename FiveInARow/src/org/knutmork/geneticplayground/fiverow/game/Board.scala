package org.knutmork.geneticplayground.fiverow.game
import scala.collection.mutable._

class Board {
  // dynamic size of matrix, starting with just one cell
  private var firstRowIndex = 0
  private var lastRowIndex = 0
  private var firstColIndex = 0
  private var lastColIndex = 0

  private val data = new ArrayBuffer[Marker]
  data += new Marker(0, 0)
  private val players = Map.empty[CellState.Value, Player]

  var initialMove = true
  var lastMarker: (Int, Int, CellState.Value) = (-1, -1, CellState.Y)
  var winList = new ArrayBuffer[Marker]

  def legalMove(x: Int, y: Int): Boolean = {
    if (x >= firstRowIndex && x <= lastRowIndex && y >= firstColIndex && y <= lastColIndex) {
      if (m(x, y).empty()) {
        return initialMove || !m(x - 1, y - 1).empty || !m(x - 1, y).empty || !m(x - 1, y + 1).empty || !m(x, y - 1).empty || !m(x, y + 1).empty || !m(x + 1, y - 1).empty || !m(x + 1, y).empty || !m(x + 1, y + 1).empty
      }
    }
    false
  }

  def addPlayer(player: Player) {
    if (players.isEmpty) players(CellState.X) = player
    else players(CellState.Y) = player
  }

  def boardDimension: (Int, Int) = (lastRowIndex + firstRowIndex.abs + 1, lastColIndex + firstColIndex.abs + 1)

  def placeMarker(x: Int, y: Int): Boolean = {
    //println("Try to place marker at " + x + ", " + y)
    if (!gameOver && legalMove(x, y)) {
      updateBoard(x, y)
      togglePlayer(x, y)
      if (!gameOver) { players(currPlayer).yourTurn() } else {players(nextPlayer).youWon()}
      true
    } else {
      //println("Illegal marker" + currPlayer.toString() + " attempt at " + x + ", " + y)
      false
    }
  }

  // Set next player as current
  private def togglePlayer(x: Int, y: Int) { lastMarker = (x, y, currPlayer) }

  def currPlayer(): CellState.Value = if (CellState.X.equals(lastMarker._3)) CellState.Y else CellState.X
  def nextPlayer(): CellState.Value = lastMarker._3

  //def matrix(x: Int, y: Int): Marker = {m(x, y)}
  def m(x: Int, y: Int): Marker = {
    if (x < firstRowIndex || x > lastRowIndex || y < firstColIndex || y > lastColIndex) {
      new Marker(x, y) // Doesn't yet exist, just return a new empty one
    } else {
      //println("Mapped " + x + ", " + y + " to bigtable at index " + ((x + firstRowIndex.abs) * boardDimension._1 + (y + firstColIndex.abs)))
      data((x + firstRowIndex.abs) * boardDimension._2 + (y + firstColIndex.abs))
    }
  }

  def gameOver(): Boolean = {
    var gameOver = false
    val x = lastMarker._1
    val y = lastMarker._2
    val cs = lastMarker._3
    val subMatrix = ArrayBuffer.empty[Marker]
    subMatrix += (m(x - 4, y - 4), m(x - 3, y - 3), m(x - 2, y - 2), m(x - 1, y - 1), m(x, y), m(x + 1, y + 1), m(x + 2, y + 2), m(x + 3, y + 3), m(x + 4, y + 4), new Marker(-1, -1))
    subMatrix += (m(x + 4, y - 4), m(x + 3, y - 3), m(x + 2, y - 2), m(x + 1, y - 1), m(x, y), m(x - 1, y + 1), m(x - 2, y + 2), m(x - 3, y + 3), m(x - 4, y + 4), new Marker(-1, -1))
    subMatrix += (m(x, y - 4), m(x, y - 3), m(x, y - 2), m(x, y - 1), m(x, y), m(x, y + 1), m(x, y + 2), m(x, y + 3), m(x, y + 4), new Marker(-1, -1))
    subMatrix += (m(x - 4, y), m(x - 3, y), m(x - 2, y), m(x - 1, y), m(x, y), m(x + 1, y), m(x + 2, y), m(x + 3, y), m(x + 4, y))

    var count = 0
    for (i <- 0 until subMatrix.length) {
      if (cs.equals(subMatrix(i).state)) count += 1 else count = 0
      if (count == 5) {
        gameOver = true
        winList += (subMatrix(i - 4), subMatrix(i - 3), subMatrix(i - 2), subMatrix(i - 1), subMatrix(i))
      }
    }
    gameOver
  }

  private def updateBoard(x: Int, y: Int) {
    println("Set marker" + currPlayer.toString() + " at " + x + ", " + y)
    m(x, y).setState(currPlayer)
    reAdjustBoard(x, y)
    initialMove = false

    debugBigTable()
  }

  private def reAdjustBoard(x: Int, y: Int) {
    if (x <= firstRowIndex) {
      // Add a top row
      for (i <- lastColIndex to firstColIndex by -1) {
        data.prepend(new Marker(x - 1, i))
      }
      firstRowIndex -= 1
    }
    if (x >= lastRowIndex) {
      // Add a last row
      for (i <- firstColIndex to lastColIndex) {
        data.append(new Marker(x + 1, i))
      }
      lastRowIndex += 1
    }
    if (y <= firstColIndex) {
      // Prepend a new col
      for (i <- 0 to boardDimension._1 - 1) {
        data.insert(i * (boardDimension._2 + 1), new Marker(firstRowIndex + i, y - 1))
      }
      firstColIndex -= 1

    }
    if (y >= lastColIndex) {
      // Append a new col
      for (i <- 1 to boardDimension._1) {
        data.insert((i * (boardDimension._2 + 1)) - 1, new Marker(firstRowIndex + i - 1, y + 1))
      }
      lastColIndex += 1
    }
  }

  def findLegalMoves() : ArrayBuffer[Marker] = {
    data.filter(marker => legalMove(marker.pos._1, marker.pos._2))
  }
  
  private def debugBigTable() {
//    println("Bigtable size: " + data.length)
//    print("Bigtable: ")
//    data.foreach(marker => print(marker.state + ", "))
//    println("")
//    
    for (i <- 0 to data.length - 1) {
      print(data(i).state)
      if ((i + 1) % (firstColIndex.abs + lastColIndex + 1) == 0) {
        print("\n")
      }
    }
    println("")
//    for (i <- 0 to data.length - 1) {
//      if ((data(i).pos._1) >= 0) {print(" ")}
//      print((data(i).pos._1) + ",")
//      if ((data(i).pos._2) >= 0) {print(" ")}
//      print((data(i).pos._2) + "  ")
//      if ((i + 1) % (firstColIndex.abs + lastColIndex + 1) == 0) {
//        print("\n")
//      }
//    }
  }
}