package controller

import com.google.inject.{AbstractModule, Guice, TypeLiteral}
import net.codingwell.scalaguice.ScalaModule
import uno.models.*
import uno.util.*
import uno.controller.*
import uno.controller.GControllerImp.GameController
import uno.models.cardComponent.cardImp.*
import uno.models.cardComponent.cardImp.cardColors.*
import uno.models.cardComponent.cardImp.cardValues.*
import uno.models.gameComponent.{IHand, IRound, gameImp}
import uno.models.gameComponent.gameImp.{Hand, Round}
import uno.models.playerComponent.{IPlayer, playerImp}
import uno.models.playerComponent.playerImp.Player
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import uno.models.cardComponent.ICard
import uno.models.fileioComponent.IFileIO
import uno.models.fileioComponent.fileioImp.{FileIOJSON, FileIOXML}
import uno.util.Event.Start

class ControllerTest extends AnyWordSpec {
  "The Controller" should {
    val round =
      Round(
        List(
          Player(
            0,
            Hand(List(Card(Some(RED), ONE), Card(Some(RED), TWO)))
          )
        ),
        Card(Some(RED), THREE),
        currentPlayer = 0
      )
    val controller = GameController(round)
    "retrieve round" in {
      controller.getRound.topCard.getValue should be(THREE)
    }
    "play card" in {
      controller.playCard(Card(Some(RED), ONE))
      controller.round.players.head.hand.getCards.size should be(1)
    }
    "draw card" in {
      controller.drawCard()
      controller.round.players.head.hand.getCards.size should be(2)
    }
    "undo and redo" in {
      val preUndoRedo = controller.round
      controller.playCard(Card(Some(RED), TWO))
      controller.round.topCard.getValue should not be preUndoRedo.topCard.getValue
      val preUndo = controller.round
      controller.undo()
      controller.round.topCard.getValue should be(preUndoRedo.topCard.getValue)
      controller.redo()
      controller.round.topCard.getValue should be(preUndo.topCard.getValue)
    }
    "quit game" in {
      class TestObserver(val controller: GameController) extends Observer:
        controller.add(this)
        var bing: Event = Start
        def update(e: Event): Unit = bing = e

      val testObserver = TestObserver(controller)
      val cards = controller.round.players.head.hand.getCards
      for card <- cards do controller.playCard(card)
      testObserver.bing should be(Event.Quit)
    }
    "save and restore state" in {
      val round =
        gameImp.Round(
          List(
            playerImp.Player(
              0,
              Hand(List(Card(Some(RED), ONE), Card(Some(RED), TWO)))
            )
          ),
          Card(Some(RED), THREE),
          currentPlayer = 0
        )
      val controller = GameController(round)
      controller.saveState()
      controller.playCard(Card(Some(RED), ONE))
      controller.round should not be round
      controller.restoreState()
      controller.round should be(round)
    }
    "save and restore game in json" in {
      class Module extends AbstractModule with ScalaModule:
        override def configure(): Unit =
          bind(classOf[GameControllerInterface]).to(classOf[GameController])
          bind(classOf[ICard]).to(classOf[Card])
          bind(classOf[IHand]).to(classOf[Hand])
          bind(classOf[IRound]).to(classOf[Round])
          bind(classOf[IPlayer]).to(classOf[Player])
          bind(classOf[IFileIO]).to(classOf[FileIOJSON])

          bind(new TypeLiteral[Option[cardColors]] {}).toInstance(None)
          bind(new TypeLiteral[cardValues] {}).toInstance(ONE)
          bind(new TypeLiteral[Hand] {}).toInstance(Hand())
          bind(new TypeLiteral[Round] {})
            .toInstance(Round(List.empty, Card(None, ONE), 0))
          bind(new TypeLiteral[Player] {}).toInstance(Player(0, Hand()))
      val injector = Guice.createInjector(new Module)

      val round =
        gameImp.Round(
          List(
            playerImp.Player(
              0,
              Hand(List(Card(Some(RED), ONE), Card(Some(RED), TWO)))
            )
          ),
          Card(Some(RED), THREE),
          currentPlayer = 0
        )
      val controller = injector.getInstance(classOf[GameControllerInterface])
      controller.setRound(round)
      controller.saveGame()
      controller.loadGame()
      controller.getRound.players.size should be(2)
      // java.nio.file.Files.delete(java.nio.file.Paths.get("round.json"))
    }
    "notify its observers on quit" in {
      class TestObserver(controller: GameController) extends Observer:
        controller.add(this)
        var bing = false
        def update(e: Event): Unit = bing = true
      val testObserver = TestObserver(controller)
      testObserver.bing should be(false)
      controller.quitGame()
      testObserver.bing should be(true)
    }
    "notify its observers on start" in {
      class TestObserver(controller: GameController) extends Observer:
        controller.add(this)
        var bing = false
        def update(e: Event): Unit = bing = true
      val testObserver = TestObserver(controller)
      testObserver.bing should be(false)
      controller.initGame()
      testObserver.bing should be(true)
    }
    "don't notify its observers on remove" in {
      class TestObserver(val controller: GameController) extends Observer:
        controller.add(this)
        var bing = false

        def update(e: Event): Unit = bing = true

      val testObserver = TestObserver(controller)
      testObserver.controller.remove(testObserver)
      testObserver.bing should be(false)
      controller.quitGame()
      testObserver.bing should be(false)
    }
    "handle special cards" in {
      val round = Round(
        List(
          Player(
            0,
            Hand(
              List(
                Card(Some(RED), DRAW_TWO),
                Card(Some(RED), REVERSE),
                Card(Some(RED), SKIP),
                Card(None, WILD),
                Card(None, WILD_DRAW_FOUR)
              )
            )
          )
        ),
        Card(Some(RED), THREE),
        currentPlayer = 0
      )
      val controller = GameController(round)
      for card <- round.players.head.hand.getCards do controller.playCard(card)
      controller.round.players.head.hand.getCards.size should be(2)
    }
    "coose color" in {
      val round = Round(
        List(
          Player(
            0,
            Hand(
              List(
                Card(Some(RED), TWO),
                Card(Some(RED), THREE)
              )
            )
          )
        ),
        Card(None, WILD),
        currentPlayer = 0
      )
      val controller = GameController(round)
      controller.chooseColor(5)
      controller.round.topCard.getColor should be(None)
      controller.chooseColor(1)
      controller.round.topCard.getColor should be(Some(RED))
      val newround = Round(
        List(
          Player(
            0,
            Hand(
              List(
                Card(Some(RED), TWO),
                Card(Some(RED), THREE)
              )
            )
          )
        ),
        Card(None, WILD_DRAW_FOUR),
        currentPlayer = 0
      )
      val newcontroller = GameController(newround)
      newcontroller.chooseColor(2)
      newcontroller.round.topCard.getColor should be(Some(BLUE))
      newcontroller.chooseColor(3)
      newcontroller.round.topCard.getColor should be(Some(GREEN))
      newcontroller.chooseColor(4)
      newcontroller.round.topCard.getColor should be(Some(YELLOW))
    }
  }
}
