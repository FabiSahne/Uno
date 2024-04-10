package uno

// Worksheet in Scala
enum cardColors:
  case RED, GREEN, BLUE, YELLOW

enum cardValues:
  case ZERO
  case ONE
  case TWO
  case THREE
  case FOUR
  case FIVE
  case SIX
  case SEVEN
  case EIGHT
  case NINE
  case SKIP
  case REVERSE
  case DRAW_TWO
  case WILD
  case WILD_DRAW_FOUR

case class Card(color: cardColors, value: cardValues)

// can card be played
def canPlayCard(card: Card, topCard: Card): Boolean = {
  card.color == topCard.color || card.value == topCard.value || card.value == cardValues.WILD || card.value == cardValues.WILD_DRAW_FOUR
}