package org.knutmork.geneticplayground.gomoku.game

trait Player {
	def yourTurn(board: Board)
	def youWon(board: Board) {}
	def youLost(board: Board) {}
}