package uno.util

import uno.controller.GControllerImp.GameController
import uno.models.gameComponent.gameImp.Round
import uno.patterns.command.Command

class UndoManager {
  private var undoStack: List[(Command, Round, Round)] = List()
  private var redoStack: List[(Command, Round, Round)] = List()

  def addCommand(
      command: Command,
      oldRound: Round,
      updatedRound: Round
  ): Unit = {
    undoStack = (command, oldRound, updatedRound) :: undoStack
    redoStack = List()
  }

  def undo(gameController: GameController): Unit = {
    undoStack match {
      case Nil => println("Nothing to undo")
      case (command, oldRound, updatedRound) :: tail =>
        command.undo(gameController, oldRound)
        undoStack = tail
        redoStack = (command, oldRound, updatedRound) :: redoStack
    }
  }

  def redo(gameController: GameController): Unit = {
    redoStack match {
      case Nil => println("Nothing to redo")
      case (command, oldRound, updatedRound) :: tail =>
        command.redo(gameController, updatedRound)
        redoStack = tail
        undoStack = (command, oldRound, updatedRound) :: undoStack
    }
  }
}
