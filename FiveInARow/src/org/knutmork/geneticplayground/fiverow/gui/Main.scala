package org.knutmork.geneticplayground.fiverow.gui

import scala.swing._
import Array._
import scala.swing.event._
import org.knutmork.geneticplayground.fiverow.game._
import javax.swing.table.TableCellRenderer
import javax.swing.JTable
import javax.swing.UIManager
import javax.swing.table.DefaultTableCellRenderer
import javax.swing.JComponent

object Main extends SimpleGUIApplication {

  val headers = Array.tabulate(20) { "" + _ }.toSeq
  var rowData = ofDim[Any](20, 20)
  val board = new Board()
  var playerX = false
  val tcr = new TCR
  val dtcr = new DefaultTableCellRenderer

  lazy val ui = new BoxPanel(Orientation.Vertical) {
    val table = new Table(rowData, headers) {
      selection.elementMode = Table.ElementMode.Cell

      override protected def rendererComponent(isSelected: Boolean, hasFocus: Boolean, row: Int, column: Int) = {
        if (model.getValueAt(row, column) != null && " Z".equals(String.valueOf(model.getValueAt(row, column))))
        	Component.wrap(tcr.getTableCellRendererComponent(peer, model.getValueAt(row, column), isSelected, hasFocus, row, column).asInstanceOf[JComponent])
        else
        	Component.wrap(dtcr.getTableCellRendererComponent(peer, model.getValueAt(row, column), isSelected, hasFocus, row, column).asInstanceOf[JComponent])
      }
    }

    table.showGrid = true
    table.gridColor = java.awt.Color.DARK_GRAY
    table.selectionBackground = java.awt.Color.LIGHT_GRAY
    val label = new Label("")
    val header = table.peer.getTableHeader()

    listenTo(table.selection)

    def outputSelection(x: Int, y: Int) {
      if (x > -1 && y > -1) {
        if (board.legalMove(x, y)) {
          board.setMarker(x, y, (if (playerX) CellState.X else CellState.Y))
          table.update(x, y, (if (playerX) " X" else " O"))
          if (board.gameOver()) {
            label.text = "GAME OVER - Player" + (if (playerX) " X" else " O") + " won"
            board.winList.foreach { m =>
              table.update(m.pos._1, m.pos._2, " Z")
            }
          } else {
            playerX = !playerX
            label.text = "Player " + (if (playerX) "X" else "O")
          }
        }
      }
    }

    reactions += {
      case TableColumnsSelected(_, r, false) =>
        outputSelection(table.selection.rows.leadIndex, table.selection.columns.leadIndex)
      case TableRowsSelected(_, r, false) =>
        outputSelection(table.selection.rows.leadIndex, table.selection.columns.leadIndex)
    }

    contents += new ScrollPane(table)
    contents += label
    rowData(9)(9) = " X"
    board.setMarker(9, 9, CellState.X)
  }

  def top = new MainFrame {
    title = "Five in a Row"
    preferredSize = new Dimension(400, 400)
    contents = ui
  }

  // For marking the winning sequence
  class TCR extends BorderPanel with TableCellRenderer {
    val label = new Label
    add(label, BorderPanel.Position.Center)

    def getTableCellRendererComponent(table: JTable, value: Any, isSelected: Boolean, hasFocus: Boolean, row: Int, column: Int) = {
      if (value != null && " Z".equals(String.valueOf(value))) {
        label.foreground = java.awt.Color.RED
        label.text = if (playerX) " X" else " O"
      } 
      else {
        label.text = if (value == null) "" else String.valueOf(value)
        label.foreground = table.getForeground
      }
      label.background = java.awt.Color.WHITE
      peer
    }
  }
}
