package org.knutmork.geneticplayground.fiverow.game

trait Player {
	def yourTurn(board: Board)
	def youWon(board: Board) {}
	def youLost(board: Board) {}
}