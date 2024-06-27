package uno.models.gameComponent.gameImp

import com.google.inject.Inject
import play.api.libs.json.*
import uno.models.cardComponent.ICard
import uno.models.cardComponent.cardImp.*
import uno.models.cardComponent.cardImp.Card.*
import uno.models.gameComponent.IHand

import scala.xml.Elem

case class Hand @Inject() (cards: List[ICard] = randomCards(7)) extends IHand:

  override def getCards: List[ICard] = cards

  override def addCard(card: ICard): Hand = copy(cards = card :: cards)

  override def addCards(cardlist: List[ICard]): Hand =
    copy(cards = cardlist ::: cards)

  override def removeCard(card: ICard): Hand =
    val toRemove = cards.find(x => x.equals(card)).get
    copy(cards = cards diff List(toRemove))

  override def toXml: scala.xml.Elem =
    <hand>
      {cards.map(_.toXml)}
    </hand>

object Hand:
  def fromXml(node: scala.xml.Node): Hand =
    val cardList = (node \ "card").map(card => Card.fromXml(card)).toList
    Hand(cardList)

  implicit val handFormat: Format[IHand] = new Format[IHand]:
    def writes(hand: IHand): JsValue =
      Json.obj(
        "cards" -> Json.toJson(hand.getCards)
      )

    def reads(json: JsValue): JsResult[IHand] =
      for cardList <- (json \ "cards").validate[List[ICard]]
      yield Hand(cardList)
