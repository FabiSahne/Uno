package uno.models.gameComponent.gameImp

import uno.models.cardComponent.ICard
import uno.models.cardComponent.cardImp._
import uno.models.gameComponent.IHand

case class Hand(override val cards: List[ICard] = randomCards(7)) extends IHand(cards) {
  override def addCard(card: ICard): Hand = copy(cards = card :: cards)

  override def addCards(cardlist: List[ICard]): Hand = copy(cards = cardlist ::: cards)

  override def removeCard(card: ICard): Hand = copy(cards = cards diff List(card))
}
