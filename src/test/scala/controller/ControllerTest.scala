package controller

import uno.models.*
import uno.util.*
import uno.controller.*
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import uno.models.cardColors.*
import uno.models.cardValues.*
import uno.util.Event.Start

class ControllerTest extends AnyWordSpec {
  "The Controller" should {
    val round =
      Round(
        List(Player(0, Hand(List(Card(RED, ONE), Card(RED, TWO))))),
        Card(RED, THREE)
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
      val card = Card(RED, ONE)
      controller.playCard(card)
      controller.round.players.head.hand.cards should not contain card
    }
    "draw card" in {
      controller.drawCard()
      controller.round.players.head.hand.cards.size should be(2)
      controller.round =
        Round(List(Player(0, Hand(List(Card(RED, ONE), Card(BLUE, TWO))))))
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
  }
}
