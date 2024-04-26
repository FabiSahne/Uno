package uno.controller

import uno.models.Card
import uno.util.*

class GameController extends Observable:
  def initGame(): Unit =
    notifyObservers(Event.Start)

  def quitGame(): Unit =
    notifyObservers(Event.Quit)

  def playCard(card: Card): Unit =
    notifyObservers(Event.Play)

  def drawCard(): Unit =
    notifyObservers(Event.Draw)
