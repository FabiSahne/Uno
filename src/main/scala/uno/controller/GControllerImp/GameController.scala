package uno.controller.GControllerImp

import uno.controller.GameControllerInterface
import uno.models.cardComponent.cardImp._
import uno.models.gameComponent.gameImp.Round
import uno.models.playerComponent.playerImp.Player
import uno.patterns.command._
import uno.patterns.memento._
import uno.patterns.strategy._
import uno.util._

class GameController(var round: Round) extends Observable with GameControllerInterface {
  private val caretaker = new Caretaker
  private val undoManager = new UndoManager

  def initGame(): Unit = {
    notifyObservers(Event.Start)
    startPlay()
  }

  def startPlay(): Unit = {
    notifyObservers(Event.Play)
  }

  def quitGame(): Unit = {
    notifyObservers(Event.Quit)
  }

  def undo(): Unit = {
    undoManager.undo(this)
    notifyObservers(Event.Undo)
  }

  def redo(): Unit = {
    undoManager.redo(this)
    notifyObservers(Event.Redo)
  }

  def playCard(card: Card): Unit = {
    val newPlayer = round.players(round.currentPlayer).playCard(card).get
    val newPlayers = round.players.updated(round.currentPlayer, newPlayer)
    if (newPlayer.hand.cards.isEmpty) {
      quitGame()
      return
    }
    saveState()
    executeCommand(new PlayCommand, round)
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
    notifyObservers(Event.Play)
  }

  def chooseColor(color: Int): Unit = {
    val strategy =
      if (round.topCard.getValue == cardValues.WILD) new WildStrategy
      else new WildDrawFourStrategy
    val cardColor: Option[cardColors] = color match {
      case 1 => Some(cardColors.RED)
      case 2 => Some(cardColors.BLUE)
      case 3 => Some(cardColors.GREEN)
      case 4 => Some(cardColors.YELLOW)
      case _ => None
    }
    strategy.execute(this, cardColor)
  }

  def drawCard(): Unit = {
    val newCard = CardFacade().randomCard
    val newPlayer = Player(
      round.currentPlayer,
      round.players(round.currentPlayer).hand.addCard(newCard)
    )
    val newPlayers = round.players.updated(round.currentPlayer, newPlayer)
    saveState()
    round = round.copy(players = newPlayers)
    notifyObservers(Event.Draw)
  }

  private def executeCommand(
                              command: Command,
                              round: Round
                            ): Unit = {
    // command.execute(this, round)
    undoManager.addCommand(
      new PlayCommand,
      round
    )
  }

  private def saveState(): Unit = {
    caretaker.addMemento(Memento(round))
  }

  def restoreState(): Unit = {
    caretaker.getMemento.foreach(memento => round = memento.round)
  }
}