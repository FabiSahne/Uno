package uno

import uno.controller.GameController
import uno.models.Round
import uno.views.{GUI, TUI}

@main def main(): Unit = {
  val round = Round()
  val controller = new GameController(round)
  // GUI.launchApp(controller)

   val tui = new TUI(controller)
   tui.startGame()
}