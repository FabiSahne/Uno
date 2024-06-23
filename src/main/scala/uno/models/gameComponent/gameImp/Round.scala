package uno.models.gameComponent.gameImp

import play.api.libs.json._
import uno.models.cardComponent.ICard
import uno.models.cardComponent.cardImp._
import uno.models.cardComponent.cardImp.Card._
import uno.models.gameComponent._
import uno.models.playerComponent.IPlayer
import uno.models.playerComponent.playerImp.Player
import uno.models.playerComponent.playerImp.Player._

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

  implicit val roundFormat: Format[IRound] = new Format[IRound]:
    def writes(round: IRound): JsValue =
      Json.obj(
        "players" -> Json.toJson(round.players),
        "topCard" -> Json.toJson(round.topCard),
        "currentPlayer" -> Json.toJson(round.currentPlayer)
      )

    def reads(json: JsValue): JsResult[IRound] =
      for
        players <- (json \ "players").validate[List[IPlayer]]
        topCard <- (json \ "topCard").validate[ICard]
        currentPlayer <- (json \ "currentPlayer").validate[Int]
      yield Round(players, topCard, currentPlayer)
