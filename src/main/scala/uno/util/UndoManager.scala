package uno.util

import uno.controller.GameController
import uno.patterns.command.Command
import uno.patterns.memento.Memento

class UndoManager {
  private var undoStack: List[(Command, Memento)] = List()
  private var redoStack: List[(Command, Memento)] = List()
  
  def addCommand(command: Command, memento: Memento): Unit = {
    undoStack = (command, memento) :: undoStack
    redoStack = List()
  }

  def undo(gameController: GameController): Unit = {
    undoStack match {
      case Nil => println("Nothing to undo")
      case (command, memento) :: tail =>
        command.undo(gameController, memento)
        undoStack = tail
        redoStack = (command, memento) :: redoStack
    }
  }

  def redo(gameController: GameController): Unit = {
    redoStack match {
      case Nil => println("Nothing to redo")
      case (command, memento) :: tail =>
        command.redo(gameController, memento)
        redoStack = tail
        undoStack = (command, memento) :: undoStack
    }
  }
}