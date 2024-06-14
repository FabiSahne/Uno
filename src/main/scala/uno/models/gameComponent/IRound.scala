package uno.models.gameComponent

import uno.models.cardComponent.cardImp.Card
import uno.models.playerComponent.playerImp.Player

trait IRound {
  def players: List[Player]
  def topCard: Card
  def currentPlayer: Int
}