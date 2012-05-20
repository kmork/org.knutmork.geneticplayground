package org.knutmork.geneticplayground.fiverow.gui

import scala.Array.ofDim
import scala.swing.event.TableColumnsSelected
import scala.swing.event.TableRowsSelected
import scala.swing.Dimension
import scala.swing.BoxPanel
import scala.swing.Component
import scala.swing.Label
import scala.swing.MainFrame
import scala.swing.Orientation
import scala.swing.ScrollPane
import scala.swing.Table

import org.knutmork.geneticplayground.fiverow.game.Board
import org.knutmork.geneticplayground.fiverow.game.Player

import javax.swing.table.DefaultTableCellRenderer
import javax.swing.JComponent

class HumanPlayer(board: Board) extends Player {

  board.addPlayer(this)
  var boardMatrixPos = (0, 0)

  val label = new Label("")
  val table = new Table(ofDim[Any](30, 30), Array.tabulate(30) { "" + _ }.toSeq) {
    selection.elementMode = Table.ElementMode.Cell
    val tcr = new WinningCellRenderer(board)
    val dtcr = new DefaultTableCellRenderer

    // For marking the winning sequence
    override protected def rendererComponent(isSelected: Boolean, hasFocus: Boolean, row: Int, column: Int) = {
      if (model.getValueAt(row, column) != null && " Z".equals(String.valueOf(model.getValueAt(row, column))))
        Component.wrap(tcr.getTableCellRendererComponent(peer, model.getValueAt(row, column), isSelected, hasFocus, row, column).asInstanceOf[JComponent])
      else
        Component.wrap(dtcr.getTableCellRendererComponent(peer, model.getValueAt(row, column), isSelected, hasFocus, row, column).asInstanceOf[JComponent])
    }
  }

  def startup() {
    val t = new MainFrame {
      title = "Five in a Row"
      preferredSize = new Dimension(500, 500)
      contents = ui
    }
    t.visible = true
  }

  def yourTurn() {
    table.update(board.lastMarker._1 + boardMatrixPos._1, board.lastMarker._2 + boardMatrixPos._2, board.lastMarker._3)
  }

  def youWon() {
    label.text = "GAME OVER - Player" + board.nextPlayer.toString() + " won"
    board.winList.foreach { m =>
      table.update(m.pos._1 + boardMatrixPos._1, m.pos._2 + boardMatrixPos._2, " Z")
    }
  }

  lazy val ui = new BoxPanel(Orientation.Vertical) {
    table.showGrid = true
    table.gridColor = java.awt.Color.DARK_GRAY
    table.selectionBackground = java.awt.Color.LIGHT_GRAY
    val header = table.peer.getTableHeader()
    var initialMove = true

    listenTo(table.selection)

    def outputSelection(x: Int, y: Int) {
      if (x > -1 && y > -1) {
        if (initialMove) {
          boardMatrixPos = (x, y)
          initialMove = false
        }
        if (board.placeMarker(x - boardMatrixPos._1, y - boardMatrixPos._2)) {
          if(!board.gameOver()) {
            table.update(x, y, board.currPlayer().toString())
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
  }
}
