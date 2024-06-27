package uno.models.playerComponent.playerImp

import com.google.inject.Inject
import play.api.libs.json.*
import uno.models.cardComponent.ICard
import uno.models.gameComponent.IHand
import uno.models.gameComponent.gameImp.Hand
import uno.models.gameComponent.gameImp.Hand.*
import uno.models.playerComponent.IPlayer

import scala.util.{Failure, Success, Try}

case class Player @Inject (id: Int, hand: IHand) extends IPlayer:

  override def canPlay(card: ICard): Boolean =
    hand.getCards.exists(_.canBePlayedOn(card))

  def playCard(card: ICard): Try[IPlayer] =
    if (canPlay(card)) {
      Success(copy(hand = hand.removeCard(card)))
    } else {
      Failure(new IllegalArgumentException("Can't play that card"))
    }

  def toXml: scala.xml.Elem =
    <player>
      <id>{id}</id>
      {hand.toXml}
    </player>

object Player:

  def fromXml(node: scala.xml.Node): IPlayer =
    val id = (node \ "id").text.toInt
    val hand = Hand.fromXml((node \ "hand").head)
    Player(id, hand)

  implicit val playerFormat: Format[IPlayer] = new Format[IPlayer]:
    def writes(player: IPlayer): JsValue =
      Json.obj(
        "id" -> Json.toJson(player.id),
        "hand" -> Json.toJson(player.hand)
      )

    def reads(json: JsValue): JsResult[IPlayer] =
      for
        id <- (json \ "id").validate[Int]
        hand <- (json \ "hand").validate[IHand]
      yield Player(id, hand)
