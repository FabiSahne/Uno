package uno.util

import uno.controller.GameController
import uno.patterns.command.Command

class UndoManager {
  private var undoStack: List[Command] = List()
  private var redoStack: List[Command] = List()

  def execute(command: Command, gameController: GameController): Unit = {
    command.execute(gameController)
    undoStack = command :: undoStack
    redoStack = List()
  }

  def undo(gameController: GameController): Unit = {
    undoStack match {
      case Nil => println("Nothing to undo")
      case head :: tail =>
        head.undo(gameController)
        undoStack = tail
        redoStack = head :: redoStack
    }
  }

  def redo(gameController: GameController): Unit = {
    redoStack match {
      case Nil => println("Nothing to redo")
      case head :: tail =>
        head.redo(gameController)
        redoStack = tail
        undoStack = head :: undoStack
    }
  }
}