package org.knutmork.geneticplayground.fiverow.game

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
	
	def pos : (Int, Int) = (x-5,y-5) // Should have used CELL_PREFIX from Board class
}