package uno.models.gameComponent

import uno.models.cardComponent.ICard
import uno.models.cardComponent.cardImp.Card

trait IHand(val cards: List[ICard]) {
  def addCard(card: ICard): IHand
  def addCards(cardlist: List[ICard]): IHand
  def removeCard(card: ICard): IHand
}