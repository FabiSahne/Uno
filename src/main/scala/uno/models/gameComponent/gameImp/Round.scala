package uno.models.gameComponent.gameImp

import play.api.libs.json._
import uno.models.cardComponent.ICard
import uno.models.cardComponent.cardImp._
import uno.models.gameComponent._
import uno.models.playerComponent.IPlayer

import scala.xml.Elem

case class Round(players: List[IPlayer], topCard: ICard, currentPlayer: Int)
    extends IRound {
  def copy(
      players: List[IPlayer] = players,
      topCard: ICard = topCard,
      currentPlayer: Int = currentPlayer
  ): Round = {
    Round(players, topCard, currentPlayer)
  }

  def toXml: Elem =
    <round>
      <players>
        {players.map(_.toXml)}
      </players>
      <topCard>
        {topCard.toXml}
      </topCard>
      <currentPlayer>
        {currentPlayer}
      </currentPlayer>
    </round>

  implicit val colorWrites: Writes[cardColors] = (color: cardColors) => Json.obj(
    "color" -> color
  )

  implicit val valueWrites: Writes[cardValues] = (value: cardValues) => Json.obj(
    "value" -> value
  )

  implicit val optionalColorWrite: Writes[Option[cardColors]] =
    Writes.optionWithNull(Json.toJson(_))

  implicit val cardWrites: Writes[ICard] = (card: ICard) => Json.obj(
    "color" -> card.getColor,
    "value" -> card.getValue
  )
  
  implicit val handWrites: Writes[IHand] = (hand: IHand) => Json.obj(
    "cards" -> hand.cards
  )

  implicit val playerWrites: Writes[IPlayer] = (player: IPlayer) => Json.obj(
    "id" -> player.id,
    "hand" -> player.hand
  )

  implicit val roundWrites: Writes[IRound] = (round: IRound) =>
    Json.obj(
      "players" -> Json.arr(round.players.map(player => Json.toJson(player))),
      "topCard" -> Json.toJson(round.topCard),
      "currentPlayer" -> round.currentPlayer
    )
}
