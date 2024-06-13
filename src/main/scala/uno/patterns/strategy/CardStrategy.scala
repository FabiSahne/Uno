package uno.patterns.strategy

import uno.controller.GameController
import uno.models.cardValues.WILD
import uno.models.{Card, CardFacade, Player, WildCard, cardColors}

trait CardStrategy {
  def execute(gameController: GameController, color: Option[cardColors]): Unit
}

case class SkipCommand() extends CardStrategy {
  override def execute(
      gameController: GameController,
      color: Option[cardColors] = None
  ): Unit =
    gameController.round = gameController.round.copy(
      currentPlayer =
        (gameController.round.currentPlayer + 1) % gameController.round.players.length
    )
}

case class ReverseCommand() extends CardStrategy {
  override def execute(
      gameController: GameController,
      color: Option[cardColors] = None
  ): Unit =
    println("Reverse command executed")
}

case class DrawTwoCommand() extends CardStrategy {
  override def execute(
      gameController: GameController,
      color: Option[cardColors] = None
  ): Unit =
    val idx = gameController.round.currentPlayer
    val cards = List.fill(2)(CardFacade().randomCard)
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

case class WildCommand() extends CardStrategy {
  override def execute(
      gameController: GameController,
      color: Option[cardColors]
  ): Unit =
    val new_card = WildCard(color, WILD)
    gameController.round = gameController.round.copy(topCard = new_card)
    println("Wild command executed")
}

case class WildDrawFourCommand() extends CardStrategy {
  override def execute(
      gameController: GameController,
      color: Option[cardColors]
  ): Unit =
    val idx = gameController.round.currentPlayer
    val cards = List.fill(4)(CardFacade().randomCard)
    gameController.round = gameController.round.copy(
      players = gameController.round.players.updated(
        idx,
        Player(
          gameController.round.currentPlayer,
          gameController.round.players(idx).hand.addCards(cards)
        )
      )
    )
    val new_card = WildCard(color, WILD)
    gameController.round = gameController.round.copy(topCard = new_card)
}
