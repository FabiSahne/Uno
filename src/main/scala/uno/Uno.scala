package uno

import com.google.inject.{Guice, Injector}
import uno.controller.GControllerImp.GameController
import uno.controller.GameControllerInterface
import uno.models.cardComponent.cardImp._
import uno.models.gameComponent.gameImp.{Hand, Round}
import uno.models.playerComponent.playerImp.Player
import uno.views.{GUI, TUI}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@main def main(): Unit = {
  val injector: Injector = Guice.createInjector(new UnoModule)
  val controller = injector.getInstance(classOf[GameControllerInterface])
  
  val players = List(Player(0, Hand(List())))
  val topCard = randomCard
  val currentPlayer = 0

  val round = Round(players, topCard, currentPlayer)

  Future {
    GUI.launchApp(controller)
  }

  val tui = new TUI(controller)

  tui.startGame()
}
