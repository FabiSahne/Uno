package uno.controller

import uno.models.*
import uno.models.cardComponent.cardImp.Card
import uno.util.*

trait GameControllerInterface extends Observable {
  def initGame(): Unit
  def startPlay(): Unit
  def quitGame(): Unit
  def undo(): Unit
  def redo(): Unit
  def playCard(card: Card): Unit
  def chooseColor(color: Int): Unit
  def drawCard(): Unit
  def restoreState(): Unit
}