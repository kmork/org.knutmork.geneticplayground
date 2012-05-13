package org.knutmork.geneticplayground.fiverow.game
import scala.collection.mutable._

class Board {
	val NUM_ROWS = 30
	val NUM_COLS = 30
	val data = new ArrayBuffer[Marker]
	
	for (i <- 0 to NUM_ROWS-1){
	  for (j <- 0 to NUM_COLS-1) {
	    data += new Marker(i, j)
	  }
	}
	
	m(9,9).setState(CellState.X)
	var lastMarker : (Int, Int, CellState.Value) = (9,9, CellState.X)
	var winList = new ArrayBuffer[Marker]

	def legalMove(x: Int, y : Int) : Boolean = {
	  if(m(x,y).empty()) {
	    return !m(x-1,y-1).empty || !m(x-1,y).empty || !m(x-1,y+1).empty || !m(x,y-1).empty || !m(x,y+1).empty || !m(x+1,y-1).empty || !m(x+1,y).empty || !m(x+1,y+1).empty	    
	  }
	  false
	}
	
	def setMarker(x : Int, y : Int) : Boolean ={
	  if(!gameOver && legalMove(x, y)) {
		  m(x,y).setState(currPlayer)
		  lastMarker = (x, y, currPlayer)
		  true
	  } else false
	}
	
	def currPlayer() : CellState.Value = if (CellState.X.equals(lastMarker._3)) CellState.Y else CellState.X
	def nextPlayer() : CellState.Value = lastMarker._3
	
	def m(x : Int, y : Int) : Marker = {
	  data((x+5)*NUM_ROWS+(y+5))
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