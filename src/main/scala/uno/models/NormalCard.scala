package uno.models

import scala.io._

case class NormalCard(color: Option[cardColors], value: cardValues) extends Card(color, value) {
  // can card be played
  def canBePlayedOn(topCard: Card): Boolean = {
    this.color == topCard.getColor || this.value == topCard.getValue
  }
}

