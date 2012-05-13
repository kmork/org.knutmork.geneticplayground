package org.knutmork.geneticplayground.fiverow.gui

import scala.Array._
import scala.swing.event._
import scala.swing._

import org.knutmork.geneticplayground.fiverow.game._

import javax.swing.table.DefaultTableCellRenderer
import javax.swing.JComponent
import javax.swing.JTable

class Main (board: Board) {

  def startup() {
    val t = new MainFrame {
      title = "Five in a Row"
      preferredSize = new Dimension(400, 400)
      contents = ui
    }
    t.visible = true
  }

  val headers = Array.tabulate(20) { "" + _ }.toSeq
  var rowData = ofDim[Any](20, 20)
  val tcr = new WinningCellRenderer(board)
  val dtcr = new DefaultTableCellRenderer

  lazy val ui = new BoxPanel(Orientation.Vertical) {
    val table = new Table(rowData, headers) {
      selection.elementMode = Table.ElementMode.Cell

      // For marking the winning sequence
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
        if (board.setMarker(x, y)) {
          table.update(x, y, board.nextPlayer().toString())
          if (board.gameOver()) {
            label.text = "GAME OVER - Player" + board.nextPlayer.toString() + " won"
            board.winList.foreach { m =>
              table.update(m.pos._1, m.pos._2, " Z")
            }
          } else {
            label.text = "Player " + board.currPlayer.toString()
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
    rowData(9)(9) = board.nextPlayer().toString()
  }
}
