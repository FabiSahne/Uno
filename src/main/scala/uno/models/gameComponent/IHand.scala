package uno.models.gameComponent

import uno.models.cardComponent.ICard

trait IHand(val cards: List[ICard]) {
  def addCard(card: ICard): IHand
  def addCards(cardlist: List[ICard]): IHand
  def removeCard(card: ICard): IHand
  def toXml: scala.xml.Elem
}
