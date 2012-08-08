package org.knutmork.geneticplayground.gomoku.game

case class Marker (x : Int, y: Int, state: CellState.Value){
	def this(x: Int, y: Int) = this(x, y, CellState.NOT_SET)
	def empty = CellState.NOT_SET == state	
	def pos = (x,y)
}