package uno.patterns.command

import uno.controller.GameController
import uno.patterns.memento.Memento

trait Command {
  def undo(gameController: GameController, memento: Memento): Unit
  def redo(gameController: GameController, memento: Memento): Unit
}

case class SkipCommand() extends Command {
  override def undo(gameController: GameController, memento: Memento): Unit = {
    gameController.round = memento.round
    println("Skip command undone")
  }
  override def redo(gameController: GameController, memento: Memento): Unit = {
    gameController.round = memento.round
    println("Skip command redone")
  }
}

case class ReverseCommand() extends Command {
  override def undo(gameController: GameController, memento: Memento): Unit = {
    gameController.round = memento.round
    println("Reverse command undone")
  }
  override def redo(gameController: GameController, memento: Memento): Unit = {
    gameController.round = memento.round
    println("Reverse command redone")
  }
}

case class DrawTwoCommand() extends Command {
  override def undo(gameController: GameController, memento: Memento): Unit = {
    gameController.round = memento.round
    println("Draw Two command undone")
  }
  override def redo(gameController: GameController, memento: Memento): Unit = {
    gameController.round = memento.round
    println("Draw Two command redone")
  }
}

case class WildCommand() extends Command {
  override def undo(gameController: GameController, memento: Memento): Unit = {
    gameController.round = memento.round
    println("Wild command undone")
  }
  override def redo(gameController: GameController, memento: Memento): Unit = {
    gameController.round = memento.round
    println("Wild command redone")
  }
}

case class WildDrawFourCommand() extends Command {
  override def undo(gameController: GameController, memento: Memento): Unit = {
    gameController.round = memento.round
    println("Wild Draw Four command undone")
  }
  override def redo(gameController: GameController, memento: Memento): Unit = {
    gameController.round = memento.round
    println("Wild Draw Four command redone")
  }
}