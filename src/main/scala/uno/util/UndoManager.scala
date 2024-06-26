package uno.util

import uno.controller.GControllerImp.GameController
import uno.models.gameComponent.IRound
import uno.patterns.command.Command

class UndoManager {
  private var undoStack: List[(Command, IRound, IRound)] = List()
  private var redoStack: List[(Command, IRound, IRound)] = List()

  def addCommand(
      command: Command,
      oldRound: IRound,
      updatedRound: IRound
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
