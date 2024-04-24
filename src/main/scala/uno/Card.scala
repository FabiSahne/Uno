// Worksheet in Scala
package uno

enum cardColors:
  case RED, GREEN, BLUE, YELLOW

enum cardValues:
  case ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, SKIP, REVERSE, DRAW_TWO, WILD, WILD_DRAW_FOUR

case class Card(color: cardColors, value: cardValues) {
  // can card be played
  def canBePlayedOn(topCard: Card): Boolean = {
    this.color == topCard.color || this.value == topCard.value || this.value == cardValues.WILD || this.value == cardValues.WILD_DRAW_FOUR
  }
}


