package uno.models.gameComponent

import uno.models.cardComponent.cardImp.Card

trait IHand {
  def addCard(card: Card): IHand
  def addCards(cardlist: List[Card]): IHand
  def removeCard(card: Card): IHand
}