package uno.models

case class Deck(allCards: List[Card]) {
  def shuffle(): Deck = {
    Deck(scala.util.Random.shuffle(allCards))
  }

  def draw(numCards: Int): (List[Card], Deck) = {
    if (numCards <= allCards.length) {
      val (drawnCards, remainingCards) = allCards.splitAt(numCards)
      (drawnCards, Deck(remainingCards))
    } else {
      throw new IllegalArgumentException("Not enough cards in the deck to draw")
    }
  }

  def isEmpty: Boolean = allCards.isEmpty
}

object Deck {
  val allColors: List[cardColors] = List(cardColors.RED, cardColors.GREEN, cardColors.BLUE, cardColors.YELLOW)
  val allValues: List[cardValues] = List(
    cardValues.ZERO, cardValues.ONE, cardValues.TWO, cardValues.THREE, cardValues.FOUR,
    cardValues.FIVE, cardValues.SIX, cardValues.SEVEN, cardValues.EIGHT, cardValues.NINE,
    cardValues.SKIP, cardValues.REVERSE, cardValues.DRAW_TWO, cardValues.WILD, cardValues.WILD_DRAW_FOUR
  )

  def generateDeck(): Deck = {
    val allCards = for {
      color <- allColors
      value <- allValues
    } yield Card(color, value)
    Deck(allCards)
  }
}