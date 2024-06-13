package uno.patterns.command

import uno.controller.GameController

trait Command {
  def execute(gameController: GameController): Unit
  def undo(gameController: GameController): Unit
  def redo(gameController: GameController): Unit
}

case class SkipCommand() extends Command {
  override def execute(gameController: GameController): Unit = {
    println("Skip command executed")
  }
  override def undo(gameController: GameController): Unit = {
      println("Skip command undone")
  }
  override def redo(gameController: GameController): Unit = {
      println("Skip command redone")
  }
}

case class ReverseCommand() extends Command {
  override def execute(gameController: GameController): Unit = {
    println("Reverse command executed")
  }
  override def undo(gameController: GameController): Unit = {
      println("Reverse command undone")
  }
  override def redo(gameController: GameController): Unit = {
    println("Reverse command redone")
  }
}

case class DrawTwoCommand() extends Command {
  override def execute(gameController: GameController): Unit = {
    println("Draw Two command executed")
  }
  override def undo(gameController: GameController): Unit = {
      println("Draw Two command undone")
  }
  override def redo(gameController: GameController): Unit = {
      println("Draw Two command redone")
  }
}

case class WildCommand() extends Command {
  override def execute(gameController: GameController): Unit = {
    println("Wild command executed")
  }
  override def undo(gameController: GameController): Unit = {
      println("Wild command undone")
  }
  override def redo(gameController: GameController): Unit = {
      println("Wild command redone")
  }
}

case class WildDrawFourCommand() extends Command {
  override def execute(gameController: GameController): Unit = {
    println("Wild Draw Four command executed")
  }
  override def undo(gameController: GameController): Unit = {
      println("Wild Draw Four command undone")
  }
  override def redo(gameController: GameController): Unit = {
      println("Wild Draw Four command redone")
  }
}
