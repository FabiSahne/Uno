package uno.models.cardComponent

import uno.models.cardComponent.cardImp.Card

trait ICardFacade {
  def randomCard: Card
  def randomCards(n: Int): List[Card]
}