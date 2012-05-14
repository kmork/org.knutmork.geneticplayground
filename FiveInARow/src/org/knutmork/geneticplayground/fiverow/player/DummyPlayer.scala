package org.knutmork.geneticplayground.fiverow.player
import org.knutmork.geneticplayground.fiverow.game.Board
import org.knutmork.geneticplayground.fiverow.game.Player

class DummyPlayer (board : Board) extends Player{
	def playMove() {
	  board.setMarker(10, 10)
	}
	
	def yourTurn() {
	  
	}
}