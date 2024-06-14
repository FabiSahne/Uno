package uno.models.gameComponent.gameImp

import uno.models.cardComponent.cardImp.{Card, CardFacade}
import uno.models.gameComponent.IRound
import uno.models.gameComponent.gameImp.Hand
import uno.models.playerComponent.playerImp
import uno.models.playerComponent.playerImp.Player

case class Round(players: List[Player], topCard: Card, currentPlayer: Int) extends IRound {}
