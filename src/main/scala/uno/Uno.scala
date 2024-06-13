package uno

import uno.controller.GameController
import uno.models.Round
import uno.views.{GUI, TUI}

@main def main(): Unit = {
  val round = Round()
  val controller = new GameController(round)

  val tui = new TUI(controller)
  new Thread(() => tui.startGame()).start()

  GUI.launchApp(controller)
}
