package uno
import scala.collection.immutable.List

case class Player(id: Int, score: Int, hand: List[Card]) {
  def canPlay(card: Card): Boolean = {
    hand.exists(_.canBePlayedOn(card))
  }

  def playCard(card: Card): Player = {
    if (canPlay(card)) {
      val newHand = hand.filterNot(c => c.color == card.color && c.value == card.value)
      Player(id, score, newHand)
    } else {
      throw new IllegalArgumentException("Can't play that card")
    }
  }
}