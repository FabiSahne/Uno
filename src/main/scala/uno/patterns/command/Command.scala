package uno.patterns.command

import uno.controller.GControllerImp.GameController
import uno.models.gameComponent.gameImp.Round
import uno.patterns.memento.Memento

trait Command {
  def undo(gameController: GameController, round: Round): Unit
  def redo(gameController: GameController, round: Round): Unit
}

case class PlayCommand() extends Command {
  
  override def undo(gameController: GameController, round: Round): Unit = {
    gameController.round = round
  }

  override def redo(gameController: GameController, round: Round): Unit = {
    gameController.round = round
  }
}