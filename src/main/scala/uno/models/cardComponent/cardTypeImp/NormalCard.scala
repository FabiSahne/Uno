package uno.models.cardComponent.cardTypeImp

import uno.models.cardComponent.ICardType
import uno.models.cardComponent.cardImp.{Card, cardColors, cardValues}

import scala.io.*

case class NormalCard(color: Option[cardColors], value: cardValues) extends Card(color, value) with ICardType {
  // can card be played
  def canBePlayedOn(topCard: Card): Boolean = {
    this.color == topCard.getColor || this.value == topCard.getValue
  }
}
