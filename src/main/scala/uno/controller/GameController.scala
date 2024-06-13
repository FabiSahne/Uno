package uno.controller

import uno.models.*
import uno.patterns.memento.*
import uno.patterns.strategy.*
import uno.util.*

class GameController(var round: Round) extends Observable:
  private val caretaker = new Caretaker
  def initGame(): Unit =
    notifyObservers(Event.Start)
    startPlay()

  def startPlay(): Unit =
    notifyObservers(Event.Play)

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
      topCard = card
    )
    card.getValue match {
      case cardValues.DRAW_TWO =>
        val strategy = new DrawTwoCommand
        executeCommand(strategy)
      case cardValues.REVERSE =>
        val strategy = new ReverseCommand
        executeCommand(strategy)
      case cardValues.SKIP =>
        val strategy = new SkipCommand
        executeCommand(strategy)
      case cardValues.WILD =>
        notifyObservers(Event.ChooseColor)
      case cardValues.WILD_DRAW_FOUR =>
        notifyObservers(Event.ChooseColor)
        val strategy = new WildDrawFourCommand
        executeCommand(strategy)
      case _ => ()
    }
    val direction = round.direction;
    val newPlayerIdx =
      if (round.currentPlayer + direction < 0)
        round.players.length - 1
      else (round.currentPlayer + direction) % round.players.length
    round = round.copy(
      currentPlayer = newPlayerIdx
    )
    notifyObservers(Event.Play)

  def chooseColor(color: Int): Unit =
    val strategy =
      if round.topCard.getValue == cardValues.WILD_DRAW_FOUR then
        new WildDrawFourCommand
      else new WildCommand
    val card_color: Option[cardColors] = color match
      case 1 => Some(cardColors.RED)
      case 2 => Some(cardColors.BLUE)
      case 3 => Some(cardColors.GREEN)
      case 4 => Some(cardColors.YELLOW)
      case _ => None
    executeCommand(strategy, card_color)

  def drawCard(): Unit =
    val newCard = CardFacade().randomCard
    val newPlayer = Player(
      round.currentPlayer,
      round.players(round.currentPlayer).hand.addCard(newCard)
    )
    val newPlayers = round.players.updated(round.currentPlayer, newPlayer)
    saveState()
    round = round.copy(players = newPlayers)
    notifyObservers(Event.Draw)

  private def executeCommand(
      strategy: CardStrategy,
      color: Option[cardColors] = None
  ): Unit = {
    strategy.execute(this, color)
  }

  private def saveState(): Unit =
    caretaker.addMemento(Memento(round))

  def restoreState(): Unit =
    caretaker.getMemento.foreach(memento => round = memento.round)
