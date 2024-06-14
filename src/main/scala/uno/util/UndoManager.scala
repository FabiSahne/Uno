package uno.util

import uno.controller.GameController
import uno.models.Round
import uno.patterns.command.Command

class UndoManager {
  private var undoStack: List[(Command, Round)] = List()
  private var redoStack: List[(Command, Round)] = List()
  
  def addCommand(command: Command, round: Round): Unit = {
    undoStack = (command, round) :: undoStack
    redoStack = List()
  }

  def undo(gameController: GameController): Unit = {
    undoStack match {
      case Nil => println("Nothing to undo")
      case (command, round) :: tail =>
        command.undo(gameController, round)
        undoStack = tail
        redoStack = (command, round) :: redoStack
    }
  }

  def redo(gameController: GameController): Unit = {
    redoStack match {
      case Nil => println("Nothing to redo")
      case (command, round) :: tail =>
        command.redo(gameController, round)
        redoStack = tail
        undoStack = (command, round) :: undoStack
    }
  }
}