package uno
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should._
class MySuite extends AnyWordSpec with Matchers {

  "Uno" should {
    "have cards" in {
      val c1 = uno.Card(uno.cardColors.RED, uno.cardValues.ONE)
      c1.color shouldEqual uno.cardColors.RED
      c1.value shouldEqual uno.cardValues.ONE
      val c2 = uno.Card(uno.cardColors.BLUE, uno.cardValues.ONE)
      val c3 = uno.Card(uno.cardColors.RED, uno.cardValues.TWO)
      val c4 = uno.Card(uno.cardColors.BLUE, uno.cardValues.TWO)
      c2.canBePlayedOn(c1) should be(true)
      c3.canBePlayedOn(c1) should be(true)
      c4.canBePlayedOn(c1) should be(false)
      c4.canBePlayedOn(c3) should be(true)
    }

    "have players" in {
      val hand1: List[uno.Card] = List(uno.Card(uno.cardColors.RED, uno.cardValues.ONE), uno.Card(uno.cardColors.RED, uno.cardValues.TWO))
      val p1 = uno.Player(1, 0, hand1)
      val topCard = uno.Card(uno.cardColors.RED, uno.cardValues.TWO)
      p1.canPlay(topCard) should be(true)
      val topCard2 = uno.Card(uno.cardColors.BLUE, uno.cardValues.THREE)
      p1.canPlay(topCard2) should be(false)
    }

    "shuffle deck" in {
      val deck = Deck.generateDeck()
      val originalDeck = deck.allCards
      val shuffledDeck = scala.util.Random.shuffle(originalDeck)

      shuffledDeck should not equal originalDeck
    }

    "allow Wild Draw Four to be played on any card" in {
      val c1 = uno.Card(uno.cardColors.RED, uno.cardValues.WILD_DRAW_FOUR)
      val c2 = uno.Card(uno.cardColors.BLUE, uno.cardValues.WILD_DRAW_FOUR)
      val c3 = uno.Card(uno.cardColors.RED, uno.cardValues.ONE)
      val c4 = uno.Card(uno.cardColors.BLUE, uno.cardValues.TWO)
      c1.canBePlayedOn(c2) should be(true)
      c1.canBePlayedOn(c3) should be(true)
      c1.canBePlayedOn(c4) should be(true)
    }

    "allow Wild to be played on any card" in {
      val c1 = uno.Card(uno.cardColors.RED, uno.cardValues.WILD)
      val c2 = uno.Card(uno.cardColors.BLUE, uno.cardValues.WILD)
      val c3 = uno.Card(uno.cardColors.RED, uno.cardValues.ONE)
      val c4 = uno.Card(uno.cardColors.BLUE, uno.cardValues.TWO)
      c1.canBePlayedOn(c2) should be(true)
      c1.canBePlayedOn(c3) should be(true)
      c1.canBePlayedOn(c4) should be(true)
    }

    "allow a player to play a card that matches the color or value of the top card" in {
      val hand = List(uno.Card(uno.cardColors.RED, uno.cardValues.ONE))
      val player = uno.Player(1, 0, hand)
      val topCard = uno.Card(uno.cardColors.RED, uno.cardValues.TWO)
      player.canPlay(topCard) should be(true)
    }

    "require a player to draw a card if they cannot play any of their cards" in {
      val hand = List(uno.Card(uno.cardColors.RED, uno.cardValues.ONE))
      val player = uno.Player(1, 0, hand)
      val topCard = uno.Card(uno.cardColors.BLUE, uno.cardValues.TWO)
      player.canPlay(topCard) should be(false)
    }

    "allow a player to play a Wild card on any turn" in {
      val hand = List(uno.Card(uno.cardColors.RED, uno.cardValues.WILD))
      val player = uno.Player(1, 0, hand)
      val topCard = uno.Card(uno.cardColors.BLUE, uno.cardValues.TWO)
      player.canPlay(topCard) should be(true)
    }

    "reduce number of cards in player's hand after playing a card" in {
      val hand = List(Card(cardColors.RED, cardValues.ONE), Card(cardColors.RED, cardValues.TWO))
      val player = Player(1, 0, hand)
      val cardToPlay = Card(cardColors.RED, cardValues.ONE)
      val initialHandSize = player.hand.length
      val newPlayer = player.playCard(cardToPlay)

      newPlayer.hand.length should be(initialHandSize - 1)
    }
  }
}
