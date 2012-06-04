package org.knutmork.geneticplayground.gomoku.game

class Marker (x : Int, y: Int){
	var state = CellState.NOT_SET

	def setState(newState: CellState.Value){
	  if(state == CellState.NOT_SET) {
	    state = newState
	  }
	}
	
	def empty() : Boolean = {
	  CellState.NOT_SET.equals(state)
	}
	
	def pos : (Int, Int) = (x,y)
}