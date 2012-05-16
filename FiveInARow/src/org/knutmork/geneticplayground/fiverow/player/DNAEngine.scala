package org.knutmork.geneticplayground.fiverow.player
import scala.collection.mutable.ArrayBuffer
import org.knutmork.geneticplayground.fiverow.game.Marker
import org.knutmork.geneticplayground.fiverow.game.Board
import org.knutmork.geneticplayground.fiverow.game.CellState

class DNAEngine (player : CellState.Value){
  val genes: ArrayBuffer[Gene] = new ArrayBuffer()
  genes += new Gene()
  
  def process(cells: ArrayBuffer[Marker], b : Board) : Marker = {
    val cellCandidates = cells.filter(cell => {
      val m = new ArrayBuffer[Marker]
      for (x <- -4 to 4) {
        for (y <- -4 to 4) {
          if (x != 0 && y != 0) { // Not include the pos that's about to be evaluated
            m += b.m(x+cell.pos._1, y+cell.pos._2)
          }
        }
      }
      val gene = genes.head // For now only one is permitted
      var ok = true
      (0 until gene.base.size).toStream.takeWhile(_ => ok).foreach(i => {
        ok = Integer.parseInt(gene.base.substring(i,i+1)) match {
          case Gene.UNKNOWN => true
          case Gene.NOT_SET => m(i).state.equals(CellState.NOT_SET)
          case Gene.ME => m(i).state.equals(player)
          case Gene.OPPONENT => (!m(i).state.equals(player) && !m(i).state.equals(CellState.NOT_SET))
          case other => println("Error in Gene data - not recognizable digit: " + gene.base.substring(i,i+1));false
        }
      })
      ok
    })
    cellCandidates.head
  }
}
