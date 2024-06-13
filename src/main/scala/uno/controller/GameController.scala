package uno.controller

import uno.models.*
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
    saveState()
    round = round.copy(
      players = newPlayers,
      topCard = card,
      currentPlayer = (round.currentPlayer + 1) % round.players.length
    )
    card.getValue match {
      case cardValues.DRAW_TWO =>
        val strategy = new DrawTwoStrategy
        executeStrategy(strategy)
      case cardValues.REVERSE =>
        val strategy = new ReverseStrategy
        executeStrategy(strategy)
      case cardValues.SKIP =>
        val strategy = new SkipStrategy
        executeStrategy(strategy)
      case cardValues.WILD =>
        notifyObservers(Event.ChooseColor)
      case cardValues.WILD_DRAW_FOUR =>
        notifyObservers(Event.ChooseColor)
        val strategy = new WildDrawFourStrategy
        executeStrategy(strategy)
      case _ => ()
    }
    notifyObservers(Event.Play)

  def chooseColor(color: Int): Unit =
    val strategy = round.topCard.getValue match
      case cardValues.WILD           => new WildStrategy
      case cardValues.WILD_DRAW_FOUR => new WildDrawFourStrategy
    val card_color: Option[cardColors] = color match
      case 1 => Some(cardColors.RED)
      case 2 => Some(cardColors.BLUE)
      case 3 => Some(cardColors.GREEN)
      case 4 => Some(cardColors.YELLOW)
      case _ => None
    executeStrategy(strategy, card_color)

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

  private def executeStrategy(strategy: CardStrategy, color: Option[cardColors] = None): Unit = {
    val memento = Memento(round.copy())
    strategy.execute(this, color)
    undoManager.addCommand(new Command {
      override def undo(gameController: GameController, memento: Memento): Unit =
        gameController.round = memento.round
      override def redo(gameController: GameController, memento: Memento): Unit =
        gameController.round = memento.round
    }, memento)
  }

  private def saveState(): Unit =
    caretaker.addMemento(Memento(round))

  def restoreState(): Unit =
    caretaker.getMemento.foreach(memento => round = memento.round)
