package uno.controller

import uno.patterns.command.*
import uno.models.*
import uno.patterns.memento.*
import uno.util.*

class GameController(var round: Round) extends Observable:
  private val caretaker = new Caretaker
  def initGame(): Unit =
    notifyObservers(Event.Start)

  def quitGame(): Unit =
    notifyObservers(Event.Quit)

  def playCard(card: Card): Unit =
    val newPlayer = round.players(round.currentPlayer).playCard(card).get
    val newPlayers = round.players.updated(round.currentPlayer, newPlayer)
    if (newPlayer.hand.cards.isEmpty) {
      quitGame()
      return
    }
    saveState()
    round = round.copy(
      players = newPlayers,
      topCard = card,
      currentPlayer = (round.currentPlayer + 1) % round.players.length
    )
    card.value match {
      case cardValues.DRAW_TWO =>
        val command = new DrawTwoCommand
        executeCommand(command)
      case cardValues.REVERSE =>
        val command = new ReverseCommand
        executeCommand(command)
      case cardValues.SKIP =>
        val command = new SkipCommand
        executeCommand(command)
      case cardValues.WILD =>
        val command = new WildCommand
        executeCommand(command)
      case cardValues.WILD_DRAW_FOUR =>
        val command = new WildDrawFourCommand
        executeCommand(command)
      case _ => ()
    }
    notifyObservers(Event.Play)

  def drawCard(): Unit =
    val newCard = randomCard
    val newPlayer = Player(
      round.currentPlayer,
      round.players(round.currentPlayer).hand.addCard(newCard)
    )
    val newPlayers = round.players.updated(round.currentPlayer, newPlayer)
    saveState()
    round = round.copy(players = newPlayers)
    notifyObservers(Event.Draw)

  private def executeCommand(command: Command): Unit = {
    command.execute(this)
  }

  private def saveState(): Unit =
    caretaker.addMemento(Memento(round))

  private def restoreState(): Unit =
    caretaker.getMemento.foreach(memento => round = memento.round)
