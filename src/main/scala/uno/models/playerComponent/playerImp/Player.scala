package uno.models.playerComponent.playerImp

import uno.models.cardComponent.ICard
import uno.models.cardComponent.cardImp.Card
import uno.models.gameComponent.IHand
import uno.models.gameComponent.gameImp.Hand
import uno.models.playerComponent.IPlayer

import scala.util.{Failure, Success, Try}

case class Player(id: Int, hand: IHand) extends IPlayer {
  override def canPlay(card: ICard): Boolean = {
    hand.cards.exists(_.canBePlayedOn(card))
  }

  def playCard(card: ICard): Try[IPlayer] = {
    if (canPlay(card)) {
      Success(copy(hand = hand.removeCard(card)))
    } else {
      Failure(new IllegalArgumentException("Can't play that card"))
    }
  }
}
