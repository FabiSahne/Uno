package uno.models.cardComponent.cardTypeImp

import uno.models.cardComponent.ICardType
import uno.models.cardComponent.cardImp.{Card, cardColors, cardValues}

import scala.io.*

case class WildCard(color: Option[cardColors], value: cardValues)
    extends Card(color, value) with ICardType {
  // can card be played
  def canBePlayedOn(topCard: Card): Boolean = true
}
