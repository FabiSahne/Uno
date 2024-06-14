package uno.models.gameComponent.gameImp

import uno.models.cardComponent.cardImp.{Card, CardFacade}
import uno.models.gameComponent.IHand

case class Hand(cards: List[Card] = CardFacade().randomCards(7)) extends IHand {
  override def addCard(card: Card): Hand = copy(cards = card :: cards)

  override def addCards(cardlist: List[Card]): Hand = copy(cards = cardlist ::: cards)

  override def removeCard(card: Card): Hand = copy(cards = cards diff List(card))
}
