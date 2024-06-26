package uno.controller

import uno.models.*
import uno.util.*
import uno.models.cardComponent.ICard
import uno.models.gameComponent.IRound

trait GameControllerInterface extends Observable {
  def getRound: IRound
  def setRound(round: IRound): Unit
  def initGame(): Unit
  def startPlay(): Unit
  def quitGame(): Unit
  def undo(): Unit
  def redo(): Unit
  def saveGame(): Unit
  def loadGame(): Unit
  def playCard(card: ICard): Unit
  def chooseColor(color: Int): Unit
  def drawCard(): Unit
  def restoreState(): Unit
}
