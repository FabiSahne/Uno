package uno.controller.GControllerImp

import com.google.inject.{Guice, Inject, Injector}
import uno.UnoModule
import uno.controller.GameControllerInterface
import uno.models.cardComponent.cardImp.*
import uno.models.gameComponent.IRound
import uno.models.gameComponent.gameImp.{Hand, Round}
import uno.models.cardComponent.ICard
import uno.models.fileioComponent.IFileIO
import uno.models.playerComponent.playerImp.Player
import uno.patterns.command.*
import uno.patterns.memento.*
import uno.patterns.strategy.*
import uno.util.*

class GameController @Inject() (var round: IRound)
    extends Observable
    with GameControllerInterface {
  private val caretaker = new Caretaker
  private val undoManager = new UndoManager

  val injector: Injector = Guice.createInjector(new UnoModule)
  private val fileIO = injector.getInstance(classOf[IFileIO])

  def getRound: IRound = round

  def setRound(round: IRound): Unit = {
    this.round = round
  }

  def initGame(): Unit = {
    round = Round(
      players =
        (0 until 4).map(i => Player(i, Hand(List.fill(7)(randomCard)))).toList,
      topCard = Card(Some(randomColor), randomNormalValue),
      currentPlayer = 0
    )
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

  def playCard(card: ICard): Unit = {
    val newPlayer = round.players(round.currentPlayer).playCard(card).get
    val newPlayers = round.players.updated(round.currentPlayer, newPlayer)
    if (newPlayer.hand.getCards.isEmpty) {
      quitGame()
      return
    }
    val oldRound = round
    round = Round(
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
    val newCard = randomCard
    val newPlayer = Player(
      round.currentPlayer,
      round.players(round.currentPlayer).hand.addCard(newCard)
    )
    val newPlayers = round.players.updated(round.currentPlayer, newPlayer)
    val oldRound = round
    round = round.copy(players = newPlayers)
    executeCommand(new PlayCommand, oldRound, round)
    notifyObservers(Event.Draw)
  }

  private def executeCommand(
      command: Command,
      oldRound: IRound,
      updatedRound: IRound
  ): Unit = {
    // command.execute(this, round)
    undoManager.addCommand(
      new PlayCommand,
      oldRound,
      updatedRound
    )
  }

  def saveGame(): Unit =
    fileIO.save(round)
    notifyObservers(Event.Play)

  def loadGame(): Unit =
    val load = fileIO.load
    if load.isSuccess then
      round = load.get
      notifyObservers(Event.Play)

  def saveState(): Unit =
    caretaker.addMemento(Memento(round))

  def restoreState(): Unit =
    caretaker.getMemento.foreach(memento => round = memento.round)
}
