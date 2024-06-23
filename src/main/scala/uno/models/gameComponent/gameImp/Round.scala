package uno.models.gameComponent.gameImp

import play.api.libs.json.*
import uno.models.cardComponent.ICard
import uno.models.cardComponent.cardImp.*
import uno.models.gameComponent.*
import uno.models.playerComponent.IPlayer
import uno.models.playerComponent.playerImp.Player

import scala.xml.Elem

case class Round(players: List[IPlayer], topCard: ICard, currentPlayer: Int)
    extends IRound:

  def copy(
      players: List[IPlayer] = players,
      topCard: ICard = topCard,
      currentPlayer: Int = currentPlayer
  ): Round =
    Round(players, topCard, currentPlayer)

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

object Round:
  def fromXml(node: Elem): IRound =
    val players =
      (node \ "players" \ "player").map(player => Player.fromXml(player)).toList
    val topCard = Card.fromXml((node \ "topCard").head)
    val currentPlayer = (node \ "currentPlayer").text.toInt
    Round(players, topCard, currentPlayer)

  implicit val roundFormat: Format[IRound] = Json.format[IRound]
  implicit val roundReads: Reads[IRound] = Json.reads[IRound]
  implicit val roundWrites: Writes[IRound] = Json.writes[IRound]
