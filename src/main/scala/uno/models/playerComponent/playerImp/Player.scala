package uno.models.playerComponent.playerImp

import uno.models.cardComponent.cardImp.Card
import uno.models.gameComponent.gameImp.Hand
import uno.models.playerComponent.IPlayer

import scala.util.{Failure, Success, Try}

case class Player(id: Int, hand: Hand) extends IPlayer {
  override def canPlay(card: Card): Boolean = {
    hand.cards.exists(_.canBePlayedOn(card))
  }

  override def playCard(card: Card): Try[Player] = {
    if (canPlay(card)) {
      Success(copy(hand = hand.removeCard(card)))
    } else {
      Failure(new IllegalArgumentException("Can't play that card"))
    }
  }
}
