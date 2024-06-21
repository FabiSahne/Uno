package uno.models.cardComponent
import uno.models.cardComponent.cardImp.{Card, cardColors, cardValues}

trait ICardType {
  def canBePlayedOn(topCard: Card): Boolean
}