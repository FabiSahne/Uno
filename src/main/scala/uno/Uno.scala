package uno

import uno.controller.GameController
import uno.models.Round
import uno.views.{GUI, TUI}
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

@main def main(): Unit = {
  val round = Round()
  val controller = new GameController(round)
  GUI.launchApp(controller)

   Future {
     val tui = new TUI(controller)
     tui.startGame() 
   }
}