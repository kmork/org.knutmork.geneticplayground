package org.knutmork.geneticplayground.fiverow.gui
import javax.swing.table.TableCellRenderer
import javax.swing.JTable
import scala.swing.BorderPanel
import scala.swing.Label
import org.knutmork.geneticplayground.fiverow.game.Board

class WinningCellRenderer(board : Board) extends BorderPanel with TableCellRenderer {
  val label = new Label
  add(label, BorderPanel.Position.Center)

  def getTableCellRendererComponent(table: JTable, value: Any, isSelected: Boolean, hasFocus: Boolean, row: Int, column: Int) = {
    label.foreground = java.awt.Color.RED
    label.text = board.nextPlayer().toString()
    label.background = java.awt.Color.WHITE
    peer
  }
}