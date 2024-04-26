package uno
import scala.collection.immutable.List
import scala.util.{Failure, Success, Try}

case class Player(id: Int, score: Int, hand: List[Card]) {
  def canPlay(card: Card): Boolean = {
    hand.exists(_.canBePlayedOn(card))
  }

  def playCard(card: Card): Try[Player] = {
    if (canPlay(card)) {
      val newHand = hand.filterNot(c => c.color == card.color && c.value == card.value)
      Success(Player(id, score, newHand))
    } else {
      Failure(IllegalArgumentException("Can't play that card"))
    }
  }
}