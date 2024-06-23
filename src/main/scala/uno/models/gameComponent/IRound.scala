package uno.models.gameComponent

import play.api.libs.json.Writes
import uno.models.cardComponent.ICard
import uno.models.playerComponent.IPlayer

trait IRound {
  def players: List[IPlayer]
  def topCard: ICard
  def currentPlayer: Int
  def copy(
      players: List[IPlayer] = players,
      topCard: ICard = topCard,
      currentPlayer: Int = currentPlayer
  ): IRound
  def toXml: scala.xml.Elem
}
