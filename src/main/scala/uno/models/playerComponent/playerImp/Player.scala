package uno.models.playerComponent.playerImp

import play.api.libs.json.*
import uno.models.cardComponent.ICard
import uno.models.gameComponent.IHand
import uno.models.gameComponent.gameImp.Hand
import uno.models.playerComponent.IPlayer

import scala.util.{Failure, Success, Try}

case class Player(id: Int, hand: IHand) extends IPlayer:

  override def canPlay(card: ICard): Boolean =
    hand.cards.exists(_.canBePlayedOn(card))

  def playCard(card: ICard): Try[IPlayer] =
    if (canPlay(card)) {
      Success(copy(hand = hand.removeCard(card)))
    } else {
      Failure(new IllegalArgumentException("Can't play that card"))
    }

  def toXml: scala.xml.Elem =
    <player>
      <id>
        {id}
      </id>
      <hand>
        {hand.toXml}
      </hand>
    </player>

object Player:

  def fromXml(node: scala.xml.Node): IPlayer =
    val id = (node \ "id").text.toInt
    val hand = Hand.fromXml((node \ "hand").head)
    Player(id, hand)
