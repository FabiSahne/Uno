package uno.models

import scala.io._

case class WildCard(color: Option[cardColors], value: cardValues)
    extends Card(color, value) {
  // can card be played
  def canBePlayedOn(topCard: Card): Boolean = true
}
