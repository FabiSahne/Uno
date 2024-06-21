package uno.models.gameComponent.gameImp

import uno.models.cardComponent.ICard
import uno.models.cardComponent.cardImp.Card
import uno.models.gameComponent.IRound
import uno.models.playerComponent.IPlayer
import uno.models.playerComponent.playerImp.Player

case class Round(players: List[IPlayer], topCard: ICard, currentPlayer: Int)
    extends IRound {
  def copy(
      players: List[IPlayer] = players,
      topCard: ICard = topCard,
      currentPlayer: Int = currentPlayer
  ): Round = {
    Round(players, topCard, currentPlayer)
  }
}
