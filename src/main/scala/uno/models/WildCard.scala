package uno.models

import scala.io._

case class WildCard(color: Option[cardColors], value: cardValues) extends Card(color, value) {

  def randomCard: WildCard = WildCard(None, randomValue)
  // can card be played
  def canBePlayedOn(topCard: Card): Boolean = true
}

def randomValue =
  cardValues.values.toList(scala.util.Random.nextInt(cardValues.values.length))
