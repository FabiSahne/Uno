package uno.models.gameComponent.gameImp

import uno.models.cardComponent.cardImp.{Card, CardFacade}
import uno.models.gameComponent.IHand

case class Hand(cards: List[Card] = CardFacade().randomCards(7)) extends IHand {
  def addCard(card: Card): Hand = copy(cards = card :: cards)
  
  def addCards(cardlist: List[Card]): Hand = copy(cards = cardlist ::: cards)

  def removeCard(card: Card): Hand = copy(cards = cards diff List(card))
}
