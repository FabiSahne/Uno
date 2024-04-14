// Worksheet in Scala
enum cardColors:
  case RED, GREEN, BLUE, YELLOW

enum cardValues:
  case ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, SKIP, REVERSE, DRAW_TWO, WILD, WILD_DRAW_FOUR

case class Card(color: cardColors, value: cardValues)

case class Player(name: String, var hand : List[Card])

class Deck {
  val allCards : List[Card] = for {
    color <- cardColors.values.toList
    value <- cardValues.values.toList
  } yield Card(color, value)
}

// shuffle card deck
var cards : List[Card] = scala.util.Random.shuffle(new Deck().allCards)

// draw a card
def drawCard() : Option[Card] = cards match {
  case Nil => None
  case topCard :: remainingCards =>
    cards = remainingCards
    Some(topCard)
}

