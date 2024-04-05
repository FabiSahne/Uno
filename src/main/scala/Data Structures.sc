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

