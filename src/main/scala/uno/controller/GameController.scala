package uno.controller

import uno.models.*
import uno.models.cardColors.*
import uno.patterns.memento.*
import uno.patterns.strategy.*
import uno.patterns.command.*
import uno.util.*

class GameController(var round: Round) extends Observable:
  private val caretaker = new Caretaker
  private val undoManager = new UndoManager

  def initGame(): Unit =
    notifyObservers(Event.Start)
    startPlay()

  def startPlay(): Unit =
    notifyObservers(Event.Play)

  def quitGame(): Unit =
    notifyObservers(Event.Quit)

  def undo(): Unit =
    undoManager.undo(this)
    notifyObservers(Event.Undo)

  def redo(): Unit =
    undoManager.redo(this)
    notifyObservers(Event.Redo)

  def playCard(card: Card): Unit =
    val newPlayer = round.players(round.currentPlayer).playCard(card).get
    val newPlayers = round.players.updated(round.currentPlayer, newPlayer)
    if (newPlayer.hand.cards.isEmpty) {
      quitGame()
      return
    }
    val oldRound = round
    round = round.copy(
      players = newPlayers,
      topCard = card,
      currentPlayer = (round.currentPlayer + 1) % round.players.length
    )
    card.getValue match {
      case cardValues.DRAW_TWO =>
        val strategy = new DrawTwoStrategy
        strategy.execute(this)
      case cardValues.REVERSE =>
        val strategy = new ReverseStrategy
        strategy.execute(this)
      case cardValues.SKIP =>
        val strategy = new SkipStrategy
        strategy.execute(this)
      case cardValues.WILD =>
        notifyObservers(Event.ChooseColor)
      case cardValues.WILD_DRAW_FOUR =>
        notifyObservers(Event.ChooseColor)
      case _ => ()
    }
    executeCommand(new PlayCommand, oldRound, round)
    notifyObservers(Event.Play)

  def chooseColor(color: Int): Unit =
    val strategy =
      if round.topCard.getValue == cardValues.WILD then new WildStrategy
      else new WildDrawFourStrategy
    val card_color: Option[cardColors] = color match
      case 1 => Some(cardColors.RED)
      case 2 => Some(cardColors.BLUE)
      case 3 => Some(cardColors.GREEN)
      case 4 => Some(cardColors.YELLOW)
      case _ => None
    strategy.execute(this, card_color)

  def drawCard(): Unit =
    val newCard = CardFacade().randomCard
    val newPlayer = Player(
      round.currentPlayer,
      round.players(round.currentPlayer).hand.addCard(newCard)
    )
    val newPlayers = round.players.updated(round.currentPlayer, newPlayer)
    val oldRound = round
    round = round.copy(players = newPlayers)
    executeCommand(new PlayCommand, oldRound, round)
    notifyObservers(Event.Draw)

  private def executeCommand(
      command: Command,
      oldRound: Round,
      updatedRound: Round
  ): Unit = {
    // command.execute(this, round)
    undoManager.addCommand(
      new PlayCommand,
      oldRound,
      updatedRound
    )
  }

  private def saveState(): Unit =
    caretaker.addMemento(Memento(round))

  def restoreState(): Unit =
    caretaker.getMemento.foreach(memento => round = memento.round)
