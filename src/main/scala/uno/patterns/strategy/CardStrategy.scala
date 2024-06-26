package uno.patterns.strategy

import uno.controller.GControllerImp.GameController
import uno.models.cardComponent.cardImp._
import uno.models.playerComponent.playerImp.Player
import uno.models.cardComponent.cardImp.cardValues.WILD

trait CardStrategy {
  def execute(gameController: GameController, color: Option[cardColors]): Unit
}

case class SkipStrategy() extends CardStrategy {
  override def execute(
      gameController: GameController,
      color: Option[cardColors] = None
  ): Unit =
    gameController.round = gameController.round.copy(
      currentPlayer =
        (gameController.round.currentPlayer + 1) % gameController.round.players.length
    )
}

case class ReverseStrategy() extends CardStrategy {
  override def execute(
      gameController: GameController,
      color: Option[cardColors] = None
  ): Unit =
    println("Reverse command executed")
}

case class DrawTwoStrategy() extends CardStrategy {
  override def execute(
      gameController: GameController,
      color: Option[cardColors] = None
  ): Unit =
    val idx = gameController.round.currentPlayer
    val cards = List.fill(2)(randomCard)
    gameController.round = gameController.round.copy(
      players = gameController.round.players.updated(
        idx,
        Player(
          gameController.round.currentPlayer,
          gameController.round.players(idx).hand.addCards(cards)
        )
      )
    )
}

case class WildStrategy() extends CardStrategy {
  override def execute(
      gameController: GameController,
      color: Option[cardColors]
  ): Unit =
//    val new_card = cardTypeImp.WildCard(color, WILD)
    val new_card = Card(color, WILD)
    gameController.round = gameController.round.copy(topCard = new_card)
    println("Wild command executed")
}

case class WildDrawFourStrategy() extends CardStrategy {
  override def execute(
      gameController: GameController,
      color: Option[cardColors]
  ): Unit =
    val idx = gameController.getRound.currentPlayer
    val cards = List.fill(4)(randomCard)
    gameController.setRound(
      gameController.getRound.copy(
        players = gameController.getRound.players.updated(
          idx,
          Player(
            gameController.getRound.currentPlayer,
            gameController.getRound.players(idx).hand.addCards(cards)
          )
        )
      )
    )
    val new_card = Card(color, WILD)
    gameController.setRound(gameController.getRound.copy(topCard = new_card))
}
