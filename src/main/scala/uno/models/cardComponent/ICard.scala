package uno.models.cardComponent

import uno.models.cardComponent.cardImp.{Card, cardColors, cardValues}

trait ICard {
  def getColor: Option[cardColors]
  def getValue: cardValues
  def getColorCode: String
}