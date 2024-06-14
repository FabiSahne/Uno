package controller

import uno.models.*
import uno.util.*
import uno.controller.*
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import uno.controller.GControllerImp.GameController
import uno.models.cardColors.*
import uno.models.cardComponent.cardTypeImp.{NormalCard, WildCard}
import uno.models.cardValues.*
import uno.models.gameComponent.gameImp.{Hand, Round}
import uno.models.playerComponent.playerImp
import uno.models.playerComponent.playerImp.Player
import uno.util.Event.Start

import java.io.ByteArrayOutputStream

class ControllerTest extends AnyWordSpec {
  "The Controller" should {
    val round =
      Round(
        List(
          Player(
            0,
            Hand(List(NormalCard(Some(RED), ONE), NormalCard(Some(RED), TWO)))
          )
        ),
        cardTypeImp.NormalCard(Some(RED), THREE)
      )
    val controller = GameController(round)
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
    "play card" in {
      val card = cardTypeImp.NormalCard(Some(RED), ONE)
      controller.playCard(card)
      controller.round.players.head.hand.cards should not contain card
    }
    "draw card" in {
      controller.drawCard()
      controller.round.players.head.hand.cards.size should be(2)
      controller.round = Round(
        List(
          playerImp.Player(
            0,
            Hand(List(cardTypeImp.NormalCard(Some(RED), ONE), cardTypeImp.NormalCard(Some(BLUE), TWO)))
          )
        )
      )
    }
    "quit game" in {
      class TestObserver(val controller: GameController) extends Observer:
        controller.add(this)
        var bing: Event = Start
        def update(e: Event): Unit = bing = e

      val testObserver = TestObserver(controller)
      val cards = controller.round.players.head.hand.cards
      controller.playCard(cards.head)
      controller.playCard(cards(1))
      testObserver.bing should be(Event.Quit)
    }
    "send commands" in {
      val round =
        gameImp.Round(
          List(
            playerImp.Player(
              0,
              Hand(
                List(
                  cardTypeImp.NormalCard(Some(RED), DRAW_TWO),
                  cardTypeImp.NormalCard(Some(RED), REVERSE),
                  cardTypeImp.NormalCard(Some(RED), SKIP),
                  WildCard(None, WILD),
                  WildCard(None, WILD_DRAW_FOUR),
                  cardTypeImp.NormalCard(Some(RED), THREE)
                )
              )
            )
          ),
          cardTypeImp.NormalCard(Some(RED), THREE)
        )
      val controller = GameController(round)
      val cards = controller.round.players.head.hand.cards
      val outCapture = ByteArrayOutputStream()
      Console.withOut(outCapture) {
        controller.playCard(cards.head)
      }
      outCapture.toString() should include("Draw Two command executed")
      Console.withOut(outCapture) {
        controller.playCard(cards(1))
      }
      outCapture.toString() should include("Reverse command executed")
      Console.withOut(outCapture) {
        controller.playCard(cards(2))
      }
      outCapture.toString() should include("Skip command executed")
      Console.withOut(outCapture) {
        controller.playCard(cards(3))
      }
      outCapture.toString() should include("Wild command executed")
      Console.withOut(outCapture) {
        controller.playCard(cards(4))
      }
      outCapture.toString() should include("Wild Draw Four command executed")
    }
    "save and restore state" in {
      val round =
        gameImp.Round(
          List(
            playerImp.Player(
              0,
              Hand(List(cardTypeImp.NormalCard(Some(RED), ONE), cardTypeImp.NormalCard(Some(RED), TWO)))
            )
          ),
          cardTypeImp.NormalCard(Some(RED), THREE)
        )
      val controller = GameController(round)
      controller.playCard(cardTypeImp.NormalCard(Some(RED), ONE))
      controller.round should not be round
      controller.restoreState()
      controller.round should be(round)
    }
  }
}
