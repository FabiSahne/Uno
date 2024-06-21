package uno

import uno.controller.GControllerImp.GameController
import uno.models.cardComponent.cardImp.CardFacade
import uno.models.gameComponent.gameImp.{Hand, Round}
import uno.models.playerComponent.playerImp.Player
import uno.views.{GUI, TUI}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@main def main(): Unit = {
  val players = List(Player(0, Hand(List())))
  val topCard = CardFacade().randomCard
  val currentPlayer = 0

  val round = Round(players, topCard, currentPlayer)
  val controller = new GameController(round)

  Future {
    GUI.launchApp(controller)
  }

  val tui = new TUI(controller)

  tui.startGame()
}
