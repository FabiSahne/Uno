package uno.patterns.command

import uno.controller.GameControllerInterface
import uno.models.gameComponent.IRound

trait Command {
  def undo(gameController: GameControllerInterface, round: IRound): Unit
  def redo(gameController: GameControllerInterface, round: IRound): Unit
}

case class PlayCommand() extends Command {
  
  override def undo(gameController: GameControllerInterface, round: IRound): Unit = {
    gameController.setRound(round)
  }

  override def redo(gameController: GameControllerInterface, round: IRound): Unit = {
    gameController.setRound(round)
  }
}