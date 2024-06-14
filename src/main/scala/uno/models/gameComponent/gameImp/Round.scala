package uno.models.gameComponent.gameImp

import uno.models.cardComponent.cardImp.Card
import uno.models.gameComponent.IRound
import uno.models.playerComponent.playerImp.Player

case class Round(players: List[Player], topCard: Card, currentPlayer: Int) extends IRound {}
