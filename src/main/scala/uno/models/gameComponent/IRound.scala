package uno.models.gameComponent

import uno.models.cardComponent.ICard
import uno.models.cardComponent.cardImp.Card
import uno.models.playerComponent.IPlayer
import uno.models.playerComponent.playerImp.Player

trait IRound {
  def players: List[IPlayer]
  def topCard: ICard
  def currentPlayer: Int
  def copy(
      players: List[IPlayer] = players,
      topCard: ICard = topCard,
      currentPlayer: Int = currentPlayer
  ): IRound
}
