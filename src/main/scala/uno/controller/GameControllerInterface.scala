package uno.controller

import uno.models.*
import uno.models.cardComponent.cardImp.Card
import uno.util.*
import uno.controller.GControllerImp
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
  def playCard(card: ICard): Unit
  def chooseColor(color: Int): Unit
  def drawCard(): Unit
  def restoreState(): Unit
}