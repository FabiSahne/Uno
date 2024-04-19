package uno
import scala.collection.immutable.List

private def generateDeck(): List[Card] = {
  val allCards = for {
    color <- List(cardColors.RED, cardColors.GREEN, cardColors.BLUE, cardColors.YELLOW)
    value <- List(
      cardValues.ZERO, cardValues.ONE, cardValues.TWO, cardValues.THREE, cardValues.FOUR,
      cardValues.FIVE, cardValues.SIX, cardValues.SEVEN, cardValues.EIGHT, cardValues.NINE,
      cardValues.SKIP, cardValues.REVERSE, cardValues.DRAW_TWO, cardValues.WILD, cardValues.WILD_DRAW_FOUR
    )
  } yield Card(color, value)
  allCards
}

case class Deck(cards: List[Card] = generateDeck()) {

  def shuffle(): Deck = {
    Deck(scala.util.Random.shuffle(cards))
  }

  def draw(numCards: Int): (Deck, List[Card]) = {
    if (numCards <= cards.length) {
      val (drawnCards, remainingCards) = cards.splitAt(numCards)
      (Deck(remainingCards), drawnCards)
    } else {
      throw new IllegalArgumentException("Not enough cards in the deck to draw")
    }
  }
}
