package org.knutmork.geneticplayground.fiverow.game

object CellState extends Enumeration {
  type CellState = Value
  val X = Value(" X")
  val Y = Value(" O")
  val NOT_SET = Value("  ")
}