package uno

import uno.controller.GameController
import uno.views.TUI

@main def hello(): Unit = {
  val controller = new GameController()
  val tui = new TUI(controller)
  tui.startGame()
}