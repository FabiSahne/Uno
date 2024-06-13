package uno.models

case class Hand(cards: List[Card] = CardFacade().randomCards(7)) {
  def addCard(card: Card): Hand = copy(cards = card :: cards)

  def removeCard(card: Card): Hand = copy(cards = cards diff List(card))
}
