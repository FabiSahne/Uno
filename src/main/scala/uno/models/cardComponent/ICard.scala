package uno.models.cardComponent

import uno.models.cardComponent.cardImp.{Card, cardColors, cardValues}

trait ICard {
  def getColor: Option[cardColors]
  def getValue: cardValues
  def canBePlayedOn(topCard: Card): Boolean
  def getColorCode: String
}