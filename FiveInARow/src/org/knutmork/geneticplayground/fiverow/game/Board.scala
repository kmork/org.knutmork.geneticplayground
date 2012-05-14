package org.knutmork.geneticplayground.fiverow.game
import scala.collection.mutable._

class Board {
	val NUM_ROWS = 30
	val NUM_COLS = 30
	val CELL_PREFIX = 10
	val data = new ArrayBuffer[Marker]
	val players = Map.empty[CellState.Value, Player]	
	
	for (i <- 0 to NUM_ROWS-1){
	  for (j <- 0 to NUM_COLS-1) {
	    data += new Marker(i, j)
	  }
	}
	
	var lastMarker : (Int, Int, CellState.Value) = (-1, -1, CellState.Y)
	var winList = new ArrayBuffer[Marker]

	def legalMove(x: Int, y : Int) : Boolean = {
	  if(x > -1 && x < NUM_ROWS-CELL_PREFIX && y > -1 && y < NUM_COLS-CELL_PREFIX) {
	    if(m(x,y).empty()) {
	      return initialMove || !m(x-1,y-1).empty || !m(x-1,y).empty || !m(x-1,y+1).empty || !m(x,y-1).empty || !m(x,y+1).empty || !m(x+1,y-1).empty || !m(x+1,y).empty || !m(x+1,y+1).empty	    
	    }
	  }
	  false
	}
	
	def initialMove() : Boolean = lastMarker._1 == -1 && lastMarker._2 == -1
	
	def addPlayer(player : Player) {
	  if (players.isEmpty) players(CellState.X) = player
	  else players(CellState.Y) = player
	}
	
	def cellDimension : (Int,Int) = (NUM_ROWS-CELL_PREFIX, NUM_COLS-CELL_PREFIX)
	
	def setMarker(x : Int, y : Int) : Boolean ={
	  //println("Legal move: " + legalMove(x, y))
	  if(!gameOver && legalMove(x, y)) {
		  println("Set marker" + currPlayer.toString() + " at " + x + ", " + y)
		  m(x,y).setState(currPlayer)
		  lastMarker = (x, y, currPlayer)
		  if (!gameOver) players(currPlayer).yourTurn()
		  true
	  } else {
		  //println("Illegal marker" + currPlayer.toString() + " attempt at " + x + ", " + y)
		  false
	  }
	}
	
	def currPlayer() : CellState.Value = if (CellState.X.equals(lastMarker._3)) CellState.Y else CellState.X
	def nextPlayer() : CellState.Value = lastMarker._3
	
	def m(x : Int, y : Int) : Marker = {
	  data((x+CELL_PREFIX/2)*NUM_ROWS+(y+CELL_PREFIX/2))
	}
	
	def gameOver() : Boolean = {
	  var gameOver = false
	  val x = lastMarker._1
	  val y = lastMarker._2
	  val cs = lastMarker._3
	  val subMatrix = ArrayBuffer.empty[Marker]
	  subMatrix += (m(x-4, y-4), m(x-3,y-3), m(x-2,y-2), m(x-1,y-1), m(x,y), m(x+1,y+1), m(x+2,y+2), m(x+3,y+3), m(x+4,y+4), new Marker(-1, -1))
	  subMatrix += (m(x+4, y-4), m(x+3,y-3), m(x+2,y-2), m(x+1,y-1), m(x,y), m(x-1,y+1), m(x-2,y+2), m(x-3,y+3), m(x-4,y+4), new Marker(-1, -1))
	  subMatrix += (m(x, y-4), m(x,y-3), m(x,y-2), m(x,y-1), m(x,y), m(x,y+1), m(x,y+2), m(x,y+3), m(x,y+4), new Marker(-1, -1))
	  subMatrix += (m(x-4, y), m(x-3,y), m(x-2,y), m(x-1,y), m(x,y), m(x+1,y), m(x+2,y), m(x+3,y), m(x+4,y))
	  
	  var count = 0
	  for (i <- 0 until subMatrix.length) {
		  if(cs.equals(subMatrix(i).state)) count+=1 else count=0
		  if (count == 5) {
			  gameOver = true
			  winList += (subMatrix(i-4), subMatrix(i-3), subMatrix(i-2), subMatrix(i-1), subMatrix(i))
		  }
	  }
	  gameOver
	}
}