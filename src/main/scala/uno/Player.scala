package uno
import scala.collection.immutable.List

case class Player(hand: List[Card]) {
  def canPlay(card: Card): Boolean = {
    hand.exists(_.canBePlayedOn(card))
  }

  def playCard(card: Card): Option[Player] = {
    if (canPlay(card)) {
      val newHand = hand diff List(card)
      Some(Player(newHand))
    } else {
      None
    }
  }
}

