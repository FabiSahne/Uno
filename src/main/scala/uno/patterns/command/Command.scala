package uno.patterns.command

import uno.controller.GameController

trait Command {
  def execute(gameController: GameController): Unit
}

case class SkipCommand() extends Command {
  override def execute(gameController: GameController): Unit = {
    // Implement the effect of a Skip card
    println("Skip command executed")
  }
}

case class ReverseCommand() extends Command {
  override def execute(gameController: GameController): Unit = {
    // Implement the effect of a Reverse card
    println("Reverse command executed")
  }
}

case class DrawTwoCommand() extends Command {
  override def execute(gameController: GameController): Unit = {
    // Implement the effect of a Draw Two card
    println("Draw Two command executed")
  }
}

case class WildCommand() extends Command {
  override def execute(gameController: GameController): Unit = {
    // Implement the effect of a Wild card
    println("Wild command executed")
  }
}

case class WildDrawFourCommand() extends Command {
  override def execute(gameController: GameController): Unit = {
    // Implement the effect of a Wild Draw Four card
    println("Wild Draw Four command executed")
  }
}
