package org.knutmork.geneticplayground.fiverow.player
import scala.collection.mutable.ArrayBuffer
import org.knutmork.geneticplayground.fiverow.game.Marker
import org.knutmork.geneticplayground.fiverow.game.Board
import org.knutmork.geneticplayground.fiverow.game.CellState

class DNAEngine(player: CellState.Value) {
  val genes: ArrayBuffer[Gene] = new ArrayBuffer()
  genes += new Gene()

  def process(cells: ArrayBuffer[Marker], b: Board): Marker = {
    cells.foldLeft[(Int, Marker)](Int.MaxValue, new Marker(0,0)) { (lowestCellPoint, cell) => 
      val m = mapBoardToArray(cell, b)
      var points = genes.size
      var ok = false
      (0 until genes.size).toStream.takeWhile(_ => !ok).foreach(g => {
        points = g
        val gene = genes(g)
        ok = true
        (0 until gene.base.size).toStream.takeWhile(_ => ok).foreach(i => {
          ok = Integer.parseInt(gene.base.substring(i, i + 1)) match {
            case Gene.UNKNOWN => true
            case Gene.NOT_SET => m(i).state.equals(CellState.NOT_SET)
            case Gene.ME => m(i).state.equals(player)
            case Gene.OPPONENT => (!m(i).state.equals(player) && !m(i).state.equals(CellState.NOT_SET))
            case other => println("Error in Gene data - not recognizable digit: " + gene.base.substring(i, i + 1)); false
          }
        })
      })
      if (points < lowestCellPoint._1) {(points, cell)} else {lowestCellPoint}
    }._2
  }

  def mapBoardToArray(cell: Marker, b: Board): ArrayBuffer[Marker] = {
    val m = new ArrayBuffer[Marker] 
    for (x <- -4 to 4) {
      for (y <- -4 to 4) {
        if (x != 0 && y != 0) { // Not include the pos that's about to be evaluated
          m += b.m(x + cell.pos._1, y + cell.pos._2)
        }
      }
    }
    m
  }
}
