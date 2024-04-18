package uno

class Deck {
  val allColors: List[cardColors] = List(cardColors.RED, cardColors.GREEN, cardColors.BLUE, cardColors.YELLOW)
  val allValues: List[cardValues] = List(
    cardValues.ZERO, cardValues.ONE, cardValues.TWO, cardValues.THREE, cardValues.FOUR,
    cardValues.FIVE, cardValues.SIX, cardValues.SEVEN, cardValues.EIGHT, cardValues.NINE,
    cardValues.SKIP, cardValues.REVERSE, cardValues.DRAW_TWO, cardValues.WILD, cardValues.WILD_DRAW_FOUR
  )
  
  var allCards: List[Card] = generateDeck()

  def generateDeck(): List[Card] = {
    val allCards = for {
      color <- allColors
      value <- allValues
    } yield Card(color, value)
    allCards
  }

  def shuffle(): Unit = {
    allCards = scala.util.Random.shuffle(allCards)
  }

  def draw(numCards: Int): List[Card] = {
    if (numCards <= allCards.length) {
      val (drawnCards, remainingCards) = allCards.splitAt(numCards)
      allCards = remainingCards
      drawnCards
    } else {
      throw new IllegalArgumentException("Not enough cards in the deck to draw")
    }
  }

  def isEmpty: Boolean = allCards.isEmpty
}
